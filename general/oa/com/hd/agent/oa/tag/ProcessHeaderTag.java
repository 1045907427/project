package com.hd.agent.oa.tag;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.common.util.CommonUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by limin on 2016/4/11.
 */
public class ProcessHeaderTag extends TagSupport {

    private Process process;

    private String width;

    private String advice;

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter out = this.pageContext.getOut();

            if(process == null) {
                out.println("No Process Found...");
                return SKIP_BODY;
            }

//            String html = ("<table style=\"width: $width$; border: none;\" class=\"noprint\">\n" +
//                    "            <tr>\n" +
//                    "                <td align=\"center\" style=\"width: 100%; border: none; border-bottom: 1px solid #DDD; padding: 5px;\">\n" +
//                    "                    <input type=\"button\" onclick=\"print()\" value=\"　打印　\"/>\n" +
//                    (StringUtils.isNotEmpty(advice) ? "                    <div class=\"advice\">" + StringEscapeUtils.escapeHtml4(advice) + "</div>\n" : "") +
//                    "                </td>\n" +
//                    "            </tr>\n" +
//                    "        </table>\n" +
//                    "        <table style=\"width: $width$; border: none;\" class=\"process-info\">\n" +
//                    "            <tr>\n" +
//                    "                <td style=\"width: 5%;border: none;\">OA号：</td>\n" +
//                    "                <td style=\"width: 15%;border: none;\"><u>&nbsp;" + process.getId() + "&nbsp;</u></td>\n" +
//                    "                <td style=\"width: 7%;border: none;\">申请人：</td>\n" +
//                    "                <td style=\"width: 15%;border: none;\"><u>&nbsp;" + process.getApplyusername() + "&nbsp;</u></td>\n" +
//                    "                <td style=\"width: 9%;border: none;\">申请时间：</td>\n" +
//                    "                <td style=\"width: 25%;border: none;\"><u>&nbsp;" + CommonUtils.dataToStr(process.getApplydate(), "yyyy-MM-dd HH:mm:ss") + "&nbsp;</u></td>\n" +
//                    "                <td style=\"width: 11%;border: none;\">当前节点：</td>\n" +
//                    "                <td style=\"width: 14%;border: none;\">\n" +
//                    (StringUtils.isEmpty(process.getTaskname()) ? "                            <span class=\"underline italic\">&nbsp;已完结&nbsp;</span>\n" : "                            <span class=\"underline italic\">&nbsp;" + StringEscapeUtils.escapeHtml4(process.getTaskname()) + "&nbsp;</span>\n") +
//                    "                </td>\n" +
//                    "            </tr>\n" +
//                    "        </table>\n" +
//                    "        <div class=\"title\" style=\"width: 100%;\">" + StringEscapeUtils.escapeHtml4(process.getDefinitionname()) + "</div>");

            String html = ("<table style=\"width: $width$; border: none;\" class=\"noprint\">\n" +
                    "            <tr>\n" +
                    "                <td align=\"center\" style=\"width: 100%; border: none; border-bottom: 1px solid #DDD; padding: 5px;\">\n" +
                    "                    <input type=\"button\" onclick=\"print()\" value=\"　打印　\"/>" +
                    "              <label style=\"font-size: 14px; font-family: 微软雅黑;\"><input type=\"checkbox\" checked=\"checked\" onclick=\"javascript:if(this.checked){$('.comment').show();}else{$('.comment').hide();}\"/>是否打印审批信息</label>" +
                    (StringUtils.isNotEmpty(advice) ? "                    <div class=\"advice\">" + StringEscapeUtils.escapeHtml4(advice) + "</div>\n" : "") +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "        </table>\n" +
                    "        <table style=\"width: $width$; border: none;\" class=\"process-info\">\n" +
                    "            <tr>\n" +
                    "                <td style=\"width: 11%;border: none;\">当前节点：</td>\n" +
                    "                <td style=\"width: 14%;border: none;\">\n" +
                    (StringUtils.isEmpty(process.getTaskname()) ? "                            <span class=\"underline italic\">&nbsp;已完结&nbsp;</span>\n" : "                            <span class=\"underline italic\">&nbsp;" + StringEscapeUtils.escapeHtml4(process.getTaskname()) + "&nbsp;</span>\n") +
                    "                </td>\n" +
                    "                <td style=\"width: 5%;border: none;\">OA号：</td>\n" +
                    "                <td style=\"width: 15%;border: none;\"><u>&nbsp;" + process.getId() + "&nbsp;</u></td>\n" +
                    "                <td style=\"width: 7%;border: none;\">申请人：</td>\n" +
                    "                <td style=\"width: 15%;border: none;\"><u>&nbsp;" + process.getApplyusername() + "&nbsp;</u></td>\n" +
                    "                <td style=\"width: 9%;border: none;\">申请时间：</td>\n" +
                    "                <td style=\"width: 20%;border: none;\"><u>" + CommonUtils.dataToStr(process.getApplydate(), "yyyy-MM-dd HH:mm:ss") + "</u></td>\n" +
                    "            </tr>\n" +
                    "        </table>\n" +
                    "        <div class=\"title\" style=\"width: 100%;\">" + StringEscapeUtils.escapeHtml4(process.getDefinitionname()) + "</div>");

            if(StringUtils.isNotEmpty(width)) {
                html = html.replaceAll("\\$width\\$", width);
            } else {
                html = html.replaceAll("\\$width\\$", "100%");
            }

            out.println(html);
        } catch(Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
        this.process = null;
    }
}
