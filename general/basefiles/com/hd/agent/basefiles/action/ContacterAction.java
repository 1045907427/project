/**
 * @(#)ContacterAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 17, 2013 zhengziyong 创建版本
 */
package com.hd.agent.basefiles.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Contacter;
import com.hd.agent.basefiles.model.ContacterAndSort;
import com.hd.agent.basefiles.model.ContacterSort;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.FilesLevel;
import com.hd.agent.basefiles.service.IContacterService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.TableColumn;

/**
 * 
 * 
 * @author zhengziyong
 */
public class ContacterAction extends BaseAction {

	private IContacterService contacterService;
	private ContacterSort contacterSort;
	private Contacter contacter;
	private ContacterAndSort contacterAndSort;
	
	/**
	 * 联系人分类页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public String contacterSort() throws Exception{
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_linkman_sort");
		int len = 0;
		if(null != list && list.size() != 0){
			len = list.size();
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<list.size();i++){
				FilesLevel level = (FilesLevel)list.get(i);
				sb.append(level.getLen() + ",");
			}
			if(sb.length() > 0){
				request.setAttribute("lenStr", sb.deleteCharAt(sb.length()-1).toString());
			}
		}
		request.setAttribute("len", len);
		return SUCCESS;
	}
	
	/**
	 * 获取联系人分类树形列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public String getContacterSortTree() throws Exception{
		String type = request.getParameter("type"); //当type为1时只取启用数据
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<ContacterSort> list = contacterService.getContacterSortList(pageMap);
		Map<String, String> first = new HashMap<String, String>();
		first.put("id", "");
		first.put("name", "联系人分类");
		first.put("open", "true");
		result.add(first);
		for(ContacterSort sort : list){
			if("1".equals(type)){
				if("1".equals(sort.getState())){
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", sort.getId());
					map.put("pid", sort.getPid());
					map.put("name", sort.getThisname());
					map.put("state", sort.getState());
					result.add(map);
				}
			}
			else{
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", sort.getId());
				map.put("pid", sort.getPid());
				map.put("name", sort.getThisname());
				map.put("state", sort.getState());
				result.add(map);
			}
		}
		addJSONArray(result);
		return SUCCESS;
	}
	
	/**
	 * 联系人分类添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String contacterSortAddPage() throws Exception{
		String id = request.getParameter("id"); //当为空时添加的联系人分类为一级目录，否则该编号为添加分类的父级
		ContacterSort contacterSort = contacterService.getContacterSortDetail(id);
		int len = 0;
		if(contacterSort != null && StringUtils.isNotEmpty(contacterSort.getId())){
			len = contacterSort.getId().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_linkman_sort", len);
		request.setAttribute("contacterSort", contacterSort);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	
	/**
	 * 添加联系人分类
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public String addContacterSort() throws Exception{
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){
			contacterSort.setState("3");
		}
		if("real".equals(addType)){
			contacterSort.setState("2");
		}
		SysUser sysUser = getSysUser();
		contacterSort.setAdduserid(sysUser.getUserid());
		contacterSort.setAdddeptid(sysUser.getDepartmentid());
		contacterSort.setAdddeptname(sysUser.getDepartmentname());
		contacterSort.setAddusername(sysUser.getName());
		boolean flag = contacterService.addContacterSort(contacterSort);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", contacterSort.getId());
		map.put("type", "add");
		Map<String, String> node = new HashMap<String, String>();
		node.put("id", contacterSort.getId());
		node.put("pid", contacterSort.getPid());
		node.put("name", contacterSort.getThisname());
		node.put("state", contacterSort.getState());
		map.put("node", node);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 修改联系人信息页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String contacterSortEditPage() throws Exception{
		String id = request.getParameter("id");
		ContacterSort contacterSort = contacterService.getContacterSortDetail(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		if(StringUtils.isNotEmpty(contacterSort.getPid())){
			ContacterSort sort = contacterService.getContacterSortDetail(contacterSort.getPid());
			request.setAttribute("parentName", sort.getName());
		}
		int len = 0;
		if(contacterSort != null && StringUtils.isNotEmpty(contacterSort.getPid())){
			len = contacterSort.getPid().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_linkman_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("editFlag", canTableDataDelete("t_base_linkman_sort", id));
		request.setAttribute("stateList", stateList);
		request.setAttribute("contacterSort", contacterSort);
		return SUCCESS;
	}
	
	/**
	 * 修改联系人分类信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String updateContacterSort() throws Exception{
		String addType = request.getParameter("addType");
		if("0".equals(contacterSort.getState()) || "1".equals(contacterSort.getState())){
			
		}
		else{
			if("real".equals(addType)){
				contacterSort.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		contacterSort.setModifyuserid(sysUser.getUserid());
		contacterSort.setModifyusername(sysUser.getName());
		Map map = contacterService.updateContacterSort(contacterSort);
		map.put("backid", contacterSort.getId());
		map.put("oldid", contacterSort.getOldid());
		map.put("type", "edit");
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 联系人分类复制页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public String contacterSortCopyPage() throws Exception{
		String id = request.getParameter("id");
		ContacterSort contacterSort = contacterService.getContacterSortDetail(id);
		if(StringUtils.isNotEmpty(contacterSort.getPid())){
			ContacterSort sort = contacterService.getContacterSortDetail(contacterSort.getPid());
			request.setAttribute("parentName", sort.getName());
		}
		request.setAttribute("contacterSort", contacterSort);
		return SUCCESS;
	}
	
	/**
	 * 联系人分类查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public String contacterSortViewPage() throws Exception{
		String id = request.getParameter("id");
		ContacterSort contacterSort = contacterService.getContacterSortDetail(id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		request.setAttribute("stateList", stateList);
		request.setAttribute("contacterSort", contacterSort);
		return SUCCESS;
	}
	
	/**
	 * 删除联系人分类信息，删除时判断该信息是否被引用，引用则无法删除。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String deleteContacterSort() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_linkman_sort", id); 
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = contacterService.deleteContacterSort(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 启用联系人分类
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String openContacterSort() throws Exception{
		String id = request.getParameter("id");
		ContacterSort sort = new ContacterSort();
		SysUser sysUser = getSysUser();
		sort.setOpenuserid(sysUser.getUserid());
		sort.setOpenusername(sysUser.getName());
		sort.setId(id);
		boolean flag = contacterService.updateContacterSortOpen(sort);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 禁用联系人分类，如果该节点是父节点，则禁用下面所有为启用状态的子节点。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String closeContacterSort() throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map condition = new HashMap();
		condition.put("id", id);
		pageMap.setCondition(condition);
		List<ContacterSort> list = contacterService.getContacterSortParentAllChildren(pageMap); //查询该节点及所有子节点信息
		int successNum = 0;
		int failureNum = 0;
		int notAllowNum = 0;
		List<String> ids = new ArrayList<String>();
		for(ContacterSort contacterSort: list){ //循环所有节点信息，判断可以禁用的则禁用。
			if(!"1".equals(contacterSort.getState())){
				notAllowNum++;
			}
			else{
				ContacterSort sort = new ContacterSort();
				sort.setCloseuserid(sysUser.getUserid());
				sort.setCloseusername(sysUser.getName());
				sort.setId(contacterSort.getId());
				boolean flag = contacterService.updateContacterSortClose(sort);
				if(flag){
					successNum++;
					ids.add(contacterSort.getId()); //返回所有禁用的记录编号，供前台更新树节点信息
				}
				else{
					failureNum++;
				}
			}
		}
		Map map = new HashMap();
		map.put("successNum", successNum);
		map.put("failureNum", failureNum);
		map.put("notAllowNum", notAllowNum);
		map.put("ids", ids);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 联系人分类编号是否被使用
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public String contacterSortNOUsed() throws Exception{
		String id = request.getParameter("id");
		ContacterSort contacterSort = contacterService.getContacterSortDetail(id);
		boolean flag = false;
		if(contacterSort != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 判断本级名称是否重复，true 不重复，false 重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public String isRepeatContacterSortThisname()throws Exception{
		String thisname = request.getParameter("thisname");
		boolean flag = contacterService.isRepeatContacterSortThisname(thisname);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 联系人管理页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 18, 2013
	 */
	public String contacterListPage() throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 获取联系人列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 18, 2013
	 */
	public String getContacterList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = contacterService.getContacterData(pageMap);
		List<SysCode> stateList = getBaseSysCodeService().showSysCodeListByType("state"); 
		List<Contacter> list = pageData.getList();
		for(Contacter contacter : list){
			StringBuilder sb = new StringBuilder();
			for(SysCode sysCode : stateList){
				if(sysCode.getCode().equals(contacter.getState())){
					contacter.setState(sysCode.getCodename());
				}
			}
			List<ContacterAndSort> conList = contacterService.getContacterAndSortList(contacter.getId());
			for(ContacterAndSort contacterAndSort : conList){
				sb.append(contacterAndSort.getLinkmansortname()).append("，");
			}
			if(sb.toString().endsWith("，")){
				sb.deleteCharAt(sb.length() - 1);
			}
			contacter.setLinkmansort(sb.toString());
			if(StringUtils.isNotEmpty(contacter.getCustomer())){
				Customer customer = getBaseSalesService().getCustomerInfo(contacter.getCustomer());
				if(null != customer){
					contacter.setCustomername(customer.getName());
				}
			}
			if(StringUtils.isNotEmpty(contacter.getSupplier())){
				BuySupplier buySupplier = getBaseBuyService().showBuySupplier(contacter.getSupplier());
				if(null != buySupplier){
					contacter.setSuppliername(buySupplier.getName());
				}
			}
		}
		pageData.setList(list);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 获取客户或供应商对应的联系人
	 * @param type: 1为客户2为联系人
	 * @param id：客户或供应商编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 22, 2013
	 */
	public String getContacterListCustomer() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		List<Contacter> list = contacterService.getContacterListByCustomer(type, id);
		List stateList = getBaseSysCodeService().showSysCodeListByType("state");
		for(Contacter contacter : list){ //联系人状态
			for(int i=0; i< stateList.size(); i++){
				SysCode sysCode = (SysCode)stateList.get(i);
				if(contacter.getState().equals(sysCode.getCode())){
					contacter.setState(sysCode.getCodename());
				}
			}
		}
		Map map = new HashMap();
		map.put("rows", list);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 联系人页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 18, 2013
	 */
	public String contacterPage() throws Exception{
		String sort = request.getParameter("sort");
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		request.setAttribute("sort", sort);
		request.setAttribute("type", type);
		request.setAttribute("id", id);
		return SUCCESS;
	}
	
	/**
	 * 联系人添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 18, 2013
	 */
	public String contacterAddPage() throws Exception{
		String sort = request.getParameter("sort");
		List sexList = getBaseSysCodeService().showSysCodeListByType("sex"); //性别
		List marryList = getBaseSysCodeService().showSysCodeListByType("maritalstatus"); //婚姻状况
		List nationList = getBaseSysCodeService().showSysCodeListByType("nation"); //民族
		List polList = getBaseSysCodeService().showSysCodeListByType("polstatus"); //政治面貌
		request.setAttribute("sexList", sexList);
		request.setAttribute("marryList", marryList);
		request.setAttribute("nationList", nationList);
		request.setAttribute("polList", polList);
		return SUCCESS;
	}
	
	/**
	 * 获取自定义字段名称
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 16, 2013
	 */
	public String contacterDIYFieldName() throws Exception{
		Map queryMap = new HashMap();
		queryMap.put("tablename", "t_base_linkman_info");
		List<TableColumn> list = getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		Map map = new HashMap();
		for(TableColumn column : list){
			if(column.getColumnname().indexOf("field")>-1){
				map.put(column.getColumnname(), column.getColchnname());
			}
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 添加联系人
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 18, 2013
	 */
	public String addContacter() throws Exception{
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){
			contacter.setState("3");
		}
		if("real".equals(addType)){
			contacter.setState("2");
		}
		SysUser sysUser = getSysUser();
		contacter.setAdduserid(sysUser.getUserid());
		contacter.setAdddeptid(sysUser.getDepartmentid());
		contacter.setAdddeptname(sysUser.getDepartmentname());
		contacter.setAddusername(sysUser.getName());
		contacter.setContacterAndSort(contacterAndSort);
		if(StringUtils.isNotEmpty(contacter.getCustomer())){
			contacter.setType("1");
		}
		else if(StringUtils.isNotEmpty(contacter.getSupplier())){
			contacter.setType("2");
		}
		boolean flag = contacterService.addContacter(contacter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", contacter.getId());
		map.put("type", "add");
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 联系人复制页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 20, 2013
	 */
	public String contacterCopyPage() throws Exception{
		String id = request.getParameter("id");
		Contacter contacter = contacterService.getContacterDetail(id);
		String contacterSortName = "";
		if(StringUtils.isNotEmpty(contacter.getLinkmansort())){
			ContacterSort contacterSort = contacterService.getContacterSortDetail(contacter.getLinkmansort());
			if(contacterSort != null){
				contacterSortName = contacterSort.getName();
			}
		}
		List sexList = getBaseSysCodeService().showSysCodeListByType("sex"); //性别
		List marryList = getBaseSysCodeService().showSysCodeListByType("maritalstatus"); //婚姻状况
		List nationList = getBaseSysCodeService().showSysCodeListByType("nation"); //民族
		List polList = getBaseSysCodeService().showSysCodeListByType("polstatus"); //政治面貌
		request.setAttribute("sexList", sexList);
		request.setAttribute("marryList", marryList);
		request.setAttribute("nationList", nationList);
		request.setAttribute("polList", polList);
		request.setAttribute("contacter", contacter);
		request.setAttribute("contacterSortName", contacterSortName);
		return SUCCESS;
	}
	
	/**
	 * 联系人修改页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 19, 2013
	 */
	public String contacterEditPage() throws Exception{
		String id = request.getParameter("id");
		if(!lockData("t_base_linkman_info", id)){
			request.setAttribute("lock", true);
			request.setAttribute("id", id);
			return SUCCESS;
		}
		Contacter contacter = contacterService.getContacterDetail(id);
		String contacterSortName = "";
		if(contacter != null &&StringUtils.isNotEmpty(contacter.getLinkmansort())){
			ContacterSort contacterSort = contacterService.getContacterSortDetail(contacter.getLinkmansort());
			if(contacterSort != null){
				contacterSortName = contacterSort.getName();
			}
		}
		List sexList = getBaseSysCodeService().showSysCodeListByType("sex"); //性别
		List marryList = getBaseSysCodeService().showSysCodeListByType("maritalstatus"); //婚姻状况
		List nationList = getBaseSysCodeService().showSysCodeListByType("nation"); //民族
		List polList = getBaseSysCodeService().showSysCodeListByType("polstatus"); //政治面貌
		request.setAttribute("editFlag", canTableDataDelete("t_base_linkman_info", id));
		List stateList = getBaseSysCodeService().showSysCodeListByType("state"); //状态
		request.setAttribute("stateList", stateList);
		request.setAttribute("sexList", sexList);
		request.setAttribute("marryList", marryList);
		request.setAttribute("nationList", nationList);
		request.setAttribute("polList", polList);
		request.setAttribute("contacter", contacter);
		request.setAttribute("contacterSortName", contacterSortName);
		return SUCCESS;
	}
	
	/**
	 * 修改联系人信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 19, 2013
	 */
	public String updateContacter() throws Exception{
		boolean lock = isLockEdit("t_base_linkman_info", contacter.getId()); // 判断锁定并解锁
		if (!lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			return SUCCESS;
		}
		String addType = request.getParameter("addType");
		String sortEdit = request.getParameter("sortEdit"); //判断对应分类没无修改
		if("0".equals(contacter.getState()) || "1".equals(contacter.getState())){
			
		}
		else{
			if("real".equals(addType)){
				contacter.setState("2");
			}
		}
		SysUser sysUser = getSysUser();
		contacter.setModifyuserid(sysUser.getUserid());
		contacter.setModifyusername(sysUser.getName());
		contacter.setContacterAndSort(contacterAndSort);
		if(StringUtils.isNotEmpty(contacter.getCustomer())){
			contacter.setType("1");
			contacter.setSupplier("");
		}
		else if(StringUtils.isNotEmpty(contacter.getSupplier())){
			contacter.setType("2");
			contacter.setCustomer("");
		}
		boolean flag = contacterService.updateContacter(contacter, sortEdit);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		map.put("backid", contacter.getId());
		map.put("oldid", contacter.getOldid());
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 联系人查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 19, 2013
	 */
	public String contacterViewPage() throws Exception{
		String id = request.getParameter("id");
		Contacter contacter = contacterService.getContacterDetail(id);
		if(contacter == null){
			return SUCCESS;
		}
		if(StringUtils.isNotEmpty(contacter.getLinkmansort())){
			ContacterSort contacterSort = contacterService.getContacterSortDetail(contacter.getLinkmansort());
			if(contacterSort != null){
				contacter.setLinkmansort(contacterSort.getName());
			}
		}
		List sexList = getBaseSysCodeService().showSysCodeListByType("sex"); //性别
		List marryList = getBaseSysCodeService().showSysCodeListByType("maritalstatus"); //婚姻状况
		List nationList = getBaseSysCodeService().showSysCodeListByType("nation"); //民族
		List polList = getBaseSysCodeService().showSysCodeListByType("polstatus"); //政治面貌
		List stateList = getBaseSysCodeService().showSysCodeListByType("state"); //状态
		request.setAttribute("stateList", stateList);
		request.setAttribute("sexList", sexList);
		request.setAttribute("marryList", marryList);
		request.setAttribute("nationList", nationList);
		request.setAttribute("polList", polList);
		request.setAttribute("contacter", contacter);
		return SUCCESS;
	}
	
	/**
	 * 删除联系人信息，删除时判断该信息是否被引用，引用则无法删除。
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String deleteContacter() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_base_linkman_info", id); 
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = contacterService.deleteContacter(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 批量删除联系人信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 20, 2013
	 */
	public String deleteMultiContacter() throws Exception{
		String ids = request.getParameter("id");
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() - 1);
		}
		String[] idArr = ids.split(",");
		int successNum = 0; //删除成功数
		int failureNum = 0; //删除失败数
		int notAllowNum = 0; //不允许删除数
		for(int i = 0; i<idArr.length; i++){
			String id = idArr[i];
			if(canTableDataDelete("t_base_linkman_info", id)){ //如果未被引就可以删除
				if(contacterService.deleteContacter(id)){
					successNum++;
					addLog("删除联系人信息，编号："+ id, true);
				}
				else{
					failureNum++;
				}
			}
			else{
				notAllowNum++;
			}
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("snum", successNum);
		map.put("fnum", failureNum);
		map.put("nnum", notAllowNum);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 启用联系人信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String openContacter() throws Exception{
		String id = request.getParameter("id");
		Contacter contacter = new Contacter();
		SysUser sysUser = getSysUser();
		contacter.setOpenuserid(sysUser.getUserid());
		contacter.setOpenusername(sysUser.getName());
		contacter.setId(id);
		boolean flag = contacterService.updateContacterOpen(contacter);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("backid", id);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 批量启用联系人信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 20, 2013
	 */
	public String openMultiContacter() throws Exception{
		String ids = request.getParameter("id");
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() - 1);
		}
		String[] idArr = ids.split(",");
		int successNum = 0; //启用成功数
		int failureNum = 0; //启用失败数
		int notAllowNum = 0; //不允许启用数
		SysUser sysUser = getSysUser();
		for(int i = 0; i<idArr.length; i++){
			String id = idArr[i];
			Contacter contacter = contacterService.getContacterDetail(id);
			if("0".equals(contacter.getState()) || "2".equals(contacter.getState())){
				Contacter con = new Contacter();
				con.setOpenuserid(sysUser.getUserid());
				con.setOpenusername(sysUser.getName());
				con.setId(contacter.getId());
				if(contacterService.updateContacterOpen(con)){
					successNum++;
					addLog("启用联系人信息，编号："+ id, true);
				}
				else{
					failureNum++;
				}
			}
			else{
				notAllowNum++;
			}
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("snum", successNum);
		map.put("fnum", failureNum);
		map.put("nnum", notAllowNum);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 禁用联系人信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public String closeContacter() throws Exception{
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Contacter contacter = new Contacter();
		contacter.setCloseuserid(sysUser.getUserid());
		contacter.setCloseusername(sysUser.getName());
		contacter.setId(id);
		boolean flag = contacterService.updateContacterClose(contacter);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("backid", id);
		addJSONObject(map);
		return SUCCESS;
	}
	
	public String closeMultiContacter() throws Exception{
		String ids = request.getParameter("id");
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() - 1);
		}
		String[] idArr = ids.split(",");
		int successNum = 0; //禁用成功数
		int failureNum = 0; //禁用失败数
		int notAllowNum = 0; //不允许禁用数
		SysUser sysUser = getSysUser();
		for(int i = 0; i<idArr.length; i++){
			String id = idArr[i];
			Contacter contacter = contacterService.getContacterDetail(id);
			if("1".equals(contacter.getState())){
				Contacter con = new Contacter();
				con.setCloseuserid(sysUser.getUserid());
				con.setCloseusername(sysUser.getName());
				con.setId(contacter.getId());
				if(contacterService.updateContacterClose(con)){
					successNum++;
					addLog("禁用联系人信息，编号："+ id, true);
				}
				else{
					failureNum++;
				}
			}
			else{
				notAllowNum++;
			}
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("snum", successNum);
		map.put("fnum", failureNum);
		map.put("nnum", notAllowNum);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 验证编号有无使用
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 19, 2013
	 */
	public String contacterNoUsed() throws Exception{
		String id = request.getParameter("id");
		Contacter contacter = contacterService.getContacterDetail(id);
		boolean flag = false;
		if(contacter != null){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 获取联系人对应分类列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 22, 2013
	 */
	public String getContacterAndSortList() throws Exception{
		String id = request.getParameter("id");
		List list = contacterService.getContacterAndSortList(id);
		Map map = new HashMap();
		map.put("rows", list);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 通过客户或供应商查询联系人列表
	 * @param type 1为客户2为供应商
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 10, 2013
	 */
	public String getContacterBy() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		List list = contacterService.getContacterListByCustomer(type, id);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 查看原图
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-21
	 */
	public String showContacterOldImgPage()throws Exception{
		String photograph = request.getParameter("photograph");
		request.setAttribute("photograph", photograph);
		return "success";
	}
	
	public IContacterService getContacterService() {
		return contacterService;
	}

	public void setContacterService(IContacterService contacterService) {
		this.contacterService = contacterService;
	}

	public ContacterSort getContacterSort() {
		return contacterSort;
	}

	public void setContacterSort(ContacterSort contacterSort) {
		this.contacterSort = contacterSort;
	}

	public Contacter getContacter() {
		return contacter;
	}

	public void setContacter(Contacter contacter) {
		this.contacter = contacter;
	}

	public ContacterAndSort getContacterAndSort() {
		return contacterAndSort;
	}

	public void setContacterAndSort(ContacterAndSort contacterAndSort) {
		this.contacterAndSort = contacterAndSort;
	}
	
}

