package com.hd.agent.common.util.ftl;

import com.hd.agent.common.util.ValueUtils;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by master on 2017-05-09.
 */
public class JSStringFilterMethodModel  implements TemplateMethodModelEx {
    //private static final Logger logger = Logger.getLogger(CutStringMethodModel.class);
    @Override
    public Object exec(List args) throws TemplateModelException {
        String result="";
        String content="";
        String filterother="";
        if (args.size() != 1) {
            throw new TemplateModelException(
                    "Error: 参数个数不正确 JSStringFilterMethodModel(content)");
        }
        try {
            TemplateScalarModel tsm = (TemplateScalarModel) args.get(0);
            if(null!=tsm) {
                content = tsm.getAsString();
            }
            if(args.size()>1) {
                tsm = (TemplateScalarModel) args.get(1);
                if (null != tsm) {
                    filterother = tsm.getAsString();
                }
            }

        } catch (ClassCastException cce) {
            String mess = "Error: 无法解析参数信息";
            throw new TemplateModelException(mess);
        }
        try{
            if(null!=content && !"".equals(content.trim())) {
                String regEx = "[~\\r\\n]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(content.trim());
                result= m.replaceAll("");

                if(null!=filterother && !"".equals(filterother.trim())){
                    result=result.replaceAll(filterother,"");
                }
            }
        }catch (Exception ex){
            String mess = "Error: 无法过滤字符串";
            throw new TemplateModelException(mess);
        }
        return result;
    }
}
