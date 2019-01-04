/**
 * @(#)CostsFeeService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-6-19 zhanghonghui 创建版本
 */
package com.hd.agent.journalsheet.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.SalesBillCheckMapper;
import com.hd.agent.basefiles.dao.DepartMentMapper;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.FilesLevelServiceImpl;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.dao.CostsFeeMapper;
import com.hd.agent.journalsheet.dao.DeptCostsSubjectMapper;
import com.hd.agent.journalsheet.dao.JournalSheetMapper;
import com.hd.agent.journalsheet.model.*;
import com.hd.agent.journalsheet.service.ICostsFeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 费用
 * @author zhanghonghui
 */
public class CostsFeeServiceImpl  extends FilesLevelServiceImpl implements ICostsFeeService {
	private CostsFeeMapper costsFeeMapper;
	
	private DepartMentMapper departMentMapper;

	public CostsFeeMapper getCostsFeeMapper() {
		return costsFeeMapper;
	}

	public void setCostsFeeMapper(CostsFeeMapper costsFeeMapper) {
		this.costsFeeMapper = costsFeeMapper;
	}

	public DepartMentMapper getDepartMentMapper() {
		return departMentMapper;
	}

	public void setDepartMentMapper(DepartMentMapper departMentMapper) {
		this.departMentMapper = departMentMapper;
	}
	
	/**
	 * 部门费用科目
	 */
	/**================================================================*/
	/**
	 * 操作DeptCostsSubject表的dao接口
	 */
	private DeptCostsSubjectMapper deptCostsSubjectMapper;

	public DeptCostsSubjectMapper getDeptCostsSubjectMapper() {
		return deptCostsSubjectMapper;
	}

	public void setDeptCostsSubjectMapper(DeptCostsSubjectMapper deptCostsSubjectMapper) {
		this.deptCostsSubjectMapper = deptCostsSubjectMapper;
	}
	
	private SalesBillCheckMapper salesBillCheckMapper;
	
	
	public SalesBillCheckMapper getSalesBillCheckMapper() {
		return salesBillCheckMapper;
	}

	public void setSalesBillCheckMapper(SalesBillCheckMapper salesBillCheckMapper) {
		this.salesBillCheckMapper = salesBillCheckMapper;
	}
	private JournalSheetMapper journalSheetMapper;

	public JournalSheetMapper getJournalSheetMapper() {
		return journalSheetMapper;
	}

	public void setJournalSheetMapper(JournalSheetMapper journalSheetMapper) {
		this.journalSheetMapper = journalSheetMapper;
	}

	/**
	 * 显示部门费用科目列表
	 */
	@Override
	public List showDeptCostsSubjectList() throws Exception{
		List list=deptCostsSubjectMapper.getDeptCostsSubjectList();
		return list;
	}
	@Override
	public List showDeptCostsSubjectEnableList() throws Exception{
		List list=deptCostsSubjectMapper.showDeptCostsSubjectEnableList();
		return list;
	}
	@Override
	public List showDeptCostsSubjectListByMap(Map map) throws Exception{
		String cols = getAccessColumnList("t_js_departmentcosts_subject",null); //字段权限
		map.put("cols", cols);
		String sql = getDataAccessRule("t_js_departmentcosts_subject",null); //数据权限
		map.put("dataSql", sql);
		return deptCostsSubjectMapper.getDeptCostsSubjectListByMap(map);
	}	
	@Override
	public PageData showDeptCostsSubjectPageList(PageMap pageMap) throws Exception{

		String cols = getAccessColumnList("t_js_departmentcosts_subject",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_js_departmentcosts_subject",null); //数据权限
		pageMap.setDataSql(sql);
		int total=deptCostsSubjectMapper.getDeptCostsSubjectCount(pageMap);
		List list=deptCostsSubjectMapper.getDeptCostsSubjectPageList(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		return pageData;
	}
	@Override
	public DeptCostsSubject showDeptCostsSubjectById(String id) throws Exception{
		DeptCostsSubject deptCostsSubject=deptCostsSubjectMapper.getDeptCostsSubjectById(id);
		return deptCostsSubject;
	} 
	@Override
	public DeptCostsSubject getDeptCostsSubjectDetail(String id) throws Exception {
		String cols = getAccessColumnList("t_js_departmentcosts_subject", null);//加字段查看权限
		String sql = getDataAccessRule("t_js_departmentcosts_subject",null); //数据权限
		
		Map paramMap=new HashMap();
		paramMap.put("cols", cols);
		paramMap.put("dataSql", sql);
		paramMap.put("id", id);
		return deptCostsSubjectMapper.getDeptCostsSubjectByMap(paramMap);
	}
	
	/**
	 * 新增费用科目
	 */
	@Override
	public Map addDeptCostsSubject(DeptCostsSubject deptCostsSubject) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		boolean flag=false;
		if(StringUtils.isEmpty(deptCostsSubject.getId())){
			map.put("flag", false);
			map.put("msg", "代码不能为空");
			return map;
		}
		map.put("id", deptCostsSubject.getId());
		int icount=deptCostsSubjectMapper.getDeptCostsSubjectCountByMap(map);
		if(icount==1){
			map.put("flag", false);
			map.put("msg", "代码已经存在");
			return map;
		}
		flag=deptCostsSubjectMapper.addDeptCostsSubject(deptCostsSubject)>0;
		map.put("flag", flag);
		return map;
	}
	
	/**
	 * 修改费用科目信息
	 */
	@Override
	public Map editDeptCostsSubject(DeptCostsSubject deptCostsSubject) throws Exception{
		Map map = new HashMap();
		Map map2 = new HashMap();
		DeptCostsSubject olddeptCostsSubject = getDeptCostsSubjectDetail(deptCostsSubject.getOldId());
		if(null != olddeptCostsSubject){
			if((StringUtils.isNotEmpty(olddeptCostsSubject.getThisid()) && !olddeptCostsSubject.getThisid().equals(deptCostsSubject.getThisid())) ||
					(StringUtils.isNotEmpty(olddeptCostsSubject.getThisname())&&!olddeptCostsSubject.getThisname().equals(deptCostsSubject.getThisname()))){
				//获取下级所有分类列表
				List<DeptCostsSubject> childList = getdeptCostsSubjectChildList(olddeptCostsSubject.getId());
				//若编码改变，下属所有的任务分类编码应做对应的替换
				if(childList.size() != 0){
					for(DeptCostsSubject repeatItem : childList){
						repeatItem.setOldId(repeatItem.getId());
						if(!olddeptCostsSubject.getThisid().equals(deptCostsSubject.getThisid())){
							String newid = repeatItem.getId().replaceFirst(olddeptCostsSubject.getThisid(), deptCostsSubject.getThisid()).trim();
							repeatItem.setId(newid);
							String newpid = repeatItem.getPid().replaceFirst(olddeptCostsSubject.getThisid(), deptCostsSubject.getThisid()).trim();
							repeatItem.setPid(newpid);
						}
						if(!olddeptCostsSubject.getThisname().equals(deptCostsSubject.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatItem.getName().replaceFirst(olddeptCostsSubject.getThisname(), deptCostsSubject.getThisname());
							repeatItem.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeatItem.getId());
						node.setParentid(repeatItem.getPid());
						node.setText(repeatItem.getThisname());
						node.setState(repeatItem.getState());
						map2.put(repeatItem.getOldId(), node);
					}
					deptCostsSubjectMapper.updateDeptCostsSubjectBatch(childList);
				}
			}
		}
		boolean flag = deptCostsSubjectMapper.updateDeptCostsSubject(deptCostsSubject) > 0;
		if(flag){
			Tree node = new Tree();
			node.setId(deptCostsSubject.getId());
			node.setParentid(deptCostsSubject.getPid());
			node.setText(deptCostsSubject.getThisname());
			node.setState(deptCostsSubject.getState());
			map2.put(deptCostsSubject.getOldId(), node);
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
	public List getdeptCostsSubjectChildList(String id) throws Exception{
		if(null==id || "".equals(id.trim())){
			return null;
		}
		Map paramMap=new HashMap();
		paramMap.put("pid", id.trim());
		return deptCostsSubjectMapper.getDeptCostsSubjectListByMap(paramMap);
	}
	/**
	 * 禁用费用科目
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@Override
	public Map disableDeptCostsSubject(String id) throws Exception{
		DeptCostsSubject deptCostsSubject = new DeptCostsSubject();
		SysUser sysUser = getSysUser();
		deptCostsSubject.setCloseuserid(sysUser.getUserid());
		deptCostsSubject.setCloseusername(sysUser.getName());
		deptCostsSubject.setId(id);
		
		Map paramMap = new HashMap();
		paramMap.put("likeid", id);
		List<DeptCostsSubject> list = deptCostsSubjectMapper.getDeptCostsSubjectListByMap(paramMap);//查询该节点及其所有子节点信息
		int successNum = 0,failureNum = 0,notAllowNum = 0;
		List<String> ids = new ArrayList<String>();
		for(DeptCostsSubject item : list){
			if(!"1".equals(item.getState())){
				notAllowNum++;
			}
			else{
				DeptCostsSubject upDeptCostsSubject = new DeptCostsSubject();
				upDeptCostsSubject.setCloseuserid(sysUser.getUserid());
				upDeptCostsSubject.setCloseusername(sysUser.getName());
				upDeptCostsSubject.setId(item.getId());
				boolean flag = deptCostsSubjectMapper.disableDeptCostsSubject(upDeptCostsSubject)>0;
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
	 * 启用费用科目
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	@Override
	public boolean enableDeptCostsSubject(String id) throws Exception{
		DeptCostsSubject deptCostsSubject = new DeptCostsSubject();
		SysUser sysUser = getSysUser();
		deptCostsSubject.setOpenuserid(sysUser.getUserid());
		deptCostsSubject.setOpenusername(sysUser.getName());
		deptCostsSubject.setId(id);
		return (deptCostsSubjectMapper.enableDeptCostsSubject(deptCostsSubject) > 0) && isTreeLeaf();
	}

	@Override
	public Map deleteDeptCostsSubjectById(String id)throws Exception{
		Map resultMap=new HashMap();
		if(null == id || "".equals(id.trim())){
			resultMap.put("flag", false);
			return resultMap;
		}
		boolean delFlag=false;
		delFlag=canTableDataDictDelete("t_js_departmentcosts_subject", id);
		if(delFlag){

			DeptCostsSubject deptCostsSubject=deptCostsSubjectMapper.getDeptCostsSubjectById(id);
			if(null!=deptCostsSubject && "1".equals(deptCostsSubject.getState())){
				resultMap.put("flag", false);
				resultMap.put("msg", "启用的数据不能被删除");
				return resultMap;
			}
			delFlag=deptCostsSubjectMapper.deleteDeptCostsSubjectById(id)>0;
			resultMap.put("delFlag", false);
		}else{
			resultMap.put("delFlag", true);	//是否被引用
		}
		resultMap.put("flag", delFlag);
		return resultMap;
	}
	/**
	 * 批量删除费用科目
	 */
	@Override
	public Map deleteDeptCostsSubjectMore(String idarrs)throws Exception{
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
			Map resultMap=deleteDeptCostsSubjectById(id);
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
	public boolean isTreeLeaf()throws Exception{
		int count = 0;
		//获取暂存以外的费用分类数据列表
		List<DeptCostsSubject> list = deptCostsSubjectMapper.showDeptCostsSubjectEnableList();
		if(list.size() != 0){
			for(DeptCostsSubject item:list){
				//判断pid是否存在,若存在则为末及标志，否则，不是
				String pid = deptCostsSubjectMapper.isLeafDeptCostsSubject(item.getId());
				if(!StringUtils.isNotEmpty(pid)){
					item.setLeaf("1");
				}
				else{
					item.setLeaf("0");
				}
				item.setOldId(item.getId());
				DeptCostsSubject beforeSubject = deptCostsSubjectMapper.getDeptCostsSubjectById(item.getId());
				//判断是否可以进行修改操作,如果可以修改，同时更新级联关系数据
				if(canBasefilesIsEdit("t_js_departmentcosts_subject", beforeSubject, item)){
					int i = deptCostsSubjectMapper.updateDeptCostsSubject(item);
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
	public List getDeptCostsSubjectParentAllChildren(Map map) throws Exception {
		String cols = getAccessColumnList("t_base_finance_expenses_sort",null); //字段权限
		map.put("cols", cols);
		String sql = getDataAccessRule("t_base_finance_expenses_sort",null); //数据权限
		map.put("dataSql",sql);
		if(map.containsKey("id")){
			map.put("likeid", map.get("id"));
			map.remove("id");
		}
		return deptCostsSubjectMapper.getDeptCostsSubjectListByMap(map);
	}

	@Override
	public boolean isUsedDeptCostsSubjectName(String name) throws Exception {
		return deptCostsSubjectMapper.isUsedDeptCostsSubjectName(name) > 0;
	}
	
	@Override
    public Map addDRDeptCostsSubjectExcel(List<DeptCostsSubject> list)throws Exception{
		Map returnMap = new HashMap();
        boolean flag = false;
        int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0,errorNum = 0,levelNum=0;
        String closeVal = "",repeatVal = "",failStr = "",errorVal = "",levelVal = "";
        if(list.size() != 0){
            String tn = "t_js_departmentcosts_subject";
            List levelList = showFilesLevelList(tn);
            if(levelList.size() != 0){
                for(DeptCostsSubject deptCostsSubject : list){
                    if(StringUtils.isEmpty(deptCostsSubject.getId())){
                        continue;
                    }
                    //根据档案级次信息根据编码获取本级编码
                    Map map = getObjectThisidByidCaseFilesLevel(tn,deptCostsSubject.getId());
                    if(null != map && !map.isEmpty()){
                        String id = deptCostsSubject.getId();
                        String thisid = (null != map.get("thisid")) ? map.get("thisid").toString() : "";
                        String pid = (null != map.get("pid")) ? map.get("pid").toString() : "";
                        try {
                            //判断商品分类是否重复
                            Map map1 = new HashMap();
                            map1.put("id",id);
                            DeptCostsSubject deptCostsSubjectInfo = deptCostsSubjectMapper.getDeptCostsSubjectByMap(map1);
                            if(deptCostsSubjectInfo == null){//不重复
                                deptCostsSubject.setThisid(thisid);
                                deptCostsSubject.setPid(pid);
                                SysUser sysUser = getSysUser();
                                deptCostsSubject.setAdduserid(sysUser.getUserid());
                                deptCostsSubject.setAdddeptid(sysUser.getDepartmentid());
                                deptCostsSubject.setAdddeptname(sysUser.getDepartmentname());
                                deptCostsSubject.setAddusername(sysUser.getName());
                                if(StringUtils.isEmpty(deptCostsSubject.getPid())){
                                    deptCostsSubject.setName(deptCostsSubject.getThisname());
                                }
                                deptCostsSubject.setState("1");
                                flag = deptCostsSubjectMapper.addDeptCostsSubject(deptCostsSubject) > 0;
                                if(!flag){
                                    if(StringUtils.isNotEmpty(failStr)){
                                        failStr += "," + deptCostsSubject.getId();
                                    }
                                    else{
                                        failStr = deptCostsSubject.getId();
                                    }
                                    failureNum++;
                                }
                                else{
                                    successNum++;
                                }
                            }
                            else{
                                if("0".equals(deptCostsSubjectInfo.getState())){//禁用状态，不允许导入
                                    if(StringUtils.isEmpty(closeVal)){
                                        closeVal = deptCostsSubjectInfo.getId();
                                    }
                                    else{
                                        closeVal += "," + deptCostsSubjectInfo.getId();
                                    }
                                    closeNum++;
                                }
                                else{
                                    if(StringUtils.isEmpty(repeatVal)){
                                        repeatVal = deptCostsSubjectInfo.getId();
                                    }
                                    else{
                                        repeatVal += "," + deptCostsSubjectInfo.getId();
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
                            levelVal += "," + deptCostsSubject.getId();
                        }
                        else{
                            levelVal = deptCostsSubject.getId();
                        }
                    }
                }
                //末级标志
                isTreeLeaf();
                Map map1=new HashMap();
                map1.put("nameLenZero", "true");
                map1.put("orderBySql", "id");
                //名称
                List<DeptCostsSubject> nonameList = deptCostsSubjectMapper.getDeptCostsSubjectListByMap(map1);
                if(nonameList.size() != 0){
                    for(DeptCostsSubject nonameES : nonameList){
                        DeptCostsSubject pES = getDeptCostsSubjectDetail(nonameES.getPid());
                        if(null != pES){
                            nonameES.setOldId(nonameES.getId());
                            String name = "";
                            if(StringUtils.isEmpty(pES.getName())){
                                name = nonameES.getThisname();
                            }else{
                                name = pES.getName() + "/" + nonameES.getThisname();
                            }
                            if(StringUtils.isNotEmpty(pES.getName())){
                                nonameES.setName(name);
                            }else{
                                nonameES.setName(pES.getThisname());
                            }
                            deptCostsSubjectMapper.updateDeptCostsSubject(nonameES);
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
	
	//================================================================//
	//部门费用表
	//================================================================//	
	
	@Override
	public PageData getDepartmentCostsPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_js_departmentcosts",null); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();

		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
			condition.put("showOrderOnly", "true");
		}
		StringBuilder sumsb=new StringBuilder();
		List<DeptCostsSubject> deptCostsSubjectsList = showDeptCostsSubjectList();
		StringBuilder sb=new StringBuilder();
		for(DeptCostsSubject deptCostsSubject : deptCostsSubjectsList){
			if(null!=deptCostsSubject && StringUtils.isNotEmpty(deptCostsSubject.getId())){
				sb.append(",IF(IFNULL(md.subjectid,'') ='");
				sb.append(deptCostsSubject.getId());
				sb.append("' ,md.amount,0.000000) AS deptCostsSubject_");
				sb.append(deptCostsSubject.getId());
				
				sumsb.append(",SUM(deptCostsSubject_");
				sumsb.append(deptCostsSubject.getId());
				sumsb.append(")");
				sumsb.append(" AS deptCostsSubject_");
				sumsb.append(deptCostsSubject.getId());
			}
		}
		if(sb.length()>0){
			condition.put("dyncSubjectColumn", sb.toString());
			condition.put("dyncSubjectSumColumn", sumsb.toString());
		}else {
			if(condition.containsKey("dyncSubjectColumn")){
				condition.remove("dyncSubjectColumn");
			}
			if(condition.containsKey("dyncSubjectSumColumn")){
				condition.remove("dyncSubjectSumColumn");
			}
		}
		
		List<Map> list = costsFeeMapper.getDepartmentCostsPageList(pageMap);
		if(list.size() != 0){
			String tmpstr="";
			for(Map map :list){
				tmpstr=(String)map.get("deptid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(tmpstr.trim());
					if(null!=departMent){
						map.put("deptname",departMent.getName());
					}
				}
				String status=(String)map.get("status");
				if(null!=status){					
					if("2".equals(status.trim())){//是否核销
						map.put("statusname", "保存");	
					}else if("3".equals(status.trim())){
						map.put("statusname", "审核");
					}
				}else{
					map.put("statusname", "");
				}
			}
		}		
		List<Map> footerList = new ArrayList<Map>();
		Map sumMap = costsFeeMapper.getDepartmentCostsPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("deptname","合计");
			footerList.add(sumMap);
		}
		int pageCount=0;
		if(!"true".equals(isExportData)){
			pageCount=costsFeeMapper.getDepartmentCostsPageCount(pageMap);
		}
		PageData pageData = new PageData(pageCount,list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public Map<String, Object> addDepartmentCosts(Map<String,Object> requestMap) throws Exception{
		Map<String, Object> resultMap=new HashMap<String, Object>(); 
		String businessdate=(String)requestMap.get("businessdate");
		String deptid=(String)requestMap.get("deptid");
		if(null==businessdate || "".equals(businessdate.trim()) || !businessdate.trim().matches("^\\d{4}-\\d{2}$")){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期不能为空");
			return resultMap;
		}
		if(!businessdate.trim().matches("^\\d{4}-\\d{2}$")){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期格式不对，格式为yyyy-mm");
			return resultMap;
		}

		String year="";
		String month="";
		year=businessdate.split("-")[0];
		month=businessdate.split("-")[1];
		if(null==deptid || "".equals(deptid.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "部门编号不能为空");
			return resultMap;
		}
		Map queryMap=new HashMap();
		queryMap.put("businessdate", businessdate);
		queryMap.put("deptid", deptid);
		int iCount=costsFeeMapper.getDepartmentCostsCountByMap(queryMap);
		if(iCount>0){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期与部门相关的费用已经存在");
			return resultMap;			
		}
		List<DeptCostsSubject> subjectList = showDeptCostsSubjectEnableList();
		if(null==subjectList || subjectList.size()==0){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关部门费用科目");
			return resultMap;			
		}
		SysUser sysUser=getSysUser();
		
		Map<String,Object> amountMap=new HashMap<String,Object>();
		BigDecimal salesAmount=getCostsDeptSalesamount(businessdate, deptid);
		amountMap.put("salesAmount", salesAmount);
		
		DepartmentCosts departmentCosts=new DepartmentCosts();
		if(!"1".equals(departmentCosts.getStatus()) && !"2".equals(departmentCosts.getStatus())){
			departmentCosts.setStatus("2");
		}
		departmentCosts.setYear(year);
		departmentCosts.setMonth(month);
		departmentCosts.setBusinessdate(businessdate);
		departmentCosts.setDeptid(deptid);
		departmentCosts.setAdduserid(sysUser.getUserid());
		departmentCosts.setAddusername(sysUser.getName());
		if(costsFeeMapper.insertDepartmentCosts(departmentCosts)==0){
			resultMap.put("flag", false);
			return resultMap;	
		}
		if(null==departmentCosts.getId()){
			resultMap.put("flag", false);
			return resultMap;				
		}
		

		//获取销售打印单据数
		Long salesPrintPaper=getCostsDeptSalesPrintPapersSumAll(businessdate, deptid);
		amountMap.put("salesPrintPaper", salesPrintPaper);
		//获取分供应商销售打印单据条数
		List<Map> salesPrintPaperList=getCostsDeptSalesPrintPapersBySupplier(businessdate,deptid);
		amountMap.put("salesPrintPaperList", salesPrintPaperList);
		BigDecimal fundAverageAmount=getFundAverageStatistics(businessdate, deptid);
		amountMap.put("fundAverageAmount", fundAverageAmount);
		//按供应商销售金额
		List<Map<String, Object>> supplerSalesAmountList= getCostsSupplierSalesAmountSum(businessdate,deptid);
		amountMap.put("supplerSalesAmountList", supplerSalesAmountList);
		//按供应商平均资金占用
		List<Map<String, Object>> supplierAvgAmount= getSupplierFundAverageStatistics(businessdate,deptid);
		amountMap.put("supplierAvgAmount", supplierAvgAmount);
		
		for(DeptCostsSubject item : subjectList){
			if(null==item || StringUtils.isEmpty(item.getId())){
				continue;
			}
			BigDecimal amount=BigDecimal.ZERO;
			amount=(BigDecimal)getDeptCostsSubjectInitAmount(item,amountMap);
			if(null==amount){
				amount=BigDecimal.ZERO;
			}
			amount=amount.setScale(2, BigDecimal.ROUND_HALF_UP);
			DepartmentCostsDetail departmentCostsDetail=new DepartmentCostsDetail();
			departmentCostsDetail.setSubjectid(item.getId());
			departmentCostsDetail.setAmount(amount);
			departmentCostsDetail.setDeptcostsid(departmentCosts.getId());
			if(costsFeeMapper.insertDepartmentCostsDetail(departmentCostsDetail)==0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	//异常回滚
				resultMap.put("flag", false);
				return resultMap;
			}
			addDeptSupplierCostsApportion(departmentCosts.getId(), item,amount, amountMap);
		}
		resultMap.put("id", departmentCosts.getId());
		resultMap.put("flag", true);
		return resultMap;
	}
	@Override
	public Map<String, Object> editDepartmentCosts(Map<String,Object> requestMap) throws Exception{
		Map<String, Object> resultMap=new HashMap<String, Object>(); 
		String id=(String)requestMap.get("id");
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关费用信息");
			return resultMap;	
		}
		id=id.trim();
		String businessdate=(String)requestMap.get("businessdate");
		String deptid=(String)requestMap.get("deptid");
		if(null==businessdate || "".equals(businessdate.trim()) || !businessdate.trim().matches("^\\d{4}-\\d{2}$")){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期不能为空");
			return resultMap;
		}
		if(!businessdate.trim().matches("^\\d{4}-\\d{2}$")){
			resultMap.put("flag", false);
			resultMap.put("msg", "业务日期格式不对，格式为yyyy-mm");
			return resultMap;
		}
		String remark=(String)requestMap.get("remark");
		if(null==remark){
			remark="";
		}
		
		String year="";
		String month="";
		year=businessdate.split("-")[0];
		month=businessdate.split("-")[1];
		if(null==deptid || "".equals(deptid.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "部门编号不能为空");
			return resultMap;
		}
		Map queryMap=new HashMap();
		queryMap.put("businessdate", businessdate);
		queryMap.put("deptid", deptid);
		queryMap.put("id", id);
		DepartmentCosts oldDepartmentCosts=costsFeeMapper.getDepartmentCostsByMap(queryMap);
		if(null==oldDepartmentCosts){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关费用信息");
			return resultMap;			
		}
		if("3".equals(oldDepartmentCosts.getStatus())){
			resultMap.put("flag", false);
			resultMap.put("msg", "审核通过的数据不能修改");
			return resultMap;			
		}
		List<DeptCostsSubject> subjectList = showDeptCostsSubjectEnableList();	//显示所有数据
		if(null==subjectList || subjectList.size()==0){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关部门费用科目信息");
			return resultMap;			
		}
		SysUser sysUser=getSysUser();
		DepartmentCosts departmentCosts=new DepartmentCosts();
		departmentCosts.setId(oldDepartmentCosts.getId());
		departmentCosts.setRemark(remark);
		departmentCosts.setModifytime(new Date());
		departmentCosts.setModifyuserid(sysUser.getUserid());
		departmentCosts.setModifyusername(sysUser.getName());
		if(costsFeeMapper.updateDepartmentCosts(departmentCosts)==0){
			resultMap.put("flag", false);
			return resultMap;				
		}
		
		Map<String,Object> amountMap=new HashMap<String,Object>();
		BigDecimal salesAmount=getCostsDeptSalesamount(businessdate, deptid);
		amountMap.put("salesAmount", salesAmount);
		//获取销售打印单据条数
		Long salesPrintPaper=getCostsDeptSalesPrintPapersSumAll(businessdate, deptid);
		amountMap.put("salesPrintPaper", salesPrintPaper);
		//分供应商打印单据条数
		List<Map> salesPrintPaperList=getCostsDeptSalesPrintPapersBySupplier(businessdate,deptid);
		amountMap.put("salesPrintPaperList", salesPrintPaperList);
		
		BigDecimal fundAverageAmount=getFundAverageStatistics(businessdate, deptid);
		amountMap.put("fundAverageAmount", fundAverageAmount);
		//按供应商销售金额
		List<Map<String, Object>> supplerSalesAmountList= getCostsSupplierSalesAmountSum(businessdate,deptid);
		amountMap.put("supplerSalesAmountList", supplerSalesAmountList);
		//按供应商平均资金占用
		List<Map<String, Object>> supplierAvgAmount= getSupplierFundAverageStatistics(businessdate,deptid);
		amountMap.put("supplierAvgAmount", supplierAvgAmount);
		int iSucsess=0;
		int iCount=0;
		queryMap.clear();
		costsFeeMapper.deleteDepartmentCostsDetailByDeptcostsId(id.trim());
		costsFeeMapper.deleteDeptSupplierCostsByDeptcostsId(id.trim());
		
		for(DeptCostsSubject item : subjectList){
			if(null==item || StringUtils.isEmpty(item.getId())){
				continue;
			}
			BigDecimal amount=BigDecimal.ZERO;
			BigDecimal tmpd=BigDecimal.ZERO;
			String deptSubjectCosts=(String)requestMap.get("deptCostsSubject_"+item.getId());
			if(StringUtils.isNotEmpty(deptSubjectCosts) && CommonUtils.isNumStr(deptSubjectCosts)){
				tmpd=new BigDecimal(deptSubjectCosts);
			}
			String supplierid=(String)requestMap.get("supplierid_"+item.getId());

			amount=(BigDecimal)getDeptCostsSubjectInitAmount(item,amountMap);
			if(null==amount){
				amount=tmpd;
			}
			if(null==amount){
				amount=BigDecimal.ZERO;
			}
			
			amount=amount.setScale(2, BigDecimal.ROUND_HALF_UP);
			DepartmentCostsDetail departmentCostsDetail=new DepartmentCostsDetail();
			departmentCostsDetail.setSubjectid(item.getId());
			departmentCostsDetail.setAmount(amount);
			if(null!=supplierid && !"".equals(supplierid.trim())){
				departmentCostsDetail.setSupplierid(supplierid);
			}
			departmentCostsDetail.setDeptcostsid(departmentCosts.getId());			

			if(costsFeeMapper.insertDepartmentCostsDetail(departmentCostsDetail)>0){
				iSucsess=iSucsess+1;
			}
			
			updateDeptSupplierCostsApportion(Integer.parseInt(id.trim()),item,amount,amountMap,supplierid);
		}
		if(iSucsess>0){
			resultMap.put("flag", true);
		}else{
			resultMap.put("flag", false);
			
		}
		return resultMap;
	}
	@Override
	public boolean deleteDepartmentCosts(String id) throws Exception{
		if(null==id || "".equals(id.trim())){
			return false;
		}

		Map queryMap=new HashMap();
		queryMap.put("id", id.trim());
		queryMap.put("notAudit", "true");
		boolean flag=costsFeeMapper.deleteDepartmentCostsByMap(queryMap)>0;	
		if(flag){
			costsFeeMapper.deleteDepartmentCostsDetailByDeptcostsId(id.trim());
			costsFeeMapper.deleteDeptSupplierCostsByDeptcostsId(id.trim());
		}
		return flag;
	}
	/**
	 * 批量删除费用科目
	 */
	@Override
	public Map deleteDepartmentCostsMore(String idarrs)throws Exception{
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
		Map queryMap=new HashMap();
		queryMap.put("notAudit", "true");
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			queryMap.put("id", id.trim());
			if(costsFeeMapper.deleteDepartmentCostsByMap(queryMap)>0){
				costsFeeMapper.deleteDepartmentCostsDetailByDeptcostsId(id.trim());
				costsFeeMapper.deleteDeptSupplierCostsByDeptcostsId(id.trim());
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
	
	@Override
	public Map<String,Object> auditDepartmentCostsMore(String idarrs)throws Exception{
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
		String sql = getDataAccessRule("t_js_departmentcosts",null); //数据权限
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		queryMap.put("dataAuthSql", "true");
		DepartmentCosts departmentCosts=new DepartmentCosts();
		departmentCosts.setStatus("3");
		departmentCosts.setAudittime(new Date());
		departmentCosts.setAudituserid(sysUser.getUserid());
		departmentCosts.setAuditusername(sysUser.getName());
		queryMap.put("departmentCosts", departmentCosts);
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				iFailure=iFailure+1;
				continue;
			}
			queryMap.put("id", id.trim());
			if(costsFeeMapper.updateDepartmentCostsByMap(queryMap)>0){
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
	@Override
	public Map<String,Object> oppauditDepartmentCostsMore(String idarrs)throws Exception{
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
		String sql = getDataAccessRule("t_js_departmentcosts",null); //数据权限
		SysUser sysUser=getSysUser();
		Map queryMap=new HashMap();
		queryMap.put("dataAuthSql", "true");
		DepartmentCosts departmentCosts=new DepartmentCosts();
		departmentCosts.setStatus("2");
		departmentCosts.setAudittime(new Date());
		departmentCosts.setAudituserid(sysUser.getUserid());
		departmentCosts.setAuditusername(sysUser.getName());
		queryMap.put("departmentCosts", departmentCosts);
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				iFailure=iFailure+1;
				continue;
			}
			queryMap.put("id", id.trim());
			if(costsFeeMapper.updateDepartmentCostsByMap(queryMap)>0){
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
	@Override
	public DepartmentCosts showDepartmentCostsByMap(Map map) throws Exception{
		String id=(String)map.get("id");
		if(null==id || "".equals(id.trim())){
			return null;
		}
		id=id.trim();
		String showDetail=(String)map.get("showDetail");
		if(null==showDetail || !"true".equals(showDetail)){
			showDetail="true";
		}
		DepartmentCosts departmentCosts=costsFeeMapper.getDepartmentCosts(id);
		List<DepartmentCostsDetail> deptCostsDetailList=null;
		if(null!=departmentCosts && "true".equals(showDetail)){
			deptCostsDetailList=costsFeeMapper.getDepartmentCostsDetailListByDeptcostsId(id);
		}
		if(null==deptCostsDetailList){
			deptCostsDetailList=new ArrayList<DepartmentCostsDetail>();
		}
		departmentCosts.setDeptCostsDetailList(deptCostsDetailList);
		return departmentCosts;
	}
	@Override
	public void setDeptCostsSubjectListWithAmount(List<DeptCostsSubject> subjectList,List<DepartmentCostsDetail> detailList) throws Exception{
		if(null!=subjectList){
			for(DeptCostsSubject subject : subjectList){
				BigDecimal amount=BigDecimal.ZERO;
				String supplierid="";
				if(null!=detailList && StringUtils.isNotEmpty(subject.getId())){
					for(DepartmentCostsDetail detail : detailList){
						if(null!=detail && StringUtils.isNotEmpty(detail.getSubjectid())){
							if(subject.getId().equals(detail.getSubjectid())){
								amount=detail.getAmount();
								supplierid=detail.getSupplierid();
								break;
							}
						}
					}
				}
				if(null==amount){
					amount=BigDecimal.ZERO;
				}
			}
		}
	}
	/**
	 * 计算科目里，计算值类型相关的金额
	 * @param subjectList
	 * @param initZero
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-2
	 */
	private Map showDeptCostsSubjectInitAmountData(List<DeptCostsSubject> subjectList,Map amountMap,boolean initZero) throws Exception{
		Map resultMap=new HashMap();
		if(null==amountMap || amountMap.size()==0){
			return resultMap;
		}
		//获取销售金额
		BigDecimal salesamount=(BigDecimal)amountMap.get("salesAmount");
		if(null==salesamount){
			salesamount=BigDecimal.ZERO;
		}
		//获取销售打印单据数
		Long salesPrintPaper=(Long)amountMap.get("salesPrintPaper");
		if(null==salesPrintPaper){
			salesPrintPaper=new Long(0);
		}
		BigDecimal fundAverageAmount=(BigDecimal)amountMap.get("fundAverageAmount");
		if(null==fundAverageAmount){
			fundAverageAmount=BigDecimal.ZERO;
		}
		for(DeptCostsSubject subject : subjectList){
			BigDecimal resultDecimal=BigDecimal.ZERO;
			BigDecimal tmpd=null;

			resultMap.put(subject.getId(), BigDecimal.ZERO);
		}
		return resultMap;
	}
	private BigDecimal getDeptCostsSubjectInitAmount(DeptCostsSubject subject,Map amountMap) throws Exception{
		BigDecimal resultDecimal=null;
		if(null==amountMap || amountMap.size()==0){
			return resultDecimal;
		}
		//获取销售金额
		BigDecimal salesamount=(BigDecimal)amountMap.get("salesAmount");
		if(null==salesamount){
			salesamount=BigDecimal.ZERO;
		}
		//获取销售打印单据数
		Long salesPrintPaper=(Long)amountMap.get("salesPrintPaper");
		if(null==salesPrintPaper){
			salesPrintPaper=new Long(0);
		}
		BigDecimal fundAverageAmount=(BigDecimal)amountMap.get("fundAverageAmount");
		if(null==fundAverageAmount){
			fundAverageAmount=BigDecimal.ZERO;
		}

		return resultDecimal;
	}
	//===========================================================================
	//其他数据接口
	//===========================================================================
	/**
	 * 部门销售金额
	 * @param businessdate
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-2
	 */
	public BigDecimal getCostsDeptSalesamount(String businessdate,String deptid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim()) || null==deptid || "".equals(deptid.trim())){
			return BigDecimal.ZERO;
		}
		Map requestMap=new HashMap();
		////查询销售金额
		requestMap.put("businessyearmonth", businessdate.trim());
		requestMap.put("branddeptlike", deptid.trim());
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		Map saleAmountMap=salesBillCheckMapper.getSalesBillSalesAmountSum(pageMap);
		BigDecimal saleAmount=BigDecimal.ZERO;
		if(null!=saleAmountMap){
			saleAmount=(BigDecimal)saleAmountMap.get("salesamount");
			if(null==saleAmount){
				saleAmount=BigDecimal.ZERO;
			}
		}
		return saleAmount;
	}
	
	private Long getCostsDeptSalesPrintPapersSumAll(String businessdate,String deptid) throws Exception{
		Map requestMap=new HashMap();
		////查询销售金额
		requestMap.put("businessyearmonth", businessdate.trim());
		requestMap.put("branddeptlike", deptid.trim());
		requestMap.put("sumAllDetailNum", "true");
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		List<Map> detailNumList = salesBillCheckMapper.getSalesBillCheckDetailNum(pageMap);
		long billnums = (long)0;
		for(Map detailNumMap : detailNumList){
			//根据销售单据明细数量计算单据数量
			if(null != detailNumMap){
				Long detailNum = Long.parseLong(detailNumMap.get("detailnum").toString());
				billnums += detailNum;
			}else{
				billnums++;
			}
		}
		return billnums;
	}
	/**
	 * 分品牌供应商合计
	 * @param businessdate
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-31
	 */
	private List<Map> getCostsDeptSalesPrintPapersBySupplier(String businessdate,String deptid) throws Exception{
		Map requestMap=new HashMap();
		////查询销售金额
		requestMap.put("businessyearmonth", businessdate.trim());
		requestMap.put("branddeptlike", deptid.trim());
		requestMap.put("groupCountBySupplierid", "true");
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		List<Map> detailNumList = salesBillCheckMapper.getSalesBillCheckDetailNum(pageMap);
		return detailNumList;
	}
	/**
	 * 部门所在供应商销售金额
	 * @param businessdate
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-2
	 */
	private List<Map<String, Object>> getCostsSupplierSalesAmountSum(String businessdate,String deptid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim()) || null==deptid || "".equals(deptid.trim())){
			return null;
		}
		DepartMent departMent=departMentMapper.getDepartmentInfo(deptid.trim());
		
		Map requestMap=new HashMap();
		////查询销售金额
		requestMap.put("businessyearmonth", businessdate.trim());
		requestMap.put("branddeptlike", deptid.trim());
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		List<Map<String, Object>> list=salesBillCheckMapper.getSalesBillSupplierSalesAmountSum(pageMap);
		
		return list;
	}
	/**
	 * 资金占用
	 * @param businessdate
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-5
	 */
	private BigDecimal getFundAverageStatistics(String businessdate,String deptid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim()) || null==deptid || "".equals(deptid.trim())){
			return BigDecimal.ZERO;
		}
		Map requestMap=new HashMap();
		requestMap.put("businessyearmonth", businessdate.trim());
		requestMap.put("supplierdeptid", deptid.trim());
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		FundInput currentFA = journalSheetMapper.getSumAvgDataGroupBySupplier(pageMap);
		BigDecimal totalAmount=BigDecimal.ZERO;
		if(null!=currentFA){
			totalAmount = currentFA.getActingmatamount().add(currentFA.getAdvanceamount()).add(currentFA.getPayableamount())
			.add(currentFA.getReceivableamount()).add(currentFA.getStockamount());
		}
		return totalAmount;
	}
	/**
	 * 按供应商平均资金占用
	 * @param businessdate
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-5
	 */
	private List<Map<String, Object>> getSupplierFundAverageStatistics(String businessdate,String deptid) throws Exception{
		if(null==businessdate || "".equals(businessdate.trim()) || null==deptid || "".equals(deptid.trim())){
			return null;
		}
		Map requestMap=new HashMap();
		requestMap.put("businessyearmonth", businessdate.trim());
		requestMap.put("supplierdeptid", deptid.trim());
		PageMap pageMap=new PageMap();
		pageMap.setCondition(requestMap);
		List<Map<String, Object>> list= journalSheetMapper.getAverageDataGroupBySupplierMapList(pageMap);
		return list;
	}
	
	//================================================================//
	//分供应商摊派
	//================================================================//
	/**
	 * 新增供应商摊派
	 * @param deptCostsSubject
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-10
	 */
	private void addDeptSupplierCostsApportion(Integer deptcostsId,DeptCostsSubject deptCostsSubject,BigDecimal costsFee,Map<String,Object> amountMap) throws Exception{
		if(null==deptcostsId || null==deptCostsSubject || null==amountMap || null==costsFee){
			return ;
		}
		//获取销售金额
		BigDecimal salesAllAmount=(BigDecimal)amountMap.get("salesAmount");
		if(null==salesAllAmount){
			salesAllAmount=BigDecimal.ZERO;
		}
		BigDecimal fundAverageAmount=(BigDecimal)amountMap.get("fundAverageAmount");
		if(null==fundAverageAmount){
			fundAverageAmount=BigDecimal.ZERO;
		}
		List<Map<String, Object>> supplerSalesAmountList=(List<Map<String,Object>>)amountMap.get("supplerSalesAmountList");
		List<Map<String, Object>> supplierAvgAmountList=(List<Map<String,Object>>)amountMap.get("supplierAvgAmount");
		List<Map> salesPrintPaperList=(List<Map>)amountMap.get("salesPrintPaperList");
		
		DeptSupplierCosts deptSupplierCosts=null;

		int isuccess=0;
		//录入及单据数情况下计算方式
		if(null!=supplerSalesAmountList && BigDecimal.ZERO.compareTo(salesAllAmount)!=0){
			for(Map itemMap : supplerSalesAmountList){
				BigDecimal salesAmount=(BigDecimal)itemMap.get("salesamount");
				String supplierid=(String)itemMap.get("supplierid");
				if(null==supplierid || "".equals(supplierid.trim())){
					continue;
				}
				if(null==salesAmount){
					salesAmount=BigDecimal.ZERO;
				}
				BigDecimal zbAmount=salesAmount.divide(salesAllAmount,6,BigDecimal.ROUND_HALF_UP);
				zbAmount=zbAmount.multiply(costsFee);
				deptSupplierCosts=new DeptSupplierCosts();
				deptSupplierCosts.setDeptcostsid(deptcostsId);
				deptSupplierCosts.setSubjectid(deptCostsSubject.getId());
				deptSupplierCosts.setSupplierid(supplierid.trim());
				deptSupplierCosts.setAmount(zbAmount);
				if(costsFeeMapper.insertDeptSupplierCosts(deptSupplierCosts)>0){
					isuccess=isuccess+1;
				}
			}
		}
	}
	
	/**
	 * 更新供应商摊派
	 * @param deptCostsSubject
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-10
	 */
	private void updateDeptSupplierCostsApportion(Integer deptcostsId,DeptCostsSubject deptCostsSubject,BigDecimal costsFee,Map<String,Object> amountMap,String assignSupplier) throws Exception{
		if(null==deptcostsId || null==deptCostsSubject || null==amountMap || null==costsFee){
			return ;
		}
		DeptSupplierCosts deptSupplierCosts=null;
		if(null!=assignSupplier && !"".equals(assignSupplier.trim())){
			//指定了供应商摊派
			deptSupplierCosts=new DeptSupplierCosts();
			deptSupplierCosts.setDeptcostsid(deptcostsId);
			deptSupplierCosts.setSubjectid(deptCostsSubject.getId());
			deptSupplierCosts.setSupplierid(assignSupplier.trim());
			deptSupplierCosts.setAmount(costsFee);
			
			costsFeeMapper.insertDeptSupplierCosts(deptSupplierCosts);
			return;
		}
		//获取销售金额
		BigDecimal salesAllAmount=(BigDecimal)amountMap.get("salesAmount");
		if(null==salesAllAmount){
			salesAllAmount=BigDecimal.ZERO;
		}
		BigDecimal fundAverageAmount=(BigDecimal)amountMap.get("fundAverageAmount");
		if(null==fundAverageAmount){
			fundAverageAmount=BigDecimal.ZERO;
		}
		List<Map<String, Object>> supplerSalesAmountList=(List<Map<String,Object>>)amountMap.get("supplerSalesAmountList");
		List<Map<String, Object>> supplierAvgAmountList=(List<Map<String,Object>>)amountMap.get("supplierAvgAmount");
		List<Map> salesPrintPaperList=(List<Map>)amountMap.get("salesPrintPaperList");
		
		int icount=0;
		int isuccess=0;
		//录入及单据数情况下计算方式
		if(null!=supplerSalesAmountList && BigDecimal.ZERO.compareTo(salesAllAmount)!=0){
			for(Map itemMap : supplerSalesAmountList){
				BigDecimal salesAmount=(BigDecimal)itemMap.get("salesamount");
				String supplierid=(String)itemMap.get("supplierid");
				if(null==supplierid || "".equals(supplierid.trim())){
					continue;
				}
				if(null==salesAmount){
					salesAmount=BigDecimal.ZERO;
				}
				BigDecimal zbAmount=salesAmount.divide(salesAllAmount,6,BigDecimal.ROUND_HALF_UP);
				zbAmount=zbAmount.multiply(costsFee);
				deptSupplierCosts=new DeptSupplierCosts();
				deptSupplierCosts.setDeptcostsid(deptcostsId);
				deptSupplierCosts.setSubjectid(deptCostsSubject.getId());
				deptSupplierCosts.setSupplierid(supplierid.trim());
				deptSupplierCosts.setAmount(zbAmount);
				
				if(costsFeeMapper.insertDeptSupplierCosts(deptSupplierCosts)>0){
					isuccess=isuccess+1;
				}
			}
		}
	}
	
	public PageData getDeptSupplierCostsPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_js_departmentcosts",null); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();

		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
			condition.put("showOrderOnly", "true");
		}
		StringBuilder sumsb=new StringBuilder();
		List<DeptCostsSubject> deptCostsSubjectsList = showDeptCostsSubjectList();
		StringBuilder sb=new StringBuilder();
		for(DeptCostsSubject deptCostsSubject : deptCostsSubjectsList){
			if(null!=deptCostsSubject && StringUtils.isNotEmpty(deptCostsSubject.getId())){
				sb.append(",IF(IFNULL(s.subjectid,'') ='");
				sb.append(deptCostsSubject.getId());
				sb.append("' ,s.amount,0.000000) AS deptCostsSubject_");
				sb.append(deptCostsSubject.getId());
				
				sumsb.append(",SUM(deptCostsSubject_");
				sumsb.append(deptCostsSubject.getId());
				sumsb.append(")");
				sumsb.append(" AS deptCostsSubject_");
				sumsb.append(deptCostsSubject.getId());
			}
		}
		if(sb.length()>0){
			condition.put("dyncSubjectColumn", sb.toString());
			condition.put("dyncSubjectSumColumn", sumsb.toString());
		}else {
			if(condition.containsKey("dyncSubjectColumn")){
				condition.remove("dyncSubjectColumn");
			}
			if(condition.containsKey("dyncSubjectSumColumn")){
				condition.remove("dyncSubjectSumColumn");
			}
		}
		
		List<Map> list = costsFeeMapper.getDeptSupplierCostsPageList(pageMap);
		if(list.size() != 0){
			String tmpstr="";
			for(Map map :list){
				tmpstr=(String)map.get("deptid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(tmpstr.trim());
					if(null!=departMent){
						map.put("deptname",departMent.getName());
					}
				}
				tmpstr=(String)map.get("supplierid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属供应商
					BuySupplier buySupplier = getBaseBuySupplierMapper().getBuySupplier(tmpstr.trim());
					if(null!=buySupplier){
						map.put("suppliername",buySupplier.getName());
					}
				}
				String status=(String)map.get("status");
				if(null!=status){					
					if("2".equals(status.trim())){//是否核销
						map.put("statusname", "保存");	
					}else if("3".equals(status.trim())){
						map.put("statusname", "审核");
					}
				}else{
					map.put("statusname", "");
				}
			}
		}		
		List<Map> footerList = new ArrayList<Map>();
		Map sumMap = costsFeeMapper.getDeptSupplierCostsPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("deptname","合计");
			footerList.add(sumMap);
		}
		int pageCount=0;
		if(!"true".equals(isExportData)){
			pageCount=costsFeeMapper.getDeptSupplierCostsPageCount(pageMap);
		}
		PageData pageData = new PageData(pageCount,list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	
	//================================================================//
	//报表
	//================================================================//
	
	
	/**
	 * 部门费用明细报表分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public PageData getDepartmentCostsMonthPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_js_departmentcosts","c"); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();
		boolean showPageRowId=false;
		int intPageRowId=1;
		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
			condition.put("showOrderOnly", "true");
		}
		String isPageflag=(String)condition.get("isPageflag");
		if(null==isPageflag || !"true".equals(isPageflag)){
			showPageRowId=true;
		}
		if(condition.containsKey("groupBycols")){
			condition.remove("groupBycols");
		}
		String groupcols=(String)condition.get("groupcols");
		if(null!=groupcols && !"".equals(groupcols.trim())){
			String[] groupcolsArr=groupcols.trim().split(",");
			List<String> groupcolsList=new ArrayList<String>();
			for(int i=0;i<groupcolsArr.length;i++){
				if(null!=groupcolsArr[i] && !"".equals(groupcolsArr[i].trim()) ){
					groupcolsList.add(groupcolsArr[i]);
				}
			}
			if(groupcolsList.size()>0){
				groupcols=StringUtils.join(groupcolsList, ",");
				if(null!=groupcols && !"".equals(groupcols.trim())){
					condition.put("groupBycols",groupcols.trim());					
				}
			}
		}
		String sort=(String)condition.get("sort");
		String order=(String)condition.get("order");
		if( (null==sort || "".equals(sort.trim())) ||
		    (null==order || "".equals(order.trim())) && StringUtils.isEmpty(pageMap.getOrderSql()) ){
			pageMap.setOrderSql(" year asc ,deptid asc,seq asc ");
		}
		StringBuilder sb=new StringBuilder();
		StringBuilder ifSumSb=new StringBuilder();
		StringBuilder onlySumSb=new StringBuilder();
		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+i;
			}
			
			sb.append(",month_");
			sb.append(month);
			
			ifSumSb.append(",SUM(if(month='");
			ifSumSb.append(month);
			ifSumSb.append("',amount,0.000000))");
			ifSumSb.append(" AS month_");
			ifSumSb.append(month);
			
			onlySumSb.append(",SUM(month_");
			onlySumSb.append(month);
			onlySumSb.append(") AS month_");
			onlySumSb.append(month);
		}
		if(sb.length()>0){
			condition.put("dyncSubjectColumn", sb.toString());
			condition.put("dyncSubjectIfSumColumn", ifSumSb.toString());
			condition.put("dyncSubjectOnlySumColumn", onlySumSb.toString());
		}else {
			if(condition.containsKey("dyncSubjectColumn")){
				condition.remove("dyncSubjectColumn");
			}
			if(condition.containsKey("dyncSubjectIfSumColumn")){
				condition.remove("dyncSubjectIfSumColumn");
			}
			if(condition.containsKey("dyncSubjectOnlySumColumn")){
				condition.remove("dyncSubjectOnlySumColumn");
			}
		}
		
		List<Map> list = costsFeeMapper.getDepartmentCostsMonthReportPageList(pageMap);
		if(list.size() != 0){
			String tmpstr="";
			for(Map map :list){
				tmpstr=(String)map.get("deptid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(tmpstr.trim());
					if(null!=departMent){
						map.put("deptname",departMent.getName());
					}
				}
				if(showPageRowId){
					map.put("pageRowId", intPageRowId);
					intPageRowId=intPageRowId+1;
				}
			}
		}	
		List<Map> footerList = new ArrayList<Map>();
		condition.put("isSumAll", "true");
		Map sumMap = costsFeeMapper.getDepartmentCostsMonthReportPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("year","");
			sumMap.put("subjectname","合计");
			footerList.add(sumMap);
		}
		int pageCount=costsFeeMapper.getDepartmentCostsMonthReportPageCount(pageMap);
		PageData pageData = new PageData(pageCount,list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	
	/**
	 * 部门分供应商费用明细报表分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public PageData getDeptSupplierCostsMonthPageData(PageMap pageMap) throws Exception{
		String sql = getDataAccessRule("t_js_departmentcosts","c"); //数据权限
		pageMap.setDataSql(sql);
		Map condition=pageMap.getCondition();
		boolean showPageRowId=false;
		int intPageRowId=1;
		String isExportData=(String)condition.get("isExportData");
		if("true".equals(isExportData)){
			condition.put("isPageflag", "true");
			condition.put("showOrderOnly", "true");
		}
		String isPageflag=(String)condition.get("isPageflag");
		if(null==isPageflag || !"true".equals(isPageflag)){
			showPageRowId=true;
		}
		if(condition.containsKey("groupBycols")){
			condition.remove("groupBycols");
		}
		String groupcols=(String)condition.get("groupcols");
		if(null!=groupcols && !"".equals(groupcols.trim())){
			String[] groupcolsArr=groupcols.trim().split(",");
			List<String> groupcolsList=new ArrayList<String>();
			for(int i=0;i<groupcolsArr.length;i++){
				if(null!=groupcolsArr[i] && !"".equals(groupcolsArr[i].trim()) ){
					groupcolsList.add(groupcolsArr[i]);
				}
			}
			if(groupcolsList.size()>0){
				groupcols=StringUtils.join(groupcolsList, ",");
				if(null!=groupcols && !"".equals(groupcols.trim())){
					condition.put("groupBycols",groupcols.trim());					
				}
			}
		}
		String sort=(String)condition.get("sort");
		String order=(String)condition.get("order");
		if( (null==sort || "".equals(sort.trim())) ||
		    (null==order || "".equals(order.trim())) && StringUtils.isEmpty(pageMap.getOrderSql()) ){
			pageMap.setOrderSql(" year asc ,supplierid asc ,deptid asc,subjectthisid asc ");
		}
		StringBuilder sb=new StringBuilder();
		StringBuilder ifSumSb=new StringBuilder();
		StringBuilder onlySumSb=new StringBuilder();
		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+i;
			}
			
			sb.append(",month_");
			sb.append(month);
			
			ifSumSb.append(",SUM(if(month='");
			ifSumSb.append(month);
			ifSumSb.append("',amount,0.000000))");
			ifSumSb.append(" AS month_");
			ifSumSb.append(month);
			
			onlySumSb.append(",SUM(month_");
			onlySumSb.append(month);
			onlySumSb.append(") AS month_");
			onlySumSb.append(month);
		}
		if(sb.length()>0){
			condition.put("dyncSubjectColumn", sb.toString());
			condition.put("dyncSubjectIfSumColumn", ifSumSb.toString());
			condition.put("dyncSubjectOnlySumColumn", onlySumSb.toString());
		}else {
			if(condition.containsKey("dyncSubjectColumn")){
				condition.remove("dyncSubjectColumn");
			}
			if(condition.containsKey("dyncSubjectIfSumColumn")){
				condition.remove("dyncSubjectIfSumColumn");
			}
			if(condition.containsKey("dyncSubjectOnlySumColumn")){
				condition.remove("dyncSubjectOnlySumColumn");
			}
		}
		
		List<Map> list = costsFeeMapper.getDeptSupplierCostsMonthReportPageList(pageMap);
		if(list.size() != 0){
			String tmpstr="";
			for(Map map :list){
				tmpstr=(String)map.get("deptid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属部门
					DepartMent departMent = departMentMapper.getDepartmentInfo(tmpstr.trim());
					if(null!=departMent){
						map.put("deptname",departMent.getName());
					}
				}
				tmpstr=(String)map.get("supplierid");
				if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属供应商
					BuySupplier buySupplier = getBaseBuySupplierMapper().getBuySupplier(tmpstr.trim());
					if(null!=buySupplier){
						map.put("suppliername",buySupplier.getName());
					}
				}
				if(showPageRowId){
					map.put("pageRowId", intPageRowId);
					intPageRowId=intPageRowId+1;
				}
			}
		}	
		List<Map> footerList = new ArrayList<Map>();
		condition.put("isSumAll", "true");
		Map sumMap = costsFeeMapper.getDeptSupplierCostsMonthReportPageSums(pageMap);
		if(null!=sumMap){
			sumMap.put("year","");
			sumMap.put("subjectname","合计");
			footerList.add(sumMap);
		}
		int pageCount=costsFeeMapper.getDeptSupplierCostsMonthReportPageCount(pageMap);
		PageData pageData = new PageData(pageCount,list,pageMap);
		pageData.setFooter(footerList);
		return pageData;
	}

    @Override
    public PageData getCustomerInputOutputData(PageMap pageMap) throws Exception {
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        String column = "";
        if(StringUtils.isNotEmpty(groupcols)){
            if(groupcols.contains(",")){
                String[] args = groupcols.split(",");
                column = args[0];
                for(String a : args){
                    if(groupcols.contains("z")){
                        groupcols += "z."+a +",";
                    }else{
                        groupcols = "z."+a +",";
                    }
                }
                groupcols = groupcols.substring(0,groupcols.length()-1);
            }else{
                column = groupcols;
                groupcols = "z."+groupcols;
            }
        }else{
            column = "customerid";
            groupcols = "z.customerid";
        }
        pageMap.getCondition().put("groupcols", groupcols);
        pageMap.getCondition().put("type", "1");
        List<Map> list = costsFeeMapper.getCustomerInputOutputData(pageMap);
        if(list.size() != 0){
            String tmpstr="";
            for(Map map :list){
                Map feeMap = new HashMap();
                tmpstr=(String)map.get("customerid");
                if(null!=tmpstr && !"".equals(tmpstr.trim())) {
                    Customer customer = getBaseCustomerMapper().getCustomerInfo(tmpstr);
                    if(null != customer){
                        map.put("customername",customer.getName());
                    }
                }
                tmpstr=(String)map.get("salesuser");
                if(null!=tmpstr && !"".equals(tmpstr.trim())) {
                    Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(tmpstr);
                    if(null != personnel){
                        map.put("salesusername",personnel.getName());
                    }
                }
                tmpstr=(String)map.get("supplierid");
                if(null!=tmpstr && !"".equals(tmpstr.trim())){//所属供应商
                    BuySupplier buySupplier = getBaseBuySupplierMapper().getBuySupplier(tmpstr.trim());
                    if(null!=buySupplier){
                        map.put("suppliername",buySupplier.getName());
                    }
                }
                BigDecimal fee = (BigDecimal)map.get("fee");
                BigDecimal factoryamount = (BigDecimal)map.get("factoryamount");

                BigDecimal sendamount = (BigDecimal) map.get("sendamount");
                BigDecimal returnamount = (BigDecimal) map.get("returnamount");
                BigDecimal pushbalanceamount = (BigDecimal) map.get("pushbalanceamount");
                //销售金额=发货金额-退货金额+冲差金额
                BigDecimal saleamount = sendamount.subtract(returnamount).add(pushbalanceamount);
                map.put("saleamount",saleamount);
                //毛利额 = 销售金额 - 成本金额
                BigDecimal costamount = map.get("costamount") == null ? BigDecimal.ZERO : new BigDecimal(map.get("costamount").toString());
                BigDecimal salemarginamount = saleamount.subtract(costamount);
                map.put("salemarginamount",salemarginamount);

                BigDecimal realrate =new BigDecimal(100);
                BigDecimal singlecost = new BigDecimal(100);
                BigDecimal singleoutput = new BigDecimal(100);
                if(saleamount.compareTo(BigDecimal.ZERO) != 0 ){
                    //实际毛利率 = （销售金额 - 成本金额）/销售金额*100
                    realrate = saleamount.subtract(costamount).divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
                    realrate = realrate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    //单店费比 = 费用合计/销售金额*100
                    singlecost = fee.divide(saleamount,6,BigDecimal.ROUND_HALF_UP);
                    singlecost = singlecost.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);

                    //单店产出比 = （销售毛利额+工厂投入-费用合计）/销售金额*100
                    singleoutput = salemarginamount.add(factoryamount).subtract(fee).divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
                    singleoutput = singleoutput.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                map.put("realrate",realrate);
                map.put("singlecost",singlecost);
                map.put("singleoutput",singleoutput);
            }
        }
        int pageCount=costsFeeMapper.getCustomerInputOutputDataCount(pageMap);
        PageData pageData = new PageData(pageCount,list,pageMap);
        if(list.size() > 0){
            List<Map> footer = costsFeeMapper.getCustomerInputOutputDataSum(pageMap);
            for(Map map : footer){
                if("salesuser".equals(column)){
                    map.put("salesusername","合计");
                }else{
                    map.put(column,"合计");
                }
				BigDecimal fee = (BigDecimal) map.get("fee");
                BigDecimal sendamount = (BigDecimal) map.get("sendamount");
                BigDecimal returnamount = (BigDecimal) map.get("returnamount");
                BigDecimal pushbalanceamount = (BigDecimal) map.get("pushbalanceamount");
				BigDecimal factoryamount = (BigDecimal) map.get("factoryamount");
                //销售金额=发货金额-退货金额+冲差金额
                BigDecimal saleamount = sendamount.subtract(returnamount).add(pushbalanceamount);
                map.put("saleamount",saleamount);
                //毛利额 = 销售金额 - 成本金额
                BigDecimal costamount = map.get("costamount") == null ? BigDecimal.ZERO : new BigDecimal(map.get("costamount").toString());
                BigDecimal salemarginamount = saleamount.subtract(costamount);
                map.put("salemarginamount",salemarginamount);
				//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
				BigDecimal realrate = new BigDecimal(0);
				if(saleamount.compareTo(BigDecimal.ZERO) != 0){
					realrate = saleamount.subtract(costamount).divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
				}
				realrate = realrate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
				map.put("realrate",realrate);
				//单店费比 = 费用合计/销售金额*100
				BigDecimal singlecost = new BigDecimal(0);
				if(saleamount.compareTo(BigDecimal.ZERO) != 0){
					singlecost = fee.divide(saleamount,6,BigDecimal.ROUND_HALF_UP);
				}
				singlecost = singlecost.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
				map.put("singlecost",singlecost);
				//单店产出比 = （销售毛利额+工厂投入-费用合计）/销售金额*100
				BigDecimal singleoutput = new BigDecimal(0);
				if(saleamount.compareTo(BigDecimal.ZERO) != 0){
					singleoutput = salemarginamount.add(factoryamount).subtract(fee).divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
				}
				singleoutput = singleoutput.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
				map.put("singleoutput",singleoutput);
            }
            pageData.setFooter(footer);
        }
        return pageData;
    }

    @Override
    public PageData getPayableDetailList(PageMap pageMap) throws Exception {

        Map condtion = pageMap.getCondition();
        for (Object key : condtion.keySet()) {
            String value = (String) condtion.get(key);
            if("empty".equals(value)){
                condtion.put(key,"");
            }
        }
        List<CustomerCostPayable> list = costsFeeMapper.getPayableDetailList(pageMap);
        for(CustomerCostPayable customerCostPayable : list){
            if(StringUtils.isNotEmpty(customerCostPayable.getExpensesort())){
                Map m = new HashMap();
                m.put("id",customerCostPayable.getExpensesort());
                ExpensesSort expensesSort = getBaseFinanceMapper().getExpensesSortDetail(m);
                if(null!=expensesSort){
                    customerCostPayable.setExpensesortname(expensesSort.getName());
                }
            }
            if(StringUtils.isNotEmpty(customerCostPayable.getSupplierid())){
                BuySupplier buySupplier = getBaseBuySupplierMapper().getBuySupplier(customerCostPayable.getSupplierid());
                if(null!=buySupplier){
                    customerCostPayable.setSuppliername(buySupplier.getName());
                }
            }
            if(StringUtils.isNotEmpty(customerCostPayable.getCustomerid())){
                Customer customer = getBaseCustomerMapper().getCustomerInfo(customerCostPayable.getCustomerid());
                if(null!=customer){
                    customerCostPayable.setCustomername(customer.getName());
                }
            }
        }
		int count = costsFeeMapper.getPayableDetailListCount(pageMap);
        return new PageData(count,list,pageMap);
    }

	@Override
	public PageData getDailyCostDetailList(PageMap pageMap) throws Exception {
		Map condtion = pageMap.getCondition();
		for (Object key : condtion.keySet()) {
			String value = (String) condtion.get(key);
			if("empty".equals(value)){
				condtion.put(key,"");
			}
		}
		List<DeptDailyCost> list = costsFeeMapper.getDailyCostDetailData(pageMap);
		for(DeptDailyCost cost : list){
			if(StringUtils.isNotEmpty(cost.getBankid())){
				Bank bank = getBaseFinanceMapper().getBankDetail(cost.getBankid());
				if(null != bank){
					cost.setBankname(bank.getName());
				}
			}if(StringUtils.isNotEmpty(cost.getDeptid())){
				DepartMent department = getDepartmentByDeptid(cost.getDeptid());
				if(null != department){
					cost.setDeptname(department.getName());
				}
			}
			if(StringUtils.isNotEmpty(cost.getCostsort())){
				DeptCostsSubject deptCostsSubject = deptCostsSubjectMapper.getDeptCostsSubjectById(cost.getCostsort());
				if(null!=deptCostsSubject){
					cost.setCostsortname(deptCostsSubject.getName());
				}
			}
			if(StringUtils.isNotEmpty(cost.getSalesuser())){
				Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(cost.getSalesuser());
				if (null != personnel) {
					cost.setSalesusername(personnel.getName());
				}
			}
			if(StringUtils.isNotEmpty(cost.getSupplierid())){
				BuySupplier buySupplier = getBaseBuySupplierMapper().getBuySupplier(cost.getSupplierid());
				if(null!=buySupplier){
					cost.setSuppliername(buySupplier.getName());
				}
			}
		}
		int count = costsFeeMapper.getDailyCostDetailDataCount(pageMap);
		return new PageData(count,list,pageMap);
	}

	@Override
    public PageData getSalesuserInputOutputData(PageMap pageMap) throws Exception {

        pageMap.getCondition().put("groupcols", "salesuser");
        pageMap.getCondition().put("type", "2");
        List<Map> list = costsFeeMapper.getCustomerInputOutputData(pageMap);
        if(list.size() != 0){
            for (Map map : list) {
                String salesuser = (String) map.get("salesuser");
                if (null != salesuser && !"".equals(salesuser.trim())) {
                    Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(salesuser);
                    if (null != personnel) {
                        map.put("salesusername", personnel.getName());
                    }
                }
                getMapResult(map);
            }
        }
        int pageCount=costsFeeMapper.getCustomerInputOutputDataCount(pageMap);
        PageData pageData = new PageData(pageCount,list,pageMap);
        if(list.size() > 0){
            List<Map> footer = costsFeeMapper.getCustomerInputOutputDataSum(pageMap);
            for(Map map : footer){
                map.put("salesusername","合计");
                getMapResult(map);
            }
            pageData.setFooter(footer);
        }
        return pageData;
    }

	/**
	 * 组装报表数据
	 * @param map
	 * @throws Exception
	 */
    public void getMapResult(Map map) throws Exception {
        BigDecimal sendamount = (BigDecimal) map.get("sendamount");
        BigDecimal returnamount = (BigDecimal) map.get("returnamount");
        BigDecimal pushbalanceamount = (BigDecimal) map.get("pushbalanceamount");
        //销售金额=发货金额-退货金额+冲差金额
        BigDecimal saleamount = sendamount.subtract(returnamount).add(pushbalanceamount);
        map.put("saleamount",saleamount);

        BigDecimal costamount = map.get("costamount") == null ? BigDecimal.ZERO : new BigDecimal(map.get("costamount").toString());
        //毛利额 = 销售金额 - 成本金额
        BigDecimal salemarginamount = saleamount.subtract(costamount);
        map.put("salemarginamount",salemarginamount);

        BigDecimal expense = (BigDecimal)map.get("factoryamount");
        map.put("expense",expense);
        //费用合计 = 费用支出+客户应付费用
        BigDecimal charge = (BigDecimal)map.get("charge");
        BigDecimal realrate = new BigDecimal(0);
        BigDecimal salesusercost = new BigDecimal(0);
        BigDecimal salesuseroutput = new BigDecimal(0);

        if(saleamount.compareTo(BigDecimal.ZERO) != 0) {
            //实际毛利率 = （销售金额 - 成本金额）/销售金额*100
            realrate = saleamount.subtract(costamount).divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
            realrate = realrate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //业务员费比 = 费用合计/销售金额*100
            salesusercost = charge.divide(saleamount,6,BigDecimal.ROUND_HALF_UP);
            salesusercost = salesusercost.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //业务员产出比 = （销售毛利额-费用合计）/销售金额*100
            salesuseroutput = salemarginamount.subtract(charge).divide(saleamount, 6, BigDecimal.ROUND_HALF_UP);
            salesuseroutput = salesuseroutput.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        map.put("realrate",realrate);
        map.put("salesusercost",salesusercost);
        map.put("salesuseroutput",salesuseroutput);
    }


}

