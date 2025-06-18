package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.BaseServlet;
import dao.DocBodyDao;
import dao.SendReciveDetailTranDao;
import dao.SendReciveTranDao;
import dto.DocBodyDto;
import dto.SendReciveDetailTranDto;
import dto.SendReciveTranDto;
import interfe.CommonTableAccessIF;
import struct.DocBodyKeyInfo;
import struct.SendReciveDetailTranKeyInfo;
import struct.SendReciveTranKeyInfo;

/**
 * Servlet implementation class SelectDocumentInfoServlet
 */
@WebServlet("/SelectDocumentInfoServlet")
public class SelectDocumentInfoServlet extends BaseServlet implements CommonTableAccessIF {
	private static final long serialVersionUID = 1L;
	
	
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectDocumentInfoServlet() {
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
		// TODO Auto-generated method stub
		
		if (postParamSearchCheck(request)) {
	       
	    

	        CommonTableAccessIF sendRDao = new SendReciveTranDao();
	        CommonTableAccessIF sendRDtlDao = new SendReciveDetailTranDao();
	        CommonTableAccessIF docDao = new DocBodyDao();
	        StringBuilder docTitle = new StringBuilder();
	        StringBuilder expirationFrom = new StringBuilder();
	        StringBuilder expirationTo = new StringBuilder();


	        int ret = mainProc(request, sendRDao, sendRDtlDao, docDao, docTitle, expirationFrom, expirationTo);
	        
	      //JSON形式変換
	    	String JsonStr = "";
	    	JsonStr = josnWrite(ret, docTitle.toString(), expirationFrom.toString(), expirationTo.toString());
	        
	      //レスポンスを出力
	        postOut(JsonStr, response);
	    }
	}
	    
		/**
		 * 主処理
		 * パラメータをチェックしキー項目が空でな場合データ取得処理を行う
		 * 戻り値：1:取得完了,0:取得未完了,-1:パラメータチェックエラー,
		 */
	    private int mainProc(HttpServletRequest request,
                CommonTableAccessIF sendRDao,
                CommonTableAccessIF sendRDtlDao,
                CommonTableAccessIF docDao,
                StringBuilder docTitleOut,
                StringBuilder expirationFromOut,
                StringBuilder expirationToOut) {
	    	
	    	int ret = 1;

	    	String send_recive_type = "";
	    	if (request.getParameter("send_recive_type") != null) {
	    	    send_recive_type = request.getParameter("send_recive_type");
	    	}

	    	String year_month = "";
	    	if (request.getParameter("year_month") != null) {
	    	    year_month = request.getParameter("year_month");
	    	}

	    	String empl_code = "";
	    	if (request.getParameter("empl_code") != null) {
	    	    empl_code = request.getParameter("empl_code");
	    	}

	    	String doc_name = "";
	    	if (request.getParameter("doc_name") != null) {
	    	    doc_name = request.getParameter("doc_name");
	    	}

	    	String doc_type = "";
	    	if (request.getParameter("doc_type") != null) {
	    	    doc_type = request.getParameter("doc_type");
	    	}

	    	// 必須項目チェック（キー項目）
	    	if ("".equals(send_recive_type) || "".equals(year_month) || "".equals(empl_code)
	    	        || "".equals(doc_name) || "".equals(doc_type)) {
	    	    return -1;  
	    	}


	    	//更新トランザクション用にDBコネクションを用意
			Connection con = getConn();

	        
	        	
	            // 送受信トラン取得
	            SendReciveTranKeyInfo srKeyParm = new SendReciveTranKeyInfo();
	            srKeyParm.setSend_recive_type(send_recive_type);
	            srKeyParm.setYear_month(year_month);
	            srKeyParm.setEmpl_code(empl_code);
	            sendRDao.setParam(srKeyParm);
	            SendReciveTranDto srDto = (SendReciveTranDto) sendRDao.selectDataByKey(con);

	            // 明細トラン取得
	            SendReciveDetailTranKeyInfo srdKeyParm = new SendReciveDetailTranKeyInfo();
	            srdKeyParm.setSend_recive_type(send_recive_type);
	            srdKeyParm.setYear_month(year_month);
	            srdKeyParm.setEmpl_code(empl_code);
	            srdKeyParm.setDoc_name(doc_name);
	            srdKeyParm.setDoc_type(doc_type);
	            sendRDtlDao.setParam(srdKeyParm);
	            SendReciveDetailTranDto srdDto = (SendReciveDetailTranDto) sendRDtlDao.selectDataByKey(con);

	            // 文書内容取得
	            DocBodyKeyInfo docKeyParm = new DocBodyKeyInfo();
	            docKeyParm.setSend_recive_type(send_recive_type);
	            docKeyParm.setYear_month(year_month);
	            docKeyParm.setEmpl_code(empl_code);
	            docKeyParm.setDoc_name(doc_name);
	            docKeyParm.setDoc_type(doc_type);
	            docDao.setParam(docKeyParm);
	            DocBodyDto docDto = (DocBodyDto) docDao.selectDataByKey(con);
	            
	            try {
	            // 必須データがすべて存在するかチェック
	            if (srDto.getEmpl_code() == null || srdDto.getEmpl_code() == null || docDto.getEmpl_code() == null) {
	                return -1;  // データなし
	            }
	            // 取得結果を引数で渡されたStringBuilderに格納
	            docTitleOut.append(docDto.getDoc_title() != null ? docDto.getDoc_title() : "");
	            expirationFromOut.append(docDto.getExpiration_from_dateTime() != null ? docDto.getExpiration_from_dateTime() : "");
	            expirationToOut.append(docDto.getExpiration_to_dateTime() != null ? docDto.getExpiration_to_dateTime() : "");

	            con.commit();
	            } catch (SQLException e) {
	                if (con != null) try { con.rollback(); } catch (SQLException ignored) {}
	                ret = -1;
	                logger.error("DB処理エラー", e);
	            } finally {
	                if (con != null) try { con.close(); } catch (SQLException ignored) {}
	            }


	         return ret;
	     }
	    

	    private String josnWrite(int retCode, String docTitle, String expirationFrom, String expirationTo) {
	        docTitle = docTitle == null ? "" : docTitle.replace("\"", "\\\"");
	        expirationFrom = expirationFrom == null ? "" : expirationFrom.replace("\"", "\\\"");
	        expirationTo = expirationTo == null ? "" : expirationTo.replace("\"", "\\\"");

	        String retVal = Integer.toString(retCode);
	        String json = String.format(
	            "{\"rtn_code\":\"%s\", \"doc_title\":\"%s\", \"expiration_from_dateTime\":\"%s\", \"expiration_to_dateTime\":\"%s\"}",
	            retVal, docTitle, expirationFrom, expirationTo
	        );
	        return "[" + json + "]";
	    }
	    
	    

		@Override
		public void setParam(Object param) {
			// TODO 自動生成されたメソッド・スタブ
			
		}

		@Override
		public void setValuesParam(Object param) {
			// TODO 自動生成されたメソッド・スタブ
			
		}

		@Override
		public void setSetParam(Object param) {
			// TODO 自動生成されたメソッド・スタブ
			
		}

		@Override
		public void setUpdateInfoParam(Object param) {
			// TODO 自動生成されたメソッド・スタブ
			
		}

		@Override
		public Object selectDataByKey(Connection con) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public Object insertData(int execTranFlag, Connection con) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public Object updateDataByKey(int execTranFlag, Connection con) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public Object deleteDataByKey(int execTranFlag, Connection con) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

	}