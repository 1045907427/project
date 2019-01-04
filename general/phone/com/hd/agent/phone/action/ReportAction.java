/**
 * @(#)ReportAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 13, 2013 zhengziyong 创建版本
 */
package com.hd.agent.phone.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.phone.service.IPhoneService;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public class ReportAction extends BaseFilesAction {
	private IPhoneService phoneService;
	
	public IPhoneService getPhoneService() {
		return phoneService;
	}

	public void setPhoneService(IPhoneService phoneService) {
		this.phoneService = phoneService;
	}


	/**
	 * 抄单汇总报表-客户抄货汇总
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 7, 2013
	 */
	public String getCustomerOrderQueryReport() throws Exception{
		String ids = request.getParameter("ids");
		String begin = request.getParameter("t1");
		String end = request.getParameter("t2");
        Map map = new HashMap();
        if(StringUtils.isNotEmpty(ids)){
            map.put("customerid",ids);
        }
        map.put("begindate",begin);
        map.put("enddate",end);
        map.put("groupcol","customerid,businessdate");
		addJSONArray(phoneService.getBaseOrderQueryReport(map));
        return SUCCESS;
	}
	
	/**
	 * 抄单汇总报表-客户抄货商品明细汇总
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 9, 2013
	 */
	public String getCustomerGoodsOrderQueryReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("groupcol","goodsid,businessdate");
		addJSONArray(phoneService.getBaseOrderQueryReport(map));
		return SUCCESS;
	}
	
	/**
	 * 抄单汇总报表-产品抄单汇总
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 9, 2013
	 */
	public String getGoodsOrderQueryReport() throws Exception{
        String ids = request.getParameter("ids");
        String begin = request.getParameter("t1");
        String end = request.getParameter("t2");
        Map map = new HashMap();
        if(StringUtils.isNotEmpty(ids)){
            map.put("goodsid",ids);
        }
        map.put("begindate",begin);
        map.put("enddate",end);
        map.put("groupcol","goodsid,businessdate");
		addJSONArray(phoneService.getBaseOrderQueryReport(map));
		return SUCCESS;
	}
	
	/**
	 * 抄单汇总报表-产品抄单汇总客户明细汇总
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 9, 2013
	 */
	public String getGoodsCustomerOrderQueryReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("groupcol","goodsid,customerid");
		addJSONArray(phoneService.getBaseOrderQueryReport(map));
		return SUCCESS;
	}
	
	/**
	 * 抄单汇总报表-业务员抄单汇总查询
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 9, 2013
	 */
	public String getSalerOrderQueryReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("groupcol","salesuser");
		addJSONArray(phoneService.getBaseOrderQueryReport(map));
		return SUCCESS;
	}
	
	/**
	 * 抄单汇总报表-业务员抄单汇总客户明细
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 10, 2013
	 */
	public String getSalerCustomerOrderQueryReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("groupcol","salesuser,businessdate");
		addJSONArray(phoneService.getBaseOrderQueryReport(map));
		return SUCCESS;
	}
	
	/**
	 * 抄单汇总报表-业务员抄单汇总客户商品明细
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 10, 2013
	 */
	public String getSalerCustomerGoodsOrderQueryReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("groupcol","goodsid");
		addJSONArray(phoneService.getBaseOrderQueryReport(map));
		return SUCCESS;
	}
	
	/**
	 * 汇总报表-客户销售汇总
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 13, 2013
	 */
	public String getCustomerSaleQueryReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", false);
		map.put("groupcols", "customerid");
		pageMap.setCondition(map);
		Map returnmap = phoneService.getBaseSalesReport(pageMap);
		addJSONObject(returnmap);
		return SUCCESS;
	}
	
	/**
	 * 汇总报表-客户销售汇总产品明细
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 13, 2013
	 */
	public String getCustomerGoodsSaleQueryReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", false);
		map.put("groupcols", "goodsid");
		pageMap.setCondition(map);
		Map returnmap = phoneService.getBaseSalesReport(pageMap);
		addJSONObject(returnmap);
		return SUCCESS;
	}
	
	/**
	 * 汇总报表-业务员销售汇总查询
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 13, 2013
	 */
	public String getSalerSaleQueryReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", false);
		map.put("groupcols", "salesuserid");
		pageMap.setCondition(map);
		Map returnmap = phoneService.getBaseSalesReport(pageMap);
		addJSONObject(returnmap);
		return SUCCESS;
	}
	
	/**
	 * 汇总报表-业务员销售汇总产品明细
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 13, 2013
	 */
	public String getSalerGoodsSaleQueryReport() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", false);
		map.put("groupcols", "customerid");
		pageMap.setCondition(map);
		Map returnmap = phoneService.getBaseSalesReport(pageMap);
		addJSONObject(returnmap);
		return SUCCESS;
	}
	
	/**
	 * 汇总报表-获取销售汇总数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月30日
	 */
	public String getBaseSalesReport() throws Exception{
		String type = request.getParameter("type");
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", false);
		if("1".equals(type)){
			map.put("groupcols", "customerid");
		}else if("2".equals(type)){
			map.put("groupcols", "salesuser");
		}else if("3".equals(type)){
			map.put("groupcols", "goodsid");
		}else if("4".equals(type)){
			map.put("groupcols", "brandid");
		}else if("5".equals(type)){
			map.put("groupcols", "branduser");
		}else if("6".equals(type)){
			map.put("groupcols", "salesarea");
		}else if("7".equals(type)){
			map.put("groupcols", "branddept");
		}else if("8".equals(type)){
			map.put("groupcols", "branddept");
		}else if("9".equals(type)){
            map.put("groupcols", "branduserdept");
        }else if("10".equals(type)){
            map.put("groupcols", "customersort");
        }
		map.put("grouptype", type);
		pageMap.setCondition(map);
		Map returnmap = phoneService.getBaseSalesReport(pageMap);
		addJSONObject(returnmap);
		return SUCCESS;
	}
	/**
	 * 回笼报表-获取销售回笼数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年9月12日
	 */
	public String getBaseWithdrawReport() throws Exception{
		String type = request.getParameter("type");
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", false);
		if("1".equals(type)){
			map.put("groupcols", "customerid");
		}else if("2".equals(type)){
			map.put("groupcols", "salesuser");
		}else if("3".equals(type)){
			map.put("groupcols", "goodsid");
		}else if("4".equals(type)){
			map.put("groupcols", "brandid");
		}else if("5".equals(type)){
			map.put("groupcols", "branduser");
		}else if("6".equals(type)){
			map.put("groupcols", "salesarea");
		}else if("7".equals(type)){
			map.put("groupcols", "branddept");
		}else if("8".equals(type)){
			map.put("groupcols", "branddept");
		}else if("10".equals(type)){
            map.put("groupcols", "customersort");
        }
		map.put("grouptype", type);
		pageMap.setCondition(map);
		Map returnmap = phoneService.getBaseWithdrawReport(pageMap);
		addJSONObject(returnmap);
		return SUCCESS;
	}
}
