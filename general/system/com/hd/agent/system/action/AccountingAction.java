/**
 * @(#)AccountingMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 23, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.system.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.model.Tree;
import com.hd.agent.system.model.Accounting;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.service.IAccountingService;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class AccountingAction extends BaseFilesAction{

	private IAccountingService accountingService;
	private Accounting accounting;

	public IAccountingService getAccountingService() {
		return accountingService;
	}

	public void setAccountingService(IAccountingService accountingService) {
		this.accountingService = accountingService;
	}
	
	public Accounting getAccounting() {
		return accounting;
	}

	public void setAccounting(Accounting accounting) {
		this.accounting = accounting;
	}

	/**
	 * 显示会计期间页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 23, 2013
	 */
	public String accountingPage()throws Exception{
		List<Accounting> list = accountingService.getAccountingYearsListOrder();
		if(list.size() != 0){
			Accounting account = list.get(list.size()-1);
			if(account != null){
				request.setAttribute("newYear", account.getYear());
				request.setAttribute("addYear", Integer.parseInt(account.getYear())+1);
			}
		}
        Accounting openAccounting = accountingService.getOpenAccounting();
        if(null != openAccounting){
            request.setAttribute("openyear",openAccounting.getYear());
        }
        String isAutoCloseAccounting = getSysParamValue("isAutoCloseAccounting");
        if(StringUtils.isNotEmpty(isAutoCloseAccounting)){
            request.setAttribute("isAutoCloseAccounting",isAutoCloseAccounting);
        }else{
            request.setAttribute("isAutoCloseAccounting","0");
        }
		return SUCCESS;
	}
	
	/**
	 * 获取会计年度树型数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 24, 2013
	 */
	public String getAccountingTree()throws Exception{
		List list = new ArrayList();
		Tree first = new Tree();
		first.setId("");
		first.setText("会计年度");
		first.setOpen("true");
		list.add(first);
		List<Accounting> accountList = accountingService.getAccountingYearsListOrder();
		if(accountList.size() != 0){
			for(Accounting accounting : accountList){
				Tree cTree = new Tree();
				cTree.setId(accounting.getYear());
				cTree.setParentid("");
				cTree.setText(accounting.getYear());
				cTree.setState(accounting.getState());
				cTree.setOpen("true");
				list.add(cTree);
			}
		}
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 根据会计年度获取对应会计期间列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 24, 2013
	 */
	public String getAccountingListByYear()throws Exception{
		String year = request.getParameter("year");
		List<Accounting> list = accountingService.getAccountingListByYear(year);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 新增会计年度时间列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 24, 2013
	 */
    @UserOperateLog(key="Accounting",type=2)
	public String getDateList()throws Exception{
		String year = request.getParameter("year");
		String month = "";
		Calendar cal = Calendar.getInstance();
		List<Accounting> list = new ArrayList<Accounting>();
        String nowdate = CommonUtils.getTodayDataStr();
		for(int i=0;i<12;i++){
			Accounting accounting = new Accounting();
			month = Integer.toString(i+1);
			if(month.length() == 1){
				month = "0"+month;
			}
			String begindate = year + "-" + month + "-01";
			cal.set(Calendar.MONTH,i);//注意,Calendar对象默认一月为0
			int dayNum=cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
			String enddate = year + "-" + month + "-" + dayNum;
            //1：datestr1>datestr2,-1：datestr1<datestr2,0：datestr1=datestr2
            int b = CommonUtils.compareDate(begindate,nowdate);
            int e = CommonUtils.compareDate(enddate,nowdate);
            if(b == -1 && e == -1){
                accounting.setState("5");
            }else if((b == -1 || b == 0) && (e == 1 || e == 0)){
                accounting.setState("1");
            }else if(b == 1 && e == 1){
                accounting.setState("0");
            }
			accounting.setBegindate(begindate);
			accounting.setEnddate(enddate);
			accounting.setYear(year);
			list.add(accounting);
		}
        Map map = new HashMap();
		boolean flag = accountingService.addAccounting(list);
		map.put("list", list);
		map.put("flag", flag);
		Tree node = new Tree();
		node.setId(year);
		node.setText(year);
		node.setParentid("");
		node.setState("0");
		map.put("node", node);
		addJSONObject(map);
        addLog("新增会计年度 :"+year, flag);
		return SUCCESS;
	}
	
	/**
	 * 会计年度时间列表修改
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 25, 2013
	 */
	public String editDateList()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        List list = accountingService.getEditDateList(map);
        addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 修改会计期间
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 25, 2013
	 */
    @UserOperateLog(key="Accounting",type=3)
	public String editAccounYear()throws Exception{
		String dateArrStr = request.getParameter("dateArrStr");
        String year = request.getParameter("year");
        Map map = new HashMap();
		if(StringUtils.isNotEmpty(dateArrStr)){
			boolean flag = accountingService.editAccounting(dateArrStr,year);
            map.put("flag",flag);
			addJSONObject(map);
            addLog("修改会计年度 :"+year, flag);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除会计期间
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 25, 2013
	 */
    @UserOperateLog(key="Accounting",type=4)
	public String deleteAccounting()throws Exception{
		String year = request.getParameter("year");
		Map map = new HashMap();
		if(StringUtils.isNotEmpty(year)){
			boolean flag = accountingService.deleteAccouting(year);
			if(flag){
				map.put("newYear", Integer.parseInt(year)-1);
				map.put("addYear", year);
			}
			map.put("flag", flag);
            addLog("删除会计年度 :"+year, flag);
		}
		addJSONObject(map);
		return SUCCESS;
	}

    /**
     * 关账指定会计区间
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-01
     */
    @UserOperateLog(key="Accounting",type=0)
    public String closeAccounting()throws Exception{
        String id = request.getParameter("id");
        String nextid = request.getParameter("nextid");
        String nowdate = CommonUtils.getTodayDataStr();
        Map map = new HashMap();
        boolean flag = false;
        String msg = "";
        Accounting accounting = accountingService.getAccountingInfo(id);
        if(null != accounting){
            String begindate = accounting.getBegindate();
            String enddate = accounting.getEnddate();
            //1：datestr1>datestr2,-1：datestr1<datestr2,0：datestr1=datestr2
            int b = CommonUtils.compareDate(begindate,nowdate);
            int e = CommonUtils.compareDate(enddate,nowdate);
            if(b == -1 && e == -1){
                accounting.setState("5");
                Map map1 = accountingService.closeAccounting(accounting,nextid);
                map.putAll(map1);
                flag = map1.get("flag").equals(true);
                if(map1.get("flag").equals(true)){
                    Tree node = new Tree();
                    node.setId(accounting.getYear());
                    node.setText(accounting.getYear());
                    node.setParentid("");
                    node.setState("1");
                    map.put("node", node);
                }
            }else{
                msg = "当前时间属于该区间，不可关账!";
            }
        }
        map.put("msg",msg);
        map.put("flag",flag);
        addLog("会计区间 :"+accounting.getBegindate()+"-"+accounting.getEnddate()+"关账", flag);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 计算两个日期之间的天数
     * @return
     * @throws Exception
     */
    public String getDaysBetweenDate()throws Exception{
        String begindate = request.getParameter("begindate");
        String enddate = request.getParameter("enddate");
        int days = CommonUtils.daysBetween(begindate,enddate);
        addJSONObject("days",days);
        return SUCCESS;
    }

    /**
     * 判断反审时间是否在会计区间内，在区间内可反审true，否则不可反审false
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-02
     */
    public String isBetweenAccounting()throws Exception{
        boolean flag = false;
        String businessdate = request.getParameter("businessdate");
        String isOpenAccounting = getSysParamValue("isOpenAccounting");
        if("1".equals(isOpenAccounting)){
            Accounting openaccounting = accountingService.getOpenAccounting();
            if(null != openaccounting){
                //1：datestr1>datestr2,-1：datestr1<datestr2,0：datestr1=datestr2
                int b = CommonUtils.compareDate(openaccounting.getBegindate(),businessdate);
                int e = CommonUtils.compareDate(openaccounting.getEnddate(),businessdate);
                if((b == -1 || b == 0) && (e == 1 || e == 0)){
                    flag = true;
                }
            }
        }else{
            flag = true;
        }
        addJSONObject("flag",flag);
        return SUCCESS;
    }
}

