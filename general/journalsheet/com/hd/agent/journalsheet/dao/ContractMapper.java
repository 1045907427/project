package com.hd.agent.journalsheet.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.Contract;
import java.util.List;
import java.util.Map;

import com.hd.agent.journalsheet.model.Contract;
import com.hd.agent.journalsheet.model.Contract;
import com.hd.agent.journalsheet.model.ContractDetail;
import org.apache.ibatis.annotations.Param;

public interface ContractMapper {

    /**
     * 获取客户费用合同列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<Contract>  getContractListData(PageMap pageMap);

    /**
     * 获取客户费用合同列表数据条数
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int getContractListDataCount(PageMap pageMap);

    /**
     * 根据编号获取客户费用合同数据
     * @param id
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public Contract getContractById(@Param("id")String id);

    /**
     * 添加客户费用合同
     * @param contract
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int addContract(Contract contract);

    /**
     * 修改客户费用合同
     * @param contract
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int editContract(Contract contract);


    /**
     * 删除客户费用合同
     * @param id
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int deleteContract(@Param("id")String id);

    /**
     * 启用客户费用合同
     * @param id
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int auditContract(@Param("id")String id);

    /**
     * 禁用客户费用合同
     * @param id
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int oppauditContract(@Param("id")String id);

    /**
     * 检测是否存在相关联的门店或者总店
     * @param customerid
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int getContractCountByCustomerid(@Param("customerid")String customerid);


//    客户费用合同明细
    /**
     * 根据编号获取客户费用合同明细数据
     * @param billid
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<ContractDetail> getContractDetailById(@Param("billid")String billid);

    /**
     * 根据编号获取客户费用合同明细数据
     * @param billid
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public ContractDetail getContractDetailByBillidAndCaluseid(@Param("billid")String billid,@Param("caluseid")String caluseid);


    /**
     * 添加客户费用合同明细
     * @param contractDetail
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int addContractDetail(ContractDetail contractDetail);
    /**
     * 删除客户费用合同明细
     * @param billid
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int deleteContractDetail(@Param("billid")String billid);


    /**
     * 根据月份获取存在符合条件条款的客户列表
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<Contract>  getAddContractReportData(PageMap pageMap);


    /**
     * 根据月份获取存在符合条件条款的客户列表数量
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int  getAddContractReportDataCount(PageMap pageMap);

    /**
     * 根据月份和客户获取合同列表
     * @param map
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<Map> getAddContractReportDataListByMonthAndCustomerid(Map map);

    /**
     * 检测合同条款是否被引用
     * @param caluseid
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int getContractDetailNumByCaluseid(@Param("caluseid")String caluseid);
}
