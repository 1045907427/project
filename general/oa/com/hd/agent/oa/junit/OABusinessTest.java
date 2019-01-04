/**
 * @(#)UsersServiceTest.java
 *
 * @author chenwei
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-7 chenwei 创建版本
 */
package com.hd.agent.oa.junit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.oa.model.OaAccess;
import com.hd.agent.oa.model.OaAccessBrandDiscount;
import com.hd.agent.oa.model.OaAccessGoodsPrice;
import com.hd.agent.oa.service.IOaAccessToBusinessService;

/**
 * 
 * usersService测试
 * @author chenwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:WebRoot/WEB-INF/applicationContext.xml"})
public class OABusinessTest {
	@Autowired
	private IOaAccessToBusinessService oaAccessToBusinessService;
	/**
	 * 
	 * 测试查询用户是否正常
	 * @author chenwei 
	 * @date 2012-12-7
	 */
	@Test
	public void showUsersListTest(){
		OaAccess oaAccess = new OaAccess();
		oaAccess.setSupplierid("1202");
		oaAccess.setCustomerid("50000");
		oaAccess.setCombegindate("2014-10-01");
		oaAccess.setComenddate("2014-10-31");
		oaAccess.setId("100000");
		oaAccess.setOaid("123456");
		oaAccess.setPricetype("2");
		List<OaAccessGoodsPrice> goodsPricelist = new ArrayList<OaAccessGoodsPrice>();
		OaAccessGoodsPrice accessGoodsPrice = new OaAccessGoodsPrice();
		accessGoodsPrice.setBillid("100000");
		accessGoodsPrice.setGoodsid("1011001");
		accessGoodsPrice.setOldprice(new BigDecimal("4.500000"));
		accessGoodsPrice.setNewprice(new BigDecimal("3.500000"));
		goodsPricelist.add(accessGoodsPrice);
		
		List<OaAccessBrandDiscount> brandDiscountList = new ArrayList();
		OaAccessBrandDiscount accessBrandDiscount = new OaAccessBrandDiscount();
		accessBrandDiscount.setBillid("100000");
		accessBrandDiscount.setBrandid("101");
		accessBrandDiscount.setAmount(new BigDecimal(100));
		brandDiscountList.add(accessBrandDiscount);
		oaAccess.setPricetype("1");
		oaAccess.setPaytype("2");
//		oaAccess.setFactoryamount(new BigDecimal(1000));
		oaAccess.setCompdiscount(new BigDecimal(100));
		oaAccess.setPayamount(new BigDecimal(100));
		oaAccess.setTotalamount(new BigDecimal(123));
		try {
			//测试降价特价
//			Map map = oaAccessToBusinessService.addOffPriceByOaAccess(oaAccess, goodsPricelist);
//			Map map = oaAccessToBusinessService.addBusinessBillByOaAccess(oaAccess, brandDiscountList);
//			boolean flag = oaAccessToBusinessService.addCustomerCostPayableByOaAccess(oaAccess);
//			boolean flag = oaAccessToBusinessService.rollbackCustomerCostPayableByOaAccess(oaAccess);
//			boolean flag = oaAccessToBusinessService.updateCustomerCostPayableColse(oaAccess);
			boolean flag = oaAccessToBusinessService.updateCustomerCostPayableOpen(oaAccess);
			System.out.println(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

