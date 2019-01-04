package com.hd.agent.message.service;

import com.hd.agent.message.model.MsgNotice;

import java.util.List;

/**
 * Created by wanghongteng on 2017/5/22.
 */
public interface IWechateTemplateService {

    /**
     * 订单支付通知
     * @param openidList  接受者openid列表
     * @param first  标题
     * @param keyword1  变动时间
     * @param keyword2  变动金额
     * @param keyword3  账户类型
     * @param keyword4 账户余额
     * @param remark  备注
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-5-22
     */
    public boolean cashChangeTemplate(List<String> openidList,String first,String keyword1,String keyword2,String keyword3,String keyword4,String remark) throws Exception;


}
