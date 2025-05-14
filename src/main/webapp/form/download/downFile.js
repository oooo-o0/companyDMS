/**
 * downFile.js
 * ファイルダウンロードプログラム
 * @author D.Ikeda
 *    Date:2021/04/10
 * @version 1.0.0
 */

$(document).ready(function(){

    $("#downButton").click(function(){
	    subDownload();
 	});

});

$(window).on('load',function(){

	$("#filename").val("202012池田大介.pdf");

});

//ダウンロード処理
function subDownload() {

	var url = "http://localhost:8080/ibiDoc/DownloadFileServlet?ACTION=";
    var action = "download";
    var dateYM = "202103";
    var enc = "503607155001279999";
    var idno = "001";
    var dt = "123";
   // var fileName = "202112池田 大介.pdf"
    var fileName = $("#filename").val();

    url += action;
    url += "&YM=";
    url += dateYM;
    url += "&EN=";
    url += enc;
    url += "&ID=";
    url += idno;
    url += "&DT=";
    url += dt;
    url += "&FN=";

    //fileNameをBASE64エンコード変換
    var encoded = encodeBase64Utf8(fileName);
    url += encoded;

    //ajax通信
    var jqXHR = getDownload(fileName, url);

}

