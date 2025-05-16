/**
 * (概要)
 *  SendReciveTranテーブルのSQL実行クラス
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
import dto.SendReciveTranDtob;
import interfe.CommonTableAccessbIF;
import struct.SendReciveTranKeybInfo;
import struct.SendReciveTranSetbInfo;
import struct.UpdateInfo;

/**
 * @author D.Ikeda
 *
 */
public class SendReciveTranDaob extends BaseServlet implements CommonTableAccessbIF {

	private SendReciveTranKeybInfo paramKeyInfo = null;

	private SendReciveTranDtob paramValusInfo = null;

	private SendReciveTranSetbInfo paramSetInfo = null;

	private UpdateInfo paramUpdateInfo = null;

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setParam()
	 */
	@Override
	public void setParam(Object param) {

		this.paramKeyInfo = (SendReciveTranKeybInfo)param;

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setValuesParam()
	 */
	@Override
	public void setValuesParam(Object param) {

		this.paramValusInfo = (SendReciveTranDtob)param;

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setSetParam()
	 */
	@Override
	public void setSetParam(Object param) {
		this.paramSetInfo = (SendReciveTranSetbInfo)param;

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

	  // 	Connection con = null;
	    PreparedStatement stm = null;
	    ResultSet rs = null;

		SendReciveTranDtob dto = new SendReciveTranDtob();

        String selectSql = "SELECT * FROM doc.SendReciveTran "
        				+ " WHERE send_recive_type = ? AND year_month = ? AND empl_code = ?";

       // con = getConn();

        try {
        	stm = con.prepareStatement(selectSql);
        	stm.setString(1, send_recive_type);
        	stm.setString(2, year_month);
        	stm.setString(3, empl_code);
            logger.info(selectSql);
            rs = stm.executeQuery();
			while (rs.next()) {
				dto.setSend_recive_type(rs.getString("send_recive_type"));
				dto.setYear_month(rs.getString("year_month"));
				dto.setEmpl_code(rs.getString("empl_code"));
				dto.setDoc_name(rs.getString("doc_name"));
				dto.setOperation_type(rs.getString("operation_type"));
				dto.setLast_send_recive_datetime(rs.getString("last_send_recive_datetime"));
				dto.setInsert_status(rs.getString("insert_status"));
				dto.setStatus(rs.getString("status"));
				dto.setDetail_num(rs.getString("detail_num"));
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
		String operation_type = paramValusInfo.getOperation_type();
		String last_send_recive_datetime = paramValusInfo.getLast_send_recive_datetime();
		String insert_status = paramValusInfo.getInsert_status();
		String status = paramValusInfo.getStatus();
		String detail_num = paramValusInfo.getDetail_num();
		String insert_dateTime = paramValusInfo.getInsert_dateTime();
		String insert_userId = paramValusInfo.getInsert_userId();
		String insert_programId = paramValusInfo.getInsert_programId();
		String update_dateTime = paramValusInfo.getUpdate_dateTime();
		String update_userId = paramValusInfo.getUpdate_userId();
		String update_programId = paramValusInfo.getUpdate_programId();

	   	Connection selfCon = null;
	    PreparedStatement stm = null;
	    int rs = 0;

		String selectSql = "INSERT INTO doc.SendReciveTran ("
				+ " send_recive_type, year_month, empl_code, doc_name, operation_type, last_send_recive_datetime,"
				+ " insert_status, status, detail_num, insert_dateTime, insert_userId, insert_programId,"
				+ " update_dateTime, update_userId, update_programId) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
				stm.setString(5, operation_type);
				stm.setString(6, last_send_recive_datetime);
				stm.setString(7, insert_status);
				stm.setString(8, status);
				stm.setString(9, detail_num);
				stm.setString(10, insert_dateTime);
				stm.setString(11, insert_userId);
				stm.setString(12, insert_programId);
				stm.setString(13, update_dateTime);
				stm.setString(14, update_userId);
				stm.setString(15, update_programId);
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

		String update_dateTime = paramUpdateInfo.getUpdate_dateTime();
		String update_userId = paramUpdateInfo.getUpdate_userId();
		String update_programId = paramUpdateInfo.getUpdate_programId();

	   	Connection selfCon = null;
	   	PreparedStatement stm = null;
	    int rs = 0;

		String selectSql = "UPDATE doc.SendReciveTran "
						+ updateSetCondition()
						+ " ,update_dateTime = ?, update_userId = ?, update_programId = ? "
						+ "WHERE send_recive_type = ? AND year_month = ? AND empl_code = ? ";

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

		if (!this.paramSetInfo.getDoc_name().equals("EMPTY")) {
			Condition += "doc_name = '" + this.paramSetInfo.getDoc_name() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getOperation_type().equals("EMPTY")) {
			Condition += Connma + "operation_type = '" + this.paramSetInfo.getOperation_type() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getLast_send_recive_datetime().equals("EMPTY")) {
			Condition += Connma + "last_send_recive_datetime = '" + this.paramSetInfo.getLast_send_recive_datetime() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getInsert_status().equals("EMPTY")) {
			Condition += Connma + "insert_status = '" + this.paramSetInfo.getInsert_status() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getStatus().equals("EMPTY")) {
			Condition += Connma + "status = '" + this.paramSetInfo.getStatus() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getDetail_num().equals("EMPTY")) {
			Condition += Connma + "detail_num = '" + this.paramSetInfo.getDetail_num() + "' ";
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

	   	Connection selfCon = null;
	   	PreparedStatement stm = null;
	    int rs = 0;

		String selectSql = "DELETE FROM doc.SendReciveTran "
						+ "WHERE send_recive_type = ? AND year_month = ? AND empl_code = ? ";

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
