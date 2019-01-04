/**
 * @(#)LimitPriceOrderTest.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-6-22 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.junit;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.purchase.model.LimitPriceOrder;
import com.hd.agent.purchase.service.ILimitPriceOrderService;

/**
 * 
 * 
 * @author zhanghonghui
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebRoot/WEB-INF/applicationContext.xml")
public class LimitPriceOrderTest {

	@Autowired
	public ILimitPriceOrderService limitPriceOrderService;
	@Test
	public void getLimitPriceOrderUnEffectList() throws Exception{
		try{
			List<LimitPriceOrder> list=limitPriceOrderService.getLimitPriceOrderUnEffectList();
			if(null!=list){
				for(LimitPriceOrder item : list){
					System.out.println(item.getId());
				}
				System.out.println(list);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

