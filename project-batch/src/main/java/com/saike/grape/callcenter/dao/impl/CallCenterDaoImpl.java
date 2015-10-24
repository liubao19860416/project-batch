package com.saike.grape.callcenter.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.saike.grape.callcenter.sevice.util.DataBaseUtil;


public class CallCenterDaoImpl {

     /**
	 * 批量返回用户信息
	 */
	public static List<HashMap<String, String>> getUserList(ArrayList<String> idList) {

		List<HashMap<String, String>> list = null;
		if (idList == null || idList.size() == 0) {
			return new ArrayList<>();
		}
		String tableName = " t_user_order ";
		String colname =  " * ";
		String conditionCol = " code ";
		try {
			list = DataBaseUtil.queryInfoList(idList, tableName, conditionCol,colname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public static void main(String[] args) {
		LinkedHashMap<String, String> conditionMap = new LinkedHashMap<>();
		conditionMap.put("USERID", "135857");
		conditionMap.put("APPID", "10000");
		conditionMap.put("PASSWORD", "46cc45a727bec7de97f9e738ad428153");

		//String tableName = "T_USERINFO";
	}

}
