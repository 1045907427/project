package com.hd.agent.common.util.ftl;

import com.hd.agent.common.util.ValueUtils;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by FD007 on 2016/12/31.
 */
public class PaddingStringMethodModel implements TemplateMethodModelEx {
    //private static final Logger logger = Logger.getLogger(CutStringMethodModel.class);
    @Override
    public Object exec(List args) throws TemplateModelException {
        String result="";
        String content="";
        String direct="";
        String padStr="";
        int sizeLen=0;
        if (args.size() != 4) {
            throw new TemplateModelException(
                    "Error: 参数个数不正确 PaddingStringMethod(content,direct,size,padStr)");
        }
        try {
            //内容
            TemplateScalarModel tsm = (TemplateScalarModel) args.get(0);
            if(null!=tsm) {
                content = tsm.getAsString();
            }
            //方向
            tsm = (TemplateScalarModel) args.get(1);
            if(null!=tsm) {
                direct = tsm.getAsString();
            }
            //长度
            SimpleNumber smLen = (SimpleNumber) args.get(2);
            if(null!=smLen) {
                sizeLen = smLen.getAsNumber().intValue();
            }
            //填充字符串
            tsm = (TemplateScalarModel) args.get(3);
            if(null!=tsm) {
                padStr = tsm.getAsString();
            }
        } catch (ClassCastException cce) {
            String mess = "Error: 无法解析参数信息";
            throw new TemplateModelException(mess);
        }
        if(null==content || "".equals(content.trim())){
            content="";
            return content;
        }
        content=content.trim();
        if(StringUtils.isEmpty(padStr)){
            padStr="";
        }
        try{
            //1为左填充
            if("1".equals(direct)) {
                result = StringUtils.leftPad(content, 19, padStr);
            }else{
                //其他为右填充
                result = StringUtils.rightPad(content, 19, padStr);
            }
        }catch (Exception ex){
            String mess = "Error: 无法进行填充符串";
            throw new TemplateModelException(mess);
        }
        return result;
    }
}
