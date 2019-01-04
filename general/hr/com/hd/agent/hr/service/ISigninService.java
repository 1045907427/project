/**
 * @(#)ISignService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-18 limin 创建版本
 */
package com.hd.agent.hr.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.hr.model.Signin;

import java.util.Map;

/**
 * 签到Service
 * 
 * @author limin
 */
public interface ISigninService {

	/**
	 * 查询签到List
	 * @param map
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-9-18
	 */
	public PageData selectSigninList(PageMap map) throws Exception;
	
	/**
	 * 查询签到详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-9-20
	 */
	public Signin selectSigninInfo(String id) throws Exception;


    /**
     * 删除签到
     * @param ids
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-3-12
     */
    public Map deleteSignin(String ids) throws Exception;
}

