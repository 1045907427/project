<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>查看</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <div class="easyui-panel" title="" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="purchase/limitprice/addLimitPriceOrder.do" id="purchase-form-limitPriceOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-limitPriceOrderAddPage-addType" name="addType"/>
	  		<div data-options="region:'north',border:false" style="height:120px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:90px;text-align: left;">编号：</td>
						<td style="width:165px;"><input type="text" style="width:130px;" readonly="readonly" name="limitPriceOrder.id" value="${limitPriceOrder.id }" /></td>
						<td style="width:90px;text-align: left;">业务日期：</td>
						<td style="width:165px;"><input type="text" value='${limitPriceOrder.businessdate }' style="width:130px;" readonly="readonly"/></td>
						<td style="width:60px;text-align: left;">状态：</td>
				    	<td style="width:165px;">			    		
	    					<select disabled="disabled" class="len136">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == limitPriceOrder.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
						</td>
					</tr>
					<tr>
						<td style="text-align: left;">申请人：</td>
						<td><input type="text" id="purchase-limitPriceOrderAddPage-applyuser" style="width:130px;" name="limitPriceOrder.applyuserid" readonly="readonly" value="${limitPriceOrder.applyuserid }"/></td>
						<td style="text-align: left;">申请部门：</td>
						<td colspan="3"><input type="text" id="purchase-limitPriceOrderAddPage-applydept" style="width:130px;" readonly="readonly" value="<c:out value="${limitPriceOrder.applydeptname}"></c:out>"/><input id="purchase-limitPriceOrderAddPage-applydeptid" type="hidden" name="limitPriceOrder.applydeptid" value="${limitPriceOrder.applydeptid }" /></td>
					</tr>
					<tr>
						<td style="width:80px;text-align: left;">开始生效日期：</td>
						<td style="width:165px;"><input type="text" name="limitPriceOrder.effectstartdate" value="${limitPriceOrder.effectstartdate }" style="width:130px;" readonly="readonly"/></td>
						<td style="width:80px;text-align: left;">有效截止日期：</td>
						<td style="width:165px;"><input type="text" name="limitPriceOrder.effectenddate" value="${limitPriceOrder.effectenddate }" style="width:130px;" readonly="readonly"/></td>
						<td style="width:60px;text-align: left;">备注：</td>
						<td>
							<input type="text" style="width:130px;" name="limitPriceOrder.remark" value="<c:out value="${limitPriceOrder.remark }"></c:out>" readonly="readonly"/>
						</td>
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-limitPriceOrderAddPage-limitPriceOrdertable"></table>
				<input type="hidden" id="purchase-limitPriceOrderAddPage-limitPriceOrderDetails" name="limitPriceOrderDetails"/>
	  		</div>
	  	</form> 
	  </div>
  </div>
  <div id="purchase-Button-tableMenu" class="easyui-menu" style="width:120px;">
        <div id="purchase-tableMenu-itemAdd" iconCls="button-add">添加</div>
        <div id="purchase-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
        <div id="purchase-tableMenu-itemInsert" iconCls="button-add">插入一行</div>
        <div id="purchase-tableMenu-itemDelete" iconCls="button-delete">删除</div>
  </div>
  <div id="purchase-limitPriceOrderAddPage-dialog-DetailOper" class="easyui-dialog" closed="true"></div>
  <script type="text/javascript">
  	
  	$(document).ready(function(){
  		$("#purchase-buttons-limitPriceOrderPage").buttonWidget("setDataID",  {id:'${limitPriceOrder.id}',state:'${limitPriceOrder.status}',type:'view'});
  	  	var $limitPriceOrdertable=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable");
  	  	var tableColJson = $limitPriceOrdertable.createGridColumnLoad({
	  	  	name :'purchase_limitPriceorder_detail',
			frozenCol : [[
	 		]],
			commonCol : [[
						{field:'goodsid',title:'商品编码',width:80,isShow:true},
 						{field:'goodsname',title:'商品名称',width:220,isShow:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.name;
					       		}else{
					       			return "";
				       			}
					        }
				        },
 						{field:'model',title:'规格型号',width:80,isShow:true,hidden:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.model;
					       		}else{
					       			return "";
					       		}
					        }
				        },
 						{field:'brandname',title:'商品品牌',width:60,isShow:true,hidden:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.brandName;
					       		}else{
					       			return "";
					       		}
					        }
				        },
    					{field:'unitid', title:'单位',width:40,
    						formatter: function(value,row,index){
								return row.unitname;
							}
						},
    					{field:'auxunitid', title:'辅单位',width:40,
    						formatter: function(value,row,index){
    							return row.auxunitname;
    						}
    					},
		        		{field:'barcode',title:'条形码',width:100,isShow:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.barcode;
					       		}else{
					       			return "";
					       		}
					        }
						},
						{field:'priceasfound',title:'调整前采购价',width:80,align:'right',
							formatter: function(value,row,index){
				        		return formatterMoney(value);
				        	}
				        },
						{field:'priceasleft',title:'调整后采购价',width:80,align:'right',
							formatter: function(value,row,index){
				        		return formatterMoney(value);
				        	}
				        },
						{field:'remark',title:'备注',width:100},
						{field:'goodsfield01',title:'${gfieldMap.field01}',width:80,hidden:true,isShow:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field01;
						       		}else{
						       			return "";
						       		}
						        }
						},
						{field:'goodsfield02',title:'${gfieldMap.field02}',width:80,hidden:true,isShow:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field02;
						       		}else{
						       			return "";
						       		}
						        }
						},
						{field:'goodsfield03',title:'${gfieldMap.field03}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.field03;
					       		}else{
					       			return "";
					       		}
					        }
						},
						{field:'goodsfield04',title:'${gfieldMap.field04}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field04;
						       		}else{
						       			return "";
						       		}
						        }
						},
						{field:'goodsfield05',title:'${gfieldMap.field05}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field05;
						       		}else{
						       			return "";
						       		}
						        }
						},
						{field:'goodsfield06',title:'${gfieldMap.field06}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.field06;
					       		}else{
					       			return "";
					       		}
					        }
						},
						{field:'goodsfield07',title:'${gfieldMap.field07}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
							   		if(rowData.goodsInfo != null){
							   			return rowData.goodsInfo.field07;
							   		}else{
							   			return "";
							   		}
							    }
						},
						{field:'goodsfield08',title:'${gfieldMap.field08}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
									if(rowData.goodsInfo != null){
										return rowData.goodsInfo.barcode;
									}else{
										return "";
									}
								}
						},
						{field:'goodsfield09',title:'${gfieldMap.field09}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
									if(rowData.goodsInfo != null){
										return rowData.goodsInfo.field09;
									}else{
										return "";
									}
								}
						}
			]]
  	  	});
  	  	$limitPriceOrdertable.datagrid({
	  	  	fit:true,
	  	  	striped:true,
	 		method:'post',
	 		rownumbers:true,
	 		//idField:'id',
	 		singleSelect:true,
	 		showFooter:true,
  	 		data: JSON.parse('${goodsDataList}'),
  	 		authority:tableColJson,
  	 		frozenColumns: tableColJson.frozen,
			columns:tableColJson.common,
  	 		onLoadSuccess:function(){
	  	  		var $potable=$("#purchase-limitPriceOrderAddPage-limitPriceOrdertable");
	  	  		var dataRows=$potable.datagrid('getRows');
	  	  		var rowlen=dataRows.length;
		  	  	if(rowlen<15){
			  	  	for(var i=rowlen;i<15;i++){
						$limitPriceOrdertable.datagrid('appendRow', {});
			  	  	}
				}
  	 		}
  	  	}).datagrid("columnMoving");
  	  	
		$("#purchase-limitPriceOrderAddPage-applyuser").widget({ 
			name:'t_purchase_limitPriceorder',
			col:'applyuserid',
			width:130,
			readonly:true,
			singleSelect:true,
			onlyLeafCheck:true,
			initValue:'${limitPriceOrder.applyuserid}'
		});	
		

  		$("#purchase-limitPriceOrderAddPage-applyuser").widget('setValue','${limitPriceOrder.applyuserid}');
  	});
  </script>
  </body>
</html>
