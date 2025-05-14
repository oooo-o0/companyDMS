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

import struct.NameMstInfo;
import base.BaseServlet;

/**
 * Servlet implementation class GetNameMstData
 */
public class GetNameMstData extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNameMstData() {
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
	 * @param  mngGrant 0:一般社員、1:管理者
	 *
	 *
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mngGrant   = "";

	    if (postParamSearchCheck(request)) {

	    	if(request.getParameter("mngGrant") != null) {

	    		mngGrant = request.getParameter("mngGrant");
	    		System.out.println(mngGrant);

	    		List<NameMstInfo> infoList = new ArrayList<NameMstInfo>();

	    		//データを取得
	    		infoList = getData(mngGrant);

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
	 * @param  mngGrant 0:一般社員、1:管理者
	 */
    public List<NameMstInfo> getData(String mngGrant) {

    	Connection con = null;
	    Statement stm = null;
	    ResultSet rs = null;

        List<NameMstInfo> infoList = new ArrayList<NameMstInfo>();

        String selectSql = "select name_type,send_recive_branch,name_code,name_value from doc.NameMst ";
        	selectSql+= "where disp_rest in ('','" + mngGrant + "','2') and del_flg ='0'";
        	selectSql+= "order by send_recive_branch,name_type,name_code";

        con = getConn();

        try {
            stm = con.createStatement();
            logger.info(selectSql);
            rs = stm.executeQuery(selectSql);
			while (rs.next()) {
				NameMstInfo info = new NameMstInfo();
			    info.setName_type(rs.getString("name_type"));
			    info.setSend_recive_branch(rs.getString("send_recive_branch"));
			    info.setName_code(rs.getString("name_code"));
			    info.setName_value(rs.getString("name_value"));

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
    public String josnWrite(List<NameMstInfo> infoList) {
    	String ret = "";
    	boolean flg = false;
    	for (NameMstInfo info:infoList) {
    		if (!flg) {
        		ret = String.format("{\"name_type\":\"%s\",\"send_recive_branch\":\"%s\",\"name_code\":\"%s\",\"name_value\":\"%s\"}",
        				info.getName_type(), info.getSend_recive_branch(), info.getName_code(), info.getName_value());
        		flg = true;
    		} else {
        		ret = ret + String.format(",{\"name_type\":\"%s\",\"send_recive_branch\":\"%s\",\"name_code\":\"%s\",\"name_value\":\"%s\"}",
           				info.getName_type(), info.getSend_recive_branch(), info.getName_code(), info.getName_value());
        	}
    	}

    	String ret2 ="[" + ret + "]";
    	return ret2;
    }

}
