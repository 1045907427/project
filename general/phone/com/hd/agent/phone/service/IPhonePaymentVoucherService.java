package com.hd.agent.phone.service;

import com.hd.agent.common.util.PageMap;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 手机交款单service
 * Created by chenwei on 2016-07-15.
 */
public interface IPhonePaymentVoucherService {
    /**
     * 添加交款单
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public Map addPaymentVoucher(JSONObject jsonObject) throws Exception;

    /**
     * 获取交款单列表
     * @return
     * @throws Exception
     */
    public List getPaymentVoucherList(PageMap pageMap) throws Exception;

    /**
     * 获取交款单信息
     * @param billid
     * @return
     * @throws Exception
     */
    public Map getPaymentVoucherInfo(String billid) throws Exception;
}
