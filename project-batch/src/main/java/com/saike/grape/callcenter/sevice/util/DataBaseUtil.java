package com.saike.grape.callcenter.sevice.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DataBaseUtil {

	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(DataBaseUtil.class);

	public static List<HashMap<String, String>> queryInfo(String sql)
			throws Exception {
		List<HashMap<String, String>> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet rs = null;
		logger.info("sql:" + sql);
		try {
			conn = DBUtil.getConnection();
			prst = conn.prepareStatement(sql);
			rs = prst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				HashMap<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String name = rsmd.getColumnName(i + 1);
					String value = rs.getString(i + 1);
					map.put(name, value);
				}
				list.add(map);

			}
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("查询失败，SQL为：" + sql);
			throw e;
		} finally {
			DbUtils.close(conn);
		}
		return list;

	}

	public static List<HashMap<String, String>> queryInfoList(
			ArrayList<String> idList, String tableName, String conditionCol,
			String colname) throws Exception {
		StringBuffer sql = new StringBuffer("select ");
		sql.append(colname);
		sql.append(" from ");
		sql.append(tableName);
		sql.append(" where 1=1 ");
		sql.append(" and ");
		sql.append(conditionCol);
		sql.append(" in (");
		int size = idList.size();
		for (int i = 0; i < size; i++) {
			sql.append("'").append(idList.get(i)).append("'");
			if (i != size - 1) {
				sql.append(",");
			}
		}
		sql.append(") ");
		List<HashMap<String, String>> list = DataBaseUtil.queryInfo(sql
				.toString());
		return list;
	}
}
