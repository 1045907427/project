package com.hd.agent.system.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.service.IUpdateDBService;

import java.util.Map;

/**
 * Created by master on 2017/3/20.
 */
public class UpdateDBAction extends BaseAction {
    private IUpdateDBService updateDBService;

    public IUpdateDBService getUpdateDBService() {
        return updateDBService;
    }

    public void setUpdateDBService(IUpdateDBService updateDBService) {
        this.updateDBService = updateDBService;
    }

    /**
     * SQL 更新列表
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Mar 20, 2017
     */
    public String showSysUpdateDBListPage() throws Exception{
        return SUCCESS;
    }

    /**
     * SQL 更新列表数据
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Mar 20, 2017
     */
    public String getSysUpdateDBPageListData() throws Exception{
        Map map= CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData=updateDBService.getUpdateDBPageListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }
}
