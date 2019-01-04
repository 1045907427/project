/**
 * @(#)ReturnOrderTest.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-6-24 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.service.IReturnOrderService;

/**
 * 
 * 
 * @author zhanghonghui
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebRoot/WEB-INF/applicationContext.xml")
public class ReturnOrderTest {
	@Autowired
	public IReturnOrderService returnOrderService;
	
	@Test
	public void showReturnOrderAndDetailList() throws Exception{
		try{
		ReturnOrder returnOrder=returnOrderService.showPureReturnOrderAndDetail("CGTHD-2013-000004");
		if(null!=returnOrder){
			System.out.println(returnOrder);
			if(null!=returnOrder.getReturnOrderDetailList()){
				System.out.println(returnOrder.getReturnOrderDetailList());
			}
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

