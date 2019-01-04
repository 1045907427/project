/**
 * @(#)FormAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 29, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.action;

import java.io.*;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.*;
import com.hd.agent.common.util.JSONUtils;
import org.apache.commons.lang.StringEscapeUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 *
 * @author zhengziyong
 */
public class FormAction extends BaseAction {

    private FormType formType;
    private Form form;

    private File file;

    public FormType getFormType() {
        return formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 自定义表单页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 29, 2013
     */
    public String formPage() throws Exception{

        return SUCCESS;
    }

    /**
     * 获取表单分类树
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 29, 2013
     */
    public String getFormTypeTree() throws Exception{
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        List<FormType> typeList = formService.getFormTypeList();
        Map<String, String> first = new HashMap<String, String>();
        first.put("id", "");
        first.put("name", "表单分类");
        first.put("key", "");
        first.put("open", "true");
        result.add(first);
        for(FormType type: typeList){
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", type.getId());
            map.put("pid", type.getPid() == null?"":type.getPid());
            map.put("name", type.getName());
            map.put("key", type.getUnkey());
            result.add(map);
        }
        addJSONArray(result);
        return SUCCESS;
    }

    /**
     * 表单分类添加页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 29, 2013
     */
    public String formTypeAddPage() throws Exception{
        String pid = request.getParameter("pid");
        FormType type = formService.getFormType(pid);
        if(type != null){
            request.setAttribute("name", type.getName());
        }
        else{
            request.setAttribute("name", "流程分类");
        }
        request.setAttribute("pid", pid);
        return SUCCESS;
    }

    /**
     * 增加表单分类
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 24, 2013
     */
    public String addFormType() throws Exception{
        SysUser sysUser = getSysUser();
        formType.setAdddeptid(sysUser.getDepartmentid());
        formType.setAdddeptname(sysUser.getDepartmentname());
        formType.setAdduserid(sysUser.getUserid());
        formType.setAddusername(sysUser.getName());
        Map result = new HashMap();
        if(formService.addFormType(formType)){
            result.put("flag", true);
        }
        else{
            result.put("flag", false);
        }
        result.put("type", "add");
        Map node = new HashMap();
        node.put("id", formType.getId());
        node.put("pid", formType.getPid());
        node.put("name", formType.getName());
        node.put("key", formType.getUnkey());
        result.put("node", node);
        addJSONObject(result);
        return SUCCESS;
    }

    /**
     * 表单分类修改页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 24, 2013
     */
    public String formTypeEditPage() throws Exception{
        String id = request.getParameter("id");
        FormType type = formService.getFormType(id);
        if(type != null){
            FormType type2 = formService.getFormType(type.getPid());
            if(type2 != null){
                request.setAttribute("name", type2.getName());
            }
            else{
                request.setAttribute("name", "流程分类");
            }
        }
        request.setAttribute("type", type);
        return SUCCESS;
    }

    /**
     * 修改流程分类
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 24, 2013
     */
    public String updateFormType() throws Exception{
        Map result = new HashMap();
        if(formService.updateFormType(formType)){
            result.put("flag", true);
        }
        else{
            result.put("flag", false);
        }
        result.put("id", formType.getId());
        result.put("name", formType.getName());
        result.put("type", "edit");
        addJSONObject(result);
        return SUCCESS;
    }

    /**
     * 删除流程分类
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 24, 2013
     */
    public String deleteFormType() throws Exception{
        String id = request.getParameter("id");
        if(formService.deleteFormType(id)){
            addJSONObject("flag", true);
        }
        else{
            addJSONObject("flag", false);
        }
        return SUCCESS;
    }

    /**
     * 表单列表与设置
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 29, 2013
     */
    public String formPage2() throws Exception{
        String type = request.getParameter("type");
        request.setAttribute("type", type);
        return SUCCESS;
    }

    /**
     * 获取表单列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 29, 2013
     */
    public String getFormList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        if(!map.containsKey("page")) {
            map.put("nopage", true);
        }
        pageMap.setCondition(map);
        addJSONObject(formService.getFormData(pageMap));
        return SUCCESS;
    }

    /**
     * 表单添加页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 29, 2013
     */
    public String formAddPage() throws Exception{
        List list = formService.getFormTypeList();
        request.setAttribute("list", list);
        return SUCCESS;
    }

    /**
     * 表单设计页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 29, 2013
     */
    public String formDesignPage() throws Exception{
        String key = request.getParameter("key");
        Form f = formService.getFormByKey(key);
        if(f != null){
            form = f;
        }
        List list = formService.getFormTypeList();
        request.setAttribute("list", list);
        request.setAttribute("form", form);
        request.setAttribute("content", StringEscapeUtils.escapeHtml(CommonUtils.bytes2String(form.getDetail())));
        return SUCCESS;
    }

    /**
     * 表单设计页面(Ueditor)
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 7, 2015
     */
    public String formDesignPage2() throws Exception{

        String key = request.getParameter("key");
        Form f = formService.getFormByKey(key);
        if(f != null){
            form = f;
        }
        List list = formService.getFormTypeList();
        request.setAttribute("list", list);
        request.setAttribute("content", StringEscapeUtils.escapeHtml(form != null ? CommonUtils.bytes2String(form.getDetail()) : ""));
        request.setAttribute("template", form != null ? CommonUtils.bytes2String(form.getTemplate()) : "");
        request.setAttribute("form", form);
        return SUCCESS;
    }

    /**
     * 表单设计页面(for phone)
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 24, 2015
     */
    public String formDesignPage3() throws Exception {

        String phonehtml = CommonUtils.nullToEmpty(request.getParameter("phonehtml"));

        request.setAttribute("phonehtml", phonehtml.split("\r\n"));
        return SUCCESS;
    }

    /**
     * 添加表单信息
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 29, 2013
     */
    public String addForm() throws Exception{
        addJSONObject("flag", formService.addForm(form, new ArrayList<FormItem>()));
        return SUCCESS;
    }

    /**
     * 选择表单页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 30, 2013
     */
    public String formSelectPage() throws Exception{

        return SUCCESS;
    }

    /**
     * 删除表单
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 30, 2013
     */
    public String deleteForm() throws Exception{
        String id = request.getParameter("id");
        String unkey = request.getParameter("unkey");
        addJSONObject("flag", formService.deleteForm(id, unkey));
        return SUCCESS;
    }

    public String formPreviewPage() throws Exception{

        String key = request.getParameter("key");
        Form form = formService.getFormByKey(key);
        request.setAttribute("form", form);
        request.setAttribute("detail", CommonUtils.bytes2String(form.getDetail()));
        return SUCCESS;
    }

    //以下方法为表单调用方法
    public String personnelChoosePage() throws Exception{

        return SUCCESS;
    }

    public String departChoosePage() throws Exception{

        return SUCCESS;
    }

    /**
     * 保存Form表单，针对于Ueditor
     * @return
     * @throws Exception
     * @author limin
     * @data Feb 7, 2015
     */
    public String saveForm() throws Exception {

        String unkey = request.getParameter("unkey");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String intro = request.getParameter("intro");
        String detail = request.getParameter("detail");
        String fieldnum = request.getParameter("fields");
        String template = request.getParameter("template");

        String itemlist = request.getParameter("list");

        List<FormItem> list = JSONUtils.jsonStrToList(itemlist, new FormItem());

        Form form = new Form();
        form.setUnkey(unkey);
        form.setName(name);
        form.setType(type);
        form.setIntro(intro);
        form.setDetail(detail.getBytes(DEFAULT_ENCODING));
        form.setFieldnum(fieldnum);
        form.setTemplate(template.getBytes(DEFAULT_ENCODING));

        boolean flag = formService.addForm(form, list);

        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * Form预览页面(Ueditor)
     * @return
     * @throws Exception
     * @author limin
     * @data Feb 8, 2015
     */
    public String formPreviewPage2() throws Exception {

        String temphtml = request.getParameter("temphtml");
        String template = request.getParameter("template");
        List list = formService.getFormTypeList();

        request.setAttribute("list", list);
        request.setAttribute("html", temphtml);
        request.setAttribute("template", template);
        return SUCCESS;
    }

    /**
     * Form预览页面(Ueditor)
     * @return
     * @throws Exception
     * @author limin
     * @data Feb 8, 2015
     */
    public String formPreviewPage3() throws Exception {

        String temphtml = request.getParameter("temphtml");

        request.setAttribute("html", temphtml);
        return SUCCESS;
    }

    /**
     * Form权限设定页面
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 9, 2015
     */
    public String formRuleSetPage() throws Exception {

        String definitionkey = request.getParameter("definitionkey");
        String taskkey = request.getParameter("taskkey");

        Definition definition = definitionService.getDefinitionByKey(definitionkey, null, "1");
        String formkey = definition.getFormkey();

        formService.initFormRule(definitionkey, taskkey);

        List<FormItemRule> list = formService.selectFormRuleList(definitionkey, null, taskkey);

        request.setAttribute("list", list);
        return SUCCESS;
    }

    /**
     * 设定表单项目规则
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 10, 2015
     */
    public String setFormItemRule() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());

        boolean flag = formService.setFormItemRule(map);

        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 根据unkey查询form
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 10, 2015
     */
    public String selectFormInfo() throws Exception {

        String unkey = request.getParameter("unkey");

        Form form = formService.getFormByKey(unkey);

        addJSONObject("form", form);
        return SUCCESS;
    }

    /**
     * form表单参照窗口页面
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 13, 2015
     */
    public String formWidgetPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 导出form表单源码
     * @throws Exception
     * @author limin
     * @date Feb 25, 2015
     */
    public void exportForm() throws Exception {

        String key = request.getParameter("key");
        String title = request.getParameter("title");
        String html = request.getParameter("html");

        if(StringUtils.isNotEmpty(key)) {

            Form form = formService.getFormByKey(key);
            title = form.getName();
            html = CommonUtils.bytes2String(form.getTemplate());
        }

        MessageDigest md5 = MessageDigest.getInstance("MD5");

        InputStream is = new ByteArrayInputStream(html.getBytes("UTF-8"));

        int len = -1;
        int total = html.getBytes("UTF-8").length;
        byte[] b;
        if(total < 2048) {
            b = new byte[total];
        } else {
            b = new byte[2048];
        }

        String fileName = URLEncoder.encode(title + "-" + CommonUtils.getDataNumber() + ".form", "UTF-8");

        /*
        if (fileName.length() > 150) {
            String guessCharset = "gb2312"; //根据request的locale 得出可能的编码，中文操作系统通常是gb2312
            fileName = new String((title + CommonUtils.getTodayDataStr() + ".dat").getBytes(guessCharset), "ISO8859-1");
        }
        */
        response.addHeader("Content-Disposition", "attachment;charset=UTF-8;filename=" + fileName);

        OutputStream os = null;
        try {

            os = response.getOutputStream();

            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
                md5.update(b, 0, len);
                total = total - b.length;
                if(total < 2048) {
                    b = new byte[total];
                } else {
                    b = new byte[2048];
                }
            }
            BigInteger bi = new BigInteger(1, md5.digest());
            String encrypt = bi.toString(16);

            os.write("\r\n".getBytes("UTF-8"));
            // 生成MD5码
            os.write(encrypt.getBytes("UTF-8"));
        } catch (Exception e) {

            throw e;

        } finally {

            os.flush();
            is.close();
            os.close();
        }

    }

    /**
     * 上传form文件
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 26, 2015
     */
    public String uploadForm() throws Exception {

        StringBuilder sb = new StringBuilder();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        InputStream is = null;
        String encrypt = "";

        try {

            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "UTF-8");
            reader = new BufferedReader(isr);
            String str = "";
            boolean check = false;

            char[] c = new char[1];
            boolean crlf = false;
            while((reader.read(c)) > 0) {

                if(c[0] == 13 || c[0] == 10) {
                    crlf = true;
                    continue;
                }

                if(!crlf) {
                    sb.append(c);
                } else {
                    encrypt = encrypt + c[0];
                }
            }

            String html = new String(sb);
            is = new ByteArrayInputStream(html.getBytes("UTF-8"));
            int total = html.getBytes("UTF-8").length;
            int len = -1;
            byte[] b;
            if(total < 2048) {
                b = new byte[total];
            } else {
                b = new byte[2048];
            }

            while ((len = is.read(b)) != -1) {
                md5.update(b, 0, len);
                total = total - b.length;
                if(total < 2048) {
                    b = new byte[total];
                } else {
                    b = new byte[2048];
                }
            }
            BigInteger bi = new BigInteger(1, md5.digest());
            // MD5校验
            check = encrypt.equals(bi.toString(16));

            request.setAttribute("check", check);
            request.setAttribute("html", check ? new String(sb) : "");
        } catch (Exception e) {
            throw e;
        } finally {
            if(reader != null) {
                reader.close();
            }
            if(isr != null) {
                isr.close();
            }
            if(fis != null) {
                fis.close();
            }
            if(is != null) {
                is.close();
            }
        }
        return SUCCESS;
    }

    /**
     * check在线表单是否被引用
     * @return
     * @throws Exception
     */
    public String isFormReferencedByDefinition() throws Exception {

        String formkey = request.getParameter("formkey");

        int count = formService.isFormReferencedByDefinition(formkey);

        addJSONObject("count", count);
        return SUCCESS;
    }

    /**
     * 保存手机表单html代码
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 26, 2015
     */
    public String savePhoneHtml() throws Exception {

        String unkey = request.getParameter("unkey");
        String phonehtml = request.getParameter("phonehtml");

        Form form = formService.getFormByKey(unkey);
        form.setPhonehtml(phonehtml.getBytes("UTF-8"));

        int ret = formService.savePhoneHtml(form);

        addJSONObject("flag", ret > 0);
        return SUCCESS;
    }
}

