package com.hd.agent.crm.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.dao.CrmVisitRecordMapper;
import com.hd.agent.crm.model.CrmVisitRecord;
import com.hd.agent.crm.model.CrmVisitRecordDetail;
import com.hd.agent.crm.service.ICrmVisitRecordService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户拜访记录业务
 * @author master
 *
 */
public class CrmVisitRecordServiceImpl extends BaseFilesServiceImpl implements ICrmVisitRecordService {
	/**
	 * 客户拜访记录数据接口
	 */
	private CrmVisitRecordMapper crmVisitRecordMapper;

	public CrmVisitRecordMapper getCrmVisitRecordMapper() {
		return crmVisitRecordMapper;
	}

	public void setCrmVisitRecordMapper(CrmVisitRecordMapper crmVisitRecordMapper) {
		this.crmVisitRecordMapper = crmVisitRecordMapper;
	}
	
	
	/**
	 * 添加客户拜访记录及其明细信息
	 * @param crmVisitRecord
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	@Override
	public boolean addCrmVisitRecordAndDetail(CrmVisitRecord crmVisitRecord) throws Exception{
		if (isAutoCreate("t_crm_visit_record")) {
			// 获取自动编号
			crmVisitRecord.setId( getAutoCreateSysNumbderForeign(crmVisitRecord, "t_crm_visit_record"));
		}else{
			crmVisitRecord.setId( "CVR-"+CommonUtils.getDataNumberSendsWithRand());
		}
		if(StringUtils.isEmpty(crmVisitRecord.getId())){
			return false;
		}
        String rbillid = geCrmVisitRecordByKeyid(crmVisitRecord.getKeyid());
        if(null!=rbillid){
            return false;
        }
        SysUser sysUser = getSysUser();
        Customer customer = getCustomerByID(crmVisitRecord.getCustomerid());
        if(null!=customer){
            crmVisitRecord.setPcustomerid(customer.getPid());
            crmVisitRecord.setSalesarea(customer.getSalesarea());
            crmVisitRecord.setCustomersort(customer.getCustomersort());
            crmVisitRecord.setSalesdept(customer.getSalesdeptid());
            crmVisitRecord.setAdduserid(sysUser.getUserid());
            crmVisitRecord.setAddusername(sysUser.getName());
            crmVisitRecord.setAdddeptid(sysUser.getDepartmentid());
            crmVisitRecord.setAdddeptname(sysUser.getDepartmentname());
        }
        Personnel personnel = getPersonnelById(sysUser.getPersonnelid());
        if(null!=personnel){
            crmVisitRecord.setPersonid(personnel.getId());
            crmVisitRecord.setLeadid(personnel.getLeadid());
        }
		boolean flag=insertCrmVisitRecord(crmVisitRecord);
		List<CrmVisitRecordDetail> list=crmVisitRecord.getCrmVisitRecordDetailList();
		if(flag && list!=null && list.size()>0){
			int iseq=1;
			for(CrmVisitRecordDetail item : list){
                item.setBillid(crmVisitRecord.getId());
				crmVisitRecordMapper.insertCrmVisitRecordDetail(item);
				iseq=iseq+1;
			}
		}
		return flag;
	}
	/**
	 * 添加客户拜访记录
	 * @param crmVisitRecord
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public boolean insertCrmVisitRecord(CrmVisitRecord crmVisitRecord) throws Exception{
		return crmVisitRecordMapper.insertCrmVisitRecord(crmVisitRecord) >0;
	}
	/**
	 * 更新客户拜访记录
	 * @param crmVisitRecord
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public boolean updateCrmVisitRecord(CrmVisitRecord crmVisitRecord) throws Exception{
		return crmVisitRecordMapper.updateCrmVisitRecord(crmVisitRecord)>0;
	}
	/**
	 * 客户拜访记录分页列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public PageData showCrmVisitRecordPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_crm_visit_record",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_crm_visit_record",null);
		pageMap.setDataSql(sql);
		List<CrmVisitRecord> cvrList=crmVisitRecordMapper.getCrmVisitRecordPageList(pageMap);
		
		for(CrmVisitRecord item:cvrList){
			Personnel personnel=null;
			Customer customer=null;
			//人员信息
			if(StringUtils.isNotEmpty(item.getPersonid())){
				personnel=getPersonnelById(item.getPersonid());
				if(null!=personnel){
					item.setPersonname(personnel.getName());
				}
			}
			//主管信息
			if(StringUtils.isNotEmpty(item.getLeadid())){
				personnel=getPersonnelById(item.getLeadid());
				if(null!=personnel){
					item.setLeadname(personnel.getName());
				}
			}
			//客户
			if(StringUtils.isNotEmpty(item.getCustomerid())){
				customer=getCustomerByID(item.getCustomerid());
				if(null!=customer){
					item.setCustomername(customer.getName());
				}
			}
			//主管信息
			if(StringUtils.isNotEmpty(item.getPcustomerid())){
				customer=getCustomerByID(item.getPcustomerid());
				if(null!=customer){
					item.setPcustomername(customer.getName());
				}
			}
			//销售区域
			if(StringUtils.isNotEmpty(item.getSalesarea())){
				SalesArea salesArea=getSalesareaByID(item.getSalesarea());
				if(null!=salesArea){
					item.setSalesareaname(salesArea.getName());
				}
			}
			//销售部门
			if(StringUtils.isNotEmpty(item.getSalesdept())){
				DepartMent departMent=getDepartmentByDeptid(item.getSalesdept());
				if(null!=departMent){
					item.setSalesdeptname(departMent.getName());
				}
			}
			//客户分类
			if(StringUtils.isNotEmpty(item.getCustomersort())){
				CustomerSort customerSort=getCustomerSortByID(item.getCustomersort());
				if(null!=customerSort){
					item.setCustomersortname(customerSort.getName());
				}
			}
		}
		
		int icount=crmVisitRecordMapper.getCrmVisitRecordPageCount(pageMap);
		
		PageData pageData=new PageData(icount, cvrList, pageMap);

		List<CrmVisitRecord> footList=new ArrayList<CrmVisitRecord>();
		pageData.setFooter(footList);
		return pageData;
	}
	
	/**
	 * 根据ID编号获取客户拜访记录
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public CrmVisitRecord showCrmVisitRecord(String id) throws Exception{
		CrmVisitRecord crmVisitRecord= crmVisitRecordMapper.getCrmVisitRecord(id);
		if(null!=crmVisitRecord){
			Personnel personnel=null;
			Customer customer=null;
			//人员信息
			if(StringUtils.isNotEmpty(crmVisitRecord.getPersonid())){
				personnel=getPersonnelById(crmVisitRecord.getPersonid());
				if(null!=personnel){
					crmVisitRecord.setPersonname(personnel.getName());
				}
			}
			//主管信息
			if(StringUtils.isNotEmpty(crmVisitRecord.getLeadid())){
				personnel=getPersonnelById(crmVisitRecord.getLeadid());
				if(null!=personnel){
					crmVisitRecord.setLeadname(personnel.getName());
				}
			}
			//客户
			if(StringUtils.isNotEmpty(crmVisitRecord.getCustomerid())){
				customer=getCustomerByID(crmVisitRecord.getCustomerid());
				if(null!=customer){
					crmVisitRecord.setCustomername(customer.getName());
				}
			}
			//主管信息
			if(StringUtils.isNotEmpty(crmVisitRecord.getPcustomerid())){
				customer=getCustomerByID(crmVisitRecord.getPcustomerid());
				if(null!=customer){
					crmVisitRecord.setPcustomername(customer.getName());
				}
			}
			//销售区域
			if(StringUtils.isNotEmpty(crmVisitRecord.getSalesarea())){
				SalesArea salesArea=getSalesareaByID(crmVisitRecord.getSalesarea());
				if(null!=salesArea){
					crmVisitRecord.setSalesareaname(salesArea.getName());
				}
			}
			//销售部门
			if(StringUtils.isNotEmpty(crmVisitRecord.getSalesdept())){
				DepartMent departMent=getDepartmentByDeptid(crmVisitRecord.getSalesdept());
				if(null!=departMent){
					crmVisitRecord.setSalesdeptname(departMent.getName());
				}
			}
			//客户分类
			if(StringUtils.isNotEmpty(crmVisitRecord.getCustomersort())){
				CustomerSort customerSort=getCustomerSortByID(crmVisitRecord.getCustomersort());
				if(null!=customerSort){
					crmVisitRecord.setCustomersortname(customerSort.getName());
				}
			}
		}
		return crmVisitRecord;
	}
	/**
	 * 根据ID编号删除客户拜访记录及其明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public boolean deleteCrmVisitRecordAndDetail(String id) throws Exception{
		boolean isok=false;
		Map queryMap=new HashMap();
		queryMap.put("id", id);
		queryMap.put("notAudit", "1");
		isok=crmVisitRecordMapper.deleteCrmVisitRecordBy(queryMap)>0;
		crmVisitRecordMapper.deleteCrmVisitRecordDetailByOrderid(id);
		return isok;
	}
	/**
	 * 检查客户拜访记录
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public Map auditCrmVisitRecord(String id) throws Exception{
		Map map=new HashMap();
		Boolean flag=false;
		CrmVisitRecord oldCrmVisitRecord=showCrmVisitRecordByDataAuth(id);
		if("2".equals(oldCrmVisitRecord.getStatus())){
			map.put("billid", id);
			int acount=crmVisitRecordMapper.getCrmVisitRecordDetailCountBy(map);
			map.clear();
			if(acount==0){
				map.put("flag", false);
				map.put("msg", "未能找到拜访记录明细信息!");
				return map;
			}
			SysUser sysUser=getSysUser();
			oldCrmVisitRecord.setBusinessdate(CommonUtils.getTodayDataStr());
			oldCrmVisitRecord.setStatus("3");
			flag=crmVisitRecordMapper.updateCrmVisitRecordStatus(oldCrmVisitRecord)>0;
		}
		map.put("flag", flag);
		map.put("msg", "");
		return map;
	}
	/**
	 * 检查设置客户拜访记录不合格
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public Map oppauditCrmVisitRecord(String id) throws Exception{
		Map map=new HashMap();
		Boolean flag=false;
		CrmVisitRecord oldCrmVisitRecord=showCrmVisitRecordByDataAuth(id);
		if("3".equals(oldCrmVisitRecord.getStatus())){
			CrmVisitRecord crmVisitRecord=new CrmVisitRecord();
			SysUser sysUser=getSysUser();
			crmVisitRecord.setId(id);
			crmVisitRecord.setStatus("2");
			flag=crmVisitRecordMapper.updateCrmVisitRecordStatus(crmVisitRecord)>0;
			if(flag){				
				List<CrmVisitRecordDetail> list=crmVisitRecordMapper.getCrmVisitRecordDetailList(crmVisitRecord.getId());			
			}
			map.put("flag", true);
		}else if("4".equals(oldCrmVisitRecord.getStatus())){
			map.put("flag", flag);
			map.put("msg", "抱歉，设置不合格失败，因为该客户拜访记录已经关闭");
		}else{

			map.put("flag", flag);
			map.put("msg", "抱歉，设置不合格失败，因为该客户拜访记录未被审核");
		}
		return map;
	}
	/**
	 *  根据ID编号及数据权限获取客户拜访记录
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public CrmVisitRecord showCrmVisitRecordByDataAuth(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String datasql = getDataAccessRule("t_crm_visit_record",null);
		map.put("authDataSql", datasql);		
		return crmVisitRecordMapper.getCrmVisitRecordBy(map);
	}
	/**
	 * 更新客户拜访记录及其明细
	 * @param crmVisitRecord
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public boolean updateCrmVisitRecordAddDetail(CrmVisitRecord crmVisitRecord) throws Exception{
		boolean flag=crmVisitRecordMapper.updateCrmVisitRecord(crmVisitRecord)>0;
		if(flag ){
			crmVisitRecordMapper.deleteCrmVisitRecordDetailByOrderid(crmVisitRecord.getId());
			List<CrmVisitRecordDetail> list=crmVisitRecord.getCrmVisitRecordDetailList();
			BigDecimal totalamount=BigDecimal.ZERO;
			if(null!=list && list.size()>0){
				int iseq=1;
				for(CrmVisitRecordDetail item : list){	
					//item.setOrderid(crmVisitRecord.getId());
					crmVisitRecordMapper.insertCrmVisitRecordDetail(item);
					iseq=iseq+1;
				}
			}
		}
		return flag;
	}
	/**
	 * 根据ID编号查看客户拜访记录及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public CrmVisitRecord showCrmVisitRecordAndDetail(String id) throws Exception{
		CrmVisitRecord crmVisitRecord= crmVisitRecordMapper.getCrmVisitRecord(id);
		if(null!=crmVisitRecord){
			List<CrmVisitRecordDetail> list=crmVisitRecordMapper.getCrmVisitRecordDetailList(crmVisitRecord.getId());
			if(null!=list && list.size()>0){
				
				crmVisitRecord.setCrmVisitRecordDetailList(list);
			}else{
				crmVisitRecord.setCrmVisitRecordDetailList(new ArrayList<CrmVisitRecordDetail>());
			}
		}
		return crmVisitRecord;
	}
	
	/**
	 * 客户拜访记录分页列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public PageData showCrmVisitRecordDetailPageList(PageMap pageMap) throws Exception{
		
		Map condition=pageMap.getCondition();
		
		String columnLens=(String)condition.get("columnLen");
		
		int iColumnLen=0;
		if(null==columnLens || !StringUtils.isNumeric(columnLens)){
			iColumnLen=3;
		}else{
			iColumnLen=Integer.parseInt(columnLens);
		}
		if(iColumnLen==0){
			iColumnLen=3;
		}
		
		
		
		List<CrmVisitRecordDetail> cvrDetailList=crmVisitRecordMapper.getCrmVisitRecordDetailPageList(pageMap);

		List<Map<String, Object>> dataList=new ArrayList<Map<String,Object>>();
		Map<String, Object> dataMap=new HashMap<String, Object>();
		int ichange=1;
		long rowId=1;
		for(CrmVisitRecordDetail item:cvrDetailList){
			Brand brandInfo=null;
			SysCode sysCode=null;
			//品牌信息
			if(StringUtils.isNotEmpty(item.getBrandid())){
				brandInfo=getGoodsBrandByID(item.getBrandid());
				if(null!=brandInfo){
					item.setBrandname(brandInfo.getName());
				}
			}
			
			if(StringUtils.isNotEmpty(item.getStandard())){
				sysCode=getBaseSysCodeMapper().getSysCodeInfo(item.getStandard(), "displayStandard");
				if(null!=sysCode){
					item.setStandardname(sysCode.getCodename());
				}
			}
			
			
			dataMap.put("data"+ichange, item);			
			if(ichange==iColumnLen){
				dataMap.put("id", rowId);
				dataList.add(dataMap);
				ichange=1;
				dataMap=new HashMap<String, Object>();
				rowId=rowId+1;
			}else{
				ichange=ichange+1;
			}
		}
		if((ichange-1) % iColumnLen !=0) {
			dataMap.put("id", rowId);
			dataList.add(dataMap);			
		}
		
		int icount=crmVisitRecordMapper.getCrmVisitRecordDetailPageCount(pageMap);

		PageData pageData=new PageData(icount, 
				dataList, pageMap);
		
		return pageData;
	}
	
	/**
	 * 设置拜访记录明细合格
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月9日
	 */
	@Override
	public Map setOKVisitRecordDetail(String idarrs) throws Exception{
		Map resultMap=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){

			resultMap.put("flag", false);
			resultMap.put("isuccess", iSuccess);
			resultMap.put("ifailure", iFailure);
			return resultMap;
		}
		String[] idarr=idarrs.trim().split(",");
		Map requestMap=new HashMap();
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			CrmVisitRecordDetail crmVisitRecordDetail=crmVisitRecordMapper.getCrmVisitRecordDetail(id.trim());
			if(null==crmVisitRecordDetail || StringUtils.isEmpty(crmVisitRecordDetail.getBillid())){
				continue;
			}
			Map parammap = null;
			parammap = new HashMap();
			parammap.put("id", id.trim());
			parammap.put("isqa", "1");
			parammap.put("isqacheck", "1");
			if(crmVisitRecordMapper.updateOKVisitRecordDetail(parammap)>0){
				
				CrmVisitRecord crmVisitRecord=crmVisitRecordMapper.getCrmVisitRecord(crmVisitRecordDetail.getBillid());
				if(null==crmVisitRecord || !"4".equals(crmVisitRecord.getStatus())){
					parammap.clear();
					parammap.put("billid", crmVisitRecordDetail.getBillid());
					parammap.put("isqaFlag", "0");
					int icount=crmVisitRecordMapper.getCrmVisitRecordDetailCountBy(parammap);

					crmVisitRecord=new CrmVisitRecord();
					crmVisitRecord.setId(crmVisitRecordDetail.getBillid());
					if(icount==0){
						crmVisitRecord.setStatus("4");
					}else{
						crmVisitRecord.setStatus("3");					
					}
					crmVisitRecordMapper.updateCrmVisitRecordStatus(crmVisitRecord);
				}
				
				
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		resultMap.clear();
		if(iSuccess>0){
			resultMap.put("flag", true);
		}else{
			resultMap.put("flag", false);
		}
		resultMap.put("isuccess", iSuccess);
		resultMap.put("ifailure", iFailure);
		return resultMap;
	}
	/**
	 * 设置拜访记录明细不合格
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月9日
	 */
	public Map setNotOKVisitRecordDetail(String idarrs) throws Exception{

		Map resultMap=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){

			resultMap.put("flag", false);
			resultMap.put("isuccess", iSuccess);
			resultMap.put("ifailure", iFailure);
			return resultMap;
		}
		String[] idarr=idarrs.trim().split(",");
		Map requestMap=new HashMap();
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			CrmVisitRecordDetail crmVisitRecordDetail=crmVisitRecordMapper.getCrmVisitRecordDetail(id.trim());
			if(null==crmVisitRecordDetail || StringUtils.isEmpty(crmVisitRecordDetail.getBillid())){
				continue;
			}
			Map parammap = null;
			parammap = new HashMap();
			parammap.put("id", id);
			parammap.put("isqa", "2");
			parammap.put("isqacheck", "2"); //当合格的不作合格设置
			if(crmVisitRecordMapper.updateOKVisitRecordDetail(parammap)>0){
				
				CrmVisitRecord crmVisitRecord=crmVisitRecordMapper.getCrmVisitRecord(crmVisitRecordDetail.getBillid());
				if(null==crmVisitRecord || !"4".equals(crmVisitRecord.getStatus())){
					parammap.clear();
					parammap.put("billid", crmVisitRecordDetail.getBillid());
					parammap.put("isqaFlag", "0");
					int icount=crmVisitRecordMapper.getCrmVisitRecordDetailCountBy(parammap);

					crmVisitRecord=new CrmVisitRecord();
					crmVisitRecord.setId(crmVisitRecordDetail.getBillid());
					if(icount==0){
						crmVisitRecord.setStatus("4");
					}else{
						crmVisitRecord.setStatus("3");					
					}
					crmVisitRecordMapper.updateCrmVisitRecordStatus(crmVisitRecord);
				}
				
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		resultMap.clear();
		if(iSuccess>0){
			resultMap.put("flag", true);
		}else{
			resultMap.put("flag", false);
		}
		resultMap.put("isuccess", iSuccess);
		resultMap.put("ifailure", iFailure);
		return resultMap;
	}
	@Override
	public PageData showCrmVisitRecordOrderDetailPageList(PageMap pageMap) throws Exception{

		Map condition=pageMap.getCondition();
		
		String columnLens=(String)condition.get("columnLen");
		
		int iColumnLen=0;
		if(null==columnLens || !StringUtils.isNumeric(columnLens)){
			iColumnLen=3;
		}else{
			iColumnLen=Integer.parseInt(columnLens);
		}
		if(iColumnLen==0){
			iColumnLen=3;
		}
		pageMap.setStartNum(pageMap.getStartNum()*iColumnLen);
		pageMap.setEndNum(pageMap.getEndNum()*iColumnLen);
		pageMap.setRows(pageMap.getRows()*iColumnLen);
		String cols = getAccessColumnList("t_crm_visit_record","t");
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_crm_visit_record","t");
		pageMap.setDataSql(sql);
		
		List<Map<String, Object>> dataList=crmVisitRecordMapper.getCrmVisitRecordOrderDetailPageList(pageMap);

		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		Map<String, Object> dataMap=new HashMap<String, Object>();
		int ichange=1;
		long rowId=1;
		for(Map item:dataList){
			Brand brandInfo=null;
			Personnel personnel=null;
			Customer customer=null;
			SysCode sysCode=null;

			String tmp="";
			//人员信息
			tmp=(String)item.get("personid");
			item.put("personname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				personnel=getPersonnelById(tmp.trim());
				if(null!=personnel){
					item.put("personname",personnel.getName());
				}
			}
			//主管信息
			tmp=(String)item.get("leadid");
			item.put("leadname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				personnel=getPersonnelById(tmp.trim());
				if(null!=personnel){
					item.put("leadname", personnel.getName());
				}
			}
			//客户
			tmp=(String)item.get("customerid");
			item.put("customername","");
			if(null!=tmp && !"".equals(tmp.trim())){
				customer=getCustomerByID(tmp.trim());
				if(null!=customer){
					item.put("customername",customer.getName());
				}
			}
			//主管信息
			tmp=(String)item.get("pcustomerid");
			item.put("pcustomername","");
			if(null!=tmp && !"".equals(tmp.trim())){
				customer=getCustomerByID(tmp.trim());
				if(null!=customer){
					item.put("pcustomername",customer.getName());
				}
			}
			//销售区域
			tmp=(String)item.get("salesarea");
			item.put("salesareaname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				SalesArea salesArea=getSalesareaByID(tmp.trim());
				if(null!=salesArea){
					item.put("salesareaname",salesArea.getName());
				}
			}
			//销售部门
			tmp=(String)item.get("salesdept");
			item.put("salesdeptname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				DepartMent departMent=getDepartmentByDeptid(tmp.trim());
				if(null!=departMent){
					item.put("salesdeptname",departMent.getName());
				}
			}
			//客户分类
			tmp=(String)item.get("customersort");
			item.put("customersortname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				CustomerSort customerSort=getCustomerSortByID(tmp.trim());
				if(null!=customerSort){
					item.put("customersortname",customerSort.getName());
				}
			}
			//品牌信息
			tmp=(String)item.get("brandid");
			item.put("brandname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				brandInfo=getGoodsBrandByID(tmp.trim());
				if(null!=brandInfo){
					item.put("brandname", brandInfo.getName());
				}
			}
			
			tmp=(String)item.get("standard");
			item.put("standardname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				sysCode=getBaseSysCodeMapper().getSysCodeInfo(tmp.trim(), "displayStandard");
				if(null!=sysCode){
					item.put("standardname", sysCode.getCodename());
				}
			}
						
			
			
			dataMap.put("data"+ichange, item);			
			if(ichange==iColumnLen){
				dataMap.put("id", rowId);
				resultList.add(dataMap);
				ichange=1;
				dataMap=new HashMap<String, Object>();
				rowId=rowId+1;
			}else{
				ichange=ichange+1;
			}
		}
		if((ichange-1) % iColumnLen !=0) {
			dataMap.put("id", rowId);
			resultList.add(dataMap);			
		}
		
		int icount=crmVisitRecordMapper.getCrmVisitRecordOrderDetailPageCount(pageMap);
		int count = icount%iColumnLen==0?icount/iColumnLen:icount/iColumnLen+1;
		PageData pageData=new PageData(count,resultList, pageMap);
		
		return pageData;
	}
	/**
	 * 获取拜访记录及其明细信息<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月14日
	 */
	@Override
	public Map getCrmVisitRecordAndDetail(Map map) throws Exception{
		Map resultMap=crmVisitRecordMapper.getCrmVisitRecordAndDetail(map);
		if(null!=resultMap && resultMap.size()>0){
			SysCode sysCode=null;
			Brand brandInfo=null;
			Personnel personnel=null;
			Customer customer=null;

			String tmp="";
			//人员信息
			tmp=(String)resultMap.get("personid");
			resultMap.put("personname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				personnel=getPersonnelById(tmp.trim());
				if(null!=personnel){
					resultMap.put("personname",personnel.getName());
				}
			}
			//主管信息
			tmp=(String)resultMap.get("leadid");
			resultMap.put("leadname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				personnel=getPersonnelById(tmp.trim());
				if(null!=personnel){
					resultMap.put("leadname", personnel.getName());
				}
			}
			//客户
			tmp=(String)resultMap.get("customerid");
			resultMap.put("customername","");
			if(null!=tmp && !"".equals(tmp.trim())){
				customer=getCustomerByID(tmp.trim());
				if(null!=customer){
					resultMap.put("customername",customer.getName());
				}
			}
			//主管信息
			tmp=(String)resultMap.get("pcustomerid");
			resultMap.put("pcustomername","");
			if(null!=tmp && !"".equals(tmp.trim())){
				customer=getCustomerByID(tmp.trim());
				if(null!=customer){
					resultMap.put("pcustomername",customer.getName());
				}
			}
			//销售区域
			tmp=(String)resultMap.get("salesarea");
			resultMap.put("salesareaname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				SalesArea salesArea=getSalesareaByID(tmp.trim());
				if(null!=salesArea){
					resultMap.put("salesareaname",salesArea.getName());
				}
			}
			//销售部门
			tmp=(String)resultMap.get("salesdept");
			resultMap.put("salesdeptname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				DepartMent departMent=getDepartmentByDeptid(tmp.trim());
				if(null!=departMent){
					resultMap.put("salesdeptname",departMent.getName());
				}
			}
			//客户分类
			tmp=(String)resultMap.get("customersort");
			resultMap.put("customersortname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				CustomerSort customerSort=getCustomerSortByID(tmp.trim());
				if(null!=customerSort){
					resultMap.put("customersortname",customerSort.getName());
				}
			}
			//品牌信息
			tmp=(String)resultMap.get("brandid");
			resultMap.put("brandname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				brandInfo=getGoodsBrandByID(tmp.trim());
				if(null!=brandInfo){
					resultMap.put("brandname", brandInfo.getName());
				}
			}
			tmp=(String)resultMap.get("standard");
			resultMap.put("standardname","");
			if(null!=tmp && !"".equals(tmp.trim())){
				sysCode=getBaseSysCodeMapper().getSysCodeInfo(tmp.trim(), "displayStandard");
				if(null!=sysCode){
					resultMap.put("standardname", sysCode.getCodename());
				}
			}
			
		}
		return resultMap;
	}

    /**
     * 根据keyid 获取客户拜访记录编号 没有返回null
     * 用来判断记录是否重复
     *
     * @param keyid
     * @return
     * @throws Exception
     */
    @Override
    public String geCrmVisitRecordByKeyid(String keyid) throws Exception {
        CrmVisitRecord crmVisitRecord = crmVisitRecordMapper.getCrmVisitRecordByKeyid(keyid);
        if(null != crmVisitRecord){
            return crmVisitRecord.getId();
        }
        return null;
    }
}
