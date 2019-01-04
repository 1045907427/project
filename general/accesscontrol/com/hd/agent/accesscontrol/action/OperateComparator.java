package com.hd.agent.accesscontrol.action;

import com.hd.agent.accesscontrol.model.Operate;

import java.util.Comparator;

/**
 * Created by hdit001 on 2016-02-18.
 */
public class OperateComparator implements Comparator<Operate> {
    @Override
    public int compare(Operate o1, Operate o2) {
        Operate operate1 = (Operate) o1;
        Operate operate2 = (Operate) o2;
        return operate1.getSeq().compareTo(operate2.getSeq());
    }
}
