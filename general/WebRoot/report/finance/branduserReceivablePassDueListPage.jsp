<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌业务员应收款基础超账期统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-baseReceivablePastDue"></table>
    	<div id="report-toolbar-baseReceivablePastDue" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/rcblBranduserReportExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-baseReceivablePastDuePage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
                <a href="javaScript:void(0);" id="report-setdays-baseReceivablePastDue" class="easyui-linkbutton" iconCls="button-intervalSet" plain="true">设置区间</a>
            </div>
    		<form action="" id="report-query-form-baseReceivablePastDue" method="post">
    		<table>
    			<tr>
    				<td>品牌业务员:</td>
    				<td class="tdinput"><input type="text" id="report-query-branduser" name="branduser"/></td>
                    <td>是否只显示超账:</td>
                    <td>
                        <select id="report-select-ispastdue" name="ispastdue" style="width: 40px">
                            <option value="0" selected="selected">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
    			</tr>
    			<tr>
                    <td>品 牌 部 门:</td>
                    <td><input type="text" id="report-query-branddept" name="branddept"/></td>
                    <td colspan="2"></td>
                    <td rowspan="3" colspan="2" class="tdbutton">
    					<input id="report-query-groupcols" type="hidden" name="groupcols" value="branduser"/>
    					<a href="javaScript:void(0);" id="report-queay-baseReceivablePastDue" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-baseReceivablePastDue" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-paymentdaysSet-dialog"></div>
    	<div id="report-baseReceivablePastDue-detail-dialog"></div>
    	<div id="report-branduserReceivablePastDue-detail-dialog"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-baseReceivablePastDue").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-baseReceivablePastDue").createGridColumnLoad({
    				frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
				    ]],
					commonCol : [[
					  {field:'branduser',title:'品牌业务员',sortable:true,width:80,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.brandusername;
			        	}
					  },
					  {field:'branddept',title:'品牌部门',sortable:true,width:80,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.branddeptname;
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
  						  {field:'returnamount',title:'退货金额',align:'right',resizable:true,sortable:true,width:100,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'returnrate',title:'退货率',align:'right',width:80,sortable:true,width:100,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
					  		if(value!=null && value!=""){
			        			return formatterMoney(value)+"%";
			        		}
			        	}
					  },
					  {field:'pushamount',title:'冲差金额',align:'right',resizable:true,sortable:true,width:100,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  }
					]]
    			});
    			$("#report-datagrid-baseReceivablePastDue").datagrid({ 
					authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-baseReceivablePastDue',
					onDblClickRow:function(rowIndex, rowData){
						var branduser = rowData.branduser;
						var brandusername = rowData.brandusername;
						var ispastdue = $("#report-select-ispastdue").val();
    					var url = 'report/finance/showBranduserReceivablePastDueDetailPage.do?branduser='+branduser+'&brandusername='+brandusername+'&ispastdue='+ispastdue;
						$("#report-branduserReceivablePastDue-detail-dialog").dialog({
							title:'按品牌业务员:['+rowData.brandusername+']统计',
				    		width:800,
				    		height:400,
				    		closed:true,
				    		modal:true,
				    		maximizable:true,
				    		cache:false,
				    		resizable:true,
				    		maximized:true,
						    href: url
						});
						$("#report-branduserReceivablePastDue-detail-dialog").dialog("open");
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
				});
				$("#report-query-branduser").widget({
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		    		width:150,
					singleSelect:false
				});
				$("#report-query-branddept").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:150,
					singleSelect:true
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-baseReceivablePastDue","report-reload-baseReceivablePastDue");
				
				//查询
				$("#report-queay-baseReceivablePastDue").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-baseReceivablePastDue").serializeJSON();
		      		$("#report-datagrid-baseReceivablePastDue").datagrid({
		      			url: 'report/finance/showBaseReceivablePassDueListData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-baseReceivablePastDue").click(function(){
					$("#report-query-branduser").widget("clear");
					$("#report-query-branddept").widget("clear");
					$("#report-query-form-baseReceivablePastDue")[0].reset();
		       		$("#report-datagrid-baseReceivablePastDue").datagrid('loadData',{total:0,rows:[],footer:[]});
				});
				
				$("#report-buttons-baseReceivablePastDuePage").Excel('export',{
					queryForm: "#report-query-form-baseReceivablePastDue", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'应收款超账期情况表',
			 		url:'report/finance/exportBaseReceivablePastDueData.do'
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
    		});

    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-baseReceivablePastDue").datagrid('getChecked');
    			var saleamount = 0;
        		var unpassamount = 0;
        		var totalpassamount=0;
        		var returnamount=0;
        		var pushamount = 0;
        		<c:forEach items="${list }" var="list">
				  var passamount${list.seq} = 0;
				</c:forEach>
        		for(var i=0;i<rows.length;i++){
        			saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
        			unpassamount = Number(unpassamount)+Number(rows[i].unpassamount == undefined ? 0 : rows[i].unpassamount);
        			totalpassamount = Number(totalpassamount)+Number(rows[i].totalpassamount == undefined ? 0 : rows[i].totalpassamount);
        			returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
        			pushamount = Number(pushamount)+Number(rows[i].pushamount == undefined ? 0 : rows[i].pushamount);
        			<c:forEach items="${list }" var="list">
            			passamount${list.seq} = Number(passamount${list.seq})+Number(rows[i].passamount${list.seq} == undefined ? 0 : rows[i].passamount${list.seq});
    				</c:forEach>
        		}
        		var foot=[{brandusername:'选中合计',unitname:'',saleamount:saleamount,unpassamount:unpassamount,totalpassamount:totalpassamount
        				<c:forEach items="${list }" var="list">
	    					,passamount${list.seq}:passamount${list.seq}
	    				</c:forEach>
        				,returnamount:returnamount,pushamount:pushamount
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-baseReceivablePastDue").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
