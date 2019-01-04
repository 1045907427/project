package com.hd.agent.account.dao;

import com.hd.agent.account.model.BeginDue;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: PXX
 * Date: 2016/9/22
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public interface BeginDueMapper {
    /**
     * 获取供应商应付款期初列表
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2016-09-28
     */
    public List<BeginDue> getBeginDueList(PageMap pageMap);

    /**
     * 获取供应商应付款期初数量
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2016-09-28
     */
    public int getBeginDueCount(PageMap pageMap);

    /**
     * 获取供应商应付款期初合计
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2016-09-28
     */
    public BeginDue getBeginDueSum(PageMap pageMap);

    /**
     * 新增供应商应付款期初
     * @param beginDue
     * @return
     * @author panxiaoxiao
     * @date 2016-09-28
     */
    public int addBeginDue(BeginDue beginDue);

    /**
     * 获取应付款期初数据
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2016-09-28
     */
    public BeginDue getBeginDueByID(@Param("id")String id);

    /**
     * 修改应付款期初
     * @param beginDue
     * @return
     * @author panxiaoxiao
     * @date 2016-09-28
     */
    public int editBeginDue(BeginDue beginDue);

    /**
     * 删除应付款期初
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2016-09-28
     */
    public int deleteBeginDue(@Param("id")String id);

    /**
     * 审核应付款期初
     * @param id
     * @param userid
     * @param username
     * @return
     * @author panxiaoxiao
     * @date 2016-09-28
     */
    public int auditBeginDue(@Param("id")String id, @Param("userid")String userid, @Param("username")String username);

    /**
     * 反审应付款期初
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2016-09-28
     */
    public int oppauditBeginDue(@Param("id")String id);

    /**
     * 来源单据新增、采购发票保存状态修改、删除时回写
     * @param beginDue
     * @return
     * @author panxiaoxiao
     * @date 2016-10-09
     */
    public int updateBeginDueInvoicerefer(BeginDue beginDue);

    /**
     * 审核采购发票时，回写应付款期初开票状态、开票金额、开票时间
     * @param beginDue
     * @return
     * @author panxiaoxiao
     * @date 2016-10-11
     */
    public int updateBeginDueInvoiceAuditWriteBack(BeginDue beginDue);

    /**
     * 核销采购发票时，回写应付款期初核销状态、核销金额、核销时间、核销人
     * @param beginDue
     * @return
     */
    public int updateBeginDueWriteBack(BeginDue beginDue);
}
