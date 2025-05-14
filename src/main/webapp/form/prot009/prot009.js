/**
 *
 */
$(function() {
	$("#button4").click(function() {
		$("#div4").dialog({
			modal:true, //モーダル表示
			title:"テストダイアログ4", //タイトル
			buttons: { //ボタン
			"確認": function() {
				$("#p4").text($("#input1").val());
				$(this).dialog("close");
				},
			"キャンセル": function() {
				$(this).dialog("close");
				}
			}
		});
	});
});