package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;

/**
 * Servlet implementation class DownloadFileServlet
 * ファイルダウンロード用サーブレット
 * @author D.Ikeda
 *    Date:2021/04/24
 * @version 1.0.0
 */
public class DownloadFileServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFileServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (postParamDownloadCheck(request)) {

			String ym = request.getParameter("YM");
			if (ym == null) {
				 return;
			}

			String enc = request.getParameter("EN");
			if (enc == null) {
				 return;
			}

			String id = request.getParameter("ID");
			if (id == null) {
				 return;
			}

			String dt = request.getParameter("DT");
			if (dt == null) {
				 return;
			}

			String fileName = request.getParameter("FN");
			if (fileName == null) {
				 return;
			}

			//空白文字を＋に変換し最後に改行コードを付加しデコード対象文字を生成する
			String decodedFileName = getDecodedFileName(fileName);

			//(1)アップロードファイルを格納するPATHを取得
			String addPath = ym + "\\" + enc + "\\" + id + "\\" + dt;
			addPath += "\\" + decodedFileName;

			getDownload(response, addPath);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
