package com.hd.agent.system.service.impl;

import java.io.File;
import java.util.Comparator;

/**
 * 对文件进行排序的方法
 * Created by chenwei on 2016-02-18.
 */
public class FileComparator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
        if (o1.isDirectory() && o2.isFile())
            return -1;
        if (o1.isFile() && o2.isDirectory())
            return 1;
        if(o1.getName().length()>o2.getName().length()){
            return 1;
        }else{
            return o1.getName().compareTo(o2.getName());
        }
    }
}
