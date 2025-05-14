/**
 * login.js
 * @author D.Ikeda
 *    Date:2021/04/30
 * @version 1.0.0
 */

var selectrdArray = [];

$(document).ready(function(){

	init("login");

    $("#login").click(function(){
	    subLogin();
 	});

    $("#userIdSelectList").change(function(){
	    subChange();
 	});

});

$(window).on('load',function(){
	  selectBoxInit();
});

//セレクトボックスの初期設定
function selectBoxInit() {
	var url = "http://localhost:8080/ibiDoc/GetUserIdSelectBoxData?ACTION=";
    var action = "search";
    var inTx1 ="";

    url += action;

    var senddata = {
    	inTx1 : inTx1
    };

    //ajax同期通信
    var jqXHR = postSeatchSync(senddata,url);

    setSelectBox(jqXHR);

}

//セレクトボックスへ選択値をセット
function setSelectBox(jqXHR) {

	jqXHR.done(function(data, stat, xhr) {
		//結果を表示
		$.each( data, function( key, value ){
		    $("#userIdSelectList").append($("<option>").val(
		    		String(value.empl_code)+","+
		    		String(value.empl_name)+","+
		    		String(value.mng_grant)+","+
		    		String(value.user_id)
		    		).text(String(value.empl_name)));
	    });

	});
}

//セレクトボックスの選択値を取得
function getSelectBoxSelected() {
	var obj = JSON.parse(data);

	$.each( obj, function( key, value ){
       $("#userIdSelectList").append($("<option>").val(String(key)).text(String(value.empl_name)));

    });
}

function subChange() {

	 selectrdArray = [];
     var strVal = $('option:selected').val();
     selectrdArray = strVal.split(",");

}

//ログイン処理
function subLogin() {

	if (selectrdArray.length==0) {
	//ユーザー未選択の場合は処理を抜ける
		return;
	}

	//新しいウィンドウで表示
	//window.open("http://localhost:8080/ibiDoc/form/menu/menu.html");
	//画面切替
	//location.href = "http://localhost:8080/ibiDoc/form/menu/menu.html";
	//return;

	var url = "http://localhost:8080/ibiDoc/LoginForward?ACTION=";
    var action = "search";

    //セレクトボックスの選択値をパラメータにセット
   // var inTx1 = selectrdArray;

    url += action;

    var senddata = {
    		pram1 : selectrdArray[0], pram2 : selectrdArray[1],
    		pram3 : selectrdArray[2], pram4 : selectrdArray[3]
    };

    //ajax通信
    var jqXHR = postSeatchSync(senddata,url);

    var forwardPath = showData(jqXHR);

	//画面切替
    location.href = "http://localhost:8080/ibiDoc/form/" + forwardPath;

}

var showData = function(jqXHR) {
	var ret = "";
	jqXHR.done(function(data, stat, xhr) {
		//結果を表示
		$.each( data, function( key, value ){
			ret = String(value.strFolder);
			ret += "/";
			ret += String(value.strHtml);
	    });
	});
	return ret;
}

