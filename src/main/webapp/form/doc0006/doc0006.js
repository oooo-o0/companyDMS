/**
 * doc0006.js
 * 文書閲覧（管理者用）
 * @author D.Ikeda
 *    Date:2021/05/03
 * @version 1.0.0
 *
 */


//グローバル変数
var strYyyyMm = "";      // 年月の保存用
var userInfoArray = [];  // ログインユーザー情報
var emplInfoArray = [];  // 対象社員情報
var docType = "";  // 閲覧文書分類コード


/* *********************************************
*初期処理
********************************************** */
function docInit() {
	userInfoArray = init("");
	var emplName = userInfoArray[1];
	$("#useName").text(emplName);
	//閉じるボタンのクリックイベント設定
	$("#closeBtn").click(function() {
    window.close(); 
});

	return;
}

/* *********************************************
* 画面の初期ロード
********************************************** */
$(window).on('load', function () {

    // 初期化
    docInit();

    // 起動パラメータ取得（例: ?sr=1?ym=202103?ecd=99998?dt=001?dn=filename）
    var parm = $(location).attr('search');
    var parmArray = String(parm).split("?"); // ['', 'sr=1', 'ym=202103', 'ecd=99998', 'dt=001', 'dn=filename']

    // キー:バリュー形式に変換
    // キー:バリュー形式に変換（安全版：値に '=' が含まれていてもOK）
    var paramMap = {};
    for (var i = 1; i < parmArray.length; i++) {
		var idx = parmArray[i].indexOf("=");
		if (idx > 0) {
			var key = parmArray[i].substring(0, idx);
			var val = parmArray[i].substring(idx + 1);
			paramMap[key] = decodeURIComponent(val);
    }
}


    // 各値取得
    strYyyyMm = paramMap["ym"];
    docType = paramMap["dt"];
    var docName = paramMap["dn"];
    var emplCode = paramMap["ecd"];

    // 対象社員情報取得
    if (emplCode) {
        emplInfoArray = getEmplMstInfo(emplCode);
    } else {
        emplInfoArray = getEmplMstInfo(userInfoArray[0]);
    }

    // 各表示項目セット
    setTargetYm(strYyyyMm);
    setDocTypeLabel(docType);
    setTargetEmplName(emplCode || (emplInfoArray.length ? emplInfoArray[0] : ""));
    setFileName(docName);

    // 文書情報取得
    subGet();
});


/* *********************************************
* 年月ラベル表示（yyyy/MM形式）
********************************************** */
function setTargetYm(ym4) {
    if (!ym4 || ym4.length !== 6) return;
    var formattedYm = ym4.slice(0, 4) + "/" + ym4.slice(4);
    $("#targetYm").text(formattedYm);
}

/* *********************************************
* 社員名ラベル表示（社員コードから名称）
********************************************** */
function setTargetEmplName(emplCode) {
    if (!emplCode) return;
    var emplInfo = getEmplMstInfo(emplCode);
    if (emplInfo && emplInfo.length > 1) {
        $("#targetEmplName").text(emplInfo[1]); // 1番目が名前と想定
    } else {
        $("#targetEmplName").text(emplCode); // 取得失敗時はコード表示
    }
}

/* *********************************************
 * 文書分類名称ラベル（マスタ参照）
 ********************************************** */
function setDocTypeLabel(docType) {
    var name = getNameMst("doc_type", "1", docType); // docTypeコードを渡す
    $("#docType").text(String(name));
}

/* *********************************************
 * ファイル名表示
 ********************************************** */
function setFileName(docName) {
	var decDocName = decodeBase64Utf8(docName);
    $("#fileName").text(decDocName);
}

/* *********************************************
*文書内容取得処理
********************************************** */
function subGet() {
    if (!inputCheck()) {
        return;
    }

    var url = "http://localhost:8080/ibiDoc/SelectDocumentInfoServlet?ACTION=";
    var action = "search";
    url += action;
    
    var yearMonth = $("#targetYm").text().replace("/", "");
    var emplName = emplInfoArray[0];
    

    var senddata = {
    send_recive_type: "1",
    year_month: yearMonth,
    empl_code: emplName,
    doc_name: $("#fileName").text(),
    doc_type: docType
  };


  // ajax通信（同期）
  var jqXHR = postSeatchSync(senddata, url);
  
  // レスポンスJSONを取得
    var data = jqXHR.responseJSON;
  
  // 通信後、結果コード取得
  var retCode = showData(data);

    if (retCode === "-1") {
        alert("文書情報の取得に失敗しました。");
        window.close();
        
    } else if (retCode === "1") {
        const docInfo = data[0];
        $("#docTitle").text(docInfo.doc_title || "");
        const from = docInfo.expiration_from_dateTime;
        const to = docInfo.expiration_to_dateTime;
        $("#dateFromTo").text(formatExpiration(from, to));
    } else {
        alert("エラーが発生しました。");
        window.close();
    }
}

/**
 * 有効期限の表示形式に整形（例: 2025/06/01 - 2025/06/02）
 * @param {string} from - 開始日 (例: "20250605")
 * @param {string} to - 終了日 (例: "20280705")
 * @returns {string} - 整形済みの文字列
 */
function formatExpiration(from, to) {
    // null や想定外のフォーマット対策
    if (!from || from.length !== 8 || !to || to.length !== 8) return "";

    const format = (dateStr) => {
        const yyyy = dateStr.substring(0, 4);
        const mm = dateStr.substring(4, 6);
        const dd = dateStr.substring(6, 8);
        return `${yyyy}/${mm}/${dd}`;
    };

    return `${format(from)} - ${format(to)}`;
}
/* *********************************************
*文書内容を取得
********************************************** */
function showData(data) {
    if (!data || data.length === 0) return "-1";
    return String(data[0].rtn_code || "-1");
}

    
/* *********************************************
* 入力チェック（起動パラメータの最低限チェック）
********************************************** */
function inputCheck() {
    if (!strYyyyMm || strYyyyMm.length !== 6) {
        alert("年月の指定が不正です。");
        return false;
    }
    if (!docType) {
        alert("文書分類の指定が不正です。");
        return false;
    }
    if (!$("#fileName").text()) {
        alert("文書ファイル名が不正です。");
        return false;
    }
    return true;
}


