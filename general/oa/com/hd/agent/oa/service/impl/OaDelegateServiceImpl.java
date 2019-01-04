/**
 * @(#)IOaDelegateService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-4-21 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.oa.dao.OaDelegateMapper;
import com.hd.agent.oa.model.OaDelegate;
import com.hd.agent.oa.service.IOaDelegateService;

/**
 * 工作委托规则Service 实现类
 *
 * @author limin
 */
public class OaDelegateServiceImpl extends BaseFilesServiceImpl implements IOaDelegateService {

    private OaDelegateMapper oaDelegateMapper;

    public OaDelegateMapper getOaDelegateMapper() {
        return oaDelegateMapper;
    }

    public void setOaDelegateMapper(OaDelegateMapper oaDelegateMapper) {
        this.oaDelegateMapper = oaDelegateMapper;
    }

    @Override
    public OaDelegate selectOaDelegate(String id) throws Exception {
        return oaDelegateMapper.selectOaDelegate(id);
    }

    @Override
    public int addOaDelegate(OaDelegate delegate) throws Exception {

        if(isAutoCreate("t_oa_delegate")) {
            delegate.setId(getAutoCreateSysNumbderForeign(delegate, "t_oa_delegate"));
        } else {
            delegate.setId("WT-" + CommonUtils.getDataNumberSendsWithRand());
        }

        SysUser user = getSysUser();
        delegate.setAdduserid(user.getUserid());
        delegate.setAddusername(user.getName());
        delegate.setAdddeptid(user.getDepartmentid());
        delegate.setAdddeptname(user.getDepartmentname());

        return oaDelegateMapper.insertOaDelegate(delegate);
    }

    @Override
    public int editOaDelegate(OaDelegate delegate) throws Exception {

        SysUser user = getSysUser();
        delegate.setModifyuserid(user.getUserid());
        delegate.setModifyusername(user.getName());

        return oaDelegateMapper.updateOaDelegate(delegate);
    }
}