package com.hd.agent.common.util.ftl;

import com.hd.agent.common.util.ValueUtils;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FD007 on 2016/12/31.
 */
public class CutStringMethodModel implements TemplateMethodModelEx {
    //private static final Logger logger = Logger.getLogger(CutStringMethodModel.class);
    @Override
    public Object exec(List args) throws TemplateModelException {
        String result="";
        String content="";
        String delpatstr="";
        int len=0;
        if (args.size()!=2 && args.size() != 3) {
            throw new TemplateModelException(
                    "Error: 参数个数不正确 CutStringMethodModel(content,len[,replacepattern])");
        }
        try {
            TemplateScalarModel tsm = (TemplateScalarModel) args.get(0);
            if(null!=tsm) {
                content = tsm.getAsString();
            }
            if(args.size()==3) {
                TemplateScalarModel delsm = (TemplateScalarModel) args.get(2);
                if(null!=delsm){
                    delpatstr=delsm.getAsString();
                }
            }
            SimpleNumber smLen = (SimpleNumber) args.get(1);
            if(null!=smLen) {
                len = smLen.getAsNumber().intValue();
            }
        } catch (ClassCastException cce) {
            String mess = "Error: 无法解析参数信息";
            throw new TemplateModelException(mess);
        }
        if(null==content || "".equals(content.trim())){
            return result;
        }
        if(null!=delpatstr && !"".equals(delpatstr.trim())){
            try{
                Pattern pattern=Pattern.compile(delpatstr);
                Matcher m=pattern.matcher(content.trim());
                if(m.find()){
                    content=m.replaceAll("");
                }
            }catch (Exception ex){
                String mess = "Error: 正则替换失败,需要替换的正则"+delpatstr;
                throw new TemplateModelException(mess);
            }
        }
        try{
            result= ValueUtils.getCutString(content, len);
        }catch (Exception ex){
            String mess = "Error: 无法截取字符串";
            throw new TemplateModelException(mess);
        }
        return result;
    }
}
