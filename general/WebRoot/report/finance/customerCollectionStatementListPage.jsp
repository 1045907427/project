<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>收款单列表页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
    <table id="report-datagrid-collectionOrderPage" data-options="border:false"></table>
    <div id="report-datagrid-toolbar-collectionOrderPage" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/account/receivable/exportCollectionOrderList.do">
                <a href="javaScript:void(0);" id="report-export-collectionOrder" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
        </div>
    	<form action="" id="report-form-query-collectionOrderPage" method="post">
    		<table class="querytable" style="padding-top: 0px">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/></td>
    				<td>编号:</td>
    				<td><input type="text" name="id" style="width: 140px;"/></td>
    				</tr>
    			<tr>
    				<td>银行:</td>
    				<td>
    					<select name="bank" style="width: 225px;">
    						<option></option>
    						<c:forEach var="list" items="${bankList}">
    							<option value="${list.id}">${list.name }</option>
    						</c:forEach>
    					</select>
    				</td>
                    <td>客户:</td>
                    <td><input id="report-query-customerid" type="text" name="customerid" style="width: 140px;"/></td>
                    <td colspan="2">
    					<input type="hidden" name="statement" value="1"/>
    					<a href="javaScript:void(0);" id="report-queay-collectionOrder" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-collectionOrder" class="button-qr">重置</a>
                    </td>
    			</tr>
    		</table>
    	</form>
    </div>
    <div id="report-datagrid-dialog-statmentpage"></div>
     <script type="text/javascript">
		var SR_footerobject  = null;
     	var initQueryJSON = $("#report-form-query-collectionOrderPage").serializeJSON();
    	$(function(){
			var tableColumnListJson = $("#report-datagrid-collectionOrderPage").createGridColumnLoad({
				name :'t_account_collection_order',
				frozenCol : [[
							{field:'idok',checkbox:true,isShow:true}
			    			]],
				commonCol : [[
							  {field:'id',title:'编号',width:130,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'customerid',title:'客户编码',width:60,sortable:true},
							  {field:'customername',title:'客户名称',width:150,isShow:true},
							  {field:'handlerid',title:'对方经手人',width:80,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.handlername;
					        	}
							  },
							  {field:'collectiondept',title:'收款部门',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.collectiondeptname;
					        	}
							  },
							  {field:'collectionuser',title:'收款人',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.collectionusername;
					        	}
							  },
							  {field:'amount',title:'收款金额',resizable:true,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'writeoffamount',title:'已核销金额',resizable:true,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'remainderamount',title:'未核销金额',resizable:true,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'bank',title:'银行名称',width:100,
							  	formatter:function(value,rowData,rowIndex){
							  		if(value!=null &&value!=""){
					        			return rowData.bankname;
					        		}
					        	}
							  },
							  {field:'addusername',title:'制单人',width:60,sortable:true},
							  {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
							  {field:'remark',title:'备注',width:80,sortable:true}
				             ]]
			});
			$("#report-datagrid-collectionOrderPage").datagrid({ 
		 		authority:tableColumnListJson,
		 		frozenColumns: tableColumnListJson.frozen,
				columns:tableColumnListJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'id',
		 		pageSize:100,
		 		sortOrder:'desc',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter:true,
				toolbar:'#report-datagrid-toolbar-collectionOrderPage',
				onDblClickRow:function(rowIndex, rowData){
					$("#report-datagrid-dialog-statmentpage").dialog({
					    title: '核销单据明细',  
			    		width:800,
			    		height:400,
			    		closed:false,
			    		modal:true,
			    		maximizable:true,
			    		cache:false,
			    		resizable:true,
					    href: 'account/receivable/showCollectionStatementDetailListPage.do?id='+rowData.id
					});
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
				name:'t_account_collection_order',
				col:'customerid',
    			singleSelect:true,
    			width:140,
    			onlyLeafCheck:false,
    			view:true
			});

			controlQueryAndResetByKey("report-queay-collectionOrder","report-reload-collectionOrder");
			
			//查询
			$("#report-queay-collectionOrder").click(function(){
	       		var queryJSON = $("#report-form-query-collectionOrderPage").serializeJSON();
	       		$("#report-datagrid-collectionOrderPage").datagrid({
	      			url: 'account/receivable/showCollectionOrderList.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		});
			});
			//重置
			$("#report-reload-collectionOrder").click(function(){
				$("#report-query-customerid").widget("clear");
				$("#report-form-query-collectionOrderPage")[0].reset();
	       		$("#report-datagrid-collectionOrderPage").datagrid('loadData',{total:0,rows:[]});
			});

            //导出
            $("#report-export-collectionOrder").Excel('export',{
                queryForm: "#report-form-query-collectionOrderPage",
                type:'exportUserdefined',
                name:'客户收款对账单',
                url:'account/receivable/exportCollectionOrderList.do'
            });
    	});
    	function countTotalAmount(){
			var rows =  $("#report-datagrid-collectionOrderPage").datagrid('getChecked');
			var amount = 0;
    		var writeoffamount = 0;
    		var remainderamount=0;
    		
    		for(var i=0;i<rows.length;i++){
    			amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
    			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
    			remainderamount = Number(remainderamount)+Number(rows[i].remainderamount == undefined ? 0 : rows[i].remainderamount);
    		}
    		var foot=[{businessdate:'选中合计',bankname:'',amount:amount,writeoffamount:writeoffamount,remainderamount:remainderamount
        			}];
    		if(null!=SR_footerobject){
        		foot.push(SR_footerobject);
    		}
    		$("#report-datagrid-collectionOrderPage").datagrid("reloadFooter",foot);
		}
    </script>
  </body>
</html>
