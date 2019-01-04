package com.hd.agent.journalsheet.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.ContractCaluse;
import com.hd.agent.journalsheet.model.ContractReport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface ContractReportMapper {

    /**
     * 获取客户费用合同条款列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<ContractReport>  getContractReportData(PageMap pageMap);

    /**
     * 获取客户费用合同条款列表数据条数
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int  getContractReportDataCount(PageMap pageMap);

    /**
     * 根据编号获取客户费用合同数据
     * @param id
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public ContractReport getContractReportById(@Param("id")String id);

    /**
     * 生成客户合同费用
     * @param contractReport
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    int addContractReport(ContractReport contractReport);

    /**
     * 保存客户合同费用
     * @param contractReport
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    int editContractReport(ContractReport contractReport);

    /**
     * 保存客户合同费用
     * @param contractReport
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    int editContractReportDetail(ContractReport contractReport);
    /**
     * 确认客户费用合同
     * @param id
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int auditContractReport(@Param("id")String id,@Param("state")String state);

    /**
     * 获取客户上报销售额
     * @param begindate
     * @param enddate
     * @param customerid
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map getCrmCustomerSaleamount(@Param("begindate")String begindate,@Param("enddate")String enddate,@Param("customerid")String customerid);

    /**
     * 获取客户销售额
     * @param begindate
     * @param enddate
     * @param customerid
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map getCustomerSaleamount(@Param("begindate")String begindate,@Param("enddate")String enddate,@Param("customerid")String customerid);

    /**
     * 获取客户销售额
     * @param month
     * @param customerid
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<Map> getContractTitleListByMonthAndCustomerid(@Param("month")String month,@Param("customerid")String customerid);

    /**
     * 获取客户销售额
     * @param month
     * @param customerid
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<Map> getContractContentListByMonthAndCustomerid(@Param("month")String month,@Param("customerid")String customerid);

    /**
     * 根据
     * @param month
     * @param contractbillid
     * @param contractcaluseid
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public ContractReport getContractContentListByMonthAndBillidAndCaluseid(@Param("month")String month,@Param("contractbillid")String contractbillid,@Param("contractcaluseid")String contractcaluseid);

    /**
     * 获取客户销售额
     * @param month
     * @param customerid
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<ContractReport> getContractReportListByMonthAndCustomerid(@Param("month")String month,@Param("customerid")String customerid);
    /**
     * 检测合同是否被引用
     * @param contractbillid
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int getContractReportNumByContractbillid(@Param("contractbillid")String contractbillid);
}