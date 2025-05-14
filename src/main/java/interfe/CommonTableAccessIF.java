/**
 * (概要)
 *  複数のテーブルで共通で使用しそうなSQLのメソッドを
 *  定義したインタフェース
 *  作成者 ：D.Ikeda
 *  作成日 : 2021/05/16
 *  履歴： バージョン 日付        更新者
 *         L0.00      2021/05/16  D.Ikeda
 *         L0.01      2024/09/05  T.Usui
 */
package interfe;

import java.sql.Connection;

/**
 * @author D.Ikeda
 *
 */
public interface CommonTableAccessIF {

	//パラメータを引き渡す
	public void setParam(Object param);

	//セット値用のパラメータを引き渡す
	public void setValuesParam(Object param);

	//セット値用のパラメータを引き渡す
	public void setSetParam(Object param);

	//更新情報セット値用のパラメータを引き渡す
	public void setUpdateInfoParam(Object param);

	/**
	 * 主キーでのデータ更新
	 * @param con DBコネクション execTranFlagが0以外の場合はNULLをセット
	 */
	public Object selectDataByKey(Connection con);

	/**
	 * データ追加
	 * @param execTranFlag 0:メソッド内でのコミット禁止
	 * @param con DBコネクション execTranFlagが0以外の場合はNULLをセット
	 */
	public Object insertData(int execTranFlag, Connection con);

	/**
	 * 主キーでのデータ更新
	 * @param execTranFlag 0:メソッド内でのコミット禁止
	 * @param con DBコネクション execTranFlagが0以外の場合はNULLをセット
	 */
	public Object updateDataByKey(int execTranFlag, Connection con);

	/**
	 * 主キーでのデータ削除
	 * @param execTranFlag 0:メソッド内でのコミット禁止
	 * @param con DBコネクション execTranFlagが0以外の場合はNULLをセット
	 */
	public Object deleteDataByKey(int execTranFlag, Connection con);

}
