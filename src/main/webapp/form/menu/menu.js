/**
 * menu.js
 * グラフデータ取得によりセレクトボックスと動的HTML生成プログラム
 * @author D.Ikeda
 *    Date:2021/04/30
 * @version 1.0.0
 */
$(function() {

	var selectrdVal = "";
	var userInfoArray = [];


	  // doc0002入力ダイアログを定義
	  $("#input_doc0002").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym = $('#parm_ym').val();
	            	$(this).dialog("close");

	            	subBtn2_After(parm_ym);
	            }
	        }
	    ]
	  });

	  // doc0004入力ダイアログを定義
	  $("#input_doc0004").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym2 = $('#parm_ym2').val();
	            	$(this).dialog("close");

	            	subBtn4_After(parm_ym2);
	            }
	        }
	    ]
	  });

	  // doc0005入力ダイアログを定義
	  $("#input_doc0005").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym3 = $('#parm_ym3').val();
	            	var parm_empl_code5 = $('#parm_empl_code5').val();
	            	$(this).dialog("close");

	            	subBtn5_After(parm_ym3, parm_empl_code5);
	            }
	        }
	    ]
	  });

	  // doc0006入力ダイアログを定義
	  $("#input_doc0006").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 650,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理

	            	var parm_ym4 = $('#parm_ym4').val();
	            	var parm_empl_code6 = $('#parm_empl_code6').val();

	            	//ラジオボタンオブジェクトを取得する
	            	var radios = document.getElementsByName("radio_grop3");

	            	//取得したラジオボタンオブジェクトから選択されたものを探し出す
	            	var parm_doc_type6;
	            	for(var i=0; i<radios.length; i++){
	            		if (radios[i].checked) {
	            	    //選択されたラジオボタンのvalue値を取得する
	            			parm_doc_type6 = radios[i].value;
	            	    	break;
	            		}
	            	}


	            	var parm_doc_name6 = $('#parm_doc_name6').val().substring(12,$('#parm_doc_name6').val().length);
	            	$(this).dialog("close");

	            	subBtn6_After(parm_ym4, parm_empl_code6, parm_doc_type6,parm_doc_name6);
	            }
	           }

	    	]
	  });

	  // doc0007入力ダイアログを定義
	  $("#input_doc0007").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 650,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym5 = $('#parm_ym5').val();
	            	var parm_empl_code7 = $('#parm_empl_code7').val();

	            	//ラジオボタンオブジェクトを取得する
	            	var radios = document.getElementsByName("radio_grop4");

	            	//取得したラジオボタンオブジェクトから選択されたものを探し出す
	            	var parm_doc_type7;
	            	for(var i=0; i<radios.length; i++){
	            		if (radios[i].checked) {
	            	    //選択されたラジオボタンのvalue値を取得する
	            			parm_doc_type7 = radios[i].value;
	            	    	break;
	            		}
	            	}

	            	var parm_doc_name7 = $('#parm_doc_name7').val().substring(12,$('#parm_doc_name7').val().length);
	            	$(this).dialog("close");

	            	subBtn7_After(parm_ym5, parm_empl_code7, parm_doc_type7, parm_doc_name7);
	            }
	        }
	    ]
	  });

	  // doc0008入力ダイアログを定義
	  $("#input_doc0008").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 650,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym6 = $('#parm_ym6').val();
	            	var parm_empl_code8 = $('#parm_empl_code8').val();

	            	//ラジオボタンオブジェクトを取得する
	            	var radios = document.getElementsByName("radio_grop5");

	            	//取得したラジオボタンオブジェクトから選択されたものを探し出す
	            	var parm_doc_type8;
	            	for(var i=0; i<radios.length; i++){
	            		if (radios[i].checked) {
	            	    //選択されたラジオボタンのvalue値を取得する
	            			parm_doc_type8 = radios[i].value;
	            	    	break;
	            		}
	            	}

	            	var parm_doc_name8 = $('#parm_doc_name8').val().substring(12,$('#parm_doc_name8').val().length);
	            	$(this).dialog("close");

	            	subBtn8_After(parm_ym6, parm_empl_code8, parm_doc_type8, parm_doc_name8);
	            }
	        }
	    ]
	  });

	  // doc0009入力ダイアログを定義
	  $("#input_doc0009").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym7 = $('#parm_ym7').val();
	            	var parm_empl_code9 = $('#parm_empl_code9').val();
	            	$(this).dialog("close");

	            	subBtn9_After(parm_ym7, parm_empl_code9);
	            }
	        }
	    ]
	  });

	  // doc0010入力ダイアログを定義
	  $("#input_doc0010").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym8 = $('#parm_ym8').val();
	            	$(this).dialog("close");

	            	subBtn10_After(parm_ym8);
	            }
	        }
	    ]
	  });

	  // doc0011入力ダイアログを定義
	  $("#input_doc0011").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 600,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym9 = $('#parm_ym9').val();
	            	var parm_doc_name11 = $('#parm_doc_name11').val();
	            	$(this).dialog("close");

	            	subBtn11_After(parm_ym9, parm_doc_name11);
	            }
	        }
	    ]
	  });

	  // doc0012入力ダイアログを定義
	  $("#input_doc0012").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 600,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym10 = $('#parm_ym10').val();
	            	var parm_doc_name12 = $('#parm_doc_name12').val();
	            	$(this).dialog("close");

	            	subBtn12_After(parm_ym10, parm_doc_name12);
	            }
	        }
	    ]
	  });

	  // doc0013入力ダイアログを定義
	  $("#input_doc0013").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 600,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym11 = $('#parm_ym11').val();
	            	var parm_doc_name13 = $('#parm_doc_name13').val();
	            	$(this).dialog("close");

	            	subBtn13_After(parm_ym11, parm_doc_name13);
	            }
	        }
	    ]
	  });

	  // doc0015入力ダイアログを定義
	  $("#input_doc0015").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 600,
	    height: 400,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	//ラジオボタンオブジェクトを取得する
	            	var radios = document.getElementsByName("radio_grop");

	            	//取得したラジオボタンオブジェクトから選択されたものを探し出す
	            	var parm_mode;
	            	for(var i=0; i<radios.length; i++){
	            		if (radios[i].checked) {
	            	    //選択されたラジオボタンのvalue値を取得する
	            	    	parm_mode = radios[i].value;
	            	    	break;
	            		}
	            	}
	            	var parm_ym12 = $('#parm_ym12').val();
	            	var parm_empl_code15 = $('#parm_empl_code15').val();

	            	//ラジオボタンオブジェクトを取得する
	            	var radios2 = document.getElementsByName("radio_grop6");

	            	//取得したラジオボタンオブジェクトから選択されたものを探し出す
	            	var parm_doc_type15;
	            	for(var i=0; i<radios2.length; i++){
	            		if (radios2[i].checked) {
	            	    //選択されたラジオボタンのvalue値を取得する
	            			parm_doc_type15 = radios2[i].value;
	            	    	break;
	            		}
	            	}


	            	var parm_doc_name15 = $('#parm_doc_name15').val().substring(12,$('#parm_doc_name15').val().length);
	            	$(this).dialog("close");

	            	subBtn15_After(parm_mode, parm_ym12, parm_empl_code15, parm_doc_type15, parm_doc_name15);
	            }
	        }
	    ]
	  });

	  // doc0017入力ダイアログを定義
	  $("#input_doc0017").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym13 = $('#parm_ym13').val();
	            	var parm_empl_code17 = $('#parm_empl_code17').val();
	            	$(this).dialog("close");

	            	subBtn17_After(parm_ym13, parm_empl_code17);
	            }
	        }
	    ]
	  });

	  // doc0018入力ダイアログを定義
	  $("#input_doc0018").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 600,
	    height: 450,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym14 = $('#parm_ym14').val();
	            	var parm_empl_code18 = $('#parm_empl_code18').val();
	            	//ラジオボタンオブジェクトを取得する
	            	var radios = document.getElementsByName("radio_grop2");

	            	//取得したラジオボタンオブジェクトから選択されたものを探し出す
	            	var parm_doc_type18;
	            	for(var i=0; i<radios.length; i++){
	            		if (radios[i].checked) {
	            	    //選択されたラジオボタンのvalue値を取得する
	            			parm_doc_type18 = radios[i].value;
	            	    	break;
	            		}
	            	}
	            	var parm_doc_name18 = $('#parm_doc_name18').val().substring(12,$('#parm_doc_name18').val().length);
	            	var parm_recive_datetime = $('#parm_recive_datetime').val();

	            	$(this).dialog("close");

	            	subBtn18_After(parm_ym14, parm_empl_code18, parm_doc_type18, parm_doc_name18, parm_recive_datetime);
	            }
	        }
	    ]
	  });

	  // doc0019入力ダイアログを定義
	  $("#input_doc0019").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 600,
	    height: 400,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym9 = $('#parm_ym15').val();
	            	var parm_doc_name19 = $('#parm_doc_name19').val();

	            	$(this).dialog("close");
	            	console.log("parm_doc_name19");
	            	console.log(parm_doc_name19);
	            	subBtn19_After(parm_ym9, parm_doc_name19);
	            }
	        }
	    ]
	  });

	  // doc0020入力ダイアログを定義
	  $("#input_doc0020").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 600,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym16 = $('#parm_ym16').val();
	            	var parm_empl_code20 = $('#parm_empl_code20').val();
	            	var parm_doc_name20 = $('#parm_doc_name20').val().substring(12,$('#parm_doc_name20').val().length);

	            	$(this).dialog("close");

	            	subBtn20_After(parm_ym16, parm_empl_code20, parm_doc_name20);
	            }
	        }
	    ]
	  });
	  
	  // doc0005b入力ダイアログを定義
	  $("#input_doc0005b").dialog({
	    autoOpen: false,
	    modal: true,
	    width: 500,
	    height: 350,
	    buttons: [
	    	{
	            text: '画面起動',
	            class:'c-button',
	            click: function() {
	                //ボタンを押したときの処理
	            	var parm_ym17 = $('#parm_ym17').val();
	            	var parm_empl_code21 = $('#parm_empl_code21').val();
	            	$(this).dialog("close");

	            	subBtn5b_After(parm_ym17, parm_empl_code21);
	            }
	        }
	    ]
	  });




	function initDialog_doc0002() {
		$("#input_doc0002").dialog("open");
	    return false;
	}

	function initDialog_doc0004() {
		$("#input_doc0004").dialog("open");
	    return false;
	}

	function initDialog_doc0005() {
		$("#input_doc0005").dialog("open");
	    return false;
	}

	function initDialog_doc0006() {
		$("#input_doc0006").dialog("open");
	    return false;
	}

	function initDialog_doc0007() {
		$("#input_doc0007").dialog("open");
	    return false;
	}

	function initDialog_doc0008() {
		$("#input_doc0008").dialog("open");
	    return false;
	}

	function initDialog_doc0009() {
		$("#input_doc0009").dialog("open");
	    return false;
	}

	function initDialog_doc0010() {
		$("#input_doc0010").dialog("open");
	    return false;
	}

	function initDialog_doc0011() {
		$("#input_doc0011").dialog("open");
	    return false;
	}

	function initDialog_doc0012() {
		$("#input_doc0012").dialog("open");
	    return false;
	}

	function initDialog_doc0013() {
		$("#input_doc0013").dialog("open");
	    return false;
	}

	function initDialog_doc0015() {
		$("#input_doc0015").dialog("open");
	    return false;
	}

	function initDialog_doc0017() {
		$("#input_doc0017").dialog("open");
	    return false;
	}

	function initDialog_doc0018() {
		$("#input_doc0018").dialog("open");
	    return false;
	}

	function initDialog_doc0019() {
		$("#input_doc0019").dialog("open");
	    return false;
	}

	function initDialog_doc0020() {
		$("#input_doc0020").dialog("open");
	    return false;
	}
	
	function initDialog_doc0005b() {
		$("#input_doc0005b").dialog("open");
	    return false;
	}

	$(document).ready(function(){

		userInfoArray = init("menu");

		menuInit();

	    $("#btn1").click(function(){
		    subBtn1();
	 	});
	    $("#btn2").click(function(){
		    subBtn2();
	 	});
	    $("#btn3").click(function(){
		    subBtn3();
	 	});
	    $("#btn4").click(function(){
		    subBtn4();
	 	});
	    $("#btn5").click(function(){
		    subBtn5();
	 	});
	    $("#btn6").click(function(){
		    subBtn6();
	 	});
	    $("#btn7").click(function(){
		    subBtn7();
	 	});
	    $("#btn8").click(function(){
		    subBtn8();
	 	});
	    $("#btn9").click(function(){
		    subBtn9();
	 	});
	    $("#btn10").click(function(){
		    subBtn10();
	 	});
	    $("#btn11").click(function(){
		    subBtn11();
	 	});
	    $("#btn12").click(function(){
		    subBtn12();
	 	});
	    $("#btn13").click(function(){
		    subBtn13();
	 	});
	    $("#btn14").click(function(){
		    subBtn14();
	 	});
	    $("#btn15").click(function(){
		    subBtn15();
	 	});
	    $("#btn16").click(function(){
		    subBtn16();
	 	});
	    $("#btn17").click(function(){
		    subBtn17();
	 	});
	    $("#btn18").click(function(){
		    subBtn18();
	 	});
	    $("#btn19").click(function(){
		    subBtn19();
	 	});
	    $("#btn20").click(function(){
		    subBtn20();
	 	});
	 	$("#btn5b").click(function(){
			subBtn5b();
	 	});
	});

	$(window).on('load',function(){

	});


	function menuInit() {

		var emplName = userInfoArray[1];
		$("#useName").text(emplName);

	}

	//画面遷移処理
	function subBtn1() {

		//パラメータなし

		//新しいウィンドウで表示
		window.open("http://localhost:8080/ibiDoc/form/doc0001/doc0001.html");

	}
	//画面遷移処理
	function subBtn2() {

		//パラメータ入力
		initDialog_doc0002();

	}

	//画面遷移処理
	function subBtn2_After(parm_ym) {

		var ym = parm_ym.replace("-","");

		var url = "http://localhost:8080/ibiDoc/form/doc0002/doc0002.html";
		url += "?ym=" + ym;

		//新しいウィンドウで表示
		window.open(url);

	}

	//画面遷移処理
	function subBtn3() {

		//パラメータなし

		//新しいウィンドウで表示
		window.open("http://localhost:8080/ibiDoc/form/doc0003/doc0003.html");

	}
	//画面遷移処理
	function subBtn4() {

		//パラメータ入力
		initDialog_doc0004();

	}

	//画面遷移処理
	function subBtn4_After(parm_ym2) {

		var ym2 = parm_ym2.replace("-","");

		var url = "http://localhost:8080/ibiDoc/form/doc0004/doc0004.html";
		url += "?ym=" + ym2;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn5() {

		//パラメータ入力
		initDialog_doc0005();

	}

	//画面遷移処理
	function subBtn5_After(parm_ym3, parm_empl_code5) {

//		var screenId ="doc0005";
		var ym3 = parm_ym3.replace("-","");
		var emplCode = parm_empl_code5;
		var url = "http://localhost:8080/ibiDoc/form/doc0005/doc0005.html";
		url += "?ym=" + ym3 + "?ecd=" + emplCode;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn6() {

		//パラメータ入力
		initDialog_doc0006();

	}

	//画面遷移処理
	function subBtn6_After(parm_ym4, parm_empl_code6, parm_doc_type6,parm_doc_name6) {
		//給与明細
		//'?sr=1?ym=202103?ecd=99998?dt=001?dn=HSFSSBSBBSNSNNSNSS?rdt=20210310205011

		//給与明細添付資料
		//'?sr=1?ym=202103?ecd=99998?dt=002?dn=HSFSSBSBBSNSNNSNSS?rdt=20210310205011

		//共通連絡
		//'?sr=1?ym=202103?ecd=99998?dt=003?dn=HSFSSBSBBSNSNNSNSS?rdt=20210310205011

		//連絡文書
		//'?sr=1?ym=202103?ecd=99998?dt=004?dn=HSFSSBSBBSNSNNSNSS?rdt=20210310205011

//		var screenId ="doc0006";
		var ym4 = parm_ym4.replace("-","");
		var emplCode = parm_empl_code6;
	    var docType = parm_doc_type6;
	    //parm_doc_nameをBASE64エンコード変換
	    var docName = encodeBase64Utf8(parm_doc_name6);

		var url = "http://localhost:8080/ibiDoc/form/doc0006/doc0006.html";
		url += "?sr=1?ym=" + ym4 + "?ecd=" + emplCode + "?dt=" + docType +  "?dn=" + docName;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn7() {

		//パラメータ入力
		initDialog_doc0007();

	}

	//画面遷移処理
	function subBtn7_After(parm_ym5, parm_empl_code7, parm_doc_type7, parm_doc_name7) {

//		var screenId ="doc0007";
		var ym5 = parm_ym5.replace("-","");
		var emplCode = parm_empl_code7;
	    var docType = parm_doc_type7;

	    //parm_doc_nameをBASE64エンコード変換
	    var docName = encodeBase64Utf8(parm_doc_name7);

		var url = "http://localhost:8080/ibiDoc/form/doc0007/doc0007.html";
		url += "?sr=1?ym=" + ym5 + "?ecd=" + emplCode + "?dt=" + docType +  "?dn=" + docName;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn8() {

		//パラメータ入力
		initDialog_doc0008();

	}

	//画面遷移処理
	function subBtn8_After(parm_ym6, parm_empl_code8, parm_doc_type8, parm_doc_name8) {

//		var screenId ="doc0008";
		var ym6 = parm_ym6.replace("-","");
		var emplCode = parm_empl_code8;
	    var docType = parm_doc_type8;

	    //parm_doc_nameをBASE64エンコード変換
	    var docName = encodeBase64Utf8(parm_doc_name8);

		var url = "http://localhost:8080/ibiDoc/form/doc0008/doc0008.html";
		url += "?sr=1?ym=" + ym6 + "?ecd=" + emplCode + "?dt=" + docType +  "?dn=" + docName;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn9() {

		//パラメータ入力
		initDialog_doc0009();

	}

	//画面遷移処理
	function subBtn9_After(parm_ym7, parm_empl_code9) {

		var ym7 = parm_ym7.replace("-","");
		var emplCode = parm_empl_code9;
		var url = "http://localhost:8080/ibiDoc/form/doc0009/doc0009.html";
		url += "?sr=2?ym=" + ym7 + "?ecd=" + emplCode;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn10() {

		//パラメータ入力
		initDialog_doc0010();

	}

	//画面遷移処理
	function subBtn10_After(parm_ym8) {

//		var screenId ="doc0010";
		var ym8 = parm_ym8.replace("-","");
		var url = "http://localhost:8080/ibiDoc/form/doc0010/doc0010.html";
		url += "?ym=" + ym8;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn11() {

		//パラメータ入力
		initDialog_doc0011();

	}

	//画面遷移処理
	function subBtn11_After(parm_ym9, parm_doc_name11) {

//		var screenId ="doc0011";
		var ym9 = parm_ym9.replace("-","");
	    //parm_doc_nameをBASE64エンコード変換
	    var docName = encodeBase64Utf8(parm_doc_name11);

		var url = "http://localhost:8080/ibiDoc/form/doc0011/doc0011.html";
		url +=  "?sr=1?ym=" + ym9 + "?dt=003?dn=" + docName;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn12() {

		//パラメータ入力
		initDialog_doc0012();

	}

	//画面遷移処理
	function subBtn12_After(parm_ym10, parm_doc_name12) {

//		var screenId ="doc0012";
		var ym10 = parm_ym10.replace("-","");
	    //parm_doc_nameをBASE64エンコード変換
	    var docName = encodeBase64Utf8(parm_doc_name12);

		var url = "http://localhost:8080/ibiDoc/form/doc0012/doc0012.html";
		url +=  "?sr=1?ym=" + ym10 + "?dt=003?dn=" + docName;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn13() {

		//パラメータ入力
		initDialog_doc0013();

	}

	//画面遷移処理
	function subBtn13_After(parm_ym11, parm_doc_name13) {

//		var screenId ="doc0013";
		var ym11 = parm_ym11.replace("-","");
	    //parm_doc_nameをBASE64エンコード変換
	    var docName = encodeBase64Utf8(parm_doc_name13);

		var url = "http://localhost:8080/ibiDoc/form/doc0013/doc0013.html";
		url += "?sr=1?ym=" + ym11 + "?dt=003?dn=" + docName;

		//新しいウィンドウで表示
		window.open(url);
	}


	//画面遷移処理
	function subBtn14() {

		//パラメータなし

		//新しいウィンドウで表示
		window.open("http://localhost:8080/ibiDoc/form/doc0014/doc0014.html");

	}

	//画面遷移処理
	function subBtn15() {

		//表示モード
		//'?md=0?sr=2?ym=202103?ecd=99998?dt=005?dn=HSFSSBSBBSNSNNSNSS

		//登録モード
		//'?md=1?sr=2?ym=202103?ecd=99998?dt=005?dn=HSFSSBSBBSNSNNSNSS

		//パラメータ入力
		initDialog_doc0015();

	}

	//画面遷移処理
	function subBtn15_After(param_mode, parm_ym12, parm_empl_code15, parm_doc_type15, parm_doc_name15) {

		var mode = param_mode;
		var ym12 = parm_ym12.replace("-","");
		var emplCode = parm_empl_code15;
	    var docType = parm_doc_type15;

	    //parm_doc_nameをBASE64エンコード変換
	    var docName = encodeBase64Utf8(parm_doc_name15);

		var url = "http://localhost:8080/ibiDoc/form/doc0015/doc0015.html";
		url += "?md=" + mode + "?sr=2?ym=" + ym12 + "?ecd=" + emplCode + "?dt=" + docType + "?dn=" + docName;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn16() {

		//パラメータなし

		//新しいウィンドウで表示
		window.open("http://localhost:8080/ibiDoc/form/doc0016/doc0016.html");

	}

	//画面遷移処理
	function subBtn17() {

		//パラメータ入力
		initDialog_doc0017();

	}

	//画面遷移処理
	function subBtn17_After(parm_ym13, parm_empl_code17) {

		var ym13 = parm_ym13.replace("-","");
		var emplCode = parm_empl_code17;
		var url = "http://localhost:8080/ibiDoc/form/doc0017/doc0017.html";
		url += "?sr=2?ym=" + ym13 + "?ecd=" + emplCode;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn18() {
		//パラメータ入力
		initDialog_doc0018();

	}

	//画面遷移処理
	function subBtn18_After(parm_ym14, parm_empl_code18, parm_doc_type18, parm_doc_name18, parm_recive_datetime) {
		//給与明細
		//'?sr=1?ym=202103?ecd=99998?dt=001?dn=HSFSSBSBBSNSNNSNSS?rdt=20210310205011

		//給与明細添付資料
		//'?sr=1?ym=202103?ecd=99998?dt=002?dn=HSFSSBSBBSNSNNSNSS?rdt=20210310205011

		var ym14 = parm_ym14.replace("-","");
		var emplCode = parm_empl_code18;
		var docType = parm_doc_type18;
	    //parm_doc_nameをBASE64エンコード変換
	    var docName = encodeBase64Utf8(parm_doc_name18);

	    //datetimeを文字列に変換
	    var reciveDateTime = toStringDatetime(parm_recive_datetime);

		var url = "http://localhost:8080/ibiDoc/form/doc0018/doc0018.html";
		url += "?sr=1?ym=" + ym14 + "?ecd=" + emplCode +"?dt=" + docType + "?dn=" + docName +"?rdt=" + reciveDateTime;

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn19() {

		//パラメータ入力
		initDialog_doc0019();

	}

	//画面遷移処理
	function subBtn19_After(parm_ym15, parm_doc_name19) {

		//'?sr=1?ym=202103?ecd=99998?dt=004?dn=HSFSSBSBBSNSNNSNSS?rdt=20210310205011

		var ym15 = parm_ym15.replace("-","");
		//var emplCode = parm_empl_code19;
	    //parm_doc_nameをBASE64エンコード変換
	    var docName = encodeBase64Utf8(parm_doc_name19);
	    //datetimeを文字列に変換
	   // var reciveDateTime = toStringDatetime(parm_recive_datetime);

		var url = "http://localhost:8080/ibiDoc/form/doc0019/doc0019.html";
		url += "?sr=1?ym=" + ym15 + "?ecd=" + "" +"?dt=004?dn=" + docName +"?rdt=" + "";

		//新しいウィンドウで表示
		window.open(url);
	}

	//画面遷移処理
	function subBtn20() {

		//パラメータ入力
		initDialog_doc0020();

	}

	//画面遷移処理
	function subBtn20_After(parm_ym16, parm_empl_code20, parm_doc_name20) {

		//'?sr=2?ym=202103?ecd=99998?dt=007?dn=HSFSSBSBBSNSNNSNSS

		var ym16 = parm_ym16.replace("-","");
		var emplCode = parm_empl_code20;
	    //parm_doc_nameをBASE64エンコード変換
	    var docName = encodeBase64Utf8(parm_doc_name20);

		var url = "http://localhost:8080/ibiDoc/form/doc0020/doc0020.html";
		url += "?sr=2?ym=" + ym16 + "?ecd=" + emplCode +"?dt=007?dn=" + docName;

		//新しいウィンドウで表示
		window.open(url);
	}
	
		//画面遷移処理
	function subBtn5b() {

		//パラメータ入力
		initDialog_doc0005b();

	}

	//画面遷移処理
	function subBtn5b_After(parm_ym17, parm_empl_code21) {

		var ym17 = parm_ym17.replace("-","");
		var emplCode = parm_empl_code21;
		var url = "http://localhost:8080/ibiDoc/form/doc0005b/doc0005b.html";
		url +="?ym=" + ym17 + "?ecd=" + emplCode;

		//新しいウィンドウで表示
		window.open(url);
	}
	
});
