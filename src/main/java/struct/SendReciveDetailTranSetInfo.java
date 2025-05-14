/**
 * (概要)
 *
 *  作成者 ：D.Ikeda
 *  作成日 : 2021/05/22
 *  履歴： バージョン 日付        更新者
 *         L0.00      2021/05/22  D.Ikeda
 */
package struct;

/**
 * @author D.Ikeda
 *
 */
public class SendReciveDetailTranSetInfo {
	//操作区分
	private String operation_type;

	//受信日時/最終連絡送信日時
	private String send_recive_datetime;

	//閲覧状況/確認状況
	private String check_status;

	//閲覧日時/確認日時
	private String check_datetime;

	//指摘内容
	private String finger_body;

	//更新日時
	private String update_dateTime;

	//更新ユーザ
	private String update_userId;

	//更新プログラムID
	private String update_programId;

	public String getOperation_type() {
		return operation_type;
	}

	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}

	public String getSend_recive_datetime() {
		return send_recive_datetime;
	}

	public void setSend_recive_datetime(String send_recive_datetime) {
		this.send_recive_datetime = send_recive_datetime;
	}

	public String getCheck_status() {
		return check_status;
	}

	public void setCheck_status(String check_status) {
		this.check_status = check_status;
	}

	public String getCheck_datetime() {
		return check_datetime;
	}

	public void setCheck_datetime(String check_datetime) {
		this.check_datetime = check_datetime;
	}

	public String getFinger_body() {
		return finger_body;
	}

	public void setFinger_body(String finger_body) {
		this.finger_body = finger_body;
	}

	public String getUpdate_dateTime() {
		return update_dateTime;
	}

	public void setUpdate_dateTime(String update_dateTime) {
		this.update_dateTime = update_dateTime;
	}

	public String getUpdate_userId() {
		return update_userId;
	}

	public void setUpdate_userId(String update_userId) {
		this.update_userId = update_userId;
	}

	public String getUpdate_programId() {
		return update_programId;
	}

	public void setUpdate_programId(String update_programId) {
		this.update_programId = update_programId;
	}

}
