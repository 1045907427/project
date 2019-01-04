/**
 * @(#)ContacterServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 17, 2013 zhengziyong 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.basefiles.dao.ContacterAndSortMapper;
import com.hd.agent.basefiles.dao.ContacterMapper;
import com.hd.agent.basefiles.dao.ContacterSortMapper;
import com.hd.agent.basefiles.dao.CustomerMapper;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Contacter;
import com.hd.agent.basefiles.model.ContacterAndSort;
import com.hd.agent.basefiles.model.ContacterSort;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.service.IContacterService;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author zhengziyong
 */
public class ContacterServiceImpl extends BaseServiceImpl implements IContacterService {

	private ContacterSortMapper contacterSortMapper;
	private ContacterMapper contacterMapper;
	private ContacterAndSortMapper contacterAndSortMapper;
	private CustomerMapper customerMapper;

	public CustomerMapper getCustomerMapper() {
		return customerMapper;
	}

	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	@Override
	public List getContacterSortList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_linkman_sort",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_linkman_sort",null); //数据权限
		pageMap.setDataSql(sql);
		return contacterSortMapper.getContacterSortList(pageMap);
	}

	@Override
	public ContacterSort getContacterSortDetail(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_linkman_sort", null); //字段权限
		map.put("id", id);
		return contacterSortMapper.getContacterSortDetail(map);
	}

	@Override
	public List getContacterSortParentAllChildren(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_linkman_sort",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_linkman_sort",null); //数据权限
		pageMap.setDataSql(sql);
		return contacterSortMapper.getContacterSortParentAllChildren(pageMap);
	}

	@Override
	public boolean addContacterSort(ContacterSort sort) throws Exception {
		return contacterSortMapper.addContacterSort(sort) > 0;
	}
	
	@Override
	public Map addDRContancter(Contacter contacter) throws Exception {
		boolean flag = false;
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		Map map = new HashMap();
		map.put("id", contacter.getId());
		if(null != contacter){
			if(StringUtils.isNotEmpty(contacter.getId())){
				if(!(contacterMapper.isRepeatContacterID(contacter.getId()) > 0)){
					flag = contacterMapper.addContacter(contacter) > 0;
					if(!flag){
						if(StringUtils.isNotEmpty(failStr)){
							failStr += "," + contacter.getId(); 
						}
						else{
							failStr = contacter.getId();
						}
						failureNum++;
					}
					else{
						successNum++;
					}
				}
				else{
					Contacter contacter2 = contacterMapper.getContacterDetail(map);
					if(null != contacter2){
						if("0".equals(contacter2.getState())){//禁用状态，不允许导入
							if(StringUtils.isEmpty(closeVal)){
								closeVal = contacter2.getId();
							}
							else{
								closeVal += "," + contacter2.getId();
							}
							closeNum++;
						}
						else{
							if(StringUtils.isEmpty(repeatVal)){
								repeatVal = contacter2.getId();
							}
							else{
								repeatVal += "," + contacter2.getId();
							}
							repeatNum++;
						}
					}
				}
			}
		}
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
	public Map updateContacterSort(ContacterSort sort) throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		Map map3 = new HashMap();
		map3.put("id", sort.getOldid());
		ContacterSort oldSort = contacterSortMapper.getContacterSortDetail(map3);
		if(null != oldSort){
			if(!oldSort.getThisid().equals(sort.getThisid()) || !oldSort.getThisname().equals(sort.getThisname())){ //判断名称是否有修改，有修改则更新所有子节点名称
				//updateTreeName("t_base_linkman_sort", sort.getName(), sort.getId());
				List<ContacterSort> childList = contacterSortMapper.getContacterSortChildList(oldSort.getId());
				if(childList.size() != 0){
					for(ContacterSort repeatCS : childList){
						repeatCS.setOldid(repeatCS.getId());
						if(!oldSort.getThisid().equals(sort.getThisid())){
							String newid = repeatCS.getId().replaceFirst(oldSort.getThisid(), sort.getThisid()).trim();
							repeatCS.setId(newid);
							String newpid = repeatCS.getPid().replaceFirst(oldSort.getThisid(), sort.getThisid()).trim();
							repeatCS.setPid(newpid);
						}
						if(!oldSort.getThisname().equals(sort.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatCS.getName().replaceFirst(oldSort.getThisname(), sort.getThisname());
							repeatCS.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeatCS.getId());
						node.setParentid(repeatCS.getPid());
						node.setText(repeatCS.getThisname());
						node.setState(repeatCS.getState());
						map2.put(repeatCS.getOldid(), node);
					}
					contacterSortMapper.editContacterSortBatch(childList);
				}
			}
		}
		boolean flag = contacterSortMapper.updateContacterSort(sort)>0;
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
	public boolean deleteContacterSort(String id) throws Exception {
		return contacterSortMapper.deleteContacterSort(id)>0;
	}

	@Override
	public boolean updateContacterSortOpen(ContacterSort sort) throws Exception {
		return contacterSortMapper.updateContacterSortOpen(sort)>0;
	}

	@Override
	public boolean updateContacterSortClose(ContacterSort sort) throws Exception {
		return contacterSortMapper.updateContacterSortClose(sort)>0;
	}

	@Override
	public boolean isRepeatContacterSortThisname(String thisname)
			throws Exception {
		if(contacterSortMapper.isRepeatThisName(thisname) > 0){//不为空，已存在该本级名称
			return false;
		}
		return true;
	}

	@Override
	public PageData getContacterData(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_linkman_info",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_linkman_info",null);
		pageMap.setDataSql(sql);
		return new PageData(contacterMapper.getContacterCount(pageMap),contacterMapper.getContacterList(pageMap),pageMap);
	}

	@Override
	public List getContacterListByCustomer(String type, String id) throws Exception {
		return contacterMapper.getContacterListByCustomer(type, id);
	}

	@Override
	public boolean addContacter(Contacter contacter) throws Exception {
		ContacterAndSort contacterAndSort = contacter.getContacterAndSort();
		if(contacterAndSort != null){
			String sortIds = contacterAndSort.getLinkmansort();
			String sortNames = contacterAndSort.getLinkmansortname();
			String isDefaults = contacterAndSort.getIsdefault();
			String remarks = contacterAndSort.getRemark();
			String[] contacterIdArr = null;
			String[] sortIdArr = null;
			String[] sortNameArr = null;
			String[] isDefaultArr = null;
			String[] remarkArr = null;
			if(null != sortIds){
				sortIdArr = sortIds.split(",");
			}
			if(null != sortNames){
				sortNameArr = sortNames.split(",");
			}
			if(null != isDefaults){
				isDefaultArr = isDefaults.split(",");
			}
			if(null != remarks){
				remarkArr = remarks.split(",");
			}
			if(sortIdArr != null){
				for(int i=0; i<sortIdArr.length;i++){
					ContacterAndSort sort = new ContacterAndSort();
					sort.setIsdefault(isDefaultArr[i].trim());
					sort.setLinkmanid(contacter.getId().trim());
					sort.setLinkmansort(sortIdArr[i].trim());
					sort.setLinkmansortname(sortNameArr[i].trim());
					sort.setRemark(remarkArr[i]);
					contacterAndSortMapper.addContacterAndSort(sort);
				}
			}
		}
		return contacterMapper.addContacter(contacter)>0;
	}

	@Override
	public Contacter getContacterDetail(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_linkman_info", null);
		map.put("id", id);
		Contacter contacter = contacterMapper.getContacterDetail(map);
		if(contacter != null){
			List list = contacterAndSortMapper.getContacterAndSortListByContacter(id);
			contacter.setContacterAndSortList(list);
			BuySupplier buySupplier = getBaseBuySupplierMapper().getBuySupplier(contacter.getSupplier());
			if(null != buySupplier){
				contacter.setSuppliername(buySupplier.getName());
			}
			Map map2 = new HashMap();
			map2.put("id", contacter.getCustomer());
			Customer customer = getBaseCustomerMapper().getCustomerDetail(map2);
			if(null != customer){
				contacter.setCustomername(customer.getName());
			}
		}
		return contacter;
	}
	
	@Override
	public Contacter getContacter(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_linkman_info", null);
		map.put("id", id);
		return contacterMapper.getContacterDetail(map);
	}

	@Override
	public boolean updateContacter(Contacter contacter, String sortEdit) throws Exception {
		ContacterAndSort contacterAndSort = contacter.getContacterAndSort();
		if("1".equals(sortEdit)){ //sortEdit为“1”表示对应分类有修改，为“0”表示没有修改，没有修改不做操作
			contacterAndSortMapper.deleteContacterAndSortByContacter(contacter.getId());
			if(contacterAndSort != null){
				String sortIds = contacterAndSort.getLinkmansort();
				String sortNames = contacterAndSort.getLinkmansortname();
				String isDefaults = contacterAndSort.getIsdefault();
				String remarks = contacterAndSort.getRemark();
				String[] sortIdArr = null;
				String[] sortNameArr = null;
				String[] isDefaultArr = null;
				String[] remarkArr = null;
				if(null != sortIds){
					sortIdArr = sortIds.split(",");
				}
				if(null != sortNames){
					sortNameArr = sortNames.split(",");
				}
				if(null != isDefaults){
					isDefaultArr = isDefaults.split(",");
				}
				if(null != remarks){
					remarkArr = remarks.split(",");
				}
				if(sortIdArr != null){
					for(int i=0; i<sortIdArr.length;i++){
						ContacterAndSort sort = new ContacterAndSort();
						sort.setIsdefault(isDefaultArr[i].trim());
						sort.setLinkmanid(contacter.getId().trim());
						sort.setLinkmansort(sortIdArr[i].trim());
						sort.setLinkmansortname(sortNameArr[i].trim());
						sort.setRemark(remarkArr[i].trim());
						contacterAndSortMapper.addContacterAndSort(sort);
					}
				}
			}
		}
		return contacterMapper.updateContacter(contacter)>0;
	}

	@Override
	public Map addDRContacterAndSort(ContacterAndSort contacterAndSort)throws Exception{
		Map map = new HashMap();
		boolean flag = false;
		if(contacterAndSort != null){
			if(null != contacterAndSort.getId()){
				contacterAndSort.setId(null);
			}
			flag = contacterAndSortMapper.addContacterAndSort(contacterAndSort) > 0;
		}
		map.put("flag", flag);
		return map;
	}
	
	@Override
	public boolean deleteContacter(String id) throws Exception {
		contacterAndSortMapper.deleteContacterAndSortByContacter(id);
		boolean flag = contacterMapper.deleteContacter(id)>0;
		if(flag){
			List<Customer> list = customerMapper.getCustomerListByContact(id);
			if(list.size() > 0){
				for(Customer customer : list){
					customer.setOldid(customer.getId());
					customer.setContact("");
					customer.setMobile("");
					customerMapper.updateCustomer(customer);
				}
			}
		}
		return flag;
	}

	@Override
	public boolean updateContacterOpen(Contacter contacter) throws Exception {
		return contacterMapper.updateContacterOpen(contacter)>0;
	}

	@Override
	public boolean updateContacterClose(Contacter contacter) throws Exception {
		return contacterMapper.updateContacterClose(contacter)>0;
	}

	@Override
	public List getContacterAndSortList(String id) throws Exception {
		return contacterAndSortMapper.getContacterAndSortListByContacter(id);
	}

	@Override
	public boolean updateContacterDetault(String state, String id) throws Exception {
		return contacterMapper.updateContacterDetault(state, id)>0;
	}

	@Override
	public boolean updateContacterDetaultBySupplier(String supplier, String contacterId) throws Exception {
		contacterMapper.updateContacterNoDetaultBySupplier(supplier);
		return updateContacterDetault("1", contacterId);
	}

	public ContacterSortMapper getContacterSortMapper() {
		return contacterSortMapper;
	}

	public void setContacterSortMapper(ContacterSortMapper contacterSortMapper) {
		this.contacterSortMapper = contacterSortMapper;
	}

	public ContacterMapper getContacterMapper() {
		return contacterMapper;
	}

	public void setContacterMapper(ContacterMapper contacterMapper) {
		this.contacterMapper = contacterMapper;
	}

	public ContacterAndSortMapper getContacterAndSortMapper() {
		return contacterAndSortMapper;
	}

	public void setContacterAndSortMapper(
			ContacterAndSortMapper contacterAndSortMapper) {
		this.contacterAndSortMapper = contacterAndSortMapper;
	}
}

