package com.hd.agent.wechat.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.service.ICustomerUserService;
import com.hd.agent.wechat.service.IWechatUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wanghongteng on 2017/5/24.
 */
public class BaseWechatAction extends BaseFilesAction {

    protected IWechatUserService wechatUserService;

    protected ICustomerUserService customerUserService;

    public IWechatUserService getWechatUserService() {
        return wechatUserService;
    }

    public void setWechatUserService(IWechatUserService wechatUserService) {
        this.wechatUserService = wechatUserService;
    }

    public ICustomerUserService getCustomerUserService() {
        return customerUserService;
    }

    public void setCustomerUserService(ICustomerUserService customerUserService) {
        this.customerUserService = customerUserService;
    }
}
