/**
 * @(#)OffpriceBillAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 22, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.model.Offprice;
import com.hd.agent.sales.model.OffpriceDetail;
import com.hd.agent.sales.model.OffpriceExcel;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 
 * @author zhengziyong
 */
public class OffpriceAction extends BaseSalesAction {

	private Offprice offprice;
	
	public Offprice getOffprice() {
		return offprice;
	}

	public void setOffprice(Offprice offprice) {
		this.offprice = offprice;
	}
	/**
	 * 显示销售特价调整单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 17, 2013
	 */
	public String showOffpriceAddPage() throws Exception{
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return "success";
	}
	/**
	 * 销售特价调整单页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 22, 2013
	 */
	public String offpricePage() throws Exception{
		String  id = request.getParameter("id");
		String type = request.getParameter("type");
		pageMap.setRows(1000);
	 	List<MeteringUnit> unitList = getBaseGoodsService().getMeteringUnitList(pageMap).getList();
	 	String unitStr = JSONUtils.listToJsonStr(unitList);
	 	request.setAttribute("unitStr", unitStr);
        if(null != id){
            Offprice offprice = salesOffpriceService.getOffprice(id);
            request.setAttribute("status",offprice.getStatus());
        }
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	
	/**
	 * 销售特价调整单添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 24, 2013
	 */
	public String offpriceAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_sales_offprice"));
		request.setAttribute("user", getSysUser());
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	
	/**
	 * 添加销售特价调整单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 24, 2013
	 */
	@UserOperateLog(key="Offprice",type=2)
	public String addOffprice() throws Exception{
		if (isAutoCreate("t_sales_offprice")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(offprice, "t_sales_offprice");
			offprice.setId(id);
		}
		String addType = request.getParameter("addType");
		String saveaudit = request.getParameter("saveaudit");
		if("temp".equals(addType)){ //暂存
			offprice.setStatus("1");
		}
		else if("real".equals(addType)){ //保存
			offprice.setStatus("2");
			if("1".equals(saveaudit)){
				offprice.setStatus("3");
			}
		}
		SysUser sysUser = getSysUser();
		offprice.setAdddeptid(sysUser.getDepartmentid());
		offprice.setAdddeptname(sysUser.getDepartmentname());
		offprice.setAdduserid(sysUser.getUserid());
		offprice.setAddusername(sysUser.getName());
		String offpriceDetailJson = request.getParameter("goodsjson");
		List<OffpriceDetail> offpriceDetailList = JSONUtils.jsonStrToList(offpriceDetailJson, new OffpriceDetail());

		offprice.setDetailList(offpriceDetailList);
		String cid = offprice.getCustomerid();
		String[] cids = cid.split(",");
		Map map = new HashMap();
		boolean flag = false;

		if(cids.length > 20){
			map.put("cid",cids.length);
		}else{
			flag = salesOffpriceService.addOffprice(offprice);
			map.put("backid", offprice.getId());
			map.put("backid", offprice.getId());
			map.put("type", "add");
		}
		map.put("flag", flag);
		addJSONObject(map);
		addLog("销售特价调整单新增 编号："+offprice.getId(), flag);
		return SUCCESS;
	}
	
	/**
	 * 特价调整单修改页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	public String offpriceEditPage() throws Exception{
		String id = request.getParameter("id");
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		Offprice offprice = salesOffpriceService.getOffprice(id);
		String jsonStr = JSONUtils.listToJsonStr(offprice.getDetailList());
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("offprice", offprice);
		request.setAttribute("statusList", statusList);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		if(null!=offprice){
			if("1".equals(offprice.getStatus()) || "2".equals(offprice.getStatus()) || "6".equals(offprice.getStatus())){
				return "editSuccess";
			}else{
				return "viewSuccess";
			}
		}else{
			return "addSuccess";
		}
	}
	
	/**
	 * 修改特价调整单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	@UserOperateLog(key="Offprice",type=3)
	public String updateOffprice() throws Exception{
//		boolean lock = isLockEdit("t_sales_offprice", offprice.getId()); // 判断锁定并解锁
//		if (!lock) { // 被锁定不能进行修改
//			addJSONObject("lock", true);
//			return SUCCESS;
//		}
		String addType = request.getParameter("addType");
		String saveaudit = request.getParameter("saveaudit");
		SysUser sysUser = getSysUser();
        if("1".equals(offprice.getStatus())){
            if("real".equals(addType)){
                offprice.setStatus("2");
            }
        }
        if("2".equals(offprice.getStatus())){
            if("1".equals(saveaudit)){
                offprice.setStatus("3");
                offprice.setAudittime(new Date());
                offprice.setAudituserid(sysUser.getUserid());
                offprice.setAuditusername(sysUser.getName());
            }
        }
        offprice.setModifyuserid(sysUser.getUserid());
        offprice.setModifyusername(sysUser.getName());
        String offpriceDetailJson = request.getParameter("goodsjson");
        List<OffpriceDetail> offpriceDetailList = JSONUtils.jsonStrToList(offpriceDetailJson, new OffpriceDetail());
        offprice.setDetailList(offpriceDetailList);

        String cid = offprice.getCustomerid();
        String[] cids = cid.split(",");

        Map map = new HashMap();
        boolean flag = false;

        if(cids.length > 20){
            map.put("cid",cids.length);
        }else{
            flag = salesOffpriceService.updateOffprice(offprice);
            map.put("backid", offprice.getId());
        }
		map.put("flag", flag);
        addJSONObject(map);
        if("1".equals(saveaudit)){
            addLog("销售特价调整单保存审核 编号："+offprice.getId(), flag);
        }else{
            addLog("销售特价调整单修改 编号："+offprice.getId(), flag);
        }

		return SUCCESS;
	}
	
	/**
	 * 销售特价调整单查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	public String offpriceViewPage() throws Exception{
		String id = request.getParameter("id");
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		Offprice offprice = salesOffpriceService.getOffprice(id);
		String jsonStr = JSONUtils.listToJsonStr(offprice.getDetailList());
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("offprice", offprice);
		request.setAttribute("statusList", statusList);
		return SUCCESS;
	}
	
	/**
	 * 销售特价调整单列表页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	public String offpriceListPage() throws Exception{
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		
		return SUCCESS;
	}
	
	/**
	 * 销售特价调整单列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	public String getOffpriceList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesOffpriceService.getOffpriceData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 删除销售特价调整单信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	@UserOperateLog(key="Offprice",type=4)
	public String deleteOffprice() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_sales_offprice", id); //判断是否被引用，被引用则无法删除。
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = salesOffpriceService.deleteOffprice(id);
		addJSONObject("flag", flag);
		addLog("销售特价调整单删除 编号："+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 审核或反审特价调整单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 25, 2013
	 */
	@UserOperateLog(key="Offprice",type=3)
	public String auditOffprice() throws Exception{
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		boolean flag = getSalesOffpriceService().auditOffprice(type, id);
		addJSONObject("flag", flag);
		if("1".equals(type)){
			addLog("销售特价调整审核 编号："+id, flag);
		}else{
			addLog("销售特价调整反审 编号："+id, flag);
		}
		return SUCCESS;
	}
	
	/**
	 * 提交工作流
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 29, 2013
	 */
	@UserOperateLog(key="Offprice",type=3)
	public String submitOffpriceProcess() throws Exception{
		String id = request.getParameter("id");
		Offprice offprice = salesOffpriceService.getOffprice(id);
		if(!offprice.getStatus().equals("2")){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		SysUser user = getSysUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		String title = "销售订单 "+offprice.getId()+" (" + offprice.getBusinessdate() + ")";
		boolean flag = salesOffpriceService.submitOffpriceProcess(title, user.getUserid(), "salesOrder", id, variables);
		addJSONObject("flag", flag);
		addLog("销售特价调整单提交工作流 编号："+id, flag);
		return SUCCESS;
	}
	/**
	 * 显示特价调整单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 8, 2013
	 */
	public String offpriceDetailAddPage() throws Exception{
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 显示特价调整单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 8, 2013
	 */
	public String offpriceDetailEditPage() throws Exception{
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

    /**
     * 特价促销导出
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-05
     */
    @UserOperateLog(key="offprice",type=0,value="特价促销导出")
    public void exportOffpriceListData()throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        String state = request.getParameter("state");
        if(StringUtils.isNotEmpty(state)){
            map.put("state", state);
        }
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
        if(map.containsKey("ordersql") && null != map.get("ordersql")){
            pageMap.setOrderSql((String)map.get("ordersql"));
        }
        pageMap.setCondition(map);

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "业务日期");
        firstMap.put("customertypename", "客户群");
        firstMap.put("customername", "客户群名称");
        firstMap.put("begindate", "生效日期");
        firstMap.put("enddate", "截止日期");
        firstMap.put("goodsid", "商品编码");
        firstMap.put("goodsname", "商品名称");
        firstMap.put("spell", "商品助记符");
        firstMap.put("barcode", "商品条形码");
        firstMap.put("mainunitname", "单位");
        firstMap.put("numextent", "数量区间");
        firstMap.put("offprice", "特价");
        firstMap.put("oldprice", "原价");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        List<OffpriceExcel> list = salesOffpriceService.getOffpriceExcelList(pageMap);
        if(list.size() != 0){
            for(OffpriceExcel offpriceExcel : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(offpriceExcel);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map2.entrySet()){
                            if(fentry.getKey().equals(entry.getKey())){
                                objectCastToRetMap(retMap,entry);
                            }
                        }
                    }
                    else{
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }


    /**
     * 特价促销导入
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-06
     */
    @UserOperateLog(key="Offprice",type=0,value="特价促销导入")
    public String importOffpriceListData()throws Exception{
        Map<String,Object> retMap = new HashMap<String,Object>();
        int ordernum = 0;
        String msg = "",backorderids = "",goodsidmsg = "",spellmsg = "",barcodemsg = "",disablegoodsidsmsg = "";
        try {
            String meth = "addOffpriceExcel";
            Object object2 = SpringContextUtils.getBean("salesOffpriceService");
            Class entity = Class.forName("com.hd.agent.sales.model.OffpriceExcel");
            Method[] methods = object2.getClass().getMethods();
            Method method = null;
            for(Method m : methods){
                if(m.getName().equals(meth)){
                    method = m;
                }
            }

            List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
            List<String> paramList2 = new ArrayList<String>();
            for(String str : paramList){
                if("业务日期".equals(str)){
                    paramList2.add("businessdate");
                }else if("客户群".equals(str)){
                    paramList2.add("customertypename");
                }else if("客户群名称".equals(str)){
                    paramList2.add("customername");
                }else if("生效日期".equals(str)){
                    paramList2.add("begindate");
                }else if("截止日期".equals(str)){
                    paramList2.add("enddate");
                }else if("商品编码".equals(str)){
                    paramList2.add("goodsid");
                }else if("商品名称".equals(str)){
                    paramList2.add("goodsname");
                }else if("商品助记符".equals(str)){
                    paramList2.add("spell");
                }else if("商品条形码".equals(str)){
                    paramList2.add("barcode");
                }else if("单位".equals(str)){
                    paramList2.add("mainunitname");
                }else if("数量区间".equals(str)){
                    paramList2.add("numextent");
                }else if("特价".equals(str)){
                    paramList2.add("offprice");
                }else if("原价".equals(str)){
                    paramList2.add("oldprice");
                }else if("备注".equals(str)){
                    paramList2.add("remark");
                }else{
                    paramList2.add("null");
                }
            }

            if(paramList.size() == paramList2.size()){
                List result = new ArrayList();
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
                        result.add(object);
                    }
                    if(result.size()  != 0){
                        retMap = excelService.insertSalesOrder(object2, result, method);
                        if(retMap.containsKey("goodsidmsg") && null != retMap.get("goodsidmsg") && StringUtils.isNotEmpty((String)retMap.get("goodsidmsg"))){
                            goodsidmsg += (String)retMap.get("goodsidmsg");
                        }
                        if(retMap.containsKey("spellmsg") && null != retMap.get("spellmsg") && StringUtils.isNotEmpty((String)retMap.get("spellmsg"))){
                            spellmsg += (String)retMap.get("spellmsg");
                        }
                        if(retMap.containsKey("barcodemsg") && null != retMap.get("barcodemsg") && StringUtils.isNotEmpty((String)retMap.get("barcodemsg"))){
                            barcodemsg += (String)retMap.get("barcodemsg");
                        }
                        if(retMap.containsKey("disablegoodsidsmsg") && null != retMap.get("disablegoodsidsmsg") && StringUtils.isNotEmpty((String)retMap.get("disablegoodsidsmsg"))){
                            disablegoodsidsmsg += (String)retMap.get("disablegoodsidsmsg");
                        }

                        if(retMap.containsKey("requirednullnum") && null != retMap.get("requirednullnum") && 0 != (Integer)retMap.get("requirednullnum")){
                            msg = String.valueOf((Integer)retMap.get("requirednullnum")) +"条数据导入必填项为空，不允许导入";
                        }
                        if(retMap.containsKey("customeridmsg") && null != retMap.get("customeridmsg") && StringUtils.isNotEmpty((String)retMap.get("customeridmsg"))){
                            if(StringUtils.isNotEmpty(msg)){
                                msg += "<br>" + "客户群名称："+(String)retMap.get("customeridmsg") + "不存在,导入失败";
                            }else{
                                msg = "客户群名称："+(String)retMap.get("customeridmsg") + "不存在,导入失败";
                            }
                        }
                        if(StringUtils.isNotEmpty(goodsidmsg)){
                            if(StringUtils.isNotEmpty(msg)){
                                msg += "<br>" + "商品编码:" + goodsidmsg + "不存在,导入失败";
                            }else{
                                msg = "商品编码:" + goodsidmsg + "不存在,导入失败";
                            }
                        }else if(StringUtils.isNotEmpty(spellmsg)){
                            if(StringUtils.isNotEmpty(msg)){
                                msg += "<br>" + "商品助记符:" + spellmsg + "不存在,导入失败";
                            }else{
                                msg = "商品助记符:" + spellmsg + "不存在,导入失败";
                            }
                        }else if(StringUtils.isNotEmpty(barcodemsg)){
                            if(StringUtils.isNotEmpty(msg)){
                                msg += "<br>" + "商品条形码:" + barcodemsg + "不存在,导入失败";
                            }else{
                                msg = "商品条形码:" + barcodemsg + "不存在,导入失败";
                            }
                        }else if(StringUtils.isNotEmpty(disablegoodsidsmsg)){
                            if(StringUtils.isNotEmpty(msg)){
                                msg += "<br>" + "商品:" + disablegoodsidsmsg + "不为启用状态,不予导入";
                            }else{
                                msg = "商品:" + disablegoodsidsmsg + "不为启用状态,不予导入";
                            }
                        }
                        retMap.put("msg", msg);
                        if(retMap.containsKey("backorderids") && null != retMap.get("backorderids")){
                            backorderids = (String)retMap.get("backorderids");
                            if(backorderids.indexOf(",") != -1){
                                ordernum = backorderids.split(",").length;
                            }else{
                                ordernum = 1;
                            }
                            retMap.put("ordernum", ordernum);
                            addLog("特价促销导入 编号:"+retMap.get("backorderids").toString(),retMap.get("flag").equals(true));
                        }
                    }
                }else{
                    retMap.put("excelempty", true);
                }
            }else{
                retMap.put("versionerror", true);
            }
            addJSONObject(retMap);
        }catch (Exception e){
            retMap.put("error", true);
            addJSONObject(retMap);
        }
        return SUCCESS;
    }

    public String offPriceAddByBrandAndSortPage() throws Exception {
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS ;
    }


    public String getOffPriceByBrandAndSort() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        String brands=(String)map.get("brand");
        String defaultsorts=(String)map.get("defaultsort");
        String[] brandArr  =null;
        String[] defaultsortArr=null;
        if(null!=brands){
            brandArr = brands.split(",");
        }
        if(null!=defaultsorts){
            defaultsortArr = defaultsorts.split(",");
        }
        Map conditionMap = new HashMap();
        conditionMap.put("state", "1");
        conditionMap.put("brandArr", brandArr);
        conditionMap.put("defaultsortArr", defaultsortArr);
		if(map.containsKey("id")){
			conditionMap.put("id",map.get("id"));
		}
        pageMap.setCondition(conditionMap);

        PageData pageData = salesOffpriceService.getGoodsByBrandAndSort(pageMap);
        addJSONObject(pageData);

        return SUCCESS ;
    }

    /**
     * 作废该促销单
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="Offprice",type=3)
    public String offpriceCancel() throws Exception {
        String ids = request.getParameter("ids");
        String operate = request.getParameter("operate");
        Map map = salesOffpriceService.offpriceCancel(ids,operate);
        addJSONObject(map);
        addLog("作废特价单 "+ids, map);
        return SUCCESS;
    }

}

