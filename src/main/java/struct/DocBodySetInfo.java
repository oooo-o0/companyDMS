/**
 * (概要)
 *
 *  作成者 ：D.Ikeda
 *  作成日 : 2021/05/23
 *  履歴： バージョン 日付        更新者
 *         L0.00      2021/05/23  D.Ikeda
 */
package struct;

/**
 * @author D.Ikeda
 *
 */
public class DocBodySetInfo {
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

	//更新日時
	private String update_dateTime;

	//更新ユーザ
	private String update_userId;

	//更新プログラムID
	private String update_programId;

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
