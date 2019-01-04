/**
 * @(#)BaseServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-24 chenwei 创建版本
 */
package com.hd.agent.common.service.impl;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.hd.agent.accesscontrol.base.AccessControlMetadataSource;
import com.hd.agent.accesscontrol.dao.AccessControlMapper;
import com.hd.agent.accesscontrol.dao.DataruleMapper;
import com.hd.agent.accesscontrol.dao.SysUserMapper;
import com.hd.agent.accesscontrol.model.AccessColumn;
import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.accesscontrol.model.Datarule;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.*;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.*;
import com.hd.agent.message.model.EmailContent;
import com.hd.agent.message.model.MobileSms;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.service.IEmailService;
import com.hd.agent.message.service.IInnerMessageService;
import com.hd.agent.message.service.IMobileSmsService;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.storage.dao.PurchaseEnterMapper;
import com.hd.agent.storage.dao.SaleRejectEnterMapper;
import com.hd.agent.storage.dao.SaleoutMapper;
import com.hd.agent.system.dao.*;
import com.hd.agent.system.model.*;
import com.hd.agent.system.service.ISysNumberService;
import com.hd.agent.system.service.ITaskScheduleService;
import com.hd.agent.system.util.SysNumberUtils;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * serivce公共父类,子类基础该类后，可以调用公共方法
 * @author chenwei
 */
public class BaseServiceImpl {
	private static final Logger logger = Logger.getLogger(BaseServiceImpl.class);
	/**
	 * 金额小数位
	 */
	protected static int decimalLen=2;
	/**
	 * 数据权限相关操作
	 */
	private DataruleMapper baseDataruleMapper;
	/**
	 * 功能权限与字段权限
	 */
	private AccessControlMapper baseAccessControlMapper;
	/**
	 * 表字段dao
	 */
	private TableColumnMapper baseTableColumnMapper;
	/**
	 * 表关联数据操作
	 */
	private TableRelationMapper baseTableRelationMapper;
	/**
	 * 用户表dao
	 */
	private SysUserMapper baseSysUserMapper;
	/**
	 * 编码dao
	 */
	private SysCodeMapper baseSysCodeMapper;
	/**
	 * 网络互斥dao
	 */
	private NetLockMapper baseNetLockMapper;
	/**
	 * 系统参数dao
	 */
	private SysParamMapper baseSysParamMapper;
	
	/**
	 * 部门档案
	 */
	private DepartMentMapper baseDepartMentMapper;
	
	/**
	 * 商品档案
	 */
	private GoodsMapper baseGoodsMapper;
	
	/**
	 * 财务管理档案
	 */
	private FinanceMapper baseFinanceMapper;
	
	/**
	 * 仓库
	 */
	private StorageMapper baseStorageMapper;

	/**
	 * 仓库货位
	 */
	private StorageItemMapper storageItemMapper;
	
	/**
	 * 客户档案
	 */
	private CustomerMapper baseCustomerMapper;
	/**
	 * 销售区域档案
	 */
	private SalesAreaMapper baseSalesAreaMapper;
	/**
	 * 供应商档案
	 */
	private BuySupplierMapper baseBuySupplierMapper;
	
	/**
	 * 人员档案
	 */
	private PersonnelMapper basePersonnelMapper;
	
	/**
	 * 联系人档案
	 */
	private ContacterMapper baseContacterMapper;
	
	/**
	 * 采购区域
	 */
	private BuyAreaMapper baseBuyAreaMapper;
	
	/**
	 * 采购分类
	 */
	private BuySupplierSortMapper baseBuySupplierSortMapper;
	
	/**
	 * 客户分类
	 */
	private CustomerSortMapper baseCustomerSortMapper;
	
	/**
	 * 对应客户分类
	 */
	private CustomerAndSortMapper baseCustomerAndSortMapper;

	private SysNumberMapper baseSysNumberMapper;
	
	/**
	 * 销售出库单dao
	 */
	private SaleoutMapper baseSaleoutMapper;
	
	/**
	 * 销售退货入库单dao
	 */
	private SaleRejectEnterMapper baseSaleRejectEnterMapper;
	
	/**
	 * 物流档案dao
	 */
	private LogisticsMapper baseLogisticsMapper;
	
	private WorkJobMapper baseWorkJobMapper;
	
	/**
	 * 采购入库单dao
	 */
	private PurchaseEnterMapper basePurchaseEnterMapper;
	/**
	 * 客户档案
	 */
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public CustomerAndSortMapper getBaseCustomerAndSortMapper() {
		return baseCustomerAndSortMapper;
	}

	public void setBaseCustomerAndSortMapper(
			CustomerAndSortMapper baseCustomerAndSortMapper) {
		this.baseCustomerAndSortMapper = baseCustomerAndSortMapper;
	}

	public SaleRejectEnterMapper getBaseSaleRejectEnterMapper() {
		return baseSaleRejectEnterMapper;
	}

	public void setBaseSaleRejectEnterMapper(
			SaleRejectEnterMapper baseSaleRejectEnterMapper) {
		this.baseSaleRejectEnterMapper = baseSaleRejectEnterMapper;
	}

	public SaleoutMapper getBaseSaleoutMapper() {
		return baseSaleoutMapper;
	}

	public void setBaseSaleoutMapper(SaleoutMapper baseSaleoutMapper) {
		this.baseSaleoutMapper = baseSaleoutMapper;
	}

	public PersonnelMapper getBasePersonnelMapper() {
		return basePersonnelMapper;
	}

	public void setBasePersonnelMapper(PersonnelMapper basePersonnelMapper) {
		this.basePersonnelMapper = basePersonnelMapper;
	}

	public CustomerMapper getBaseCustomerMapper() {
		return baseCustomerMapper;
	}

	public void setBaseCustomerMapper(CustomerMapper baseCustomerMapper) {
		this.baseCustomerMapper = baseCustomerMapper;
	}

	public GoodsMapper getBaseGoodsMapper() {
		return baseGoodsMapper;
	}

	public void setBaseGoodsMapper(GoodsMapper baseGoodsMapper) {
		this.baseGoodsMapper = baseGoodsMapper;
	}

	public DepartMentMapper getBaseDepartMentMapper() {
		return baseDepartMentMapper;
	}

	public void setBaseDepartMentMapper(DepartMentMapper baseDepartMentMapper) {
		this.baseDepartMentMapper = baseDepartMentMapper;
	}

	public DataruleMapper getBaseDataruleMapper() {
		return baseDataruleMapper;
	}

	public void setBaseDataruleMapper(DataruleMapper baseDataruleMapper) {
		this.baseDataruleMapper = baseDataruleMapper;
	}
	
	public AccessControlMapper getBaseAccessControlMapper() {
		return baseAccessControlMapper;
	}

	public void setBaseAccessControlMapper(
			AccessControlMapper baseAccessControlMapper) {
		this.baseAccessControlMapper = baseAccessControlMapper;
	}
	
	public TableRelationMapper getBaseTableRelationMapper() {
		return baseTableRelationMapper;
	}

	public void setBaseTableRelationMapper(
			TableRelationMapper baseTableRelationMapper) {
		this.baseTableRelationMapper = baseTableRelationMapper;
	}

	public SysUserMapper getBaseSysUserMapper() {
		return baseSysUserMapper;
	}

	public void setBaseSysUserMapper(SysUserMapper baseSysUserMapper) {
		this.baseSysUserMapper = baseSysUserMapper;
	}
	
	public SysCodeMapper getBaseSysCodeMapper() {
		return baseSysCodeMapper;
	}

	public void setBaseSysCodeMapper(SysCodeMapper baseSysCodeMapper) {
		this.baseSysCodeMapper = baseSysCodeMapper;
	}
	
	public NetLockMapper getBaseNetLockMapper() {
		return baseNetLockMapper;
	}

	public void setBaseNetLockMapper(NetLockMapper baseNetLockMapper) {
		this.baseNetLockMapper = baseNetLockMapper;
	}
	
	public TableColumnMapper getBaseTableColumnMapper() {
		return baseTableColumnMapper;
	}

	public void setBaseTableColumnMapper(TableColumnMapper baseTableColumnMapper) {
		this.baseTableColumnMapper = baseTableColumnMapper;
	}

	public SysParamMapper getBaseSysParamMapper() {
		return baseSysParamMapper;
	}

	public void setBaseSysParamMapper(SysParamMapper baseSysParamMapper) {
		this.baseSysParamMapper = baseSysParamMapper;
	}
	
	public int getDecimalLen() {
		return decimalLen;
	}

	public void setDecimalLen(int decimalLen) {
		this.decimalLen = decimalLen;
	}
	
	public SysNumberMapper getBaseSysNumberMapper() {
		return baseSysNumberMapper;
	}

	public void setBaseSysNumberMapper(SysNumberMapper baseSysNumberMapper) {
		this.baseSysNumberMapper = baseSysNumberMapper;
	}

	public FinanceMapper getBaseFinanceMapper() {
		return baseFinanceMapper;
	}

	public void setBaseFinanceMapper(FinanceMapper baseFinanceMapper) {
		this.baseFinanceMapper = baseFinanceMapper;
	}

	public StorageMapper getBaseStorageMapper() {
		return baseStorageMapper;
	}

	public void setBaseStorageMapper(StorageMapper baseStorageMapper) {
		this.baseStorageMapper = baseStorageMapper;
	}

	public BuySupplierMapper getBaseBuySupplierMapper() {
		return baseBuySupplierMapper;
	}

	public void setBaseBuySupplierMapper(BuySupplierMapper baseBuySupplierMapper) {
		this.baseBuySupplierMapper = baseBuySupplierMapper;
	}

	public ContacterMapper getBaseContacterMapper() {
		return baseContacterMapper;
	}

	public void setBaseContacterMapper(ContacterMapper baseContacterMapper) {
		this.baseContacterMapper = baseContacterMapper;
	}

	public BuyAreaMapper getBaseBuyAreaMapper() {
		return baseBuyAreaMapper;
	}

	public void setBaseBuyAreaMapper(BuyAreaMapper baseBuyAreaMapper) {
		this.baseBuyAreaMapper = baseBuyAreaMapper;
	}

	public CustomerSortMapper getBaseCustomerSortMapper() {
		return baseCustomerSortMapper;
	}

	public void setBaseCustomerSortMapper(CustomerSortMapper baseCustomerSortMapper) {
		this.baseCustomerSortMapper = baseCustomerSortMapper;
	}

	public SalesAreaMapper getBaseSalesAreaMapper() {
		return baseSalesAreaMapper;
	}

	public void setBaseSalesAreaMapper(SalesAreaMapper baseSalesAreaMapper) {
		this.baseSalesAreaMapper = baseSalesAreaMapper;
	}
	
	
	public PurchaseEnterMapper getBasePurchaseEnterMapper() {
		return basePurchaseEnterMapper;
	}

	public void setBasePurchaseEnterMapper(PurchaseEnterMapper basePurchaseEnterMapper) {
		this.basePurchaseEnterMapper = basePurchaseEnterMapper;
	}

	public LogisticsMapper getBaseLogisticsMapper() {
		return baseLogisticsMapper;
	}

	public void setBaseLogisticsMapper(LogisticsMapper baseLogisticsMapper) {
		this.baseLogisticsMapper = baseLogisticsMapper;
	}

	public BuySupplierSortMapper getBaseBuySupplierSortMapper() {
		return baseBuySupplierSortMapper;
	}

	public void setBaseBuySupplierSortMapper(
			BuySupplierSortMapper baseBuySupplierSortMapper) {
		this.baseBuySupplierSortMapper = baseBuySupplierSortMapper;
	}

	public WorkJobMapper getBaseWorkJobMapper() {
		return baseWorkJobMapper;
	}

	public void setBaseWorkJobMapper(WorkJobMapper baseWorkJobMapper) {
		this.baseWorkJobMapper = baseWorkJobMapper;
	}

	public StorageItemMapper getStorageItemMapper() {
		return storageItemMapper;
	}

	public void setStorageItemMapper(StorageItemMapper storageItemMapper) {
		this.storageItemMapper = storageItemMapper;
	}

	/**
	 * 返回状态和信息
	 * @param flag 是否操作成功
	 * @param msg 返回信息
	 * @return
	 */
	public Map returnMap(boolean flag, String msg) {
		Map result = new HashMap();
		result.put("flag", flag);
		result.put("msg", msg);
		return result;
	}

	/**
	 * 根据表名获取当前用户访问该资源的数据权限
	 * @param tablename 表名
	 * @param alias 表的别名
	 * @return sql查询条件
	 * @author chenwei 
	 * @date 2012-12-24
	 */
	public String getDataAccessRule(String tablename,String alias) throws Exception{
        String username = "";
		//判断表名后缀，如果是历史表（_h后缀）取正式表的字段
		if(null!=tablename&&tablename.length()>2){
			String suffix = tablename.substring(tablename.length()-2, tablename.length());
			if("_h".equals(suffix)||"_v".equals(suffix)){
				tablename = tablename.substring(0,tablename.length()-2);
			}
		}
		//获取当前用户登录的用户名以及权限
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(null != userDetails){
            username = userDetails.getUsername();
        }
        String username1 = username;
        if(username.startsWith("P_")){
            String[] strArr = username.split("P_");
            username1 = strArr[1];
        }
		//根据用户名获取用户详细信息
		SysUser sysUser = baseSysUserMapper.getUser(username1);
		if(username.startsWith("P_")){
			sysUser.setLoginType("2");
		}else{
			sysUser.setLoginType("1");
		}
		Collection<GrantedAuthority> collection = userDetails.getAuthorities();
		List authorityList = new ArrayList();
		for(GrantedAuthority configAttribute : collection){
			String authorityid = configAttribute.getAuthority().trim();
			authorityList.add(authorityid);
		}
		String sql = null;
        if(null != sysUser){
            Datarule datarule = baseDataruleMapper.getDataruleByUserid(tablename, sysUser.getUserid());
            if(null==datarule){
                datarule = baseDataruleMapper.getDatarule(tablename);
            }
            Map map = new HashMap();
            map.put("alias", alias);
            map.put("sysUser", sysUser);
            map.put("CurrentRoleID", authorityList);
            if(null!=datarule && StringUtils.isNotEmpty(datarule.getRule())){
                sql = RuleJSONUtils.dataRuleToSQLString(datarule.getRule(),map);
            }
        }
		return sql;
	}
	
	/**
	 * 根据表名获取用户访问该资源的数据权限
	 * @param tablename 表名
	 * @param alias 表的别名
	 * @param userid 用户编号
	 * @return sql查询条件
	 * @author chenwei 
	 * @date 2012-12-24
	 */
	public String getDataAccessRule(String tablename,String alias,String userid) throws Exception{
		//判断表名后缀，如果是历史表（_h后缀）取正式表的字段
		if(null!=tablename&&tablename.length()>2){
			String suffix = tablename.substring(tablename.length()-2, tablename.length());
			if("_h".equals(suffix)||"_v".equals(suffix)){
				tablename = tablename.substring(0,tablename.length()-2);
			}
		}
		SysUser sysUser = baseSysUserMapper.getUserById(userid);
		List authorityList = baseSysUserMapper.getUserAuthorityList(sysUser.getUsername());
		String sql = null;
		Datarule datarule = baseDataruleMapper.getDatarule(tablename);
		Map map = new HashMap();
		map.put("alias", alias);
		map.put("sysUser", sysUser);
		map.put("CurrentRoleID", authorityList);
		if(null!=datarule){
			sql = RuleJSONUtils.dataRuleToSQLString(datarule.getRule(),map);
		}
		return sql;
	}
	/**
	 * 根据表名获取当前用户访问该表的字段权限
	 * @param tablename 表名
	 * @param alias 表的别名
	 * @return String
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-4
	 */
	public String getAccessColumnList(String tablename,String alias) throws Exception{
		//获取当前用户拥有的角色（权限）
		String columns = null;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> collection = userDetails.getAuthorities();
		List authorityList = new ArrayList();
		for(GrantedAuthority configAttribute : collection){
			String authorityid = configAttribute.getAuthority().trim();
			authorityList.add(authorityid);
		}
		//根据条件获取字段权限列表
		Map map = new HashMap();
		map.put("authorityList", authorityList);
		//判断表名后缀，如果是历史表（_h后缀）取正式表的字段
		if(null!=tablename&&tablename.length()>2){
			String suffix = tablename.substring(tablename.length()-2, tablename.length());
			if("_h".equals(suffix)||"_v".equals(suffix)){
				tablename = tablename.substring(0,tablename.length()-2);
			}
		}
		map.put("tablename", tablename);
		List<AccessColumn> list = baseAccessControlMapper.showAccessColumnsByAuthoritys(map);
		//根据获取的字段权限列表组装成字段列表
		Map columnMap = new HashMap();
		for(AccessColumn accessColumn : list){
			String collist = accessColumn.getCollist();
			//防止sql注入
			collist = StringEscapeUtils.escapeSql(collist);
			String[] colArray = collist.split(",");
			for(String col : colArray){
				columnMap.put(col, col);
			}
			String editcollist = accessColumn.getEditcollist();
			//防止sql注入
			editcollist = StringEscapeUtils.escapeSql(editcollist);
			String[] editcolArray = editcollist.split(",");
			for(String col : editcolArray){
				columnMap.put(col, col);
			}
			
		}
		String cols = null;
		if(columnMap.size()>0){
			cols = "";
			//判断是否有表的别名
			if(null!=alias&&!"".equals(alias)){
				alias = alias+".";
			}else{
				alias = "";
			}
			Iterator it = columnMap.entrySet().iterator(); 
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				cols += ","+alias+entry.getKey().toString();
			}
		}
		return cols;
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
		//根据条件获取字段权限列表
		Map map = new HashMap();
		map.put("authorityList", authorityList);
		//判断表名后缀，如果是历史表（_h后缀）取正式表的字段
		if(null!=tablename&&tablename.length()>2){
			String suffix = tablename.substring(tablename.length()-2, tablename.length());
			if("_h".equals(suffix)||"_v".equals(suffix)){
				tablename = tablename.substring(0,tablename.length()-2);
			}
		}
		map.put("tablename", tablename);
		List<AccessColumn> accessColumnList = baseAccessControlMapper.showAccessColumnsByAuthoritys(map);
		Map columnMap = new HashMap();
		for(AccessColumn accessColumn : accessColumnList){
			String collist = accessColumn.getCollist();
			String[] colArray = collist.split(",");
			String editColList = accessColumn.getEditcollist();
			String[] editColArray = editColList.split(",");
			for(String col : colArray){
				columnMap.put(col, col);
			}
			for(String col : editColArray){
				columnMap.put(col, col);
			}
		}
		if(map.size()==0){
			Map queryMap = new HashMap();
			queryMap.put("tablename", tablename);
			List<TableColumn> list = baseTableColumnMapper.getTableColumnListBy(queryMap);
			for(TableColumn tableColumn : list){
				columnMap.put(tableColumn.getColumnname(),tableColumn.getColumnname());
			}
		}
		return columnMap;
	}
	/**
	 * 根据表名获取当前用户访问该表的字段权限(字符串)
	 * @param tablename 表名
	 * @param alias 表的别名
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-4
	 */
	public Map getAccessColumnMap(String tablename,String alias) throws Exception{
		//获取当前用户拥有的角色（权限）
		String columns = null;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> collection = userDetails.getAuthorities();
		List authorityList = new ArrayList();
		for(GrantedAuthority configAttribute : collection){
			String authorityid = configAttribute.getAuthority().trim();
			authorityList.add(authorityid);
		}
		//根据条件获取字段权限列表
		Map map = new HashMap();
		map.put("authorityList", authorityList);
		//判断表名后缀，如果是历史表（_h后缀）取正式表的字段
		if(null!=tablename&&tablename.length()>2){
			String suffix = tablename.substring(tablename.length()-2, tablename.length());
			if("_h".equals(suffix)||"_v".equals(suffix)){
				tablename = tablename.substring(0,tablename.length()-2);
			}
		}
		map.put("tablename", tablename);
		List<AccessColumn> list = baseAccessControlMapper.showAccessColumnsByAuthoritys(map);
		//根据获取的字段权限列表组装成字段列表
		Map columnMap = new HashMap();
		for(AccessColumn accessColumn : list){
			String collist = accessColumn.getCollist();
			//防止sql注入
			collist = StringEscapeUtils.escapeSql(collist);
			String[] colArray = collist.split(",");
			for(String col : colArray){
				columnMap.put(col, col);
			}
			String editcollist = accessColumn.getEditcollist();
			//防止sql注入
			editcollist = StringEscapeUtils.escapeSql(editcollist);
			String[] editcolArray = editcollist.split(",");
			for(String col : editcolArray){
				columnMap.put(col, col);
			}
		}
		String cols = null;
		if(columnMap.size()>0){
			cols = "";
			//判断是否有表的别名
			if(null!=alias&&!"".equals(alias)){
				alias = alias+".";
			}else{
				alias = "";
			}
			Iterator it = columnMap.entrySet().iterator(); 
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				cols += ","+alias+entry.getKey().toString();
			}
		}
		Map colmap = new HashMap();
		colmap.put("cols", cols);
		return colmap;
	}
	/**
	 * 根据多个表名获取受字段权限控制后的字段，
	 * 并且根据条件生成不同的表别名以及字段别名
	 * @param tableMap 表名（key=表名,value=表别名）
	 * @param columnMap 列名集合(key:表名.列名，value:列别名);
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-4
	 */
	public String getAccessColumnListByTables(Map tableMap,Map columnMap) throws Exception{
		//获取当前用户拥有的角色（权限）
		String columns = null;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> collection = userDetails.getAuthorities();
		List authorityList = new ArrayList();
		for(GrantedAuthority configAttribute : collection){
			String authorityid = configAttribute.getAuthority().trim();
			authorityList.add(authorityid);
		}
		Iterator it = tableMap.entrySet().iterator(); 
		String cols = null;
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			//表名
			String tablename = entry.getKey().toString();
			//表别名
			String tableAlias = entry.getValue().toString();
			//根据条件获取字段权限列表
			Map map = new HashMap();
			map.put("authorityList", authorityList);
			//判断表名后缀，如果是历史表（_h后缀）取正式表的字段
			if(null!=tablename&&tablename.length()>2){
				String suffix = tablename.substring(tablename.length()-2, tablename.length());
				if("_h".equals(suffix)||"_v".equals(suffix)){
					tablename = tablename.substring(0,tablename.length()-2);
				}
			}
			map.put("tablename", tablename);
			List<AccessColumn> list = baseAccessControlMapper.showAccessColumnsByAuthoritys(map);
			//根据获取的字段权限列表组装成字段列表
			Map resultMap = new HashMap();
			for(AccessColumn accessColumn : list){
				String collist = accessColumn.getCollist();
				//防止sql注入
				collist = StringEscapeUtils.escapeSql(collist);
				String[] colArray = collist.split(",");
				for(String col : colArray){
					resultMap.put(col, col);
				}
				String editcollist = accessColumn.getEditcollist();
				//防止sql注入
				editcollist = StringEscapeUtils.escapeSql(editcollist);
				String[] editcolArray = editcollist.split(",");
				for(String col : editcolArray){
					resultMap.put(col, col);
				}
			}
			if(resultMap.size()>0){
				cols = "";
				//判断是否有表的别名
				if(null!=tableAlias&&!"".equals(tableAlias)){
					tableAlias =tableAlias+".";
				}else{
					tableAlias = "";
				}
				Iterator resultIt = resultMap.entrySet().iterator(); 
				while (resultIt.hasNext()) {
					Map.Entry resultEntry = (Map.Entry) resultIt.next();
					String alias = null;
					if(null!=columnMap){
						alias = (String) columnMap.get(tablename+"."+resultEntry.getKey().toString());
					}
					if(null!=alias&&!"".equals(alias)){
						cols += ","+tableAlias+resultEntry.getKey().toString()+" as "+alias;
					}else{
						cols += ","+tableAlias+resultEntry.getKey().toString();
					}
					
				}
			}
		}
		return cols;
	}
	/**
	 * 根据表名获取表字段列表
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 30, 2013
	 */
	public List getTableColumn(String tablename) throws Exception{
		Map map = new HashMap();
		map.put("tablename", tablename);
		List list = baseTableColumnMapper.getTableColumnListBy(map);
		return list;
	}
	/**
	 * 根据主表表名和主表字段名，查询所有从属表中数据是否要删除确认<br/>
	 * 如果传的主表名、主字段名、字段数据其中一个为，则返回true<br/>
	 * 返回结果：<br/>
	 * true：可以删除<br/>
	 * false：不可以删除<br/>
	 * @param tablename 主表名
	 * @param object 表的对象数据
	 * @return 
	 * @author zhanghonghui 
	 * @date 2013-1-7
	 */
	public boolean canTableDataDictDelete(String tablename,Object object) throws Exception{
		//对象转成map
		Map dataMap = null;
		String id = null;
		if (object  instanceof String) {
			id = (String) object;
		}else{
			dataMap = BeanUtils.describe(object);
		}
		Map queryMap=new HashMap();
		queryMap.put("maintablename", tablename);
		List<TableRelation> list=baseTableRelationMapper.getTableRelationListBy(queryMap);
		boolean flag = true;
		for(TableRelation tableRelation : list){
			queryMap.put("tablename", tableRelation.getTablename());
			if(null == dataMap){
				queryMap.put("columnname", tableRelation.getColumnname());
				queryMap.put("coldata", id);
			}else{
				queryMap.put("columnname", tableRelation.getColumnname());
				queryMap.put("coldata", dataMap.get(tableRelation.getMaincolumnname()));
			}
			if(baseTableRelationMapper.getTableDataDictCount(queryMap)>0){
				flag = false;
				break;
			}
		}
		return flag;
	}
	/**
	 * 获取主表中级联关系字段是否可以修改<br/>
	 * 返回Map key:字段名 value:true能修改 false不能修改
	 * @param tablename	表名
	 * @param object 数据对象
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-21
	 */
	public Map canTableDataDictEdit(String tablename,Object object) throws Exception {
		//对象转成map
		Map dataMap = BeanUtils.describe(object);
		
		Map queryMap=new HashMap();
		queryMap.put("maintablename", tablename);
		//级联替换为否（0）不能修改
		queryMap.put("cascadechange", "0");
		List<TableRelation> list=baseTableRelationMapper.getTableRelationListBy(queryMap);
		Map map = new HashMap();
		for(TableRelation tableRelation : list){
			//判断主表字段是否已经不可以修改
			Boolean flag = (Boolean) map.get(tableRelation.getMaincolumnname());
			if(null!=flag&&flag==false){
				break;
			}else{
				queryMap.put("tablename", tableRelation.getTablename());
				queryMap.put("columnname", tableRelation.getColumnname());
				queryMap.put("coldata", dataMap.get(tableRelation.getMaincolumnname()));
				if(baseTableRelationMapper.getTableDataDictCount(queryMap)>0){
					map.put(tableRelation.getMaincolumnname(), false);
				}else{
					map.put(tableRelation.getMaincolumnname(), true);
				}
			}
		}
		return map;
	}
	/**
	 * 根据编码类型获取该类型下的编码列表
	 * @param codetype
	 * @return Map(key:代码,value：代码名称)
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public Map getCodeListByType(String codetype) throws Exception{
		Map map = new HashMap();
		List<SysCode> list = baseSysCodeMapper.getSysCodeListForeign(codetype);
		for(SysCode sysCode : list){
			map.put(sysCode.getCode(), sysCode.getCodename());
		}
		return map;
	}

    /**
     * 根据编码类型获取该类型下的编码列表
     * @param codetype
     * @return
     * @throws Exception
     */
    public List<SysCode> getCodeByType(String codetype) throws Exception{
        List<SysCode> list = baseSysCodeMapper.getSysCodeListForeign(codetype);
        return list;
    }
	/**
	 * 根据代码类型(type)调用缓存过数据
	 * @param codetype
	 * @return java.util.List<com.hd.agent.system.model.SysCode>
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 28, 2016
	 */
	public List<SysCode> getCodeListCacheByType(String codetype) throws Exception{
		List<SysCode> list = baseSysCodeMapper.getCodeListCacheByType(codetype);
		return list;
	}
    /**
     * 根据编码 和 类型获取编码信息
     * @param code
     * @param type
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2016年2月19日
     */
    public SysCode getBaseSysCodeInfo(String code,String type) throws Exception{
    	SysCode sysCode=baseSysCodeMapper.getSysCodeInfo(code, type);
    	return sysCode;
    }

	/**
	 * 根据编码和类型获取启用状态下编码
	 * @param code
	 * @param type
	 * @return com.hd.agent.system.model.SysCode
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 28, 2016
	 */
	public SysCode getBaseEnableSysCodeInfo(String code,String type) throws Exception{
		SysCode sysCode=baseSysCodeMapper.getEnableSysCodeInfo(code,type);
		return sysCode;
	}
	/**
	 * 获取当前登陆的账套名称
	 * @return 名称
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-19
	 */
	public String getCurrentSetOfBookDbName() throws Exception{
		List<SysCode> list=baseSysCodeMapper.getSysCodeListForeign("currentSetOfBook");
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
	 * 获取当前登录的用户
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 27, 2013
	 */
	public SysUser getSysUser() throws Exception{
		//获取security中的用户名
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(null!=authentication){
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = userDetails.getUsername();
			String username1 = username;
			if(username.startsWith("P_")){
				String[] strArr = username.split("P_");
				username1 = strArr[1];
			}
			//根据用户名获取用户详细信息
			SysUser sysUser = baseSysUserMapper.getUser(username1);
			if(username.startsWith("P_")){
				sysUser.setLoginType("2");
			}
			return sysUser;
		}else{
			return null;
		}
	}
	/**
	 * 获取用户拥有的角色列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 11, 2014
	 */
	public List getSysUserRoleList() throws Exception{
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> collection = userDetails.getAuthorities();
		List authorityList = new ArrayList();
		for(GrantedAuthority configAttribute : collection){
			String authorityid = configAttribute.getAuthority().trim();
			authorityList.add(authorityid);
		}
		return authorityList;
	}
	/**
	 * 根据用户编号获取系统用户信息
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 11, 2013
	 */
	public SysUser getSysUserById(String userid) throws Exception{
		return baseSysUserMapper.getUserById(userid);
	}

	/**
	 * 根据系统用户编号获取人员编号
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public String getPersonIdBySysuserid(String userid) throws Exception{
		SysUser sysUser = getSysUserById(userid);
		if(null!=sysUser){
			return sysUser.getPersonnelid();
		}else{
			return "";
		}
	}
	/**
	 * 通过用户账号获取系统用户信息
	 * @param userName
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Aug 25, 2013
	 */
	public SysUser getSysUserByUserName(String userName) throws Exception{
		return baseSysUserMapper.getUserByUsername(userName);
	}
	
	/**
	 * 根据角色名称获取该角色下的用户列表
	 * @param rolename
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 11, 2013
	 */
	public List getSysUserListByRole(String rolename) throws Exception{
		List list = baseSysUserMapper.getSysUserListByRole(rolename);
		return list;
	}
	/**
	 * 判断当前用户是否拥有该角色
	 * @param rolename			角色名称
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 11, 2014
	 */
	public boolean isSysUserHaveRole(String rolename) throws Exception{
		SysUser sysUser = getSysUser();
		boolean flag = false;
		List<Authority> list = baseSysUserMapper.getAuthorityListByUserid(sysUser.getUserid());
		for(Authority authority : list){
			if(rolename.indexOf(authority.getAuthorityname())>=0){
				flag = true;
				break;
			}
		}
		return flag;
	}
	/**
	 * 判断用户是否拥有该角色
	 * @param rolename
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 25, 2014
	 */
	public boolean isSysUserHaveRole(String rolename,String userid) throws Exception{
		List<SysUser> list = baseSysUserMapper.getSysUserListByRole(rolename);
		SysUser sysUser = getSysUserById(userid);
		boolean flag = false;
		for(SysUser user : list){
			if(user.getUserid().equals(sysUser.getUserid())){
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
	 * 根据角色编号获取该角色下的用户列表
	 * @param roleid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public List getSysUserListByRoleid(String roleid) throws Exception{
		List list = baseSysUserMapper.getSysUserListByRoleid(roleid);
		return list;
	}
	/**
	 * 根据部门编号获取该部门下的用户人员列表
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 11, 2013
	 */
	public List getSysUserListByDept(String deptid) throws Exception{
		List list = baseSysUserMapper.getSysUserListByDept(deptid);
		return list;
	}
	/**
	 * 根据角色名称和部门编号，获取两个的交集
	 * @param rolename
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 11, 2013
	 */
	public List getSysUserListByRoleAndDept(String rolename,String deptid) throws Exception{
		List list = baseSysUserMapper.getSysUserListByRoleAndDept(rolename, deptid);
		return list;
	}
	/**
	 * 根据角色编号和部门编号，获取用户列表
	 * @param roleid
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 6, 2013
	 */
	public List getSysUserListByRoleidAndDeptid(String roleid,String deptid) throws Exception{
		List list = baseSysUserMapper.getSysUserListByRoleidAndDeptid(roleid, deptid);
		return list;
	}
	/**
	 * 根据工作岗位获取人员列表
	 * @param workjobname
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 6, 2013
	 */
	public List getSysUserListByWorkjob(String workjobname) throws Exception{
		List list = baseSysUserMapper.getSysUserListByWorkjob(workjobname);
		return list;
	}
	/**
	 * 根据工作岗位编号获取人员列表
	 * @param workjobid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 6, 2013
	 */
	public List getSysUserListByWorkjobid(String workjobid) throws Exception{
		List list = baseSysUserMapper.getSysUserListByWorkjobid(workjobid);
		return list;
	}
	
	/**
	 * 根据关联人员编码获取用户列表数据
	 * @param personnelid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 28, 2013
	 */
	public List getSysUserListByPersonnelid(String personnelid)throws Exception{
		List list = baseSysUserMapper.getSysUserListByPersonnelid(personnelid);
		return list;
	}
	/**
	 * 根据条件获取用户列表.
	 * userList:用户编号列表（List对象或String数组）存放在PageMap中的condition里<br/>
	 * 返回的PageData根据isPage条件，PageData中的list会生成是否分页的数据。不分页则包含全部数据
	 * @param pageMap
	 * @param isPage
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 20, 2013
	 */
	public PageData getSysUserListByConditon(PageMap pageMap,boolean isPage) throws Exception{
		pageMap.getCondition().put("isPage", String.valueOf(isPage));
		//单表取字段权限
		String cols = getAccessColumnList("t_sys_user",null);
		pageMap.setCols(cols);

		//数据权限
		String sql = getDataAccessRule("t_sys_user",null);
		pageMap.setDataSql(sql);

		PageData pageData = new PageData(baseSysUserMapper.showSysUserCount(pageMap), baseSysUserMapper.showSysUserList(pageMap), pageMap);
		return pageData;
	}
	/**
	 * 启用状态下用户列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-6
	 */
	public List getSysUserListByEnable() throws Exception{
		return baseSysUserMapper.getSysUserList();
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
	 * @date Mar 13, 2013
	 */
	public boolean isLockEdit(String tablename, String lockid) throws Exception {
		//获取当前用户对象
		SysUser sysUser = getSysUser();
		NetLock netLock = baseNetLockMapper.getNetLock(tablename, lockid);
		boolean flag = false;
		if(null!=netLock){
			//加锁人是当前用户
			//则对该数据进行解锁  并且返回可以修改的状态
			if(netLock.getLockuserid().equals(sysUser.getUserid())){
				int i = baseNetLockMapper.deleteNetLock(tablename, lockid);
				flag = i>0;
			}else{
				flag = false;
			}
		}else{
			flag = true;
		}
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
	public boolean isLock(String tablename, String lockid) throws Exception {
		//获取当前用户对象
		SysUser sysUser = getSysUser();
		NetLock netLock = baseNetLockMapper.getNetLock(tablename, lockid);
		boolean flag = true;
		if(null!=netLock){
			//加锁人是当前用户
			//则对该数据进行解锁  并且返回可以修改的状态
			if(netLock.getLockuserid().equals(sysUser.getUserid())){
				flag = false;
			}else{
				flag = true;
			}
		}else{
			flag = false;
		}
		return flag;
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
	 * msgtype :消息类型1个人短信2公告通知3电子邮件4工作流5业务预警6单据<br/>
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
		if(!map.containsKey("mtiptype") || map.get("mtiptype") == null || StringUtils.isEmpty(map.get("mtiptype").toString())){
			return false;
		}		
		String[] mtiptypearr=map.get("mtiptype").toString().split(",");
		String senduserid = "system";;
		if(map.containsKey("senduserid")){
			senduserid = map.get("senduserid").toString();
		}
		
		if(ArrayUtils.contains(mtiptypearr, "1") && map.containsKey("receivers") && StringUtils.isNotEmpty(map.get("receivers").toString())){
		    IInnerMessageService innerMessageService=null;
		    try{
		    	innerMessageService=(IInnerMessageService)	SpringContextUtils.getBean("innerMessageService");
		    }catch(Exception e){
				//logger.error("BaseServiceImpl短信处理", e);
		    }
		    if(innerMessageService!=null){
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
                
				flag=innerMessageService.addSendMessage(msgContent);
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
		}
		if(ArrayUtils.contains(mtiptypearr, "2") && map.containsKey("receivers") && StringUtils.isNotEmpty(map.get("receivers").toString())){
			IEmailService emailService=null;
		    try{
		    	emailService=(IEmailService)	SpringContextUtils.getBean("emailService");
		    }catch(Exception e){
				//logger.error("BaseServiceImpl短信处理", e);
		    }
		    if(emailService!=null){
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
				flag=emailService.addEmail(emailContent);
		    }
		} 
		if(ArrayUtils.contains(mtiptypearr, "3")
				&& ( map.containsKey("receivers") && StringUtils.isNotEmpty(map.get("receivers").toString()) 
				||  map.containsKey("mobiles") && StringUtils.isNotEmpty(map.get("mobiles").toString()) )){
			IMobileSmsService mobileSmsService=null;
		    try{
		    	mobileSmsService=(IMobileSmsService)	SpringContextUtils.getBean("mobileSmsService");
		    }catch(Exception e){
				//logger.error("BaseServiceImpl短信处理", e);
		    }
		    if(mobileSmsService!=null){
				//手机短信
				List<MobileSms> smsList=new ArrayList<MobileSms>();
				Calendar calendar=Calendar.getInstance();
				calendar.add(Calendar.MINUTE, 1);	//延时1分钟发送，这样可以使用JOB服务
				Date sendDate=calendar.getTime();
				String serialno=mobileSmsService.getSerialno();
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
				if(mobileSmsService.addMobileSmsList(smsList)){

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
		}
		return flag;
	}
	/**
	 * 基础档案修改后，更新级联关系表中的数据
	 * @param tablename 表名
	 * @param beforeObject 修改前对象
	 * @param updatedObject 修改后对象
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-2-2
	 */
	public boolean updateRelatedTableByBasefiles(String tablename,Object beforeObject,Object updatedObject) throws Exception{
		
		Map beforeMap = BeanUtils.describe(beforeObject);
		Map updatedMap = BeanUtils.describe(updatedObject);
		
		Iterator it = updatedMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next(); 
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if(beforeMap.containsKey(key)){
				String beforeValue = (String) beforeMap.get(key);
				//判断修改后字段是否与修改前相同
				if(null!=value&&null!=beforeValue&&!beforeValue.equals(value)){
					 Map queryMap = new HashMap();
					 queryMap.put("maintablename", tablename);
					 queryMap.put("maincolumnname", key);
					 queryMap.put("cascadechange", "1");
					 List<TableRelation> list = baseTableRelationMapper.getTableRelationListBy(queryMap);
					 //更新级联关系表数据
					 for(TableRelation tableRelation : list){
						 baseTableRelationMapper.updateRelatedTable(StringEscapeUtils.escapeSql(tableRelation.getTablename()),
								StringEscapeUtils.escapeSql(tableRelation.getColumnname()), beforeValue, value);
					 }
				}
			}
		}
		return true;
	}
	/**
	 * 判断基础档案该对象是否可以修改。<br/>
	 * 如果可以修改并且更新级联关系信息
	 * @param beforeObject 修改前对象
	 * @param updatedObject 修改后对象
	 * @return
	 * @author chenwei 
	 * @date 2013-2-2
	 */
	public boolean canBasefilesIsEdit(String tablename,Object beforeObject,Object updatedObject) throws Exception{
		Map beforeMap = BeanUtils.describe(beforeObject);
		Map updatedMap = BeanUtils.describe(updatedObject);
		
		//获取哪些字段不可以修改
		Map map = canTableDataDictEdit(tablename, beforeObject);
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next(); 
			String key = (String) entry.getKey();
			Boolean value = (Boolean) entry.getValue();
			if(!value){
				String before = (String) beforeMap.get(key);
				String updated = (String) updatedMap.get(key);
				//当修改的值为null或者与修改前相同时 允许修改 否则不允许修改
				if(null==updated||(null!=updated&&updated.equals(before))){
				}else{
					return false;
				}
			}
		}
		//更新级联关系表中的信息		
		//updateRelatedTableByBasefiles(tablename, beforeObject, updatedObject);
		return true;
	}
	/**
	 * 手机号发送序列
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-16
	 */
	public String getSmsSerialno() throws Exception{
		String serialno="";
		IMobileSmsService mobileSmsService=null;
	    try{
	    	mobileSmsService=(IMobileSmsService)	SpringContextUtils.getBean("mobileSmsService");
	    }catch(Exception e){
			//logger.error("BaseServiceImpl短信处理", e);
	    }
	    if(mobileSmsService!=null){
	    	serialno=mobileSmsService.getSerialno();
	    }else{
	    	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");	//比uuid_short 多一位
			serialno = sf.format(new Date());
	    }
	    return serialno;
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
		IMobileSmsService mobileSmsService=null;
	    try{
	    	mobileSmsService=(IMobileSmsService)	SpringContextUtils.getBean("mobileSmsService");
	    }catch(Exception e){
			//logger.error("BaseServiceImpl短信处理", e);
	    }
		if(mobileSmsService.addMobileSmsList(list)){
			flag=true;
			ITaskScheduleService taskScheduleService=null;
			try{
				taskScheduleService=(ITaskScheduleService) SpringContextUtils.getBean("taskScheduleService");
			}catch (Exception ex) {
				logger.error(ex);
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
		SysParam sysParam = baseSysParamMapper.getSysParam(name);
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
		decimalLen = CommonUtils.decimalLen;
		return decimalLen;
	}
	/**
	 * 根据传过来的单据编号billcode，判断是否自动生成
	 * 返回值结果：<br/>
	 * flag=true时，为是自动生成，flag=false时，不是
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-19
	 */
	public boolean isAutoCreate(String billcode)throws Exception{
		boolean flag=false;
		ISysNumberService sysNumberService=(ISysNumberService) SpringContextUtils.getBean("sysNumberService");
		SysNumber sysNumber=sysNumberService.getSysNumberAutoCreate(billcode);
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
	 * 获取当前登录日期
	 * @return
	 */
	public String getCurrentDate() throws Exception{
		String busDateStr = null;
		try{
			HttpSession session = ServletActionContext.getRequest().getSession();
			String busdate_type = (String) session.getAttribute("busdate_type");
			String  thisBusdateStr = (String) session.getAttribute("busdate");
			if("now_date".equals(busdate_type)){
				busDateStr = CommonUtils.getTodayDataStr();
				if(null!=thisBusdateStr && !thisBusdateStr.equals(busDateStr)){
					session.setAttribute("busdate", busDateStr);
				}
			}else{
				busDateStr = (String) session.getAttribute("busdate");
			}
		}catch (Exception e){}
		if(StringUtils.isEmpty(busDateStr)){
			busDateStr = CommonUtils.getTodayDataStr();
		}
		String IsOpenBusDate = getSysParamValue("IsOpenBusDate");
		if(!(StringUtils.isNotEmpty(IsOpenBusDate)  && "1".equals(IsOpenBusDate))){
			busDateStr = CommonUtils.getTodayDataStr();
		}
		return busDateStr;
	}

	/**
	 * 获取审核后的业务日期
	 * @param businessdate  当前业务日期
	 * @return
	 * @throws Exception
	 */
	public String getAuditBusinessdate(String businessdate) throws Exception{
		if(StringUtils.isEmpty(businessdate)){
			businessdate = CommonUtils.getTodayDataStr();
		}
		//审核业务日期
		String auditBusinessdate = businessdate;
		//业务日期是否取审核日期 0否1是2登录日期
		String isAuditBusinessdate=getSysParamValue("IsAuditBusinessdate");
		if(StringUtils.isEmpty(isAuditBusinessdate)){
			isAuditBusinessdate = "1";
		}
		if("1".equals(isAuditBusinessdate)){
			auditBusinessdate = CommonUtils.getTodayDataStr();
		}else if("2".equals(isAuditBusinessdate)){
			auditBusinessdate=getCurrentDate();
		}
		return auditBusinessdate;
	}
    /**
     * 自动生成单据编号（根据编号规则）
     * @param obj
     * @param billcode
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-13
     */
	public Map autoCreateSysNumbderForeign(Object obj, String billcode)
			throws Exception {
		String busDateStr = getCurrentDate();
        Date busDate = CommonUtils.stringToDate(busDateStr);
        String billno = "";
        boolean serialAccordFlag = false;//根据流水依据字段值是否相同后判断是否递增，false 重置，true 递增
        Map retMap = new HashMap();
        if(obj != null){
            //获取tablename表启用的单据编号详情
            SysNumber sysNumber = baseSysNumberMapper.getSysNumberAutoCreate(billcode);
            SysNumberSerial serial = null;
            //判断是否存在该单据编号设置
            if(sysNumber != null){
                Map map = BeanUtils.describe(obj);
                //第一步，判断是否存在流水号字段，若存在，对比流水号字段值是否改变，若改变流水号重置，反之，继续新增
                //获取流水号依据为是“1”的单据编号规则
                List<SysNumberRule> ruleList = baseSysNumberMapper.getSysNumberRuleList(sysNumber.getNumberid());
                if(ruleList.size() != 0){
                    //流水依据值
                    String serialKey = "";
                    for(SysNumberRule rule : ruleList){
                        if("1".equals(rule.getState())){//以该数据作为流水依据，从map中获取该字段的值作为流水号依据字段值testvalue
                            //SATField流水依据字段
                            // 判断流水依据字段是否为系统日期
                            if("3".equals(rule.getColtype())){
                                SimpleDateFormat sdf = new SimpleDateFormat(rule.getVal());
                                String systime = sdf.format(busDate);//系统当前时间
                                map.put(rule.getVal(), systime);//如：{[sysdatetime]:20130610}
                                //根据编号规则详情截取字段值retStr
                                String retStr = getNumberRule(systime,rule);
                                serialKey += retStr;
                            }else{
                                if(map.containsKey(rule.getVal())){
                                    //从map中根据流水依据字段获取对应的字段值，即流水依据字段值
                                    Object valObject = map.get(rule.getVal());
                                    String SATFieldValue = "";
                                    if(null!=valObject){
                                        SATFieldValue = valObject.toString();
                                    }
                                    //map.put(map.get("serialAccord").toString(), serialAccordByVal);
                                    String retStr = getNumberRule(SATFieldValue,rule);
                                    serialKey += retStr;
                                }
                            }
                        }
                        //第二步，根据单据编号规则拼装单据编号（除流水号）
                        //判断编号规则字段类型：1固定值2字段3系统日期
                        if("1".equals(rule.getColtype())){
                            billno += rule.getPrefix()+rule.getVal()+rule.getSuffix();

                        }else if("2".equals(rule.getColtype())){
                            if(map.containsKey(rule.getVal()) && null != map.get(rule.getVal())){
                                String test = map.get(rule.getVal()).toString();
                                billno += getNumberRule(test,rule);
                            }
                        }else if("3".equals(rule.getColtype())){
                            if(!"1".equals(rule.getState())){
                                SimpleDateFormat sdf = new SimpleDateFormat(rule.getVal());
                                String systime = sdf.format(busDate);//系统当前时间
                                //根据编号规则详情截取字段值retStr
                                billno += getNumberRule(systime,rule);
                            }
                            else{
                                if(map.containsKey(rule.getVal()) && map.get(rule.getVal()) != null){
                                    String test = map.get(rule.getVal()).toString();
                                    billno += getNumberRule(test,rule);
                                }
                            }
                        }
                    }
                    // 流水号字段拼接
                    sysNumber.setTestvalue(serialKey);
                    serial = baseSysNumberMapper.selectSysNumberSerialByColval(sysNumber.getNumberid(), serialKey);
                    if(serial == null) {
                        serialAccordFlag = false;
                        serial = new SysNumberSerial();
                        serial.setNumberid(sysNumber.getNumberid());
                        serial.setSerialkey(serialKey);
                        serial.setSerialval(sysNumber.getSerialstart().toString());
                        baseSysNumberMapper.insertSysNumberSerial(serial);
                    } else {
                        serialAccordFlag = true;
                    }
                }
                //第三步，流水号获取规则
                int length = sysNumber.getSeriallength();//流水号长度
                int step = sysNumber.getSerialstep();//流水号步长
                int start = sysNumber.getSerialstart();//流水号起始值
                String serialNum = serial.getSerialval();//当前流水号
                String zeroStr = "",serialStr = "";
                //是否递增，true 递增，false 重置
                if(serialAccordFlag){
                    //当前流水号为空时，第一次自动生成编号,用起始值
                    if(StringUtils.isEmpty(sysNumber.getSerialnumber())){
                        for(int i=0;i<length - String.valueOf(start).length();i++){
                            zeroStr += "0";
                        }
                        serialStr = zeroStr + String.valueOf(start);
                        if(serialStr.length() <= length){
                            sysNumber.setSerialnumber(serialStr);
                            serial.setSerialval(serialStr);
                            baseSysNumberMapper.updateSysNumberSerial(serial);
                        }
                        else{
                            retMap.put("spill", "spill");
                        }
                    }
                    else{//判断起始值是否大于当前流水号，大于则使用起始值，反之，使用当前流水号
                        if(start > Integer.parseInt(serialNum)){
                            for(int i=0;i<length - String.valueOf(start).length();i++){
                                zeroStr += "0";
                            }
                            serialStr=zeroStr+String.valueOf(start);
                            if(serialStr.length() <= length){
                                sysNumber.setSerialnumber(serialStr);
                                serial.setSerialval(serialStr);
                                baseSysNumberMapper.updateSysNumberSerial(serial);
                            }
                            else{
                                retMap.put("spill", "spill");
                            }
                        }
                        else{
                            for(int i=0;i<length - String.valueOf(Integer.parseInt(serialNum)+ step).length();i++){
                                zeroStr += "0";
                            }
                            serialStr = zeroStr + String.valueOf(Integer.parseInt(serialNum)+ step);
                            if(serialStr.length() <= length){
                                sysNumber.setSerialnumber(serialStr);
                                serial.setSerialval(serialStr);
                                baseSysNumberMapper.updateSysNumberSerial(serial);
                            }
                            else{
                                retMap.put("spill", "spill");
                            }
                        }
                    }
                }
                else{
                    for(int i=0;i<length - String.valueOf(start).length();i++){
                        zeroStr += "0";
                    }
                    serialStr=zeroStr+String.valueOf(start);
                    if(serialStr.length() <= length){
                        sysNumber.setSerialnumber(serialStr);
                        serial.setSerialval(serialStr);
                        baseSysNumberMapper.updateSysNumberSerial(serial);
                    }
                    else{
                        retMap.put("spill", "spill");
                    }
                }
                //最后，获取完整的单据编号billno
                if(baseSysNumberMapper.editSysNumber(sysNumber) > 0){
                    billno += serialStr;
                }
            }
            else{billno = null;}
        }else{billno = null;}
        retMap.put("billno", billno);
        return retMap;
	}
	
	/**
	 * 根据单据规则截取生成字符串
	 * @param str
	 * @param sysNumberRule
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-19
	 */
	public String getNumberRule(String str,SysNumberRule sysNumberRule){
		String retStr="",sCover="";
		if(null == sysNumberRule.getSubstart()){
			sysNumberRule.setSubstart(0);
		}
		if("1".equals(sysNumberRule.getSubtype())){//截取方向1:从前向后2从后向前
			//判断长度是否足够
			if(str.length()>=sysNumberRule.getLength()+sysNumberRule.getSubstart()){
				retStr= str.substring(sysNumberRule.getSubstart(), sysNumberRule.getLength()+sysNumberRule.getSubstart());
			}else{
				//长度不够 需要补位
				retStr= str.substring(sysNumberRule.getSubstart(), str.length());
			}
		}else{
			//当截取位置大于字符串总长度时
			if(sysNumberRule.getSubstart()>str.length()){
				if(str.length()>=sysNumberRule.getLength()){
					retStr= str.substring(str.length()-sysNumberRule.getLength(), str.length());
				}else{
					retStr= str.substring(0, str.length());
				}
			}else{
				//截取开始位置 大于需要长度时
				if(sysNumberRule.getSubstart()>=sysNumberRule.getLength()){
					retStr=str.substring(sysNumberRule.getSubstart()-sysNumberRule.getLength(), str.length());
				}else{
					//截取开始位置 小于需要长度时 
					//表示需要补位
					retStr=str.substring(0, sysNumberRule.getSubstart());
				}
			}
		}
		//截取后长度不够 补位
		if(retStr.length()<sysNumberRule.getLength()){
			String cover = "";
			for(int i=0;i<sysNumberRule.getLength()-retStr.length();i++){
				cover += sysNumberRule.getCover();
			}
			retStr += cover;
		}
		//补上前缀后缀
		retStr = sysNumberRule.getPrefix()+retStr+sysNumberRule.getSuffix();
		return retStr;
	}
	/**
	 * 根据数据对象获取自动生成的单据编号
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-19
	 */
	public synchronized String getAutoCreateSysNumbderForeign(Object obj,String billcode)throws Exception{
		Map retMap = new HashMap();
		Map sysNumberCodeRetMap=new SysNumberUtils().getSysNumber(obj, billcode);
		if(sysNumberCodeRetMap.containsKey("billno") && null != sysNumberCodeRetMap.get("billno")){
			retMap.put("billno", sysNumberCodeRetMap.get("billno").toString());
			if(sysNumberCodeRetMap.containsKey("spill") && null != sysNumberCodeRetMap.get("spill")){//spill存在且不为空，则流水号溢出
				throw new RuntimeException("编号溢出");
			}else{
				return sysNumberCodeRetMap.get("billno").toString();
			}
		}
		else{
			return null;
		}
	}
	
	/**
	 * 更新父节点名称同步修改子节点名称，只对树形基础档案有效
	 * @param tname 表名
	 * @param name 修改的名称
	 * @param id 修改的编号
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 27, 2013
	 */
	public void updateTreeName(String tname, String name, String id) throws Exception{
		SalesAreaMapper salesAreaMapper = (SalesAreaMapper)SpringContextUtils.getBean("salesAreaMapper");
		Map map = new HashMap();
		map.put("tname", tname);
		map.put("name", name);
		map.put("pid", id);
		salesAreaMapper.updateTreeName(map);
		Map map2 = new HashMap();
		map2.put("tname", tname);
		map2.put("pid", id);
		List<Map> list = salesAreaMapper.getTreeInfo(map2);
		if(list.size() > 0){
			for(Map map3 : list){
				String idString = map3.get("id").toString();
				String nameString = map3.get("name").toString();
				updateTreeName(tname, nameString, idString);
			}
		}
	}
	/**
	 * 根据部门编码获取部门信息
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 26, 2013
	 */
	public DepartMent getDepartmentByDeptid(String deptid)throws Exception{
		return baseDepartMentMapper.getDepartmentInfo(deptid);
	}
	
	/**
	 * 根据部门名称获取部门信息
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 22, 2014
	 */
	public DepartMent getDepartMentByName(String name)throws Exception{
		return baseDepartMentMapper.getDepartmentInfoLimitOne(name);
	}

	/**
	 * 新增客户档案基础信息
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 8, 2014
	 */
	public boolean addBaseCustomerInfo(Customer customer)throws Exception{
		SysUser sysUser = getSysUser();
		customer.setAdddeptid(sysUser.getDepartmentid());
		customer.setAdddeptname(sysUser.getDepartmentname());
		customer.setAdduserid(sysUser.getUserid());
		customer.setAddusername(sysUser.getName());
		if(StringUtils.isEmpty(customer.getThisid())){
			customer.setThisid(customer.getId());
		}
		if(StringUtils.isEmpty(customer.getPinyin()) && StringUtils.isNotEmpty(customer.getShortname())){
			customer.setPinyin(CommonUtils.getPinYingJCLen(customer.getShortname()));
		}
        //联系人
        if(StringUtils.isNotEmpty(customer.getContactname())){
            customer.setContact(customer.getContactname());
        }
		int i = baseCustomerMapper.addCustomer(customer);
		if(i > 0){
			if(StringUtils.isNotEmpty(customer.getCustomersort())){
				CustomerAndSort customerAndSort = new CustomerAndSort();
				customerAndSort.setCustomerid(customer.getId());
				customerAndSort.setDefaultsort("1");
				customerAndSort.setSortid(customer.getCustomersort());
				Map map = new HashMap();
				map.put("id", customer.getCustomersort());
				CustomerSort sort = baseCustomerSortMapper.getCustomerSortDetail(map);
				if(null != sort){
					customerAndSort.setSortname(sort.getName());
				}
				baseCustomerAndSortMapper.addCustomerAndSort(customerAndSort);
			}
		}
		return i > 0;
	}
	
	/**
	 * 新增商品档案基础信息
	 * @param goodsInfo
	 * @param priceInfoList
	 * @param goodsMUInfo
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 30, 2014
	 */
	public boolean addBaseGoodsInfo(GoodsInfo goodsInfo,
			List<GoodsInfo_PriceInfo> priceInfoList,
			GoodsInfo_MteringUnitInfo goodsMUInfo)throws Exception{
		Map map = new HashMap();
		//判断是否存在默认税种,若不存在,则取默认税种
		if(StringUtils.isEmpty(goodsInfo.getDefaulttaxtype())){
			SysParam sysParamTaxType = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
			if(null != sysParamTaxType){
				goodsInfo.setDefaulttaxtype(sysParamTaxType.getPvalue());
				goodsInfo.setDefaulttaxtypeName(sysParamTaxType.getPvdescription());
			}
		}
		//新增价格套管理信息
		if(priceInfoList != null && priceInfoList.size() != 0){
			List<GoodsInfo_PriceInfo> addPriceList = new ArrayList<GoodsInfo_PriceInfo>();
			for(GoodsInfo_PriceInfo priceInfo2 : priceInfoList){
				if(null != priceInfo2.getTaxprice() && priceInfo2.getTaxprice().compareTo(new BigDecimal(0)) != -1){
					priceInfo2.setGoodsid(goodsInfo.getId());
					//税种赋值
					if(StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())){
						priceInfo2.setTaxtype(goodsInfo.getDefaulttaxtype());
						//获取税率
						TaxType taxType = baseFinanceMapper.getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
						if(null != taxType && null != taxType.getRate() && taxType.getRate().compareTo(new BigDecimal(0)) != -1){
							//计算无税单价
							BigDecimal rate = taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1));
							BigDecimal price = priceInfo2.getTaxprice().divide(rate, 6, BigDecimal.ROUND_HALF_UP);
							priceInfo2.setPrice(price);
						}
					}
				}

                // 4067 通用版：新品登录申请单完结后，商品档案中要显示对应价格套的箱价
                priceInfo2.setBoxprice(priceInfo2.getTaxprice() == null ? BigDecimal.ZERO : priceInfo2.getTaxprice().multiply(goodsInfo.getBoxnum()));
                baseGoodsMapper.addPriceInfo(priceInfo2);
			}
		}
		//新增辅计量单位
		if(null != goodsMUInfo){
			goodsMUInfo.setGoodsid(goodsInfo.getId());
			//换算类型
			goodsMUInfo.setType("1");
			//换算方式
			goodsMUInfo.setMode("1");
			goodsMUInfo.setIsdefault("1");
			if(StringUtils.isNotEmpty(goodsInfo.getBoxbarcode())){
				goodsMUInfo.setBarcode(goodsInfo.getBoxbarcode());
			}
			List<GoodsInfo_MteringUnitInfo> muList = new ArrayList<GoodsInfo_MteringUnitInfo>();
			muList.add(goodsMUInfo);
			map.put("meteringUnitListMap", muList);
		}
		//新增对应仓库
		if(StringUtils.isNotEmpty(goodsInfo.getStorageid())){
			GoodsInfo_StorageInfo storageInfo = new GoodsInfo_StorageInfo();
			storageInfo.setGoodsid(goodsInfo.getId());
			storageInfo.setStorageid(goodsInfo.getStorageid());
			storageInfo.setIsdefault("1");
			List<GoodsInfo_StorageInfo> sList = new ArrayList<GoodsInfo_StorageInfo>();
			sList.add(storageInfo);
			map.put("storageInfoListMap", sList);
		}
		//新增对应分类
		if(StringUtils.isNotEmpty(goodsInfo.getDefaultsort())){
			GoodsInfo_WaresClassInfo wcInfo = new GoodsInfo_WaresClassInfo();
			wcInfo.setGoodsid(goodsInfo.getId());
			wcInfo.setWaresclass(goodsInfo.getDefaultsort());
			wcInfo.setIsdefault("1");
			List<GoodsInfo_WaresClassInfo> wcList = new ArrayList<GoodsInfo_WaresClassInfo>();
			wcList.add(wcInfo);
			map.put("waresClassInfoListMap", wcList);
		}
		//新增对应库位
		if(StringUtils.isNotEmpty(goodsInfo.getStoragelocation())){
			GoodsStorageLocation slInfo = new GoodsStorageLocation();
			slInfo.setGoodsid(goodsInfo.getId());
			slInfo.setStoragelocationid(goodsInfo.getStoragelocation());
			slInfo.setIsdefault("1");
			if(null != goodsInfo.getSlboxnum()){
				slInfo.setBoxnum(goodsInfo.getSlboxnum());
			}
			List<GoodsStorageLocation> slList = new ArrayList<GoodsStorageLocation>();
			slList.add(slInfo);
			map.put("SLListMap", slList);
		}
		if(map.containsKey("priceInfoMap")){
			baseGoodsMapper.addPriceInfos(map);
		}
		if(map.containsKey("meteringUnitListMap")){
			baseGoodsMapper.addMeteringUnitInfos(map);
		}
		if(map.containsKey("storageInfoListMap")){
			baseGoodsMapper.addStorageInfos(map);
		}
		if(map.containsKey("waresClassInfoListMap")){
			baseGoodsMapper.addWaresClassInfos(map);
		}
		if(map.containsKey("SLListMap")){
			baseGoodsMapper.addGoodsStorageLocation(map);
		}
		if(StringUtils.isEmpty(goodsInfo.getState())){
			goodsInfo.setState("1");
		}
		//拼音
		if(StringUtils.isEmpty(goodsInfo.getPinyin()) && StringUtils.isNotEmpty(goodsInfo.getName())){
			goodsInfo.setPinyin(CommonUtils.getPinYingJCLen(goodsInfo.getName()));
		}
		//最高采购价 == 最新采购价 == 最新库存价
		if(null != goodsInfo.getHighestbuyprice()){
			goodsInfo.setNewstorageprice(goodsInfo.getHighestbuyprice());
			goodsInfo.setNewbuyprice(goodsInfo.getHighestbuyprice());
		}
		//箱重
		if(null != goodsInfo.getTotalweight()){
			goodsInfo.setTotalweight(goodsInfo.getTotalweight());
		}
		//毛重=箱重/箱装量(换算比率)
		if(null != goodsInfo.getTotalweight() && null != goodsInfo.getBoxnum() && 
				goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0 &&
				goodsInfo.getTotalweight().compareTo(BigDecimal.ZERO) != 0
		){
			BigDecimal grossweight = goodsInfo.getTotalweight().divide(goodsInfo.getBoxnum(), 6, BigDecimal.ROUND_HALF_UP);
			goodsInfo.setGrossweight(grossweight);
		}
		//长高宽、单体积
		if(null != goodsInfo.getGlength() && null != goodsInfo.getGhight() && null != goodsInfo.getGwidth() &&
			goodsInfo.getGlength().compareTo(BigDecimal.ZERO) != 0 &&
			goodsInfo.getGhight().compareTo(BigDecimal.ZERO) != 0 &&
			goodsInfo.getGwidth().compareTo(BigDecimal.ZERO) != 0
		){
			goodsInfo.setGlength(goodsInfo.getGlength());
			goodsInfo.setGhight(goodsInfo.getGhight());
			goodsInfo.setGwidth(goodsInfo.getGwidth());
		}
		//箱体积 = 长*高*宽*数量
		if(null != goodsInfo.getTotalvolume()){
			goodsInfo.setTotalvolume(goodsInfo.getTotalvolume());
		}else{
			if(null != goodsInfo.getGlength() && null != goodsInfo.getGhight() && null != goodsInfo.getGwidth() &&
				goodsInfo.getGlength().compareTo(BigDecimal.ZERO) != 0 &&
				goodsInfo.getGhight().compareTo(BigDecimal.ZERO) != 0 &&
				goodsInfo.getGwidth().compareTo(BigDecimal.ZERO) != 0
			){
				BigDecimal totalvolume = goodsInfo.getGlength().multiply(goodsInfo.getGhight()).multiply(goodsInfo.getGwidth());
				goodsInfo.setTotalvolume(totalvolume);
			}
		}
		//单体积 = 箱体积/数量
		if(null == goodsInfo.getSinglevolume() || goodsInfo.getSinglevolume().compareTo(BigDecimal.ZERO) == 0){
			if(null != goodsInfo.getTotalvolume() && null != goodsInfo.getBoxnum() &&
					goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0 &&
					goodsInfo.getTotalvolume().compareTo(BigDecimal.ZERO) != 0
			){
				BigDecimal singlevolume = goodsInfo.getTotalvolume().divide(goodsInfo.getBoxnum(), 6, BigDecimal.ROUND_HALF_UP);
				goodsInfo.setSinglevolume(singlevolume);
			}else{
				goodsInfo.setSinglevolume(goodsInfo.getSinglevolume());
			}
		}
		//根据品牌获取所属部门
		if(StringUtils.isEmpty(goodsInfo.getDeptid())){
			Brand brand = getBaseGoodsMapper().getBrandInfo(goodsInfo.getBrand());
			if(null != brand){
				goodsInfo.setDeptid(brand.getDeptid());
			}
		}
		return baseGoodsMapper.addGoodsInfo(goodsInfo) > 0;
	}
	
	/**
	 * 根据客户编码删除分配的品牌业务员(流程撤销调用)
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 23, 2014
	 */
	public void deleteAllotCustomerToPsn(String customerid)throws Exception{
		List<PersonnelCustomer> cList = basePersonnelMapper.getCustomerByCustomerid(customerid);
		for(PersonnelCustomer pc : cList){
			//根据品牌业务员编码删除对应品牌
			basePersonnelMapper.deleteBrandByPerid(pc.getPersonid());
		}
		//根据客户编码删除品牌业务员对应客户
		basePersonnelMapper.deleteCustomerListByCustomerid(customerid);
		//根据客户编码删除品牌业务员对应客户对应品牌表
		basePersonnelMapper.deleteBrandAndCustomerByCustomer(customerid);
	}

	/**
	 * 根据客户编码删除分配的厂家业务员(流程撤销调用)
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 8, 2014
	 */
	public void deleteAllotCustomerToSupplierPsn(String customerid)throws Exception{
		Map map = new HashMap();
		map.put("customerid", customerid);
		List<PersonnelCustomer> supplierCustomerList = basePersonnelMapper.getSupplierCustomerListByMap(map);
		for(PersonnelCustomer pc : supplierCustomerList){
			//根据厂家业务员编码删除对应品牌
			Map map2 = new HashMap();
			map2.put("personid", pc.getPersonid());
			basePersonnelMapper.deleteSupplierBrandListByParam(map2);
		}
		//根据客户编码删除厂家业务员对应客户
		basePersonnelMapper.deleteSupplierCustomerListByParam(map);
		//根据客户编码删除厂家业务员对应客户对应品牌表
		basePersonnelMapper.deleteSupplierBrandAndCustomerByMap(map);
	}

    /**
     * 根据回单来源单据发货单编号判断其回单配送状态0未配送1配送中2已配送3配送完毕
     * @param receipt
     * @return
     * @throws Exception
     */
    protected String getIsDeliveryByReceiptSourceids(Receipt receipt)throws Exception{
        String isdelivery = "0";
        String saleoutids = null != receipt.getBillno() ? receipt.getBillno() : "";
        if(StringUtils.isNotEmpty(saleoutids)){
            String[] idsarr = saleoutids.split(",");
            Map map = new HashMap();
            map.put("idsArr",idsarr);
            isdelivery = baseSaleoutMapper.getIsDeliveryByReceiptSourceids(map);
        }
        return isdelivery;
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
    public boolean sendPhoneMsg(String userid,String type,String title,String msg,String url){
        SysUser sysUser = baseSysUserMapper.showSysUserInfo(userid);
        if(null!=sysUser && StringUtils.isNotEmpty(sysUser.getCid())){
            String appId = "UN7009aEub8agAkLEVSDKA";
            String appKey = "THyCH4lWVz9on0c81WbVQ6";
            String masterSecret = "g2Milux7lZ7VludwymTEA6";
            String apiurl = "http://sdk.open.api.igexin.com/apiex.htm";
            // 新建一个IGtPush实例，传入调用接口网址，appkey和masterSecret
            IGtPush push = new IGtPush(apiurl, appKey, masterSecret);
            TransmissionTemplate template = transmissionTemplateDemo(appId, appKey, type,title, msg,url);

            SingleMessage message = new SingleMessage();
            message.setOffline(true);
            message.setOfflineExpireTime(2 * 1000 * 3600);
            message.setData(template);

            Target target1 = new Target();
            target1.setAppId(appId);
            target1.setClientId(sysUser.getCid());
//		 target1.setAlias(Alias);
            try {
                IPushResult ret = push.pushMessageToSingle(message, target1);
//                System.out.println("推送结果" + ret.getResponse().toString());
            } catch (RequestException e) {
                String requstId = e.getRequestId();
                IPushResult ret = push.pushMessageToSingle(message, target1,requstId);
//                System.out.println("推送结果"+ ret.getResponse().toString());
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * 透传消息模版
     * @param appId
     * @param appkey
     * @param type
     * @param title
     * @param content
     * @return
     */
    public TransmissionTemplate transmissionTemplateDemo(String appId,String appkey,String type,String title,String content,String url) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appkey);
        JSONObject jsonObject = new JSONObject();
        //type 1 发送公告短信等内容包含url地址 2上传定位 3 强制启用程序
        jsonObject.put("type",type);
        jsonObject.put("title",title);
        if(content.length()<=1500){
            jsonObject.put("content",content);
        }else{
            jsonObject.put("content",content.substring(0,1500));
        }
        jsonObject.put("url",url);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        if("3".equals(type)){
            template.setTransmissionType(1);
        }else{
            template.setTransmissionType(2);
        }
        template.setTransmissionContent(jsonObject.toString());
        return template;
    }

    /**
     * 判断客户是否拥有品牌结算方式（包含总店的结算方式）
     * @param customerid
     * @return
     * @throws Exception
     */
    public boolean isCustomerHasBrandSettle(String customerid) throws Exception{
        boolean flag = false;
        Customer customer = baseCustomerMapper.getCustomerById(customerid);
        if(null!=customer){
            List list = getBaseCustomerMapper().getCustomerBrandSettletypeList(customerid);
            if(null!=list && list.size()>0){
                flag = true;
            }else if(StringUtils.isNotEmpty(customer.getPid())){
                List plist = getBaseCustomerMapper().getCustomerBrandSettletypeList(customer.getPid());
                if(null!=plist && plist.size()>0){
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 获取客户品牌结算方式
     * 客户品牌结算方式不存在时 取总店的品牌结算方式
     * @param customerid
     * @param brandid
     * @return
     * @throws Exception
     */
    public CustomerBrandSettletype getCustomerBrandSettletype(String customerid,String brandid) throws Exception{
        CustomerBrandSettletype customerBrandSettletype = null;
        if(StringUtils.isNotEmpty(customerid) && StringUtils.isNotEmpty(brandid)){
            Customer customer = baseCustomerMapper.getCustomerById(customerid);
            if(null!=customer){
                customerBrandSettletype = baseCustomerMapper.getCustomerBrandSettletypeByCustomeridAndBrandid(customerid,brandid);
                if(null==customerBrandSettletype && StringUtils.isNotEmpty(customer.getPid())){
                    customerBrandSettletype = baseCustomerMapper.getCustomerBrandSettletypeByCustomeridAndBrandid(customer.getPid(),brandid);
                }
            }
        }
        return customerBrandSettletype;
    }
	
	/**
	 * 给查询条件中与数据字典中箱符合的字段加前缀
	 * @param tablename
	 * @param prefix
	 * @param condition
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年4月22日
	 */
	public Map<String,String> getColumnPrefixQueryCondition(String tablename,String prefix,Map condition) throws Exception{
		Map<String,String> resultMap=null;
		if(tablename==null || "".equals(tablename.trim())
				|| prefix==null || "".equals(prefix.trim())
				|| condition==null || condition.size()==0){
			return resultMap;
		}
		tablename=tablename.trim();
		prefix=prefix.trim();
		//判断表名后缀，如果是历史表（_h后缀）取正式表的字段
		if(null!=tablename&&tablename.length()>2){
			String suffix = tablename.substring(tablename.length()-2, tablename.length());
			if("_h".equals(suffix)||"_v".equals(suffix)){
				tablename = tablename.substring(0,tablename.length()-2);
			}
		}
		List<TableColumn> list = baseTableColumnMapper.getTableColumnListByTable(tablename);
		//根据获取的字段权限列表组装成字段列表
		Map columnMap = new HashMap();
		for(TableColumn tableColumn : list){
			if(condition.containsKey(tableColumn.getColumnname())){
				resultMap.put(tableColumn.getColumnname(),prefix+condition.get(tableColumn).toString());
			}
		}
		return resultMap;
	}



	/**
	 * 短信接口
	 * 参数：
	 * type：发送信息类型1.短信，
	 * templateCode：短信模板
	 * paramList: List<Map>  Map中元素为valueName,value.
	 * recNum:接收短信的号码
	 *
	 * @author wanghongteng
	 * @date 2016-12-28
	 */
	public String SendMessageReminder(String type,String templateCode,List<Map> paramList,String recNum,String SignName) throws Exception {
		String url="http://gw.api.taobao.com/router/rest";
		String appkey=WechatProperties.getValue("msg_appkey");
		String secret=WechatProperties.getValue("msg_secret");
		if("1".equals(type)){// 发送短信
			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend("123456");//非必填  用来确定使用人
			req.setSmsType("normal");
			req.setSmsFreeSignName(SignName);
			String param=msgParamListToParam(paramList);
			req.setSmsParamString(param);//模板中的参数
			req.setRecNum(recNum);
			req.setSmsTemplateCode(templateCode);
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			return rsp.getBody();
		}
		return null;
	}


	/**
	 *将模板中的变量参数列表转化成字符串
	 *
	 * @author wanghongteng
	 * @date 2016-12-28
	 */
	public String msgParamListToParam(List<Map> paramList) throws  Exception {
		String param="";
		if(paramList.size()!=0){
			param="{";
			for(int i = 0;i<paramList.size();i++){
				Map map=paramList.get(i);
				if(i==0){
					param=param+"\""+map.get("valueName")+"\":\""+map.get("value")+"\"";
				}else{
					param=param+",\""+map.get("valueName")+"\":\""+map.get("value")+"\"";
				}
			}
			param=param+"}";
		}
		return param;
	}

	/**
	 * 公众号模板消息发送
	 * 参数：
	 * openidList：接受者openid列表
	 * templateid：消息模板编号
	 * dataList: 模板数据
	 *
	 * @author wanghongteng
	 * @date 2016-12-28
	 */
	public Map SendWechatTemplateMsg(List<String> openidList,String templateid,Map data) throws Exception {
		Map resultMap=new HashMap();
		for(String openid : openidList){
			JSONObject pushJson = new JSONObject();

			pushJson.put("touser", openid);
			pushJson.put("template_id", templateid);
			pushJson.put("data", data);
			String as=pushJson.toString();
			JSONObject pushRetJson = WechatUtils.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + WechatUtils.getAccessToken(), pushJson.toString());
			String end=pushRetJson.getString("errcode");
		}
		resultMap.put("flag",true);
		return resultMap;
	}

	/**
	 * 获取供应商品牌结算方式
	 * 供应商品牌结算方式不存在时 取总店的品牌结算方式
	 * @param supplierid
	 * @param brandid
	 * @return
	 * @throws Exception
	 */
	public BuySupplierBrandSettletype getSupplierBrandSettletype(String supplierid,String brandid) throws Exception{
		BuySupplierBrandSettletype buySupplierBrandSettletype = null;
		if(StringUtils.isNotEmpty(supplierid) && StringUtils.isNotEmpty(brandid)){
			BuySupplier buySupplier = baseBuySupplierMapper.getBuySupplier(supplierid);
			if(null!=buySupplier){
				buySupplierBrandSettletype = baseBuySupplierMapper.getSupplierBrandSettletypeBySupplieridAndBrandid(supplierid, brandid);
			}
		}
		return buySupplierBrandSettletype;
	}

	/**
	 * 判断客户是否拥有品牌结算方式（包含总店的结算方式）
	 * @param supplierid
	 * @return boolean
	 * @throws
	 * @author luoqiang
	 * @date Oct 26, 2017
	 */
	public boolean isSupplierHasBrandSettle(String supplierid) throws Exception {
		boolean flag = false;
		BuySupplier buySupplier = baseBuySupplierMapper.getBuySupplier(supplierid);
		if(null!=buySupplier){
			List list = getBaseBuySupplierMapper().getSupplierBrandSettletypeList(supplierid);
			if(null!=list && list.size()>0){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 根据角色编号和用户编号获取符合条件的用户
	 * @param roleid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 26, 2013
	 */
	public List getSysUserListByRoleidAndUserid(String roleid,String userid) throws Exception{
		List list = baseSysUserMapper.getSysUserListByRoleidAndUserid(roleid,userid);
		return list;
	}
	/**
	 * 根据商品和仓库获取商品的货位
	 * @param storageid
	 * @param goodsid
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Nov 23, 2017
	 */
	public String getItemnoByGoodsAndStorage(String goodsid,String storageid) {
		StorageItemGoods storageItemGoods=storageItemMapper.getStorageItemGoods(goodsid,storageid);
		if(storageItemGoods!=null){
			return storageItemGoods.getItemno();
		}else{
			GoodsInfo goodsInfo=baseGoodsMapper.getGoodsInfo(goodsid);
			if(null != goodsInfo){
				return goodsInfo.getItemno();
			}else{
				return null;
			}

		}
	}

}

