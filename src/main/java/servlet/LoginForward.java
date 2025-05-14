package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import base.BaseServlet;

/**
 * Servlet implementation class LoginForward
 * ログイン認証クラス
 * @author D.Ikeda
 *  Date : 2021/05/02
 * @version 1.0.0
 */
public class LoginForward extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginForward() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    if (postParamSearchCheck(request)) {

	    	if(request.getParameter("pram1") != null) {

	    		String emplCode = request.getParameter("pram1");
	    		String emplName = request.getParameter("pram2");
	    		String mngGrant = request.getParameter("pram3");
	    		String userId = request.getParameter("pram4");

	    		HttpSession session = request.getSession(false);
	    		if (session == null){
	    			logger.info("セッションは存在しません。開始します");
	    		    session = request.getSession(true);
	    		    logger.info("sessionId=" + session.getId());

	    		//    if (session.getAttribute("empl_code") == null) {
	    		    	session.setAttribute("empl_code", emplCode);
	    		    	session.setAttribute("empl_name", emplName);
	    		    	session.setAttribute("mng_grant", mngGrant);
	    		    	session.setAttribute("user_id", userId);
	    		 //   }

	    		}else{
		    		logger.info("セッション開始してます");
	    		    logger.info("sessionId=" + session.getId());

	    		 //   if (session.getAttribute("empl_code") == null) {
	    		    	session.setAttribute("empl_code", emplCode);
	    		    	session.setAttribute("empl_name", emplName);
	    		    	session.setAttribute("mng_grant", mngGrant);
	    		    	session.setAttribute("user_id", userId);
	    		  //  }
	    		}

	    		//JSON形式変換
	    		String JsonStr = "";
	    		JsonStr = josnWrite("menu","menu.html");

	    		//レスポンスを出力
	    		postOut(JsonStr, response);

	    	}
	    }

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
