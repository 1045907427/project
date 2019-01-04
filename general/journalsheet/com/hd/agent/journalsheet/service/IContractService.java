package com.hd.agent.journalsheet.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.Contract;
import com.hd.agent.journalsheet.model.ContractCaluse;
import com.hd.agent.journalsheet.model.ContractDetail;
import com.hd.agent.journalsheet.model.ContractReport;

import java.util.List;
import java.util.Map;

/**
 * Created by wanghongteng on 2017/11/8.
 */
public interface IContractService {

//    客户费用合同条款

    /**
     * 获取客户费用合同条款列表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public PageData getContractCaluseListData(PageMap pageMap) throws Exception;

    /**
     * 根据id获取客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public ContractCaluse getContractCaluseById(String id) throws Exception;

    /**
     * 添加客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map addContractCaluse(ContractCaluse contractCaluse) throws Exception;

    /**
     * 修改客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map editContractCaluse(ContractCaluse contractCaluse) throws Exception;

    /**
     * 删除客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map deleteContractCaluse(String id) throws Exception;

    /**
     * 启用客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map openContractCaluse(String id) throws Exception;

    /**
     * 禁用客户费用合同条款
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map closeContractCaluse(String id)  throws Exception;



//    客户费用合同

    /**
     * 获取客户费用合同列表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public PageData getContractListData(PageMap pageMap) throws Exception;

    /**
     * 根据id获取客户费用合同
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map getContractById(String id) throws Exception;

    /**
     * 添加客户费用合同
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map addContract(Contract contract, List<ContractDetail> contractDetailList) throws Exception;

    /**
     * 修改客户费用合同
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map editContract(Contract contract, List<ContractDetail> contractDetailList) throws Exception;

    /**
     * 删除客户费用合同
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map deleteContract(String id) throws Exception;

    /**
     * 启用客户费用合同
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map auditContract(String id) throws Exception;

    /**
     * 禁用客户费用合同
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map oppauditContract(String id)  throws Exception;


    /**
     * 检测是否存在相关联的门店或者总店
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map checkCorrelationCustomer(String customerid)  throws Exception;

//  客户合同费用报表
    /**
     * 生成客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map addContractReport(String month,String type,String id) throws Exception;
    /**
     * 获取可以生成客户合同费用报表的数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public PageData getAddContractReportData(PageMap pageMap) throws Exception;
    /**
     * 根据月份客户编号获取合同费用统计明细数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map getContractReportDetailDataByMonthAndCustomerid(String month,String customerid) throws Exception;
    /**
     * 获取客户合同费用报表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public PageData getContractReportData(PageMap pageMap) throws Exception;

    /**
     * 保存行修改的客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map editContractReport(ContractReport contractReport) throws Exception;

    /**
     * 保存行修改的客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map editContractReportDetail(ContractReport contractReport) throws Exception;
    /**
     * 确认客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map auditContractReport(String month,String  customerid) throws Exception;
    /**
     * 取消确认客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map oppauditContractReport(String month,String  customerid) throws Exception;
    /**
     * 生成客户应付费用和代垫录入费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map CreatPayableAndMatcost(String month,String  customerid) throws Exception;
    /**
     * 获取客户合同费用报表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public PageData getContractLiabilityData(PageMap pageMap) throws Exception;
}
