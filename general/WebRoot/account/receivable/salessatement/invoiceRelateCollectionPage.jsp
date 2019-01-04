<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>发票关联收款单</title>
  </head>
  
  <body>
  	<div id="account-relateCollectionOrder-toolbar">
   		<form action="account/receivable/addRelateCollectionOrder.do" method="post" id="account-form-relateCollectionOrder">
   		<table  class="box_table" >
   			<tr>
   				<td>编 号:</td>
   				<td>
   					<input type="text" width="180"  readonly="readonly" name="billid" value="${billid }"/>
   					<input type="hidden" name="billtype" value="${billtype }"/>
   				</td>
                <td>已关联金额:</td>
                <td style="text-align: left;">
                    <input type="text" id="account-relateCollectionOrder-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" name="amount" readonly="readonly"/>
                </td>
   			</tr>
   			<tr>
                <td>金 额:</td>
                <td style="text-align: left;">
                    <input type="text" id="account-relateCollectionOrder-invoiceamount" class="easyui-numberbox no_input" data-options="precision:2" readonly="readonly"  value="${amount }"/>
                </td>
   				<td width="120">未关联金额:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-relateCollectionOrder-last-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" readonly="readonly" value="${amount }"/>
   				</td>
   			</tr>
   			<tr>
   				<td>尾差金额:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-relateCollectionOrder-last-tailamount" class="easyui-numberbox no_input" data-options="precision:2" name="tailamount" readonly="readonly" value="${-amount }"/>
   				</td>
   				<td>
                    <security:authorize url="/account/receivable/collectionOrderAddPage.do">
   					<a href="javaScript:void(0);" id="account-add-collectionOrder" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增收款单">新增</a>
   				    </security:authorize>
                </td>
   				<td colspan="2"></td>
   			</tr>
   			<input type="hidden" id="account-relateCollectionOrder-detail" name="detailList"/>
   		</table>
    	</form>
    </div>
    <table id="account-relateCollectionOrder-table"></table>
   <script type="text/javascript">
   		$(function(){
   			$("#account-form-relateCollectionOrder").form({
			    onSubmit: function(){  
			    	var rows = $("#account-relateCollectionOrder-table").datagrid("getChecked");
			    	var data = [];
			    	for(var i=0;i<rows.length;i++){
			    		var object = {id:rows[i].id,businessdate:rows[i].businessdate,amount:rows[i].amount,writeoffamount:rows[i].writeoffamount,
			    						remainderamount:rows[i].remainderamount,relateamount:rows[i].relateamount,version:rows[i].version};
			    		data.push(object);
			    	}
			    	$("#account-relateCollectionOrder-detail").val(JSON.stringify(data));
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒","关联成功");
			    		$('#account-dialog-collection-relate_invoice-content').dialog("close");
			    		$("#account-dialog-writeoff-info-content").dialog("refresh");
			    	}else{
			    		$.messager.alert("提醒","关联失败");
			    		$("#account-relate-ok").val("0");
			    	}
			    }  
			}); 
   			$("#account-relateCollectionOrder-table").datagrid({
   				columns:[[
                    {field:'ck',checkbox:true},
                    {field:'id',title:'收款单编号',width:125},
                    {field:'businessdate',title:'收款日期',width:80},
                    {field:'amount',title:'收款单金额',resizable:true,align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'writeoffamount',title:'已核销金额',resizable:true,align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'remainderamount',title:'剩余金额',resizable:true,align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'relateamount',title:'关联金额',resizable:true,align:'right',align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'tailcontrol',title:'尾差控制',resizable:true,align:'right',align:'right',
                        formatter:function(value,rowData,rowIndex){
                            if(rowData.checkedrow == '1' && rowData.remainderamount<${positeTailAmount} && rowData.remainderamount > ${negateTailAmount} && rowData.remainderamount>0){
                                return '<a href="javascript:tailModify(\''+rowData.id+'\',\''+rowIndex+'\');">尾差调整</a>';
                            }
                        }
                    },
                    {field:'remark',title:'备注',width:80}
                ]],
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		singleSelect: false,
		 		data:JSON.parse('${detailList}'),
				toolbar:'#account-relateCollectionOrder-toolbar',
				onClickCell:function(rowIndex, field, value){
					var unrelateamount  = $("#account-relateCollectionOrder-last-remainderamount").numberbox("getValue");
					if(field!="tailcontrol" && unrelateamount != 0){
						$(this).datagrid("checkRow",rowIndex);
					}
				},
    			onCheck:function(rowIndex,rowData){
    				rowData['checkedrow'] = '1';
    				var unrelateamount  = $("#account-relateCollectionOrder-last-remainderamount").numberbox("getValue");
    				if(Number(unrelateamount)==0){
    					$(this).datagrid("uncheckRow",rowIndex);
                        $.messager.alert("提醒","金额已全部关联！");
    					return false;
    				}
    				if(rowData.relateamount==null || Number(rowData.relateamount)==0){
	    				var unrelateamount = $("#account-relateCollectionOrder-last-remainderamount").numberbox("getValue");
						if(Number(unrelateamount)>Number(rowData.remainderamount)){
	    					rowData.relateamount=rowData.remainderamount;
	    					rowData.remainderamount = 0;
	    					$(this).datagrid('updateRow',{index:rowIndex, row:rowData});
	    				}else{
	    					rowData.remainderamount = rowData.remainderamount - unrelateamount;
	    					rowData.relateamount=unrelateamount;
	    					$(this).datagrid('updateRow',{index:rowIndex, row:rowData});
	    				}
    				}
    				countRelateAmount();
    			},
    			onUncheck:function(rowIndex,rowData){
    				rowData['checkedrow'] = '0';
    				rowData.remainderamount= Number(rowData.remainderamount)+Number(rowData.relateamount);
    				rowData.relateamount=0;
	    			$(this).datagrid('updateRow',{index:rowIndex, row:rowData});
    				countRelateAmount();
    			},
    			onCheckAll:function(){
    				$(this).datagrid("uncheckAll");
    			},
    			onUncheckAll:function(rows){
    				for(var i=0;i<rows.length;i++){
		    			rows[i].relateamount=0;
		    			$(this).datagrid('updateRow',{index:i, row:rows[i]});
		    		}
    			}
   			});
   			
   			//新增审核收款单
   			$("#account-add-collectionOrder").click(function(){
   				var customerid = $("#writeoffCollectionOrder-hidden-customerid").val();
   				var customername = $("#writeoffCollectionOrder-hidden-customername").val();
   				$('<div id="account-panel-collectionOrder-addauditpage1"></div>').appendTo('#account-panel-collectionOrder-addauditpage');
   				$('#account-panel-collectionOrder-addauditpage1').dialog({  
				    title: '收款单新增',  
				    width: 650,  
				    height: 310,  
				    collapsible:false,
				    minimizable:false,
				    maximizable:true,
				    resizable:true,
				    cache: false,  
				    href: 'account/receivable/showCollectionOrderAddAuditPage.do?customerid='+customerid+'&customername='+customername,  
				    modal: true,
				    onLoad:function(){
				    	$("#account-collectionOrder-amount").focus();
				    },
					onClose:function(){
				    	$('#account-panel-collectionOrder-addauditpage1').dialog("destroy");
				    }
				});
   			});
   		});
        function numberMax(max){ //验证接收数最大值
    		$.extend($.fn.validatebox.defaults.rules, {
				numberMax:{
			    	validator:function(value){
			    		return parseInt(value) <= max;
			    	},
			    	message:'请输入小于等于'+max+'的数字!'
			    }
			});
    	}
    	function countRelateAmount(){
    		var rows = $("#account-relateCollectionOrder-table").datagrid("getChecked");
    		var relateamount = 0;
    		for(var i=0;i<rows.length;i++){
    			relateamount = Number(relateamount)+Number(rows[i].relateamount);
    		}
    		var totalamount = $("#account-relateCollectionOrder-invoiceamount").numberbox("getValue");
    		var unrelateamount = Number(totalamount)-Number(relateamount);
    		$("#account-relateCollectionOrder-remainderamount").numberbox("setValue",relateamount);
    		$("#account-relateCollectionOrder-last-remainderamount").numberbox("setValue",unrelateamount);
    		$("#account-relateCollectionOrder-last-tailamount").numberbox("setValue",-unrelateamount);
    	}
    	//关联收款单
    	function addRelateCollectionOrder(){
    		var unrelateamount = $("#account-relateCollectionOrder-last-remainderamount").numberbox("getValue");
    		var positeTailAmount = ${positeTailAmount};
    		var negateTailAmount = ${negateTailAmount};
    		var tailAmountLimit = "${negateTailAmount}~${positeTailAmount}";
    		var tailamount = $("#account-relateCollectionOrder-last-tailamount").numberbox('getValue');
    		if(tailamount<negateTailAmount || tailamount > positeTailAmount){
    			$.messager.alert("提醒","尾差金额为："+unrelateamount+",超过系统设置的金额:"+tailAmountLimit+"。不能进行核销关联！");
    			$("#account-relate-ok").val("0");
    			return false;
    		}
    		var rows = $("#account-relateCollectionOrder-table").datagrid("getChecked");
    		if(null==rows || rows.length==0){
    			$.messager.alert("提醒","未关联收款单");
    			$("#account-relate-ok").val("0");
    			return false;
    		}
    		if(unrelateamount>0){
    			$.messager.confirm("提醒","金额未全部关联，剩余未关联金额是否作为尾差金额。尾差金额为:"+unrelateamount+"？",function(r){
					if(r){
						$("#account-form-relateCollectionOrder").submit();
					}
				});
    		}else{
    			$("#account-form-relateCollectionOrder").submit();
    		}
    	}
    	//调整尾差金额
    	function tailModify(id,rowIndex){
    		var rows = $("#account-relateCollectionOrder-table").datagrid("getChecked");
    		var rowData = null;
    		for(var i=0;i<rows.length;i++){
    			if(rows[i].id==id){
    				rowData = rows[i];
    				break;
    			}
    		}
    		if(null!=rowData){
    			$.messager.confirm("提醒","收款单剩余:"+formatterMoney(rowData.remainderamount)+"，是否作为尾差金额？",function(r){
					if(r){
						rowData.relateamount = Number(rowData.relateamount) + Number(rowData.remainderamount);
	    				rowData.remainderamount = 0;
						$("#account-relateCollectionOrder-table").datagrid('updateRow',{index:rowIndex, row:rowData});
						$("#account-relateCollectionOrder-table").datagrid('selectRow',rowIndex);
						countRelateAmount();
					}
				});
    		}
    		
    	}
   </script>
  </body>
</html>
