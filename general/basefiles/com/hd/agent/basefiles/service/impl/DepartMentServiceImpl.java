/**
 * @(#)DepartMentServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-21 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.DepartMentMapper;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.service.IDepartMentService;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class DepartMentServiceImpl extends FilesLevelServiceImpl implements IDepartMentService {
	
	private DepartMentMapper departMentMapper;

	public DepartMentMapper getDepartMentMapper() {
		return departMentMapper;
	}

	public void setDepartMentMapper(DepartMentMapper departMentMapper) {
		this.departMentMapper = departMentMapper;
	}
	
    @Override
	public List showDepartmentNameList() throws Exception{
		List list=departMentMapper.getDepartmentNameList();
		return list;
	}
	@Override
	public List showDepartmentOpenList() throws Exception{
		List list = departMentMapper.showDepartmentOpenList();
		return list;
	}
    @Override
	public PageData showDepartmentList(PageMap pageMap) throws Exception{
		//单表取字段权限
		String cols=getAccessColumnList("t_base_department", null);
		pageMap.setCols(cols);
		int total=departMentMapper.getDepartmentCount(pageMap);
		List list=departMentMapper.getDepartmentList(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		return pageData;
	}
	
    @Override
	public DepartMent showDepartMentInfo(String id) throws Exception{
		DepartMent departMent=departMentMapper.getDepartmentInfo(id);
		return departMent;
	}

	@Override
	public DepartMent getDepartMentInfoByNameLimitOne(String name) throws Exception {
		return departMentMapper.getDepartmentInfoLimitOne(name);
	}

	@Override
	public boolean addDepartMent(DepartMent departMent)throws Exception{
		//判断部门档案是否重复
		String str = departMentMapper.isExistDepartmentId(departMent.getId());
		if(StringUtils.isEmpty(str)){
			if(StringUtils.isNotEmpty(departMent.getDepttype()) && departMent.getDepttype().indexOf(",") != -1){
				departMent.setDepttype(departMent.getDepttype().substring(0, departMent.getDepttype().lastIndexOf(",")));
			}
			int i=departMentMapper.addDepartment(departMent);
			return i>0;
		}
		else{
			return false;
		}
	}

    @Override
	public Map addDRdeptMent(List<DepartMent> list)throws Exception{
        Map returnMap = new HashMap();
        boolean flag = false;
        int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0,errorNum = 0,levelNum=0,reptthisNum = 0;
        String closeVal = "",repeatVal = "",failStr = "",errorVal = "",levelVal = "",reptthisnameVal = "";
		if(null != list && list.size() != 0){
            String tn = "t_base_department";
            List levelList = showFilesLevelList(tn);
            if(levelList.size() != 0){
                for(DepartMent departMent : list){
                    if(StringUtils.isEmpty(departMent.getId())){
                        continue;
                    }
                    //根据档案级次信息根据编码获取本级编码
                    Map map = getObjectThisidByidCaseFilesLevel(tn,departMent.getId());
                    if(null != map && !map.isEmpty()){
                        String id = departMent.getId();
                        String thisid = (null != map.get("thisid")) ? map.get("thisid").toString() : "";
                        String pid = (null != map.get("pid")) ? map.get("pid").toString() : "";
                        try {
                            String str = departMentMapper.isExistDepartmentId(departMent.getId());
                            if(StringUtils.isEmpty(str)){
								//判断本机名称是否重复
								if(StringUtils.isEmpty(departMentMapper.checkSoleName(departMent.getName()))){
									departMent.setPid(pid);
									departMent.setThisid(thisid);
									SysUser sysUser = getSysUser();
									departMent.setAdddeptid(sysUser.getDepartmentid());
									departMent.setAdduserid(sysUser.getUserid());
									if(StringUtils.isEmpty(departMent.getState())){
										departMent.setState("1");
									}
									flag = departMentMapper.addDepartment(departMent) > 0;
									if(flag){
										successNum++;
									}
									else{
										if(StringUtils.isNotEmpty(failStr)){
											failStr += "," + departMent.getId();
										}
										else{
											failStr = departMent.getId();
										}
										failureNum++;
									}
								}else{
									if(StringUtils.isEmpty(reptthisnameVal)){
										reptthisnameVal = departMent.getId();
									}else{
										reptthisnameVal += "," + departMent.getId();
									}
									reptthisNum++;
								}
                            }
                            else{
                                DepartMent departMent2 = departMentMapper.getDepartmentInfo(departMent.getId());
                                if(null != departMent2){
                                    if("0".equals(departMent2.getState())){//禁用状态，不允许导入
                                        if(StringUtils.isEmpty(closeVal)){
                                            closeVal = departMent2.getId();
                                        }
                                        else{
                                            closeVal += "," + departMent2.getId();
                                        }
                                        closeNum++;
                                    }
                                    else{
                                        if(StringUtils.isEmpty(repeatVal)){
                                            repeatVal = departMent2.getId();
                                        }
                                        else{
                                            repeatVal += "," + departMent2.getId();
                                        }
                                        repeatNum++;
                                    }
                                }
                            }
                        }catch (Exception e){
                            if(StringUtils.isEmpty(errorVal)){
                                errorVal = id;
                            }else{
                                errorVal += "," + id;
                            }
                            errorNum++;
                            continue;
                        }
                    }else{
                        levelNum++;
                        if(StringUtils.isNotEmpty(levelVal)){
                            levelVal += "," + departMent.getId();
                        }
                        else{
                            levelVal = departMent.getId();
                        }
                    }
                }
                //末级标志
                isTreeLeaf();
            }else{
                returnMap.put("nolevel", true);
            }
		}
        returnMap.put("flag", flag);
        returnMap.put("success", successNum);
        returnMap.put("failure", failureNum);
        returnMap.put("failStr", failStr);
        returnMap.put("repeatNum", repeatNum);
        returnMap.put("closeNum", closeNum);
        returnMap.put("closeVal", closeVal);
        returnMap.put("repeatVal", repeatVal);
        returnMap.put("levelNum", levelNum);
        returnMap.put("levelVal", levelVal);
		returnMap.put("reptthisNum", reptthisNum);
		returnMap.put("reptthisnameVal", reptthisnameVal);
		return returnMap;
	}
	
    @Override
	public Map editDepartMent(DepartMent departMent)throws Exception{
        Map map = new HashMap();
        Map map2 = new HashMap();
        String msg = "";
		//保存修改前判断数据是否已经被加锁 可以修改,true可以操作。false不可以操作。
		boolean lockFlag = isLockEdit("t_base_department", departMent.getId());
		if(lockFlag){
			//获取修改前部门信息
			DepartMent beforeDept=departMentMapper.getDepartmentInfo(departMent.getOldId());
			//判断是否可以进行修改操作，若可以修改，更新级联关系数据
			boolean flag = canBasefilesIsEdit("t_base_department", beforeDept, departMent);
			if(flag){
                if(!beforeDept.getThisid().equals(departMent.getThisid()) || !beforeDept.getName().equals(departMent.getName())){
                    List<DepartMent> childList = departMentMapper.getDepartMentChildList(beforeDept.getId());
                    //若编码改变，下属所有的任务分类编码应做对应的替换
                    if(childList.size() != 0){
                        for(DepartMent repeatDM : childList){
                            repeatDM.setOldId(repeatDM.getId());
                            if(!beforeDept.getThisid().equals(departMent.getThisid())){
                                String newid = repeatDM.getId().replaceFirst(beforeDept.getThisid(), departMent.getThisid()).trim();
                                repeatDM.setId(newid);
                                String newpid = repeatDM.getPid().replaceFirst(beforeDept.getThisid(), departMent.getThisid()).trim();
                                repeatDM.setPid(newpid);
                            }
                            if(!beforeDept.getName().equals(departMent.getName())){
                                //若本级名称改变，下属所有的任务分类名称应做对应的替换
                                String newname = repeatDM.getName().replaceFirst(beforeDept.getName(), departMent.getName());
                                repeatDM.setName(newname);
                            }
                            Tree node = new Tree();
                            node.setId(repeatDM.getId());
                            node.setParentid(repeatDM.getPid());
                            node.setText(repeatDM.getName());
                            node.setState(repeatDM.getState());
                            map2.put(repeatDM.getOldId(), node);
                        }
                        departMentMapper.editDepartMentBatch(childList);
                    }
                }
				boolean flag1 = departMentMapper.editDepartMent(departMent) > 0;
                if(flag1){
					if(null != beforeDept && null != departMent && !beforeDept.getName().equals(departMent.getName())){
						departMentMapper.updateCustomerSalesdeptname(departMent.getId());
					}

                    Tree node = new Tree();
                    node.setId(departMent.getId());
                    node.setParentid(departMent.getPid());
                    node.setText(departMent.getName());
                    node.setState(departMent.getState());
                    map2.put(departMent.getOldId(), node);
                }
                map.put("flag", flag1);
                map.put("nodes", map2);
			}else{
                msg = "数据不允许修改!";
                map.put("flag", flag);
            }
		}
        map.put("msg",msg);
		return map;
	}
	
    @Override
	public boolean editDeptMentAllNextChange(DepartMent departMent)throws Exception{
		int i=departMentMapper.editDeptMentAllNextChange(departMent);
		return i>0;
	}
	
    @Override
	public boolean deleteDepartMent(String id)throws Exception{
		return departMentMapper.deleteDepartMent(id) > 0;
	}
	
    @Override
	public boolean disableDepartMent(String id)throws Exception{
		DepartMent departMent = new DepartMent();
		departMent.setId(id);
		SysUser sysUser = getSysUser();
		departMent.setCloseuserid(sysUser.getUserid());
		int i=departMentMapper.disableDepartMent(departMent);
		return i>0;
	}
	
    @Override
	public boolean enableDepartMent(String id)throws Exception{
		DepartMent departMent = new DepartMent();
		departMent.setId(id);
		SysUser sysUser = getSysUser();
		departMent.setOpenuserid(sysUser.getUserid());
		int i=departMentMapper.enableDepartMent(departMent);
		return (i>0 && isTreeLeaf());
	}

    @Override
	public String isExistDepartmentId(String id)throws Exception{
		String str=departMentMapper.isExistDepartmentId(id);
		return str;
	}

    @Override
	public Map deleteDepartMentAll(String pid,String id)throws Exception{
		//DepartMent departMentInfo = departMentMapper.getDepartmentInfo(pid);
		boolean retFlag = false;
		String newDeptIdStr = "";
		int isSuc = 0,isFail = 0;
		//根据父级id获取当前父级与其下子级的部门列表
		List<DepartMent> deptList = departMentMapper.getPidAllDeptList(pid);
		if(deptList.size() != 0){
			for(DepartMent departMent:deptList){
				if(null != departMent){
					if(!"4".equals(departMent.getState()) && !"1".equals(departMent.getState())){//状态不为新增、启用，可允许删除
						newDeptIdStr = departMent.getId() + "," + newDeptIdStr;
					}
					else{
						isFail += 1;
					}
				}
			}
			if(StringUtils.isNotEmpty(newDeptIdStr)){
				String[] delDeptIdArr = newDeptIdStr.split(",");
				int i=departMentMapper.deleteDepartMentAll(delDeptIdArr);
				if(i > 0){
					isSuc = i;
					isFail = delDeptIdArr.length - i + isFail;
					retFlag = true;
				}
				else{
					isFail = delDeptIdArr.length + isFail;
				}
			}
		}
		Map retMap = new HashMap();
		retMap.put("sucMes", "成功删除"+isSuc+"条记录；");
		retMap.put("failMes", "无效删除"+isFail+"条记录；");
		retMap.put("retFlag", retFlag);
		return retMap;
	}

    @Override
	public Map disableDepartMentAll(PageMap pageMap)throws Exception{
		int isSuc=0,isFail=0;
		String newDeptIds ="";
		boolean retFlag = false;
		if(pageMap != null){
			String pId = pageMap.getCondition().get("id").toString();
			List<DepartMent> deptList = departMentMapper.getPidAllDeptList(pId);
			for(DepartMent departMent:deptList){
				if(!"1".equals(departMent.getState())){//当状态不为启用 
					isFail += 1;
				}
				else{
					newDeptIds = departMent.getId() + "," +newDeptIds;
				}
			}
			if(StringUtils.isNotEmpty(newDeptIds)){
				String[] deptIdsArr = newDeptIds.split(",");
				Map paramMap = new HashMap();
				paramMap.put("closeuserid", pageMap.getCondition().get("closeuserid").toString());
				paramMap.put("deptIdsArr", deptIdsArr);
				int i=departMentMapper.disableDepartMentAll(paramMap);
				if(i > 0){
					isSuc = i;
					isFail = deptIdsArr.length - i + isFail;
					retFlag = true;
				}
				else{
					isFail = deptIdsArr.length + isFail;
				}
			}
		}
		Map retMap = new HashMap();
		retMap.put("sucMes", "成功禁用"+isSuc+"条记录；");
		retMap.put("failMes", "无效禁用"+isFail+"条记录；");
		retMap.put("retFlag", retFlag);
		return retMap;
	}

    @Override
	public Map enableDepartMentAll(PageMap pageMap)throws Exception{
		int isSuc=0,isFail=0;
		String newDeptIds ="";
		boolean retFlag = false;
		if(pageMap != null){
			String pId = pageMap.getCondition().get("id").toString();
			List<DepartMent> deptList = departMentMapper.getPidAllDeptList(pId);
			for(DepartMent departMent:deptList){
				if(!"2".equals(departMent.getState()) && !"0".equals(departMent.getState())){//当状态不为保存或禁用
					isFail += 1;
				}
				else{
					newDeptIds = departMent.getId() + "," +newDeptIds;
				}
			}
			if(StringUtils.isNotEmpty(newDeptIds)){
				String[] deptIdsArr = newDeptIds.split(",");
				Map paramMap = new HashMap();
				paramMap.put("openuserid", pageMap.getCondition().get("openuserid").toString());
				paramMap.put("deptIdsArr", deptIdsArr);
				int i=departMentMapper.enableDepartMentAll(paramMap);
				if(i > 0){
					isSuc = i;
					isFail = deptIdsArr.length - i + isFail;
					retFlag = (true && isTreeLeaf());
				}
				else{
					isFail = deptIdsArr.length + isFail;
				}
			}
		}
		Map retMap = new HashMap();
		retMap.put("sucMes", "成功启用"+isSuc+"条记录；");
		retMap.put("failMes", "无效启用"+isFail+"条记录；");
		retMap.put("retFlag", retFlag);
		return retMap;
	}

    @Override
	public List getDBUnimportDeptList(Map queryMap) throws Exception{
		List list=departMentMapper.getDBUnimportDeptList(queryMap);
		return list;
	}

    @Override
	public List showDeptListByQuery()throws Exception{
		List list=departMentMapper.getDeptListByQuery();
		return list;
	}

	public boolean isTreeLeaf()throws Exception{
		int count=0;
		List<DepartMent> list = departMentMapper.getDeptListByState();//获取暂存以外的部门列表数据
		if(list.size() != 0){
			for(DepartMent departMent: list){
				String pid = departMentMapper.setIsTreeLeaf(departMent.getId());//判断pid是否存在,若存在则为末及标志，否则，不是
				if(!StringUtils.isNotEmpty(pid)){
					departMent.setLeaf("1");
				}
				else{
					departMent.setLeaf("0");
				}
				departMent.setOldId(departMent.getId());
				//获取修改前部门信息
				DepartMent beforeDept=departMentMapper.getDepartmentInfo(departMent.getOldId());
				//判断是否可以进行修改操作,如果可以修改，同时更新级联关系数据
				boolean flag = canBasefilesIsEdit("t_base_department", beforeDept, departMent);
				if(flag){
					int i = departMentMapper.editDepartMent(departMent);
					count = count + i;
				}
			}
			if(count == list.size()){
				return true;
			}
		}
		return false;
	}

    @Override
	public boolean getPidAllDeptListIsHold(String pId,String gPid,String type)throws Exception{
		if(StringUtils.isNotEmpty(gPid)){//上级为鸿都工贸
			DepartMent departMentInfo = departMentMapper.getDepartmentInfo(gPid);
			if(departMentInfo != null){
				if("enable".equals(type)){//点击启用按钮，上级部门状态为禁用或保存时，不能启用
					if("0".equals(departMentInfo.getState()) || "2".equals(departMentInfo.getState())){
						return false;
					}
				}
				else{//点击禁用按钮，上级部门状态不为启用，不能禁用
					if(!"1".equals(departMentInfo.getState())){
						return false;
					}
				}
			}
		}
		return true;
	}

    @Override
	public List<DepartMent> getDeptListByIdsStr(String idsStr)throws Exception{
		String[] idsArr = idsStr.split(",");
		List<DepartMent> list = departMentMapper.getDeptListByIdsStr(idsArr);
		return list;
	}

    @Override
	public List<DepartMent> getPidAllDeptList(String pid)throws Exception{
		List<DepartMent> deptList = departMentMapper.getPidAllDeptList(pid);
		return deptList;
	}

    @Override
	public boolean checkSoleName(String name)throws Exception{
		String str = departMentMapper.checkSoleName(name);
		if(StringUtils.isNotEmpty(str)){//不为空，则已存在该名称，返回false
			return false;
		}
		return true;
	}

	@Override
	public List<DepartMent> getDeptListByOperType(String depttypeId)
			throws Exception {
		List<DepartMent> retDeptsList = departMentMapper.getDeptListByOperType(depttypeId);
		return retDeptsList;
	}
	
	@Override
	public List<DepartMent> getDeptListForMecshop() throws Exception {
		return departMentMapper.getDeptListForMecshop();
	}
}

