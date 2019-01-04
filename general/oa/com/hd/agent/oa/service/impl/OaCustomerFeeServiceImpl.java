package com.hd.agent.oa.service.impl;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.dao.OaCustomerFeeMapper;
import com.hd.agent.oa.model.OaCustomerFee;
import com.hd.agent.oa.model.OaCustomerFeeDetail;
import com.hd.agent.oa.service.IOaCustomerFeeService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 客户费用申请单（账扣）Service实现类
 * @author limin
 * @date Mar 23, 2016
 */
public class OaCustomerFeeServiceImpl extends BaseFilesServiceImpl implements IOaCustomerFeeService {

    private OaCustomerFeeMapper oaCustomerFeeMapper;

    public OaCustomerFeeMapper getOaCustomerFeeMapper() {
        return oaCustomerFeeMapper;
    }

    public void setOaCustomerFeeMapper(OaCustomerFeeMapper oaCustomerFeeMapper) {
        this.oaCustomerFeeMapper = oaCustomerFeeMapper;
    }

    @Override
    public OaCustomerFee selectOaCustomerFee(String id) throws Exception {
        return oaCustomerFeeMapper.selectOaCustomerFee(id);
    }

    @Override
    public int addOaCustomerFee(OaCustomerFee fee, List<OaCustomerFeeDetail> list) throws Exception {

        if(isAutoCreate("t_oa_customerfee")) {
            fee.setId(getAutoCreateSysNumbderForeign(fee, "t_oa_customerfee"));
        } else {
            fee.setId("KHFY-" + CommonUtils.getDataNumberSendsWithRand());
        }

        int ret = oaCustomerFeeMapper.insertOaCustomerFee(fee);

        if(ret > 0) {

            for(OaCustomerFeeDetail detail : list) {

                if(StringUtils.isEmpty(detail.getSupplierid())) {
                    continue;
                }

                detail.setBillid(fee.getId());
                oaCustomerFeeMapper.insertOaCustomerFeeDetail(detail);
            }
        }

        return ret;
    }

    @Override
    public int editOaCustomerFee(OaCustomerFee fee, List<OaCustomerFeeDetail> list) throws Exception {

        int ret = oaCustomerFeeMapper.updateOaCustomerFee(fee);

        if(ret > 0) {

            oaCustomerFeeMapper.deleteOaCustomerFeeDetailByBillid(fee.getId());
            for(OaCustomerFeeDetail detail : list) {

                if(StringUtils.isEmpty(detail.getSupplierid())) {
                    continue;
                }

                detail.setBillid(fee.getId());
                detail.setBillid(fee.getId());
                oaCustomerFeeMapper.insertOaCustomerFeeDetail(detail);
            }
        }

        return ret;
    }

    @Override
    public PageData getCustomerFeeDetailList(PageMap map) throws Exception {

        List list = oaCustomerFeeMapper.getCustomerFeeDetailList(map);
        int count = oaCustomerFeeMapper.getCustomerFeeDetailListCount(map);
        return new PageData(count, list, map);
    }

    @Override
    public List<OaCustomerFeeDetail> getCustomerFeeDetailListByBillid(String billid) throws Exception {
        return oaCustomerFeeMapper.getCustomerFeeDetailListByBillid(billid);
    }
}
