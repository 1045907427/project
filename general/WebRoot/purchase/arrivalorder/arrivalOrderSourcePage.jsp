<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购进货单参照上游单据页面</title>
    <%@include file="/include.jsp" %>  
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:false" style="height:260px">
			<table id="purchase-buyOrderDatagrid-arrivalOrderSourcePage"></table>
		</div>
		<div data-options="region:'center',border:false">
			<table id="purchase-buyOrderdetailDatagrid-arrivalOrderSourcePage"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#purchase-buyOrderdetailDatagrid-arrivalOrderSourcePage").datagrid({
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
	 			singleSelect:true,	
				//fitColumns:true,
				columns:[[
						{field:'goodsid',title:'商品编码',width:60,isShow:true},
 						{field:'goodsname',title:'商品名称',width:220,isShow:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.name;
					       		}else{
					       			return "";
				       			}
					        }
				        },
		        		{field:'barcode',title:'条形码',width:90,isShow:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.barcode;
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
 						{field:'brand',title:'商品品牌',width:60,isShow:true,hidden:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.brandName;
					       		}else{
					       			return "";
					       		}
					        }
				        },
    					{field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
					       		}else{
					       			return "";
					       		}
					        }
    					},
						{field:'unitname',title:'单位',width:35,isShow:true},
						{field:'unitnum',title:'数量',width:60,align:'right',
							formatter: function(value,row,index){
			        			return formatterBigNumNoLen(value);
							}
				        },
						{field:'taxprice',title:'单价',width:60,align:'right',
							formatter: function(value,row,index){
				        		return formatterMoney(value);
				        	}
				        },
						{field:'taxamount',title:'金额',width:60,align:'right',
							formatter: function(value,row,index){
			        			return formatterMoney(value);
				        	}
				        },
						{field:'notaxprice',title:'无税单价',width:50,align:'right',
							formatter: function(value,row,index){
			        			return formatterMoney(value);
				        	}
				        },
						{field:'notaxamount',title:'无税金额',width:60,align:'right',
							formatter: function(value,row,index){
			        			return formatterMoney(value);
				        	}
				        },
						{field:'taxtypename',title:'税种',width:60,hidden:true},
						{field:'tax',title:'税额',width:80,align:'right',
							formatter: function(value,row,index){
			        			return formatterMoney(value);
		        			}
		        		},
				        {field:'auxunitid',title:'辅单位',width:50,isShow:true,
    						formatter: function(value,row,index){
								return row.auxunitname;
							}
						},
						{field:'auxnumdetail',title:'辅数量',width:60},
						{field:'arrivedate',title:'要求到货日期'},
						{field:'remark',title:'备注',width:200},
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
		});
	</script>
  </body>
</html>
