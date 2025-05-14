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

import struct.SendReciveTranEmplNameInfo;
import base.BaseServlet;
import docconst.CONST;

/**
 * Servlet implementation class GetSendReciveTranDataServlet
 */
public class GetSendReciveTranDataServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSendReciveTranDataServlet() {
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
    		if (request.getParameter(CONST.SEND_RECIVE_TYPE) != null) {
    			send_recive_type = request.getParameter(CONST.SEND_RECIVE_TYPE);
    		}

     		String year_month = "";
    		if (request.getParameter(CONST.YEAR_MONTH) != null) {
    			year_month = request.getParameter(CONST.YEAR_MONTH);
    		}

     		String doc_name = "";
    		if (request.getParameter(CONST.DOC_NAME) != null) {
    			doc_name = request.getParameter(CONST.DOC_NAME);
    		}

    		String empl_code = "";
    		if (request.getParameter(CONST.EMPL_CODE) != null) {
    			empl_code = request.getParameter(CONST.EMPL_CODE);
    		}

    		String last_send_recive_datetime_from = "";
    		if (request.getParameter(CONST.LAST_SEND_RECIVE_DATETIME_FROM) != null) {
    			last_send_recive_datetime_from = request.getParameter(CONST.LAST_SEND_RECIVE_DATETIME_FROM);
    		}

    		String last_send_recive_datetime_to = "";
    		if (request.getParameter(CONST.LAST_SEND_RECIVE_DATETIME_TO) != null) {
    			last_send_recive_datetime_to = request.getParameter(CONST.LAST_SEND_RECIVE_DATETIME_TO);
    		}

    		String insert_status = "";
    		if (request.getParameter(CONST.INSERT_STATUS) != null) {
    			insert_status = request.getParameter(CONST.INSERT_STATUS);
    		}

    		String status = "";
    		if (request.getParameter(CONST.STATUS) != null) {
    			status = request.getParameter(CONST.STATUS);
    		}

    		String employment_date = "";
    		if (request.getParameter(CONST.EMPLOYMENT_DATE) != null) {
    			employment_date = request.getParameter(CONST.EMPLOYMENT_DATE);
    		}

    		List<SendReciveTranEmplNameInfo> infoList = new ArrayList<SendReciveTranEmplNameInfo>();

    		//データを取得
    		infoList = getData(empl_code, year_month, send_recive_type, doc_name, last_send_recive_datetime_from,
    				last_send_recive_datetime_to, insert_status, status, employment_date);

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
    public List<SendReciveTranEmplNameInfo> getData(String empl_code, String year_month, String send_recive_type,
    		String doc_name, String last_send_recive_datetime_from, String last_send_recive_datetime_to,
    		String insert_status, String status,String employment_date) {

    	Connection con = null;
	    Statement stm = null;
	    ResultSet rs = null;

		//空白文字を＋に変換し最後に改行コードを付加しデコード対象文字を生成する
		String decodedFileName ="";

		decodedFileName = doc_name;

		System.out.println("emplCode:"+ empl_code);
		System.out.println("yearMonth:"+ year_month);
		System.out.println("sendReciveType:"+ send_recive_type);
		System.out.println("docName:"+ decodedFileName);
		System.out.println("last_send_recive_datetime_from:"+ last_send_recive_datetime_from);
		System.out.println("last_send_recive_datetime_to:"+ last_send_recive_datetime_to);
		System.out.println("insert_status:"+ insert_status);
		System.out.println("status:"+ status);
		System.out.println("employment_date:"+ employment_date);

        List<SendReciveTranEmplNameInfo> infoList = new ArrayList<SendReciveTranEmplNameInfo>();

        String selectSql = "";

        if ("".equals(decodedFileName)) {
        //文書名がALL又は空白の場合
            selectSql ="SELECT Z.*,C.name_value operation_name,D.name_value last_ins_status_name, E.name_value status_name"
            		+ " FROM ("
    				+ " SELECT B2.*,B3.empl_name,B3.retire_date FROM doc.SendReciveTran B2,("
    				+ "  SELECT A.empl_code,A.empl_name,A.retire_date FROM "
    				+ "(SELECT * FROM doc.EmplMst WHERE del_flg = '0' "
    				+ " AND (retire_date = '' OR retire_date >= '" + employment_date + "')) A WHERE EXISTS"
    				+ "  (SELECT * FROM doc.SendReciveTran B WHERE A.empl_code=B.empl_code)"
    				+ " ) B3 WHERE B2.empl_code = B3.empl_code"
    				+ " AND B2.send_recive_type = '1' AND B2.year_month ='" + year_month + "'"
    				+ " UNION "
    				+ "SELECT '1' send_recive_type,'" + year_month + "' year_month, A.empl_code,'' doc_name,'01' operaton_type,"
    				+ " '' last_send_recive_datetime,'01' insert_status,'00' status,'0' detail_num,"
    				+ " '' insert_datetime,'' insert_userid,'' insert_programid,'' update_datetime,'' update_userid,'' update_programid,"
    				+ " A.empl_name,A.retire_date FROM (SELECT * FROM doc.EmplMst WHERE del_flg = '0'"
    				+ " AND (retire_date = '' OR retire_date >= '" + employment_date + "')) A WHERE NOT EXISTS"
    				+ " (SELECT * FROM  doc.SendReciveTran B WHERE A.empl_code=B.empl_code"
    				+ " AND B.send_recive_type = '1' AND B.year_month ='" + year_month + "'"
    				+ ")"
    				+ ") Z"
    				+ " LEFT OUTER JOIN doc.NameMst C ON Z.operation_type = C.name_code  AND C.name_type = 'operation_type' AND C.send_recive_branch ='1'  AND C.del_flg = '0'"
    				+ " LEFT OUTER JOIN doc.NameMst D ON Z.insert_status = D.name_code  AND D.name_type = 'last_ins_status' AND D.send_recive_branch ='1'  AND D.del_flg = '0'"
    				+ " LEFT OUTER JOIN doc.NameMst E ON Z.status = E.name_code AND E.name_type = 'status' AND E.send_recive_branch ='1' AND E.del_flg = '0' "
    				+ selectWhereConditionALL(empl_code, year_month, send_recive_type,
    						last_send_recive_datetime_from, last_send_recive_datetime_to, insert_status, status);

        } else {
        //文書名が指定されている場合
            selectSql ="SELECT A.*, B.empl_name,B.retire_date,C.name_value operation_name,"
            		+ "D.name_value last_ins_status_name, E.name_value status_name "
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
    				+ selectWhereCondition(empl_code, year_month, send_recive_type, decodedFileName,
    						last_send_recive_datetime_from, last_send_recive_datetime_to, insert_status, status);
        }

        con = getConn();

        try {
            stm = con.createStatement();
    		logger.info(selectSql);
            rs = stm.executeQuery(selectSql);
			while (rs.next()) {
				SendReciveTranEmplNameInfo info = new SendReciveTranEmplNameInfo();
			    info.setSend_recive_type(rs.getString("send_recive_type"));
			    info.setYear_month(rs.getString("year_month").replace("-", ""));
			    info.setEmpl_code(rs.getString("empl_code"));
			    info.setDoc_name(rs.getString("doc_name"));
			    info.setOperation_type(rs.getString("operation_type"));
			    info.setLast_send_recive_datetime(rs.getString("last_send_recive_datetime"));
			    info.setInsert_status(rs.getString("insert_status"));
			    info.setStatus(rs.getString("status"));
			    info.setDetail_num(rs.getString("detail_num"));
			    info.setInsert_dateTime(rs.getString("insert_dateTime"));
			    info.setInsert_userId(rs.getString("insert_userId"));
			    info.setInsert_programId(rs.getString("insert_programId"));
			    info.setUpdate_dateTime(rs.getString("update_dateTime"));
			    info.setUpdate_userId(rs.getString("update_userId"));
			    info.setUpdate_programId(rs.getString("update_programId"));
			    info.setEmpl_name(rs.getString("empl_name"));
			    info.setRetire_date(rs.getString("retire_date"));
			    info.setOperation_name(rs.getString("operation_name"));
			    info.setLast_ins_status_name(rs.getString("last_ins_status_name"));
			    info.setStatus_name(rs.getString("status_name"));
			    if (rs.getString("operation_type").equals(CONST.OPERATION_TYPE_ETURAN)) {
				//文書閲覧の場合は操作ボタン背景色：ピンク（データの不整合以外はこのロジックは通過しない）
				    info.setOperation_color("#F8CBAD");
			    } else if(rs.getString("operation_type").equals(CONST.OPERATION_TYPE_RENRAKU)) {
				//共通連絡の場合は操作ボタン背景色：緑色
				    info.setOperation_color("#E2EFDA");
			    } else {
			    //操作ボタン背景色：水色
				    info.setOperation_color("#BDD7EE");
			    }
			    infoList.add(info);
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
    public String josnWrite(List<SendReciveTranEmplNameInfo> infoList) {
    	String ret="";
    	boolean flg = false;
    	for (SendReciveTranEmplNameInfo info:infoList) {
    		if (!flg) {
        		ret = String.format("{\"send_recive_type\":\"%s\",\"year_month\":\"%s\","
        				+ "\"empl_code\":\"%s\",\"doc_name\":\"%s\","
        				+ "\"operation_type\":\"%s\",\"last_send_recive_datetime\":\"%s\","
        				+ "\"insert_status\":\"%s\",\"status\":\"%s\","
        				+ "\"detail_num\":\"%s\",\"insert_dateTime\":\"%s\","
        				+ "\"insert_userId\":\"%s\",\"insert_programId\":\"%s\","
        				+ "\"update_dateTime\":\"%s\",\"update_userId\":\"%s\","
        				+ "\"update_programId\":\"%s\", \"empl_name\":\"%s\","
        				+ "\"retire_date\":\"%s\", \"operation_name\":\"%s\","
        				+ "\"last_ins_status_name\":\"%s\", \"status_name\":\"%s\","
        				+ "\"operation_color\":\"%s\"}",
        				 info.getSend_recive_type(), info.getYear_month(), info.getEmpl_code(), info.getDoc_name(),
        				 info.getOperation_type(), info.getLast_send_recive_datetime(), info.getInsert_status(), info.getStatus(),
        				 info.getDetail_num(), info.getInsert_dateTime(), info.getInsert_userId(), info.getInsert_programId(),
        				 info.getUpdate_dateTime(), info.getUpdate_userId(), info.getUpdate_programId(),
        				 info.getEmpl_name(), info.getRetire_date(), info.getOperation_name(),
        				 info.getLast_ins_status_name(), info.getStatus_name(),
        				 info.getOperation_color());
        		flg = true;
    		} else {
        		ret = ret + String.format(",{\"send_recive_type\":\"%s\",\"year_month\":\"%s\","
           				+ "\"empl_code\":\"%s\",\"doc_name\":\"%s\","
        				+ "\"operation_type\":\"%s\",\"last_send_recive_datetime\":\"%s\","
        				+ "\"insert_status\":\"%s\",\"status\":\"%s\","
        				+ "\"detail_num\":\"%s\",\"insert_dateTime\":\"%s\","
        				+ "\"insert_userId\":\"%s\",insert_programId:\"%s\","
        				+ "update_dateTime:\"%s\",update_userId:\"%s\","
        				+ "\"update_programId\":\"%s\", \"empl_name\":\"%s\","
        				+ "\"retire_date\":\"%s\", \"operation_name\":\"%s\","
        				+ "\"last_ins_status_name\":\"%s\", \"status_name\":\"%s\","
        				+ "\"operation_color\":\"%s\"}",
        				 info.getSend_recive_type(), info.getYear_month(), info.getEmpl_code(), info.getDoc_name(),
        				 info.getOperation_type(), info.getLast_send_recive_datetime(), info.getInsert_status(), info.getStatus(),
        				 info.getDetail_num(), info.getInsert_dateTime(), info.getInsert_userId(), info.getInsert_programId(),
        				 info.getUpdate_dateTime(), info.getUpdate_userId(), info.getUpdate_programId(),
        				 info.getEmpl_name(), info.getRetire_date(), info.getOperation_name(),
        				 info.getLast_ins_status_name(), info.getStatus_name(),
        				 info.getOperation_color());
     		}
    	}

    	String ret2 ="[" + ret + "]";
    	return ret2;
    }

	/*
	 * SELECT文のWHERE句を動的に文字列を生成
	 */
	private String selectWhereConditionALL(String empl_code, String year_month, String send_recive_type,
			String last_send_recive_datetime_from, String last_send_recive_datetime_to, String insert_status,
			String status) {

		String Condition = "WHERE ";
		String Connma = "";

		if (!"".equals(empl_code)) {
			Condition += "Z.empl_code = '" + empl_code + "'";
			Connma = " AND ";
		}

		String Connma2 = "";

		if (!"".equals(year_month)) {
			Condition += Connma + "Z.year_month = '" + year_month + "'";
			Connma = "";
			Connma2 = " AND ";
		}

		String Connma3 = "";

		if (!"".equals(send_recive_type)) {
			Condition += Connma + Connma2 + "Z.send_recive_type = '" + send_recive_type + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = " AND ";
		}

		String Connma4 = "";

		if (!"".equals(last_send_recive_datetime_from)) {
			Condition += Connma + Connma2 + Connma3 + "Z.last_send_recive_datetime >= '"
					+ last_send_recive_datetime_from + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = "";
			Connma4 = " AND ";
		}

		String Connma5 = "";

		if (!"".equals(last_send_recive_datetime_to)) {
			Condition += Connma + Connma2 + Connma3 + Connma4 + "Z.last_send_recive_datetime <= '"
					+ last_send_recive_datetime_to + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = "";
			Connma4 = "";
			Connma5 = " AND ";
		}

		String Connma6 = "";

		if (!"".equals(insert_status)) {
			Condition += Connma + Connma2 + Connma3 + Connma4 + Connma5 + "Z.insert_status = '"
					+ insert_status + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = "";
			Connma4 = "";
			Connma5 = "";
			Connma6 = " AND ";
		}

		if (!"".equals(status)) {
			Condition += Connma + Connma2 + Connma3 + Connma4 + Connma5 + Connma6 + "Z.status = '" + status + "'";
		}

		return Condition;
	}


	/*
	 * SELECT文のWHERE句を動的に文字列を生成
	 */
	private String selectWhereCondition(String empl_code, String year_month, String send_recive_type, String doc_name,
			String last_send_recive_datetime_from, String last_send_recive_datetime_to, String insert_status,
			String status) {

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
			Condition += Connma + Connma2 + Connma3 +"A.doc_name = '" + doc_name + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = "";
			Connma4 = " AND ";
		}

		String Connma5 = "";

		if (!"".equals(last_send_recive_datetime_from)) {
			Condition += Connma + Connma2 + Connma3 + Connma4 +"A.last_send_recive_datetime >= '"
					+ last_send_recive_datetime_from + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = "";
			Connma4 = "";
			Connma5 = " AND ";
		}

		String Connma6 = "";

		if (!"".equals(last_send_recive_datetime_to)) {
			Condition += Connma + Connma2 + Connma3 + Connma4 + Connma5 +"A.last_send_recive_datetime <= '"
					+ last_send_recive_datetime_to + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = "";
			Connma4 = "";
			Connma5 = "";
			Connma6 = " AND ";
		}

		String Connma7 = "";

		if (!"".equals(insert_status)) {
			Condition += Connma + Connma2 + Connma3 + Connma4 + Connma5 + Connma6 + "A.insert_status = '"
					+ insert_status + "'";
			Connma = "";
			Connma2 = "";
			Connma3 = "";
			Connma4 = "";
			Connma5 = "";
			Connma6 = "";
			Connma7 = " AND ";
		}

		if (!"".equals(status)) {
			Condition += Connma + Connma2 + Connma3 + Connma4 + Connma5 + Connma6 + Connma7 + "A.status = '" + status + "'";
		}

		return Condition;
	}


}
