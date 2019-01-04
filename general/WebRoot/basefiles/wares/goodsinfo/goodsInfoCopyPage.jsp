<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品档案复制页面</title>
  </head>
  
  <body>
    <div id="goodsInfo-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
    	<div id="goodsInfo-layout-detail-north" data-options="region:'north',border:false" style="height:102px">
    		<form id="goodsInfo-form-head" action="" method="post" style="padding: 3px">
				<table cellspacing="3px" cellpadding="3px" border="0">
					 <tbody>
					 	<tr>
							<td width="80px"><div align="right">编码:</div></td>
							<td style="width: 5px"></td>
						    <td><input id="goodsInfo-id-baseInfo" type="text" style="width:120px;" name="goodsInfo.id" class="easyui-validatebox" validType="validId[20]" required="true"/>
						    	<input type="hidden" id="goodsInfo-hdid-baseInfo" value="<c:out value="${goodsInfo.id }"></c:out>"/>
						    	<input id="goodsPriceInfo-hidden-delIds" type="hidden">
						    	<input id="goodsInfo-hidden-oldId" type="hidden" value="<c:out value="${oldId }"></c:out>" name="goodsInfo.oldId">
						    	<input id="goodsPriceInfo-hidden-addClickNum" type="hidden" value="0"> 
						    	<input id="goodsMeteringUnitInfo-hidden-delIds" type="hidden">
						    	<input id="goodsMeteringUnitInfo-hidden-addClickNum" type="hidden" value="0">
						    	<input id="goodsStorageInfo-hidden-delIds" type="hidden">
						    	<input id="goodsStorageInfo-hidden-addClickNum" type="hidden" value="0">
						    	<input id="goodsWaresClass-hidden-delIds" type="hidden">
						    	<input id="goodsWaresClass-hidden-addClickNum" type="hidden" value="0">
						    	<input id="goodsInfo-hidden-addType" type="hidden" name="type">
						    	<input id="goodsPriceInfo-input-hddelPriceIds" type="hidden" name="goodsInfo.delPriceIds" />
						    	<input id="goodsMUInfo-input-hddelMUIds" type="hidden" name="goodsInfo.delMUIds" />
						    	<input id="goodsStorageInfo-input-hddelStorageIds" type="hidden" name="goodsInfo.delStorageIds" />
						    	<input id="goodsWCInfo-input-hddelWCIds" type="hidden" name="goodsInfo.delWCIds" />
						    	<input id="goodinfo-hidden-hdimage" type="hidden" name="goodsInfo.image" value="${goodsInfo.image}"/>
						    	<input id="goodsInfo-hidden-operateType" type="hidden" value="${operateType }">
						    	<input id="goodsInfo-dblclick-hdstorageid" type="hidden" name="goodsInfo.storageid" value="<c:out value="${goodsInfo.storageid }"></c:out>">
						    	<input id="goodsInfo-dblclick-hdwaresClassid" type="hidden" name="goodsInfo.defaultsort" value="<c:out value="${goodsInfo.defaultsort }"></c:out>"/>
						    	<input id="goodsInfo-dblclick-hdstoragelocation" type="hidden" name="goodsInfo.storagelocation" value="<c:out value="${goodsInfo.storagelocation }"></c:out>"/>
						    	<input id="goodsInfo-hidden-boxnum" type="hidden" value="${goodsInfo.boxnum }"/>
						    </td>
							<td width="80px"><div align="right">名称:</div></td>
							<td style="width: 5px"></td>
						    <td><input type="text" id="goodsInfo-name-baseInfo" style="width:200px;" name="goodsInfo.name" class="easyui-validatebox" required="true" validType="validName[100]"/></td>
							<td width="80px"><div align="right">状态:</div></td>
							<td style="width: 5px"></td>
						    <td><input id="goodsInfo-widget-state" value="4" disabled="disabled" style="width: 120px" /></td>
						</tr>
						<tr>
						  	<td width="80px"><div align="right">规格型号:</div></td>
							<td style="width: 5px"></td>
						    <td colspan="4"><input type="text" style="width:450px;" name="goodsInfo.model" value="<c:out value="${goodsInfo.model}"></c:out>" maxlength="200"/></td>
						 	<td width="80px"><div align="right">单位:</div></td>
							<td style="width: 5px"></td>
						    <td><input id="goodsInfo-widget-meteringUnit" type="text" style="width:120px;" name="goodsInfo.mainunit" value="<c:out value="${goodsInfo.mainunit}"></c:out>"/></td>
					 	</tr>
					 </tbody>
				</table>
			</form>
			<ul class="tags" style="min-width: 965px">
				<security:authorize url="/basefiles/goodsInfoBaseInfoTab.do">
					<li class="selectTag"><a href="javascript:void(0)">基本信息</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/goodsInfoControlInfoTab.do">
					<li><a href="javascript:void(0)">控制信息</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/goodsInfoPriceInfoTab.do">
					<li><a href="javascript:void(0)">价格套管理</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/goodsInfoMeteringUnitInfoTab.do">
					<li><a href="javascript:void(0)">辅助计量单位</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/goodsInfoStorageInfoTab.do">
					<li><a href="javascript:void(0)">对应仓库</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/goodsInfoWaresClassInfoTab.do">
					<li><a href="javascript:void(0)">对应分类</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/goodsInfoStorageLocationInfoTab.do">
					<li><a href="javascript:void(0)">对应库位</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/goodsInfoPhysicsInfoTab.do">
					<li><a href="javascript:void(0)">物理信息</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/goodsInfoDefineInfoTab.do">
					<li><a href="javascript:void(0)">自定义信息</a></li>
				</security:authorize>
			</ul>
    	</div>
    	<div id="goodsInfo-layout-detail-center" data-options="region:'center',border:false">
    		<div class="tagsDiv">
				<div class="tagsDiv_item" style="display:block;">
					<form action="" method="post" id="wares-form-goodsInfoBaseInfo" >
						<table cellspacing="5px" cellpadding="5px" border="0">
						 	<tbody>
						 		<tr>
						 			<td width="120px"><div align="right">名称拼音:</div></td>
									<td style="width: 5px"></td>
									<td><input type="text" name="goodsInfo.pinyin" style="width: 120px"/></td>
									<td width="80px"><div align="right">商品类型:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-widget-goodstype" name="goodsInfo.goodstype" value="${goodsInfo.goodstype}" style="width: 120px"/></td>
									<td width="80px"><div align="right">助记码:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.spell" value="<c:out value="${goodsInfo.spell}"></c:out>" maxlength="20" class="easyui-validatebox" required="true"/></td>
									<td width="80px"><div align="right"></div></td>
									<td style="width: 5px"></td>
								    <td></td>
								</tr>
						  		<tr>
									<td width="80px"><div align="right">商品品牌:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-brand" type="text" style="width:120px;" name="goodsInfo.brand" value="<c:out value="${goodsInfo.brand}"></c:out>"/></td>
									<td width="80px"><div align="right">所属部门:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-deptid" type="text" style="width: 120px;" name="goodsInfo.deptid" value="<c:out value="${goodsInfo.deptid }"></c:out>"/></td>
								    <td width="80px"><div align="right">默认分类:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-dblclick-waresClass" type="text" style="width:120px;" class="easyui-validatebox" required="true" <c:if test="${goodsInfo.defaultsort==''}">value="请双击跳转对应分类标签!"</c:if> value="<c:out value="${goodsInfo.defaultsortName }"></c:out>" readonly="readonly"/></td>
						 		</tr>
								<tr>
									<td width="80px"><div align="right">条形码:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.barcode" class="easyui-validatebox" required="true"/></td>
									<td width="80px"><div align="right">箱装条码:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.boxbarcode" /></td>
									<td width="80px"><div align="right">商品货位:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.itemno" value="<c:out value="${goodsInfo.itemno}"></c:out>" /></td>
								</tr>
								<tr>
									<td width="80px"><div align="right">ABC码:</div></td>
									<td style="width: 5px"></td>
								    <td><select style="width:120px;" name="goodsInfo.abclevel">
								    		<option></option>
								    		<option value="A" <c:if test="${goodsInfo.abclevel=='A'}">selected="selected"</c:if> >A</option>
								    		<option value="B" <c:if test="${goodsInfo.abclevel=='B'}">selected="selected"</c:if> >B</option>
								    		<option value="C" <c:if test="${goodsInfo.abclevel=='C'}">selected="selected"</c:if> >C</option>
								    	</select></td>
									<td width="80px"><div align="right">购销类型:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-bstype" name="goodsInfo.bstype" value="${goodsInfo.bstype}" style="width: 120px"/></td>
									<td width="80px"><div align="right">产地:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" name="goodsInfo.productfield" value="<c:out value="${goodsInfo.productfield }"></c:out>"/>
								    	<input disabled="disabled" type="hidden" style="width:120px;" name="goodsInfo.sortkey" value="${goodsInfo.sortkey}"/>
								    </td>
								</tr>
								<tr>
									<td width="80px"><div align="right">是否采购:</div></td>
									<td style="width: 5px"></td>
								    <td><select name="goodsInfo.isinoutstorage" style="width:120px;">
								    		<option value="1" <c:if test="${goodsInfo.isinoutstorage=='1'}">selected="selected"</c:if> >是</option>
								    		<option value="0" <c:if test="${goodsInfo.isinoutstorage=='0'}">selected="selected"</c:if> >否</option>
								    	</select></td>
									<td width="80px"><div align="right">默认仓库:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-dblclick-storage" type="text" style="width:120px;" class="easyui-validatebox" required="true" <c:if test="${goodsInfo.storageid==''}">value="请双击跳转对应仓库标签!"</c:if> value="<c:out value="${goodsInfo.storageName }"></c:out>" readonly="readonly"/></td>
									<td width="80px"><div align="right">默认库位:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-dblclick-storagelocation" type="text" style="width:120px;" <c:if test="${goodsInfo.storagelocation==''}">value="请双击跳转对应库位标签!"</c:if> value="<c:out value="${goodsInfo.storagelocationName}"></c:out>" readonly="readonly"/></td>
						 		</tr>
						  		<tr>
									<td width="80px"><div align="right">库位管理:</div></td>
									<td style="width: 5px"></td>
								    <td><select name="goodsInfo.isstoragelocation" style="width:120px;">
								    		<option value="1" <c:if test="${goodsInfo.isstoragelocation=='1'}">selected="selected"</c:if> >是</option>
								    		<option value="0" <c:if test="${goodsInfo.isstoragelocation=='0'}">selected="selected"</c:if> >否</option>
								    	</select></td>
									<td width="80px"><div align="right">保质期管理:</div></td>
									<td style="width: 5px"></td>
								    <td><select name="goodsInfo.isshelflife" style="width:120px;">
										<option value="1" >是</option>
										<option value="0" selected="selected">否</option>
									</select></td>
									<td width="80px"><div align="right">保质期:</div></td>
									<td style="width: 5px"></td>
								    <td>
								    <span style="float: left;">
								    	<input type="text" name="goodsInfo.shelflife" value="${goodsInfo.shelflife}" style="width:80px; float:left;" class="easyui-numberbox" data-options="min:0,max:9999999999,groupSeparator:','"/>
									    <select name="goodsInfo.shelflifeunit" style="width:40px; float:right;">
									      <option value="1" <c:if test="${goodsInfo.shelflifeunit=='1'}">selected="selected"</c:if> >天</option>
										  <option value="2" <c:if test="${goodsInfo.shelflifeunit=='2'}">selected="selected"</c:if> >周</option>
									      <option value="3" <c:if test="${goodsInfo.shelflifeunit=='3'}">selected="selected"</c:if> >月</option>
									      <option value="4" <c:if test="${goodsInfo.shelflifeunit=='4'}">selected="selected"</c:if> >年</option>
									    </select>
								    </span>
								    </td>
						  		</tr>
						  		<tr>
						  			<td width="80px"><div align="right">批次管理:</div></td>
									<td style="width: 5px"></td>
								    <td><select name="goodsInfo.isbatch" style="width:120px;">
								    		<option value="1" <c:if test="${goodsInfo.isbatch=='1'}">selected="selected"</c:if> >是</option>
								    		<option value="0" <c:if test="${goodsInfo.isbatch=='0'}">selected="selected"</c:if> >否</option>
								    	</select></td>
						  		</tr>
						   		<tr>
						 			<td width="80px"><div align="right">备注:</div></td>
									<td style="width: 5px"></td>
						            <td colspan="7"><textarea name="goodsInfo.remark" cols="" rows="5" style="width:650px;"><c:out value="${goodsInfo.remark}"></c:out></textarea></td>
						   		</tr> 
						  </tbody>
						</table>
					</form>
				</div>
				<div class="tagsDiv_item">
					<form action="" method="post" id="wares-form-goodsInfoControlInfo" >
						<table cellspacing="5px" cellpadding="5px" border="0">
						 	<tbody>
						 		<tr>
								 	<td width="100px"><div align="right">采购价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-highestbuyprice"  style="width: 120px;" name="goodsInfo.highestbuyprice" value="${goodsInfo.highestbuyprice}" class="easyui-numberbox" data-options="required:true,min:0,max:99999999,precision:6,groupSeparator:','"/>
								    </td>
									<td width="100px"><div align="right">最低销售价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" name="goodsInfo.lowestsaleprice" value="${goodsInfo.lowestsaleprice}" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"/>
								    </td>
									<td width="110px"><div align="right">基准销售价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" name="goodsInfo.basesaleprice" value="${goodsInfo.basesaleprice}" class="easyui-numberbox" data-options="required:true,min:0,max:99999999,precision:6,groupSeparator:','"/>
								    </td>
						  		</tr>
						  		<tr>
								 	<td ><div align="right">最高库存（总）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" name="goodsInfo.highestinventory" value="${goodsInfo.highestinventory}" class="easyui-numberbox" data-options="min:0,max:99999999,precision:0,groupSeparator:','"></td>
									<td><div align="right">最低库存（总）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" name="goodsInfo.lowestinventory" value="${goodsInfo.lowestinventory}" class="easyui-numberbox" data-options="min:0,max:99999999,precision:0,groupSeparator:','"></td>
									<td><div align="right">安全库存（总）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" name="goodsInfo.safeinventory" value="${goodsInfo.safeinventory}" class="easyui-numberbox" data-options="min:0,max:99999999,precision:0,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">标准（计划）价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" name="goodsInfo.normalprice" value="${goodsInfo.normalprice}" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"/>
								    </td>
									<td><div align="right">盘点方式:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-checktype" value="${goodsInfo.checktype}" name="goodsInfo.checktype" style="width: 120px" /></td>
									<td><div align="right">盘点周期:</div></td>
									<td style="width: 5px"></td>
								    <td>
								    	<span style="float: left;">
								    	<input type="text" name="goodsInfo.checkdate" style="width:80px; float:left;" class="easyui-numberbox" data-options="min:0,max:9999999999,groupSeparator:','">
									    <select name="goodsInfo.checkunit" style="width:40px; float:right;">
									      <option value="1" selected="selected">天</option>
										  <option value="2">周</option>
									      <option value="3">月</option>
									      <option value="4">年</option>
									    </select>
								    	</span>
								    </td>
								</tr>
								<tr>
								 	<td><div align="right">默认采购员:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-defaultbuyer" type="text" style="width: 120px;" name="goodsInfo.defaultbuyer" value="<c:out value="${goodsInfo.defaultbuyer }"></c:out>"/></td>
									<td><div align="right">默认业务员:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-defaultsaler" type="text" style="width: 120px;" name="goodsInfo.defaultsaler" value="<c:out value="${goodsInfo.defaultsaler }"></c:out>"/></td>
									<td><div align="right">默认供应商:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-supplierWidget-defaultsupplier" type="text" style="width: 120px;" name="goodsInfo.defaultsupplier" text="<c:out value="${goodsInfo.defaultsupplierName }"></c:out>" value="<c:out value="${goodsInfo.defaultsupplier }"></c:out>"/></td>
								</tr>
								<tr>
								 	<td><div align="right">默认税种:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-defaulttaxtype" type="text" style="width: 120px;" name="goodsInfo.defaulttaxtype" value="<c:out value="${goodsInfo.defaulttaxtype}"></c:out>"/>
								    </td>
									<td><div align="right">计划毛利率%:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" class="easyui-numberbox" name="goodsInfo.planmargin" value="${goodsInfo.planmargin }" data-options="min:0,max:999,precision:2"/>
								    </td>
									<td><div align="right">核算成本价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" class="easyui-numberbox" value="${goodsInfo.costaccountprice}" name="goodsInfo.costaccountprice" data-options="min:0,max:99999999,precision:6,groupSeparator:','"/>
								    </td>
							  	</tr>
							  	<tr>
							  		<td><div align="right">采购箱价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-buyboxprice" style="width: 120px;" name="goodsInfo.buyboxprice" value="${goodsInfo.buyboxprice }" /></td>
                                    <td><div align="right">成本未分摊金额:</div></td>
                                    <td style="width: 5px"></td>
                                    <td><input type="text" style="width: 120px;"readonly="readonly" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','" /></td>
                                </tr>
						  </tbody>
						</table>
						<div style="margin-top:30px;margin-left:20px;">
							<div class="easyui-panel" title="最新动态" style="width:960px">
								<table cellspacing="5px" cellpadding="5px" border="0">
									<tbody>
								      	<tr>
									 		<td width="80px"><div align="right">最新采购价:</div></td>
											<td style="width: 5px"></td>
									   		<td><input type="text" id="goodsInfo-numberbox-newbuyprice" style="width: 120px;" name="goodsInfo.newbuyprice" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"
									   			<c:choose>
													<c:when test="${showMap.newbuyprice==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newbuyprice}" </c:otherwise>
												</c:choose>/>
									   		</td>
									   		<td ><div align="right">最新采购总数量:</div></td>
											<td style="width: 5px"></td>
									   		<td><input type="text" style="width: 120px;" readonly="readonly" name="goodsInfo.newtotalbuynum" class="easyui-numberbox" data-options="precision:0,groupSeparator:','" 
									   			<c:choose>
													<c:when test="${showMap.newtotalbuynum==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newtotalbuynum}" </c:otherwise>
												</c:choose>/>
											</td>
											<td ><div align="right">最新采购总金额:</div></td>
											<td style="width: 5px"></td>
									   		<td><input type="text" style="width: 120px;" readonly="readonly" name="goodsInfo.newtotalbuyamount" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"
									   			<c:choose>
													<c:when test="${showMap.newtotalbuyamount==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newtotalbuyamount}" </c:otherwise>
												</c:choose>/>
											</td>
								  		</tr>
										<tr>
											<td width="130px"><div align="right">最新销售价:</div></td>
											<td style="width: 5px"></td>
									   		<td><input type="text" style="width: 120px;" name="goodsInfo.newsaleprice" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"
									   			<c:choose>
													<c:when test="${showMap.newsaleprice==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newsaleprice}" </c:otherwise>
												</c:choose>/>
									   		</td>
											<td width="130px"><div align="right">最新库存价:</div></td>
											<td style="width: 5px"></td>
									    	<td><input type="text" style="width: 120px;" name="goodsInfo.newstorageprice" class="easyui-numberbox" data-options="max:99999999,precision:6,groupSeparator:','"
									    		<c:choose>
													<c:when test="${showMap.newstorageprice==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newstorageprice}" </c:otherwise>
												</c:choose>/>
									    	</td>
										 	<td ><div align="right">最新采购日期:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px;" name="goodsInfo.newbuydate"
										    	<c:choose>
													<c:when test="${showMap.newbuydate==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newbuydate}" </c:otherwise>
												</c:choose>/>
										    </td>
										 </tr>
										 <tr>
										 	<td><div align="right">最新销售日期:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px;" name="goodsInfo.newsaledate"
										    	<c:choose>
													<c:when test="${showMap.newsaledate==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newsaledate}" </c:otherwise>
												</c:choose>/>
										    </td>
											<td ><div align="right">每单平均销量:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px;" name="goodsInfo.everybillaveragesales" class="easyui-numberbox" data-options="precision:0,groupSeparator:','" 
										    	<c:choose>
													<c:when test="${showMap.everybillaveragesales==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.everybillaveragesales}" </c:otherwise>
												</c:choose>/>
										    </td>
										 	<td ><div align="right">最新入库日期:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px;" name="goodsInfo.newinstroragedate"
										    	<c:choose>
													<c:when test="${showMap.newinstroragedate==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newinstroragedate}" </c:otherwise>
												</c:choose>/>
										    </td>
								  		</tr>
									   	<tr>
									   		<td ><div align="right">最新出库日期:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px;" name="goodsInfo.newoutstoragedate"
										    	<c:choose>
													<c:when test="${showMap.newoutstoragedate==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newoutstoragedate}" </c:otherwise>
												</c:choose>/>
										    </td>
											<td ><div align="right">最新库存（总）:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px;" name="goodsInfo.newinventory"
										    	<c:choose>
													<c:when test="${showMap.newinventory==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newinventory}" </c:otherwise>
												</c:choose>/>
										    </td>
									 		<td ><div align="right">最新盘点日期:</div></td>
											<td style="width: 5px"></td>
									    	<td><input type="text" style="width: 120px;" name="goodsInfo.newcheckdate"
									    		<c:choose>
													<c:when test="${showMap.newcheckdate==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newcheckdate}" </c:otherwise>
												</c:choose>/>
									    	</td>
									  	</tr>
								    </tbody>
								 </table>
							 </div>
						</div>
					</form>
				</div>
				<div class="tagsDiv_item">
					<table id="goodsInfo-table-priceInfoList" ></table>
				</div>
				<div class="tagsDiv_item">
					<table id="goodsInfo-table-meteringUnitInfo"></table>
				</div>
				<div class="tagsDiv_item">
					<table id="goodsInfo-table-StorageInfo"></table>
				</div>
				<div class="tagsDiv_item">
					<table id="goodsInfo-table-WaresClass"></table>
				</div>
				<div class="tagsDiv_item">
					<table id="goodsInfo-table-StorageLocation"></table>
				</div>
				<div class="tagsDiv_item">
					<form action="" method="post" id="wares-form-goodsInfoPhysicsInfo" >
						<table cellspacing="5px" cellpadding="5px" border="0">
							 <tbody>
							 	<tr>
									<td width="80px"><div align="right">商品形状:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-gshape" value="1" name="goodsInfo.gshape" value="${goodsInfo.gshape }" style="width: 120px" /></td>
									<td width="120px"><div align="right"></div></td>
									<td style="width: 5px"></td>
								    <td></td>
									<td width="240px" colspan="3" rowspan="7">
										<table cellpadding="0" cellspacing="0" style="margin: 10px 0px 10px 55px;border:1px black solid;width:120px;height:168px;">
											<tr><td colspan="2">
												<img id="goodsInfo-img-preview" width="165px" height="186px" alt="商品图片" style="diplay:none;" 
													<c:choose>
														<c:when test="${goodsInfo.image != null && goodsInfo.image != ''}">
															src="${goodsInfo.image}"
														</c:when>
														<c:otherwise>
															src="./image/photo_per_default.jpg"
														</c:otherwise>
													</c:choose>/>
											</td></tr>
											<tr><td style="width: 5px" colspan="2" ></td></tr>
										</table>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										照片上传：<a href="javaScript:void(0);" id="goodsInfo-button-uploadImg" class="easyui-linkbutton" title="浏览图片">浏览</a>
										<a href="javaScript:void(0);" id="goodsInfo-button-showOldImg" class="easyui-linkbutton"  title="查看原图">查看原图</a>
									</td>
								</tr>
								<tr>
								 	<td><div align="right">长度（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-glength" style="width:120px;" name="goodsInfo.glength" value="${goodsInfo.glength }"></td>
									<td><div align="right">长度转换因子（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.gmlength" value="${goodsInfo.gmlength }" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right"> 宽度（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-gwidth" style="width:120px;" name="goodsInfo.gwidth" value="${goodsInfo.gwidth }"></td>
									<td><div align="right">宽度转换因子（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.gmwidth" value="${goodsInfo.gmwidth }" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">高度（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-ghight" style="width:120px;" name="goodsInfo.ghight" value="${goodsInfo.ghight }"></td>
									<td><div align="right">高度转换因子（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.gmhight" value="${goodsInfo.gmhight }" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">直径（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.gdiameter" value="${goodsInfo.gdiameter }" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','"></td>
									<td><div align="right">直径转换因子（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.gmdiameter" value="${goodsInfo.gmdiameter }" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">毛重（kg）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-grossweight" style="width:120px;" name="goodsInfo.grossweight" value="${goodsInfo.grossweight }"></td>
									<td><div align="right">毛重转换因子（kg）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.kgweight" value="${goodsInfo.kgweight}" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">净重（kg）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width:120px;" name="goodsInfo.netweight" value="${goodsInfo.netweight}" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:6,groupSeparator:','"></td>
									<td><div align="right">箱重（kg）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-totalweight" style="width: 120px;" name="goodsInfo.totalweight" value="${goodsInfo.totalweight }"></td>
								</tr>
								<tr>
								 	<td><div align="right">箱体积（m<sup>3</sup>）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-totalvolume" style="width: 120px;" name="goodsInfo.totalvolume" value="${goodsInfo.totalvolume }"></td>
									<td><div align="right">单体积（m<sup>3</sup>）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-singlevolume" style="width: 120px;" name="goodsInfo.singlevolume" value="${goodsInfo.singlevolume }"></td>
								</tr>
								<tr>
									<td width="80px"><div align="right">备注:</div></td>
									<td style="width: 5px"></td>
								    <td colspan="7"><textarea name="goodsInfo.physicsremark" cols="" rows="5" style="width:830px;"><c:out value="${goodsInfo.physicsremark}"></c:out></textarea></td>
								</tr> 
							</tbody>
						</table>
					</form>
				</div>
				<div class="tagsDiv_item">
					<form action="" method="post" id="wares-form-goodsInfoDefineInfo" >
						<table border="0" cellpadding="2px" cellspacing="2px">
							<tr>
								<td><label>
								<c:choose>
									<c:when test="${fieldmap.field01 != null}"><c:out value="${fieldmap.field01 }"></c:out></c:when>
									<c:otherwise>自定义信息01</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="goodsInfo.field01" class="easyui-validatebox" validType="maxLen[100]" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field01==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field01}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field02 != null}"><c:out value="${fieldmap.field02 }"></c:out></c:when>
									<c:otherwise>自定义信息02</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field02" class="easyui-validatebox" validType="maxLen[100]" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field02==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field02}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field03 != null}"><c:out value="${fieldmap.field03 }"></c:out></c:when>
									<c:otherwise>自定义信息03</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field03" class="easyui-validatebox" validType="maxLen[100]" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field03==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field03}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field04 != null}"><c:out value="${fieldmap.field04 }"></c:out></c:when>
									<c:otherwise>自定义信息04</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field04" class="easyui-validatebox" validType="maxLen[100]" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field04==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field04}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field05 != null}"><c:out value="${fieldmap.field05 }"></c:out></c:when>
									<c:otherwise>自定义信息05</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field05" class="easyui-validatebox" validType="maxLen[100]" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field05==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field05}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field06 != null}"><c:out value="${fieldmap.field06 }"></c:out></c:when>
									<c:otherwise>自定义信息06</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field06" class="easyui-validatebox" validType="maxLen[100]" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field06==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field06}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field07 != null}"><c:out value="${fieldmap.field07 }"></c:out></c:when>
									<c:otherwise>自定义信息07</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field07" class="easyui-validatebox" validType="maxLen[100]" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field07==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field07}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field08 != null}"><c:out value="${fieldmap.field08 }"></c:out></c:when>
									<c:otherwise>自定义信息08</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field08" class="easyui-validatebox" validType="maxLen[100]" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field08==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field08}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field09 != null}"><c:out value="${fieldmap.field09 }"></c:out></c:when>
									<c:otherwise>自定义信息09</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field09" class="easyui-validatebox" validType="maxLen[100]" type="text" style="width: 120px"
									<c:choose>
										<c:when test="${showMap.field09==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field09}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field10 != null}"><c:out value="${fieldmap.field10 }"></c:out></c:when>
									<c:otherwise>自定义信息10</c:otherwise>
								</c:choose></label>：</td>
								<td colspan="5"><input type="text" name="goodsInfo.field10" style="width: 625px"
									<c:choose>
										<c:when test="${showMap.field10==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field10}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field11 != null}"><c:out value="${fieldmap.field11 }"></c:out></c:when>
									<c:otherwise>自定义信息11</c:otherwise>
								</c:choose></label>：</td>
								<td colspan="5"><input type="text" name="goodsInfo.field11" style="width: 625px"
									<c:choose>
										<c:when test="${showMap.field11==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field11}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field12 != null}"><c:out value="${fieldmap.field12 }"></c:out></c:when>
									<c:otherwise>自定义信息12</c:otherwise>
								</c:choose></label>：</td>
								<td colspan="5"><input type="text" name="goodsInfo.field12" style="width: 625px"
									<c:choose>
										<c:when test="${showMap.field12==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>value="<c:out value="${goodsInfo.field12}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
                            <!-- 该行没有意义，保证页面对齐设置-->
                            <tr>
                                <td><label></label> </td>
                                <td colspan="5">
                                    <input type="text" name="lineInfo.field12" style="width: 650px;border: 2px solid snow;"
                                            <c:choose>
                                                <c:when test="${showMap.field12==null}"> value=""</c:when>
                                                <c:otherwise>value="<c:out value="${lineInfo.field12}"></c:out>" </c:otherwise>
                                            </c:choose> />
                                </td>
                            </tr>
						</table>
					</form>
				</div>
			</div>
    	</div>
    </div>
    <script type="text/javascript">
    	var goods_title = tabsWindowTitle('/basefiles/showGoodsInfoListPage.do');
    	//跳转对应分类标签
		$("#goodsInfo-dblclick-waresClass").dblclick(function(){
			$(".tags").find("li").eq(5).trigger("click")
		});
		
		//跳转对应仓库标签
		$("#goodsInfo-dblclick-storage").dblclick(function(){
			$(".tags").find("li").eq(4).trigger("click")
		});
    	
    	//跳转对应库位标签
		$("#goodsInfo-dblclick-storagelocation").dblclick(function(){
			$(".tags").find("li").eq(6).trigger("click");
		});
    	
    	var $goodsInfo_headInfo = $("#goodsInfo-form-head");//商品档案基本主信息 
		var $goodsInfo_baseInfo = $("#wares-form-goodsInfoBaseInfo");//基本信息
		var $goodsInfo_controlInfo = $("#wares-form-goodsInfoControlInfo");//控制信息
		var $goodsInfo_physicsInfo = $("#wares-form-goodsInfoPhysicsInfo");//物理信息
		var $goodsInfo_defineInfo = $("#wares-form-goodsInfoDefineInfo");//自定义信息
		
		function goodsInfoFormValidate(){//true验证成功，false验证失败
			return $goodsInfo_headInfo.form('validate') && $goodsInfo_baseInfo.form('validate') && $goodsInfo_controlInfo.form('validate') && 
				$goodsInfo_physicsInfo.form('validate') && $goodsInfo_defineInfo.form('validate');
		}
		//商品档案数据表单json对象集合
		function goodsInfo_JSONs(){
			var wholeInfo = $goodsInfo_headInfo.serializeJSON();
			var baseInfo = $goodsInfo_baseInfo.serializeJSON();
			var controlInfo = $goodsInfo_controlInfo.serializeJSON();
			var priceInfo = priceJson();
			var meteringUnitInfo = MUJson();
			var storageInfo = storageJson();
			var waresClassInfo = WCJson();
			var SLInfo = SLJson();
			var physicsInfo = $goodsInfo_physicsInfo.serializeJSON();
			var defineInfo = $goodsInfo_defineInfo.serializeJSON();
			for(key in baseInfo){
				wholeInfo[key] = baseInfo[key];
			};
			for(key in controlInfo){
				wholeInfo[key] = controlInfo[key];
			};
			for(key in priceInfo){
				wholeInfo[key] = priceInfo[key];
			};
			for(key in meteringUnitInfo){
				wholeInfo[key] = meteringUnitInfo[key];
			};
			for(key in storageInfo){
				wholeInfo[key] = storageInfo[key];
			};
			for(key in waresClassInfo){
				wholeInfo[key] = waresClassInfo[key];
			};
			for(key in SLInfo){
				wholeInfo[key] = SLInfo[key];
			};
			for(key in physicsInfo){
				wholeInfo[key] = physicsInfo[key];
			};
			for(key in defineInfo){
				wholeInfo[key] = defineInfo[key];
			};
			return wholeInfo;
		}
    	
    	function addGoodsInfo(type){
    		$("#goodsInfo-hidden-addType").val(type);
    		var ret = goodsInfo_AjaxConn(goodsInfo_JSONs(),'basefiles/addGoodsInfo.do');
    		var retJson = $.parseJSON(ret);
    		if(retJson.flag){
    			if (top.$('#tt').tabs('exists',goods_title)){
    				var queryJSON = tabsWindow(goods_title).$("#wares-form-goodsInfoListQuery").serializeJSON();
    				tabsWindow(goods_title).$("#wares-table-goodsInfoList").datagrid('load',queryJSON);
    			}
    			panelRefresh('basefiles/showGoodsInfoViewPage.do?id='+$("#goodsInfo-id-baseInfo").val(),' 商品档案【详情】','view');
				$dgPriceInfoList = null,$dgMeteringUnitInfoList = null,$dgStorageInfoList = null,$dgWaresClassInfoList = null,$dgStorageLocationInfoList = null;
				$("#goodsInfo-div-button").buttonWidget("addNewDataId",$("#goodsInfo-id-baseInfo").val());
				if(type == "save"){
					$.messager.alert("提醒","保存成功!");
				}
				else{
					$.messager.alert("提醒","暂存成功!");
				}
			}
			else{
				if(type == "save"){
					$.messager.alert("提醒","保存失败!");
				}
				else{
					$.messager.alert("提醒","暂存失败!");
				}
			}
    	}
    	 //上传图片按钮，浏览 
		$("#goodsInfo-button-uploadImg").upload({
			auto: true,
			del: false,
			allowType:"*.jpg;*.gif;*.bmp;*.png",
			onComplete: function(json){
				if(json.flag == "1"){
					$("#z-upload-dialog").dialog('close',true);
					$("#goodinfo-hidden-hdimage").val(json.fullPath);
					$("#goodsInfo-img-preview").attr("src",json.fullPath);
				}
				else{
					$.messager.alert("提醒","图片上传失败!");
				}
			}
		});
		
		//查看原图
		$("#goodsInfo-button-showOldImg").click(function(){
			if($(this).linkbutton('options').disabled){
				return false;
			}
			var photograph = document.getElementById("goodsInfo-img-preview").getAttribute("src");
			$('#goodsInfo-window-showOldImg').window({  
				    title: '查看原图',  
				    width: $(window).width(),  
				    height: $(window).height(),  
				    closed: true,  
				    cache: false,  
				    modal: true 
				});
			$("#goodsInfo-window-showOldImg").window("open");
			$("#goodsInfo-window-showOldImg").window("refresh","basefiles/showGoodsInfoOldImgPage.do?photograph="+photograph);
		});
    	$(function(){
    		$("#goodsInfo-div-button").buttonWidget("initButtonType","add");
    		
    		$("#goodsInfo-div-button").buttonWidget("disableMenu","printMenuButton");
    		
    		var $dgPriceInfoList = null,$dgMeteringUnitInfoList = null,$dgStorageInfoList = null,$dgWaresClassInfoList = null,$dgStorageLocationInfoList = null;
    		
    		loadDropdown();
    		
    		getTabs("copy");
    	});
    </script>
  </body>
</html>
