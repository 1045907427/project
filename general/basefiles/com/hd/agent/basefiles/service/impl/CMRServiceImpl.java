/**
 * @(#)CMRServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.CMRMapper;
import com.hd.agent.basefiles.model.MarketActivitySort;
import com.hd.agent.basefiles.model.SaleChance_Sort;
import com.hd.agent.basefiles.model.SaleMode;
import com.hd.agent.basefiles.model.SaleMode_Detail;
import com.hd.agent.basefiles.model.TaskSort;
import com.hd.agent.basefiles.service.ICMRService;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class CMRServiceImpl extends FilesLevelServiceImpl implements ICMRService{

	private CMRMapper cmrMapper;

	public CMRMapper getCmrMapper() {
		return cmrMapper;
	}

	public void setCmrMapper(CMRMapper cmrMapper) {
		this.cmrMapper = cmrMapper;
	}
	
	/*----------------------------------------销售方式------------------------------------------------------------------*/
	@Override
	public PageData showSaleModeListPage(PageMap pageMap) throws Exception {
		//数据权限
		String sql = getDataAccessRule("t_base_crm_salemode",null);
		pageMap.setDataSql(sql);
		PageData pageData = new PageData(cmrMapper.getSaleModeListCount(pageMap),
				cmrMapper.getSaleModeListPage(pageMap),pageMap);
		return pageData;
	}

	@Override
	public SaleMode showSaleModeInfo(String id) throws Exception {
		return cmrMapper.getSaleModeInfo(id);
	}

	@Override
	public List showSaleModeDetailList(String salemodeid) throws Exception {
		return cmrMapper.getSaleModeDetailList(salemodeid);
	}

	@Override
	public boolean isRepeatSaleModeId(String id) throws Exception {
		return cmrMapper.isRepeatSaleModeId(id) > 0;
	}

	@Override
	public boolean isRepeatSaleModeName(String name) throws Exception {
		return cmrMapper.isRepeatSaleModeName(name) > 0;
	}

	@Override
	public boolean addSaleMode(SaleMode saleMode,
			List<SaleMode_Detail> insertDetailList) throws Exception {
		//销售方式新增
		int s = cmrMapper.addSaleMode(saleMode);
		if(s > 0){
			for(SaleMode_Detail saleModeDetail : insertDetailList){
				saleModeDetail.setSalemodeid(saleMode.getId());
			}
		}
		int sd = cmrMapper.addSaleModeDetails(insertDetailList);
		return (s > 0) && (sd > 0);
	}

	@Override
	public boolean editSaleMode(SaleMode saleMode,
			List<SaleMode_Detail> insertDetailList,
			List<SaleMode_Detail> updateDetailList,
			List<SaleMode_Detail> delDetailList) throws Exception {
		int addSMD = 0,editSMD = 0,delSMD = 0;
		SaleMode beforeSaleMode = cmrMapper.getSaleModeInfo(saleMode.getOldId());
		//判断是否可以进行修改操作，若可以修改，更新级联关系数据，true 允许修改，false 不允许修改
		if(canBasefilesIsEdit("t_base_crm_salemode",beforeSaleMode,saleMode)){
			int s = cmrMapper.editSaleMode(saleMode);//修改销售方式
			if(s > 0){//修改成功后，修改对应的销售方式细节详情列表
				if(insertDetailList.size() != 0){//有新增操作
					for(SaleMode_Detail detail:insertDetailList){
						detail.setSalemodeid(saleMode.getId());
					}
					addSMD = cmrMapper.addSaleModeDetails(insertDetailList);
				}
				else{
					addSMD = 1;
				}
				if(updateDetailList.size() != 0){//有修改操作
					editSMD = cmrMapper.editSaleModeDetails(updateDetailList);
				}
				else{
					editSMD = 1;
				}
				if(delDetailList.size() != 0){//有删除操作
					delSMD = cmrMapper.deleteSaleModeDetails(delDetailList);
				}
				else{
					delSMD = 1;
				}
				return (addSMD > 0) && (editSMD > 0) && (delSMD > 0);
			}
		}
		return false;
	}

	@Override
	public boolean deleteSaleMode(String id) throws Exception {
		int s = cmrMapper.deleteSaleMode(id);//删除销售方式 id:销售方式编码
		if(s > 0){//销售方式删除成功后，删除对应销售方式细节详情列表
			List<SaleMode_Detail> delDetailList = cmrMapper.getSaleModeDetailList(id);
			int delSMD = cmrMapper.deleteSaleModeDetails(delDetailList);
			return delSMD > 0;
		}
		return false;
	}

	@Override
	public boolean disableSaleMode(Map paramMap) throws Exception {
		return cmrMapper.disableSaleMode(paramMap) > 0;
	}

	@Override
	public boolean enableSaleMode(Map paramMap) throws Exception {
		return cmrMapper.enableSaleMode(paramMap) > 0;
	}
	
	public Map addDRSaleMode(List<SaleMode> list)throws Exception{
		boolean flag = false;
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		if(list.size() != 0){
			for(SaleMode saleMode : list){
				//判断销售方式编码是否重复
				SaleMode saleMode2 = cmrMapper.getSaleModeInfo(saleMode.getId());
				if(null == saleMode2){
					SysUser sysUser = getSysUser();
					saleMode.setAdddeptid(sysUser.getDepartmentid());
					saleMode.setAdddeptname(sysUser.getDepartmentname());
					saleMode.setAdduserid(sysUser.getUserid());
					saleMode.setAddusername(sysUser.getName());
					if(StringUtils.isEmpty(saleMode.getState())){
						saleMode.setState("2");
					}
					flag = cmrMapper.addSaleMode(saleMode) > 0;
					if(!flag){
						if(StringUtils.isNotEmpty(failStr)){
							failStr += "," + saleMode.getId(); 
						}
						else{
							failStr = saleMode.getId();
						}
						failureNum++;
					}
					else{
						successNum++;
					}
				}
				else{
					if("0".equals(saleMode2.getState())){//禁用状态，不允许导入
						if(StringUtils.isEmpty(closeVal)){
							closeVal = saleMode2.getId();
						}
						else{
							closeVal += "," + saleMode2.getId();
						}
						closeNum++;
					}
					else{
						if(StringUtils.isEmpty(repeatVal)){
							repeatVal = saleMode2.getId();
						}
						else{
							repeatVal += "," + saleMode2.getId();
						}
						repeatNum++;
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatVal", repeatVal);
		return map;
	}

	@Override
	public boolean isRepeatStageCode(Map map) throws Exception {
		return cmrMapper.isRepeatStageCode(map) > 0;
	}

	@Override
	public boolean isRepeatStageName(Map map) throws Exception {
		return cmrMapper.isRepeatStageName(map) > 0;
	}
	
	/*----------------------------------------销售机会来源分类------------------------------------------------------------------*/
	
	@Override
	public boolean addSaleChance(SaleChance_Sort saleChance_Sort)
			throws Exception {
		return cmrMapper.addSaleChance(saleChance_Sort) > 0;
	}

	@Override
	public boolean deleteSaleChance(String id) throws Exception {
		return cmrMapper.deleteSaleChance(id) > 0;
	}

	@Override
	public boolean disableSaleChance(SaleChance_Sort saleChance_Sort) throws Exception {
		return cmrMapper.disableSaleChance(saleChance_Sort) > 0;
	}

	@Override
	public Map editSaleChance(SaleChance_Sort saleChance_Sort)
			throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		SaleChance_Sort oldSaleChanceSort = getSaleChanceInfo(saleChance_Sort.getOldId());
		if(null != oldSaleChanceSort){
			if(!oldSaleChanceSort.getThisid().equals(saleChance_Sort.getThisid()) || !oldSaleChanceSort.getThisname().equals(saleChance_Sort.getThisname())){
				//获取下级所有分类列表
				List<SaleChance_Sort> childList = cmrMapper.getSaleChanceSortChildList(oldSaleChanceSort.getId());
				//若编码改变，下属所有的任务分类编码应做对应的替换
				if(childList.size() != 0){
					for(SaleChance_Sort repeatSCS : childList){
						repeatSCS.setOldId(repeatSCS.getId());
						if(!oldSaleChanceSort.getThisid().equals(saleChance_Sort.getThisid())){
							String newid = repeatSCS.getId().replaceFirst(oldSaleChanceSort.getThisid(), saleChance_Sort.getThisid()).trim();
							repeatSCS.setId(newid);
							String newpid = repeatSCS.getPid().replaceFirst(oldSaleChanceSort.getThisid(), saleChance_Sort.getThisid()).trim();
							repeatSCS.setPid(newpid);
						}
						if(!oldSaleChanceSort.getThisname().equals(saleChance_Sort.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatSCS.getName().replaceFirst(oldSaleChanceSort.getThisname(), saleChance_Sort.getThisname());
							repeatSCS.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeatSCS.getId());
						node.setParentid(repeatSCS.getPid());
						node.setText(repeatSCS.getThisname());
						node.setState(repeatSCS.getState());
						map2.put(repeatSCS.getOldId(), node);
					}
					cmrMapper.editSaleChanceSortBatch(childList);
				}
			}
		}
		boolean flag = cmrMapper.editSaleChance(saleChance_Sort) > 0;
		if(flag){
			Tree node = new Tree();
			node.setId(saleChance_Sort.getId());
			node.setParentid(saleChance_Sort.getPid());
			node.setText(saleChance_Sort.getThisname());
			node.setState(saleChance_Sort.getState());
			map2.put(saleChance_Sort.getOldId(), node);
		}
		map.put("flag", flag);
		map.put("nodes", map2);
		return map;
	}

	@Override
	public boolean enableSaleChance(SaleChance_Sort saleChance_Sort) throws Exception {
		return (cmrMapper.enableSaleChance(saleChance_Sort) > 0) && isTreeLeaf("salechance_sort");
	}

	@Override
	public SaleChance_Sort getSaleChanceInfo(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_crm_salechance_sort", null); //加字段查看权限
		map.put("id", id);
		return cmrMapper.getSaleChanceInfo(map);
	}

	@Override
	public List getSaleChanceSortList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_crm_salechance_sort",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_crm_salechance_sort",null); //数据权限
		pageMap.setDataSql(sql);
		return cmrMapper.getSaleChanceSortList(pageMap);
	}

	@Override
	public List getSalesAreaParentAllChildren(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_crm_salechance_sort",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_crm_salechance_sort",null); //数据权限
		pageMap.setDataSql(sql);
		return cmrMapper.getSalesAreaParentAllChildren(pageMap);
	}

	@Override
	public boolean saleChanceNameNOUsed(String thisname) throws Exception {
		return cmrMapper.saleChanceNameNOUsed(thisname) > 0;
	}

	/*---------------------------------任务分类-------------------------------------------------*/
	
	@Override
	public boolean addTaskSort(TaskSort taskSort) throws Exception {
		return cmrMapper.addTaskSort(taskSort) > 0;
	}

	@Override
	public boolean deleteTaskSort(String id) throws Exception {
		return cmrMapper.deleteTaskSort(id) > 0;
	}

	@Override
	public boolean disableTaskSort(TaskSort taskSort) throws Exception {
		return cmrMapper.disableTaskSort(taskSort) > 0;
	}

	@Override
	public Map editTaskSort(TaskSort taskSort) throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		TaskSort oldTaskSort = getTaskSortView(taskSort.getOldId());
		if(null != oldTaskSort){
			if(!oldTaskSort.getThisid().equals(taskSort.getThisid()) || !oldTaskSort.getThisname().equals(taskSort.getThisname())){
				//获取下级所有分类列表
				List<TaskSort> childList = cmrMapper.getTaskSortChildList(oldTaskSort.getId());
				//若编码改变，下属所有的任务分类编码应做对应的替换
				if(childList.size() != 0){
					for(TaskSort repeaTaskSort : childList){
						repeaTaskSort.setOldId(repeaTaskSort.getId());
						if(!oldTaskSort.getThisid().equals(taskSort.getThisid())){
							String newid = repeaTaskSort.getId().replaceFirst(oldTaskSort.getThisid(), taskSort.getThisid()).trim();
							repeaTaskSort.setId(newid);
							String newpid = repeaTaskSort.getPid().replaceFirst(oldTaskSort.getThisid(), taskSort.getThisid()).trim();
							repeaTaskSort.setPid(newpid);
						}
						if(!oldTaskSort.getThisname().equals(taskSort.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeaTaskSort.getName().replaceFirst(oldTaskSort.getThisname(), taskSort.getThisname());
							repeaTaskSort.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeaTaskSort.getId());
						node.setParentid(repeaTaskSort.getPid());
						node.setText(repeaTaskSort.getThisname());
						node.setState(repeaTaskSort.getState());
						map2.put(repeaTaskSort.getOldId(), node);
					}
					cmrMapper.editTaskSortBatch(childList);
				}
			}
		}
		boolean flag = cmrMapper.editTaskSort(taskSort) > 0;
		if(flag){
			Tree node = new Tree();
			node.setId(taskSort.getId());
			node.setParentid(taskSort.getPid());
			node.setText(taskSort.getThisname());
			node.setState(taskSort.getState());
			map2.put(taskSort.getOldId(), node);
		}
		map.put("flag", flag);
		map.put("nodes", map2);
		return map;
	}

	@Override
	public boolean enableTaskSort(TaskSort taskSort) throws Exception {
		return (cmrMapper.enableTaskSort(taskSort) > 0) && isTreeLeaf("task_sort");
	}

	@Override
	public List getTaskSortList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_crm_task_sort", null);//字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_crm_task_sort", null);//数据权限
		pageMap.setDataSql(sql);
		return cmrMapper.getTaskSortList(pageMap);
	}

	@Override
	public List getTaskSortParentAllChildren(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_crm_task_sort", null);//字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_crm_task_sort", null);//数据权限
		pageMap.setDataSql(sql);
		return cmrMapper.getTaskSortParentAllChildren(pageMap);
	}

	@Override
	public TaskSort getTaskSortView(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_crm_task_sort", null);//加字段查看权限
		map.put("id", id);
		return cmrMapper.getTaskSortView(map);
	}

	@Override
	public boolean isUsedTaskSortName(String name) throws Exception {
		return cmrMapper.isUsedTaskSortName(name) > 0;
	}

	/*---------------------------------------市场活动分类---------------------------------------------------*/
	
	@Override
	public boolean addmarketActivitySort(MarketActivitySort marketActivitySort)
			throws Exception {
		return cmrMapper.addmarketActivitySort(marketActivitySort) > 0;
	}

	@Override
	public boolean deletemarketActivitySort(String id) throws Exception {
		return cmrMapper.deletemarketActivitySort(id) > 0;
	}

	@Override
	public boolean disablemarketActivitySort(MarketActivitySort marketActivitySort)
			throws Exception {
		return cmrMapper.disablemarketActivitySort(marketActivitySort) > 0;
	}

	@Override
	public Map editmarketActivitySort(MarketActivitySort marketActivitySort)
			throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		MarketActivitySort oldMarketActivitySort = getmarketActivitySortDetail(marketActivitySort.getOldId());
		if(null != oldMarketActivitySort){
			if(!oldMarketActivitySort.getThisid().equals(marketActivitySort.getThisid()) || !oldMarketActivitySort.getThisname().equals(marketActivitySort.getThisname())){
				//获取下级所有分类列表
				List<MarketActivitySort> childList = cmrMapper.getMarketActivitySortChildList(oldMarketActivitySort.getId());
				//若编码改变，下属所有的任务分类编码应做对应的替换
				if(childList.size() != 0){
					 //本级编码长度
					for(MarketActivitySort repeatMAS : childList){
						repeatMAS.setOldId(repeatMAS.getId());
						if(!oldMarketActivitySort.getThisid().equals(marketActivitySort.getThisid())){
							String newid = repeatMAS.getId().replaceFirst(oldMarketActivitySort.getThisid(), marketActivitySort.getThisid()).trim();
							repeatMAS.setId(newid);
							String newpid = repeatMAS.getPid().replaceFirst(oldMarketActivitySort.getThisid(), marketActivitySort.getThisid()).trim();
							repeatMAS.setPid(newpid);
						}
						if(!oldMarketActivitySort.getThisname().equals(marketActivitySort.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatMAS.getName().replaceFirst(oldMarketActivitySort.getThisname(), marketActivitySort.getThisname());
							repeatMAS.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeatMAS.getId());
						node.setParentid(repeatMAS.getPid());
						node.setText(repeatMAS.getThisname());
						node.setState(repeatMAS.getState());
						map2.put(repeatMAS.getOldId(), node);
					}
					cmrMapper.editMarketActivitySortBatch(childList);
				}
			}
		}
		boolean flag = cmrMapper.editmarketActivitySort(marketActivitySort) > 0;
		if(flag){
			Tree node = new Tree();
			node.setId(marketActivitySort.getId());
			node.setParentid(marketActivitySort.getPid());
			node.setText(marketActivitySort.getThisname());
			node.setState(marketActivitySort.getState());
			map2.put(marketActivitySort.getOldId(), node);
		}
		map.put("flag", flag);
		map.put("nodes", map2);
		return map;
	}

	@Override
	public boolean enablemarketActivitySort(MarketActivitySort marketActivitySort)
			throws Exception {
		int i = cmrMapper.enablemarketActivitySort(marketActivitySort);
		boolean flag = isTreeLeaf("marketactivity_sort");
		return (i > 0) && flag;
	}

	@Override
	public MarketActivitySort getmarketActivitySortDetail(String id)
			throws Exception {
		Map map = getAccessColumnMap("t_base_crm_marketactivity_sort", null); //加字段查看权限
		map.put("id", id);
		return cmrMapper.getmarketActivitySortDetail(map);
	}

	@Override
	public List getmarketActivitySortList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_crm_marketactivity_sort",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_crm_marketactivity_sort",null);
		pageMap.setDataSql(sql);
		return cmrMapper.getmarketActivitySortList(pageMap);
	}

	@Override
	public List getmarketActivitySortParentAllChildren(PageMap pageMap)
			throws Exception {
		String cols = getAccessColumnList("t_base_crm_marketactivity_sort",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_crm_marketactivity_sort",null);
		pageMap.setDataSql(sql);
		return cmrMapper.getmarketActivitySortParentAllChildren(pageMap);
	}

	@Override
	public boolean isUsedmarketActivitySortName(String name) throws Exception {
		return cmrMapper.isUsedmarketActivitySortName(name) > 0;
	}
	
	/**
	 * 判断pid是否存在,若存在则为末及标志，否则，不是，进行级联替换
	 * @return
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public boolean isTreeLeaf(String type)throws Exception{
		int count = 0;
		if("marketactivity_sort".equals(type)){//市场活动分类末及标志
			//获取暂存以外的市场活动分类列表
			List<MarketActivitySort> marketList = cmrMapper.getmarketActivitySortByStateList();
			if(marketList.size() != 0){
				for(MarketActivitySort market : marketList){
					String pid = cmrMapper.isLeafmarketActivitySort(market.getId());
					if(!StringUtils.isNotEmpty(pid)){
						market.setLeaf("1");
					}
					else{
						market.setLeaf("0");
					}
					market.setOldId(market.getId());
					Map map = new HashMap();
					map.put("id", market.getId());
					//获取修改前市场活动分类信息
					MarketActivitySort beforeMarket = cmrMapper.getmarketActivitySortDetail(map);
					//判断是否可以进行修改操作,如果可以修改，同时更新级联关系数据
					if(canBasefilesIsEdit("t_base_crm_marketactivity_sort", beforeMarket, market)){
						int i = cmrMapper.editmarketActivitySort(market);
						count++;
					}
				}
				if(count == marketList.size()){
					return true;
				}
			}
		}
		else if("salechance_sort".equals(type)){//销售机会来源分类
			//获取暂存以外的销售机会来源分类列表
			List<SaleChance_Sort> salechanceList = cmrMapper.getSaleChanceByStateList();
			if(salechanceList.size() != 0){
				for(SaleChance_Sort saleChance : salechanceList){
					String pid = cmrMapper.isLeafSaleChance(saleChance.getId());
					if(!StringUtils.isNotEmpty(pid)){
						saleChance.setLeaf("1");
					}
					else{
						saleChance.setLeaf("0");
					}
					saleChance.setOldId(saleChance.getId());
					Map map = new HashMap();
					map.put("id", saleChance.getId());
					SaleChance_Sort beforeSaleChance = cmrMapper.getSaleChanceInfo(map);
					if(canBasefilesIsEdit("t_base_crm_salechance_sort", beforeSaleChance, saleChance)){
						int i = cmrMapper.editSaleChance(saleChance);
						count++;
					}
				}
				if(count == salechanceList.size()){
					return true;
				}
			}
		}
		else{
			//获取暂存以外的任务分类列表
			List<TaskSort> taskList = cmrMapper.getTaskSortByStateList();
			if(taskList.size() != 0){
				for(TaskSort taskSort : taskList){
					String pid = cmrMapper.isLeafTaskSort(taskSort.getId());
					if(!StringUtils.isNotEmpty(pid)){
						taskSort.setLeaf("1");
					}
					else{
						taskSort.setLeaf("0");
					}
					taskSort.setOldId(taskSort.getId());
					Map map = new HashMap();
					map.put("id", taskSort.getId());
					TaskSort beforeTask = cmrMapper.getTaskSortView(map);
					if(canBasefilesIsEdit("t_base_crm_task_sort", beforeTask, taskSort)){
						int i = cmrMapper.editTaskSort(taskSort);
						count++;
					}
				}
				if(count == taskList.size()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List getmarketActivitySortListData() throws Exception {
		return cmrMapper.getmarketActivitySortListData();
	}

	@Override
	public List getSaleChanceSortListData() throws Exception {
		return cmrMapper.getSaleChanceSortListData();
	}

	@Override
	public List getSaleModeListData() throws Exception {
		return cmrMapper.getSaleModeListData();
	}

	@Override
	public List getTaskSortListData() throws Exception {
		return cmrMapper.getTaskSortListData();
	}
	
	
}

