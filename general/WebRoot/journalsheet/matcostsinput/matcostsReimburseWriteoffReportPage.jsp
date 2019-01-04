<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>收回核销情况报表</title>
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
   	<div id="journalsheet-query-matcostsReimburseWriteoffReport">
    	<form action="" id="journalsheet-query-form-matcostsReimburseWriteoffReport" method="post">
    		<table class="querytable">
                <tr>
                    <security:authorize url="/journalsheet/matcostsInput/exportMatcostsReimburseWriteoffReportBtn.do">
                        <a href="javaScript:void(0);" id="journalsheet-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                    </security:authorize>
                </tr>
    			<tr>    				
    				<td>核销日期:</td>
    				<td><input id="journalsheet-query-writeoffdate1" type="text" name="writeoffdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${firstday }"/> 到 <input id="journalsheet-query-writeoffdate2" type="text" name="writeoffdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today }"/></td>    				
    				<td>核销状态:</td>
    				<td>
    					<select name="iswriteoff" id="journalsheet-query-iswriteoff" style="width:180px;">
    						<option value="">全部</option>
    						<option value="1">核销</option>
    						<option value="0">未核销</option>
    					</select>
    				</td>
                     </tr>
    			<tr>
                    <td>业务日期:</td>
                    <td><input id="journalsheet-query-businessdate1" type="text" name="begintime" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/> 到 <input id="journalsheet-query-businessdate2" type="text" name="endtime" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/></td>
                    <td>小计列:</td>
                    <td>
                        <input type="checkbox" class="costsgroupcols checkbox1" value="supplierid"/>
                        <label class="divtext">供应商</label>
                        <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                    </td>
    			</tr>
                <tr>
                    <td>供应商:</td>
                    <td><input id="journalsheet-query-supplierid" type="text" name="supplierid" style="width: 225px;"/></td>
                    <td colspan="2"></td>
                    <td rowspan="3" colspan="2" class="tdbutton">
                        <a href="javaScript:void(0);" id="journalsheet-query-buton" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="journalsheet-reload-buton" class="button-qr">重置</a>

                    </td>
                </tr>
    		</table>
    	</form>
    </div>
    <table id="journalsheet-table-matcostsReimburseWriteoffReport"></table>
    <div style="display:none">
    	<div id="journalsheet-div-matcostsReimburseWriteoffReport-detail" ></div>
    </div>
    <a href="javaScript:void(0);" id="matcostsInput-buttons-exportclick" style="display: none"title="导出">导出</a>
    <script type="text/javascript">

		var footerobject=null;
    	$(function(){
    		var columnJson = $("#journalsheet-table-matcostsReimburseWriteoffReport").createGridColumnLoad({
				frozenCol : [[
			    			]],
				commonCol : [[
							  {field:'id',title:'编码',width:125,sortable:true},
				     		  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'supplierid',title:'供应商编号',width:80,sortable:true},
							  {field:'suppliername',title:'供应商名称',width:150,sortable:true},
							  {field:'supplierdeptid',title:'部门名称',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.supplierdeptname;
					        	}
							  },
							  {field:'reimburseamount',title:'收回金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'iswriteoff',title:'核销',resizable:true,sortable:true,hidden:true,
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
										if((rowData.iswriteoff=='1' || rowData.iswriteoff=='2') && val!=null){
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
			$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid({ 
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
				toolbar:'#journalsheet-query-matcostsReimburseWriteoffReport',
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
		  		width:225,
				name:'t_js_matcostsinput',
				col:'supplierid',
				singleSelect:true,
				view:true
			});
			$("#journalsheet-query-buton").click(function(){
				reloadColumn();
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#journalsheet-query-form-matcostsReimburseWriteoffReport").serializeJSON();
	      		$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid({
	      			url: 'journalsheet/matcostsInput/getMatcostsReimburseWriteoffReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#journalsheet-reload-buton").click(function(){
				$("#journalsheet-query-form-matcostsReimburseWriteoffReport")[0].reset();
				$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('loadData',{total:0,rows:[]});
				$("#journalsheet-query-supplierid").supplierWidget('clear');
			});
			$("#journalsheet-export-buton").click(function(){
				var groupcols=$("#report-query-groupcols").val()||"";
				var title="收回核销情况报表";
				if("supplierid"==groupcols){
					title=title+"-按供应商小计";
				}
				$("#matcostsInput-buttons-exportclick").Excel('export',{
					queryForm: "#journalsheet-query-form-matcostsReimburseWriteoffReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/matcostsInput/exportMatcostsReimburseWriteoffReportData.do'
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
				$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('hideColumn', "id");
				$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('hideColumn', "businessdate");
				//$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('hideColumn', "iswriteoff");
				$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('hideColumn', "writeoffdate");
				$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('hideColumn', "writeoffer");
			}
			else{
        		$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('showColumn', "id");
				$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('showColumn', "businessdate");
				//$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('showColumn', "iswriteoff");
				$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('showColumn', "writeoffdate");
				$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('showColumn', "writeoffer");
			}

			var iswriteoff=$("#journalsheet-query-iswriteoff").val();
			if("0"==iswriteoff){
				<c:forEach items="${reimbursetypeList }" var="list">
					$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('hideColumn', "reimburse_${list.code}");
				</c:forEach>
				$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('hideColumn', "writeoffdate");
				$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('hideColumn', "writeoffer");
				
			}else{
				<c:forEach items="${reimbursetypeList }" var="list">
					$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('showColumn', "reimburse_${list.code}");
				</c:forEach>
			}
    	}
    	function writeoffCountTotalAmount(){
    		var rows =  $("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid('getChecked');
    		if(null==rows || rows.length==0){
           		var foot=[];
    			if(null!=footerobject){
	        		foot.push(emptyChooseObjectFoot());
	        		foot.push(footerobject);
	    		}
    			$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid("reloadFooter",foot);
           		return false;
       		}    		
    		var reimburseamount = 0;
    		var writeoffamount = 0;

    		var unwriteoffamount = 0;
    		
    		<c:forEach items="${reimbursetypeList }" var="list">
    			var reimburse_${list.code}=0;
    		</c:forEach>
    		
    		for(var i=0;i<rows.length;i++){
    			
    			reimburseamount = Number(reimburseamount)+Number(rows[i].reimburseamount == undefined ? 0 : rows[i].reimburseamount);
    			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
    			unwriteoffamount = Number(unwriteoffamount)+Number(rows[i].unwriteoffamount == undefined ? 0 : rows[i].unwriteoffamount);
    			
        		<c:forEach items="${reimbursetypeList }" var="list">
        			reimburse_${list.code} = Number(reimburse_${list.code})+Number(rows[i].reimburse_${list.code} == undefined ? 0 : rows[i].reimburse_${list.code});
        		</c:forEach>
        		
    		}
    		var foot=[{suppliername:'选中金额',
        				reimburseamount:reimburseamount,writeoffamount:writeoffamount,unwriteoffamount:unwriteoffamount
        				
        				<c:forEach items="${reimbursetypeList }" var="list">
							,reimburse_${list.code} : reimburse_${list.code}
						</c:forEach>
						
        			}];
    		if(null!=footerobject){
        		foot.push(footerobject);
    		}
    		$("#journalsheet-table-matcostsReimburseWriteoffReport").datagrid("reloadFooter",foot);
    	}
    	function emptyChooseObjectFoot(){
    		var reimburseamount = 0;
    		var writeoffamount=0;
    		var unwriteoffamount=0;
    		<c:forEach items="${reimbursetypeList }" var="list">
    			var reimburse_${list.code}=0;
    		</c:forEach>
    		var foot={suppliername:'选中金额',
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
