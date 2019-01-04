/**
 * @(#)ServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月21日 chenwei 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.SubjectMapper;
import com.hd.agent.basefiles.model.Subject;
import com.hd.agent.basefiles.service.ISubjectService;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysCode;

/**
 * 部门service实现类
 * 
 * @author chenwei
 */
public class SubjectServiceImpl extends FilesLevelServiceImpl implements
		ISubjectService {
	/**
	 * 
	 */
	private SubjectMapper subjectMapper;
	
	
	public SubjectMapper getSubjectMapper() {
		return subjectMapper;
	}

	public void setSubjectMapper(
			SubjectMapper subjectMapper) {
		this.subjectMapper = subjectMapper;
	}

	/**
	 * ===========================================
	 * 科目分类
	 * ===========================================
	 */
	
	/**
	 * 新增科目分类
	 */
	@Override
	public Map addSubjectType(Subject subject) throws Exception{
		boolean flag=false;
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("id", subject.getId());
		int icount=subjectMapper.getSubjectCountByMap(queryMap);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(icount==1){
			resultMap.put("flag", false);
			resultMap.put("msg", "科目编码已经存在");
			return resultMap;
		}
		queryMap.clear();
		queryMap.put("typecode", subject.getTypecode());
		icount=subjectMapper.getSubjectCountByMap(queryMap);
		if(icount==1){
			resultMap.put("flag", false);
			resultMap.put("msg", "科目分类代码已经存在");
			return resultMap;
		}

		subject.setThisid(subject.getId());
		subject.setThisname(subject.getName());
		subject.setPid("");
		subject.setIstypehead("1");
		subject.setLeaf("1");
		
		flag=subjectMapper.addSubject(subject)>0;
		resultMap.put("flag", flag);
		return resultMap;
	}
	
	/**
	 * 修改科目信息分类
	 */
	@Override
	public Map editSubjectType(Subject subject) throws Exception{
		Map resultMap = new HashMap();		
		Subject oldsubject = getSubjectDetail(subject.getOldid());
		if(null==oldsubject){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到科目分类信息");
			return resultMap;
		}
		Map queryMap=new HashMap();
		queryMap.put("pid", subject.getOldid());
		int icount=subjectMapper.getSubjectCountByMap(queryMap);
		if(icount>0 && !subject.getOldid().equals(subject.getId())){
			resultMap.put("flag", false);
			resultMap.put("msg", "该分类下已经有科目档案，不能修改分类编码");
			return resultMap;
		}

		SysUser sysUser=getSysUser();
		subject.setThisid(subject.getId());
		subject.setThisname(subject.getName());
		subject.setModifyuserid(sysUser.getUserid());
		subject.setModifyusername(sysUser.getName());
		subject.setModifytime(new Date());
		boolean flag = subjectMapper.updateSubjectType(subject) > 0;
		resultMap.put("flag", flag);
		return resultMap;
	}
	
	@Override
	public Map deleteSubjectType(String id) throws Exception{
		Map resultMap=new HashMap();
		if(null == id || "".equals(id.trim())){
			resultMap.put("flag", false);
			return resultMap;
		}
		Map queryMap=new HashMap();
		queryMap.put("pid", id);
		int icount=subjectMapper.getSubjectCountByMap(queryMap);
		boolean flag=false;
		if(icount==0){
			Subject subject=subjectMapper.getSubjectById(id);
			if(null!=subject && "1".equals(subject.getState())){
				resultMap.put("flag", false);
				resultMap.put("msg", "启用的数据不能被删除");
				return resultMap;
			}
			flag=subjectMapper.deleteSubjectById(id)>0;
			resultMap.put("flag", flag);
		}else{
			resultMap.put("msg", "该分类有子级科目，不能被删除");
		}
		return resultMap;
	}
	
	@Override
	public Map enableSubjectType(String id) throws Exception{
		Map resultMap = new HashMap();
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关信息");
			return resultMap;
		}
		Subject subject=subjectMapper.getSubjectById(id);
		if(null==subject){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关信息");
			return resultMap;			
		}
		if("1".equals(subject.getState())){
			resultMap.put("flag", false);
			resultMap.put("msg", "该分类已经被启用");
			return resultMap;			
		}
		
		SysUser sysUser = getSysUser();
		subject.setCloseuserid(sysUser.getUserid());
		subject.setCloseusername(sysUser.getName());
		subject.setId(id);
		boolean flag = subjectMapper.enableSubject(subject)>0;
		resultMap.put("flag", flag);
		return resultMap;		
	}
	
	@Override
	public Map disableSubjectType(String id) throws Exception{
		Map resultMap = new HashMap();
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关信息");
			return resultMap;
		}
		Subject subject=subjectMapper.getSubjectById(id);
		if(null==subject){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关信息");
			return resultMap;			
		}
		if("0".equals(subject.getState())){
			resultMap.put("flag", false);
			resultMap.put("msg", "该分类已经被禁用");
			return resultMap;			
		}
		
		SysUser sysUser = getSysUser();
		subject.setCloseuserid(sysUser.getUserid());
		subject.setCloseusername(sysUser.getName());
		subject.setId(id);
		boolean flag = subjectMapper.disableSubject(subject)>0;
		resultMap.put("flag", flag);
		return resultMap;
	}
	@Override
	public Subject getSubjectTypeById(String id) throws Exception{
		return subjectMapper.getSubjectTypeById(id);
	}
	@Override
	public Subject getSubjectTypeByCode(String typecode) throws Exception{
		return subjectMapper.getSubjectTypeByCode(typecode);
	}
	/**
	 * ===========================================
	 * 科目档案
	 * ===========================================
	 */
	
	@Override
	public List showSubjectList() throws Exception{
		List list=subjectMapper.getSubjectList();
		return list;
	}
	@Override
	public List showSubjectEnableList(String typecode) throws Exception{
		List list=subjectMapper.showSubjectEnableList(typecode);
		return list;
	}
	@Override
	public List showSubjectListByMap(Map map) throws Exception{
		String cols = getAccessColumnList("t_base_subject",null); //字段权限
		map.put("cols", cols);
		String sql = getDataAccessRule("t_base_subject",null); //数据权限
		map.put("dataSql", sql);
		return subjectMapper.getSubjectListByMap(map);
	}	
	@Override
	public PageData showSubjectPageList(PageMap pageMap) throws Exception{

		String cols = getAccessColumnList("t_base_subject",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_subject",null); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();
		int total=0;
		String isNoPageflag="false";
		if(condition.containsKey("isNoPageflag")){
			isNoPageflag=(String)condition.get("isNoPageflag");
		}
		if(!"true".equals(isNoPageflag)){
			total=subjectMapper.getSubjectCount(pageMap);
		}
		List<Subject> list=subjectMapper.getSubjectPageList(pageMap);
		for(Subject item:list){
			if(StringUtils.isNotEmpty(item.getState())){
				SysCode sysCode=getBaseSysCodeInfo(item.getState(),"state");
				if(null!=sysCode){
					item.setStatename(sysCode.getCodename());
				}
			}
			if(StringUtils.isNotEmpty(item.getIstypehead())){
				if("1".equals(item.getIstypehead())){
					item.setIstypeheadname("是");
				}else{
					item.setIstypeheadname("否");
				}
			}
			if(StringUtils.isNotEmpty(item.getLeaf())){
				if("1".equals(item.getLeaf())){
					item.setLeafname("是");
				}else{
					item.setLeafname("否");
				}
			}
		}
		PageData pageData=new PageData(total, list, pageMap);
		return pageData;
	}
	@Override
	public Subject getSubjectDetail(String id) throws Exception {
		String cols = getAccessColumnList("t_base_subject", null);//加字段查看权限
		String sql = getDataAccessRule("t_base_subject",null); //数据权限
		
		Map paramMap=new HashMap();
		paramMap.put("cols", cols);
		paramMap.put("dataSql", sql);
		paramMap.put("id", id);
		return subjectMapper.getSubjectByMap(paramMap);
	}
	
	/**
	 * 新增科目
	 */
	@Override
	public Map addSubject(Subject subject) throws Exception{
		Map<String,Object> resultMap=new HashMap<String,Object>();
		boolean flag=false;
		if(StringUtils.isEmpty(subject.getId())){
			resultMap.put("flag", false);
			resultMap.put("msg", "代码不能为空");
			return resultMap;
		}
		resultMap.put("id", subject.getId());
		int icount=subjectMapper.getSubjectCountByMap(resultMap);
		if(icount==1){
			resultMap.put("flag", false);
			resultMap.put("msg", "代码已经存在");
			return resultMap;
		}
		Subject sujectType=subjectMapper.getSubjectTypeById(subject.getTypeid());
		if(null==sujectType){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关科目分类信息");
			return resultMap;
		}
		if(StringUtils.isEmpty(sujectType.getTypecode())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关科目分类信息");
			return resultMap;
		}
		subject.setTypecode(sujectType.getTypecode());
		subject.setTypeid(sujectType.getId());
		subject.setIstypehead("0");
		flag=subjectMapper.addSubject(subject)>0;
		resultMap.put("flag", flag);
		return resultMap;
	}
	
	/**
	 * 修改科目信息
	 */
	@Override
	public Map editSubject(Subject subject) throws Exception{
		Map map = new HashMap();
		Map map2 = new HashMap();
		Date nowDate=new Date();
		SysUser sysUser=getSysUser();
		subject.setModifyuserid(sysUser.getUserid());
		subject.setModifyusername(sysUser.getName());
		subject.setModifytime(nowDate);
		
		Subject oldsubject = getSubjectDetail(subject.getOldid());
		if(null != oldsubject){
			if(StringUtils.isNotEmpty(oldsubject.getThisid()) && !oldsubject.getThisid().equals(subject.getThisid()) 
					|| StringUtils.isNotEmpty(oldsubject.getThisname())&&!oldsubject.getThisname().equals(subject.getThisname())){
				//获取下级所有分类列表
				List<Subject> childList = getsubjectChildList(oldsubject.getId());
				//若编码改变，下属所有的任务分类编码应做对应的替换
				if(childList.size() != 0){
					boolean canModifyId=true;
					for(Subject repeatItem : childList){
						repeatItem.setOldid(repeatItem.getId());
						if(!oldsubject.getThisid().equals(subject.getThisid())){
							canModifyId=canTableDataDictDelete("t_base_subject", repeatItem.getId());
							if(!canModifyId){
								break;
							}
							String newid = repeatItem.getId().replaceFirst(oldsubject.getThisid(), subject.getThisid()).trim();
							repeatItem.setId(newid);
							String newpid = repeatItem.getPid().replaceFirst(oldsubject.getThisid(), subject.getThisid()).trim();
							repeatItem.setPid(newpid);
						}
						if(!oldsubject.getThisname().equals(subject.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatItem.getName().replaceFirst(oldsubject.getThisname(), subject.getThisname());
							repeatItem.setName(newname);
						}
						repeatItem.setModifyuserid(sysUser.getUserid());
						repeatItem.setModifyusername(sysUser.getName());
						repeatItem.setModifytime(nowDate);
						
						Tree node = new Tree();
						node.setId(repeatItem.getId());
						node.setParentid(repeatItem.getPid());
						node.setText(repeatItem.getThisname());
						node.setState(repeatItem.getState());
						map2.put(repeatItem.getOldid(), node);
					}
					if(canModifyId){
						subjectMapper.updateSubjectBatch(childList);
					}else{
						map.put("flag", false);
						map.put("msg", "该科目子级被引用，不能修改本级编码");
						return map;
					}
				}
			}
		}
		boolean flag = subjectMapper.updateSubject(subject) > 0;
		if(flag){
			Tree node = new Tree();
			node.setId(subject.getId());
			node.setParentid(subject.getPid());
			node.setText(subject.getThisname());
			node.setState(subject.getState());
			map2.put(subject.getOldid(), node);
		}
		map.put("flag", flag);
		map.put("nodes", map2);
		return map;
	}
	/**
	 * 根据pid获取所在的子结点的列表信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public List getsubjectChildList(String id) throws Exception{
		if(null==id || "".equals(id.trim())){
			return null;
		}
		Map paramMap=new HashMap();
		paramMap.put("pid", id.trim());
		return subjectMapper.getSubjectListByMap(paramMap);
	}
	/**
	 * 禁用科目
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@Override
	public Map disableSubject(String id) throws Exception{
		Subject subject = new Subject();
		SysUser sysUser = getSysUser();
		subject.setCloseuserid(sysUser.getUserid());
		subject.setCloseusername(sysUser.getName());
		subject.setId(id);
		
		Map paramMap = new HashMap();
		paramMap.put("likeid", id);
		List<Subject> list = subjectMapper.getSubjectListByMap(paramMap);//查询该节点及其所有子节点信息
		int successNum = 0,failureNum = 0,notAllowNum = 0;
		List<String> ids = new ArrayList<String>();
		for(Subject item : list){
			if(!"1".equals(item.getState())){
				notAllowNum++;
			}
			else{
				Subject upSubject = new Subject();
				upSubject.setCloseuserid(sysUser.getUserid());
				upSubject.setCloseusername(sysUser.getName());
				upSubject.setId(item.getId());
				boolean flag = subjectMapper.disableSubject(upSubject)>0;
				if(flag){
					successNum++;
					ids.add(item.getId());//返回所有禁用的记录编号，供前台更新树节点信息
				}
				else{
					failureNum++;
				}
			}
		}
		Map resultMap = new HashMap();
		resultMap.put("flag", successNum>0);
		resultMap.put("successNum", successNum);
		resultMap.put("failureNum", failureNum);
		resultMap.put("notAllowNum", notAllowNum);
		resultMap.put("ids", ids);
		return resultMap;
	}
	
	/**
	 * 启用科目
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	@Override
	public Map enableSubject(String id) throws Exception{
		Subject subject = new Subject();
		Map resultMap = new HashMap();
		Subject oldSubject=subjectMapper.getSubjectById(id);
		if(null==oldSubject){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关科目信息");
			return resultMap;
		}
		if(StringUtils.isEmpty(oldSubject.getTypecode())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到该科目所属分类");
			return resultMap;			
		}
		
		SysUser sysUser = getSysUser();
		subject.setOpenuserid(sysUser.getUserid());
		subject.setOpenusername(sysUser.getName());
		subject.setId(id);
		boolean flag= (subjectMapper.enableSubject(subject) > 0) && isTreeLeaf(oldSubject.getTypecode());

		resultMap.put("flag", flag);
		return resultMap;
	}

	@Override
	public Map deleteSubjectById(String id)throws Exception{
		Map resultMap=new HashMap();
		if(null == id || "".equals(id.trim())){
			resultMap.put("flag", false);
			return resultMap;
		}
		boolean delFlag=false;
		delFlag=canTableDataDictDelete("t_base_subject", id);
		if(delFlag){

			Subject subject=subjectMapper.getSubjectById(id);
			if(null!=subject && "1".equals(subject.getState())){
				resultMap.put("flag", false);
				resultMap.put("msg", "启用的数据不能被删除");
				return resultMap;
			}
			delFlag=subjectMapper.deleteSubjectById(id)>0;
			resultMap.put("delFlag", false);
		}else{
			resultMap.put("delFlag", true);	//是否被引用
		}
		resultMap.put("flag", delFlag);
		return resultMap;
	}
	/**
	 * 批量删除科目
	 */
	@Override
	public Map deleteSubjectMore(String idarrs)throws Exception{
		Map map=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){

			map.put("flag", false);
			map.put("isuccess", iSuccess);
			map.put("ifailure", iFailure);
			return map;
		}
		String[] idarr=idarrs.trim().split(",");
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			Map resultMap=deleteSubjectById(id);
			Boolean flag=false;
			if(null!=resultMap){
				flag=(Boolean)resultMap.get("flag");
				if(null==flag){
					flag=false;
				}
			}
			if(flag){
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		map.clear();
		if(iSuccess>0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		return map;
	}
	
	/**
	 * 判断pid是否存在,若存在则为末及标志，否则，不是，进行级联替换
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月28日
	 */
	public boolean isTreeLeaf(String typecode)throws Exception{
		int count = 0;
		//获取暂存以外的费用分类数据列表
		List<Subject> list = subjectMapper.showSubjectEnableList(typecode);
		if(list.size() != 0){
			for(Subject item:list){
				//判断pid是否存在,若存在则为末及标志，否则，不是
				String pid = subjectMapper.isLeafSubject(item.getId());
				if(!StringUtils.isNotEmpty(pid)){
					item.setLeaf("1");
				}
				else{
					item.setLeaf("0");
				}
				item.setOldid(item.getId());
				Subject beforeSubject = subjectMapper.getSubjectById(item.getId());
				//判断是否可以进行修改操作,如果可以修改，同时更新级联关系数据
				if(canBasefilesIsEdit("t_base_subject", beforeSubject, item)){
					Subject upSubject=new Subject();
					upSubject.setOldid(item.getId());
					upSubject.setLeaf(item.getLeaf());
					int i = subjectMapper.updateSubject(upSubject);
					count++;
				}
			}
			if(count == list.size()){
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public List getSubjectParentAllChildren(Map map) throws Exception {
		String cols = getAccessColumnList("t_base_subject",null); //字段权限
		map.put("cols", cols);
		String sql = getDataAccessRule("t_base_subject",null); //数据权限
		map.put("dataSql",sql);
		if(map.containsKey("id")){
			map.put("likeid", map.get("id"));
			map.remove("id");
		}
		return subjectMapper.getSubjectListByMap(map);
	}

	
	
	@Override
    public Map addDRSubjectExcel(List<Subject> list,String typeid)throws Exception{
		Map resultMap = new HashMap();
		Subject subjectType=subjectMapper.getSubjectTypeById(typeid);
		if(null==subjectType){
			resultMap.put("error", true);
			resultMap.put("msg", "未能找相关科目分类");
			return resultMap;
		}
        boolean flag = false;
        int successNum = 0;
        int failureNum = 0;
        int repeatNum = 0;
        int closeNum = 0;
        int errorNum = 0;
        int levelNum=0;
        List<Subject> errorDataList=new ArrayList<Subject>();
        if(list.size() != 0){
            String tn = "t_base_subject";
            List levelList = showFilesLevelList(tn);
            if(levelList.size() != 0){
                for(Subject subject : list){
                    if(StringUtils.isEmpty(subject.getId())){
                    	subject.setErrormsg("该科目编号不能为空");
                    	errorDataList.add(subject);
                        continue;
                    }
                    if(subject.getId().equals(subjectType.getId())){
                    	subject.setErrormsg("科目分类不能导入");
                    	errorDataList.add(subject);
                    	continue;
                    }
                    if(!subject.getId().startsWith(subjectType.getId())){
                    	subject.setErrormsg("编号有误。注意：编号以科目分类编码为开头");
                    	errorDataList.add(subject);
                    	continue;                    	
                    }

                    //判断商品分类是否重复
                	Map queryMap=new HashMap();
                	queryMap.put("id", subject.getId());
                	int icount=subjectMapper.getSubjectCountByMap(queryMap);
                	if(icount>0){
                        subject.setErrormsg("该科目编号已经存在");
                        repeatNum++;
                        continue;
                	}
                	
                    if(StringUtils.isEmpty(subject.getThisname())){
                    	subject.setErrormsg("本级名称不能为空");
                    	errorDataList.add(subject);
                    	continue;
                    }
                    
                    subject.setTypecode(subjectType.getTypecode());
                    subject.setTypeid(subjectType.getId());
                    //根据档案级次信息根据编码获取本级编码
                    Map map = getObjectThisidByidCaseFilesLevel(tn,subject.getId());
                    if(null != map && !map.isEmpty()){
                        String id = subject.getId();
                        String thisid = (null != map.get("thisid")) ? map.get("thisid").toString() : "";

                        if("".equals(thisid.trim())){
                        	subject.setErrormsg("根据级次规则判断编号，未知本级编号");
                        	errorDataList.add(subject);
                        	continue;
                        }
                        String pid = (null != map.get("pid")) ? map.get("pid").toString() : "";

                        if("".equals(pid.trim())){
                        	subject.setErrormsg("根据级次规则判断编号，未知上级编号");
                        	errorDataList.add(subject);
                        	continue;
                        }

                    	subject.setThisid(thisid);
                        subject.setPid(pid);
                        
                        if(subject.getPid().equals(subjectType.getId())){
                        	subject.setName(subject.getThisname());
                        	queryMap.clear();
                        	queryMap.put("typecode", subjectType.getTypecode());
                        	queryMap.put("name", subject.getName());
                        	icount=subjectMapper.getSubjectCountByMap(queryMap);
                        	if(icount>0){
                                subject.setErrormsg("该科目名称已经存在");
                                repeatNum++;
                                continue;
                        	}
                        }else{
                        	subject.setName(subject.getId()+":"+subject.getThisname());
                        }
                    	
                        SysUser sysUser = getSysUser();
                        subject.setAdduserid(sysUser.getUserid());
                        subject.setAdddeptid(sysUser.getDepartmentid());
                        subject.setAdddeptname(sysUser.getDepartmentname());
                        subject.setAddusername(sysUser.getName());
                        if(StringUtils.isEmpty(subject.getPid())){
                            subject.setName(subject.getThisname());
                        }
                        if(!"0".equals(subject.getState()) && !"1".equals(subject.getState()) && !"2".equals(subject.getState())){
                        	subject.setState("1");
                        }
                        subject.setIstypehead("0");
                        
                        try {
                            flag = subjectMapper.addSubject(subject) > 0;
                            if(!flag){
                                subject.setErrormsg("未能存入数据库");
                                errorDataList.add(subject);
                                failureNum++;
                                continue;
                            }
                            else{
                                successNum++;
                            }
                        } catch (Exception ex) {
                            subject.setErrormsg("该科目存入数据库时系统异常："+ex.getMessage());
                            errorDataList.add(subject);
                            errorNum++;
                            continue;
                        }
                    }else{
                    	subject.setErrormsg("该档案对应的编码级次未定义"); 
                    	errorDataList.add(subject);
                        levelNum++;
                    }
                }
                if(successNum>0){
	                //末级标志
	                isTreeLeaf(subjectType.getTypecode());
	                Map queryMap=new HashMap();
	                queryMap.put("typecode", subjectType.getTypecode());
	                queryMap.put("istypehead", "0");
	                queryMap.put("isQueryByOrder", "true");
	                queryMap.put("orderBySql", "id asc");
	                List<Subject> upNameList=subjectMapper.getSubjectListByMap(queryMap);
	                	                
	                for(Subject upNameSubject : upNameList){
                    	Subject upSubject=new Subject();
                    	upSubject.setOldid(upNameSubject.getId());
                        Subject parentSubject=null;
                        if(upNameSubject.getPid().equals(subjectType.getId())){
                        	continue;
                        }
                        parentSubject=subjectMapper.getSubjectById(upNameSubject.getPid());
                        if(null != parentSubject){
                            String name = "";
                            if(StringUtils.isEmpty(parentSubject.getName())){
                                name = upNameSubject.getThisname();
                            }else{
                                name = parentSubject.getName() + "/" + upNameSubject.getThisname();
                            }
                            if(StringUtils.isNotEmpty(parentSubject.getName())){
                                upNameSubject.setName(name);
                            }else{
                                upNameSubject.setName(parentSubject.getThisname());
                            }
                            upSubject.setName(upNameSubject.getName());
                            subjectMapper.updateSubject(upSubject);
                        }
                    }
                }
            }else{
                resultMap.put("nolevel", true);
            }
        }
        resultMap.put("flag", flag);
        resultMap.put("success", successNum);
        resultMap.put("failure", failureNum);
        resultMap.put("repeatNum", repeatNum);
        resultMap.put("closeNum", closeNum);
        resultMap.put("errorNum", errorNum);
        resultMap.put("levelNum", levelNum);
        resultMap.put("errorDataList", errorDataList);
        return resultMap;
    }
	
	/**
	 * ===========================================
	 * 共用
	 * ===========================================
	 */

	@Override
	public Subject showSubjectById(String id) throws Exception{
		Subject subject=subjectMapper.getSubjectById(id);
		return subject;
	}
	@Override
	public boolean isUsedSubjectName(String name) throws Exception {
		return subjectMapper.isUsedSubjectName(name) > 0;
	} 

	@Override
	public boolean isUsedSubjectType(String typecode) throws Exception {
		if(null==typecode || "".equals(typecode.trim())){
			return false;
		}
		Map map=new HashMap();
		map.put("typecode", typecode);
		int icount=subjectMapper.getSubjectCountByMap(map);
		return icount>0;
	} 
	@Override
	public int getSubjectCountByMap(Map map) throws Exception{
		return subjectMapper.getSubjectCountByMap(map);
	}
}

