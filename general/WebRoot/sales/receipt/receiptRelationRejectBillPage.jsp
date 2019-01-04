<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:false" style="height:260px">
			<table id="sales-receiptRelationRejectBillPage"></table>
		</div>
		<div data-options="region:'center',border:false">
			<table id="sales-detailList-receiptRelationRejectBillPage"></table>
		</div>
	</div>
  	<input type="hidden" id="sales-receiptid" value="${id}"/>
  	<script type="text/javascript">
  		var receipt_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}

        function checkIsReceipt(){
            var rowArr =$("#sales-receiptRelationRejectBillPage").datagrid("getChecked");
            var ids = null;
            for(var i=0;i<rowArr.length;i++){
                if(rowArr[i].receiptid == ""){
                    if(ids==null){
                        ids = rowArr[i].id;
                    }else{
                        ids +=","+ rowArr[i].id;
                    }
                }else{
                    var rowindex = $("#sales-receiptRelationRejectBillPage").datagrid('getRowIndex',rowArr[i]);
                    $("#sales-receiptRelationRejectBillPage").datagrid('uncheckRow',rowindex);
                    $.messager.alert('提醒',"回单已关联销售退货通知单，请先取消关联！");
                }
            }
            if(ids != null){
                $("#sales-detailList-receiptRelationRejectBillPage").datagrid({
                    url:"sales/showDirectRejectBillDetailByIds.do",
                    queryParams:{
                        ids: ids
                    }
                });
            }
        }

  		$(function(){
  			$("#sales-receiptRelationRejectBillPage").datagrid({
  				fit:true,
  				columns:[[
  							{field:'ck',checkbox:true},
  							{field:'id',title:'编号',width:120, align: 'left'},
							  {field:'businessdate',title:'业务日期',width:80,align:'left'},
							  {field:'customerid',title:'客户名称',width:100,align:'left',
							  	formatter:function(value,row,index){
						        		return row.customername;
							        }
							  },
							  {field:'handlerid',title:'对方经手人',width:80,align:'left'},
							  {field:'billtype',title:'退货类型',width:60,align:'left',
							  		formatter:function(value,row,index){
						        		if(value=="1"){
						        			return "直退";
						        		}else if(value=="2"){
						        			return "售后退货";
						        		}
							        }
							  },
							  {field:'salesdept',title:'销售部门',width:80,align:'left'},
							  {field:'salesuser',title:'客户业务员',width:80,align:'left'},
							  {field:'status',title:'关联状态',width:60,align:'left',
							  		formatter:function(value,row,index){
							  			if(row.receiptid!=""){
							  				return "已关联";
							  			}else{
							  				return "未关联";
							  			}
							        }
							  },
							  {field:'receiptid',title:'关联回单编号',width:100,align:'left'},
							  {field:'addusername',title:'制单人',width:80,sortable:true},
							  {field:'addtime',title:'制单时间',width:80,sortable:true},
							   {field:'remark',title:'备注',width:100,sortable:true}
				              ]],
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
		 		selectOnCheck:true,
		 		checkOnSelect:true,
		 		data:JSON.parse('${jsonStr}'),
		 		onCheck:function(index, data){
                    checkIsReceipt();
				},
                onUncheck:function(){
                    checkIsReceipt();
				},
                onCheckAll:function(rows){
                    checkIsReceipt();
                },
                onUncheckAll:function(rows){
                    checkIsReceipt();
                }
  			});
  			$("#sales-detailList-receiptRelationRejectBillPage").datagrid({
  				fit:true,
  				columns:[[
  						{field:'goodsid',title:'商品编码',width:80,align:'left'},
    					{field:'goodsname', title:'商品名称', width:100,align:'left',aliascol:'goodsid',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			if(rowData.deliverytype=='1'){
					       				return "<font color='blue'>&nbsp;赠 </font>"+rowData.goodsInfo.name;
					       			}else{
					       				return rowData.goodsInfo.name;
					       			}
					       		}else{
					       			return "";
				       			}
					        }
    					},
    					{field:'barcode', title:'条形码',width:90,align:'left',aliascol:'goodsid',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.barcode;
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'brandName', title:'商品品牌',width:80,align:'left',aliascol:'goodsid',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.brandName;
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'unitname', title:'主单位',width:50,align:'left'},
    					{field:'boxnum', title:'箱装量',aliascol:'goodsid',width:50,align:'right',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
					       		}else{
					       			return "";
					       		}
					        }
    					},
                        {field:'taxprice', title:'单价',width:70,align:'right',
                            formatter:function(value,row,index){
                                 return formatterMoney(value);
                            }
                        },
    					{field:'unitnum', title:'数量',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
    					{field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
                        {field:'taxamount', title:'金额',width:80,align:'right',
                            formatter:function(value,row,index){
                                return formatterMoney(value);
                            }
                        },
    					{field:'remark', title:'备注',width:80,align:'left'}
    				  ]],
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
		 		selectOnCheck:true,
		 		checkOnSelect:true,
				onLoadSuccess:function(data){
					var detailList = $("#sales-datagrid-receiptAddPage").datagrid("getRows");
					if(null!=detailList && detailList.length>0){
						for(var i=0;i<data.rows.length;i++){
							var goodsinfo1 = data.rows[i].goodsInfo;
							for(var j=0;j<detailList.length;j++){
								var goodsinfo2 = detailList[j].goodsInfo;
								if(data.rows[i].goodsid==detailList[j].goodsid || goodsinfo1.barcode==goodsinfo2.barcode){
									var index = $("#sales-detailList-receiptRelationRejectBillPage").datagrid("getRowIndex",data.rows[i]);
									$("#sales-detailList-receiptRelationRejectBillPage").datagrid("selectRow",index);
									break;
								}
							}
						}
					}
				}
  			});
  		});
  		function relationRejectSubmit(){
  			var receiptid = $("#sales-receiptid").val();
  			var data =  $("#sales-receiptRelationRejectBillPage").datagrid("getChecked");
  			if(null==data || data.length==0){
				$.messager.alert("提醒","请选择销售退货通知单!");
				return false;
			}
			var rejectbillid = "";
			for(var i=0;i<data.length;i++){
				if(rejectbillid==""){
					rejectbillid = data[i].id;
				}else{
					rejectbillid += ","+data[i].id;
				}
			}
			loading("提交中..");
			var ret = receipt_AjaxConn({receiptid:receiptid,rejectbillid:rejectbillid},'sales/rejectNumBigerThanReceiptNum.do');
			var retjson = $.parseJSON(ret);
			if(!retjson.flag){
				loaded();
				$.messager.alert("提醒",retjson.msg);
				return false;
			}
  			$.ajax({
	   			url:'sales/receiptRelationRejectBill.do',
	   			dataType:'json',
	   			type:'post',
	   			data:{receiptid:receiptid,rejectbillid:rejectbillid},
	   			success:function(json){
	   				loaded();
	   				if(json.flag){
	   					$.messager.alert("提醒","关联成功!<br/>"+json.msg);
	   					$("#sales-panel-receiptPage").panel("refresh");
	   					$("#sales-dialog-reletionRejectPage").dialog("close");
	   				}else{
	   					$.messager.alert("提醒","关联失败!<br/>"+json.msg);
	   				}
   				}
  			});
  		}
  	</script>
  </body>
</html>
