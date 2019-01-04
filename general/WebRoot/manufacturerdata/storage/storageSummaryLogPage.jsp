<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
  		<div id="storage-storageSummaryLog-table-query">
  			<form action="" id="storage-storageSummaryLog-table-form">
  			    <td>业务日期:</td>
    			<td class="tdinput"><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate"
    			onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到
    			<input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
  				<td>
                    <a href="javaScript:void(0);" id="storageSummaryLog-queay-queryList" class="button-qr">查询</a>
                </td>
  				<input type="hidden" name="goodsid" value="${goodsid}"/>
  				<input type="hidden" name="markid" value="${markid}"/>
  			</form>
  		</div>
		<table id="storage-storageSummaryLog-table"></table>
		<script type="text/javascript">
			$(function(){
				var storageSummaryLogFormJson = $("#storage-storageSummaryLog-table-form").serializeJSON();
				//根据初始的列与用户保存的列生成以及字段权限生成新的列
				storageSummaryLogJson = $("#storage-storageSummaryLog-table").createGridColumnLoad({
					name :'storage_summary_log',
					frozenCol : [[]],
					commonCol : [[   {field:'goodsInfo.name',title:'商品编码',width:60,aliascol:'goodsid',
						                 formatter:function(value,rowData,rowIndex){
			        	            	 return rowData.goodsid;
			        	                 }
					                 },
							        {field:'goodsid',title:'商品名称',width:120,
							        	formatter:function(value,rowData,rowIndex){
							        		if(rowData.goodsInfo!=null){
							        			return rowData.goodsInfo.name;
							        		}else{
							        			return "";
							        		}
							        	}
							        },

									{field:'billmodel',title:'单据类型',width:120,aliascol:'goodsid',
										formatter:function(value,rowData,rowIndex){
							        		return rowData.billmodelname;
							        	}
									},
									{field:'billid',title:'单据编号',width:140},
									{field:'goodsInfo.brand',title:'商品品牌',width:80,aliascol:'goodsid',hidden:true,
							        	formatter:function(value,rowData,rowIndex){
							        		if(rowData.goodsInfo!=null){
						        				return rowData.goodsInfo.brandName;
							        		}else{
							        			return "";
							        		}
							        	}
							        },
							        {field:'goodsInfo.model',title:'规格型号',width:100,aliascol:'goodsid',hidden:true,
							        	formatter:function(value,rowData,rowIndex){
							        		if(rowData.goodsInfo!=null){
							        			return rowData.goodsInfo.model;
							        		}else{
							        			return "";
							        		}
							        	}
							        },
							        {field:'goodsInfo.barcode',title:'条形码',width:85,aliascol:'goodsid',hidden:true,
							        	formatter:function(value,rowData,rowIndex){
							        		if(rowData.goodsInfo!=null){
							        			return rowData.goodsInfo.barcode;
							        		}else{
							        			return "";
							        		}
							        	}
							        },
							        {field:'storageid',title:'所属仓库',width:80,isShow:true,
							        	formatter:function(value,rowData,rowIndex){
							        		return rowData.storagename;
							        	}
							        },
							        {field:'unitid',title:'主单位',width:60,
							        	formatter:function(value,rowData,rowIndex){
							        		if(rowData.goodsid!=null && rowData.goodsid!=""){
					        				    return rowData.unitname;
							        		}else{
							        			return "";
							        		}
							        	}
							        },
							        {field:'receivenum',title:'收货数量',width:80,align:'right',
							        	formatter:function(value,rowData,rowIndex){
							        		if(value!=null && value!=0){
							        			if(rowData.unitname!=null){
							        				return formatterBigNumNoLen(value)+rowData.unitname;
							        			}else{
							        				return formatterBigNumNoLen(value);
							        			}
							        		}else{
							        			return "";
							        		}
							        	}
							        },
							        {field:'sendnum',title:'发货数量',width:80,align:'right',
							        	formatter:function(value,rowData,rowIndex){
							        		if(value!=null && value!=0){
							        			if(rowData.unitname!=null){
							        				return formatterBigNumNoLen(value)+rowData.unitname;
							        			}else{
							        				return formatterBigNumNoLen(value);
							        			}
							        		}else{
							        			return "";
							        		}
							        	}
							        },
							        {field:'auxunitid',title:'辅单位',width:50,
							        	formatter:function(value,rowData,rowIndex){
							        		if(rowData.goodsid!=null && rowData.goodsid!=""){
							        		    return rowData.auxunitname;
							        		}else{
							        			return "";
							        		}
							        	}
							        },
							        {field:'auxreceivenum',title:'辅单位收货数量',width:90,align:'right',aliascol:'auxunitid',
							        	formatter:function(value,rowData,rowIndex){
							        		return rowData.auxreceivenumdetail;
							        	}
							        },
							        {field:'auxsendnum',title:'辅单位发货数量',width:90,align:'right',aliascol:'auxunitid',
							        	formatter:function(value,rowData,rowIndex){
							        		return rowData.auxsendnumdetail;
							        	}
							        },
							        {field:'addtime',title:'操作时间',width:140},
							    ]]
				});
				$('#storage-storageSummaryLog-table').datagrid({
                    authority:storageSummaryLogJson,
                    frozenColumns: storageSummaryLogJson.frozen,
                    columns:storageSummaryLogJson.common,
                    fit:true,
                    showFooter:true,
                    method:'post',
                    rownumbers:true,
                    idField:'billid',
                    singleSelect:true,
                    pagination:true,
                    striped:true,
                    toolbar:'#storage-storageSummaryLog-table-query',
                    url:'manufacturerdata/showDataStorageSummaryLogList.do',
                    queryParams:storageSummaryLogFormJson
                }).datagrid("columnMoving");
			});
			//查询
			function querySumarryLog(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#storage-storageSummaryLog-table-form").serializeJSON();
	       		$("#storage-storageSummaryLog-table").datagrid("load",queryJSON);
			}
			$("#storageSummaryLog-queay-queryList").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#storage-storageSummaryLog-table-form").serializeJSON();
	       		$("#storage-storageSummaryLog-table").datagrid("load",queryJSON);
    		});
		</script>
  </body>
</html>
