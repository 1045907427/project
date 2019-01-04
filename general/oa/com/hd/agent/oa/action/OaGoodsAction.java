/**
 * @(#)OaGoodsAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-9 limin 创建版本
 */
package com.hd.agent.oa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.oa.service.impl.OaGoodsServiceImpl;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.oa.model.OaGoods;
import com.hd.agent.oa.model.OaGoodsDetail;
import com.hd.agent.system.model.SysCode;

/**
 * 
 * 
 * @author limin
 */
public class OaGoodsAction extends BaseOaAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3209631465403684746L;

	private OaGoodsServiceImpl oaGoodsService;
	
	private OaGoods goods;
	
	public OaGoodsServiceImpl getOaGoodsService() {
		return oaGoodsService;
	}

	public void setOaGoodsService(OaGoodsServiceImpl oaGoodsService) {
		this.oaGoodsService = oaGoodsService;
	}

	public OaGoods getGoods() {
		return goods;
	}

	public void setGoods(OaGoods goods) {
		this.goods = goods;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-7-9
	 */
	public String oagoodsPage() throws Exception {
		
		List<SysCode> list = getBaseSysCodeService().showSysCodeListByType("price_list");
		
		String price1name = "1号价";
		String price2name = "2号价";
		String price3name = "3号价";
		String price4name = "4号价";
		String price5name = "5号价";
		
		int count = 0 ;
		
		if(list.size() > count) {
			
			price1name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price2name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price3name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price4name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price5name = list.get(count++).getCodename();
		}

		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		Map map = new HashMap();
		map.put("billid", id);
		pageMap.setCondition(map);
		pageMap.setRows(9999);
		
		PageData pageData = oaGoodsService.selectGoodsDetailList(pageMap);
		List goodsList = pageData.getList();

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Process process = workService.getProcess(processid, "1");
            request.setAttribute("process", process);

            String type = request.getParameter("type");
            String step = request.getParameter("step");

            String url = "";
            Object[] params = {CommonUtils.nullToEmpty(request.getParameter("type")),
                    CommonUtils.nullToEmpty(request.getParameter("processid")),
                    CommonUtils.nullToEmpty(request.getParameter("taskid")),
                    process != null ? CommonUtils.nullToEmpty(process.getBusinessid()) : "",
					CommonUtils.nullToEmpty(request.getParameter("sign"))};
            if("view".equals(type) || "99".equals(step)) {

                url = "oa/oagoodsViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else {

                url = "oa/oagoodsHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";
            }

            response.sendRedirect(String.format(url, params));
            return null;
        }

		request.setAttribute("list", JSONUtils.listToJsonStr(goodsList));
        request.setAttribute("priceList", list);
		request.setAttribute("price1name", price1name);
		request.setAttribute("price2name", price2name);
		request.setAttribute("price3name", price3name);
		request.setAttribute("price4name", price4name);
		request.setAttribute("price5name", price5name);
		return SUCCESS;
	}
	
	/**
	 * 新品登录单查看页面
	 * @return
	 * @author limin 
	 * @throws Exception 
	 * @date 2014-7-9
	 */
	public String oagoodsViewPage() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		goods = oaGoodsService.selectOaGoods(id);
        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Map map = new HashMap();
            map.put("billid", id);
            pageMap.setCondition(map);
            pageMap.setRows(9999);

            PageData pageData = oaGoodsService.selectGoodsDetailList(pageMap);
            List goodsList = pageData.getList();

            List<SysCode> priceList = getBaseSysCodeService().showSysCodeListByType("price_list");

            if(goods != null && StringUtils.isNotEmpty(goods.getSupplierid())) {

                BuySupplier supplier = getBaseBuySupplierById(goods.getSupplierid());
                request.setAttribute("supplier", supplier);
            }

            request.setAttribute("goodsList", JSONUtils.listToJsonStr(goodsList));
            request.setAttribute("price_count", priceList.size());
            request.setAttribute("priceList", priceList);
            request.setAttribute("goods", goods);
            request.setAttribute("process", process);
            return TO_PHONE;
        }

		request.setAttribute("goods", goods);
        request.setAttribute("process", process);
		return SUCCESS;
	}
	
	/**
	 * 新品登录单审批页面
	 * @return
	 * @author limin 
	 * @throws Exception 
	 * @date 2014-7-9
	 */
	public String oagoodsHandlePage() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		OaGoods goods = oaGoodsService.selectOaGoods(id);
        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Map map = new HashMap();
            map.put("billid", id);
            pageMap.setCondition(map);
            pageMap.setRows(9999);

            PageData pageData = oaGoodsService.selectGoodsDetailList(pageMap);
            List goodsList = pageData.getList();

            List<SysCode> priceList = getBaseSysCodeService().showSysCodeListByType("price_list");

            if(goods != null && StringUtils.isNotEmpty(goods.getSupplierid())) {

                BuySupplier supplier = getBaseBuySupplierById(goods.getSupplierid());
                request.setAttribute("supplier", supplier);
            }

            request.setAttribute("goodsList", JSONUtils.listToJsonStr(goodsList));
            request.setAttribute("price_count", priceList.size());
            request.setAttribute("priceList", priceList);
            request.setAttribute("goods", goods);
            request.setAttribute("process", process);

            return sign ? TO_SIGN : TO_PHONE;
        }

		request.setAttribute("goods", goods);
        request.setAttribute("process", process);
		return SUCCESS;
	}
	
	/**
	 * 新品登录单商品明细
	 * @return
	 * @author limin 
	 * @throws Exception 
	 * @date 2014-7-9
	 */
	public String oagoodsDetailAddPage() throws Exception {
		
		List<SysCode> list = getBaseSysCodeService().showSysCodeListByType("price_list");
		
		String price1name = "1号价";
		String price2name = "2号价";
		String price3name = "3号价";
		String price4name = "4号价";
		String price5name = "5号价";
		
		int count = 0 ;
		
		if(list.size() > count) {
			
			price1name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price2name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price3name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price4name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price5name = list.get(count++).getCodename();
		}

		request.setAttribute("price1name", price1name);
		request.setAttribute("price2name", price2name);
		request.setAttribute("price3name", price3name);
		request.setAttribute("price4name", price4name);
		request.setAttribute("price5name", price5name);
		String defaultTaxtype = getSysParamValue("DEFAULTAXTYPE");
		String defaultAuxunitid = getSysParamValue("DEFAULTAUXUNITID");
		request.setAttribute("defaulttaxtype", defaultTaxtype);
		request.setAttribute("defaultAuxunitid", defaultAuxunitid);
		request.setAttribute("priceList", list);
		return SUCCESS;
	}
	
	/**
	 * 添加商品明细List
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-7-25
	 */
	public String addGoods() throws Exception {

		if(isAutoCreate("t_oa_goods")) {
			goods.setId(getAutoCreateSysNumbderForeign(goods, "t_oa_goods"));
		} else {
			goods.setId("SP-" + CommonUtils.getDataNumber());
		}

		SysUser user = getSysUser();
		goods.setAdduserid(user.getUserid());
		goods.setAddusername(user.getName());
		goods.setAdddeptid(user.getDepartmentid());
		goods.setAdddeptname(user.getDepartmentname());
		
		String customerBrandJSON = request.getParameter("goodsBrandJSON");
		List<OaGoodsDetail> list = JSONUtils.jsonStrToList(customerBrandJSON, new OaGoodsDetail());

		int ret = oaGoodsService.addGoods(goods, list);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", goods.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("新品登录单新增 编号：" + goods.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 修改商品明细List
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-7-25
	 */
	public String editGoods() throws Exception {

		if(StringUtils.isEmpty(goods.getId())) {
			
			addGoods();
			return SUCCESS;
		}
		
		SysUser user = getSysUser();
		goods.setModifyuserid(user.getUserid());
		goods.setModifyusername(user.getName());
		
		String customerBrandJSON = request.getParameter("goodsBrandJSON");
		List<OaGoodsDetail> list = JSONUtils.jsonStrToList(customerBrandJSON, new OaGoodsDetail());

		int ret = oaGoodsService.editGoods(goods, list);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", goods.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("新品登录单编辑 编号：" + goods.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 商品明细编辑页面
	 * @return
	 * @author limin 
	 * @throws Exception 
	 * @date 2014-7-16
	 */
	public String oagoodsDetailEditPage() throws Exception {
		
		List<SysCode> list = getBaseSysCodeService().showSysCodeListByType("price_list");
		
		String price1name = "1号价";
		String price2name = "2号价";
		String price3name = "3号价";
		String price4name = "4号价";
		String price5name = "5号价";
		
		int count = 0 ;
		
		if(list.size() > count) {
			
			price1name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price2name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price3name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price4name = list.get(count++).getCodename();
		}
		if(list.size() > count) {
			
			price5name = list.get(count++).getCodename();
		}

		request.setAttribute("price1name", price1name);
		request.setAttribute("price2name", price2name);
		request.setAttribute("price3name", price3name);
		request.setAttribute("price4name", price4name);
		request.setAttribute("price5name", price5name);
        request.setAttribute("priceList", list);
		return SUCCESS;
	}
	
	/**
	 * 查询新品登录单对应的商品明细List
	 * @return
	 * @author limin 
	 * @date 2014-7-16
	 */
	public String selectOaGoodsDetailList() {
		
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setRows(9999);
		
		PageData pageData = oaGoodsService.selectGoodsDetailList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 查询该商品编号是否已经存在
	 * @return
	 * @author limin 
	 * @date 2014-7-31
	 */
	public String selectExistedGoodsid() {
		
		String goodsid = request.getParameter("goodsid");
		
		int ret = oaGoodsService.selectExistedGoodsid(goodsid, null);
		
		addJSONObject("flag", ret > 0);
		return SUCCESS;
	}
	
	/**
	 * 查询该商品名称是否已存在
	 * @return
	 * @author limin 
	 * @date 2014-7-31
	 */
	public String selectExistedGoodsname() {
		
		String goodsname = request.getParameter("goodsname");
		
		int ret = oaGoodsService.selectExistedGoodsname(goodsname);
		
		addJSONObject("flag", ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 查询该条形码是否已存在
	 * @return
	 * @author limin 
	 * @date 2014-7-31
	 */
	public String selectExistedBarcode() {
		
		String barcode = request.getParameter("barcode");
		
		int ret = oaGoodsService.selectExistedBarcode(barcode);
		
		addJSONObject("flag", ret > 0);

		return SUCCESS;
	}
	
	public String selectExistedBoxbarcode() {
		
		String boxbarcode = request.getParameter("boxbarcode");
		
		int ret = oaGoodsService.selectExistedBoxbarcode(boxbarcode);
		
		addJSONObject("flag", ret > 0);

		return SUCCESS;
	}

    /**
     * 新品登录单打印页面
     * @return
     * @author limin
     * @throws Exception
     * @date 2015-04-07
     */
    public String oagoodsPrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        BuySupplier supplier = null;

        goods = oaGoodsService.selectOaGoods(id);

        if(goods != null && StringUtils.isNotEmpty(goods.getSupplierid())) {
            supplier = getBaseBuySupplierById(goods.getSupplierid());
        }

        Map map = new HashMap();
        map.put("billid", id);
        pageMap.setCondition(map);
        PageData data = oaGoodsService.selectGoodsDetailList(pageMap);
        List<OaGoodsDetail> list = data.getList();
        for(OaGoodsDetail detail : list) {

            if(StringUtils.isNotEmpty(detail.getBrandid())) {

                detail.setBrandid(getBaseGoodsService().getBrandInfo(detail.getBrandid()).getName());
            }

            if(StringUtils.isNotEmpty(detail.getGoodssort())) {

                detail.setGoodssort(getBaseGoodsService().getWaresClassInfo(detail.getGoodssort()).getName());
            }

            if(StringUtils.isNotEmpty(detail.getStorageid())) {

                detail.setStorageid(getStorageInfo(detail.getStorageid()).getName());
            }
        }

        List<SysCode> pricelist = getBaseSysCodeService().showSysCodeListByType("price_list");

        Process process = workService.getProcess(processid, "1");

        request.setAttribute("goods", goods);
        request.setAttribute("supplier", supplier);
        request.setAttribute("list", list);
        request.setAttribute("process", process);
        request.setAttribute("pricelist", pricelist);
        return SUCCESS;
    }

    /**
     * 商品明细页面
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 14, 2015
     */
    public String oagoodsDetailPage() throws Exception {

        List<SysCode> priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
        String defaultTaxtype = getSysParamValue("DEFAULTAXTYPE");
        String defaultAuxunitid = getSysParamValue("DEFAULTAUXUNITID");

        TaxType defaultTex = getTaxType(defaultTaxtype);
        MeteringUnit defaultAuxunit = oaGoodsService.getMeteringUnitById(defaultAuxunitid);

        request.setAttribute("priceList", priceList);
        request.setAttribute("defaultTaxtype", defaultTaxtype);
        request.setAttribute("defaultTex", defaultTex);
        request.setAttribute("defaultAuxunitid", defaultAuxunitid);
        request.setAttribute("defaultAuxunit", defaultAuxunit);
        return SUCCESS;
    }

    /**
     * 查询该商品编号是否已经存在，该方法用户jquery validate验证
     * @return
     * @author limin
     * @date Oct 15, 2015
     */
    public String selectExistedGoodsid2() throws Exception{

        String goodsid = request.getParameter("goodsid");
        String billid = request.getParameter("billid");

        int ret = oaGoodsService.selectExistedGoodsid(goodsid, billid);

        addJSONObject("flag", ret == 0);
        return SUCCESS;
    }
}

