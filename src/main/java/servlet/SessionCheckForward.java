package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import base.BaseServlet;

/**
 * Servlet implementation class SessionCheckForward
 * セッション認証クラス
 * @author D.Ikeda
 *  Date : 2021/04/30
 * @version 1.0.0
 */
public class SessionCheckForward extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionCheckForward() {
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
	/* (非 Javadoc)
	 * @see base.BaseServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    if (postParamSearchCheck(request)) {

	    	if(request.getParameter("pram1") != null) {

	    		String strMode = request.getParameter("pram1");
	    		String sessionId = "";
	    		String strHtml = "";
	    		String emplCode = "";
	    		String emplName = "";
	    		String mngGrant = "";
	    		String userId = "";

	    		HttpSession session = request.getSession(false);
	    		if (session == null) {
	    		//認証完了していない場合
	    			if ("0".equals(strMode)) {
	    			//テストモードの場合
		    			logger.info("セッションは存在しません。ログイン画面へ遷移します");
			    		strHtml = "login/login.html";
	    			} else {
		    			logger.info("セッションは存在しません。被保険者証情報入力へ遷移します");
			    		strHtml = "doc0016/doc0016.html";
	    			}
	    		} else {
	    			logger.info("sessionId=" + session.getId());

	    			logger.info("empl_code=" + session.getAttribute("empl_code"));
	    			logger.info("empl_name=" + session.getAttribute("empl_name"));
	    			logger.info("mng_grant=" + session.getAttribute("mng_grant"));
	    			logger.info("user_id=" + session.getAttribute("user_id"));

	    			if (session.getAttribute("empl_code") == null) {
		    			//認証完了していない場合
		    			if ("0".equals(strMode)) {
			    		//テストモードの場合
				    		logger.info("セッションは存在しません。ログイン画面へ遷移します");
				    		strHtml = "login/login.html";
			    		} else {
				    		logger.info("セッションは存在しません。被保険者証情報入力へ遷移します");
				    		strHtml = "doc0016/doc0016.html";
			    		}

	    			} else {
		    			//認証完了している場合
	    				emplCode = session.getAttribute("empl_code").toString();
	    				if (session.getAttribute("empl_name") == null) {
			    		//認証完了していない場合
			    			if ("0".equals(strMode)) {
				    		//テストモードの場合
					    		logger.info("セッションは存在しません。ログイン画面へ遷移します");
					    		strHtml = "login/login.html";
				    		} else {
					    		logger.info("セッションは存在しません。被保険者証情報入力へ遷移します");
					    		strHtml = "doc0016/doc0016.html";
				    		}

		    			} else {

		    				emplName = session.getAttribute("empl_name").toString();
		    				if (session.getAttribute("mng_grant") == null) {
					    		//認証完了していない場合
					    		if ("0".equals(strMode)) {
						    	//テストモードの場合
							    	logger.info("セッションは存在しません。ログイン画面へ遷移します");
							    	strHtml = "login/login.html";
						    	} else {
							    	logger.info("セッションは存在しません。被保険者証情報入力へ遷移します");
							    	strHtml = "doc0016/doc0016.html";
						    	}

		    				} else {
			    				mngGrant = session.getAttribute("mng_grant").toString();
			    				if (session.getAttribute("user_id") == null) {
					    		//認証完了していない場合
					    			if ("0".equals(strMode)) {
						    		//テストモードの場合
							    		logger.info("セッションは存在しません。ログイン画面へ遷移します");
							    		strHtml = "login/login.html";
						    		} else {
							    		logger.info("セッションは存在しません。被保険者証情報入力へ遷移します");
							    		strHtml = "doc0016/doc0016.html";
						    		}

				    			} else {
				    				userId = session.getAttribute("user_id").toString();
				    				logger.info("セッション開始してます");
				    				sessionId = session.getId();
				    				logger.info("sessionId=" + sessionId);
				    			}
		    				}
		    			}
	    			}
	    		}

	    		//JSON形式変換
	    		String JsonStr = "";
	    		JsonStr = josnWrite(sessionId, strHtml, emplCode, emplName, mngGrant, userId);

	    		//レスポンスを出力
	    		postOut(JsonStr, response);

	    	}
	    }

	}

	/** JSON文字列を返す */
    //@Override
    public String josnWrite(String sessionId, String strHtml, String emplCode, String emplName,
    		String mngGrant,String userId) {
    	String ret = "";

    	ret = String.format("{\"sessionId\":\"%s\",\"strHtml\":\"%s\",\"emplCode\":\"%s\","
    			+ "\"emplName\":\"%s\",\"mngGrant\":\"%s\",\"userId\":\"%s\"}",sessionId,strHtml,emplCode,
    			emplName,mngGrant,userId);

    	String ret2 ="[" + ret + "]";
    	return ret2;
    }


}
