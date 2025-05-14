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

import struct.UserInfo;
import base.BaseServlet;

/**
 * Servlet implementation class GetUserIdSelectBoxData
 * ユーザーのセレクトボックスの値取得クラス
 * @author D.Ikeda
 *  Date : 2021/04/29
 * @version 1.0.0
 */
public class GetUserIdSelectBoxData extends BaseServlet {
	private static final long serialVersionUID = 1L;

 	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inTx1   = "";

		//request.setCharacterEncoding("Shift_JIS");

	    if (postParamSearchCheck(request)) {

	    	if(request.getParameter("inTx1") != null) {

	    		inTx1 = request.getParameter("inTx1");
	    		System.out.println(inTx1);

	    		List<UserInfo> infoList = new ArrayList<UserInfo>();

	    		//データを取得
	    		infoList = getData();

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
    public List<UserInfo> getData() {

    	Connection con = null;
	    Statement stm = null;
	    ResultSet rs = null;

        List<UserInfo> infoList = new ArrayList<UserInfo>();

        String selectSql = "SELECT A.empl_code,A.empl_name,A.mng_grant,A.user_id ";
        	selectSql+= "FROM doc.EmplMst A,doc.UserMst B WHERE A.user_id = B.user_id";

        con = getConn();

        try {
            stm = con.createStatement();
            logger.info(selectSql);
            rs = stm.executeQuery(selectSql);
			while (rs.next()) {
				UserInfo info = new UserInfo();
			    info.setEmpl_code(rs.getString("empl_code"));
			    info.setEmpl_name(rs.getString("empl_name"));
			    info.setMng_grant(rs.getString("mng_grant"));
			    info.setUser_id(rs.getString("user_id"));

			    infoList.add(info);
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}

        try {
            if ((rs != null)||(stm != null)||(con != null)) {
				rs.close();
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}

        return infoList;
    }

	/** JSON文字列を返す */
    //@Override
    public String josnWrite(List<UserInfo> infoList) {
    	String ret = "";
    	boolean flg = false;
    	for (UserInfo info:infoList) {
    		if (!flg) {
        		ret = String.format("{\"empl_code\":\"%s\",\"empl_name\":\"%s\",\"mng_grant\":\"%s\",\"user_id\":\"%s\"}",
        				info.getEmpl_code(), info.getEmpl_name(), info.getMng_grant(), info.getUser_id());
        		flg = true;
    		} else {
        		ret = ret + String.format(",{\"empl_code\":\"%s\",\"empl_name\":\"%s\",\"mng_grant\":\"%s\",\"user_id\":\"%s\"}",
           				info.getEmpl_code(), info.getEmpl_name(), info.getMng_grant(), info.getUser_id());
        	}
    	}

    	String ret2 ="[" + ret + "]";
    	return ret2;
    }

}
