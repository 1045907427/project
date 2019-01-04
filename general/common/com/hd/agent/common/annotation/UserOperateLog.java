/**
 * @(#)UserOperateLog.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-11 chenwei 创建版本
 */
package com.hd.agent.common.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户操作日志注解
 * @author chenwei
 */
@Target({ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface UserOperateLog {
	/** 
     * 日志内容
     * @return 日志内容，默认为空串 
     */  
    String value() default "";  
      
    /** 
     * 用户操作类型，默认类型为0<br/> 
     * 0 - 其他操作 <br/> 
     * 1 - 查询 <br/> 
     * 2 - 新增 <br/> 
     * 3 - 修改 <br/> 
     * 4 - 删除 
     * @return 用户操作类型 
     */  
    int type() default 0;  
      
    /** 
     * 用户日志所属模块
     * @return key 
     */  
    String key() default "";  
}
