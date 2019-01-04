/**
 * @(#)ClientInterfaceServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月14日 limin 创建版本
 */
package com.hd.agent.client.service.impl;

import com.hd.agent.basefiles.dao.CustomerSortMapper;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.CustomerSort;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.SalesArea;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.client.service.IClientInterfaceService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.sales.dao.OffpriceMapper;
import com.hd.agent.system.model.SysCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 *
 *
 * @author limin
 */
public class ClientInterfaceServiceImpl extends BaseFilesServiceImpl implements IClientInterfaceService {

    private OffpriceMapper offpriceMapper;

    public OffpriceMapper getOffpriceMapper() {
        return offpriceMapper;
    }

    public void setOffpriceMapper(OffpriceMapper offpriceMapper) {
        this.offpriceMapper = offpriceMapper;
    }

}
