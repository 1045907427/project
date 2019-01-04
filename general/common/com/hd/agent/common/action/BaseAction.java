/**
 * @(#)BaseAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-8 chenwei 创建版本
 */
package com.hd.agent.common.action;

import com.hd.agent.accesscontrol.base.AccessControlMetadataSource;
import com.hd.agent.accesscontrol.model.AccessColumn;
import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.accesscontrol.model.Operate;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.accesscontrol.service.IDataAccessService;
import com.hd.agent.accesscontrol.service.ISecurityService;
import com.hd.agent.accesscontrol.service.ISysUserService;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.FilesLevel;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.service.*;
import com.hd.agent.common.model.SysLog;
import com.hd.agent.common.service.ISysLogService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.*;
import com.hd.agent.message.model.EmailContent;
import com.hd.agent.message.model.MobileSms;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.service.IEmailService;
import com.hd.agent.message.service.IInnerMessageService;
import com.hd.agent.message.service.IMobileSmsService;
import com.hd.agent.system.model.*;
import com.hd.agent.system.service.*;
import com.hd.agent.system.util.SysNumberUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 基础action类，所以action需要基础该类
 * @author chenwei
 */
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, ServletContextAware{


	public static final String Current_Date="busdate";


	private static final Logger logger = Logger.getLogger(BaseAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 3218710618630789519L;
	/**
	 * 金额小数位
	 */
	protected int decimalLen=2;
	/**
	 * 当前页
	 */
	private int page = 1;
	/**
	 * 每页显示条数
	 */
	private int rows = 10;
	/**
	 * 通用查询规则
	 */
	private String queryRules;
	/**
	 * 通用查询规则下的排序
	 */
	private String orderRules;
	/**
	 * 分页类
	 */
	protected PageMap pageMap = new PageMap( page,rows);
	/**
	 * 自定义日志内容
	 */
	protected String logStr;
	/**
	 * JSON对象
	 */
	protected Map jsonResult;
	/**
	 * JSON数组对象
	 */
	protected List jsonArray;
	/**
	 * 上传Excel文件
	 */
	protected File excelFile;
    /**
     * 模板文件
     */
    protected File importFile;
	/**
	 * HttpServletRequest对象
	 */
	protected HttpServletRequest request;
	/**
	 * HttpServletResponse对象
	 */
	protected HttpServletResponse response;
	/**
	 * ServletContext对象
	 */
	protected ServletContext servletContext;
	/**
	 * 用户相关service
	 */
	private ISysUserService baseSysUserService;
	/**
	 * 功能权限字段权限相关service
	 */
	private ISecurityService baseSecurityService;
	/**
	 * 数据权限相关service
	 */
	private IDataAccessService baseDataAccessService;
	/**
	 * 单据编号规则相关service
	 */
	private ISysNumberRuleService baseSysNumberRuleService;
	/**
	 * 单据编号相关service
	 */
	private ISysNumberService baseSysNumberService;
	/**
	 * 数据异常规则service
	 */
	private IDataExceptionService baseDataExceptionService;
	/**
	 * 数据字典service
	 */
	private IDataDictionaryService baseDataDictionaryService;
	/**
	 * 网络互斥service
	 */
	private INetLockService baseNetLockService;
	/**
	 * 内部短信service
	 */
	private IInnerMessageService baseInnerMessageService;
	/**
	 * 邮件service
	 */
	private IEmailService baseEmailService;
	/**
	 * 手机短信service
	 */
	private IMobileSmsService baseMobileSmsService;
	
	/**
	 * 编码表service
	 */
	private ISysCodeService baseSysCodeService;
	
	/**
	 * 部门档案service
	 */
	private IDepartMentService baseDepartMentService;
	
	/**
	 * 人员档案service
	 */
	private IPersonnelService basePersonnelService;
	/**
	 * 档案级次定义service
	 */
	private IFilesLevelService baseFilesLevelService;
	/**
	 * 工作日历service
	 */
	private IWorkCanlendarService baseWorkCanlendarService;
	/**
	 * 系统参数service
	 */
	private ISysParamService baseSysParamService;
	
	/**
	 * 基础档案财务管理service
	 */
	private IFinanceService baseFinanceService;
	
	/**
	 * 采购service
	 */
	private IBuyService baseBuyService;
	
	/**
	 * 商品档案
	 */
	private IGoodsService baseGoodsService;
	
	/**
	 * 参照窗口service
	 */
	private IReferWindowService baseReferWindowService;
	
	/**
	 * 基础档案销售管理service
	 */
	private ISalesService baseSalesService;
	
	private IStorageService baseStorageService;

    private IAccountingService baseAccountingService;

    public IAccountingService getBaseAccountingService() {
        return baseAccountingService;
    }

    public void setBaseAccountingService(IAccountingService baseAccountingService) {
        this.baseAccountingService = baseAccountingService;
    }

    public IStorageService getBaseStorageService() {
		return baseStorageService;
	}
	public void setBaseStorageService(IStorageService baseStorageService) {
		this.baseStorageService = baseStorageService;
	}
	public IFinanceService getBaseFinanceService() {
		return baseFinanceService;
	}
	public void setBaseFinanceService(IFinanceService baseFinanceService) {
		this.baseFinanceService = baseFinanceService;
	}
	public IGoodsService getBaseGoodsService() {
		return baseGoodsService;
	}
	public void setBaseGoodsService(IGoodsService baseGoodsService) {
		this.baseGoodsService = baseGoodsService;
	}
	public ISalesService getBaseSalesService() {
		return baseSalesService;
	}
	public void setBaseSalesService(ISalesService baseSalesService) {
		this.baseSalesService = baseSalesService;
	}
	public IReferWindowService getBaseReferWindowService() {
		return baseReferWindowService;
	}
	public void setBaseReferWindowService(IReferWindowService baseReferWindowService) {
		this.baseReferWindowService = baseReferWindowService;
	}
	public IWorkCanlendarService getBaseWorkCanlendarService() {
		return baseWorkCanlendarService;
	}
	public void setBaseWorkCanlendarService(
			IWorkCanlendarService baseWorkCanlendarService) {
		this.baseWorkCanlendarService = baseWorkCanlendarService;
	}
	public ISysNumberRuleService getBaseSysNumberRuleService() {
		return baseSysNumberRuleService;
	}
	public void setBaseSysNumberRuleService(
			ISysNumberRuleService baseSysNumberRuleService) {
		this.baseSysNumberRuleService = baseSysNumberRuleService;
	}
	public ISysNumberService getBaseSysNumberService() {
		return baseSysNumberService;
	}
	public void setBaseSysNumberService(ISysNumberService baseSysNumberService) {
		this.baseSysNumberService = baseSysNumberService;
	}
	
	public IDataExceptionService getBaseDataExceptionService() {
		return baseDataExceptionService;
	}
	public void setBaseDataExceptionService(
			IDataExceptionService baseDataExceptionService) {
		this.baseDataExceptionService = baseDataExceptionService;
	}
	
	public IDataDictionaryService getBaseDataDictionaryService() {
		return baseDataDictionaryService;
	}
	public void setBaseDataDictionaryService(
			IDataDictionaryService baseDataDictionaryService) {
		this.baseDataDictionaryService = baseDataDictionaryService;
	}
	public INetLockService getBaseNetLockService() {
		return baseNetLockService;
	}
	public void setBaseNetLockService(INetLockService baseNetLockService) {
		this.baseNetLockService = baseNetLockService;
	}
	
	public IInnerMessageService getBaseInnerMessageService() {
		return baseInnerMessageService;
	}
	public void setBaseInnerMessageService(
			IInnerMessageService baseInnerMessageService) {
		this.baseInnerMessageService = baseInnerMessageService;
	}
	public IEmailService getBaseEmailService() {
		return baseEmailService;
	}
	public void setBaseEmailService(IEmailService baseEmailService) {
		this.baseEmailService = baseEmailService;
	}
	public IMobileSmsService getBaseMobileSmsService() {
		return baseMobileSmsService;
	}
	public void setBaseMobileSmsService(IMobileSmsService baseMobileSmsService) {
		this.baseMobileSmsService = baseMobileSmsService;
	}
	public ISysCodeService getBaseSysCodeService() {
		return baseSysCodeService;
	}
	public void setBaseSysCodeService(ISysCodeService baseSysCodeService) {
		this.baseSysCodeService = baseSysCodeService;
	}
	public IDepartMentService getBaseDepartMentService() {
		return baseDepartMentService;
	}
	public void setBaseDepartMentService(IDepartMentService baseDepartMentService) {
		this.baseDepartMentService = baseDepartMentService;
	}
	public IPersonnelService getBasePersonnelService() {
		return basePersonnelService;
	}
	public void setBasePersonnelService(IPersonnelService basePersonnelService) {
		this.basePersonnelService = basePersonnelService;
	}
	
	public IFilesLevelService getBaseFilesLevelService() {
		return baseFilesLevelService;
	}
	public void setBaseFilesLevelService(IFilesLevelService baseFilesLevelService) {
		this.baseFilesLevelService = baseFilesLevelService;
	}
	
	public ISysParamService getBaseSysParamService() {
		return baseSysParamService;
	}
	public void setBaseSysParamService(ISysParamService baseSysParamService) {
		this.baseSysParamService = baseSysParamService;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		pageMap.setPage(page);
		this.page = page;
	}
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		pageMap.setRows(rows);
		this.rows = rows;
	}
	public PageMap getPageMap() {
		return pageMap;
	}
	public void setPageMap(PageMap pageMap) {
		this.pageMap = pageMap;
	}
	public String getLogStr() {
		return logStr;
	}
	public void setLogStr(String logStr) {
		this.logStr = logStr;
	}
	
	public Map getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(Map jsonResult) {
		this.jsonResult = jsonResult;
	}
	public List getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(List jsonArray) {
		this.jsonArray = jsonArray;
	}
	public File getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

    public File getImportFile() {
        return importFile;
    }

    public void setImportFile(File importFile) {
        this.importFile = importFile;
    }

    @Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}
	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}
	public ISysUserService getBaseSysUserService() {
		return baseSysUserService;
	}
	public void setBaseSysUserService(ISysUserService baseSysUserService) {
		this.baseSysUserService = baseSysUserService;
	}
	public ISecurityService getBaseSecurityService() {
		return baseSecurityService;
	}
	public void setBaseSecurityService(ISecurityService baseSecurityService) {
		this.baseSecurityService = baseSecurityService;
	}
	public IDataAccessService getBaseDataAccessService() {
		return baseDataAccessService;
	}
	public void setBaseDataAccessService(IDataAccessService baseDataAccessService) {
		this.baseDataAccessService = baseDataAccessService;
	}
	public String getQueryRules() {
		return queryRules;
	}
	
	public IBuyService getBaseBuyService() {
		return baseBuyService;
	}
	public void setBaseBuyService(IBuyService baseBuyService) {
		this.baseBuyService = baseBuyService;
	}
	
	public int getDecimalLen() {
		return decimalLen;
	}
	public void setDecimalLen(int decimalLen) {
		this.decimalLen = decimalLen;
	}
	/**
	 * 获取页面通用查询规则后，直接转换成sql语句赋值到PageMap中
	 * @param queryRules
	 * @author chenwei 
	 * @date 2013-1-12
	 */
	public void setQueryRules(String queryRules) {
		if(null!=queryRules && !"".equals(queryRules)){
			String sql = RuleJSONUtils.dataRuleToSQLString(queryRules, new HashMap());
			pageMap.setQuerySql(sql);
			pageMap.setQueryRules(queryRules);
		}
		this.queryRules = queryRules;
	}
	
	public String getOrderRules() {
		return orderRules;
	}
	public void setOrderRules(String orderRules) {
		if(StringUtils.isNotEmpty(orderRules)){
			String sql = RuleJSONUtils.orderRulesToSql(orderRules, null);
			pageMap.setOrderSql(sql);
			pageMap.setOrderRules(orderRules);
		}
		this.orderRules = orderRules;
	}
	/**
	 * pageDada转成JSONObject，并且赋值给jsonresult
	 * @param pageData
	 */
	public void addJSONObject(PageData pageData){
		if(null!=pageData){
			jsonResult = new HashMap();
			jsonResult.put("total", pageData.getTotal());
			jsonResult.put("rows", pageData.getList());
		}
	}
	/**
	 * pageDada转成JSONObject，并且赋值给jsonresult
	 * @param pageData
	 * @author chenwei
	 * @date Jun 1, 2013
	 */
	public void addJSONObjectWithFooter(PageData pageData){
		jsonResult = new HashMap();
		jsonResult.put("total", pageData.getTotal());
		jsonResult.put("rows", pageData.getList());
		jsonResult.put("footer", pageData.getFooter());
	}
	/**
	 * 对象转换生存JSONObject对象
	 * @param name
	 * @param o
	 */
	public void addJSONObject(String name,Object o){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(name, o);
		jsonResult =jsonMap;
	}
	/**
	 * map转JSONObject对象
	 * @param map
	 */
	public void addJSONObject(Map map){
        if(null!=map && map.containsKey("msg")){
            Object msg = map.get("msg");
            if(msg instanceof  java.lang.String){
                String msgStr = (String) msg;
                msgStr=msgStr.replaceAll("<br/>","<br>");
                msgStr=msgStr.replaceAll("</br>","<br>");
                map.put("msg",msgStr);
            }

        }
		jsonResult = map;
	}
	/**
	 * List转为json数组
	 * @param list
	 * @author chenwei 
	 * @date 2012-12-8
	 */
	public void addJSONArray(List list){
		jsonArray = list;
	}
	
	
	/**
	 * 获取当前登录用户
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-13
	 */
	public SysUser getSysUser() throws Exception{
		//获取security中的用户名
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		String username1 = username;
		String deptid = "";
		if(username.startsWith("P_")){
			String[] strArr = username.split("P_");
			username1 = strArr[1];
		} else if(username.startsWith("CW_")){
			String[] strArr = username.split("CW_");
			username1 = strArr[1];
		}else if(username.startsWith("C_")) {
			String[] strArr = username.split("C_");
			username1 = strArr[1];
			//if(username1.endsWith("@@")) {

			int loc = username1.lastIndexOf("@@");
			if(loc >= 0) {
				deptid = username1.substring(loc + 2);
				username1 = username1.substring(0, loc);
			}
			//}
		}
		//根据用户名获取用户详细信息
		SysUser sysUser = baseSysUserService.getUser(username1);
		SysUser sysUser2 = (SysUser) CommonUtils.deepCopy(sysUser);
		if(null!=sysUser){
			if(username.startsWith("P_")){
				sysUser2.setLoginType("2");
			} else if(username.startsWith("C_")){
				sysUser2.setLoginType("3");
			}else if(username.startsWith("CW_")){
				sysUser2.setLoginType("4");
			}else{
				sysUser2.setLoginType("1");
			}
		}

		if(StringUtils.isNotEmpty(deptid)) {
			sysUser2.setDeptid(deptid);
		}
		return sysUser2;
	}
	
	// 返回一个字节的十六进制字符串    
	static String hexByte(byte b) {    
		String s = "000000" + Integer.toHexString(b);    
		return s.substring(s.length() - 2);    
	} 
	
	/**
	 * 获取当前本级MAC地址
	 * JDK1.6新特性获取网卡MAC地址
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 1, 2014
	 */
	public static String getMACAddress()throws Exception{
		String retmac = "";
		try {    
			Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();    
			while (el.hasMoreElements()) {    
				byte[] mac = el.nextElement().getHardwareAddress();    
				if (mac == null)    
					continue;    
	  
				StringBuilder builder = new StringBuilder();    
				for (byte b : mac) {    
					builder.append(hexByte(b));    
					builder.append("-");    
				}
				if(builder.length() > 0){
					builder.deleteCharAt(builder.length() - 1);
				}
				System.out.println(builder);
				//retmac = builder.toString();
			}    
	    } catch (Exception exception) {    
	    	exception.printStackTrace();    
	    }
	    return retmac;
	}
	
	/**
	 * 获取用户拥有的角色列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-5
	 */
	public List<String> getUserAuthorityList() throws Exception{
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> collection = userDetails.getAuthorities();
		List<String> authorityList = new ArrayList<String>();
		for(GrantedAuthority configAttribute : collection){
			String authorityid = configAttribute.getAuthority().trim();
			authorityList.add(authorityid);
		}
		return authorityList;
	}
	/**
	 * 判断当前用户是否拥有该角色
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 30, 2013
	 */
	public boolean isHaveAuthorithBySysuser(String rolename) throws Exception{
		boolean flag = false;
		List<String> roleList = getUserAuthorityList();
		Authority authority = getAuthorityByName(rolename);
		String roleid = null;
		if(null!=authority){
			roleid = authority.getAuthorityid();
		}
		for(String authorityid : roleList){
			if(authorityid.equals(roleid)){
				flag = true;
				break;
			}
		}
		return flag;
	}
    /**
     * 判断用户是否拥有该url权限
     * @param url
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年7月23日
     */
    public boolean isSysUserHaveUrl(String url) throws Exception{
        boolean flag = false;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> collection1 = userDetails.getAuthorities();
        Map<String, Collection<ConfigAttribute>> resourceMap = AccessControlMetadataSource.resourceMap;
        if(null!=resourceMap){
            Collection<ConfigAttribute> collection = resourceMap.get(url);
            if(null!=collection){
                //用户拥有的权限与系统访问的权限比较
                //判断用户是否有权限访问该资源（URL）
                for(ConfigAttribute configAttribute : collection){
                    for(GrantedAuthority gAuthority : collection1){
                        if(configAttribute.getAttribute().trim().equals(gAuthority.getAuthority().trim())){
                            flag = true;
                            break;
                        }
                    }
                }
            }else{
                flag = true;
            }
        }
        return flag;
    }
	/**
	 * 根据名称获取角色信息
	 * @param name 			角色名称
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 30, 2013
	 */
	public Authority getAuthorityByName(String name) throws Exception{
		Authority authority = baseSecurityService.getAuthorityListByName(name);
		return authority;
	}
	/**
	 * 根据角色编号获取角色详细信息<br/>
	 * 多个编号用,分割
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 22, 2013
	 */
	public List getAuthorityListByIds(String ids) throws Exception{
		List list = baseSecurityService.getAuthorityListByIds(ids);
		return list;
	}
	/**
	 * 根据表名获取当前用户拥有的字段权限<br/>
	 * 可查看字段权限由可访问字段权限与可编辑字段权限组合一起
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public Map getAccessColumn(String tablename) throws Exception{
		String columns = null;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> collection = userDetails.getAuthorities();
		List authorityList = new ArrayList();
		for(GrantedAuthority configAttribute : collection){
			String authorityid = configAttribute.getAuthority().trim();
			authorityList.add(authorityid);
		}
		Map map = null;
		if(authorityList.size() != 0){
			map = new HashMap();
			List<AccessColumn> accessColumnList = baseSecurityService.showAccessColumnsByAuthoritys(authorityList, tablename);
			for(AccessColumn accessColumn : accessColumnList){
				String collist = accessColumn.getCollist();
				String[] colArray = collist.split(",");
				String editColList = accessColumn.getEditcollist();
				String[] editColArray = editColList.split(",");
				for(String col : colArray){
					map.put(col, col);
				}
				for(String col : editColArray){
					map.put(col, col);
				}
			}
			if(map.size()==0){
				Map queryMap = new HashMap();
				queryMap.put("tablename", tablename);
				List<TableColumn> list = baseDataDictionaryService.getTableColumnListBy(queryMap);
				for(TableColumn tableColumn : list){
					map.put(tableColumn.getColumnname(),tableColumn.getColumnname());
				}
			}
		}
		return map;
	}
	
	/**
	 * 根据表名获取当前用户拥有的可编辑字段权限
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-15
	 */
	public Map getEditAccessColumn(String tablename) throws Exception{
		String columns = null;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> collection = userDetails.getAuthorities();
		List authorityList = new ArrayList();
		for(GrantedAuthority configAttribute : collection){
			String authorityid = configAttribute.getAuthority().trim();
			authorityList.add(authorityid);
		}
		List<AccessColumn> accessColumnList = baseSecurityService.showAccessColumnsByAuthoritys(authorityList, tablename);
		Map map = new HashMap();
		for(AccessColumn accessColumn : accessColumnList){
			String collist = accessColumn.getEditcollist();
			String[] colArray = collist.split(",");
			for(String col : colArray){
				map.put(col, col);
			}
		}
		if(map.size()==0){
			Map queryMap = new HashMap();
			queryMap.put("tablename", tablename);
			List<TableColumn> list = baseDataDictionaryService.getTableColumnListBy(queryMap);
			for(TableColumn tableColumn : list){
				map.put(tableColumn.getColumnname(),tableColumn.getColumnname());
			}
		}
		return map;
	}
	
	/**
	 * 根据表名获取必填字段
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 2, 2015
	 */
	public Map getRequiredColumn(String tablename)throws Exception{
		Map map = new HashMap();
		List<String> list = baseDataDictionaryService.getRequiredColumnByTable(tablename);
		if(list.size() != 0){
			for(String col : list){
				if(StringUtils.isNotEmpty(col)){
					map.put(col, col);
				}
			}
		}
		return map;
	}
	
	/**
	 * 获取表字段权限sql字符串
	 * @param tablename		表名
	 * @param alias			表别名
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 30, 2013
	 */
	public String getAccessColumnSqlStr(String tablename,String alias) throws Exception{
		String sql = baseSecurityService.getAccessColumnSqlStr(tablename, alias);
		return sql;
	}
	/**
	 * 根据多个表名获取受字段权限控制后的字段，
	 * 并且根据条件生成不同的表别名以及字段别名
	 * @param tableMap 表名（key=表名,value=表别名）
	 * @param columnMap 列名集合(key:表名.列名，value:列别名);
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 30, 2013
	 */
	public String getAccessColumnSqlStr(Map tableMap,Map columnMap) throws Exception{
		String sql = baseSecurityService.getAccessColumnSqlStrByTables(tableMap, columnMap);
		return sql;
	}
	
	/**
	 * 根据数据对象获取自动生成的单据编号
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-19
	 */
	public synchronized String getAutoCreateSysNumbderForeign(Object obj,String tablename)throws Exception{
		Map retMap = new HashMap();
		Map sysNumberCodeRetMap= SysNumberUtils.getSysNumber(obj, tablename);
		if(sysNumberCodeRetMap.containsKey("billno") && null != sysNumberCodeRetMap.get("billno")){
			retMap.put("billno", sysNumberCodeRetMap.get("billno").toString());
			if(sysNumberCodeRetMap.containsKey("spill") && null != sysNumberCodeRetMap.get("spill")){//spill存在且不为空，则流水号溢出
				throw new Exception("编号溢出");
			}else{
				return sysNumberCodeRetMap.get("billno").toString();
			}
		}
		else{
			return null;
		}
	}
	
	/**
	 * 根据传过来的表名判断编号是否允许修改
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-2-21
	 */
	public boolean canEdit(String tablename) throws Exception{
		Map numberRule = baseSysNumberRuleService.setAutoCreateSysNumbderForeign(null, tablename);
		if(numberRule.containsKey("modifyFlag") && "1".equals(numberRule.get("modifyFlag"))){
			return true;
		}
		else if(numberRule.containsKey("modifyFlag") && "2".equals(numberRule.get("modifyFlag"))){
			return false;
		}
		return true;
	}
	
	/**
	 * 根据传过来的单据编号，判断是否自动生成
	 * 返回值结果：<br/>
	 * flag=true时，为是自动生成，flag=false时，不是
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-19
	 */
	public boolean isAutoCreate(String billcode)throws Exception{
		boolean flag=false;
		SysNumber sysNumber=baseSysNumberService.getSysNumberAutoCreate(billcode);
		if(null == sysNumber){
			flag=false;
		}
		else{
			if("1".equals(sysNumber.getAutocreate())){
				flag=true;
			}
		}
		return flag;
	}
	/**
	 * 根据条件获取用户列表.
	 * userList:用户编号列表（List对象或String数组）存放在PageMap中的condition里<br/>
	 * 返回的PageData根据isPage条件，PageData中的list会生成是否分页的数据。不分页则包含全部数据
	 * @param pageMap
	 * @param isPage 是否分页 true是false否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public PageData getSysUserListByConditon(PageMap pageMap,boolean isPage) throws Exception{
		pageMap.getCondition().put("isPage", String.valueOf(isPage));
		PageData pageData = baseSysUserService.showSysUserList(pageMap);
		return pageData;
	}
	/**
	 * 根据编号获取用户
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-3-25
	 */
	public SysUser getSysUserById(String userid) throws Exception{
		SysUser sysUser=baseSysUserService.showSysUserMoreInfo(userid);
		return sysUser;
	}
	/**
	 * 给通用查询规则sql语句字段设置别名
	 * @param alias
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	public void setQueryRulesWithAlias(String alias){
		if(null!=queryRules && !"".equals(queryRules)){
			Map map = new HashMap();
			map.put("alias", alias);
			String sql = RuleJSONUtils.dataRuleToSQLString(queryRules, map);
			pageMap.setQuerySql(sql);
		}
	}
	/**
	 * 验证表单是否能提交
	 * @param object
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-25
	 */
	public boolean validateFormSubmit(Object object) throws Exception{
		Map returnMap = new HashMap();
		List msgList = new ArrayList();
		String url = request.getServletPath();
		//type为null或者空时，表示表单验证要处于正常区间内
		//type为1时，表示表单验证时，值可以处于异常区间内
		String type = request.getParameter("type");
		Map map = BeanUtils.describe(object);
		List<DataException> list = baseDataExceptionService.getDataExceptionByURL(url);
		boolean flag = true;
		//数据异常状态 N正常W异常E错误
		String state = "N";
		for(DataException dataException : list){
			//得到表单提交过来的值
			String valueStr = (String) map.get(dataException.getMcolumn());
			BigDecimal value;
			if(null!=valueStr&&!"".equals(valueStr)){
				value = new BigDecimal(valueStr);
			}else{
				value = new BigDecimal(0);
			}
			BigDecimal val = new BigDecimal(0);
			//数据异常规则校验类型为固定值时
			if("1".equals(dataException.getType())){
				val = dataException.getVal();
				
			}else{
				//数据异常规则校验类型为其他表时 获取该表对应字段的值
				String columnValue = (String) map.get(dataException.getMrelatecolumn());
				val = baseDataDictionaryService.getTableNumber(dataException.getRtable(),dataException.getRcolumn(),
						dataException.getRrelatecolumn(), columnValue);
			}
			
			//获取正常上限值=固定值+固定值*(正常上限/100)
			BigDecimal normalupVal = val.add(val.multiply(dataException.getNormalup().divide(new BigDecimal(100))));
			//获取正常下限值=固定值-固定值*(正常下限/100)
			BigDecimal normaldownVal = val.subtract(val.multiply(dataException.getNormaldown().divide(new BigDecimal(100))));
			//获取异常上限值=固定值+固定值*(异常上限/100)
			BigDecimal exceptionupVal = val.add(val.multiply(dataException.getExceptionup().divide(new BigDecimal(100))));
			//获取异常下限值=固定值-固定值*(异常下限/100)
			BigDecimal exceptiondownVal = val.subtract(val.multiply(dataException.getExceptiondown().divide(new BigDecimal(100))));
			
			//数据异常提醒信息
			TableColumn mcolumn = baseDataDictionaryService.getColumnByTableColumn(dataException.getMtable(), dataException.getMcolumn());
			String msg = "属性："+mcolumn.getColchnname()+".提醒："+dataException.getExremind()+
						".正常范围上限为"+normalupVal.setScale(4)+"下限为"+normaldownVal.setScale(4)+
						".异常范围上限为"+normalupVal.setScale(4)+"-"+exceptionupVal.setScale(4)+"下限为"+exceptiondownVal.setScale(4)+"-"+normaldownVal.setScale(4);
			
			//值比较 小于或等于正常上限 且大于或等于正常下限时
			if((value.compareTo(normalupVal)==-1||value.compareTo(normalupVal)==0)&&
					(value.compareTo(normaldownVal)==1||value.compareTo(normaldownVal)==0)){
				
			} else if (value.compareTo(normalupVal) == 1
					&& (value.compareTo(exceptionupVal) == 0 || value.compareTo(exceptionupVal) == -1)) {
				//值大于正常上限 且小于或等于异常上限时
				if("1".equals(type)&&flag){
					flag = true;
				}else{
					msgList.add(msg);
					if(!"E".equals(state)){
						state = "W";
					}
					flag = false;
				}
			}else if(value.compareTo(normaldownVal) == -1
					&& (value.compareTo(exceptiondownVal) == 0 || value.compareTo(exceptiondownVal) == 1)){
				//值小于正常下限 且大于或等于异常下限时
				if("1".equals(type)&&flag){
					flag = true;
				}else{
					msgList.add(msg);
					if(!"E".equals(state)){
						state = "W";
					}
					flag = false;
				}
			}else{
				//在异常上下限之外的 
				String errorMsg = "属性："+mcolumn.getColchnname()+".数据不符合规则"+
								".正常范围上限为"+normalupVal.setScale(4)+"下限为"+normaldownVal.setScale(4)+
								".异常范围上限为"+normalupVal.setScale(4)+"-"+exceptionupVal.setScale(4)+"下限为"+exceptiondownVal.setScale(4)+"-"+normaldownVal.setScale(4);
				msgList.add (errorMsg);
				state = "E";
				flag = false;
			}
		}
		returnMap.put("flag", flag);
		returnMap.put("state", state);
		returnMap.put("msgList", msgList);
		addJSONObject(returnMap);
		return !flag;
	}
	
	//**************************************//
	// 短信提醒
	//**************************************//
	/**
	 * 添加消息提醒<br/>
	 * map中的参数：<br/>
	 * 公共参数：<br/>
	 * mtiptype: 选用提交方式，1短信，2邮件，3手机短信，可以组合发送，如1,2,3，表明三种方式都要发送<br/>
	 * title : 如何为空，则从content中截取前200个字<br/>
	 * content : 提醒的内容<br/>
	 * receivers : 接收用户编号组,字符串型，使用“,”隔开<br/>
	 * senduserid : 发送者编号,如果是系统消息，就不用填写或者填入system<br/>
	 * 内部消息特有参数：<br/>
	 * msgtype :消息类型1个人短信2公告通知3电子邮件4工作流5业务预警<br/>
	 * remindurl : 所提醒的业务详细地址<br/>
	 * tabtitle : tab标题<br/>
	 * 手机短信特有参数：<br/>
	 * mobiles : 手机号，字符串型，使用“,”隔开<br/>
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-9
	 */
	public boolean addMessageReminder(Map map) throws Exception{
		boolean flag=false;
		if(!map.containsKey("mtiptype") || StringUtils.isEmpty(map.get("mtiptype").toString())){
			return false;
		}		
		String[] mtiptypearr=map.get("mtiptype").toString().split(",");
		String senduserid = "system";;
		if(map.containsKey("senduserid")){
			senduserid = map.get("senduserid").toString();
		}
		if(ArrayUtils.contains(mtiptypearr, "1") && map.containsKey("receivers") && StringUtils.isNotEmpty(map.get("receivers").toString())){

			//短信
			MsgContent msgContent=new MsgContent();
			msgContent.setReceivers(map.get("receivers").toString());
			msgContent.setClocktype("1");	//及时发送
			if(map.containsKey("remindurl")){
				msgContent.setUrl(map.get("remindurl").toString());	//提醒地址
			}
			if(map.containsKey("content")){
				msgContent.setContent(map.get("content").toString());
			}else{
				msgContent.setContent(" ");
			}
			if(map.containsKey("title")){
				if(StringUtils.isNotEmpty(map.get("title").toString())){
					msgContent.setTitle(map.get("title").toString());
					if(msgContent.getTitle().length()>200){
						msgContent.setTitle(msgContent.getTitle().substring(0, 200));
					}
				}else{
					msgContent.setTitle("");
				}
			}else{
				msgContent.setTitle(CommonUtils.htmlFilter(msgContent.getContent()));
				if(msgContent.getTitle().length()>200){
					msgContent.setTitle(msgContent.getTitle().substring(0, 200));
				}
			}
			if(map.containsKey("tabtitle") && StringUtils.isNotEmpty(map.get("tabtitle").toString())){
				msgContent.setTabtitle(map.get("tabtitle").toString());
			}
			msgContent.setAdduserid(senduserid);
			msgContent.setDelflag("1");
			if(map.containsKey("msgtype")){
				msgContent.setMsgtype(map.get("msgtype").toString());
			}else{
				msgContent.setMsgtype("0");	//其他
			}
			flag=baseInnerMessageService.addSendMessage(msgContent);
			if(flag){
				String[] recArr  = msgContent.getReceivers().split(",");
                String phoneUrl="";
				//是否启用内部消息推送
				//1启用时，内部短信会推荐到手机
				//0禁用时，内部短信不会推荐到手机
				//		默认值为1
				String isInnerMessagePushToMobile = getSysParamValue("InnerMessagePushToMobile");
				if(null==isInnerMessagePushToMobile || "".equals(isInnerMessagePushToMobile.trim())){
					isInnerMessagePushToMobile="1";
				}
                for(String userid : recArr){
                	if(null==userid || "".equals(userid.trim())){
                		continue;
                	}
                    Pattern pattern=null;
                    Matcher matcher=null;
                    String urlTemp="";
                    if(StringUtils.isNotEmpty(msgContent.getUrl())){
                    	if("1".equals(msgContent.getMsgtype())){
                    		phoneUrl="phone/message/showInnerMessageDetailPage.do?id="+msgContent.getId()+"&markreceiveflag=true&backlistpagetip=true";
                    	}
                        if("2".equals(msgContent.getMsgtype())){
                            //公告
                            urlTemp="phone/message/showNoticeDetailPage.do?id=";
                            pattern=Pattern.compile("noticeid=(\\d+)");
                            matcher=pattern.matcher(msgContent.getUrl());
                            if(matcher.find()){
                                if(null!=matcher.group(1)){
                                	phoneUrl=urlTemp+matcher.group(1)+"&backlistpagetip=true";
                                }
                            }

                        }else if("3".equals(msgContent.getMsgtype())){
                            //邮件
                            urlTemp="phone/message/showEmailReceiveDetailPage.do?id=";
                            pattern=Pattern.compile("id=(\\d+)");
                            matcher=pattern.matcher(msgContent.getUrl());
                            if(matcher.find()){
                                if(null!=matcher.group(1)){
                                	phoneUrl=urlTemp+matcher.group(1)+"&backlistpagetip=true";
                                }
                            }
                        }else if("4".equals(msgContent.getMsgtype())){
                        	phoneUrl=msgContent.getUrl().replace("act/", "act/phone/");
                        }
                    }

                    String title=CommonUtils.htmlFilter(msgContent.getTitle());
                    String content=CommonUtils.htmlFilter(msgContent.getContent());
					if("1".equals(isInnerMessagePushToMobile.trim())) {
						try {
							sendPhoneMsg(userid, "1", title, content, phoneUrl);
						}catch (Exception ex){
							logger.error("推送到手机时，异常",ex);
						}
					}
                }
			}
		}
		if(ArrayUtils.contains(mtiptypearr, "2") && map.containsKey("receivers") && StringUtils.isNotEmpty(map.get("receivers").toString())){

			//邮件
			EmailContent emailContent = new EmailContent();
			emailContent.setSendflag("1");
			emailContent.setReceiveuser(map.get("receivers").toString());
			if(map.containsKey("content")){
				emailContent.setContent(map.get("content").toString());
			}else{
				emailContent.setContent("");
			}
			if(map.containsKey("title")){
				if(StringUtils.isNotEmpty(map.get("title").toString())){
					emailContent.setTitle(map.get("title").toString());
					if(emailContent.getTitle().length()>200){
						emailContent.setTitle(emailContent.getTitle().substring(0, 200));
					}
				}else{
					emailContent.setTitle("");
				}
			}else{
				emailContent.setTitle(CommonUtils.htmlFilter(emailContent.getContent()));
				if(emailContent.getTitle().length()>200){
					emailContent.setTitle(emailContent.getTitle().substring(0, 200));
				}
			}
			emailContent.setMailtype("0");
			emailContent.setDelflag("1");
			emailContent.setImportantflag("0");
			emailContent.setAdduserid(senduserid);
			emailContent.setAddtime((new Date()));
			flag= baseEmailService.addEmail(emailContent);
		} 
		if(ArrayUtils.contains(mtiptypearr, "3") 
				&& ( map.containsKey("receivers") && StringUtils.isNotEmpty(map.get("receivers").toString()) 
				||  map.containsKey("mobiles") && StringUtils.isNotEmpty(map.get("mobiles").toString()) )){
			//手机短信
			List<MobileSms> smsList=new ArrayList<MobileSms>();
				Calendar calendar=Calendar.getInstance();
				calendar.add(Calendar.MINUTE, 1);	//延时1分钟发送，这样可以使用JOB服务
				Date sendDate=calendar.getTime();

				String serialno=baseMobileSmsService.getSerialno();
				if(StringUtils.isEmpty(serialno)){
					serialno=CommonUtils.getDataNumber();
				}
			if(map.containsKey("receivers")&& StringUtils.isNotEmpty(map.get("receivers").toString())){
				String[] idarr=map.get("receivers").toString().split(",");
				Map<String,Object> userMap=new HashMap<String,Object>();
				userMap.put("userList", idarr);
				PageMap pMap=new PageMap();
				pMap.setCondition(userMap);
				PageData pageData=getSysUserListByConditon(pMap,false);
				if(pageData!=null && pageData.getList()!=null){
					List<SysUser> list=pageData.getList();
					for(SysUser item : list){
						if(StringUtils.isNotEmpty(item.getMobilephone())){
							MobileSms mobileSms=new MobileSms();
							mobileSms.setAdduserid(senduserid);
							mobileSms.setMobile(item.getMobilephone());
							mobileSms.setRecvuserid(item.getUserid());
							if(StringUtils.isNotEmpty(map.get("content").toString())){
								mobileSms.setContent(map.get("content").toString());
							}else{
								mobileSms.setContent("");
							}
							mobileSms.setDelflag("1");
							mobileSms.setSendflag("1");
							mobileSms.setAddtime(sendDate);
							mobileSms.setSendtime(sendDate);
							mobileSms.setSerialno(serialno);
							smsList.add(mobileSms);
						}
					}
				}
			}
			if(map.containsKey("mobiles") && StringUtils.isNotEmpty(map.get("mobiles").toString())){
				String[] mobarr=map.get("mobiles").toString().split(",");
				for(String item :mobarr){
					if(item!=null && !"".equals(item.trim())){
						MobileSms mobileSms=new MobileSms();
						mobileSms.setAdduserid(senduserid);
						mobileSms.setMobile(item.trim());
						if(StringUtils.isNotEmpty(map.get("content").toString())){
							mobileSms.setContent(map.get("content").toString());
						}else{
							mobileSms.setContent("");
						}
						mobileSms.setDelflag("1");
						mobileSms.setSendflag("1");
						mobileSms.setAddtime(sendDate);
						mobileSms.setSendtime(sendDate);
						mobileSms.setSerialno(serialno);
						smsList.add(mobileSms);
					}
				}
			}
				if(baseMobileSmsService.addMobileSmsList(smsList)){
					ITaskScheduleService taskScheduleService=null;
					try{
						taskScheduleService=(ITaskScheduleService) SpringContextUtils.getBean("taskScheduleService");
					}catch (Exception e) {

					}
					if(taskScheduleService!=null){
						String con = CommonUtils.getQuartzCronExpression(sendDate);
						
						Map<String,Object> dataMap = new HashMap<String,Object>();
						dataMap.put("serialno",serialno);
						dataMap.put("senduserid", senduserid);
						taskScheduleService.addTaskScheduleAndStart( serialno  ,"手机短信发送(按单次计划)", "com.hd.agent.message.job.MobileSmsDelayOneSendJob", "MobileSmsOneDelaySend", con, "1",dataMap);
					}
				}
				flag=true;
		}
		return flag;
	}
	
	/**
	 * 给数据加锁
	 * @param tablename		表名
	 * @param lockid		需要加锁的数据编号
	 * @param isOverTime	是否自动超时true是false否 选择true,加锁时间超过10分钟后将自动解锁，false将不会自动解锁
	 * @return				true:可以进行操作 false不可以进行操作 数据已被别人加锁
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 12, 2013
	 */
	public boolean lockData(String tablename,String lockid,boolean isOverTime) throws Exception{
		return baseNetLockService.doLockData(tablename, lockid, isOverTime);
	}
	/**
	 * 给数据加锁。默认自动超时为true。
	 * @param tablename	表名
	 * @param lockid	需要加锁的数据编号
	 * @return			true:可以进行操作 false不可以进行操作 数据已被别人加锁
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 12, 2013
	 */
	public boolean lockData(String tablename,String lockid) throws Exception{
		return baseNetLockService.doLockData(tablename, lockid, true);
	}
	/**
	 * 判断数据是否可以操作。
	 * 如果加锁人是自己，则对该数据进行解锁
	 * true可以操作。false不可以操作
	 * @param tablename
	 * @param lockid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 29, 2013
	 */
	public boolean isLockEdit(String tablename, String lockid) throws Exception{
		boolean flag = baseNetLockService.setUnLock(tablename, lockid);
		return flag;
	}
	/**
	 * 判断对于当前用户 该数据是否已经加锁
	 * true加锁 不可以操作 false未加锁 可以操作
	 * @param tablename
	 * @param lockid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 30, 2013
	 */
	public boolean isLock(String tablename, String lockid) throws Exception{
		boolean flag = baseNetLockService.isLock(tablename, lockid);
		return flag;
	}
	/**
	 * 添加日志内容。
	 * flag给日志内容加上成功或者失败标识
	 * @param msg			日志内容。
	 * @param flag			true:成功 false:失败
	 * @author chenwei 
	 * @date Mar 26, 2013
	 */
	public void addLog(String msg,boolean flag){
		if(null==msg){
			msg = "";
		}
		if(flag){
			logStr = msg+" 成功";
		}else{
			logStr = msg+" 失败";
		}
	}
	/**
	 * 添加日志内容。
	 * flag给日志内容加上成功或者失败标识
	 * @param msg			日志内容。
	 * @param map			去map中的flag属性 true表示成功false表示失败
	 * @author chenwei
	 * @date Mar 26, 2013
	 */
	public void addLog(String msg,Map map){
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
		}else if(null!=map && map.containsKey("auditflag")){
			flag = (Boolean) map.get("auditflag");
		}
		if(null==msg){
			msg = "";
		}
		if(flag){
			logStr = msg+" 成功";
		}else{
			logStr = msg+" 失败";
		}
	}
	/**
	 * 添加日志内容
	 * @param msg
	 * @author chenwei 
	 * @date Mar 26, 2013
	 */
	public void addLog(String msg){
		if(null==msg){
			msg = "";
		}
		logStr = msg;
	}
	
	/**
	 * 根据URL地址获取菜单或者按钮信息
	 * @param url
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 28, 2013
	 */
	public Operate getOperateByURL(String url) throws Exception{
		if(null != url && url.indexOf("?")>0){
			url = url.substring(0,url.indexOf("?"));
		}
		if(".".equals(url.substring(0, 1))){
			url = url.substring(1,url.length());
		}else if(!"/".equals(url.substring(0, 1))){
			url = "/" +url;
		}
		return baseSecurityService.getOperateByURL(url);
	}
	
	/**
	 * 根据部门id数组字符串获取部门详情列表
	 * @param idsStr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-28
	 */
	public List<DepartMent> getDeptListByIds(String idsStr)throws Exception{
		List<DepartMent> deptList = baseDepartMentService.getDeptListByIdsStr(idsStr);
		return deptList;
	}
	
	/**
	 * 根据人员ID数组字符串获取人员档案列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-2
	 */
	public List<Personnel> getPersonnelListByIds(String idsStr)throws Exception{
		List<Personnel> personList = basePersonnelService.getPersonnelListByIds(idsStr);
		return personList;
	}
	
	/**
	 * 用户关联人员信息
	 * 根据系统用户中关联的人员编号获取人员信息，若没有人员信息，则返回空值null
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-30
	 */
	public Personnel sysUserConnectePersonnelInfo(String userid)throws Exception{
		if(StringUtils.isNotEmpty(userid)){
			SysUser userInfo = baseSysUserService.showSysUserInfo(userid);
			if(null != userInfo){
				if(StringUtils.isNotEmpty(userInfo.getPersonnelid())){
					return basePersonnelService.showPersonnelInfo(userInfo.getPersonnelid());
				}
			}
		}
		return null;
	}
	/**
	 * 根据用户档案编号获取用户档案信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-7
	 */
	public Personnel getPersonnelInfoById(String id) throws Exception{
		if(null == id || "".equals(id.trim())){
			return null;
		}
		return basePersonnelService.showPersonnelInfo(id.trim());
	}
	/**
	 * 判断基础档案数据是否可以删除
	 * true 可以删除 false不可以删除
	 * @param tablename
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 12, 2013
	 */
	public boolean canTableDataDelete(String tablename,String id) throws Exception{
		boolean flag = baseDataDictionaryService.canTableDataDelete(tablename,id);
		return flag;
	}
	
	/**
	 * 传入树状本级编码的长度，获取下一节点编码的长度
	 * @param tablename
	 * @param length
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public int getBaseTreeFilesNext(String tablename,int length) throws Exception{
		tablename = CommonUtils.tablenameDealWith(tablename);
		tablename = StringEscapeUtils.escapeSql(tablename);
		List<FilesLevel> list = baseFilesLevelService.showFilesLevelList(tablename);
		int i = 0;
		int level = 0;
		for(FilesLevel filesLevel : list){
			i += filesLevel.getLen();
			if(i==length&&length!=0){
				level = filesLevel.getLevel()+1;
			}
		}
		if(length==0){
			level=1;
		}
		int nextLen = baseFilesLevelService.getFilesLevel(tablename, level);
		return nextLen;
	}
	
	/**
	 * 根据表名称获取树状各级次的长度
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 14, 2014
	 */
	public String getTreeLevelLens(String tablename)throws Exception{
		String msg = "档案编码级次长度定义为：";
		tablename = CommonUtils.tablenameDealWith(tablename);
		tablename = StringEscapeUtils.escapeSql(tablename);
		List<FilesLevel> list = baseFilesLevelService.showFilesLevelList(tablename);
		if(list.size() != 0){
			String lenMsg = "";
			for(FilesLevel filesLevel : list){
				if(StringUtils.isEmpty(lenMsg)){
					lenMsg = "级次"+filesLevel.getLevel()+"长度"+filesLevel.getLen();
				}else{
					lenMsg += "、" + "级次"+filesLevel.getLevel()+"长度"+filesLevel.getLen();
				}
			}
			if(StringUtils.isNotEmpty(lenMsg)){
				msg = msg + lenMsg;
			}else{
				msg = "";
			}
		}else{
			msg = "请先配置档案编码级次定义!";
		}
		return 	msg;
	}
	
	/**
	 * 结合字段权限获取基础档案表中字段是否可以修改<br/>
	 * 返回可以访问的字段map map中不存在的字段表示不能修改
	 * @param tablename
	 * @param object
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-21
	 */
	public Map getBaseFilesEditWithAuthority(String tablename,Object object) throws Exception{
		//字段权限
		Map authorityMap = getEditAccessColumn(tablename);
		
		//基础档案被引用了的字段以及是否可以修改，启用后是否可以修改等条件集合
		Map baseFileseditMap = baseDataDictionaryService.getBaseFilesEditState(tablename, object);
		
		//两种字段控制结合
		Iterator it = baseFileseditMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next(); 
			//基础档案能修改的字段与字段权限结合
			if((Boolean)entry.getValue()==true){
				authorityMap.put(entry.getKey(), entry.getKey());
			}else{
				//不能修改的字段则同时把字段权限中的移除
				authorityMap.remove(entry.getKey());
			}
		}
		return authorityMap;
	}
	
	/**
	 * 根据业务属性employetypeId，获取人员列表
	 * 0采购员 1客户业务员 2理货员 3品牌业务员
	 * 4销售内勤 5车销人员 6物流 7厂家业务员
	 * @param employetypeId
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public List<Personnel> getPersListByOperationType(String employetypeId)throws Exception{
		List<Personnel> perList = basePersonnelService.getPersListByOperType(employetypeId);
		return perList;
	}
	
	/**
	 * 根据业务属性employetype，获取人员所属部门列表
	 * @param employetypeId
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 17, 2015
	 */
	public List<Map> getDeptListByOpertype(String employetypeId)throws Exception{
		List<Map> deptlist = basePersonnelService.getDeptListByOpertype(employetypeId);
		return deptlist;
	}
	/**
	 * 根据业务属性employetypeId，部门编号，获取人员列表
	 * 0采购员 1客户业务员 2理货员 3品牌业务员
	 * 4销售内勤 5车销人员 6物流 7厂家业务员
	 * @param employetypeId
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 17, 2013
	 */
	public List<Personnel> getPersListByOperationTypeAndDeptid(String employetypeId,String deptid)throws Exception{
		List<Personnel> perList = basePersonnelService.getPersListByOperTypeAndDeptid(employetypeId,deptid);
		return perList;
	}
	/**
	 * 根据业务属性depttypeId，获取部门列表
	 * @param depttypeId
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public List<DepartMent> getDeptListByOperationType(String depttypeId)throws Exception{
		List<DepartMent> deptList = baseDepartMentService.getDeptListByOperType(depttypeId);
		return deptList;
	}
	/**
	 * 手机号发送序列
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-16
	 */
	public String getSmsSerialno() throws Exception{
		return baseMobileSmsService.getSerialno();
	}
	/**
	 * 根据组装好的短信列表发送短信
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-16
	 */
	public boolean addSmsSendBy(List<MobileSms> list,String smsserialno,String senduserid) throws Exception{
		boolean flag=false;
		if(list==null || list.size()==0){
			return false;
		}
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 1);	//延时1分钟发送，这样可以使用JOB服务
		Date sendDate=calendar.getTime();
		if(baseMobileSmsService.addMobileSmsList(list)){
			flag=true;
			ITaskScheduleService taskScheduleService=null;
			try{
				taskScheduleService=(ITaskScheduleService) SpringContextUtils.getBean("taskScheduleService");
			}catch (Exception e) {

			}
			if(taskScheduleService!=null){
				String con = CommonUtils.getQuartzCronExpression(sendDate);
				
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("serialno",smsserialno);
				dataMap.put("senduserid", senduserid);
				taskScheduleService.addTaskScheduleAndStart( smsserialno  ,"手机短信发送(按单次计划)", "com.hd.agent.message.job.MobileSmsDelayOneSendJob", "MobileSmsOneDelaySend", con, "1",dataMap);
			}
		}
		return flag;
	}
	/**
	 * 根据参数名获取参数的值
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 24, 2013
	 */
	public String getSysParamValue(String name) throws Exception{
		SysParam sysParam = baseSysParamService.getSysParam(name);
		if(null!=sysParam){
			return sysParam.getPvalue();
		}else{
			return "";
		}
	}
	/**
	 * 获取金额小数位保留位数
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 23, 2014
	 */
	public int getAmountDecimalsLength() throws Exception{
		int len =2;
		String amountDecimalsLength = getSysParamValue("amountDecimalsLength");
		if(null==amountDecimalsLength || "".equals(amountDecimalsLength) || !StringUtils.isNumeric(amountDecimalsLength)){
			len = 2;
		}else{
			len = Integer.parseInt(amountDecimalsLength);
		}
		if(CommonUtils.decimalLen!=len){
			BaseServiceImpl baseServiceImpl = (BaseServiceImpl) SpringContextUtils.getBean("baseServiceImpl");
			baseServiceImpl.setDecimalLen(len);
			CommonUtils.decimalLen = len;
		}
		decimalLen = len;
		return decimalLen;
	}
	/**
	 * 获取当前用户的打印权限 是否能不限制打印次数
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 30, 2013
	 */
	public String getPrintLimitInfo() throws Exception{
		String printlimit=getSysParamValue("PrintOnceLimitBILL");
		String printNoLimitBILL = getSysParamValue("printNoLimitBILL");
		boolean printroleflag = isHaveAuthorithBySysuser(printNoLimitBILL);
		printlimit=(!"1".equals(printlimit)?"0":printlimit);
		if(printroleflag){
			printlimit = "0";
		}
		return printlimit;
	}
	/**
	 * 获取自定义字段名称
	 * @return map
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 16, 2013
	 */
	public Map<String, Object> getColumnDIYFieldName(String tablename) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		if(null==tablename || "".equals(tablename.trim())){
			return map;
		}
		map.put("tablename", tablename);
		List<TableColumn> list = getBaseDataDictionaryService().getTableColumnListBy(map);
		map.clear();
		if(list!=null && list.size()>0){
			for(TableColumn column : list){
				if(column.getColumnname().indexOf("field")>-1){
					map.put(column.getColumnname(), column.getColchnname());
				}
			}
		}
		return map;
	}
	/**
	 * 根据表名从数据字典中获取自定义描述
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 20, 2013
	 */
	public Map getRowDescFromDataDict(String tablename)throws Exception{
		//从数据字典中获取自定义描述
		Map queryMap=new HashMap();
		queryMap.put("tablename",tablename);
		List<TableColumn> list=getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		Map map = new HashMap();
		for(TableColumn tableColumn : list){
			if(!"".equals(tableColumn.getColumnname()) && !"".equals(tableColumn.getColchnname()) ){
				map.put(tableColumn.getColumnname(),tableColumn.getColchnname());
			}
		}
		return map;
	}
	/**
	 * 根据表名和字段名获取表字段信息
	 * @param tablename
	 * @param columnname
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-8
	 */
	public TableColumn getDictTableColumnBy(String tablename,String columnname) throws Exception{
		//从数据字典中获取自定义描述
		Map queryMap=new HashMap();
		queryMap.put("tablename",tablename);
		TableColumn tableColumn=null;
		List<TableColumn> list=getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		for(TableColumn item : list){
			if(null!=columnname && !"".equals(columnname.trim()) && columnname.equals(item.getColumnname())){
				tableColumn=item;
				break;
			}
		}
		return tableColumn;
	}
	
	/**
	 * 获取当前登陆的账套名称
	 * @return 名称
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-19
	 */
	public String getCurrentSetOfBookDbName() throws Exception{
		List<SysCode> list=baseSysCodeService.showSysCodeListByType("currentSetOfBook");
		SysCode sysCode=null;
		if(null!=list && list.size()>0){
			sysCode=list.get(0);			
		}
		String sob="agenterp";
		if(null!=sysCode){
			if(StringUtils.isNotEmpty(sysCode.getCodename())){
				sob=sysCode.getCodename();
			}
		}
		return sob;
	}
	
	/**
	 * 将获取的对象转换成对应的格式放入Map中
	 * @param retMap
	 * @param entry
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void objectCastToRetMap(Map<String, Object> retMap,Map.Entry<String, Object> entry){
		Object object = entry.getValue();
		if(null!=object){
			if (object instanceof String) {
				retMap.put(entry.getKey(), object);
			}else if(object instanceof BigDecimal){
				BigDecimal bignum = (BigDecimal) object;
				retMap.put(entry.getKey(), bignum);
			}else if(object instanceof Timestamp){
				Timestamp timestamp = (Timestamp) object;
				retMap.put(entry.getKey(), timestamp);
			}else if(object instanceof Date){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				retMap.put(entry.getKey(), df.format(object));
			} else if(object instanceof Integer){
                Integer intnum = (Integer) object;
                retMap.put(entry.getKey(), intnum);
            } else if(object instanceof Long){
                Long longval = (Long) object;
                retMap.put(entry.getKey(), longval);
            }
		}else{
			retMap.put(entry.getKey(), "");
		}
	}
	
	/**
	 * 导入类型转换
	 * @param map
	 * @param fields
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 30, 2013
	 */
	public void DRCastTo(Map<String, Object> map,Field[] fields)throws Exception{
		for(Field field : fields){
			if(field.getType() == java.util.Date.class){ //如果该属性是Date类型，转换为时间格式
				if(map.containsKey(field.getName()) && null != map.get(field.getName())){
					String str = map.get(field.getName()).toString();
					if(StringUtils.isNotEmpty(str)){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						map.put(field.getName(), dateFormat.parse(str));
					}
					else{
						map.put(field.getName(), null);
					}
				}
				else{
					map.put(field.getName(), null);
				}
			}
			else if(field.getType() == java.lang.Integer.class){ //如果该属性是Integer类型，转换为数字格式
				if(map.containsKey(field.getName()) && null != map.get(field.getName())){
					String str = map.get(field.getName()).toString();
                    str = str.trim();
					if(StringUtils.isNumeric(str) && StringUtils.isNotEmpty(str)){
						if(str.indexOf(".") >= 0){
							str = str.substring(0, str.lastIndexOf("."));
						}
						map.put(field.getName(), Integer.parseInt(str));
					}
					else{
						map.put(field.getName(), 0);
					}
				}
				else{
					map.put(field.getName(), 0);
				}
			}
			else if(field.getType() == java.lang.String.class){ //如果该属性是Integer类型，转换为数字格式
				if(map.containsKey(field.getName()) && null != map.get(field.getName())){
					String str = map.get(field.getName()).toString();
                    str = str.trim();
					if(StringUtils.isNotEmpty(str)){
						map.put(field.getName(), str);
					}
					else{
						map.put(field.getName(), null);
					}
				}
				else{
					map.put(field.getName(), null);
				}
			}
			else if(field.getType() == java.lang.Double.class){ //如果该属性是Double类型，转换为Double格式
				if(map.containsKey(field.getName()) && null != map.get(field.getName())){
					String str = map.get(field.getName()).toString();
                    str = str.trim();
					if(StringUtils.isNotEmpty(str) && StringUtils.isNumeric(str)){
						map.put(field.getName(), Double.parseDouble(str));
					}
					else{
						map.put(field.getName(), Double.parseDouble("0"));
					}
				}
				else{
					map.put(field.getName(), Double.parseDouble("0"));
				}
			}
			else if(field.getType() == java.math.BigDecimal.class){
				if(map.containsKey(field.getName()) && null != map.get(field.getName())){
					String str = map.get(field.getName()).toString();
                    str = str.trim();
                    if(StringUtils.isNotEmpty(str)){
						map.put(field.getName(), new BigDecimal(str));
					}
					else{
						map.put(field.getName(), BigDecimal.ZERO);
					}
				}
				else{
					map.put(field.getName(), BigDecimal.ZERO);
				}
			}
		}
	}

	/**
	 * 导入类型转换(库存初始化专用)
	 * @param map
	 * @param fields
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Oct 30, 2013
	 */
	public void DRCastToStockInit(Map<String, Object> map,Field[] fields)throws Exception{
		for(Field field : fields){
			if(field.getType() == java.util.Date.class){ //如果该属性是Date类型，转换为时间格式
				if(map.containsKey(field.getName()) && null != map.get(field.getName())){
					String str = map.get(field.getName()).toString();
					if(StringUtils.isNotEmpty(str)){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						map.put(field.getName(), dateFormat.parse(str));
					}
					else{
						map.put(field.getName(), null);
					}
				}
				else{
					map.put(field.getName(), null);
				}
			}
			else if(field.getType() == java.lang.Integer.class){ //如果该属性是Integer类型，转换为数字格式
				if(map.containsKey(field.getName()) && null != map.get(field.getName())){
					String str = map.get(field.getName()).toString();
					str = str.trim();
					if(StringUtils.isNumeric(str) && StringUtils.isNotEmpty(str)){
						if(str.indexOf(".") >= 0){
							str = str.substring(0, str.lastIndexOf("."));
						}
						map.put(field.getName(), Integer.parseInt(str));
					}
					else{
						map.put(field.getName(), 0);
					}
				}
				else{
					map.put(field.getName(), 0);
				}
			}
			else if(field.getType() == java.lang.String.class){ //如果该属性是Integer类型，转换为数字格式
				if(map.containsKey(field.getName()) && null != map.get(field.getName())){
					String str = map.get(field.getName()).toString();
					str = str.trim();
					if(StringUtils.isNotEmpty(str)){
						map.put(field.getName(), str);
					}
					else{
						map.put(field.getName(), null);
					}
				}
				else{
					map.put(field.getName(), null);
				}
			}
			else if(field.getType() == java.lang.Double.class){ //如果该属性是Double类型，转换为Double格式
				if(map.containsKey(field.getName()) && null != map.get(field.getName())){
					String str = map.get(field.getName()).toString();
					str = str.trim();
					if(StringUtils.isNotEmpty(str) && StringUtils.isNumeric(str)){
						map.put(field.getName(), Double.parseDouble(str));
					}
					else{
						map.put(field.getName(), Double.parseDouble("0"));
					}
				}
				else{
					map.put(field.getName(), Double.parseDouble("0"));
				}
			}
			else if(field.getType() == java.math.BigDecimal.class){
				if(map.containsKey(field.getName()) && null != map.get(field.getName())){
					String str = map.get(field.getName()).toString();
					str = str.trim();
					if(StringUtils.isNotEmpty(str)){
						map.put(field.getName(), new BigDecimal(str));
					}
				}
			}
		}
	}
	
	/**
	 * 打印日志
	 * @param key
	 * @param logStr
	 * @param userid 用户编号，可以为空，使用情况SysUser >> userid >> 空
	 * @author zhanghonghui 
	 * @date 2014-1-7
	 */
	public void addPrintLogInfo(String key,String logStr,String userid){
		try{
			ISysLogService sysLogService=(ISysLogService) SpringContextUtils.getBean("sysLogService");
			SysLog sysLog = new SysLog();
			
			//获取当前登录的用户
			SysUser sysUser = getSysUser();
			if(null!=sysUser){
	        	sysLog.setUserid(sysUser.getUserid());
	        	sysLog.setName(sysUser.getName());
			}else if (null!=userid && !"".equals(userid)){
				sysLog.setUserid(userid);
			}
			
        	sysLog.setKeyname(key);
        	sysLog.setContent(logStr);
        	sysLog.setType("5");
        	sysLog.setIp(CommonUtils.getIP(request));
        	sysLogService.addSysLog(sysLog);
			
		}catch (Exception e) {
		}
	}
	
	/**
	 * 获取商品价格套列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 10, 2014
	 */
	public List getGoodsPriceList()throws Exception{
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		return priceList;
	}
	
	/**
	 * 验证价格套是否存在该价格套名称
	 * @param str
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 26, 2014
	 */
	public boolean isExistPriceCodeName(String str)throws Exception{
		List<SysCode> priceList = getGoodsPriceList();
		for(SysCode sysCode : priceList){
			if(str.equals(sysCode.getCodename())){
				return true;
			}
		}
		return false;
	}

    /**
     * 往手机发送信息
     * @param userid            用户编号
     * @param type              1 发送公告短信等内容包含url地址 2上传定位 3 强制启用程序
     * @param title             标题
     * @param msg               内容
     * @param url               url地址
     * @return
     * @throws Exception
     */
    protected boolean sendPhoneMsg(String userid,String type,String title,String msg,String url) throws Exception{
        return baseMobileSmsService.sendPhoneMsg(userid,type,title,msg,url);
    }


	/**
	 * 获取token，默认为sessionid
	 * @return
	 */
	public String getToken() {

		return request.getSession().getId();
	}

	/**
	 * 向系统日志里添加日志
	 * @param logStr
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Mar 19, 2017
	 */
	public void addSysLogInfo(String logStr,String username){
		ISysLogService sysLogService=(ISysLogService) SpringContextUtils.getBean("sysLogService");
		try{
			SysLog sysLog = new SysLog();


			sysLog.setUserid("systemuserid");
			if(StringUtils.isNotEmpty(username)){
				sysLog.setName(username);
			}else{
				sysLog.setName("系统");
			}


			sysLog.setContent(logStr);
			sysLog.setType("0");
			sysLog.setIp("127.0.0.1");
			sysLogService.addSysLog(sysLog);

		}catch (Exception ex) {
		}
	}
	/**
	 * 创建错误文件，用户提醒用户导入文件错误信息
	 *
	 * @param titleMap
	 * @param errorList
	 * @param request
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Dec 25, 2016
	 */
	public String createErrorFile(Map<String, Object> titleMap, List<Map<String, Object>> errorList, HttpServletRequest request) throws Exception {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result.add(titleMap);

		for (Map error : errorList) {
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map2 = error;
			for (Map.Entry<String, Object> fentry : titleMap.entrySet()) {
				if (map2.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
					for (Map.Entry<String, Object> entry : map2.entrySet()) {
						if (fentry.getKey().equals(entry.getKey())) {
							objectCastToRetMap(retMap, entry);
						}
					}
				} else {
					retMap.put(fentry.getKey(), "");
				}
			}
			result.add(retMap);
		}

		return ExcelUtils.createErrorExcelFile(result, "错误-" + getSysUser().getUserid() + "-" + CommonUtils.getDataNumberSendsWithRand());

	}

	/**
	 * 获取当前操作日期
	 * @return
	 * @throws Exception
	 */
	public String getCurrentDate() throws Exception{
		String currentDate = null;
		String IsOpenBusDate = getSysParamValue("IsOpenBusDate");
		if(!(StringUtils.isNotEmpty(IsOpenBusDate)  && "1".equals(IsOpenBusDate))){
			currentDate = CommonUtils.getTodayDateStr();
		}else{
			currentDate = (String) request.getSession().getAttribute(Current_Date);
//			//判断登陆是否取当前日期
//			String currentType = (String) request.getSession().getAttribute(Busdate_type);
//			if(Current_Type_Now.equals(currentType)){
//				if(null!=currentDate && !currentDate.equals(CommonUtils.getTodayDateStr())){
//					request.getSession().setAttribute(Current_Date,CommonUtils.getTodayDateStr());
//				}
//				currentDate = CommonUtils.getTodayDateStr();
//			}
		}
		if(StringUtils.isEmpty(currentDate)){
			currentDate = CommonUtils.getTodayDateStr();
		}
		return currentDate;
	}

}

