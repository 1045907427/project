package com.hd.agent.phone.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.OfficeUtils;
import com.hd.agent.system.model.ReferWindow;
import com.hd.agent.system.model.ReferWindowColumn;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.service.IDataDictionaryService;
import com.hd.agent.system.service.IReferWindowService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 手机安装包压缩
 * User: chenwei
 * Date: 20160308
 */
public class PhoneCommonAction extends BaseFilesAction {

    public void b() throws Exception {
        String PhoneOutURL = getSysParamValue("PhoneOutURL");
        String PhoneInterURL = getSysParamValue("PhoneInterURL");
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNotEmpty(PhoneOutURL)){
            jsonObject.put("o",PhoneOutURL);
        }
        if(StringUtils.isNotEmpty(PhoneInterURL)){
            jsonObject.put("i",PhoneInterURL);
        }
        Project prj = new Project();

        String filepath = OfficeUtils.getFilepath();
        File zipFile = new File(filepath+"/agent"+System.currentTimeMillis()+".zip");

        Zip zip = new Zip();
        zip.setComment(jsonObject.toString());
        zip.setProject(prj);
        zip.setDestFile(zipFile);
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(new File(getUpdatePath()));
        fileSet.setIncludes("Agent.apk");
        //fileSet.setExcludes(...); 排除哪些文件或文件夹
        zip.addFileset(fileSet);
        zip.execute();

        String datestr = CommonUtils.dataToStr(new Date(), "yyyyMM");
        HttpServletResponse response = ServletActionContext.getResponse();
        InputStream is = new BufferedInputStream(new FileInputStream(zipFile));
        response.addHeader("Content-Length", "" + zipFile.length());
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(("Agent"+datestr+".zip").getBytes("UTF-8"), "ISO8859-1" )+"\"");
        PrintWriter os = response.getWriter();
        int i;
        while ((i = is.read()) != -1) {
            os.write(i);
        }

        is.close();
        os.flush();
        os.close();
        zipFile.delete();
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
                path = path + "/phone/download";
            } else if (path.indexOf("\\") != -1) {
                path = path + "\\phone\\download";
            }
        }
        String osName = System.getProperty("os.name");
        if ((osName != null) && (osName.toLowerCase().startsWith("windows"))) {
            path = path.replaceFirst("/", "");
        }
        return path;
    }

    /**
     * 下载手机端安装包，写入相关信息
     * @throws Exception
     */
    public void a() throws Exception{
        //原始apk的存放位置 这里有个坑 就是不能用已经重写过的文件
        String apkPath = getUpdatePath()+File.separator+"Agent.apk";
        File apk = new File(apkPath);
        FileInputStream is = new FileInputStream(apk);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int length = -1;

        //我们把文件的内容读出来
        byte[] cache = new byte[256];
        while ((length = is.read(cache)) != -1) {
            os.write(cache, 0, length);
        }
        byte[] copy = os.toByteArray();
        is.close();
        os.flush();
        os.close();

        //写在comment的头部
        //内容其实很随意 取你喜欢的名字就行 我这里用的是我gf的谐音
        byte[] magic = {0x52, 0x56, 0x0b, 0x0b};

        String PhoneOutURL = getSysParamValue("PhoneOutURL");
        String PhoneInterURL = getSysParamValue("PhoneInterURL");
        String baiduAK = getSysParamValue("baiduAK");
        String baiduYYServerid = getSysParamValue("baiduYYServerid");
        String isOpenBaiduYY = getSysParamValue("isOpenBaiduYY");
        String baiduYYHZ = getSysParamValue("baiduYYHZ");
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNotEmpty(PhoneOutURL)){
            jsonObject.put("o",PhoneOutURL);
        }
        if(StringUtils.isNotEmpty(PhoneInterURL)){
            jsonObject.put("i",PhoneInterURL);
        }
        if(StringUtils.isNotEmpty(baiduAK)){
            jsonObject.put("baiduAK",baiduAK);
        }
        if(StringUtils.isNotEmpty(baiduYYServerid)){
            jsonObject.put("baiduYYServerid",baiduYYServerid);
        }
        if(StringUtils.isNotEmpty(isOpenBaiduYY)){
            jsonObject.put("isOpenBaiduYY",isOpenBaiduYY);
        }
        if(StringUtils.isNotEmpty(baiduYYHZ)){
            jsonObject.put("baiduYYHZ",baiduYYHZ);
        }
        String flavor = jsonObject.toString();
        //渠道的长度
        byte[] content = flavor.getBytes();
        //渠道加上魔数的长度等于注释的长度
        short commentLength = (short) (content.length + magic.length);

        //末尾在存放整个的大小 方便之后文件指针的读取 所以真正的渠道号要再多两个字节
        commentLength += 2;

        //要用小端模式存放
        for (int i = 0; i < 2; ++i) {
            copy[copy.length - 2 + i] = (byte) (commentLength % 0xff);
            commentLength >>= 8;
        }
        //目的位置
        File outapk = new File(getUpdatePath()+File.separator+"Agent-release.apk");
        CommonUtils.fileChannelCopy(apk, outapk);
        FileOutputStream fileOutputStream = new FileOutputStream(outapk);
        //先是存放的原始内容
        fileOutputStream.write(copy);
        //存放的是魔数
        fileOutputStream.write(magic);
        //写入内容
        fileOutputStream.write(content);
        //再把长度信息添加到末尾
        for (int i = 0; i < 2; ++i) {
            fileOutputStream.write(copy[copy.length - 2 + i]);
        }

        fileOutputStream.flush();
        fileOutputStream.close();

        HttpServletResponse response = ServletActionContext.getResponse();
        InputStream iis = new BufferedInputStream(new FileInputStream(outapk));
        response.addHeader("Content-Length", "" + outapk.length());
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(("Agent-release.apk").getBytes("UTF-8"), "ISO8859-1" )+"\"");
        PrintWriter oos = response.getWriter();
        int i;
        while ((i = iis.read()) != -1) {
            oos.write(i);
        }

        iis.close();
        oos.flush();
        oos.close();
        outapk.delete();
    }


}
