<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户应付费用明细页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div id="customercost-detail-table-payableBtn">
   			<form action="" id="customercost-detail-form-ListQuery-detail" method="post">
				<input type="hidden" name="reporttype" value="${param.reporttype }"/>
    			<table class="querytable">
    				<tr>
    					<td>业务日期：</td>
    					<td>
    						<input type="text" name="businessdate" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${param.begindate }" />
    						到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${param.enddate }"/>
    					</td>
    					<td>费用类别：</td>
    					<td>
    						<input id="customercost-detail-query-expensesort" type="text" name="expensesort" style="width: 130px;"/>
    					</td>
    					<td>OA编号：</td>
    					<td>
    						<input id="customercost-detail-query-oaid" type="text" name="oaid" style="width: 135px;"/>
    					</td>
    				</tr>
    				<tr>
    					<td>申请人：</td>
    					<td>
    						<input id="customercost-detail-query-applyuserid" type="text" name="applyuserid" style="width: 225px;"/>
    					</td>
                        <!--
    					<td>申请部门：</td>
    					<td>
    						<input id="customercost-detail-query-applydeptid" type="text" name="applydeptid" style="width: 130px;"/>
    					</td>
    					-->
    					<td>是否红冲：</td>
    					<td>
    						<select name="ishcflag" style="width:130px;">
	    						<option value="">全部</option>
	    						<option value="1">是</option>
	    						<option value="2">否</option>
	    					</select>
    					</td>
                        <td colspan="2" align="left">
                            <div>
                                <input type="hidden" name="customerid" value="${param.customerid }"/>
                                <input type="hidden" name="supplierid" value="${param.supplierid }"/>
                                <input type="hidden" name="branddeptid" value="${param.branddeptid }"/>
								 <input type="hidden" name="isPcustomer" value="${param.isPcustomer }"/>
                                <a href="javaScript:void(0);" id="customercost-detail-payable-query-List"  class="button-qr"  title="查询">查询</a>
                                <a href="javaScript:void(0);" id="customercost-detail-payable-query-reloadList"  class="button-qr" title="重置">重置</a>
                            </div>
                        </td>
    				</tr>
    			</table>
    		</form>
   		</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<table id="customercost-detail-table-payable"></table>
    	</div>
    	<a href="javaScript:void(0);" id="customercost-detail-buttons-exportclick" style="display: none"title="导出">导出</a>
    </div>
    <div id="customercost-detail-table-payable-detail"></div>
    <script type="text/javascript">
  //根据初始的列与用户保存的列生成以及字段权限生成新的列
	var footerobject = null;
	var initQueryJSON =  $("#customercost-detail-form-ListQuery-detail").serializeJSON();
     $(function(){
    	 var customercostdetailListColJson=$("#customercost-detail-table-payable").createGridColumnLoad({
    	     	frozenCol:[[]],
    	     	commonCol:[[
    	     	    {field:'ck',checkbox:true},
    				{field:'oaid',title:'OA编号',width:70,
                        formatter: function(value,row,index){

                            if(value != undefined 
                            		&& value != null 
                            		&& value != '' 
                            		&& value != '合计'
                                    && value != '选中金额') {

                                return '<a href="javascript:void(0);" onclick="viewOa(\'' + value + '\')">' + value + '</a>';
                            }

                            return value;
                        }
                    },
    				{field:'businessdate',title:'业务日期',width:80},
    				{field:'expensesortname',title:'费用类别',width:100,isShow:true},
    				{field:'supplierid',title:'供应商编码',width:70,isShow:true},
    				{field:'suppliername',title:'供应商名称',width:200,isShow:true},
					{field:'beginamount',title:'期初应付金额',width:100,align:'right',
						formatter:function(val,rowData,rowIndex){

							if(rowData.oaid == '合计' || rowData.oaid == '选中合计') {
								return '';
							}

							if(val != "" && val != null){
								return formatterMoney(val);
							}
							else{
								return "0.00";
							}
						}
					},
    				{field:'lendamount',title:'本期应付(借)',width:100,align:'right',
    					formatter:function(val,rowData,rowIndex){
    						if(val != "" && val != null){
    							return formatterMoney(val);
    						}
    						else{
    							return "";
    						}
    					}
    				},
    				{field:'payamount',title:'本期已付(贷)',width:100,align:'right',
    					formatter:function(val,rowData,rowIndex){
    						if(val != "" && val != null){
    							return formatterMoney(val);
    						}
    						else{
    							return "";
    						}
    					}
    				},
					{field:'endamount',title:'期末应付金额',width:100,align:'right',
						formatter:function(val,rowData,rowIndex){

							if(rowData.oaid == '合计' || rowData.oaid == '选中合计') {
								return '';
							}

							if(val != "" && val != null){
								return formatterMoney(val);
							}
							else{
								return "0.00";
							}
						}
					},
    				{field:'hcflag',title:'是否红冲',width:60,
    					formatter:function(val,rowData,rowIndex){
    						if(val=='0'){
    							return "否";
    						}else if(val=='1' || val=='2'){
    							return "是";
    						}
    					}
    				},
    				{field:'isbegin',title:'是否期初',width:60,
    					formatter:function(val,rowData,rowIndex){
    						if(val=='0'){
    							return "否";
    						}else if(val=='1'){
    							return "是";
    						}
    					}
    				},
    				{field:'remark',title:'备注',width:100,isShow:true},
    				{field:'relateoaid',title:'关联OA号',width:70,isShow:true,
                        formatter: function(value,row,index){

                            if(value != undefined 
                            		&& value != null 
                            		&& value != '' 
                            		&& value != '合计' 
                            		&& value != '选中金额'
                                    && value != '余额') {

                                return '<a href="javascript:void(0);" onclick="viewOa(\'' + value + '\')">' + value + '</a>';
                            }

                            return value;
                        }
                    },
    				{field:'applyusername',title:'申请人',width:80,isShow:true},
    				/*{field:'applydeptname',title:'申请部门',width:80,isShow:true},*/
    				{field:'addtime',title:'申请时间',width:130,isShow:true},
    				{field:'billtype',title:'单据类型',resizable:true,width:80,
    					formatter:function(val,rowData,rowIndex){
    						if(val=="1"){
    							return "借";
    						}else if(val=="2"){
    							return "贷";
    						}else if(val=="3"){
								return "借";
							}
    					}
    				},
    				{field:'sourcefrom',title:'数据来源',resizable:true,width:80,
    					formatter:function(val,rowData,rowIndex){
                            if(val=="0"){
                                return "手工录入";
                            }else if(val=="1"){
                                return "代垫";
                            }else if(val=="11"){
                                return "费用冲差支付单";
                            }else if(val=="12"){
                                return "通路单";
                            }else if(val=="13"){
                                return "客户费用申请单";
                            }else if(val=="14"){
                                return "客户费用批量支付申请单";
                            }else if(val=="15"){
                                return "客户费用申请单（账扣）";
                            }else if(val=="16"){
                                return "客户费用申请单（账扣）";
                            }else if(val=="17"){
                                return "品牌费用申请单（支付）";
                            }else if(val=="18"){
                                return "品牌费用申请单（支付）";
                            }else if(val=="19"){
                                return "费用冲差支付单";
                            }
    					}
    				},
    				{field:'billno',title:'来源单据号',resizable:true},
    				{field:'hcreferid',title:'红冲关联单据号',resizable:true}
    			]]
    	     });
    	 $("#customercost-detail-table-payable").datagrid({
				authority:customercostdetailListColJson,
	  	 		frozenColumns:[[]],
				columns:customercostdetailListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortOrder:'asc',
	  	 		sortName:'z.businessdate,z.addtime,z.id',
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
	  	 		checkOnSelect:true,
				selectOnCheck:true,
				pageSize:200,
				url:'journalsheet/customercost/showCustomerCostPayableDetailList.do',
				queryParams:initQueryJSON,
				toolbar:'#customercost-detail-table-payableBtn',
				onLoadSuccess: function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
						countTotalAmount();
					}
				},
		 		rowStyler:function(index,row){
		 			if(row.hcflag && row.hcflag=='1'){
		 				return "color:#f00";
		 			}else if(row.hcflag && row.hcflag=='2'){
		 				return "color:#00f";
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
    	 $("#customercost-detail-query-expensesort").widget({
    		 referwid:'RT_T_BASE_FINANCE_EXPENSES_SORT_1',
    		 width:130,
    		 singleSelect:true
    	 });
    	 $("#customercost-detail-query-applyuserid").widget({
    		 referwid:'RT_T_SYS_USER',
    		 width:225,
    		 singleSelect:true
    	 });
    	 $("#customercost-detail-query-applydeptid").widget({
    		 referwid:'RT_T_SYS_DEPT',
    		 width:130,
    		 onlyLeafCheck:false,
    		 singleSelect:true
    	 });
    	 $("#customercost-detail-payable-query-List").click(function(){
    		//高级查询
   			var queryJSON = $("#customercost-detail-form-ListQuery-detail").serializeJSON();
   			$("#customercost-detail-table-payable").datagrid('load', queryJSON);
    	 });
    	 $("#customercost-detail-payable-query-reloadList").click(function(){
    		 $("#customercost-detail-query-expensesort").widget("clear");
        	 $("#customercost-detail-query-applyuserid").widget("clear");
    		 $("#customercost-detail-form-ListQuery-detail").form("reset");
			 var queryJSON = $("#customercost-detail-form-ListQuery-detail").serializeJSON();
			 $("#customercost-detail-table-payable").datagrid('loadData',{total:0,rows:[]});
    	 });
     });
     function countTotalAmount(){
 		var rows =  $("#customercost-detail-table-payable").datagrid('getChecked');
 		var lendamount = 0;
 		var payamount = 0;
 		for(var i=0;i<rows.length;i++){
 			lendamount = Number(lendamount)+Number(rows[i].lendamount == undefined ? 0 : rows[i].lendamount);
 			payamount = Number(payamount)+Number(rows[i].payamount == undefined ? 0 : rows[i].payamount);
 		}
 		var footerrows = [{oaid:'选中合计',lendamount:lendamount,payamount:payamount}];
 		if(null != footerobject){
 			footerrows.push(footerobject);
 		}
 		$("#customercost-detail-table-payable").datagrid("reloadFooter",footerrows);
 	}

        function viewOa(id) {

            top.addTab('act/workViewPage.do?processid=' + id, '工作查看');
        }
    </script>
  </body>
</html>
