/**
 * doc0016.js
 * @author D.Ikeda
 *    Date:2021/05/03
 * @version 1.0.0
 */

var selectrdArray = [];
var grantDateVal = "";
var warekiFlg = 0;

$(function() {
  // 入力ダイアログを定義
  $("#input").dialog({
    autoOpen: false,
    modal: true,
    width: 500,
    height: 350,
    buttons: [
    	{
            text: '送 信',
            class:'c-button',
            click: function() {
                //ボタンを押したときの処理
            	 subLogin();
            }
        }
    ]
  });

  // エラーメッセージダイアログを定義
  $("#message").dialog({
    autoOpen: false,
    modal: true,
    title: "エラーメッセージ",
    width: 400,
    height: 200,
    buttons: [
    	{
            text: 'OK',
            class:'c-button',
            click: function() {
                //ボタンを押したときの処理
            	$(this).dialog("close");
            }
        }
    ]
  });

});

// 処理メッセージ表示
function displayMessage(str) {
 $('#message').empty();
 $('#message').append("<p>" + str + "</p>");
 $("#message").dialog("open");
 return false;
}

$(document).ready(function(){

	//モードを取得
	var mode = getProperties("mode");
	if (0 == mode) {
	//testモードの場合
		//セッションチェック
		init("doc0016");
	}

	initDialog();

	$('input[name="check1"]').change(function() {
		subChecked();
	});

});

$(window).on('load',function(){

	//交付日の初期処理
	initDate();

});

function initDialog() {
	$("#input").dialog("open");
    return false;
}

//チェックボックスの判定
function subChecked() {

	var prop = $('#chk1').prop('checked');

	if (prop) {
	//チェックON
		warekiFlg = 1;
		warekiDate();
	} else {
	//チェックOFF
		warekiFlg = 0;
		seirekiDate();
	}
}

//交付日に当日の初期値をセット
function initDate() {

	$("#grant_date").append("<input type=\"date\" id=\"grantDate\" size=\"10\" maxlength=\"8\" />");

	var ymd = toDayVal();

    //交付日
    document.getElementById("grantDate").value = ymd;
}

//交付日に西暦値をセット
function seirekiDate() {

	//和暦の指定日付を西暦のDateに変換
	var dt = warekiToSeireki($('#grantDateWareki').val());

	$("#grant_date").empty();

	$("#grant_date").append("<input type=\"date\" id=\"grantDate\" size=\"10\" maxlength=\"8\" />");

	var ymd = inputDayVal(dt);

    //交付日
    document.getElementById("grantDate").value = ymd;

}

//交付日を和暦表示
function warekiDate() {

	//西暦の指定日付を取得
	grantDateVal = new Date($('#grantDate').val() );

	var year = grantDateVal.getFullYear();
	var month = grantDateVal.getMonth();
	var day = grantDateVal.getDate();

	$("#grant_date").empty();

    $("#grant_date").append("<input type=\"text\" id=\"grantDateWareki\" size=\"15\" maxlength=\"16\" />");

	var dt = new Date(year, month, day);
	var opt = {year:'numeric',month:'long',day:'numeric'};
	var wareki = dt.toLocaleDateString("ja-JP-u-ca-japanese", opt);

    //交付日
    document.getElementById("grantDateWareki").value = wareki;

}

//ログイン処理
function subLogin() {

	//入力エラーチェックを行う
	if (!inputCheck()) {
		return;
	}

	//if (selectrdArray.length==0) {
	//ユーザー未選択の場合は処理を抜ける
	//	return;
	//}

	//新しいウィンドウで表示
	//window.open("http://localhost:8080/ibiDoc/form/menu/menu.html");
	//画面切替
	//location.href = "http://localhost:8080/ibiDoc/form/menu/menu.html";
	//return;

	var url = "http://localhost:8080/ibiDoc/AuthForward?ACTION=";
    var action = "search";

    url += action;

    var senddata = {
    		pram1 : String($('#kigo').val()), pram2 : String($('#no').val()),
    		pram3 : String($('#health_no').val()), pram4 : String($('#grantDate').val())
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

//入力チェック処理
var inputCheck = function() {
	var ret = true;
	//記号に値が入っているか？
	if (isBlank($('#kigo').val())) {
		//エラー"記号は必須です。"
		displayMessage(getMsg("msg0016_001"));
		return false;
	}

	//記号が数字か？
	if (isNumeric($('#kigo').val())) {
		//エラー"記号は数値で入力してください。"
		displayMessage(getMsg("msg0016_002"));
		return false;
	}

	//番号に値が入っているか？
	if (isBlank($('#no').val())) {
		//エラー"番号は必須です。"
		displayMessage(getMsg("msg0016_003"));
		return false;
	}

	//番号が数字か？
	if (isNumeric($('#no').val())) {
		//エラー"番号は数値で入力してください。"
		displayMessage(getMsg("msg0016_004"));
		return false;
	}

	//保険者番号に値が入っているか？
	if (isBlank($('#health_no').val())) {
		//エラー"保険者番号は必須です。"
		displayMessage(getMsg("msg0016_005"));
		return false;
	}

	//保険者番号が数字か？
	if (isNumeric($('#health_no').val())) {
		//エラー"保険者番号は数値で入力してください。"
		displayMessage(getMsg("msg0016_006"));
		return false;
	}


	if (warekiFlg==1) {
	//和暦のチェック
		//交付日に値が入っているか？
		if (isBlank($('#grantDateWareki').val())) {
			//エラー"交付日は必須です。"
			displayMessage(getMsg("msg0016_007"));
			return false;
		}

		//交付日が日付か？
		//和暦を西暦変換しエラーがあればエラー
		if (warekiDateCheck($('#grantDateWareki').val())) {
			//エラー"交付日の和暦は正しいい値で入力してください。"
			displayMessage(getMsg("msg0016_008"));
			return false;

		}
	}

	return ret;
}

