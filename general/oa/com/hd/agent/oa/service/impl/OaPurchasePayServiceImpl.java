/**
 * @(#)OaPurchasePayServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-9-14 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.oa.dao.OaPurchasePayMapper;
import com.hd.agent.oa.model.OaPurchasePay;
import com.hd.agent.oa.service.IOaPurchasePayService;

/**
 * 行政采购付款申请单Service实现类
 *
 * Created by limin on 2016/9/14.
 */
public class OaPurchasePayServiceImpl extends BaseFilesServiceImpl implements IOaPurchasePayService {

    private OaPurchasePayMapper oaPurchasePayMapper;

    public OaPurchasePayMapper getOaPurchasePayMapper() {
        return oaPurchasePayMapper;
    }

    public void setOaPurchasePayMapper(OaPurchasePayMapper oaPurchasePayMapper) {
        this.oaPurchasePayMapper = oaPurchasePayMapper;
    }

    @Override
    public OaPurchasePay selectPurchasePay(String id) throws Exception {
        return oaPurchasePayMapper.selectPurchasePay(id);
    }

    @Override
    public int addOaPurchasePay(OaPurchasePay pay) throws Exception {

        // 获取自动编号
        if (isAutoCreate("t_oa_purchase_pay")) {
            String id = getAutoCreateSysNumbderForeign(pay, "t_oa_purchase_pay");
            pay.setId(id);
        } else {
            pay.setId("CGFK-" + CommonUtils.getDataNumberSendsWithRand());
        }

        SysUser user = getSysUser();
        pay.setAdduserid(user.getUserid());
        pay.setAddusername(user.getName());

        int ret = oaPurchasePayMapper.insertPurchasePay(pay);
        return ret;
    }

    @Override
    public int editOaPurchasePay(OaPurchasePay pay) throws Exception {

        SysUser user = getSysUser();
        pay.setModifyuserid(user.getUserid());
        pay.setModifyusername(user.getName());

        int ret = oaPurchasePayMapper.updatePurchasePay(pay);

        return ret;
    }
}
