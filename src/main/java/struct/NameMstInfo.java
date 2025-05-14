package struct;

public class NameMstInfo {

	//名称分類
	private String name_type;

	//送受信表示切分区分
	private String send_recive_branch;

	//名称コード
	private String name_code;

	//名称
	private String name_value;

	public String getName_type() {
		return name_type;
	}

	public void setName_type(String name_type) {
		this.name_type = name_type;
	}

	public String getSend_recive_branch() {
		return send_recive_branch;
	}

	public void setSend_recive_branch(String send_recive_branch) {
		this.send_recive_branch = send_recive_branch;
	}

	public String getName_code() {
		return name_code;
	}

	public void setName_code(String name_code) {
		this.name_code = name_code;
	}

	public String getName_value() {
		return name_value;
	}

	public void setName_value(String name_value) {
		this.name_value = name_value;
	}

}
