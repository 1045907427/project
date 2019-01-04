package com.hd.agent.wechat.service;

import java.util.Map;

/**
 * Created by wanghongteng on 2017/5/22.
 */
public interface IWechatUserService {
    /**
     * 发送短信
     */
    public Map sendMessage(String mobile, String type) throws Exception;
}
