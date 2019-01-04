package com.hd.agent.account.service;

import com.hd.agent.account.model.GoldTaxCustomerInvoice;
import com.hd.agent.account.model.GoldTaxCustomerInvoiceDetail;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

public interface IGoldTaxCustomerInvoiceService {

    /**
     * 获取客户金税开票数据分页数据
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public PageData getGoldTaxCustomerInvoicePageData(PageMap pageMap) throws Exception;
    /**
     * 添加客户金税开票和明细
     * @param goldTaxCustomerInvoice
     * @return map
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public Map addGoldTaxCustomerInvoiceAddDetail(GoldTaxCustomerInvoice goldTaxCustomerInvoice) throws Exception;

    /**
     * 修改客户金税开票和明细
     * @param goldTaxCustomerInvoice
     * @return map
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public Map updateGoldTaxCustomerInvoiceAndDetail(GoldTaxCustomerInvoice goldTaxCustomerInvoice) throws Exception;

    /**
     * 根据编号删除客户金税开票数据
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deleteGoldTaxCustomerInvoiceAndDetail(String id) throws Exception;

    /**
     * 获取客户金税开票数据
     * @param id
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public GoldTaxCustomerInvoice getGoldTaxCustomerInvoice(String id) throws Exception;

    /**
     * 获取客户金税开票数据和明细
     * @param id
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public GoldTaxCustomerInvoice getGoldTaxCustomerInvoiceAndDetail(String id) throws Exception;
    /**
     * 根据单据编号获取金税第三方明细
     * @param id
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public List<GoldTaxCustomerInvoiceDetail> getGoldTaxCustomerInvoiceDetailListByBillid(String id) throws Exception;

    /**
     * 审核客户金税开票数据
     * @param id
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public Map auditGoldTaxCustomerInvoice(String id) throws Exception;
    /**
     * 反审客户金税开票数据
     * @param id
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public Map oppauditGoldTaxCustomerInvoice(String id) throws Exception;
    /**
     * 更新客户金税开票导出次数
     * @param goldTaxCustomerInvoice
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Dec 17, 2017
     */
    public boolean updateOrderJSExportTimes(GoldTaxCustomerInvoice goldTaxCustomerInvoice) throws Exception;
    /**
     * 客户金税开票导入
     * @param dataList
     * @return java.util.Map
     * @throws
     * @author zhanghonghui
     * @date Dec 23, 2017
     */
    public Map importCustomerGoldTaxInvoiceData(String billid,List<Map<String,Object>> dataList) throws Exception;
    /**
     * 修改客户金税开票发票号
     * @param goldTaxCustomerInvoice
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Dec 26, 2017
     */
    public boolean editGoldTaxCustomerInvoiceNo(GoldTaxCustomerInvoice goldTaxCustomerInvoice) throws Exception;

    /**
     * 航天开票XML数据
     * @param map
     * @return java.util.Map
     * @throws
     * @author zhanghonghui
     * @date Dec 27, 2017
     */
    public Map getGoldTaxCustomerInvoiceBillXMLForHTKP(Map map) throws Exception;
}
