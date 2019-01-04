<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商应付款流水明细表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
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
  <body>
    	<table id="report-datagrid-supplierPayFlow"></table>
    	<div id="report-toolbar-supplierPayFlow" style="padding:0px;height:auto">
            <div class="buttonBG">
                <security:authorize url="/report/finance/supplierPayFlowExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-supplierPayFlowPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-supplierPayFlow" method="post">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                    <td>供应商:</td>
                    <td><input type="text" id="report-query-supplier" name="supplierid" style="width: 150px;"/></td>
                </tr>
    			<tr>
    				<td>品牌名称:</td>
    				<td><input type="text" id="report-query-brand" name="brandid" style="width: 225px;"/></td>
                    <td>单据类型:</td>
                    <td>
                        <select name="billtype" style="width:150px;">
                            <option value=""></option>
                            <option value="1">采购入库单</option>
                            <option value="2">采购退货出库单</option>
                        </select>
                    </td>
    			</tr>
    			<tr>
                    <td>单据编号:</td>
                    <td><input type="text" name="id" style="width: 225px;"/></td>
                    <td colspan="2">
                        <input type="checkbox" name="invoice2" value="1" checked="checked" class="checkbox1" id="report-query-invoice2"/>
                        <label class="divtext" for="report-query-invoice2">已开票</label>
                        <input type="checkbox" name="writeoff2" value="1" checked="checked" class="checkbox1" id="report-query-writeoff2"/>
                        <label class="divtext" for="report-query-writeoff2">已核销</label>
                        <input type="checkbox" name="invoice1" value="1" checked="checked" class="checkbox1" id="report-query-invoice1"/>
                        <label class="divtext" for="report-query-invoice1">未开票</label>
                        <input type="checkbox" name="writeoff1" value="1" checked="checked" class="checkbox1" id="report-query-writeoff1"/>
                        <label class="divtext" for="report-query-writeoff1">未核销</label>
                    </td>
                    <td rowspan="3" colspan="2" class="tdbutton">
    					<a href="javaScript:void(0);" id="report-queay-supplierPayFlow" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-supplierPayFlow" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-supplierPayFlow").serializeJSON();
    		$(function(){
    			$("#report-datagrid-supplierPayFlow").datagrid({ 
					columns:[[
								  {field:'businessdate',title:'业务日期',width:100},
								  {field:'billtype',title:'单据类型',width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		if(value=='1'){
						        			return "采购入库单";
						        		}else if(value=='2'){
						        			return "采购退货出库单";
						        		}
						        	}
								  },
								  {field:'id',title:'单据编号',width:130},
								  {field:'supplierid',title:'供应商编码',width:70},
								  {field:'suppliername',title:'供应商名称',width:200,isShow:true},
								  {field:'goodsid',title:'商品编码',width:60},
								  {field:'goodsname',title:'商品名称',width:180},
								  {field:'barcode',title:'条形码',width:90},
								  {field:'brandid',title:'品牌',width:60,
									formatter:function(value,rowData,rowIndex){
								  		return rowData.brandname;
							  		}
								  },
								  {field:'unitname',title:'单位',width:40},
								  {field:'auxunitnumdetail',title:'辅数量',width:50,align:'right'},
								  {field:'unitnum',title:'数量',width:60,align:'right',
								  	formatter:function(value,rowData,rowIndex){
								  		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'price',title:'单价',width:60,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'taxamount',title:'金额',resizable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'isinvoice',title:'开票状态',align:'right',width:80,
								  	formatter:function(value,rowData,rowIndex){
								  		if(rowData.isinvoice!=null){
							        		if(rowData.isinvoice=='1'){
									  			return "已开票";
									  		}else if(rowData.isinvoice=='0'){
									  			return "未开票";
									  		}
								  		}
						        	}
								  },
								  {field:'iswriteoff',title:'核销状态',align:'right',width:80,isShow:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(rowData.iswriteoff!=null){
							        		if(rowData.iswriteoff=='1'){
									  			return "已核销";
									  		}else if(rowData.iswriteoff=='0'){
									  			return "未核销";
									  		}
								  		}
						        	}
								  },
								  {field:'remark',title:'备注',align:'right',width:80}
					         ]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-toolbar-supplierPayFlow'
				}).datagrid("columnMoving");
				$("#report-query-supplier").supplierWidget({
				});

		  		//品牌
		  		$("#report-query-brand").widget({
		   			referwid:'RL_T_BASE_GOODS_BRAND',
		   			singleSelect:false,
		   			width:225,
		   			onlyLeafCheck:false
		   		});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-supplierPayFlow","report-reload-supplierPayFlow");
				
				//查询
				$("#report-queay-supplierPayFlow").click(function(){
					//验证表单
					var flag = $("#report-query-form-supplierPayFlow").form('validate');
					if(flag==false){
						$("#report-query-customerid").focus();
						return false;
					}
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-supplierPayFlow").serializeJSON();
		      		$("#report-datagrid-supplierPayFlow").datagrid({
		      			url:'report/finance/showSupplierPaysFlowListData.do',
		      			pageNumber:1,
   						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-supplierPayFlow").click(function(){
					$("#report-query-customerid").supplierWidget("clear");
					$("#report-query-brand").widget("clear");
					$("#report-query-form-supplierPayFlow").form("reset");
		       		$("#report-datagrid-supplierPayFlow").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-supplierPayFlowPage").Excel('export',{
					queryForm: "#report-query-form-supplierPayFlow", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'供应商应付款流水明细表',
			 		url:'report/finance/exportSupplierPayFlowData.do'
				});
				
    		});
    	</script>
  </body>
</html>
