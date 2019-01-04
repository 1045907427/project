package com.hd.agent.message.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.message.service.IWechateTemplateService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghongteng on 2017/5/22.
 */
public class WechatTemplateAction extends BaseAction {

    private IWechateTemplateService wechateTemplateService;

    public IWechateTemplateService getWechateTemplateService() {
        return wechateTemplateService;
    }

    public void setWechateTemplateService(IWechateTemplateService wechateTemplateService) {
        this.wechateTemplateService = wechateTemplateService;
    }

    /**
     * 显示微信消息模板列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public String showWechatTemplateListPage() throws Exception {
        List<String> openidList = new ArrayList();
        openidList.add("ohWaZwxj94yuZrXSMs0uqTP607Sc");
        openidList.add("ohWaZwyf6A9Mjuyu4qx2d4Z0gAFU");
        wechateTemplateService.cashChangeTemplate(openidList,"您有一笔订单已经生成但尚未支付，请尽快到“我的订单”支付。","20140815888","钟姐杏仁牛轧糖","4盒","100元","订单8小时后自动取消，若有疑虑请拨打客服热线4008859059。");
        return "success";
    }
}
