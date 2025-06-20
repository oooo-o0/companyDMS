/**
 * doc0007.js
 * 文書更新（管理者用）
 * @author A.T
 *    Date:2025/06/19
 * @version 1.0.0
 *
 */


//グローバル変数
var strYyyyMm = "";      // 年月の保存用
var userInfoArray = [];  // ログインユーザー情報
var emplInfo = [];  // 対象社員情報
var docType = "";  // 閲覧文書分類コード
var docName = "";
var decDocName = "";
var empl_code = "";
var target_from = "";
var target_to = "";
var upddate_from = "";
var update_to = "";
var target_doc_name = "";
var update_doc_name = "";
var filesData;
var filePath;



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

	// 各表示項目セット
	setTargetYm(strYyyyMm);
	setDocTypeLabel(docType);
	setTargetEmplName(emplCode);
	setFileName(docName);

	// 文書情報取得
	subGet();
});

/* *********************************************
*初期処理
********************************************** */
function docInit() {
	userInfoArray = init("");
	var emplName = userInfoArray[1];
	$("#useName").text(emplName);
	//更新ボタンのイベント設定
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
	//ファイルアップロードボタンの選択イベント設定
	$("#fileSelectBtn").change(function() {
		filePath = $(this).prop('files')[0].name;
		filesData = $(this).prop('files')[0];
		$("#file_select_text").val(filePath);
	});


	return;
}


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
	emplInfo = getEmplMstInfo(empl_code);

	$("#targetEmplName").text(emplInfo[1]);
}

/* *********************************************
 * ファイル名初期表示
 ********************************************** */
function setFileName(docName) {
	target_doc_name = decodeBase64Utf8(docName);
	$("#file_select_text").val(target_doc_name);
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

	var senddata = {
		send_recive_type: "1",
		year_month: yearMonth,
		empl_code: emplInfo[0],
		doc_name: target_doc_name,
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
		var docTitle = $("#docTitle").text(docInfo.doc_title || "");
		//有効期限の初期表示
		var from = formatDate(docInfo.expiration_from_dateTime);
		var to = formatDate(docInfo.expiration_to_dateTime);
		$("#dateFrom").val(from);
		$("#dateTo").val(to);
		target_from = unformatDate(from);
		target_to = unformatDate(to);
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
*有効期限の変換（YYYY-MM-DD → YYYYMMDD に変換）
********************************************** */
const unformatDate = (dateStr) => {
	if (!dateStr || dateStr.length !== 10) return "";
	return dateStr.replace(/-/g, "");
};



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

	var dTime = nowTimeVal();


	var senddata = {
		/*  送受信トラン  */
		// 送受信区分
		send_recive_type: "1",
		// 年月
		year_month: String($("#targetYm").text()).replace("/", ""),
		// 社員コード
		empl_code: emplInfo[0],
		// 文書名(更新前/文書)
		target_doc_name: target_doc_name,
		// 文書名(更新/文書)
		update_doc_name: update_doc_name,
		// 更新日時
		update_datetime: dTime,
		// 更新ユーザ
		update_userid: userInfoArray[0],
		// 更新プログラムID
		update_programid: "doc0007",
		// 文書種類
		doc_type: docType,
		// 有効期限(開始)
		expiration_from_dateTime: upddate_from,
		// 有効期限(終了)
		expiration_to_dateTime: update_to,
	};

	// ajax通信（同期）
	var jqXHR = postSeatchSync(senddata, url);

	// レスポンスJSONを取得
	var data = jqXHR.responseJSON;

	// 通信後、結果コード取得
	var retCode = showData(data);

	if (retCode == "1") {

		// ファイルアップロードが必要か条件チェック
		//文書名が変更されている場合
		if (update_doc_name.trim() !== target_doc_name.trim()) {
			// ファイルアップロード実施
			subUpload();
		}
		//更新が完了しました。
		displayMessage(getMsg("msg0005_006"));
	} else if (retCode == "-2") {
		//既に登録済みのファイルが指定されています。
		displayMessage(getMsg("msg0005_010"));
	} else if (retCode == "0") {
		//対象文書が存在しません
		displayMessage(getMsg("msg0006_001"));
	} else {
		//更新が失敗しました。
		displayMessage(getMsg("msg0005_007"));
	}
}
/* *********************************************
*更新結果を取得
********************************************** */
function showData(data) {
	return String(data[0].rtn_code);
}

/* *********************************************
*入力チェック処理
********************************************** */
var inputCheck = function() {
	var ret = true;

	update_doc_name = String($("#file_select_text").val());
	upddate_from = String($("#dateFrom").val()).replaceAll("-", "");
	update_to = String($("#dateTo").val()).replaceAll("-", "");

	// 文書ファイルに値が入っているか？
	if (isBlank(update_doc_name)) {
		displayMessage(getMsg("msg0005_003")); // 文書ファイルは必須です。
		return false;
	}

	// ファイル名の桁数チェック
	if (update_doc_name.length > 35) {
		displayMessage(getMsg("msg0005_013")); // 文書ファイル名は35文字以内にしてください。
		return false;
	}


	// 有効期限未入力チェック
	if (isBlank(upddate_from) || isBlank(update_to)) {
		displayMessage(getMsg("msg0005_012")); // 有効期限は必須です
		return false;
	}

	// 有効期限の大小チェック
	var fromDate = new Date(upddate_from);
	var toDate = new Date(update_to);

	if (fromDate.getTime() > toDate.getTime()) {
		displayMessage(getMsg("msg0005_011")); // 有効期限の開始日が終了日を上回っています。
		return false;
	}
	// 前回登録された内容とすべて同じ場合のチェック
	if (
		update_doc_name.trim() === target_doc_name.trim() && // 文書名が同じ
		upddate_from === target_from &&                     // 有効期限Fromが同じ
		update_to === target_to                             // 有効期限Toが同じ
	) {
		displayMessage(getMsg("msg0007_001"));
		// 前回登録した内容です。更新する場合はファイル名または有効期限を変更してください。
		return false;
	}
	return ret;
}


/* *********************************************
*アップロード処理
********************************************** */
function subUpload() {

	var url = "http://localhost:8080/ibiDoc/UploadFileServlet?ACTION=";
	var action = "upload";


	url += action;
	url += "&YM=";
	url += $('#targetYm').text();
	url += "&EN=";
	url += $('#docTitle').text();
	url += "&ID=";
	url += emplInfo[1];
	url += "&DT=";
	url += $('#docType').text();

	var fd = new FormData();
	fd.append("upfile", filesData);

	//ajax通信
	var jqXHR = postUpload(fd, url);

}

