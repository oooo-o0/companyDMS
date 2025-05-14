/**
 * inputCheckUtil.js
 * 入力チェック共通関数
 * @author D.Ikeda
 *    Date:2021/05/03
 * @version 1.0.0
 */

/**
 * 入力値が空かの判定
 * @param {String} str 入力値
 * @return {Boolean} 判定結果 true:空白
 */
var isBlank = function(str) {
	var ret = false;
	if (str == "") {
		ret = true;
	}
	return ret;
}

/**
 * 入力値が数字かの判定
 * @param {String} str 入力値
 * @return {Boolean} 判定結果 true:数値ではない
 */
var isNumeric = function(str) {
	return isNaN(str);
}

/**
 * 入力値がメールアドレスかの判定
 *   3文字以下はエラー
 *   "@"が1桁目に出現した場合はエラー
 *   "@"が1つもない場合はエラー
 * @param {String} str 入力値
 * @return {Boolean} 判定結果 true:メールアドレスである
 */
var isMailaddr = function(str) {

	var ret = true;
	var count = 0;

	if (str.length < 3) {
	//3桁以下はエラー
		ret = false;
	} else {

		for (var i=0; i<str.length;i++) {
			if (str.substr(i,1)=="@") {
				if (i==0) {
					//@が1文字目の場合はエラー
					ret = false;
				}
				count++;
			}
		}

		if (ret) {
		//エラーになっていない場合にチェックする
			if (count != 1) {
				//@が1個以外の場合はエラー
				ret = false;
			}
		}
	}

	return ret;
}
