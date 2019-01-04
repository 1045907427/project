<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>促销活动修改页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-promotionEditPage" action="sales/editPromotionGroup.do" method="post">
    		    		<input type="hidden" id="sales-addType-promotionAddPage" name="addType" />
    		<%--<input type="hidden" id="sales-saveaudit-promotionAddPage" name="saveaudit" />--%>
	    	<div data-options="region:'north',border:false" style="height:100px;">
	    		<table  border="0" cellpadding="5px" cellspacing="5px" style="border-collapse:collapse;">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165">
	    					<input id="sales-promotionViewPage-id" class="len150 easyui-validatebox" readonly="readonly" name="promotionPackage.id" value="${promotionPackage.id}" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len150" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${promotionPackage.businessdate}" name="promotionPackage.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
                            <select disabled="disabled" class="len150"  name ="status">
                                <c:forEach items="${statusList }" var="list">
                                    <c:choose>
                                        <c:when test="${list.code == promotionPackage.status}">
                                            <option value="${list.code }" selected="selected" >${list.codename }</option>
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
                            <input type="text" id="sales-customertype-promotionAddPage" name="promotionPackage.customertype" value="${promotionPackage.customertype}"/>
                        </td>
	    				<td class="len80 left">客户群名称：</td>
	    				<td id="customertd"><input id="sales-customer-promotionAddPage" value="${promotionPackage.customerid}" name="promotionPackage.customerid" /></td>
                        <td class="len80 left">制单人：</td>
                        <td><input id="sales-applyuser-promotionAddPage" class="len150" name="promotionPackage.addusername" value="${promotionPackage.addusername}" readonly="readonly" />
                        <input type="hidden" name="promotionPackage.adduserid" value="${promotionPackage.adduserid}"/>
                        <input type="hidden" name="promotionPackage.applyuserid" value="${promotionPackage.applyuserid}"/>
                        </td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">生效日期：</td>
	    				<td><input  id="sales-beginDate-promotionAddPage"  class="len150 easyui-validatebox" name="promotionPackage.begindate"  value="${promotionPackage.begindate}" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  /></td>
	    				<td class="len80 left">截止日期：</td>
	    				<td><input id="sales-endDate-promotionAddPage" class="len150 easyui-validatebox" name="promotionPackage.enddate" value="${promotionPackage.enddate}" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  /></td>
                        <td class="len80 left">备注：</td>
                        <td colspan="3"><input type="text" name="promotionPackage.remark" class="len150" value="${promotionPackage.remark}" /></td>
                        <input type="hidden" id="sales-promotion-activityType"  name="promotionPackage.ptype"  value="${act}"/>
                    </tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="sales-datagrid-promotionAddPage"></table>

	    		<input type="hidden" name="groupAndGoodsjson" id="sales-goodsJson-promotionAddPage" />
	    	</div>
	    </form>
    </div>
	    <div class="easyui-menu" id="sales-contextMenu-promotionAddPage" style="display: none;">
          <div id="sales-addRow-promotionAddPage" data-options="iconCls:'button-add'">添加</div>
         <div id="sales-editRow-promotionAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-promotionAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <script type="text/javascript">

     var type = $("#sales-promotion-activityType").val();
     var rowInfo =JSON.parse('${goodsJson}');
     var rowlen = rowInfo.length;

     var $wareList = $("#sales-datagrid-promotionAddPage");

    	$(function(){
            if(type == '1' || type == '3') {
                $("#sales-datagrid-promotionAddPage").datagrid({
                    authority: wareListJson,
                    columns: wareListJson.common,
                    frozenColumns: wareListJson.frozen,
                    border: true,
                    fit: true,
                    rownumbers: true,
                    showFooter: true,
                    striped: true,
                    showHeaderButton: false,
                    singleSelect: true,
                    data: JSON.parse('${goodsJson}'),//数据
                    onRowContextMenu: function(e, rowIndex, rowData){
                        e.preventDefault();

                        $wareList.datagrid('selectRow', rowIndex);

                        $("#sales-contextMenu-promotionAddPage").menu('show', {
                            left:e.pageX,
                            top:e.pageY
                        });

                    },
                    onSortColumn:function(sort, order){

                        var rows = $("#sales-datagrid-promotionAddPage").datagrid("getRows");
                        var dataArr = [];

                        for(var i=0;i<rowlen;i++){
                            if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                                dataArr.push(rows[i]);
                            }
                        }

                        dataArr.sort(function(a,b){

                            if($.isNumeric(a[sort])){
                                if(order=="asc"){
                                    return Number(a[sort])>Number(b[sort])?1:-1
                                }else{
                                    return Number(a[sort])<Number(b[sort])?1:-1
                                }
                            }else{
                                if(order=="asc"){
                                    return a[sort]>b[sort]?1:-1
                                }else{
                                    return a[sort]<b[sort]?1:-1
                                }
                            }
                        });

                        $("#sales-datagrid-promotionAddPage").datagrid("loadData",dataArr);

                        return false;
                    },
                    onLoadSuccess: function (data) {

                        var rows = data.rows ;
                        var index =  "";
                        var groupid = "";
                        var pageJsonList = [];

                        for(var i=0;i<data.total;i++){//相同产品组进行单元格合并
                            if(groupid == ""){
                               index = $("#sales-datagrid-promotionAddPage").datagrid('getRowIndex',rows[i]);
                                groupid = rows[i].groupid;
                                pageJsonList.push(rows[i]);
                            }else if(groupid == rows[i].groupid){
                                pageJsonList.push(rows[i]);
                            }else{
                                $("#sales-datagrid-promotionAddPage").datagrid('mergeCells',
                                 {index: index,field: 'groupid',rowspan: pageJsonList.length });

                                --i;
                                groupid = "";
                                pageJsonList = [];
                            }
                        }

                        $("#sales-datagrid-promotionAddPage").datagrid('mergeCells',
                                {index: index,field: 'groupid',rowspan: pageJsonList.length });

                        if (rowlen < 20) {
                            for (var i = rowlen; i <20; i++) {
                                $("#sales-datagrid-promotionAddPage").datagrid('appendRow', {});
                            }
                        }else{
                            for (var i = 0; i <5; i++) {
                                $("#sales-datagrid-promotionAddPage").datagrid('appendRow', {});
                            }
                        }
                    },
                    onDblClickRow: function (rowIndex, rowData) {

                        if (rowData.groupname == undefined) {
                            beginAddGroup1(type);

                        }else{
                            var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');
                            rowIndex = cursorIndex(rowIndex,rowData);
                            $wareList.datagrid('selectRow', rowIndex);
                            rowData = $wareList.datagrid('getSelected');

                            var count = -1;
                            var rowList = [];

                            for(var i=0;i<rows.length;i++){
                                if(rows[i].groupname == rowData.groupname){
                                    rowList.push(rows[i]);
                                    ++ count ;
                                }
                            }

                            beginEditGroup(rowIndex, rowList, type ,count);
                        }
                    }
                }).datagrid('columnMoving');

            }else{

                $("#sales-datagrid-promotionAddPage").datagrid({
                    authority: bundleJson,
                    columns: bundleJson.common,
                    frozenColumns:bundleJson.frozen,
                    border: true,
                    fit: true,
                    rownumbers: true,
                    showFooter: true,
                    striped: true,
                    showHeaderButton: false,
                    singleSelect: true,
                    data: JSON.parse('${goodsJson}'),
                    onRowContextMenu: function(e, rowIndex, rowData){
                        e.preventDefault();

                        cursorIndex(rowIndex,rowData);

                        $wareList.datagrid('selectRow', rowIndex);
                        $("#sales-contextMenu-promotionAddPage").menu('show', {
                            left:e.pageX,
                            top:e.pageY
                        });

                    },
                    onLoadSuccess: function (data) {

                        var rows = data.rows ;
                        var mer_index =  "";
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

                        if (rowlen < 20) {
                            for (var i = rowlen; i <20; i++) {
                                $("#sales-datagrid-promotionAddPage").datagrid('appendRow', {});
                            }
                        }else{
                            for (var i = 0; i <5; i++) {
                                $("#sales-datagrid-promotionAddPage").datagrid('appendRow', {});
                            }
                        }
                   },
                    onDblClickRow: function(rowIndex, rowData) {
                        rowIndex = cursorIndex(rowIndex,rowData);
                        $wareList.datagrid('selectRow', rowIndex);

                        if (rowData.groupname == undefined) {
                                beginAddGroup2(type,rowIndex);

                        }else{

                            var rows = $("#sales-datagrid-promotionAddPage").datagrid('getRows');
                            var count = 0;
                            var rowList = [];

                            for( var i = 0;i<rows.length ;i++){
                                if(rows[i].groupid == rowData.groupid){
                                    rowList.push(rows[i]);
                                    ++ count ;
                                }
                            }

                            beginEditGroup(rowIndex, rowList, type,count);
                        }
                    }
                }).datagrid('columnMoving');
            }

            $("#sales-removeRow-promotionAddPage").click(function(){
                removeDetail();
            });

    		$("#sales-addRow-promotionAddPage").click(function(){
    			beginAddRow();
    		});
            $("#sales-editRow-promotionAddPage").click(function(){
                editRow();
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

            /**
            *根据客户群返回对应的客户群名称
            */
            changeCustomerWidget("${promotionPackage.customertype}","${promotionPackage.customerid}","0");

            $("#sales-customertype-promotionAddPage").widget({
                name:'t_sales_promotion_package',
                col:'customertype',
                singleSelect:false,
                width:150,
                treePName:false,
                onSelect:function(data){
                    changeCustomerWidget(data.id,"","0");
                }
            });

			//提交表单  
           $("#sales-form-promotionEditPage").form({
            onSubmit:function(){
                var flag =$(this).form('validate');
                if(flag == false) return false;
                var customertype = $("#sales-customertype-promotionAddPage").widget('getValue');
                var customer = "";
                if("1" == customertype){
                   customer = $("#sales-customer-promotionAddPage").widget('getValue');
                }else if("2" == customertype){
                   customer = $("#sales-promotionsort-promotionAddPage").widget('getValue');
                }else if("3" == customertype){
                   customer = $("#sales-customersort-promotionAddPage").widget('getValue');
                }else if("4" == customertype){
                   customer = $("#sales-pricelist-promotionAddPage").widget('getValue');
                }else if("5" == customertype){
                   customer = $("#sales-salesarea-promotionAddPage").widget('getValue');
                }else if("6" == customertype){
                   customer = $("#sales-pcustomer-promotionAddPage").widget('getValue');
                }else if("7" == customertype){
                   customer = $("#sales-crenditrating-promotionAddPage").widget('getValue');
                }else if("8" == customertype){
                   customer = $("#sales-canceltype-promotionAddPage").widget('getValue');
                }else if("0" == customertype){

                }
                if(customer!=""){
                    var arr = customer.split(",");
                    if(arr.length > 30){
                        $.messager.alert("提醒","当前客户数"+arr.length+"个，一般不超过30个，请修改选中的客户数量");
                        return false ;
                    }
                }
                loading("提交中……");
            },
            success:function(data){
                loaded();
                var json = $.parseJSON(data);
                var act = $("#sales-promotion-activityType").val();
                if(json.flag == true){
                    if(json.audit == true){
                        $.messager.alert("提醒","保存并审核成功");
                    }else{
                        $.messager.alert("提醒","保存成功");
                    }

                    $("#sales-promotion-detailId").val();
                    $("#sales-panel-promotionPage").panel('refresh', 'sales/promotionEditPage.do?id='+ json.id+"&act="+act);

                }else{
                    if(json.msg){
                        $.messager.alert("提醒","保存失败!"+json.msg);
                    }else{
                        $.messager.alert("提醒","保存失败");
                    }
                }
            }
        });

        $("#sales-buttons-promotionPage").buttonWidget(
                "setDataID", {id:'${promotionPackage.id}', state:'${promotionPackage.status}', type:'edit'});
        $("#sales-buttons-promotionPage").buttonWidget("disableButton","button-unCancel");

    });


    </script>
    </body>
    </html>
