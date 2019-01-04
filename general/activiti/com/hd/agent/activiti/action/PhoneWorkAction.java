/**
 * @(#)PhoneOaAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 4, 2015 limin 创建版本
 */
package com.hd.agent.activiti.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.*;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.OfficeUtils;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by limin on 2015/6/4.
 */
public class PhoneWorkAction extends BaseAction {

    private static Map<String, String> macros = new HashMap<String, String>();
    static {

        macros.put("sys_datetime", "yyyy-MM-dd HH:mm");
        macros.put("sys_date", "yyyy-MM-dd");
        macros.put("sys_date_cn", "yyyy年MM月dd日");
        macros.put("sys_date_cn_short1", "yyyy年MM月");
        macros.put("sys_date_cn_short4", "yyyy");
        macros.put("sys_date_cn_short3", "yyyy年");
        macros.put("sys_date_cn_short2", "MM月dd日");
        macros.put("sys_time", "HH:mm");
        macros.put("sys_week", "EEE");
    }

    /**
     * 手机上传的文件
     */
    private File file;

    private IAttachFileService attachFileService;

    public IAttachFileService getAttachFileService() {
        return attachFileService;
    }

    public void setAttachFileService(IAttachFileService attachFileService) {
        this.attachFileService = attachFileService;
    }

    /**
     * 手机工作流主页面
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 15, 2015
     */
    public String workListPage1() throws Exception {

        return SUCCESS;
    }

    /**
     * 办理中页面
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 15, 2015
     */
    public String workListPage2() throws Exception {

        Map map = new HashMap();
        map.put("type", "13");
        map.put("userid", getSysUser().getUserid());
        pageMap.setCondition(map);
        pageMap.setPage(1);
        pageMap.setRows(20);

        request.setAttribute("list", workService.getProcessData(pageMap).getList());

        pageMap.getCondition().clear();
        pageMap.setRows(9999);
        List definitions = definitionService.getDefinitionData(pageMap).getList();
        request.setAttribute("definitions", JSONUtils.listToJsonStr(definitions));

        return SUCCESS;
    }

    /**
     * 处理工作页面（跳转）
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 16, 2015
     */
    public String workHandlePage() throws Exception {

        String id = request.getParameter("id");
        String instanceid = request.getParameter("instanceid");
        String definitionkey = request.getParameter("definitionkey");
        String title = request.getParameter("title");
        String taskid = CommonUtils.nullToEmpty(request.getParameter("taskid"));

        Process process = null;
        SysUser user = getSysUser();

        if(StringUtils.isEmpty(definitionkey)) {

            process = workService.getProcess(id, "1");

            if(process == null) {

                process = workService.getProcess(instanceid, "2");
            }

            request.setAttribute("process", process);

            if(process == null) {

                request.setAttribute("exist", false);
                return SUCCESS;
            }

            title = process.getTitle();
            definitionkey = process.getDefinitionkey();
        }

        request.setAttribute("valid", true);
//        if(StringUtils.isNotEmpty(taskid) && process != null && !taskid.equals(process.getTaskid())) {
//
//            request.setAttribute("valid", false);
//            return SUCCESS;
//        }

        if(process != null) {

            // 是否会签
            boolean sign = definitionService.isSignTask(process.getDefinitionid(), process.getTaskkey());

            if(sign) {
                if(workService.isSignUser(process.getInstanceid(), taskid, user.getUserid()) == 0) {
                    request.setAttribute("valid", false);
                    return SUCCESS;
                }
            } else {
                if(!user.getUserid().equals(process.getAssignee())) {
                    request.setAttribute("valid", false);
                    return SUCCESS;
                }
            }

        }

        // 获取definition以及definitiontask设置
        Definition definition = null;
        if(process == null) {

            definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
        } else {

            definition = definitionService.getDefinitionByKey(definitionkey, process.getDefinitionid(), StringUtils.isEmpty(process.getDefinitionid()) ? "1" : null);
        }

        // instanceid为空时，新增工作
        if(StringUtils.isEmpty(instanceid)) {

            process = new Process();
            process.setDefinitionkey(definitionkey);
            process.setDefinitionname(definition.getName());
            process.setDefinitionid(definition.getDefinitionid());
            process.setTitle(title);
            process.setBusinessid(null);
            process.setJson(null);

            if(definition != null && StringUtils.isNotEmpty(definition.getFormkey())) {

                Form form = formService.getFormByKey(definition.getFormkey());
                process.setPhonehtml(form.getPhonehtml());
                process.setHtml(form.getDetail());
            }
        }

        // 接收工作
        workService.doHandleWork2(process);
        process = workService.getProcess(process.getId(), "1");

        String businessUrl = null;
        DefinitionTask task = definitionService.getDefinitionTask(process.getDefinitionkey(), process.getDefinitionid(), process.getTaskkey());
        if(task != null && StringUtils.isNotEmpty(task.getBusinessurl())) {
            businessUrl = task.getBusinessurl();
        }

        request.setAttribute("process", process);
        request.setAttribute("definition", definition);
        request.setAttribute("task", task);

        boolean sign = definitionService.isSignTask(process.getDefinitionid(), process.getTaskkey());

        String url = "act/phone/workFormPage.do";
        Object[] params = {/*sign ? "handle" : "view",*/
                process != null ? process.getId() : "",
                process != null ? process.getBusinessid() : "",
                CommonUtils.nullToEmpty(taskid),
                sign ? "1" : "0"};
        if(StringUtils.isNotEmpty(definition.getFormkey())) {

            url = "act/phone/workFormPage.do";

        } else {

            if(task != null && StringUtils.isNotEmpty(task.getBusinessurl())) {
                url = "../../" + task.getBusinessurl();
            } else {
                url = "../../" + definition.getBusinessurl();
            }
        }

        if(url.indexOf("?") > 0) {

            url = url + "&to=phone&type=handle&processid=%s&id=%s&taskid=%s&sign=%s";

        } else {

            url = url + "?to=phone&type=handle&processid=%s&id=%s&taskid=%s&sign=%s";
        }

        response.sendRedirect(String.format(url, params));
        return null;
    }

    /**
     * 工作处理页面footer
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 22, 2015
     */
    public String workHandleFooterPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 转交页面
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 22, 2015
     */
    public String commentAddPage() throws Exception {

        String id = request.getParameter("id");
        String taskid = CommonUtils.nullToEmpty(request.getParameter("taskid"));
        String sign = request.getParameter("sign");
        SysUser user = getSysUser();

        Process process = workService.getProcess(id, "1");
        request.setAttribute("process", process);

        request.setAttribute("valid", true);
        if("0".equals(sign) && !taskid.equals(process.getTaskid())) {

            request.setAttribute("valid", false);
            return "1".equals(sign) ? "sign" : SUCCESS;
        }

        if("1".equals(sign) && workService.isSignUser(process.getInstanceid(), taskid, user.getUserid()) == 0) {

            request.setAttribute("valid", false);
            return "1".equals(sign) ? "sign" : SUCCESS;
        }

        Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), process.getDefinitionid(), null);

        Map map = workService.getNextDefinitionTaskInfo(definition.getDefinitionid(), process.getTaskkey());

        List<Map> nexttasklist = new ArrayList<Map>();
        for(Map activity : (List<Map>) map.get("tasklist")) {

            Map temp = new HashMap();
            temp.put("id", activity.get("taskkey"));
            temp.put("name", activity.get("taskname"));
            temp.put("type", activity.get("type"));
            boolean taskIsSign = definitionService.isSignTask(process.getDefinitionid(), (String) activity.get("taskkey"));
            temp.put("sign", taskIsSign);

            Map next = workService.getNextTaskDefinition(definition.getDefinitionid(), id, (String) activity.get("taskkey"), process.getApplyuserid());

            temp.put("users", next);
            if(taskIsSign) {
                temp.put("next", workService.getNextTasksBySignTask(process.getInstanceid(), (String) activity.get("taskkey")));
            }
            nexttasklist.add(temp);
        }

        Comment comment = workService.getCommentByTask(taskid);
        request.setAttribute("nexttasklist", nexttasklist);
        request.setAttribute("next", JSONUtils.listToJsonStr(nexttasklist));
        request.setAttribute("user", user);
        request.setAttribute("time", CommonUtils.getDataNumber());
        request.setAttribute("comment", comment);
        request.setAttribute("process", process);
        return "1".equals(sign) ? "sign" : SUCCESS;
    }

    /**
     * 新建工作向导1
     * @return
     * @throws Exception
     * @author limin
     * @date Jul 16, 2015
     */
    public String workGuidePage() throws Exception {

        List list = definitionService.getDefinitionTree("aud");

        pageMap.setRows(9999);
        List definitions = definitionService.getDefinitionData(pageMap).getList();

        request.setAttribute("list", JSONUtils.listToJsonStr(list));
        request.setAttribute("definitions", JSONUtils.listToJsonStr(definitions));
        return SUCCESS;
    }

    /**
     * 新建工作向导2
     * @return
     * @throws Exception
     * @author limin
     * @date Jul 16, 2015
     */
    public String workGuidePage2() throws Exception {

        String definitionkey = request.getParameter("definitionkey");

        SysUser user = getSysUser();
        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String title = user.getDepartmentname()+ "-"+ user.getName()+ "-"+ definition.getName()+ "-"+ dateFormat.format(new Date());

        request.setAttribute("title", title);
        request.setAttribute("definition", definition);
        return SUCCESS;
    }

    /**
     * 驳回工作页面
     * @return
     * @throws Exception
     * @author limin
     * @date Jul 21, 2015
     */
    public String workBackPage() throws Exception {

        String id = request.getParameter("id");
        String taskid = request.getParameter("taskid");

        TaskDefinition taskDefinition = null;
        Process process = workService.getProcess(id, "1");
        String taskkey = process.getTaskkey();
        String definitionkey = process.getDefinitionkey();

        Comment c = workService.getCommentByTask(taskid);
        if(c == null){ //如果不存在该task的comment，则添加。
            c = new Comment();
            c.setTaskid(taskid);
            workService.addComment(c);
        }

        List<Comment> cList = workService.getRealCommentList(process.getId(), process.getInstanceid(), true, true);
        List<Map> commentMapList = new ArrayList<Map>();
        for(Comment comment : cList) {
            Map temp = new HashMap();
            temp.put("taskkey", comment.getTaskkey());
            temp.put("taskname", comment.getTaskname());
            temp.put("sign", definitionService.isSignTask(process.getDefinitionid(), comment.getTaskkey()));
            commentMapList.add(temp);
        }

        Definition definition = definitionService.getDefinitionByKey(definitionkey, process.getDefinitionid(), null);

        request.setAttribute("list", commentMapList);
        request.setAttribute("task", taskDefinition);
        request.setAttribute("process", process);
        request.setAttribute("comment", c);
        return SUCCESS;
    }

    /**
     * 工作查询页面
     * @return
     * @throws Exception
     * @author limin
     * @date Jul 21, 2015
     */
    public String workQueryPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 工作查看页面
     * @return
     * @throws Exception
     * @author limin
     * @date Jul 23, 2015
     */
    public String workViewPage() throws Exception {

        String id = CommonUtils.nullToEmpty(request.getParameter("id"));
        String processid = CommonUtils.nullToEmpty(request.getParameter("processid"));
        String instanceid = request.getParameter("instanceid");

        Process process = workService.getProcess(id, "1");

        if(process == null) {

            process = workService.getProcess(processid, "1");
        }

        if(process == null) {

            process = workService.getProcess(instanceid, "2");
        }

        if(process == null) {

            request.setAttribute("valid", false);
            return SUCCESS;
        }

        Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), process.getDefinitionid(), null);

        request.setAttribute("valid", true);
        request.setAttribute("process", process);
        request.setAttribute("definition", definition);

        String url = "act/phone/workFormPage.do";
        Object[] params = {process != null ? process.getBusinessid() : "", process != null ? process.getId() : ""};
        if(StringUtils.isNotEmpty(definition.getFormkey())) {

            url = "act/phone/workFormPage.do";

        } else {

            url = "../../" + definition.getBusinessurl();
        }

        if(url.indexOf("?") > 0) {

            url = url + "&to=phone&type=view&id=%s&processid=%s";

        } else {

            url = url + "?to=phone&type=view&id=%s&processid=%s";
        }

        response.sendRedirect(String.format(url, params));
        return null;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 12, 2015
     */
    public String workFormPage() throws Exception {

        String id = request.getParameter("processid");
        String taskid = request.getParameter("taskid");

        Process process = workService.getProcess(id, "1");
        Definition definition = definitionService.getDefinitionByKey(process.getDefinitionkey(), process.getDefinitionid(), null);
        Form form = formService.getFormByKey(definition.getFormkey());

        List items = formService.selectFormItemList(form.getUnkey());
        List rules = formService.selectFormRuleList(process.getDefinitionkey(), process.getDefinitionid(), process.getTaskkey());

        Map macro = new HashMap();
        for(String key :  macros.keySet()) {

            String format = (String) macros.get(key);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = new Date();

            String str = sdf.format(date);
            macro.put(key, str);
        }

        SysUser user = getSysUser();
        macro.put("sys_userid", user.getUserid());
        macro.put("sys_username", user.getName());
        macro.put("sys_deptid", user.getDepartmentid());
        macro.put("sys_deptname", user.getDepartmentname());

        // signature
        macro.put("sys_signature_name", user.getName());
        macro.put("sys_signature_date", CommonUtils.getTodayDataStr());

        Map editor = new HashMap();
        editor.put("macro", macro);

        // wdate 设定
        Map wdate = new HashMap();
        String today = CommonUtils.getTodayDataStr();
        String month01 = CommonUtils.getCurrentYearStr() + "-" + CommonUtils.getCurrentMonthStr() + "-01";
        String month31 = CommonUtils.getCurrentMonthLastDay();
        String year01 = CommonUtils.getCurrentYearStr() + "-01-01";
        String year365 = CommonUtils.getCurrentYearStr() + "-12-31";
        wdate.put("today", today);
        wdate.put("month01", month01);
        wdate.put("month31", month31);
        wdate.put("year01", year01);
        wdate.put("year365", year365);
        editor.put("wdate", wdate);

        request.setAttribute("editor", JSONUtils.mapToJsonStr(editor));
        request.setAttribute("macro", JSONUtils.mapToJsonStr(macro));
        request.setAttribute("wdate", JSONUtils.mapToJsonStr(wdate));

        request.setAttribute("process", process);
        request.setAttribute("json", CommonUtils.bytes2String(process.getJson()));
        request.setAttribute("definition", definition);
        request.setAttribute("form", form);
        if (process != null && process.getPhonehtml() != null && StringUtils.isNotEmpty(CommonUtils.bytes2String(process.getPhonehtml()))) {
            request.setAttribute("phonehtml", CommonUtils.bytes2String(process.getPhonehtml()));
        } else if (form != null && form.getPhonehtml() != null && StringUtils.isNotEmpty(CommonUtils.bytes2String(form.getPhonehtml()))) {
            request.setAttribute("phonehtml", CommonUtils.bytes2String(form.getPhonehtml()));
        }
        request.setAttribute("items", JSONUtils.listToJsonStr(items));
        request.setAttribute("rules", JSONUtils.listToJsonStr(rules));

        String title = process.getTitle().substring(process.getId().length() + 1);
        request.setAttribute("title", title);
        request.setAttribute("taskid", taskid);
        if(StringUtils.isNotEmpty(process.getTaskid())) {
            request.setAttribute("taskid", process.getTaskid());
        }

        return SUCCESS;
    }

    /**
     * 手机OA附件
     *
     * @return
     * @throws Exception
     * @author limin
     * @date May 28, 2016
     */
    public String workAttachPage() throws Exception {

        String processid = request.getParameter("processid");

        Map files = workService.listAttach(processid);

        request.setAttribute("files", files);
        return SUCCESS;
    }

    /**
     * 手机OA附件查看
     *
     * @return
     * @throws Exception
     * @author limin
     * @date May 28, 2016
     */
    public String workAttachViewPage() throws Exception {

        String id = request.getParameter("id");
        request.setAttribute("image", "");
        request.setAttribute("text", false);
        String content = "";        // 文本文件内容
        String charset = "UTF-8";   // 文本文件编码
        String information = "";

        AttachFile attach = attachFileService.getAttachFile(id);
        String ext = CommonUtils.nullToEmpty(attach.getExt()).toLowerCase();
        request.setAttribute("attach", attach);
        String WebOffice = getSysParamValue("WebOffice");
        if("1".equals(WebOffice) || "0".equals(WebOffice)) {
            // 图片
            if (".jpg".equals(ext) || ".jpeg".equals(ext) || ".bmp".equals(ext) || ".gif".equals(ext) || ".png".equals(ext)) {

                request.setAttribute("image", attach.getFullpath());

                // 文本
            } else if (".txt".equals(ext)) {

                request.setAttribute("text", true);
                String path = attach.getFullpath();
                //文件存放路径
                String filepath = OfficeUtils.getFilepath();
                filepath = filepath.replace("upload", "");
                String fullPath = filepath + path;
                File file = new File(fullPath);

                String charset2 = CommonUtils.getFileCharset(file);
                if (StringUtils.isNotEmpty(charset2)) {

                    charset = charset2;
                }

                FileInputStream fis = null;
                BufferedInputStream bis = null;
                byte[] bytes = new byte[1024];
                StringBuilder sb = new StringBuilder();

                try {

                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    int length = 0;
                    long total = 0L;

                    while ((length = bis.read(bytes)) > 0) {

                        total = total + length;
                        sb.append(new String(bytes, 0, length, charset));
                        if (total >= 1024 * 1024) {
                            information = "内容过多，无法全部显示！";
                            break;
                        }
                    }

                    content = new String(sb);
                    // content = content.replace(" ", "&nbsp;").replace("\r\n", "<br/>").replace("\n", "<br/>").replace("\r", "<br/>");
                    request.setAttribute("information", information);
                    request.setAttribute("content", content);

                } catch (Exception e) {

                } finally {

                    if (bis != null) {
                        bis.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                }
            }
            return SUCCESS;
        }else if("2".equals(WebOffice)){
            if(".xls".equals(ext)
                    || ".xlsx".equals(ext)
                    || ".doc".equals(ext)
                    || ".docx".equals(ext)
                    || ".pdf".equals(ext)
                    || ".pdf".equals(ext)
                    || ".pptx".equals(ext)
                    || ".ppt".equals(ext)){
                //永中在线文件查看API地址
                String YongZhongWebApi = getSysParamValue("YongZhongWebApi");
                if(StringUtils.isNotEmpty(YongZhongWebApi)){
                    String FileWebUrl = getSysParamValue("FileWebUrl");
                    if(!FileWebUrl.endsWith("/")){
                        FileWebUrl += "/";
                    }
                    YongZhongWebApi += "&url="+FileWebUrl+attach.getFullpath();
                    request.setAttribute("url",YongZhongWebApi);
                    response.sendRedirect(YongZhongWebApi);
//                    return "YongZhongWebSuccess";
                }
            }else if(".jpg".equals(ext) || ".gif".equals(ext) || ".png".equals(ext)
                    || ".bmp".equals(ext) || ".jpeg".equals(ext)){
                request.setAttribute("image", attach.getFullpath());
                return SUCCESS;
            }else{
                response.sendRedirect("../"+attach.getFullpath());
                return SUCCESS;
            }
        }
        return null;
    }

    /**
     * 该方法不做任何操作，只是为了保持session 不超时
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Jul 18, 2016
     */
    public String connect() throws Exception {

        addJSONObject(new HashMap());
        return SUCCESS;
    }

    /**
     * 手机审批意见list
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 18, 2016
     */
    public String commentListPage() throws Exception {

        String processid = request.getParameter("processid");
        Process process = workService.getProcess(processid, "1");
        request.setAttribute("process", process);

        if(process == null) {
            return SUCCESS;
        }

        List<Comment> comments = workService.getCommentListByProcessid(processid);
        request.setAttribute("comments", comments);
        return SUCCESS;
    }
}
