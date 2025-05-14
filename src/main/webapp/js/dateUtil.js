/**
 * 日付の共通関数
 * @author D.Ikeda
 *    Date:2021/05/03
 * @version 1.0.0
 */


/**
 * 当月の値を取得
 */
var toMonthVal = function() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;

    var yyyy = toTwoDigits(year, 4);
    var mm = toTwoDigits(month, 2);
    var ym = yyyy + "-" + mm;

    return ym;
}

/**
 * 当日の値を取得
 */
var toDayVal = function() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();

    var yyyy = toTwoDigits(year, 4);
    var mm = toTwoDigits(month, 2);
    var dd = toTwoDigits(day, 2);
    var ymd = yyyy + "-" + mm + "-" + dd;

	return ymd;
}

/**
 * 現在日時の値をYYYYMMDDHHMMSSで取得
 */
var nowTimeVal = function() {

	var date = new Date();
    var time = toStringDatetime(date);

	return time;
}

//前ゼロを付け指定した桁で戻す
function toTwoDigits(num, digit) {
    num += '';
    if (num.length < digit) {
      num = '0' + num;
    }
    return num;
}

/**
 * 指定した日に指定日を加算する
 * @param day yyyy-mm-dd
 * @param num 加算日
 * @retrun yyyy-mm-dd
 *
 */
var addDayVal = function(day, num) {

	var mon = String(Number(String(day).substring(5,7)) - 1);

	var date = new Date(String(day).substring(0,4), mon, String(day).substring(8,10));
	//1か月前
	date.setDate(date.getDate() + num);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();

    var yyyy = toTwoDigits(year, 4);
    var mm = toTwoDigits(month, 2);
    var dd = toTwoDigits(day, 2);
    var ymd = yyyy + "-" + mm + "-" + dd;

	return ymd;
}

/**
 * 指定した日に指定月を加算する
 * @param day yyyy-mm-dd
 * @param num 加算月
 * @retrun yyyy-mm-dd
 *
 */
var addMonthVal = function(day, num) {

	var date = new Date(String(day).substring(0,4), String(day).substring(5,7), String(day).substring(8,10));
	//1か月前
	date.setMonth(date.getMonth() + num);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();

    var yyyy = toTwoDigits(year, 4);
    var mm = toTwoDigits(month, 2);
    var dd = toTwoDigits(day, 2);
    var ymd = yyyy + "-" + mm + "-" + dd;

	return ymd;
}

/**
 * 指定した日の値を取得
 */
var inputDayVal = function(date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();

    var yyyy = toTwoDigits(year, 4);
    var mm = toTwoDigits(month, 2);
    var dd = toTwoDigits(day, 2);
    var ymd = yyyy + "-" + mm + "-" + dd;

	return ymd;
}

/**
 * 指定した年月の1か月前の値を取得
 */
var lastMonthVal = function(strYyyyMm) {
	var date = new Date(String(strYyyyMm).substring(0,4), String(strYyyyMm).substring(4,6), "01");
	//1か月前
	date.setMonth(date.getMonth() - 1);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var yyyy = toTwoDigits(year, 4);
    var mm = toTwoDigits(month, 2);

	return yyyy + mm;
}

/**
 * 和暦年を西暦年に変換し取得
 * @param {String} name 元号
 * @param {String} year 和暦の年
 * @return {String} 西暦の年
 */
var JCToAD = function(name, year) {
    var data, i, len;

    if (!name || !year) return false;

    if (typeof year === 'string') {
        year = Number(year.replace('年', ''));
    }

    if (typeof year !== 'number' || year === NaN) return false;
    if (year <= 0) return false;

    data = [
        {
            name      : '令和',
            year      : false,
            startYear : 2018
        },
        {
            name      : '平成',
            year      : 31,
            startYear : 1988
        },
        {
            name      : '昭和',
            year      : 64,
            startYear : 1925
        },
        {
            name      : '大正',
            year      : 15,
            startYear : 1911
        },
        {
            name      : '明治',
            year      : 45,
            startYear : 1867
        },
        {
            name      : '慶応',
            year      : 4,
            startYear : 1864
        }
    ];

    for (i = 0, len = data.length; i < len; i++) {
        if ((name === data[i].name) && (data[i].year === false || year <= data[i].year)) {
            return data[i].startYear + year;
        }
    }

    return false;
}

/**
 * 和暦を西暦のDateへ変換し取得
 * @param {String} wareki 和暦の文字
 * @return {Date} 西暦
 */
var warekiToSeireki = function(wareki) {

	var gengo = String(wareki).substring(0,2);
	var nenIdx = getNenIdx(wareki);
	var tukIdx = getTukiIdx(wareki);
	var hiIdx = getHiIdx(wareki);

	var nen = String(wareki).substring(2, nenIdx);
	var tuki = String(wareki).substring(nenIdx +1, tukIdx);
	var hi = String(wareki).substring(tukIdx +1, hiIdx);

	var seirkiNen = JCToAD(gengo,nen);

	var dt = new Date(seirkiNen, tuki -1, hi);
	return dt;
}

/**
 * 和暦を西暦の8桁文字に変換し取得
 * @param {String} wareki 和暦の文字
 * @return {String} 西暦
 */
var warekiToSeirekiStr = function(wareki) {

	var gengo = String(wareki).substring(0,2);
	var nenIdx = getNenIdx(wareki);
	var tukIdx = getTukiIdx(wareki);
	var hiIdx = getHiIdx(wareki);

	var nen = String(wareki).substring(2, nenIdx);
	var tuki = String(wareki).substring(nenIdx +1, tukIdx);
	var hi = String(wareki).substring(tukIdx +1, hiIdx);

	var seirkiNen = JCToAD(gengo,nen);

    var yyyy = toTwoDigits(seirkiNen, 4);
    var mm = toTwoDigits(tuki, 2);
    var dd = toTwoDigits(hi, 2);
    var ymd = String(yyyy) + String(mm) + String(dd);

	return ymd;
}

/**
 * 「年」文字位置を戻す
 * @param {String} wareki 和暦の文字
 * @return {Number} 「年」文字位置
 */
function getNenIdx(wareki) {
	var nenIdx = String(wareki).indexOf("年");
	return nenIdx;
}

/**
 * 「月」文字位置を戻す
 * @param {String} wareki 和暦の文字
 * @return {Number} 「月」文字位置
 */
function getTukiIdx(wareki) {
	var tukIdx = String(wareki).indexOf("月");
	return tukIdx;
}

/**
 * 「日」文字位置を戻す
 * @param {String} wareki 和暦の文字
 * @return {Number} 「日」文字位置
 */
function getHiIdx(wareki) {
	var hiIdx = String(wareki).indexOf("日");
	return hiIdx;
}

/**
 * 和暦の妥当性チェック
 * @param {String} wareki 和暦の文字
 * @return {Boolean} 判定結果 true:和暦の文字ではない
 */
var warekiDateCheck = function(wareki) {
	ret = false;

	//元号チェック
	if (gengoCheck(String(wareki).substring(0,2))) {
		ret = true;
		return ret;
	}

	if (warekiDateCheck(wareki)) {
		ret = true;
		return ret;
	}

	return ret;
}

/**
 * 元号チェック
 * @param {String} gengo 元号の文字
 * @return {Boolean} 判定結果 true:元号の文字ではない
 */
var gengoCheck = function(gengo) {
	ret = false;

	switch (gengo) {
	case "令和":
		ret = false;
		break;
	case "平成":
		ret = false;
		break;
	case "昭和":
		ret = false;
		break;
	case "大正":
		ret = false;
		break;
	case "明治":
		ret = false;
		break;
	case "慶応":
		ret = false;
		break;
	default :
		ret = true;
		break;
	}

	return ret;
}

/**
 * 和暦の年月日チェック
 * @param {String} wareki 和暦の文字
 * @return {Boolean} 判定結果 true:和暦の年月日ではない
 */
var warekiDateCheck = function(wareki) {

	ret = false;

	//年月日文字存在チェック
	var nenIdx = getNenIdx(wareki);
	var tukIdx = getTukiIdx(wareki);
	var hiIdx = getHiIdx(wareki);

	var nen = String(wareki).substring(2, nenIdx);
	var tuki = String(wareki).substring(nenIdx +1, tukIdx);
	var hi = String(wareki).substring(tukIdx +1, hiIdx);

	var gengo = String(wareki).substring(0,2);
	if (!JCToAD(gengo, nen)) {
	//年のエラー
	   return true;
	}

	//和暦を西暦の8桁文字に変換
	var ymd = warekiToSeirekiStr(wareki);

	//元号と期間チェック
	if (gengoKikanCheck(gengo, ymd)) {
	//元号と年月日の不一致エラー
	   return true;
	}

	if (dateCheck(ymd)) {
		//日付の妥当性チェックエラー
		return true;
	}

	return ret;
}

/**
 * 元号と期間チェック
 * @param {String} gengo 元号の文字
 * @param {String} ymd 西暦8桁の文字
 * @return {Boolean} 判定結果 true:元号と年月日の不一致エラー
 */
var gengoKikanCheck = function(gengo, ymd) {

	ret = false;

	switch (gengo) {
	case "令和":
		if ("20190501" <= ymd) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case "平成":
		if ("19890108" <= ymd && "20190430" >= ymd) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case "昭和":
		if ("19261225" <= ymd && "19890107" >= ymd) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case "大正":
		if ("19120730" <= ymd && "19261224" >= ymd) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case "明治":
		if ("18680125" <= ymd && "19120729" >= ymd) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case "慶応":
		if ("18650501" <= ymd && "18680124" >= ymd) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	default :
		ret = true;
		break;
	}

	return ret;
}

/**
 * 月日の妥当性チェック
 * @param {String} ymd 西暦8桁の文字
 * @return {Boolean} 判定結果 true:月日の妥当性チェックエラー
 */
var dateCheck = function(ymd) {
	ret = false;

	//うるう年の計算
	var uruuNen = false;
	var numNen = Number(String(ymd).substring(0,4));
	if (0==(numNen%4)) {
	//4で割り切れる
		if (0==(numNen%100)) {
		//100で割り切れる
			if (0==(numNen%400)) {
			//400で割り切れる：うるう年の
				uruuNen = true;
			} else {
			//400で割り切れない：平年
				uruuNen = false;
			}
		} else {
		//100で割り切れない：うるう年の
			uruuNen = true;
		}
	} else {
	//4で割り切れない：平年
		uruuNen = false;
	}

	//月の妥当性チェック
	var numTuki =  Number(String(ymd).substring(4,6));
	if (numTuki<1 || numTuki >12) {
		//月の妥当性チェックエラー
		return true;
	}

	//日の妥当性チェック
	var numHi =  Number(String(ymd).substring(6,8));
	switch (numTuki) {
	case 1:
		if (1 <= numHi && 31 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case 2:
		if(uruuNen){
		//うるう年
			if (1 <= numHi && 29 >= numHi) {
				ret = false;
			} else {
				ret = true;
			}
		} else {
		//平年
			if (1 <= numHi && 28 >= numHi) {
				ret = false;
			} else {
				ret = true;
			}
		}
		break;
	case 3:
		if (1 <= numHi && 31 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case 4:
		if (1 <= numHi && 30 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case 5:
		if (1 <= numHi && 31 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case 6:
		if (1 <= numHi && 30 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case 7:
		if (1 <= numHi && 31 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case 8:
		if (1 <= numHi && 31 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case 9:
		if (1 <= numHi && 30 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case 10:
		if (1 <= numHi && 31 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case 11:
		if (1 <= numHi && 30 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	case 12:
		if (1 <= numHi && 31 >= numHi) {
			ret = false;
		} else {
			ret = true;
		}
		break;
	default :
		ret = true;
		break;
	}

	return ret;
}

/**
 * 指定したdatetime-local値を文字列にする
 */
var toStringDatetime = function(dt) {
	var dateTime = new Date(dt);
    var year = dateTime.getFullYear();
    var month = dateTime.getMonth() + 1;
    var day = dateTime.getDate();
    var hour = dateTime.getHours();
    var minute = dateTime.getMinutes();
    var second = dateTime.getSeconds();

    var yyyy = toTwoDigits(year, 4);
    var mm = toTwoDigits(month, 2);
    var dd = toTwoDigits(day, 2);
    var hh = toTwoDigits(hour, 2);
    var mm2 = toTwoDigits(minute, 2);
    var ss = toTwoDigits(second, 2);
    var strDateTime = yyyy + mm + dd + hh + mm2 + ss;

	return strDateTime;
}


