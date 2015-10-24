package com.saike.grape.callcenter.sevice.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*import com.saic.ebiz.agent.service.api.IMaintenanceService;
import com.saic.ebiz.agent.service.entity.OBMaintenData;*/
import com.saic.ebiz.eadccc.service.api.callcenter.IMaintenanceService;
import com.saic.ebiz.eadccc.service.vo.callcenter.OBMaintenData;
import com.saike.grape.callcenter.dao.impl.CallCenterDaoImpl;
import com.saike.grape.callcenter.sevice.api.BatchCallCenterService;
import com.saike.grape.callcenter.sevice.util.ServiceFactoryUtil;



/**
 * 定时向呼叫中心发送数据
 * 
 * @author 袁中述
 * 
 */
public class BatchCallCenterServiceImpl  implements BatchCallCenterService {
	 /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(BatchCallCenterServiceImpl.class);
	//private Logger logger = Logger.getLogger(this.getClass());
	public static  ArrayList<String> orderList=new  ArrayList<String>();

	@Override
	public  boolean addorderNo(String orderNo) {
		addList(orderNo);
		return true;
	}
	
	private synchronized void addList(String orderNo){
		orderList.add(orderNo);
	}
	private synchronized void deleteOrderNo(String orderNo){
		for (int i = 0; i < orderList.size(); i++) {
			if(orderNo.equals(orderList.get(i))){
				orderList.remove(i);
			}
		}
	}
	private synchronized void deleteOrderList(ArrayList<String> list){
		/*int size=list.size();
		for (int i = size; i >=0; i--) {
			if(i<size){
				list.remove(i);
			}
		
		}*/
		for(int i=0;i<list.size();i++){
			for(int j=orderList.size()-1;j>=0;j--){
				if(list.get(i).equals(orderList.get(j))){
					orderList.remove(j);
				}
			}
		}
	}
	/**
	 * 定时扫描数据库订单状态，有待确认的订单就向call center发送
	 */
	public void work() {
		logger.info("Quartz start！！！size:"+orderList.size()+"  order:"+orderList.toString());
		List<HashMap<String, String>>listMap =new ArrayList<>();
		if(orderList.size()>0){
			 listMap = CallCenterDaoImpl.getUserList(orderList);
		}		
		List<OBMaintenData> list = new ArrayList<>();
		ArrayList<String> listOrderNo = new ArrayList<>();
		if (listMap.size() > 0) {
			for (int i = 0; i < listMap.size(); i++) {
				HashMap<String, String> map = listMap.get(i);
				if (map.get("status").equals("未确认")) {
                     OBMaintenData maintenData=new OBMaintenData();
                     maintenData.setCustName(map.get("userName"));
                     maintenData.setCustPhone(map.get("userMobilePhone"));
                     String orderNo=map.get("code");
                     maintenData.setOrderId(orderNo);
                     listOrderNo.add(orderNo);
                     String createTime=map.get("createdDatetime");
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     String maintenTime=map.get("beginDatetime")+"-"+map.get("endDatetime");
                     maintenData.setMaintenTime(maintenTime);
                     try {
    					maintenData.setCreateOrderTime(sdf.parse(createTime));
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
                     long   dealerId= Long.parseLong(map.get("dealerCode"));
                     maintenData.setDealerId(dealerId);
                     String name=map.get("dealerName");
                     maintenData.setDealerName(name);
                    	/*maintenData.setUserName(map.get("dealerName").split(",")[0]);
                        maintenData.setUserPhone(map.get("dealerMobile").split(",")[0]);*/
                    	//maintenData.setUserName(name.substring(0,7));
                        maintenData.setUserPhone(map.get("dealerPhone"));
                    list.add(maintenData);
				} else {
					deleteOrderNo(map.get("code"));
				}
			}
			int flag=0;
			if(list.size()>0){			
				flag=venusCallcenterClient(list);
				logger.info("call center:"+flag);
			}		
			if(flag==1){				
				deleteOrderList(listOrderNo);  
			}
			
		}
		logger.info("Quartz end！！！size:"+orderList.size()+"order:"+orderList.toString());
	  }
	private int venusCallcenterClient(List<OBMaintenData> list){
		IMaintenanceService  maintenanceService=ServiceFactoryUtil.getServiceFactory().getService(IMaintenanceService.class);
	   return maintenanceService.saveMaintenList(list);
	}

}
