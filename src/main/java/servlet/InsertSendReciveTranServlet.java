package servlet;

import interfe.CommonTableAccessIF;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import struct.SendReciveDetailTranKeyInfo;
import struct.SendReciveTranKeyInfo;
import struct.SendReciveTranSetInfo;
import struct.UpdateInfo;
import base.BaseServlet;
import dao.DocBodyDao;
import dao.SendReciveDetailTranDao;
import dao.SendReciveTranDao;
import dto.DocBodyDto;
import dto.SendReciveDetailTranDto;
import dto.SendReciveTranDto;

/**
 * Servlet implementation class InsertSendReciveTranServlet
 */
public class InsertSendReciveTranServlet extends BaseServlet implements CommonTableAccessIF {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertSendReciveTranServlet() {
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

	    if (postParamInsertCheck(request)) {

	    	//インタフェースにてクラスを参照
	    	CommonTableAccessIF sendRDao = new SendReciveTranDao();
	    	CommonTableAccessIF sendRDtlDao = new SendReciveDetailTranDao();
	    	CommonTableAccessIF docDao = new DocBodyDao();

	    	//パラメータをチェックし登録処理を実施
	    	int ret = mainProc(request, sendRDao, sendRDtlDao, docDao);

	    	//JSON形式変換
	    	String JsonStr = "";
	    	JsonStr = josnWrite(ret);

	    	//レスポンスを出力
	    	postOut(JsonStr, response);

	    }

	}

	/**
	 * 主処理
	 * パラメータをチェックしキー項目が空でな場合登録処理を行う
	 * 戻り値：1:登録完了,0:登録未完了,-1:パラメータチェックエラー,
	 * 		-2:送受信明細トランのデータが存在しているのでエラー
	 */
	private int mainProc(HttpServletRequest request,CommonTableAccessIF sendRDao,
			CommonTableAccessIF sendRDtlDao, CommonTableAccessIF docDao) {

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

		//送受信トランのキー項目チェック
		if ("".equals(send_recive_type) || "".equals(year_month) ||	"".equals(empl_code)){
			return -1;
		}

 		String doc_name = "";
		if (request.getParameter("doc_name") != null) {
			doc_name = request.getParameter("doc_name");
		}

 		String operation_type = "";
		if (request.getParameter("operation_type") != null) {
			operation_type = request.getParameter("operation_type");
		}

 		String last_send_recive_datetime = "";
		if (request.getParameter("last_send_recive_datetime") != null) {
			last_send_recive_datetime = request.getParameter("last_send_recive_datetime");
		}

 		String insert_status = "";
		if (request.getParameter("insert_status") != null) {
			insert_status = request.getParameter("insert_status");
		}

 		String status = "";
		if (request.getParameter("status") != null) {
			status = request.getParameter("status");
		}

 		String detail_num = "";
		if (request.getParameter("detail_num") != null) {
			detail_num = request.getParameter("detail_num");
		}

 		String insert_dateTime = "";
		if (request.getParameter("insert_dateTime") != null) {
			insert_dateTime = request.getParameter("insert_dateTime");
		}

 		String insert_userId = "";
		if (request.getParameter("insert_userId") != null) {
			insert_userId = request.getParameter("insert_userId");
		}

 		String insert_programId = "";
		if (request.getParameter("insert_programId") != null) {
			insert_programId = request.getParameter("insert_programId");
		}

		String doc_type = "";
		if (request.getParameter("doc_type") != null) {
			doc_type = request.getParameter("doc_type");
		}

		//キー項目チェック
		if ("".equals(send_recive_type) || "".equals(year_month) ||
				"".equals(empl_code) || "".equals(doc_name) || "".equals(doc_type)){
			return -1;

		}

		String operation_type_detil = "";
		if (request.getParameter("operation_type_detil") != null) {
			operation_type_detil = request.getParameter("operation_type_detil");
		}

		String send_recive_datetime = "";
		if (request.getParameter("send_recive_datetime") != null) {
			send_recive_datetime = request.getParameter("send_recive_datetime");
		}

		String check_status = "";
		if (request.getParameter("check_status") != null) {
			check_status = request.getParameter("check_status");
		}

		String check_datetime = "";
		if (request.getParameter("check_datetime") != null) {
			check_datetime = request.getParameter("check_datetime");
		}

		String finger_body = "";
		if (request.getParameter("finger_body") != null) {
			finger_body = request.getParameter("finger_body");
		}

		String doc_title = "";
		if (request.getParameter("doc_title") != null) {
			doc_title = request.getParameter("doc_title");
		}

		String doc_body = "";
		if (request.getParameter("doc_body") != null) {
			doc_body = request.getParameter("doc_body");
		}

		String expiration_from_dateTime = "";
		if (request.getParameter("expiration_from_dateTime") != null) {
			expiration_from_dateTime = request.getParameter("expiration_from_dateTime");
		}

		String expiration_to_dateTime = "";
		if (request.getParameter("expiration_to_dateTime") != null) {
			expiration_to_dateTime = request.getParameter("expiration_to_dateTime");
		}

		String mail_send_recive_dateTime = "";
		if (request.getParameter("mail_send_recive_dateTime") != null) {
			mail_send_recive_dateTime = request.getParameter("mail_send_recive_dateTime");
		}

		//更新トランザクション用にDBコネクションを用意
		Connection con = getConn();

		//*****************************
		// 送受信トランの存在チェック
		//*****************************
		SendReciveTranKeyInfo keyParm = new SendReciveTranKeyInfo();

		keyParm.setSend_recive_type(send_recive_type);
		keyParm.setYear_month(year_month);
		keyParm.setEmpl_code(empl_code);

		sendRDao.setParam(keyParm);
		SendReciveTranDto sRDto = new SendReciveTranDto();
		sRDto = (SendReciveTranDto)sendRDao.selectDataByKey(con);

		try {
			if (sRDto.getEmpl_code() == null) {
			//送受信トランのデータが存在していない

				//*****************************
				//送受信トランのINSERT処理
				//*****************************
				SendReciveTranDto insValue = new SendReciveTranDto();
				insValue.setSend_recive_type(send_recive_type);
				insValue.setYear_month(year_month);
				insValue.setEmpl_code(empl_code);
				insValue.setDoc_name(doc_name);
				insValue.setOperation_type(operation_type);
				insValue.setLast_send_recive_datetime(last_send_recive_datetime);
				insValue.setInsert_status(insert_status);
				insValue.setStatus(status);
				insValue.setDetail_num("1");
				insValue.setInsert_dateTime(insert_dateTime);
				insValue.setInsert_userId(insert_userId);
				insValue.setInsert_programId(insert_programId);
				insValue.setUpdate_dateTime(insert_dateTime);
				insValue.setUpdate_userId(insert_userId);
				insValue.setUpdate_programId(insert_programId);

				sendRDao.setValuesParam(insValue);
				ret = Integer.parseInt(sendRDao.insertData(0, con).toString());
				if (ret < 1) {
					//完了でない場合はエラー
					con.rollback();
					logger.error("SendReciveTran Insert エラー：" + Integer.toString(ret));
					return ret;
				}

				//********************************
				//送受信明細トランのINSERT処理
				//********************************
				SendReciveDetailTranDto insValueDtl = new SendReciveDetailTranDto();
				insValueDtl.setSend_recive_type(send_recive_type);
				insValueDtl.setYear_month(year_month);
				insValueDtl.setEmpl_code(empl_code);
				insValueDtl.setDoc_name(doc_name);
				insValueDtl.setDoc_type(doc_type);
				insValueDtl.setOperation_type(operation_type_detil);
				insValueDtl.setSend_recive_datetime(mail_send_recive_dateTime);
				insValueDtl.setCheck_status(check_status);
				insValueDtl.setCheck_datetime(check_datetime);
				insValueDtl.setFinger_body(finger_body);
				insValueDtl.setInsert_dateTime(insert_dateTime);
				insValueDtl.setInsert_userId(insert_userId);
				insValueDtl.setInsert_programId(insert_programId);
				insValueDtl.setUpdate_dateTime(insert_dateTime);
				insValueDtl.setUpdate_userId(insert_userId);
				insValueDtl.setUpdate_programId(insert_programId);

				sendRDtlDao.setValuesParam(insValueDtl);
				ret = Integer.parseInt(sendRDtlDao.insertData(0, con).toString());
				if (ret < 1) {
					//完了でない場合はエラー
					con.rollback();
					logger.error("SendReciveDetailTran Insert エラー：" + Integer.toString(ret));
					return ret;
				}

				//*****************************
				//文書内容のINSERT処理
				//*****************************
				DocBodyDto insValueDoc = new DocBodyDto();
				insValueDoc.setSend_recive_type(send_recive_type);
				insValueDoc.setYear_month(year_month);
				insValueDoc.setEmpl_code(empl_code);
				insValueDoc.setDoc_name(doc_name);
				insValueDoc.setDoc_type(doc_type);
				insValueDoc.setDoc_title(doc_title);
				insValueDoc.setDoc_body(doc_body);
				insValueDoc.setExpiration_from_dateTime(expiration_from_dateTime);
				insValueDoc.setExpiration_to_dateTime(expiration_to_dateTime);
				insValueDoc.setMail_send_recive_dateTime(mail_send_recive_dateTime);
				insValueDoc.setInsert_dateTime(insert_dateTime);
				insValueDoc.setInsert_userId(insert_userId);
				insValueDoc.setInsert_programId(insert_programId);
				insValueDoc.setUpdate_dateTime(insert_dateTime);
				insValueDoc.setUpdate_userId(insert_userId);
				insValueDoc.setUpdate_programId(insert_programId);

				docDao.setValuesParam(insValueDoc);
				ret = Integer.parseInt(docDao.insertData(0, con).toString());
				if (ret < 1) {
					//完了でない場合はエラー
					con.rollback();
					logger.error("DocBody Insert エラー：" + Integer.toString(ret));
					return ret;
				}

			} else {
			//送受信トランのデータが存在している
				//明細数を取得
				int dtlNum = Integer.parseInt(sRDto.getDetail_num());

				//************************************
				//送受信明細トランの存在チェック処理
				//************************************
				SendReciveDetailTranKeyInfo keyParmDtl = new SendReciveDetailTranKeyInfo();

				keyParmDtl.setSend_recive_type(send_recive_type);
				keyParmDtl.setYear_month(year_month);
				keyParmDtl.setEmpl_code(empl_code);
				keyParmDtl.setDoc_name(doc_name);
				keyParmDtl.setDoc_type(doc_type);
				sendRDtlDao.setParam(keyParmDtl);

				SendReciveDetailTranDto sRDtlDto = new SendReciveDetailTranDto();
				sRDtlDto = (SendReciveDetailTranDto)sendRDtlDao.selectDataByKey(con);

				if (sRDtlDto.getEmpl_code() == null) {
				//送受信明細トランのデータが存在していない

					//**********************************************************
					//送受信トランの最終文書名・最終登録日時・明細数の更新処理
					//**********************************************************
					SendReciveTranSetInfo setInfo = new SendReciveTranSetInfo();
					//明細数を1件増やす
					dtlNum++;
					setInfo.setDetail_num(Integer.toString(dtlNum));
					setInfo.setDoc_name(doc_name);
					setInfo.setLast_send_recive_datetime(last_send_recive_datetime);
					sendRDao.setSetParam(setInfo);

					UpdateInfo updInfo = new UpdateInfo();
					updInfo.setUpdate_dateTime(insert_dateTime);
					updInfo.setUpdate_userId(insert_userId);
					updInfo.setUpdate_programId(insert_programId);
					sendRDao.setUpdateInfoParam(updInfo);

					ret = Integer.parseInt(sendRDao.updateDataByKey(0, con).toString());
					if (ret < 1) {
						//完了でない場合はエラー
						con.rollback();
						logger.error("SendReciveTran Update エラー：" + Integer.toString(ret));
						return ret;
					}

					//*****************************
					//送受信明細トランのINSERT処理
					//*****************************
					SendReciveDetailTranDto insValueDtl = new SendReciveDetailTranDto();
					insValueDtl.setSend_recive_type(send_recive_type);
					insValueDtl.setYear_month(year_month);
					insValueDtl.setEmpl_code(empl_code);
					insValueDtl.setDoc_name(doc_name);
					insValueDtl.setDoc_type(doc_type);
					insValueDtl.setOperation_type(operation_type_detil);
					insValueDtl.setSend_recive_datetime(mail_send_recive_dateTime);
					insValueDtl.setCheck_status(check_status);
					insValueDtl.setCheck_datetime(check_datetime);
					insValueDtl.setFinger_body(finger_body);
					insValueDtl.setInsert_dateTime(insert_dateTime);
					insValueDtl.setInsert_userId(insert_userId);
					insValueDtl.setInsert_programId(insert_programId);
					insValueDtl.setUpdate_dateTime(insert_dateTime);
					insValueDtl.setUpdate_userId(insert_userId);
					insValueDtl.setUpdate_programId(insert_programId);

					sendRDtlDao.setValuesParam(insValueDtl);
					ret = Integer.parseInt(sendRDtlDao.insertData(0, con).toString());
					if (ret < 1) {
						//完了でない場合はエラー
						con.rollback();
						logger.error("SendReciveDetailTran Insert エラー：" + Integer.toString(ret));
						return ret;
					}

					//*****************************
					//文書内容のINSERT処理
					//*****************************
					DocBodyDto insValueDoc = new DocBodyDto();
					insValueDoc.setSend_recive_type(send_recive_type);
					insValueDoc.setYear_month(year_month);
					insValueDoc.setEmpl_code(empl_code);
					insValueDoc.setDoc_name(doc_name);
					insValueDoc.setDoc_type(doc_type);
					insValueDoc.setDoc_title(doc_title);
					insValueDoc.setDoc_body(doc_body);
					insValueDoc.setExpiration_from_dateTime(expiration_from_dateTime);
					insValueDoc.setExpiration_to_dateTime(expiration_to_dateTime);
					insValueDoc.setMail_send_recive_dateTime(mail_send_recive_dateTime);
					insValueDoc.setInsert_dateTime(insert_dateTime);
					insValueDoc.setInsert_userId(insert_userId);
					insValueDoc.setInsert_programId(insert_programId);
					insValueDoc.setUpdate_dateTime(insert_dateTime);
					insValueDoc.setUpdate_userId(insert_userId);
					insValueDoc.setUpdate_programId(insert_programId);

					docDao.setValuesParam(insValueDoc);
					ret = Integer.parseInt(docDao.insertData(0, con).toString());
					if (ret < 1) {
						//完了でない場合はエラー
						con.rollback();
						logger.error("DocBody Insert エラー：" + Integer.toString(ret));
						return ret;
					}

				} else {
				//送受信明細トランのデータが存在しているのでエラー
					return -2;
				}
			}

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
		}

		 return ret;

	 }

	/** JSON文字列を返す */
	//@Override
	public String josnWrite(int retCode) {
	   	String retVal = Integer.toString(retCode);
    	String ret = String.format("{\"rtn_code\":\"%s\"}", retVal);
    	String ret2 ="[" + ret + "]";
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
