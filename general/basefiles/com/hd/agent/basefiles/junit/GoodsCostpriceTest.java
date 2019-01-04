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
package com.hd.agent.basefiles.junit;


import com.hd.agent.basefiles.service.IBaseFilesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.basefiles.service.IGoodsService;

/**
 * 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("file:WebRoot/WEB-INF/applicationContext.xml")
public class GoodsCostpriceTest {
	@Autowired
	private IBaseFilesService baseFilesService;
	@Autowired
	private GoodsMapper goodsMapper;
	@Test
	public void goodsCostpriceChange()throws Exception{
		baseFilesService.doAutoUpdateBillByBaseFiles();
	}
}

