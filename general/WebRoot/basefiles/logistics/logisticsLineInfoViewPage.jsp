<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>线路档案详情页面</title>
  </head>
  
  <body>
  	<div id="line-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
		<div id="line-layout-detail-north" data-options="region:'north',border:false" style="height:82px;">
			<form id="lineInfo-form-head" action="" method="post" style="padding: 5px">
                <input type="hidden" name="lineInfo.startpoint" value="<c:out value="${lineInfo.startpoint}"></c:out>"/>
                <input type="hidden" name="lineInfo.defaultpoint" value="<c:out value="${lineInfo.defaultpoint}"></c:out>"/>
                <table cellspacing="5px" cellpadding="5px" border="0">
					 <tbody>
					 	<tr>
							<td width="80px"><div align="right">编码:</div></td>
							<td style="width: 5px"></td>
						    <td><input id="lineInfo-id-baseInfo" type="text" style="width: 120px ;" name="lineInfo.id" value="<c:out value="${lineInfo.id}"></c:out>" readonly="readonly"/>
						    	<input id="infoPriceInfo-hidden-delIds" type="hidden">
						    	<input id="infoPriceInfo-hidden-addClickNum" type="hidden" value="0">
						    	<input id="infoMeteringUnitInfo-hidden-delIds" type="hidden">
						    	<input id="infoMeteringUnitInfo-hidden-addClickNum" type="hidden" value="0">
						    	<input id="infoStorageInfo-hidden-delIds" type="hidden">
						    	<input id="infoStorageInfo-hidden-addClickNum" type="hidden" value="0">
						    	<input id="infoWaresClass-hidden-delIds" type="hidden">
						    	<input id="infoWaresClass-hidden-addClickNum" type="hidden" value="0">
						    	<input id="lineInfo-hidden-addType" type="hidden" name="type">
						    	<input id="lineInfo-hidden-operateType" type="hidden">
						    	<input id="lineInfo-dblclick-hdstorageid" type="hidden">
						    	<input id="lineInfo-dblclick-hdwaresClassid" type="hidden"/>
						    	<input id="lineInfo-dblclick-hdstoragelocation" type="hidden"/>
						    	<input type="hidden" id="lineInfo-oldid" value="${lineInfo.id }"/>
						    </td>
							<td width="80px"><div align="right">名称:</div></td>
							<td style="width: 5px"></td>
						    <td><input type="text" style="width: 200px ;" name="lineInfo.name" value="<c:out value="${lineInfo.name}"></c:out>" readonly="readonly"/></td>
							<td width="80px"><div align="right">状态:</div></td>
							<td style="width: 5px"></td>
						    <td><input id="lineInfo-widget-state" value="${lineInfo.state}" disabled="disabled" style="width: 200px" /></td>
						</tr>
					 </tbody>
				</table>
			</form>
			<ul class="tags">
				<security:authorize url="/basefiles/lineInfoBaseInfoTab.do">
					<li class="selectTag"><a href="javascript:void(0)">基本信息</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/lineInfoCarInfoTab.do">
					<li><a href="javascript:void(0)">所属车辆</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/lineInfoCustomerInfoTab.do">
					<li><a href="javascript:void(0)">线路客户</a></li>
				</security:authorize>
				<security:authorize url="/basefiles/lineInfoDefineInfoTab.do">
					<li><a href="javascript:void(0)">自定义信息</a></li>
				</security:authorize>
			</ul>
		</div>
		<div id="line-layout-detail-center" data-options="region:'center',border:false">
			<div class="tagsDiv">
				<div class="tagsDiv_item" style="display:block;">
					<form action="" method="post" id="wares-form-lineInfoBaseInfo" >
						<table cellspacing="5px" cellpadding="5px" border="0">
						 	<tbody>
						 		<tr>
									<td width="120px"><div align="right">所属地区:</div></td>
									<td style="width: 5px"></td>
									<td><input type="text" id="line-customer-sortarea" name="lineInfo.salesarea" value="<c:out value="${lineInfo.salesarea}"></c:out>" style="width: 120px" readonly="readonly"/></td>
									<td width="120px"><div align="right">区域客户家数:</div></td>
									<td style="width: 5px"></td>
								    <td><input type="text" id="lineInfo-widget-infotype" name="lineInfo.totalcustomers" value="${lineInfo.totalcustomers}" readonly="readonly" style="width: 120px"/></td>
									<td width="120px">
											<div align="right">
												默认车辆:
											</div>
										</td>
										<td style="width: 5px"></td>
										<td>
											<select type="text" id="lineInfo-select-car" readonly="readonly"
												name="lineInfo.carid" value="<c:out value="${lineInfo.carname}"></c:out>"
												style="width: 120px">
												<option value="<c:out value="${lineInfo.carid}"></c:out>">
													<c:out value="${lineInfo.carname}"></c:out>
												</option>
											</select>
										</td>
								</tr>
						  		<tr>
									<td width="120px"><div align="right">线路暂估路程:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="lineInfo-widget-brand" type="text" style="width: 120px" name="lineInfo.distance" value="<c:out value="${lineInfo.distance}"></c:out>" readonly="readonly"/></td>
									<td width="120px"><div align="right">路程复杂度:</div></td>
									<td style="width: 5px"></td>
								    <td><input id="lineInfo-widget-linetype" type="text" style="width: 120px" name="lineInfo.linetype" value="${lineInfo.linetype}" readonly="readonly"/></td>
								</tr>
							</tbody>
						</table>
					</form>
					<div style="margin-top:25px;margin-left:0px;">
						<div class="easyui-panel" title="控制信息">
							<table cellspacing="5px" cellpadding="5px" border="0">
								<tbody>
									<tr>
										<td width="120px"><div align="right">家数指标</div></td>
										<td style="width: 5px"></td>
									    <td><input type="text" style="width: 120px" name="lineInfo.basecustomers" value="${lineInfo.basecustomers}" readonly="readonly"/></td>
										<td width="120px"><div align="right">基本补贴:</div></td>
										<td style="width: 5px"></td>
									    <td><input type="text" style="width: 120px" name="lineInfo.baseallowance" data-options="min:0,max:999,precision:2" class="easyui-numberbox" value="${lineInfo.baseallowance}" readonly="readonly"/></td>
										<td width="120px"><div align="right">单家补贴:</div></td>
										<td style="width: 5px"></td>
									    <td><input type="text" style="width: 120px" name="lineInfo.singleallowance" data-options="min:0,max:999,precision:2" class="easyui-numberbox" value="${lineInfo.singleallowance}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td width="120px"><div align="right">出车补贴:</div></td>
										<td style="width: 5px"></td>
									    <td><input id="lineInfo-widget-bstype" name="lineInfo.carsubsidy" data-options="min:0,max:999,precision:2" class="easyui-numberbox" value="${lineInfo.carsubsidy}" readonly="readonly" style="width: 120px "/></td>
										<td width="120px"><div align="right">出车津贴:</div></td>
										<td style="width: 5px"></td>
									    <td><input type="text" style="width: 120px" name="lineInfo.carallowance" data-options="min:0,max:999,precision:2" class="easyui-numberbox" value="${lineInfo.carallowance}" readonly="readonly"/>
									    </td>
									</tr>
                                    <tr>
                                        <td><div align="right">备注:</div></td>
                                        <td style="width: 5px"></td>
                                        <td colspan="7"><textarea name="lineInfo.remark" cols="" readonly="readonly" rows="3" style="width:700px"><c:out value="${lineInfo.remark}"></c:out></textarea></td>
                                    </tr>
								</tbody>
							</table>
						</div>
					</div>
					<div style="margin-top:25px;margin-left:0px;">
						<div class="easyui-panel" title="回写信息">
							<table cellspacing="5px" cellpadding="5px" border="0">
								<tbody>
									<tr>
										<td width="120px"><div align="right">出车次数总计:</div></td>
										<td ></td>
									    <td width="120px"><input type="text" style="width: 120px" class="easyui-numberbox" readonly="readonly" value="${lineInfo.totalcars}"/></td>
										<td width="120px"><div align="right">出车家数总计:</div></td>
										<td style="width: 5px"></td>
									    <td ><input type="text" style="width: 120px" class="easyui-numberbox" readonly="readonly" value="${lineInfo.totalcarcustomers}"/></td>
							 		</tr>
							  		<tr>
										<td><div align="right">线路收款总计金额:</div></td>
										<td style="width: 5px"></td>
									    <td><input type="text" style="width: 120px" data-options="min:0,max:999,precision:2" class="easyui-numberbox" readonly="readonly" value="${lineInfo.totalamount}"/></td>
										<td><div align="right">最新收款金额:</div></td>
										<td style="width: 5px"></td>
									    <td><input type="text" style="width: 120px" data-options="min:0,max:999,precision:2" class="easyui-numberbox" readonly="readonly" value="${lineInfo.newamount}"/></td>
							  		</tr>
						  		</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="tagsDiv_item">
					<table id="lineInfo-table-car"></table>
				</div>
				<div class="tagsDiv_item">
					<table id="lineInfo-table-customer"></table>
				</div>
				<div class="tagsDiv_item">
					<form action="" method="post" id="wares-form-lineInfoDefineInfo" >
						<table border="0" cellpadding="2px" cellspacing="2px">
							<tr>
								<td><label>
								<c:choose>
									<c:when test="${fieldmap.field01 != null}"><c:out value="${fieldmap.field01 }"></c:out></c:when>
									<c:otherwise>自定义信息01</c:otherwise>
								</c:choose>									
								</label>：</td>
								<td><input name="lineInfo.field01" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field01==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field01}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field02 != null}"><c:out value="${fieldmap.field02 }"></c:out></c:when>
									<c:otherwise>自定义信息02</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="lineInfo.field02" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field02==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field02}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field03 != null}"><c:out value="${fieldmap.field03 }"></c:out></c:when>
									<c:otherwise>自定义信息03</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="lineInfo.field03" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field03==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field03}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field04 != null}"><c:out value="${fieldmap.field04 }"></c:out></c:when>
									<c:otherwise>自定义信息04</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="lineInfo.field04" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field04==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field04}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field05 != null}"><c:out value="${fieldmap.field05 }"></c:out></c:when>
									<c:otherwise>自定义信息05</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="lineInfo.field05" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field05==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field05}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field06 != null}"><c:out value="${fieldmap.field06 }"></c:out></c:when>
									<c:otherwise>自定义信息06</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="lineInfo.field06" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field06==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field06}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field07 != null}"><c:out value="${fieldmap.field07 }"></c:out></c:when>
									<c:otherwise>自定义信息07</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="lineInfo.field07" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field07==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field07}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field08 != null}"><c:out value="${fieldmap.field08 }"></c:out></c:when>
									<c:otherwise>自定义信息08</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="lineInfo.field08" type="text" style="width: 120px "
									<c:choose>
										<c:when test="${showMap.field08==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field08}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
								<td><label><c:choose>
									<c:when test="${fieldmap.field09 != null}"><c:out value="${fieldmap.field09 }"></c:out></c:when>
									<c:otherwise>自定义信息09</c:otherwise>
								</c:choose></label>：</td>
								<td><input name="lineInfo.field09" type="text" style="width: 120px " 
									<c:choose>
										<c:when test="${showMap.field09==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field09}"></c:out>" </c:otherwise>
									</c:choose> />
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field10 != null}"><c:out value="${fieldmap.field10 }"></c:out></c:when>
									<c:otherwise>自定义信息10</c:otherwise>
								</c:choose></label>：</td>
								<td colspan="5"><input type="text" name="lineInfo.field10" style="width: 625px"
									<c:choose>
										<c:when test="${showMap.field10==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field10}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field11 != null}"><c:out value="${fieldmap.field11 }"></c:out></c:when>
									<c:otherwise>自定义信息11</c:otherwise>
								</c:choose></label>：</td>
								<td colspan="5"><input type="text" name="lineInfo.field11" style="width: 625px"
									<c:choose>
										<c:when test="${showMap.field11==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field11}"></c:out>" </c:otherwise>
									</c:choose>/>
								</td>
							</tr>
							<tr>
								<td><label><c:choose>
									<c:when test="${fieldmap.field12 != null}"><c:out value="${fieldmap.field12 }"></c:out></c:when>
									<c:otherwise>自定义信息12</c:otherwise>
								</c:choose></label>：</td>
								<td colspan="5"><input type="text" name="lineInfo.field12" style="width: 625px"
									<c:choose>
										<c:when test="${showMap.field12==null}">readonly="readonly" value=""</c:when>
										<c:otherwise>readonly="readonly" value="<c:out value="${lineInfo.field12}"></c:out>" </c:otherwise>
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

        var $customerDatagrid = null;

    	$("#line-customer-sortarea").widget({ //区域
   			referwid:'RT_T_BASE_SALES_AREA',
			//col:'salesarea',
   			singleSelect:true,
   			width:120,
   			onlyLeafCheck:false,
   			view: true
   		});

    	loadDropdown();
    	
    	var id = $("#lineInfo-id-baseInfo").val();
    	var carUrl = "basefiles/showCarInfoList.do?lineid="+id;
		var customerUrl = "basefiles/showCustomerInfoList.do?lineid="+id;
    	
    	$(function(){
    		$("#lineInfo-div-button").buttonWidget("setDataID",{id:$("#lineInfo-oldid").val(),state:"${lineInfo.state}",type:"view"});
    		
    		var $dgPriceInfoList = null,$dgInfoCustomer = null,$dgStorageInfoList = null,$dgWaresClassInfoList = null,$dgStorageLocationInfoList = null;
    		
    		$(".tags").find("li").click(function(){
    			var height = $(window).height()-142;
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 1){
					var $dgPriceInfoList = $("#lineInfo-table-car");
					if(!$dgPriceInfoList.hasClass("create-datagrid")){
						$dgPriceInfoList.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:true,
				  			height:height,
				  			url:carUrl,
				  			pageSize:500,
					  		border:false,
					  		pagination:true,
				   			columns:[[
				   				//{field:'id',title:'编号',width:150,hidden:true},
				   				//{field:'lineid',title:'线路编码',width:150,hidden:true},
				   				{field:'id',title:'车辆编码',width:80},
				  				{field:'name',title:'车辆名称',width:100,align:'left'},
				  				{field:'remark',title:'备注',width:200,align:'left'}
				   			]]
				    	});
						$dgPriceInfoList.addClass("create-datagrid");
					}
				}
				else if(index == 2){
                    var $dgInfoCustomer = $("#lineInfo-table-customer");
					if(!$dgInfoCustomer.hasClass("create-datagrid")){
						$dgInfoCustomer.datagrid({
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				  			singleSelect:false,
				  			height:height,
				  			url:customerUrl,
				  			pageSize:500,
				  			border:false,
				  			pagination:true,
				  			//fit:true,
				   			columns:[[
				   				{field:'customerid',title:'客户编码',width:80},
				  				{field:'customername',title:'客户名称',width:220,align:'left'},
                                {field:'carnum',title:'装车次数',width:60},
				  				{field:'seq',title:'排序',width:60,align:'left',isShow:true}
				   			]]
                            <security:authorize url="/basefiles/showLineInfoMapPage.do">
                            ,toolbar : [ {
                                text : "查看地图",
                                iconCls : "button-view",
                                handler : function() {
                                    $('#line-dialog-customer').dialog({
                                        title: '对应客户查看',
                                        width: 600,
                                        height: 400,
                                        closed: false,
                                        cache: false,
                                        href: 'basefiles/showCustomerToLineViewPage.do',
                                        modal: true,
                                        maximized: true
                                    });
                                }
                            }]
                            </security:authorize>
				    	});
						$dgInfoCustomer.addClass("create-datagrid");

                        $customerDatagrid = $dgInfoCustomer;
					}
				}
			});
    	});
    </script>
  </body>
</html>
