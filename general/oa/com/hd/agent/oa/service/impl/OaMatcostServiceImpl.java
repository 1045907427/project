/**
 * @(#)OaMatcostServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2018-2-12 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.ExpensesSort;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.oa.dao.OaMatcostMapper;
import com.hd.agent.oa.model.OaMatcost;
import com.hd.agent.oa.model.OaMatcostDetail;
import com.hd.agent.oa.service.IOaMatcostService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 代垫费用申请单Service
 *
 * @author limin
 * @date Feb 12, 2018
 */
public class OaMatcostServiceImpl extends BaseFilesServiceImpl implements IOaMatcostService {

    private OaMatcostMapper oaMatcostMapper;

    public OaMatcostMapper getOaMatcostMapper() {
        return oaMatcostMapper;
    }

    public void setOaMatcostMapper(OaMatcostMapper oaMatcostMapper) {
        this.oaMatcostMapper = oaMatcostMapper;
    }

    @Override
    public OaMatcost getOaMatcost(String id) {
        return oaMatcostMapper.selectOaMatcost(id);
    }

    @Override
    public List<OaMatcostDetail> getOaMatcostDetailListByBillid(String billid) throws Exception {
        List<OaMatcostDetail> detailList = oaMatcostMapper.selectOaMatcostDetailListByBillid(billid);
        for (OaMatcostDetail detail : detailList) {

            Customer customer = getCustomerByID(detail.getCustomerid());
            if (customer != null) {
                detail.setCustomername(customer.getName());
            }

            Brand brand = getGoodsBrandByID(detail.getBrandid());
            if (brand != null) {
                detail.setBrandname(brand.getName());
            }

            ExpensesSort expensesSort = getExpensesSortByID(detail.getExpensesort());
            if (expensesSort != null) {
                detail.setExpensesortname(expensesSort.getName());
            }
        }
        return detailList;
    }

    @Override
    public int addOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        if(isAutoCreate("t_oa_matcost")) {
            matcost.setId(getAutoCreateSysNumbderForeign(matcost, "t_oa_matcost"));
        } else {
            matcost.setId("DDFY-" + CommonUtils.getDataNumberSendsWithRand());
        }

        SysUser user = getSysUser();
        matcost.setAdduserid(user.getUserid());
        matcost.setAddusername(user.getName());
        matcost.setAdddeptid(user.getDepartmentid());
        matcost.setAdddeptname(user.getDepartmentname());

        int ret = oaMatcostMapper.insertOaMatcost(matcost);

        if(ret > 0) {

            for(OaMatcostDetail detail : detailList) {

                if(StringUtils.isEmpty(detail.getCustomerid())) {
                    continue;
                }

                detail.setBillid(matcost.getId());
                oaMatcostMapper.insertOaMatcostDetail(detail);
            }
        }

        return ret;
    }

    @Override
    public int editOaMatcost(OaMatcost matcost, List<OaMatcostDetail> detailList) throws Exception {

        SysUser user = getSysUser();
        matcost.setModifyuserid(user.getUserid());
        matcost.setModifyusername(user.getName());

        int ret = oaMatcostMapper.updateOaMatcost(matcost);

        if(ret > 0) {

            oaMatcostMapper.deleteOaMatcostDetailByBillid(matcost.getId());
            for(OaMatcostDetail detail : detailList) {

                if(StringUtils.isEmpty(detail.getCustomerid())) {
                    continue;
                }

                detail.setBillid(matcost.getId());
                oaMatcostMapper.insertOaMatcostDetail(detail);
            }
        }

        return ret;
    }
}
