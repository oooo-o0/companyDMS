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
import dto.EmplMstDto;

/**
 * Servlet implementation class GetEmplMstDataServlet
 */
public class GetEmplMstDataServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEmplMstDataServlet() {
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

    		String empl_code = "";
    		if (request.getParameter("empl_code") != null) {
    			empl_code = request.getParameter("empl_code");
    		}

    		String empl_name = "";
    		if (request.getParameter("empl_name") != null) {
    			empl_name = request.getParameter("empl_name");
    		}

    		String mng_grant = "";
    		if (request.getParameter("mng_grant") != null) {
    			mng_grant = request.getParameter("mng_grant");
    		}

    		String user_id = "";
    		if (request.getParameter("user_id") != null) {
    			user_id = request.getParameter("user_id");
    		}

    		String employment_date = "";
    		if (request.getParameter("employment_date") != null) {
    			employment_date = request.getParameter("employment_date");
    		}

    		List<EmplMstDto> infoList = new ArrayList<EmplMstDto>();

    		//データを取得
    		infoList = getData(empl_code, empl_name, mng_grant, user_id, employment_date);

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
    public List<EmplMstDto> getData(String empl_code, String empl_name, String mng_grant, String user_id,
    		String employment_date) {

    	Connection con = null;
	    Statement stm = null;
	    ResultSet rs = null;

		System.out.println("emplCode:"+ empl_code);
		System.out.println("empl_name:"+ empl_name);
		System.out.println("mng_grant:"+ mng_grant);
		System.out.println("user_id:"+ user_id);
		System.out.println("employment_date:"+ employment_date);

        List<EmplMstDto> infoList = new ArrayList<EmplMstDto>();

        String selectSql ="SELECT * "
							+ "FROM doc.EmplMst "
							+ " WHERE del_flg = '0' AND (retire_date = '' OR retire_date >= '" + employment_date + "') "
							+ selectWhereCondition(empl_code, empl_name, mng_grant, user_id);

        con = getConn();

        try {
            stm = con.createStatement();
    		logger.info(selectSql);
            rs = stm.executeQuery(selectSql);
			while (rs.next()) {
				EmplMstDto info = new EmplMstDto();
			    info.setEmpl_code(rs.getString("empl_code"));
			    info.setEmpl_name(rs.getString("empl_name"));
			    info.setMng_grant(rs.getString("mng_grant"));
			    info.setBirth_date(rs.getString("birth_date"));
			    info.setMail_addr1(rs.getString("mail_addr1"));
			    info.setMail_addr2(rs.getString("mail_addr2"));
			    info.setMail_addr3(rs.getString("mail_addr3"));
			    info.setSend_mail_no(rs.getString("send_mail_no"));
			    info.setUser_id(rs.getString("user_id"));
			    info.setEmpl_date(rs.getString("empl_date"));
			    info.setRetire_date(rs.getString("retire_date"));
			    info.setDel_flg(rs.getString("del_flg"));
			    info.setInsert_dateTime(rs.getString("insert_dateTime"));
			    info.setInsert_userId(rs.getString("insert_userId"));
			    info.setInsert_programId(rs.getString("insert_programId"));
			    info.setUpdate_dateTime(rs.getString("update_dateTime"));
			    info.setUpdate_userId(rs.getString("update_userId"));
			    info.setUpdate_programId(rs.getString("update_programId"));
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
    public String josnWrite(List<EmplMstDto> infoList) {
    	String ret="";
    	boolean flg = false;
    	for (EmplMstDto info:infoList) {
    		if (!flg) {
        		ret = String.format("{\"empl_code\":\"%s\",\"empl_name\":\"%s\","
        				+ "\"mng_grant\":\"%s\",\"birth_date\":\"%s\","
        				+ "\"mail_addr1\":\"%s\",\"mail_addr2\":\"%s\","
        				+ "\"mail_addr3\":\"%s\",\"send_mail_no\":\"%s\","
        				+ "\"user_id\":\"%s\",\"empl_date\":\"%s\","
        				+ "\"retire_date\":\"%s\",\"del_flg\":\"%s\","
        				+ "\"insert_dateTime\":\"%s\","
        				+ "\"insert_userId\":\"%s\",\"insert_programId\":\"%s\","
        				+ "\"update_dateTime\":\"%s\",\"update_userId\":\"%s\","
        				+ "\"update_programId\":\"%s\"}",
        				 info.getEmpl_code(), info.getEmpl_name(), info.getMng_grant(), info.getBirth_date(),
        				 info.getMail_addr1(), info.getMail_addr2(), info.getMail_addr3(), info.getSend_mail_no(),
        				 info.getUser_id(), info.getEmpl_date(), info.getRetire_date(), info.getDel_flg(),
        				 info.getInsert_dateTime(), info.getInsert_userId(), info.getInsert_programId(),
        				 info.getUpdate_dateTime(), info.getUpdate_userId(), info.getUpdate_programId());
        		flg = true;
    		} else {
        		ret = ret + String.format(",{\"empl_code\":\"%s\",\"empl_name\":\"%s\","
        				+ "\"mng_grant\":\"%s\",\"birth_date\":\"%s\","
        				+ "\"mail_addr1\":\"%s\",\"mail_addr2\":\"%s\","
        				+ "\"mail_addr3\":\"%s\",\"send_mail_no\":\"%s\","
        				+ "\"user_id\":\"%s\",\"empl_date\":\"%s\","
        				+ "\"retire_date\":\"%s\",\"del_flg\":\"%s\","
        				+ "\"insert_dateTime\":\"%s\","
        				+ "\"insert_userId\":\"%s\",\"insert_programId\":\"%s\","
        				+ "\"update_dateTime\":\"%s\",\"update_userId\":\"%s\","
        				+ "\"update_programId\":\"%s\"}",
        				 info.getEmpl_code(), info.getEmpl_name(), info.getMng_grant(), info.getBirth_date(),
        				 info.getMail_addr1(), info.getMail_addr2(), info.getMail_addr3(), info.getSend_mail_no(),
        				 info.getUser_id(), info.getEmpl_date(), info.getRetire_date(), info.getDel_flg(),
        				 info.getInsert_dateTime(), info.getInsert_userId(), info.getInsert_programId(),
        				 info.getUpdate_dateTime(), info.getUpdate_userId(), info.getUpdate_programId());
    		}
    	}

    	String ret2 ="[" + ret + "]";
    	return ret2;
    }

	/*
	 * SELECT文のWHERE句を動的に文字列を生成
	 */
	private String selectWhereCondition(String empl_code, String empl_name, String mng_grant, String user_id) {

		String Condition = "AND ";
		String Connma = "";

		if (!"".equals(empl_code)) {
			Condition += Connma + "empl_code = '" + empl_code + "'";
			Connma = " AND ";
		} else {
			return "";
		}

		if (!"".equals(empl_name)) {
			Condition += Connma + "empl_name = '" + empl_name + "'";
			Connma = " AND ";
		}

		if (!"".equals(mng_grant)) {
			Condition += Connma + "mng_grant = '" + mng_grant + "'";
			Connma = " AND ";
		}

		if (!"".equals(user_id)) {
			Condition += Connma +"user_id = '" + user_id + "'";
		}

		return Condition;
	}

}
