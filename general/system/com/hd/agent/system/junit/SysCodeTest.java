/**
 * @(#)SysCodeTest.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 panxiaoxiao 创建版本
 */
package com.hd.agent.system.junit;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.service.ISysCodeService;
import net.sf.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * 
 *公共代码测试
 * @author panxiaoxiao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebRoot/WEB-INF/applicationContext.xml")
public class SysCodeTest {
    static {
        try {
            Log4jConfigurer.initLogging("file:WebRoot/WEB-INF/log4j.properties");
        } catch (FileNotFoundException ex) {
            System.err.println("Cannot Initialize log4j");
        }
    }
	@Autowired
	private ISysCodeService sysCodeService;
	
	@Autowired
	private BaseAction baseAction;
	
	@Test
	public void showSysCodeList(){
		List list=null;
		try{
			list=sysCodeService.showSysCodeList();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(list);
	}
	
	@Test
	public void addSysCode() throws Exception{
		SysCode sysCode=new SysCode();
		sysCode.setCode("ac");
		sysCode.setCodename("BB");
		sysCode.setSeq(4);
		sysCode.setState("0");
		sysCode.setType("cc");
		sysCode.setTypename("CC");
		boolean flag=sysCodeService.addSysCode(sysCode);
		baseAction.addJSONObject("flag", flag);
		System.out.println(flag);
	}
	
	@Test
	public void showSysCodeTypes() throws Exception{
		List typeList=sysCodeService.showSysCodeTypes();
		JSONArray jsonResult = JSONArray.fromObject(typeList);
		System.out.println(typeList);
	}
	
	@Test
	public void SysCodeListByType() throws Exception{
		List listByType=sysCodeService.showSysCodeListByType("module");
		System.out.println(listByType);
	}
}

