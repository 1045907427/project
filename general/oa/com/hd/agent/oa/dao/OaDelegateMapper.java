/**
 * @(#)OaDelegateMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-4-21 limin 创建版本
 */
package com.hd.agent.oa.dao;

import com.hd.agent.oa.model.OaDelegate;
import org.apache.ibatis.annotations.Param;

/**
 * 工作委托规则Mapper
 *
 * @author limin
 */
public interface OaDelegateMapper {

    /**
     * 查询委托规则
     * @param id
     * @return
     * @author limin
     * @date @Apr 21, 2016
     */
    public OaDelegate selectOaDelegate(@Param("id")String id);

    /**
     * 删除委托规则
     * @param id
     * @return
     * @author limin
     * @date @Apr 21, 2016
     */
    public int deleteOaDelegate(@Param("id")String id);

    /**
     * 新增委托规则
     * @param delegate
     * @return
     * @author limin
     * @date @Apr 21, 2016
     */
    public int insertOaDelegate(OaDelegate delegate);

    /**
     * 编辑委托规则
     * @param delegate
     * @return
     * @author limin
     * @date @Apr 21, 2016
     */
    public int updateOaDelegate(OaDelegate delegate);
}