package com.hd.agent.oa.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.oa.model.OaGoodsPrice;
import com.hd.agent.oa.model.OaGoodsPriceDetail;
import com.hd.agent.oa.service.IOaGoodsPriceService;
import com.hd.agent.system.model.SysCode;

import java.util.*;

/**
 * Created by limin on 2015/5/27.
 */
public class OaGoodsPriceAction extends BaseOaAction {

    /**
     *
     */
    private static final long serialVersionUID = -4779212839628055890L;

    private OaGoodsPrice price;

    private IOaGoodsPriceService priceService;

    public OaGoodsPrice getPrice() {
        return price;
    }

    public void setPrice(OaGoodsPrice price) {
        this.price = price;
    }

    public IOaGoodsPriceService getPriceService() {
        return priceService;
    }

    public void setPriceService(IOaGoodsPriceService priceService) {
        this.priceService = priceService;
    }

    /**
     * 商品调价单页面
     * @return
     * @throws Exception
     * @author limin
     * @date May 27, 2015
     */
    public String oaGoodsPricePage() throws Exception {

        List<SysCode> list = getBaseSysCodeService().showSysCodeListByType("price_list");
        List<SysCode> openList = new ArrayList<SysCode>();
        for(SysCode code : list){
            if(code.getState().equals("1")){
                openList.add(code);
            }
        }
        request.setAttribute("list", openList);

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        Map map = new HashMap();
        map.put("billid",id);

        pageMap.setCondition(map);
        pageMap.setRows(9999);
        PageData pageData = priceService.selectGoodsDetailList(pageMap);
        List goodsList = pageData.getList();

        request.setAttribute("priceList", JSONUtils.listToJsonStr(goodsList));

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
                    CommonUtils.nullToEmpty(request.getParameter("sign"))};
            if("view".equals(type) || "99".equals(step)) {

                url = "oa/goodsprice/oaGoodsPriceViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else if("handle".equals(type) && "01".equals(step)){

                url = "oa/goodsprice/oaGoodsPriceHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else {

                url = "oa/goodsprice/oaGoodsPriceHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";
            }

            response.sendRedirect(String.format(url, params));
            return null;
        }

        return SUCCESS;
    }

    /**
     * 商品调价单处理页面
     * @return
     * @throws Exception
     * @author limin
     * @date May 27, 2015
     */
    public String oaGoodsPriceHandlePage() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaGoodsPrice price = priceService.selectOaGoodsPrice(id);

        request.setAttribute("price", price);
        if(price != null ){

            BuySupplier supplier = getBaseBuySupplierById(price.getSupplierid());
            request.setAttribute("supplier",supplier);
        }

        Process process = workService.getProcess(processid, "1");
        request.setAttribute("process", process);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            List list = priceService.selectOaGoodsPriceDetailListByBillid(id);

            List<SysCode> priceList = getBaseSysCodeService().showSysCodeListByType("price_list");

            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            request.setAttribute("price_count", priceList.size());
            request.setAttribute("priceList", priceList);
            return sign ? TO_SIGN : TO_PHONE;
        }

        return SUCCESS;
    }

    /**
     * 商品调价单查看页面
     * @return
     * @throws Exception
     * @author limin
     * @date May 27, 2015
     */
    public String oaGoodsPriceViewPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaGoodsPrice price = priceService.selectOaGoodsPrice(id);

        request.setAttribute("price", price);
        if(price != null ){

            BuySupplier supplier = getBaseBuySupplierById(price.getSupplierid());
            request.setAttribute("supplier",supplier);
        }

        Process process = workService.getProcess(processid, "1");
        request.setAttribute("process", process);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            List list = priceService.selectOaGoodsPriceDetailListByBillid(id);

            List<SysCode> priceList = getBaseSysCodeService().showSysCodeListByType("price_list");

            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            request.setAttribute("price_count", priceList.size());
            request.setAttribute("priceList", priceList);
            return TO_PHONE;
        }

        return SUCCESS;
    }

    /**
     * 商品调价单明细新增页面
     * @return
     * @throws Exception
     * @author lin_xx
     * @date june 3, 2015
     */
    public String oagoodsPriceDetailAddPage() throws Exception{

        request.setAttribute("index", request.getParameter("index"));
        request.setAttribute("supplierid", request.getParameter("supplierid"));

        List<SysCode> list = getBaseSysCodeService().showSysCodeListByType("price_list");
        List<SysCode> openList = new ArrayList<SysCode>();
        for(SysCode code : list){
            if(code.getState().equals("1")){
                openList.add(code);
            }
        }
        request.setAttribute("list", openList);
        request.setAttribute("pricelength",openList.size());

        return SUCCESS;
    }
    /**
     * 商品调价单明细修改页面
     * @return
     * @throws Exception
     * @author lin_xx
     * @date june 3, 2015
     */
    public String oagoodsPriceDetailEditPage() throws Exception{

        request.setAttribute("index", request.getParameter("index"));
        request.setAttribute("supplierid", request.getParameter("supplierid"));

        List<SysCode> list = getBaseSysCodeService().showSysCodeListByType("price_list");
        List<SysCode> openList = new ArrayList<SysCode>();
        for(SysCode code : list){
            if(code.getState().equals("1")){
                openList.add(code);
            }
        }
        request.setAttribute("list", openList);
        request.setAttribute("pricelength",openList.size());
        return SUCCESS;
    }

    /**
     * 商品调价单新增保存
     * @return
     * @throws Exception
     * @author lin_xx
     * @date june 3, 2015
     */
    public String addOaGoodsPrice() throws  Exception{

        String pricedetail = request.getParameter("goodpricedetail");

        if (isAutoCreate("t_oa_goodsprice")) {

            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(price, "t_oa_goodsprice");
            price.setId(id);
        } else {

            price.setId("RC-" + CommonUtils.getDataNumber());
        }

        SysUser user = getSysUser();
        price.setAdduserid(user.getUserid());
        price.setAddusername(user.getName());
        price.setAdddeptid(user.getDepartmentid());
        price.setAdddeptname(user.getDepartmentname());
        List detailList = JSONUtils.jsonStrToList(pricedetail, new OaGoodsPriceDetail());

        int ret = priceService.insertOaGoodsPrice(price, detailList);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", price.getId());
        map.put("type", "add");
        addJSONObject(map);
        addLog("商品价格调整单新增 编号：" + price.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 商品调价单修改保存
     * @return
     * @throws Exception
     * @author lin_xx
     * @date june 3, 2015
     */
    public String editOaGoodsPrice() throws  Exception{

        String pricedetail = request.getParameter("goodpricedetail");

        SysUser user = getSysUser();
        price.setModifyuserid(user.getUserid());
        price.setAddusername(user.getName());
        price.setModifytime(new Date());

        List detailList = JSONUtils.jsonStrToList(pricedetail, new OaGoodsPriceDetail());

        int ret = priceService.editOaGoodsPrice(price, detailList);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", price.getId());
        map.put("type", "edit");
        addJSONObject(map);
        addLog("商品价格调整单修改 编号：" + price.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 商品调价单明细查看页面
     * @return
     * @throws Exception
     * @author lin_xx
     * @date june 3, 2015
     */
    public String selectGoodsPriceDetailList() throws Exception{

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        pageMap.setRows(9999);

        PageData pageData = priceService.selectGoodsDetailList(pageMap);
        addJSONObject(pageData);

        return SUCCESS;
    }

    /**
     * 明细编辑页面(手机)
     * @return
     * @throws Exception
     * @author limin
     * @date Oct 12, 2015
     */
    public String oaGoodsPriceDetailPage() throws Exception {

        List<SysCode> priceList = getBaseSysCodeService().showSysCodeListByType("price_list");

        request.setAttribute("priceList", priceList);
        return TO_PHONE;
    }
}
