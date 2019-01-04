package com.hd.agent.account.dao;

//~--- non-JDK imports --------------------------------------------------------

import com.hd.agent.account.model.SalesInvoice;
import com.hd.agent.account.model.SalesInvoiceBill;
import com.hd.agent.account.model.SalesInvoiceBillDetail;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Interface description
 *
 *
 * @author         panxiaoxiao
 */
public interface SalesInvoiceBillMapper {

    /**
     * 新增申请开票
     * @param salesInvoiceBill
     * @return
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public int addSalesInvoiceBill(SalesInvoiceBill salesInvoiceBill);

    /**
     * 新增申请开票明细
     * @param salesInvoiceBillDetail
     * @return
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public int addSalesInvoiceBillDetail(SalesInvoiceBillDetail salesInvoiceBillDetail);

    /**
     * 批量新增申请开票明细
     * @param list
     * @return
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public int addSalesInvoiceBillDetailList(List<SalesInvoiceBillDetail> list);

    /**
     * 修改申请开票
     * @param salesInvoiceBill
     * @return
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public int editSalesInvoiceBill(SalesInvoiceBill salesInvoiceBill);

    /**
     * 获取申请开票列表数据
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public List<SalesInvoiceBill> getSalesInvoiceBillList(PageMap pageMap);

    /**
     * 获取申请开票列表数量
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public int getSalesInvoiceBillCount(PageMap pageMap);

    /**
     * 获取申请开票合计
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public SalesInvoiceBill getSalesInvoiceBillDataSum(PageMap pageMap);

    /**
     * 根据申请开票编号获取申请开票详情
     * @param id
     * @return
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public SalesInvoiceBill getSalesInvoiceBillInfo(String id);

    /**
     * 根据客户编号 获取该客户上次开票客户名称(申请开票)
     * @param customerid
     * @return
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public String getSalesInvoiceBillNameByCustomerid(@Param("customerid") String customerid);

    /**
     * 获取销售发票来源单据编号列表(申请开票)
     * @param id
     * @return
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    public List getSalesInvoiceBillSouceidList(@Param("id") String id);

    /**
     *     获取销售发票开票明细列表（合计品牌折扣）
     *     @param billid
     *     @return
     *     @author chenwei
     *     @date Dec 18, 2013
     */
    public List getSalesInvoiceBillDetailListSumBranddiscount(@Param("billid") String billid);

    /**
     * 获取销售开票明细列表（当前页）
     * @param pageMap
     * @return
     */
    public List getSalesInvoiceBillDetailListSumBranddiscountByPageMap(PageMap pageMap);

    /**
     * 获取销售开票明细列表总数（分页）
     * @param pageMap
     * @return
     */
    public int getSalesInvoiceBillDetailCountSumBranddiscountByPageMap(PageMap pageMap);

    /**
     * 获取销售开票明细列表合计（分页）
     * @param pageMap
     * @return
     */
    public List getSalesInvoiceBillDetailTotalSumBranddiscountByPageMap(PageMap pageMap);
    
    /**
     * 获取销售发票开票明细列表（合计品牌折扣），打印时使用，如果修改getSalesInvoiceBillDetailListSumBranddiscountForPrint，请查看本方法是否需要修改<br/>
     * map中参数：<br/>
     * billid:发票单据编号<br/>
     * orderby:排序,值为print时，打印时修改，null时默认排序
     * @param map
     * @return
     * @author chenwei
     * @date Dec 18, 2013
     */
    public List getSalesInvoiceBillDetailListSumBranddiscountForPrint(Map map);

    /**
     * 获取销售发票开票的客户列表
     * @param billid
     * @return
     * @author panxiaoxiao
     * @date Feb 15, 2015
     */
    public List getSalesInvoiceBillCustomerList(@Param("billid") String billid);

    /**
     * 根据单据编号和商品编码 删除销售开票明细
     * @param billid
     * @param goodsid
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public int deleteSalesInvoiceBillDetailByGoodsid(@Param("billid") String billid, @Param("goodsid") String goodsid);

    /**
     * 获取销售开票明细列表
     * @param billid
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public List getSalesInvoiceBillDetailList(@Param("billid") String billid);

    /**
     * 销售开票审核
     * @param id
     * @param userid
     * @param username
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public int auditSalesInvoiceBill(@Param("id") String id, @Param("userid") String userid, @Param("username") String username,@Param("businessdate") String businessdate);

    /**
     * 销售开票反审
     * @param id
     * @param userid
     * @param username
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public int oppauditSalesInvoiceBill(@Param("id") String id, @Param("userid") String userid, @Param("username") String username);

    /**
     * 更新销售开票是否关联收款单状态
     * @param id
     * @param isrelate
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public int updateSalesInvoiceBillIsrelate(@Param("id") String id, @Param("isrelate") String isrelate);

    /**
     * 删除销售开票
     * @param id
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public int deleteSalesInvoiceBill(@Param("id") String id);

    /**
     * 删除销售开票明细
     * @param billid
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public int deleteSalesInvoiceBillDetail(@Param("billid") String billid);

    /**
     * 获取来源单据开票金额
     * @param billid
     * @param sourceid
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public BigDecimal getSalesInvoiceBillDetailAmount(@Param("billid") String billid, @Param("sourceid") String sourceid);

    /**
     * 根据销售开票编号和来源单据编号 获取明细列表
     * @param billid
     * @param sourceid
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public List getSalesInvoiceBillDetailListByBillidAndSourceid(@Param("billid") String billid, @Param("sourceid") String sourceid);

    /**
     * 根据单据编码、来源单据、商品编码  删除销售开票明细
     * @param billid
     * @param goodsid
     * @param sourceid
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public int deleteSalesInvoiceBillDetailByGoodsidBoth(@Param("billid") String billid, @Param("goodsid") String goodsid, @Param("sourceid") String sourceid);

    /**
     * 获取销售开票明细数据
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public List showSalesInvoiceBillDetailData(PageMap pageMap);

    /**
     * 获取销售开票明细数据数量
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public int showSalesInvoiceBillDetailDataCount(PageMap pageMap);

    /**
     * 根据销售开票编号获取销售发票明细合计数据
     *
     * @param id
     * @return
     * @author panxiaoxiao
     * @date Feb 16, 2015
     */
    public Map getSalesInvoiceBillDetailSumData(String id);

    /**
     * 销售开票核销
     *
     * @param id
     * @return
     * @author panxiaoxiao
     * @date Feb 25, 2015
     */
    public int writeOffSalesInvoiceBill(@Param("id") String id);

    /**
     * 更新销售开票回退状态（中止）
     * @param id
     * @param userid
     * @param username
     * @return
     * @author panxiaoxiao
     * @date Feb 25, 2015
     */
    public int updateSalesInvoiceBillCancel(@Param("id") String id, @Param("userid") String userid, @Param("username") String username);

    /**
     * 根据客户编号获取该客户的保存状态的销售开票列表
     * @param customerid
     * @return
     * @author panxiaoxiao
     * @date Feb 26, 2015
     */
    public List showSalesInvoiceBillListPageByCustomer(@Param("customerid") String customerid,@Param("billtype")String billtype);

    /**
     * 检查选中的销售开票是否允许申请核销
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2015-03-01
     */
    public int checkSalesInvoiceBillCanMuApplyWriteOff(String id);

    /**
     * 检查选中的销售开票是否已完全生成申请核销
     * @param id
     * @return =0 已完全申请核销 >0未完全申请核销
     * @author panxiaoxiao
     * @date 2015-03-02
    */
    public int checkSalesInvoiceBillCanMuApplyWriteOffAll(String id);

    /**
     * 获取为申请核销过的单据明细列表
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2015-03-02
     */
    public List getUnWriteoffSalesInvoiceBillList(String id);
    /**
     * 获取由生成预开票的发货单的发货回单明细列表
     * @param invoicebillid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-13
     */
    public List getUnWriteoffSalesInvoiceBillListCaseAdvance(@Param("invoicebillid")String invoicebillid);

    /**
     * 更新打印次数
     * @param salesInvoiceBill
     * @return
     */
    public int updateOrderPrinttimes(SalesInvoiceBill salesInvoiceBill);

    /**
     * 检查开票明细中是否存在未核销的来源明细 true存在 false不存在
     * @param billid
     * @return
     */
    public int checkSalesInvoiceBillDetailHasNoWriteoff(@Param("billid")String billid);

    /**
     * 根据开票单据编号关闭开票单据
     * @param id
     * @return
     */
    public int closeSalesInvoiceBill(@Param("id")String id);

    /**
     * 回写销售开票数据
     * @param map
     * @return
     */
    public int updateSalesInvoiceBillBack(Map map);

    /**
     * 根据销售开票编号和销售核销编码集合判断销售开票明细是否与销售核销明细相同，0为相同，大于0为不相同
     * 销售开票明细数量大于等于销售核销明细数量使用
     * @param map
     * @return
     */
    public int checkInvoiceBillDetailSameAsInvoiceDetailByMap(Map map);

    /**
     * 根据核销状态获取销售开票列表数据
     * @param iswriteoff
     * @return
     */
//    public List getSalesInvoiceBillListByIswriteoff(@Param("iswriteoff")String iswriteoff);

    /**
     * 获取对应核销状态的来源单据的申请开票单据列表数据
     * @return
     */
    public List getTotalWriteoffSalesInvoiceBillList(Map map);

    /**
     * 获取销售开票列表数据
     * @param map
     * @return
     */
    public List getSalesInvoiceBillListByPhone(Map map);
    /**
     * 获取销售开票明细中,一个不能为空的税种
     * @param invoicebillid
     * @return
     * @author zhanghonghui 
     * @date 2016年3月2日
     */
    public String getSalesInvoiceBillDetailOneTaxtype(@Param("invoicebillid")String invoicebillid);
    /**
     * 获取销售开票明细中，合计税种个数
     * @param salesinvoiceid
     * @return
     * @author zhanghonghui 
     * @date 2016年3月2日
     */
    public int getSalesInvoiceBillDetailTaxtypeCount(@Param("invoicebillid")String invoicebillid);

    /**
     * 根据发票号或者销售发票单据号 或者销售开票列表
     * @param ids
     * @return
     */
    public List getSalesInvoiceBillListPageByIds(@Param("ids") String ids);

 

    /**
     * 金税导出次数更新
     * @param salesInvoiceBill
     * @return
     */
    public int updateOrderJSExportTimes(SalesInvoiceBill salesInvoiceBill);

    /**
     * 数据结果给航天开票使用，获取销售发票开票明细列表 正常商品按编码合计，（所有折扣及冲差）合计
     * @param billid
     * @return java.util.List
     * @throws
     * @author zhang_honghui
     * @date Dec 31, 2016
     */
    public List getSalesInvoiceBillDetailListSumForHTKP(@Param("billid") String billid);
    /**
     * 数据结果给航天开票XML格式使用，获取销售发票开票明细列表 正常商品按编码合计，（所有折扣及冲差）合计
     * @param billid
     * @return java.util.List
     * @throws
     * @author zhang_honghui
     * @date Dec 31, 2016
     */
    public List getSalesInvoiceBillDetailListSumXMLForHTKP(@Param("billid") String billid);
    /**
     * 申请开票 生成凭证数据
     * @throws
     * @author lin_xx
     * @date 2018-01-26
     */
     public List<Map> getSalesInvoiceBillSumData(List<String> ids) throws Exception;

    /**
     * 获取销售开票导出数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Mar 02, 2018
     */
     public List getSalesInvoiceBillExportData(PageMap pageMap);

}

