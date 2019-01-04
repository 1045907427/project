package com.hd.agent.system.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.system.service.ISyncService;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by xuxin on 2017/5/18.
 */
public class SyncAction extends BaseAction {
    private ISyncService syncService;

    public ISyncService getSyncService() {
        return syncService;
    }

    public void setSyncService(ISyncService syncService) {
        this.syncService = syncService;
    }

    /**
     * 显示数据源配置列表页面
     *
     * @return
     * @throws Exception
     * @author xuxin
     * @date May 18, 2017
     */
    public String dataSync() throws Exception {
        return SUCCESS;
    }

    /**
     * 添加数据源配置
     *
     * @return
     * @throws Exception
     * @author xuxin
     * @date May 18, 2017
     */
    @UserOperateLog(key = "Sync", type = 2)
    public String subDataSync() throws Exception {
        String dataSource = request.getParameter("dataSource");
        //String dataTarget = request.getParameter("dataTarget");
        String code = request.getParameter("code");
        String businessdate = request.getParameter("businessdate");
        String businessdate1 = null;
        String businessdate2 = null;
        if (StringUtils.isNotEmpty(businessdate)) {
            Date busdate = CommonUtils.stringToDate(businessdate,"yyyy-MM");
            businessdate1 = CommonUtils.getNowMonthDay(busdate);
            businessdate2 = CommonUtils.dataToStr(CommonUtils.getLastDayOfMonth(busdate),"yyyy-MM-dd") ;
        }
        Map result = syncService.syncData(dataSource, code, businessdate1, businessdate2);
        addLog(String.format("数据同步.dataSource:%s;code:%s;businessdate1:%s;businessdate2:%s;结果:%s", dataSource, code, businessdate1, businessdate2, result.get("msg")), result);
        addJSONObject(result);
        return SUCCESS;
    }
}
