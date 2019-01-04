<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购进货单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>  
  <div class="easyui-panel" title="采购进货单新增" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="purchase/arrivalorder/addArrivalOrder.do" id="purchase-form-arrivalOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-addType" name="addType"/>
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-referpage" value="1"/>
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-referbillno" name="arrivalOrder.billno" value="${ arrivalOrder.billno }"/>
	  		<div data-options="region:'north',border:false" style="height:150px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" style="width:130px;" value="${arrivalOrder.id }" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td style="width:165px;"><input type="text" name="arrivalOrder.businessdate" value='${today }' style="width:130px;" readonly="readonly" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:165px;">			    		
	    					<select disabled="disabled" style="width:130px;" ><option>新增</option></select>
	    					<input type="hidden" name="arrivalOrder.status" value="2" />
						</td>
					</tr>
					<tr>
						<td style="">供应商：</td>
						<td colspan="3"><input type="text" id="purchase-arrivalOrderAddPage-supplier" style="width:400px;" name="arrivalOrder.supplierid" value="${arrivalOrder.supplierid }" text="${arrivalOrder.suppliername}" readonly="readonly"/></td>
						<td>对方经手人：</td>
				    	<td>
				    		<select id="purchase-arrivalOrderAddPage-handler" style="width:130px;" name="arrivalOrder.handlerid"  ></select>
				    	</td>
					</tr>
					<tr>
						<td style="">采购部门：</td>
				    	<td>
				    		<select id="purchase-arrivalOrderAddPage-buydept" name="arrivalOrder.buydeptid" style="width:130px;" >
					    		<option value=""></option>
					    		<c:forEach items="${buyDept}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == arrivalOrder.buydeptid}">
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
							<select id="purchase-arrivalOrderAddPage-buyuser" name="arrivalOrder.buyuserid" style="width:130px;" ></select>
						</td>	
						<td>来源类型：</td>
						<td>
	    					<select style="width:130px;" disabled="disabled">
	    						<option value="1">入库单</option>
	    					</select>
		    			</td>				
					</tr>
					<tr>
						<td style="">入库仓库：</td>
				    	<td><input type="text" id="purchase-arrivalOrderAddPage-storage" name="arrivalOrder.storageid" style="width:130px;" /></td>						
						<td>备注：</td>
						<td colspan="3">
							<input type="text" style="width:395px;" name="arrivalOrder.remark" value="${arrivalOrder.remark}"/>
						</td>		
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-arrivalOrderAddPage-arrivalOrdertable"></table>
				<input type="hidden" id="purchase-arrivalOrderAddPage-arrivalOrderDetails" name="arrivalOrderDetails"/>
	  		</div>
	  		<input type="hidden" name="arrivalOrder.id" value="${arrivalOrder.id }" />	
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-paytype" style="width:130px;" name="arrivalOrder.paytype" />
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-settletype" style="width:130px;" name="arrivalOrder.settletype" />
	  	</form> 
	  </div>
  </div>
  <div id="purchase-Button-tableMenu" class="easyui-menu" style="width:120px;">
        <div id="purchase-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
  </div>
  <div id="purchase-arrivalOrderAddPage-dialog-DetailOper"></div>
  <script type="text/javascript">
  var editRowIndex = undefined;
	function getAddRowIndex(){
		var $potable=$("#purchase-arrivalOrderAddPage-arrivalOrdertable");
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
		$("#purchase-buttons-arrivalOrderPage").buttonWidget("setDataID",  {id:'${arrivalOrder.id}',state:'2',type:'edit'});
  		$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-print");
  		$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-preview");
	  	var $arrivalOrdertable=$("#purchase-arrivalOrderAddPage-arrivalOrdertable");
	  	var tableColJson = $arrivalOrdertable.createGridColumnLoad({
	  	  	name :'purchase_arrivalorder_detail',
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
					        		return formatterMoney(value);
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
	    					{field:'auxunitid', title:'辅单位',width:60,hidden:true,
	    						formatter: function(value,row,index){
	    							return row.auxunitname;
	    						}
	    					},
							{field:'auxnumdetail',title:'辅数量',width:80,align:'right',hidden:true},
							{field:'batchno',title:'批次号'},
							{field:'produceddate',title:'生产日期'},
							{field:'deadline',title:'有效截止日期'},
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
	  	$arrivalOrdertable.datagrid({
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
		  		var dataRows=$arrivalOrdertable.datagrid('getRows');
		  		var rowlen=dataRows.length;
		  	  	if(rowlen<15){
			  	  	for(var i=rowlen;i<15;i++){
			  	  		$arrivalOrdertable.datagrid('appendRow', {});
			  	  	}
				}
	 			$arrivalOrdertable.datagrid('reloadFooter',[
					{goodsid: '合计', amount: '0',taxprice:'0',notaxprice:'0',notaxamount:'0',taxamount:'0',tax:'0'}
				]);
	 			footerReCalc();
	 		},
      	onDblClickRow: function(rowIndex, rowData){ //选中行
	 			editRowIndex=rowIndex;
	 			if(rowData.goodsid && rowData.goodsid!=""){
	 				<c:choose>
	 					<c:when test="${arrivalOrder.source !='1'}">
	  	 					orderDetailEditDialog(rowData);
	  	 				</c:when>
	 					<c:otherwise>
	  	 					orderDetailViewDialog(rowData);
	 					</c:otherwise>
	 				</c:choose>
	 			}else{
	 				<c:if test="${arrivalOrder.source !='1'}">
	 					orderDetailAddDialog();
	 				</c:if>
	 			}
      	},
	 		onRowContextMenu:function(e, rowIndex, rowData){
        		<c:if test="${arrivalOrder.source !='1'}">
	 			e.preventDefault();
	 			var $contextMenu=$('#purchase-Button-tableMenu');
	 			$contextMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				});
	 			$arrivalOrdertable.datagrid('selectRow', rowIndex);
				editRowIndex=rowIndex;
              $contextMenu.menu('enableItem', '#purchase-tableMenu-itemDelete');
              </c:if>
	 		}
	  	}).datagrid("columnMoving");

	  	<c:if test="${arrivalOrder.source !='1'}">
		//添加按钮事件
		$("#purchase-tableMenu-itemAdd").live("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			orderDetailAddDialog();			
		});
		</c:if>
	  	//编辑按钮事件
		$("#purchase-tableMenu-itemEdit").live("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var data=$("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('getSelected');
			orderDetailEditDialog(data);			
		});
		<c:if test="${arrivalOrder.source !='1'}">
		$("#purchase-tableMenu-itemDelete").live("click",function(){
			if($("#purchase-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var dataRow=$arrivalOrdertable.datagrid('getSelected');
			if(dataRow!=null){
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
	        			if(dataRow!=null){
							var rowIndex =$arrivalOrdertable.datagrid('getRowIndex',dataRow);
							$arrivalOrdertable.datagrid('updateRow',{
								index:rowIndex,
								row:{}
							});
							$arrivalOrdertable.datagrid('deleteRow', rowIndex);
							var rowlen=$arrivalOrdertable.datagrid('getRows').length; 
							if(rowlen<15){
								$arrivalOrdertable.datagrid('appendRow', {});
							}
							editRowIndex=undefined;
							
							$arrivalOrdertable.datagrid('clearSelections');
							footerReCalc();
	        			}
					}
				});	
			}		
		});
		</c:if>

		$.getJSON('basefiles/getContacterBy.do', {type:"2", id:"${arrivalOrder.supplierid}"}, function(json){
			if(json.length>0){
				$("#purchase-arrivalOrderAddPage-handler").html("");
				$("#purchase-arrivalOrderAddPage-handler").append("<option value=''></option>");
				for(var i=0;i<json.length;i++){
					$("#purchase-arrivalOrderAddPage-handler").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
				}
				$("#purchase-arrivalOrderAddPage-handler").val("${arrivalOrder.handlerid}");
			}
		});
		$("#purchase-arrivalOrderAddPage-buydept").change(function(){
			var v = $(this).val();
			if(v!=null && v !=""){
				$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#purchase-arrivalOrderAddPage-buyuser").html("");
	    				$("#purchase-arrivalOrderAddPage-buyuser").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#purchase-arrivalOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
			}else{
				$("#purchase-arrivalOrderAddPage-buyuser").html("");
			}
		});
		$("#purchase-arrivalOrderAddPage-buydept").change(function(){
			var v = $(this).val();
			if(v!=null && v !=""){
				$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#purchase-arrivalOrderAddPage-buyuser").html("");
	    				$("#purchase-arrivalOrderAddPage-buyuser").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#purchase-arrivalOrderAddPage-buyuser").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
			}else{
				$("#purchase-arrivalOrderAddPage-buyuser").html("");
			}
		});
		
		$("#purchase-arrivalOrderAddPage-supplier").supplierWidget({ });	

		

		$("#purchase-arrivalOrderAddPage-storage").widget({ 
			name:'t_purchase_arrivalorder',
			col:'storageid',
			width:130,
    		<c:if test="${arrivalOrder.source == '1' && !empty(arrivalOrder.storageid)}">readonly:true,</c:if>
			initValue:'${arrivalOrder.storageid}',
			singleSelect:true,
			onlyLeafCheck:true
		});
		
	});
  </script>
  </body>
</html>