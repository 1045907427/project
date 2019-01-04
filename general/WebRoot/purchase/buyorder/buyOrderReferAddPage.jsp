<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <div class="easyui-panel" title="采购订单新增" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="purchase/buyorder/addBuyOrder.do" id="purchase-form-buyOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-buyOrderAddPage-addType" name="addType"/>
	  		<input type="hidden" id="purchase-buyOrderAddPage-referpage" value="0"/>
	  		<input type="hidden" id="purchase-buyOrderAddPage-referbillno" name="buyOrder.billno" value="${buyOrder.billno }" />
	  		<div data-options="region:'north',border:false" style="height:150px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" style="width:130px;" readonly="readonly" /></td>
						<td style="width:120px;">业务日期：</td>
						<td style="width:165px;"><input type="text" name="buyOrder.businessdate" value='${buyOrder.businessdate }' style="width:130px;" readonly="readonly" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:60px;">状态：</td>
				    	<td style="width:165px;">				    					    		
	    					<select disabled="disabled" style="width:130px;" ><option>新增</option></select>
	    					<input type="hidden" name="buyOrder.status" value="2" />
				    	</td>
					</tr>
					<tr>
						<td style="">供应商：</td>
						<td><input type="text" id="purchase-buyOrderAddPage-supplier" style="width:130px;" name="buyOrder.supplierid" readonly="readonly"/></td>
						<td style="">对方经手人：</td>
				    	<td><select id="purchase-buyOrderAddPage-handler" style="width:130px;" name="buyOrder.handlerid"></select></td>			    	
						<td style="">结算方式：</td>
						<td>
							<select id="purchase-buyOrderAddPage-settletype" style="width:130px;" name="buyOrder.settletype" >
								<option value=""></option>
	    						<c:forEach items="${settletype}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == buyOrder.settletype}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    				</td>
					</tr>
					<tr>
						<td style="">采购部门：</td>
				    	<td>
				    		<select id="purchase-buyOrderAddPage-buydept" name="buyOrder.buydeptid" style="width:130px;" >
					    		<option value=""></option>
					    		<c:forEach items="${buyDept}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == buyOrder.buydeptid}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
				    		</select>
						</td>
						<td style="">采购员：</td>
						<td>
							<select id="purchase-buyOrderAddPage-buyuser" name="buyOrder.buyuserid" style="width:130px;" ></select>
						</td>
						<td style="">支付方式：</td>
						<td>
							<select id="purchase-buyOrderAddPage-paytype" style="width:130px;" name="buyOrder.paytype" >
								<option value=""></option>
	    						<c:forEach items="${paytype}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == buyOrder.paytype}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
							</select>
						</td>					
					</tr>
					<tr>
						<td style="">入库仓库：</td>
				    	<td><input type="text" id="purchase-buyOrderAddPage-storage" name="buyOrder.storageid" style="width:130px;" /></td>
						<td>来源类型：</td>
						<td>
	    					<select style="width:130px;" disabled="disabled">
	    						<option value="0">无</option>
	    						<option value="1" selected="selected">采购计划单</option>
	    					</select>
	    					<input type="hidden" name="buyOrder.source" value="1"/>
		    			</td>
						<td style="width:60px;">备注：</td>
						<td>
							<input type="text" style="width:130px;" name="buyOrder.remark" value="${buyOrder.remark}"/>
						</td>		
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-buyOrderAddPage-buyOrdertable"></table>
				<input type="hidden" id="purchase-buyOrderAddPage-buyOrderDetails" name="buyOrderDetails"/>
	  		</div>
	  		<div data-options="region:'south',border:false" style="width:80px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:60px;text-align: left;">审核人：</td>
						<td style="width:165px;"><input type="text" style="width:130px;"  readonly="readonly" /></td>
						<td style="width:60px;text-align: left;">修改人：</td>
						<td style="width:165px;">						
			    			<input class="easyui-validatebox" readonly="readonly"  readonly="readonly" />
						</td>
						<td style="width:60px;text-align: left;">制单人：</td>
				    	<td style="width:165px;">						
			    			<input class="easyui-validatebox" readonly="readonly" value="${user.name }" />
			    			<input type="hidden" value="${user.userid }" name="buyOrder.adduserid" />
			    			<input type="hidden" value="${user.departmentid }" name="buyOrder.adddeptid" />
			    		</td>
					</tr>
					<tr>
						<td style="text-align: left;">审核时间：</td>
						<td><input type="text" style="width:130px;" readonly="readonly" /></td>
						<td style="text-align: left;">修改时间：</td>
						<td><input type="text" style="width:130px;" readonly="readonly"/></td>
						<td style="text-align: left;">制单时间：</td>
				    	<td><input type="text" value='${today }' style="width:130px;" readonly="readonly"/></td>
					</tr>
				</table>
	  		</div>
	  	</form> 
	  </div>
  </div>
  <div id="purchase-buyOrderAddPage-dialog-DetailOper" class="easyui-dialog" closed="true"></div>
  <script type="text/javascript">
  	var editRowIndex = undefined;
  	function getAddRowIndex(){
  		var $potable=$("#purchase-buyOrderAddPage-buyOrdertable");
  		var dataRows=$potable.datagrid('getRows');
  		
  		var rindex=0;
  		for(rindex=0;rindex<dataRows.length;rindex++){
  	  		if(dataRows[rindex].goodsid==null || dataRows[rindex].goodsid==""){
  	  	  		break;
  	  		}
  		}
  		if(rindex==dataRows.length){
  	  		$potable.datagrid('appendRow',{});
  		}
  		return rindex;
  	}
	
  	$(document).ready(function(){
  		$("#purchase-buttons-buyOrderPage").buttonWidget("setDataID",  {id:'${buyOrder.id}',state:'2',type:'edit'});
  	  	var $buyOrdertable=$("#purchase-buyOrderAddPage-buyOrdertable");
  	  	var tableColJson = $buyOrdertable.createGridColumnLoad({
	  	  	name :'purchase_buyorder_detail',
			frozenCol : [[
	 		]],
			commonCol : [[
							{field:'id',title:'编码',width:80,hidden:true,isShow:true},
							{field:'goodsid',title:'商品编码',width:80,isShow:true},
	 						{field:'name',title:'商品名称',width:220,isShow:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.name;
						       		}else{
						       			return value;
					       			}
						        }
					        },
			        		{field:'barcode',title:'条形码',width:90,isShow:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.barcode;
						       		}else{
						       			return value;
						       		}
						        }
							},
	 						{field:'model',title:'规格型号',width:80,isShow:true,hidden:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.model;
						       		}else{
						       			return value;
						       		}
						        }
					        },
	 						{field:'brandName',title:'商品品牌',width:100,isShow:true,hidden:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.brandName;
						       		}else{
						       			return value;
						       		}
						        }
					        },
	    					{field:'unitid', title:'单位',width:70,
	    						formatter: function(value,row,index){
									return row.unitname;
								}
							},
							{field:'taxprice',title:'单价',width:80,align:'right',
								formatter: function(value,row,index){
					        		return formatterMoney(value);
					        	}
					        },
	    					{field:'unitnum', title:'数量',width:80,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterBigNumNoLen(value);
						        }
					    	},
							{field:'taxamount',title:'金额',width:60,align:'right',
								formatter: function(value,row,index){
				        			return formatterMoney(value);
					        	}
					        },
							{field:'notaxprice',title:'未税单价',width:80,align:'right',
								formatter: function(value,row,index){
				        			return formatterMoney(value);
					        	}
					        },
							{field:'notaxamount',title:'未税金额',width:60,align:'right',
								formatter: function(value,row,index){
				        			return formatterMoney(value);
					        	}
					        },
							{field:'taxtypename',title:'税种',width:80},
							{field:'tax',title:'税额',width:80,align:'right',	
								formatter: function(value,row,index){
				        			return formatterMoney(value);
			        			}
			        		},
	    					{field:'auxunitid', title:'辅单位',width:60,
	    						formatter: function(value,row,index){
	    							return row.auxunitname;
	    						}
	    					},
							{field:'auxnumdetail',title:'辅数量',width:80,align:'right'},
							{field:'arrivedate',title:'要求到货日期'},
							{field:'remark',title:'备注',width:200},
							{field:'goodsfield01',title:'${gfieldMap.field01}',width:80,hidden:true,isShow:true,
		    						formatter:function(value,rowData,rowIndex){
							       		if(rowData.goodsInfo != null){
							       			return rowData.goodsInfo.field01;
							       		}else{
							       			return value;
							       		}
							        }
							},
							{field:'goodsfield02',title:'${gfieldMap.field02}',width:80,hidden:true,isShow:true,
		    						formatter:function(value,rowData,rowIndex){
							       		if(rowData.goodsInfo != null){
							       			return rowData.goodsInfo.field02;
							       		}else{
							       			return value;
							       		}
							        }
							},
							{field:'goodsfield03',title:'${gfieldMap.field03}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field03;
						       		}else{
						       			return value;
						       		}
						        }
							},
							{field:'goodsfield04',title:'${gfieldMap.field04}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
							       		if(rowData.goodsInfo != null){
							       			return rowData.goodsInfo.field04;
							       		}else{
							       			return value;
							       		}
							        }
							},
							{field:'goodsfield05',title:'${gfieldMap.field05}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
							       		if(rowData.goodsInfo != null){
							       			return rowData.goodsInfo.field05;
							       		}else{
							       			return value;
							       		}
							        }
							},
							{field:'goodsfield06',title:'${gfieldMap.field06}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field06;
						       		}else{
						       			return value;
						       		}
						        }
							},
							{field:'goodsfield07',title:'${gfieldMap.field07}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
								   		if(rowData.goodsInfo != null){
								   			return rowData.goodsInfo.field07;
								   		}else{
								   			return value;
								   		}
								    }
							},
							{field:'goodsfield08',title:'${gfieldMap.field08}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
										if(rowData.goodsInfo != null){
											return rowData.goodsInfo.barcode;
										}else{
											return value;
										}
									}
							},
							{field:'goodsfield09',title:'${gfieldMap.field09}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
										if(rowData.goodsInfo != null){
											return rowData.goodsInfo.field09;
										}else{
											return value;
										}
									}
							}
			]]
  	  	});
  	  	$buyOrdertable.datagrid({
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
		  		var dataRows=$buyOrdertable.datagrid('getRows');
		  		var rowlen=dataRows.length;
		  	  	if(rowlen<15){
			  	  	for(var i=rowlen;i<15;i++){
			  	  		$buyOrdertable.datagrid('appendRow', {});
			  	  	}
				}
		 			$buyOrdertable.datagrid('reloadFooter',[
					{goodsid: '合计', amount: '0',taxprice:'0',notaxprice:'0',notaxamount:'0',taxamount:'0',tax:'0'}
				]);
	 			footerReCalc();
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行
        		editRowIndex=rowIndex;
  	 			if(rowData.goodsid && rowData.goodsid!=""){
  	 				orderDetailEditDialog(rowData);
  	 			}
        	}
  	  	}).datagrid("columnMoving");
  	  	
  	  	$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: "${buyOrder.buydeptid}"}, function(json){
			if(json.length>0){
				$("#purchase-buyOrderAddPage-buyuser").html("");
				$("#purchase-buyOrderAddPage-buyuser").append("<option value=''></option>");
				for(var i=0;i<json.length;i++){
					$("#purchase-buyOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
				}
  			$("#purchase-buyOrderAddPage-buyuser").val("${buyOrder.buyuserid}");
			}	
		});
		$.getJSON('basefiles/getContacterBy.do', {type:"2", id:"${buyOrder.supplierid}"}, function(json){
			if(json.length>0){
				$("#purchase-buyOrderAddPage-handler").html("");
				$("#purchase-buyOrderAddPage-handler").append("<option value=''></option>");
				for(var i=0;i<json.length;i++){
					$("#purchase-buyOrderAddPage-handler").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
				}
				$("#purchase-buyOrderAddPage-handler").val("${buyOrder.handlerid}");
			}
		});
		
		$("#purchase-buyOrderAddPage-supplier").widget({ 
			name:'t_purchase_buyorder',
			col:'supplierid',
			width:130,
			readonly:true,
			singleSelect:true,
			onlyLeafCheck:true,
			initValue:'${buyOrder.supplierid}'
		});	

		$("#purchase-buyOrderAddPage-buydept").change(function(){
			var v = $(this).val();
			if(v!=null && v !=""){
				$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#purchase-buyOrderAddPage-buyuser").html("");
	    				$("#purchase-buyOrderAddPage-buyuser").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#purchase-buyOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
			}else{
				$("#purchase-buyOrderAddPage-buyuser").html("");
			}
		});
		
		$("#purchase-buyOrderAddPage-storage").widget({ 
			name:'t_purchase_buyorder',
			col:'storageid',
			width:130,
			singleSelect:true,
			onlyLeafCheck:true
		});
		
  	});
  </script>
  </body>
</html>