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
import dto.DocBodyDto;
import interfe.CommonTableAccessIF;
import struct.DocBodyKeyInfo;
import struct.DocBodySetInfo;
import struct.UpdateInfo;

/**
 * @author D.Ikeda
 *
 */
public class DocBodyDao extends BaseServlet implements CommonTableAccessIF {

	private DocBodyKeyInfo paramKeyInfo = null;

	private DocBodyDto paramValusInfo = null;

	private DocBodySetInfo paramSetInfo = null;

	private UpdateInfo paramUpdateInfo = null;

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setParam()
	 */
	@Override
	public void setParam(Object param) {

		this.paramKeyInfo = (DocBodyKeyInfo)param;

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setValuesParam()
	 */
	@Override
	public void setValuesParam(Object param) {

		this.paramValusInfo = (DocBodyDto)param;

	}

	/* (非 Javadoc)
	 * @see interfe.CommonTableAccessIF#setSetParam()
	 */
	@Override
	public void setSetParam(Object param) {
		this.paramSetInfo = (DocBodySetInfo)param;

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

		DocBodyDto dto = new DocBodyDto();

        String selectSql = "SELECT * FROM doc.DocBody "
        				+ "WHERE "
						+ "  send_recive_type = ? AND year_month = ? AND empl_code = ? "
						+ "  AND doc_name = ? AND doc_type = ?";
      //  con = getConn();

        try {
        	stm = con.prepareStatement(selectSql);
        	stm.setString(1, send_recive_type);
        	stm.setString(2, year_month);
        	stm.setString(3, empl_code);
        	stm.setString(4, doc_name);
        	stm.setString(5, doc_type);
            logger.info(selectSql);
            logger.info(selectSql);
            rs = stm.executeQuery();
			while (rs.next()) {
				dto.setSend_recive_type(rs.getString("send_recive_type"));
				dto.setYear_month(rs.getString("year_month"));
				dto.setEmpl_code(rs.getString("empl_code"));
				dto.setDoc_name(rs.getString("doc_name"));
				dto.setDoc_type(rs.getString("doc_type"));
				dto.setDoc_title(rs.getString("doc_title"));
				dto.setDoc_body(rs.getString("doc_body"));
				dto.setExpiration_from_dateTime(rs.getString("expiration_from_dateTime"));
				dto.setExpiration_to_dateTime(rs.getString("expiration_to_dateTime"));
				dto.setMail_send_recive_dateTime(rs.getString("mail_send_recive_dateTime"));
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
		String doc_title = paramValusInfo.getDoc_title();
		String doc_body = paramValusInfo.getDoc_body();
		String expiration_from_dateTime = paramValusInfo.getExpiration_from_dateTime();
		String expiration_to_dateTime = paramValusInfo.getExpiration_to_dateTime();
		String mail_send_recive_dateTime = paramValusInfo.getMail_send_recive_dateTime();
		String insert_dateTime = paramValusInfo.getInsert_dateTime();
		String insert_userId = paramValusInfo.getInsert_userId();
		String insert_programId = paramValusInfo.getInsert_programId();
		String update_dateTime = paramValusInfo.getUpdate_dateTime();
		String update_userId = paramValusInfo.getUpdate_userId();
		String update_programId = paramValusInfo.getUpdate_programId();

	   	Connection selfCon = null;
	    PreparedStatement stm = null;
	    int rs = 0;

		String selectSql = "INSERT INTO doc.DocBody ( "
				+ " send_recive_type, year_month, empl_code, doc_name, doc_type, doc_title, doc_body,"
				+ " expiration_from_datetime, expiration_to_datetime, mail_send_recive_datetime, insert_dateTime,"
				+ " insert_userId, insert_programId, update_dateTime, update_userId, update_programId ) "
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
				stm.setString(6, doc_title);
				stm.setString(7, doc_body);
				stm.setString(8, expiration_from_dateTime);
				stm.setString(9, expiration_to_dateTime);
				stm.setString(10, mail_send_recive_dateTime);
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

		String selectSql = "UPDATE doc.DocBody "
						+ updateSetCondition()
						+ " ,update_dateTime=?, update_userId=?, update_programId=? "
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

		if (!this.paramSetInfo.getDoc_title().equals("EMPTY")) {
			Condition += "doc_title = '" + this.paramSetInfo.getDoc_title() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getDoc_body().equals("EMPTY")) {
			Condition += Connma + "doc_body = '" + this.paramSetInfo.getDoc_body() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getExpiration_from_dateTime().equals("EMPTY")) {
			Condition += Connma + "expiration_from_dateTime = '" + this.paramSetInfo.getExpiration_from_dateTime() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getExpiration_to_dateTime().equals("EMPTY")) {
			Condition += Connma + "expiration_to_dateTime = '" + this.paramSetInfo.getExpiration_to_dateTime() + "' ";
			Connma = ",";
		}

		if (!this.paramSetInfo.getMail_send_recive_dateTime().equals("EMPTY")) {
			Condition += Connma + "mail_send_recive_dateTime = '" + this.paramSetInfo.getMail_send_recive_dateTime() + "' ";
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

		String selectSql = "DELETE FROM doc.DocBody "
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
