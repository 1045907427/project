<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品档案详情页面</title>
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
						    <td><input id="goodsInfo-id-baseInfo" type="text" style="width: 120px ;" name="goodsInfo.id" value="<c:out value="${goodsInfo.id}"></c:out>" readonly="readonly"/>
						    	<input id="goodsPriceInfo-hidden-delIds" type="hidden">
						    	<input id="goodsPriceInfo-hidden-addClickNum" type="hidden" value="0">
						    	<input id="goodsMeteringUnitInfo-hidden-delIds" type="hidden">
						    	<input id="goodsMeteringUnitInfo-hidden-addClickNum" type="hidden" value="0">
						    	<input id="goodsStorageInfo-hidden-delIds" type="hidden">
						    	<input id="goodsStorageInfo-hidden-addClickNum" type="hidden" value="0">
						    	<input id="goodsWaresClass-hidden-delIds" type="hidden">
						    	<input id="goodsWaresClass-hidden-addClickNum" type="hidden" value="0">
						    	<input id="goodsInfo-hidden-addType" type="hidden" name="type">
						    	<input id="goodinfo-hidden-hdimage" type="hidden" name="goodsInfo.image" value="${goodsInfo.image}"/>
						    	<input id="goodsInfo-hidden-operateType" type="hidden" value="${operateType }">
						    	<input id="goodsInfo-dblclick-hdstorageid" type="hidden" name="goodsInfo.storageid" value="<c:out value="${goodsInfo.storageid}"></c:out>">
						    	<input id="goodsInfo-dblclick-hdwaresClassid" type="hidden" name="goodsInfo.defaultsort" value="<c:out value="${goodsInfo.defaultsort}"></c:out>"/>
						    	<input id="goodsInfo-dblclick-hdstoragelocation" type="hidden" name="goodsInfo.storagelocation" value="<c:out value="${goodsInfo.storagelocation}"></c:out>"/>
						  		<input id="goodsInfo-defaulttaxtype" type="hidden" value="<c:out value="${goodsInfo.defaulttaxtype }"></c:out>"/>
						    </td>
							<td width="80px"><div align="right">名称:</div></td>
							<td style="width: 5px"></td>
						    <td><input type="text" style="width: 200px ;" name="goodsInfo.name" value="<c:out value="${goodsInfo.name}"></c:out>" readonly="readonly"/></td>
							<td width="80px"><div align="right">状态:</div></td>
							<td style="width: 5px"></td>
						    <td><input id="goodsInfo-widget-state" value="${goodsInfo.state}" disabled="disabled" style="width: 200px" /></td>
						</tr>
						<tr>
						  	<td width="80px"><div align="right">规格型号:</div></td>
							<td style="width: 5px"></td>
						    <td colspan="4"><input type="text" style="width:450px;" name="goodsInfo.model" value="<c:out value="${goodsInfo.model}"></c:out>" readonly="readonly"/></td>
						 	<td width="80px"><div align="right">单位:</div></td>
							<td style="width: 5px"></td>
						    <td><input id="goodsInfo-widget-meteringUnit" type="text" style="width: 120px ;" name="goodsInfo.mainunit" value="<c:out value="${goodsInfo.mainunit}"></c:out>" disabled="disabled"/></td>
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
									<td><input type="text" name="goodsInfo.pinyin" value="<c:out value="${goodsInfo.pinyin }"></c:out>" style="width: 120px" readonly="readonly"/></td>
									<td width="80px"><div align="right">商品类型:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-widget-goodstype" name="goodsInfo.goodstype" value="<c:out value="${goodsInfo.goodstype}"></c:out>" disabled="disabled" style="width: 120px "/></td>
									<td width="80px"><div align="right">助记码:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.spell" value="<c:out value="${goodsInfo.spell}"></c:out>" readonly="readonly" class="easyui-validatebox" validType="maxLen[20]"/></td>
									<td width="80px"><div align="right"></div></td>
									<td style="width: 5px"></td>
								    <td></td>
								</tr>
						  		<tr>
									<td width="80px"><div align="right">商品品牌:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-brand" type="text" style="width: 120px ;" name="goodsInfo.brand" value="<c:out value="${goodsInfo.brand}"></c:out>" disabled="disabled"/></td>
									<td width="80px"><div align="right">所属部门:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-deptid" type="text" style="width: 120px;" name="goodsInfo.deptid" value="<c:out value="${goodsInfo.deptid }"></c:out>" disabled="disabled"/></td>
								    <td width="80px"><div align="right">默认分类:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-dblclick-waresClass" type="text" style="width: 120px ;" disabled="disabled" value="<c:out value="${goodsInfo.defaultsortName}"></c:out>"/></td>
						 		</tr>
								<tr>
									<td width="80px"><div align="right">条形码:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.barcode" value="<c:out value="${goodsInfo.barcode}"></c:out>" readonly="readonly"/></td>
									<td width="80px"><div align="right">箱装条码:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.boxbarcode" value="<c:out value="${goodsInfo.boxbarcode}"></c:out>" readonly="readonly"/></td>
									<td width="80px"><div align="right">商品货位:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.itemno" value="<c:out value="${goodsInfo.itemno}"></c:out>" readonly="readonly"/></td>
								</tr>
								<tr>
									<td width="80px"><div align="right">ABC码:</div></td>
									<td style="width: 5px"></td>
								    <td><select style="width: 120px ;" disabled="disabled" name="goodsInfo.abclevel">
								    		<option></option>
								    		<option value="A" <c:if test="${goodsInfo.abclevel=='A'}">selected="selected"</c:if> >A</option>
								    		<option value="B" <c:if test="${goodsInfo.abclevel=='B'}">selected="selected"</c:if> >B</option>
								    		<option value="C" <c:if test="${goodsInfo.abclevel=='C'}">selected="selected"</c:if> >C</option>
								    	</select></td>
									<td width="80px"><div align="right">购销类型:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-bstype" name="goodsInfo.bstype" value="${goodsInfo.bstype}" disabled="disabled" style="width: 120px "/></td>
									<td width="80px"><div align="right">产地:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" name="goodsInfo.productfield" value="<c:out value="${goodsInfo.productfield }"></c:out>" readonly="readonly"/>
								    	<input type="hidden" style="width: 120px ;" name="goodsInfo.sortkey" value="${goodsInfo.sortkey}" disabled="disabled"/>
								    </td>
								</tr>
								<tr>
									<td width="80px"><div align="right">是否采购:</div></td>
									<td style="width: 5px"></td>
								    <td><select name="goodsInfo.isinoutstorage" disabled="disabled" style="width: 120px ;">
								    		<option value="1" <c:if test="${goodsInfo.isinoutstorage=='1'}">selected="selected"</c:if> >是</option>
								    		<option value="0" <c:if test="${goodsInfo.isinoutstorage=='0'}">selected="selected"</c:if> >否</option>
								    	</select></td>
									<td width="80px"><div align="right">默认仓库:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-dblclick-storage" type="text" style="width: 120px ;" disabled="disabled" value="<c:out value="${goodsInfo.storageName}"></c:out>"/></td>
									<td width="80px"><div align="right">默认库位:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-dblclick-storagelocation" type="text" style="width: 120px ;" disabled="disabled" value="<c:out value="${goodsInfo.storagelocationName}"></c:out>"/></td>
						 		</tr>
						  		<tr>
									<td width="80px"><div align="right">库位管理:</div></td>
									<td style="width: 5px"></td>
								    <td><select name="goodsInfo.isstoragelocation" disabled="disabled" style="width: 120px ;">
								    		<option value="1" <c:if test="${goodsInfo.isstoragelocation=='1'}">selected="selected"</c:if> >是</option>
								    		<option value="0" <c:if test="${goodsInfo.isstoragelocation=='0'}">selected="selected"</c:if> >否</option>
								    	</select></td>
									<td width="80px"><div align="right">保质期管理:</div></td>
									<td style="width: 5px"></td>
								    <td><select name="goodsInfo.isshelflife" disabled="disabled" style="width: 120px ;">
								    		<option value="1" <c:if test="${goodsInfo.isshelflife=='1'}">selected="selected"</c:if> >是</option>
								    		<option value="0" <c:if test="${goodsInfo.isshelflife=='0'}">selected="selected"</c:if> >否</option>
								   		</select></td>
									<td width="80px"><div align="right">保质期:</div></td>
									<td style="width: 5px"></td>
								    <td><span style="float: left;">
								    	<input type="text" name="goodsInfo.shelflife" value="${goodsInfo.shelflife}" readonly="readonly" style="width:80px; float:left;" class="easyui-numberbox" data-options="min:0,max:9999999999,groupSeparator:','"/>
									    <select name="goodsInfo.shelflifeunit" disabled="disabled" style="width:40px; float:right;">
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
								    <td><select name="goodsInfo.isbatch" disabled="disabled" style="width: 120px ;">
								    		<option value="1" <c:if test="${goodsInfo.isbatch=='1'}">selected="selected"</c:if> >是</option>
								    		<option value="0" <c:if test="${goodsInfo.isbatch=='0'}">selected="selected"</c:if> >否</option>
								    	</select></td>
						  		</tr>
						   		<tr>
						 			<td width="80px"><div align="right">备注:</div></td>
									<td style="width: 5px"></td>
						            <td colspan="7"><textarea name="goodsInfo.remark" cols="" readonly="readonly" rows="5" style="width:650px;"><c:out value="${goodsInfo.remark}"></c:out></textarea></td>
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
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.highestbuyprice" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"
								    	<c:choose>
											<c:when test="${showMap.highestbuyprice==null}">disabled="disabled" value=""</c:when>
											<c:otherwise>readonly="readonly" value="${goodsInfo.highestbuyprice}" </c:otherwise>
										</c:choose>/>
								    </td>
									<td width="100px"><div align="right">最低销售价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.lowestsaleprice" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"
								    	<c:choose>
											<c:when test="${showMap.lowestsaleprice==null}">disabled="disabled" value=""</c:when>
											<c:otherwise>readonly="readonly" value="${goodsInfo.lowestsaleprice}" </c:otherwise>
										</c:choose>/>
								    </td>
									<td width="110px"><div align="right">基准销售价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.basesaleprice" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"
								    	<c:choose>
											<c:when test="${showMap.basesaleprice==null}">disabled="disabled" value=""</c:when>
											<c:otherwise>readonly="readonly" value="${goodsInfo.basesaleprice}" </c:otherwise>
										</c:choose>/>
								    </td>
						  		</tr>
						  		<tr>
								 	<td ><div align="right">最高库存（总）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.highestinventory" readonly="readonly" value="${goodsInfo.highestinventory}" class="easyui-numberbox" data-options="min:0,max:99999999,precision:0,groupSeparator:','"></td>
									<td><div align="right">最低库存（总）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.lowestinventory" readonly="readonly" value="${goodsInfo.lowestinventory}" class="easyui-numberbox" data-options="min:0,max:99999999,precision:0,groupSeparator:','"></td>
									<td><div align="right">安全库存（总）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.safeinventory" readonly="readonly" value="${goodsInfo.safeinventory}" class="easyui-numberbox" data-options="min:0,max:99999999,precision:0,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">标准（计划）价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.normalprice" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"
								    	<c:choose>
											<c:when test="${showMap.normalprice==null}">disabled="disabled" value=""</c:when>
											<c:otherwise>readonly="readonly" value="${goodsInfo.normalprice}" </c:otherwise>
										</c:choose>/>
								    </td>
									<td><div align="right">盘点方式:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-checktype" value="${goodsInfo.checktype}" disabled="disabled" name="goodsInfo.checktype" style="width: 120px " /></td>
									<td><div align="right">盘点周期:</div></td>
									<td style="width: 5px"></td>
								    <td>
								    	<span style="float: left;">
								    	<input type="text" name="goodsInfo.checkdate" style="width:80px; float:left;" readonly="readonly" data-options="min:0,max:9999999999,groupSeparator:','">
									    <select name="goodsInfo.checkunit" disabled="disabled" style="width:40px; float:right;">
									      <option value="1" <c:if test="${goodsInfo.checkunit=='1'}">selected="selected"</c:if> >天</option>
										  <option value="2" <c:if test="${goodsInfo.checkunit=='2'}">selected="selected"</c:if> >周</option>
									      <option value="3" <c:if test="${goodsInfo.checkunit=='3'}">selected="selected"</c:if> >月</option>
									      <option value="4" <c:if test="${goodsInfo.checkunit=='4'}">selected="selected"</c:if> >年</option>
									    </select>
									   	</span>
									 </td>
								</tr>
								<tr>
								 	<td><div align="right">默认采购员:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-defaultbuyer" type="text" style="width: 120px ;" disabled="disabled" name="goodsInfo.defaultbuyer" value="<c:out value="${goodsInfo.defaultbuyer }"></c:out>"/></td>
									<td><div align="right">默认业务员:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-defaultsaler" type="text" style="width: 120px ;" disabled="disabled" name="goodsInfo.defaultsaler" value="<c:out value="${goodsInfo.defaultsaler}"></c:out>"/></td>
									<td><div align="right">默认供应商:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" disabled="disabled" name="goodsInfo.defaultsupplier" value="<c:out value="${goodsInfo.defaultsupplierName}"></c:out>"/></td>
								</tr>
								<tr>
								 	<td><div align="right">默认税种:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="goodsInfo-widget-defaulttaxtype" type="text" disabled="disabled" style="width: 120px ;" name="goodsInfo.defaulttaxtype"
								    	<c:choose>
											<c:when test="${showMap.defaulttaxtype==null}">value=""</c:when>
											<c:otherwise>value="<c:out value="${goodsInfo.defaulttaxtype }"></c:out>" </c:otherwise>
										</c:choose>/>
								    </td>
									<td><div align="right">计划毛利率%:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" class="easyui-numberbox" name="goodsInfo.planmargin" data-options="min:0,max:999,precision:2"
								    	<c:choose>
											<c:when test="${showMap.planmargin==null}">disabled="disabled" value=""</c:when>
											<c:otherwise>readonly="readonly" value="${goodsInfo.planmargin}" </c:otherwise>
										</c:choose>/>
								    </td>
									<td><div align="right">核算成本价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" class="easyui-numberbox" name="goodsInfo.costaccountprice" data-options="min:0,max:99999999,precision:6,groupSeparator:','"
								    	<c:choose>
											<c:when test="${showMap.costaccountprice==null}">disabled="disabled" value=""</c:when>
											<c:otherwise>readonly="readonly" value="${goodsInfo.costaccountprice}" </c:otherwise>
										</c:choose>/>
								    </td>
							  	</tr>
							  	<tr>
							  		<td><div align="right">采购箱价:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px;" name="goodsInfo.buyboxprice" value="${goodsInfo.buyboxprice }" readonly="readonly" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','" /></td>
                                    <td><div align="right">成本未分摊金额:</div></td>
                                    <td style="width: 5px"></td>
                                    <td><input type="text" style="width: 120px;" value="${goodsInfo.field12 }" readonly="readonly" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','" /></td>

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
									   		<td><input type="text" id="goodsInfo-numberbox-newbuyprice" style="width: 120px ;" name="goodsInfo.newbuyprice" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"
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
									   		<td><input type="text" style="width: 120px;" readonly="readonly" name="goodsInfo.newtotalbuyamount" class="easyui-numberbox" data-options="precision:6,groupSeparator:','"
									   			<c:choose>
													<c:when test="${showMap.newtotalbuyamount==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newtotalbuyamount}" </c:otherwise>
												</c:choose>/>
											</td>
								  		</tr>
										<tr>
										 	<td ><div align="right">最新采购日期:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px ;" name="goodsInfo.newbuydate"
										    	<c:choose>
													<c:when test="${showMap.newbuydate==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newbuydate}" </c:otherwise>
												</c:choose>/>
										    </td>
										    <td width="100px"><div align="right">最新销售价:</div></td>
											<td style="width: 5px"></td>
									   		<td><input type="text" style="width: 120px ;" name="goodsInfo.newsaleprice" class="easyui-numberbox" data-options="min:0,max:99999999,precision:6,groupSeparator:','"
									   			<c:choose>
													<c:when test="${showMap.newsaleprice==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newsaleprice}" </c:otherwise>
												</c:choose>/>
									   		</td>
											<td width="110px"><div align="right">最新库存价:</div></td>
											<td style="width: 5px"></td>
									    	<td><input type="text" style="width: 120px ;" name="goodsInfo.newstorageprice" class="easyui-numberbox" data-options="max:99999999,precision:6,groupSeparator:','"
									    		<c:choose>
													<c:when test="${showMap.newstorageprice==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newstorageprice}" </c:otherwise>
												</c:choose>/>
									    	</td>
										 </tr>
										 <tr>
										 	<td ><div align="right">最新入库日期:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px ;" name="goodsInfo.newinstroragedate"
										    	<c:choose>
													<c:when test="${showMap.newinstroragedate==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newinstroragedate}" </c:otherwise>
												</c:choose>/>
										    </td>
										    <td><div align="right">最新销售日期:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px ;" name="goodsInfo.newsaledate"
										    	<c:choose>
													<c:when test="${showMap.newsaledate==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newsaledate}" </c:otherwise>
												</c:choose>/>
										    </td>
											<td ><div align="right">每单平均销量:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px ;" name="goodsInfo.everybillaveragesales" class="easyui-numberbox" data-options="precision:0,groupSeparator:','" 
										    	<c:choose>
													<c:when test="${showMap.everybillaveragesales==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.everybillaveragesales}" </c:otherwise>
												</c:choose>/>
										    </td>
								  		</tr>
									   	<tr>
									   		<td ><div align="right">最新出库日期:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px ;" name="goodsInfo.newoutstoragedate"
										    	<c:choose>
													<c:when test="${showMap.newoutstoragedate==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newoutstoragedate}" </c:otherwise>
												</c:choose>/>
										    </td>
											<td ><div align="right">最新库存（总）:</div></td>
											<td style="width: 5px"></td>
										    <td><input type="text" style="width: 120px ;" name="goodsInfo.newinventory"
										    	<c:choose>
													<c:when test="${showMap.newinventory==null}">disabled="disabled" value=""</c:when>
													<c:otherwise>readonly="readonly" value="${goodsInfo.newinventory}" </c:otherwise>
												</c:choose>/>
										    </td>
									 		<td ><div align="right">最新盘点日期:</div></td>
											<td style="width: 5px"></td>
									    	<td><input type="text" style="width: 120px ;" name="goodsInfo.newcheckdate"
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
								    <td><input id="goodsInfo-widget-gshape" name="goodsInfo.gshape" value="${goodsInfo.gshape }" disabled="disabled" style="width: 120px " /></td>
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
										照片上传：<a href="javaScript:void(0);" id="goodsInfo-button-uploadImg" class="easyui-linkbutton" disabled="true" title="浏览图片">浏览</a>
										<a href="javaScript:void(0);" id="goodsInfo-button-showOldImg" class="easyui-linkbutton"  title="查看原图">查看原图</a>
									</td>
								</tr>
								<tr>
								 	<td><div align="right">长度（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-glength" style="width: 120px ;" name="goodsInfo.glength" value="${goodsInfo.glength }" readonly="readonly"></td>
									<td><div align="right">长度转换因子（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.gmlength" value="${goodsInfo.gmlength }" readonly="readonly" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:4,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">宽度（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-gwidth" style="width: 120px ;" name="goodsInfo.gwidth" value="${goodsInfo.gwidth }" readonly="readonly"></td>
									<td><div align="right">宽度转换因子（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.gmwidth" value="${goodsInfo.gmwidth }" readonly="readonly" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:4,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">高度（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-ghight" style="width: 120px ;" name="goodsInfo.ghight" value="${goodsInfo.ghight }" readonly="readonly"></td>
									<td><div align="right">高度转换因子（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.gmhight" value="${goodsInfo.gmhight }" readonly="readonly" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:4,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">直径（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.gdiameter" value="${goodsInfo.gdiameter }" readonly="readonly" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:4,groupSeparator:','"></td>
									<td><div align="right">直径转换因子（m）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.gmdiameter" value="${goodsInfo.gmdiameter }" readonly="readonly" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:4,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">毛重（kg）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-grossweight" style="width: 120px ;" name="goodsInfo.grossweight" value="${goodsInfo.grossweight }" readonly="readonly"></td>
									<td><div align="right">毛重转换因子（kg）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.kgweight" value="${goodsInfo.kgweight}" readonly="readonly" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:4,groupSeparator:','"></td>
								</tr>
								<tr>
								 	<td><div align="right">净重（kg）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" style="width: 120px ;" name="goodsInfo.netweight" value="${goodsInfo.netweight}" readonly="readonly" class="easyui-numberbox" data-options="min:0,max:999999999999,precision:4,groupSeparator:','"></td>
									<td><div align="right">箱重（kg）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-totalweight" style="width: 120px;" name="goodsInfo.totalweight" value="${goodsInfo.totalweight }" readonly="readonly"></td>
								</tr>
								<tr>
								 	<td><div align="right">箱体积（m<sup>3</sup>）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-totalvolume" style="width: 120px;" name="goodsInfo.totalvolume" value="${goodsInfo.totalvolume }" readonly="readonly"></td>
									<td><div align="right">单体积（m<sup>3</sup>）:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="goodsInfo-numberbox-singlevolume" style="width: 120px;" name="goodsInfo.singlevolume" value="${goodsInfo.singlevolume }" readonly="readonly"></td>
								</tr>
								<tr>
									<td width="80px"><div align="right">备注:</div></td>
									<td style="width: 5px"></td>
								    <td colspan="7"><textarea name="goodsInfo.physicsremark" readonly="readonly" cols="" rows="5" style="width:830px;"><c:out value="${goodsInfo.physicsremark}"></c:out></textarea></td>
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
								<td><input name="goodsInfo.field01" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field01==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field01}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field02 != null}"><c:out value="${fieldmap.field02 }"></c:out></c:when>
									<c:otherwise>自定义信息02</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field02" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field02==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field02}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field03 != null}"><c:out value="${fieldmap.field03 }"></c:out></c:when>
									<c:otherwise>自定义信息03</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field03" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field03==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field03}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field04 != null}"><c:out value="${fieldmap.field04 }"></c:out></c:when>
									<c:otherwise>自定义信息04</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field04" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field04==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field04}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field05 != null}"><c:out value="${fieldmap.field05 }"></c:out></c:when>
									<c:otherwise>自定义信息05</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field05" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field05==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field05}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field06 != null}"><c:out value="${fieldmap.field06 }"></c:out></c:when>
									<c:otherwise>自定义信息06</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field06" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field06==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field06}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field07 != null}"><c:out value="${fieldmap.field07 }"></c:out></c:when>
									<c:otherwise>自定义信息07</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field07" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field07==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field07}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field08 != null}"><c:out value="${fieldmap.field08 }"></c:out></c:when>
									<c:otherwise>自定义信息08</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field08" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field08==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field08}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field09 != null}"><c:out value="${fieldmap.field09 }"></c:out></c:when>
									<c:otherwise>自定义信息09</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="goodsInfo.field09" type="text" style="width: 120px " 
									<c:choose>
										<c:when test="${showMap.field09==null}">disabled="disabled" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field09}"></c:out>" </c:otherwise>
									</c:choose> />
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
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field10}"></c:out>" </c:otherwise>
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
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field11}"></c:out>" </c:otherwise>
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
										<c:otherwise>readonly="readonly" value="<c:out value="${goodsInfo.field12}"></c:out>" </c:otherwise>
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
    	loadDropdown();
    	var id = encodeURIComponent($("#goodsInfo-id-baseInfo").val());
    	//var taxtype = $("#goodsInfo-defaulttaxtype").val();
    	var priceUrl = "basefiles/showPriceInfoList.do?goodsid="+id+"&taxtype="+$("#goodsInfo-defaulttaxtype").val()+"&type=view";
		var MUUrl = "basefiles/showMeteringUnitInfoList.do?goodsid="+id;
		var storageUrl = "basefiles/showStorageInfoList.do?goodsid="+id;
		var waresClassUrl = "basefiles/showWaresClassInfoList.do?goodsid="+id;
		var SLUrl = "basefiles/showGoodsStorageLocationList.do?goodsid="+id;
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
    		$("#goodsInfo-div-button").buttonWidget("setDataID",{id:$("#goodsInfo-id-baseInfo").val(),state:"${goodsInfo.state}",type:"view"});
    		
    		$("#goodsInfo-div-button").buttonWidget("enableMenu","printMenuButton");
    		
    		$(".tags").find("li").click(function(){
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 2){
					var $dgPriceInfoList = $("#goodsInfo-table-priceInfoList");
					if(!$dgPriceInfoList.hasClass("create-datagrid")){
						$dgPriceInfoList.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			url:priceUrl,
				  			border:false,
				   			columns:[[
				   				{field:'id',title:'编号',width:150,hidden:true},
				   				{field:'goodsid',title:'商品编码',width:150,hidden:true},
				   				{field:'code',title:'价格套编码',width:100,hidden:true},
				  				{field:'name',title:'价格套名称',width:100,align:'center'},
				  				{field:'taxprice',title:'含税单价',width:80,align:'right',
				  					formatter:function(val,rowData,rowIndex){
				  						if(val != "" && val != null){
				  							return formatterMoney(val);
				  						}
				  					}
				  				},
				  				{field:'taxtype',title:'税种',width:100,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return rowData.taxtypeName;
				  					}
				  				},
				  				{field:'price',title:'无税单价',width:85,align:'right',
									formatter:function(val,rowData,rowIndex){
				  						if(val != "" && val != null){
				  							return formatterMoney(val);
				  						}
				  					}
								},
				  				{field:'remark',title:'备注',width:150,align:'center'}
				   			]]
				    	});
						$dgPriceInfoList.addClass("create-datagrid");
					}
				}
				else if(index == 3){
					var $dgMeteringUnitInfoList = $("#goodsInfo-table-meteringUnitInfo");
					if(!$dgMeteringUnitInfoList.hasClass("create-datagrid")){
						$dgMeteringUnitInfoList.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			url:MUUrl,
				  			border:false,
				   			columns:[[
				   				{field:'id',title:'编号',width:150,hidden:true},
				   				{field:'goodsid',title:'商品编码',width:150,hidden:true},
				   				{field:'meteringunitid',title:'计量单位',width:100,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return rowData.meteringunitName;
				  					}
				  				},
				  				{field:'isdefault',title:'默认辅单位',width:80,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						if(val == "1"){
				  							return "是";
				  						}
				  						else{
				  							return "否";
				  						}
				  					}
				  				},
				  				{field:'type',title:'换算类型',width:80,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return getSysCodeName("priceType",val);
				  					}
				  				},
				  				{field:'mode',title:'换算方式',width:80,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return getSysCodeName("priceMode",val);
				  					}
				  				},
				  				{field:'rate',title:'换算比率',width:80,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						if(val != "" && val != null){
				  							return formatterBigNumNoLen(val);
				  						}
				  					}
				  				},
				  				{field:'barcode',title:'辅单位条形码',width:100,align:'center'},
				  				{field:'remark',title:'备注',width:150,align:'center'}
				   			]]
				    	});
						$dgMeteringUnitInfoList.addClass("create-datagrid");
					}
				}
				else if(index == 4){
					var $dgStorageInfoList = $("#goodsInfo-table-StorageInfo");
					if(!$dgStorageInfoList.hasClass("create-datagrid")){
						$dgStorageInfoList.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			url:storageUrl,
				  			border:false,
				  			frozenColumns:[[
				  				{field:'id',title:'编号',width:150,hidden:true},
				   				{field:'goodsid',title:'商品编码',width:150,hidden:true},
				   				{field:'storageid',title:'仓库名称',width:120,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return rowData.storageName;
				  					}
				  				},
				  				{field:'isdefault',title:'默认仓库',width:80,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						if(val == "1"){
				  							return "是";
				  						}
				  						else{
				  							return "否";
				  						}
				  					}
				  				},
				  				{field:'highestinventory',title:'最高库存',width:100,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						if(val != "" && val != null){
				  							return formatterBigNumNoLen(val);
				  						}
				  					}
				  				},
				  				{field:'lowestinventory',title:'最低库存',width:100,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						if(val != "" && val != null){
				  							return formatterBigNumNoLen(val);
				  						}
				  					}
				  				},
				  				{field:'safeinventory',title:'安全库存',width:100,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						if(val != "" && val != null){
				  							return formatterBigNumNoLen(val);
				  						}
				  					}
				  				},
				  				{field:'checktype',title:'盘点方式',width:95,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return getSysCodeName("checktype",val);
				  					}
				  				}
				  			]],
				   			columns:[[
				   				{title:'盘点周期',colspan:2},
				   				{field:'lastcheckdate',title:'上次盘点日期',width:110,rowspan:2,align:'center'},
				  				{field:'remark',title:'备注',width:150,rowspan:2,align:'center'}
				  				],[
				  					{field:'checkdate',title:'数量',width:60,align:'center'},
					  				{field:'checkunit',title:'单位',width:80,align:'center',
					  					formatter:function(val,rowData,rowIndex){
					  						switch(val){
					  							case "1":
					  								return "天";
					  							case "2":
					  								return "周";
					  							case "3":
					  								return "月";
					  							case "4":
					  								return "年";
					  						}
					  					}
					  				}
				  				]]
				    	});
						$dgStorageInfoList.addClass("create-datagrid");
					}
				}
				else if(index == 5){
					var $dgWaresClassInfoList = $("#goodsInfo-table-WaresClass");
					if(!$dgWaresClassInfoList.hasClass("create-datagrid")){
						$dgWaresClassInfoList.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			url:waresClassUrl,
				  			border:false,
				   			columns:[[
				   				{field:'id',title:'编号',width:150,hidden:true},
				   				{field:'goodsid',title:'商品编码',width:150,hidden:true},
				   				{field:'waresclass',title:'商品分类',width:90,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return rowData.waresclassName;
				  					}
				  				},
				  				{field:'isdefault',title:'默认分类',width:80,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						if(val == "1"){
				  							return "是";
				  						}
				  						else if(val == "0"){
				  							return "否";
				  						}
				  					}
				  				},
				  				{field:'remark',title:'备注',width:150,align:'center'}
				   			]]
				    	});
						$dgWaresClassInfoList.addClass("create-datagrid");
					}
				}
				else if(index == 6){
					var $dgStorageLocationInfoList = $("#goodsInfo-table-StorageLocation");
					if(!$dgStorageLocationInfoList.hasClass("create-datagrid")){
						$dgStorageLocationInfoList.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			url:SLUrl,
				  			border:false,
				   			columns:[[
				   				{field:'id',title:'编号',width:150,hidden:true},
				   				{field:'goodsid',title:'商品编码',width:150,hidden:true},
				   				{field:'storagelocationid',title:'库位名称',width:90,align:'center',
				  					formatter:function(val,rowData,rowIndex){
				  						return rowData.storagelocationName;
				  					}
				  				},
				  				{field:'isdefault',title:'默认库位',width:80,align:'center',
					  				formatter:function(val,rowData,rowIndex){
				  						switch(val){
				  							case "0":
				  								return "否";
				  							case "1":
				  								return "是";
				  						}
				  					}
				  				},
				  				{field:'boxnum',title:'库位容量',width:80,align:'right',
				  					formatter:function(val,rowData,rowIndex){
				  						if(val != "" && val != null){
				  							return formatterBigNumNoLen(val);
				  						}
				  					}
				  				},
				  				{field:'remark',title:'备注',width:150,align:'center'}
				   			]]
				    	});
						$dgStorageLocationInfoList.addClass("create-datagrid");
					}
				}
			});
    	});
    </script>
  </body>
</html>
