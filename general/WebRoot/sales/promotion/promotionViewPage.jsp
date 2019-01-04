<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>促销活动查看页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-promotionAddPage" action="" method="post">
    		<input type="hidden" id="sales-addType-promotionAddPage" name="addType" />
    		<input type="hidden" id="sales-saveaudit-promotionAddPage" name="saveaudit" />
	    	<div data-options="region:'north',border:false" style="height:100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165">
	    					<input id="sales-promotionViewPage-id" class="len150 easyui-validatebox" name="promotionPackage.id" readonly="readonly" value="${promotionPackage.id}"
	    				 <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len150" readonly="readonly"   value="${promotionPackage.businessdate}" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
                            <select disabled="disabled" class="len150">
                                <c:forEach items="${statusList }" var="list">
                                    <c:choose>
                                        <c:when test="${list.code == promotionPackage.status}">
                                            <option value="${list.code }" selected="selected">${list.codename }</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${list.code }">${list.codename }</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
	    				</td>
	    			</tr>
	    			<tr>
	    			<td class="len80 left">客户群：</td>
	    			<td>
                        <input type="text" id="sales-customertype-promotionAddPage" name="promotionPackage.customertype" value="${promotionPackage.customertype}" disabled="disabled"/>
	    				</td>
	    				<td class="len80 left">客户群名称：</td>
	    				<td id="customertd"><input id="sales-customer-promotionAddPage" name="promotionPackage.customerid" value="${promotionPackage.customerid }" disabled="disabled"/></td>
                        <td class="len80 left">制单人：</td>
                        <td><input id="sales-applyuser-promotionAddPage"  class="len150" name="promotionPackage.addusername" value="${promotionPackage.addusername }" readonly/></td>

                    </tr>
	    			<tr>
	    				<td class="len80 left">生效日期：</td>
	    				<td><input class="len150 easyui-validatebox" readonly="readonly" name="promotionPackage.begindate"  value="${promotionPackage.begindate}"  /></td>
	    				<td class="len80 left">截止日期：</td>
	    				<td><input class="len150 easyui-validatebox" readonly="readonly" name="promotionPackage.enddate" value="${promotionPackage.enddate}" /></td>
                        <td class="len80 left">备注：</td>
                        <td colspan="3"><input type="text" name="promotionPackage.remark"  class="len150"  value="${promotionPackage.remark}" readonly /></td>
                    </tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="sales-datagrid-promotionAddPage"></table>
                <input type="hidden"  id="sales-promotion-activityType"  value="${act}"/>
                <input type="hidden" name="groupAndGoodsjson" id="sales-goodsJson-promotionAddPage"  value = '${goodsJson}'/>
	    	</div>
	    </form>
    </div>

    <script type="text/javascript">
        var rowInfo =JSON.parse('${goodsJson}');
        var rowlen = rowInfo.length;
        var type = $("#sales-promotion-activityType").val();
    	$(function(){
            $("#sales-buttons-promotionPage").buttonWidget("initButtonType", 'base');
            $("#sales-customertype-promotionAddPage").widget({
                name:'t_sales_promotion_package',
                col:'customertype',
                singleSelect:false,
                width:150
            });
            if(type == '1' || type == '3'){
                $("#sales-datagrid-promotionAddPage").datagrid({
                    authority:wareListJson,
                    columns: wareListJson.common,
                    frozenColumns: wareListJson.frozen,
                    border: true,
                    fit: true,
                    rownumbers: true,
                    showFooter: true,
                    striped:true,
                    singleSelect: true,
                    data: JSON.parse('${goodsJson}'),//数据
                    onLoadSuccess: function(data){

                        var rows = data.rows ;
                        var index =  "";
                        var groupid = "";
                        var pageJsonList = [];
                        for(var i=0;i<data.total;i++){//相同产品组进行单元格合并
                            if(groupid == ""){
                                mer_index = $("#sales-datagrid-promotionAddPage").datagrid('getRowIndex',rows[i]);
                                groupid = rows[i].groupid;
                                pageJsonList.push(rows[i]);
                            }else if(groupid == rows[i].groupid){
                                pageJsonList.push(rows[i]);
                            }else{
                                $("#sales-datagrid-promotionAddPage").datagrid('mergeCells',
                                        {index: mer_index,field: 'groupid',rowspan: pageJsonList.length });
                                --i;
                                groupid = "";
                                pageJsonList = [];
                            }
                        }
                        $("#sales-datagrid-promotionAddPage").datagrid('mergeCells',
                                {index: mer_index,field: 'groupid',rowspan: pageJsonList.length });

                        //var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');
                        if (rowlen < 20) {
                            for (var i = rowlen; i <20; i++) {
                                $("#sales-datagrid-promotionAddPage").datagrid('appendRow', {});
                            }
                        }
                    },
                onDblClickRow: function(rowIndex, rowData){
                groupDetailView(type);
    		}
    		}).datagrid('columnMoving');
            }else{
                $("#sales-datagrid-promotionAddPage").datagrid({
                    authority:bundleJson,
                    columns:bundleJson.common,
                    frozenColumns: bundleJson.frozen,
                    border: true,
                    fit: true,
                    rownumbers: true,
                    showFooter: true,
                    striped:true,
                    singleSelect: true,
                    data: JSON.parse('${goodsJson}'),//数据
                    onLoadSuccess:  function (data) {
                        var rows = data.rows ;
                        var index =  "";
                        var groupid = "";
                        var pageJsonList = [];
                        for(var i=0;i<data.total;i++){//相同产品组进行单元格合并
                            if(groupid == ""){
                                mer_index = $("#sales-datagrid-promotionAddPage").datagrid('getRowIndex',rows[i]);
                                groupid = rows[i].groupid;
                                pageJsonList.push(rows[i]);
                            }else if(groupid == rows[i].groupid){
                                pageJsonList.push(rows[i]);
                            }else{
                                $("#sales-datagrid-promotionAddPage").datagrid('mergeCells',
                                        {index: mer_index,field: 'groupid',rowspan: pageJsonList.length });
                                --i;
                                groupid = "";
                                pageJsonList = [];
                            }
                        }
                        $("#sales-datagrid-promotionAddPage").datagrid('mergeCells',
                                {index: mer_index,field: 'groupid',rowspan: pageJsonList.length });

                        //var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');
                        if (rowlen < 20) {
                            for (var i = rowlen; i <20; i++) {
                                $("#sales-datagrid-promotionAddPage").datagrid('appendRow', {});
                            }
                        }
                    },
                    onDblClickRow: function(rowIndex, rowData){
                        bundleView(rowData);
                    }
                }).datagrid('columnMoving');
            }
            $("#sales-savegoon-groupDetailEditPage").click(function(){
                editSaveGroupDetail(false);
            });


                $("#sales-applydept-promotionAddPage").widget({ //申请部门参照窗口
    			name:'t_sales_offprice',
    			col:'applydeptid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true,
    			onSelect:function(data){
    				$("#sales-applyuser-promotionAddPage").widget({ //申请人参照窗口
			    		name:'t_sales_offprice',
			    		col:'applyuserid',
			    		singleSelect:true,
			    		width:130,
			    		onlyLeafCheck:true,
			    		param:[{field:'pid',op:'equal',value:data.id}]
			    	});
    			}
    		});

            changeCustomerWidget("${promotionPackage.customertype}","${promotionPackage.customerid}","1");
            $("#sales-buttons-promotionPage").buttonWidget("setDataID", {id:'${promotionPackage.id}', state:'${promotionPackage.status}', type:'view'});
    	
    	});
    	  var $wareList = $("#sales-datagrid-promotionAddPage");
    </script>
    </body>
    </html>