/**
 * doc0005b.js
 * 文書登録（管理者用）
 * @author A.T
 *    Date:2025/06/01
 * @version 1.0.0
 *
 */

//年月
var strYyyyMm = "";

var userInfoArray = [];

var emplInfoArray = [];

var selectDocType = "";

var filesData;

var selectDocText;

var parmArray = [];

var docTitl = "";

var titleYm = "";

var nengetu = "";

$(function() {
	/* *********************************************
	 *エラーメッセージダイアログを定義
	 ********************************************** */
	$("#message").dialog({
	    autoOpen: false,
	    modal: true,
	    title: "エラーメッセージ",
	    width: 400,
	    height: 200,
	    buttons: [
	    	{
	            text: 'OK',
	            class:'d-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	$(this).dialog("close");
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
*画面の準備
********************************************** */
$(document).ready(function(){

	userInfoArray = init("");

	docInit();

	//ファイルアップロードボタンの選択イベント設定
	$("#fileSelectBtn").change(function(){
		var filePath = $(this).prop('files')[0].name;
		filesData = $(this).prop('files')[0];
		$("#file_select_text").val(filePath);
	});

	// 文書分類セレクトボックス変更イベント
	$("#docTypeSelectList").change(function(){
		subChange();
	});

	// 有効期限開始日選択時の処理
	$("#dateFrom").change(function(){
		var selectDateFrom = $(this).val();
		document.getElementById("dateTo").min = selectDateFrom;
	});

	// 登録ボタンイベント（form送信防止）
	$("#insertBtn").click(function(e){
		e.preventDefault();  // フォームの送信を防ぐ
	    subInsert();
 	});
});


/* *********************************************
*文書分類セレクトボックスの選択イベント処理
********************************************** */
function subChange() {

	 //選択文書分類の名称コード
	 selectDocType = $('option:selected').val();

	//年月と選択文字で文書タイトルを自動セット
     selectDocText = $('option:selected').text();
     nengetu = $("#targetYm").text();
     titleYm = String(nengetu).substring(0,4) + "年"+ String(nengetu).substring(5,7) + "月";
     docTitl = titleYm + selectDocText;
     $("#docTitle").val(docTitl);

}

/* *********************************************
*初期処理
********************************************** */
function docInit() {

	var emplName = userInfoArray[1];
	$("#useName").text(emplName);

	return;
}

/* *********************************************
* 画面の初期ロード
********************************************** */
$(window).on('load',function(){

	//起動パラメータ取得
	var parm = $(location).attr('search');
	
	parmArray = String(parm).split("?");

	//年月の初期処理
	monthInit(parmArray[1]);
	//社員名の初期処理
	emplInit(parmArray[2]);
	//有効期限
	expiration();
	//文書分類
	selectBox()
});

/* *********************************************
* 年月に初期値をセット
********************************************** */
function monthInit(ym) {

	var nengetu = ym.substring(3, 7) + "/" + ym.substring(7, 9);

	$("#targetYm").text(nengetu);

}

/* *********************************************
* 社員名に初期値をセット
********************************************** */
function emplInit(emplCode) {
	var empl_code = String(emplCode);

	emplInfoArray = getEmplMstInfo(empl_code.substring(4, empl_code.length));

	$("#targetEmplName").text(emplInfoArray[1]);

}

/* *********************************************
* 有効期限に初期値をセット
********************************************** */
function expiration() {
	/* 有効期限開始日 */
	//当日を取得
	var toDay = toDayVal();
	//1日加算する
	var addDay = addDayVal(toDay, 1);

	$("#dateFrom").val(addDay);
	//有効期限開始日の最小値を当日にする
	document.getElementById("dateFrom").min = toDay;

	/* 有効期限終了日 */
	//システムプロパティから保存期間（月数）を取得
	var numMonth = Number(getProperties("retention"));
	//保存期間（月数）を加算する
	var addMonthDay = addMonthVal(addDay, numMonth);

	$("#dateTo").val(addMonthDay);
	//有効期限終了日の最小値を有効期限開始日の値にする
	document.getElementById("dateTo").min = addDay;

}

/* *********************************************
*セレクトボックスの初期設定
********************************************** */
function selectBox() {
	//名称マスタの一覧を取得
	var nameValue = getNameMst("doc_type","1","");

	$.each( nameValue, function( key, value ){
	    $("#docTypeSelectList").append($("<option>").val(String(value.key)).text(String(value.value)));
    });

}

/* *********************************************
*登録処理
********************************************** */
function subInsert() {

	//入力エラーチェックを行う
	if (!inputCheck()) {
	//入力チェックエラーの場合は処理を抜ける
		return;
	}

	//チェックエラーでない場合は登録処理を実施
	var url = "http://localhost:8080/ibiDoc/InsertSendReciveTranServlet?ACTION=";
	   var action = "insert";

	url += action;

	var dTime = nowTimeVal();

	var senddata = {
		/*  送受信トラン  */
		// 送受信区分
		send_recive_type : "1",
		// 年月
		year_month : String($("#targetYm").text()).replace("/",""),
		// 社員コード
		empl_code : emplInfoArray[0],
		// 文書名(最終登録文書/文書)
		doc_name : String($("#file_select_text").val()),
		// 操作区分
	    operation_type : "01",
	    // 最終登録日時/最終受信日時
	    last_send_recive_datetime : dTime,
	    // 最終登録状況/登録状況
	    insert_status : "01",
	    // 閲覧状況/確認状況
	    status : "00",
	    // 明細数
	    detail_num : "",
	    //作成日時
	    insert_dateTime: dTime,
	    // 作成ユーザ
	    insert_userId : userInfoArray[0],
	    // 作成プログラムID
	    insert_programId : "doc0005",

	    /*  送受信明細トラン  */
	    // 文書種類
	    doc_type : selectDocType,
	    // 操作区分
	    operation_type_detil : "02",
	    // 受信日時/最終連絡送信日時
	    send_recive_datetime : "",
	    // 閲覧状況/確認状況
	    check_status : "01",
	    // 閲覧日時/確認日時
	    check_datetime : "",
	    // 指摘内容
	    finger_body : "",

	    /*  文書内容  */
	    // タイトル
	    doc_title : String($("#docTitle").val()),
	    // 内容
	    doc_body : "",
	    // 有効期限(開始)
	    expiration_from_dateTime : String($("#dateFrom").val()).replaceAll("-",""),
	    // 有効期限(終了)
	    expiration_to_dateTime : String($("#dateTo").val()).replaceAll("-",""),
	    // メール送信日時/受信日時
	    mail_send_recive_dateTime : ""

    };

	//ajax通信
	var jqXHR = postSeatchSync(senddata,url);

	var retCode = showData(jqXHR);

	if (retCode=="1") {
	//登録完了

	    	//ファイルアップロード
	    	subUpload();

			displayMessage(getMsg("msg0005_004"));
	} else {
	    if (retCode=="-2") {
	    //存在チェックエラー
			displayMessage(getMsg("msg0005_010"));
	    } else {
    	//登録失敗
	    	displayMessage(getMsg("msg0005_005"));
	    }
	}

}

/* *********************************************
*登録結果を取得
********************************************** */
function showData(jqXHR) {
		var ret = "";
		jqXHR.done(function(data, stat, xhr) {
			//結果を表示
			$.each( data, function( key, value ){
				ret = String(value.rtn_code);
		    });
		});

	return ret;
}

/* *********************************************
*入力チェック処理
********************************************** */
var inputCheck = function() {
	var ret = true;

	//文書分類に値が入っているか？
	if (isBlank($('#docTypeSelectList> option:selected').val())) {
		//エラー"文書分類は必須です。"
		displayMessage(getMsg("msg0005_001"));
		return false;
	}

	//文書タイトルに値が入っているか？
	if (isBlank($('#docTitle').val())) {
		//エラー"文書タイトルは必須です。"
		displayMessage(getMsg("msg0005_002"));
		return false;
	}

	//文書ファイルに値が入っているか？
	if (isBlank($('#file_select_text').val())) {
		//エラー"文書ファイルは必須です。"
		displayMessage(getMsg("msg0005_003"));
		return false;
	}

	//有効期限の大小チェック
	var strDate = String($("#dateFrom").val());
	var strDate2 = String($("#dateTo").val());
	var date = new Date(String(strDate).substring(0,4), String(strDate).substring(5,7), String(strDate).substring(8,10));
	var date2 = new Date(String(strDate2).substring(0,4), String(strDate2).substring(5,7), String(strDate2).substring(8,10));

	if (date.getTime() > date2.getTime()) {
		//エラー"有効期限の開始日が終了日を上回っています。"
		displayMessage(getMsg("msg0005_011"));
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
    url += titleYm;
    url += "&EN=";
    url += docTitl;
    url += "&ID=";
    url += emplInfoArray[0];
    url += "&DT=";
    url += selectDocText;
    
    var fd = new FormData();
    fd.append("upfile", filesData);

    //ajax通信
    var jqXHR = postUpload(fd,url);

}