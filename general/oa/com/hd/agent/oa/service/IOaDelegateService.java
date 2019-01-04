/**
 * @(#)IOaDelegateService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-4-21 limin 创建版本
 */
package com.hd.agent.oa.service;

import com.hd.agent.oa.model.OaDelegate;
import org.apache.ibatis.annotations.Param;

/**
 * 工作委托规则Service
 *
 * @author limin
 */
public interface IOaDelegateService {

    /**
     * 查询工作委托规则
     * @param id
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 22, 2016
     */
    public OaDelegate selectOaDelegate(String id) throws Exception;

    /**
     * 新增工作委托规则
     * @param delegate
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 22, 2016
     */
    public int addOaDelegate(OaDelegate delegate) throws Exception;

    /**
     * 编辑工作委托规则
     * @param delegate
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 22, 2016
     */
    public int editOaDelegate(OaDelegate delegate) throws Exception;
}