package com.hd.agent.wechat.service.impl;

import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.WechatProperties;
import com.hd.agent.wechat.service.IWechatUserService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongteng on 2017/5/22.
 */
public class WechatUserServiceImpl extends BaseWechatServiceImpl implements IWechatUserService {

    /**
     * 发送短信
     */
    public Map sendMessage(String mobile, String type) throws Exception{
        String randomNum=getMessageTestNumber();
        List list=new ArrayList();
        Map mapName=new HashMap();
        Map mapCode=new HashMap();
//        map.put("code",randomNum);
        mapName.put("valueName", "name");
        mapName.put("value", "客户");
        list.add(mapName);
        mapCode.put("valueName", "code");
        mapCode.put("value", randomNum);
        list.add(mapCode);
        String messagetype= "1";
        String templateCode="";
        if("1".equals(type)){
            templateCode= WechatProperties.getValue("templateCode1");
        }else if("2".equals(type)){
            templateCode=WechatProperties.getValue("templateCode2");
        }
        String signName=getSysParamValue("SIGNNAME");
        String resmsg=SendMessageReminder(messagetype, templateCode, list, mobile, signName);
        Map resmap=new HashMap();
        if(StringUtils.isNotEmpty(resmsg)){
            Map map1 = JSONUtils.jsonStrToMap(resmsg);
            String resmsg2=(String)(map1.get("alibaba_aliqin_fc_sms_num_send_response"));
            if(StringUtils.isNotEmpty(resmsg2)){
                Map map2= JSONUtils.jsonStrToMap(resmsg2);
                if(null!=map2){
                    String resmsg3=(String)(map2.get("result"));
                    if(StringUtils.isNotEmpty(resmsg3)) {
                        Map map3 = JSONUtils.jsonStrToMap(resmsg3);
                        if (null != map3) {
                            String flag = (String) map3.get("success");
                            if (flag.equals("true")) {
                                resmap.put("randomNum", randomNum);
                                resmap.put("flag", true);
                                return resmap;
                            }
                        }
                    }
                }
            }
        }
        resmap.put("flag",false);
        return resmap;
    }

    /**
     * 生成6位短信验证码
     */
    public static String getMessageTestNumber(){
        String numberstr="0123456789";
        StringBuilder resstr=new StringBuilder();
        for(int i=0;i<6;i++){
            int charnum=(int)(0+Math.random()*(10-1+1));
            resstr.append(numberstr.charAt(charnum));
        }
        return resstr.toString();
    }
}
