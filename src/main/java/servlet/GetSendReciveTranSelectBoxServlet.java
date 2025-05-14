package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;

/**
 * Servlet implementation class GetSendReciveTranSelectBoxServlet
 */
public class GetSendReciveTranSelectBoxServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSendReciveTranSelectBoxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (postParamSearchCheck(request)) {

    		String send_recive_type = "";
    		if (request.getParameter("send_recive_type") != null) {
    			send_recive_type = request.getParameter("send_recive_type");
    		}

     		String year_month = "";
    		if (request.getParameter("year_month") != null) {
    			year_month = request.getParameter("year_month");
    		}

     		String doc_name = "";
    		if (request.getParameter("doc_name") != null) {
    			doc_name = request.getParameter("doc_name");
    		}

    		String empl_code = "";
    		if (request.getParameter("empl_code") != null) {
    			empl_code = request.getParameter("empl_code");
    		}

    		String last_send_recive_datetime = "";
    		if (request.getParameter("last_send_recive_datetime") != null) {
    			last_send_recive_datetime = request.getParameter("last_send_recive_datetime");
    		}

    		String insert_status = "";
    		if (request.getParameter("insert_status") != null) {
    			insert_status = request.getParameter("insert_status");
    		}

    		String status = "";
    		if (request.getParameter("status") != null) {
    			status = request.getParameter("status");
    		}

    		String employment_date = "";
    		if (request.getParameter("employment_date") != null) {
    			employment_date = request.getParameter("employment_date");
    		}

    		List<String> infoList = new ArrayList<String>();

    		//データを取得
    		infoList = getData(empl_code, year_month, send_recive_type, doc_name, last_send_recive_datetime,
    				insert_status, status, employment_date);

    		//JSON形式変換
    		String JsonStr = "";
    		JsonStr = josnWrite(infoList);

    		//レスポンスを出力
    		postOut(JsonStr, response);

    	}

	}

	/**
	 * SQLを実行しデータを取得
	 */
    public List<String> getData(String empl_code, String year_month, String send_recive_type,
    		String doc_name, String last_send_recive_datetime, String insert_status, String status,String employment_date) {

    	Connection con = null;
	    Statement stm = null;
	    ResultSet rs = null;

		//空白文字を＋に変換し最後に改行コードを付加しデコード対象文字を生成する
		String decodedFileName ="";

	    try {
			decodedFileName = getDecodedFileName(doc_name);
		} catch (ServletException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println("emplCode:"+ empl_code);
		System.out.println("yearMonth:"+ year_month);
		System.out.println("sendReciveType:"+ send_recive_type);
		System.out.println("docName:"+ decodedFileName);
		System.out.println("last_send_recive_datetime:"+ last_send_recive_datetime);
		System.out.println("insert_status:"+ insert_status);
		System.out.println("status:"+ status);
		System.out.println("employment_date:"+ employment_date);

        List<String> infoList = new ArrayList<String>();

        String selectSql ="SELECT DISTINCT A.doc_name "
				+ "FROM doc.SendReciveTran A "
				+ "INNER JOIN doc.EmplMst B "
				+ "ON A.empl_code = B.empl_code"
				+ " AND B.del_flg = '0' AND (B.retire_date = '' OR B.retire_date >= '" + employment_date + "') "
				+ "LEFT OUTER JOIN doc.NameMst C "
				+ "ON A.operation_type = C.name_code "
				+ " AND C.name_type = 'operation_type' AND C.send_recive_branch ='1' "
				+ " AND C.del_flg = '0' "
				+ "LEFT OUTER JOIN doc.NameMst D "
				+ "ON A.insert_status = D.name_code "
				+ " AND D.name_type = 'last_ins_status' AND D.send_recive_branch ='1' "
				+ " AND D.del_flg = '0' "
				+ "LEFT OUTER JOIN doc.NameMst E "
				+ "ON A.status = E.name_code "
				+ "AND E.name_type = 'status' AND E.send_recive_branch ='1' "
				+ "AND E.del_flg = '0' "
				+ selectWhereCondition(empl_code, year_month, send_recive_type, decodedFileName, last_send_recive_datetime, insert_status, status);

        con = getConn();

        try {
            stm = con.createStatement();
    		logger.info(selectSql);
            rs = stm.executeQuery(selectSql);
			while (rs.next()) {
			    infoList.add(rs.getString("doc_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

        try {
            if ((rs != null)||(stm != null)||(con != null)) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return infoList;
    }

	/** JSON文字列を返す */
    //@Override
    public String josnWrite(List<String> infoList) {
    	String ret="";
    	boolean flg = false;
    	for (String info:infoList) {
    		if (!flg) {
        		ret = String.format("{\"doc_name\":\"%s\"}",
        				 info);
        		flg = true;
    		} else {
        		ret = ret + String.format(", {\"doc_name\":\"%s\"}",
       				 info);
     		}
    	}

    	String ret2 ="[" + ret + "]";
    	return ret2;
    }

	/*
	 * SELECT文のWHERE句を動的に文字列を生成
	 */
	private String selectWhereCondition(String empl_code, String year_month, String send_recive_type, String doc_name,
			String last_send_recive_datetime, String insert_status, String status) {

		String Condition = "WHERE ";
		String Connma = "";

		if (!"".equals(empl_code)) {
			Condition += "A.empl_code = '" + empl_code + "'";
			Connma = " AND ";
		}

		String Connma2 = "";

		if (!"".equals(year_month)) {
			Condition += Connma + "A.year_month = '" + year_month + "'";
			Connma = "";
			Connma2 = " AND ";
		}

		String Connma3 = "";

		if (!"".equals(send_recive_type)) {
			Condition += Connma + Connma2 + "A.send_recive_type = '" + send_recive_type + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = " AND ";
		}

		String Connma4 = "";

		if (!"".equals(doc_name)) {
			Condition += Connma + Connma2 + Connma3 +"doc_name = '" + doc_name + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = "";
			Connma4 = " AND ";
		}

		String Connma5 = "";

		if (!"".equals(last_send_recive_datetime)) {
			Condition += Connma + Connma2 + Connma3 + Connma4 +"last_send_recive_datetime = '" + last_send_recive_datetime + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = "";
			Connma4 = "";
			Connma5 = " AND ";
		}

		String Connma6 = "";

		if (!"".equals(insert_status)) {
			Condition += Connma + Connma2 + Connma3 + Connma4 + Connma5 +"insert_status = '" + insert_status + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = "";
			Connma4 = "";
			Connma5 = "";
			Connma6 = " AND ";
		}

		if (!"".equals(status)) {
			Condition += Connma + Connma2 + Connma3 + Connma4 + Connma5 + Connma6 + "status = '" + status + "'";
		}

		return Condition;
	}

}
