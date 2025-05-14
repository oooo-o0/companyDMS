package servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;

/**
 * メッセージpropertiesの値を取得サーブレット
 * @author D.Ikeda
 *    Date:2021/05/03
 * @version 1.0.0
 */
public class GetMessageResource extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inTx1   = "";

	    if (postParamSearchCheck(request)) {

	    	if(request.getParameter("inTx1") != null) {

	    		inTx1 = request.getParameter("inTx1");

	    		LinkedHashMap<String, Object> messageMap = new LinkedHashMap<String, Object>();

	    		//メッセージリソースMapを取得
	    		messageMap = getMessageProperties();

	    		//JSON形式変換
	    		String JsonStr = "";
	    		JsonStr = josnWrite(messageMap);

	    		//レスポンスを出力
	    		postOut(JsonStr, response);

	    	}
	    }
	}

	/** JSON文字列を返す */
    //@Override
    public String josnWrite(LinkedHashMap<String, Object> messageMap) {
    	String ret="";
    	boolean flg = false;

    	for (Map.Entry<String, Object> entry : messageMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
    		if (!flg) {
        		ret = String.format("{\"key\":\"%s\",\"value\":\"%s\"}", entry.getKey(), entry.getValue());
        		flg = true;
    		} else {
        		ret = ret + String.format(",{\"key\":\"%s\",\"value\":\"%s\"}", entry.getKey(), entry.getValue());
    		}
        }

    	String ret2 ="[" + ret + "]";
    	return ret2;
    }


}
