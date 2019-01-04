package com.hd.agent.system.service.impl;

import com.alibaba.druid.filter.config.ConfigTools;
import com.hd.agent.activiti.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.*;
import com.hd.agent.system.dao.BackupDBMapper;
import com.hd.agent.system.model.BackupDB;
import com.hd.agent.system.model.SysDataSource;
import com.hd.agent.system.service.IBackupDBService;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import javax.servlet.ServletContext;

/**
 * Created by wanghongteng on 2017/9/28.
 */
public class BackupDBServiceImpl extends BaseServiceImpl implements IBackupDBService {


    public BackupDBMapper backupDBMapper;

    public BackupDBMapper getBackupDBMapper() {
        return backupDBMapper;
    }

    public void setBackupDBMapper(BackupDBMapper backupDBMapper) {
        this.backupDBMapper = backupDBMapper;
    }

    @Override
    public BackupDB getBackupDB() throws Exception{
        return backupDBMapper.getBackupDBByConnectname("default");
    }


    @Override
    public List<Map> getBackupDBFileList() throws Exception{
        List<Map> list = new ArrayList<Map>();
        BackupDB backupDB=backupDBMapper.getBackupDBByConnectname("default");
        if(null!=backupDB){
            String path = backupDB.getFileurl(); // 路径
            File f = new File(path);
            File[] fl = f.listFiles();
            if(null!=fl){
                for (int i = 0; i < fl.length; i++) {
                    String filename = fl[i].getName();
                    if(".gz".equals(filename.substring(filename.length()-3,filename.length())) || ".zip".equals(filename.substring(filename.length()-4,filename.length())) ){
                        Map map = new HashMap();
                        map.put("filename",filename);
                        list.add(map);
                    }
                }
                Collections.sort(list,new Comparator<Map>(){
                    public int compare(Map arg0, Map arg1) {
                        return ((String)arg1.get("filename")).compareTo((String)arg0.get("filename"));
                    }
                });
            }
        }
        return  list;
    }


    @Override
    public Map saveBackup(BackupDB backupDB) throws Exception{
        Map map = new HashMap();
        backupDBMapper.deleteBackupDBByConnectname("default");
        backupDB.setConnectname("default");
        boolean flag = backupDBMapper.addBackupDB(backupDB)>0;
        map.put("flag",flag);
        return map;
    }

    @Override
    public Map doBackup() throws Exception{
        String jdbc_url="";
        String jdbc_port="";
        String jdbc_dbname="";
        Map properties = PropertiesUtils.readPropertiesFileNew(FileUtils.getClassPath() + "config.properties");
        String url = (String) properties.get("jdbc_url");
        Pattern pattern = Pattern.compile("//(.*?):(.*)/(.*)\\?");
        Matcher matcher = pattern.matcher(url);
        if(matcher.find( )) {
            jdbc_url = matcher.group(1);
            jdbc_port = matcher.group(2);
            jdbc_dbname = matcher.group(3);
        } else {
            System.out.println("NO MATCH");
        }
        String jdbc_username = (String) properties.get("jdbc_username");
        String jdbc_password = ConfigTools.decrypt((String) properties.get("jdbc_password"));
        BackupDB backupDB=backupDBMapper.getBackupDBByConnectname("default");
        Map map = new HashMap();
        boolean flag= false;
        String msg = "";
        int exnum=0;
        String filename=CommonUtils.getDataNumberSeconds();
        if(null!=backupDB){
            String OS = System.getProperty("os.name").toLowerCase();
            String dumpurl = "";
            if(OS.indexOf("windows")>=0){
                 dumpurl = FileUtils.getApplicationRealPath() + "/mysqldump/windows";
            }else if (OS.indexOf("linux")>=0){
                dumpurl= FileUtils.getApplicationRealPath() + "/mysqldump/linux";
            }
            String command = dumpurl +"/mysqldump -h "+jdbc_url+" -P "+jdbc_port+" -u"+jdbc_username+" -p"+ jdbc_password+" --default-character-set=utf8 --databases "+jdbc_dbname;
            try {
                Process process = Runtime.getRuntime().exec(command);
                InputStreamReader reader = new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8"));
                BufferedReader buffReader = new BufferedReader(reader);
                String inStr;
                String outStr;
                StringBuffer buffer = new StringBuffer();
                OutputStream os = new FileOutputStream(backupDB.getFileurl()+File.separator+filename+".sql");
                OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
                int linenum = 0;
                while ((inStr = buffReader.readLine()) != null) {
                    buffer.append(inStr + "\r\n");
                    if(linenum==10){
                        exnum++;
                        outStr = buffer.toString();
                        writer.write(outStr);
                        outStr="";
                        inStr="";
                        int  sb_length = buffer.length();// 取得字符串的长度
                        buffer.delete(0,sb_length);
                        linenum=0;
                    }
                    linenum++;
                    if(!flag){
                        flag=true;
                    }
                }
                writer.flush();
                reader.close();
                buffReader.close();
                os.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(flag){
                msg="手动备份成功!";
            }else{
                msg="手动备份失败!";
            }
        }else{
            msg="请先填写基础配置!";
        }

        map.put("flag",flag);
        map.put("msg",msg);
        map.put("filename",filename);
        return map;
    }

    public String getJdbcurl(String dburl,String dbname) throws  Exception{
        return "jdbc:mysql://"+dburl+":3306/"+dbname+"?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&noAccessToProcedureBodies=true";
    }

    @Override
    public Map doBackupFileToGzip(String filename) throws  Exception{
        BackupDB backupDB=backupDBMapper.getBackupDBByConnectname("default");
        try {
            File file = new File(backupDB.getFileurl()+File.separator+filename+".gz");
            FileOutputStream fout = new FileOutputStream(file);
            BufferedInputStream bin = null;
            ZipOutputStream zout = new ZipOutputStream(fout);

            //备份数据库备份文件
            File fin = new File(backupDB.getFileurl()+File.separator+filename+".sql");
            zout.putNextEntry(new ZipEntry(filename+".sql"));
            FileInputStream in = new FileInputStream(fin);
            byte[] bb = new byte[10240];
            int aa = 0;
            while ((aa = in.read(bb)) != -1) {
                zout.write(bb, 0, aa);
            }
            in.close();

            //删除源文件
            fin.delete();

            //备份upload文件夹
            String path = OfficeUtils.getFilepath(); // 路径
            File f = new File(path);
            directoryZip(zout,f,"upload");
            zout.close();
//
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("压缩成功！");
        Map map = new HashMap();
        return map;
    }


    /**
     * 添加到压缩文件中
     * @param out
     * @param f
     * @param base
     * @throws Exception
     */
    private void directoryZip(ZipOutputStream out, File f, String base) throws Exception {
        // 如果传入的是目录
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            // 创建压缩的子目录
            out.putNextEntry(new ZipEntry(base + File.separator));
            if (base.length() == 0) {
                base = "";
            } else {
                base = base + File.separator;
            }
            for (int i = 0; i < fl.length; i++) {
                directoryZip(out, fl[i], base + fl[i].getName());
            }
        } else {
            // 把压缩文件加入rar中
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            byte[] bb = new byte[10240];
            int aa = 0;
            while ((aa = in.read(bb)) != -1) {
                out.write(bb, 0, aa);
            }
            in.close();
        }
    }

    @Override
    public Map deleteExpireFile() throws  Exception{
        Map map = new HashMap();
        BackupDB backupDB=backupDBMapper.getBackupDBByConnectname("default");
        if(null!=backupDB){

            File files = null;
            String[] paths;

            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE,-backupDB.getSavedaynum());
            date=calendar.getTime();
            String dateStr = sf.format(date);

            try{
                files = new File(backupDB.getFileurl());

                paths = files.list();

                for(String path:paths)
                {
                    if(dateStr.compareTo(path)>0){
                        File file = new File(backupDB.getFileurl()+File.separator+path);
                        file.delete();
                    }
                }
            }catch(Exception e){
                // if any error occurs
                e.printStackTrace();
            }
        }
        map.put("flag",true);
        return map;
    }



    /**
     * 定时器自动备份
     * @param
     * @return java.lang.String
     * @throws
     * @author wanghongteng
     * @date 2017-9-28
     */
    @Override
    public void autoDoBackDB() throws Exception{
        Map map = doBackup();
        if((Boolean)map.get("flag")){
            doBackupFileToGzip((String)map.get("filename"));
            deleteExpireFile();
        }
    }
}
