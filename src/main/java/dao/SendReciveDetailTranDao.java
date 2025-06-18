/**
 * (概要)
 *
 *  作成者 ：D.Ikeda
 *  作成日 : 2021/05/16
 *  履歴： バージョン 日付        更新者
 *         L0.00      2021/05/16  D.Ikeda
 *         L0.01      2024/06/05  T.Usui
 *         L0.02      2024/09/05  T.Usui
 */
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import base.BaseServlet;
import dto.SendReciveDetailTranDto;
import interfe.CommonTableAccessIF;
import struct.SendReciveDetailTranKeyInfo;
import struct.SendReciveDetailTranSetInfo;
import struct.UpdateInfo;

/**
 * @author D.Ikeda
 *
 */
public class SendReciveDetailTranDao extends BaseServlet implements CommonTableAccessIF {

	private SendReciveDetailTranKeyInfo paramKeyInfo = null;

	private SendReciveDetailTranDto paramValusInfo = null;

	private SendReciveDetailTranSetInfo paramSetInfo = null;

	private UpdateInfo paramUpdateInfo = null;

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setParam()
	 */
	@Override
	public void setParam(Object param) {

		this.paramKeyInfo = (SendReciveDetailTranKeyInfo)param;

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setValuesParam()
	 */
	@Override
	public void setValuesParam(Object param) {

		this.paramValusInfo = (SendReciveDetailTranDto)param;

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setSetParam()
	 */
	@Override
	public void setSetParam(Object param) {
		this.paramSetInfo = (SendReciveDetailTranSetInfo)param;

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setUpdateInfoParam()
	 */
	@Override
	public void setUpdateInfoParam(Object param) {
		this.paramUpdateInfo = (UpdateInfo)param;

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#selectDataByKey()
	 */
	@Override
	public Object selectDataByKey(Connection con) {

		String send_recive_type = paramKeyInfo.getSend_recive_type();
		String year_month = paramKeyInfo.getYear_month();
		String empl_code = paramKeyInfo.getEmpl_code();
		String doc_name = paramKeyInfo.getDoc_name();
		String doc_type = paramKeyInfo.getDoc_type();

	 //  	Connection con = null;
	   	PreparedStatement stm = null;
	    ResultSet rs = null;

		SendReciveDetailTranDto dto = new SendReciveDetailTranDto();

        String selectSql = "SELECT * FROM doc.SendReciveDetailTran "
        				+ "WHERE "
						+ "  send_recive_type = ? AND year_month = ? AND empl_code = ? "
						+ "  AND doc_name = ? AND doc_type = ?";

     //   con = getConn();

        try {
        	stm = con.prepareStatement(selectSql);
        	stm.setString(1, send_recive_type);
        	stm.setString(2, year_month);
        	stm.setString(3, empl_code);
        	stm.setString(4, doc_name);
        	stm.setString(5, doc_type);
            logger.info(selectSql);
            rs = stm.executeQuery();
			while (rs.next()) {
				dto.setSend_recive_type(rs.getString("send_recive_type"));
				dto.setYear_month(rs.getString("year_month"));
				dto.setEmpl_code(rs.getString("empl_code"));
				dto.setDoc_name(rs.getString("doc_name"));
				dto.setDoc_type(rs.getString("doc_type"));
				dto.setOperation_type(rs.getString("operation_type"));
				dto.setSend_recive_datetime(rs.getString("send_recive_datetime"));
				dto.setCheck_status(rs.getString("check_status"));
				dto.setCheck_datetime(rs.getString("check_datetime"));
				dto.setFinger_body(rs.getString("finger_body"));
				dto.setInsert_dateTime(rs.getString("insert_dateTime"));
				dto.setInsert_userId(rs.getString("insert_userId"));
				dto.setInsert_programId(rs.getString("insert_programId"));
				dto.setUpdate_dateTime(rs.getString("update_dateTime"));
				dto.setUpdate_userId(rs.getString("update_userId"));
				dto.setUpdate_programId(rs.getString("update_programId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

        try {
            if ((rs != null)||(stm != null)||(con != null)) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return dto;
	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#insertData()
	 * @param execTranFlag 0:メソッド内でのコミット禁止
	 * @param con DBコネクション execTranFlagが0以外の場合はNULL
	 */
	@Override
	public Object insertData(int execTranFlag, Connection con) {

		String send_recive_type = paramValusInfo.getSend_recive_type();
		String year_month = paramValusInfo.getYear_month();
		String empl_code = paramValusInfo.getEmpl_code();
		String doc_name = paramValusInfo.getDoc_name();
		String doc_type = paramValusInfo.getDoc_type();
		String operation_type = paramValusInfo.getOperation_type();
		String send_recive_datetime = paramValusInfo.getSend_recive_datetime();
		String check_status = paramValusInfo.getCheck_status();
		String check_datetime = paramValusInfo.getCheck_datetime();
		String finger_body = paramValusInfo.getFinger_body();
		String insert_dateTime = paramValusInfo.getInsert_dateTime();
		String insert_userId = paramValusInfo.getInsert_userId();
		String insert_programId = paramValusInfo.getInsert_programId();
		String update_dateTime = paramValusInfo.getUpdate_dateTime();
		String update_userId = paramValusInfo.getUpdate_userId();
		String update_programId = paramValusInfo.getUpdate_programId();

	   	Connection selfCon = null;
	    PreparedStatement stm = null;
	    int rs = 0;

		String selectSql = "INSERT INTO doc.SendReciveDetailTran ( "
				+ " send_recive_type, year_month, empl_code, doc_name, doc_type, operation_type,"
				+ " send_recive_datetime, check_status, check_datetime, finger_body, insert_dateTime,"
				+ " insert_userId, insert_programId, update_dateTime, update_userId, update_programId) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    if (execTranFlag !=0 ) {
	    	selfCon = getConn();
	    }

        try {
			try {
			    if (execTranFlag !=0 ) {
			    	stm = selfCon.prepareStatement(selectSql);
			    } else {
					stm = con.prepareStatement(selectSql);
			    }
				stm.setString(1, send_recive_type);
				stm.setString(2, year_month);
				stm.setString(3, empl_code);
				stm.setString(4, doc_name);
				stm.setString(5, doc_type);
				stm.setString(6, operation_type);
				stm.setString(7, send_recive_datetime);
				stm.setString(8, check_status);
				stm.setString(9, check_datetime);
				stm.setString(10, finger_body);
				stm.setString(11, insert_dateTime);
				stm.setString(12, insert_userId);
				stm.setString(13, insert_programId);
				stm.setString(14, update_dateTime);
				stm.setString(15, update_userId);
				stm.setString(16, update_programId);
			    logger.info(selectSql);
			    rs = stm.executeUpdate();

			    if (execTranFlag !=0 ) {
			    	selfCon.commit();
			    }
			} catch (SQLException e) {

			    if (execTranFlag !=0 ) {
			    	selfCon.rollback();
			    }

			    e.printStackTrace();

			    if (execTranFlag !=0 ) {
			    	logger.error("コミット処理エラー：" + e.getMessage());
			    }
			}
		} catch (SQLException e1) {
			e1.printStackTrace();

		    if (execTranFlag !=0 ) {
		    	logger.error("ロールバック処理エラー：" + e1.getMessage());
		    }
		}

        logger.info("登録結果:"+ rs);
        return rs;
	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#updateDataByKey()
	 * @param execTranFlag 0:メソッド内でのコミット禁止
	 * @param con DBコネクション execTranFlagが0以外の場合はNULL
	 */
	@Override
	public Object updateDataByKey(int execTranFlag, Connection con) {

		String send_recive_type = paramKeyInfo.getSend_recive_type();
		String year_month = paramKeyInfo.getYear_month();
		String empl_code = paramKeyInfo.getEmpl_code();
		String doc_name = paramKeyInfo.getDoc_name();
		String doc_type = paramKeyInfo.getDoc_type();

		String update_dateTime = paramUpdateInfo.getUpdate_dateTime();
		String update_userId = paramUpdateInfo.getUpdate_userId();
		String update_programId = paramUpdateInfo.getUpdate_programId();

	   	Connection selfCon = null;
	   	PreparedStatement stm = null;
	    int rs = 0;

		String selectSql = "UPDATE doc.SendReciveDetailTran "
						+ updateSetCondition()
						+ " ,update_dateTime = ?, update_userId = ?, update_programId= ? "
        				+ "WHERE "
						+ "  send_recive_type = ? AND year_month = ? AND empl_code = ? "
						+ "  AND doc_name = ? AND doc_type = ?";

	    if (execTranFlag !=0 ) {
	    	selfCon = getConn();
	    }

        try {
			try {
			    if (execTranFlag !=0 ) {
			    	stm = selfCon.prepareStatement(selectSql);
			    } else {
					stm = con.prepareStatement(selectSql);
			    }
				stm.setString(1, update_dateTime);
				stm.setString(2, update_userId);
				stm.setString(3, update_programId);
				stm.setString(4, send_recive_type);
				stm.setString(5, year_month);
				stm.setString(6, empl_code);
				stm.setString(7, doc_name);
				stm.setString(8, doc_type);
			    logger.info(selectSql);
			    rs = stm.executeUpdate();

			    if (execTranFlag !=0 ) {
			    	selfCon.commit();
			    }
			} catch (SQLException e) {

				if (execTranFlag !=0 ) {
					selfCon.rollback();
			    }

				e.printStackTrace();

				if (execTranFlag !=0 ) {
					logger.error("コミット処理エラー：" + e.getMessage());
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();

			if (execTranFlag !=0 ) {
				logger.error("ロールバック処理エラー：" + e1.getMessage());
			}
		}

        logger.info("更新結果:"+ rs);
        return rs;
	}

	/*
	 * UPDATE文のSET句を動的に文字列を生成
	 */
	private String updateSetCondition() {

		String Condition = "SET ";
		String Connma = "";

		if (!this.paramSetInfo.getOperation_type().equals("EMPTY")) {
			Condition += "operation_type = '" + this.paramSetInfo.getOperation_type() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getSend_recive_datetime().equals("EMPTY")) {
			Condition += Connma + "send_recive_datetime = '" + this.paramSetInfo.getSend_recive_datetime() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getCheck_status().equals("EMPTY")) {
			Condition += Connma + "check_status = '" + this.paramSetInfo.getCheck_status() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getCheck_datetime().equals("EMPTY")) {
			Condition += Connma + "check_datetime = '" + this.paramSetInfo.getCheck_datetime() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getFinger_body().equals("EMPTY")) {
			Condition += Connma + "finger_body = '" + this.paramSetInfo.getFinger_body() + "' ";
		}

		return Condition;
	}


	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#deleteDataByKey()
	 * @param execTranFlag 0:メソッド内でのコミット禁止
	 * @param con DBコネクション execTranFlagが0以外の場合はNULL
	 */
	@Override
	public Object deleteDataByKey(int execTranFlag, Connection con) {

		String send_recive_type = paramKeyInfo.getSend_recive_type();
		String year_month = paramKeyInfo.getYear_month();
		String empl_code = paramKeyInfo.getEmpl_code();
		String doc_name = paramKeyInfo.getDoc_name();
		String doc_type = paramKeyInfo.getDoc_type();


	   	Connection selfCon = null;
	   	PreparedStatement stm = null;
	    int rs = 0;

		String selectSql = "DELETE FROM doc.SendReciveDetailTran "
						+ "WHERE send_recive_type = ? AND year_month = ? AND empl_code = ? AND doc_name = ? AND doc_type = ? ";

	    if (execTranFlag !=0 ) {
	    	selfCon = getConn();
	    }

        try {
			try {
			    if (execTranFlag !=0 ) {
			    	stm = selfCon.prepareStatement(selectSql);
			    } else {
					stm = con.prepareStatement(selectSql);
			    }
				stm.setString(1, send_recive_type);
				stm.setString(2, year_month);
				stm.setString(3, empl_code);
				stm.setString(4, doc_name);
				stm.setString(5, doc_type);

			    logger.info(selectSql);
			    rs = stm.executeUpdate();

			    if (execTranFlag !=0 ) {
			    	selfCon.commit();
			    }
			} catch (SQLException e) {

				if (execTranFlag !=0 ) {
					selfCon.rollback();
			    }

				e.printStackTrace();

				if (execTranFlag !=0 ) {
					logger.error("コミット処理エラー：" + e.getMessage());
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();

			if (execTranFlag !=0 ) {
				logger.error("ロールバック処理エラー：" + e1.getMessage());
			}
		}

        logger.info("削除結果:"+ rs);
        return rs;

	}

}
