/**
 * クラス名：DocBodyDto.java
 * 説明：文書内容テーブルの項目クラス
 * 新規作成者：D.Ikeda
 * 新規作成日：2021/05/15
 */
package dto;

public class DocBodyDto {
	//送受信区分
	private String send_recive_type;

	//年月
	private String year_month;

	//社員コード
	private String empl_code;

	//文書名
	private String doc_name;

	//文書種類
	private String doc_type;

	//タイトル
	private String doc_title;

	//内容
	private String doc_body;

	//有効期限(開始)
	private String expiration_from_dateTime;

	//有効期限(終了)
	private String expiration_to_dateTime;

	//メール送信日時/受信日時
	private String mail_send_recive_dateTime;

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

	public String getDoc_type() {
		return doc_type;
	}

	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}

	public String getDoc_title() {
		return doc_title;
	}

	public void setDoc_title(String doc_title) {
		this.doc_title = doc_title;
	}

	public String getDoc_body() {
		return doc_body;
	}

	public void setDoc_body(String doc_body) {
		this.doc_body = doc_body;
	}

	public String getExpiration_from_dateTime() {
		return expiration_from_dateTime;
	}

	public void setExpiration_from_dateTime(String expiration_from_dateTime) {
		this.expiration_from_dateTime = expiration_from_dateTime;
	}

	public String getExpiration_to_dateTime() {
		return expiration_to_dateTime;
	}

	public void setExpiration_to_dateTime(String expiration_to_dateTime) {
		this.expiration_to_dateTime = expiration_to_dateTime;
	}

	public String getMail_send_recive_dateTime() {
		return mail_send_recive_dateTime;
	}

	public void setMail_send_recive_dateTime(String mail_send_recive_dateTime) {
		this.mail_send_recive_dateTime = mail_send_recive_dateTime;
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
