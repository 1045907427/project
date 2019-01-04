/**
 * @(#)JuelFormEngine.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-24 zhengziyong 创建版本
 */
package org.activiti.engine.impl.form;

import java.io.UnsupportedEncodingException;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.scripting.ScriptingEngines;
/**
 * 
 * 
 * @author zhengziyong
 */
public class JuelFormEngine implements FormEngine {

	public String getName() {
	    return "juel";
	  }

	  public Object renderStartForm(StartFormData startForm) {
	    if (startForm.getFormKey()==null) {
	      return null;
	    }
	    String formTemplateString = getFormTemplateString(startForm, startForm.getFormKey());
	    ScriptingEngines scriptingEngines = Context.getProcessEngineConfiguration().getScriptingEngines();
	    return scriptingEngines.evaluate(formTemplateString, ScriptingEngines.DEFAULT_SCRIPTING_LANGUAGE, null);
	  }

	  public Object renderTaskForm(TaskFormData taskForm) {
	    if (taskForm.getFormKey()==null) {
	      return null;
	    }
	    String formTemplateString = getFormTemplateString(taskForm, taskForm.getFormKey());
	    ScriptingEngines scriptingEngines = Context.getProcessEngineConfiguration().getScriptingEngines();
	    TaskEntity task = (TaskEntity) taskForm.getTask();
	    return scriptingEngines.evaluate(formTemplateString, ScriptingEngines.DEFAULT_SCRIPTING_LANGUAGE, task.getExecution());
	  }

	  protected String getFormTemplateString(FormData formInstance, String formKey) {
	    String deploymentId = formInstance.getDeploymentId();
	    
	    ResourceEntity resourceStream = Context
	      .getCommandContext()
	      .getResourceManager()
	      .findResourceByDeploymentIdAndResourceName(deploymentId, formKey);
	    
	    if (resourceStream == null) {
	      throw new ActivitiException("Form with formKey '"+formKey+"' does not exist");
	    }
	    
	    byte[] resourceBytes = resourceStream.getBytes();
	    String encoding = "UTF-8";
	    String formTemplateString = "";
	    try {
	      formTemplateString = new String(resourceBytes, encoding);
	    } catch (UnsupportedEncodingException e) {
	      throw new ActivitiException("Unsupported encoding of :" + encoding, e);
	    }
	    return formTemplateString;
	  }

}

