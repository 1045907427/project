package com.hd.agent.journalsheet.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.model.ContractCaluse;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface ContractCaluseMapper {

//    客户费用档案条款

    /**
     * 获取客户费用合同条款列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public List<ContractCaluse>  getContractCaluseListData(PageMap pageMap);

     /**
     * 获取客户费用合同条款列表数据条数
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int getContractCaluseListDataCount(PageMap pageMap);

    /**
     * 根据编号获取客户费用合同条款数据
     * @param id
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public ContractCaluse getContractCaluseById(@Param("id")String id);

    /**
     * 添加客户费用合同条款
     * @param contractCaluse
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int addContractCaluse(ContractCaluse contractCaluse);

    /**
     * 修改客户费用合同条款
     * @param contractCaluse
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int editContractCaluse(ContractCaluse contractCaluse);

    /**
     * 删除客户费用合同条款
     * @param id
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int deleteContractCaluse(@Param("id")String id);

    /**
     * 启用客户费用合同条款
     * @param id
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int openContractCaluse(@Param("id")String id);

    /**
     * 禁用客户费用合同条款
     * @param id
     * @return
     * @author wanghongteng
     * @date 2017-11-8
     */
    public int closeContractCaluse(@Param("id")String id);
}