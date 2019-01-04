/**
 * @(#)SigninServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-18 limin 创建版本
 */
package com.hd.agent.hr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.hr.dao.SigninMapper;
import com.hd.agent.hr.model.Signin;
import com.hd.agent.hr.service.ISigninService;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author limin
 */
public class SigninServiceImpl extends BaseServiceImpl implements ISigninService {

	private SigninMapper signinMapper;

	public SigninMapper getSigninMapper() {
		return signinMapper;
	}

	public void setSigninMapper(SigninMapper signinMapper) {
		this.signinMapper = signinMapper;
	}

	@Override
	public PageData selectSigninList(PageMap map) throws Exception {

		int cnt = signinMapper.selectSigninListCount(map);
		List<Signin> list = (List<Signin>)signinMapper.selectSigninList(map);
        for(Signin signin : list) {

            String deptid = signin.getDeptid();
            if(StringUtils.isNotEmpty(deptid)) {

                DepartMent dept = getDepartmentByDeptid(deptid);
                if (dept != null) {
                    signin.setDeptname(dept.getName());
                }
            }
        }

		PageData data = new PageData(cnt, list, map);
		return data;
	}

	@Override
	public Signin selectSigninInfo(String id) throws Exception {

		Signin signin = signinMapper.selectSigninInfo(id);
		return signin;
	}

    @Override
    public Map deleteSignin(String ids) throws Exception {

		String[] idArr = ids.split(",");
		int success = 0;
		String successIds = "";
		int failure = 0;
		for(String id : idArr) {

			int ret = signinMapper.deleteSignin(id);
			if(ret == 0) {
				failure++;
			} else {
				success++;
				successIds = successIds + "," + id;
			}
		}

		Map map = new HashMap();
		map.put("success", success);
		map.put("failure", failure);
		map.put("successIds", successIds.length() > 0 ? successIds.substring(1) : "");
        return map;
    }
}

