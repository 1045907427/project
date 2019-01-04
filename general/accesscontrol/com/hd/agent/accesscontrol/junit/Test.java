/**
 * @(#)Test.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-13 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.junit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * 
 * @author chenwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:WebRoot/WEB-INF/applicationContext.xml"})
public class Test {
	
	@org.junit.Test
	public void commonUtiltest(){
//		String pwd = CommonUtils.MD5("123456");
//		System.out.println(pwd);
		
	}
}

