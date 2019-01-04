/**
 * @(#)OffPriceServiceImpl.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月1日 huangzhiqian 创建版本
 */
package com.hd.agent.client.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.client.dao.ClientOffpriceLogMapper;
import com.hd.agent.client.dao.ClientOffpriceMapper;
import com.hd.agent.client.exception.ClientExcelException;
import com.hd.agent.client.model.ClientOffprice;
import com.hd.agent.client.model.ClientOffpriceLog;
import com.hd.agent.client.service.IOffPriceService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class OffPriceServiceImpl extends BaseFilesServiceImpl implements IOffPriceService{

	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat tf = new SimpleDateFormat("HH:mm");

	Pattern datePattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))");
	Pattern timePattern = Pattern.compile("(0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}");
	Pattern moneyPattern = Pattern.compile("^((([1-9]{1}\\d{0,9}))|([0]{1}))((\\.(\\d){1,}))?$");

	public static final int OFFPRICE_LOGTYPE_ADD = 0;

	public static final int OFFPRICE_LOGTYPE_UPDATE = 1;

	private ClientOffpriceMapper clientOffpriceMapper;

	private ClientOffpriceLogMapper clientOffpriceLogMapper;

	public ClientOffpriceLogMapper getClientOffpriceLogMapper() {
		return clientOffpriceLogMapper;
	}

	public void setClientOffpriceLogMapper(ClientOffpriceLogMapper clientOffpriceLogMapper) {
		this.clientOffpriceLogMapper = clientOffpriceLogMapper;
	}

	public ClientOffpriceMapper getClientOffpriceMapper() {
		return clientOffpriceMapper;
	}

	public void setClientOffpriceMapper(ClientOffpriceMapper clientOffpriceMapper) {
		this.clientOffpriceMapper = clientOffpriceMapper;
	}
	
	@Override
	public PageData getOffPriceList(PageMap pageMap) throws Exception {

		// 数据权限控制
		String dataSql = getDataAccessRule("t_client_offprice", "t");
		pageMap.setDataSql(dataSql);

		List<ClientOffprice> rsList = clientOffpriceMapper.getOffPriceList(pageMap);
		for(ClientOffprice info : rsList){
			DepartMent departMent=getDepartmentByDeptid(info.getDeptid());
			if(departMent!=null){
				info.setDeptname(departMent.getName());
			}
			GoodsInfo goods=getGoodsInfoByID(info.getGoodsid());
			if(goods!=null){
				info.setGoodsname(goods.getName());
				info.setBarcode(goods.getBarcode());
				info.setMainunit(goods.getMainunitName());
                info.setBasesaleprice(goods.getBasesaleprice());
			}

            String operateuserid = info.getOperateuserid();
            SysUser user = getSysUserById(operateuserid);
            if(user != null) {
                info.setOperateusername(user.getName());
            }

			if("1".equals(info.getStatus())) {
				info.setStatus("未生效");
			} else if("2".equals(info.getStatus())) {
				info.setStatus("生效");
			} else if("3".equals(info.getStatus())) {
				info.setStatus("已失效");
			}
		}
		int count = clientOffpriceMapper.getOffPriceCount(pageMap);
		return new PageData(count, rsList, pageMap);
	}

	@Override
	public Map addOffPriceForExcel(List<ClientOffprice> infoList)throws Exception{
		Map map=new HashMap();
		String samenameStr = ""; //重复
		int successNum = 0; //成功条数
		int coverNum = 0; //覆盖数量
		String coverVal = "";
		Map<String,Set<String>> checkRepeat = new HashMap<String,Set<String>>();
		boolean flag = true;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Pattern p = Pattern.compile("(0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}");
//		for(ClientOffprice info : infoList){
		for(int i = 0; i < infoList.size(); i++){
			ClientOffprice info = infoList.get(i);

			if(StringUtils.isEmpty(info.getDeptid())) {
				throw new ClientExcelException(String.format("部门名称为空"));
			}

			if(StringUtils.isEmpty(info.getGoodsid())) {
				throw new ClientExcelException(String.format("商品编码为空"));
			}

			//时间戳的校验
			Matcher matcher = p.matcher(info.getBegintime());
			if(!matcher.matches()){
				throw new ClientExcelException(String.format("部门编号:%s,商品编号 :%s,起始时间输入有误", info.getDeptid(),info.getGoodsid()));
			}
			matcher=p.matcher(info.getEndtime());
			if(!matcher.matches()){
				throw new ClientExcelException(String.format("部门编号:%s,商品编号 :%s,终止时间输入有误", info.getDeptid(),info.getGoodsid()));
			}
			//日期校验
			try {
				fmt.parse(info.getBegindate());
			} catch (ParseException e) {
				e.printStackTrace();
				throw new ClientExcelException(String.format("部门编号:%s,商品编号 :%s,起始日期输入有误", info.getDeptid(),info.getGoodsid()));
			}
			try {
				fmt.parse(info.getEnddate());
			} catch (ParseException e) {
				e.printStackTrace();
				throw new ClientExcelException(String.format("部门编号:%s,商品编号 :%s,终止日期输入有误", info.getDeptid(),info.getGoodsid()));
			}
			//检验重复
//			String storeId=info.getDeptid();
//			if(checkRepeat.containsKey(storeId)){
//				Set<String> set=checkRepeat.get(storeId);
//				if(!set.add(info.getGoodsid())){
//					//如果重复,记录下来
//					String tmp=String.format(" 部门编号:%s  商品编号:%s   ", storeId,info.getGoodsid());
//					samenameStr+=tmp;
//					continue;
//				}
//			}else{
//				Set<String> set = new HashSet<String>();
//				set.add(info.getGoodsid());
//				checkRepeat.put(storeId,set);
//			}
			//添加数据
			String goodsId=info.getGoodsid();
			GoodsInfo goods=getGoodsInfoByID(goodsId);
			if(goods==null){
				throw new ClientExcelException(String.format("商品编号 :%s,找不到商品",info.getGoodsid()));
			}

			List<ClientOffprice> dbList=clientOffpriceMapper.selectOffPriceGoodsByDeptId(info.getDeptid(),info.getGoodsid());
//			if(dbList!=null&&dbList.size()>0){ //已存在 更新
//				boolean tmpflag=updateSaleOffGoods(info);
//				coverVal=coverVal+String.format("部门编号:%s  商品编号:%s  ,", storeId,info.getGoodsid());
//				if(tmpflag){
//					coverNum++;
//				}
//			}else{  //不存在新增

			boolean exist = false;
			for(ClientOffprice off : dbList) {

				boolean dateCheck = isDatetimeSpanConflicted(
						off.getBegindate(),
						off.getEnddate(),
						info.getBegindate(),
						info.getEnddate()
				);
				boolean timeCheck = isDatetimeSpanConflicted(
						off.getBegintime(),
						off.getEndtime(),
						info.getBegintime(),
						info.getEndtime()
				);

				if(dateCheck && timeCheck) {
					exist = true;
					break;
				}
			}

			if(exist) {
				String tmp=String.format(" 部门编号:%s  商品编号:%s 商品价格设定冲突  ", info.getDeptid(), info.getGoodsid());
				samenameStr+=tmp;
				continue;
			}

			info.setAddtime(new Date());
			flag=addOffPriceGoods(info);

			successNum++;
//			}
			if(!flag){
				throw new Exception();
			}
		}
		
		map.put("flag",true);
		map.put("success",successNum);
		map.put("samenameStr",samenameStr);
		
		if(coverNum>0&& StringUtils.isNotEmpty(coverVal)){
			map.put("coverNum", coverNum);
			map.put("coverVal", coverVal.substring(0,coverVal.length()-1));
		}
		
		return map;
	}

	public boolean addOffPriceGoods(ClientOffprice info) throws Exception {

		if (isAutoCreate("t_client_offprice")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(info, "t_client_offprice");
			info.setId(id);
		} else {
			info.setId("MDTJ-" + CommonUtils.getDataNumberSendsWithRand());
		}

		boolean flag=clientOffpriceMapper.insertOffPriceGoods(info)>0;
		if(flag){
			addOffPriceChangeLog(info,OFFPRICE_LOGTYPE_ADD);
		}
		return flag;
	}

	@Override
	public boolean updateSaleOffGoods(ClientOffprice info) throws Exception{
		boolean flag=false;
		if(addOffPriceChangeLog(info,OFFPRICE_LOGTYPE_UPDATE)){
			flag=clientOffpriceMapper.updateOffPriceGoodsInfo(info)>0;
		}
		return flag;
	}

	/**
	 * 特价商品日志  
	 * @param info
	 * @param type  ()
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年12月7日
	 */
	private boolean addOffPriceChangeLog(ClientOffprice info, int type) throws Exception {
		SysUser user=getSysUser();
		//日志
		ClientOffpriceLog log = new ClientOffpriceLog();
		log.setOffpriceid(info.getId());
		log.setDeptid(info.getDeptid());
		log.setGoodsid(info.getGoodsid());
		log.setOperatetype(String.valueOf(type));
		log.setOperateuserid(user.getUserid());
		log.setOperateusername(user.getUsername());
		log.setOperatetime(new Date());

		log.setBegindateafter(info.getBegindate());
		log.setEnddateafter(info.getEnddate());
		log.setBegintimeafter(info.getBegintime());
		log.setEndtimeafter(info.getEndtime());
		log.setRetailpriceafter(info.getRetailprice());

		boolean addLogFlag = true;
		
		if(type == OFFPRICE_LOGTYPE_UPDATE){
//			List<ClientOffprice> oldList= clientOffpriceMapper.findOffPriceGoodsByGoodsIdAndStoreId(info.getDeptid(), info.getGoodsid());
			ClientOffprice olddata = clientOffpriceMapper.selectClientOffPriceById(info.getId());
//			if(oldList!=null&&oldList.size()==1){
//				ClientOffprice olddata=oldList.get(0);
				//查看新旧数据是否有变化
				addLogFlag=hasChanges(olddata, info); 
				//有变化就吧原数据记录下来
				if(addLogFlag){
					log.setBegindatebefore(olddata.getBegindate());
					log.setEnddatebefore(olddata.getEnddate());
					log.setBegintimebefore(olddata.getBegintime());
					log.setEndtimebefore(olddata.getEndtime());
					log.setRetailpricebefore(olddata.getRetailprice());
				}
//			}else{
//				throw new ClientExcelException(String.format("部门编号:%s  商品编号:%s有多条数据",info.getDeptid(),info.getGoodsid()));
//			}
		}
		
		
		if(addLogFlag){
			clientOffpriceLogMapper.addOffPriceLog(log);
		}
		return addLogFlag;
	}
	
	/**
	 * 检验是否修改了数据
	 * @param oldData
	 * @param newData
	 * @return 有改变true,没改变false
	 * @author huangzhiqian
	 * @date 2015年12月7日
	 */
	private boolean hasChanges(ClientOffprice oldData, ClientOffprice newData){
		if(!oldData.getBegindate().equals(newData.getBegindate())){
			return true;
		}
		if(!oldData.getEnddate().equals(newData.getEnddate())){
			return true;
		}
		if(!oldData.getEndtime().equals(newData.getEndtime())){
			return true;
		}
		if(!oldData.getBegintime().equals(newData.getBegintime())){
			return true;
		}
		if(oldData.getRetailprice().compareTo(newData.getRetailprice())!=0){
			return true;
		}
		
		return false;
	}

	@Override
	public List<ClientOffprice> selectOffPriceGoodsByGoodsIdAndDeptId(String deptid, String goodsid) throws Exception {
		List<ClientOffprice> rsList = clientOffpriceMapper.findOffPriceGoodsByGoodsIdAndDeptId(deptid, goodsid);
		return rsList;
	}

	//*************************客户端接口部分***************************************//
	
	@Override
	public List<ClientOffprice> getOffPriceForClient(String deptid, String goodsid) throws Exception {
		List<ClientOffprice> goodsList = clientOffpriceMapper.selectOffPriceGoodsByDeptId(deptid, goodsid);
		List<ClientOffprice> rsList = new ArrayList<ClientOffprice>();
		Date today = new Date();
		String endDate = null;
		Date date = null;
		for(ClientOffprice goods : goodsList){
			endDate = CommonUtils.getNextDayByDate(goods.getEnddate());
			date= CommonUtils.stringToDate(endDate);
			if(date.compareTo(today)>0){
				rsList.add(goods);
			}
		}
		return rsList;
	}

	/**
	 * 判断日期或事件是否重叠
	 * @param startDate1
	 * @param endDate1
	 * @param startDate2
	 * @param endDate2
	 * @return	false: 不重叠；true: 重叠
	 * @author limin
	 * @date Apr 5, 2016
	 */
	private boolean isDatetimeSpanConflicted(
			String startDate1,
			String endDate1,
			String startDate2,
			String endDate2
	) {

		if(endDate1.compareTo(startDate2) < 0 || startDate1.compareTo(endDate2) > 0) {
			return false;
		}

		return true;
	}

	@Override
	public Map importClientOffPrice(List<Map> list) throws Exception {

		String msg = "";
		int success = 0;
		for(Map map : list) {

			String deptname = (String) map.get("deptname");
			String goodsid = (String) map.get("goodsid");
			String begindate = (String) map.get("begindate");
			String enddate = (String) map.get("enddate");
			String begintime = (String) map.get("begintime");
			String endtime = (String) map.get("endtime");
			String retailprice = (String) map.get("retailprice");

			// 部门名称是否为空
			if(StringUtils.isEmpty(deptname)) {
				msg = msg + "部门为空\n";
				continue;
			}
			// 部门是否存在
			DepartMent departMent = getDepartMentByName(deptname);
			if(departMent == null) {
				msg = msg + "部门:" + deptname + "不存在\n";
				continue;
			}

			// 商品编码是否为空
			if(StringUtils.isEmpty(goodsid)) {

				msg = msg + "商品编码为空\n";
				continue;
			}
			// 商品是否存在
			GoodsInfo goods = getGoodsInfoByID(goodsid);
			if(goods == null) {
				msg = msg + "商品编码:" + goodsid + "不存在\n";
				continue;
			}

			// 起始日期是否为空
			if(StringUtils.isEmpty(begindate)) {

				msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 起始日期为空\n";
				continue;
			}
			// 起始日期格式
			if(!datePattern.matcher(begindate).matches()) {

				msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 起始日期格式有误\n";
				continue;
			}

			// 终止日期是否为空
			if(StringUtils.isEmpty(enddate)) {

				msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 终止日期为空\n";
				continue;
			}
			// 终止日期格式
			if(!datePattern.matcher(enddate).matches()) {

				msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 终止日期格式有误\n";
				continue;
			}

			// 起始时间是否为空
			if(StringUtils.isEmpty(begintime)) {

				msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 起始时间为空\n";
				continue;
			}
			// 起始时间格式
			if(!timePattern.matcher(begintime).matches()) {

				msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 起始时间格式有误\n";
				continue;
			}

			// 结束时间是否为空
			if(StringUtils.isEmpty(endtime)) {

				msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 结束时间为空\n";
				continue;
			}
			// 结束时间格式
			if(!timePattern.matcher(endtime).matches()) {

				msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 结束时间格式有误\n";
				continue;
			}

			// 零售价格是否为空
			if(StringUtils.isEmpty(retailprice)) {

				msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 零售价格为空\n";
				continue;
			}
			// 零售价格格式
			if(!moneyPattern.matcher(retailprice).matches()) {

				msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 零售价格格式有误\n";
				continue;
			}

			List<ClientOffprice> existOffPrices = selectOffPriceGoodsByGoodsIdAndDeptId(departMent.getId(), goodsid);
			boolean comflictCheck = false;
			for(ClientOffprice existOffPrice : existOffPrices) {

				boolean dateComflicted = false;
				boolean timeComflicted = false;

				dateComflicted = isDatetimeSpanConflicted(
						begindate,
						enddate,
						existOffPrice.getBegindate(),
						existOffPrice.getEnddate()
				);
				timeComflicted = isDatetimeSpanConflicted(
						begintime,
						endtime,
						existOffPrice.getBegintime(),
						existOffPrice.getEndtime()
				);

				if(dateComflicted && timeComflicted) {

					msg = msg + "部门:" + deptname + "，商品：" + goodsid + " 与现存特价有冲突\n";
					comflictCheck = true;
					break;
				}
			}

			if(comflictCheck) {
				continue;
			}

			ClientOffprice offPrice = new ClientOffprice();
			offPrice.setDeptid(departMent.getId());
			offPrice.setGoodsid(goodsid);
			offPrice.setBegindate(begindate);
			offPrice.setEnddate(enddate);
			offPrice.setBegintime(begintime);
			offPrice.setEndtime(endtime);
			offPrice.setAddtime(new Date());
			offPrice.setRetailprice(new BigDecimal(retailprice).setScale(6, BigDecimal.ROUND_HALF_UP));

//			int ret = clientOffpriceMapper.insertOffPriceGoods(offPrice);
			boolean ret = addOffPriceGoods(offPrice);
			if(ret) {
				success++;
			}
		}

		Map map = new HashMap();
		map.put("msg", "成功导入 " + success + " 条记录" + (success < list.size() ? (" 部分记录未能导入成功，原因如下：\n" + msg) : ""));
		map.put("flag", true);
		return map;
	}

	@Override
	public ClientOffprice selectClientOffPriceById(String id) throws Exception {
		return clientOffpriceMapper.selectClientOffPriceById(id);
	}
}
