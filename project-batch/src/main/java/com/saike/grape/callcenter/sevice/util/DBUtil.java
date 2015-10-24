package com.saike.grape.callcenter.sevice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class DBUtil {

    // private static DataSource ds = null;

    // static {
    // ApplicationContext ac = null;
    // try {
    // ac = new FileSystemXmlApplicationContext(
    // "/conf/applicationContext-rigida-jdbc.xml");
    // } catch (Exception e) {
    // ac = new FileSystemXmlApplicationContext(
    // "file:${project.home}/conf/applicationContext-rigida-jdbc.xml");
    // }
    // ds = (DataSource) ac.getBean("dataSource");
    // }
    // public static Connection getConnection() {
    // Connection conn = null;
    // try {
    // conn = ds.getConnection();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return conn;
    // }
    static {
        Map<String, String> map = CustomizedPropertyPlaceholderConfigurer.ctxPropertiesMap;
        try {
            Class.forName(CustomizedPropertyPlaceholderConfigurer
                    .getContextProperty("orm.mysql.jdbc.driverClassName"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            String url = CustomizedPropertyPlaceholderConfigurer
                    .getContextProperty("orm.mysql.jdbc.url.v20");
            if (url == null) {
                url = "jdbc:mysql://10.32.87.4:3306/grape20?useUnicode=true&characterEncoding=utf-8";

            }
            String user = CustomizedPropertyPlaceholderConfigurer
                    .getContextProperty("orm.mysql.jdbc.username.v20");
            if (user == null) {
                user = "grape20";
            }
            String pass = CustomizedPropertyPlaceholderConfigurer
                    .getContextProperty("orm.mysql.jdbc.password.v20");
            if (pass == null) {
                pass = "grape20";
            }
            conn = DriverManager.getConnection(url, user, pass);//
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
