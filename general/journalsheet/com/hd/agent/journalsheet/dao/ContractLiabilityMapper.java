package com.hd.agent.journalsheet.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.ContractLiability;
import org.apache.ibatis.annotations.Param;

public interface ContractLiabilityMapper {
    /**
     * 获取客户费用合同条款客户数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<Map>  getContractLiabilityDataCustomerList(PageMap pageMap);

    /**
     * 获取客户合同费用客户下科目费用金额
     * @param customerid
     * @param map
     * @return
     * @author wanghongteng
     * @date 2017-12-12
     */
    public List getContractSubjectDataList(@Param("customerid")String customerid,@Param("con")Map map);
    /**
     * 获取客户费用合同条款列表数据合计
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Map getContractLiabilityDataSums(PageMap pageMap);
    /**
     * 获取客户费用合同条款列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<ContractLiability>  getContractLiabilityData(PageMap pageMap);

    /**
     * 获取客户费用合同条款列表数据条数
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int  getContractLiabilityDataCount(PageMap pageMap);
    /**
     * 生成客户合同费用
     * @param contractLiability
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    int addContractLiability(ContractLiability contractLiability);
    /**
     * 根据月份和客户编号删除客户合同费用
     * @param month
     * @param customerid
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    int deleteContractLiabilityByMonthAndCustomerid(@Param("month")String month,@Param("customerid")String customerid);
}