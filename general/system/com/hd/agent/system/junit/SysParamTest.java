/**
 * @(#)SysParamTest.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-20 panxiaoxiao 创建版本
 */
package com.hd.agent.system.junit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysCodeService;
import com.hd.agent.system.service.ISysNumberRuleService;
import com.hd.agent.system.service.ISysParamService;

/**
 * 
 * 
 * @author panxiaoxiao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebRoot/WEB-INF/applicationContext.xml")
public class SysParamTest {
	@Autowired
	private ISysParamService sysParamService;
	
	@Autowired
	private ISysCodeService sysCodeService;
	
	@Autowired
	private ISysNumberRuleService sysNumberRuleService;
	@Test
	public void showSysParamList(){
		List list=null;
		try{
			list=sysParamService.showSysParamList();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(list);
	}
	
	@Test
	public void showSysCodeCodes() throws Exception{
		List codeList=sysCodeService.showSysCodeCodes();
		JSONArray jsonResult = JSONArray.fromObject(codeList);
		System.out.println(codeList);
	}
	
	@Test
	public void  searchPname() throws Exception{
		boolean flag=sysParamService.searchPname("sdf");
		System.out.println(flag);
	}
	
	@Test
	public void addPname()throws Exception{
		SysParam sysParam=new SysParam();
		sysParam.setParamid("");
		sysParam.setPname("sfsd");
		sysParam.setPuser("sdfdg");
		sysParam.setPvalue("hj");
		sysParam.setPvdescription("sdfg");
		sysParam.setState("1");
		sysParam.setModuleid("gdf");
		sysParam.setDescription("dggh");
		boolean flag=sysParamService.addSysParam(sysParam);
		System.out.println(flag);
	}
	
	@Test
	public void getAutoCreateSysNumbderCode()throws Exception{
		Map authority=new HashMap();
		authority.put("authorityid","Authorityid");
		authority.put("authorityname","sdfg");
		authority.put("description","豆腐干豆腐花");
		Map map=sysNumberRuleService.setAutoCreateSysNumbderForeign(authority, "t_ac_authoper");
		System.out.println(map);
	}
}

