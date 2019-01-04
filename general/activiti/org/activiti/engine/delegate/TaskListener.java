package org.activiti.engine.delegate;

public abstract interface TaskListener
{
  public static final String EVENTNAME_CREATE = "create";
  public static final String EVENTNAME_ASSIGNMENT = "assignment";
  public static final String EVENTNAME_COMPLETE = "complete";

  public abstract void notify(DelegateTask paramDelegateTask) throws Exception;
}