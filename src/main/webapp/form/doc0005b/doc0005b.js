/**
 * doc0005b.js
 * 文書登録（管理者用）
 * @author D.Ikeda
 *    Date:2025/05/15
 * @version 1.0.0
 *
 */

//年月
var strYyyyMm = "";
var userInfoArray = [];

$(function(){
	//エラーメッセージダイアログを定義
	$("#message").dialog({
		autoOpen:false,
		modal:true,
		title:"エラーメッセージ",
		width:400,
		height:200,
		buttons:[
			{
				text:'OK',
				class:'d-button',
				click:function(){
					//ボタンを押したときの処理
					$(this).dialog("close");
				}
			}
		]
	});
});


//処理メッセージ表示
function displayMessage(str){
	$('#message').empty();
	$('#message').append("<p>" + str + "</p>");
	$('#message').dialog("open");
	return false;
}

//画面の準備
$(document).ready(function(){

	userInfoArray = init("");

	docInit();
 	
 	//ファイルアップロードボタンの選択イベント設定
	$("#fileSelectBtn").change(function(){
		var filePath = $(this).prop('files')[0].name;
		$("#file_select_text").val(filePath);
		$(this).val("");
	});
	
	//文書分類セレクトボックスの選択イベント設定
 	$("#docTypeSelectList").change(function(){
	    subChange();
 	});
 	
 	//有効期限開始日の選択イベント設定
 	$("#dateFrom").change(function(){
		var selectDateFrom = $(this).val();
		//有効期限終了日の最小値を行こう期限開始日の値に変更する
		document.getElementById("dateTo").min = selectDateFrom;
	 });
	 
	 //登録ボタンのイベント設定
	 $("#insertBtn").click(function() {
		 subInsert();
	 });
});

function subChange() {

	 //selectrdArray = [];
	 //選択文書分類の名称コード
     var strVal = $('option:selected').val();
     
     //年月と選択文字で文書タイトルを自動セット
     var strText = $('option:selected').text();
     var nengetu = $("#targetYm").text();
     var titleYm = String(nengetu).substring(0,4) + "年" + String(nengetu).substring(5,7) + "月";
     $("#doctitil").val(titleYm + strText);
     //selectrdArray = strVal.split(",");

}

//初期処理
function docInit() {

	var emplName = userInfoArray[1];
	$("#useName").text(emplName);
	
	return;

}

//画面の初期ロード
$(window).on('load',function(){
	
	//起動パラメータ取得
	var parm = $(location).attr('search');
	//var parm =location.seach;
	var parmArray = [];
	var parmArray = String(parm).split("?");
	
	//年月の初期処理
	monthInit(parmArray[2]);
	//社員名の初期処理
	emplInit(parmArray[3]);
	//有効期限
	expiration();
	//文書分類
	selectBox();
	
	/**
	 * 年月に初期値をセット
	 */
	function monthInit(ym){
		var nengetu = ym.substring(3, 7) + "/" + ym.substring(7, 9);
		$("#targetYm").text(nengetu);
	}
	/**
	 * 社員名に初期値をセット
	 */
	function emplInit(emplCode){
		var empl_code = String(emplCode);
		emplInfoArray = getEmplMstInfo(empl_code.substring(4,empl_code.length));
		
		$("#targetEmplName").text(emplInfoArray[1]);
	}
	/**
	 * 有効期限に初期値をセット
	 */
	function expiration(){
		/* 有効期限開始日  */
		//当日を取得
		var toDay = toDayVal();
		//1日加算する
		var addDay = addDayVal(toDay, 1);
		
		$("#dateFrom").val(addDay);
		//有効期限開始日の最小値を当日にする
		document.getElementById("dateFrom").min = toDay;
		
		/**
		 * 有効期限終了日
		 */
		// システムプロパティから保存期間（月数）を取得
		var numMonth = Number(getProperties("retention"));
		//保存期間（月数）を加算する
		var addMonthDay = addMonthVal(addDay, numMonth);
		$("#dateTo").val(addMonthDay);
		// 有効期限終了日の最小値を有効期限開始日の値にする
		document.getElementById("dateTo").min = addDay;
	}
	/**
	 * セレクトボックスの初期処理
	 */
	function selectBox() {
		//名称マスタの一覧を取得
		var nameValue = getNameMst("doc_type","1","");
		
		$.each(nameValue, function( key, value ){
			$("#docTypeSelectList").append($("<option>").val(String(value.key)).text(String(value.value)));
		});
	}

	//全セレクトボックスの初期処理
	selectBoxInitAll();

	//登録日付の初期処理
	dateInit();

	//初期検索処理
	initSearch();

});




//操作ボタン：文書登録のイベント
$(document).on('click', '.operation_btn01' , function() {
	var ym3 = strYyyyMm.replace("-","");
	var emplCode = $(this).closest('tr').find('input[name=emplCode]').val();
	var url = "http://localhost:8080/ibiDoc/form/doc0005/doc0005.html";
	url += "?ym=" + ym3 + "?ecd=" + emplCode;

	//新しいウィンドウで表示
	window.open(url);
});

//登録処理
function subInsert(){
	//入力エラーチェックを行う
	inputCheck();
}

//入力チェック処理
var inputCheck = function() {
	var ret = "";
	//文書分類に値が入っているか？
	if (isBlank($('#docTypeSelectList> option:selected').val())) {
		//エラー"文書分類は必須です。"
		displayMessage(getMsg("msg0005_001"));
		return;
	}
	//文書タイトルに値が入っているか？
	if (isBlank($('#doctitil').val())) {
		//エラー"文書タイトルは必須です。"
		displayMessage(getMsg("msg0005_002"));
		return;
	}
	//文書ファイルに値が入っているか？
	if (isBlank($('#file_select_text').val())) {
		//エラー"記号は必須です。"
		displayMessage(getMsg("msg0005_003"));
		return;
	}
	return ret;
}

