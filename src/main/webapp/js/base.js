/**
 * 一般的な共通関数
 * @author D.Ikeda
 *    Date:2021/05/03
 * @version 1.0.0
 */
var userInfoArray = [];

var emplMstInfoArray = [];

/**
 * 初期処理（モード判定）
 * @param {String} screenId 画面ID
 * @return {Array} userInfoArray ユーザー情報
 */
var init = function(screenId) {

	switch (screenId) {
		case "login":
			if (0 != getProperties("mode")) {
			//testモードでない場合は画面を閉じる
				//一度再表示してからClose
				 open('about:blank', '_self').close();
			} else{
			//testモードの場合
				//messegeリソース読み込み
				localStorage.clear();
				getMessageProperties();
			}
			break;
		case "menu":
			var mode = getProperties("mode");
			if (0 != mode) {
			//testモードでない場合は画面を閉じる
				//一度再表示してからClose
				 open('about:blank', '_self').close();
			} else {
				//セッションチェック
				userInfoArray = sessionCheck(mode);
				//権限に対応した名称マスタを取得
				getNameMstInfo(userInfoArray[2]);
			}
			break;
		case "doc0016":
			//モードを取得
			var mode = getProperties("mode");
			if (0 == mode) {
			//testモードの場合
				//セッションチェック
				userInfoArray = sessionCheck(mode);
				//権限に対応した名称マスタを取得
				getNameMstInfo(userInfoArray[2]);
			} else {
			//testモードでない場合
				//messegeリソース読み込み
				localStorage.clear();
				getMessageProperties();
			}
			break;

		case "doc0001":
			//モードを取得
			var mode = getProperties("mode");
			if (0 == mode) {
			//testモードの場合
				//セッションチェック
				userInfoArray = sessionCheck(mode);
			} else {
			//testモードでない場合
				//セッションチェック
				userInfoArray = sessionCheck(mode);
				//権限に対応した名称マスタを取得
				getNameMstInfo(userInfoArray[2]);
			}
			break;

		case "doc0003":
			//モードを取得
			var mode = getProperties("mode");
			if (0 == mode) {
			//testモードの場合
				//セッションチェック
				userInfoArray = sessionCheck(mode);
			} else {
			//testモードでない場合
				//セッションチェック
				userInfoArray = sessionCheck(mode);
				//権限に対応した名称マスタを取得
				getNameMstInfo(userInfoArray[2]);
			}
			break;

		default :
			//モードを取得
			var mode = getProperties("mode");
			//セッションチェック
			userInfoArray = sessionCheck(mode);
			break;
	}

	return userInfoArray;

}

/**
 * Properties値の取得
 * @param {String} key Propertiesのキー
 * @return {String}  Propertiesの値
 */
var getProperties = function (key) {

	var url = "http://localhost:8080/ibiDoc/GetPropertiesData?ACTION=";
    var action = "search";

    var inTx1 = key;

    url += action;

    var senddata = {inTx1 : inTx1};

    //ajax通信
    var jqXHR = postSeatchSync(senddata,url);

    var ret = getPropertiesVal(jqXHR);
    return ret;

}

var getPropertiesVal = function(jqXHR) {
	var ret = "";
	jqXHR.done(function(data, stat, xhr) {
		//結果を表示
		$.each( data, function( key, value ){
			ret = String(value.properties_val);
	    });
	});
	return ret;
}

/**
 * MessagePropertiesを一覧取得しlocalStorageに保存
 */
var getMessageProperties = function () {

	var url = "http://localhost:8080/ibiDoc/GetMessageResource?ACTION=";
    var action = "search";

    var inTx1 = "Get";

    url += action;

    var senddata = {inTx1 : inTx1};

    //ajax通信
    var jqXHR = postSeatchSync(senddata,url);

    getMessagePropertiesVal(jqXHR);

    return;

}

//MessageをlocalStorageに保存
var getMessagePropertiesVal = function(jqXHR) {

	const messageMap = new Map();

	jqXHR.done(function(data, stat, xhr) {
		//結果をlocalStorageにセット
		$.each( data, function( key, value ){
			localStorage.setItem(value.key, value.value);
	    });
	});

	return;
}

/**
 * Messageの値をlocalStorageから取得
 * @param {String} strKey messageのキー
 * @return {String} messageの値
 */
var getMsg = function (strKey) {

	var rtn = localStorage.getItem(strKey);

    return rtn;

}

/**
 * sessionチェック
 * @param {String} mode システムの起動モード 0:テストモード
 * @return {Array} ユーザー情報
 */
var sessionCheck = function (mode) {

	var url = "http://localhost:8080/ibiDoc/SessionCheckForward?ACTION=";
    var action = "search";

    url += action;

    var senddata = {pram1 : mode};

    //ajax通信
    var jqXHR = postSeatchSync(senddata,url);

    var forwardPath = getSessionCheckVal(jqXHR);

    if (forwardPath != "") {
		//画面切替
	    location.href = "http://localhost:8080/ibiDoc/form/" + forwardPath;
    } else {
    	var emplCode = userInfoArray[0];
    	var emplName = userInfoArray[1];
    	var mngGrant = userInfoArray[2];
    	var userId = userInfoArray[3];
    }

    return userInfoArray;

}

var getSessionCheckVal = function(jqXHR) {
	var ret = "";
	var ret2 = "";
	userInfoArray = [];
	jqXHR.done(function(data, stat, xhr) {
		//結果を表示
		$.each( data, function( key, value ){
			ret = String(value.sessionId);
			ret2 = String(value.strHtml);
			userInfoArray[0] = String(value.emplCode);
			userInfoArray[1] = String(value.emplName);
			userInfoArray[2] = String(value.mngGrant);
			userInfoArray[3] = String(value.userId);
	    });
	});
	return ret2;
}

/**
 * 名称マスタを管理者権限により切り分けて一覧取得しlocalStorageに保存
 * @param [String] mngGrant
 */
var getNameMstInfo = function (mngGrant) {

	var url = "http://localhost:8080/ibiDoc/GetNameMstData?ACTION=";
    var action = "search";

    var param = mngGrant;

    url += action;

    var senddata = {mngGrant : param};

    //ajax通信
    var jqXHR = postSeatchSync(senddata,url);

    getNameMstInfoVal(jqXHR);

    return;

}

//名称マスタをlocalStorageに保存
var getNameMstInfoVal = function(jqXHR) {

	jqXHR.done(function(data, stat, xhr) {
		//結果をlocalStorageにセット
		localStorage.setItem("nameMst", JSON.stringify(data));
	});

	return;
}

/**
 * 名称マスタの値をlocalStorageから取得
 * @param {String} nameType 名称分類
 * @param {String} sendReciveBranch 送受信表示切分区分
 * @param {String} nameCode 名称コード
 * @return {JSON or String} 名称コードが空の場合：一覧のJSON、空でない場合：名称
 */
var getNameMst = function (nameType, sendReciveBranch, nameCode) {

	var rtn;

	//localStorageに保持しているJSON形式の文字列をJSONに変換し取得
	var data = JSON.parse(localStorage.getItem("nameMst"));

	if (nameCode == "") {
	//セレクトボックス用に一覧取得
		var strKey = String(nameType) + "-" + String(sendReciveBranch) + "-";

		var flg = 0;

		$.each( data, function( key, value ){
			var name_type = value.name_type;
			var send_recive_branch = value.send_recive_branch;
			var name_code = value.name_code;
			var name_value = value.name_value;
			var strKey2 = String(name_type) + "-" + String(send_recive_branch) + "-";

			if (strKey == strKey2) {
			//キーと一致した場合は名称コードと名称をJSON文字列に組み立てる
				if (flg==0) {
					rtn = "[{";
					flg = 1;
				} else {
					rtn += ",{";
				}
				var key = name_code;
				var val = name_value;
				rtn += "\"key\":\"" + key + "\",\"value\":\"" + val + "\"}";
			}
		});

		if (flg==1) {
			rtn += "]";
		}

		//JSON形式の文字列をJSONに変換
		rtn = JSON.parse(rtn);

	} else {
	//名称を取得
		var strKey = String(nameType) + "-" + String(sendReciveBranch) + "-" + String(nameCode);

		$.each( data, function( key, value ){
			var name_type = value.name_type;
			var send_recive_branch = value.send_recive_branch;
			var name_code = value.name_code;
			var name_value = value.name_value;
			var strKey2 = String(name_type) + "-" + String(send_recive_branch) + "-" + String(name_code);

			if (strKey == strKey2) {
			//キーと一致した場合は名称を戻り値にセット
				rtn = name_value;
			}
		});

	}

    return rtn;

}

/**
 * 社員マスタ情報取得
 * @param {String} emplCode 社員コード
 * @return {Array} 社員マスタ情報
 */
var getEmplMstInfo = function (emplCode) {

	var url = "http://localhost:8080/ibiDoc/GetEmplMstInfo?ACTION=";
    var action = "search";

    url += action;

    var senddata = {pram1 : emplCode};

    //ajax通信
    var jqXHR = postSeatchSync(senddata,url);

    emplMstInfoArray = getEmplMstInfoVal(jqXHR);

    return emplMstInfoArray;

}

var getEmplMstInfoVal = function(jqXHR) {
	emplMstInfoArray = [];
	jqXHR.done(function(data, stat, xhr) {
		//結果を表示
		$.each( data, function( key, value ){
			emplMstInfoArray[0] = String(value.empl_code);
			emplMstInfoArray[1] = String(value.empl_name);
			emplMstInfoArray[2] = String(value.mng_grant);
			emplMstInfoArray[3] = String(value.birth_date);
			emplMstInfoArray[4] = String(value.mail_addr1);
			emplMstInfoArray[5] = String(value.mail_addr2);
			emplMstInfoArray[6] = String(value.mail_addr3);
			emplMstInfoArray[7] = String(value.send_mail_no);
			emplMstInfoArray[8] = String(value.user_id);
			emplMstInfoArray[9] = String(value.empl_date);
			emplMstInfoArray[10] = String(value.retire_date);
			emplMstInfoArray[11] = String(value.del_flg);
			emplMstInfoArray[12] = String(value.insert_dateTime);
			emplMstInfoArray[13] = String(value.insert_userId);
			emplMstInfoArray[14] = String(value.insert_programId);
			emplMstInfoArray[15] = String(value.update_dateTime);
			emplMstInfoArray[16] = String(value.update_userId);
			emplMstInfoArray[17] = String(value.update_programId);
	    });
	});
	return emplMstInfoArray;
}

/**
 * ajax通信 同期（JSON形式データ送信・取得）
 */
var postSeatchSync = function(senddata, url) {
	
	//ajax通信
	var jqXHR = $.ajax({
		type: "POST",
		url: url,
		dataType: 'json',
		cache: false,
		data: senddata,
		async: false
	})

		.done(function(xhr, textStatus, jqXHR) {
			console.log(jqXHR.responseText);
		})

		.fail(function(xhr, textStatus, jqXHR) {
			console.log(jqXHR.responseJSON);
			handleError();
		})

	return jqXHR;

}

/**
 * ajax通信 非同期（JSON形式データ送信・取得）
 */
var postSeatch = function(senddata, url) {

	//ajax通信
	var jqXHR = $.ajax({
		type: "POST",
		url: url,
		dataType: 'json',
		cache: false,
		data: senddata,
		async: true
	})

		.done(function(xhr, textStatus, jqXHR) {
			console.log(jqXHR.responseText);
		})

		.fail(function(xhr, textStatus, jqXHR) {
			console.log(jqXHR.responseJSON);
			handleError();
		})

	return jqXHR;

}

/**
 * ajax通信（ファイルアップロード）
 */

var postUpload = function(fd, url) {
	//ajax通信
	var jqXHR = $.ajax({
		type    : "POST",
	    url     : url,
	    cache: false,
	    data    : fd,
	    processData: false,
	    contentType: false
	    })
	    
		.done(function(xhr, textStatus, jqXHR) {
			console.log(jqXHR.responseText);
		})

		.fail(function(xhr, textStatus, jqXHR) {
			console.log(jqXHR.responseJSON);
			handleError();
		})

	return jqXHR;

}

/**
 * ajax通信（ファイルダウンロード）
 */

var getDownload = function(fileName, url) {
	//ajax通信
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.responseType = 'blob'; //blob型のレスポンスを受け付ける
        xhr.onload = function (e) {
            if (this.status == 200) {
                var blob = this.response;//レスポンス
                //IEとその他で処理の切り分け
                if (navigator.appVersion.toString().indexOf('.NET') > 0) {
                    //IE 10+
                    window.navigator.msSaveBlob(blob, fileName);
                } else {
                    //aタグの生成
                    var a = document.createElement("a");
                    //レスポンスからBlobオブジェクト＆URLの生成
                    var blobUrl = window.URL.createObjectURL(new Blob([blob], {
                        type: blob.type
                    }));
                    //上で生成したaタグをアペンド
                    document.body.appendChild(a);
                    a.style = "display: none";
                    //BlobオブジェクトURLをセット
                    a.href = blobUrl;
                    //ダウンロードさせるファイル名の生成
                    a.download = fileName;
                    //クリックイベント発火
                    a.click();
                }
            }
        };
        xhr.send();
}

/**
 * Base64・Utf8エンコードする
 */
var encodeBase64Utf8 = function(fileName, url) {

	//fileNameをBASE64エンコード変換
    var utf8str = String.fromCharCode.apply(null, new TextEncoder().encode(fileName));
    var encoded = btoa(utf8str);

    return encoded;
}

/**
 * Base64・Utf8デコードする
 */
var decodeBase64Utf8 = function(fileName, url) {

	var decoded = decodeURIComponent(escape(window.atob(fileName)));

    return decoded;
}
