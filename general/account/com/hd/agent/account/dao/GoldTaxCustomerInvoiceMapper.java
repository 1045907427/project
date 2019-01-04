package com.hd.agent.account.dao;

import com.hd.agent.account.model.GoldTaxCustomerInvoice;
import com.hd.agent.account.model.GoldTaxCustomerInvoiceDetail;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoldTaxCustomerInvoiceMapper {
    /**
     * 获取客户金税开票分页数据
     * @param pageMap
     * @return java.util.List<com.hd.agent.account.model.GoldTaxCustomerInvoice>
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public List<GoldTaxCustomerInvoice> getGoldTaxCustomerInvoicePageList(PageMap pageMap);

    /**
     * 获取客户金税开票分页条数
     * @param pageMap
     * @return java.util.List<com.hd.agent.account.model.GoldTaxCustomerInvoice>
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int getGoldTaxCustomerInvoicePageCount(PageMap pageMap);

    /**
     * 添加客户金税开票数据
     * @param goldTaxCustomerInvoice
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int insertGoldTaxCustomerInvoice(GoldTaxCustomerInvoice goldTaxCustomerInvoice);

    /**
     * 修改客户金税开票数据
     * @param goldTaxCustomerInvoice
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int updateGoldTaxCustomerInvoce(GoldTaxCustomerInvoice goldTaxCustomerInvoice);
    /**
     * 修改客户金税开票数据单据中金额（金额、未税金额、税额）
     * @param goldTaxCustomerInvoice
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int updateGoldTaxCustomerInvoceAmount(GoldTaxCustomerInvoice goldTaxCustomerInvoice);

    /**
     * 获取客户金税开票数据
     * @param id
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public GoldTaxCustomerInvoice getGoldTaxCustomerInvoice(@Param("id")String id);

    /**
     * 删除客户金税开票数据
     * @param id
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int deleteGoldTaxCustomerInvoice(@Param("id")String id);

    /**
     * 审核客户金税开票数据
     * @param goldTaxCustomerInvoice
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int auditGoldTaxCustomerInvoice(GoldTaxCustomerInvoice goldTaxCustomerInvoice);

    /**
     * 反审客户金税开票数据
     * @param goldTaxCustomerInvoice
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int oppauditGoldTaxCustomerInvoice(GoldTaxCustomerInvoice goldTaxCustomerInvoice);
    /**
     * 获取客户金税开票明细数据合计（金额、未税金额、税额）<br/>
     * 返回数据：<br/>
     * taxamount:含税金额<br/>
     * notaxamount:未税金额<br/>
     * tax:税额
     * @param id
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public GoldTaxCustomerInvoiceDetail getGoldTaxCustomerInvoiceDetailSum(@Param("billid")String billid);
    /**
     * 更新客户金税导出次数<br/>
     * tax:税额
     * @param goldTaxCustomerInvoice
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int updateOrderJSExportTimes(GoldTaxCustomerInvoice goldTaxCustomerInvoice);





    /**
     * 获取客户金税明细分页数据
     * @param pageMap
     * @return java.util.List<com.hd.agent.account.model.GoldTaxCustomerInvoiceDetail>
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public List<GoldTaxCustomerInvoiceDetail> getGoldTaxCustomerInvoiceDetailPageList(PageMap pageMap);
    /**
     * 获取客户金税明细分页数据合计
     * @param pageMap
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int getGoldTaxCustomerInvoiceDetailPageCount(PageMap pageMap);
    /**
     * 添加客户金税明细分页数据
     * @param goldTaxCustomerInvoiceDetail
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int insertGoldTaxCustomerInvoiceDetail(GoldTaxCustomerInvoiceDetail goldTaxCustomerInvoiceDetail);
    /**
     * 根据map中参数获取客户金税开票明细数据<br/>
     * map中参数<br/>
     * billid:单据中编号
     * @param map
     * @return java.util.List<com.hd.agent.account.model.GoldTaxCustomerInvoiceDetail>
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public List<GoldTaxCustomerInvoiceDetail> getGoldTaxCustomerInvoiceDetailListBy(Map map);
    /**
     * 根据billid参数获取客户金税开票明细数据<br/>
     * @param billid
     * @return java.util.List<com.hd.agent.account.model.GoldTaxCustomerInvoiceDetail>
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public List<GoldTaxCustomerInvoiceDetail> getGoldTaxCustomerInvoiceDetailListByBillid(@Param("billid")String billid);

    /**
     * 根据billid参数删除客户金税开票明细数据<br/>
     * @param billid
     * @return java.util.List<com.hd.agent.account.model.GoldTaxCustomerInvoiceDetail>
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public int deleteGoldTaxCustomerInvoiceDetailByBillid(@Param("billid")String billid);
    /**
     * 批量插入客户金税开票数据
     * @param list
     * @return int
     * @throws
     * @author zhanghonghui
     * @date Dec 23, 2017
     */
    public int insertGoldTaxCustomerInvoiceDetailBatch(List<GoldTaxCustomerInvoiceDetail> list);

    /**
     * 合计明细金额
     * @param map
     * @return
     */
    public GoldTaxCustomerInvoiceDetail getGoldTaxCustomerInvoiceDetailSumByMap(Map map);
}
