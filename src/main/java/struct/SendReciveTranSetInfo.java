/**
 * (概要)
 *
 *  作成者 ：D.Ikeda
 *  作成日 : 2021/05/16
 *  履歴： バージョン 日付        更新者
 *         L0.00      2021/05/16  D.Ikeda
 */
package struct;

/**
 * @author D.Ikeda
 *
 */
public class SendReciveTranSetInfo {

	//最終登録文書/文書
	private String doc_name = "EMPTY";

	//操作区分
	private String operation_type = "EMPTY";

	//最終登録日時/最終受信日時
	private String last_send_recive_datetime  = "EMPTY";

	//最終登録状況/登録状況
	private String insert_status  = "EMPTY";

	//閲覧状況/確認状況
	private String status  = "EMPTY";

	//明細数
	private String detail_num  = "EMPTY";

	//更新日時
	private String update_dateTime  = "EMPTY";

	//更新ユーザ
	private String update_userId  = "EMPTY";

	//更新プログラムID
	private String update_programId  = "EMPTY";

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
