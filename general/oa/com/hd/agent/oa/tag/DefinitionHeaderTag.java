package com.hd.agent.oa.tag;

import com.hd.agent.activiti.model.Process;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by limin on 2016/4/11.
 */
public class DefinitionHeaderTag extends TagSupport {

    private Process process;

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter out = this.pageContext.getOut();

            if(process == null) {
                out.println("No Process Found...");
                return SKIP_BODY;
            }

            out.println("<div style=\"margin: 0px auto; height: 50px; font-size: 16pt; font-weight: bold; line-height: 50px; text-align: center;\">");
            out.println(StringEscapeUtils.escapeHtml4(process.getDefinitionname()));
            out.println("</div>");
            out.println("<div style=\"border-top: 1px solid #AAA;\">&nbsp;</div>");

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
