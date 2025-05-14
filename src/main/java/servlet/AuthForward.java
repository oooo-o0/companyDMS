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
import javax.servlet.http.HttpSession;

import struct.EmplMstInfo;
import base.BaseServlet;

/**
 * Servlet implementation class AuthForward
 *  * 保険書情報での認証クラス
 * @author D.Ikeda
 *    Date:2021/05/02
 * @version 1.0.0

 */
public class AuthForward extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthForward() {
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

	    	logger.info("pram1=" + request.getParameter("pram1"));

	    	if(request.getParameter("pram1") != null) {

	    		String kigo = request.getParameter("pram1");
	    		String no = request.getParameter("pram2");
	    		String healthNo = request.getParameter("pram3");
	    		String grantDate = request.getParameter("pram4");
	    		grantDate = grantDate.replace("-", "");

	    		List<EmplMstInfo> infoList = new ArrayList<EmplMstInfo>();

	    		//データを取得
	    		infoList = getData(kigo,no,healthNo,grantDate);

	    		if (infoList.size()==0) {
		    	//ユーザーのデータが存在していない場合
		    		//JSON形式変換
		    		String JsonStr = "";
		    		JsonStr = josnWrite("err","err.html");
		    		//レスポンスを出力
		    		postOut(JsonStr, response);

	    		} else {
	    		//ユーザーのデータが存在していた場合
		    		HttpSession session = request.getSession(false);
		    		if (session == null){
		    			logger.info("セッションは存在しません。開始します");
		    		    session = request.getSession(true);
		    		    logger.info("sessionId=" + session.getId());

	    		    	session.setAttribute("empl_code", infoList.get(0).getEmpl_code());
	    		    	session.setAttribute("empl_name", infoList.get(0).getEmpl_name());
	    		    	session.setAttribute("mng_grant", infoList.get(0).getMng_grant());
	    		    	session.setAttribute("user_id", infoList.get(0).getUser_id());

		    		}else{
			    		logger.info("セッション開始してます");
		    		    logger.info("sessionId=" + session.getId());

	    		    	session.setAttribute("empl_code", infoList.get(0).getEmpl_code());
	    		    	session.setAttribute("empl_name", infoList.get(0).getEmpl_name());
	    		    	session.setAttribute("mng_grant", infoList.get(0).getMng_grant());
	    		    	session.setAttribute("user_id", infoList.get(0).getUser_id());
		    		}

		    		//JSON形式変換
		    		String JsonStr = "";

		    		if ("1".equals(infoList.get(0).getMng_grant())) {
		    		//管理者の場合は社内送信BOX画面（管理者用）へ遷移
			    		JsonStr = josnWrite("doc0001","doc0001.html");
		    		} else {
			    	//一般社員の場合は社内受信BOX画面（一般社員用）へ遷移
		    			JsonStr = josnWrite("doc0003","doc0003.html");
		    		}

		    		//レスポンスを出力
		    		postOut(JsonStr, response);
	    		}
	    	}
	    }

	}

	/**
	 * SQLを実行しデータを取得
	 */
    public List<EmplMstInfo> getData(String kigo, String no, String healthNo, String grantDate) {

    	Connection con = null;
	    Statement stm = null;
	    ResultSet rs = null;

        List<EmplMstInfo> infoList = new ArrayList<EmplMstInfo>();

        String selectSql = "SELECT A.empl_code, A.empl_name, A.mng_grant, A.birth_date, A.mail_addr1, A.mail_addr2, A.mail_addr3,"
        + "A.send_mail_no, A.user_id, A.empl_date, A.retire_date, A.del_flg, A.insert_dateTime, A.insert_userId,"
        + "A.insert_programId, A.update_dateTime, A.update_userId, A.update_programId"
        + "	FROM doc.EmplMst A, doc.HealthCardInfo B "
        +" WHERE A.empl_code = B.empl_code AND B.kigo='" + kigo +"' AND B.no='" + no + "' AND B.health_no ='" + healthNo
        + "' AND B.grant_date = '" + grantDate + "' AND A.del_flg='0' AND B.del_flg = '0' ";

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
    public String josnWrite(String strFolder,String strHtml) {
    	String ret = "";

    	ret = String.format("{\"strFolder\":\"%s\",\"strHtml\":\"%s\"}",strFolder,strHtml);

    	String ret2 ="[" + ret + "]";
    	return ret2;
    }


}
