<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分客户应收款超账原因统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-receivablePastDueReson"></table>
    	<div id="report-toolbar-receivablePastDueReson" style="padding: 0px">
    		<form action="" id="report-query-form-receivablePastDueReson" method="post">
	    		<input id="report-query-groupcols" type="hidden" name="groupcols" value="customerid"/>
	    		<table class="querytable">
                    <tr>
                        <security:authorize url="/account/receivable/pastDueReasonOneclear.do">
                            <a href="javaScript:void(0);" id="report-oneclear-receivablePastDueReson" class="easyui-linkbutton button-list" iconCls="button-delete" plain="true">一键清除</a>
                        </security:authorize>
                        <security:authorize url="/account/receivable/receivablePastDueResonExport.do">
                            <a href="javaScript:void(0);" id="report-buttons-receivablePastDueResonPage" class="easyui-linkbutton button-list" iconCls="button-export" plain="true">导出</a>
                        </security:authorize>
                        <a href="javaScript:void(0);" id="report-setdays-baseReceivablePastDue" class="easyui-linkbutton button-list" iconCls="button-intervalSet"  plain="true">设置区间</a>
                    </tr>
	    			<tr>
	    				<td>客户业务员:</td>
				    	<td class="tdinput"><input type="text" id="report-query-salesuser" name="salesuser"/></td>
                        <td>承诺到款日期:</td>
                        <td class="tdinput"  colspan="3"><input id="report-query-commitmentdate1" type="text" name="commitmentdate1" style="width:125px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                            到 <input id="report-query-commitmentdate2" type="text" name="commitmentdate2" class="Wdate" style="width:125px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                        <td>品牌部门:</td>
                        <td  class="tdinput"><input type="text" id="report-query-branddept" name="branddept"/></td>
                    </tr>
	    			<tr>
	    				<td>收款人:</td>
	    				<td  class="tdinput"><input type="text" id="report-query-payeeid" name="payeeid"/></td>
                        <td>是否只显示超账:</td>
                        <td>
                            <select name="ispastdue" style="width: 50px">
                                <option value="0" selected="selected">否</option>
                                <option value="1">是</option>
                            </select>
                        </td>
                        <td> 销售区域:</td>
                        <td><input type="text" id="report-query-salesarea" name="salesarea"/></td>
						<td>销售部门:</td>
						<td  class="tdinput"><input type="text" id="report-query-salesdept" name="salesdept"/></td>
	    			</tr>
	    			<tr>
	    				<td>客户名称:</td>
	    				<td  class="tdinput"><input type="text" id="report-query-customerid" name="customerid"/></td>
                        <td>显示全部客户:</td>
                        <td>
                            <select name="isAll" style="width: 50px">
                                <option value="0" selected="selected">否</option>
                                <option value="1">是</option>
                            </select>
                        </td>
                        <td>总店名称:</td>
                        <td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
                        <td rowspan="2" colspan="2" class="tdbutton">
                            <a href="javaScript:void(0);" id="report-queay-receivablePastDueReson" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="report-reload-receivablePastDueReson" class="button-qr">重置</a>
                        </td>
	    			</tr>
	    			<%--<tr>--%>
	    				<%----%>
                        <%--<td colspan="2"></td>--%>
                        <%--<td rowspan="3" colspan="2" class="tdbutton">--%>
	    					<%--<a href="javaScript:void(0);" id="report-queay-receivablePastDueReson" class="button-qr">查询</a>--%>
							<%--<a href="javaScript:void(0);" id="report-reload-receivablePastDueReson" class="button-qr">重置</a>--%>
						<%--</td>--%>
	    			<%--</tr>--%>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-paymentdaysSet-dialog"></div>
    	<div id="report-customerSalesFlowDetail-dialog"></div>
    	<div id="report-customerReceivablePastDueReason-dialog"></div>
    	<div id="report-historyCustomerReceivablePastDueReason-dialog"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-receivablePastDueReson").serializeJSON();
    		var reason_chooseNo;
	    	function frm_focus(val){
	    		reason_chooseNo = val;
	    	}
	    	function frm_blur(val){
	    		if(val == reason_chooseNo){
	    			reason_chooseNo = "";
	    		}
	    	}
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-receivablePastDueReson").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
				    	]],
					commonCol : [[
						  {field:'customerid',title:'客户编号',width:60,sortable:true},
						  {field:'customername',title:'客户名称',width:210,
						  	formatter:function(value,rowData,rowIndex){
						  		if(rowData.customerid!=null && rowData.customername != "选中合计" && rowData.customername != "合计"){
				        			return '<a href="javascript:showFlowDetail(\''+rowData.customerid+'\',\''+value+'\');" style="text-decoration:underline">'+rowData.customername+'</a>';
				        		}else{
				        			return rowData.customername;
				        		}
				        	}
						  },
						{field:'salesdept',title:'销售部门',width:80,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return rowData.salesdeptname;
							}
						},
						  {field:'branddept',title:'品牌部门',width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
						  		return rowData.branddeptname;
				        	}
						  },
						{field:'beginamount',title:'应收款期初',align:'right',resizable:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						  {field:'saleamount',title:'应收款',align:'right',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'unpassamount',title:'正常期金额',align:'right',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalpassamount',title:'超账期金额',align:'right',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	},
				        	styler:function(value,rowData,rowIndex){
				        		return 'color:blue';
				        	}
						  },
						  <c:forEach items="${list }" var="list">
						  {field:'passamount${list.seq}',title:'${list.detail}',align:'right',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	}
						  },
  						  </c:forEach>
  						  {field:'overreason',title:'超账期原因',resizable:true,
	  						  	formatter:function(value,rowData,rowIndex){
	  						  		if(rowData.overreason != undefined && rowData.overreason != ""){
						  			return '<a href="javascript:showHistory(\''+rowData.customerid+'\');" style="text-decoration:underline">'+rowData.overreason+'</a>';
				        		}
				        	}
  						  },
  						  {field:'commitmentdate',title:'承诺到款日期',width:90},
						  {field:'commitmentamount',title:'承诺到款金额',align:'right',resizable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			if(value != "" && value != null){
			        				return formatterMoney(value);
			        			}
				        	}	
						  },
						  {field:'actualamount',title:'实际到款金额',resizable:true,
						  	formatter:function(value,rowData,rowIndex){
						  		return formatterMoney(value);
				        	},
				        	styler:function(value,rowData,rowIndex){
				        		if(value < rowData.commitmentamount){
				        			return  'background-color:#D4FDD7;';
				        		}
				        	}
						  },
						  {field:'changenum',title:'变更次数',width:60},
						  {field:'cstmerbalance',title:'客户余额',resizable:true,align:'right',sortable:true,
						  	formatter:function(value,rowData,rowIndex){
						  		if(null != value && "" != value){
			        				return formatterMoney(value);
			        			}
				        	}
						  },
						  {field:'realsaleamount',title:'实际应收款',align:'right',resizable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'salesuser',title:'客户业务员',sortable:true,width:80,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.salesusername;
				        	}
						  },
						  {field:'salesarea',title:'销售区域',sortable:true,width:80,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.salesareaname;
				        	}
						  },
						  {field:'payeeid',title:'收款人',width:60,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.payeename;
				        	}
						  },
                        {field:'settletype',title:'结算方式',width:60,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.settletypename;
                            }
                        },
                        {field:'settleday',title:'结算日',width:60}
					]]
				});
    			$("#report-datagrid-receivablePastDueReson").datagrid({ 
					authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
			 		//sortName:'cstmerbalance',
			 		//sortOrder:'desc',
					toolbar:'#report-toolbar-receivablePastDueReson',
					onDblClickRow:function(rowIndex, rowData){
    					<security:authorize url="/account/receivable/pastDueReasonDblClick.do">
    					var url = 'account/receivable/showEditCustomerReceivablePastDueReasonPage.do?customerid='+rowData.customerid+'&rowindex='+rowIndex+'&saleamount='+rowData.saleamount+'&unpassamount='+rowData.unpassamount+'&totalpassamount='+rowData.totalpassamount;
                        $("#report-customerReceivablePastDueReason-dialog").dialog({
                            title:'编辑超账期原因',
                            width:350,
                            height:240,
                            closed:true,
                            modal:true,
                            cache:false,
                            resizable:true,
                            href: url,
                            onLoad:function(data){
                                $("#receivablePastDueReason-reason").focus();
                            }
                        });
                        $("#report-customerReceivablePastDueReason-dialog").dialog("open");
						</security:authorize>
						$(this).datagrid('clearSelections');
						$(this).datagrid('selectRow',rowIndex);
					},
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							countTotalAmount();
						}
			 		},
					onCheckAll:function(){
						countTotalAmount();
					},
					onUncheckAll:function(){
						countTotalAmount();
					},
					onCheck:function(){
						countTotalAmount();
					},
					onUncheck:function(){
						countTotalAmount();
					}
				}).datagrid("columnMoving");
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
		    		width:225,
					singleSelect:false
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:155,
                    onlyLeafCheck:false,
					singleSelect:false
				});
				//品牌部门
				$("#report-query-branddept").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:150,
		    		onlyLeafCheck:false,
					singleSelect:false
				});
				
				//销售区域
				$("#report-query-salesarea").widget({
					referwid:'RT_T_BASE_SALES_AREA',
		    		width:155,
		    		onlyLeafCheck:false,
					singleSelect:false
				});
				//销售部门
				$("#report-query-salesdept").widget({
					referwid:'RL_T_BASE_DEPARTMENT_SELLER',
					width:150,
					onlyLeafCheck:false,
					singleSelect:false
				});
				//收款人 
				$("#report-query-payeeid").widget({
					referwid:'RL_T_BASE_CUSTOMER_PAYEE',
		    		width:225,
		    		listnum:150,
		    		onlyLeafCheck:false,
					singleSelect:false
				});
				//客户业务员
				$("#report-query-salesuser").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
		    		width:225,
					singleSelect:false
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-receivablePastDueReson","report-reload-receivablePastDueReson");
				
				//查询
				$("#report-queay-receivablePastDueReson").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-receivablePastDueReson").serializeJSON();
		      		$("#report-datagrid-receivablePastDueReson").datagrid({
		      			url: 'account/receivable/showCustomerReceivablePastDueReasonListData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-receivablePastDueReson").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-payeeid").widget('clear');
					$("#report-query-salesuser").widget('clear');
					$("#report-query-salesarea").widget('clear');
					$("#report-query-branddept").widget('clear');
					$("#report-query-salesdept").widget('clear');
					$("#report-query-form-receivablePastDueReson")[0].reset();
		       		$("#report-datagrid-receivablePastDueReson").datagrid('loadData',{total:0,rows:[],footer:[]});
				});
				
				//一键清除
				$("#report-oneclear-receivablePastDueReson").click(function(){
					$.messager.confirm("提醒","确定一键清除?",function(r){
 						if(r){
 							loading("清除中..");
				  			$.ajax({
					  			url:'account/receivable/oneClearReceivablePastDueReason.do',
					  			dataType:'json',
					  			type:'post',
					  			success:function(retJson){
					  				loaded();
									if(retJson.flag){
										$("#report-datagrid-receivablePastDueReson").datagrid('reload');
										$.messager.alert("提醒","清除成功!");
									}
									else{
										$.messager.alert("提醒","清除失败!");
									}
					  			}
				  			});
 						}
 					});
				});
				
				$("#report-buttons-receivablePastDueResonPage").Excel('export',{
					queryForm: "#report-query-form-receivablePastDueReson", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'客户应收款超账原因统计报表',
			 		url:'account/receivable/exportBasereceivablePastDueResonData.do?type=customerid'
				});
				
				//设置超账期区间
				$("#report-setdays-baseReceivablePastDue").click(function(){
					$("#report-paymentdaysSet-dialog").dialog({
					    title: '超账期区间设置',  
					    width: 400,  
					    height: 400,  
					    closed: false,  
					    cache: false,  
					    modal: true,
					    href: 'report/paymentdays/showPaymetdaysSetPage.do'
					});
				});

				$(document).keydown(function(event){
					switch(event.keyCode){
						case 13://Enter
							if(reason_chooseNo == "overreason"){
								var reason = $("#receivablePastDueReason-reason").val();
					    		if(reason == "" || null == reason){
					    			$("#receivablePastDueReason-reason").focus();
					    			return false;
					    		}
								$("#receivablePastDueReason-commitmentamount").focus();
		 						return false;
							}
							if(reason_chooseNo == "commitmentamount" && $("#receivablePastDueReason-commitmentamount").validatebox('isValid')){
								$("#receivablePastDueReason-commitmentdate").focus();
		 						return false;
							}
							if(reason_chooseNo == "commitmentdate"){
								$("#receivablePastDueReason-commitmentdate").click();
		 						return false;
							}
							if(reason_chooseNo == "save"){
								$("#sales-save-saveMenu").click();
							}
						break;
						case 27://Esc
							$("#report-customerReceivablePastDueReason-dialog").dialog('close');
						break;
					}
				});
				
    		});
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-receivablePastDueReson").datagrid('getChecked');
    			var beginamount = 0;
				var saleamount = 0;
        		var unpassamount = 0;
        		var totalpassamount=0;
        		var realsaleamount = 0;
        		<c:forEach items="${list }" var="list">
				  var passamount${list.seq} = 0;
				</c:forEach>
        		var cstmerbalance = 0;
        		for(var i=0;i<rows.length;i++){
					beginamount = Number(beginamount)+Number(rows[i].beginamount == undefined ? 0 : rows[i].beginamount);
        			saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
        			unpassamount = Number(unpassamount)+Number(rows[i].unpassamount == undefined ? 0 : rows[i].unpassamount);
        			totalpassamount = Number(totalpassamount)+Number(rows[i].totalpassamount == undefined ? 0 : rows[i].totalpassamount);
    				cstmerbalance = Number(cstmerbalance)+Number(rows[i].cstmerbalance == undefined ? 0 : rows[i].cstmerbalance);
        			realsaleamount = Number(realsaleamount)+Number(rows[i].realsaleamount == undefined ? 0 : rows[i].realsaleamount);
        			<c:forEach items="${list }" var="list">
            			passamount${list.seq} = Number(passamount${list.seq})+Number(rows[i].passamount${list.seq} == undefined ? 0 : rows[i].passamount${list.seq});
    				</c:forEach>
        		}
        		var foot=[{customername:'选中合计',bankname:'',beginamount:beginamount,saleamount:saleamount,unpassamount:unpassamount,totalpassamount:totalpassamount,realsaleamount:realsaleamount
        			<c:forEach items="${list }" var="list">
    					,passamount${list.seq}:passamount${list.seq}
    				</c:forEach>
        			,cstmerbalance:cstmerbalance}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-receivablePastDueReson").datagrid("reloadFooter",foot);
    		}
    		
    		//根据客户编码获取该客户的流水明细
    		function showFlowDetail(customerid,customername){
    			var url = 'account/receivable/showCustomerSalesFlowDetailListPage.do?customerid='+customerid+'&customername='+customername;
				$('<div id="report-customerSalesFlowDetail-dialog1"></div>').appendTo('#report-customerSalesFlowDetail-dialog');
				$("#report-customerSalesFlowDetail-dialog1").dialog({
					title:'客户：['+customername+']销售流水明细表',
		    		width:800,
		    		height:400,
		    		closed:true,
		    		modal:true,
		    		maximizable:true,
		    		cache:false,
		    		resizable:true,
		    		maximized:true,
				    href: url,
				    onClose:function(){
				    	$('#report-customerSalesFlowDetail-dialog1').dialog("destroy");
				    }
				});
				$("#report-customerSalesFlowDetail-dialog1").dialog("open");
    		}
    		
    		//历史超账期原因
			function showHistory(customerid){
				var url = 'account/receivable/showHistoryCustomerReceivablePastDueReasonPage.do?customerid='+customerid;
				$('<div id="report-historyCustomerReceivablePastDueReason-dialog1"></div>').appendTo('#report-historyCustomerReceivablePastDueReason-dialog');
				$("#report-historyCustomerReceivablePastDueReason-dialog1").dialog({
					title:'历史超账期原因',
		    		width:800,
		    		height:400,
		    		closed:true,
		    		modal:true,
		    		//maximizable:true,
		    		cache:false,
		    		resizable:true,
		    		//maximized:true,
				    href: url,
				    onClose:function(){
				    	$('#report-historyCustomerReceivablePastDueReason-dialog1').dialog("destroy");
				    }
				});
				$("#report-historyCustomerReceivablePastDueReason-dialog1").dialog("open");
			}
    	</script>
  </body>
</html>
