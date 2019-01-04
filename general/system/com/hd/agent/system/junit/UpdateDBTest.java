/**
 * @(#)UpdateDBTest.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月20日 chenwei 创建版本
 */
package com.hd.agent.system.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.system.service.IUpdateDBService;

/**
 * 
 * 
 * @author chenwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebRoot/WEB-INF/applicationContext.xml")
public class UpdateDBTest {
	@Autowired
	private IUpdateDBService updateDBService;
	@Test
	public void updateDBTest(){
		try {
			updateDBService.updateDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

