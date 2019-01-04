/**
 * @(#)OaBrandFeeServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-4-8 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.oa.dao.OaBrandFeeMapper;
import com.hd.agent.oa.model.OaBrandFee;
import com.hd.agent.oa.model.OaBrandFeeDetail;
import com.hd.agent.oa.service.IOaBrandFeeService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 品牌费用申请单（支付）Service 实现类
 * 
 * @author limin
 */
public class OaBrandFeeServiceImpl extends BaseFilesServiceImpl implements IOaBrandFeeService {

	private OaBrandFeeMapper oaBrandFeeMapper;

	public OaBrandFeeMapper getOaBrandFeeMapper() {
		return oaBrandFeeMapper;
	}

	public void setOaBrandFeeMapper(OaBrandFeeMapper oaBrandFeeMapper) {
		this.oaBrandFeeMapper = oaBrandFeeMapper;
	}

	@Override
	public OaBrandFee selectOaBrandFee(String id) throws Exception {
		return oaBrandFeeMapper.selectOaBrandFee(id);
	}

	@Override
	public List<OaBrandFeeDetail> selectBrandFeeDetailByBillid(String billid) throws Exception {
		return oaBrandFeeMapper.selectBrandFeeDetailByBillid(billid);
	}

	@Override
	public int addBrandFee(OaBrandFee fee, List<OaBrandFeeDetail> list) throws Exception {

		if(isAutoCreate("t_oa_brandfee")) {
			fee.setId(getAutoCreateSysNumbderForeign(fee, "t_oa_brandfee"));
		} else {
			fee.setId("PPFY-" + CommonUtils.getDataNumberSendsWithRand());
		}

		SysUser user = getSysUser();
		fee.setAdduserid(user.getUserid());
		fee.setAddusername(user.getName());
		fee.setAdddeptid(user.getDepartmentid());
		fee.setAdddeptname(user.getDepartmentname());
		int ret = oaBrandFeeMapper.insertOaBrandFee(fee);

		if(ret > 0) {

			for(OaBrandFeeDetail detail : list) {

				if(StringUtils.isEmpty(detail.getReason())) {
					continue;
				}

				detail.setBillid(fee.getId());
				oaBrandFeeMapper.insertOaBrandFeeDetail(detail);
			}
		}

		return ret;
	}

	@Override
	public int editBrandFee(OaBrandFee fee, List<OaBrandFeeDetail> list) throws Exception {

		SysUser user = getSysUser();
		fee.setModifyuserid(user.getUserid());
		fee.setModifyusername(user.getName());
		int ret = oaBrandFeeMapper.updateOaBrandFee(fee);

		if(ret > 0) {

			oaBrandFeeMapper.deleteOaBrandFeeDetailByBillid(fee.getId());
			for(OaBrandFeeDetail detail : list) {

				if(StringUtils.isEmpty(detail.getReason())) {
					continue;
				}

				detail.setBillid(fee.getId());
				oaBrandFeeMapper.insertOaBrandFeeDetail(detail);
			}
		}

		return ret;
	}
}

