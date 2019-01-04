/**
 * @(#)ClientInterfaceAction.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月2日 huangzhiqian 创建版本
 */
package com.hd.agent.client.action;

import java.util.*;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.client.model.ClientOffprice;
import com.hd.agent.client.service.IOffPriceService;
import com.hd.agent.client.service.impl.ClientInterfaceServiceImpl;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.util.CommonUtils;

import org.apache.commons.lang3.StringUtils;

/**
 * 客户端接口 Action
 *
 * @author huangzhiqian
 */
public class ClientInterfaceAction extends BaseAction{

    /**  */
    private static final long serialVersionUID = 4037844346622735119L;

    private IOffPriceService offPriceService;

    private ClientInterfaceServiceImpl clientInterfaceService;

    public IOffPriceService getOffPriceService() {
        return offPriceService;
    }

    public void setOffPriceService(IOffPriceService offPriceService) {
        this.offPriceService = offPriceService;
    }

    public ClientInterfaceServiceImpl getClientInterfaceService() {
        return clientInterfaceService;
    }

    public void setClientInterfaceService(ClientInterfaceServiceImpl clientInterfaceService) {
        this.clientInterfaceService = clientInterfaceService;
    }

    /**
     * 获取特价商品,客户端
     * @return
     * @author huangzhiqian
     * @throws Exception
     * @date 2015年12月2日
     */
    public String getOffPriceGoods() throws Exception{
        SysUser sysUser = getSysUser();
        String personnelid = sysUser.getPersonnelid();
        Personnel personnel =  getPersonnelInfoById(personnelid);
        String deptid="";
        if(null!= personnel){
            deptid = personnel.getBelongdeptid();
        }
        List<ClientOffprice> goodsList = offPriceService.getOffPriceForClient(deptid, null);

        Map map = new HashMap();
        map.put("data", goodsList);
        addJSONObject(map);

        return SUCCESS;
    }

    /**
     * 获取当前时间
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 30, 2015
     */
    public String getTime() throws Exception {

        String date = CommonUtils.getTodayDataStr();    // 当前日期
        String time = CommonUtils.getNowTime();         // 当前时间

        Map map = new HashMap();
        map.put("time", date + " " + time);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 获取更新的商品信息
     * @return
     * @throws Exception
     * @author limin
     * @date Dec 9, 2015
     */
    public String getUpdatedGoodsList() throws Exception {

        String modifytime = request.getParameter("modifytime");
        List<Map> list = new ArrayList<Map>();
        List<Map> list2 = new ArrayList<Map>();

        Date date = new Date(0L);   // 默认更新日期是1970年1月1日
        if(StringUtils.isNotEmpty(modifytime)) {

            date = CommonUtils.stringToDate(modifytime);
        }

        // 默认零售价格套代码
        String defaultRetailPriceCode = CommonUtils.nullToEmpty(getSysParamValue("DefaultRetailPriceCode"));

        SysUser user = getSysUser();                                                    // 当前用户
        DepartMent departMent = getBaseDepartMentService().showDepartMentInfo(user.getDeptid());    // 用户所属部门
        Customer customer = null;                                                       // 门店默认客户，用于客户端零售

        Map param = new HashMap();
        param.put("modifytime", date);
        param.put("code", defaultRetailPriceCode);
        param.put("goodsid", request.getParameter("goodsid"));

  //TODO 2017/5/16 部门默认客户待加
//        // 门店默认客户
//        if(departMent != null) {
//
//            customer = getBaseSalesService().getCustomerInfo(departMent.getDefaultcustomer());
//            if(customer != null && StringUtils.isNotEmpty(customer.getPricesort())) {
//                param.put("code", customer.getPricesort());
//            }
//        }
        list = getBaseGoodsService().getUpdatedGoodsList(param);

        for(Map goods : list) {

            Map temp = new HashMap();
            temp.put("id", goods.get("id"));
            temp.put("name", goods.get("name"));
            if(goods.get("barcode") == null){
                continue;
            }
            temp.put("barcode", goods.get("barcode"));
            temp.put("mainunit", goods.get("mainunit") == null ? "无" : goods.get("mainunit"));
            temp.put("state", goods.get("state"));
            temp.put("remark", goods.get("remark") == null ? "无" : goods.get("remark"));

            // 箱装量
            temp.put("boxnum", goods.get("boxnum"));
            if(temp.get("boxnum") == null){
                continue;
            }
            // 零售价
            // 默认客户为空时，根据系统参数配置取价格套
            temp.put("saleprice", goods.get("saleprice"));
            if(temp.get("saleprice") == null){
                continue;
            }

            list2.add(temp);
        }

        addJSONArray(list2);
        return SUCCESS;
    }


}

