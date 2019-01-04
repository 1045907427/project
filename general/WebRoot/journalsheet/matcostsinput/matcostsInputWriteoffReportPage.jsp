<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫核销情况报表</title>
    <%@include file="/include.jsp" %>
      <style>
          .checkbox1{
              float:left;
              height: 22px;
              line-height: 22px;
          }
          .divtext{
              height:22px;
              line-height:22px;
              float:left;
              display: block;
          }
      </style>
  </head>
  <body>
   	<div id="journalsheet-query-matcostsInputWriteoffReport">
    	<form action="" id="journalsheet-query-form-matcostsInputWriteoffReport" method="post">
    		<table class="querytable">
                <tr><security:authorize url="/journalsheet/matcostsInput/exportMatcostsInputWriteoffReportBtn.do">
                    <a href="javaScript:void(0);" id="journalsheet-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
                </tr>
    			<tr>
    				<td>核销日期:</td>
    				<td><input id="journalsheet-query-writeoffdate1" type="text" name="writeoffdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${firstday }"/> 到 <input id="journalsheet-query-writeoffdate2" type="text" name="writeoffdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"  value="${today }"/></td>
    				<td>核销状态:</td>
    				<td colspan="2">
    					<div style="float:left">
	    					<select name="iswriteoff" id="journalsheet-query-iswriteoff" style="width:80px;">
	    						<option value="">全部</option>
	    						<option value="1">核销</option>
	    						<option value="0">未核销</option>
	    					</select>
    					</div>
    					<div style="float:left;text-align:left;">
	    					<label class="divtext">&nbsp;小计列：</label>
	                        <input id="journalsheet-query-supplierid1" type="checkbox" class="costsgroupcols checkbox1" value="supplierid"/>
	                        <label for="journalsheet-query-supplierid1" class="divtext">供应商</label>
	                        <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                       </div>
                    </td>
    			</tr>
    			<tr>
                    <td>业务日期:</td>
                    <td><input id="journalsheet-query-businessdate1" type="text" name="begintime" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/> 到 <input id="journalsheet-query-businessdate2" type="text" name="endtime" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/></td>
                    <td>供应商:</td>
    				<td>
                        <input id="journalsheet-query-supplierid" type="text" name="supplierid" style="width: 225px;"/>
                    </td>
    				<td colspan="2">    										 					
    					<a href="javaScript:void(0);" id="journalsheet-query-buton" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="journalsheet-reload-buton" class="button-qr">重置</a>
    				</td>			
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="journalsheet-table-matcostsInputWriteoffReport"></table>
    <div style="display:none">
    	<div id="journalsheet-div-matcostsInputWriteoffReport-detail"></div>
    </div>
    <a href="javaScript:void(0);" id="matcostsInput-buttons-exportclick" style="display: none"title="导出">导出</a>
    <script type="text/javascript">

		var footerobject=null;
    	$(function(){
    		var columnJson = $("#journalsheet-table-matcostsInputWriteoffReport").createGridColumnLoad({
				frozenCol : [[
			    			]],
				commonCol : [[
							{field:'id',title:'编码',width:125,sortable:true},
				     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
				     		{field:'paydate',title:'支付日期',width:80,sortable:true},
				     		{field:'takebackdate',title:'收回日期',width:80,sortable:true},
							{field:'oaid',title:'OA编号',width:80,sortable:true},
							{field:'brandid',title:'商品品牌',width:60,sortable:true,
								formatter:function(val,rowData,rowIndex){
									return rowData.brandname;
								}
							},
							  {field:'supplierid',title:'供应商编号',width:80,sortable:true},
							  {field:'suppliername',title:'供应商名称',width:150,sortable:true},
							  {field:'supplierdeptid',title:'部门名称',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.supplierdeptname;
					        	}
							  },
							  {field:'customerid',title:'客户名称',width:130,sortable:true,
								formatter:function(val,rowData,rowIndex){
									return rowData.customername;
								}
							  },
							  {field:'subjectid',title:'科目名称',width:60,sortable:true,
								formatter:function(val,rowData,rowIndex){
									return rowData.subjectname;
								}
							  },
							  {field:'factoryamount',title:'工厂投入',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  <%--
							  {field:'htcompdiscount',title:'电脑折让',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'htpayamount',title:'支付金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'branchaccount',title:'转入分公司',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  --%>
							  {field:'iswriteoff',title:'核销',resizable:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		if(value=='1'){
						        		return "核销";
					        		}else if(value=='0'){
						        		return "未核销";
					        		}
					        	}
							  },
							  <c:forEach items="${reimbursetypeList }" var="list">
							  {field:'reimburse_${list.code}',title:'${list.codename}-核销',align:'right',resizable:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
				        			return formatterMoney(value);
					        	}
							  },
    						  </c:forEach>
							  {field:'writeoffamount',title:'核销合计金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'unwriteoffamount',title:'未核销金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'writeoffdate',title:'最新核销日期',resizable:true,sortable:true,
									formatter:function(val,rowData,rowIndex){
										if(rowData.iswriteoff=='1' && val){
											if(val.length>=10){
												return val.substring(0,10);
											}else{
												return val;
											}
										}
									}
								},
								{field:'writeoffer',title:'核销人员',resizable:true,sortable:true,
									formatter:function(val,rowData,rowIndex){
										if(rowData.iswriteoff=='1' && rowData.writeoffername){
											return rowData.writeoffername;
										}
									}
								}
				             ]]
			});
			$("#journalsheet-table-matcostsInputWriteoffReport").datagrid({ 
		 		authority:columnJson,
		 		frozenColumns: columnJson.frozen,
				columns:columnJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'supplierid',
		 		sortOrder:'asc',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				toolbar:'#journalsheet-query-matcostsInputWriteoffReport',
				onDblClickRow:function(rowIndex, rowData){
				},
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
					}
		 		},
				onCheckAll:function(){
		 			writeoffCountTotalAmount();
				},
				onUncheckAll:function(){
					writeoffCountTotalAmount();
				},
				onCheck:function(){
					writeoffCountTotalAmount();
				},
				onUncheck:function(){
					writeoffCountTotalAmount();
				}
			}).datagrid("columnMoving");
			
			$("#journalsheet-query-supplierid").supplierWidget({
				name:'t_js_matcostsinput',
				col:'supplierid',
				singleSelect:true,
				width:200,
				view:true
			});
			$("#journalsheet-query-buton").click(function(){
				reloadColumn();
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#journalsheet-query-form-matcostsInputWriteoffReport").serializeJSON();
	      		$("#journalsheet-table-matcostsInputWriteoffReport").datagrid({
	      			url: 'journalsheet/matcostsInput/getMatcostsInputWriteoffReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#journalsheet-reload-buton").click(function(){
				$("#journalsheet-query-supplierid").supplierWidget('clear');
				$("#journalsheet-query-form-matcostsInputWriteoffReport")[0].reset();
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('loadData',{total:0,rows:[]});
			});
			$("#journalsheet-export-buton").click(function(){
				var groupcols=$("#report-query-groupcols").val()||"";
				var title="代垫核销情况报表";
				if("supplierid"==groupcols){
					title=title+"-按供应商小计";
				}
				$("#matcostsInput-buttons-exportclick").Excel('export',{
					queryForm: "#journalsheet-query-form-matcostsInputWriteoffReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/matcostsInput/exportMatcostsInputWriteoffReportData.do'
				});	
				$("#matcostsInput-buttons-exportclick").trigger("click");
			});
			
			$(".costsgroupcols").click(function(){
				var cols = "";
				$("#report-query-groupcols").val("");
				$(".costsgroupcols").each(function(){
					if($(this).attr("checked")){
						if(cols==""){
							cols = $(this).val();
						}else{
							cols += ","+$(this).val();
						}
						$("#report-query-groupcols").val(cols);
					}
				});
			});
			
    	});
    	function reloadColumn(){
        	var cols=$("#report-query-groupcols").val()||"";
    		if(cols=="supplierid"){				
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "id");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "businessdate");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "oaid");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "brandid");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "customerid");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "subjectid");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "iswriteoff");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "writeoffdate");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "writeoffer");
			}
			else{
        		$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('showColumn', "id");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('showColumn', "businessdate");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('showColumn', "oaid");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('showColumn', "brandid");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('showColumn', "customerid");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('showColumn', "subjectid");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('showColumn', "iswriteoff");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('showColumn', "writeoffdate");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('showColumn', "writeoffer");
			}

    		var iswriteoff=$("#journalsheet-query-iswriteoff").val();
			if("0"==iswriteoff){
				 <c:forEach items="${reimbursetypeList }" var="list">
					$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "reimburse_${list.code}");
				</c:forEach>

				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "writeoffdate");
				$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('hideColumn', "writeoffer");		
			}else{
				 <c:forEach items="${reimbursetypeList }" var="list">
					$("#journalsheet-table-matcostsInputWriteoffReport").datagrid('showColumn', "reimburse_${list.code}");
				</c:forEach>
			}
    	}
    	function writeoffCountTotalAmount(){
    		var rows =  $("#journalsheet-table-matcostsInputWriteoffReport").datagrid('getChecked');
    		if(null==rows || rows.length==0){
           		var foot=[];
    			if(null!=footerobject){
	        		foot.push(emptyChooseObjectFoot());
	        		foot.push(footerobject);
	    		}
    			$("#journalsheet-table-matcostsInputWriteoffReport").datagrid("reloadFooter",foot);
           		return false;
       		}
    		var factoryamount = 0;
    		var htcompdiscount = 0;
    		var htpayamount=0;
    		var branchaccount=0;
    		var actingmatamount = 0;
    		var reimburseamount = 0;
    		var writeoffamount = 0;

    		var unwriteoffamount = 0;
    		
    		<c:forEach items="${reimbursetypeList }" var="list">
    			var reimburse_${list.code}=0;
    		</c:forEach>
    		
    		for(var i=0;i<rows.length;i++){
    			factoryamount = Number(factoryamount)+Number(rows[i].factoryamount == undefined ? 0 : rows[i].factoryamount);
    			htcompdiscount = Number(htcompdiscount)+Number(rows[i].htcompdiscount == undefined ? 0 : rows[i].htcompdiscount);
    			htpayamount = Number(htpayamount)+Number(rows[i].htpayamount == undefined ? 0 : rows[i].htpayamount);
    			branchaccount = Number(branchaccount)+Number(rows[i].branchaccount == undefined ? 0 : rows[i].branchaccount);
    			actingmatamount = Number(actingmatamount)+Number(rows[i].actingmatamount == undefined ? 0 : rows[i].actingmatamount);
    			reimburseamount = Number(reimburseamount)+Number(rows[i].reimburseamount == undefined ? 0 : rows[i].reimburseamount);
    			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
    			unwriteoffamount = Number(unwriteoffamount)+Number(rows[i].unwriteoffamount == undefined ? 0 : rows[i].unwriteoffamount);
    			
        		<c:forEach items="${reimbursetypeList }" var="list">
        			reimburse_${list.code} = Number(reimburse_${list.code})+Number(rows[i].reimburse_${list.code} == undefined ? 0 : rows[i].reimburse_${list.code});
        		</c:forEach>
        		
    		}
    		var foot=[{suppliername:'选中金额',factoryamount:factoryamount,htcompdiscount:htcompdiscount,
        				htpayamount:htpayamount,branchaccount:branchaccount,actingmatamount:actingmatamount,
        				reimburseamount:reimburseamount,writeoffamount:writeoffamount,unwriteoffamount:unwriteoffamount
        				
        				<c:forEach items="${reimbursetypeList }" var="list">
							,reimburse_${list.code} : reimburse_${list.code}
						</c:forEach>
						
        			}];
    		if(null!=footerobject){
        		foot.push(footerobject);
    		}
    		$("#journalsheet-table-matcostsInputWriteoffReport").datagrid("reloadFooter",foot);
    	}
    	function emptyChooseObjectFoot(){
    		var factoryamount = 0;
    		var htcompdiscount = 0;
    		var htpayamount=0;
    		var branchaccount=0;
    		var actingmatamount = 0;
    		var reimburseamount = 0;
    		var writeoffamount=0;
    		var unwriteoffamount=0;
    		<c:forEach items="${reimbursetypeList }" var="list">
    			var reimburse_${list.code}=0;
    		</c:forEach>
    		var foot={suppliername:'选中金额',factoryamount:factoryamount,htcompdiscount:htcompdiscount,
				htpayamount:htpayamount,branchaccount:branchaccount,actingmatamount:actingmatamount,
				reimburseamount:reimburseamount,writeoffamount:writeoffamount,unwriteoffamount:unwriteoffamount
				
				<c:forEach items="${reimbursetypeList }" var="list">
					,reimburse_${list.code} : reimburse_${list.code}
				</c:forEach>
				
			};
			return foot;
    	}
    </script>
  </body>
</html>
