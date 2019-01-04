package com.hd.agent.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


public class ListSortLikeSQLComparator  {

	protected static final Logger logger = Logger.getLogger(Logger.class);

	/**
	 * 类似sql orderby
	 * @param orderby
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月7日
	 */
    public static <T> Comparator createComparator(String orderby) {
        return new ImComparator<T>(orderby);
    }

    public static class ImComparator<T> implements Comparator<T> {
        
        Map<String, Integer> sortMap=new HashMap<String, Integer>();
        List<String> fieldSortList=new ArrayList<String>();

        public ImComparator(String orderby) {
            String[] orderbyArr=orderby.split(",");
            for(String tmp : orderbyArr){
            	if(null==tmp || "".equals(tmp.trim())){
            		continue;
            	}
            	tmp=tmp.trim().replaceAll(" ", " ");
            	String[] fieldSortArr=tmp.split(" ");
            	if(null==fieldSortArr[0] || "".equals(fieldSortArr[0].trim())){
            		continue;
            	}
            	this.fieldSortList.add(fieldSortArr[0].trim());
            	int sort=1;
            	if(fieldSortArr.length>=2){
            		sort=(null==fieldSortArr[1] || "ASC".equalsIgnoreCase(fieldSortArr[1]))? 1 : -1;
            	}
        		this.sortMap.put(fieldSortArr[0].trim(), sort);
            }
        }

        @Override
        public int compare(Object o1, Object o2) {
        	if(o1==null || o2==null){
        		return 0;
        	}
            int result = 0;
            //判断是否是Map形式的list
        	if(o1 instanceof Map && o2 instanceof Map){
        		result=compareMap(o1, o2);
        	}else{
        		//普通实体类的list
	            for (String fieldSort : fieldSortList) {
	            	result=compareField(o1, o2, fieldSort);
	            	if(result!=0){
	            		break;
	            	}
	            }
        	}

            return result;
        }
        /**
         * 比较字段
         * @param o1
         * @param o2
         * @param fieldSort
         * @return
         * @author zhanghonghui 
         * @date 2015年12月8日
         */
        public int compareField(Object o1,Object o2,String fieldSort){
        	try{
                Object vObj1 = null; //获取field的值
                Object vObj2 = null; //获取field的值
                int sort = sortMap.get(fieldSort);
                if(fieldSort.indexOf(".")>0){
                    String [] fieldSortArr=fieldSort.split("\\.");
                    if(fieldSortArr.length>=2){
                        Object tmpObj1=getObjectByInvoke(o1, fieldSortArr[0]);
                        Object tmpObj2=getObjectByInvoke(o2, fieldSortArr[0]);
                        if(null==tmpObj1 && null==tmpObj2){
                            return 0;
                        }else if(null==tmpObj1){
                            return 1;
                        }else if(null==tmpObj2){
                            return -1;
                        }
                        vObj1 = getObjectByInvoke(tmpObj1, fieldSortArr[1]);
                        vObj2 = getObjectByInvoke(tmpObj2, fieldSortArr[1]);
                    }
                }else{
                    vObj1=getObjectByInvoke(o1, fieldSort);
                    vObj2=getObjectByInvoke(o2,fieldSort);
                }
                if(null==vObj1 && null==vObj2){
                    return 0;
                }else if(null==vObj1){
                    return 1;
                }else if(null==vObj2){
                    return -1;
                }
                return compareObjectValue(vObj1,vObj2,sort);
                
        	}catch(Exception ex){
        		logger.error("ListSortLikeSQLComparator类使用 compare时出错", ex);
        	}
        	return 0;
        }
        /**
         * 判断List<Map<String,Object>> 这种情况
         * @param o1
         * @param o2
         * @return
         * @author zhanghonghui 
         * @date 2015年12月22日
         */
        public int compareMap(Object o1, Object o2){
        	int result = 0;
        	Map map1=(Map)o1;
        	Map map2=(Map)o2;
            for (String fieldSort : fieldSortList) {
            	result=compareMapField(map1, map2, fieldSort);
            	if(result!=0){
            		break;
            	}
            }

            return result;
        }
        public int compareMapField(Map o1, Map o2,String fieldSort){
            try {
                int sort = sortMap.get(fieldSort);
                if (fieldSort.indexOf(".") > 0) {
                    String[] fieldSortArr = fieldSort.split("\\.");
                    if (fieldSortArr.length >= 2) {
                        Object tmpObj1 = o1.get(fieldSortArr[0]);
                        Object tmpObj2 = o2.get(fieldSortArr[0]);
                        Object vObj1 = getObjectByInvoke(tmpObj1, fieldSortArr[1]);
                        Object vObj2 = getObjectByInvoke(tmpObj2, fieldSortArr[1]);
                        return compareObjectValue(vObj1,vObj2,sort);
                    }
                } else {
                    Object vObj1=o1.get(fieldSort);
                    Object vObj2=o2.get(fieldSort);
                    return compareObjectValue(vObj1,vObj2,sort);
                }
            }catch (Exception ex){
                logger.error("ListSortLikeSQLComparator类使用 compare时出错", ex);
            }
            return 0;
        }

        public Object getObjectByInvoke(Object obj,String filed) throws Exception{
            Object result=null;
            Class clazz = obj.getClass();
            PropertyDescriptor pd = new PropertyDescriptor(filed, clazz);
            Method getMethod = pd.getReadMethod();//获得get方法
            if(pd!=null){
                result=getMethod.invoke(obj);
            }
            return result;
        }

        /**
         * 比较对象里的值
         * @param vObj1
         * @param vObj2
         * @param sort
         * @return int
         * @throws
         * @author zhanghonghui
         * @date Sep 30, 2016
         */
        public int compareObjectValue(Object vObj1,Object vObj2,int sort) throws Exception{
            if(vObj1==null || vObj2==null){
                return 0;
            }
            String typeName = vObj1.getClass().getName().toLowerCase(); //转换成小写
            if(typeName.endsWith("string")) {
                String value1 = vObj1.toString();
                String value2 = vObj2.toString();
                if(null==value1){
                    value1="";
                }
                if(null==value2){
                    value2="";
                }
                if("".equals(value1) && "".equals(value2)){
                    return 0;
                }else if("".equals(value1)){
                    return 1;
                }else if("".equals(value2)){
                    return -1;
                }
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else if(typeName.endsWith("short")) {
                Short value1 = Short.parseShort(vObj1.toString());
                Short value2 = Short.parseShort(vObj2.toString());
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else if(typeName.endsWith("byte")) {
                Byte value1 = Byte.parseByte(vObj1.toString());
                Byte value2 = Byte.parseByte(vObj2.toString());
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else if(typeName.endsWith("char")) {
                Integer value1 = (int)(vObj1.toString().charAt(0));
                Integer value2 = (int)(vObj2.toString().charAt(0));
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else if(typeName.endsWith("int") || typeName.endsWith("integer")) {
                Integer value1 = Integer.parseInt(vObj1.toString());
                Integer value2 = Integer.parseInt(vObj2.toString());
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else if(typeName.endsWith("long")) {
                Long value1 = Long.parseLong(vObj1.toString());
                Long value2 = Long.parseLong(vObj2.toString());
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else if(typeName.endsWith("float")) {
                Float value1 = Float.parseFloat(vObj1.toString());
                Float value2 = Float.parseFloat(vObj2.toString());
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else if(typeName.endsWith("double")) {
                Double value1 = Double.parseDouble(vObj1.toString());
                Double value2 = Double.parseDouble(vObj2.toString());
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else if(typeName.endsWith("boolean")) {
                Boolean value1 = Boolean.parseBoolean(vObj1.toString());
                Boolean value2 = Boolean.parseBoolean(vObj2.toString());
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else if(typeName.endsWith("date")) {
                Date value1 = (Date)(vObj1);
                Date value2 = (Date)(vObj2);
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else if(typeName.endsWith("timestamp")) {
                java.sql.Timestamp value1 = (java.sql.Timestamp)(vObj1);
                java.sql.Timestamp value2 = (java.sql.Timestamp)(vObj2);
                return (sort==1) ? value1.compareTo(value2) : value2.compareTo(value1);
            }
            else {
                //调用对象的compareTo()方法比较大小
                Method method = vObj1.getClass().getDeclaredMethod("compareTo", new Class[]{vObj1.getClass()});
                method.setAccessible(true); //设置可访问权限
                int resultVal  = (Integer)method.invoke(vObj1, new Object[]{vObj2});
                return (sort==1) ? resultVal : resultVal*(-1);
            }
        }
    }
}