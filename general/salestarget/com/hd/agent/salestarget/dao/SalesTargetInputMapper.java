package com.hd.agent.salestarget.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.salestarget.model.SalesTargetInput;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 销售目标数据操作层
 * Created by master on 2016/7/14.
 */
public interface SalesTargetInputMapper {

    /**
     * 获取分页数据
     * @param pageMap
     * @return
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public List getSalesTargetInputPageList(PageMap pageMap);
    /**
     * 获取分页数据条数合计
     * @param pageMap
     * @return
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public int getSalesTargetInputPageCount(PageMap pageMap);

    /**
     * 获取分布数据金额统计
     * @param pageMap
     * @return
     */
    public SalesTargetInput getSalesTargetInputPageSum(PageMap pageMap);
    /**
     * 添加销售目标
     * @param salesTargetInput
     * @return
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public int insertSalesTargetInput(SalesTargetInput salesTargetInput);

    /**
     * 更新销售目标
     * @param salesTargetInput
     * @return
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public int updateSalesTargetInput(SalesTargetInput salesTargetInput);

    /**
     * 删除销售目标
     * @param id
     * @return
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public int deleteSalesTargetInput(@Param("id")String id);

    /**
     * 获取销售目标
     * @param id
     * @return
     */
    public  SalesTargetInput getSalesTargetInput(@Param("id")String id);

    /**
     * 审核销售目标
     * @param salesTargetInput
     * @return
     */
    public int auditSalesTargetInput(SalesTargetInput salesTargetInput);

    /**
     * 反审销售目标
     * @param id
     * @return
     */
    public int oppauditSalesTargetInput(@Param("id")String id);

    /**
     * 获取第一，第二销售目标及毛利合计数据
     * @param queryMap
     * @return
     */
    public Map getSalesTargetInputSum(Map queryMap);
    /**
     * 根据参数获取销售目标中的客户分类或品牌分组数据<br/>
     * Map中参数：<br/>
     * grouptype: 分组类型值为customersort 或者 brandid,必填<br/>
     * customersort: 客户分类<br/>
     * brandid：品牌<br/>
     * status:状态<br/>
     * statusarr:状态值，字符串列表例如:3,4<br/>
     * 如果status 或statusarr都没有，默认为3,4<br/>
     * @param paramMap
     * @return java.util.List<java.util.Map>
     * @throws
     * @author zhanghonghui
     * @date Sep 22, 2016
     */
    public List<Map> getSalesTargetInputGroupListBy(Map paramMap);

    /**
     * 根据参数获取销售报表中的客户分类或品牌分组数据<br/>
     * Map中参数：<br/>
     * grouptype: 分组类型值为customersort 或者 brandid,必填<br/>
     * customersort: 客户分类<br/>
     * brandid：品牌<br/>
     * @param paramMap
     * @return java.util.List<java.util.Map>
     * @throws
     * @author zhanghonghui
     * @date Oct 13, 2016
     */
    public List<Map> getBrandCustomersortListInSalesReport(Map paramMap);
}
