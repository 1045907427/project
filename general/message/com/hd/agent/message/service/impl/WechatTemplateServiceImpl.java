package com.hd.agent.message.service.impl;

import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.message.model.MsgNotice;
import com.hd.agent.message.service.IWechateTemplateService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongteng on 2017/5/22.
 */
public class WechatTemplateServiceImpl  extends BaseServiceImpl implements IWechateTemplateService {


    /**
     * 订单支付通知
     * @param openidList  接受者openid列表
     * @param first  标题
     * @param keyword1  变动时间
     * @param keyword2  变动金额
     * @param keyword3  账户类型
     * @param keyword4 账户余额
     * @param remark  备注
     *  模板样式：
     *  {{first.DATA}}
        订单号码：{{keyword1.DATA}}
        商品名称：{{keyword2.DATA}}
        商品数量：{{keyword3.DATA}}
        支付金额：{{keyword4.DATA}}
        {{remark.DATA}}
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-5-22
     */
    public boolean cashChangeTemplate(List<String> openidList, String first, String keyword1, String keyword2, String keyword3, String keyword4, String remark) throws Exception{
        String templateid = "68nrEnQhCwRBNGYnI-rg8UC0PN2_2beEwkS0_yc2fQw";

        Map data = new HashMap();

        Map firstMap = new HashMap();
        firstMap.put("value",first);
        firstMap.put("color","#173177");
        data.put("first",firstMap);

        Map keyword1Map = new HashMap();
        keyword1Map.put("value",keyword1);
        keyword1Map.put("color","#173177");
        data.put("keyword1",keyword1Map);

        Map keyword2Map = new HashMap();
        keyword2Map.put("value",keyword2);
        keyword2Map.put("color","#173177");
        data.put("keyword2",keyword2Map);

        Map keyword3Map = new HashMap();
        keyword3Map.put("value",keyword3);
        keyword3Map.put("color","#173177");
        data.put("keyword3",keyword3Map);

        Map keyword4Map = new HashMap();
        keyword4Map.put("value",keyword4);
        keyword4Map.put("color","#173177");
        data.put("keyword4",keyword4Map);

        Map remarkMap = new HashMap();
        remarkMap.put("value",remark);
        remarkMap.put("color","#173177");
        data.put("remark",remarkMap);

        SendWechatTemplateMsg(openidList,templateid,data);

        return true;
    }

}
