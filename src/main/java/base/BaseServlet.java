package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * BaseServlet
 * Servlet基底クラス
 *
 * @author D.Ikeda
 *    Date:2021/05/03
 * @version 1.0.0
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    Connection conn = null;

    // log4j
    public static Logger logger = Logger.getLogger(BaseServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseServlet() {
        super();

        // log4j設定ファイルを読み込む
	    DOMConfigurator.configure("../workspace/ibiDoc/log4j.xml");

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
	}

	/**
	 * リクエストのパラメータにACTIONのsearchがあるかチェック
	 */
	protected boolean postParamSearchCheck(HttpServletRequest request) throws ServletException, IOException {
		boolean ret = false;
		String action = request.getParameter("ACTION");
		if (action == null) {
			 ret = false;
		} else {
			if (action.equals("search")) {
				 ret = true;
			} else{
				 ret = false;
			}
		}
		return ret;
	}

	/**
	 * リクエストのパラメータにACTIONのinsertがあるかチェック
	 */
	protected boolean postParamInsertCheck(HttpServletRequest request) throws ServletException, IOException {
		boolean ret = false;
		String action = request.getParameter("ACTION");
		if (action == null) {
			 ret = false;
		} else {
			if (action.equals("insert")) {
				 ret = true;
			} else{
				 ret = false;
			}
		}
		return ret;
	}

	/**
	 * リクエストのパラメータにACTIONのupdateがあるかチェック
	 */
	protected boolean postParamUpdateCheck(HttpServletRequest request) throws ServletException, IOException {
		boolean ret = false;
		String action = request.getParameter("ACTION");
		if (action == null) {
			 ret = false;
		} else {
			if (action.equals("update")) {
				 ret = true;
			} else{
				 ret = false;
			}
		}
		return ret;
	}

	/**
	 * リクエストのパラメータにACTIONのdeleteがあるかチェック
	 */
	protected boolean postParamDeleteCheck(HttpServletRequest request) throws ServletException, IOException {
		boolean ret = false;
		String action = request.getParameter("ACTION");
		if (action == null) {
			 ret = false;
		} else {
			if (action.equals("delete")) {
				 ret = true;
			} else{
				 ret = false;
			}
		}
		return ret;
	}

	/**
	 * リクエストのパラメータにACTIONのuploadがあるかチェック
	 */
	protected boolean postParamUploadCheck(HttpServletRequest request) throws ServletException, IOException {
		boolean ret = false;
		String action = request.getParameter("ACTION");
		if (action == null) {
			 ret = false;
		} else {
			if (action.equals("upload")) {
				 ret = true;
			} else{
				 ret = false;
			}
		}
		return ret;
	}

	/**
	 * リクエストのパラメータにACTIONのdownloadがあるかチェック
	 */
	protected boolean postParamDownloadCheck(HttpServletRequest request) throws ServletException, IOException {
		boolean ret = false;
		String action = request.getParameter("ACTION");
		if (action == null) {
			 ret = false;
		} else {
			if (action.equals("download")) {
				 ret = true;
			} else{
				 ret = false;
			}
		}
		return ret;
	}

	/**
	 * JSONデータのレスポンスを返す
	 */
	protected void postOut(String JsonStr, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(JsonStr);

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		//response.setCharacterEncoding("Shift_JIS");
		out.print(JsonStr);
		out.flush();

	}

	/**
	 * ファイルアップロード
	 */
	@SuppressWarnings("rawtypes")
	protected boolean postUpload(HttpServletRequest request, String addPath) throws ServletException, IOException {

		//(1)アップロードファイルを格納するPATHを取得
		String path = getProperties("uploadpath") + addPath;

        //(2)ServletFileUploadオブジェクトを生成
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        //(3)アップロードする際の基準値を設定
        factory.setSizeThreshold(1024);
        upload.setSizeMax(-1);
        upload.setHeaderEncoding("UTF-8");

        try {
            //(4)ファイルデータ(FileItemオブジェクト)を取得し、
            //   Listオブジェクトとして返す
             List list = upload.parseRequest(request);

            //(5)ファイルデータ(FileItemオブジェクト)を順に処理
            Iterator iterator = list.iterator();
            while(iterator.hasNext()){
                FileItem fItem = (FileItem)iterator.next();

                //(6)ファイルデータの場合、if内を実行
                if(!(fItem.isFormField())){
                    //(7)ファイルデータのファイル名(PATH名含む)を取得
                    String fileName = fItem.getName();
                    if((fileName != null) && (!fileName.equals(""))){
                        //(8)PATH名を除くファイル名のみを取得
                        fileName=(new File(fileName)).getName();
                        //(9)ファイルデータを指定されたファイルに書き出し
                        fItem.write(new File(path + "/" + fileName));
                    }
                }
            }
        }catch (FileUploadException e) {
            e.printStackTrace();
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
	}

	/**
	 * ファイルダウンロード
	 */
	protected boolean getDownload(HttpServletResponse response, String addPath) throws ServletException, IOException {

		//(1)アップロードファイルを格納するPATHを取得
		String path = getProperties("uploadpath") + addPath;

		// ダウンロード対象ファイルの読み込み用オブジェクト
		FileInputStream fis = null;
		InputStreamReader isr = null;

		// ダウンロードファイルの書き出し用オブジェクト
		OutputStream os = null;
		OutputStreamWriter osw = null;

		try {
			// ダウンロード対象ファイルのFileオブジェクトを生成
			File file = new File(path);

			if (!file.exists() || !file.isFile()) {
				// ファイルが存在しない場合のエラー処理
			}

			// レスポンスオブジェクトのヘッダー情報を設定
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" +
				new String("ダイアログに表示するファイル名".getBytes("Windows-31J"), "ISO-8859-1"));

			// ダウンロード対象ファイルの読み込み用オブジェクトを生成
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "ISO-8859-1");

			// ダウンロードファイルの書き出し用オブジェクトを生成
			os = response.getOutputStream();
			osw = new OutputStreamWriter(os, "ISO-8859-1");

			// IOストリームを用いてファイルダウンロードを行う
			int i;
			while ((i = isr.read()) != -1) {
				osw.write(i);
			}
		} catch (FileNotFoundException e) {
			// 例外発生時処理
			return false;
		} catch (UnsupportedEncodingException e) {
			// 例外発生時処理
			return false;
		} catch (IOException e) {
			// 例外発生時処理
			return false;
		} finally {
			try {
				// 各オブジェクトを忘れずにクローズ
				if (osw != null) {
					osw.close();
				}
				if (os != null) {
					os.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				// 例外発生時処理
			}
		}

		return true;
	}

	/**
	 * Base64デコードしたファイル名を取得
	 */
	protected String getDecodedFileName(String fileName) throws ServletException, IOException {

		//空白文字を＋に変換し最後に改行コードを付加しデコード対象文字を生成する
		String target = "";
		String[] fruits = fileName.split(" ");

        for (int i=0;i< fruits.length; i++) {
        	if (i != (fruits.length-1)) {
            	target += fruits[i] + "+";

        	} else {
        		target += fruits[i];
        	}
        }
		target += "\n";

		byte[] decodedBytes = Base64.decodeBase64(target.getBytes());
		String decodedFileName = new String(decodedBytes, "UTF-8");

		return decodedFileName;
	}

	/**
	 * propertiesの値を取得
	 */
	protected String getProperties(String key) throws ServletException, IOException {

		String ret = "";
		Properties properties = new Properties();

        //プロパティファイルのパスを指定する
		String strpass = this.getServletContext().getRealPath("/WEB-INF/ibidoc.properties");

        try {
            InputStream istream = new FileInputStream(strpass);
            properties.load(istream);
            ret = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

		return ret;

	}

	/**
	 * メッセージpropertiesの値を取得
	 */
	protected LinkedHashMap<String, Object> getMessageProperties() throws ServletException, IOException {

		Properties properties = new Properties();

		//メッセージのキーと内容の組み合わせを格納するためのマップ
		LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();

        //プロパティファイルのパスを指定する
		String strpass = this.getServletContext().getRealPath("/WEB-INF/message_ja.properties");

        try {
            InputStream istream = new FileInputStream(strpass);
            properties.load(istream);
            //キーの一覧を取得
    		Enumeration<Object> keys = properties.keys();

    		//全てのキーを対象にキーと内容をマップに格納する
    		while (keys.hasMoreElements()) {

    			//キーを取得する
    			String key = (String) keys.nextElement();

    			//キーに対応する内容を取得する
    			String value = properties.getProperty(key);

    			resultMap.put(key, value);
    		}

        } catch (IOException e) {
            e.printStackTrace();
        }

		return resultMap;

	}

	/**
	 * DB接続
	 */
	public Connection getConn() {

		String gURL = "";
		try {
			gURL = getProperties("url");
		} catch (ServletException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

	    //接続文字列
        String url = "jdbc:postgresql://" + gURL;
        String user = "";
		String password = "";

		try {
			user = getProperties("user");
			password = getProperties("pass");
		} catch (ServletException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	    try{
			try {
				Class.forName("org.postgresql.Driver").newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

	        //PostgreSQLへ接続
	        conn = DriverManager.getConnection(url, user, password);

	        //自動コミットOFF
	        conn.setAutoCommit(false);

	    }catch (SQLException e){
		        e.printStackTrace();
		}
		finally {

		}
		return conn;

	}

}
