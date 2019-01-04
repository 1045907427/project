/**
 * @(#)PersonnelServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-29 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import com.hd.agent.accesscontrol.dao.SysUserMapper;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.*;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.IPersonnelService;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class PersonnelServiceImpl extends FilesLevelServiceImpl implements IPersonnelService{
	
	private PersonnelMapper personnelMapper;
	
	private SysUserMapper sysUserMapper;
	
	private GoodsMapper goodsMapper;
	
	private CustomerMapper customerMapper;
	
	private ContacterMapper contacterMapper;
	
	private CustomerSortMapper customerSortMapper;
	
	private SalesAreaMapper salesAreaMapper;
	
	public ContacterMapper getContacterMapper() {
		return contacterMapper;
	}

	public void setContacterMapper(ContacterMapper contacterMapper) {
		this.contacterMapper = contacterMapper;
	}

	public CustomerMapper getCustomerMapper() {
		return customerMapper;
	}

	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	public GoodsMapper getGoodsMapper() {
		return goodsMapper;
	}

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	public SysUserMapper getSysUserMapper() {
		return sysUserMapper;
	}

	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	private Personnel personnel;
	
	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	private DepartMentMapper departMentMapper;
	
	public DepartMentMapper getDepartMentMapper() {
		return departMentMapper;
	}

	public void setDepartMentMapper(DepartMentMapper departMentMapper) {
		this.departMentMapper = departMentMapper;
	}

	public PersonnelMapper getPersonnelMapper() {
		return personnelMapper;
	}

	public void setPersonnelMapper(PersonnelMapper personnelMapper) {
		this.personnelMapper = personnelMapper;
	}
	
	/**
	 * 根据部门编号显示人员档案列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-29
	 */
	public PageData showPersonnelList(PageMap pageMap)throws Exception{
		String belangdeptName = "";
		String cols=getAccessColumnList("t_base_personnel", null);//查看单表取字段权限
		pageMap.setCols(cols);
		//数据权限
		String sql = getDataAccessRule("t_base_personnel",null);
		pageMap.setDataSql(sql);
		
		List<Personnel> list=personnelMapper.getPersonnelList(pageMap);
		for(Personnel personnel: list){
			if("root".equals(personnel.getBelongdeptid())){
				belangdeptName = null;
			}
			else{
				belangdeptName = departMentMapper.getDeptName(personnel.getBelongdeptid());
			}
			personnel.setBelongdept(belangdeptName);//所属部门 
			DepartMent departMent = getDepartmentByDeptid(personnel.getAdddeptid());
			if(null != departMent){
				personnel.setAdddept(departMent.getName());//新建档案部门
			}
			SysUser addSysUser = getSysUserById(personnel.getAdduserid());
			if(null != addSysUser){
				personnel.setAdduser(addSysUser.getName());
			}
			SysUser closeSysUser = getSysUserById(personnel.getCloseuserid());
			if(null != closeSysUser){
				personnel.setCloseuser(closeSysUser.getName());
			}
			SysUser openSysUser = getSysUserById(personnel.getOpenuserid());
			if(null != openSysUser){
				personnel.setOpenuser(openSysUser.getName());
			}
			SysUser modifySysUser = getSysUserById(personnel.getModifyuserid());
			if(null != modifySysUser){
				personnel.setModifyuser(modifySysUser.getName());
			}
			
			if(StringUtils.isNotEmpty(personnel.getState())){ //状态
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(personnel.getState(), "state");
				if(sysCode != null){
					personnel.setStateName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(personnel.getPersonnelstyle())){ //员工类型
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(personnel.getPersonnelstyle(), "personnelstyle");
				if(sysCode != null){
					personnel.setPerStyleName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(personnel.getHighestdegree())){ //最高学历
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(personnel.getHighestdegree(), "highestdegree");
				if(sysCode != null){
					personnel.setHighestDegreeName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(personnel.getBelongpost())){ //所属岗位
				WorkJob workJob = getBaseWorkJobMapper().showWorkJobInfo(personnel.getBelongpost());
				if(workJob != null){
					personnel.setBelongpostName(workJob.getJobname());
				}
			}
			if(StringUtils.isNotEmpty(personnel.getSex())){ //性别
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(personnel.getSex(), "sex");
				if(sysCode != null){
					personnel.setSexName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(personnel.getMaritalstatus())){ //婚姻状况
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(personnel.getMaritalstatus(), "maritalstatus");
				if(sysCode != null){
					personnel.setMaritalstatusName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(personnel.getNation())){ //民族
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(personnel.getNation(), "nation");
				if(sysCode != null){
					personnel.setNationName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(personnel.getPolstatus())){ //政治面貌
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(personnel.getPolstatus(), "polstatus");
				if(sysCode != null){
					personnel.setPolstatusName(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(personnel.getEmployetype())){ //业务属性
				String employetypeName = "";
				String[] employetypeArr = personnel.getEmployetype().toString().split(",");
				for(int i = 0;i<employetypeArr.length;i++){
					SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(employetypeArr[i], "employetype");
					if(sysCode != null){
						employetypeName = sysCode.getCodename() + "," + employetypeName;
					}
				}
				if(StringUtils.isNotEmpty(employetypeName)){
					employetypeName = employetypeName.substring(0, employetypeName.lastIndexOf(","));
				}
				personnel.setEmployetypeName(employetypeName);
			}
			Personnel lead = personnelMapper.getPersonnelInfo(personnel.getLeadid());
			if(null != lead){
				personnel.setLeadname(lead.getName());
			}
		}
		PageData pageData=new PageData(personnelMapper.getPersonnelCount(pageMap), list, pageMap);
		return pageData;
	}
	
	/**
	 * 添加人员信息
	 * @param personnel
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-1
	 */
	public Map addPersonnelInfo(Personnel personnel,
			List<Personneledu> personnelEduList,
			List<Personnelworks> personnelWorksList,
			List<Personnelpost> personnelPostsList,
			List<PersonnelCustomer> personnelCustomerList,
			List<PersonnelBrand> personnelBrandList)throws Exception
	{
		Map listMap = new HashMap();
		int b = 0, e = 0,w = 0,p = 0,c = 0;
		List<PersonnelBrandAndCustomer> addList = new ArrayList<PersonnelBrandAndCustomer>();
		if(null != personnelEduList && personnelEduList.size() != 0){
			for(Personneledu personneledu : personnelEduList){//教育
				if(StringUtils.isNotEmpty(personneledu.getPersonid())){//复制状态
					personneledu.setId(null);
				}
				personneledu.setPersonid(personnel.getId());
			}
			//添加教育经历
			listMap.put("eduListMap", personnelEduList);
			e = personnelMapper.addEdus(listMap);
		}
		else{
			e = 1;
		}
		if(null != personnelWorksList && personnelWorksList.size() != 0){
			for(Personnelworks personnelworks : personnelWorksList){//工作
				if(StringUtils.isNotEmpty(personnelworks.getPersonid())){//复制状态
					personnelworks.setId(null);
				}
				personnelworks.setPersonid(personnel.getId());
			}
			//添加工作经历
			listMap.put("workListMap", personnelWorksList);
			w = personnelMapper.addWorks(listMap);
		}
		else{
			w = 1;
		}
		if(null != personnelPostsList && personnelPostsList.size() != 0){
			for(Personnelpost personnelpost : personnelPostsList){//岗位
				if(StringUtils.isNotEmpty(personnelpost.getPersonid())){//复制状态
					personnelpost.setId(null);
				}
				personnelpost.setPersonid(personnel.getId());
			}
			//添加岗位变动记录
			listMap.put("postListMap", personnelPostsList);
			p = personnelMapper.addPosts(listMap);
		}
		else{
			p = 1;
		}
		//添加对应客户、对应品牌
		if(personnel.getEmployetype().indexOf("3") != -1){
			personnelMapper.deleteCustomerByPerid(personnel.getId());
			if(null != personnelCustomerList && personnelCustomerList.size() != 0){
				for(PersonnelCustomer personnelCustomer : personnelCustomerList){
					personnelCustomer.setPersonid(personnel.getId());
				}
				listMap.put("customerListMap", personnelCustomerList);
				personnelMapper.addCustomer(listMap);
			}
			personnelMapper.deleteBrandByPerid(personnel.getId());
			if(null != personnelBrandList && personnelBrandList.size() != 0){
				for(PersonnelBrand personnelBrand : personnelBrandList){
					personnelBrand.setPersonid(personnel.getId());
				}
				listMap.put("brandListMap", personnelBrandList);
				personnelMapper.addBrand(listMap);
			}else{
				if(StringUtils.isNotEmpty(personnel.getOldId())){
					List<PersonnelBrand> blist = personnelMapper.getBrandList(personnel.getOldId());
					if(blist.size() != 0){
						for(PersonnelBrand personnelBrand : blist){
							personnelBrand.setPersonid(personnel.getId());
						}
						listMap.put("brandListMap", blist);
						personnelMapper.addBrand(listMap);
					}
				}
			}
			personnelMapper.deleteBrandAndCustomerByPersonid(personnel.getId());
			personnelMapper.addPsnBrandAndCustomerFromTbrandAndTcustomer(personnel.getId());
		}else if(personnel.getEmployetype().indexOf("7") != -1){
			Map map = new HashMap();
			map.put("personid", personnel.getId());
			personnelMapper.deleteSupplierCustomerListByParam(map);
			if(null != personnelCustomerList && personnelCustomerList.size() != 0){
				for(PersonnelCustomer personnelCustomer : personnelCustomerList){
					personnelCustomer.setPersonid(personnel.getId());
				}
				listMap.put("supplierCustomerListMap", personnelCustomerList);
				personnelMapper.addSupplierCustomer(listMap);
			}
			personnelMapper.deleteSupplierBrandListByParam(map);
			if(null != personnelBrandList && personnelBrandList.size() != 0){
				for(PersonnelBrand personnelBrand : personnelBrandList){
					personnelBrand.setPersonid(personnel.getId());
				}
				listMap.put("supplierBrandListMap", personnelBrandList);
				personnelMapper.addSupplierBrand(listMap);
			}else{
				if(StringUtils.isNotEmpty(personnel.getOldId())){
					List<PersonnelBrand> blist = personnelMapper.getSupplierBrandListNoPage(personnel.getOldId());
					if(blist.size() != 0){
						for(PersonnelBrand personnelBrand : blist){
							personnelBrand.setPersonid(personnel.getId());
						}
						listMap.put("supplierBrandListMap", blist);
						personnelMapper.addSupplierBrand(listMap);
					}
				}
			}
			personnelMapper.deleteSupplierBrandAndCustomerByPersonid(personnel.getId());
			personnelMapper.addOfSupplierBrandAndCustomer(personnel.getId());
		}
		if(e>0 && w>0 && p>0){
			//添加基本信息
			b = personnelMapper.addPersonnelInfo(personnel);
		}
		Map map = new HashMap();
		map.put("flag", b > 0);
		return map;
	}
	
	/**
	 * 人员编号是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public boolean isExistPersonnelId(String id)throws Exception{
		return personnelMapper.isExistPersonnelId(id) > 0;
	}
	
	/**
	 * 显示人员信息详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-5
	 */
	public Personnel showPersonnelInfo(String id)throws Exception{
		Personnel personnel=personnelMapper.getPersonnelInfo(id);
        if(null != personnel){
            personnel.setOldId(id);
        }
		String brandids = "",customerids = "";
		if(null != personnel && StringUtils.isNotEmpty(personnel.getEmployetype())){
			List<PersonnelBrand> brandList = new ArrayList<PersonnelBrand>();
			List<PersonnelCustomer> customerList = new ArrayList<PersonnelCustomer>();
			if(personnel.getEmployetype().indexOf("3") != -1){
				brandList = personnelMapper.getBrandList(id);
				customerList = personnelMapper.getCustomerList(id);
			}else if(personnel.getEmployetype().indexOf("7") != -1){
				brandList = personnelMapper.getSupplierBrandListNoPage(id);
				customerList = personnelMapper.getSupplierCustomerListNoPage(id);
			}
			for(PersonnelBrand psnBrand : brandList){
				brandids += psnBrand.getBrandid() + ",";
			}
			for(PersonnelCustomer psnCustomer : customerList){
				customerids += psnCustomer.getCustomerid() + ",";
			}
			personnel.setBrandids(brandids);
			personnel.setCustomerids(customerids);
		}
		return personnel;
	}
	
	@Override
	public boolean checkEmployetypeOfCustomerData(String personid,
			String employetype) throws Exception {
		boolean retflag = false;
		if("1".equals(employetype)){
			retflag = customerMapper.getCustomerListCountBySalesuserid(personid) > 0;
		}else if("3".equals(employetype)){
			List brandList = personnelMapper.getBrandList(personid);
			List customerList = personnelMapper.getCustomerList(personid);
			if(brandList.size() > 0 || customerList.size() > 0){
				retflag = true;
			}
		}else if("7".equals(employetype)){
			List brandList = personnelMapper.getSupplierBrandListNoPage(personid);
			List customerList = personnelMapper.getSupplierCustomerListNoPage(personid);
			if(brandList.size() > 0 || customerList.size() > 0){
				retflag = true;
			}
		}
		return retflag;
	}

	/**
	 * 人员信息编辑,前提，判断是否被引用，是否允许修改 （网络互斥）
	 * @param personnel
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-5
	 */
	public Map editPersonnelInfo(Personnel personnel,
			List<Personneledu> personnelEduList,
			List<Personnelworks> personnelWorksList,
			List<Personnelpost> personnelPostsList,
			List<PersonnelCustomer> personnelCustomerList,
			List<PersonnelBrand> personnelBrandList)throws Exception{
		//保存修改前判断数据是否已经被加锁 可以修改
		boolean flag = isLockEdit("t_base_personnel", personnel.getOldId());
		int isLock = 1;
		boolean editFlag = true;
		Map map = new HashMap();
		if(flag){
			//是否允许修改
			Personnel beforePersonnelInfo = personnelMapper.getPersonnelInfo(personnel.getOldId());
			//判断是否可以进行修改操作，若可以修改，更新级联关系数据
			boolean flagPersonBase = canBasefilesIsEdit("t_base_personnel", beforePersonnelInfo, personnel);
			if(!flagPersonBase){
				editFlag = false;;
			}
			if(null != personnel){
				if(StringUtils.isNotEmpty(personnel.getId())){
					personnel.setIdArr(personnel.getIdArr()+","+personnel.getId());
					int i = personnelMapper.editPersonnelInfo(personnel);
					if(i > 0){
						//人员档案名称修改时同步修改客户档案中的客户业务员名称
						if(null != beforePersonnelInfo && !beforePersonnelInfo.getName().equals(personnel.getName())){
							personnelMapper.updateCustomerSalesusername(personnel.getId());
						}
						//判断所属部门是否修改，若修改用户管理中的部门也要修改
						List<SysUser> sysuserlist = getSysUserListByPersonnelid(personnel.getOldId());
						if(null != sysuserlist && sysuserlist.size() != 0){
							for(SysUser sysUser : sysuserlist){
								boolean sysflag = false;
								if(null!=personnel.getId() && !personnel.getId().equals(personnel.getOldId())){
									sysUser.setPersonnelid(personnel.getId());
									sysflag = true;
								}
								if(null!=personnel.getName() && !personnel.getName().equals(sysUser.getName())){
									sysUser.setName(personnel.getName());
									sysUser.setUsername(personnel.getName());
									sysflag = true;
								}
								if(null!=sysUser.getDepartmentid() && !sysUser.getDepartmentid().equals(personnel.getBelongdeptid())){
									DepartMent departMent = getDepartmentByDeptid(personnel.getBelongdeptid());
									if(null != departMent){
										sysUser.setDepartmentid(personnel.getBelongdeptid());
										sysUser.setDepartmentname(departMent.getName());
									}
									sysflag = true;
								}
								if(sysflag){
									getBaseSysUserMapper().editSysUser(sysUser);
								}
							}
						}
						editEdus(personnelEduList,personnel);
						editWorks(personnelWorksList,personnel);
						editPosts(personnelPostsList,personnel);
						//人员档案保存的时候 不重新添加删除品牌业务员对应客户
//						Map map1 = editBrandAndCustomer(personnelCustomerList,personnelBrandList,personnel);
//						map.put("cstbrdmsg",(String)map1.get("msg"));
						map.put("flag", true);
					}
					else{
						map.put("flag", false);
					}
				}
			}
		}
		else{
			isLock = 0;
		}
		map.put("isLock", isLock);
		map.put("editFlag", editFlag);
		return map;
	}
	
	/**
	 * 删除人员信息(批量)--(加锁，是否被引用)
	 * @param perIdArr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-17
	 */
	public Map deletePersonnel(String[] perIdArr)throws Exception{
		String newperids = "",undelpsnids = "",undelsupsnids = "",msg ="";
		boolean retflag = false;
		for(String personid : perIdArr){
			boolean delflag = true;
			Personnel personnel = personnelMapper.getPersonnelInfo(personid);
			if(null != personnel){
				if(null != personnel.getEmployetype()){
					if(personnel.getEmployetype().indexOf("1") != -1){//客户业务员
						List list = customerMapper.getCustomerBySalesuserid(personid);
						if(list.size() != 0){
							customerMapper.editClearCustomerSalesuser(personid);
						}
					}else if(personnel.getEmployetype().indexOf("3") != -1){//品牌业务员
						//对应客户、对应品牌中存在该人员（品牌业务员），不允许删除该人员
						List brandlist = personnelMapper.getBrandList(personid);
						List customerlist = personnelMapper.getCustomerList(personid);
						if(brandlist.size() >0 || customerlist.size() > 0){
							delflag = false;
							if(StringUtils.isEmpty(undelpsnids)){
								undelpsnids = personid;
							}else{
								undelpsnids += "," + personid;
							}
						}
					}else if(personnel.getEmployetype().indexOf("7") != -1){//厂家业务员
						//对应客户、对应品牌中存在该人员（厂家业务员），不允许删除该人员
						int brandcount = personnelMapper.getSupplierBrandListCount(personid);
						int customercount = personnelMapper.getSupplierCustomerListCount(personid);
						if(brandcount >0 || customercount > 0){
							delflag = false;
							if(StringUtils.isEmpty(undelsupsnids)){
								undelsupsnids = personid;
							}else{
								undelsupsnids += "," + personid;
							}
						}
					}
				}
				if(delflag){
					if(StringUtils.isEmpty(newperids)){
						newperids = personid;
					}else{
						newperids += "," + personid;
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(newperids)){
			String[] newPsnidArr =  newperids.split(",");
			boolean flag = personnelMapper.deletePersonnelInfos(newPsnidArr) > 0;
			if(flag){
				boolean detailflag = true;
				for(String personid : newPsnidArr){
					int e = 0,w = 0, p = 0,cb = 0;
					//判断该人员是否关联系统用户，若关联则同步删除系统用户
					SysUser sysUser = getBaseSysUserMapper().checkSysUserByPerId(personid);
					if(null != sysUser){
						getBaseSysUserMapper().deleteSysUser(sysUser.getUserid());
					}

					List<Personneledu> eduList = personnelMapper.getEducationList(personid);//获取教育经历列表
					List<Personnelworks> workList = personnelMapper.getWorksList(personid);//获取工作经历列表
					List<Personnelpost> postList = personnelMapper.getPostList(personid);//获取岗位变更列表
					List<PersonnelBrandAndCustomer> brandAndCustomerList = personnelMapper.getBrandAndCustomerList(personid);//获取对应品牌和客户列表
					Map map = new HashMap();
					map.put("personid", personid);
					List<PersonnelBrandAndCustomer> supplierBrandCustomerList = personnelMapper.getSupplierBrandCustomerList(map);
					if(eduList.size() != 0){
						e = personnelMapper.deleteEduByPerid(personid);//根据人员编号personid删除教育经历
					}
					else{
						e = 1;
					}
					if(workList.size() != 0){
						w = personnelMapper.deleteWorkByPerid(personid);//根据人员编号personid删除工作经历
					}
					else{
						w = 1;
					}
					if(postList.size() != 0){
						p = personnelMapper.deletePostByPerid(personid);//根据人员编号personid删除岗位变动记录
					}
					else{
						p = 1;
					}
					if(brandAndCustomerList.size() != 0){
						cb = personnelMapper.deleteBrandAndCustomerByPersonid(personid);//根据人员编号personid删除对应客户
						personnelMapper.deleteBrandByPerid(personid);
						personnelMapper.deleteCustomerByPerid(personid);
					}
					else{
						personnelMapper.deleteBrandByPerid(personid);
						personnelMapper.deleteCustomerByPerid(personid);
						cb = 1;
					}
					if(supplierBrandCustomerList.size() != 0){
						cb = personnelMapper.deleteSupplierBrandAndCustomerByMap(map);
						personnelMapper.deleteSupplierBrandListByParam(map);
						personnelMapper.deleteSupplierCustomerListByParam(map);
					}else{
						personnelMapper.deleteSupplierBrandListByParam(map);
						personnelMapper.deleteSupplierCustomerListByParam(map);
						cb = 1;
					}

					detailflag = detailflag && (e > 0) && (w > 0) && (p > 0) && (cb > 0);
					if(!detailflag){
						throw new Exception("人员档案删除明细失败，回滚数据");
					}
				}
				if(detailflag){
					retflag = true;
					msg = "人员编号："+newperids+"删除成功;";
				}
			}else{
				msg = "人员编号："+newperids+"删除失败;";
			}
		}
		if(StringUtils.isNotEmpty(undelpsnids)){
			if(StringUtils.isEmpty(msg)){
				msg = "人员(品牌业务员)编号："+undelpsnids+"存在对应客户或对应品牌，不允许删除；";
			}else{
				msg += "<br>" + "人员(品牌业务员)编号："+undelpsnids+"存在对应客户或对应品牌，不允许删除；";
			}
		}
		if(StringUtils.isNotEmpty(undelsupsnids)){
			if(StringUtils.isEmpty(msg)){
				msg = "人员(厂家业务员)编号："+undelsupsnids+"存在对应客户或对应品牌，不允许删除；";
			}else{
				msg += "<br>" + "人员(厂家业务员)编号："+undelsupsnids+"存在对应客户或对应品牌，不允许删除；";
			}
		}
		Map map = new HashMap();
		map.put("msg",msg);
		map.put("retflag",retflag);
		return map;
	}
	
	/**
	 * 启用人员(批量)
	 * @param perIdArr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-18
	 */
	public Map enablePersonnels(String perIdArr,String openuserid)throws Exception{
		String[] sarrayList=perIdArr.split(",");
		int isFail = 0,isSuc = 0, unSucNum = 0;//isFail：失败次数,isSuc:成功次数
		String newPerIdStr ="";//新的人员数组字符串
		if(sarrayList.length != 0){
			for(int i=0;i<sarrayList.length;i++){//判断人员状态是否为保存或禁用
				Personnel personnelInfo = personnelMapper.getPersonnelInfo(sarrayList[i]);
				if(personnelInfo != null){
					if(!"0".equals(personnelInfo.getState()) && !"2".equals(personnelInfo.getState())){//状态不为禁用或保存时，不允许启用操作
						isFail += 1;
					}
					else{
						newPerIdStr = sarrayList[i] + "," + newPerIdStr;//重新生成状态为启用的有效人员编号数组字符串
					}
				}
				else{//不存在该人员
					isFail += 1;
				}
			}
		}
		Map retMap = new HashMap();
		if(StringUtils.isNotEmpty(newPerIdStr)){
			String[] newPerIdArr=newPerIdStr.split(",");
			Map listMap = new HashMap();
			//数组转化为List
			List<String> enableList = new ArrayList<String>(Arrays.asList(newPerIdArr));
			listMap.put("enableList", enableList);
			listMap.put("openuserid", openuserid);
			int p = personnelMapper.enablePersonnels(listMap);
			retMap.put("retFlag", (p>0));
			if(p> 0){
				isSuc = isSuc + p;
			}
			else{
				unSucNum = newPerIdArr.length;
			}
		}
		retMap.put("sucMes", "成功启用"+isSuc+"条记录;");
		retMap.put("failMes", "无效记录"+unSucNum+"条;");
		retMap.put("unSucMes", "启用失败"+isFail+"条记录;");
		retMap.put("newPerIdStr", newPerIdStr);
		return retMap;
	}
	
	/**
	 * 禁用人员(批量)
	 * @param newPerIdStr 人员编号数组字符串，closeuserid 禁用人员编号，type 是否同步禁用系统用户
	 * 异步:yibu,同步:tonbu
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-18
	 */
	public Map disablePersonnels(String newPerIdStr,String closeuserid,String type)throws Exception{
		boolean flag = false,countFlag = true,retFlag = false;;
		String Mes = "",userPerIdsStr="";
		int isFail = 0,isSuc = 0;//isFail：失败次数,isSuc:成功次数
		if(StringUtils.isNotEmpty(newPerIdStr)){
			String[] perIdsArr=newPerIdStr.split(",");
			if("yibu".equals(type)){
				//为异步时，只禁用人员
				Map listMap = new HashMap();
				//数组转化为List
				List<String> disableList = new ArrayList<String>(Arrays.asList(perIdsArr));
				listMap.put("disableList", disableList);
				listMap.put("closeuserid", closeuserid);
				int m = personnelMapper.disablePersonnels(listMap);
				if(m > 0){
					isSuc = isSuc + m;
					retFlag = true;
				}
			}
			else{//同步时，同时禁用系统用户
				//判断是否存在该系统用户 or 是否已分配系统用户
				for(int i=0;i<perIdsArr.length;i++){
					SysUser sysUserInfo = sysUserMapper.checkSysUserByPerId(perIdsArr[i]);
					if(null == sysUserInfo){//为空时,该人员没有分配系统用户，直接禁用该人员
						Map disMap = new HashMap();
						disMap.put("id", perIdsArr[i]);
						disMap.put("closeuserid", closeuserid);
						int n = personnelMapper.disablePersonnel(disMap);
						if(n > 0){
							flag = true;
							isSuc +=1;
						}
						else{
							isFail +=1;
						}
						countFlag = countFlag && flag;
					}
					else{//已分配系统用户
						userPerIdsStr = perIdsArr[i] + "," + userPerIdsStr;//关联用户人员编号数组字符串
					}
				}
			}
			retFlag = countFlag;
			if(StringUtils.isNotEmpty(userPerIdsStr)){
				String[] userPerIdsArr = userPerIdsStr.split(",");
				Map listMap = new HashMap();
				//数组转化为List
				List<String> disableList = new ArrayList<String>(Arrays.asList(userPerIdsArr));
				listMap.put("disableList", disableList);
				listMap.put("closeuserid", closeuserid);
				int p = personnelMapper.disablePersonnels(listMap);
				if(p > 0){
					int u = sysUserMapper.disableSysUserByPerId(userPerIdsArr);//批量禁用系统用户
					if(u > 0){
						isSuc = isSuc + p;
						retFlag = true;
					}
				}
			}
		}
		Map retMap = new HashMap();
		retMap.put("retFlag", retFlag);
		retMap.put("failMes","禁用失败"+isFail+"条;");
		retMap.put("sucMes", "禁用成功"+isSuc+"条;");
		return retMap;
	}
	
	/*---------教育经历------------------*/
	/**
	 * 根据人员编号查询对应的教育经历列表 
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public List showEducationList(String personid)throws Exception{
		List list = personnelMapper.getEducationList(personid);
		return list;
	}
	
	/**
	 * 删除教育经历 or 批量
	 * @param idArr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean deleteEdus(String idArr)throws Exception{
		if(idArr.indexOf("X") != -1){
			idArr = idArr.replaceAll("X", "#");
		}
		String[] sarrayList=idArr.split(",");
		//数组转化为List
		List<String> list = new ArrayList<String>(Arrays.asList(sarrayList));
//		//List转化为Map
//		Map idMap = new HashMap();
//		idMap.put("id", list);
		int i=personnelMapper.deleteEdus(list);
		return i>0;
	}

	/**
	 * 添加教育经历 or 批量
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean addEdus(List<Personneledu> list)throws Exception{
		Map listMap=new HashMap();
		List<Personneledu> addList=new ArrayList();
		for(Personneledu personneledu : list){
			addList.add(personneledu);
		}
		listMap.put("eduListMap", addList);
		int i=personnelMapper.addEdus(listMap);
		return i>0;
	}
	
	/**
	 * 修改教育经历 or 批量
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean editEdus(List<Personneledu> list,Personnel personnel)throws Exception{
		int ae=0,j=0,count=0,s=0,allE =0;
		boolean editEFlag = true,delEduBool = true;
		Map listMap=new HashMap();
		List<Personneledu> addList=new ArrayList();
		if(null != list && list.size() != 0){
			for(Personneledu personneledu : list){
				if(null != personneledu){
					if(null != personneledu.getId()){
						int e = personnelMapper.isExistPersonnelEdu(personneledu.getId());//e>0存在，则修改，反之新增
						if(e > 0){
							personneledu.setPersonid(personnel.getId());
							count +=1;
							j=personnelMapper.editEdus(personneledu);
							if(j>0){
								s +=1;
							}
						}
						else{
							personneledu.setId(null);
							personneledu.setPersonid(personnel.getId());
							addList.add(personneledu);
						}
					}
					else{
						personneledu.setPersonid(personnel.getId());
						addList.add(personneledu);
					}
				}
			}
			if(addList.size() != 0){
				listMap.put("eduListMap", addList);
				ae = personnelMapper.addEdus(listMap);
			}
			else{
				ae = 1;
			}
			if(StringUtils.isNotEmpty(personnel.getEduListDelId())){
				delEduBool = deleteEdus(personnel.getEduListDelId());
			}
			editEFlag = (count == s) && (ae > 0) && delEduBool;
		}
		else{
			if(StringUtils.isNotEmpty(personnel.getEduListDelId())){
				editEFlag = personnelMapper.deleteEduByPerid(personnel.getOldId()) > 0;
			}
		}
		return editEFlag;
	}
	
	/**
	 * 根据人员编号查询对应的工作经历列表 
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public List showWorksList(String personid)throws Exception{
		List list = personnelMapper.getWorksList(personid);
		return list;
	}
	
	/**
	 * 删除工作经历 or 批量
	 * @param idArr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean deleteWorks(String idArr)throws Exception{
		if(idArr.indexOf("X") != -1){
			idArr = idArr.replaceAll("X", "#");
		}
		String[] sarrayList=idArr.split(",");
		//数组转化为List
		List<String> list = new ArrayList<String>(Arrays.asList(sarrayList));
		int i=personnelMapper.deleteWorks(list);
		return i>0;
	}
	
	/**
	 * 添加工作经历 or 批量
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean addWorks(List<Personnelworks> list)throws Exception{
		Map workListMap=new HashMap();
		List<Personnelworks> addWorkList=new ArrayList();
		for(Personnelworks personnelworks : list){
			addWorkList.add(personnelworks);
		}
		workListMap.put("workListMap", addWorkList);
		int i=personnelMapper.addWorks(workListMap);
		return i>0;
	}
	
	/**
	 * 修改工作经历 or 批量
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean editWorks(List<Personnelworks> list,Personnel personnel)throws Exception{
		int aw=0,j=0,count=0,s=0,allW =0;
		boolean editWFlag = true,delWorkBool = true;
		Map workListMap=new HashMap();
		List<Personnelworks> addWorkList=new ArrayList();
		if(null != list && list.size() != 0){
			for(Personnelworks personnelworks : list){
				if(null != personnelworks){
					if(null != personnelworks.getId()){
						int w = personnelMapper.isExistPersonnelWork(personnelworks.getId());//e>0存在，则修改，反之新增
						if(w > 0){
							personnelworks.setPersonid(personnel.getId());
							count +=1;
							j=personnelMapper.editWork(personnelworks);
							if(j>0){
								s +=1;
							}
						}
						else{
							personnelworks.setId(null);
							personnelworks.setPersonid(personnel.getId());
							addWorkList.add(personnelworks);
						}
					}
					else{
						personnelworks.setPersonid(personnel.getId());
						addWorkList.add(personnelworks);
					}
				}
			}
			if(addWorkList.size() != 0){
				workListMap.put("workListMap", addWorkList);
				aw = personnelMapper.addWorks(workListMap);
			}
			else{
				aw = 1;
			}
			if(StringUtils.isNotEmpty(personnel.getWorkListDelId())){
				delWorkBool = deleteEdus(personnel.getWorkListDelId());
			}
			editWFlag = (count == s) && (aw > 0) && delWorkBool;
		}
		else{
			if(StringUtils.isNotEmpty(personnel.getWorkListDelId())){
				editWFlag = personnelMapper.deleteWorkByPerid(personnel.getOldId()) > 0;
			}
		}
		return editWFlag;
	}
	
	/*------------------岗位变动记录-------------------------*/
	/**
	 * 根据人员编号查询对应的岗位变动记录列表 
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public List showPostsList(String personid)throws Exception{
		List<Personnelpost> list = personnelMapper.getPostList(personid);
		for(Personnelpost personnelpost :list){//所属部门
			if(personnelpost.getBelongdeptid() != null){
				DepartMent departMent = getDepartmentByDeptid(personnelpost.getBelongdeptid());
				if(null != departMent){
					personnelpost.setBelongdeptName(departMent.getName());
				}
			}
			if(personnelpost.getBelongpostid() != null){
				WorkJob workJob = getBaseWorkJobMapper().showWorkJobInfo(personnelpost.getBelongpostid());
				if(null != workJob){
					personnelpost.setBelongpostName(workJob.getJobname());
				}
			}
		}
		return list;
	}
	
	/**
	 * 删除岗位变动记录 or 批量
	 * @param idArr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean deletePosts(String idArr)throws Exception{
		if(idArr.indexOf("X") != -1){
			idArr = idArr.replaceAll("X", "#");
		}
		String[] sarrayList=idArr.split(",");
		//数组转化为List
		List<String> list = new ArrayList<String>(Arrays.asList(sarrayList));
		int i=personnelMapper.deletePosts(list);
		return i>0;
	}
	
	/**
	 * 删除对应客户
	 * @param idStr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public boolean deleteCustomer(String idStr)throws Exception{
		if(StringUtils.isNotEmpty(idStr)){
			if(idStr.indexOf(",") != -1){
				String[] idArr = idStr.split(",");
				List<String> list = new ArrayList<String>(Arrays.asList(idArr));
				return personnelMapper.deleteCustomer(list) > 0;
			}
			else{
				String[] idArr = new String[0];
				idArr[0] = idStr;
				List<String> list = new ArrayList<String>(Arrays.asList(idArr));
				return personnelMapper.deleteCustomer(list) > 0;
			}
		}
		return false;
	}
	
	/**
	 * 删除对应品牌
	 * @param idStr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public boolean deleteBrand(String idStr)throws Exception{
		if(StringUtils.isNotEmpty(idStr)){
			String[] idArr = idStr.split(",");
			List<String> list = new ArrayList<String>(Arrays.asList(idArr));
			int i = personnelMapper.deleteBrand(list);
			return i > 0;
		}
		return false;
	}
	
	/**
	 * 添加岗位变动记录 or 批量
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean addPosts(List<Personnelpost> list)throws Exception{
		Map postListMap=new HashMap();
		List<Personnelpost> addPostList=new ArrayList();
		for(Personnelpost personnelpost : list){
			addPostList.add(personnelpost);
		}
		postListMap.put("postListMap", addPostList);
		int i=personnelMapper.addPosts(postListMap);
		return i>0;
	}
	
	/**
	 * 修改岗位变动记录 or 批量
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean editPosts(List<Personnelpost> list,Personnel personnel)throws Exception{
		int ap=0,j=0,count=0,s=0,allP =0;
		boolean editPFlag = true,delPostBool = true;
		Map postListMap=new HashMap();
		List<Personnelpost> addPostList=new ArrayList();
		if(null != list && list.size() != 0){
			for(Personnelpost personnelpost : list){
				if(null != personnelpost){
					if(null != personnelpost.getId()){
						int p = personnelMapper.isExistPersonnelPost(personnelpost.getId());//e>0存在，则修改，反之新增
						if(p > 0){
							personnelpost.setPersonid(personnel.getId());
							count +=1;
							j=personnelMapper.editPost(personnelpost);
							if(j>0){
								s +=1;
							}
						}
						else{
							personnelpost.setId(null);
							personnelpost.setPersonid(personnel.getId());
							addPostList.add(personnelpost);
						}
					}
					else{
						personnelpost.setPersonid(personnel.getId());
						addPostList.add(personnelpost);
					}
				}
			}
			if(addPostList.size() != 0){
				postListMap.put("postListMap", addPostList);
				ap = personnelMapper.addPosts(postListMap);
			}
			else{
				ap = 1;
			}
			if(StringUtils.isNotEmpty(personnel.getPostListDelId())){
				delPostBool = deletePosts(personnel.getPostListDelId());
			}
			editPFlag = (count == s) && (ap > 0) && delPostBool;
		}
		else{
			if(StringUtils.isNotEmpty(personnel.getDelPostIds())){
				editPFlag = personnelMapper.deletePostByPerid(personnel.getOldId()) > 0;
			}
		}
		return editPFlag;
	}
	
	/**
	 * 修改对应客户
	 * @param customerList
	 * @param brandList
	 * @param personnel
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public Map editBrandAndCustomer(List<PersonnelCustomer> customerList,List<PersonnelBrand> brandList,Personnel personnel)throws Exception{
		Map retMap = new HashMap();
		Map map = new HashMap();
		map.put("personid", personnel.getOldId());
		boolean flag = false,retflag = true;
		String customerids = "",brandids = "",msg = "";
		if(StringUtils.isNotEmpty(personnel.getEmployetype())){
			if(personnel.getEmployetype().indexOf("3") != -1){
				//对应客户不为空，判断是否存在删除操作，若为空，判断是否存在删除操作,存在则删除全部对应客户数据
				if(null != customerList && customerList.size() != 0 ){
					List<String> oldList = new ArrayList<String>();
					List<String> newList = new ArrayList<String>();
					List<PersonnelCustomer> clist = personnelMapper.getCustomerList(personnel.getOldId());
					if(null != clist && clist.size() != 0){
						for(PersonnelCustomer pc : clist){
							oldList.add(pc.getCustomerid());
						}
						personnelMapper.deleteCustomerByPerid(personnel.getOldId());
					}else{
						for(PersonnelCustomer pc : customerList){
							if(StringUtils.isEmpty(customerids)){
								customerids = pc.getCustomerid();
							}else{
								customerids += "," + pc.getCustomerid();
							}
						}
					}
					for(PersonnelCustomer personnelCustomer : customerList){
						newList.add(personnelCustomer.getCustomerid());
						personnelCustomer.setPersonid(personnel.getId());
					}
					map.put("customerListMap", customerList);
					personnelMapper.addCustomer(map);
					flag = true;

					//新添加的客户编号
					for(String customerid : newList){
						if (!oldList.contains(customerid)) {
							if(StringUtils.isEmpty(customerids)){
								customerids = customerid;
							}else{
								customerids += "," + customerid;
							}
						}
					}
				}else{
					if(StringUtils.isNotEmpty(personnel.getCustomerListDelId())){
						//删除全部对应客户数据
						personnelMapper.deleteCustomerByPerid(personnel.getOldId());
						flag = true;
					}else{//判断编码是否改变,若改变，则对应客户人员编码做相应改变
						if(!personnel.getOldId().equals(personnel.getId())){
							List<PersonnelCustomer> clist = personnelMapper.getCustomerList(personnel.getOldId());
							if(null != clist && clist.size() != 0){
								for(PersonnelCustomer personnelCustomer : clist){
									personnelCustomer.setPersonid(personnel.getId());
									personnelMapper.editCustomer(personnelCustomer);
								}
								flag = true;
							}
						}
					}
				}
				if(null != brandList && brandList.size() != 0){
					List<String> oldList = new ArrayList<String>();
					List<String> newList = new ArrayList<String>();
					List<PersonnelBrand> blist1 = personnelMapper.getBrandList(personnel.getOldId());
					if(null != blist1 && blist1.size() != 0){
						for(PersonnelBrand pb : blist1){
							oldList.add(pb.getBrandid());
						}
						personnelMapper.deleteBrandByPerid(personnel.getOldId());
					}
					for(PersonnelBrand psnBrand : brandList){
						newList.add(psnBrand.getBrandid());
						psnBrand.setPersonid(personnel.getId());
					}
					map.put("brandListMap", brandList);
					personnelMapper.addBrand(map);
					flag = true;

					//新添加的品牌编号
					for(String brandid : newList){
						if (!oldList.contains(brandid)) {
							if(StringUtils.isEmpty(brandids)){
								brandids = brandid;
							}else{
								brandids += "," + brandid;
							}
						}
					}
				}
				if(flag){
					//清空人员编码对应客户品牌表数据
					personnelMapper.deleteBrandAndCustomerByPersonid(personnel.getOldId());
					retflag = personnelMapper.addPsnBrandAndCustomerFromTbrandAndTcustomer(personnel.getId()) > 0;
				}
			}else if(personnel.getEmployetype().indexOf("7") != -1){
				Map map2 = new HashMap();
				map2.put("personid", personnel.getOldId());
				//对应客户不为空，判断是否存在删除操作，若为空，判断是否存在删除操作,存在则删除全部对应客户数据
				if(null != customerList && customerList.size() != 0 ){
					List<String> oldList = new ArrayList<String>();
					List<String> newList = new ArrayList<String>();
					List<PersonnelCustomer> clist = personnelMapper.getSupplierCustomerListByMap(map2);
					if(null != clist && clist.size() != 0){
						for(PersonnelCustomer pc : clist){
							oldList.add(pc.getCustomerid());
						}
						personnelMapper.deleteSupplierCustomerListByParam(map2);
					}else{
						for(PersonnelCustomer pc : customerList){
							if(StringUtils.isEmpty(customerids)){
								customerids = pc.getCustomerid();
							}else{
								customerids += "," + pc.getCustomerid();
							}
						}
					}
					for(PersonnelCustomer personnelCustomer : customerList){
						newList.add(personnelCustomer.getCustomerid());
						personnelCustomer.setPersonid(personnel.getId());
					}
					map.put("supplierCustomerListMap", customerList);
					personnelMapper.addSupplierCustomer(map);
					flag = true;

					//新添加的客户编号
					for(String customerid : newList){
						if (!oldList.contains(customerid)) {
							if(StringUtils.isEmpty(customerids)){
								customerids = customerid;
							}else{
								customerids += "," + customerid;
							}
						}
					}
				}else{
					if(StringUtils.isNotEmpty(personnel.getCustomerListDelId())){
						//删除全部对应客户数据
						personnelMapper.deleteSupplierCustomerListByParam(map2);
						flag = true;
					}else{//判断编码是否改变,若改变，则对应客户人员编码做相应改变
						if(!personnel.getOldId().equals(personnel.getId())){
							List<PersonnelCustomer> clist = personnelMapper.getSupplierCustomerListByMap(map2);
							if(null != clist && clist.size() != 0){
								for(PersonnelCustomer personnelCustomer : clist){
									personnelCustomer.setPersonid(personnel.getId());
									personnelMapper.editSupplierCustomer(personnelCustomer);
								}
								flag = true;
							}
						}
					}
				}
				if(null != brandList && brandList.size() != 0){
					List<String> oldList = new ArrayList<String>();
					List<String> newList = new ArrayList<String>();
					List<PersonnelBrand> blist1 = personnelMapper.getSupplierBrandListNoPage(personnel.getOldId());
					if(null != blist1 && blist1.size() != 0){
						for(PersonnelBrand pb : blist1){
							oldList.add(pb.getBrandid());
						}
						personnelMapper.deleteSupplierBrandListByParam(map2);
					}
					for(PersonnelBrand psnBrand : brandList){
						newList.add(psnBrand.getBrandid());
						psnBrand.setPersonid(personnel.getId());
					}
					map.put("supplierBrandListMap", brandList);
					personnelMapper.addSupplierBrand(map);
					flag = true;

					//新添加的品牌编号
					for(String brandid : newList){
						if (!oldList.contains(brandid)) {
							if(StringUtils.isEmpty(brandids)){
								brandids = brandid;
							}else{
								brandids += "," + brandid;
							}
						}
					}
				}
				if(flag){
					//清空人员编码对应客户品牌表数据
					personnelMapper.deleteSupplierBrandAndCustomerByPersonid(personnel.getOldId());
					retflag = personnelMapper.addOfSupplierBrandAndCustomer(personnel.getId()) > 0;
				}
			}
		}
		if(retflag){
			String customeridmsg = "",brandidmsg = "";
			if(StringUtils.isNotEmpty(customerids)){
				customeridmsg = "添加对应客户编码："+customerids;
			}
			if(StringUtils.isNotEmpty(brandids)){
				brandidmsg = "添加对应品牌编码："+brandids;
			}
			if(StringUtils.isNotEmpty(customeridmsg) || StringUtils.isNotEmpty(brandidmsg)){
				msg = customeridmsg + "，" + brandidmsg;
			}
		}
		retMap.put("retflag",retflag);
		retMap.put("msg",msg);
		return retMap;
	}
	
	/**
	 * 根据籍贯编号id获取籍贯名称
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-28
	 */
	public String getNPname(String id)throws Exception{
		String name = personnelMapper.getNPname(id);
		return name;
	}
	
	/**
	 * 根据人员id数组字符串获取人员档案列表
	 * @param idsStr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-28
	 */
	public List<Personnel> getPersonnelListByIds(String idsStr)throws Exception{
		String[] idsArr = idsStr.split(",");
		List<Personnel> list = personnelMapper.getPersonnelListByIds(idsArr);
		return list;
	}
	
	/**
	 * Excel导入添加人员信息，
	 * 判断导入的人员是否重复，导入成功后的状态为保存2
	 * @param personnel
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-20
	 */
	public Map addDRPersonnelInfo(Personnel personnel)throws Exception{
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		boolean flag = false;
		int  e = personnelMapper.isExistPersonnelId(personnel.getId());
		if(!(e > 0)){//不重复
			SysUser sysUser = getSysUser();
			personnel.setAdduserid(sysUser.getUserid());
			personnel.setAdduser(sysUser.getName());
			personnel.setAdddept(sysUser.getDepartmentname());
			personnel.setAdddeptid(sysUser.getDepartmentid());
			if(StringUtils.isEmpty(personnel.getState())){
				personnel.setState("2");
			}
			if(StringUtils.isEmpty(personnel.getHighestdegree())){
				personnel.setHighestdegree("8");
			}
			if(StringUtils.isEmpty(personnel.getPersonnelstyle())){//员工类型
				personnel.setPersonnelstyle("3");
			}
			if(StringUtils.isEmpty(personnel.getSex())){//性别
				personnel.setSex("2");
			}
			if(StringUtils.isEmpty(personnel.getMaritalstatus())){//婚姻状态
				personnel.setMaritalstatus("3");
			}
			flag = personnelMapper.addPersonnelInfo(personnel) > 0;
			if(!flag){
				if(StringUtils.isNotEmpty(failStr)){
					failStr += "," + personnel.getId(); 
				}
				else{
					failStr = personnel.getId();
				}
				failureNum++;
			}
			else{
				successNum++;
			}
		}
		else{
			Personnel personnel2 = personnelMapper.getPersonnelInfo(personnel.getId());
			if(null != personnel2){
				if("0".equals(personnel2.getState())){//禁用状态，不允许导入
					if(StringUtils.isEmpty(closeVal)){
						closeVal = personnel2.getId();
					}
					else{
						closeVal += "," + personnel2.getId();
					}
					closeNum++;
				}
				else{
					if(StringUtils.isEmpty(repeatVal)){
						repeatVal = personnel2.getId();
					}
					else{
						repeatVal += "," + personnel2.getId();
					}
					repeatNum++;
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
	public Map addDRPersonnelEdu(List<Personneledu> list)
			throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		if(null != list && list.size() != 0){
			List<Personneledu> addList = new ArrayList<Personneledu>();
			for(Personneledu personneledu : list){
				if(null != personneledu.getId()){
					if(!(personnelMapper.isExistPersonnelEdu(personneledu.getId()) > 0)){
						personneledu.setId(null);
						addList.add(personneledu);
						map.put("eduListMap", addList);
						flag = personnelMapper.addEdus(map) > 0;
					}
				}
				else{
					addList.add(personneledu);
					map.put("eduListMap", addList);
					flag = personnelMapper.addEdus(map) > 0;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map addDRPersonnelPost(List<Personnelpost> list)
			throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		if(null != list && list.size() != 0){
			List<Personnelpost> addList = new ArrayList<Personnelpost>();
			for(Personnelpost personnelpost : list){
				if(null != personnelpost.getId()){
					if(!(personnelMapper.isExistPersonnelPost(personnelpost.getId()) > 0)){
						personnelpost.setId(null);
						addList.add(personnelpost);
						map.put("postListMap", addList);
						flag = personnelMapper.addPosts(map) > 0;
					}
				}
				else{
					addList.add(personnelpost);
					map.put("postListMap", addList);
					flag = personnelMapper.addPosts(map) > 0;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map addDRPersonnelWork(List<Personnelworks> list)
			throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		if(null != list && list.size() != 0){
			List<Personnelworks> addList = new ArrayList<Personnelworks>();
			for(Personnelworks personnelworks : list){
				if(null != personnelworks.getId()){
					if(!(personnelMapper.isExistPersonnelWork(personnelworks.getId()) > 0)){
						personnelworks.setId(null);
						addList.add(personnelworks);
						map.put("workListMap", addList);
						flag = personnelMapper.addWorks(map) > 0;
					}
				}
				else{
					addList.add(personnelworks);
					map.put("workListMap", addList);
					flag = personnelMapper.addWorks(map) > 0;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map addDRPsnlBrandAndCustomer(Map map)throws Exception {
		boolean bflag = true ,cflag = true,flag = true;
		Map map2 = new HashMap();
		if(!map.isEmpty()){
			List<PersonnelBrand> brandList = null;
			List<PersonnelCustomer> customerList = null;
			if(map.containsKey("PersonnelBrand") && null != map.get("PersonnelBrand")){
				brandList = (List<PersonnelBrand>)map.get("PersonnelBrand");
			}
			if(map.containsKey("PersonnelCustomer") && null != map.get("PersonnelCustomer")){
				customerList = (List<PersonnelCustomer>)map.get("PersonnelCustomer");
			}
			String personid = "";
			if(null != brandList && brandList.size() != 0){
				personid = brandList.get(0).getPersonid();
			}else if(null != customerList && customerList.size() != 0){
				personid = customerList.get(0).getPersonid();
			}
			Personnel personnel = showPersonnelInfo(personid);
			if(null != personnel && StringUtils.isNotEmpty(personnel.getEmployetype())){
				if(personnel.getEmployetype().indexOf("3") != -1){
					if(null != brandList && brandList.size() != 0){
						if(null != customerList && customerList.size() != 0){
							String brandids = "";
							List<PersonnelCustomer> realPsnCustomerList = new ArrayList<PersonnelCustomer>();
							for(PersonnelBrand psnBrand : brandList){
								brandids += psnBrand.getBrandid() + ",";
							}
							for(PersonnelCustomer psnCustomer : customerList){
								boolean checkflag = checkBrandAndCustomerRepeat(psnCustomer.getCustomerid(), brandids, "3");
								//true不存在false存在
								if(checkflag){
									realPsnCustomerList.add(psnCustomer);
								}
							}
							if(realPsnCustomerList.size() != 0){
								map.put("customerListMap", realPsnCustomerList);
								cflag = personnelMapper.addCustomer(map) > 0;
								map.put("brandListMap", brandList);
								bflag = personnelMapper.addBrand(map) > 0;
							}else{
								cflag = false;
							}
						}
					}else{
						if(null != customerList && customerList.size() != 0){
							map.put("customerListMap", customerList);
							cflag = personnelMapper.addCustomer(map) > 0;
						}
					}
					if(bflag && cflag){
						flag = personnelMapper.addPsnBrandAndCustomerFromTbrandAndTcustomer(personid) > 0;
					}
				}else if(personnel.getEmployetype().indexOf("7") != -1){
					if(null != brandList && brandList.size() != 0){
						if(null != customerList && customerList.size() != 0){
							String brandids = "";
							List<PersonnelCustomer> realPsnCustomerList = new ArrayList<PersonnelCustomer>();
							for(PersonnelBrand psnBrand : brandList){
								brandids += psnBrand.getBrandid() + ",";
							}
							for(PersonnelCustomer psnCustomer : customerList){
								boolean checkflag = checkBrandAndCustomerRepeat(psnCustomer.getCustomerid(), brandids, "7");
								//true不存在false存在
								if(checkflag){
									realPsnCustomerList.add(psnCustomer);
								}
							}
							if(realPsnCustomerList.size() != 0){
								map.put("supplierCustomerListMap", realPsnCustomerList);
								cflag = personnelMapper.addSupplierCustomer(map) > 0;
								map.put("supplierBrandListMap", brandList);
								bflag = personnelMapper.addSupplierBrand(map) > 0;
							}else{
								cflag = false;
							}
						}
					}else{
						if(null != customerList && customerList.size() != 0){
							map.put("supplierCustomerListMap", customerList);
							cflag = personnelMapper.addSupplierCustomer(map) > 0;
						}
					}
					if(bflag && cflag){
						flag = personnelMapper.addOfSupplierBrandAndCustomer(personid) > 0;
					}
				}
			}
		}
		map2.put("flag", flag);
		return  map2;
	}
	
	@Override
	public List<Personnel> getPersListByOperType(String employetypeId)
			throws Exception {
		List<Personnel> retPersList = personnelMapper.getPersListByOperType(employetypeId);
		return retPersList;
	}
	
	@Override
	public List<Map> getDeptListByOpertype(String employetypeId)
			throws Exception {
		List<Map> deptList = personnelMapper.getDeptListByOpertype(employetypeId);
		for(Map map : deptList){
			String deptid = null != map.get("deptid") ? (String)map.get("deptid") : "";
			DepartMent departMent = getDepartmentByDeptid(deptid);
			if(null != departMent){
				map.put("deptname", departMent.getName());
			}else{
				map.put("deptname", "未指定公司");
			}
		}
		return deptList;
	}

	@Override
	public List<Personnel> getPersListByOperTypeAndDeptid(String employetypeId,
			String deptid) throws Exception {
		List<Personnel> retPersList = personnelMapper.getPersListByOperTypeAndDeptid(employetypeId,deptid);
		return retPersList;
	}
	@Override
	public List showBrandList(String personid) throws Exception {
		List<PersonnelBrand> list = personnelMapper.getBrandList(personid);
		if(list.size() != 0){
			for(PersonnelBrand personnelBrand : list){
				if(StringUtils.isNotEmpty(personnelBrand.getBrandid())){
					Brand brand = goodsMapper.getBrandInfo(personnelBrand.getBrandid());
					if(brand != null){
						personnelBrand.setBrandname(brand.getName());
					}
				}
			}
		}
		return list;
	}

	@Override
	public List showCustomerList(String personid) throws Exception {
		List<PersonnelCustomer> list = personnelMapper.getCustomerList(personid);
		if(list.size() != 0){
			for(PersonnelCustomer personnelCustomer : list){
				Map map = new HashMap();
				map.put("id", personnelCustomer.getCustomerid());
				Customer customer = customerMapper.getCustomerDetail(map);
				if(null != customer){
					personnelCustomer.setCustomername(customer.getName());
					personnelCustomer.setCustomersort(customer.getCustomersort());
					map.put("id", customer.getCustomersort());
					CustomerSort customerSort  = customerSortMapper.getCustomerSortDetail(map);
					if(null != customerSort){
						personnelCustomer.setCustomersortname(customerSort.getThisname());
					}
					personnelCustomer.setSalesarea(customer.getSalesarea());
					map.put("id", customer.getSalesarea());
					SalesArea salesArea = salesAreaMapper.getSalesAreaDetail(map);
					if(null != salesArea){
						personnelCustomer.setSalesareaname(salesArea.getThisname());
					}
				}
			}
		}
		return list;
	}
	
	@Override
	public PageData showCustomerList(PageMap pageMap) throws Exception {
		List<PersonnelCustomer> list = personnelMapper.getCustomerList2(pageMap);
		if(list.size() != 0){
			for(PersonnelCustomer personnelCustomer : list){
				Map map = new HashMap();
				map.put("id", personnelCustomer.getCustomerid());
				Customer customer = customerMapper.getCustomerDetail(map);
				if(null != customer){
					personnelCustomer.setCustomername(customer.getName());
				}
			}
		}
		PageData pageData = new PageData(personnelMapper.getCustomerListCount(pageMap),list,pageMap);
		return pageData;
	}
	
	@Override
	public PageData showBrandList(PageMap pageMap) throws Exception {
		List<PersonnelBrand> list = personnelMapper.getBrandList2(pageMap);
		if(list.size() != 0){
			for(PersonnelBrand personnelBrand : list){
				Brand brand = goodsMapper.getBrandInfo(personnelBrand.getBrandid());
				if(null != brand){
					personnelBrand.setBrandname(brand.getName());
				}
			}
		}
		PageData pageData = new PageData(personnelMapper.getBrandListCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public PageData showSupplierBrandList(PageMap pageMap) throws Exception {
		List<PersonnelBrand> list = personnelMapper.getSupplierBrandList(pageMap);
		if(list.size() != 0){
			for(PersonnelBrand personnelBrand : list){
				Brand brand = goodsMapper.getBrandInfo(personnelBrand.getBrandid());
				if(null != brand){
					personnelBrand.setBrandname(brand.getName());
				}
			}
		}
		PageData pageData = new PageData(personnelMapper.getSupplierBrandCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public Personnel getPersonnelByGCB(Map map) throws Exception {
		return personnelMapper.getPersonnelByGCB(map);
	}

	@Override
	public PersonnelBrand getBrandInfo(Integer id) throws Exception {
		return personnelMapper.getBrandInfo(id);
	}

	@Override
	public PageData getCustomerListForPsnCstm(PageMap pageMap) throws Exception {
		int customerCount = customerMapper.getCustomerCount(pageMap);
		List<Customer> list = customerMapper.getCustomerList(pageMap);
		List<PersonnelCustomer> newDataList = new ArrayList<PersonnelCustomer>();
		if(list.size() != 0){
			for(Customer customer : list){
				if(null != customer){
					PersonnelCustomer personnelCustomer = new PersonnelCustomer();
					personnelCustomer.setCustomerid(customer.getId());
					personnelCustomer.setCustomername(customer.getName());
					Map map = new HashMap();
					map.put("id", customer.getSalesarea());
					//所属区域
					SalesArea salesArea = getBaseSalesAreaMapper().getSalesAreaDetail(map);
					if(null != salesArea){
						personnelCustomer.setSalesareaname(salesArea.getName());
					}
					//所属分类
					map.put("id", customer.getCustomersort());
					CustomerSort customerSort = getBaseCustomerSortMapper().getCustomerSortDetail(map);
					if(null != customerSort){
						personnelCustomer.setCustomersortname(customerSort.getThisname());
					}
					newDataList.add(personnelCustomer);
				}
			}
		}
		PageData pageData = new PageData(customerCount,newDataList,pageMap);
		return pageData;
	}

	@Override
	public List<PersonnelCustomer> getCustomerListByCustomerid(String customerid)
			throws Exception {
		return personnelMapper.getCustomerByCustomerid(customerid);
	}

	@Override
	public List getPersonnelListByDeptid(String belongdeptid) throws Exception {
		return personnelMapper.getPersonnelListByDeptid(belongdeptid);
	}

	@Override
	public PageData getBrandListFromPsnBrandAndCustomer(PageMap pageMap)
			throws Exception {
		List<PersonnelBrand> list = personnelMapper.getBrandListFromPsnBrandAndCustomer(pageMap);
		int count = personnelMapper.getBrandCountFromPsnBrandAndCustomer(pageMap);
		if(list.size() != 0){
			for(PersonnelBrand personnelBrand : list){
				Brand brand = goodsMapper.getBrandInfo(personnelBrand.getBrandid());
				if(null != brand){
					personnelBrand.setBrandname(brand.getName());
				}
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		return pageData;
	}

	@Override
	public PageData getCustomerListFromPsnBrandAndCustomer(PageMap pageMap)
			throws Exception {
		List<PersonnelCustomer> list = personnelMapper.getCustomerListFromPsnBrandAndCustomer(pageMap);
		if(list.size() != 0){
			for(PersonnelCustomer personnelCustomer : list){
				Map map = new HashMap();
				map.put("id", personnelCustomer.getCustomerid());
				Customer customer = customerMapper.getCustomerDetail(map);
				if(null != customer){
					personnelCustomer.setCustomername(customer.getName());
					personnelCustomer.setCustomersort(customer.getCustomersort());
					map.put("id", customer.getCustomersort());
					CustomerSort customerSort  = customerSortMapper.getCustomerSortDetail(map);
					if(null != customerSort){
						personnelCustomer.setCustomersortname(customerSort.getThisname());
					}
					personnelCustomer.setSalesarea(customer.getSalesarea());
					map.put("id", customer.getSalesarea());
					SalesArea salesArea = salesAreaMapper.getSalesAreaDetail(map);
					if(null != salesArea){
						personnelCustomer.setSalesareaname(salesArea.getThisname());
					}
				}
			}
		}
		PageData pageData = new PageData(personnelMapper.getCustomerCountFromPsnBrandAndCustomer(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public PageData getPersonCustomerList(PageMap pageMap) throws Exception {
		List<PersonnelCustomer> list = personnelMapper.getCustomerList2(pageMap);
		int count = personnelMapper.getCustomerListCount(pageMap);
		if(list.size() != 0){
			for(PersonnelCustomer personnelCustomer : list){
				Map map = new HashMap();
				map.put("id", personnelCustomer.getCustomerid());
				Customer customer = customerMapper.getCustomerDetail(map);
				if(null != customer){
					personnelCustomer.setCustomername(customer.getName());
					personnelCustomer.setCustomersort(customer.getCustomersort());
					map.put("id", customer.getCustomersort());
					CustomerSort customerSort  = customerSortMapper.getCustomerSortDetail(map);
					if(null != customerSort){
						personnelCustomer.setCustomersortname(customerSort.getThisname());
					}
					personnelCustomer.setSalesarea(customer.getSalesarea());
					map.put("id", customer.getSalesarea());
					SalesArea salesArea = salesAreaMapper.getSalesAreaDetail(map);
					if(null != salesArea){
						personnelCustomer.setSalesareaname(salesArea.getThisname());
					}
				}
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		return pageData;
	}

	@Override
	public PageData getPersonSupplierCustomerList(PageMap pageMap)
			throws Exception {
		List<PersonnelCustomer> list = personnelMapper.getSupplierCustomerList(pageMap);
		if(list.size() != 0){
			for(PersonnelCustomer personnelCustomer : list){
				Map map = new HashMap();
				map.put("id", personnelCustomer.getCustomerid());
				Customer customer = customerMapper.getCustomerDetail(map);
				if(null != customer){
					personnelCustomer.setCustomername(customer.getName());
					personnelCustomer.setCustomersort(customer.getCustomersort());
					map.put("id", customer.getCustomersort());
					CustomerSort customerSort  = customerSortMapper.getCustomerSortDetail(map);
					if(null != customerSort){
						personnelCustomer.setCustomersortname(customerSort.getThisname());
					}
					personnelCustomer.setSalesarea(customer.getSalesarea());
					map.put("id", customer.getSalesarea());
					SalesArea salesArea = salesAreaMapper.getSalesAreaDetail(map);
					if(null != salesArea){
						personnelCustomer.setSalesareaname(salesArea.getThisname());
					}
				}
			}
		}
		PageData pageData = new PageData(personnelMapper.getSupplierCustomerCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public boolean checkBrandAndCustomerRepeat(String customerid,
			String brandidStr,String employetype) throws Exception {
		boolean retflag = true;
		if(StringUtils.isNotEmpty(brandidStr)){
			brandidStr = brandidStr.substring(0, brandidStr.lastIndexOf(","));
			String[] brandidArr = brandidStr.split(",");
			if(employetype.indexOf("3") != -1){
				for(String brandid : brandidArr){
					boolean flag = personnelMapper.checkExistBrandAndCusotmerPerson(brandid, customerid) > 0;
					retflag = retflag && !flag;
				}
			}else if(employetype.indexOf("7") != -1){
				for(String brandid : brandidArr){
					boolean flag = personnelMapper.checkExistBrandAndCusotmerSupplierPerson(brandid, customerid) > 0;
					retflag = retflag && !flag;
				}
			}
		}
		//true不存在false存在
		return retflag;
	}

	@Override
	public boolean checkBrandAndCustomerRepeat2(String brandid,
			String customeridStr,String employetype) throws Exception {
		boolean retflag = true;
		if(StringUtils.isNotEmpty(customeridStr)){
			customeridStr = customeridStr.substring(0, customeridStr.lastIndexOf(","));
			String[] customeridArr = customeridStr.split(",");
			if(employetype.indexOf("3") != -1){
				for(String customerid : customeridArr){
					boolean flag = personnelMapper.checkExistBrandAndCusotmerPerson(brandid, customerid) > 0;
					retflag = retflag && !flag;
				}
			}else if(employetype.indexOf("7") != -1){
				for(String customerid : customeridArr){
					boolean flag = personnelMapper.checkExistBrandAndCusotmerSupplierPerson(brandid, customerid) > 0;
					retflag = retflag && !flag;
				}
			}
		}
		//true不存在false存在
		return retflag;
	}

	public CustomerSortMapper getCustomerSortMapper() {
		return customerSortMapper;
	}

	public void setCustomerSortMapper(CustomerSortMapper customerSortMapper) {
		this.customerSortMapper = customerSortMapper;
	}

	public SalesAreaMapper getSalesAreaMapper() {
		return salesAreaMapper;
	}

	public void setSalesAreaMapper(SalesAreaMapper salesAreaMapper) {
		this.salesAreaMapper = salesAreaMapper;
	}

	@Override
	public boolean addPsnBrandCustomer(Map paramMap) throws Exception {
		String customerid = (String)paramMap.get("customerid");
		String personids = (String)paramMap.get("personids");
		String delpersonids = (String)paramMap.get("delpersonids");
		String companys = (String)paramMap.get("companys");
		Map listMap = new HashMap();
		boolean flag = true;
		//判断是否存在要删除的对应客户品牌业务员
//		if(StringUtils.isNotEmpty(delpersonids)){
//			String delpersonArr[];
//			if(delpersonids.indexOf(",") != -1){
//				delpersonArr = delpersonids.split(",");
//			}else{
//				delpersonArr = new String[1];
//				delpersonArr[0] = delpersonids;
//			}
//			for(String delpersonid : delpersonArr){
//				Map map = new HashMap();
//				map.put("personid", delpersonid);
//				map.put("customerid", customerid);
//				personnelMapper.deletePersonCustomerByParam(map);
//				personnelMapper.deleteBrandAndCustomerListByParam(map);
//			}
//		}
		//根据所属分公司获取所有该公司的品牌
		String[] companyArr;
		if(StringUtils.isEmpty(companys)){
			companyArr = new String[0];
		}else{
			companyArr = companys.split(",");
		}
		String brandStr = "";
		if(companyArr.length != 0){
			for(String company : companyArr){
				List<Brand> brandList = getBaseGoodsMapper().getBrandListWithParentByDeptid(company);
				for(Brand brand : brandList){
					if(StringUtils.isEmpty(brandStr)){
						brandStr = brand.getId();
					}else{
						brandStr += "," + brand.getId();
					}
				}
			}
		}
		Map map3 = new HashMap();
		map3.put("customerid", customerid);
		map3.put("brandids", brandStr);
		List<PersonnelBrandAndCustomer> doBACList = personnelMapper.getPersonListByCustomerWithCustomer(map3);
		for(PersonnelBrandAndCustomer doBAC : doBACList){
			map3.put("personid", doBAC.getPersonid());
			//删除对应客户表中的品牌业务员数据
			personnelMapper.deletePsnCustomerByPsnidAndCustomerid(map3);
			//删除对应客户对应品牌表中的品牌业务员数据
			Map map4 = new HashMap();
			map4.put("customerid", customerid);
			map4.put("personid", doBAC.getPersonid());
			personnelMapper.deleteBrandAndCustomerListByParam(map4);
		}
//		//验证对应客户对应品牌是否重复false存在true不存在
//		boolean ckflag = checkBrandAndCustomerRepeat(customerid, brandStr);
//		if(!ckflag){
//			//根据客户编码品牌编码删除对应品牌对应客户表中的数据
//			personnelMapper.deleteBrandAndCustomerByCustomerWithBrandids(map3);
//			
//		}
		if(StringUtils.isNotEmpty(personids)){
			String personArr[];
			if(personids.indexOf(",") != -1){
				personArr = personids.split(",");
			}else{
				personArr = new String[1];
				personArr[0] = personids;
			}
			for(String personid : personArr){
				Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(personid);
				if(null != personnel){
					List<PersonnelCustomer> clist = new ArrayList<PersonnelCustomer>();
					PersonnelCustomer personnelCustomer = new PersonnelCustomer();
					personnelCustomer.setCustomerid(customerid);
					personnelCustomer.setPersonid(personnel.getId());
					clist.add(personnelCustomer);
					listMap.put("customerListMap", clist);
					personnelMapper.addCustomer(listMap);
					
					List<PersonnelBrand> blist = getBasePersonnelMapper().getBrandList(personnel.getId());
					if(null == blist || blist.size() == 0){
						//若为品牌业务员，且所属部门不为空，获取其对应品牌列表数据
						if(personnel.getEmployetype().indexOf("3") != -1){
							blist = new ArrayList<PersonnelBrand>();
							List<Brand> brlist = getBaseGoodsMapper().getBrandListWithParentByDeptid(personnel.getBelongdeptid());
							if(brlist.size() != 0){
								for(Brand brand : brlist){
									PersonnelBrand personnelBrand = new PersonnelBrand();
									personnelBrand.setBrandid(brand.getId());
									personnelBrand.setPersonid(personnel.getId());
									blist.add(personnelBrand);
								}
								listMap.put("brandListMap", blist);
								personnelMapper.addBrand(listMap);
							}
						}
					}
					boolean bcflag = personnelMapper.addPsnBrandAndCustomerFromTbrandAndTcustomerBypsnWithcst(personnel.getId(),customerid) > 0;
					flag = bcflag && flag;
				}
			}
		}
		return flag;
	}

	@Override
	public boolean addOfEmployetypeToPsnBrandCustomer(Map paramMap)
			throws Exception {
		String customerid = (String)paramMap.get("customerid");
		String personids = (String)paramMap.get("personids");
		String companys = (String)paramMap.get("companys");
		String employetype = (String)paramMap.get("employetype");
		Map listMap = new HashMap();
		boolean flag = true;
		if(StringUtils.isNotEmpty(personids)){
			String[] personArr = personids.split(",");
			if("3".equals(employetype)){//品牌业务员
                //清除对应品牌为空且存在该客户的对应客户数据
                List<PersonnelCustomer> customerList = personnelMapper.getPsnCustomerListByMap(paramMap);
                for(PersonnelCustomer personnelCustomer : customerList){
                    Map map = new HashMap();
                    map.put("customerid", customerid);
                    map.put("personid", personnelCustomer.getPersonid());
                    //删除对应客户表中的品牌业务员数据
                    personnelMapper.deletePsnCustomerByPsnidAndCustomerid(map);

                    List<PersonnelBrand> brandList = personnelMapper.getBrandList(personnelCustomer.getPersonid());
                    if(brandList.size() != 0){
                        String brandStr = "";
                        for(PersonnelBrand personnelBrand : brandList){
                            if(StringUtils.isEmpty(brandStr)){
                                brandStr = personnelBrand.getBrandid();
                            }else{
                                brandStr += "," + personnelBrand.getBrandid();
                            }
                        }
                        if(StringUtils.isNotEmpty(brandStr)){
                            map.put("brandArr", brandStr.split(","));
                            //删除对应客户对应品牌表中的品牌业务员数据
                            personnelMapper.deleteBrandAndCustomerListByParam(map);
                        }
                    }
                }
                for(String personid : personArr){
                    List<PersonnelCustomer> clist = new ArrayList<PersonnelCustomer>();
                    PersonnelCustomer personnelCustomer = new PersonnelCustomer();
                    personnelCustomer.setCustomerid(customerid);
                    personnelCustomer.setPersonid(personid);
                    clist.add(personnelCustomer);
                    listMap.put("customerListMap", clist);
                    boolean cflag = personnelMapper.addCustomer(listMap) > 0;

                    personnelMapper.addPsnBrandAndCustomerFromTbrandAndTcustomerBypsnWithcst(personid,customerid);
                    flag = cflag && flag;
                }
			}else if("7".equals(employetype)){//厂家业务员
				//清除对应品牌为空且存在该客户的对应客户数据
				List<PersonnelCustomer> customerList = personnelMapper.getSupplierCustomerListByMapLeft(paramMap);
				for(PersonnelCustomer personnelCustomer : customerList){
					Map map = new HashMap();
					map.put("customerid", customerid);
					map.put("personid", personnelCustomer.getPersonid());
					//删除对应客户表中的厂家业务员数据
					personnelMapper.deleteSupplierCustomerListByParam(map);
					
					List<PersonnelBrand> brandList = personnelMapper.getSupplierBrandListNoPage(personnelCustomer.getPersonid());
					if(brandList.size() != 0){
						String brandStr = "";
						for(PersonnelBrand personnelBrand : brandList){
							if(StringUtils.isEmpty(brandStr)){
								brandStr = personnelBrand.getBrandid();
							}else{
								brandStr += "," + personnelBrand.getBrandid();
							}
						}
						if(StringUtils.isNotEmpty(brandStr)){
							map.put("brandArr", brandStr.split(","));
							//删除对应客户对应品牌表中的厂家业务员数据
							personnelMapper.deleteSupplierBrandAndCustomerByMap(map);
						}
					}
				}
				for(String personid : personArr){
					List<PersonnelCustomer> clist = new ArrayList<PersonnelCustomer>();
					PersonnelCustomer personnelCustomer = new PersonnelCustomer();
					personnelCustomer.setCustomerid(customerid);
					personnelCustomer.setPersonid(personid);
					clist.add(personnelCustomer);
					listMap.put("supplierCustomerListMap", clist);
					boolean cflag = personnelMapper.addSupplierCustomer(listMap) > 0;
					
					personnelMapper.addOfSupplierBrandAndCustomerBypsnWithcst(personid,customerid);
					flag = cflag && flag;
				}
			}
		}
		return flag;
	}

	@Override
	public boolean addOfEmployetypeToPsnBrandCustomer2(Map paramMap) throws Exception {
		boolean flag = true;

		String customerids = (String)paramMap.get("customerids");
		String personids = (String)paramMap.get("personids");
		String employetype = (String)paramMap.get("employetype");

		if(StringUtils.isNotEmpty(customerids) && StringUtils.isNotEmpty(personids)){
			String[] customeridArr = customerids.split(",");
			String[] personidArr = personids.split(",");
			List<String> psnList = new ArrayList<String>();
			Collections.addAll(psnList, personidArr);
			Map listMap = new HashMap();
			//判断分配业务员对应客户、对应品牌中是否已存在业务员,若已存在，则先清除该对应品牌对应客户后，再分配业务员
			if("3".equals(employetype)){//品牌业务员
				Map map = new HashMap();
				map.put("customeridArr",customeridArr);
				map.put("personidArr",personidArr);
				List<String> list = personnelMapper.getPersonidByPSNCustomerAndBrand(map);
				if(null != list && list.size() != 0){
					for(String personid : list){
						if(!personids.contains(personid)){
							psnList.add(personid);
						}
					}
				}
				for(String personid : psnList){
					map.put("personid",personid);
					//删除业务员对应客户数据
					personnelMapper.deletePsnCustomerByPsnidAndCustomerid(map);
					//删除对应客户对应品牌数据
					personnelMapper.deleteBrandAndCustomerByPersonid(personid);
				}

				//根据分配的客户新增对应客户
				for(String personid : personidArr){
					List<PersonnelCustomer> clist = new ArrayList<PersonnelCustomer>();
					for(String customerid : customeridArr){
						PersonnelCustomer personnelCustomer = new PersonnelCustomer();
						personnelCustomer.setCustomerid(customerid);
						personnelCustomer.setPersonid(personid);
						clist.add(personnelCustomer);
					}
					listMap.put("customerListMap", clist);
					boolean cflag = personnelMapper.addCustomer(listMap) > 0;
					flag = flag && cflag;
				}
				for(String personid : psnList){
					personnelMapper.addPsnBrandAndCustomerFromTbrandAndTcustomer(personid);
				}
			}else if("7".equals(employetype)){//厂家业务员
				Map map = new HashMap();
				map.put("customeridArr",customeridArr);
				map.put("personidArr",personidArr);
				List<String> list = personnelMapper.getPersonidBySupplierPSNCustomerAndBrand(map);
				if(null != list && list.size() != 0){
					for(String personid : list){
						if(!personids.contains(personid)){
							psnList.add(personid);
						}
					}
				}
				for(String personid : psnList){
					map.put("personid",personid);
					//删除业务员对应客户数据
					personnelMapper.deleteSupplierCustomerListByParam(map);
					//删除对应客户对应品牌数据
					personnelMapper.deleteSupplierBrandAndCustomerByPersonid(personid);
				}

				//根据分配的客户新增对应客户
				for(String personid : personidArr){
					List<PersonnelCustomer> clist = new ArrayList<PersonnelCustomer>();
					for(String customerid : customeridArr){
						PersonnelCustomer personnelCustomer = new PersonnelCustomer();
						personnelCustomer.setCustomerid(customerid);
						personnelCustomer.setPersonid(personid);
						clist.add(personnelCustomer);
					}
					listMap.put("supplierCustomerListMap", clist);
					boolean cflag = personnelMapper.addSupplierCustomer(listMap) > 0;
					flag = flag && cflag;
				}
				for(String personid : psnList){
					personnelMapper.addOfSupplierBrandAndCustomer(personid);
				}
			}
		}
		return flag;
	}

	@Override
	public List getPersonListByCustoemrids(String customerids) throws Exception {
		return personnelMapper.getPersonListByCustoemrids(customerids);
	}

	@Override
	public boolean deletePersonCustomer(String customerids,String personid,String employetype) throws Exception {
		boolean retfalg = true;
		if(StringUtils.isNotEmpty(customerids) && StringUtils.isNotEmpty(personid)){
			if(customerids.indexOf(",") != -1){
				String[] customeridArr = customerids.split(",");
				for(String customerid : customeridArr){
					Map map = new HashMap();
					map.put("personid", personid);
					map.put("customerid", customerid);
					if(StringUtils.isNotEmpty(employetype)){
						if(employetype.indexOf("3") != -1){
							PersonnelCustomer personnelCustomer = personnelMapper.getPersonCustomerInfo(map);
							if(null != personnelCustomer){
								boolean flag = personnelMapper.deletePersonCustomerByParam(map) > 0;
								personnelMapper.deleteBrandAndCustomerByCustomerid(map);
								retfalg = flag && retfalg;
							}else{
								retfalg = retfalg && true;
							}
						}else if (employetype.indexOf("7") != -1) {
							PersonnelCustomer personnelCustomer = personnelMapper.getPersonSupplierCustomerInfo(map);
							if(null != personnelCustomer){
								boolean flag = personnelMapper.deleteSupplierCustomerListByParam(map) > 0;
								personnelMapper.deleteSupplierBrandAndCustomerByMap(map);
								retfalg = flag && retfalg;
							}else{
								retfalg = retfalg && true;
							}
						}
					}
				}
			}else{
				Map map = new HashMap();
				map.put("personid", personid);
				map.put("customerid", customerids);
				if(StringUtils.isNotEmpty(employetype)){
					if(employetype.indexOf("3") != -1){
						PersonnelCustomer personnelCustomer = personnelMapper.getPersonCustomerInfo(map);
						if(null != personnelCustomer){
							boolean flag = personnelMapper.deletePersonCustomerByParam(map) > 0;
							personnelMapper.deleteBrandAndCustomerByCustomerid(map);
							retfalg = flag && retfalg;
						}else{
							retfalg = retfalg && true;
						}
					}else if (employetype.indexOf("7") != -1) {
						PersonnelCustomer personnelCustomer = personnelMapper.getPersonSupplierCustomerInfo(map);
						if(null != personnelCustomer){
							boolean flag = personnelMapper.deleteSupplierCustomerListByParam(map) > 0;
							personnelMapper.deleteSupplierBrandAndCustomerByMap(map);
							retfalg = flag && retfalg;
						}else{
							retfalg = retfalg && true;
						}
					}
				}
			}
		}
		return retfalg;
	}

	@Override
	public boolean addPersonCustomer(String customerids,String personid,String employetype) throws Exception {
		boolean retfalg = true;
		if(StringUtils.isNotEmpty(customerids) && StringUtils.isNotEmpty(personid)){
			String[] customeridArr = customerids.split(",");
			for(String customerid : customeridArr){
				Map map = new HashMap();
				map.put("personid", personid);
				map.put("customerid", customerid);
				if(StringUtils.isNotEmpty(employetype)){
					if(employetype.indexOf("3") != -1){
					    //删除人员对应客户
					    personnelMapper.deletePersonCustomerByParam(map);
					    retfalg = personnelMapper.addPersonnelCustomer(personid,customerid)>0;

					}else if (employetype.indexOf("7") != -1) {
					    personnelMapper.deleteSupplierCustomerListByParam(map);
					    retfalg = personnelMapper.addSupplierCustomerDetail(personid,customerid)>0;
					}
				}
			}
			if(employetype.indexOf("3") != -1){
                //清空人员编码对应客户品牌表数据
                personnelMapper.deleteBrandAndCustomerByPersonid(personid);
                personnelMapper.addPsnBrandAndCustomerFromTbrandAndTcustomer(personid);
            }else if (employetype.indexOf("7") != -1) {
                //清空人员编码对应客户品牌表数据
                personnelMapper.deleteSupplierBrandAndCustomerByPersonid(personid);
                personnelMapper.addOfSupplierBrandAndCustomer(personid);
            }
		}
		return retfalg;
	}
	@Override
	public boolean deletePersonBrand(String brandids, String personid)
			throws Exception {
		boolean retfalg = true;
		Personnel personnel = personnelMapper.getPersonnelInfo(personid);
		if(null != personnel){
			String[] brandidArr = brandids.split(",");
			if(personnel.getEmployetype().indexOf("3") != -1){
				for(String brandid : brandidArr){
					Map map = new HashMap();
					map.put("personid", personid);
					map.put("brandid", brandid);
					PersonnelBrand personnelBrand = personnelMapper.getPersonBrandInfo(map);
					if(null != personnelBrand){
						boolean flag = personnelMapper.deletePersonBrandByParam(map)>0;
						personnelMapper.deleteBrandAndCustomerByBrandid(map);
						retfalg = flag && retfalg;
					}
				}
			}else if(personnel.getEmployetype().indexOf("7") != -1){
				for(String brandid : brandidArr){
					Map map = new HashMap();
					map.put("personid", personid);
					map.put("brandid", brandid);
					PersonnelBrand personnelBrand = personnelMapper.getPersonSupplierBrandInfo(map);
					if(null != personnelBrand){
						boolean flag = personnelMapper.deleteSupplierBrandListByParam(map)>0;
						personnelMapper.deleteSupplierBrandAndCustomerByMap(map);
						retfalg = flag && retfalg;
					}
				}
			}
		}
		return retfalg;
	}

    /**
     * 根据对应品牌编码删除人员对应品牌
     *
     * @param brandids
     * @param personid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 5, 2014
     */
    @Override
    public boolean addPersonBrand(String brandids, String personid) throws Exception {
        Personnel personnel = personnelMapper.getPersonnelInfo(personid);
        boolean retfalg = true;
		if(StringUtils.isNotEmpty(brandids) && null!=personnel){
		    String employetype = personnel.getEmployetype();
			String[] brandidArr = brandids.split(",");
			for(String brandid : brandidArr){
			    if(StringUtils.isEmpty(brandid)){
				    continue;
                }
				Map map = new HashMap();
				map.put("personid", personid);
				map.put("brandid", brandid);

				if(StringUtils.isNotEmpty(employetype)){
					if(employetype.indexOf("3") != -1){
					    //删除人员对应品牌
					    personnelMapper.deletePersonBrandByParam(map);
					    retfalg = personnelMapper.addPersonBrand(personid,brandid)>0;
					}else if (employetype.indexOf("7") != -1) {
					    personnelMapper.deleteSupplierBrandListByParam(map);
					    retfalg = personnelMapper.addSupplierBrandDetail(personid,brandid)>0;
					}
				}
			}
			if(employetype.indexOf("3") != -1){
                //清空人员编码对应客户品牌表数据
                personnelMapper.deleteBrandAndCustomerByPersonid(personid);
                personnelMapper.addPsnBrandAndCustomerFromTbrandAndTcustomer(personid);
            }else if (employetype.indexOf("7") != -1) {
                //清空人员编码对应客户品牌表数据
                personnelMapper.deleteSupplierBrandAndCustomerByPersonid(personid);
                personnelMapper.addOfSupplierBrandAndCustomer(personid);
            }
		}
		return retfalg;
    }

    @Override
	public List getPersonCustomerListByCustomerids(String customerids)
			throws Exception {
		List<PersonnelCustomer> pcList = new ArrayList<PersonnelCustomer>();
		if(StringUtils.isNotEmpty(customerids)){
			List<Customer> clist = getBaseCustomerMapper().getCustomerListByCustomerids(customerids);
			if(clist.size() != 0){
				for(Customer customer : clist){
					PersonnelCustomer pCustomer = new PersonnelCustomer();
					pCustomer.setCustomerid(customer.getId());
					pCustomer.setCustomername(customer.getName());
					Map map = new HashMap();
					if(StringUtils.isNotEmpty(customer.getSalesarea())){
						map.put("id", customer.getSalesarea());
						SalesArea salesArea = getBaseSalesAreaMapper().getSalesAreaDetail(map);
						if(null != salesArea){
							pCustomer.setSalesareaname(salesArea.getThisname());
						}
					}
					if(StringUtils.isNotEmpty(customer.getCustomersort())){
						map.put("id", customer.getCustomersort());
						CustomerSort customerSort = getBaseCustomerSortMapper().getCustomerSortDetail(map);
						if(null != customerSort){
							pCustomer.setCustomersortname(customerSort.getThisname());
						}
					}
					pcList.add(pCustomer);
				}
			}
		}
		return pcList;
	}

	@Override
	public List getPersonBrandListByBrandids(String brandids) throws Exception {
		return personnelMapper.getPersonBrandListByBrandids(brandids);
	}

	@Override
	public PageData getPersonCustomerListByCustomerids(String customerids,PageMap pageMap)
			throws Exception {
		PageData pageData = null;
		List<PersonnelCustomer> pcList = new ArrayList<PersonnelCustomer>();
		if(StringUtils.isNotEmpty(customerids)){
			List<Customer> clist = getBaseCustomerMapper().getCustomerListByCustomerids(customerids);
			if(clist.size() != 0){
				for(Customer customer : clist){
					PersonnelCustomer pCustomer = new PersonnelCustomer();
					pCustomer.setCustomerid(customer.getId());
					pCustomer.setCustomername(customer.getName());
					Map map = new HashMap();
					if(StringUtils.isNotEmpty(customer.getSalesarea())){
						map.put("id", customer.getSalesarea());
						SalesArea salesArea = getBaseSalesAreaMapper().getSalesAreaDetail(map);
						if(null != salesArea){
							pCustomer.setSalesareaname(salesArea.getThisname());
						}
					}
					if(StringUtils.isNotEmpty(customer.getCustomersort())){
						map.put("id", customer.getCustomersort());
						CustomerSort customerSort = getBaseCustomerSortMapper().getCustomerSortDetail(map);
						if(null != customerSort){
							pCustomer.setCustomersortname(customerSort.getThisname());
						}
					}
					pcList.add(pCustomer);
				}
				pageData = new PageData(clist.size(),clist,pageMap);
			}
		}
		return pageData;
	}

	@Override
	public List getBrandToPersonList(String brandids,String personid) throws Exception {
		List<PersonnelBrand> list = new ArrayList<PersonnelBrand>();
		if(StringUtils.isNotEmpty(brandids)){
			Map map = new HashMap();
			map.put("brandArr", brandids.substring(0, brandids.lastIndexOf(",")).split(","));
			List<Brand> brandList = goodsMapper.getBrandListByBrandids(map);
			for(Brand brand : brandList){
				PersonnelBrand personnelBrand = new PersonnelBrand();
				personnelBrand.setPersonid(personid);
				personnelBrand.setBrandid(brand.getId());
				personnelBrand.setBrandname(brand.getName());
				list.add(personnelBrand);
			}
		}
		return list;
	}

	@Override
	public List getSupplierBrandListNoPage(String personid) throws Exception {
		return personnelMapper.getSupplierBrandListNoPage(personid);
	}
	
	@Override
	public List getSupplierCustomerListNoPage(String personid) throws Exception {
		return personnelMapper.getSupplierCustomerListNoPage(personid);
	}

	@Override
	public List getSupplierPersonListByCustoemrids(String customerids)
			throws Exception {
		return personnelMapper.getSupplierPersonListByCustoemrids(customerids);
	}

	@Override
	public boolean addNoPersonBrandAndCustomer(List<Map<String, String>> list)
			throws Exception {
		return personnelMapper.addNoPersonBrandAndCustomer(list) > 0;
	}

    @Override
    public Map checkPersonsIsRepeatBrandids(String sql) throws Exception {
        Map map = new HashMap();
        boolean flag = true;
        String msg = "";
        List<Map> list = personnelMapper.checkPersonsIsRepeatBrandids(sql);
        if(null != list && list.size() != 0){
            flag = false;
            String personmsg = "";
            for(Map map1 : list){
                String personid = (String)map1.get("personid");
                Personnel personnel1 = personnelMapper.getPersonnelInfo(personid);
                if(null != personnel1){
                    if(StringUtils.isEmpty(personmsg)){
                        personmsg = personnel1.getName();
                    }else{
                        personmsg += "、" + personnel1.getName();
                    }
                }
            }
            if(StringUtils.isNotEmpty(personmsg)){
                msg = "业务员" + personmsg + "存在重复的品牌;";
            }
        }
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

	@Override
	public boolean deletePersonFiles(String personid, String fileid)
			throws Exception {
		String newadjunctids = "";
		boolean flag = false;
		Personnel personnel = personnelMapper.getPersonnelInfo(personid);
		if(null != personnel){
			String adjunctids = personnel.getAdjunctid();
			if(StringUtils.isNotEmpty(adjunctids)){
				String[] adjunctidArr = adjunctids.split(",");
				for(String adjunctid : adjunctidArr){
					if(!adjunctid.equals(fileid)){
						if(StringUtils.isEmpty(newadjunctids)){
							newadjunctids = adjunctid;
						}else{
							newadjunctids += "," + adjunctid;
						}
					}
				}
			}
			Personnel personnel2 = new Personnel();
			personnel2.setOldId(personnel.getId());
			personnel2.setAdjunctid(newadjunctids);
			flag = personnelMapper.editPersonnelInfo(personnel2) > 0;
		}
		return flag;
	}

	@Override
	public boolean addPersonFiles(String personid, String fileid)
			throws Exception {
		boolean flag = false;
		Personnel personnel = personnelMapper.getPersonnelInfo(personid);
		if(null != personnel){
			String adjunctids = personnel.getAdjunctid();
			if(StringUtils.isEmpty(adjunctids)){
				adjunctids = fileid;
			}else{
				adjunctids += "," + fileid;
			}
			Personnel personnel2 = new Personnel();
			personnel2.setOldId(personnel.getId());
			personnel2.setAdjunctid(adjunctids);
			flag = personnelMapper.editPersonnelInfo(personnel2) > 0;
		}
		return flag;
	}

	@Override
	public boolean deleteNoPersonBrandAndCustomer(Map map1) throws Exception {
		boolean flag =false;
		String type = (String)map1.get("type");
		if("delcustomer".equals(type)){
			if(map1.containsKey("brandList")){
				flag = personnelMapper.deleteNoPersonBrandAndCustomerDelCustomer(map1) > 0;
			}
		}else if("delbrand".equals(type)){
			if(map1.containsKey("customerList")){
				flag = personnelMapper.deleteNoPersonBrandAndCustomerDelBrand(map1) > 0;
			}
		}
		return flag;
	}

	@Override
	public boolean doClearCustomerToPsn(String employetype, String customerids) throws Exception {
		boolean flag = false;
		if(StringUtils.isNotEmpty(customerids)){
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			String[] customeridArr = customerids.split(",");
			if("3".equals(employetype)){//品牌业务员
				for(String customerid : customeridArr){
					personnelMapper.deleteCustomerListByCustomerid(customerid);

					List<PersonnelBrandAndCustomer> delList = personnelMapper.getBrandCustomerListByCustomerid(customerid);
					for(PersonnelBrandAndCustomer noBAC : delList){
						Map map = new HashMap();
						map.put("delpersonid", noBAC.getPersonid());
						map.put("customerid", customerid);
						map.put("employetype", "3");
						map.put("brandid", noBAC.getBrandid());

						list.add(map);
					}

					personnelMapper.deleteBrandAndCustomerByCustomer(customerid);
				}
			}else if("7".equals(employetype)){//厂家业务员
				for(String customerid : customeridArr){
					Map map = new HashMap();
					map.put("customerid",customerid);
					personnelMapper.deleteSupplierCustomerListByParam(map);

					List<PersonnelBrandAndCustomer> delList = personnelMapper.getSupplierBrandCustomerList(map);
					for(PersonnelBrandAndCustomer noBAC : delList){
						Map map2 = new HashMap();
						map2.put("delpersonid", noBAC.getPersonid());
						map2.put("customerid", customerid);
						map2.put("employetype", "7");
						map2.put("brandid", noBAC.getBrandid());
						list.add(map2);
					}

					personnelMapper.deleteSupplierBrandAndCustomerByMap(map);
				}
			}

			if(list.size() != 0){
				flag = personnelMapper.addNoPersonBrandAndCustomer(list) > 0;
			}else{
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public boolean doTotalBillsBranduserReset() throws Exception {
		int count = personnelMapper.updateTotalBillsBranduser();
		return count >= 0;
	}
}

