/**
 * @(#)BuyAreaService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-17 zhanghonghui 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.BuyAreaMapper;
import com.hd.agent.basefiles.dao.BuySupplierDetailSortMapper;
import com.hd.agent.basefiles.dao.BuySupplierMapper;
import com.hd.agent.basefiles.dao.BuySupplierSortMapper;
import com.hd.agent.basefiles.dao.ContacterMapper;
import com.hd.agent.basefiles.dao.PersonnelMapper;
import com.hd.agent.basefiles.service.IBuyService;
import com.hd.agent.basefiles.service.IContacterService;
import com.hd.agent.basefiles.service.IPersonnelService;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.RuleJSONUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysParam;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class BuyServiceImpl extends FilesLevelServiceImpl implements IBuyService {

	private BuyAreaMapper buyAreaMapper;
	private BuySupplierSortMapper buySupplierSortMapper;

	private BuySupplierDetailSortMapper buySupplierDetailSortMapper;
	
	private BuySupplierMapper buySupplierMapper;

	private PersonnelMapper personnelMapper;
	
	private ContacterMapper contacterMapper;
	
	public BuyAreaMapper getBuyAreaMapper() {
		return buyAreaMapper;
	}

	public void setBuyAreaMapper(BuyAreaMapper buyAreaMapper) {
		this.buyAreaMapper = buyAreaMapper;
	}
	
	public BuySupplierSortMapper getBuySupplierSortMapper() {
		return buySupplierSortMapper;
	}

	public void setBuySupplierSortMapper(BuySupplierSortMapper buySupplierSortMapper) {
		this.buySupplierSortMapper = buySupplierSortMapper;
	}	
	
	public BuySupplierDetailSortMapper getBuySupplierDetailSortMapper() {
		return buySupplierDetailSortMapper;
	}

	public void setBuySupplierDetailSortMapper(
			BuySupplierDetailSortMapper buySupplierDetailSortMapper) {
		this.buySupplierDetailSortMapper = buySupplierDetailSortMapper;
	}

	public BuySupplierMapper getBuySupplierMapper() {
		return buySupplierMapper;
	}

	public void setBuySupplierMapper(BuySupplierMapper buySupplierMapper) {
		this.buySupplierMapper = buySupplierMapper;
	}
	
	public PersonnelMapper getPersonnelMapper() {
		return personnelMapper;
	}

	public void setPersonnelMapper(PersonnelMapper personnelMapper) {
		this.personnelMapper = personnelMapper;
	}
	
	public ContacterMapper getContacterMapper() {
		return contacterMapper;
	}

	public void setContacterMapper(ContacterMapper contacterMapper) {
		this.contacterMapper = contacterMapper;
	}

	@Override
	public boolean addBuyArea(BuyArea area) throws Exception {
		return buyAreaMapper.addBuyArea(area)>0;
	}
	
	@Override
	public Map updateBuyArea(BuyArea area) throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		BuyArea oldArea = getBuyAreaDetail(area.getOldid());
		if(null != oldArea){
			if(!oldArea.getThisid().equals(area.getThisid()) || !oldArea.getThisname().equals(area.getThisname())){ //判断名称是否有修改，有修改则更新所有子节点名称
				//updateTreeName("t_base_sales_customersort", customer.getName(), customer.getId());
				List<BuyArea> childList = buyAreaMapper.getBuyAreaChildList(oldArea.getId());
				if(childList.size() != 0){
					for(BuyArea repeatBA : childList){
						repeatBA.setOldid(repeatBA.getId());
						if(!oldArea.getThisid().equals(area.getThisid())){
							String newid = repeatBA.getId().replaceFirst(oldArea.getThisid(), area.getThisid()).trim();
							repeatBA.setId(newid);
							String newpid = repeatBA.getPid().replaceFirst(oldArea.getThisid(), area.getThisid()).trim();
							repeatBA.setPid(newpid);
						}
						if(!oldArea.getThisname().equals(area.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatBA.getName().replaceFirst(oldArea.getThisname(), area.getThisname());
							repeatBA.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeatBA.getId());
						node.setParentid(repeatBA.getPid());
						node.setText(repeatBA.getThisname());
						node.setState(repeatBA.getState());
						map2.put(repeatBA.getOldid(), node);
					}
					buyAreaMapper.editBuyAreaBatch(childList);
				}
			}
		}
		boolean flag = buyAreaMapper.updateBuyArea(area)>0;
		if(flag){
			Tree node = new Tree();
			node.setId(area.getId());
			node.setParentid(area.getPid());
			node.setText(area.getThisname());
			node.setState(area.getState());
			map2.put(area.getOldid(), node);
		}
		map.put("flag", flag);
		map.put("nodes", map2);
		return map;
	}

	@Override
	public List getBuyAreaList() throws Exception {
		return buyAreaMapper.getBuyAreaList();
	}

	@Override
	public BuyArea getBuyAreaDetail(String id) throws Exception {
		return buyAreaMapper.getBuyAreaDetail(id);
	}
	
	@Override
	public List getBuyAreaListByMap(Map map) throws Exception{
		String cols = getAccessColumnList("t_base_buy_area",null);
		map.put("cols", cols);
		String sql = getDataAccessRule("t_base_buy_area",null);
		map.put("dataSql", sql);
		return buyAreaMapper.getBuyAreaListByMap(map);
	}

	@Override
	public boolean deleteBuyArea(String id) throws Exception {
		return buyAreaMapper.deleteBuyArea(id)>0;
	}
	/**
	 * 更新采购区域<br/>
	 * @param
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	@Override
	public boolean updateBuyAreaBy(Map map) throws Exception{
		if(!map.containsKey("isdataauth") || !"0".equals(map.get("isdataauth").toString())){
			String sql = getDataAccessRule("t_base_buy_area",null);
			map.put("authDataSql", sql);
		}
		return buyAreaMapper.updateBuyAreaBy(map)>0;
	}
	
	@Override
	public int getBuyAreaCountBy(Map map) throws Exception{
		if(map.containsKey("isdataauth") && "1".equals(map.get("isdataauth").toString())){
			String sql = getDataAccessRule("t_base_buy_area",null);
			map.put("authDataSql", sql);			
		}
		return buyAreaMapper.getBuyAreaCountBy(map);
	}

	@Override
	public Map closeBuyArea(String id) throws Exception{
		SysUser sysUser = getSysUser();
		Map condition = new HashMap();
		condition.put("parentAllChildren", id);
		List<BuyArea> list = getBuyAreaListByMap(condition); //查询该节点及所有子节点信息
		int successNum = 0;
		int failureNum = 0;
		int notAllowNum = 0;
		List<String> ids = new ArrayList<String>();

		Map<String,Object> map=new HashMap<String, Object>();
		map.put("closeuserid", sysUser.getUserid());
		map.put("openusername", sysUser.getName());
		map.put("state","0");
		boolean flag=false;
		for(BuyArea buyArea: list){ //循环所有节点信息，判断可以禁用的则禁用。
			if(!"1".equals(buyArea.getState())){
				notAllowNum++;
			}
			else{
				map.put("id", buyArea.getId());
				flag = updateBuyAreaBy(map) || flag;
				if(flag){
					successNum++;
					ids.add(buyArea.getId()); //返回所有禁用的记录编号，供前台更新树节点信息
				}
				else{
					failureNum++;
				}
			}
		}
		map.clear();
		map.put("flag", flag);
		map.put("isuccess", successNum);
		map.put("ifailure", failureNum);
		map.put("inohandle", notAllowNum);
		map.put("ids", ids);
		return map;
	}
		
	@Override
	public boolean isRepeatBuyAreaThisname(String thisname) throws Exception {
		if(buyAreaMapper.isRepeatThisName(thisname) > 0){//不为空，已存在该本级名称
			return false;
		}
		return true;
	}
	//--------------------------------------------------------------//
	//	供应商分类	//
	//--------------------------------------------------------------//
		
	@Override
	public boolean addBuySupplierSort(BuySupplierSort sort) throws Exception {
		return buySupplierSortMapper.addBuySupplierSort(sort)>0;
	}

	@Override
	public Map updateBuySupplierSort(BuySupplierSort sort) throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		BuySupplierSort oldSort = getBuySupplierSortDetail(sort.getOldid());
		if(null != oldSort){
			if(!oldSort.getThisid().equals(sort.getThisid()) || !oldSort.getThisname().equals(sort.getThisname())){ //判断名称是否有修改，有修改则更新所有子节点名称
				//updateTreeName("t_base_sales_customersort", customer.getName(), customer.getId());
				List<BuySupplierSort> childList = buySupplierSortMapper.getBuySupplierSortChildList(oldSort.getId());
				if(childList.size() != 0){
					for(BuySupplierSort repeatBSS : childList){
						repeatBSS.setOldid(repeatBSS.getId());
						if(!oldSort.getThisid().equals(sort.getThisid())){
							String newid = repeatBSS.getId().replaceFirst(oldSort.getThisid(), sort.getThisid()).trim();
							repeatBSS.setId(newid);
							String newpid = repeatBSS.getPid().replaceFirst(oldSort.getThisid(), sort.getThisid()).trim();
							repeatBSS.setPid(newpid);
						}
						if(!oldSort.getThisname().equals(sort.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatBSS.getName().replaceFirst(oldSort.getThisname(), sort.getThisname());
							repeatBSS.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeatBSS.getId());
						node.setParentid(repeatBSS.getPid());
						node.setText(repeatBSS.getThisname());
						node.setState(repeatBSS.getState());
						map2.put(repeatBSS.getOldid(), node);
					}
					buySupplierSortMapper.editBuySupplierSortBatch(childList);
				}
			}
		}
		boolean flag = buySupplierSortMapper.updateBuySupplierSort(sort)>0;
		if(flag){
			Tree node = new Tree();
			node.setId(sort.getId());
			node.setParentid(sort.getPid());
			node.setText(sort.getThisname());
			node.setState(sort.getState());
			map2.put(sort.getOldid(), node);
		}
		map.put("flag", flag);
		map.put("nodes", map2);
		return map;
	}

	@Override
	public List getBuySupplierSortList() throws Exception {
		return buySupplierSortMapper.getBuySupplierSortList();
	}
	@Override
	public List getBuySupplierSortListByMap(Map map) throws Exception{
		String cols = getAccessColumnList("t_base_buy_supplier_sort",null);
		map.put("cols", cols);
		String sql = getDataAccessRule("t_base_buy_supplier_sort",null);
		map.put("dataSql", sql);
		return buySupplierSortMapper.getBuySupplierSortListByMap(map);		
	}

	@Override
	public BuySupplierSort getBuySupplierSortDetail(String id) throws Exception {
		return buySupplierSortMapper.getBuySupplierSortDetail(id);
	}

	@Override
	public boolean deleteBuySupplierSort(String id) throws Exception {
		return buySupplierSortMapper.deleteBuySupplierSort(id)>0;
	}
	@Override
	public boolean updateBuySupplierSortBy(Map map) throws Exception{
		if(!map.containsKey("isdataauth") || !"0".equals(map.get("isdataauth").toString())){
			String sql = getDataAccessRule("t_base_buy_supplier_sort",null);
			map.put("authDataSql", sql);
		}
		return buySupplierSortMapper.updateBuySupplierSortBy(map)>0;
	}
	@Override
	public int getBuySupplierSortCountBy(Map map) throws Exception{
		if(map.containsKey("isdataauth") && "1".equals(map.get("isdataauth").toString())){
			String sql = getDataAccessRule("t_base_buy_supplier_sort",null);
			map.put("authDataSql", sql);			
		}
		return buySupplierSortMapper.getBuySupplierSortCountBy(map);
	}
	
	@Override
	public Map closeBuySuppplierSort(String id) throws Exception{
		SysUser sysUser = getSysUser();
		Map condition = new HashMap();
		condition.put("parentAllChildren", id);
		List<BuySupplierSort> list = getBuySupplierSortListByMap(condition); //查询该节点及所有子节点信息
		int successNum = 0;
		int failureNum = 0;
		int notAllowNum = 0;
		List<String> ids = new ArrayList<String>();

		Map<String,Object> map=new HashMap<String, Object>();
		map.put("closeuserid", sysUser.getUserid());
		map.put("openusername", sysUser.getName());
		map.put("state","0");
		boolean flag=false;
		for(BuySupplierSort item: list){ //循环所有节点信息，判断可以禁用的则禁用。
			if(!"1".equals(item.getState())){
				notAllowNum++;
			}
			else{
				map.put("id", item.getId());
				flag = updateBuySupplierSortBy(map) || flag;
				if(flag){
					successNum++;
					ids.add(item.getId()); //返回所有禁用的记录编号，供前台更新树节点信息
				}
				else{
					failureNum++;
				}
			}
		}
		map.clear();
		map.put("flag", flag);
		map.put("isuccess", successNum);
		map.put("ifailure", failureNum);
		map.put("inohandle", notAllowNum);
		map.put("ids", ids);
		return map;
	}
	
	@Override
	public boolean isRepeatSuppplierSortThisname(String thisname)
			throws Exception {
		if(buySupplierSortMapper.isRepeatThisName(thisname) > 0){//不为空，已存在该本级名称
			return false;
		}
		return true;
	}
	//--------------------------------------------------------------//
	//	供应商对应分类	//
	//--------------------------------------------------------------//
	
	/**
	 * 添加供应商对应分类
	 * @param detailsort
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean addBuySupplierDetailSort(BuySupplierDetailsort detailsort) throws Exception{
		return buySupplierDetailSortMapper.insertBuySupplierDetailSort(detailsort)>0;
	}
	/**
	 * 修改供应商对应分类
	 * @param detailsort
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean updateBuySupplierDetailSort(BuySupplierDetailsort detailsort) throws Exception{
		return buySupplierDetailSortMapper.updateBuySupplierDetailSort(detailsort)>0;
	}
	/**
	 * 删除供应商对应分类
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean deleteBuySupplierDetailSort(String id) throws Exception{
		return buySupplierDetailSortMapper.deleteBuySupplierDetailSort(id)>0;
	}

	public boolean deleteBuySupplierDetailSortBySupplier(String supplierid) throws Exception{
		return buySupplierDetailSortMapper.deleteBuySupplierDetailSortBySupplier(supplierid)>0;
	}
	/**
	 * 根据供应商编号显示供应商对应分类列表<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public List showBuySupplierDetailSortListBy(Map map) throws Exception{
		List list=buySupplierDetailSortMapper.showBuySupplierDetailSortListBy(map);
		return list;
	}
	
	//--------------------------------------------------------------//
	//	供应商档案	//
	//--------------------------------------------------------------//

	@Override
	public PageData showBuySupplierPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_base_buy_supplier",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_buy_supplier",null);
		pageMap.setDataSql(sql);
		List<BuySupplier> list=buySupplierMapper.getBuySupplierPageList(pageMap);
		addBuySupplierNameInfo(list);
		return new PageData(buySupplierMapper.getBuySupplierPageListCount(pageMap),list,pageMap);
	}
	private void addBuySupplierNameInfo(List<BuySupplier> list) throws Exception{
		if(null==list || list.size()==0){
			return ;
		}
		IContacterService contacterService=(IContacterService) SpringContextUtils.getBean("contacterService");
		IPersonnelService personnelService=(IPersonnelService) SpringContextUtils.getBean("personnelService");
		DepartMent departMent=null;
		for(BuySupplier item : list){
			if(StringUtils.isNotEmpty(item.getContact())){
				if(null!=contacterService){
					Contacter contacter=contacterService.getContacter(item.getContact());
					if(null!=contacter){
						item.setContactname(contacter.getName());
					}
				}
			}
			if(StringUtils.isNotEmpty(item.getBuydeptid())){
				departMent=getDepartmentByDeptid(item.getBuydeptid());
				if(null!=departMent){
					item.setBuydeptname(departMent.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getBuyuserid())){
				if(null!=personnelService){
					Personnel personnel=personnelService.showPersonnelInfo(item.getBuyuserid());
					if(null!=personnel){
						item.setBuyusername(personnel.getName());
					}
				}
			}
			//所属部门
			if(StringUtils.isNotEmpty(item.getFiliale())){
				DepartMent departMent2 = getDepartmentByDeptid(item.getFiliale());
				if(null != departMent2){
					item.setFilialename(departMent2.getName());
				}
			}
			SysCode stateSysCode = getBaseSysCodeMapper().getSysCodeInfo(item.getState(), "state");
			if(null != stateSysCode){
				item.setStatename(stateSysCode.getCodename());
			}
			Map map = new HashMap();
			map.put("id", item.getSettletype());
			Settlement settlement = getBaseFinanceMapper().getSettlemetDetail(map);
			if(null != settlement){
				item.setSettletypename(settlement.getName());
			}
			StorageInfo storageInfo = getBaseStorageMapper().showBaseStorageInfo(item.getStorageid());
			if(null != storageInfo){
				item.setStoragename(storageInfo.getName());
			}
		}
	}
	@Override
	public Map addImportBuySupplier(BuySupplier buySupplier) throws Exception{
		String failStr = "",closeVal = "",repeatVal = "",errorVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0,errorNum = 0;
		boolean flag = false;
		Map map = new HashMap();
		try {
			BuySupplier supplier = buySupplierMapper.getBuySupplier(buySupplier.getId());
			if(null == supplier){
				SysUser sysUser = getSysUser();
				sysUser.setAdduserid(sysUser.getUserid());
				if(StringUtils.isEmpty(buySupplier.getState())){
					buySupplier.setState("2");
				}
				if(StringUtils.isNotEmpty(buySupplier.getShortname())){
					buySupplier.setPinyin(CommonUtils.getPinYingJC(buySupplier.getShortname()));
				}
				flag = buySupplierMapper.insertBuySupplier(buySupplier) > 0;
				if(!flag){
					if(StringUtils.isNotEmpty(failStr)){
						failStr += "," + buySupplier.getId(); 
					}
					else{
						failStr = buySupplier.getId();
					}
					failureNum++;
				}
				else{
					successNum++;
					//对方联系人
					if(StringUtils.isNotEmpty(buySupplier.getContactname())){
						Contacter contacter = new Contacter();
						contacter.setName(buySupplier.getContactname());
						if(StringUtils.isNotEmpty(buySupplier.getContactmobile())){
							contacter.setMobile(buySupplier.getContactmobile());
						}
						contacter.setState("1");
						contacter.setIsdefault("1");
						contacter.setSupplier(buySupplier.getId());
						if(contacterMapper.addContacter(contacter) > 0){
							List<Contacter> clist = contacterMapper.getContacterByName(buySupplier.getContactname());
							if(clist.size() != 0){
								buySupplier.setContact(clist.get(0).getId());
								buySupplier.setOldid(buySupplier.getId());
							}
							buySupplierMapper.updateBuySupplier(buySupplier);
						}
					}
				}
			}
			else{
				if("0".equals(supplier.getState())){//禁用状态，不允许导入
					if(StringUtils.isEmpty(closeVal)){
						closeVal = supplier.getId();
					}
					else{
						closeVal += "," + supplier.getId();
					}
					closeNum++;
				}
				else{
					if(StringUtils.isEmpty(repeatVal)){
						repeatVal = supplier.getId();
					}
					else{
						repeatVal += "," + supplier.getId();
					}
					repeatNum++;
				}
			}
		} catch (Exception e) {
			if(StringUtils.isEmpty(repeatVal)){
				errorVal = buySupplier.getId();
			}
			else{
				errorVal += "," + buySupplier.getId();
			}
			errorNum++;
		}
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatVal", repeatVal);
		map.put("errorNum", errorNum);
		map.put("errorVal", errorVal);
		return map;
	}
	
	@Override
	public Map addDRSupplierDS(List<BuySupplierDetailsort> list)
			throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		if(list.size() != 0){
			for(BuySupplierDetailsort buySupplierDetailsort : list){
				try {
					if(null != buySupplierDetailsort.getId()){
						BuySupplierDetailsort buySupplierDetailsort2 = buySupplierDetailSortMapper.getBuySupplierDetailsortInfo(buySupplierDetailsort.getId().toString());
						if(null != buySupplierDetailsort2){
							buySupplierDetailsort.setId(null);
							flag = buySupplierDetailSortMapper.insertBuySupplierDetailSort(buySupplierDetailsort) > 0;
						}
					}
					else{
						flag = buySupplierDetailSortMapper.insertBuySupplierDetailSort(buySupplierDetailsort) > 0;
					}
				} catch (Exception e) {
					flag = false;
					break;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public boolean addBuySupplier(BuySupplier buySupplier) throws Exception{
		if(StringUtils.isEmpty(buySupplier.getPinyin()) && StringUtils.isNotEmpty(buySupplier.getShortname())){
			buySupplier.setPinyin(CommonUtils.getPinYingJC(buySupplier.getShortname()));
		}
		boolean flag= buySupplierMapper.insertBuySupplier(buySupplier)>0;
		if(flag){
			BuySupplierDetailsort buySupplierDetailsort=buySupplier.getBuySupplierDetailsort();
			if(buySupplierDetailsort!=null){
				String sorts=buySupplierDetailsort.getSuppliersort();
				if(sorts!=null  && !"".equals(sorts.trim())){
					String sortnames=buySupplierDetailsort.getSuppliersortname();
					String remarks=buySupplierDetailsort.getRemark();
					if(remarks==null){
						remarks="";
					}
					String isdefaults=buySupplierDetailsort.getIsdefault();
					if(isdefaults==null){
						isdefaults="";
					}
					String[] sortarr=sorts.split(",");
					String[] sortnamearr=sorts.split(",");
					String[] remarkarr=remarks.split(",");
					String[] isdefarr=isdefaults.split(",");
					for(int i=0; i<sortarr.length;i++){
						buySupplierDetailsort=new BuySupplierDetailsort();
						buySupplierDetailsort.setSupplierid(buySupplier.getId());
						buySupplierDetailsort.setSuppliersort(sortarr[i]);
						if(i<sortnamearr.length){
							buySupplierDetailsort.setSuppliersortname(sortnamearr[i]);
						}
						if(i<remarkarr.length){
							buySupplierDetailsort.setRemark(remarkarr[i]);
						}
						if(i<isdefarr.length){
							buySupplierDetailsort.setIsdefault(isdefarr[i]);
						}
						addBuySupplierDetailSort(buySupplierDetailsort);
					}
				}				
			}
		}
		return flag;
	}
	/**
	 * 更新供应商档案
	 * @param buySupplier
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	@Override
	public boolean updateBuySupplier(BuySupplier buySupplier) throws Exception{
		if(StringUtils.isEmpty(buySupplier.getPinyin()) && StringUtils.isNotEmpty(buySupplier.getShortname())){
			buySupplier.setPinyin(CommonUtils.getPinYingJC(buySupplier.getShortname()));
		}
		//尤妮佳、强生、大宝、舒士达、葛兰素史可、中美史可、蓝月亮、妮维雅(供应商编码)
		String UNJIASupids = getSysParamValue("UNJIAIDS");
		if(StringUtils.isNotEmpty(UNJIASupids)){
			if(UNJIASupids.indexOf(",") != -1){
				String[] UNJIASupidArr = UNJIASupids.split(",");
				for(String UNJIASupid : UNJIASupidArr){
					if(buySupplier.getOldid().equals(UNJIASupid)){
						SysParam sysParam = getBaseSysParamMapper().getSysParam("UNJIAIDS");
						//字符串“,”隔开，执行替换
						String newUNJIASupids = sysParam.getPvalue().replace(UNJIASupid, buySupplier.getId());
						sysParam.setPvalue(newUNJIASupids);
						getBaseSysParamMapper().updateSysParam(sysParam);
					}
				}
			}else{
				if(buySupplier.getOldid().equals(UNJIASupids)){
					SysParam sysParam = getBaseSysParamMapper().getSysParam("UNJIAIDS");
					sysParam.setPvalue(buySupplier.getId());
					getBaseSysParamMapper().updateSysParam(sysParam);
				}
			}
		}
		//判断商品品牌数据是否需要修改,若编码改变，则需要修改
		if(!buySupplier.getOldid().equals(buySupplier.getId())){
			List<Brand> blist = getBaseGoodsMapper().getBrandListBySupplierid(buySupplier.getOldid());
			if(null != blist && blist.size() != 0){
				for(Brand brand : blist){
					brand.setOldId(brand.getId());
					brand.setSupplierid(buySupplier.getId());
					getBaseGoodsMapper().editBrand(brand);
				}
			}
		}
		boolean flag= buySupplierMapper.updateBuySupplier(buySupplier)>0;
		if(flag){
			buySupplierDetailSortMapper.deleteBuySupplierDetailSortBySupplier(buySupplier.getId());
			BuySupplierDetailsort buySupplierDetailsort=buySupplier.getBuySupplierDetailsort();
			if(buySupplierDetailsort!=null){
				String sorts=buySupplierDetailsort.getSuppliersort();
				if(sorts!=null  && !"".equals(sorts.trim())){
					String sortnames=buySupplierDetailsort.getSuppliersortname();
					String remarks=buySupplierDetailsort.getRemark();
					if(remarks==null){
						remarks="";
					}
					String isdefaults=buySupplierDetailsort.getIsdefault();
					if(isdefaults==null){
						isdefaults="";
					}
					String[] sortarr=sorts.split(",");
					if(StringUtils.isEmpty(sortnames)){
						sortnames = "";
						for(String sort : sortarr){
							BuySupplierSort buySupplierSort = getBuySupplierSortDetail(sort);
							if(null != buySupplierSort){
								if(StringUtils.isEmpty(sortnames)){
									sortnames = buySupplierSort.getThisname();
								}else{
									sortnames += "," + buySupplierSort.getThisname();
								}
							}
						}
					}
					String[] sortnamearr=sorts.split(",");
					String[] remarkarr=remarks.split(",");
					String[] isdefarr=isdefaults.split(",");
					for(int i=0; i<sortarr.length;i++){
						buySupplierDetailsort=new BuySupplierDetailsort();
						buySupplierDetailsort.setSupplierid(buySupplier.getId());
						buySupplierDetailsort.setSuppliersort(sortarr[i]);
						if(i<sortnamearr.length){
							buySupplierDetailsort.setSuppliersortname(sortnamearr[i]);
						}
						if(i<remarkarr.length){
							buySupplierDetailsort.setRemark(remarkarr[i]);
						}
						if(i<isdefarr.length){
							buySupplierDetailsort.setIsdefault(isdefarr[i]);
						}
						addBuySupplierDetailSort(buySupplierDetailsort);
					}
				}				
			}
		}
		return flag;
	}
	/**
	 * 删除供应商档案
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	@Override
	public boolean deleteBuySupplier(String id) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		if(null==id || "".equals(id.trim())){
			return false;
		}
		map.put("id", id);
		BuySupplier buySupplier = buySupplierMapper.getBuySupplier(id);
		if(null != buySupplier){
			buySupplierDetailSortMapper.deleteBuySupplierDetailSortBySupplier(id);
			if(StringUtils.isNotEmpty(buySupplier.getContact())){
				contacterMapper.deleteContacter(buySupplier.getContact());
			}
		}
		return deleteBuySupplierBy(map);
	}
	@Override
	public boolean deleteBuySupplierBy(Map map)throws Exception{
		String sql = getDataAccessRule("t_base_buy_supplier",null);
		map.put("authDataSql", sql);	
		return buySupplierMapper.deleteBuySupplierBy(map)>0;
	}
	@Override
	public int getBuySupplierCountBy(Map map) throws Exception{
		if(map.containsKey("isdataauth") && "1".equals(map.get("isdataauth").toString())){
			String sql = getDataAccessRule("t_base_buy_supplier",null);
			map.put("authDataSql", sql);			
		}
		return buySupplierMapper.getBuySupplierCountBy(map);
	}
	
	@Override
	public BuySupplier showBuySupplier(String id) throws Exception{
		BuySupplier buySupplier = buySupplierMapper.getBuySupplier(id);
		return buySupplier;
	}
	@Override
	public List getBuySupplierListBy(Map map) throws Exception{
		String cols = getAccessColumnList("t_base_buy_supplier",null);
		map.put("authCols",cols);
		String sql = getDataAccessRule("t_base_buy_supplier",null);
		map.put("authDataSql", sql);
		return buySupplierMapper.getBuySupplierListBy(map);
	}
	@Override
	public BuySupplier getBuySupplierBy(Map map) throws Exception{
		String cols = getAccessColumnList("t_base_buy_supplier",null);
		map.put("authCols",cols);
		String sql = getDataAccessRule("t_base_buy_supplier",null);
		map.put("authDataSql", sql);
		return buySupplierMapper.getBuySupplierBy(map);
	}
	@Override
	public BuySupplier getBuySupplierByAuth(String id) throws Exception{
		if(null==id && !"".equals(id.trim())){
			return null;
		}
		Map map=new HashMap();
		String cols = getAccessColumnList("t_base_buy_supplier",null);
		map.put("authCols",cols);
		String sql = getDataAccessRule("t_base_buy_supplier",null);
		map.put("authDataSql", sql);
		map.put("id", id.trim());
		return buySupplierMapper.getBuySupplierBy(map);
	}
	
	@Override
	public boolean updateBuySupplierBy(Map map) throws Exception{
		String sql = getDataAccessRule("t_base_buy_supplier",null);
		map.put("authDataSql", sql);
		return buySupplierMapper.updateBuySupplierBy(map)>0;
	}
	@Override
	public List getBuySupplierStateListBy(Map map) throws Exception{
		if(map.containsKey("isdataauth") && "1".equals(map.get("isdataauth").toString())){
			String sql = getDataAccessRule("t_base_buy_supplier",null);
			map.put("authDataSql", sql);
		}
		return buySupplierMapper.getBuySupplierStateListBy(map);
	}

	@Override
	public PageData getSupplierSelectListData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_base_buy_supplier", null);
		pageMap.setDataSql(dataSql);
		//控件传过来的参数 生成sql语句
		String paramRule = (String) pageMap.getCondition().get("paramRule");
		String paramRuleSql = RuleJSONUtils.widgetParamToSql(paramRule,null);
		pageMap.getCondition().put("paramRuleSql", paramRuleSql);
		List<BuySupplier> list = buySupplierMapper.getSupplierSelectListData(pageMap);
		for(BuySupplier buySupplier :list){
			DepartMent departMent = getDepartmentByDeptid(buySupplier.getBuydeptid());
			if(null!=departMent){
				buySupplier.setBuydeptname(departMent.getName());
			}
			Personnel personnel = personnelMapper.getPersonnelInfo(buySupplier.getBuyuserid());
			if(null!=personnel){
				buySupplier.setBuyusername(personnel.getName());
			}
			Map map = new HashMap();
			map.put("id", buySupplier.getContact());
			Contacter contacter = contacterMapper.getContacterDetail(map);
			if(null!=contacter){
				buySupplier.setContactname(contacter.getName());
			}
		}
		PageData pageData = new PageData(buySupplierMapper.getSupplierSelectListDataCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public Map addShortcutSupplierExcel(List<BuySupplier> list)
			throws Exception {
		boolean flag = false;
		String closeVal = "",repeatVal = "",failStr="",errorVal = "",repeateIndex="",closeIndex="",errorIndex="";
		int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0,errorNum = 0;
		Map returnMap = new HashMap();
        boolean fl = "".equals(closeIndex);
		if(list.size() != 0){
			for(BuySupplier buySupplier : list){
				try {
					Map map = new HashMap();
					map.put("id", buySupplier.getId());
					BuySupplier buySupplier2 = getBuySupplierBy(map);
					if(null != buySupplier2){//重复
						if("0".equals(buySupplier2.getState())){//禁用状态，不允许导入
							if(StringUtils.isEmpty(closeVal)){
								closeVal = buySupplier.getId();
                                closeIndex = (list.indexOf(buySupplier)+2)+"";
							}
							else{
								closeVal += "," + buySupplier.getId();
                                closeIndex += "," +(list.indexOf(buySupplier)+2);
							}
							closeNum++;
						}
						else{
							if(StringUtils.isEmpty(repeatVal)){
								repeatVal = buySupplier.getId();
                                repeateIndex = (list.indexOf(buySupplier)+2)+"";
							}
							else{
								repeatVal += "," + buySupplier.getId();
                                repeateIndex += "," +(list.indexOf(buySupplier)+2);
							}
							repeatNum++;
						}

					}
					else{
						if(StringUtils.isEmpty(buySupplier.getPinyin()) && StringUtils.isNotEmpty(buySupplier.getName())){
							buySupplier.setPinyin(CommonUtils.getPinYingJC(buySupplier.getName()));
						}
						if(StringUtils.isNotEmpty(buySupplier.getFilialename())){
							List<DepartMent> list2 = getBaseDepartMentMapper().returnDeptIdByName(buySupplier.getFilialename());
							if(null != list2 && list2.size() != 0){
								buySupplier.setFiliale(list2.get(0).getId());
							}
						}
						//所属分类
						if(StringUtils.isNotEmpty(buySupplier.getSuppliersortname())){
							BuySupplierSort buySupplierSort = getBaseBuySupplierSortMapper().getBuySupplierSortInfoByThisname(buySupplier.getSuppliersortname());
							if(null != buySupplierSort){
								buySupplier.setSuppliersort(buySupplierSort.getId());
								buySupplierDetailSortMapper.deleteBuySupplierDetailSortBySupplier(buySupplier.getId());
								BuySupplierDetailsort buySupplierDetailsort = new BuySupplierDetailsort();
								buySupplierDetailsort.setSupplierid(buySupplier.getId());
								buySupplierDetailsort.setSuppliersort(buySupplierSort.getId());
								buySupplierDetailsort.setSuppliersortname(buySupplier.getSuppliersortname());
								buySupplierDetailsort.setIsdefault("1");
								buySupplierDetailSortMapper.insertBuySupplierDetailSort(buySupplierDetailsort);
							}
						}
						//采购区域
						if(StringUtils.isNotEmpty(buySupplier.getBuyareaname())){
							BuyArea buyArea = getBaseBuyAreaMapper().getBuyAreaByThisname(buySupplier.getBuyareaname());
							if(null != buyArea){
								buySupplier.setBuyarea(buyArea.getId());
							}
						}
						//默认采购部门
						if(null != buySupplier.getBuydeptname()){
							List<DepartMent> dList = getBaseDepartMentMapper().returnDeptIdByName(buySupplier.getBuydeptname());
							if(dList.size() != 0){
								buySupplier.setBuydeptid(dList.get(0).getId());
							}
						}
						//默认采购员
						if(null != buySupplier.getBuyusername()){
							List<Personnel> pList = getPersonnelMapper().returnPersnnelIdByName(buySupplier.getBuyusername());
							if(pList.size() != 0){
								buySupplier.setBuyuserid(pList.get(0).getId());
							}
						}
//						//结算方式
//						if(null != buySupplier.getSettletypename()){
//							List<Settlement> list2 = getBaseFinanceMapper().returnSettlementListByName(buySupplier.getSettletypename());
//							if(list2.size() != 0){
//								buySupplier.setSettletype(list2.get(0).getId());
//							}
//						}
						if(null != buySupplier.getCanceltypename()){
							String canceltype = getBaseSysCodeMapper().codenametocode(buySupplier.getCanceltypename(), "canceltype");
							if(StringUtils.isNotEmpty(canceltype)){
								buySupplier.setCanceltype(canceltype);
							}
						}
						//对应仓库
						if(null != buySupplier.getStoragename()){
							List<StorageInfo> list2 = getBaseStorageMapper().returnStorageIdByName(buySupplier.getStoragename());
							if(list2.size() != 0){
								buySupplier.setStorageid(list2.get(0).getId());
							}
						}
						//结算方式
						if(null != buySupplier.getSettletypename()){
							//结算方式
							Settlement settlement = getBaseFinanceMapper().getSettlementListByName(buySupplier.getSettletypename());
							if(null != settlement){
								buySupplier.setSettletype(settlement.getId());
								if("1".equals(settlement.getType())){
									if(StringUtils.isEmpty(buySupplier.getSettleday())){
										buySupplier.setSettleday("1");
									}else if(Integer.parseInt(buySupplier.getSettleday())>31){
										buySupplier.setSettleday("1");
									}
								}
							}
						}
						//订单追加
						if(StringUtils.isNotEmpty(buySupplier.getOrderappendname())){
							String orderappend = getBaseSysCodeMapper().codenametocode(buySupplier.getOrderappendname(), "yesorno");
							if(StringUtils.isNotEmpty(orderappend)){
								buySupplier.setOrderappend(orderappend);
							}
						}else{
							buySupplier.setOrderappend("1");
						}
						//状态
						if(null != buySupplier.getStatename()){
							String state = getBaseSysCodeMapper().codenametocode(buySupplier.getStatename(), "state");
							if(StringUtils.isNotEmpty(state)){
								buySupplier.setState(state);
							}
						}
						else{
							buySupplier.setState("1");
						}
						SysUser sysUser = getSysUser();
						buySupplier.setAdddeptid(sysUser.getDepartmentid());
						buySupplier.setAdddeptname(sysUser.getDepartmentname());
						buySupplier.setAdduserid(sysUser.getUserid());
						buySupplier.setAddusername(sysUser.getName());
						if(null != buySupplier.getRecoverymodename()){
							String recoverymode = getBaseSysCodeMapper().codenametocode(buySupplier.getRecoverymodename(), "recoverymode");
							if(StringUtils.isNotEmpty(recoverymode)){
								buySupplier.setRecoverymode(recoverymode);
							}
						}
						flag = buySupplierMapper.insertBuySupplier(buySupplier) > 0;
						if(flag){
							successNum++;
						}else{
							failureNum++;
							if(StringUtils.isNotEmpty(failStr)){
								failStr += "," + buySupplier.getId(); 
							}else{
								failStr = buySupplier.getId();
							}
						}
					}
				} catch (Exception e) {
					if(StringUtils.isEmpty(errorVal)){
						errorVal = buySupplier.getId();
                        errorIndex = (list.indexOf(buySupplier)+2)+"";
					}else{
						errorVal += "," + buySupplier.getId();
                        errorIndex += ","+(list.indexOf(buySupplier)+2);
					}
					errorNum++;
					continue;
				}
			}
		}
        if(repeateIndex != ""){
            repeatVal = repeatVal+"(导入数据中第"+repeateIndex+"行数据) ";
        }
        if(closeIndex != ""){
            closeVal = closeVal +"(导入数据中第"+closeIndex+"行数据) ";
        }
        if(errorIndex != ""){
            errorVal = errorVal +"(导入数据中第"+errorIndex+"行数据) 导入格式";
        }
		returnMap.put("flag", flag);
		returnMap.put("success", successNum);
		returnMap.put("failure", failureNum);
		returnMap.put("failStr", failStr);
		returnMap.put("repeatNum", repeatNum);
		returnMap.put("closeNum", closeNum);
		returnMap.put("closeVal", closeVal);
		returnMap.put("repeatVal", repeatVal);
		returnMap.put("errorNum", errorNum);
		returnMap.put("errorVal", errorVal);
		return returnMap;
	}

    @Override
    public Map addDRBuyAreaExcel(List<BuyArea> list) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0,errorNum = 0,levelNum=0,reptthisNum = 0;
        String closeVal = "",repeatVal = "",failStr = "",errorVal = "",levelVal = "",reptthisnameVal = "";
        if(list.size() != 0){
            String tn = "t_base_buy_area";
            List levelList = showFilesLevelList(tn);
            if(levelList.size() != 0){
                for(BuyArea buyArea : list){
                    if(StringUtils.isEmpty(buyArea.getId())){
                        continue;
                    }
                    //根据档案级次信息根据编码获取本级编码
                    Map map = getObjectThisidByidCaseFilesLevel(tn,buyArea.getId());
                    if(null != map && !map.isEmpty()){
                        String id = buyArea.getId();
                        String thisid = (null != map.get("thisid")) ? map.get("thisid").toString() : "";
                        String pid = (null != map.get("pid")) ? map.get("pid").toString() : "";
                        try {
                            //判断销售区域是否重复
                            BuyArea buyAreaInfo = buyAreaMapper.getBuyAreaDetail(id);
                            if(buyAreaInfo == null){//不重复
								//判断本机名称是否重复
								if(buyAreaMapper.isRepeatThisName(buyArea.getThisname()) == 0){
									buyArea.setThisid(thisid);
									buyArea.setPid(pid);
									SysUser sysUser = getSysUser();
									buyArea.setAdduserid(sysUser.getUserid());
									buyArea.setAdddeptid(sysUser.getDepartmentid());
									buyArea.setAdddeptname(sysUser.getDepartmentname());
									buyArea.setAddusername(sysUser.getName());
									if(StringUtils.isEmpty(buyArea.getPid())){
										buyArea.setName(buyArea.getThisname());
									}
									buyArea.setState("1");
									flag = buyAreaMapper.addBuyArea(buyArea) > 0;
									if(!flag){
										if(StringUtils.isNotEmpty(failStr)){
											failStr += "," + buyArea.getId();
										}
										else{
											failStr = buyArea.getId();
										}
										failureNum++;
									}else{
										successNum++;
									}
								}else{
									if(StringUtils.isEmpty(reptthisnameVal)){
										reptthisnameVal = buyArea.getId();
									}else{
										reptthisnameVal += "," + buyArea.getId();
									}
									reptthisNum++;
								}
                            }else{
                                if("0".equals(buyAreaInfo.getState())){//禁用状态，不允许导入
                                    if(StringUtils.isEmpty(closeVal)){
                                        closeVal = buyAreaInfo.getId();
                                    }else{
                                        closeVal += "," + buyAreaInfo.getId();
                                    }
                                    closeNum++;
                                }
                                else{
                                    if(StringUtils.isEmpty(repeatVal)){
                                        repeatVal = buyAreaInfo.getId();
                                    }else{
                                        repeatVal += "," + buyAreaInfo.getId();
                                    }
                                    repeatNum++;
                                }
                            }
                        }catch (Exception e) {
                            if(StringUtils.isEmpty(errorVal)){
                                errorVal = id;
                            }
                            else{
                                errorVal += "," + id;
                            }
                            errorNum++;
                            continue;
                        }
                    }else{
                        levelNum++;
                        if(StringUtils.isNotEmpty(levelVal)){
                            levelVal += "," + buyArea.getId();
                        }
                        else{
                            levelVal = buyArea.getId();
                        }
                    }
                }
                //名称
                List<BuyArea> nonameList = buyAreaMapper.getBuyAreaWithoutName();
                if(nonameList.size() != 0){
                    for(BuyArea nonameBA : nonameList){
                        BuyArea pBA = getBuyAreaDetail(nonameBA.getPid());
                        if(null != pBA){
                            nonameBA.setOldid(nonameBA.getId());
                            String name = "";
                            if(StringUtils.isEmpty(pBA.getName())){
                                name = nonameBA.getThisname();
                            }else{
                                name = pBA.getName() + "/" + nonameBA.getThisname();
                            }
                            if(StringUtils.isNotEmpty(pBA.getName())){
                                nonameBA.setName(name);
                            }else{
                                nonameBA.setName(pBA.getThisname());
                            }
                            buyAreaMapper.updateBuyArea(nonameBA);
                        }
                    }
                }
            }else{
                returnMap.put("nolevel", true);
            }
        }
        returnMap.put("flag", flag);
        returnMap.put("success", successNum);
        returnMap.put("failure", failureNum);
        returnMap.put("failStr", failStr);
        returnMap.put("repeatNum", repeatNum);
        returnMap.put("repeatVal", repeatVal);
        returnMap.put("closeNum", closeNum);
        returnMap.put("closeVal", closeVal);
        returnMap.put("errorNum", errorNum);
        returnMap.put("errorVal", errorVal);
        returnMap.put("levelNum", levelNum);
        returnMap.put("levelVal", levelVal);
		returnMap.put("reptthisNum", reptthisNum);
		returnMap.put("reptthisnameVal", reptthisnameVal);
        return returnMap;
    }

    @Override
    public Map addDRSupplierSortExcel(List<BuySupplierSort> list) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0,errorNum = 0,levelNum=0,reptthisNum = 0;
        String closeVal = "",repeatVal = "",failStr = "",errorVal = "",levelVal = "",reptthisnameVal = "";
        if(list.size() != 0){
            String tn = "t_base_buy_supplier_sort";
            List levelList = showFilesLevelList(tn);
            if(levelList.size() != 0){
                for(BuySupplierSort buySupplierSort : list){
                    if(StringUtils.isEmpty(buySupplierSort.getId())){
                        continue;
                    }
                    //根据档案级次信息根据编码获取本级编码
                    Map map = getObjectThisidByidCaseFilesLevel(tn,buySupplierSort.getId());
                    if(null != map && !map.isEmpty()){
                        String id = buySupplierSort.getId();
                        String thisid = (null != map.get("thisid")) ? map.get("thisid").toString() : "";
                        String pid = (null != map.get("pid")) ? map.get("pid").toString() : "";
                        try {
                            //判断销售区域是否重复
                            BuySupplierSort buySupplierSortInfo = buySupplierSortMapper.getBuySupplierSortDetail(id);
                            if(buySupplierSortInfo == null){//不重复
								//判断本机名称是否重复
								if(buySupplierSortMapper.isRepeatThisName(buySupplierSort.getThisname()) == 0){
									buySupplierSort.setThisid(thisid);
									buySupplierSort.setPid(pid);
									SysUser sysUser = getSysUser();
									buySupplierSort.setAdduserid(sysUser.getUserid());
									buySupplierSort.setAdddeptid(sysUser.getDepartmentid());
									buySupplierSort.setAdddeptname(sysUser.getDepartmentname());
									buySupplierSort.setAddusername(sysUser.getName());
									if(StringUtils.isEmpty(buySupplierSort.getPid())){
										buySupplierSort.setName(buySupplierSort.getThisname());
									}
									buySupplierSort.setState("1");
									flag = buySupplierSortMapper.addBuySupplierSort(buySupplierSort) > 0;
									if(!flag){
										if(StringUtils.isNotEmpty(failStr)){
											failStr += "," + buySupplierSort.getId();
										}
										else{
											failStr = buySupplierSort.getId();
										}
										failureNum++;
									}else{
										successNum++;
									}
								}else{
									if(StringUtils.isEmpty(reptthisnameVal)){
										reptthisnameVal = buySupplierSort.getId();
									}else{
										reptthisnameVal += "," + buySupplierSort.getId();
									}
									reptthisNum++;
								}
                            }else{
                                if("0".equals(buySupplierSortInfo.getState())){//禁用状态，不允许导入
                                    if(StringUtils.isEmpty(closeVal)){
                                        closeVal = buySupplierSortInfo.getId();
                                    }else{
                                        closeVal += "," + buySupplierSortInfo.getId();
                                    }
                                    closeNum++;
                                }
                                else{
                                    if(StringUtils.isEmpty(repeatVal)){
                                        repeatVal = buySupplierSortInfo.getId();
                                    }else{
                                        repeatVal += "," + buySupplierSortInfo.getId();
                                    }
                                    repeatNum++;
                                }
                            }
                        }catch (Exception e) {
                            if(StringUtils.isEmpty(errorVal)){
                                errorVal = id;
                            }
                            else{
                                errorVal += "," + id;
                            }
                            errorNum++;
                            continue;
                        }
                    }else{
                        levelNum++;
                        if(StringUtils.isNotEmpty(levelVal)){
                            levelVal += "," + buySupplierSort.getId();
                        }
                        else{
                            levelVal = buySupplierSort.getId();
                        }
                    }
                }
                //名称
                List<BuySupplierSort> nonameList = buySupplierSortMapper.getSupplierSortWithoutName();
                if(nonameList.size() != 0){
                    for(BuySupplierSort nonameBS : nonameList){
                        BuySupplierSort pBS = getBuySupplierSortDetail(nonameBS.getPid());
                        if(null != pBS){
                            nonameBS.setOldid(nonameBS.getId());
                            String name = "";
                            if(StringUtils.isEmpty(pBS.getName())){
                                name = nonameBS.getThisname();
                            }else{
                                name = pBS.getName() + "/" + nonameBS.getThisname();
                            }
                            if(StringUtils.isNotEmpty(pBS.getName())){
                                nonameBS.setName(name);
                            }else{
                                nonameBS.setName(pBS.getThisname());
                            }
                            buySupplierSortMapper.updateBuySupplierSort(nonameBS);
                        }
                    }
                }
            }else{
                returnMap.put("nolevel", true);
            }
        }
        returnMap.put("flag", flag);
        returnMap.put("success", successNum);
        returnMap.put("failure", failureNum);
        returnMap.put("failStr", failStr);
        returnMap.put("repeatNum", repeatNum);
        returnMap.put("repeatVal", repeatVal);
        returnMap.put("closeNum", closeNum);
        returnMap.put("closeVal", closeVal);
        returnMap.put("errorNum", errorNum);
        returnMap.put("errorVal", errorVal);
        returnMap.put("levelNum", levelNum);
        returnMap.put("levelVal", levelVal);
		returnMap.put("reptthisNum", reptthisNum);
		returnMap.put("reptthisnameVal", reptthisnameVal);
        return returnMap;
    }

	@Override
	public List<Map> getSupplierListForMecshop() throws Exception {

		return buySupplierMapper.getSupplierListForMecshop();
	}

	/**
	 * 获取供应商列表
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	@Override
	public PageData getSupplierListForPact(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_base_buy_supplier", "a");
		pageMap.setDataSql(dataSql);
		//单表取字段权限
		String cols = getAccessColumnList("t_base_buy_supplier","a");
		pageMap.setCols(cols);
		List<BuySupplier> buySupplierList = buySupplierMapper.getSupplierListForPactList(pageMap);
		for(BuySupplier buySupplier: buySupplierList){
			//联系人
			Map map = new HashMap();
			map.put("id", buySupplier.getContact());
			Contacter contacter = getBaseContacterMapper().getContacterDetail(map);
			if(null != contacter){
				buySupplier.setContactname(contacter.getName());
				buySupplier.setContactmobile(contacter.getMobile());
			}
			//结算方式
			map.put("id", buySupplier.getSettletype());
			Settlement settlement = getBaseFinanceMapper().getSettlemetDetail(map);
			if(null != settlement){
				buySupplier.setSettletypename(settlement.getName());
			}


			BuySupplierSort buySupplierSort = getBuySupplierSortDetail(buySupplier.getSuppliersort());
			if(buySupplierSort != null){
				buySupplier.setSuppliersortname(buySupplierSort.getThisname());
			}
			BuyArea buyArea = getBuyAreaDetail(buySupplier.getBuyarea());
			if(buyArea != null){
				buySupplier.setBuyareaname(buyArea.getName());
			}
			SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(buySupplier.getState(), "state");
			if(null != sysCode){
				buySupplier.setStatename(sysCode.getCodename());
			}
		}
		return new PageData(buySupplierMapper.getSupplierListForPactCount(pageMap), buySupplierList, pageMap);
	}

	/**
	 * 添加供应商品牌结算方式
	 * @param buySupplierBrandSettletype
	 * @return java.lang.Boolean
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	@Override
	public Boolean addSupplierBrandSettletype(BuySupplierBrandSettletype buySupplierBrandSettletype) throws Exception{
		boolean flag = false;
		IJournalSheetService journalSheetService = (IJournalSheetService) SpringContextUtils.getBean("journalSheetService");
		if(StringUtils.isNotEmpty(buySupplierBrandSettletype.getBrandids()) && StringUtils.isNotEmpty(buySupplierBrandSettletype.getSupplierid())){
			String supplierid=buySupplierBrandSettletype.getSupplierid();
			buySupplierMapper.deleteSupplierBrandSettletypeBySupplieridAndBrandid(supplierid,buySupplierBrandSettletype.getBrandids());
			String[] brandidArr = buySupplierBrandSettletype.getBrandids().split(",");
			for(String brandid : brandidArr){
				buySupplierBrandSettletype.setBrandid(brandid);
				boolean flag2 = buySupplierMapper.addSupplierBrandSettletype(buySupplierBrandSettletype) > 0;
				if(flag2){
					journalSheetService.updateMatcostsNoWriteoffDuefromdateBySupplier(buySupplierBrandSettletype.getSupplierid());
				}
			}

			flag = true;
		}
		return flag;
	}

	/**
	 * 获取供应商关联的品牌结算方式
	 * @param supplierid
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	@Override
	public List getSupplierBrandSettletypeList(String supplierid){
		List<BuySupplierBrandSettletype> list = buySupplierMapper.getSupplierBrandSettletypeList(supplierid);
		for(BuySupplierBrandSettletype buySupplierBrandSettletype : list){
			Brand brand = getBaseGoodsMapper().getBrandInfo(buySupplierBrandSettletype.getBrandid());
			if(null!=brand){
				buySupplierBrandSettletype.setBrandname(brand.getName());
			}
			//结算方式
			Map map = new HashMap();
			map.put("id",buySupplierBrandSettletype.getSettletype());
			Settlement settlement = getBaseFinanceMapper().getSettlemetDetail(map);
			if(null != settlement){
				buySupplierBrandSettletype.setSettletypename(settlement.getName());
			}
		}
		return list;
	}
	/**
	 * 删除供应商品牌结算方式
	 * @param idstr
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public Map deleteSupplierBrandSettletypes(String idstr) throws Exception{
		String[] idarr = idstr.split(",");
		String sucbrands = "",unsucbrands = "";
		int sucnum = 0,unsucnum = 0;
		IJournalSheetService journalSheetService = (IJournalSheetService) SpringContextUtils.getBean("journalSheetService");
		for(String id : idarr){
			BuySupplierBrandSettletype buySupplierBrandSettletype = buySupplierMapper.getSupplierBrandSettletypeInfo(id);
			if(null != buySupplierBrandSettletype){
				boolean flag = buySupplierMapper.deleteSupplierBrandSettletype(id) > 0;
				if(flag){
					journalSheetService.updateMatcostsNoWriteoffDuefromdateBySupplier(buySupplierBrandSettletype.getSupplierid());
					sucnum++;
					if(sucbrands == ""){
						sucbrands = buySupplierBrandSettletype.getBrandid();
					}else{
						sucbrands += "," + buySupplierBrandSettletype.getBrandid();
					}
				}else{
					unsucnum++;
					if(unsucbrands == ""){
						unsucbrands = buySupplierBrandSettletype.getBrandid();
					}else{
						unsucbrands += "," + buySupplierBrandSettletype.getBrandid();
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("sucnum", sucnum);
		map.put("sucbrands", sucbrands);
		map.put("unsucnum", unsucnum);
		map.put("unsucbrands", unsucbrands);
		return map;
	}
	/**
	 * 修改供应商品牌结算方式
	 * @param buySupplierBrandSettletype
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	public boolean editSupplierBrandSettletype(BuySupplierBrandSettletype buySupplierBrandSettletype)throws Exception{
		IJournalSheetService journalSheetService = (IJournalSheetService) SpringContextUtils.getBean("journalSheetService");

		boolean flag2 = buySupplierMapper.editSupplierBrandSettletype(buySupplierBrandSettletype) > 0;
		if(flag2){
			journalSheetService.updateMatcostsNoWriteoffDuefromdateBySupplier(buySupplierBrandSettletype.getSupplierid());
		}
		return flag2;
	}

//	/**
//	 * 导入供应商品牌结算方式
//	 * @param list
//	 * @return java.util.Map
//	 * @throws
//	 * @author luoqiang
//	 * @date Nov 06, 2017
//	 */
//	@Override
//	public Map importSupplierBrandSettletype(List<Map<String, Object>> list) throws Exception{
//		String sucstr = "",failStr = "",errorVal = "",noidval = "",nobidval = "",errorIndex="",noidindex = "",nosupplierstr="";
//		int failureNum = 0,successNum = 0,errorNum = 0,noidnum = 0,nobidnum = 0,nullcstnum = 0,nullbnum = 0,nosuppliernum=0;
//		Map returnMap = new HashMap();
//		Map<String, String> errorMap = new HashMap<String, String>();
//		Map<String, String> nocstidMap = new HashMap<String, String>();
//		Map<String, String> failMap = new HashMap<String, String>();
//		Map<String, String> sucMap = new HashMap<String, String>();
//		Map<String, String> nosupplierMap = new HashMap<String, String>();
//		IJournalSheetService journalSheetService = (IJournalSheetService) SpringContextUtils.getBean("journalSheetService");
//		if(list.size() != 0){
//			for(Map map : list){
//				BuySupplierBrandSettletype brandSettletype = new BuySupplierBrandSettletype();
//				BeanUtils.populate(brandSettletype, map);
//				try {
//					Brand brand= getBaseGoodsMapper().getBrandInfo(brandSettletype.getBrandid());
//					if(brand==null||!brand.getSupplierid().equals(brandSettletype.getSupplierid())){
//						nosupplierMap.put("<供应商编码："+brandSettletype.getSupplierid()+"，品牌编码："+brandSettletype.getBrandid()+">",brandSettletype.getBrandid());
//						nosuppliernum++;
//						continue;
//					}
//
//					if(StringUtils.isEmpty(brandSettletype.getSupplierid())){
//						nullcstnum++;
//					}else if(StringUtils.isEmpty(brandSettletype.getBrandid())) {
//						nullbnum++;
//					}else{
//						BuySupplier buySupplier = buySupplierMapper.getBuySupplier(brandSettletype.getSupplierid());
//						if(null == buySupplier){
//							if(!nocstidMap.containsKey(brandSettletype.getSupplierid())){
//								nocstidMap.put("<供应商编码："+brandSettletype.getSupplierid()+"，品牌编码："+brandSettletype.getBrandid()+">",brandSettletype.getBrandid());
////								nocstidMap.put(brandSettletype.getSupplierid(),brandSettletype.getSupplierid());
//							}
//							if(StringUtils.isEmpty(noidindex)){
//								noidindex = (list.indexOf(buySupplier)+2)+"";
//							} else{
//								noidindex +="," + (list.indexOf(buySupplier)+2);
//							}
//							noidnum++;
//						}else{
//							if(null == brand){
//								if(StringUtils.isNotEmpty(nobidval)){
//									nobidval += "," + brandSettletype.getBrandid();
//								}else{
//									nobidval = brandSettletype.getBrandid();
//								}
//								nobidnum++;
//							}else{
//								//根据客户、品牌判断是否已存在该结算方式，若存在进行覆盖
//								BuySupplierBrandSettletype buySupplierBrandSettletype=buySupplierMapper.getSupplierBrandSettletypeBySupplieridAndBrandid(brandSettletype.getSupplierid(),brandSettletype.getBrandid());
//								if(null != buySupplierBrandSettletype){//重复
//									buySupplierMapper.deleteSupplierBrandSettletypeBySupplieridAndBrandid(brandSettletype.getSupplierid(),brandSettletype.getBrandid());
//								}
//								//结算方式
//								Settlement settlement = getBaseFinanceMapper().getSettlementListByName(brandSettletype.getSettletypename());
//								if(null != settlement){
//									brandSettletype.setSettletype(settlement.getId());
//									if("1".equals(settlement.getType())){
//										if(StringUtils.isEmpty(brandSettletype.getSettleday())){
//											brandSettletype.setSettleday("1");
//										}else if(brandSettletype.getSettleday().compareTo("31") > 0){
//											brandSettletype.setSettleday("1");
//										}
//									}
//								}
//								if(StringUtils.isNotEmpty(brandSettletype.getBrandid()) && StringUtils.isNotEmpty(brandSettletype.getSupplierid())){
//									boolean flag = buySupplierMapper.addSupplierBrandSettletype(brandSettletype) > 0;
//									if(flag){
//										journalSheetService.updateMatcostsNoWriteoffDuefromdateBySupplier(buySupplierBrandSettletype.getSupplierid());
//										successNum++;
//										if(sucMap.containsKey(brandSettletype.getSupplierid())){
//											String brandids = (String)sucMap.get(brandSettletype.getSupplierid());
//											if(StringUtils.isEmpty(brandids)){
//												brandids = brandSettletype.getBrandid();
//											}else{
//												brandids += "," + brandSettletype.getBrandid();
//											}
//											sucMap.put(brandSettletype.getSupplierid(),brandids);
//										}else{
//											sucMap.put(brandSettletype.getSupplierid(),brandSettletype.getBrandid());
//										}
//									}else{
//										failureNum++;
//										if(failMap.containsKey(brandSettletype.getSupplierid())){
//											String brandids = (String)failMap.get(brandSettletype.getSupplierid());
//											if(StringUtils.isEmpty(brandids)){
//												brandids = brandSettletype.getBrandid();
//											}else{
//												brandids += "," + brandSettletype.getBrandid();
//											}
//											failMap.put(brandSettletype.getSupplierid(),brandids);
//										}else{
//											failMap.put(brandSettletype.getSupplierid(),brandSettletype.getBrandid());
//										}
//									}
//								}
//							}
//						}
//					}
//				}catch (Exception e) {
//					if(errorMap.containsKey(brandSettletype.getSupplierid())){
//						String brandids = (String)errorMap.get(brandSettletype.getSupplierid());
//						if(StringUtils.isNotEmpty(brandids)){
//							brandids += "," + brandSettletype.getBrandid();
//						}else{
//							brandids = brandSettletype.getBrandid();
//						}
//						errorMap.put(brandSettletype.getSupplierid(),brandids);
//					}else{
//						errorMap.put(brandSettletype.getSupplierid(),brandSettletype.getBrandid());
//					}
//					if(StringUtils.isEmpty(errorIndex)){
//						errorIndex = (list.indexOf(brandSettletype)+2)+"";
//					}else{
//						errorIndex +="," + (list.indexOf(brandSettletype)+2);
//					}
//					errorNum++;
//					continue;
//				}
//			}
//		}
//
//		if(!errorMap.isEmpty()){
//			for (Map.Entry<String, String> entry : errorMap.entrySet()) {
//				if(StringUtils.isEmpty(errorVal)){
//					errorVal = "<供应商编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
//				}else{
//					errorVal += "<br>" + "供应商编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
//				}
//			}
//			if(errorIndex != ""){
//				errorVal = errorVal +" (导入数据中第"+errorIndex+"行数据) 导入格式";
//			}
//		}
//		if(!nosupplierMap.isEmpty()){
//			for (Map.Entry<String, String> entry : nosupplierMap.entrySet()) {
//				if(StringUtils.isEmpty(nosupplierstr)){
//					nosupplierstr = entry.getKey()+"；";
//				}else{
//					nosupplierstr += "<br>" +  entry.getKey()+"；";
//				}
//			}
//		}
//		if(!nocstidMap.isEmpty()){
//			for (Map.Entry<String, String> entry : nocstidMap.entrySet()) {
//				if(StringUtils.isEmpty(noidval)){
//					noidval = entry.getKey();
//				}else{
//					noidval += "," + entry.getKey();
//				}
//			}
//		}
//		if(!failMap.isEmpty()){
//			for (Map.Entry<String, String> entry : failMap.entrySet()) {
//				if(StringUtils.isEmpty(failStr)){
//					failStr = "<供应商编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
//				}else{
//					failStr += "<br>" + "<供应商编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
//				}
//			}
//		}
//		if(!sucMap.isEmpty()){
//			for (Map.Entry<String, String> entry : sucMap.entrySet()) {
//				if(StringUtils.isEmpty(sucstr)){
//					sucstr = "<供应商编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
//				}else{
//					sucstr += "<br>" + "<供应商编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
//				}
//			}
//		}
//		String msg = "";
//		if(StringUtils.isNotEmpty(errorVal)){
//			msg = errorVal + " 共" + errorNum +"条数据出错";
//		}
//		if(nullcstnum > 0){
//			if(StringUtils.isEmpty(msg)){
//				msg = nullcstnum + "条导入数据供应商编码为空，不允许导入";
//			}else{
//				msg += "<br>" + nullcstnum + "条导入数据供应商编码为空，不允许导入";
//			}
//		}
//		if(nullbnum > 0){
//			if(StringUtils.isEmpty(msg)){
//				msg = nullbnum + "条导入数据品牌编码为空，不允许导入";
//			}else{
//				msg += "<br>" + nullbnum + "条导入数据品牌编码为空，不允许导入";
//			}
//		}
//		if(StringUtils.isNotEmpty(noidval)){
//			if(noidindex != ""){
//				noidval = noidval +" (导入数据中第"+noidindex+"行数据) 供应商不存在，"+noidnum+"条不允许导入";
//			}
//			if(StringUtils.isEmpty(msg)){
//				msg = noidval;
//			}else{
//				msg += "<br>" + noidval;
//			}
//		}
//		if(StringUtils.isNotEmpty(nobidval)){
//			if(StringUtils.isEmpty(msg)){
//				msg = "品牌编码："+nobidval+" 不存在，"+nobidnum+"条不允许导入";
//			}else{
//				msg += "<br>" + "品牌编码："+nobidval+" 不存在，"+nobidnum+"条不允许导入";
//			}
//		}
//		if(StringUtils.isNotEmpty(failStr)){
//			if(StringUtils.isEmpty(msg)){
//				msg = failStr + " 共" + failureNum + "条数据，导入失败";
//			}else{
//				msg += "<br>" + failStr + " 共" + failureNum + "条数据，导入失败";
//			}
//		}
//		if(StringUtils.isNotEmpty(nosupplierstr)){
//			if(StringUtils.isEmpty(msg)){
//				msg = nosupplierstr + " 共" + nosuppliernum + "条数据，品牌与所属供应商不匹配";
//			}else{
//				msg += "<br>" + nosupplierstr + " 共" + nosuppliernum + "条数据，品牌与所属供应商不匹配";
//			}
//		}
//
//		if(StringUtils.isNotEmpty(sucstr)){
//			if(StringUtils.isEmpty(msg)){
//				msg = sucstr + " 共" + successNum +"条数据,导入成功";
//			}else{
//				msg += "<br>" + sucstr + " 共" + successNum +"条数据,导入成功";
//			}
//		}
//		returnMap.put("sucstr", sucstr);
//		returnMap.put("msg", msg);
//		return returnMap;
//	}

	/**
	 * 导入供应商品牌结算方式
	 * @param list
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Nov 06, 2017
	 */
	public List<Map<String, Object>> importSupplierBrandSettletype(List<Map<String, Object>> list) throws Exception{
		IJournalSheetService journalSheetService = (IJournalSheetService) SpringContextUtils.getBean("journalSheetService");
		List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> temp =  (Map<String, Object>)list.get(i);
			BuySupplierBrandSettletype brandSettletype = new BuySupplierBrandSettletype();
			BeanUtils.populate(brandSettletype, temp);

			StringBuilder msgSb = new StringBuilder();
			Brand brand= getBaseGoodsMapper().getBrandInfo(brandSettletype.getBrandid());
			if(brand==null){
				Map errorMap = new HashMap(temp);
				errorMap.put("lineno", (i + 2));
				errorMap.put("errors", "品牌不存在");
				errorList.add(errorMap);
				continue;
			}
			BuySupplier buySupplier = buySupplierMapper.getBuySupplier(brandSettletype.getSupplierid());
			if(buySupplier==null){
				Map errorMap = new HashMap(temp);
				errorMap.put("lineno", (i + 2));
				errorMap.put("errors", "供应商不存在");
				errorList.add(errorMap);
				continue;
			}
			if(!brand.getSupplierid().equals(brandSettletype.getSupplierid())){
				Map errorMap = new HashMap(temp);
				errorMap.put("lineno", (i + 2));
				errorMap.put("errors", "该供应商下不存在该品牌");
				errorList.add(errorMap);
				continue;
			}
			//结算方式
			Settlement settlement = getBaseFinanceMapper().getSettlementListByName(brandSettletype.getSettletypename());
			if(settlement==null){
				Map errorMap = new HashMap(temp);
				errorMap.put("lineno", (i + 2));
				errorMap.put("errors", "结算方式不存在");
				errorList.add(errorMap);
				continue;
			}
			brandSettletype.setSettletype(settlement.getId());
			if("1".equals(settlement.getType())){
				if(StringUtils.isEmpty(brandSettletype.getSettleday())){
					brandSettletype.setSettleday("1");
				}else if(brandSettletype.getSettleday().compareTo("31") > 0){
					brandSettletype.setSettleday("1");
				}
			}

			//根据客户、品牌判断是否已存在该结算方式，若存在进行覆盖
			BuySupplierBrandSettletype buySupplierBrandSettletype=buySupplierMapper.getSupplierBrandSettletypeBySupplieridAndBrandid(brandSettletype.getSupplierid(),brandSettletype.getBrandid());
			if(null != buySupplierBrandSettletype){//重复
				buySupplierMapper.deleteSupplierBrandSettletypeBySupplieridAndBrandid(brandSettletype.getSupplierid(),brandSettletype.getBrandid());
			}

			boolean flag = buySupplierMapper.addSupplierBrandSettletype(brandSettletype) > 0;
			if(flag){
				journalSheetService.updateMatcostsNoWriteoffDuefromdateBySupplier(buySupplierBrandSettletype.getSupplierid());
			}else{
				Map errorMap = new HashMap(temp);
				errorMap.put("lineno", (i + 2));
				errorMap.put("errors", "供应商品牌结算方式添加失败");
				errorList.add(errorMap);
				continue;
			}

			if (msgSb.length() > 0) {
				Map errorMap = new HashMap(temp);
				errorMap.put("lineno", (i + 2));
				errorMap.put("errors", new String(msgSb));
				errorList.add(errorMap);
			}
		}
		return errorList;
	}

	/**
	 * 获取全部供应商
	 * @param
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 22, 2017
	 */
	public List getAllBuySupplierList(){
		return buySupplierMapper.getAllBuySupplierList();
	}
}

