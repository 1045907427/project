/**
 * @(#)DistributionRuleMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-11-1 limin 创建版本
 */
package com.hd.agent.basefiles.dao;

import com.hd.agent.basefiles.model.DistributionRule;
import com.hd.agent.basefiles.model.DistributionRuleDetail;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DistributionRuleMapper {

    /**
     * 查询分销规则
     *
     * @param id
     * @return
     * @author limin
     * @date Nov 1, 2016
     */
    public DistributionRule selectDistributionRule(@Param("id") String id);

    /**
     * 删除分销规则
     *
     * @param id
     * @return
     * @author limin
     * @date Nov 1, 2016
     */
    public int deleteDistributionRule(@Param("id") String id);

    /**
     * 新增分销规则
     *
     * @param rule
     * @return
     * @author limin
     * @date Nov 1, 2016
     */
    public int insertDistributionRule(DistributionRule rule);

    /**
     * 修改分销规则
     *
     * @param rule
     * @return
     * @author limin
     * @date Nov 1, 2016
     */
    public int updateDistributionRule(DistributionRule rule);

    /**
     * 根据规则编号删除规则明细
     *
     * @param ruleid
     * @return
     * @author limin
     * @date Nov 3, 2016
     */
    public int deleteDistributionRuleDetailByRuleid(@Param("ruleid") String ruleid);

    /**
     * 新增分销规则明细
     *
     * @param detail
     * @return
     * @author limin
     * @date Nov 3, 2016
     */
    public int insertDistributionRuleDetail(DistributionRuleDetail detail);

    /**
     * 查询分销规则list
     *
     * @param pageMap
     * @return
     * @author limin
     * @date Nov 4, 2016
     */
    public List<DistributionRule> selectDistributionRuleList(PageMap pageMap);

    /**
     * 查询分销规则count
     *
     * @param pageMap
     * @return
     * @author limin
     * @date Nov 4, 2016
     */
    public int selectDistributionRuleTotalCount(PageMap pageMap);

    /**
     * 根据规则编号查询规则明细
     *
     * @param ruleid
     * @return
     * @author limin
     * @date Nov 4, 2016
     */
    public List<DistributionRuleDetail> selectDistributionRuleDetailListByRuleid(@Param("ruleid") String ruleid);

    /**
     * 启用分销规则
     *
     * @param map
     * @return
     * @author limin
     * @date Nov 4, 2016
     */
    public int enableDistributionRule(Map map);

    /**
     * 启用分销规则
     *
     * @param map
     * @return
     * @author limin
     * @date Nov 4, 2016
     */
    public int disableDistributionRule(Map map);

    /**
     * 查询与客户相关的分销规则
     *
     * @param map
     * @return
     * @author limin
     * @date Nov 9, 2016
     */
    public List<DistributionRule> selectDistributionRuleIdByCustomer(Map map);

    /**
     * 查询与客户相关的商品编码
     *
     * @param map
     * @return
     * @author limin
     * @date Nov 9, 2016
     */
    public List<String> selectRelatedGoodsidByCustomer(Map map);

    /**
     * 查询与客户相关的品牌编码
     *
     * @param map
     * @return
     * @author limin
     * @date Nov 9, 2016
     */
    public List<String> selectRelatedBrandidByCustomer(Map map);

    /**
     * 查询与客户相关的商品分类
     *
     * @param map
     * @return
     * @author limin
     * @date Nov 9, 2016
     */
    public List<String> selectRelatedGoodssortByCustomer(Map map);

    /**
     * 查询与客户相关的商品类型
     *
     * @param map
     * @return
     * @author limin
     * @date Nov 9, 2016
     */
    public List<String> selectRelatedGoodstypeByCustomer(Map map);

    /**
     * 查询与客户相关的供应商编码
     *
     * @param map
     * @return
     * @author limin
     * @date Nov 9, 2016
     */
    public List<String> selectRelatedSupplieridByCustomer(Map map);
    /**
     * 获取全部分销规则列表
     * @return
     * @author limin
     * @date Nov 4, 2016
     */
    public List<DistributionRule> getAllDistributionRuleList(@Param("date") String date);
}