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
public class UpdateInfo {

	//更新日時
	private String update_dateTime;

	//更新ユーザ
	private String update_userId;

	//更新プログラムID
	private String update_programId;

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
