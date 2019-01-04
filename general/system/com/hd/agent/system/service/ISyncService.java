package com.hd.agent.system.service;

import com.hd.agent.system.model.SysDataSource;

import java.util.Map;

/**
 * Created by xuxin on 2017/5/18 0018.
 */
public interface ISyncService {
    Map syncData(String dataSource, String code, String bussinessdata1, String bussinessdata2) throws Exception;
}
