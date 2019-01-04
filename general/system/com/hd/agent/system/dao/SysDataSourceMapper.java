package com.hd.agent.system.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysDataSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by master on 2017/2/28.
 */
public interface SysDataSourceMapper {

    /**
     * 获取数据源查询列表
     * @param pageMap
     * @return java.util.List<com.hd.agent.system.model.SysDataSource>
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    List<SysDataSource> getSysDataSourcePageList(PageMap pageMap);

    /**
     * 获取数据源查询分页数据
     * @param pageMap
     * @return
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    int getSysDataSourcePageCount(PageMap pageMap);
    /**
     * 向数据库添加数据源信息
     * @param sysDataSource
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    int insertSysDataSource(SysDataSource sysDataSource);
    /**
     * 向数据库更新数据源信息
     * @param sysDataSource
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    int updateSysDataSource(SysDataSource sysDataSource);
    /**
     * 删除新数据源信息
     * @param id
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    int deleteSysDataSource(@Param("id")String id);

    /**
     * 启用新数据源信息
     * @param sysDataSource
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    int enableSysDataSource(SysDataSource sysDataSource);

    /**
     * 启用新数据源信息
     * @param sysDataSource
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    int disableSysDataSource(SysDataSource sysDataSource);
    /**
     * 根据编号获取数据源信息
     * @param id
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    SysDataSource getSysDataSource(@Param("id")String id);
    /**
     * 根据代码获取数据源信息
     * @param code
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    SysDataSource getSysDataSourceByCode(@Param("code")String code);
    /**
     * 根据代码获取启用数据源信息
     * @param code
     * @return int
     * @throws
     * @author zhang_hh
     * @date Mar 11, 2017
     */
    SysDataSource getEnableSysDataSourceByCode(@Param("code")String code);
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
    int getSysDataSourceCountBy(Map map);
}
