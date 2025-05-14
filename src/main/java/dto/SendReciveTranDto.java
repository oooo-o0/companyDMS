/**
 * クラス名：SendReciveTranDto.java
 * 説明：送受信トランテーブルの項目クラス
 * 新規作成者：D.Ikeda
 * 新規作成日：2021/05/15
 */
package dto;

public class SendReciveTranDto {

	//送受信区分
	private String send_recive_type;

	//年月
	private String year_month;

	//社員コード
	private String empl_code;

	//最終登録文書/文書
	private String doc_name;

	//操作区分
	private String operation_type;

	//最終登録日時/最終受信日時
	private String last_send_recive_datetime;

	//最終登録状況/登録状況
	private String insert_status;

	//閲覧状況/確認状況
	private String status;

	//明細数
	private String detail_num;

	//作成日時
	private String insert_dateTime;

	//作成ユーザ
	private String insert_userId;

	//作成プログラムID
	private String insert_programId;

	//更新日時
	private String update_dateTime;

	//更新ユーザ
	private String update_userId;

	//更新プログラムID
	private String update_programId;

	public String getSend_recive_type() {
		return send_recive_type;
	}

	public void setSend_recive_type(String send_recive_type) {
		this.send_recive_type = send_recive_type;
	}

	public String getYear_month() {
		return year_month;
	}

	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}

	public String getEmpl_code() {
		return empl_code;
	}

	public void setEmpl_code(String empl_code) {
		this.empl_code = empl_code;
	}

	public String getDoc_name() {
		return doc_name;
	}

	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}

	public String getOperation_type() {
		return operation_type;
	}

	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}

	public String getLast_send_recive_datetime() {
		return last_send_recive_datetime;
	}

	public void setLast_send_recive_datetime(String last_send_recive_datetime) {
		this.last_send_recive_datetime = last_send_recive_datetime;
	}

	public String getInsert_status() {
		return insert_status;
	}

	public void setInsert_status(String insert_status) {
		this.insert_status = insert_status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDetail_num() {
		return detail_num;
	}

	public void setDetail_num(String detail_num) {
		this.detail_num = detail_num;
	}

	public String getInsert_dateTime() {
		return insert_dateTime;
	}

	public void setInsert_dateTime(String insert_dateTime) {
		this.insert_dateTime = insert_dateTime;
	}

	public String getInsert_userId() {
		return insert_userId;
	}

	public void setInsert_userId(String insert_userId) {
		this.insert_userId = insert_userId;
	}

	public String getInsert_programId() {
		return insert_programId;
	}

	public void setInsert_programId(String insert_programId) {
		this.insert_programId = insert_programId;
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
