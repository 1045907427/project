<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分供应商代垫应收款分析报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-receivablePastDueReson"></table>
    	<div id="report-toolbar-receivablePastDueReson" style="padding: 0px">
    		<form action="" id="report-query-form-receivablePastDueReson" method="post">
	    		<input id="report-query-groupcols" type="hidden" name="groupcols" value="supplierid"/>
	    		<table class="querytable">
                    <tr>
                        <%--<security:authorize url="/account/receivable/pastDueReasonOneclear.do">--%>
                            <%--<a href="javaScript:void(0);" id="report-oneclear-receivablePastDueReson" class="easyui-linkbutton button-list" iconCls="button-delete" plain="true">一键清除</a>--%>
                        <%--</security:authorize>--%>
                        <security:authorize url="/account/receivable/receivablePastDueResonExport.do">
                            <a href="javaScript:void(0);" id="report-buttons-receivablePastDueResonPage" class="easyui-linkbutton button-list" iconCls="button-export" plain="true">导出</a>
                        </security:authorize>
                        <a href="javaScript:void(0);" id="report-setdays-baseReceivablePastDue" class="easyui-linkbutton button-list" iconCls="button-intervalSet"  plain="true">设置区间</a>
                    </tr>
	    			<tr>
						<td>业务日期:</td>
						<td class="tdinput" ><input id="report-query-businessdate1" type="text" name="businessdate1" style="width:125px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
							到 <input id="report-query-businessdate2" type="text" name="businessdate2" class="Wdate" style="width:125px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
	    				<td>供应商:</td>
				    	<td class="tdinput"><input type="text" id="report-query-supplier" name="supplierid"/></td>
						<td>所属部门:</td>
						<td class="tdinput"><input type="text" id="report-query-deptid" name="supplierdeptid"/></td>
                    </tr>
	    			<tr>
						<td>商品品牌:</td>
						<td  class="tdinput"><input type="text" id="report-query-brandid" name="brandid"/></td>
						<td>科目名称:</td>
						<td  class="tdinput"><input type="text" id="report-query-subjectid" name="subjectid"/></td>
                        <td>是否只显示超账:</td>
                        <td>
                            <select name="ispastdue" style="width: 155px">
                                <option value="0" selected="selected">否</option>
                                <option value="1">是</option>
                            </select>
                        </td>
						<td rowspan="2" colspan="2" class="tdbutton">
							<a href="javaScript:void(0);" id="report-queay-receivablePastDueReson" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-receivablePastDueReson" class="button-qr">重置</a>
						</td>
	    			</tr>
	    			<tr>



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
						  {field:'supplierid',title:'供应商编号',width:60,sortable:true},
						  {field:'suppliername',title:'供应商名称',width:210,
						  	formatter:function(value,rowData,rowIndex){
						  		if(rowData.supplierid!=null && rowData.suppliername != "选中合计" && rowData.suppliername != "合计"){
				        			return '<a href="javascript:showFlowDetail(\''+rowData.supplierid+'\',\''+value+'\');" style="text-decoration:underline">'+rowData.suppliername+'</a>';
				        		}else{
				        			return rowData.suppliername;
				        		}
				        	}
						  },
						{field:'beginamount',title:'应收款期初',align:'right',resizable:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						  {field:'saleamount',title:'本期金额',align:'right',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'unpassamount',title:'正常期金额',align:'right',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						{field:'endamount',title:'期末金额',align:'right',resizable:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(rowData.beginamount==undefined){
									return "";
								}
								return formatterMoney(rowData.beginamount+rowData.saleamount);
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
					toolbar:'#report-toolbar-receivablePastDueReson',
					onDblClickRow:function(rowIndex, rowData){

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
				//供应商
				$("#report-query-supplier").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
		    		width:155,
					singleSelect:false
				});
				//所属部门
				$("#report-query-deptid").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:155,
                    onlyLeafCheck:false,
					singleSelect:true
				});
				//科目
				$("#report-query-subjectid").widget({
					name:'t_js_matcostsinput',
					col:'subjectid',
		    		width:155,
		    		onlyLeafCheck:false,
					singleSelect:false
				});
				//商品品牌
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
					width:270,
					onlyLeafCheck:false,
					singleSelect:false
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-receivablePastDueReson","report-reload-receivablePastDueReson");
				
				//查询
				$("#report-queay-receivablePastDueReson").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-receivablePastDueReson").serializeJSON();
		      		$("#report-datagrid-receivablePastDueReson").datagrid({
		      			url: 'account/receivable/showSupplierReceivablePastDueReasonListData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-receivablePastDueReson").click(function(){
					$("#report-query-supplier").widget('clear');
					$("#report-query-deptid").widget('clear');
					$("#report-query-brandid").widget('clear');
					$("#report-query-subjectid").widget('clear');
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
			 		name:'代垫应收分析报表',
			 		url:'account/receivable/exportSupplierPastDueResonData.do'
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
					    href: 'report/paymentdays/showPaymetdaysSetPage.do?type=3'
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

        			<c:forEach items="${list }" var="list">
            			passamount${list.seq} = Number(passamount${list.seq})+Number(rows[i].passamount${list.seq} == undefined ? 0 : rows[i].passamount${list.seq});
    				</c:forEach>
        		}
        		var foot=[{suppliername:'选中合计',bankname:'',beginamount:beginamount,saleamount:saleamount,unpassamount:unpassamount,totalpassamount:totalpassamount
        			<c:forEach items="${list }" var="list">
    					,passamount${list.seq}:passamount${list.seq}
    				</c:forEach>
        			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-receivablePastDueReson").datagrid("reloadFooter",foot);
    		}
    		
    		//根据客户编码获取该客户的流水明细
    		function showFlowDetail(supplierid,suppliername){
    			var url = 'account/receivable/showSupplierMatcostsListPage.do?supplierid='+supplierid+"&suppliername="+suppliername;
				$('<div id="report-customerSalesFlowDetail-dialog1"></div>').appendTo('#report-customerSalesFlowDetail-dialog');
				$("#report-customerSalesFlowDetail-dialog1").dialog({
					title:'供应商：['+suppliername+']代垫明细表',
		    		width:800,
		    		height:400,
		    		closed:true,
		    		modal:true,
		    		maximizable:true,
		    		cache:false,
		    		resizable:true,
		    		maximized:true,
				    href: url,
					queryParams: $("#report-query-form-receivablePastDueReson").serializeJSON(),
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
