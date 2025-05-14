/**
 * クラス名：UserInfo.java
 * 説明：ログイン画面でUserIdのセレクトボックス用の構造体
 * 新規作成者：D.Ikeda
 * 新規作成日：2021/04/29
 */

package struct;

public class UserInfo {
	public String empl_code;
	public String empl_name;
	public String mng_grant;
	public String user_id;

	public String getEmpl_code() {
		return empl_code;
	}

	public void setEmpl_code(String empl_code) {
		this.empl_code = empl_code;
	}

	public String getEmpl_name() {
		return empl_name;
	}

	public void setEmpl_name(String empl_name) {
		this.empl_name = empl_name;
	}

	public String getMng_grant() {
		return mng_grant;
	}

	public void setMng_grant(String mng_grant) {
		this.mng_grant = mng_grant;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
