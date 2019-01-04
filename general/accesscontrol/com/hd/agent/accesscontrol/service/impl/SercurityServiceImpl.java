/**
 * @(#)SercurityServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-12 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.service.impl;

import com.hd.agent.accesscontrol.dao.AccessControlMapper;
import com.hd.agent.accesscontrol.dao.SysUserMapper;
import com.hd.agent.accesscontrol.model.*;
import com.hd.agent.accesscontrol.service.ISecurityService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.system.model.SysParam;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * ISecurityService实现类
 * @author chenwei
 */
public class SercurityServiceImpl extends BaseServiceImpl implements ISecurityService{
    /**
     * 权限控制dao
     */
    private AccessControlMapper accessControlMapper;

    private SysUserMapper sysUserMapper;

    private SessionRegistry sessionRegistry;

    public SessionRegistry getSessionRegistry() {
        return sessionRegistry;
    }
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }
    public AccessControlMapper getAccessControlMapper() {
        return accessControlMapper;
    }
    public void setAccessControlMapper(AccessControlMapper accessControlMapper) {
        this.accessControlMapper = accessControlMapper;
    }

    public SysUserMapper getSysUserMapper() {
        return sysUserMapper;
    }
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }
    /**
     * 获取全部权限与资源的对应关系
     */
    public Map<String, Collection<ConfigAttribute>> getResourceMap()
            throws Exception {
        Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        //获取所有权限列表
        List<String> authorityList = accessControlMapper.getAllAuthorityList();
        for(String authorityid:authorityList){
            ConfigAttribute ca = new SecurityConfig(authorityid);
            List<String> list = accessControlMapper.getOperateListFromAuthority(authorityid);
            for (String url : list) {
                //判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。
                if (resourceMap.containsKey(url)) {
                    Collection<ConfigAttribute> value = resourceMap.get(url);
                    value.add(ca);
                    resourceMap.put(url, value);
                } else {
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                    atts.add(ca);
                    resourceMap.put(url, atts);
                }
            }
        }
        //给系统创建虚拟权限 默认能访问全部有效的URL
        ConfigAttribute ca = new SecurityConfig("loginRole");
        List<String> list = accessControlMapper.getAllOperateList();
        for (String url : list) {
            //判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。
            if (resourceMap.containsKey(url)) {
                Collection<ConfigAttribute> value = resourceMap.get(url);
                value.add(ca);
                resourceMap.put(url, value);
            } else {
                Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                atts.add(ca);
                resourceMap.put(url, atts);
            }
        }
        return resourceMap;
    }

    @Override
    public Collection<GrantedAuthority> getUserAuthority(String username)
            throws Exception {
        Collection<GrantedAuthority> authoritys = new ArrayList<GrantedAuthority>();
        List<String> list = sysUserMapper.getUserAuthorityList(username);
        //用户岗位拥有的权限
        List<String> wjList = sysUserMapper.getUserAuthorityListByWorkjob(username);
        for(String authority:list){
            GrantedAuthorityImpl gai = new GrantedAuthorityImpl(authority);
            boolean flag = true;
            for(int i=0;i<wjList.size();i++){
                if(authority.equals(wjList.get(i))){
                    flag = false;
                    break;
                }
            }
            if(flag){
                authoritys.add(gai);
            }
        }
        for(String authority:wjList){
            GrantedAuthorityImpl gai = new GrantedAuthorityImpl(authority);
            authoritys.add(gai);
        }
        return authoritys;
    }
    @Override
    public List showOperListByMenu(String operateid) throws Exception {
        List list = accessControlMapper.showOperListByMenu(operateid);
        return list;
    }
    @Override
    public List showMenuList(String state) throws Exception {
        List list = accessControlMapper.showMenuList(state);
        return list;
    }

    @Override
    public List showMenuTreeByUserid(String state, String userid,String buttontype) throws Exception {
        List list = accessControlMapper.showMenuTreeByUserid(state,userid,buttontype);
        return list;
    }

    @Override
    public List showMenuTreeByRoleids(String state, String roleids) throws Exception {
        List list = accessControlMapper.showMenuTreeByRoleids(state,roleids);
        return list;
    }

    @Override
    public List showUserMenuTree(List authorityList,String easyuiThemeName) throws Exception{
        List indexList = accessControlMapper.showUserMenuTree(authorityList);
        if("default2".equals(easyuiThemeName)){
            List list = new ArrayList();
            for(Object object : indexList){
                Operate indexOperate=(Operate)object;
                Operate operate=(Operate) BeanUtils.cloneBean(indexOperate);
                if("0".equals(operate.getPid())){/*新增大菜单后在此处添加*/
                    if("OA管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-oa.png");
                        operate.setIcon("image/default2/menu/menu-oa.png");
                    }else if("基础档案".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-base.png");
                        operate.setIcon("image/default2/menu/menu-base.png");
                    }else if("CRM管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-crm.png");
                        operate.setIcon("image/default2/menu/menu-crm.png");
                    }else if("采购管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-buy.png");
                        operate.setIcon("image/default2/menu/menu-buy.png");
                    }else if("销售管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-sales.png");
                        operate.setIcon("image/default2/menu/menu-sales.png");
                    }else if("库存管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-storage.png");
                        operate.setIcon("image/default2/menu/menu-storage.png");
                    }else if("配送管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-delivery.png");
                        operate.setIcon("image/default2/menu/menu-delivery.png");
                    }else if("应收管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-receipt.png");
                        operate.setIcon("image/default2/menu/menu-receipt.png");
                    }else if("应付管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-payable.png");
                        operate.setIcon("image/default2/menu/menu-payable.png");
                    }else if("财务管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-finance.png");
                        operate.setIcon("image/default2/menu/menu-finance.png");
                    }else if("报表管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-report.png");
                        operate.setIcon("image/default2/menu/menu-report.png");
                    }else if("费用管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-cost.png");
                        operate.setIcon("image/default2/menu/menu-cost.png");
                    }else if("厂方对接".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-cost.png");
                        operate.setIcon("image/default2/menu/menu-cost.png");
                    }else if("电商管理".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-eb.png");
                        operate.setIcon("image/default2/menu/menu-eb.png");
                    }else if("系统设置".equals(operate.getOperatename())){
                        operate.setImage("image/default2/menu/menu-sys.png");
                        operate.setIcon("image/default2/menu/menu-sys.png");
                    }
                }else if(!"0".equals(operate.getPid())){
                    operate.setImage("");
                }
                list.add(operate);
            }
            return list;
        }
        return indexList;
    }

    @Override
    public List showUserOperate(List authorityList, String easyuiThemeName) throws Exception {
        List list = accessControlMapper.showUserOperate(authorityList);
        return list;
    }

    @Override
    public List showOperateTree(String state) throws Exception{
        List list = accessControlMapper.showOperateTree(state);
        return list;
    }
    @Override
    public List showOperateChecked(String authorityid) throws Exception{
        List list =accessControlMapper.showOperateChecked(authorityid);
        return list;
    }
    @Override
    public List showMenuCloseList() throws Exception {
        List list = accessControlMapper.showMenuCloseList();
        return list;
    }
    @Override
    public List showMenuCloseListByMap(Map conditionMap) throws Exception {
        List list = accessControlMapper.showMenuCloseListByMap(conditionMap);
        return list;
    }
    @Override
    public boolean addOperate(Operate operate) throws Exception {
        int i = accessControlMapper.addOperate(operate);
        return i>0;
    }
    @Override
    public boolean deleteOperate(String operateid) throws Exception {
        //删除菜单
        int i = accessControlMapper.deleteOperate(operateid);
        //删除菜单下的页面操作
        accessControlMapper.deleteOperAfterMenu(operateid);
        return i>0;
    }
    @Override
    public boolean setOperateClose(String operateid) throws Exception {
        int i = accessControlMapper.closeOperate(operateid);
        closeOperateLeaf(operateid);
        return i>0;
    }
    /**
     * 关闭子节点
     * @param operateid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Sep 11, 2013
     */
    public boolean closeOperateLeaf(String operateid) throws Exception{
        boolean flag = true;
        List<Operate> list = accessControlMapper.getOperateListByoperateid(operateid);
        accessControlMapper.closeOperateBypid(operateid);
        if(null!=list && list.size()>0){
            for(Operate operate : list){
                closeOperateLeaf(operate.getOperateid());
            }
        }else{
            flag = false;
        }
        return flag;
    }
    @Override
    public boolean setOperateOpen(String operateid) throws Exception {
        int i = accessControlMapper.openOperate(operateid);
        return i>0;
    }

    @Override
    public boolean setOperateOpenByIds(String operateidarrs) throws Exception {
        int i = accessControlMapper.openOperateByIds(operateidarrs);
        return i>0;
    }

    @Override
    public boolean setOperateCloseByIds(String operateidarrs) throws Exception {
        int i = accessControlMapper.openOperate(operateidarrs);
        return i>0;
    }
    @Override
    public Operate showOperateInfo(String operateid) throws Exception {
        Operate operate = accessControlMapper.showOperateInfo(operateid);
        return operate;
    }
    @Override
    public boolean editOperate(Operate operate) throws Exception {
        int i = accessControlMapper.editOperate(operate);
        return i>0;
    }
    @Override
    public List showAuthorityList(String state) throws Exception {
        String dataSql = getDataAccessRule("t_ac_authority", null);
        List list =accessControlMapper.showAuthorityList(state,dataSql);
        return list;
    }
    @Override
    public int getAuthorityNameCount(String name) throws Exception{
        int i = accessControlMapper.getAuthorityNameCount(name);
        return i;
    }
    @Override
    public String getAuthorityid() throws Exception{
        String authorityid = accessControlMapper.getAuthorityid();
        return authorityid;
    }
    @Override
    public boolean addAuthority(Authority authority, String operates,
                                String datarules) throws Exception {

        int i = accessControlMapper.addAuthority(authority);
        //添加权限与功能操作关联信息
        if(null!=operates&&!"".equals(operates.trim())){
            String[] operateArray = operates.split(",");
            for(String operateid : operateArray){
                accessControlMapper.addAuthOper(authority.getAuthorityid(), operateid, authority.getAdduserid());
            }
        }
        return i>0;
    }
    @Override
    public boolean closeAuthority(String authorityid) {
        int i = accessControlMapper.setAuthorityState(authorityid,"0");
        return i>0;
    }
    @Override
    public boolean openAuthority(String authorityid) throws Exception {
        int i = accessControlMapper.setAuthorityState(authorityid,"1");
        return i>0;
    }
    @Override
    public boolean deleteAuthority(String authorityid) throws Exception {
        int i = accessControlMapper.deleteAuthority(authorityid);
        accessControlMapper.deleteAuthOper(authorityid);
        return i > 0;
    }
    @Override
    public Authority showAuthorityInfo(String authorityid) throws Exception {
        Authority authority = accessControlMapper.showAuthorityInfo(authorityid);
        return authority;
    }
    @Override
    public boolean editAuthority(Authority authority, String operates,
                                 String datarules) throws Exception {
        int i = accessControlMapper.editAuthority(authority);
        //清除菜单功能按钮和数据权限与权限的关联信息
        accessControlMapper.deleteAuthOper(authority.getAuthorityid());
        //重新添加权限与功能操作关联信息
        if(null!=operates&&!"".equals(operates.trim())){
            String[] operateArray = operates.split(",");
            for(String operateid : operateArray){
                accessControlMapper.addAuthOper(authority.getAuthorityid(), operateid, authority.getAdduserid());
            }
        }
        return i>0;
    }
    @Override
    public boolean addAccessColumn(AccessColumn accessColumn,String authorityid) throws Exception {
        int i = accessControlMapper.addAccessColumn(accessColumn);
        accessControlMapper.addAuthorityCol(authorityid, accessColumn.getColumnid(), accessColumn.getAdduserid());
        return i>0;
    }
    @Override
    public List showAuthorityColumnList(String authorityid) throws Exception {
        List list = accessControlMapper.showAuthorityColumnList(authorityid);
        return list;
    }
    @Override
    public boolean deleteAccessColumn(String authorityid, String columnid)
            throws Exception {
        int i = accessControlMapper.deleteAuthorityColumn(authorityid, columnid);
        accessControlMapper.deleteAccessColumn(columnid);
        return i>0;
    }
    @Override
    public List showAccessColumnsByAuthoritys(List authorityList,
                                              String tablename) throws Exception {
        Map map = new HashMap();
        map.put("authorityList", authorityList);
        map.put("tablename", tablename);
        List list = accessControlMapper.showAccessColumnsByAuthoritys(map);
        return list;
    }
    @Override
    public List getAuthorityListByIds(String ids) throws Exception {
        if(null!=ids){
            String[] idArray = ids.split(",");
            List list = accessControlMapper.getAuthorityListByIds(idArray);
            return list;
        }else{
            return null;
        }
    }
    @Override
    public Operate getOperateByURL(String url) throws Exception {
        List list = accessControlMapper.getOperateByURL(url);
        if(null!=list && list.size()>0){
            return (Operate) list.get(0);
        }else{
            return null;
        }
    }
    @Override
    public String getAccessColumnSqlStr(String tablename, String alias)
            throws Exception {
        String sql = getAccessColumnList(tablename, alias);
        return sql;
    }
    @Override
    public String getAccessColumnSqlStrByTables(Map tableMap, Map columnMap)
            throws Exception {
        String sql = getAccessColumnListByTables(tableMap, columnMap);
        return sql;
    }
    @Override
    public List getOperateButtonsByAuthority(List authorityList)
            throws Exception {
        List list = accessControlMapper.getOperateButtonsByAuthority(authorityList);
        return list;
    }
    @Override
    public Authority getAuthorityListByName(String name) throws Exception {
        return accessControlMapper.getAuthorityListByName(name);
    }
    @Override
    public String getCompanyName() throws Exception {
        return getSysParamValue("COMPANYNAME");
    }
    @Override
    public boolean updateCompanyName(String name) throws Exception {
        SysParam sysParam = getBaseSysParamMapper().getSysParam("COMPANYNAME");
        sysParam.setPvalue(name);
        return getBaseSysParamMapper().updateSysParam(sysParam)>0;
    }
    @Override
    public UserLoginNum getUserLoginNum() throws Exception {
        UserLoginNum userLoginNum = new UserLoginNum();
        int phonenum = 0;
        int sysnum = 0;
        int totalnum = 0;
        //获取在线人员用户编号
        List<Object> list = sessionRegistry.getAllPrincipals();
        for (Object object : list) {
            UserDetails userDetails = (UserDetails) object;
            String username = userDetails.getUsername();
            if(username.startsWith("P_")){
                phonenum ++;
            }else{
                sysnum ++;
            }
            totalnum ++;
        }
        userLoginNum.setPhonenum(phonenum);
        userLoginNum.setSysnum(sysnum);
        userLoginNum.setTotalnum(totalnum);
        return userLoginNum;
    }
    @Override
    public boolean isLogin(String username) throws Exception {
        boolean flag = false;
        //获取在线人员用户编号
        List<Object> list = sessionRegistry.getAllPrincipals();
        for (Object object : list) {
            UserDetails userDetails = (UserDetails) object;
            String name = userDetails.getUsername();
            if(name.equals(username)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 保存菜单帮助内容
     *
     * @param opreateHelp
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveOperateHelp(OperateHelp opreateHelp) throws Exception {
        accessControlMapper.deleteOperateHelpByMD5(opreateHelp.getMd5url());
        return accessControlMapper.saveOperateHelp(opreateHelp)>0;
    }

    /**
     * 获取菜单帮助内容
     *
     * @param operateid
     * @return
     * @throws Exception
     */
    @Override
    public OperateHelp getOperateHelp(String operateid) throws Exception {
        return accessControlMapper.getOperateHelp(operateid);
    }

    /**
     * 获取菜单帮助内容
     *
     * @param md5url
     * @return
     * @throws Exception
     */
    @Override
    public OperateHelp getOperateHelpByMD5(String md5url) throws Exception {
        return accessControlMapper.getOperateHelpByMD5(md5url);
    }
}

