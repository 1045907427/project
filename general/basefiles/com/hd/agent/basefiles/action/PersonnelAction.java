/**
 * @(#)PersonnelAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-29 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.action;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.MenuTree;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.PersonnelBrand;
import com.hd.agent.basefiles.model.PersonnelCustomer;
import com.hd.agent.basefiles.model.Personneledu;
import com.hd.agent.basefiles.model.Personnelpost;
import com.hd.agent.basefiles.model.Personnelworks;
import com.hd.agent.basefiles.model.WorkJob;
import com.hd.agent.basefiles.service.IDepartMentService;
import com.hd.agent.basefiles.service.IGoodsService;
import com.hd.agent.basefiles.service.IPersonnelService;
import com.hd.agent.basefiles.service.ISalesService;
import com.hd.agent.basefiles.service.IWorkJobService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.service.ISysParamService;
import com.hd.agent.system.service.ITaskScheduleService;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class PersonnelAction extends FilesLevelAction{

	private Personnel personnel;
	
	private PersonnelBrand personnelBrand;
	
	private IGoodsService goodsService;
	
	private ISalesService salesService;
	
	private IPersonnelService personnelService;
	
	private List<Personneledu> personnelList; 
	
	private List<Personnelworks> personnelworksList;
	
	private List<Personnelpost> personnelpostsList;
	
	private List<PersonnelCustomer> personnelCustomerList;
	
	private List<PersonnelBrand> personnelBrandList;
	
	private ISysParamService sysParamService;
	
	private ITaskScheduleService taskScheduleService;
	
	public ITaskScheduleService getTaskScheduleService() {
		return taskScheduleService;
	}

	public void setTaskScheduleService(ITaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}

	public ISysParamService getSysParamService() {
		return sysParamService;
	}

	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	public List<PersonnelCustomer> getPersonnelCustomerList() {
		return personnelCustomerList;
	}

	public PersonnelBrand getPersonnelBrand() {
		return personnelBrand;
	}

	public void setPersonnelBrand(PersonnelBrand personnelBrand) {
		this.personnelBrand = personnelBrand;
	}

	public IGoodsService getGoodsService() {
		return goodsService;
	}

	public void setGoodsService(IGoodsService goodsService) {
		this.goodsService = goodsService;
	}

	public void setPersonnelCustomerList(
			List<PersonnelCustomer> personnelCustomerList) {
		this.personnelCustomerList = personnelCustomerList;
	}

	public List<PersonnelBrand> getPersonnelBrandList() {
		return personnelBrandList;
	}

	public void setPersonnelBrandList(List<PersonnelBrand> personnelBrandList) {
		this.personnelBrandList = personnelBrandList;
	}

	public List<Personnelworks> getPersonnelworksList() {
		return personnelworksList;
	}

	public void setPersonnelworksList(List<Personnelworks> personnelworksList) {
		this.personnelworksList = personnelworksList;
	}

	public List<Personnelpost> getPersonnelpostsList() {
		return personnelpostsList;
	}

	public void setPersonnelpostsList(List<Personnelpost> personnelpostsList) {
		this.personnelpostsList = personnelpostsList;
	}

	public List<Personneledu> getPersonnelList() {
		return personnelList;
	}

	public void setPersonnelList(List<Personneledu> personnelList) {
		this.personnelList = personnelList;
	}

	private Personneledu personneledu;
	
	public Personneledu getPersonneledu() {
		return personneledu;
	}

	public void setPersonneledu(Personneledu personneledu) {
		this.personneledu = personneledu;
	}

	private IDepartMentService departMentService;
	
	public IDepartMentService getDepartMentService() {
		return departMentService;
	}

	public void setDepartMentService(IDepartMentService departMentService) {
		this.departMentService = departMentService;
	}

	public IPersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(IPersonnelService personnelService) {
		this.personnelService = personnelService;
	}
	
	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public ISalesService getSalesService() {
		return salesService;
	}

	public void setSalesService(ISalesService salesService) {
		this.salesService = salesService;
	}
	
	/**
	 * 显示人员档案主页
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 22, 2013
	 */
	public String showPersonnelPage()throws Exception{
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String state = request.getParameter("state");
		String belongdeptid = request.getParameter("belongdeptid");
		request.setAttribute("type", type);
		request.setAttribute("id", id);
		request.setAttribute("personid", id);
		request.setAttribute("state", state);
		request.setAttribute("belongdeptid", belongdeptid);
		return SUCCESS;
	}

	/**
	 * 显示人员档案列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-30
	 */
	public String showPersonnelListPage()throws Exception{
		//从数据字典中获取自定义描述
		Map queryMap=new HashMap();
		queryMap.put("tablename","t_base_personnel");
		List<TableColumn> list=getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		Map map = new HashMap();
		for(TableColumn tableColumn : list){
			if(!"".equals(tableColumn.getColumnname()) && !"".equals(tableColumn.getColchnname()) ){
				map.put(tableColumn.getColumnname(),tableColumn.getColchnname());
			}
		}
		SysUser sysUser = getSysUser();
		request.setAttribute("sysUserid", sysUser.getUserid());
		request.setAttribute("fieldmap", map);
		logStr="显示人员档案列表主页";
		return "success";
	}
	
	/**
	 * 根据部门编号显示人员档案列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-30
	 */
	public String showPersonnelList()throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		//将年月转化为年月日
		if(map.containsKey("datesemployed") && null != map.get("datesemployed")){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
			Date date =sdf.parse((String)map.get("datesemployed"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int max = calendar.getMaximum(Calendar.DAY_OF_MONTH);
			map.put("datesemployed1", (String)map.get("datesemployed")+"-01");
			map.put("datesemployed2", (String)map.get("datesemployed")+"-"+String.valueOf(max));
		}
		pageMap.setCondition(map);
		PageData pageData=personnelService.showPersonnelList(pageMap);
		addJSONObject(pageData);
		logStr="根据部门编号显示人员档案列表";
		return "success";
	}
	
	/**
	 * 获取部门树型结构信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-30
	 */
	public String getPersonelDeptTree()throws Exception{
		List<DepartMent> list=departMentService.showDepartmentNameList();
		List treeList=new ArrayList();
		MenuTree tree = new MenuTree();
		SysParam sysParam = sysParamService.getSysParam("COMPANYNAME");
		if(null != sysParam){
			tree.setName(sysParam.getPvalue());
		}
		else{
			tree.setName("系统参数配置错误,请联系管理员!");
		}
		tree.setId("");
		//tree.setName("鸿都百货");
		tree.setDeptmanaguserid("");
		tree.setOpen("true");
		treeList.add(tree);
		for(DepartMent departMent : list){
			MenuTree menuTree = new MenuTree();
			menuTree.setId(departMent.getId());
			menuTree.setpId(departMent.getPid());
			menuTree.setName(departMent.getName());
			menuTree.setState(departMent.getState());
			menuTree.setDeptmanaguserid(departMent.getManageruserid());
			treeList.add(menuTree);
		}
		addJSONArray(treeList);
		return "success";
	}

	/**
	 * 部门档案中“状态”为“启用”且“末级标志”为“是”的部门；
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-31
	 */
	public String showPersonnelDept()throws Exception{
		List list=departMentService.showDeptListByQuery();
		addJSONArray(list);
		return "success";
	}
	
	/**
	 * 显示新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-1
	 */
	public String showPersonnelAddPage()throws Exception{
		String adjunctIds="";
		request.setAttribute("state", "4");
		String idArr = request.getParameter("idArr");
		request.setAttribute("idArr", idArr);
		String belongdeptid = request.getParameter("belongdeptid");
		request.setAttribute("belongdeptid", belongdeptid);
		request.setAttribute("adjunctIds", adjunctIds);
		List codeList = getBaseSysCodeService().showSysCodeListByType("employetype");
		request.setAttribute("codeList", codeList);
		logStr="显示人员新增页面";
		//从数据字典中获取自定义描述
		Map queryMap=new HashMap();
		queryMap.put("tablename","t_base_personnel");
		List<TableColumn> list=getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		Map map = new HashMap();
		for(TableColumn tableColumn : list){
			if(StringUtils.isNotEmpty(tableColumn.getColumnname()) && StringUtils.isNotEmpty(tableColumn.getColchnname())){
				map.put(tableColumn.getColumnname(),tableColumn.getColchnname());
			}
		}
		SysParam sysParam = getBaseSysParamService().getSysParam("NOREPEATEMPLOYE");
		if(null != sysParam){
			request.setAttribute("norepeatemploye", sysParam.getPvalue());
		}
		request.setAttribute("fieldmap", map);
		return "success";
	}
	
	/**
	 * 新增人员
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 24, 2013
	 */
	@UserOperateLog(key="Personnel",type=2,value="")
	public String addPersonnel()throws Exception{
		String type = request.getParameter("type");
		if("save".equals(type)){
			personnel.setState("2");
		}
		else if("hold".equals(type)){
			personnel.setState("3");
		}
		//获取编辑数据 这里获取到的是json字符串
		String eduChange = request.getParameter("eduChange");
		String workChange = request.getParameter("workChange");
		String postChange = request.getParameter("postChange");
		String customerChange = request.getParameter("customerChange");
		String brandChange = request.getParameter("brandChange");
		if(eduChange != null){
		    //把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(eduChange);
		    personnelList = JSONArray.toList(json, Personneledu.class);
		    //TODO 下面就可以根据转换后的对象进行相应的操作了
		}
		if(workChange != null){
		    //把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(workChange);
		    personnelworksList = JSONArray.toList(json, Personnelworks.class);
		}
		if(postChange != null){
		    //把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(postChange);
		    personnelpostsList = JSONArray.toList(json, Personnelpost.class);
		}
		if(customerChange != null){
		    //把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(customerChange);
			personnelCustomerList = JSONArray.toList(json, PersonnelCustomer.class);
		}
		if(brandChange != null){
		    //把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(brandChange);
			personnelBrandList = JSONArray.toList(json, PersonnelBrand.class);
		}
		SysUser sysUserInfo = getSysUser();
		personnel.setAdddept(sysUserInfo.getDepartmentname());
		personnel.setAdddeptid(sysUserInfo.getDepartmentid());
		personnel.setAdduserid(sysUserInfo.getUserid());
		personnel.setAdduser(sysUserInfo.getUsername());
		Map map = personnelService.addPersonnelInfo(personnel,personnelList,
					personnelworksList,personnelpostsList,personnelCustomerList,personnelBrandList);
		addJSONObject(map);
		addLog("保存新增人员 编号:"+personnel.getId(),map.get("flag").equals(true));
		return SUCCESS;
	}
	
	/**
	 * 人员编号是否存在
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public String isExistPersonnelId()throws Exception{
		String id=request.getParameter("id");
		boolean flag=personnelService.isExistPersonnelId(id);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 显示人员信息详情
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-5
	 */
	public String showPersonnelInfoPage()throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Personnel personnel=personnelService.showPersonnelInfo(id);
		if(null != personnel){
			//获取t_base_personnel表的字段编辑权限
			Map colMap = getAccessColumn("t_base_personnel");
			request.setAttribute("showMap", colMap);
			Map map = retBackFieldMap(personnel);
			request.setAttribute("type", type);
			request.setAttribute("fieldmap", map);
			request.setAttribute("personnel", personnel);
			List codeList = getBaseSysCodeService().showSysCodeListByType("employetype");
			request.setAttribute("codeList", codeList);
			SysParam sysParam = getBaseSysParamService().getSysParam("NOREPEATEMPLOYE");
			if(null != sysParam){
				request.setAttribute("norepeatemploye", sysParam.getPvalue());
			}
		}
		return "success";
	}
	
	/**
	 * 返回自定义信息描述名
	 * @param personnel
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 24, 2013
	 */
	public Map retBackFieldMap(Personnel personnel)throws Exception{
		//从数据字典中获取自定义描述
		Map queryMap=new HashMap();
		queryMap.put("tablename","t_base_personnel");
		List<TableColumn> list=getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		Map map = new HashMap();
		if(null != list && list.size() != 0){
			for(TableColumn tableColumn : list){
				if(StringUtils.isNotEmpty(tableColumn.getColumnname()) && StringUtils.isNotEmpty(tableColumn.getColchnname())){
					map.put(tableColumn.getColumnname(),tableColumn.getColchnname());
				}
			}
			if(StringUtils.isNotEmpty(personnel.getBirthday())){
				personnel.setBirthday(birthdayFormater(personnel.getBirthday().toString()));
			}
			if(StringUtils.isNotEmpty(personnel.getDatesemployed())){
				personnel.setDatesemployed(birthdayFormater(personnel.getDatesemployed().toString()));
			}
		}
		return map;
	}
	
	/**
	 * 显示人员编辑页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-5
	 */
	public String showPersonnelEditPage()throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Personnel personnel=personnelService.showPersonnelInfo(id);
		if(null != personnel){
			//获取t_base_personnel表的字段编辑权限
			Map mapCols =  getBaseFilesEditWithAuthority("t_base_personnel", personnel);
			Map map = retBackFieldMap(personnel);
			request.setAttribute("fieldmap", map);
			//显示修改页面时，给数据加锁,true:可以修改，false:不可以修改 
			boolean flag = lockData("t_base_personnel", id);
			if(flag){
				request.setAttribute("editMap", mapCols);
				if(StringUtils.isNotEmpty(personnel.getBirthday())){
					request.setAttribute("birthday", birthdayFormater(personnel.getBirthday().toString()));
				}
				if(StringUtils.isNotEmpty(personnel.getDatesemployed())){
					request.setAttribute("dateSemployed", birthdayFormater(personnel.getDatesemployed().toString()));
				}
			}
			else{
				request.setAttribute("editMap", null);
			}
			request.setAttribute("oldId", id);
			request.setAttribute("type", type);
			request.setAttribute("personnel", personnel);
			List codeList = getBaseSysCodeService().showSysCodeListByType("employetype");
			request.setAttribute("codeList", codeList);
			SysParam sysParam = getBaseSysParamService().getSysParam("NOREPEATEMPLOYE");
			if(null != sysParam){
				request.setAttribute("norepeatemploye", sysParam.getPvalue());
			}
		}
		return "success";
	}
	/**
	 * 显示复制人员信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-22
	 */
	public String showPersonnelCopyPage()throws Exception{
		String id = request.getParameter("id");
		Personnel personnel=personnelService.showPersonnelInfo(id);
		if(null != personnel){
			Map map = retBackFieldMap(personnel);
			request.setAttribute("fieldmap", map);
			if(null != personnel.getBirthday() && !"".equals(personnel.getBirthday())){
				request.setAttribute("birthday", birthdayFormater(personnel.getBirthday().toString()));
			}
			if(null != personnel.getDatesemployed() && !"".equals(personnel.getDatesemployed())){
				request.setAttribute("dateSemployed", birthdayFormater(personnel.getDatesemployed().toString()));
			}
			List codeList = getBaseSysCodeService().showSysCodeListByType("employetype");
			request.setAttribute("codeList", codeList);
			request.setAttribute("fieldmap", map);
			request.setAttribute("personnel", personnel);
			SysParam sysParam = getBaseSysParamService().getSysParam("NOREPEATEMPLOYE");
			if(null != sysParam){
				request.setAttribute("norepeatemploye", sysParam.getPvalue());
			}
		}
		return "success";
	}
	
	/**
	 * 修改人员
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 24, 2013
	 */
	@UserOperateLog(key="Personnel",type=3,value="")
	public String editPersonnel()throws Exception{
		String type = request.getParameter("type");
		if(!"1".equals(personnel.getState()) && !"0".equals(personnel.getState())){
			if("save".equals(type)){
				personnel.setState("2");
			}
			else if("hold".equals(type)){
				personnel.setState("3");
			}
		}
		//获取编辑数据 这里获取到的是json字符串
		String eduChange = request.getParameter("eduChange");
		String workChange = request.getParameter("workChange");
		String postChange = request.getParameter("postChange");
		String customerChange = request.getParameter("customerChange");
		String brandChange = request.getParameter("brandChange");
		if(eduChange != null){
		    //把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(eduChange);
		    personnelList = JSONArray.toList(json, Personneledu.class);
		}
		if(workChange != null){
		    //把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(workChange);
		    personnelworksList = JSONArray.toList(json, Personnelworks.class);
		}
		if(postChange != null){
		    //把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(postChange);
		    personnelpostsList = JSONArray.toList(json, Personnelpost.class);
		}
		if(customerChange != null){
			//把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(customerChange);
		    personnelCustomerList = JSONArray.toList(json, PersonnelCustomer.class);
		}
		if(brandChange != null){
		    //把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(brandChange);
		    personnelBrandList = JSONArray.toList(json, PersonnelBrand.class);
		}
		Map map = personnelService.editPersonnelInfo(personnel,personnelList,personnelworksList,
				personnelpostsList,personnelCustomerList,personnelBrandList);
		//添加日志内容
		if(map.containsKey("flag")){
			String msg = "修改人员 编号:"+personnel.getId();
			String cstbrdmsg = (String)map.get("cstbrdmsg");
			if(StringUtils.isNotEmpty(cstbrdmsg)){
				msg = msg + "，"+cstbrdmsg;
			}

//			List<String> customeridList = new ArrayList<String>();
//			for(PersonnelCustomer personnelCustomer : personnelCustomerList) {
//				customeridList.add(personnelCustomer.getCustomerid());
//			}
//
//			if(customeridList.size() > 0) {
//				msg = msg + " 对应客户：" + StringUtils.join(customeridList.toArray(), ",");
//			} else {
//				msg = msg + " 对应客户：无";
//			}

			addLog(msg,map.get("flag").equals(true));
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 删除人员信息(批量),删除前检测是否被引用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-17
	 */
	@UserOperateLog(key="Personnel",type=4,value="")
	public String deletePersonnelInfo()throws Exception{
		String perIdStr=request.getParameter("ids");
		String newPerIdStr = "",msg = "",usepsnid = "",lockpsnid = "";
		int userNum = 0,lockNum = 0;
		Map retMap = new HashMap();
		String[] perIdArr = perIdStr.split(",");
		//检测要删除人员的状态，选中记录判断是否被引用
		for(int i=0;i<perIdArr.length;i++){
			Personnel personnel = personnelService.showPersonnelInfo(perIdArr[i]);
			if(personnel != null){
				//判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
				if(!isLock("t_base_personnel", perIdArr[i])){
					//判断是否被引用
					if(!canTableDataDelete("t_base_personnel",perIdArr[i])){//true可以操作，false不可以操作
						userNum++;
						if(StringUtils.isEmpty(usepsnid)){
							usepsnid = perIdArr[i];
						}else{
							usepsnid += "," + perIdArr[i];
						}
					}
					else{
						newPerIdStr += perIdArr[i] + ",";
					}
				}
				else{
					lockNum++;
					if(StringUtils.isEmpty(lockpsnid)){
						lockpsnid = perIdArr[i];
					}else{
						lockpsnid += "," + perIdArr[i];
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(usepsnid)){
			msg = "人员编号："+usepsnid+"被引用,不允许删除；";
		}
		if(StringUtils.isNotEmpty(lockpsnid)){
			if(StringUtils.isEmpty(msg)){
				msg = "人员编号："+lockpsnid+"网络互斥,不允许删除；";
			}else{
				msg += "<br>" + "人员编号："+lockpsnid+"网络互斥,不允许删除；";
			}
		}
		if(StringUtils.isNotEmpty(newPerIdStr)){
			Map map=personnelService.deletePersonnel(newPerIdStr.split(","));
			retMap.putAll(map);
			if(StringUtils.isEmpty(msg)){
				msg = (String)map.get("msg");
			}else{
				msg += "<br>" + (String)map.get("msg");
			}
		}
		retMap.put("msg", msg);
		retMap.put("userNum", userNum);
		retMap.put("lockNum", lockNum);
		addJSONObject(retMap);
		//添加日志内容
		addLog(msg,true);
		return "success";
	}
	
	/**
	 * 启用人员(批量)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-18
	 */
	@UserOperateLog(key="Personnel",type=0,value="启用人员")
	public String enablePersonnels()throws Exception{
		String perIdArr=request.getParameter("ids");
		SysUser sysUser=getSysUser();
		String openuserid = sysUser.getUserid().toString();
		Map retMap=personnelService.enablePersonnels(perIdArr,openuserid);
		boolean flag = false;
		if(retMap.get("retFlag").equals(true)){
			flag = true;
		}
		//添加日志内容
		addLog("启用人员 编号:"+perIdArr,flag);
		addJSONObject(retMap);
		return "success";
	}
	
	/**
	 * 禁用人员(批量)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-18
	 */
	@UserOperateLog(key="Personnel",type=0,value="")
	public String disablePersonnels()throws Exception{
		String perIdStr=request.getParameter("ids");
		String type = request.getParameter("type");
		SysUser sysUser=getSysUser();
		String closeuserid = sysUser.getUserid().toString();
		String[] perIdArr = perIdStr.split(",");
		String newPerIdStr = "",userMes ="";
		int isUser = 0;
		for(int i=0;i<perIdArr.length;i++){
			//判断数据是否被加锁,True已被加锁。Fasle未加锁。
			if(!isLock("t_base_personnel", perIdArr[i])){
				Personnel personnelInfo = getPersonnelInfoById(perIdArr[i]);
				if(personnelInfo != null){
					if("1".equals(personnelInfo.getState())){//状态不为启用时，不允许禁用操作
						newPerIdStr = perIdArr[i] + "," + newPerIdStr;
					}
				}
				else{//不存在该人员
					throw new Exception("不存在该人员!");
				}
			}
			else{
				isUser += 1;
			}
		}
		if(isUser > 0){
			userMes = "人员存在"+isUser+"条记录被加锁,暂时不能完全禁用选中记录;";
		}
		Map retMap = new HashMap();
		retMap=personnelService.disablePersonnels(newPerIdStr,closeuserid,type);
		retMap.put("userMes", userMes);
		//添加日志内容
		if(StringUtils.isNotEmpty(newPerIdStr)){
			addLog("禁用人员 编号:"+newPerIdStr.substring(0, newPerIdStr.lastIndexOf(",")),retMap.get("retFlag").equals(true));
		}else{
			addLog("禁用人员 编号:"+perIdStr,false);
		}
		addJSONObject(retMap);
		return "success";
	}
	
	/*------------教育经历--------------*/
	
	/**
	 * 根据人员编号获取教育经历列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public String showPersEducationList()throws Exception{
		String personid=request.getParameter("personid");
		if(personid.indexOf("X") != -1){
			personid = personid.replace("X", "#");
		}
		List EduList=personnelService.showEducationList(personid);
		addJSONArray(EduList);
		return "success";
	}
	
	/**
	 * 删除教育经历 or 批量
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-7
	 */
	public String deleteEdus()throws Exception{
		String idArr=request.getParameter("ids");
		if(idArr.indexOf("X") != -1){
			idArr = idArr.replace("X", "#");
		}
		boolean flag=personnelService.deleteEdus(idArr);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/*-----------------工作经历----------------------*/
	/**
	 * 根据人员编号获取工作经历列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-7
	 */
	public String showPersWorksList()throws Exception{
		String personid=request.getParameter("personid");
		if(personid.indexOf("X") != -1){
			personid = personid.replace("X", "#");
		}
		List worksList = personnelService.showWorksList(personid);
		addJSONArray(worksList);
		return "success";
	}
	
	/**
	 * 删除工作经历 or 批量
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-7
	 */
	public String deleteWorks()throws Exception{
		String idArr=request.getParameter("ids");
		if(idArr.indexOf("X") != -1){
			idArr = idArr.replace("X", "#");
		}
		boolean flag=personnelService.deleteWorks(idArr);
		addJSONObject("flag", flag);
		return "success";
	}
	/*-----------------岗位变动记录----------------------*/
	/**
	 * 根据人员编号获取岗位变动记录列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-7
	 */
	public String showPersPostsList()throws Exception{
		String personid=request.getParameter("personid");
		if(personid.indexOf("X") != -1){
			personid = personid.replace("X", "#");
		}
		List<Personnelpost> postsList = personnelService.showPostsList(personid);
		addJSONArray(postsList);
		return "success";
	}
	
	/**
	 * 删除岗位变动记录 or 批量
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-7
	 */
	public String deletePosts()throws Exception{
		String idArr=request.getParameter("ids");
		if(idArr.indexOf("X") != -1){
			idArr = idArr.replace("X", "#");
		}
		boolean flag=personnelService.deletePosts(idArr);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 根据籍贯编号id获取籍贯名称
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-28
	 */
	public String getNPname()throws Exception{
		String id = request.getParameter("id");
		Map mapNPname = new HashMap();
		if(id != null){
			String name = personnelService.getNPname(id);
			if(name != null){
				mapNPname.put("name", name);
			}
		}
		else{
			mapNPname.put("name", null);
		}
		addJSONObject(mapNPname);
		return "success";
	}
	
	/**
	 * 显示导入窗口页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-20
	 */
	public String importExcelPage() throws Exception{
		return "success";
	}
	
	/**
	 * 查看原图
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-21
	 */
	public String showPersonnelOldImgPage()throws Exception{
		String photograph = request.getParameter("photograph");
		request.setAttribute("photograph", photograph);
		return "success";
	}
	
	/**
	 * 显示分配系统用户页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-21
	 */
	public String showAllotSysUserPage()throws Exception{
		request.setAttribute("PerIDs", request.getParameter("PerIDs"));
		return "success";
	}
	
	/**
	 * 出生日期格式化
	 * @param birthday
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-27
	 */
	public String birthdayFormater(String birthday)throws Exception{
		if(birthday.indexOf(".") != -1){//格式中是否存在“.”，存在，则替换成“-”
			birthday = birthday.replace(".","-");
		}
		else if(birthday.indexOf("/") != -1){
			birthday = birthday.replace("/","-");
		}
		return birthday;
	}
	
	/**
	 * 根据人员编号获取对应客户列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 20, 2013
	 */
	public String showCustomerList()throws Exception{
		Map condition = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(condition);
		//String personid=request.getParameter("personid");
		PageData pageData = personnelService.showCustomerList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 根据人员编码获取对应品牌列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 20, 2013
	 */
	public String showBrandList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = personnelService.showBrandList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示对应品牌新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 22, 2013
	 */
	public String showBrandToGoodsPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示对应品牌修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 22, 2013
	 */
	public String showBrandToGoodsEditPage()throws Exception{
		String brandid = request.getParameter("brandid");
		String index = request.getParameter("index");
		String remark = request.getParameter("remark");
		request.setAttribute("remark", remark);
		request.setAttribute("index", index);
		request.setAttribute("brandid", brandid);
		return SUCCESS;
	}
	
	/**
	 * 显示对应客户新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 25, 2013
	 */
	public String showCustomerToGoodsAddPage()throws Exception{
		String personid = request.getParameter("personid");
		String type = request.getParameter("type");
		String deptid = request.getParameter("deptid");//获取商品品牌数据用
		String customerids = "",brandids = "";
		List<PersonnelCustomer> clist = personnelService.showCustomerList(personid);
		if(null != clist && clist.size() != 0){
			for(PersonnelCustomer personnelCustomer : clist){
				customerids += personnelCustomer.getCustomerid() + ",";
				personnelCustomer.setId(null);
			}
		}
		if(StringUtils.isNotEmpty(deptid)){
			List<Brand> bList = getBaseGoodsService().getBrandListWithParentByDeptid(deptid);
			if(bList.size() != 0){
				for(Brand brand : bList){
					if(StringUtils.isNotEmpty(brandids)){
						brandids += "," + brand.getId();
					}else{
						brandids = brand.getId();
					}
				}
			}
		}
		request.setAttribute("customerids", customerids);
		request.setAttribute("brandids", brandids);
		request.setAttribute("personid", personid);
		request.setAttribute("type", type);
		JSONArray json=JSONArray.fromObject(clist);
		String jsonstr = json.toString();
		request.setAttribute("mlist", jsonstr);
		return SUCCESS;
	}
	
	/**
	 * 显示对应客户修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 25, 2013
	 */
	public String showCustomerToGoodsEditPage()throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 获取虚拟对应品牌列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 22, 2013
	 */
	public String showBrandListData()throws Exception{
		String brandid = request.getParameter("brandid");
		String brandrow = request.getParameter("brandrow");
		List<Brand> dataList = new ArrayList<Brand>();
		List<PersonnelBrand> dataList2 = new ArrayList<PersonnelBrand>();
		List<PersonnelBrand> brandList = new ArrayList<PersonnelBrand>();
		if(StringUtils.isNotEmpty(brandid)){
			JSONArray json = JSONArray.fromObject(brandid);
			dataList = JSONArray.toList(json, Brand.class);
			for(Brand brand : dataList){
				PersonnelBrand personnelBrand = new PersonnelBrand();
				personnelBrand.setBrandid(brand.getId());
				personnelBrand.setBrandname(brand.getName());
				brandList.add(personnelBrand);
			}
		}
		JSONArray json2 = JSONArray.fromObject(brandrow);
		dataList2 = JSONArray.toList(json2, PersonnelBrand.class);
		if(dataList2.size() != 0){
			brandList.addAll(dataList2);
		}
		addJSONArray(brandList);
		return SUCCESS;
	}
	
	/**
	 * 获取虚拟对应客户列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 25, 2013
	 */
	public String showCustomerListData()throws Exception{
		String customerids = request.getParameter("customerids");
		String personid = request.getParameter("personid");
		String delcustomerids = request.getParameter("delcustomerids");
		if(StringUtils.isNotEmpty(delcustomerids)){
			customerids = customerids.replace(delcustomerids+",", "");
		}
		List<Customer> dataList = new ArrayList<Customer>();
		List<PersonnelCustomer> customerList = personnelService.getPersonCustomerListByCustomerids(customerids);
		int seq = 1;
		for(PersonnelCustomer pCustomer : customerList){
			pCustomer.setSeq(seq);
			seq++;
		}
		addJSONArray(customerList);
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 24, 2013
	 */
	public String getCustomerListForPsnCstm()throws  Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = personnelService.getCustomerListForPsnCstm(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 根据部门编码获取人员所有列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public String getPersonnelListByDeptid()throws Exception{
		String deptid = request.getParameter("deptid");
		List list = personnelService.getPersonnelListByDeptid(deptid);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 根据人员编码获取对应客户列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public String getCustomerListFromPsnBrandAndCustomer()throws Exception{
		Map condition = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(condition);
		PageData pageData = personnelService.getCustomerListFromPsnBrandAndCustomer(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 根据人员编码获取对应客户列表数据(t_base_personnel_customer)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 8, 2014
	 */
	public String getPersonCustomerListPageData()throws Exception {
		Map condition = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(condition);
		PageData pageData = personnelService.getPersonCustomerList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 根据人员编码获取对应品牌列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public String getBrandListFromPsnBrandAndCustomer()throws Exception{
		Map condition = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(condition);
		PageData pageData = personnelService.getBrandListFromPsnBrandAndCustomer(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 根据客户验证对应品牌和客户是否重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 25, 2013
	 */
	public String checkBrandAndCustomerRepeat()throws Exception{
		String customerid = request.getParameter("customerid");
		String brandidStr = request.getParameter("brandidStr");
		String employetype = request.getParameter("employetype");
		boolean flag = personnelService.checkBrandAndCustomerRepeat(customerid, brandidStr,employetype);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 根据品牌验证对应品牌和客户是否重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 25, 2013
	 */
	public String checkBrandAndCustomerRepeat2()throws Exception{
		String brandid = request.getParameter("brandid");
		String customeridStr = request.getParameter("customeridStr");
		String employetype = request.getParameter("employetype");
		boolean flag = personnelService.checkBrandAndCustomerRepeat2(brandid, customeridStr,employetype);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 根据对应客户编码删除人员对应客户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 8, 2014
	 */
	@UserOperateLog(key="Personnel",type=4)
	public String deletePersonCustomer()throws Exception{
		String personid = request.getParameter("personid");
		String customerids = request.getParameter("customerids");
		Personnel personnel = personnelService.showPersonnelInfo(personid);
		String employetype = "";
		if(null != personnel){
			employetype = personnel.getEmployetype();
		}
		boolean flag = personnelService.deletePersonCustomer(customerids,personid,employetype);
		if(flag && StringUtils.isNotEmpty(customerids)){
			addDeletePersonCustomerTaskSchedule(personid,customerids,employetype);
		}
		addLog("人员 编号:"+personid+"，删除对应客户 编码："+customerids,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 根据对应客户编码删除人员对应客户
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 8, 2014
	 */
	@UserOperateLog(key="Personnel",type=4)
	public String addPersonCustomer()throws Exception{
		String personid = request.getParameter("personid");
		String customerids = request.getParameter("customerids");
		Personnel personnel = personnelService.showPersonnelInfo(personid);
		String employetype = "";
		if(null != personnel){
			employetype = personnel.getEmployetype();
		}
		boolean flag = personnelService.addPersonCustomer(customerids,personid,employetype);
		addLog("人员 编号:"+personid+"，添加对应客户 编码："+customerids,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/**
	 * 新增人员对应客户删除时单据品牌业务员清空
	 * @param personid
	 * @param customerids
	 * @param employetype
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 20, 2015
	 */
	private void addDeletePersonCustomerTaskSchedule(String personid,String customerids,String employetype)throws Exception{
		String[] customeridArr = customerids.split(",");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map map1 = new HashMap();
		map1.put("type","delcustomer");
		map1.put("customeridArr",customeridArr);
		if(StringUtils.isNotEmpty(employetype)){
			if(employetype.indexOf("3") != -1){
				List<PersonnelBrand> brandList = personnelService.showBrandList(personid);
				if(brandList.size() != 0){
					map1.put("employetype","3");
					map1.put("brandList",brandList);
					personnelService.deleteNoPersonBrandAndCustomer(map1);
				}
				for(String customerid : customeridArr){
					for(PersonnelBrand personnelBrand : brandList){
						Map map = new HashMap();
						map.put("delpersonid", personid);
						map.put("customerid", customerid);
						map.put("employetype", "3");
						map.put("brandid", personnelBrand.getBrandid());

						list.add(map);
					}
				}
			}else if(employetype.indexOf("7") != -1){
				List<PersonnelBrand> supplierBrandList = personnelService.getSupplierBrandListNoPage(personid);
				if(supplierBrandList.size() != 0){
					map1.put("employetype","7");
					map1.put("brandList",supplierBrandList);
					personnelService.deleteNoPersonBrandAndCustomer(map1);
				}
				for(String customerid : customeridArr){
					for(PersonnelBrand personnelBrand : supplierBrandList){
						Map map = new HashMap();
						map.put("delpersonid", personid);
						map.put("customerid", customerid);
						map.put("employetype", "7");
						map.put("brandid", personnelBrand.getBrandid());
						list.add(map);
					}
				}
			}
			if(list.size() != 0){
				boolean flag = personnelService.addNoPersonBrandAndCustomer(list);
			}
		}
	}
	
	/**
	 * 根据对应品牌编码删除人员对应品牌
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 5, 2014
	 */
	@UserOperateLog(key="Personnel",type=4)
	public String deletePersonBrand()throws Exception{
		String personid = request.getParameter("personid");
		String delbrandids = request.getParameter("delbrandids");
		boolean flag = personnelService.deletePersonBrand(delbrandids,personid);
		if(flag){
			Personnel personnel = personnelService.showPersonnelInfo(personid);
			if(null != personnel && StringUtils.isNotEmpty(delbrandids)){
				addDeletePersonBrandTaskSchedule(personid, delbrandids, personnel.getEmployetype());
			}
		}
		addLog("人员 编号:"+personid+"，删除对应品牌 编码："+delbrandids,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 根据对应品牌编码添加人员对应品牌
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Nov 5, 2014
	 */
	@UserOperateLog(key="Personnel",type=4)
	public String addPersonBrand()throws Exception{
		String personid = request.getParameter("personid");
		String brandids = request.getParameter("brandids");
		boolean flag = personnelService.addPersonBrand(brandids,personid);
		addLog("人员 编号:"+personid+"，添加对应品牌 编码："+brandids,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/**
	 * 新增人员对应品牌删除时单据品牌业务员清空
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 20, 2015
	 */
	private void addDeletePersonBrandTaskSchedule(String personid,String delbrandids,String employetype)throws Exception{
		String[] brandidArr = delbrandids.split(",");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map map1 = new HashMap();
		map1.put("type","delbrand");
		map1.put("brandidArr",brandidArr);
		if(StringUtils.isNotEmpty(employetype)){
			if(employetype.indexOf("3") != -1){
				List<PersonnelCustomer> customerList = personnelService.showCustomerList(personid);
				if(customerList.size() != 0){
					map1.put("employetype","3");
					map1.put("customerList",customerList);
					personnelService.deleteNoPersonBrandAndCustomer(map1);
				}
				for(String brandid : brandidArr){
					for(PersonnelCustomer personCustomer : customerList){
						Map map = new HashMap();
						map.put("delpersonid", personid);
						map.put("customerid", personCustomer.getCustomerid());
						map.put("employetype", "3");
						map.put("brandid", brandid);
						list.add(map);
					}
				}
			}else if(employetype.indexOf("7") != -1){
				List<PersonnelCustomer> customerList = personnelService.getSupplierCustomerListNoPage(personid);
				if(customerList.size() != 0){
					map1.put("employetype","7");
					map1.put("customerList",customerList);
					personnelService.deleteNoPersonBrandAndCustomer(map1);
				}
				for(String brandid : brandidArr){
					for(PersonnelCustomer personCustomer : customerList){
						Map map = new HashMap();
						map.put("delpersonid", personid);
						map.put("customerid", personCustomer.getCustomerid());
						map.put("employetype", "7");
						map.put("brandid", brandid);
						list.add(map);
					}
				}
			}
			if(list.size() != 0){
				boolean flag = personnelService.addNoPersonBrandAndCustomer(list);
			}
		}
	}
	
	/**
	 * 根据所属部门编码获取商品品牌档案中的品牌列表数据（包含父级）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 9, 2014
	 */
	public String getPersonBrandListByDeptid()throws Exception{
		String deptid = request.getParameter("deptid");
		List<PersonnelBrand> retList = new ArrayList<PersonnelBrand>();
		if(StringUtils.isNotEmpty(deptid)){
			List<Brand> list = getBaseGoodsService().getBrandListWithParentByDeptid(deptid);
			if(list.size() != 0){
				for(Brand brand : list){
					PersonnelBrand pBrand = new PersonnelBrand();
					pBrand.setBrandid(brand.getId());
					pBrand.setBrandname(brand.getName());
					retList.add(pBrand);
				}
				addJSONArray(retList);
			}
		}else{
			addJSONArray(retList);
		}
		return SUCCESS;
	}
	
	/**
	 * 新增复制页面添加新的对应客户数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 9, 2014
	 */
	public String addPersonCustomerList()throws Exception{
		String customerids = request.getParameter("customerids");
		List<PersonnelCustomer> list = personnelService.getPersonCustomerListByCustomerids(customerids);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 根据品牌编码字符串集合获取对应品牌列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 4, 2014
	 */
	public String getBrandToPersonList()throws Exception{
		String brandids = request.getParameter("brandids");
		String personid = request.getParameter("personid");
		List list = personnelService.getBrandToPersonList(brandids,personid);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 获取厂家业务员对应客户列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public String getPersonSupplierCustomerListPageData()throws Exception{
		Map condition = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(condition);
		PageData pageData = personnelService.getPersonSupplierCustomerList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 获取厂家业务员对应品牌列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public String showSupplierBrandList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = personnelService.showSupplierBrandList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 根据人员编码判断不同业务属性是否存在对应客户、对应品牌
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 8, 2014
	 */
	public String checkEmployetypeOfCustomerData()throws Exception{
		String personid = request.getParameter("personid");
		String employetype = request.getParameter("employetype");
		boolean flag = personnelService.checkEmployetypeOfCustomerData(personid,employetype);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 导入人员档案对应客户
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2014
	 */
	public String importPersonBrandCustomerData()throws Exception{
		String id = request.getParameter("id");
		String customerids = request.getParameter("customerids");
		String brandids = request.getParameter("brandids");
		String employetype = request.getParameter("employetype");
		String personid = request.getParameter("personid");
		List<PersonnelCustomer> result = new ArrayList<PersonnelCustomer>();
		List<PersonnelBrand> brandList = new ArrayList<PersonnelBrand>();
		if(StringUtils.isNotEmpty(customerids)){
			result = personnelService.getPersonCustomerListByCustomerids(customerids);
		}else{
			customerids = "";
		}
		if(StringUtils.isEmpty(brandids)){
			brandids = "";
		}
		Class entity = Class.forName("com.hd.agent.basefiles.model.PersonnelCustomer");
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("人员编号".equals(str)){
				paramList2.add("personid");
			}else if("品牌编码".equals(str)){
				paramList2.add("brandid");
			}else if("客户编码".equals(str)){
				paramList2.add("customerid");
			}else if("助记符".equals(str)){
				paramList2.add("shortcode");
			}else{
				paramList2.add("null");
			}
		}
		
		Map<String,Object> retMap = new HashMap<String,Object>();
		List<PersonnelCustomer> result2 = new ArrayList<PersonnelCustomer>();
		List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
		if(list.size() != 0){
			Map detialMap = new HashMap();
			for(Map<String, Object> map4 : list){
				Object object = entity.newInstance();
				Field[] fields = entity.getDeclaredFields();
				//获取的导入数据格式转换
				DRCastTo(map4,fields);
				//BeanUtils.populate(object, map4);
				PropertyUtils.copyProperties(object, map4);
				PersonnelCustomer personnelCustomer = (PersonnelCustomer)object;
				result2.add(personnelCustomer);
			}
		}
		
		String drcustomerids = "",repeatcustomerids = "",unexistcustoemrids = "";
		for(PersonnelCustomer personnelCustomer :result2){
			Customer customer = null;
			if(StringUtils.isNotEmpty(personnelCustomer.getCustomerid())){
				customer = getBaseSalesService().getOnlyCustomer(personnelCustomer.getCustomerid());
			}else if(StringUtils.isNotEmpty(personnelCustomer.getShortcode())){
				customer = getBaseSalesService().getCustomerInfoLimitOneByShortcode(personnelCustomer.getShortcode());
			}
			
			if(null != customer){
				boolean flag = personnelService.checkBrandAndCustomerRepeat(customer.getId(), brandids, employetype);
				if(!flag){
					if(StringUtils.isEmpty(repeatcustomerids)){
						repeatcustomerids = customer.getId();
					}else{
						repeatcustomerids += "," + customer.getId();
					}
				}else{
					personnelCustomer.setCustomername(customer.getName());
					drcustomerids += customer.getId() + ",";
					result.add(personnelCustomer);
				}
			}else{
				if(StringUtils.isEmpty(unexistcustoemrids)){
					unexistcustoemrids = personnelCustomer.getCustomerid();
				}else{
					unexistcustoemrids += "," + personnelCustomer.getCustomerid();
				}
			}
		}
		boolean flag = personnelService.addPersonCustomer(drcustomerids,personid,employetype);

		String msg = "";
		if(StringUtils.isNotEmpty(repeatcustomerids)){
			msg = "该人员已存在该对应客户："+repeatcustomerids+"，对应品牌;";
		}
		if(StringUtils.isNotEmpty(unexistcustoemrids)){
			if(StringUtils.isEmpty(msg)){
				msg = "系统不存在客户："+unexistcustoemrids+";";
			}else{
				msg += "<br>" + "系统不存在客户："+unexistcustoemrids+";";
			}
		}
		Map map = new HashMap();
		map.put("customerids", customerids+drcustomerids);
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("list", result);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 删除人员档案附件
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2015年6月5日
	 */
	public String deletePersonFiles()throws Exception{
		String personid = request.getParameter("personid");
		String fileid = request.getParameter("fileid");
		boolean flag = personnelService.deletePersonFiles(personid,fileid);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 添加人员档案附件
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2015年6月5日
	 */
	public String addPersonFiles()throws Exception{
		String personid = request.getParameter("personid");
		String fileid = request.getParameter("fileid");
		boolean flag = personnelService.addPersonFiles(personid,fileid);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
}

