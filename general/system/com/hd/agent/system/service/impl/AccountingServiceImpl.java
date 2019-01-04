/**
 * @(#)AccountingServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 23, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.util.CommonUtils;
import net.sf.json.JSONArray;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.system.dao.AccountingMapper;
import com.hd.agent.system.model.Accounting;
import com.hd.agent.system.service.IAccountingService;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class AccountingServiceImpl extends BaseFilesServiceImpl  implements IAccountingService{

	private AccountingMapper accountingMapper;

	public AccountingMapper getAccountingMapper() {
		return accountingMapper;
	}

	public void setAccountingMapper(AccountingMapper accountingMapper) {
		this.accountingMapper = accountingMapper;
	}

	@Override
	public List getAccountingYearsListOrder() throws Exception {
        List<Accounting> list = accountingMapper.getAccountingYearsListOrder();
		return list;
	}

	@Override
	public List getAccountingListByYear(String year) throws Exception {
        List<Accounting> list = accountingMapper.getAccountingListByYear(year);
        int count = 1;
        for(Accounting accounting : list){
            String belongmonth = Integer.toString(count);
            if(belongmonth.length() == 1){
                belongmonth = "0" + Integer.toString(count);
            }
            accounting.setBelongmonth(belongmonth);
            count++;
        }
		return list;
	}

	@Override
	public boolean addAccounting(List<Accounting> list) throws Exception {
		return accountingMapper.addAccounting(list) > 0;
	}

	@Override
	public boolean editAccounting(String dateArrStr,String year) throws Exception {
		int ae = 0;
        JSONArray json = JSONArray.fromObject(dateArrStr);
        List<Accounting> list = JSONArray.toList(json, Accounting.class);
        //根据year删除该年度会计区间
        accountingMapper.deleteAccouting(year);
        if(list.size() != 0){
            ae = accountingMapper.addAccounting(list);
        }
        if(ae == list.size()){
            return true;
        }
		return false;
	}

	@Override
	public boolean deleteAccouting(String year) throws Exception {
		return accountingMapper.deleteAccouting(year) > 0;
	}

    @Override
    public Accounting getOpenAccounting() throws Exception {
        return accountingMapper.getOpenAccountting();
    }

    @Override
    public Accounting getAccountingInfo(String id) throws Exception {
        return accountingMapper.getAccountingInfo(id);
    }

    @Override
    public boolean editAccountingInfo(String id) throws Exception {
        Accounting accounting = accountingMapper.getAccountingInfo(id);
        if(null != accounting){
            return accountingMapper.editAccounting(accounting) > 0;
        }else{
            return false;
        }
    }

    @Override
    public Map closeAccounting(Accounting accounting, String nextid) throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        boolean flag1 = accountingMapper.editAccounting(accounting) > 0;
        if(flag1){
            Accounting nextaccounting = accountingMapper.getAccountingInfo(nextid);
            if(null != nextaccounting){
                nextaccounting.setState("1");
                flag = accountingMapper.editAccounting(nextaccounting) > 0;
                if(flag){
                    map.put("openyear",nextaccounting.getYear());
                    //获取日期月份
                    String openmonth = CommonUtils.getMonthStr(nextaccounting.getBegindate());
                    map.put("openmonth",openmonth);
                }
            }
        }
        map.put("flag",flag);
        return map;
    }

    @Override
    public void closeAccountingTask() throws Exception {
        String nowdate = CommonUtils.getTodayDataStr();
        String year = CommonUtils.getYearStr(nowdate);
        List<Accounting> list = accountingMapper.getAccountingListByYear(year);
        for(Accounting accounting : list){
            if(null != accounting){
                //1：datestr1>datestr2,-1：datestr1<datestr2,0：datestr1=datestr2
                int b = CommonUtils.compareDate(accounting.getBegindate(),nowdate);
                int e = CommonUtils.compareDate(accounting.getEnddate(),nowdate);
                if(b == -1 && e == -1){
                    accounting.setState("5");
                }else if((b == -1 || b == 0) && (e == 1 || e == 0)){
                    accounting.setState("1");
                }else if(b == 1 && e == 1){
                    accounting.setState("0");
                }
                accountingMapper.editAccounting(accounting);
            }
        }
    }

    @Override
    public List getEditDateList(Map map) throws Exception {
        String year = (String)map.get("year");
        String nochangeids = (String)map.get("nochangeids");
        String changeid = (String)map.get("changeid");
        String newEndDate = (String)map.get("newEndDate");
        String nowdate = CommonUtils.getTodayDataStr();
        List<Accounting> editList = new ArrayList<Accounting>();
        if(StringUtils.isNotEmpty(newEndDate)){
            List<Accounting> list = accountingMapper.getAccountingListByYear(year);
            SysUser sysUser = getSysUser();
            for(Accounting accounting : list){
                if(nochangeids.indexOf(accounting.getId()) != -1){
                    editList.add(accounting);
                }else if(changeid.equals(accounting.getId())){
                    accounting.setModifyusername(sysUser.getUsername());
                    accounting.setModifyuserid(sysUser.getUserid());
                    accounting.setEnddate(newEndDate);
                    editList.add(accounting);
                }
            }
            if(editList.size() != 0){
                String begindate = "",enddate = newEndDate;
                for(int i=12-editList.size();i>0;i--){
                    Accounting accounting = null;
                    accounting = new Accounting();
                    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                    Date date =sdf.parse(enddate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    calendar.add(Calendar.DATE,1);
                    begindate = sdf.format(calendar.getTime());

                    calendar.add(Calendar.MONTH,1);
                    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
                    enddate = sdf.format(calendar.getTime());

                    accounting.setYear(year);
                    accounting.setBegindate(begindate);
                    accounting.setEnddate(enddate);
                    editList.add(accounting);
                }
            }
            for(Accounting accounting1 : editList){
                //1：datestr1>datestr2,-1：datestr1<datestr2,0：datestr1=datestr2
                int b = CommonUtils.compareDate(accounting1.getBegindate(),nowdate);
                int e = CommonUtils.compareDate(accounting1.getEnddate(),nowdate);
                if(b == -1 && e == -1){
                    accounting1.setState("5");
                }else if((b == -1 || b == 0) && (e == 1 || e == 0)){
                    accounting1.setState("1");
                }else if(b == 1 && e == 1){
                    accounting1.setState("0");
                }
            }
        }
        return editList;
    }

}

