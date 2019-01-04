/**
 * @(#)BugetAnalyzerServiceImpl.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月28日 huangzhiqian 创建版本
 */
package com.hd.agent.journalsheet.service.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.dao.BugetAnalyzerMapper;
import com.hd.agent.journalsheet.model.BugetAnalyzer;
import com.hd.agent.journalsheet.model.BugetAnalyzerGroup;
import com.hd.agent.journalsheet.service.IBugetAnalyzerService;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class BugetAnalyzerServiceImpl extends BaseFilesServiceImpl implements IBugetAnalyzerService{
	private BugetAnalyzerMapper bugetAnalyzerMapper;

	public BugetAnalyzerMapper getBugetAnalyzerMapper() {
		return bugetAnalyzerMapper;
	}

	public void setBugetAnalyzerMapper(BugetAnalyzerMapper bugetAnalyzerMapper) {
		this.bugetAnalyzerMapper = bugetAnalyzerMapper;
	}

	@Override
	public Map addbuget(BugetAnalyzer bugetAnalyzer) throws Exception {
		Map map = new HashMap();
		if (isAutoCreate("t_js_budget")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(bugetAnalyzer, "t_js_budget");
			bugetAnalyzer.setBudgetid(id);
		}else{
			bugetAnalyzer.setBudgetid(CommonUtils.getDataNumberSendsWithRand());
		}
		if(bugetAnalyzerMapper.addbuget(bugetAnalyzer)>0){
			map.put("flag", true);
			map.put("msg", "录入成功");
		}else{
			map.put("flag", false);
			map.put("msg", "录入失败");
		}
		return map;
	}

	@Override
	public PageData getbugetAnalyzerList(PageMap pageMap) throws Exception {
		Map condition = pageMap.getCondition();
		Object yearMonth = condition.get("yearMonth");
		if(yearMonth!=null){
			String ymStr = (String)yearMonth;
			if(StringUtils.isNotEmpty(ymStr)){
				String[] ym=ymStr.split("-");
				condition.put("year",ym[0]);
				condition.put("month",ym[1]);
			}
			
		}
		List<BugetAnalyzer> rsList = bugetAnalyzerMapper.getbugetAnalyzerList(pageMap);
		for(BugetAnalyzer data:rsList){
			data.setYearMonth(data.getYear()+"-"+data.getMonth());
			if(StringUtils.isNotEmpty(data.getBrand())){
				Brand brand = getGoodsBrandByID(data.getBrand());
				if(brand!=null){
					data.setBrandname(brand.getName());
				}
			}
			
			if(StringUtils.isNotEmpty(data.getDeptid())){
				DepartMent dpt = getDepartMentById(data.getDeptid());
				if(dpt!=null){
					data.setDeptname(dpt.getName());
				}
			}
			
			if(StringUtils.isNotEmpty(data.getSupplierid())){
				BuySupplier supplier = getSupplierInfoById(data.getSupplierid());
				if(supplier!=null){
					data.setSuppliername(supplier.getName());
				}
			}
			
			if(StringUtils.isNotEmpty(data.getAdduserid())){
				SysUser user = getSysUserById(data.getAdduserid());
				if(user!=null){
					data.setAddusername(user.getName());
				}
			}
			
			
		}
		
		int count = bugetAnalyzerMapper.getbugetAnalyzerListCount(pageMap);
		
		return new PageData(count, rsList, pageMap);
	}

	@Override
	public Map deletebugetAnalyzer(String[] idArr) throws Exception {
		Map rs =new HashMap();
		for(int i = 0;i<idArr.length;i++){
			BugetAnalyzer analyzer = getBugetAnalyzerById(idArr[i]);
			if(analyzer == null){
				throw new Exception(String.format("删除失败,预算编号%s未找到", idArr[i]));
			}
			if(analyzer.getState()==1){
				throw new Exception(String.format("删除失败,预算编号%s状态为启用", analyzer.getBudgetid()));
			}
			if(bugetAnalyzerMapper.deleteAnalyzerByBugetId(idArr[i])<=0){
				throw new Exception(String.format("删除失败,预算编号%s删除失败", analyzer.getBudgetid()));
			}
		}
		rs.put("flag",true);
		rs.put("msg","删除成功");
		return rs;
	}

	@Override
	public BugetAnalyzer getBugetAnalyzerById(String id) throws Exception {
		BugetAnalyzer data = bugetAnalyzerMapper.getAnalyzerByBugetId(id);
		
		if(StringUtils.isNotEmpty(data.getDeptid())){
			DepartMent dpt = getDepartMentById(data.getDeptid());
			if(dpt!=null){
				data.setDeptname(dpt.getName());
			}
		}
		
		if(StringUtils.isNotEmpty(data.getSupplierid())){
			BuySupplier supplier = getSupplierInfoById(data.getSupplierid());
			if(supplier!=null){
				data.setSuppliername(supplier.getName());
			}
		}
		
		if(StringUtils.isNotEmpty(data.getBrand())){
			Brand brand = getGoodsBrandByID(data.getBrand());
			if(brand!=null){
				data.setBrandname(brand.getName());
			}
		}
		
		if(StringUtils.isNotEmpty(data.getAdduserid())){
			SysUser user = getSysUserById(data.getAdduserid());
			if(user!=null){
				data.setAddusername(user.getName());
			}
		}
		return data;
	}

	@Override
	public Map updateState(String[] idArr, String type) throws Exception {
		String str = "启用"; 
		int state = 1;
		if("diable".equals(type)){
			str ="禁用";
			state = 0;
		}
		Map rs =new HashMap();
		for(int i = 0;i<idArr.length;i++){
			BugetAnalyzer analyzer = getBugetAnalyzerById(idArr[i]);
			if(analyzer == null){
				throw new Exception(String.format("%s失败,预算编号%s未找到",str, idArr[i]));
			}
			if(bugetAnalyzerMapper.updateState(idArr[i],state)<0){
				throw new Exception(String.format("%s失败,预算编号%s%s失败",str, analyzer.getBudgetid(),str));
			}
		}
		rs.put("flag",true);
		rs.put("msg","启用成功");
		return rs;
	}

	@Override
	public Map updatebugetAnalyzer(BugetAnalyzer bugetAnalyzer) throws Exception {
		Map rs =new HashMap();
		if(StringUtils.isEmpty(bugetAnalyzer.getBudgetid())||bugetAnalyzerMapper.getAnalyzerByBugetId(bugetAnalyzer.getBudgetid())==null){
			rs.put("flag",false);
			rs.put("msg","未找到要修改的数据");
			return rs;
		}
		
		if(bugetAnalyzerMapper.updateBugetAnalyzer(bugetAnalyzer)>=0){
			rs.put("flag",true);
			rs.put("msg","修改成功");
		}else{
			rs.put("flag",false);
			rs.put("msg","修改失败");
		}
		return rs;
	}
	
	
	
	
	
	/*******************报表部分**********************/
	@Override
	public PageData getbugetAnalyzerGroupData(PageMap pageMap) throws Exception {
		
		//查询出所有数据  group by   brand,deptid,supplierid,bugettype,month
		List<BugetAnalyzer> queryList = bugetAnalyzerMapper.getbugetAnalyzerGroupList(pageMap);
		
		Map<String,BugetAnalyzerGroup> rsMap = new LinkedHashMap<String, BugetAnalyzerGroup>();
		
		BugetAnalyzerGroup group = null ;
		for(BugetAnalyzer data : queryList){
				StringBuffer sb = new StringBuffer();
				sb.append(data.getBrand()==null?"":data.getBrand()); sb.append(",");
				sb.append(data.getDeptid()==null?"":data.getDeptid()); sb.append(",");
				sb.append(data.getSupplierid()==null?"":data.getSupplierid()); sb.append(",");
				sb.append(data.getBugettype()==null?"":data.getBugettype());
				if(rsMap.get(sb.toString())==null){
					group = new BugetAnalyzerGroup();
					rsMap.put(sb.toString(), group);
				}else{
					group = rsMap.get(sb.toString());
				}
				Method getMethod = group.getClass().getMethod("getMonth"+data.getMonth());
				BigDecimal beforeData = (BigDecimal)getMethod.invoke(group); 
				Method setMethod = group.getClass().getMethod("setMonth"+data.getMonth(), new Class[]{java.math.BigDecimal.class});
				setMethod.invoke(group, beforeData.add(data.getBudgetnum()));
		}
		
		List<BugetAnalyzerGroup> rsList = transFerMaptoBean(rsMap);
		//合计部分
		List<BugetAnalyzerGroup> footer =new ArrayList<BugetAnalyzerGroup>();
		BugetAnalyzerGroup foot = new BugetAnalyzerGroup();
		footer.add(foot);
		BindFootData(foot,rsList);
		
		
		int count = rsList.size();
		//分页
		int startIndex = pageMap.getStartNum();
		int endIndex = pageMap.getStartNum()+pageMap.getRows();
		//endIndex大于数据长度,sublist到最后一条
		if(endIndex>rsList.size()){
			endIndex=rsList.size();
		}
		//startIndex>总长度return null
		if(startIndex>rsList.size()){
			return new PageData(0, null, pageMap);
		}
		
		rsList = rsList.subList(startIndex,endIndex);
		
		PageData pgdt = new PageData(count, rsList, pageMap);
		pgdt.setFooter(footer);
		return pgdt;
	}
	
	/**
	 * map转换为BugetAnalyzerGroup
	 * @param rsMap
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	private List<BugetAnalyzerGroup> transFerMaptoBean(Map<String,BugetAnalyzerGroup> rsMap)throws Exception{
		List<BugetAnalyzerGroup> rsList = new ArrayList<BugetAnalyzerGroup>();
		BugetAnalyzerGroup group ;
		for(Entry<String, BugetAnalyzerGroup> entry :rsMap.entrySet()){
				String[] args = entry.getKey().split(",");
				group = rsMap.get(entry.getKey());
				group.setBrand(args[0]);
				group.setDeptid(args[1]);
				group.setSupplierid(args[2]);
				group.setBugettype(Integer.parseInt(args[3]));
				
				if(StringUtils.isNotEmpty(group.getDeptid())){
					DepartMent dpt = getDepartMentById(group.getDeptid());
					if(dpt!=null){
						group.setDeptname(dpt.getName());
					}
				}
				
				if(StringUtils.isNotEmpty(group.getSupplierid())){
					BuySupplier supplier = getSupplierInfoById(group.getSupplierid());
					if(supplier!=null){
						group.setSuppliername(supplier.getName());
					}
				}
				
				if(StringUtils.isNotEmpty(group.getBrand())){
					Brand brand = getGoodsBrandByID(group.getBrand());
					if(brand!=null){
						group.setBrandname(brand.getName());
					}
				}
				
				
				rsList.add(group);
		}
		return rsList;
	}
	
	/**
	 * 合计的数据
	 * @param foot
	 * @param rsList
	 * @author huangzhiqian
	 * @date 2015年12月30日
	 */
	private void BindFootData(BugetAnalyzerGroup foot,List<BugetAnalyzerGroup> rsList) {
		foot.setBrandname("合计");
		BigDecimal totalMonth1 = new BigDecimal(0);
		BigDecimal totalMonth2 = new BigDecimal(0);
		BigDecimal totalMonth3 = new BigDecimal(0);
		BigDecimal totalMonth4 = new BigDecimal(0);
		BigDecimal totalMonth5 = new BigDecimal(0);
		BigDecimal totalMonth6 = new BigDecimal(0);
		BigDecimal totalMonth7 = new BigDecimal(0);
		BigDecimal totalMonth8 = new BigDecimal(0);
		BigDecimal totalMonth9 = new BigDecimal(0);
		BigDecimal totalMonth10 = new BigDecimal(0);
		BigDecimal totalMonth11 = new BigDecimal(0);
		BigDecimal totalMonth12 = new BigDecimal(0);
		for(BugetAnalyzerGroup data:rsList){
			totalMonth1 = totalMonth1.add(data.getMonth01());
			totalMonth2 = totalMonth2.add(data.getMonth02());
			totalMonth3 = totalMonth3.add(data.getMonth03());
			totalMonth4 = totalMonth4.add(data.getMonth04());
			totalMonth5 = totalMonth5.add(data.getMonth05());
			totalMonth6 = totalMonth6.add(data.getMonth06());
			totalMonth7 = totalMonth7.add(data.getMonth07());
			totalMonth8 = totalMonth8.add(data.getMonth08());
			totalMonth9 = totalMonth9.add(data.getMonth09());
			totalMonth10 = totalMonth10.add(data.getMonth10());
			totalMonth11 = totalMonth11.add(data.getMonth11());
			totalMonth12 = totalMonth12.add(data.getMonth12());
		}
		foot.setMonth01(totalMonth1);
		foot.setMonth02(totalMonth2);
		foot.setMonth03(totalMonth3);
		foot.setMonth04(totalMonth4);
		foot.setMonth05(totalMonth5);
		foot.setMonth06(totalMonth6);
		foot.setMonth07(totalMonth7);
		foot.setMonth08(totalMonth8);
		foot.setMonth09(totalMonth9);
		foot.setMonth10(totalMonth10);
		foot.setMonth11(totalMonth11);
		foot.setMonth12(totalMonth12);
	}

}

