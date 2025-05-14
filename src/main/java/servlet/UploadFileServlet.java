package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;

/**
 * Servlet implementation class UploadFileServlet
 * ファイルアップロードクラス
 * @author D.Ikeda
 *  Date : 2021/04/29
 * @version 1.0.0

 */
public class UploadFileServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFileServlet() {
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

		if (postParamUploadCheck(request)) {

			//年月
			String ym = request.getParameter("YM");
			if (ym == null) {
				 return;
			}

			//エンコード文字
			String enc = request.getParameter("EN");
			if (enc == null) {
				 return;
			}

			//社員コード
			String id = request.getParameter("ID");
			if (id == null) {
				 return;
			}

			//文書分類名
			String dt = request.getParameter("DT");
			if (dt == null) {
				 return;
			}

			//(1)アップロードファイルを格納するPATHを取得
			String childPath = ym + "\\" + enc +"\\" + id +"\\" + dt;

			postUpload(request, childPath);

		}
	}

}
