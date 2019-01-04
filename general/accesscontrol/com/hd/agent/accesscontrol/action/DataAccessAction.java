/**
 * @(#)DataAccessAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-20 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.Datarule;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.accesscontrol.service.IDataAccessService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.system.model.TableInfo;

/**
 * 
 * 数据权限管理
 * @author chenwei
 */
public class DataAccessAction extends BaseAction {
	/**
	 * 数据权限
	 */
	private Datarule datarule;
	
	private IDataAccessService dataAccessService;
	
	public IDataAccessService getDataAccessService() {
		return dataAccessService;
	}

	public void setDataAccessService(IDataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
	
	public Datarule getDatarule() {
		return datarule;
	}

	public void setDatarule(Datarule datarule) {
		this.datarule = datarule;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3066771992321761296L;
	
	/**
	 * 显示数据权限管理页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-20
	 */
	public String showDataPage() throws Exception{
		return "success";
	}
	/**
	 * 显示数据权限信息添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-20
	 */
	public String showDataruleAddPage() throws Exception{
		return "success";
	}
	/**
	 * 添加数据权限
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-21
	 */
	@UserOperateLog(key="CommonShortcut",type=2,value="")
	public String addDatarule() throws Exception{
		SysUser sysUser = getSysUser();
		datarule.setAdduserid(sysUser.getUserid());
		boolean flag = dataAccessService.addDatarule(datarule);
		addJSONObject("flag", flag);
		//添加日志内容
		addLog("添加数据权限 编号:"+datarule.getDataid(),flag);
		return "success";
	}
	/**
	 * 显示数据权限资源规则列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-21
	 */
	public String showDataruleList() throws Exception{
		List list = dataAccessService.showDataruleList();
		addJSONArray(list);
		return "success";
	}
	/**
	 * 删除数据权限资源规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-22
	 */
	@UserOperateLog(key="CommonShortcut",type=4,value="")
	public String deleteDatarule() throws Exception{
		String dataid = request.getParameter("dataid");
		boolean flag = dataAccessService.deleteDatarule(dataid);
		addJSONObject("flag", flag);
		
		//添加日志内容
		addLog("删除数据权限 编号:"+dataid,flag);
		return "success";
	}
	/**
	 * 显示数据权限修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2012-12-26
	 */
	public String showDataruleEditPage() throws Exception{
		String dataid = request.getParameter("dataid");
		Datarule datarule = dataAccessService.showDataruleInfo(dataid);
		request.setAttribute("datarule", datarule);
		return "success";
	}
	/**
	 * 修改数据权限规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-7
	 */
	@UserOperateLog(key="CommonShortcut",type=3,value="")
	public String editDatarule() throws Exception{
		SysUser sysUser = getSysUser();
		datarule.setModifyuserid(sysUser.getUserid());
		boolean flag = dataAccessService.editDatarule(datarule);
		addJSONObject("flag", flag);
		
		//添加日志内容
		addLog("修改数据权限 编号:"+datarule.getDataid(),flag);
		return "success";
	}
	/**
	 * 验证该资源是否配置了数据权限规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2013-1-9
	 */
	public String checkDataruleTable() throws Exception{
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		if(!"2".equals(type)){
			//处理页面传递过来的表名
			name = CommonUtils.tablenameDealWith(name);
		}
		boolean flag = dataAccessService.checkDataruleTable(name);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 根据用户编号获取用户的数据权限列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public String getDataruleListByUserid() throws Exception{
		String userid = request.getParameter("userid");
		List list = dataAccessService.getDataruleListByUserid(userid);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 根据用户编号添加数据权限
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public String showDataruleAddPageByUserid() throws Exception{
		String userid = request.getParameter("userid");
		request.setAttribute("userid", userid);
		return "success";
	}
	/**
	 * 获取已经启用的数据权限列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public String getDataruleOpneList() throws Exception{
		String type = request.getParameter("type");
		List list = dataAccessService.getDataruleOpneList(type);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示修改用户数据权限列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月19日
	 */
	public String showDataruleEditPageByUserid() throws Exception{
		String userid = request.getParameter("userid");
		String dataid = request.getParameter("dataid");
		Datarule datarule = dataAccessService.showDataruleInfo(dataid);
		request.setAttribute("datarule", datarule);
		request.setAttribute("userid", userid);
		return "success";
	}

    /**
     * 显示菜单数据权限配置页面
     * @return
     * @throws Exception
     * @author chenwei
     */
    public String showMenuDataRulePage() throws  Exception{
        return SUCCESS;
    }

    /**
     * 根据用户编号和数据权限表名 获取数据权限配置规则
     * @return
     * @throws Exception
     */
    public String getDataRuleInfoByTablenameAndUserid() throws  Exception{
        String userid = request.getParameter("userid");
        String tablename = request.getParameter("tablename");
        Datarule datarule = dataAccessService.getDataRuleInfoByTablenameAndUserid(userid, tablename);
        if(null!=datarule){
            addJSONObject("datarule",datarule);
        }else{
            datarule = new Datarule();
            datarule.setTablename(tablename);
            datarule.setUserid(userid);
            TableInfo tableInfo =  getBaseDataDictionaryService().showTableInfo(tablename);
            if(null!=tableInfo){
                datarule.setDataname(tableInfo.getTabledescname());
            }
            addJSONObject("datarule", datarule);
        }
        return SUCCESS;
    }
    /**
     * 添加或者修改数据权限规则
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2013-1-7
     */
    @UserOperateLog(key="CommonShortcut",type=3,value="")
    public String addOrUpdateDatarule() throws Exception{
        SysUser sysUser = getSysUser();
        if(StringUtils.isEmpty(datarule.getDataid())){
            datarule.setAdduserid(sysUser.getUserid());
            boolean flag = dataAccessService.addDatarule(datarule);
            addJSONObject("flag", flag);
            //添加日志内容
            addLog("添加数据权限 编号:"+datarule.getDataid(),flag);
        }else{
            datarule.setModifyuserid(sysUser.getUserid());
            boolean flag = dataAccessService.editDatarule(datarule);
            addJSONObject("flag", flag);
            //添加日志内容
            addLog("修改数据权限 编号:"+datarule.getDataid(),flag);
        }
        return "success";
    }
}

