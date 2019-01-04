/**
 * @(#)PaymentdaysSetServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 15, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.report.dao.PaymentdaysSetMapper;
import com.hd.agent.report.model.PaymentdaysSet;
import com.hd.agent.report.service.IPaymentdaysSetService;

import java.util.List;

/**
 * 
 * 超账期设置service实现类
 * @author chenwei
 */
public class PaymentdaysSetServiceImpl extends BaseFilesServiceImpl implements
		IPaymentdaysSetService {
	
	private PaymentdaysSetMapper paymentdaysSetMapper;

	public PaymentdaysSetMapper getPaymentdaysSetMapper() {
		return paymentdaysSetMapper;
	}

	public void setPaymentdaysSetMapper(PaymentdaysSetMapper paymentdaysSetMapper) {
		this.paymentdaysSetMapper = paymentdaysSetMapper;
	}

    @Override
    public boolean addPaymentdays(List<PaymentdaysSet> list,String type) throws Exception {
        SysUser sysUser = getSysUser();
        paymentdaysSetMapper.deletePaymentdays(sysUser.getUserid(),type);
        for(PaymentdaysSet paymentdaysSet : list){
            paymentdaysSet.setAdduserid(sysUser.getUserid());
            if(null!=paymentdaysSet.getDetail() && !"".equals(paymentdaysSet.getDetail())){
                paymentdaysSet.setType(type);
                paymentdaysSetMapper.addPaymentdays(paymentdaysSet);
            }
        }
        return true;
    }

    @Override
    public List getPaymentdaysSetInfo() throws Exception {
        SysUser sysUser = getSysUser();
        List list = paymentdaysSetMapper.getPaymentdaysSetByUserid(sysUser.getUserid(),"1");
        return list;
    }

    /**
     * 获取当前用户的超账期区间设置
     *
     * @param type
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 15, 2013
     */
    @Override
    public List getPaymentdaysSetInfo(String type) throws Exception {
        SysUser sysUser = getSysUser();
        List list = paymentdaysSetMapper.getPaymentdaysSetByUserid(sysUser.getUserid(),type);
        return list;
    }
	
}

