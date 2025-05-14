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

import struct.EmplMstInfo;
import base.BaseServlet;

/**
 * EmplMstの情報を取得するサーブレット
 * @author D.Ikeda
 *    Date:2021/05/09
 * @version 1.0.0
 */
public class GetEmplMstInfo extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEmplMstInfo() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String emplCode   = "";

	    if (postParamSearchCheck(request)) {

	    	if(request.getParameter("pram1") != null) {

	    		emplCode = request.getParameter("pram1");
	    		System.out.println(emplCode);

	    		List<EmplMstInfo> infoList = new ArrayList<EmplMstInfo>();

	    		//データを取得
	    		infoList = getData(emplCode);

	    		//JSON形式変換
	    		String JsonStr = "";
	    		JsonStr = josnWrite(infoList);

	    		//レスポンスを出力
	    		postOut(JsonStr, response);

	    	}
	    }

	}

	/**
	 * SQLを実行しデータを取得
	 */
    public List<EmplMstInfo> getData(String emplCode) {

    	Connection con = null;
	    Statement stm = null;
	    ResultSet rs = null;

        List<EmplMstInfo> infoList = new ArrayList<EmplMstInfo>();

        String selectSql = "SELECT empl_code, empl_name, mng_grant, birth_date, mail_addr1, mail_addr2, mail_addr3,"
        + "send_mail_no, user_id, empl_date, retire_date, del_flg, insert_dateTime, insert_userId,"
        + "insert_programId, update_dateTime, update_userId, update_programId"
        + "	FROM doc.EmplMst"
        +" WHERE empl_code ='" + emplCode +"'";

        con = getConn();

        try {
            stm = con.createStatement();
    		logger.info(selectSql);
            rs = stm.executeQuery(selectSql);
			while (rs.next()) {
				EmplMstInfo info = new EmplMstInfo();
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
    public String josnWrite(List<EmplMstInfo> infoList) {
    	String ret="";
    	boolean flg = false;
    	for (EmplMstInfo info:infoList) {
    		if (!flg) {
        		ret = String.format("{\"empl_code\":\"%s\",\"empl_name\":\"%s\","
        				+ "\"mng_grant\":\"%s\",\"birth_date\":\"%s\","
        				+ "\"mail_addr1\":\"%s\",\"mail_addr2\":\"%s\","
        				+ "\"mail_addr3\":\"%s\",\"send_mail_no\":\"%s\","
        				+ "\"user_id\":\"%s\",\"empl_date\":\"%s\","
        				+ "\"retire_date\":\"%s\",\"del_flg\":\"%s\","
        				+ "\"insert_dateTime\":\"%s\",\"insert_userId\":\"%s\","
        				+ "\"insert_programId\":\"%s\",\"update_dateTime\":\"%s\","
        				+ "\"update_userId\":\"%s\",\"update_programId\":\"%s\"}", info.getEmpl_code(),
        				 info.getEmpl_name(), info.getMng_grant(), info.getBirth_date(), info.getMail_addr1(),
        				 info.getMail_addr2(), info.getMail_addr3(), info.getSend_mail_no(), info.getUser_id(),
        				 info.getEmpl_date(), info.getRetire_date(), info.getDel_flg(), info.getInsert_dateTime(),
        				 info.getInsert_userId(), info.getInsert_programId(), info.getUpdate_dateTime(),
        				 info.getUpdate_userId(), info.getUpdate_programId());
        		flg = true;
    		} else {
        		ret = ret + String.format(",{\"empl_code\":\"%s\",\"empl_name\":\"%s\","
        				+ "\"mng_grant\":\"%s\",\"birth_date\":\"%s\","
        				+ "\"mail_addr1\":\"%s\",\"mail_addr2\":\"%s\","
        				+ "\"mail_addr3\":\"%s\",\"send_mail_no\":\"%s\","
        				+ "\"user_id\":\"%s\",\"empl_date\":\"%s\","
        				+ "\"retire_date\":\"%s\",\"del_flg\":\"%s\","
        				+ "\"insert_dateTime\":\"%s\",\"insert_userId\":\"%s\","
        				+ "\"insert_programId\":\"%s\",\"update_dateTime\":\"%s\","
        				+ "\"update_userId\":\"%s\",\"update_programId\":\"%s\"}", info.getEmpl_code(),
        				 info.getEmpl_name(), info.getMng_grant(), info.getBirth_date(), info.getMail_addr1(),
        				 info.getMail_addr2(), info.getMail_addr3(), info.getSend_mail_no(), info.getUser_id(),
        				 info.getEmpl_date(), info.getRetire_date(), info.getDel_flg(), info.getInsert_dateTime(),
        				 info.getInsert_userId(), info.getInsert_programId(), info.getUpdate_dateTime(),
        				 info.getUpdate_userId(), info.getUpdate_programId());
    		}
    	}

    	String ret2 ="[" + ret + "]";
    	return ret2;
    }


}
