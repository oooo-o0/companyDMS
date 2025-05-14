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
public class SendReciveTranKeyInfo {

	//送受信区分
	private String send_recive_type;

	//年月
	private String year_month;

	//社員コード
	private String empl_code;

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

}
