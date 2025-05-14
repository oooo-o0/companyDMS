/**
 * upFile.js
 * ファイルアップロードプログラム
 * @author D.Ikeda
 *    Date:2021/04/24
 * @version 1.0.0
 */

$(document).ready(function(){

    $("#upButton").click(function(){
	    subUpload();
 	});

});

$(window).on('load',function(){

});

//アップロード処理
function subUpload() {

	var url = "http://localhost:8080/ibiDoc/UploadFileServlet?ACTION=";
    var action = "upload";


    var dateYM = "202103";
    var enc = "503607155001279999";
    var idno = "001";
    var dt = "123";

    url += action;
    url += "&YM=";
    url += dateYM;
    url += "&EN=";
    url += enc;
    url += "&ID=";
    url += idno;
    url += "&DT=";
    url += dt;


    var $upfile = $('input[name="upfile"]');
    var fd = new FormData();
    fd.append("upfile", $upfile.prop('files')[0]);

    //ajax通信
    var jqXHR = postUpload(fd,url);

}

