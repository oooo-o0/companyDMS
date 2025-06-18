/**
 * doc0007.js
 * 文書更新（管理者用）
 * @author A.Tomita
 *    Date:2025/06/09
 * @version 1.0.0
 *
 */


//グローバル変数
var strYyyyMm = "";      // 年月の保存用
var userInfoArray = [];  // ログインユーザー情報
var emplInfoArray = [];  // 対象社員情報
var docType = "";  // 閲覧文書分類コード
var docName = "";
var decDocName = "";
var empl_code = "";

/* *********************************************
*エラーメッセージダイアログを定義
********************************************** */
$(function() {
	$("#message").dialog({
		autoOpen: false,
		modal: true,
		title: "エラーメッセージ",
		width: 400,
		height: 200,
		buttons: [
			{
				text: 'OK',
				class: 'd-button',
				click: function() {
					//ボタンを押したときの処理
					$(this).dialog("close");
					window.close();
				}
			}
		]
	});
});
/* *********************************************
 *処理メッセージ表示
 ********************************************** */
function displayMessage(str) {
	$('#message').empty();
	$('#message').append("<p>" + str + "</p>");
	$("#message").dialog("open");
	return false;
}
/* *********************************************
*初期処理
********************************************** */
function docInit() {
	userInfoArray = init("");
	var emplName = userInfoArray[1];
	$("#useName").text(emplName);
	//登録ボタンのイベント設定
	$("#updateBtn").click(function() {
		subUpdate();
	});
	//ファイル名選択表示
	$("#fileSelectBtn").on("change", function() {
		const file = this.files[0];
		if (file) {
			$("#file_select_text").val(file.name);
		}
	});


	return;
}

/* *********************************************
* 画面の初期ロード
********************************************** */
$(window).on('load', function() {

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
	docName = paramMap["dn"];
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
	empl_code = String(emplCode);
	if (!emplCode) return;
	var emplInfo = getEmplMstInfo(emplCode);
	if (emplInfo && emplInfo.length > 1) {
		$("#targetEmplName").text(emplInfo[1]); // 1番目が名前と想定
	} else {
		$("#targetEmplName").text(emplCode); // 取得失敗時はコード表示
	}
}
/* *********************************************
 * ファイル名初期表示
 ********************************************** */
function setFileName(docName) {
	decDocName = decodeBase64Utf8(docName);
	$("#file_select_text").val(decDocName);
}


/* *********************************************
 * 文書分類名称ラベル（マスタ参照）
 ********************************************** */
function setDocTypeLabel(docType) {
	var name = getNameMst("doc_type", "1", docType); // docTypeコードを渡す
	$("#docType").text(String(name));
}

/* *********************************************
*文書内容取得処理
********************************************** */
function subGet() {

	var url = "http://localhost:8080/ibiDoc/SelectDocumentInfoServlet?ACTION=";
	var action = "search";
	url += action;

	var yearMonth = $("#targetYm").text().replace("/", "");
	var emplName = emplInfoArray[0];
	decDocName = decodeBase64Utf8(docName);


	var senddata = {
		send_recive_type: "1",
		year_month: yearMonth,
		empl_code: emplName,
		doc_name: decDocName,
		doc_type: docType
	};


	// ajax通信（同期）
	var jqXHR = postSeatchSync(senddata, url);

	// レスポンスJSONを取得
	var data = jqXHR.responseJSON;

	// 通信後、結果コード取得
	var retCode = showData(data);

	if (retCode === "1") {
		const docInfo = data[0];
		//文書タイトルの初期表示
		$("#docTitle").text(docInfo.doc_title || "");
		//有効期限の初期表示
		const from = formatDate(docInfo.expiration_from_dateTime);
		const to = formatDate(docInfo.expiration_to_dateTime);
		$("#dateFrom").val(from);
		$("#dateTo").val(to);
	} else {
		//エラー"対象文書が存在しません。"
		displayMessage(getMsg("msg0006_001"));
	}
}
/* *********************************************
*有効期限の初期表示（YYYYMMDD → YYYY-MM-DD に変換）
********************************************** */
const formatDate = (dateStr) => {
	if (!dateStr || dateStr.length !== 8) return "";
	return `${dateStr.slice(0, 4)}-${dateStr.slice(4, 6)}-${dateStr.slice(6, 8)}`;
};
/* *********************************************
*文書内容を取得
********************************************** */
function showData(data) {
	if (!data || data.length === 0) return "-1";
	return String(data[0].rtn_code || "-1");
}




/* *********************************************
*文書情報を更新
********************************************** */
function subUpdate() {
	//入力エラーチェックを行う
	if (!inputCheck()) {
		//入力チェックエラーの場合は処理を抜ける
		return;
	}
	//チェックエラーでない場合は登録処理を実施
	var url = "http://localhost:8080/ibiDoc/UpdateDocumentInfoServlet?ACTION=";
	var action = "update";
	 
	url += action;
	decDocName = decodeBase64Utf8(docName);

	var dTime = nowTimeVal();

	var senddata = {
		/*  送受信トラン  */
		// 送受信区分
		send_recive_type: "1",
		// 年月
		year_month: String($("#targetYm").text()).replace("/", ""),
		// 社員コード
		empl_code: emplInfoArray[0],
		// 文書名(更新前/文書)
		target_doc_name: decDocName,
		// 文書名(更新/文書)
		update_doc_name: String($("#file_select_text").val()),
		// 更新日時
		update_datetime: dTime,
		// 更新ユーザ
		update_userid: userInfoArray[0],
		// 更新プログラムID
		update_programid: "doc0007",
		// 文書種類
		doc_type: docType,
		// 有効期限(開始)
		expiration_from_dateTime: String($("#dateFrom").val()).replaceAll("-", ""),
		// 有効期限(終了)
		expiration_to_dateTime: String($("#dateTo").val()).replaceAll("-", ""),
	};

	// ajax通信（同期）
	var jqXHR = postSeatchSync(senddata, url);

	// レスポンスJSONを取得
	var data = jqXHR.responseJSON;

	// 通信後、結果コード取得
	var retCode = showData(data);

	if (retCode == "1") {
		//更新が完了しました。
		displayMessage(getMsg("msg0005_006"));
	} else if (retCode == "-1") {
		//既に登録済みのファイルが指定されています。
		displayMessage(getMsg("msg0005_010"));
	} else {
		//更新が失敗しました。
		displayMessage(getMsg("msg0005_007"));
	}
}


/* *********************************************
*入力チェック処理
********************************************** */
var inputCheck = function() {
	var ret = true;

	// 文書ファイル名の取得
	var docFileName = $('#file_select_text').val();

	// 文書ファイルに値が入っているか？
	if (isBlank(docFileName)) {
		displayMessage(getMsg("msg0005_003")); // 文書ファイルは必須です。
		return false;
	}

	// ファイル名の桁数チェック
	if (docFileName.length > 35) {
		displayMessage(getMsg("msg0005_013")); // 文書ファイル名は35文字以内にしてください。
		return false;
	}

	// 有効期限の開始・終了日取得
	var strDateFrom = $("#dateFrom").val();
	var strDateTo = $("#dateTo").val();

	// 有効期限未入力チェック
	if (isBlank(strDateFrom) || isBlank(strDateTo)) {
		displayMessage(getMsg("msg0005_012")); // 有効期限は必須です
		return false;
	}

	// 有効期限の大小チェック
	var fromDate = new Date(strDateFrom);
	var toDate = new Date(strDateTo);

	if (fromDate.getTime() > toDate.getTime()) {
		displayMessage(getMsg("msg0005_011")); // 有効期限の開始日が終了日を上回っています。
		return false;
	}
	return ret;
}

