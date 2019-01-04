package com.hd.agent.salestarget.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.salestarget.model.SalesTargetInput;

import java.util.List;
import java.util.Map;

/**
 * Created by master on 2016/7/15.
 */
public interface ISalesTargetInputService {
    /**
     * 销售目标分页列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public PageData showSalesTargetInputListPageData(PageMap pageMap)throws Exception;

    /**
     * 添加销售目标
     * @param salesTargetInput
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public Map addSalesTargetInput(SalesTargetInput salesTargetInput) throws Exception;

    /**
     * 添加销售目标
     * @param salesTargetInput
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public Map editSalesTargetInput(SalesTargetInput salesTargetInput) throws Exception;

    /**
     * 获取销售目标信息
     * @param id
     * @return
     * @throws Exception
     */
    public SalesTargetInput showSalesTargetInput(String id) throws Exception;
    /**
     * 删除销售目标
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public Map deleteSalesTargetInput(String id) throws Exception;


    /**
     * 批量删除销售目标
     * @param idarrs
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public Map deleteSalesTargetInputMore(String idarrs) throws Exception;

    /**
     * 审核销售目标
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public Map auditSalesTargetInput(String id) throws Exception;
    /**
     * 批量审核销售目标
     * @param idarrs
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public Map auditSalesTargetInputMore(String idarrs) throws Exception;

    /**
     * 批量反审销售目标
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public Map oppauditSalesTargetInput(String id) throws Exception;
    /**
     * 批量反审销售目标
     * @param idarrs
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public Map oppauditSalesTargetInputMore(String idarrs) throws Exception;

    /**
     * 新增导入功能
     * @param list
     * @return
     * @throws
     * @author zhang_honghui
     * @date Sep 20, 2016
     */
    public Map addDRSalesTargetInput(List<Map> list) throws Exception;

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
    public List<Map> getSalesTargetInputGroupListBy(Map paramMap) throws Exception;

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
    public List<Map> getBrandCustomerSortListInSalesReport(Map paramMap) throws Exception;
}
