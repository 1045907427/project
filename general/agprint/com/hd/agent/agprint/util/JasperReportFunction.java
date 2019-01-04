package com.hd.agent.agprint.util;

import com.hd.agent.basefiles.dao.BuySupplierMapper;
import com.hd.agent.basefiles.dao.FinanceMapper;
import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.basefiles.dao.SalesAreaMapper;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.SpringContextUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 
 * 本类主要提供给IReport 模板里函数，其他地方请不要引用
 * 记得修改 com.hd.agent.common.util.JasperReportFunction{@link com.hd.agent.common.util.JasperReportFunction}
 * @author zhanghonghui
 */
public class JasperReportFunction {
	private static final Logger logger =Logger.getLogger(JasperReportFunction.class.getName());
	/**
	 * 数字转中文金额,（方法面向IReport模板，其他地方不要引用）
	 * @param n
	 * @return
	 * @author zhanghonghui
	 * @date 2014-3-25
	 */
	public static String toChineseAmount(double n){
		String fraction[] = {"角", "分"};
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = {{"元", "万", "亿"},
				{"", "拾", "佰", "仟"}};

		String head = n < 0? "负": "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			BigDecimal tmpD=new BigDecimal(n * 10 * Math.pow(10, i));
			tmpD=tmpD.setScale(2, BigDecimal.ROUND_HALF_UP);
			s += (digit[(int)(Math.floor(tmpD.doubleValue()) % 10)] + fraction[i]).replaceAll("(零.)+", "");
		}
		if(s.length()<1){
			s = "整";
		}
		int integerPart = (int)Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p ="";
			for (int j = 0; j < unit[1].length && integerPart > 0; j++) {
				p = digit[integerPart%10]+unit[1][j] + p;
				integerPart = (int)Math.floor(integerPart/10);
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head + s.replaceAll("(零.)*零元", "元").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}
	/**
	 * 获取所在位的中文大写金额的名称<br/>
	 * bit：所在位，正数表示取整数部分从1开始，负数表示取小部分从-1开始。<br/>
	 * 如果所在位为零，则默认为第一位。如果没有对应的所在，则返回零
	 * @param money
	 * @param bit
	 * @return
	 * @author zhanghonghui
	 * @date 2016年1月27日
	 */
	public static String getChineseAmountName(double money,int bit){
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

		money=Math.abs(money);
		money=money*1.000000;
		boolean isPToInt=(bit>=0);
		int nBit=Math.abs(bit);
		if(nBit==0){
			nBit=1;
		}
		String moneyStr=String.valueOf(money);
		String[] moneyArray=moneyStr.split("\\.");
		if(null==moneyArray || moneyArray.length==0){
			return "零";
		}
		String intPartStr=moneyArray[0];
		String smallPartStr=moneyArray[1];
		if(isPToInt){
			if(intPartStr.length()>=nBit){
				String item=intPartStr.charAt(nBit-1)+"";
				int index=Integer.valueOf(item);
				return digit[index];
			}
		}else{
			if(smallPartStr.length()>=nBit){
				String item=smallPartStr.charAt(nBit-1)+"";
				int index=Integer.valueOf(item);
				return digit[index];
			}
		}
		return "零";

	}
	/**
	 * 根据商品编号获取商品信息
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年4月2日
	 */
	public static GoodsInfo getBaseGoodsInfo(String goodsid) throws Exception{
		GoodsInfo goodsInfo=new GoodsInfo();
		GoodsMapper goodsMapper =(GoodsMapper) SpringContextUtils.getBean("goodsMapper");
		if(null==goodsMapper){
			return goodsInfo;
		}
		if(null!=goodsid && !"".equals(goodsid.trim())){
			goodsInfo=goodsMapper.getBaseGoodsInfo(goodsid.trim());
			if(goodsInfo==null){
				goodsInfo=new GoodsInfo();
			}
		}
		return goodsInfo;
	}
	/**
	 * 根据保质期时间，保质期单位，获取保质期
	 * @param value 值 
	 * @param unit 单位，1天2周3月4年
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年4月29日
	 */
	public static int getGoodsLifeDay(BigDecimal value,String unit) throws Exception{
		int result=0;
		int iValue=0;
		if(value==null ||unit==null || "".equals(unit.trim())){
			return result;
		}
		try{
			iValue=value.intValue();
		}catch(Exception ex){
			return result;
		}
		unit=unit.trim();
		if("1".equals(unit)){
			result=iValue;
		}else if("2".equals(unit)){
			result=iValue*7;
		}else if("3".equals(unit)){
			result=iValue*30;
		}else if("4".equals(unit)){
			result=iValue*365;
		}
		return result;
	}

	/**
	 * 根据保质期时间，保质期单位，获取保质期，格式类似 “10天”
	 * @param value 值
	 * @param unit 单位，1天2周3月4年
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年4月29日
	 */
	public static String getGoodsLifeDayString(BigDecimal value,String unit) throws Exception{
		String result="";
		int iValue=0;
		if(value==null ||unit==null || "".equals(unit.trim())){
			return result;
		}
		try{
			iValue=value.intValue();
		}catch(Exception ex){
			return result;
		}
		unit=unit.trim();
		if("1".equals(unit)){
			result=iValue+"天";
		}else if("2".equals(unit)){
			result=iValue+"周";
		}else if("3".equals(unit)){
			result=iValue+"月";
		}else if("4".equals(unit)){
			result=iValue+"年";
		}
		return result;
	}

	/**
	 * 获取结算方式信息
	 * @param settleid
	 * @return com.hd.agent.basefiles.model.Settlement
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 28, 2016
	 */
	public static Settlement getBaseSettlementInfo(String settleid) throws Exception{
		Settlement settlementInfo=new Settlement();
		FinanceMapper financeMapper =(FinanceMapper) SpringContextUtils.getBean("financeMapper");
		if(null==financeMapper){
			return settlementInfo;
		}
		if(null!=settleid && !"".equals(settleid.trim())){

			Map map = new HashMap();
			map.put("id", settleid);
			settlementInfo=financeMapper.getSettlemetDetail(map);
			if(settlementInfo==null){
				settlementInfo=new Settlement();
			}
		}
		return settlementInfo;

	}

	/**
	 * 获取结算方式名称
	 * @param settleid
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 28, 2016
	 */
	public static String getBaseSettlementName(String settleid) throws Exception{
		String name="";
		Settlement settlementInfo=getBaseSettlementInfo(settleid);
		if(null!=settlementInfo){
			name=settlementInfo.getName();
		}
		return name;
	}

	/**
	 * 根据编号获取供应商信息
	 * @param supplierid
	 * @return com.hd.agent.basefiles.model.BuySupplier
	 * @throws
	 * @author zhang_hh
	 * @date Feb 18, 2017
	 */
	public static BuySupplier getBuyerSupplierInfo(String supplierid) throws Exception{
		BuySupplier buySupplier=new BuySupplier();
		if(null==supplierid || "".equals(supplierid.trim())){
			return  buySupplier;
		}
		supplierid=supplierid.trim();
		BuySupplierMapper buySupplierMapper=(BuySupplierMapper)SpringContextUtils.getBean("buySupplierMapper");
		if(null==buySupplierMapper){
			return  buySupplier;
		}
		buySupplier=buySupplierMapper.getBuySupplier(supplierid);
		if(null==buySupplier){
			buySupplier=new BuySupplier();
		}
		return buySupplier;
	}

	/**
	 * 根据编号获取供应商名称
	 * @param supplierid
	 * @return java.lang.String
	 * @throws
	 * @author zhang_hh
	 * @date Feb 18, 2017
	 */
	public static String getBuyerSupplierName(String supplierid) throws Exception{
		BuySupplier buySupplier=getBuyerSupplierInfo(supplierid);
		if(null!=buySupplier){
			return buySupplier.getName();
		}
		return "";
	}

	/**
	 * 计算字符串实际长度，ascii占一个，中文占二个
	 * @param str
	 * @return int
	 * @throws
	 * @author zhanghonghui
	 * @date Jun 08, 2017
	 */
	public static int calculatePlaces(String str) throws Exception{
		int m = 0;
		char arr[] = str.toCharArray();
		for(int i=0;i<arr.length;i++)
		{
			char c = arr[i];
			if((c>=0x0000 && c<=0x00FF)) //ascii
			{
				m = m + 1;
			}else {
				m = m + 2; //双节字符
			}
		}
		return m;
	}

	/**
	 * 获取销售区域信息
	 * @param salesareid
	 * @return com.hd.agent.basefiles.model.SalesArea
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 28, 2016
	 */
	public static SalesArea getBaseSalesAreaInfo(String salesareid) throws Exception{
		SalesArea salesAreaInfo=new SalesArea();
		SalesAreaMapper salesAreaMapper =(SalesAreaMapper) SpringContextUtils.getBean("baseFilesSalesAreaMapper");
		if(null==salesAreaMapper){
			return salesAreaInfo;
		}
		if(null!=salesareid && !"".equals(salesareid.trim())){

			Map map = new HashMap();
			map.put("id", salesareid);
			salesAreaInfo=salesAreaMapper.getSalesAreaDetail(map);
			if(null==salesAreaInfo){
				salesAreaInfo=new SalesArea();
			}
		}
		return salesAreaInfo;

	}

	/**
	 * 获取当前日期的前后日期，负数表前几天，正数表示后几天
	 * @param days
	 * @return
	 * @author chenwei
	 * @date Aug 16, 2013
	 */
	public static Date getDateTimeInDays(Date dateObject,int days){
		if(null==dateObject){
			return null;
		}
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateObject);
			calendar.add(calendar.DATE,days);
			dateObject=calendar.getTime();
		} catch (Exception e) {
		}
		return dateObject;
	}
	/**
	 * 获取税种信息
	 * @param taxtypeid
	 * @return TaxType
	 * @throws
	 * @author zhanghonghui
	 * @date 2017-07-30
	 */
	public static TaxType getBaseTaxTypeInfo(String taxtypeid) throws Exception{
		TaxType taxTypeInfo=new TaxType();
		FinanceMapper financeMapper =(FinanceMapper) SpringContextUtils.getBean("financeMapper");
		if(null==financeMapper){
			return taxTypeInfo;
		}
		if(null!=taxtypeid && !"".equals(taxtypeid.trim())){

			taxTypeInfo=financeMapper.getTaxTypeInfo(taxtypeid);
			if(taxTypeInfo==null){
				taxTypeInfo=new TaxType();
			}
		}
		return taxTypeInfo;
	}
	/**
	 * 使用java正则表达式去掉多余的.与0<br/>
	 *  com.hd.agent.common.util.CommonUtils.subZeroAndDot
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s){
		if(s.indexOf(".") > 0){
			s = s.replaceAll("0+?$", "");//去掉多余的0
			s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
		}
		return s;
	}

	/**
	 * 根据分隔符和索引位，获取指定字符串
	 * @param data
	 * @param sign
	 * @param index
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Nov 01, 2017
	 */
	public static String getSplitContent(String data,String sign,int index){
		if(index<1){
			return "";
		}
		if(null==sign || "".equals(sign.trim())){
			return data;
		}
		String content="";
		String[] contArr=null;
		if(null!=data && !"".equals(data.trim())){
			contArr= StringUtils.split(data,sign);
		}
		int len=0;
		if(null!=contArr && contArr.length>=index){
			content=contArr[index-1];
		}
		return content;
	}

}
