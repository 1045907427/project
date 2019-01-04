
/**
 * @(#)ISalesInvoiceBillService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Feb 14, 2015 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service;


import com.hd.agent.account.model.SalesInvoiceBill;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 *
 * 申请开票业务借口
 * @author panxiaoxiao
 */
public interface ISalesInvoiceBillService {

    /**
     * 获取申请发票列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 2, 2013
     */
    public PageData getSalesInvoiceBillData(PageMap pageMap) throws Exception;

    /**
     * 根据销售发货回单和销售退货通知单明细 生成销售发票
     * @param ids
     * @param customerid
     * @param iswriteoff
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public Map addSalesInvoiceBillByReceiptAndRejectbill(String ids, String customerid, String iswriteoff) throws Exception;

    /**
     * 获取销售发票开票信息
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 15, 2015
     */
    public Map getSalesInvoiceBillInfo(String id) throws Exception;

    /**
     * 销售发票开票是否有冲差
     * @param invoicebillid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 15, 2015
     */
    public int getSalesInvoiceBillHasBlance(String invoicebillid) throws Exception;

    /**
     * 销售开票修改
     * @param salesInvoiceBill
     * @param delgoodsids
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 15, 2015
     */
    public Map editSalesInvoiceBill(SalesInvoiceBill salesInvoiceBill, String delgoodsids) throws Exception;

    /**
     * 销售开票审核
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public boolean auditSalesInvoiceBill(String id) throws Exception;

    /**
     * 销售开票反审
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public boolean oppauditSalesInvoiceBill(String id) throws Exception;

    /**
     * 销售开票删除
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public Boolean deleteSalesInvoiceBill(String id) throws Exception;

    /**
     * 获取销售开票上游单据列表
     * @param salesinvoicebillid
     * @param sourcetype
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public List showSalesInvoiceBillSourceListReferPage(String salesinvoicebillid, String sourcetype) throws Exception;

    /**
     * 销售开票中删除明细，回写来源单据开票状态，开票金额、未开票金额等
     * @param map
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public Map deleteSalesInvoiceBillSource(Map map) throws Exception;

    /**
     * 获取销售开票明细数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public PageData showSalesInvoiceBillDetailData(PageMap pageMap) throws Exception;

    /**
     * 根据开票编号获取销售开票列表
     * @param invoiceid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public List getSalesInvoiceBillListByInvoiceids(String invoiceid) throws Exception;

    /**
     * 回退销售开票（作废 中止）
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 25, 2015
     */
    public boolean updateSalesInvoiceBillCancel(String id) throws Exception;

    /**
     * 显示销售开票
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 26, 2015
     */
    public SalesInvoiceBill getPureSalesInvoiceBillPureInfo(String id) throws Exception;

    /**
     * 根据客户编号获取销售开票列表
     * @param customerid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 26, 2015
     */
    public List showSalesInvoiceBillListPageByCustomer(String customerid,String billtype) throws Exception;

    /**
     * 根据销售发货回单和销售退货通知单明细 追加到销售开票中
     * @param ids
     * @param customerid
     * @param billid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 26, 2015
     */
    public Map addToSalesInvoiceBillByReceiptAndRejectbill(String ids, String customerid, String billid) throws Exception;

    /**
     * 修改销售开票发票号、发票代码
     * @param salesInvoiceBill
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 26, 2015
     */
    public boolean editSalesInvoiceBillInvoiceno(SalesInvoiceBill salesInvoiceBill) throws Exception;

    /**
     * 批量删除申请开票回退
     * @param ids
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-01
     */
    public Map salesInvoiceBillMutiDelete(String ids) throws Exception;

    /**
     * 申请核销
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2015-03-01
     *
     * @throws Exception
     */
    public Map doSalesInvoiceBillMuApplyWriteOff(String id) throws Exception;

    /**
     * 检查选中的销售开票是否允许申请核销
     * @param id
     * @return true 已存在核销过的单据 false 不存在核销过的单据
     * @author panxiaoxiao
     * @date 2015-03-01
     *
     * @throws Exception
     */
    public boolean checkSalesInvoiceBillCanMuApplyWriteOff(String id) throws Exception;

    /**
     * 检查选中的销售开票是否已完全生成申请核销
     * @param id
     * @return false 已完全申请核销 true未完全申请核销
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-02
    */
    public boolean checkSalesInvoiceBillCanMuApplyWriteOffAll(String id) throws Exception;

    /**
     * 根据来源单据编号和来源类型生成开票(预开票)
     * @param ids
     * @param customerid
     * @return map
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-11
     */
    public Map addSalesAdvanceBillByRefer(String ids, String customerid)throws Exception;
    /**
     * 销售预开票删除
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-12
     */
    public boolean deleteSalesAdvanceBill(String id)throws Exception;
    /**
     * 根据销售发货单明细 追加到销售预开票中
     * @param ids
     * @param customerid
     * @param billid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-13
     */
    public Map addToSalesInvoiceBillBySaleout(String ids, String customerid, String billid)throws Exception;
    /**
     * 获取销售开票打印列表<br/>
     * map中参数：<br/>
     * idarrs: id字符串组，格式1,2,3<br/>
     * showdetail : 1表示查询明细列表<br/>
     * @param map
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-16
     */
    public List<SalesInvoiceBill> showSalesInvoiceBillPrintListBy(Map map)throws Exception;

    /**
     * 更新打印次数
     * @param salesInvoiceBill
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-16
     */
    public boolean updateOrderPrinttimes(SalesInvoiceBill salesInvoiceBill)throws Exception;

    /**
     * 关闭来源单据全部核销了的申请开票单据
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-01-14
     */
    public void doCloseWriteoffSourcesInvoicebill()throws Exception;

    /**
     * 检查选中的销售开票是否允许申请核销
     * @param ids
     * @return
     * @throws Exception
     */
    public Map doCheckSalesInvoiceBillCanMuApplyWriteOff(String ids)throws Exception;

    /**
     * 根据开票编号获取开票明细分页列表数据
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData getSalesInvoiceBillDetailList(PageMap pageMap)throws Exception;

    /**
     * 获取销售开票列表数据
     * @param map
     * @return
     * @throws Exception
     */
    public List getSalesInvoiceBillListByPhone(Map map) throws Exception;

    /**
     * 根据发票号或者发票单据编号 获取销售开票列表
     * @param ids
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-03-17
     */
    public List getSalesInvoiceBillListPageByIds(String ids) throws Exception;

    /**
     * 获取航天开票信息格式的销售开票
     * @param id
     * @return java.util.Map
     * @throws
     * @author zhang_honghui
     * @date Dec 30, 2016
     */
    public Map getSalesInvoiceBillForHTKP(String id) throws Exception;

    /**
     * 导出xml格式时，获取航天开票信息格式的销售开票列表数据
     * @param id
     * @return java.util.Map
     * @throws
     * @author zhang_honghui
     * @date Dec 30, 2016
     */
    public Map getSalesInvoiceBillXMLForHTKP(String id) throws Exception;
    /**
     * 获取航天开票信息格式的销售开票 红字发票
     * @param id
     * @return java.util.Map
     * @throws
     * @author zhang_honghui
     * @date Dec 30, 2016
     */
    public Map getSalesInvoiceBillForHTKPHZ(String id) throws Exception ;
    /**
     * 金税导出次数更新
     * @param salesInvoiceBill
     * @return boolean
     * @throws
     * @author zhang_honghui
     * @date Dec 31, 2016
     */
    public boolean updateOrderJSExportTimes(SalesInvoiceBill salesInvoiceBill) throws Exception;

    /**
     * 判断并组装导入的数据是否符合规范
     * @param orderId
     * @param dataList
     * @return java.util.Map
     * @throws
     * @author zhanghonghui
     * @date Apr 26, 2017
     */
    public Map andCheckSalesInvoiceBillListByImportForHTKP(String orderId,List<Map<String,Object>> dataList) throws Exception;

    /**
     * 更新金税人员相关系统配置
     * @param paramMap
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Mar 20, 2017
     */
    Map updateJSKPSysParamConfig(Map paramMap) throws Exception;

    /**
     * 申请开票 生成凭证
     * @throws
     * @author lin_xx
     * @date 2018-01-26
     */
    public List<Map> getSalesInvoiceBillByIdList(List<String> list) throws Exception;

    /**
     * 获取销售开票导出数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Mar 02, 2018
     */
    public List getSalesInvoiceBillExportData(PageMap pageMap) throws Exception;

}

