package com.saike.grape.callcenter.sevice.api;

import com.meidusa.venus.annotations.Endpoint;
import com.meidusa.venus.annotations.Param;
import com.meidusa.venus.annotations.Service;

/**
 * 
 * @author 袁忠述
 * 
 */
@Service(name = "BatchCallCenterService", version = 1)
public interface BatchCallCenterService {

	/**
	 * 登陆
	 * 
	 * @param account
	 * @param password
	 * @param deviceId
	 * @param appId
	 * @return
	 */
	@Endpoint(name = "addorderNo", async = false)
	public boolean addorderNo(@Param(name = "orderNo") String orderNo);

}
