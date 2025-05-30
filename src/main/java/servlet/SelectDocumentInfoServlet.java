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
		
		if (!postParamSelectCheck(request)) {
	        String jsonStr = josnWrite("0", null);
	        postOut(jsonStr, response);
	        return;
	    }

	        CommonTableAccessIF sendRDao = new SendReciveTranDao();
	        CommonTableAccessIF sendRDtlDao = new SendReciveDetailTranDao();
	        CommonTableAccessIF docDao = new DocBodyDao();


	        DocBodyDto docDto = mainProc(request, sendRDao, sendRDtlDao, docDao);
	        int rtnCode = (docDto == null) ? 0 : 1;
	        String jsonStr = josnWrite(rtnCode, docDto);
	        postOut(josnWrite(rtnCode, docDto), response);
	    }
	    

	    private int mainProc(HttpServletRequest request,
	                         CommonTableAccessIF sendRDao,
	                         CommonTableAccessIF sendRDtlDao,
	                         CommonTableAccessIF docDao) {

	        String send_recive_type = nvl(request.getParameter("send_recive_type"));
	        String year_month = nvl(request.getParameter("year_month"));
	        String empl_code = nvl(request.getParameter("empl_code"));
	        String doc_name = nvl(request.getParameter("doc_name"));
	        String doc_type = nvl(request.getParameter("doc_type"));

	        if (send_recive_type.isEmpty() || year_month.isEmpty() || empl_code.isEmpty()
	                || doc_name.isEmpty() || doc_type.isEmpty()) {
	            return null;
	        }

	        Connection con = null;

	        try {
	        	con = getConn();
	        	
	            // 送受信トラン取得
	            SendReciveTranKeyInfo srKey = new SendReciveTranKeyInfo();
	            srKey.setSend_recive_type(send_recive_type);
	            srKey.setYear_month(year_month);
	            srKey.setEmpl_code(empl_code);
	            sendRDao.setParam(srKey);
	            SendReciveTranDto srDto = (SendReciveTranDto) sendRDao.selectDataByKey(con);

	            // 明細トラン取得
	            SendReciveDetailTranKeyInfo srDtlKey = new SendReciveDetailTranKeyInfo();
	            srDtlKey.setSend_recive_type(send_recive_type);
	            srDtlKey.setYear_month(year_month);
	            srDtlKey.setEmpl_code(empl_code);
	            srDtlKey.setDoc_name(doc_name);
	            srDtlKey.setDoc_type(doc_type);
	            sendRDtlDao.setParam(srDtlKey);
	            SendReciveDetailTranDto srDtlDto = (SendReciveDetailTranDto) sendRDtlDao.selectDataByKey(con);

	            // 文書内容取得
	            DocBodyKeyInfo docKey = new DocBodyKeyInfo();
	            docKey.setSend_recive_type(send_recive_type);
	            docKey.setYear_month(year_month);
	            docKey.setEmpl_code(empl_code);
	            docKey.setDoc_name(doc_name);
	            docKey.setDoc_type(doc_type);
	            docDao.setParam(docKey);
	            DocBodyDto docDto = (DocBodyDto) docDao.selectDataByKey(con);

	            // 必須データがすべて存在するかチェック
	            if (srDto.getEmpl_code() == null || srDtlDto.getEmpl_code() == null || docDto.getEmpl_code() == null) {
	                return null;  // データなし
	            }

	            // 必要な属性を設定（使ってないなら削除してもよい）
	            request.setAttribute("docTitle", docDto.getDoc_title());
	            request.setAttribute("dateFrom", docDto.getExpiration_from_dateTime());
	            request.setAttribute("dateTo", docDto.getExpiration_to_dateTime());

	            return docDto;

	            
	        } catch (Exception e) {
	            logger.error("文書閲覧情報取得中にエラー", e);
	            try {
	                if (con != null) con.rollback();
	            } catch (SQLException rollbackEx) {
	                logger.error("ロールバックエラー", rollbackEx);
	            }
	            return null;
	        } finally {
	            try {
	                if (con != null) con.close();
	            } catch (SQLException closeEx) {
	                logger.error("コネクションクローズ失敗", closeEx);
	            }
	        }
	    }

	    // パラメータチェック
	    private boolean postParamSelectCheck(HttpServletRequest request) {
	        return request.getParameter("send_recive_type") != null &&
	               request.getParameter("year_month") != null &&
	               request.getParameter("empl_code") != null &&
	               request.getParameter("doc_name") != null &&
	               request.getParameter("doc_type") != null;
	    }

	    // null を "" に置換するユーティリティ
	    private String nvl(String val) {
	        return (val == null) ? "" : val;
	    }

	    private String josnWrite(int ret, DocBodyDto docDto) {
	        StringBuilder sb = new StringBuilder();
	        sb.append("{");
	        sb.append("\"result\": ").append(ret);
	        if (ret == 1 && docDto != null) {
	            sb.append(", ");
	            sb.append("\"doc_title\": \"").append(docDto.getDoc_title()).append("\", ");
	            sb.append("\"expiration_from\": \"").append(docDto.getExpiration_from_dateTime()).append("\", ");
	            sb.append("\"expiration_to\": \"").append(docDto.getExpiration_to_dateTime()).append("\"");
	        }
	        sb.append("}");
	        return sb.toString();
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