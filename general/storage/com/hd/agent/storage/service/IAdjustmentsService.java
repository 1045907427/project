/**
 * @(#)IAdjustmentsService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 24, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.Adjustments;
import com.hd.agent.storage.model.AdjustmentsDetail;

import java.util.List;
import java.util.Map;

/**
 * 
 * 库存调账单service
 * @author chenwei
 */
public interface IAdjustmentsService {
	/**
	 * 获取调账单数据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public PageData showAdjustmentsList(PageMap pageMap) throws Exception;
	/**
	 * 获取调账单详细信息
	 * @param id		调账单编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public Map getAdjustmentsInfo(String id) throws Exception;
	/**
	 * 添加调账单
	 * @param adjustments
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public boolean addAdjustments(Adjustments adjustments,List<AdjustmentsDetail> list) throws Exception;
	/**
	 * 删除调账单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public boolean deleteAdjustments(String id) throws Exception;
	/**
	 * 调账单修改
	 * @param adjustments
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	public boolean editAdjustments(Adjustments adjustments,List<AdjustmentsDetail> list) throws Exception;
	/**
	 * 审核调账单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	public Map auditAdjustments(String id) throws Exception;

    /**
     * 反审调账单
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date May 27, 2013
     */
    public Map oppauditAdjustments(String id) throws Exception;
	/**
	 * 根据盘点单编号生成调账单
	 * @param checklistid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public String addAdjustmentsByRefer(String checklistid) throws Exception;
	/**
	 * 调账单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public boolean submitAdjustmentsPageProcess(String id) throws Exception;
	
	
	/**
	 * 导出调账单
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 29, 2014
	 */
	public List<Map<String, Object>> getAdjustmentListExport(PageMap pageMap)throws Exception;
	/**
	 * 获取调账单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * billtype : 单据类型1报益单2报损单<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showAdjustmentsListBy(Map map) throws Exception;
	/**
	 * 更新打印次数
	 * @param adjustments
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public boolean updateOrderPrinttimes(Adjustments adjustments) throws Exception;
	/**
	 * 获取调账单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年1月13日
	 */
	public Adjustments showPureAdjustments(String id) throws Exception;

	/**
	 * 获取报溢调账单生成凭证的数据
	 * @param ids
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Jan 03, 2018
	 */
	public List getAdjustmentsSumData(List ids,String billtype);
}

