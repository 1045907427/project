/**
 * @(#)UpdateDBServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月20日 chenwei 创建版本
 */
package com.hd.agent.system.service.impl;

import com.alibaba.druid.filter.config.ConfigTools;
import com.hd.agent.accesscontrol.base.AccessControlMetadataSource;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.PropertiesUtils;
import com.hd.agent.system.dao.UpdateDBMapper;
import com.hd.agent.system.model.UpdateDB;
import com.hd.agent.system.service.IUpdateDBService;
import org.apache.ibatis.annotations.Update;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;

import java.io.File;
import java.util.*;

/**
 * 
 * 执行更新数据库的操作
 * @author chenwei
 */
public class UpdateDBServiceImpl extends BaseServiceImpl implements
		IUpdateDBService {
    private static final Logger logger = Logger.getLogger(Logger.class);
	/**
	 * 数据库更新日志dao
	 */
	private UpdateDBMapper updateDBMapper;
	
	public UpdateDBMapper getUpdateDBMapper() {
		return updateDBMapper;
	}
	public void setUpdateDBMapper(UpdateDBMapper updateDBMapper) {
		this.updateDBMapper = updateDBMapper;
	}

	@Override
	public boolean updateDB() throws Exception {
		//判断是否存在t_sys_update_db表 不存在则创建该表
		int i = updateDBMapper.isHasUpdateDB();
		if(i==0){
			updateDBMapper.createUpdateDB();
		}
		String path = getUpdatePath();
		//获取更新文件信息
		//获取需要更新的文件 并且按文件名称排序
		File file = new File(path);   
        File[] array = file.listFiles();
        List<File> fileList = new ArrayList<File>();
        for (File f : array) {
            fileList.add(f);
        }
        //对文件进行排序
        Collections.sort(fileList,new FileComparator());
        //获取数据库配置信息
		Map map = PropertiesUtils.readPropertiesFileNew(getClassPath()+"config.properties");
		String driverClassName = (String) map.get("driverClassName");
		String url = (String) map.get("jdbc_url");
		String username = (String) map.get("jdbc_username");
		String passwordEncrypt = (String) map.get("jdbc_password");
		//密码解密
		String  password = ConfigTools.decrypt(passwordEncrypt);
		
        for(File fileObject :fileList){
        	String fileName = fileObject.getName();
			String updateSql = path+fileName;
			//判断该文件是否执行过
			UpdateDB updateDB = updateDBMapper.getUpdateDBByName(fileName);
			if(null==updateDB){
                boolean flag = true;
                try {
                    SQLExec sqlExec = new SQLExec();
                    // 设置数据库参数
                    sqlExec.setDriver(driverClassName);
                    sqlExec.setUrl(url);
                    sqlExec.setUserid(username);
                    sqlExec.setPassword(password);

                    sqlExec.setEncoding("UTF-8");
                    sqlExec.setSrc(new File(updateSql));
                    // 要指定这个属性，不然会出错
                    sqlExec.setProject(new Project());
                    //设定分隔符 sql文件中 多条sql语句 以$ 分割
                    sqlExec.setDelimiter("￥");
                    sqlExec.setKeepformat(true);
                    sqlExec.execute();
                }catch (Exception e){
                    flag = false;
                    logger.error("脚本:"+fileName,e);
                }
				if(flag){
                    UpdateDB addUpdateDB = new UpdateDB();
                    addUpdateDB.setName(fileName);
                    updateDBMapper.addUpdateDBLog(addUpdateDB);
                }

			}
        }
        //防止菜单按钮添加后 未加入到权限控制中。 重新刷新菜单权限
        //对功能权限相关进行操作后，更新权限与URL的关系
        AccessControlMetadataSource.refresh();
		return false;
	}
	
	/**
	 * 获取更新文件的路径
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年8月20日
	 */
	public String getUpdatePath() throws Exception{
		String path = getClass().getClassLoader().getResource("").getPath().replaceAll("%20", " ");
		int pos = -1;
		if (path.indexOf("/") != -1) {
			pos = path.indexOf("/WEB-INF/");
		} else if (path.indexOf("\\") != -1) {
			pos = path.indexOf("\\WEB-INF\\");
		}
		if (pos != -1) {
			path = path.substring(0, pos);
			if (path.indexOf("/") != -1) {
				path = path + "/updateDB/";
			} else if (path.indexOf("\\") != -1) {
				path = path + "\\updateDB\\";
			}
		}
		String osName = System.getProperty("os.name");
		if ((osName != null) && (osName.toLowerCase().startsWith("windows"))) {
			path = path.replaceFirst("/", "");
		}
	    return path;
	}
	/**
	 * 获取class路径
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年8月20日
	 */
	public String getClassPath() throws Exception{
		String path = getClass().getClassLoader().getResource("").getPath().replaceAll("%20", " ");
		int pos = -1;
		if (path.indexOf("/") != -1) {
			pos = path.indexOf("/WEB-INF/");
		} else if (path.indexOf("\\") != -1) {
			pos = path.indexOf("\\WEB-INF\\");
		}
		if (pos != -1) {
			path = path.substring(0, pos);
			if (path.indexOf("/") != -1) {
				path = path + "/WEB-INF/classes/";
			} else if (path.indexOf("\\") != -1) {
				path = path + "\\WEB-INF\\classes\\";
			}
		}
		String osName = System.getProperty("os.name");
		if ((osName != null) && (osName.toLowerCase().startsWith("windows"))) {
			path = path.replaceFirst("/", "");
		}
	    return path;
	}
	@Override
	public PageData getUpdateDBPageListData(PageMap pageMap) throws Exception{
		List<UpdateDB> list=updateDBMapper.getUpdateDBPageList(pageMap);
		int itotal=updateDBMapper.getUpdateDBCount(pageMap);
		PageData pageData = new PageData(itotal,list,pageMap);
		return pageData;
	}
}

