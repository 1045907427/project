<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  	<input type="hidden" id="purchase-backid-returnCheckOrderAddPage" value="${id }" />
  	<div id="purchase-returnCheckOrderPage-layout" class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="purchase-buttons-returnCheckOrderPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">
    		<div class="easyui-panel" data-options="fit:true" id="purchase-panel-returnCheckOrderPage">
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
	var returnCheckOrder_type = '${type}';
	returnCheckOrder_type=$.trim(returnCheckOrder_type.toLowerCase());
	if(returnCheckOrder_type==""){
		returnCheckOrder_type='edit';
	}
	var returnCheckOrder_url = "purchase/returnorder/returnCheckOrderEditPage.do";
	if(returnCheckOrder_type == "view"|| returnCheckOrder_type == "show" || returnCheckOrder_type == "handle"){
		returnCheckOrder_url = "purchase/returnorder/returnCheckOrderViewPage.do?id=${id}";
	}
	if(returnCheckOrder_type == "edit"){
		returnCheckOrder_url = "purchase/returnorder/returnCheckOrderEditPage.do?id=${id}";
	}
	var pageListUrl="/purchase/returnorder/returnCheckOrderListPage.do";
	function returnCheckOrder_realSave_form_submit(){
		$("#purchase-form-returnCheckOrderAddPage").form({
		    onSubmit: function(){  
		    	var flag = $(this).form('validate');
	  		   	if(flag==false){
	  		   		return false;
	  		   	}
	  		  	loading("提交中..");
	  		},  
	  		success:function(data){
	  		  	loaded();
	  		  	var json = $.parseJSON(data);
	  		    if(json.flag==true){
	  		       	$.messager.alert("提醒","保存并验收成功");
	  		    	returnCheckOrder_RefreshDataGrid();
	  		      	$("#purchase-panel-returnCheckOrderPage").panel('refresh', 'purchase/returnorder/returnCheckOrderEditPage.do?id='+ json.backid);
	  		      	$("#purchase-backid-returnCheckOrderAddPage").val(json.backid);
	  		    }
	  		    else{
	  		    	if(json.msg){
	  		       		$.messager.alert("提醒","保存并验收失败。"+json.msg);
	  		    	}else{
	  		       		$.messager.alert("提醒","保存并验收失败");
	  		    	}
	  		    }
	  		}
	  	});
	}
	function returnCheckOrder_RefreshDataGrid(){
		try{			
			tabsWindowURL(pageListUrl).$("#purchase-table-returnCheckOrderListPage").datagrid('reload');
		}catch(e){
		}
	}

	function isLockData(id,tname){
		var flag = false;
		$.ajax({
            url :'system/lock/unLockData.do',
            type:'post',
            data:{id:id,tname:tname},
            dataType:'json',
            async: false,
            success:function(json){
            	flag = json.flag ;
            }
        });
        return flag;
	}
  	function orderDetailEditDialog(initdata){
		var flag=$("#purchase-form-returnCheckOrderAddPage").form('validate');
	   	if(flag==false){
			return false;
		}
	   	$('<div id="purchase-returnCheckOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-returnCheckOrderAddPage-dialog-DetailOper');
  		var $DetailOper=$("#purchase-returnCheckOrderAddPage-dialog-DetailOper-content");
		$DetailOper.dialog({
			title:'商品信息修改(按ESC退出)',
		    width: 680,  
		    height: 400,
		    closed: true,
		    cache: false, 
		    modal: true,
		    maximizable:true,
		    resizable:true,
		    href:"purchase/returnorder/returnCheckOrderDetailEditPage.do",
		    onLoad:function(){
		    	try{
				    if(initdata!=null && initdata.goodsid!=null && initdata.goodsid!=""){
				    	if($("#purchase-form-returnCheckOrderDetailEditPage").size()>0){
						    if(initdata.goodsInfo != null){
						    	$("#purchase-form-returnCheckOrderDetailEditPage").form('load',initdata.goodsInfo);
						    }
				  	  		$("#purchase-returnCheckOrderDetail-span-auxunitname").html(initdata.auxunitname);
				  	  		$("#purchase-returnCheckOrderDetail-span-unitname").html(initdata.unitname);
			  	  			$("#purchase-form-returnCheckOrderDetailEditPage").form('load',initdata);
					    }
			  		}	
			    }catch(e){
			    }
			    $("#purchase-returnCheckOrderDetail-taxprice").focus();
			    $("#purchase-returnCheckOrderDetail-taxprice").select();

				formaterNumSubZeroAndDot();

				$("#purchase-form-returnCheckOrderDetailEditPage").form('validate');
		    },
		    onClose:function(){
	            $DetailOper.dialog("destroy");
	        }
		});
        $DetailOper.dialog("open");
  	}
  	function checkAfterAddGoods(goodsid){
  	  	if(goodsid==null || goodsid==""){
  	  	  	return false;
  	  	}
  	  	var $ordertable=$("#purchase-returnCheckOrderAddPage-returnCheckOrdertable");
  	  	var flag=true;
  	  	if($ordertable.size()>0){
  	  	  	var data=$ordertable.datagrid('getRows');
  	  	  	if(data!=null && data.length>0){
	  	  	  	for(var i=0;i<data.length;i++){
	  	  	  	  	if(data[i].goodsid && data[i].goodsid==goodsid){
	  	  	  	  	  	flag=false;
	  	  	  	  	  	break;
	  	  	  	  	}
	  	  	  	}
  	  	  	}
  	  	}
  	  	return flag;
  	}
  	function footerReCalc(){
  		disableChoiceWidget();
		var $potable=$("#purchase-returnCheckOrderAddPage-returnCheckOrdertable");
		var data = $potable.datagrid('getRows');
		var unitnum = 0;
		var taxamount=0;
		var notaxamount=0;
		var tax=0;
		var auxnum=0;
		var auxunitname="";
		var auxremainder=0;
		for(var i=0;i<data.length;i++){
 			if(data[i].unitnum){
				unitnum = Number(unitnum)+Number(data[i].unitnum);
 			}
 			if(data[i].taxamount){
				taxamount=Number(taxamount)+Number(data[i].taxamount);
 			}
 			if(data[i].notaxamount){
 				notaxamount=Number(notaxamount)+Number(data[i].notaxamount);
 			}
			if(data[i].tax){
				tax=Number(tax)+Number(data[i].tax);
			}
			if(data[i].auxnum){
				auxnum=Number(auxnum)+Number(data[i].auxnum);
			}
			if(data[i].auxremainder){
				auxremainder=Number(auxremainder)+Number(data[i].auxremainder);
			}
			if((auxunitname=="" || auxunitname==null) && data[i].auxunitname){
				auxunitname=data[i].auxunitname;
			}
		}
		unitnum=String(unitnum);
		taxamount=String(taxamount);
		notaxamount=String(notaxamount);
		tax=String(tax);
		auxnum=String(parseInt(auxnum))+auxunitname+(auxremainder>0?auxremainder:"");
		$potable.datagrid('reloadFooter',[
    		{goodsid: '合计',unitnum: unitnum,taxamount:taxamount,notaxamount:notaxamount,auxnumdetail:auxnum,tax:tax}
    	]);
	}

	function checkGoodsDetailEmpty(){
		var flag=true;
		var $ordertable=$("#purchase-returnCheckOrderAddPage-returnCheckOrdertable");
		var data = $ordertable.datagrid('getRows');
		for(var i=0;i<data.length;i++){
			if(data[i].goodsid && data[i].goodsid!=""){
				flag=false;
				break;
			}
		}
		return flag;
	}
	//禁用表单title
	function disableChoiceWidget(){
		var rows =  $("#purchase-returnCheckOrderAddPage-returnCheckOrdertable").datagrid('getRows');
		var count = 0;
		for(var i=0;i<rows.length;i++){
			if(rows[i].goodsid && rows[i].goodsid!=""){
				count ++;
			}
		}
		if(count>0){
    		$("#purchase-returnCheckOrderAddPage-supplier").supplierWidget("readonly",true);
		}else{
			$("#purchase-returnCheckOrderAddPage-supplier").supplierWidget("readonly",false);
		}
	}
	var tableColJson = $("#purchase-returnCheckOrderAddPage-returnCheckOrdertable").createGridColumnLoad({
  	  	name :'purchase_returnorder_detail',
		frozenCol : [[
 		]],
		commonCol : [[
					{field:'goodsid',title:'商品编码',width:70,sortable:true},
					{field:'name', title:'商品名称', width:220,aliascol:'goodsid',
						formatter:function(value,rowData,rowIndex){
				       		if(rowData.goodsInfo != null){
				       			return rowData.goodsInfo.name;
				       		}else{
				       			return "";
			       			}
				        }
					},
					{field:'barcode', title:'条形码',width:90,aliascol:'goodsid',sortable:true,
						formatter:function(value,rowData,rowIndex){
				       		if(rowData.goodsInfo != null){
				       			return rowData.goodsInfo.barcode;
				       		}else{
				       			return "";
				       		}
				        }
					},
					{field:'model', title:'规格型号',width:80,aliascol:'goodsid',hidden:true,
						formatter:function(value,rowData,rowIndex){
				       		if(rowData.goodsInfo != null){
				       			return rowData.goodsInfo.model;
				       		}else{
				       			return "";
				       		}
				        }
					},
					{field:'brandName', title:'商品品牌',width:60,aliascol:'goodsid',hidden:true,
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
				       		if(rowData.goodsInfo != null && rowData.goodsInfo.boxnum!=null){
				       			return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
				       		}else if(value!=null){
					       		return formatterBigNumNoLen(value);
				       		}
				       		else{
				       			return "";
				       		}
				        }
					},
					{field:'unitid', title:'单位',width:35,
						formatter: function(value,row,index){
							return row.unitname;
						}
					},
					{field:'unitnum', title:'数量',width:60,align:'right',sortable:true,
						formatter:function(value,row,index){
			        		return formatterBigNumNoLen(value);
				        }
			    	},
					{field:'taxprice', title:'单价',width:60,align:'right',sortable:true,
						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
			    	},
			    	{field:'boxprice', title:'箱价',aliascol:'taxprice',width:60,align:'right',sortable:true,
						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
			    	},
					{field:'taxamount', title:'金额',width:60,align:'right',sortable:true,
						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
				    },
				    {field:'notaxprice', title:'未税单价',width:60,align:'right',sortable:true,
						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
			    	},
			    	{field:'noboxprice', title:'未税箱价',aliascol:'notaxprice',width:60,align:'right',sortable:true,
						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
			    	},
					{field:'notaxamount', title:'未税金额',width:60,align:'right',sortable:true,
						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
				    },
				    {field:'taxtypename', title:'税种',width:60,align:'right',hidden:true},
				    {field:'tax', title:'税额',width:60,align:'right',sortable:true,
						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
				    },
					{field:'auxunitid', title:'辅单位',width:60,hidden:true,
						formatter: function(value,row,index){
							return row.auxunitname;
						}
					},
					{field:'auxnumdetail', title:'辅数量',width:80,align:'right'},
				    {field:'storagelocationid', title:'所属库位',width:100,hidden:true,
				    	formatter:function(value,row,index){
			        		return row.storagelocationname;
				        }
				    },
				    {field:'batchno',title:'批次号',width:80,hidden:true},
					{field:'produceddate',title:'生产日期',width:80,hidden:true},
			        {field:'deadline',title:'有效截止日期',width:80,hidden:true},
				    {field:'remark', title:'备注',width:150}
		]]
	});
    function detailOnSortColumn(sort, order){
        var goodsInfoArr=["barcode"];
        var issort=false;
        if(sort==null || sort==""){
            return true;
        }
        var data = $("#purchase-returnCheckOrderAddPage-returnCheckOrdertable").datagrid("getData");
        var rows = data.rows;
        var dataArr = [];
        for(var i=0;i<rows.length;i++){
            if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                dataArr.push(rows[i]);
            }
        }
        dataArr.sort(function(a,b){
            var atmp=0;
            var btmp=0;
            if($.inArray(sort,goodsInfoArr)>=0){
                console.log(sort);
                console.log(goodsInfoArr);
                var aGInfo=a.goodsInfo || {};
                var bGInfo=b.goodsInfo || {};
                atmp=aGInfo[sort];
                btmp=bGInfo[sort];

            }else{
                atmp = a[sort];
                btmp = b[sort];
            }
            if(atmp==null || btmp==null){
                return -1;
            }

            if($.isNumeric(atmp)){
                if(order=="asc"){
                    return Number(atmp)>Number(btmp)?1:-1
                }else{
                    return Number(atmp)<Number(btmp)?1:-1
                }
            }else{
                if(order=="asc"){
                    return atmp>btmp?1:-1
                }else{
                    return atmp<btmp?1:-1
                }
            }
        });
        $("#purchase-returnCheckOrderAddPage-returnCheckOrdertable").datagrid("loadData",{rows:dataArr,total:data.total});
        return true;
    };
    $(document).ready(function(){
    	$("#purchase-panel-returnCheckOrderPage").panel({
			href : returnCheckOrder_url,
		    cache:false,
		    maximized:true,
		    border:false
		});
		//按钮
		$("#purchase-buttons-returnCheckOrderPage").buttonWidget({
			initButton:[
				<security:authorize url="/purchase/returnorder/returnCheckOrderBack.do">
				{
					type:'button-back',
					handler: function(data){
					   	if(data!=null && data.id!=null && data.id!=""){
							$("#purchase-backid-returnCheckOrderAddPage").val(data.id);
							$("#purchase-panel-returnCheckOrderPage").panel('refresh','purchase/returnorder/returnCheckOrderEditPage.do?id='+ data.id);
					   	}
					}
				},
				</security:authorize>
				<security:authorize url="/purchase/returnorder/returnCheckOrderNext.do">
				{
					type:'button-next',
					handler: function(data){
					   	if(data!=null && data.id!=null && data.id!=""){
							$("#purchase-backid-returnCheckOrderAddPage").val(data.id);
							$("#purchase-panel-returnCheckOrderPage").panel('refresh','purchase/returnorder/returnCheckOrderEditPage.do?id='+ data.id);
					   	}
					}
				},
				</security:authorize>
				{}
			],
			buttons:[
				<security:authorize url="/purchase/returnorder/returnOrderCheckBtn.do">
				{
					id: 'button-returnorder-savecheck',
					name:'保存并验收',
					iconCls:'button-audit',
					handler: function(){
						
						$.messager.confirm("提醒","是否保存并验收？",function(r){
							if(r){
								var datarows=$("#purchase-returnCheckOrderAddPage-returnCheckOrdertable").datagrid('getRows');
								if(datarows!=null && datarows.length>0){
									$("#purchase-returnCheckOrderAddPage-returnCheckOrderDetails").val(JSON.stringify(datarows));
								}
								returnCheckOrder_realSave_form_submit();
								$("#purchase-form-returnCheckOrderAddPage").submit();
							}
						});
					}
				},
				</security:authorize>
				<security:authorize url="/purchase/returnorder/returnOrderCheckCancelBtn.do">
				{
					id: 'button-returnorder-checkcancel',
					name:'取消验收',
					iconCls:'button-oppaudit',
					handler: function(){
						var id = $("#purchase-backid-returnCheckOrderAddPage").val();
						if(id == ""){
							return false;
						}
						$.messager.confirm("提醒","是否取消验收？",function(r){
							if(r){
								loading("取消验收中..");
								$.ajax({   
						            url :'purchase/returnorder/updateReturnOrderCheckCancel.do?id='+ id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
							            if(json.flag){
							            	//$("#purchase-returnCheckOrderAddPage-status").val("2");
						            		$.messager.alert("提醒", "取消验收成功");	
								            returnCheckOrder_RefreshDataGrid();
							  		      	$("#purchase-panel-returnCheckOrderPage").panel('refresh', 'purchase/returnorder/returnCheckOrderEditPage.do?id='+ id);				            		
							            }else{
							            	if(json.msg){
							            		$.messager.alert("提醒","取消验收失败。"+json.msg);							            		
							            	}else{
							            		$.messager.alert("提醒","取消验收失败");
							            	}
							            }	
						            }
						        });	
							}
						});
					}
				},
				</security:authorize>
				{}
			],
			layoutid:'purchase-returnCheckOrderPage-layout',
			model: 'bill',
			type:'view',
			taburl:pageListUrl,
			id:'${id}',
			datagrid:'purchase-table-returnCheckOrderListPage',
			tname:'t_purchase_returnorder'
		});
		$(document).bind('keydown', 'esc',function (){
			$("#purchase-returnCheckOrderDetail-remark").focus();
			if($("#purchase-returnCheckOrderAddPage-dialog-DetailOper-content").size()>0){
				$("#purchase-returnCheckOrderAddPage-dialog-DetailOper-content").dialog("close");
			}
	    });

		$(document).bind('keydown', 'ctrl+enter',function (){
			$("#purchase-returnCheckOrderDetail-remark").focus();
			setTimeout(function(){
	    		$("#purchase-returnCheckOrderDetailEditPage-editSave").trigger("click");
	    	},300);
	    	return false;
	    });
	    $(document).bind('keydown', '+',function (){
	    	$("#purchase-returnCheckOrderDetail-remark").focus();
	    	setTimeout(function(){
	    		$("#purchase-returnCheckOrderDetailEditPage-editSave").trigger("click");
	    	},300);
	    	return false;
	    });
	});
    </script>
  </body>
</html>
