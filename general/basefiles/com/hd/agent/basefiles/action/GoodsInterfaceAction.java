package com.hd.agent.basefiles.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.accesscontrol.service.ISysUserService;
import com.hd.agent.basefiles.service.IGoodsService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghongteng on 2017/6/15.
 */
public class GoodsInterfaceAction  extends BaseAction {

    private IGoodsService goodsService;

    private ISysUserService sysUserService;

    public IGoodsService getGoodsService() {
        return goodsService;
    }

    public void setGoodsService(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }

    public ISysUserService getSysUserService() {
        return sysUserService;
    }

    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 修改商品货位
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-6-15
     */
    public String editGoodsItemno() throws Exception {
        Map resultMap = new HashMap();
        String goodsid = StringUtils.isEmpty(request.getParameter("goodsid")) ? "" : request.getParameter("goodsid");
        String itemno = StringUtils.isEmpty(request.getParameter("itemno")) ? "" : request.getParameter("itemno");
        String username = StringUtils.isEmpty(request.getParameter("username")) ? "" : request.getParameter("username");
        String token = StringUtils.isEmpty(request.getParameter("token")) ? "" : request.getParameter("token");
        SysUser user = sysUserService.getUser(username);
        if(null!=user){
//            if(user.getPassword().equals(CommonUtils.MD5(token))){
            if(user.getPassword().equals(token)){
                resultMap =goodsService.editGoodsItemno(goodsid,itemno);
            }else{
                resultMap.put("flag",false);
                resultMap.put("msg","token错误！");
            }
        }else{
            resultMap.put("flag",false);
            resultMap.put("msg","用户不存在！");
        }

        addSysLogInfo("修改商品货位 编号:" + goodsid+"，货位:"+itemno,username);
        addJSONObject(resultMap);
        return SUCCESS;
    }
}
