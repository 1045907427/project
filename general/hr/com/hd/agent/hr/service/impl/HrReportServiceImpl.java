/**
 * @(#)HrReportMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-9-1 limin 创建版本
 */
package com.hd.agent.hr.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.hr.dao.HrReportMapper;
import com.hd.agent.hr.service.IHrReportService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/9/1.
 */
public class HrReportServiceImpl extends BaseServiceImpl implements IHrReportService{

    private HrReportMapper hrReportMapper;

    public HrReportMapper getHrReportMapper() {
        return hrReportMapper;
    }

    public void setHrReportMapper(HrReportMapper hrReportMapper) {
        this.hrReportMapper = hrReportMapper;
    }

    @Override
    public PageData getSignReportData(PageMap pageMap) throws Exception {

        List<Map> list = hrReportMapper.getSignReportDataList(pageMap);
        for(Map map : list) {

            String deptid = (String) map.get("deptid");
            if(StringUtils.isNotEmpty(deptid)) {

                DepartMent dept = getDepartmentByDeptid(deptid);
                if (dept != null) {

                    map.put("deptname", dept.getName());
                }
            }

            String userid = (String) map.get("userid");
            if(StringUtils.isNotEmpty(userid)) {

                SysUser user = getSysUserById(userid);
                if (user != null) {

                    map.put("username", user.getName());
                }
            }
        }

        int count = hrReportMapper.getSignReportDataCount(pageMap);

        PageData data = new PageData(count, list, pageMap);
        return data;
    }
}
