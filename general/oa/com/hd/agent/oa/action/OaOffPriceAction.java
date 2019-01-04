/**
 * @(#)OaOffPriceAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-3-9 limin 创建版本
 */
package com.hd.agent.oa.action;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.oa.model.OaOffPrice;
import com.hd.agent.oa.model.OaOffPriceDetail;
import com.hd.agent.oa.service.IOaOffPriceService;

/**
 * 批量特价申请单Action
 *
 * @author limin
 */
public class OaOffPriceAction extends BaseOaAction {

    /**
     *
     */
    private static final long serialVersionUID = 6109965953731752516L;

    private IOaOffPriceService oaOffPriceService;

    public IOaOffPriceService getOaOffPriceService() {
        return oaOffPriceService;
    }

    public void setOaOffPriceService(IOaOffPriceService oaOffPriceService) {
        this.oaOffPriceService = oaOffPriceService;
    }

    private OaOffPrice price;

    public OaOffPrice getPrice() {
        return price;
    }

    public void setPrice(OaOffPrice price) {
        this.price = price;
    }

    /**
     * 批量特价调整单
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 9, 2015
     */
    public String oaOffPricePage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaOffPrice price = oaOffPriceService.selectOaOffPrice(id);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Process process = workService.getProcess(processid, "1");
            request.setAttribute("process", process);
            request.setAttribute("price", price);

            String type = request.getParameter("type");
            String step = request.getParameter("step");

            String url = "";
            Object[] params = {CommonUtils.nullToEmpty(request.getParameter("type")),
                    CommonUtils.nullToEmpty(request.getParameter("processid")),
                    CommonUtils.nullToEmpty(request.getParameter("taskid")),
                    process != null ? CommonUtils.nullToEmpty(process.getBusinessid()) : "",
                    CommonUtils.nullToEmpty(request.getParameter("buyprice")),
                    CommonUtils.nullToEmpty(request.getParameter("sign")),
                    CommonUtils.nullToEmpty(request.getParameter("pricetype"))};
            if("view".equals(type) || "99".equals(step)) {

                url = "oa/offprice/oaOffPriceViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&buyprice=%s&sign=%s&pricetype=%s";

            } else if("handle".equals(type) && "01".equals(step)){

                url = "oa/offprice/oaOffPriceHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&buyprice=%s&sign=%s&pricetype=%s";

            } else if("handle".equals(type) && "02".equals(step)){

                url = "oa/offprice/oaOffPriceHandlePage2.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&buyprice=%s&sign=%s&pricetype=%s";

            } else {

                url = "oa/offprice/oaOffPriceHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&buyprice=%s&sign=%s&pricetype=%s";
            }

            response.sendRedirect(String.format(url, params));
            return null;
        }

        request.setAttribute("price", price);
        return SUCCESS;
    }

    /**
     * 批量特价调整单处理页面
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 9, 2015
     */
    public String oaOffPriceHandlePage() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaOffPrice price = oaOffPriceService.selectOaOffPrice(id);

        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            List list = oaOffPriceService.selectOaOffPriceDetailListByBillid(id);

            if(price != null && StringUtils.isNotEmpty(price.getCustomerid())) {

                Customer customer = getCustomerById(price.getCustomerid());
                request.setAttribute("customer", customer);
            }

            if(price != null && StringUtils.isNotEmpty(price.getIndoorstaff())) {

                Personnel indoorstaff = getPersonnelInfoById(price.getIndoorstaff());
                request.setAttribute("indoorstaff", indoorstaff);
            }

            if(price != null && StringUtils.isNotEmpty(price.getSalesuserid())) {

                Personnel salesuser = getPersonnelInfoById(price.getSalesuserid());
                request.setAttribute("salesuser", salesuser);
            }

            request.setAttribute("price", price);
            request.setAttribute("process", process);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            return sign ? TO_SIGN : TO_PHONE;
        }

        request.setAttribute("price", price);
        request.setAttribute("process", process);
        return SUCCESS;
    }

    /**
     * 批量特价调整单查看页面
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 9, 2015
     */
    public String oaOffPriceViewPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaOffPrice price = oaOffPriceService.selectOaOffPrice(id);

        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            List list = oaOffPriceService.selectOaOffPriceDetailListByBillid(id);

            if(price != null && StringUtils.isNotEmpty(price.getCustomerid())) {

                Customer customer = getCustomerById(price.getCustomerid());
                request.setAttribute("customer", customer);
            }

            if(price != null && StringUtils.isNotEmpty(price.getIndoorstaff())) {

                Personnel indoorstaff = getPersonnelInfoById(price.getIndoorstaff());
                request.setAttribute("indoorstaff", indoorstaff);
            }

            if(price != null && StringUtils.isNotEmpty(price.getSalesuserid())) {

                Personnel salesuser = getPersonnelInfoById(price.getSalesuserid());
                request.setAttribute("salesuser", salesuser);
            }

            request.setAttribute("price", price);
            request.setAttribute("process", process);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            return TO_PHONE;
        }

        request.setAttribute("price", price);
        request.setAttribute("process", process);
        return SUCCESS;
    }

    /**
     * 查询特价调整单明细
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 9, 2015
     */
    public String selectOaOffPriceDetailList() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        pageMap.setRows(9999);

        PageData pageData = oaOffPriceService.selectOaOffPriceDetailList(pageMap);
        addJSONObject(pageData);

        return SUCCESS;
    }

    /**
     * 新增特价调整单
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 10, 2015
     */
    @UserOperateLog(key = "OaOffPrice",type = 2)
    public String addOaOffPrice() throws Exception {

        if (isAutoCreate("t_oa_offprice")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(price, "t_oa_offprice");
            price.setId(id);
        } else {
            price.setId("TJ-" + CommonUtils.getDataNumber());
        }

        if(StringUtils.isEmpty(price.getBusinessdate())) {

            price.setBusinessdate(CommonUtils.getTodayDataStr());
        }

        SysUser user = getSysUser();
        price.setAdduserid(user.getUserid());
        price.setAddusername(user.getName());
        price.setAdddeptid(user.getDepartmentid());
        price.setAdddeptname(user.getDepartmentname());

        String paydetail = request.getParameter("paydetail");
        List<OaOffPriceDetail> detailList = JSONUtils.jsonStrToList(paydetail, new OaOffPriceDetail());

        int ret = oaOffPriceService.addOaOffPrice(price, detailList);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", price.getId());
        map.put("type", "add");
        addJSONObject(map);
        addLog("批量特价申请单新增(OA) 编号：" + price.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 新增特价调整单
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 10, 2015
     */
    @UserOperateLog(key = "OaOffPrice",type = 3)
    public String editOaOffPrice() throws Exception {

        SysUser user = getSysUser();
        price.setModifyuserid(user.getUserid());
        price.setModifyusername(user.getName());

        if(StringUtils.isEmpty(price.getBusinessdate())) {

            price.setBusinessdate(CommonUtils.getTodayDataStr());
        }

        String paydetail = request.getParameter("paydetail");
        List<OaOffPriceDetail> detailList = JSONUtils.jsonStrToList(paydetail, new OaOffPriceDetail());

        int ret = oaOffPriceService.editOaOffPrice(price, detailList);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", price.getId());
        map.put("type", "edit");
        addJSONObject(map);
        addLog("批量特价申请单修改(OA) 编号：" + price.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 批量特价调整单查看页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 9, 2015
     */
    public String oaOffPricePrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        Customer customer = null;
        Personnel personnel = null;
        Personnel businessPersonnel = null;

        OaOffPrice price = oaOffPriceService.selectOaOffPrice(id);

        if(price != null && StringUtils.isNotEmpty(price.getCustomerid())) {
            customer = getCustomerById(price.getCustomerid());
        }

        if(price != null && StringUtils.isNotEmpty(price.getIndoorstaff())) {
            personnel = getPersonnelInfoById(price.getIndoorstaff());
        }

        if(price != null && StringUtils.isNotEmpty(price.getSalesuserid())) {
            businessPersonnel = getPersonnelInfoById(price.getSalesuserid());
        }

        Process process = workService.getProcess(processid, "1");

        List<OaOffPriceDetail> list = oaOffPriceService.selectOaOffPriceDetailListByBillid(price.getId());
        for(OaOffPriceDetail detail : list) {

            if(StringUtils.isNotEmpty(detail.getGoodsid())) {

                detail.setGoodsname(getGoodsInfoByID(detail.getGoodsid()).getName());
            }
        }

        request.setAttribute("price", price);
        request.setAttribute("customer", customer);
        request.setAttribute("personnel", personnel);
        request.setAttribute("businessPersonnel", businessPersonnel);
        request.setAttribute("list", list);
        request.setAttribute("process", process);
        return SUCCESS;
    }

    /**
     * 批量特价申请单
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 30, 2016
     */
    public String oaOffPriceDetailsAddPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 查询商品
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 30, 2016
     */
    public String getGoodsList() throws Exception {

        Map condition = CommonUtils.changeMap(request.getParameterMap());
        condition.put("nopackage", "1");
        condition.put("state", "1");
        String brandids = (String) condition.get("brandids");
        if(StringUtils.isNotEmpty(brandids)){
            condition.put("brandArr", brandids.split(","));
        }
        String defaultsorts = (String) condition.get("defaultsorts");
        if(StringUtils.isNotEmpty(defaultsorts)){
            condition.put("defaultsortArr", defaultsorts.split(","));
        }
        pageMap.setCondition(condition);

        PageData data = oaOffPriceService.getGoodsList(pageMap);
        addJSONObject(data);
        return SUCCESS;
    }
}

