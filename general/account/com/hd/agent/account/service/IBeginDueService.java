package com.hd.agent.account.service;

import com.hd.agent.account.model.BeginDue;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: PXX
 * Date: 2016/9/22
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public interface IBeginDueService {

    /**
     * 获取供应商应付款期初数据列表
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public PageData getBeginDueList(PageMap pageMap)throws Exception;

    /**
     * 添加应付款期初
     * @param beginDue
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public boolean addBeginDue(BeginDue beginDue)throws Exception;

    /**
     * 根据编号获取应付款期初详细信息
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public BeginDue getBeginDueInfo(String id)throws Exception;

    /**
     * 修改应付款期初
     * @param beginDue
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public boolean editBeginDue(BeginDue beginDue)throws Exception;

    /**
     * 删除应付款期初
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public boolean deleteBeginDue(String id)throws Exception;

    /**
     * 审核应付款期初
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public Map auditBeignDue(String id)throws Exception;

    /**
     * 反审应付款期初
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public Map oppauditBeignDue(String id)throws Exception;

    /**
     * 应付款期初导入
     * @param list
     * @return
     * @throws Exception
     */
    public Map importBeignDue(List<Map> list)throws Exception;

    /**
     * 来源单据新增、采购发票保存状态修改、删除时回写
     * @return
     * @throws Exception
     */
    public boolean updateBeginDueInvoicerefer(BeginDue beginDue)throws Exception;
}
