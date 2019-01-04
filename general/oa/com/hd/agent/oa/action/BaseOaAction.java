/**
 * @(#)BaseOaAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-6-14 limin 创建版本
 */
package com.hd.agent.oa.action;

import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.IDepartMentService;
import com.hd.agent.basefiles.service.IGoodsService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.oa.service.IBaseOaService;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.system.service.ISysCodeService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * OA模块基础Action类
 * 
 * @author limin
 */
public class BaseOaAction extends BaseFilesAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1486695393832267674L;

    /**
     * 手机页面
     */
    protected static final String TO_PHONE = "phone";

    /**
     * 手机页面
     */
    protected static final String TO_SIGN = "sign";

	protected IDepartMentService departMentService;

	protected ISysCodeService sysCodeService;

    protected IWorkService workService;

    public IGoodsService goodsService;

    protected IBaseOaService baseOaService;

	public IDepartMentService getDepartMentService() {
		return departMentService;
	}

	public void setDepartMentService(IDepartMentService departMentService) {
		this.departMentService = departMentService;
	}

	public ISysCodeService getSysCodeService() {
		return sysCodeService;
	}

	public void setSysCodeService(ISysCodeService sysCodeService) {
		this.sysCodeService = sysCodeService;
	}

    public IWorkService getWorkService() {
        return workService;
    }

    public void setWorkService(IWorkService workService) {
        this.workService = workService;
    }

    public IGoodsService getGoodsService() {
        return goodsService;
    }

    public void setGoodsService(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }

    public IBaseOaService getBaseOaService() {
        return baseOaService;
    }

    public void setBaseOaService(IBaseOaService baseOaService) {
        this.baseOaService = baseOaService;
    }

    /**
     * 查询商品
     * @return
     * @throws Exception
     */
	public String selectGoodsInfo() throws Exception {
		
		String id = request.getParameter("id");
		
		GoodsInfo goods = getGoodsInfoByID(id);
		
		addJSONObject("goods", goods);
		return SUCCESS;
	}

    /**
     * 查询供应商
     * @return
     * @throws Exception
     */
    public String selectSupplierInfo() throws Exception {

        String id = request.getParameter("id");

        BuySupplier supplier = getBaseBuySupplierById(id);

        addJSONObject("supplier", supplier);
        return SUCCESS;
    }

    /**
     * 根据商品编号获取其对应的价格套
     * @author lin_xx
     * @return
     * @throws Exception
     */
    public String selectPriceListByGoodsid() throws Exception{

        String id = request.getParameter("id");

        List<GoodsInfo_PriceInfo> priceList = getGoodsService().getPriceListByGoodsid(id);

        addJSONObject("priceList", priceList);
        return SUCCESS;
    }

    /**
     * 获取客户
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 3, 2015
     */
    public String selectCustomerInfo() throws Exception {

        String id = request.getParameter("id");

        Customer customer = getCustomerById(id);

        addJSONObject("customer", customer);
        return SUCCESS;
    }

    /**
     * 获取部门信息
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 3, 2015
     */
    public String selectDeptInfo() throws Exception {

        String id = request.getParameter("id");

        DepartMent dept = getDepartMentService().showDepartMentInfo(id);

        addJSONObject("dept", dept);
        return SUCCESS;
    }

    /**
     * 查询人员档案
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 6, 2015
     */
    public String selectPersonnelInfo() throws Exception {

        String id = request.getParameter("id");

        Personnel personnel = getPersonnelInfoById(id);

        addJSONObject("personnel", personnel);
        return SUCCESS;
    }

    /**
     * 获取商品价格
     * @return
     * @throws Exception
     * @author limin
     * @date Jan 28, 2016
     */
    public String getGoodsPrice() throws Exception {

        String customerid = request.getParameter("customerid");
        String goodsid = request.getParameter("goodsid");
        String index = request.getParameter("index");

        OrderDetail orderDetail = baseOaService.getGoodsPrice(customerid, goodsid);

        Map map = new HashMap();
        map.put("goods", orderDetail);
        map.put("index", index);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * map → url 例如：{a：1, b: 2} → a=1&b=2
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 13, 2016
     */
    protected String changeMapToUrl(Map<String, String> map) throws Exception {

        StringBuilder sb = new StringBuilder();
        Set<String> keys = map.keySet();

        if(keys.size() == 0) {
            return "";
        }

        for(String key : keys) {
            sb.append("&" + key + "=" + CommonUtils.nullToEmpty((String) map.get(key)));
        }

        return new String(sb).substring(1);
    }

}

