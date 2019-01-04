package com.hd.agent.agprint.action;

import com.hd.agent.basefiles.action.BaseFilesAction;

/**
 * Created by xuxin on 2017/4/25 0025.
 */
public class PrintBaseAction extends BaseFilesAction {

    //region 属性
    private String code;
    private String templateFilePath;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemplateFilePath() {
        return templateFilePath;
    }

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }
    //endregion


}
