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
import struct.SendReciveTranSetInfo;
import struct.UpdateInfo;

/**
 * Servlet implementation class UpdateDocumentInfoServlet
 * 文書更新用サーブレット
 * @author A.T
 *    Date:2025/06/19
 * @version 1.0.0
 */
@WebServlet("/UpdateDocumentInfoServlet")
public class UpdateDocumentInfoServlet extends BaseServlet implements CommonTableAccessIF {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateDocumentInfoServlet() {
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

		if (postParamUpdateCheck(request)) {
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
	 * 主処理（文書情報更新サーブレット）
	 * 
	 * リクエストパラメータを取得・検証し、送受信トラン・明細・文書情報の各テーブルに対して
	 * 文書情報の更新処理を行います。
	 * 
	 * 更新処理の内容：
	 * - 送受信トラン・文書情報の存在チェック
	 * - 更新後の文書名重複チェック（送受信明細／文書内容）
	 * - 文書削除後、更新文書名で再登録（DELETE＋INSERT）
	 * - 送受信トランの更新（ヘッダ更新）
	 * 
	 * 処理順序：
	 *  1. 必須パラメータの取得とチェック
	 *  2. 同一文書名チェック（早期リターン）
	 *  3. 送受信トランの存在確認
	 *  4. 更新後文書の重複チェック（送受信明細／文書内容）
	 *  5. 更新前文書の存在チェック（送受信明細／文書内容）
	 *  6. 送受信トランの更新（UPDATE）
	 *  7. 旧文書データの削除（送受信明細／文書内容）
	 *  8. 新文書データの登録（送受信明細／文書内容）
	 * 
	 * @param request HTTPリクエスト（パラメータ含む）
	 * @param sendRDao 送受信トラン用DAO
	 * @param sendRDtlDao 送受信明細用DAO
	 * @param docDao 文書内容用DAO
	 * 
	 * @return int 
	 *   1 : 更新成功  
	 *   0 : トランザクション処理中にエラーが発生しました
	 *  -2 : 文書名が同一、または重複により更新不要／不可  
	 *  -1 : 対象の送受信トランまたは文書が存在しません  
	 */

	private int updateDocumentInfo(HttpServletRequest request,
			CommonTableAccessIF sendRDao,
			CommonTableAccessIF sendRDtlDao,
			CommonTableAccessIF docDao) {

		int ret = 1;

		// 1-1. パラメータ取得（null安全な取得）
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

		String target_doc_name = "";
		if (request.getParameter("target_doc_name") != null) {
			target_doc_name = request.getParameter("target_doc_name").trim();
		}

		String update_doc_name = "";
		if (request.getParameter("update_doc_name") != null) {
			update_doc_name = request.getParameter("update_doc_name").trim();
		}

		String expiration_from_dateTime = "";
		if (request.getParameter("expiration_from_dateTime") != null) {
			expiration_from_dateTime = request.getParameter("expiration_from_dateTime").trim();
		}

		String expiration_to_dateTime = "";
		if (request.getParameter("expiration_to_dateTime") != null) {
			expiration_to_dateTime = request.getParameter("expiration_to_dateTime").trim();
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

		// 1-2, パラメータ不足のチェック（必須項目が空の場合は0を返す）
		if ("".equals(send_recive_type) || "".equals(year_month) || "".equals(empl_code) ||
				"".equals(doc_type) || "".equals(target_doc_name) || "".equals(update_doc_name)) {
			return -1; // 対象文書が存在しません
		}
		// デバッグ用ログ
		System.out.println("Debug: target_doc_name = [" + target_doc_name + "]");
		System.out.println("Debug: update_doc_name = [" + update_doc_name + "]");
		System.out.println("Debug: target_doc_name.length() = " + target_doc_name.length());
		System.out.println("Debug: update_doc_name.length() = " + update_doc_name.length());
		System.out.println("Debug: target_doc_name.equals(update_doc_name) = " + target_doc_name.equals(update_doc_name));

		//更新トランザクション用にDBコネクションを用意
		Connection con = getConn();

		try {

			// 2-1. 送受信トランに作成履歴存在チェック
			SendReciveTranKeyInfo sendReciveKey = new SendReciveTranKeyInfo();
			sendReciveKey.setSend_recive_type(send_recive_type);
			sendReciveKey.setYear_month(year_month);
			sendReciveKey.setEmpl_code(empl_code);
			sendRDao.setParam(sendReciveKey); // ← パラメータセット必須
			SendReciveTranDto sendReciveDto = (SendReciveTranDto) sendRDao.selectDataByKey(con);

			// 作成履歴が存在しない場合
			if (sendReciveDto == null ||
					sendReciveDto.getEmpl_code() == null ||
					sendReciveDto.getEmpl_code().trim().isEmpty()) {
				return -1; // 対象文書が存在しません
			}


			// 3. 重複チェック（更新後文書名で既存文書があるか）
			// 3-1. 送受信明細トランでチェック
			if (!target_doc_name.trim().equals(update_doc_name.trim())) {
				// 3-1. 送受信明細トランでチェック
				SendReciveDetailTranKeyInfo updateKey = new SendReciveDetailTranKeyInfo();
				updateKey.setSend_recive_type(send_recive_type);
				updateKey.setYear_month(year_month);
				updateKey.setEmpl_code(empl_code);
				updateKey.setDoc_type(doc_type);
				updateKey.setDoc_name(update_doc_name);
				sendRDtlDao.setParam(updateKey);
				SendReciveDetailTranDto existingUpdate = (SendReciveDetailTranDto) sendRDtlDao.selectDataByKey(con);

				// 重複チェック（デバッグログ追加）
				System.out.println("Debug: existingUpdate = " + existingUpdate);
				System.out.println("Debug: existingUpdate.getDoc_name() = " +
						(existingUpdate != null ? existingUpdate.getDoc_name() : "null"));

				// 更新文書が送受信明細トランに存在した場合
				if (existingUpdate.getDoc_name() != null &&
						!existingUpdate.getDoc_name().trim().isEmpty()) {
					System.out.println("Debug: 送受信明細トランで重複検出");
					return ret = -2; // 文書が既に存在します
				}

				// 3-2. 文書内容でチェック
				DocBodyKeyInfo updateDocKey = new DocBodyKeyInfo();
				updateDocKey.setSend_recive_type(send_recive_type);
				updateDocKey.setYear_month(year_month);
				updateDocKey.setEmpl_code(empl_code);
				updateDocKey.setDoc_type(doc_type);
				updateDocKey.setDoc_name(update_doc_name);
				docDao.setParam(updateDocKey);
				DocBodyDto existingDocBody = (DocBodyDto) docDao.selectDataByKey(con);

				// 重複チェック（デバッグログ追加）
				System.out.println("Debug: existingDocBody = " + existingDocBody);
				System.out.println("Debug: existingDocBody.getDoc_name() = " +
						(existingDocBody != null ? existingDocBody.getDoc_name() : "null"));

				// 更新文書が文書内容に存在した場合
				if (existingDocBody.getDoc_name() != null &&
						!existingDocBody.getDoc_name().trim().isEmpty()) {
					System.out.println("Debug: 文書内容で重複検出");
					return ret = -2; // 文書が既に存在します
				}
			}

			// 4. 更新前文書存在チェック
			// 4-1. 送受信明細トランでチェック
			SendReciveDetailTranKeyInfo targetKey = new SendReciveDetailTranKeyInfo();
			targetKey.setSend_recive_type(send_recive_type);
			targetKey.setYear_month(year_month);
			targetKey.setEmpl_code(empl_code);
			targetKey.setDoc_type(doc_type);
			targetKey.setDoc_name(target_doc_name);
			sendRDtlDao.setParam(targetKey);
			SendReciveDetailTranDto targetDetailDto = (SendReciveDetailTranDto) sendRDtlDao.selectDataByKey(con);

			if (targetDetailDto == null ||
					targetDetailDto.getDoc_name() == null ||
					targetDetailDto.getDoc_name().trim().isEmpty()) {
				return -1; // 対象文書が存在しません
			}

			// 4-2. 文書内容でチェック
			DocBodyKeyInfo targetDocKey = new DocBodyKeyInfo();
			targetDocKey.setSend_recive_type(send_recive_type);
			targetDocKey.setYear_month(year_month);
			targetDocKey.setEmpl_code(empl_code);
			targetDocKey.setDoc_type(doc_type);
			targetDocKey.setDoc_name(target_doc_name);
			docDao.setParam(targetDocKey);
			DocBodyDto targetDocBodyDto = (DocBodyDto) docDao.selectDataByKey(con);

			// 更新前文書が存在しない場合
			if (targetDocBodyDto == null ||
					targetDocBodyDto.getDoc_name() == null ||
					targetDocBodyDto.getDoc_name().trim().isEmpty()) {
				return -1; // 対象文書が存在しません
			}

			// 4-3. 作成日時・作成ユーザー・作成プログラムIDを取得
			String target_detail_datetime = targetDetailDto.getInsert_dateTime();
			String target_detail_userid = targetDetailDto.getInsert_userId();
			String target_detail_programid = targetDetailDto.getInsert_programId();
			String target_docbody_datetime = targetDocBodyDto.getInsert_dateTime();
			String target_docbody_userid = targetDocBodyDto.getInsert_userId();
			String target_docbody_programid = targetDocBodyDto.getInsert_programId();

			// 4-4. 送受信トラン更新（作成日時・ユーザー・プログラムID以外）
			UpdateInfo updateInfo = new UpdateInfo();
			updateInfo.setUpdate_dateTime(update_datetime);
			updateInfo.setUpdate_userId(update_userid);
			updateInfo.setUpdate_programId(update_programid);
			sendRDao.setUpdateInfoParam(updateInfo);

			SendReciveTranSetInfo setInfo = new SendReciveTranSetInfo();
			setInfo.setDoc_name(update_doc_name);
			sendRDao.setSetParam(setInfo);

			ret = Integer.parseInt(sendRDao.updateDataByKey(0, con).toString());

			// 4-5. UPDATEに失敗した場合
			if (ret < 1) {
				con.rollback();
				logger.error("SendReciveTran Update エラー：" + Integer.toString(ret));
				return ret; // CRUD操作失敗
			}

			// 4-6. 送受信明細トランをDELETE
			ret = Integer.parseInt(sendRDtlDao.deleteDataByKey(0, con).toString());

			// 4-7. 削除に失敗した場合
			if (ret < 1) {
				con.rollback();
				logger.error("SendReciveDetailTran Delete エラー：" + Integer.toString(ret));
				return ret; // CRUD操作失敗
			}

			// 4-8. 文書内容をDELETE
			ret = Integer.parseInt(docDao.deleteDataByKey(0, con).toString());

			// 4-9. 削除に失敗した場合
			if (ret < 1) {
				con.rollback();
				logger.error("DocBody Delete エラー：" + Integer.toString(ret));
				return ret; // CRUD操作失敗
			}

			// 4-10. 送受信明細トランをINSERT
			// 送受信明細トラン：更新前データから必要な情報を取得してINSERT
			SendReciveDetailTranDto newDetail = new SendReciveDetailTranDto();
			newDetail.setSend_recive_type(send_recive_type);
			newDetail.setYear_month(year_month);
			newDetail.setEmpl_code(empl_code);
			newDetail.setDoc_type(doc_type);
			newDetail.setDoc_name(update_doc_name);
			// 更新前文書から取得したデータを使用
			newDetail.setOperation_type(targetDetailDto.getOperation_type());
			newDetail.setSend_recive_datetime(targetDetailDto.getSend_recive_datetime());
			newDetail.setCheck_status(targetDetailDto.getCheck_status());
			newDetail.setCheck_datetime(targetDetailDto.getCheck_datetime());
			newDetail.setFinger_body(targetDetailDto.getFinger_body());
			newDetail.setInsert_dateTime(target_detail_datetime);
			newDetail.setInsert_userId(target_detail_userid);
			newDetail.setInsert_programId(target_detail_programid);
			// 更新情報はパラメータから設定
			newDetail.setUpdate_dateTime(update_datetime);
			newDetail.setUpdate_userId(update_userid);
			newDetail.setUpdate_programId(update_programid);

			sendRDtlDao.setValuesParam(newDetail);
			ret = Integer.parseInt(sendRDtlDao.insertData(0, con).toString());

			// 4-11. 追加に失敗した場合
			if (ret < 1) {
				con.rollback();
				logger.error("SendReciveDetailTran Insert エラー：" + Integer.toString(ret));
				return ret; // CRUD操作失敗
			}

			// 4-12. 文書内容をINSERT
			// 文書内容：更新前データから必要な情報を取得してINSERT
			DocBodyDto newDocBody = new DocBodyDto();
			newDocBody.setSend_recive_type(send_recive_type);
			newDocBody.setYear_month(year_month);
			newDocBody.setEmpl_code(empl_code);
			newDocBody.setDoc_type(doc_type);
			newDocBody.setDoc_name(update_doc_name);
			// 文書タイトル・本文・メール送受信日時は更新前データから取得
			newDocBody.setDoc_title(targetDocBodyDto.getDoc_title());
			newDocBody.setDoc_body(targetDocBodyDto.getDoc_body());
			newDocBody.setMail_send_recive_dateTime(targetDocBodyDto.getMail_send_recive_dateTime());
			// 有効期限はパラメータから設定
			newDocBody.setExpiration_from_dateTime(expiration_from_dateTime);
			newDocBody.setExpiration_to_dateTime(expiration_to_dateTime);
			// 作成情報は更新前文書から取得
			newDocBody.setInsert_dateTime(target_docbody_datetime);
			newDocBody.setInsert_userId(target_docbody_userid);
			newDocBody.setInsert_programId(target_docbody_programid);
			// 更新情報はパラメータから設定
			newDocBody.setUpdate_dateTime(update_datetime);
			newDocBody.setUpdate_userId(update_userid);
			newDocBody.setUpdate_programId(update_programid);

			docDao.setValuesParam(newDocBody);
			ret = Integer.parseInt(docDao.insertData(0, con).toString());

			// 4-13. 追加に失敗した場合
			if (ret < 1) {
				con.rollback();
				logger.error("DocBody Insert エラー：" + Integer.toString(ret));
				return ret; // CRUD操作失敗
			}

			// 4-14. 追加に成功した場合コミット
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
		} catch (Exception e) {
			try {
				con.rollback();
				e.printStackTrace();
				logger.error("処理エラー：" + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
				logger.error("ロールバック処理エラー：" + e1.getMessage());
			}
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
