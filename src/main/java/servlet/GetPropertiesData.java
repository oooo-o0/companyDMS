package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;

/**
 * Servlet implementation class GetPropertiesData
 * システムのPropertiesの値を取得するクラス
 * @author D.Ikeda
 *  Date : 2021/04/30
 * @version 1.0.0
 */
public class GetPropertiesData extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPropertiesData() {
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

		String sectionKey   = "";

	    if (postParamSearchCheck(request)) {

	    	if(request.getParameter("inTx1") != null) {

	    		sectionKey = request.getParameter("inTx1");

	    		//値を取得
	    		String sectionVal = getProperties(sectionKey);

	    		//JSON形式変換
	    		String JsonStr = "";
	    		JsonStr = josnWrite(sectionVal);

	    		//レスポンスを出力
	    		postOut(JsonStr, response);

	    	}
	    }

	}

	/** JSON文字列を返す */
    //@Override
    public String josnWrite(String sectionVal) {
    	String ret = "";
    	ret = String.format("{\"properties_val\":\"%s\"}", sectionVal);
    	String ret2 ="[" + ret + "]";
    	return ret2;
    }

}
