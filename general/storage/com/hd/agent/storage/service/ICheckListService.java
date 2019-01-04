/**
 * @(#)ICheckListService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 18, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.CheckList;
import com.hd.agent.storage.model.CheckListDetail;

/**
 * 
 * 盘点单service
 * @author chenwei
 */
public interface ICheckListService {
	/**
	 * 根据仓库编码获取该仓库下面的商品信息列表
	 * @param storageid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public List getCheckListDetail(String storageid,String brands,String goodssorts) throws Exception;
	/**
	 * 根据仓库编码和商品类别获取该仓库下面的商品信息列表
	 * @param storageid
	 * @param goodssorts
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public List getCheckListDetailByGoodssorts(String storageid,String goodssorts) throws Exception;
	/**
	 * 添加盘点单信息
	 * @param checkList
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public boolean addCheckList(CheckList checkList,List<CheckListDetail> detailList) throws Exception;
	/**
	 * 获取盘点单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public Map getCheckListInfo(String id) throws Exception;
	/**
	 * 获取盘点单分页列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public PageData showCheckListPageData(PageMap pageMap) throws Exception;
	/**
	 * 盘点单修改
	 * @param checkList
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	public boolean editCheckList(CheckList checkList,List<CheckListDetail> list) throws Exception;

	/**
	 * 只修改盘点单主表信息
	 * @param checkList
	 * @return
	 * @throws Exception
	 */
	public boolean editJustCheckList(CheckList checkList)throws Exception;
	/**
	 * 删除盘点单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	public boolean deleteCheckList(String id) throws Exception;
	/**
	 * 根据仓库编码判断是否有未审核的盘点单存在
	 * flag : true:有 false无
	 * oldid : 为审核的盘点单编号
	 * @param storageid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public Map isHaveCheckListByStorageid(String storageid) throws Exception;
	/**
	 * 盘点单审核
	 * 审核通过后生成调账单
	 * 返回Map对象 
	 * flag：表示审核状态 true审核通过 false审核不通过
	 * adjustFlag:表示是否生成调账单 true是 false否
	 * adjustmentsid:调账单编号
	 * @param id			盘点单编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public Map auditCheckList(String id) throws Exception;
	/**
	 * 盘点单反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 28, 2013
	 */
	public Map oppauditCheckList(String id) throws Exception;
	/**
	 * 获取盘点单明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public List showCheckListDetailData(String id) throws Exception;
	/**
	 * 盘点单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public boolean submitCheckListPageProcess(String id) throws Exception;
	/**
	 * 盘点单检查 验证数量是否一致
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public Map getCheckListNumIsTure(String id) throws Exception;
	/**
	 * 盘点单盘点完成
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public boolean updateCheckListFinish(String id) throws Exception;
	/**
	 * 结束盘点 修改状态
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 26, 2013
	 */
	public Map closeCheckListByCheck(String id)throws Exception;
	/**
	 * 根据盘点结果 重新生成盘点单
	 * @param id	盘点单号
	 * @param flag	是否生成新的盘点单 true是 false否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public Map addCheckListByCheck(String id) throws Exception;
	/**
	 * 盘点单导出
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	public List exportCheckListData(PageMap pageMap) throws Exception;
	/**
	 * 通过导入的盘点单进行盘点
	 * @param list		盘点列表数据
	 * @param id 		盘点单号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	public Map updateCheckListDataByImport(List<Map> list,String id) throws Exception;
	
	/**
	 * 获取要导入的txt数据(瑞家)
	 * @param excelFile
	 * @param paramList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 13, 2014
	 */
	public List<Map<String, Object>> getImoportTxtData(File file,List<String> paramList,String id)throws Exception;

	/**
	 * 根据盘点单编号获取其明细列表分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2015-02-03
	 */
	public PageData getCheckListDetailData(PageMap pageMap)throws Exception;

	/**
	 * 添加盘点单明细列表数据根据查询到的数据
	 * @param checkList
	 * @param storageid
	 * @param brands
	 * @param goodssorts
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-04
	 */
	public Map addCheckListDetail(CheckList checkList,String storageid, String brands, String goodssorts)throws Exception;

	/**
	 * 检查该商品是否存在该单据明细中
	 * true 存在 false 不存在
	 * @param goodsid
	 * @param checklistid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-15
	 */
	public boolean doCheckListDetailIsHadGoods(String goodsid, String checklistid)throws Exception;

	/**
	 * 添加盘点单单个明细数据
	 * @param checkList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-15
	 */
	public Map addSaveCheckListDetail(CheckList checkList,List<CheckListDetail> detailList)throws Exception;

	/**
	 * 删除盘点单明细列表数据
	 * @param checkList
	 * @param delDetailList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-16
	 */
	public Map deleteCheckListDetails(CheckList checkList,List<CheckListDetail> delDetailList)throws Exception;

	/**
	 * 修改盘点单单个明细数据
	 * @param checkList
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-17
	 */
	public Map editSaveCheckListDetail(CheckList checkList, List<CheckListDetail> detailList)throws Exception;

	/**
	 * 申请盘点单
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-08-25
	 */
	public Map addDynamicCheckList(Map map)throws Exception;

	/**
	 * 获取某条盘点明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-15
	 */
	public CheckListDetail getCheckListDetailById(String checklistid,String id)throws Exception;

	/**
	 * 根据盘点单编号更新打印次数
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-03
	 */
	public boolean updatePrintTimes(String id)throws Exception;
}

