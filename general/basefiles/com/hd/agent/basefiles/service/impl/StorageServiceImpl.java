/**
 * @(#)StorageServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 16, 2013 chenwei 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.StorageMapper;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.model.StorageInout;
import com.hd.agent.basefiles.model.StorageLocation;
import com.hd.agent.basefiles.model.StorageType;
import com.hd.agent.basefiles.service.IStorageService;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.storage.dao.StorageSummaryMapper;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 仓库档案service实现类
 * @author chenwei
 */
public class StorageServiceImpl extends FilesLevelServiceImpl implements IStorageService{
	
	private StorageMapper storageMapper;
	
	public StorageMapper getStorageMapper() {
		return storageMapper;
	}

	public void setStorageMapper(StorageMapper storageMapper) {
		this.storageMapper = storageMapper;
	}
	
	@Override
	public Map addDRStorageInout(List<StorageInout> list) throws Exception {
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		boolean flag = false;
		if(null != list && list.size() != 0){
			for(StorageInout storageInout : list){
				if(storageMapper.getStorageInoutCountByID(storageInout.getId())==0){
					if(storageMapper.getStorageInoutCountByName(storageInout.getName())==0){
						SysUser sysUser = getSysUser();
						if(StringUtils.isEmpty(storageInout.getState())){
							storageInout.setState("2");
						}
						storageInout.setAdduserid(sysUser.getUserid());
						storageInout.setAddusername(sysUser.getName());
						storageInout.setAdddeptid(sysUser.getDepartmentid());
						storageInout.setAdddeptname(sysUser.getDepartmentname());
						flag = storageMapper.addStorageInout(storageInout) > 0;
						if(flag){
							successNum++;
						}
						else{
							if(StringUtils.isNotEmpty(failStr)){
								failStr += "," + storageInout.getId(); 
							}
							else{
								failStr = storageInout.getId();
							}
							failureNum++;
						}
					}else{
						if(StringUtils.isNotEmpty(failStr)){
							failStr += "," + storageInout.getId(); 
						}
						else{
							failStr = storageInout.getId();
						}
						failureNum++;
					}
				}
				else{
					StorageInout storageInout2 = storageMapper.showStorageInoutInfo(storageInout.getId());
					if(null != storageInout2){
						if("0".equals(storageInout2.getState())){//禁用状态，不允许导入
							if(StringUtils.isEmpty(closeVal)){
								closeVal = storageInout2.getId();
							}
							else{
								closeVal += "," + storageInout2.getId();
							}
							closeNum++;
						}
						else{
							if(StringUtils.isEmpty(repeatVal)){
								repeatVal = storageInout2.getId();
							}
							else{
								repeatVal += "," + storageInout2.getId();
							}
							repeatNum++;
						}
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
	public boolean addStorageInout(StorageInout storageInout) throws Exception {
		if(null!=storageInout && storageMapper.getStorageInoutCountByID(storageInout.getId())==0
				&& storageMapper.getStorageInoutCountByName(storageInout.getName())==0){
			SysUser sysUser = getSysUser();
			storageInout.setAdddeptid(sysUser.getDepartmentid());
			storageInout.setAdddeptname(sysUser.getDepartmentname());
			storageInout.setAdduserid(sysUser.getUserid());
			storageInout.setAddusername(sysUser.getName());
			int i = storageMapper.addStorageInout(storageInout);
			return i>0;
		}
		return false;
	}

	@Override
	public boolean checkStorageInoutID(String id) throws Exception {
		int i = storageMapper.getStorageInoutCountByID(id);
		return i==0;
	}

	@Override
	public boolean checkStorageInoutName(String name) throws Exception {
		int i = storageMapper.getStorageInoutCountByName(name);
		return i==0;
	}

	@Override
	public StorageInout showStorageInoutInfo(String id) throws Exception {
		StorageInout storageInout = storageMapper.showStorageInoutInfo(id);
		return storageInout;
	}

	@Override
	public PageData showStorageInoutList(PageMap pageMap) throws Exception {
		String sql = getDataAccessRule("t_base_storage_inout", null);
		pageMap.setDataSql(sql);
		PageData pageData = new PageData(storageMapper.showStorageInoutCount(pageMap),storageMapper.showStorageInoutList(pageMap),pageMap);
		return pageData;
	}

	@Override
	public boolean editStorageInout(StorageInout storageInout) throws Exception {
		int i = storageMapper.editStorageInout(storageInout);
		return i>0;
	}

	@Override
	public boolean deleteStorageInout(String id) throws Exception {
		int i = storageMapper.deleteStorageInout(id);
		return i>0;
	}

	@Override
	public boolean openStorageInout(String id) throws Exception {
		SysUser sysUser = getSysUser();
		int i = storageMapper.openStorageInout(id, sysUser.getUserid(), sysUser.getName());
		return i>0;
	}

	@Override
	public boolean closeStorageInout(String id) throws Exception {
		SysUser sysUser = getSysUser();
		int i = storageMapper.closeStorageInout(id, sysUser.getUserid(), sysUser.getName());
		return i>0;
	}

	@Override
	public Map addDRStorageType(List<StorageType> list) throws Exception {
		String failStr = "",closeVal = "",repeatVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		boolean flag = false;
		if(null != list && list.size() != 0){
			for(StorageType storageType : list){
				if(storageMapper.getStorageTypeCountByID(storageType.getId())==0){
					if(storageMapper.getStorageTypeCountByName(storageType.getName())==0){
						SysUser sysUser = getSysUser();
						storageType.setAdddeptid(sysUser.getDepartmentid());
						storageType.setAdddeptname(sysUser.getDepartmentname());
						storageType.setAdduserid(sysUser.getUserid());
						storageType.setAddusername(sysUser.getName());
						if(StringUtils.isEmpty(storageType.getState())){
							storageType.setState("2");
						}
						flag = storageMapper.addStorageType(storageType) > 0;
						if(flag){
							successNum++;
						}else{
							if(StringUtils.isNotEmpty(failStr)){
								failStr += "," + storageType.getId(); 
							}
							else{
								failStr = storageType.getId();
							}
							failureNum++;
						}
					}else{
						if(StringUtils.isNotEmpty(failStr)){
							failStr += "," + storageType.getId(); 
						}
						else{
							failStr = storageType.getId();
						}
						failureNum++;
					}
				}else{
					StorageType storageType2 = storageMapper.showStorageTypeInfo(storageType.getId());
					if(null != storageType2){
						if("0".equals(storageType2.getState())){//禁用状态，不允许导入
							if(StringUtils.isEmpty(closeVal)){
								closeVal = storageType2.getId();
							}
							else{
								closeVal += "," + storageType2.getId();
							}
							closeNum++;
						}
						else{
							if(StringUtils.isEmpty(repeatVal)){
								repeatVal = storageType2.getId();
							}
							else{
								repeatVal += "," + storageType2.getId();
							}
							repeatNum++;
						}
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
	public boolean addStorageType(StorageType storageType) throws Exception {
		if(null!=storageType && storageMapper.getStorageTypeCountByID(storageType.getId())==0
				&& storageMapper.getStorageTypeCountByName(storageType.getName())==0){
			SysUser sysUser = getSysUser();
			storageType.setAdddeptid(sysUser.getDepartmentid());
			storageType.setAdddeptname(sysUser.getDepartmentname());
			storageType.setAdduserid(sysUser.getUserid());
			storageType.setAddusername(sysUser.getName());
			int i = storageMapper.addStorageType(storageType);
			return i>0;
		}
		return false;
	}

	@Override
	public boolean checkStorageTypeID(String id) throws Exception {
		int i = storageMapper.getStorageTypeCountByID(id);
		return i==0;
	}

	@Override
	public boolean checkStorageTypeName(String name) throws Exception {
		int i = storageMapper.getStorageTypeCountByName(name);
		return i==0;
	}

	@Override
	public StorageType showStorageTypeInfo(String id) throws Exception {
		StorageType storageType = storageMapper.showStorageTypeInfo(id);
		return storageType;
	}

	@Override
	public PageData showStorageTypeList(PageMap pageMap) throws Exception {
		String sql = getDataAccessRule("t_base_storage_type", null);
		pageMap.setDataSql(sql);
		PageData pageData = new PageData(storageMapper.showStorageTypeCount(pageMap),storageMapper.showStorageTypeList(pageMap),pageMap);
		return pageData;
	}

	@Override
	public boolean editStorageType(StorageType storageType) throws Exception {
		int i = storageMapper.editStorageType(storageType);
		return i>0;
	}

	@Override
	public boolean deleteStorageType(String id) throws Exception {
		int i = storageMapper.deleteStorageType(id);
		return i>0;
	}

	@Override
	public boolean openStorageType(String id) throws Exception {
		SysUser sysUser = getSysUser();
		int i = storageMapper.openStorageType(id, sysUser.getUserid(), sysUser.getName());
		return i>0;
	}
	
	@Override
	public boolean closeStorageType(String id) throws Exception {
		SysUser sysUser = getSysUser();
		int i = storageMapper.closeStorageType(id, sysUser.getUserid(), sysUser.getName());
		return i>0;
	}
	
	@Override
	public List getStorageLocationList() throws Exception {
		List list = storageMapper.getStorageLocationList();
		return list;
	}

	@Override
	public boolean addStorageLocation(StorageLocation storageLocation)
			throws Exception {
		if(null!=storageLocation && storageMapper.getStorageLocationCountByID(storageLocation.getId())==0
				&& storageMapper.getStorageLocationCountByName(storageLocation.getName())==0){
			SysUser sysUser = getSysUser();
			storageLocation.setAdddeptid(sysUser.getDepartmentid());
			storageLocation.setAdddeptname(sysUser.getDepartmentname());
			storageLocation.setAdduserid(sysUser.getUserid());
			storageLocation.setAddusername(sysUser.getName());
			int i = storageMapper.addStorageLocation(storageLocation);
			return i>0;
		}
		return false;
	}

	@Override
	public StorageLocation showStorageLocationInfo(String id) throws Exception {
		StorageLocation storageLocation = storageMapper.showStorageLocationInfo(id);
		return storageLocation;
	}

	@Override
	public boolean checkStorageLocationID(String id) throws Exception {
		int i = storageMapper.getStorageLocationCountByID(id);
		return i==0;
	}

	@Override
	public boolean checkStorageLocationName(String name) throws Exception {
		int i = storageMapper.getStorageLocationCountByName(name);
		return i==0;
	}

	@Override
	public Map editStorageLocation(StorageLocation storageLocation)
			throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		StorageLocation oldLocation = showStorageLocationInfo(storageLocation.getOldid());
		if(null != oldLocation){
			if(!oldLocation.getThisid().equals(storageLocation.getThisid()) || !oldLocation.getThisname().equals(storageLocation.getThisname())){ //判断名称是否有修改，有修改则更新所有子节点名称
				//updateTreeName("t_base_sales_customersort", customer.getName(), customer.getId());
				List<StorageLocation> childList = storageMapper.getStorageLocationChildList(oldLocation.getId());
				if(childList.size() != 0){
					for(StorageLocation repeatSL : childList){
						repeatSL.setOldid(repeatSL.getId());
						if(!oldLocation.getThisid().equals(storageLocation.getThisid())){
							String newid = repeatSL.getId().replaceFirst(oldLocation.getThisid(), storageLocation.getThisid()).trim();
							repeatSL.setId(newid);
							String newpid = repeatSL.getPid().replaceFirst(oldLocation.getThisid(), storageLocation.getThisid()).trim();
							repeatSL.setPid(newpid);
						}
						if(!oldLocation.getThisname().equals(storageLocation.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatSL.getName().replaceFirst(oldLocation.getThisname(), storageLocation.getThisname());
							repeatSL.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeatSL.getId());
						node.setParentid(repeatSL.getPid());
						node.setText(repeatSL.getThisname());
						node.setState(repeatSL.getState());
						map2.put(repeatSL.getOldid(), node);
					}
					storageMapper.editStorageLocationBatch(childList);
				}
			}
		}
		SysUser sysUser = getSysUser();
		storageLocation.setModifyuserid(sysUser.getUserid());
		storageLocation.setModifyusername(sysUser.getName());
		boolean flag = storageMapper.editStorageLocation(storageLocation) > 0;
		if(flag){
			Tree node = new Tree();
			node.setId(storageLocation.getId());
			node.setParentid(storageLocation.getPid());
			node.setText(storageLocation.getThisname());
			node.setState(storageLocation.getState());
			map2.put(storageLocation.getOldid(), node);
		}
		map.put("flag", flag);
		map.put("nodes", map2);
		return map;
	}

	@Override
	public boolean openStorageLocation(String id) throws Exception {
		SysUser sysUser = getSysUser();
		int i = storageMapper.openStorageLocation(id, sysUser.getUserid(), sysUser.getName());
		return i>0;
	}

	@Override
	public boolean closeStorageLocation(String id) throws Exception {
		SysUser sysUser = getSysUser();
		int i = storageMapper.closeStorageLocation(id, sysUser.getUserid(), sysUser.getName());
		return i>0;
	}

	@Override
	public boolean deleteStorageLocation(String id) throws Exception {
		int i = storageMapper.deleteStorageLocation(id);
		return i>0;
	}

	@Override
	public boolean isRepeatStorageLocationThisname(String thisname)
			throws Exception {
		if(storageMapper.isRepeatThisName(thisname) > 0){//不为空，已存在该本级名称
			return false;
		}
		return true;
	}

	@Override
	public boolean checkStorageInfoID(String id) throws Exception {
		int i = storageMapper.getStorageInfoCountByID(id);
		return i==0;
	}

	@Override
	public boolean checkStorageInfoName(String name) throws Exception {
		int i = storageMapper.getStorageInfoCountByName(name);
		return i==0;
	}

	@Override
	public StorageInfo getStorageByName(String name) throws Exception {
		return storageMapper.getStorageInfoByName(name);
	}

	@Override
	public boolean addStorageInfo(StorageInfo storageInfo) throws Exception {
		if(null!=storageInfo && storageMapper.getStorageInfoCountByID(storageInfo.getId())==0
				&& storageMapper.getStorageInfoCountByName(storageInfo.getName())==0){
			SysUser sysUser = getSysUser();
			storageInfo.setAdddeptid(sysUser.getDepartmentid());
			storageInfo.setAdddeptname(sysUser.getDepartmentname());
			storageInfo.setAdduserid(sysUser.getUserid());
			storageInfo.setAddusername(sysUser.getName());
			int i = storageMapper.addStorageInfo(storageInfo);
			return i>0;
		}
		return false;
	}

	@Override
	public Map addDRStorageInfo(List<StorageInfo> list) throws Exception {
		String failStr = "",closeVal = "",repeatVal = "",samenameStr = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0;
		boolean flag = false;
		if(list.size() != 0){
			for(StorageInfo storageInfo : list){
                StorageInfo storageInfo2 = storageMapper.showStorageInfo(storageInfo.getId());
                if(null != storageInfo2){
                    if("0".equals(storageInfo2.getState())){//禁用状态，不允许导入
                        if(StringUtils.isEmpty(closeVal)){
                            closeVal = storageInfo2.getId();
                        }
                        else{
                            closeVal += "," + storageInfo2.getId();
                        }
                        closeNum++;
                    }
                    else{
                        if(StringUtils.isEmpty(repeatVal)){
                            repeatVal = storageInfo2.getId();
                        }
                        else{
                            repeatVal += "," + storageInfo2.getId();
                        }
                        repeatNum++;
                    }
                }else{
                    if(null!=storageInfo && storageMapper.getStorageInfoCountByName(storageInfo.getName())==0){
                        SysUser sysUser = getSysUser();
                        storageInfo.setAdddeptid(sysUser.getDepartmentid());
                        storageInfo.setAdddeptname(sysUser.getDepartmentname());
                        storageInfo.setAdduserid(sysUser.getUserid());
                        storageInfo.setAddusername(sysUser.getName());
                        flag = storageMapper.addStorageInfo(storageInfo) > 0;
                        if(!flag){
                            if(StringUtils.isNotEmpty(failStr)){
                                failStr += "," + storageInfo.getId();
                            }
                            else{
                                failStr = storageInfo.getId();
                            }
                            failureNum++;
                        }
                        else{
                            successNum++;
                        }
                    }else{
                        if(StringUtils.isNotEmpty(samenameStr)){
                            samenameStr += "," + storageInfo.getId();
                        }
                        else{
                            samenameStr = storageInfo.getId();
                        }
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
        map.put("samenameStr", samenameStr);
		return map;
	}

	@Override
	public StorageInfo showStorageInfo(String id) throws Exception {
		StorageInfo storageInfo = storageMapper.showStorageInfo(id);
		return storageInfo;
	}

	@Override
	public PageData showStorageInfoList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_base_storage_info", null);
		pageMap.setDataSql(dataSql);
		List<StorageInfo> list = storageMapper.showStorageInfoList(pageMap);
		for(StorageInfo storageInfo : list){
			SysCode storagetype = getBaseSysCodeMapper().getSysCodeInfo(storageInfo.getStoragetype(), "storagetype");
			if(null != storagetype){
				storageInfo.setStoragetypename(storagetype.getCodename());
			}
		}
		PageData pageData = new PageData(storageMapper.showStorageInfoCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public boolean editStorageInfo(StorageInfo storageInfo) throws Exception {
		int i = storageMapper.editStorageInfo(storageInfo);
		if(i>0){
			StorageSummaryMapper storageSummaryMapper =  (StorageSummaryMapper) SpringContextUtils.getBean("storageSummaryMapper");
			//更新仓库中商品库存状态
			storageSummaryMapper.editStorageSummaryInfoByStorageid(storageInfo.getId(), storageInfo.getIstotalcontrol(), storageInfo.getIssendstorage());
		}
		return i>0;
	}

	@Override
	public boolean deleteStorageInfo(String id) throws Exception {
		int i = storageMapper.deleteStorageInfo(id);
		return i>0;
	}

	@Override
	public boolean openStorageInfo(String id) throws Exception {
		SysUser sysUser = getSysUser();
		int i = storageMapper.openStorageInfo(id, sysUser.getUserid(), sysUser.getName());
        //仓库档案启用后 重新更新仓库商品状态
        if(i>0){
            StorageInfo storageInfo = storageMapper.showStorageInfo(id);
            if(null!=storageInfo){
                StorageSummaryMapper storageSummaryMapper =  (StorageSummaryMapper) SpringContextUtils.getBean("storageSummaryMapper");
                //更新仓库中商品库存状态
                storageSummaryMapper.editStorageSummaryInfoByStorageid(storageInfo.getId(), storageInfo.getIstotalcontrol(), storageInfo.getIssendstorage());
            }
        }
		return i>0;
	}

	@Override
	public boolean closeStorageInfo(String id) throws Exception {
		SysUser sysUser = getSysUser();
		int i = storageMapper.closeStorageInfo(id, sysUser.getUserid(), sysUser.getName());
        //仓库档案禁用后 更新仓库状态为不参与总量控制 不是发货仓
        if(i>0){
            StorageInfo storageInfo = storageMapper.showStorageInfo(id);
            if(null!=storageInfo){
                StorageSummaryMapper storageSummaryMapper =  (StorageSummaryMapper) SpringContextUtils.getBean("storageSummaryMapper");
                //更新仓库中商品库存状态
                storageSummaryMapper.editStorageSummaryInfoByStorageid(storageInfo.getId(), "0", "0");
            }
        }
		return i>0;
	}

    @Override
    public Map addDRStorageLocationExcel(List<StorageLocation> list) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0,errorNum = 0,levelNum=0;
        String closeVal = "",repeatVal = "",failStr = "",errorVal = "",levelVal = "";
        if(list.size() != 0){
            String tn = "t_base_storage_location";
            List levelList = showFilesLevelList(tn);
            if(levelList.size() != 0){
                for(StorageLocation storageLocation : list){
                    if(StringUtils.isEmpty(storageLocation.getId())){
                        continue;
                    }
                    //根据档案级次信息根据编码获取本级编码
                    Map map = getObjectThisidByidCaseFilesLevel(tn,storageLocation.getId());
                    if(null != map && !map.isEmpty()){
                        String id = storageLocation.getId();
                        String thisid = (null != map.get("thisid")) ? map.get("thisid").toString() : "";
                        String pid = (null != map.get("pid")) ? map.get("pid").toString() : "";
                        try {
                            //判断商品分类是否重复
                            StorageLocation storageLocationInfo = storageMapper.showStorageLocationInfo(id);
                            if(storageLocationInfo == null){//不重复
                                storageLocation.setThisid(thisid);
                                storageLocation.setPid(pid);
                                SysUser sysUser = getSysUser();
                                storageLocation.setAdduserid(sysUser.getUserid());
                                storageLocation.setAdddeptid(sysUser.getDepartmentid());
                                storageLocation.setAdddeptname(sysUser.getDepartmentname());
                                storageLocation.setAddusername(sysUser.getName());
                                if(StringUtils.isEmpty(storageLocation.getPid())){
                                    storageLocation.setName(storageLocation.getThisname());
                                }
                                storageLocation.setState("1");
                                flag = storageMapper.addStorageLocation(storageLocation) > 0;
                                if(!flag){
                                    if(StringUtils.isNotEmpty(failStr)){
                                        failStr += "," + storageLocation.getId();
                                    }
                                    else{
                                        failStr = storageLocation.getId();
                                    }
                                    failureNum++;
                                }
                                else{
                                    successNum++;
                                }
                            }
                            else{
                                if("0".equals(storageLocationInfo.getState())){//禁用状态，不允许导入
                                    if(StringUtils.isEmpty(closeVal)){
                                        closeVal = storageLocationInfo.getId();
                                    }
                                    else{
                                        closeVal += "," + storageLocationInfo.getId();
                                    }
                                    closeNum++;
                                }
                                else{
                                    if(StringUtils.isEmpty(repeatVal)){
                                        repeatVal = storageLocationInfo.getId();
                                    }
                                    else{
                                        repeatVal += "," + storageLocationInfo.getId();
                                    }
                                    repeatNum++;
                                }
                            }
                        } catch (Exception e) {
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
                            levelVal += "," + storageLocation.getId();
                        }
                        else{
                            levelVal = storageLocation.getId();
                        }
                    }
                }
                //名称
                List<StorageLocation> nonameList = storageMapper.getStorageLocationWithoutName();
                if(nonameList.size() != 0){
                    for(StorageLocation nonameSL : nonameList){
                        StorageLocation pSL = showStorageLocationInfo(nonameSL.getPid());
                        if(null != pSL){
                            nonameSL.setOldid(nonameSL.getId());
                            String name = "";
                            if(StringUtils.isEmpty(pSL.getName())){
                                name = nonameSL.getThisname();
                            }else{
                                name = pSL.getName() + "/" + nonameSL.getThisname();
                            }
                            if(StringUtils.isNotEmpty(pSL.getName())){
                                nonameSL.setName(name);
                            }else{
                                nonameSL.setName(pSL.getThisname());
                            }
                            storageMapper.editStorageLocation(nonameSL);
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
        return returnMap;
    }

}

