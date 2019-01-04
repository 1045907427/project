package com.hd.agent.system.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.OfficeUtils;
import com.hd.agent.system.model.BackupDB;
import com.hd.agent.system.service.IBackupDBService;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongteng on 2017/9/28.
 */
public class BackupDBAction extends BaseAction {

    private BackupDB backupDB;

    public BackupDB getBackupDB() {
        return backupDB;
    }

    public void setBackupDB(BackupDB backupDB) {
        this.backupDB = backupDB;
    }

    private IBackupDBService backupDBService;

    public IBackupDBService getBackupDBService() {
        return backupDBService;
    }

    public void setBackupDBService(IBackupDBService backupDBService) {
        this.backupDBService = backupDBService;
    }

    /**
     * 显示数据库备份参数配置页面
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public String showBackupDBPage() throws Exception{
        BackupDB backupDB = backupDBService.getBackupDB();
        List list = backupDBService.getBackupDBFileList();
        String jsonStr = JSONUtils.listToJsonStr(list);
        request.setAttribute("detailList", jsonStr);
        request.setAttribute("backupDB",backupDB);
        return SUCCESS;
    }

    /**
     * 保存数据库备份参数配置页面
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public String saveBackup() throws Exception{
        Map result = new HashMap();

        result=backupDBService.saveBackup(backupDB);
        addJSONObject(result);

        return SUCCESS;
    }



    /**
     * 手动备份数据库
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public String doBackup() throws Exception{
        Map result = new HashMap();


        result=backupDBService.doBackup();
        addJSONObject(result);

        return SUCCESS;
    }
    /**
     * 手动备份数据库
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public String delete() throws Exception{
        String filename=request.getParameter("filename");
        Map result = new HashMap();
        result=backupDBService.deleteExpireFile();
        addJSONObject(result);

        return SUCCESS;
    }

    /**
     * 手动备份数据库
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    public String doBackupFileToGzip() throws Exception{
        String filename=request.getParameter("filename");
        Map result = new HashMap();
        result=backupDBService.doBackupFileToGzip(filename);
        addJSONObject(result);

        return SUCCESS;
    }



    /**
     * 文件下载
     * @throws Exception
     * @author zhengziyong
     * @date 2013-3-19
     */
    public void downloadDBFile() throws Exception{

        String fileName = request.getParameter("filename");
        BackupDB backupDB = backupDBService.getBackupDB();
        String fullPath = CommonUtils.getDownFilePhysicalpath(backupDB.getFileurl(),fileName);
        File file = new File(fullPath);
        if(null==file || !file.exists()){
            return;
        }
        InputStream is = new FileInputStream(file);
        if(null==is){
            return;
        }
//		if(!canOperate(attachFile)){
//			String tip = "您没有权限下载该文件！";
//			is = new ByteArrayInputStream(tip.getBytes());
//		}
        int len = -1;
        byte[] b = new byte[1024];
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        String userAgent = request.getHeader("User-Agent");
        //针对IE或者以IE为内核的浏览器：
        if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            //非IE浏览器的处理：
            fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream os = response.getOutputStream();
        while((len = is.read(b)) != -1){
            os.write(b, 0, len);
        }
        os.flush();
        is.close();
        os.close();
    }
}
