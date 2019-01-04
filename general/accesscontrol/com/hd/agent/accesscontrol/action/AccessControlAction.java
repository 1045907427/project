/**
 * @(#)AccessControlAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-14 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.action;

import com.hd.agent.accesscontrol.base.AccessControlMetadataSource;
import com.hd.agent.accesscontrol.model.*;
import com.hd.agent.accesscontrol.service.ISecurityService;
import com.hd.agent.accesscontrol.util.AccessUtils;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.MenuPropertiesUtils;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.model.TableInfo;
import com.hd.agent.system.service.IDataDictionaryService;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.*;

/**
 * 
 * 权限管理,权限配置等
 * @author chenwei
 */
public class AccessControlAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -622555295176378566L;
	/**
	 * 功能操作类
	 */
	private Operate operate;
    /**
     * 菜单帮助
     */
	private OperateHelp opreateHelp;
	/**
	 * 权限
	 */
	private Authority authority;
	/**
	 * 字段权限
	 */
	private AccessColumn accessColumn;
	/**
	 * 权限管理service
	 */
	private ISecurityService securityService;
	
	private IDataDictionaryService dataDictionaryService;
	
	public ISecurityService getSecurityService() {
		return securityService;
	}
	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}
	
	public Operate getOperate() {
		return operate;
	}
	public void setOperate(Operate operate) {
		this.operate = operate;
	}
	
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	
	public AccessColumn getAccessColumn() {
		return accessColumn;
	}
	public void setAccessColumn(AccessColumn accessColumn) {
		this.accessColumn = accessColumn;
	}
	
	public IDataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}
	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

    public OperateHelp getOpreateHelp() {
        return opreateHelp;
    }

    public void setOpreateHelp(OperateHelp opreateHelp) {
        this.opreateHelp = opreateHelp;
    }

    /**
	 * 显示菜单管理页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public String showMenuPage() throws Exception{
		return "success";
	}
	/**
	 * 获取菜单数json数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public String showMenuTree() throws Exception{
		String state = request.getParameter("state");
		String datarule = request.getParameter("datarule");
		if(null==state){
			state = "1";
		}
		List<Operate> list = securityService.showMenuList(state);
		List<Operate>  treeIndexList = new ArrayList();
		if(!MenuPropertiesUtils.getIsSuperAccess()) {
			for(Operate operate : list){
				if("0".equals(operate.getOperateid())){
					treeIndexList.add(operate);
				}else{
					Map<String, String> pMap = MenuPropertiesUtils.getpMap();
					boolean haveAccess =false;
					for(Map.Entry<String, String> vo : pMap.entrySet()){
						if(operate.getUrl().equals(vo.getValue())){
							haveAccess=true;
							break;
						}
					}
					if(haveAccess){
						treeIndexList.add(operate);
						treeIndexList.addAll(filterOperateButtonByOperateid(operate.getOperateid(), list));
					}
				}
			}
		}else{
			treeIndexList=list;
		}
		List treeList = new ArrayList();
		for(Operate operate : treeIndexList){
			MenuTree menuTree = new MenuTree();
			menuTree.setId(operate.getOperateid());
			menuTree.setpId(operate.getPid());
			menuTree.setIcon(operate.getImage());
			menuTree.setUrlStr(operate.getUrl());
			menuTree.setTablename(operate.getTablename());

//            if(null==operate.getPid() || "0".equals(operate.getPid())){
//                menuTree.setOpen("true");
//            }
			if("1".equals(operate.getType())){
				if("1".equals(datarule)){
					menuTree.setName(operate.getOperatename());
				}else{
					menuTree.setName(operate.getOperatename()+"["+operate.getSeq()+"]");
				}
			}else{
				if("1".equals(datarule)){
					menuTree.setName(operate.getOperatename());
				}else{
					menuTree.setName(operate.getOperatename()+"["+operate.getSeq()+"]");
				}
				if(null==operate.getPid()){
					menuTree.setOpen("true");
				}
			}
			treeList.add(menuTree);
		}
		addJSONArray(treeList);
		return "success";
	}

    /**
     * 获取用户的菜单树
     * @return
     * @throws Exception
     */
    public String showMenuTreeByUserid() throws Exception{
        String userid = request.getParameter("userid");
        String state = request.getParameter("state");
        String datarule = request.getParameter("datarule");
        String buttontype = request.getParameter("buttontype");
        if(null==state){
            state = "1";
        }
        List<Operate> list = securityService.showMenuTreeByUserid(state,userid,buttontype);
        List treeList = new ArrayList();
        for(Operate operate : list){
            MenuTree menuTree = new MenuTree();
            menuTree.setId(operate.getOperateid());
            menuTree.setpId(operate.getPid());
            menuTree.setIcon(operate.getImage());
            menuTree.setUrlStr(operate.getUrl());
            menuTree.setTablename(operate.getTablename());

//            if(null==operate.getPid() || "0".equals(operate.getPid())){
//                menuTree.setOpen("true");
//            }
            if("1".equals(operate.getType())){
                menuTree.setName(operate.getOperatename()+"[按钮]");
            }else{
                menuTree.setName(operate.getOperatename());
                if(null==operate.getPid()){
                    menuTree.setOpen("true");
                }
            }
            treeList.add(menuTree);
        }
        addJSONArray(treeList);
        return SUCCESS;
    }

    /**
     * 根据角色编号 获取菜单树
     * @return
     * @throws Exception
     */
    public String showMenuTreeByRoleids() throws Exception{
		String roleids = request.getParameter("roleids");
		String state = request.getParameter("state");
		if(null==state){
			state = "1";
		}
		List<Operate> list = securityService.showMenuTreeByRoleids(state,roleids);
		List<Operate>  treeIndexList = new ArrayList();
		if(!MenuPropertiesUtils.getIsSuperAccess()){
			for(Operate operate : list){
				if("0".equals(operate.getOperateid())){
					treeIndexList.add(operate);
				}else{
					Map<String, String> pMap = MenuPropertiesUtils.getpMap();
					boolean haveAccess =false;
					for(Map.Entry<String, String> vo : pMap.entrySet()){
						if(operate.getUrl().equals(vo.getValue())){
							haveAccess=true;
							break;
						}
					}
					if(haveAccess){
						treeIndexList.add(operate);
						treeIndexList.addAll(filterOperateButtonByOperateid(operate.getOperateid(), list));
					}
				}
			}
		}else{
			treeIndexList=list;
		}
		List treeList = new ArrayList();
		for(Operate operate : treeIndexList){
			MenuTree menuTree = new MenuTree();
			menuTree.setId(operate.getOperateid());
			menuTree.setpId(operate.getPid());
			menuTree.setIcon(operate.getImage());
			menuTree.setUrlStr(operate.getUrl());
			menuTree.setTablename(operate.getTablename());

//            if(null==operate.getPid() || "0".equals(operate.getPid())){
//                menuTree.setOpen("true");
//            }
			if("1".equals(operate.getType())){
				menuTree.setName(operate.getOperatename()+"[按钮]");
			}else{
				menuTree.setName(operate.getOperatename());
				if(null==operate.getPid()){
					menuTree.setOpen("true");
				}
			}
			treeList.add(menuTree);
		}
        addJSONArray(treeList);
        return SUCCESS;
    }
	/**
	 * 获取能适合easyui树的数据格式
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-24
	 */
	public String getMenuTreeForEasyui() throws Exception{
		List<Operate> list = securityService.showMenuList("1");
		List<Tree> treeList = new ArrayList();
		for(Operate operate : list){
			Tree tree = new Tree();
			tree.setId(operate.getOperateid());
			tree.setText(operate.getOperatename());
			tree.setParentid(operate.getPid());
			treeList.add(tree);
		}
		List tree = getTree(treeList);
		addJSONArray(tree);
		return "success";
	}
	/**
	 * 把list转成tree格式
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date 2013-1-8
	 */
	public List getTree(List<Tree> list){
		List treeList = new ArrayList();
		for(Tree tree : list){
			if(null==tree.getParentid()||"".equals(tree.getParentid())){
				getTableDataDictTreeChild(tree,list);
				treeList.add(tree);
			}
		}
		return treeList;
	}
	/**
	 * 递归查找子节点
	 * @param tree
	 * @param list
	 * @author chenwei 
	 * @date 2013-1-8
	 */
	public void getTableDataDictTreeChild(Tree tree,List<Tree> list){
		List childList = new ArrayList();
		for(Tree tree2 : list){
			if(tree.getId().equals(tree2.getParentid())){
				getTableDataDictTreeChild(tree2,list);
				childList.add(tree2);
			}
		}
		tree.setChildren(childList);
	}
	/**
	 * 获取菜单与操作树
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public String showOperateTree() throws Exception{
		String state = request.getParameter("state");
		if(null==state){
			state = "1";
		}
		List<Operate> list = securityService.showOperateTree(state);
		Map map = new HashMap();
		for(Operate operate : list){
			if("0".equals(operate.getType())){
				map.put(operate.getPid(), operate.getOperateid());
			}
		}
		List treeList = new ArrayList();
		for(Operate operate : list){
			MenuTree menuTree = new MenuTree();
			menuTree.setId(operate.getOperateid());
			menuTree.setpId(operate.getPid());
			menuTree.setIcon(operate.getImage());
			if("1".equals(operate.getType())){
				menuTree.setName(operate.getOperatename()+"[按钮]");
			}else{
				menuTree.setName(operate.getOperatename()+"[菜单]");
				if(null==operate.getPid()){
					menuTree.setOpen("true");
				}else{
					String operateid = (String) map.get(operate.getOperateid());
				}
			}
			menuTree.setIcon(operate.getImage());
			treeList.add(menuTree);
		}
		addJSONArray(treeList);
		return "success";
	}
	/**
	 * 获取选中的菜单与按钮树
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-20
	 */
	public String showOperateCheckedTree() throws Exception{
		String state = request.getParameter("state");
		if(null==state){
			state = "1";
		}
		String view = request.getParameter("view");
		String authorityid = request.getParameter("authorityid");
		//菜单与功能按钮列表
		List<Operate> list = securityService.showOperateTree(state);
		List<Operate>  treeIndexList = new ArrayList();
		if(!MenuPropertiesUtils.getIsSuperAccess()){
			for(Operate operate : list){
				if("0".equals(operate.getOperateid())){
					treeIndexList.add(operate);
				}else{
					Map<String, String> pMap = MenuPropertiesUtils.getpMap();
					boolean haveAccess =false;
					for(Map.Entry<String, String> vo : pMap.entrySet()){
						if(operate.getUrl().equals(vo.getValue())){
							haveAccess=true;
							break;
						}
					}
					if(haveAccess){
						treeIndexList.add(operate);
						treeIndexList.addAll(filterOperateButtonByOperateid(operate.getOperateid(), list));
					}
				}
			}
		}else{
			treeIndexList=list;
		}
		//权限已拥有的菜单和功能按钮列表
		List<Operate> operateCheckedList = securityService.showOperateChecked(authorityid);
		Map checkedMap = new HashMap();
		for(Operate operate : operateCheckedList){
			checkedMap.put(operate.getOperateid(), operate.getOperatename());
		}
		List treeList = new ArrayList();
		for(Operate operate : treeIndexList){
			MenuTree menuTree = new MenuTree();
			menuTree.setId(operate.getOperateid());
			menuTree.setpId(operate.getPid());
			menuTree.setIcon(operate.getImage());
			if("1".equals(operate.getType())){
				menuTree.setName(operate.getOperatename()+"[按钮]");
			}else{
				menuTree.setName(operate.getOperatename()+"[菜单]");
				if(null==operate.getPid()){
					menuTree.setOpen("true");
					menuTree.setIcon(operate.getImage());
				}
			}
			//根据权限已拥有的菜单和功能按钮列表
			//在整个菜单功能树上选中
			String operatename = (String) checkedMap.get(operate.getOperateid());
			if(null!=operatename){
				menuTree.setChecked("true");
				menuTree.setOpen("true");
			}
			if("view".equals(view)){
				menuTree.setChkDisabled(true);
			}
			treeList.add(menuTree);
		}
		addJSONArray(treeList);
		return "success";
	}
	/**
	 * 根据菜单编号获取菜单下面的页面操作列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public String showOperListByMenu() throws Exception{
		String operateid = request.getParameter("operateid");
		List list = securityService.showOperListByMenu(operateid);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示停用菜单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public String showMenuCloseList() throws Exception{
		String operateid = request.getParameter("operateid");
		Map map=CommonUtils.changeMap(request.getParameterMap());
		List list = securityService.showMenuCloseListByMap(map);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示菜单添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	public String showMenuAddPage() throws Exception{
		String pid = request.getParameter("pid");
		String pname = request.getParameter("pname");
		request.setAttribute("pid", pid);
		request.setAttribute("pname", pname);
		return "success";
	}
	/**
	 * 显示页面操作添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public String showOperAddPage() throws Exception{
		String pid = request.getParameter("pid");
		Operate operate = securityService.showOperateInfo(pid);
		request.setAttribute("pid", pid);
		if(null!=operate){
			request.setAttribute("pname", operate.getOperatename()+"["+operate.getSeq()+"]");
		}
		return "success";
	}
	/**
	 * 添加功能操作数据包括菜单与页面操作
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	@UserOperateLog(key="Accesscontrol",type=2,value="")
	public String addOperate() throws Exception{
		//获取当前登录用户信息
		SysUser sysUser = getSysUser();
		operate.setAdduserid(sysUser.getUserid());
		boolean flag = securityService.addOperate(operate);
		addJSONObject("flag", flag);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		
		//添加日志内容
		addLog("添加菜单或者按钮 编号"+operate.getOperateid(),flag);
		return "success";
	}
	/**
	 * 删除功能操作数据
	 * 包括菜单与页面操作
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-14
	 */
	@UserOperateLog(key="Accesscontrol",type=4,value="")
	public String deleteOperate() throws Exception{
		String operateid = request.getParameter("operateid");
		boolean flag = securityService.deleteOperate(operateid);
		addJSONObject("flag", flag);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		
		//添加日志内容
		addLog("删除菜单或者按钮 编号为"+operateid,flag);
		return "success";
	}
	/**
	 * 关闭功能操作(包括菜单与页面操作)
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="Accesscontrol",type=3,value="")
	public String closeOperate() throws Exception{
		String operateids = request.getParameter("operateid");
		String  operateidArr[] = operateids.split(",");
		for(String operateid : operateidArr ){
			boolean flag = securityService.setOperateClose(operateid);
			addJSONObject("flag", flag);
			//对功能权限相关进行操作后，更新权限与URL的关系
			AccessControlMetadataSource.refresh();

			//添加日志内容
			addLog("关闭菜单或者按钮 编号为"+operateid,flag);
		}
		return "success";
	}
	/**
	 * 开启功能操作(包括菜单与页面操作)
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="Accesscontrol",type=3,value="")
	public String openOperate() throws Exception{
		String operateid = request.getParameter("operateid");
		boolean flag = securityService.setOperateOpen(operateid);
		addJSONObject("flag", flag);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		
		//添加日志内容
		addLog("启用菜单或者按钮 编号为"+operateid,flag);
		return "success";
	}

	/**
	 * 可以批量开启功能操作(包括菜单与页面操作)
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="Accesscontrol",type=3,value="")
	public String openOperateMulti() throws Exception {
		String operateids = request.getParameter("operateidarrs");
		boolean flag = securityService.setOperateOpenByIds(operateids);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		//添加日志内容
		addLog("启用菜单或者按钮 编号为"+operateids,flag);
		Map map = new HashMap();
		map.put("flag",flag);
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 显示菜单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-15
	 */
	public String showMenuEditPage() throws Exception{
		String operateid = request.getParameter("operateid");
		Operate operate = securityService.showOperateInfo(operateid);
		Operate pOperate = securityService.showOperateInfo(operate.getPid());
		String pname = null;
		if(null!=pOperate){
			pname = pOperate.getOperatename();
		}
		request.setAttribute("operate", operate);
		request.setAttribute("pname", pname);
		return "success";
	}
	/**
	 * 显示页面操作修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-17
	 */
	public String showOperateEditPage() throws Exception{
		String operateid = request.getParameter("operateid");
		Operate operate = securityService.showOperateInfo(operateid);
		Operate pOperate = securityService.showOperateInfo(operate.getPid());
		String pname = null;
		if(null!=pOperate){
			pname = pOperate.getOperatename();
		}
		request.setAttribute("operate", operate);
		request.setAttribute("pname", pname);
		return "success";
	}
	/**
	 * 修改功能操作（菜单和页面操作）
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-17
	 */
	@UserOperateLog(key="Accesscontrol",type=3,value="")
	public String editOperate() throws Exception{
		SysUser sysUser = getSysUser();
		operate.setModifyuserid(sysUser.getUserid());
		boolean flag = securityService.editOperate(operate);
		addJSONObject("flag", flag);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		
		//添加日志内容
		addLog("修改菜单或者按钮 编号"+operate.getOperateid(),flag);
		return "success";
	}
	/**
	 * 显示角色管理页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-17
	 */
	public String showRolePage() throws Exception{
		return "success";
	}
	/**
	 * 获取权限列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-17
	 */
	public String showAuthorityList() throws Exception{
		List list = securityService.showAuthorityList(null);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 权限添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public String showAuthorityAddPage() throws Exception{
		String authorityid = securityService.getAuthorityid();
		request.setAttribute("authorityid", authorityid);
		return "success";
	}
	/**
	 * 添加权限
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="Accesscontrol",type=2,value="")
	public String addAuthority() throws Exception{
		String operates = request.getParameter("operates");
		String datarules = request.getParameter("datarules");
		SysUser sysUser = getSysUser();
		authority.setAdduserid(sysUser.getUserid());
		boolean flag = securityService.addAuthority(authority, operates, datarules);
		addJSONObject("flag", flag);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		
		//添加日志内容
		addLog("添加角色 编号:"+authority.getAuthorityid(),flag);
		return "success";
	}
	/**
	 * 停用权限
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="Accesscontrol",type=3,value="")
	public String closeAuthority() throws Exception{
		String authorityid = request.getParameter("authorityid");
		boolean flag = securityService.closeAuthority(authorityid);
		addJSONObject("flag", flag);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		
		//添加日志内容
		addLog("停用角色 编号:"+authorityid,flag);
		return "success";
	}
	/**
	 * 启用权限
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-20
	 */
	@UserOperateLog(key="Accesscontrol",type=3,value="")
	public String openAuthority() throws Exception{
		String authorityid = request.getParameter("authorityid");
		boolean flag = securityService.openAuthority(authorityid);
		addJSONObject("flag", flag);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		
		//添加日志内容
		addLog("启用角色 编号:"+authorityid,flag);
		return "success";
	}
	/**
	 * 删除权限
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="Accesscontrol",type=4,value="")
	public String deleteAuthority() throws Exception{
		String authorityid = request.getParameter("authorityid");
        Map map = new HashMap();
        boolean flag = false ;
        //判断是否被引用
        if(canTableDataDelete("t_ac_authority", authorityid)){
            flag = securityService.deleteAuthority(authorityid);
            map.put("flag", flag);
            map.put("isrecommend",false);
        }else{
            map.put("isrecommend",true);
        }
        addJSONObject(map);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		//添加日志内容
		addLog("删除角色 编号:"+authorityid,flag);
		return "success";
	}
	/**
	 * 显示权限修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-19
	 */
	public String showAuthorityEditPage() throws Exception{
		String authorityid = request.getParameter("authorityid");
		//页面类型 type=view时，权限详情页面
		String type = request.getParameter("type");
		Authority authority = securityService.showAuthorityInfo(authorityid);
		request.setAttribute("authority", authority);
		request.setAttribute("type", type);
		return "success";
	}
	/**
	 * 修改权限
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-20
	 */
	@UserOperateLog(key="Accesscontrol",type=3,value="")
	public String editAuthority() throws Exception{
		String operates = request.getParameter("operates");
		String datarules = request.getParameter("datarules");
		SysUser sysUser = getSysUser();
		authority.setModifyuserid(sysUser.getUserid());
		boolean flag = securityService.editAuthority(authority, operates, datarules);
		addJSONObject("flag", flag);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		
		//添加日志内容
		addLog("修改角色 编号:"+authority.getAuthorityid(),flag);
		return "success";
	}
	/**
	 * 显示字段权限添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-29
	 */
	public String showAccessColumnAddPage() throws Exception{
		String authorityid = request.getParameter("authorityid");
		String type = request.getParameter("type");
		request.setAttribute("authorityid", authorityid);
		request.setAttribute("type", type);
		return "success";
	}
	/**
	 * 添加字段权限
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-29
	 */
	public String addAccessColumn() throws Exception{
		String authorityid = request.getParameter("authorityid");
		SysUser sysUser = getSysUser();
		accessColumn.setAdduserid(sysUser.getUserid());
		//获取表字段列表
		Map queryMap = new HashMap();
		queryMap.put("tablename", accessColumn.getTablename());
		List<TableColumn> list = dataDictionaryService.getTableColumnListBy(queryMap);
		//根据列名 生成中文名称
		String[] colList = accessColumn.getCollist().split(",");
		String[] editColList = accessColumn.getEditcollist().split(",");
		String colListName = null;
		String editColListName = null;
		for(TableColumn column : list){
			for(String col : colList){
				if(col.equals(column.getColumnname())){
					if(null==colListName){
						colListName = column.getColchnname();
					}else{
						colListName += ","+column.getColchnname();
					}
					
				}
			}
			for(String col : editColList){
				if(col.equals(column.getColumnname())){
					if(null==editColListName){
						editColListName = column.getColchnname();
					}else{
						editColListName += ","+column.getColchnname();
					}
					
				}
			}
		}
		accessColumn.setCollistname(colListName);
		accessColumn.setEditcollistname(editColListName);
		boolean flag = securityService.addAccessColumn(accessColumn, authorityid);
		addJSONObject("flag", flag);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		return "success";
	}
	/**
	 * 获取权限拥有的字段权限列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public String showAuthorityColumnList() throws Exception{
		String authorityid = request.getParameter("authorityid");
		List list = securityService.showAuthorityColumnList(authorityid);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 删除字段权限
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public String deleteAccessColumn() throws Exception{
		String authorityid = request.getParameter("authorityid");
		String columnid = request.getParameter("columnid");
		boolean flag = securityService.deleteAccessColumn(authorityid, columnid);
		addJSONObject("flag", flag);
		//对功能权限相关进行操作后，更新权限与URL的关系
		AccessControlMetadataSource.refresh();
		return "success";
	}
	/**
	 * 根据权限获取权限未添加的字段权限表资源
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-30
	 */
	public String getUnAddTableList() throws Exception{
		//获取表列表
		Map queryMap = new HashMap();
		//表类型 1系统表2业务表
		queryMap.put("tabletype", "2");
		//使用状态，0停用，1启用
		queryMap.put("state", "1");
		List<TableInfo> list = dataDictionaryService.getTableInfoListBy(queryMap);
		//获取权限拥有的字段列表
		String authorityid = request.getParameter("authorityid");
		List<AccessColumn> columnList = securityService.showAuthorityColumnList(authorityid);
		List jsonList = new ArrayList();
		for(TableInfo tableInfo : list){
			boolean flag = true;
			for(AccessColumn accessColumn : columnList){
				if(tableInfo.getTablename().equals(accessColumn.getTablename())){
					flag = false;
					break;
				}
			}
			if(flag){
				Map map = new HashMap();
				map.put("id", tableInfo.getTablename());
				map.put("name", tableInfo.getTabledescname());
				jsonList.add(map);
			}
		}
		addJSONArray(jsonList);
		return "success";
	}
	/**
	 * 获取启用的权限列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-3
	 */
	public String showAuthorityOpenList() throws Exception{
		//获取启用的权限列表
		List list = securityService.showAuthorityList("1");
		addJSONArray(list);
		return "success";
	}
	/**
	 * 从文件夹中读取菜单图片名称列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-7
	 */
	public String showMenuImageList() throws Exception{
		String dirPath = servletContext.getRealPath("/")+"image\\menu\\";
		File filesDir = new File(dirPath);
        /** 取得代表目录中所有文件的File对象数组 */
        File list[] = filesDir.listFiles();
        List imageList = new ArrayList();
		for (int i = 0; i < list.length; i++) {
			if (list[i].isFile()) {
				imageList.add(list[i].getName());
			}
		}
		addJSONArray(imageList);
		return "success";
	}
	/**
	 * 验证权限（角色）别名是否重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-17
	 */
	public String checkAuthorityName() throws Exception{
		String name = request.getParameter("name");
		int i = securityService.getAuthorityNameCount(name);
		boolean flag = false;
		if(i==0){
			flag = true;
		}
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 获取当前用户不能点击的按钮列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 3, 2013
	 */
	public String getNotClickOperateButtonsByUser() throws Exception{
		List authorityList = getUserAuthorityList();
		List list = securityService.getOperateButtonsByAuthority(authorityList);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示系统权限设置页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月20日
	 */
	public String accessSetPage() throws Exception{
		return "success";
	}
	/**
	 * 设置系统授权
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月20日
	 */
	public String addAccessSet() throws Exception{
		String name = request.getParameter("name");
		String serializeid = request.getParameter("serializeid");
		String phonenum = request.getParameter("phonenum");
		String sysnum = request.getParameter("sysnum");
		String duedate = request.getParameter("duedate");
		String aesName = "";
		if(StringUtils.isNotEmpty(duedate)){
			duedate = duedate.replaceAll("-", "");
			aesName = AccessUtils.aesDecrypt(name+"_"+phonenum+"_"+sysnum+"_"+duedate);
		}else{
			aesName = AccessUtils.aesDecrypt(name+"_"+phonenum+"_"+sysnum);
		}
		boolean flag = false;
		if(serializeid.equals(aesName)){
			flag = AccessUtils.addAccessSet(CommonUtils.getTodayDataStr(), name, serializeid,phonenum,sysnum,duedate);
			flag = securityService.updateCompanyName(name);
		}
		addJSONObject("flag", flag);
		return "success";
	}

    /**
     * 显示帮助文档编辑页面
     * @return
     * @throws Exception
     */
	public String showOperateHelpPage() throws Exception{
	    String id = request.getParameter("id");
	    Operate operate = securityService.showOperateInfo(id);
	    if(null!=operate){
            String md5url = CommonUtils.MD5(operate.getUrl());
            request.setAttribute("title",operate.getOperatename());
            request.setAttribute("md5url",md5url);
            OperateHelp operateHelp = securityService.getOperateHelpByMD5(md5url);
            if(null!=operateHelp){
                request.setAttribute("title",operateHelp.getTitle());
                request.setAttribute("content",operateHelp.getContent());
            }
        }
	    request.setAttribute("id",id);
	    return "success";
    }

    /**
     * 保存帮助文档
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="Accesscontrol",type=3,value="")
    public String saveOperateHelp() throws Exception{
        if(StringUtils.isNotEmpty(opreateHelp.getOperateid())){
            Operate operate = securityService.showOperateInfo(opreateHelp.getOperateid());
            if(null!=operate){
                String url = operate.getUrl();
                String md5url = CommonUtils.MD5(url);
                opreateHelp.setMd5url(md5url);
            }
        }
        boolean flag = securityService.saveOperateHelp(opreateHelp);
        addJSONObject("flag",flag);
        addLog("菜单帮助保存 编号:"+opreateHelp.getOperateid(),flag);
	    return "success";
    }

    /**
     * 显示帮助文档编辑页面
     * @return
     * @throws Exception
     */
    public String showOperateHelpViewPage() throws Exception{
        String md5url = request.getParameter("md5url");
        OperateHelp operateHelp = securityService.getOperateHelpByMD5(md5url);
        if(null!=operateHelp){
            request.setAttribute("title",operateHelp.getTitle());
            request.setAttribute("content",operateHelp.getContent());
        }else{
            request.setAttribute("content","未找到帮助文档");
        }
        return "success";
    }

    /**
     * 显示帮助文档编辑页面
     * @return
     * @throws Exception
     */
    public String showOperateHelpEditPage() throws Exception{
        String md5url = request.getParameter("md5url");
        String title = request.getParameter("title");
        request.setAttribute("title",title);
        request.setAttribute("md5url",md5url);
        OperateHelp operateHelp = securityService.getOperateHelpByMD5(md5url);
        if(null!=operateHelp){
            request.setAttribute("content",operateHelp.getContent());
        }else{
            request.setAttribute("content","");
        }
        return "success";
    }

	/**
	 * 根据url获取该url下的资料列表
	 * @param name
	 * @param url
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<Operate> filterOperateByURL(String name, String url,List<Operate> list) throws Exception{
		List<Operate> dataList = new ArrayList<Operate>();
		String pid = null;
		for(Operate operate : list){
			if(url.equals(operate.getUrl())){
				pid = operate.getOperateid();
				if(StringUtils.isNotEmpty(name)){
					name += ">>"+operate.getOperatename();
				}else{
					name = operate.getOperatename();
				}
				break;
			}
		}
		if(StringUtils.isNotEmpty(pid)){
			for(Operate operate : list){
				if(pid.equals(operate.getPid())){
					operate.setNavigation(name);
					dataList.add(operate);
					List dataList1 = filterOperateByPid(operate.getNavigation(),pid, list);
					if(null!=dataList1){
						dataList.addAll(dataList1);
					}
				}
			}
		}
		HashSet hs = new HashSet(dataList);
		Iterator i = hs.iterator();
		List newDatalist = new ArrayList();
		while(i.hasNext()){
			newDatalist.add(i.next());
		}
		Collections.sort(newDatalist,new OperateComparator());
		return newDatalist;
	}
	public List<Operate> filterOperateByPid(String name,String pid,List<Operate> list) throws Exception{
		List<Operate> dataList = null;
		if(StringUtils.isNotEmpty(pid)){
			dataList = new ArrayList<Operate>();
			for(Operate operate : list){
				if(pid.equals(operate.getPid())){
					operate.setNavigation(name+"&nbsp;>>&nbsp;"+operate.getOperatename());
					dataList.add(operate);
					List dataList1 = filterOperateByPid(operate.getNavigation(),operate.getOperateid(), list);
					if(null!=dataList1){
						dataList.addAll(dataList1);
					}
				}
			}
		}
		return dataList;
	}


	/**
	 * 根据菜单编号获取该菜单下的所有按钮
	 * @param operateid
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<Operate> filterOperateButtonByOperateid(String operateid, List<Operate> list) throws Exception{
		List<Operate> dataList = new ArrayList<Operate>();
		for(Operate operate : list){
			if(operateid.equals(operate.getPid())&&"1".equals(operate.getType())){
				dataList.add(operate);
			}
		}
		return dataList;
	}
}

