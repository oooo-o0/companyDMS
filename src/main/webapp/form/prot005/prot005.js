/**
 * prot005.js
 * グラフデータ取得によりセレクトボックスと動的HTML生成プログラム
 */

var selectrdVal = "";

$(document).ready(function(){

    $("#btn1").click(function(){
	    subSearch();
 	});

    $("#countrySelectList").change(function(){
	    subChange();
 	});

});

$(window).on('load',function(){
	  selectBoxInit();
});

//セレクトボックスの初期設定
function selectBoxInit() {
	var url = "http://localhost:8080/ibiDoc/GetSelectBoxDataServlet?ACTION=";
    var action = "search";
    var inTx1 ="";

    url += action;

    var senddata = {
    	inTx1 : inTx1
    };

    //ajax通信
    var jqXHR = postSeatch(senddata,url);

    setSelectBox(jqXHR);

}

//セレクトボックスへ選択値をセット
function setSelectBox(jqXHR) {
	//var obj = JSON.parse(data);
    $("#countrySelectList").append($("<option>").val("").text("ALL"));

	jqXHR.done(function(data, stat, xhr) {
		//結果を表示
		$.each( data, function( key, value ){
		    $("#countrySelectList").append($("<option>").val(String(value.country)).text(String(value.country)));
	    });
	});
}

//セレクトボックスの選択値を取得
function getSelectBoxSelected() {
	var obj = JSON.parse(data);

	$.each( obj, function( key, value ){
       $("#countrySelectList").append($("<option>").val(String(key)).text(String(value.country)));

    });
}

function subChange() {

     selectrdVal = $('option:selected').val();

}

//表示内容のクリア
function detailClear() {
	$('table#detail tbody *').remove();

}

//検索処理
function subSearch() {

    detailClear();

	var url = "http://localhost:8080/ibiDoc/GetGrafDataSearchServlet?ACTION=";
    var action = "search";

    //セレクトボックスの選択値をパラメータにセット
    var inTx1 = selectrdVal;
   // var inTx2 = 99;

    url += action;

//    var senddata = [
//                    {inTx1 : inTx1},
//                    {inTx2 : inTx2}
//                   ];

    var senddata = {inTx1 : inTx1};

    //ajax通信
    var jqXHR = postSeatch(senddata,url);

    showData(jqXHR);

}

function showData(jqXHR) {

	jqXHR.done(function(data, stat, xhr) {
		//結果を表示
		$.each( data, function( key, value ){
		    $("#detail>tbody").append("<tr><td>"+ String(value.country) +"</td><td>"+String(value.visits)+"</td></tr>");
	    });
	});

}

