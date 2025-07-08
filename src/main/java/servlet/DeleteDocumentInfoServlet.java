package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import struct.SendReciveTranSetInfo;
import struct.UpdateInfo;

/**
 * Servlet implementation class DeleteDocumentInfoServlet 
 * 文書削除用サーブレット
 * @author A.T
 *    Date:2025/07/08
 * @version 1.0.0
 */
@WebServlet("/DeleteDocumentInfoServlet")
public class DeleteDocumentInfoServlet extends BaseServlet implements CommonTableAccessIF {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteDocumentInfoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (postParamDeleteCheck(request)) {
			// DAOインスタンス生成
			CommonTableAccessIF sendRDao = new SendReciveTranDao();
			CommonTableAccessIF sendRDtlDao = new SendReciveDetailTranDao();
			CommonTableAccessIF docDao = new DocBodyDao();

			int ret = updateDocumentInfo(request, sendRDao, sendRDtlDao, docDao);

			//JSON形式変換
			String JsonStr = "";
			JsonStr = josnWrite(ret);
			postOut(JsonStr, response);
		}
	}

	/**
	 * 主処理（文書削除サーブレット）
	 * 
	 * リクエストパラメータを取得・検証し、送受信トラン・送受信明細トラン・文書内容の各テーブルに対して
	 * 文書情報の削除処理を行います。
	 * 
	 * 更新処理の内容：
	 * - 送受信明細トラン・文書内容・送受信トランの存在チェック
	 * - 送受信明細トラン・文書内容削除
	 * - 送受信トランの明細数をチェック
	 * - １の場合、送受信トラン削除
	 * - それ以外の場合、明細数を-1に更新
	 * - 作成日時が近日の文書内容の文書名を取得
	 * - 送受信トランの文書名と更新日時と更新（ヘッダ更新）
	 * 
	 * 処理順序：
	 *  1. 必須パラメータの取得とチェック
	 *  2. 送受信トラン／送受信明細／文書内容の存在確認
	 *  3. 送受信明細と文書内容を削除
	 *  4. 送受信トランの明細数をチェック
	 *  5. １の場合は送受信トランを削除
	 *  6. それ以外の場合、明細数を‐1
	 *  7. 明細に該当する文書内容で作成日時が近日の文書名を取得
	 *  8. 7で取得した文書名、パラメータで受け取った更新日時・更新者・更新・更新プログラムIDに更新
	 * 
	 * @param request HTTPリクエスト（パラメータ含む）
	 * @param sendRDao 送受信トラン用DAO
	 * @param sendRDtlDao 送受信明細用DAO
	 * @param docDao 文書内容用DAO
	 * 
	 * @return int 
	 *   1 : 更新成功  
	 *   0 : トランザクション処理中にエラーが発生しました 
	 *  -1 : 対象の送受信トランまたは文書が存在しません  
	 */

	private int updateDocumentInfo(HttpServletRequest request,
			CommonTableAccessIF sendRDao,
			CommonTableAccessIF sendRDtlDao,
			CommonTableAccessIF docDao) {

		int ret = 1;

		// 1-1. パラメータ取得（null安全）
		String send_recive_type = "";
		if (request.getParameter("send_recive_type") != null) {
			send_recive_type = request.getParameter("send_recive_type").trim();
		}

		String year_month = "";
		if (request.getParameter("year_month") != null) {
			year_month = request.getParameter("year_month").trim();
		}

		String empl_code = "";
		if (request.getParameter("empl_code") != null) {
			empl_code = request.getParameter("empl_code").trim();
		}

		String doc_type = "";
		if (request.getParameter("doc_type") != null) {
			doc_type = request.getParameter("doc_type").trim();
		}

		String doc_name = "";
		if (request.getParameter("doc_name") != null) {
			doc_name = request.getParameter("doc_name").trim();
		}

		String update_userid = "";
		if (request.getParameter("update_userid") != null) {
			update_userid = request.getParameter("update_userid").trim();
		}

		String update_datetime = "";
		if (request.getParameter("update_datetime") != null) {
			update_datetime = request.getParameter("update_datetime").trim();
		}

		String update_programid = "";
		if (request.getParameter("update_programid") != null) {
			update_programid = request.getParameter("update_programid").trim();
		}


		// 1-2. 必須チェック
		if (send_recive_type.isEmpty() || year_month.isEmpty() || empl_code.isEmpty()
				|| doc_type.isEmpty() || doc_name.isEmpty()) {
			return -1; // 対象文書なし
		}

		// DBコネクション取得
		Connection con = getConn();

		try {
			// 2-1. 送受信トラン存在チェック
			SendReciveTranKeyInfo sendReciveKey = new SendReciveTranKeyInfo();
			sendReciveKey.setSend_recive_type(send_recive_type);
			sendReciveKey.setYear_month(year_month);
			sendReciveKey.setEmpl_code(empl_code);
			sendRDao.setParam(sendReciveKey);
			SendReciveTranDto sendReciveDto = (SendReciveTranDto) sendRDao.selectDataByKey(con);

			if (sendReciveDto == null || sendReciveDto.getEmpl_code() == null
					|| sendReciveDto.getEmpl_code().isEmpty()) {
				return -1; // 対象文書なし
			}

			// 3-1. 送受信明細トラン存在チェック
			SendReciveDetailTranKeyInfo detailKey = new SendReciveDetailTranKeyInfo();
			detailKey.setSend_recive_type(send_recive_type);
			detailKey.setYear_month(year_month);
			detailKey.setEmpl_code(empl_code);
			detailKey.setDoc_type(doc_type);
			detailKey.setDoc_name(doc_name);
			sendRDtlDao.setParam(detailKey);
			SendReciveDetailTranDto detailDto = (SendReciveDetailTranDto) sendRDtlDao.selectDataByKey(con);

			if (detailDto == null || detailDto.getDoc_name() == null || detailDto.getDoc_name().isEmpty()) {
				return -1; // 対象文書なし
			}

			// 3-1. 文書内容存在チェック
			DocBodyKeyInfo docKey = new DocBodyKeyInfo();
			docKey.setSend_recive_type(send_recive_type);
			docKey.setYear_month(year_month);
			docKey.setEmpl_code(empl_code);
			docKey.setDoc_type(doc_type);
			docKey.setDoc_name(doc_name);
			docDao.setParam(docKey);
			DocBodyDto docDto = (DocBodyDto) docDao.selectDataByKey(con);

			if (docDto == null || docDto.getDoc_name() == null || docDto.getDoc_name().isEmpty()) {
				return -1; // 対象文書なし
			}

			// 3-2. 送受信明細トラン削除
			ret = Integer.parseInt(sendRDtlDao.deleteDataByKey(0, con).toString());
			if (ret < 1) {
				con.rollback();
				return 0;
			}

			// 文書内容削除
			ret = Integer.parseInt(docDao.deleteDataByKey(0, con).toString());
			if (ret < 1) {
				con.rollback();
				return 0;
			}

			// 4. 明細数取得（String→int）
			int detailNum = 0;
			try {
				detailNum = Integer.parseInt(sendReciveDto.getDetail_num());
			} catch (NumberFormatException e) {
				con.rollback();
				return 0;
			}

			if (detailNum <= 1) {
				// 4-1. 明細1件 → 送受信トラン削除
				ret = Integer.parseInt(sendRDao.deleteDataByKey(0, con).toString());
				if (ret < 1) {
					con.rollback();
					return 0;
				}
			} else {
				// 4-2. 明細数-1に更新
				SendReciveTranSetInfo setInfo = new SendReciveTranSetInfo();
				setInfo.setDetail_num(Integer.toString(detailNum - 1));

				// 4-3. 最新文書名取得
				String latestDocName = selectLatestDocName(con, send_recive_type, year_month, empl_code);
				if (latestDocName == null || latestDocName.isEmpty()) {
					con.rollback();
					return 0;
				}
				setInfo.setDoc_name(latestDocName);

				sendRDao.setSetParam(setInfo);

				UpdateInfo updateInfo = new UpdateInfo();
				updateInfo.setUpdate_dateTime(update_datetime);
				updateInfo.setUpdate_userId(update_userid);
				updateInfo.setUpdate_programId(update_programid);
				sendRDao.setUpdateInfoParam(updateInfo);

				// 4-4. 送受信トラン更新
				ret = Integer.parseInt(sendRDao.updateDataByKey(0, con).toString());
				if (ret < 1) {
					con.rollback();
					return 0;
				}
			}

			// コミット
			con.commit();

		} catch (SQLException e) {
			try {
				con.rollback();
				e.printStackTrace();
				logger.error("コミット処理エラー：" + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.error("ロールバック処理エラー：" + e1.getMessage());
			}
			ret = 0;
		} catch (Exception e) {
			try {
				con.rollback();
				e.printStackTrace();
				logger.error("処理エラー：" + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.error("ロールバック処理エラー：" + e1.getMessage());
			}
			ret = 0;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return ret;
	}

	/**
	 * 最新の文書名（作成日時が一番新しい）を取得
	 */
	public String selectLatestDocName(Connection con, String send_recive_type, String year_month, String empl_code)
			throws SQLException {
		String sql = "SELECT doc_name " +
				"FROM doc.docbody " +
				"WHERE send_recive_type = ? " +
				"AND year_month = ? " +
				"AND empl_code = ? " +
				"ORDER BY insert_dateTime DESC " +
				"LIMIT 1";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, send_recive_type);
			pstmt.setString(2, year_month);
			pstmt.setString(3, empl_code);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("doc_name");
				}
			}
		}
		return null;
	}

	/** JSON文字列を返す */
	public String josnWrite(int retCode) {
		String retVal = Integer.toString(retCode);
		String ret = String.format("{\"rtn_code\":\"%s\"}", retVal);
		String ret2 = "[" + ret + "]";
		return ret2;
	}

	// 以下のメソッドはインタフェースでのデフォルトメソッドです。
	// 削除は駄目です。使用する事も駄目です。
	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setParam(java.lang.Object)
	 */
	@Override
	public void setParam(Object param) {

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setValuesParam(java.lang.Object)
	 */
	@Override
	public void setValuesParam(Object param) {

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setSetParam(java.lang.Object)
	 */
	@Override
	public void setSetParam(Object param) {

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setUpdateInfoParam(java.lang.Object)
	 */
	@Override
	public void setUpdateInfoParam(Object param) {

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#insertData(int, java.sql.Connection)
	 */
	@Override
	public Object insertData(int execTranFlag, Connection con) {
		return null;
	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#updateDataByKey(int, java.sql.Connection)
	 */
	@Override
	public Object updateDataByKey(int execTranFlag, Connection con) {
		return null;
	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#deleteDataByKey(int, java.sql.Connection)
	 */
	@Override
	public Object deleteDataByKey(int execTranFlag, Connection con) {
		return null;
	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#selectDataByKey(java.sql.Connection)
	 */
	@Override
	public Object selectDataByKey(Connection con) {
		return null;
	}

}
