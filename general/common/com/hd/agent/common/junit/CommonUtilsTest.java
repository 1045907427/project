/**
 * @author chenwei
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-7 chenwei 创建版本
 */
package com.hd.agent.common.junit;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.service.IDepartMentService;
import com.hd.agent.basefiles.service.IGoodsService;
import com.hd.agent.basefiles.service.IPersonnelService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.common.util.OfficeUtils;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.ArrivalOrderDetail;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:WebRoot/WEB-INF/applicationContext.xml"})
public class CommonUtilsTest extends BaseAction{
	@Autowired
	private IDepartMentService departMentService;
	@Autowired
	private IPersonnelService personnelService;
	@Autowired
	private IGoodsService goodsService;
	@Test
	public void isDataStrTest(){
		String flag = CommonUtils.getPreMonthFirstDay();
		System.out.println(flag);
		System.out.println(CommonUtils.getPreMonthLastDay());
	}
	
	@Test
	public void showDeptListByIdsStr()throws Exception{
		List<DepartMent> deptList = departMentService.getDeptListByIdsStr("01,02,01001,");
		for (DepartMent departMent : deptList){
            System.out.println(departMent);
		}
	}
	@Test
	public void getPersListByOperationType()throws Exception{
		List<Personnel> perList = personnelService.getPersListByOperType("1");
		System.out.println(perList);
	}
	@Test
	public void getDeptListByOperationType()throws Exception{
		List<DepartMent> deptList = departMentService.getDeptListByOperType("4");
		System.out.println(deptList);
	}
	@Test
	public void getGoodsInfoByCondition()throws Exception{
		PageMap pageMap = new PageMap();
		Map map = new HashMap();
		map.put("defaultsupplier", "1001");
		map.put("state", "1");
		pageMap.setCondition(map);
		List<GoodsInfo> list = goodsService.getGoodsInfoByCondition(pageMap);
		System.out.println(list);
	}
	@Test
	public void getPersonnelByGCB()throws Exception{
		Map map = new HashMap();
		map.put("brandid", "PP-00005");
		map.put("customerid", "005");
		Personnel personnel = personnelService.getPersonnelByGCB(map);
		System.out.println(personnel);
	}
	@Test
	public void office2PDF() throws Exception{
		OfficeUtils.office2Other("E:\\2222.xls", "E:\\333.html");
	}
	@Test
	public void getPinYingJC() throws Exception{
		String name = "1020大连上海家化日用化学品销售有限公司";
		String py = CommonUtils.getPinYingJCLen(name);
		System.out.println(py);
	}
	@Test
	public void testListSortLikeSQLComparator() throws Exception{
		String orderby="id asc,billno desc,auxnum desc";
		List<ArrivalOrderDetail> list=new ArrayList<ArrivalOrderDetail>();
		ArrivalOrderDetail arrivalOrderDetail=new ArrivalOrderDetail();
		arrivalOrderDetail.setId(1);
		arrivalOrderDetail.setBillno("DD-2015-10");
		arrivalOrderDetail.setAuxnum(new BigDecimal("100"));
		list.add(arrivalOrderDetail);
		
		arrivalOrderDetail=new ArrivalOrderDetail();
		arrivalOrderDetail.setId(2);
		arrivalOrderDetail.setBillno("DD-2016-33");
		arrivalOrderDetail.setAuxnum(new BigDecimal("101"));
		list.add(arrivalOrderDetail);
		
		arrivalOrderDetail=new ArrivalOrderDetail();
		arrivalOrderDetail.setId(1);
		arrivalOrderDetail.setBillno("DD-2015-10");
		arrivalOrderDetail.setAuxnum(new BigDecimal("101"));
		list.add(arrivalOrderDetail);
		Collections.sort(list,ListSortLikeSQLComparator.createComparator(orderby));
		for (ArrivalOrderDetail item : list) {
			System.out.println("id:"+item.getId());
			System.out.println("billno:"+item.getBillno());
			System.out.println("auxnum:"+item.getAuxnum());
		}
	}
	@Test
	public void testListMapSortLikeSQLComparator() throws Exception{
		String orderby="id asc,billno desc,auxnum desc";
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", 1);
		map.put("billno", "DD-2015-10");
		map.put("auxnum", new BigDecimal("100"));
		list.add(map);
		
		map=new HashMap<String, Object>();
		map.put("id", 2);
		map.put("billno", "DD-2015-10");
		map.put("auxnum", new BigDecimal("101"));
		list.add(map);
		
		map=new HashMap<String, Object>();
		map.put("id", 1);
		map.put("billno", "DD-2016-10");
		map.put("auxnum", new BigDecimal("101"));
		list.add(map);
		Collections.sort(list,ListSortLikeSQLComparator.createComparator(orderby));
		for(Map<String, Object> item:list){
			System.out.println("id:"+item.get("id"));
			System.out.println("billno:"+item.get("billno"));
			System.out.println("auxnum:"+item.get("auxnum"));
		}
	}
}

