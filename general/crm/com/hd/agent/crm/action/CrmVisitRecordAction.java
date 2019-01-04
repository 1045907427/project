package com.hd.agent.crm.action;

import java.util.*;

import com.hd.agent.common.util.ExcelUtils;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.crm.model.CrmVisitRecord;
import com.hd.agent.crm.service.ICrmVisitRecordService;

/**
 * 客户拜访记录 Action
 */
public class CrmVisitRecordAction extends BaseFilesAction {
	/**
	 * 客户拜访记录 业务接口
	 */
	private ICrmVisitRecordService crmVisitRecordService;

	public ICrmVisitRecordService getCrmVisitRecordService() {
		return crmVisitRecordService;
	}

	public void setCrmVisitRecordService(
			ICrmVisitRecordService crmVisitRecordService) {
		this.crmVisitRecordService = crmVisitRecordService;
	}
	
	/**
	 * 添加拜访记录
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月7日
	 */
	public String addCrmVisitRecord() throws Exception{
		return SUCCESS;
	}
	/**
	 * 编辑拜访记录
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月7日
	 */
	public String editCrmVisitRecord() throws Exception{
		return SUCCESS;
	}
	/**
	 * 拜访记录列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月7日
	 */
	public String crmVisitRecordListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 显示拜访记录列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月7日
	 */
	public String showCrmVisitRecordPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(null!=map){
			if(map.containsKey("isNoPageflag")){
				map.remove("isNoPageflag");
			}
		}
		String sort = (String)map.get("sort");
		String order = (String) map.get("order");
		if(StringUtils.isEmpty(sort) || StringUtils.isEmpty(order)){
			if(map.containsKey("sort")){
				map.remove("sort");
			}
			if(map.containsKey("order")){
				map.remove("order");
			}
			pageMap.setOrderSql(" businessdate desc , id desc");
		}
        pageMap.setCondition(map);
        PageData pageData=crmVisitRecordService.showCrmVisitRecordPageList(pageMap);
        addJSONObjectWithFooter(pageData);

		return SUCCESS;
	}

    public void exportCrmVisitRecordPageList() throws Exception {

        Map map=CommonUtils.changeMap(request.getParameterMap());
        map.put("isNoPageflag", "true");
        String sort = (String)map.get("sort");
        String order = (String) map.get("order");
        if(StringUtils.isEmpty(sort) || StringUtils.isEmpty(order)){
            if(map.containsKey("sort")){
                map.remove("sort");
            }
            if(map.containsKey("order")){
                map.remove("order");
            }
            pageMap.setOrderSql(" businessdate desc , id desc");
        }
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        if(StringUtils.isEmpty(title)){
            title = "list";
        }
        //数据转换，list转化符合excel导出的数据格式
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("weekdayname","星期");
        firstMap.put("personname","业务员名称");
        firstMap.put("leadname","主管名称");
        firstMap.put("customerid","客户编号");
        firstMap.put("customername","客户名称");
        firstMap.put("pcustomername","上级客户");
        firstMap.put("customersortname","客户分类");
        firstMap.put("salesareaname","销售区域");
        firstMap.put("salesdeptname","销售部门");
        firstMap.put("addtime","制单时间");
        firstMap.put("status","检查状态");
        firstMap.put("isplan","线路内/外");
        firstMap.put("remark","备注");
        result.add(firstMap);

        PageData pageData=crmVisitRecordService.showCrmVisitRecordPageList(pageMap);
        List<CrmVisitRecord> list = pageData.getList();
        for(CrmVisitRecord record : list){
            if( 1 == record.getWeekday()){
                record.setWeekdayname("星期一");
            }else if( 2 == record.getWeekday()){
                record.setWeekdayname("星期二");
            }else if( 3 == record.getWeekday()){
                record.setWeekdayname("星期三");
            }else if( 4 == record.getWeekday()){
                record.setWeekdayname("星期四");
            }else if( 5 == record.getWeekday()){
                record.setWeekdayname("星期五");
            }else if( 6 == record.getWeekday()){
                record.setWeekdayname("星期六");
            }else if( 7 == record.getWeekday()){
                record.setWeekdayname("星期日");
            }
            if("4".equals(record.getStatus())){
                record.setStatus("完成");
            }else{
                record.setStatus("未完成");
            }

            if("1".equals(record.getIsplan())){
                record.setIsplan("线路内");
            }else{
                record.setIsplan("线路外");
            }
        }
        for(CrmVisitRecord record : list){
            if(null != record){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(record);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map2.entrySet()){
                            if(fentry.getKey().equals(entry.getKey())){
                                objectCastToRetMap(retMap,entry);
                            }
                        }
                    }else{
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }



	/**
	 * 拜访记录明细列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月8日
	 */
	public String crmVisitRecordDetailPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 拜访记录明细数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月8日
	 */
	public String showCrmVisitRecordDetailPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(null!=map){
			if(map.containsKey("isNoPageflag")){
				map.remove("isNoPageflag");
			}
		}
		pageMap.setCondition(map);
		PageData pageData=crmVisitRecordService.showCrmVisitRecordDetailPageList(pageMap);
		
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	

	/**
	 * 拜访记录明细页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月8日
	 */
	public String crmVisitRecordDetailListPage() throws Exception{
		List displayStandard = getBaseSysCodeService().showSysCodeListByType("displayStandard");
		request.setAttribute("displayStandard", displayStandard);
		return SUCCESS;
	}
	
	/**
	 * 设置合格
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月9日
	 */
	@UserOperateLog(key="CrmVisitRecord",type=4)
	public String setOKVisitRecordDetail() throws Exception{
		String idarrs = request.getParameter("idarrs");
		Map requestMap=new HashMap();
		requestMap.put("idarrs", idarrs);
		requestMap.put("isqa", "2");
		Map resultMap= crmVisitRecordService.setOKVisitRecordDetail(idarrs);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量设置拜访记录合格 编号:"+idarrs,flag);
		}else{
			addLog("批量设置拜访记录合格 编号失败:"+idarrs);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 设置不合格
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月9日
	 */
	@UserOperateLog(key="CrmVisitRecord",type=4)
	public String setNotOKVisitRecordDetail() throws Exception{
		String idarrs = request.getParameter("idarrs");
		Map requestMap=new HashMap();
		requestMap.put("idarrs", idarrs);
		requestMap.put("isqa", "2");
		Map resultMap= crmVisitRecordService.setNotOKVisitRecordDetail(idarrs);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量设置拜访记录不合格 编号:"+idarrs,flag);
		}else{
			addLog("批量设置拜访记录不合格 编号失败:"+idarrs);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	

	/**
	 * 拜访记录明细页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月8日
	 */
	public String crmVisitRecordOrderDetailListPage() throws Exception{
		List displayStandard = getBaseSysCodeService().showSysCodeListByType("displayStandard");
		request.setAttribute("displayStandard", displayStandard);		
		return SUCCESS;
	} 
	
	/**
	 * 拜访记录明细数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月8日
	 */
	public String showCrmVisitRecordOrderDetailPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(null!=map){
			if(map.containsKey("isNoPageflag")){
				map.remove("isNoPageflag");
			}
		}
		pageMap.setCondition(map);
		PageData pageData=crmVisitRecordService.showCrmVisitRecordOrderDetailPageList(pageMap);
		
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 拜访记录明细图片
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月13日
	 */
	public String crmVisitRecordDetailInfoPage() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		Map resultMap=crmVisitRecordService.getCrmVisitRecordAndDetail(map);
		if(null==resultMap){
			resultMap=new HashMap();
		}
		request.setAttribute("resultMap", resultMap);
		return SUCCESS;
	}
	/**
	 * 拜访记录信息详情图片
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月14日
	 */
	public String crmVisitRecordInfoPage() throws Exception{
		String id=request.getParameter("id");
		CrmVisitRecord crmVisitRecord=crmVisitRecordService.showCrmVisitRecord(id);
		request.setAttribute("crmVisitRecord",crmVisitRecord);
		return SUCCESS;
	}
	/**
	 * 拜访记录门店地图
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月14日
	 */
	public String crmVisitRecordMapPage() throws Exception{
		String id=request.getParameter("id");
		CrmVisitRecord crmVisitRecord=null;
		if(null!=id && id!=""){
			crmVisitRecord=crmVisitRecordService.showCrmVisitRecord(id);
		}
		if(null==crmVisitRecord){
			crmVisitRecord=new CrmVisitRecord();
		}
		request.setAttribute("crmVisitRecord",crmVisitRecord);
		return SUCCESS;
	}
	/**
	 * 拜访明细门店地图
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月14日
	 */
	public String crmVisitRecordDetailMapPage() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		Map resultMap=crmVisitRecordService.getCrmVisitRecordAndDetail(map);
		if(null==resultMap){
			resultMap=new HashMap();
		}
		request.setAttribute("resultMap", resultMap);
		return SUCCESS;
	}
}
