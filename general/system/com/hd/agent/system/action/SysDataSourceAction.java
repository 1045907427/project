package com.hd.agent.system.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysDataSource;
import com.hd.agent.system.service.ISysDataSourceService;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by master on 2017/3/12.
 */
public class SysDataSourceAction extends BaseAction {
    /**
     * 业务层
     */
    private ISysDataSourceService sysDataSourceService;

    public ISysDataSourceService getSysDataSourceService() {
        return sysDataSourceService;
    }

    public void setSysDataSourceService(ISysDataSourceService sysDataSourceService) {
        this.sysDataSourceService = sysDataSourceService;
    }

    private SysDataSource sysDataSource;

    public SysDataSource getSysDataSource() {
        return sysDataSource;
    }

    public void setSysDataSource(SysDataSource sysDataSource) {
        this.sysDataSource = sysDataSource;
    }

    /**
     * 显示数据源配置列表页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Mar 12, 2017
     */
    public String showSysDataSourceListPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 数据源配置
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Mar 12, 2017
     */
    public String showSysDataSourcePageListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());

        pageMap.setCondition(map);

        PageData pageData = sysDataSourceService.getSysDataSourceListPageData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 添加数据源配置
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Mar 12, 2017
     */
    @UserOperateLog(key = "SysDataSource", type = 2)
    public String addSysDataSource() throws Exception {
        Map resultMap = new HashMap();
        if (StringUtils.isEmpty(sysDataSource.getName())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写数据源名称");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        resultMap = sysDataSourceService.addSysDataSource(sysDataSource);
        Boolean flag = null;
        if (null != resultMap) {
            flag = (Boolean) resultMap.get("flag");
            if (null == flag) {
                flag = false;
                resultMap.put("flag", flag);
            }
        } else {
            flag = false;
            resultMap.put("flag", false);
        }
        addJSONObject("flag", flag);

        addLog("添加打印模板代码 代码分类:" + sysDataSource.getCode(), flag);
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 编辑数据源配置
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    @UserOperateLog(key = "SysDataSource", type = 3)
    public String editSysDataSource() throws Exception {
        Map resultMap = new HashMap();
        if (StringUtils.isEmpty(sysDataSource.getName())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写数据源配置名称");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        String ismodifypasswd = (String) request.getParameter("ismodifypasswd");
        if ("1".equals(ismodifypasswd)) {
            sysDataSource.setIsmodifypwd("1");
        }
        resultMap = sysDataSourceService.updateSysDataSource(sysDataSource);
        if (null != resultMap) {
            Boolean flag = (Boolean) resultMap.get("flag");
            if (null == flag) {
                flag = false;
            }
            resultMap.put("flag", flag);
            addLog("修改数据源配置", flag);
        } else {
            addLog("修改数据源配置失败");
            resultMap.put("flag", false);
        }
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 删除数据源配置信息
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Mar 12, 2017
     */
    @UserOperateLog(key = "SysDataSource", type = 4)
    public String deleteSysDataSource() throws Exception {
        String id = request.getParameter("id");
        Map resultMap = sysDataSourceService.deleteSysDataSource(id);
        if (null != resultMap) {
            Boolean flag = (Boolean) resultMap.get("flag");
            if (null == flag) {
                flag = false;
            }
            resultMap.put("flag", flag);
            addLog("删除数据源配置", flag);
        } else {
            addLog("删除数据源配置失败");
            resultMap.put("flag", false);
        }
        addJSONObject(resultMap);
        return SUCCESS;
    }


    /**
     * 显示数据源配置添加页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Mar 12, 2017
     */
    public String showSysDataSourceAddPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 显示数据源配置编辑页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Mar 12, 2017
     */
    public String showSysDataSourceEditPage() throws Exception {
        String id = request.getParameter("id");
        SysDataSource oldSysDataSource = sysDataSourceService.getSysDataSource(id);
        request.setAttribute("sysDataSource", oldSysDataSource);
        return SUCCESS;
    }

    /**
     * 显示数据源配置编辑页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Mar 12, 2017
     */
    public String showSysDataSourceViewPage() throws Exception {
        String id = request.getParameter("id");
        SysDataSource oldSysDataSource = sysDataSourceService.getSysDataSource(id);
        request.setAttribute("sysDataSource", oldSysDataSource);
        return SUCCESS;
    }

    /**
     * 禁用数据源配置
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2012-12-15
     */
    @UserOperateLog(key = "SysDataSource", type = 3)
    public String disableSysDataSource() throws Exception {
        String id = request.getParameter("id");
        Map resultMap = sysDataSourceService.disableSysDataSource(id);
        Boolean flag = null;
        if (null != resultMap) {
            flag = (Boolean) resultMap.get("flag");
            if (null == flag) {
                flag = false;
                resultMap.put("flag", flag);
            }
        } else {
            flag = false;
            resultMap.put("flag", false);
        }
        addJSONObject(resultMap);

        addLog("禁用数据源配置 编号:" + id, flag);
        return SUCCESS;
    }

    /**
     * 启用数据源配置
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2012-12-19
     */
    @UserOperateLog(key = "SysDataSource", type = 3)
    public String enableSysDataSource() throws Exception {
        String id = request.getParameter("id");
        Map resultMap = sysDataSourceService.enableSysDataSource(id);
        Boolean flag = null;
        if (null != resultMap) {
            flag = (Boolean) resultMap.get("flag");
            if (null == flag) {
                flag = false;
                resultMap.put("flag", flag);
            }
        } else {
            flag = false;
            resultMap.put("flag", false);
        }
        addJSONObject(resultMap);

        addLog("启用数据源配置 编号:" + id, flag);
        return SUCCESS;
    }

    /**
     * 启用数据源配置
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2012-12-19
     */
    @UserOperateLog(key = "SysDataSource")
    public String testSysDataSource() throws Exception {
        Map result = new HashMap();
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        SysDataSource dataSource;
        if (StringUtils.isNotEmpty(id)) {
            dataSource = sysDataSourceService.getSysDataSource(id);
        } else {
            dataSource = sysDataSourceService.getSysDataSourceByCode(code);
        }
        if (dataSource == null) {
            result.put("flag", false);
            result.put("msg", "找不到数据源信息");
        } else {
            result = sysDataSourceService.testSysDataSource(dataSource);
        }
        addJSONObject(result);

        addLog("测试数据源配置 编号:" + id + ".code:" + code + "结果:" + result.get("msg"), result);
        return SUCCESS;
    }

    /**
     * 验证科目档案分类代码是否被使用，true 被使用，false 未被使用
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 21, 2013
     */
    public String isValidDataUsed() throws Exception {
        String validdata = request.getParameter("validdata");
        String validtype = request.getParameter("validtype");
        if (null == validtype || "".equals(validtype.trim())
                || null == validdata || "".equals(validdata.trim())) {
            addJSONObject("flag", false);
            return SUCCESS;
        }
        boolean flag = false;
        Map queryMap = new HashMap();
        if ("name".equals(validtype.trim())) {
            queryMap.put("name", validdata.trim());
        } else if ("code".equals(validtype.trim())) {
            queryMap.put("code", validdata.trim());
        } else {
            addJSONObject("flag", flag);
            return SUCCESS;
        }
        flag = sysDataSourceService.getSysDataSourceCountBy(queryMap) > 0;
        addJSONObject("flag", flag);
        return SUCCESS;
    }
}
