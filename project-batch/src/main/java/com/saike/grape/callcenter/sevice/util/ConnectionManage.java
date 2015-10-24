package com.saike.grape.callcenter.sevice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.log4j.Logger;

public class ConnectionManage {
	private static final String JDBC_DRIVER_RIGIDA = "com.mysql.jdbc.Driver";
	private static final String JDBC_URL_RIGIDA = "jdbc:mysql://10.32.17.17/grape?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
	private static final String JDBC_USERNAME_RIGIDA = "grape";
	private static final String JDBC_PASSWORD_RIGIDA = "grape";

	private static Logger logger = Logger.getLogger(ConnectionManage.class);

	static ThreadLocal<Connection> rigidaLocal = new ThreadLocal<Connection>();

	public static Connection getRegidaLocalConnection() {
		Connection conn = null;
		if (rigidaLocal.get() == null) {
			try {
				Class.forName(JDBC_DRIVER_RIGIDA);
				DriverManager.setLoginTimeout(1*60*60*20);
				conn = DriverManager.getConnection(JDBC_URL_RIGIDA,JDBC_USERNAME_RIGIDA, JDBC_PASSWORD_RIGIDA);
				rigidaLocal.set(conn);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获取数据库连接失败");
			}
		} else {
			conn = rigidaLocal.get();
			try {
				if (conn.isClosed()) {
					rigidaLocal.remove();
					Class.forName(JDBC_DRIVER_RIGIDA);
					DriverManager.setLoginTimeout(1*60*60*20);
					conn = DriverManager.getConnection(JDBC_URL_RIGIDA,
							JDBC_USERNAME_RIGIDA, JDBC_PASSWORD_RIGIDA);
					rigidaLocal.set(conn);
					System.out.println("获取连接22222========================");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获取数据库连接失败", e);
			}

		}
		return conn;
	}
}
