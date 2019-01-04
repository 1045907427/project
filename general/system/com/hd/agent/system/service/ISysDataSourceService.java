package com.hd.agent.system.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysDataSource;

import java.util.Map;

/**
 * Created by master on 2017/3/11.
 */
public interface ISysDataSourceService {

    /**
     * 获取数据源查询列表
     * @param pageMap
     * @return java.util.List<com.hd.agent.system.model.SysDataSource>
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    PageData getSysDataSourceListPageData(PageMap pageMap) throws Exception;

    /**
     * 向数据库添加数据源信息
     * @param sysDataSource
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    Map addSysDataSource(SysDataSource sysDataSource) throws Exception;
    /**
     * 向数据库更新数据源信息
     * @param sysDataSource
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    Map updateSysDataSource(SysDataSource sysDataSource) throws Exception;
    /**
     * 删除新数据源信息
     * @param id
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    Map deleteSysDataSource(String id) throws Exception;

    /**
     * 启用新数据源信息
     * @param id
     * @return map
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    Map enableSysDataSource(String id) throws Exception;

    /**
     * 启用新数据源信息
     * @param id
     * @return map
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    Map disableSysDataSource(String id) throws Exception;
    /**
     * 根据编号获取数据源信息
     * @param id
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    SysDataSource getSysDataSource(String id) throws Exception;
    /**
     * 根据代码获取数据源信息
     * @param code
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    SysDataSource getSysDataSourceByCode(String code) throws Exception;
    /**
     * 根据代码获取启用数据源信息
     * @param code
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    SysDataSource getEnableSysDataSourceByCode(String code) throws Exception;
    /**
     * 根据条件获取相关条数<br/>
     * Map中参数：<br/>
     * notequalid:与id不相等条件:<br/>
     * name：名称<br/>
     * code:代码<br/>
     * @param map
     * @return
     * @author zhanghonghui
     * @date Mar 11, 2017
     */
    int getSysDataSourceCountBy(Map map) throws Exception;

    /**
     * 测试配置项能否正常连接数据库
     * @param dataSource
     * @return
     */
    Map testSysDataSource(SysDataSource dataSource);
}
