<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售单据核对页面</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<div id="account-datagrid-toolbar-billcheck" style="padding: 0px">
    <form action="" id="account-form-query-billcheck" method="post">
        <table class="querytable">
            <tr>
                <security:authorize url="/account/receivable/accountBillCheckImportBtn.do">
                    <a href="javaScript:void(0);" id="account-import-billcheck" class="easyui-linkbutton" iconCls="button-import" plain="true" title="导入">导入</a>
                </security:authorize>
                <%--<security:authorize url="/account/receivable/accountBillCheckExportBtn.do">--%>
                    <%--<a href="javaScript:void(0);" id="account-export-billcheck" class="easyui-linkbutton" iconCls="button-export" plain="true" title="明细导出">明细导出</a>--%>
                <%--</security:authorize>--%>
                <security:authorize url="/account/receivable/accountBillCheckAutoExportBtn.do">
                    <a href="javaScript:void(0);" id="account-export-autobillcheck" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
            </tr>
            <tr>
                <td>业务日期:</td>
                <td><input type="text" id="businessdate1" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/>
                    <input type="hidden" name="onedayflag" value="1"/>
                </td>
                <td>客户名称:</td>
                <td><input type="text" name="customerid" id="account-customerid-billcheck"/></td>
                <td>单据数:</td>
                <td><select name="billnums" class="len130">
                    <option></option>
                    <option value="0">等于0</option>
                    <option value="1" selected="selected">不等于0</option>
                </select></td>
                <td>是否显示冲差:</td>
                <td>
                    <select name="isPush" style="width: 125px">
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="4"></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="account-queay-billcheck" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="account-reload-billcheck" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<table id="account-datagrid-billcheck"></table>
<div id="account-dialog-billcheck"></div>
<script type="text/javascript">
    var SR_footerobject  = null;
    var intiQueryJSON = $("#account-form-query-billcheck").serializeJSON();
    $(function(){
        var tableColumnListJson = $("#account-datagrid-billcheck").createGridColumnLoad({
            frozenCol : [[{field:'idok',checkbox:true,isShow:true}]],
            commonCol : [[
                {field:'customerid',title:'客户编码',sortable:true,width:'60'},
                {field:'customername',title:'客户名称',width:210},
                {field:'businessdate',title:'业务日期',width:120},
                {field:'pcustomerid',title:'总店名称',sortable:true,width:60,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.pcustomername;
                    }
                },
                {field:'salesuserid',title:'客户业务员',sortable:true,width:70,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesusername;
                    }
                },
                {field:'customersort',title:'客户分类',sortable:true,width:60,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.customersortname;
                    }
                },
                {field:'salesarea',title:'销售区域',sortable:true,width:60,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesareaname;
                    }
                },
                {field:'salesdept',title:'销售部门',sortable:true,width:80,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesdeptname;
                    }
                },
                <c:if test="${map.salesamount == 'salesamount'}">
                {field:'salesamount',title:'销售金额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                </c:if>
                <c:if test="${map.billnums == 'billnums'}">
                {field:'billnums',title:'单据数',resizable:true,align:'right'},
                </c:if>
                {field:'inputsalesamount',title:'录入销售金额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'inputbillnums',title:'录入单据数',resizable:true,align:'right'},
                {field:'remark',title:'备注',width:'120',align:'right'}
            ]]
        });
        $("#account-datagrid-billcheck").datagrid({
            authority:tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns:tableColumnListJson.common,
            fit:true,
            method:'post',
            rownumbers:true,
            pagination:true,
            showFooter: true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            pageSize:100,
            rowStyler:function(index,row){
                if(undefined != row.eqflag && !row.eqflag){
                    return 'background-color:#DFF1D1';
                }
            },
            url: 'account/receivable/showSalesBillCheckList.do',
            queryParams:intiQueryJSON,
            toolbar:'#account-datagrid-toolbar-billcheck',
            onDblClickRow:function(rowIndex, rowData){
                var businessdate = $("#businessdate1").val();
                var url = 'account/receivable/showSalesBillCheckInfoPage.do?customerid='+rowData.customerid+'&businessdate='+businessdate;
                $("#account-dialog-billcheck").dialog({
                    title: '销售单据核对编辑',
                    width: 300,
                    height: 250,
                    closed: false,
                    cache: false,
                    href: url,
                    modal: true,
                    onLoad:function(data){
                        keydownNumberBox();
                    }
                });
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
        });
        //客户名称
        $("#account-customerid-billcheck").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            width:180,
            onlyLeafCheck:false,
            singleSelect:true
        });

        //回车事件
        controlQueryAndResetByKey("account-queay-billcheck","account-reload-billcheck");

        //查询
        $("#account-queay-billcheck").click(function(){
            var queryJSON = $("#account-form-query-billcheck").serializeJSON();
            $("#account-datagrid-billcheck").datagrid("load",queryJSON);
        });
        //重置
        $("#account-reload-billcheck").click(function(){
            $("#account-form-query-billcheck")[0].reset();
            $("#account-customerid-billcheck").widget('clear');
            var queryJSON = $("#account-form-query-billcheck").serializeJSON();
            $("#account-datagrid-billcheck").datagrid("load",queryJSON);
        });

        //导入
        $("#account-import-billcheck").Excel('import',{
            type:'importUserdefined',
            importparam:'客户编码、业务日期、录入销售金额、录入单据数必填',//参数描述
            url:'account/receivable/importSalesBillCheckList.do',
            onClose: function(){
                $("#account-datagrid-billcheck").datagrid('clearSelections');
                var queryJSON = $("#account-form-query-billcheck").serializeJSON();
                $("#account-datagrid-billcheck").datagrid("load",queryJSON);
            }
        });

        //明细导出
//        $("#account-export-billcheck").Excel('export',{
//            type:'exportUserdefined',
//            queryForm: "#account-form-query-billcheck", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
//            name:'销售单据核对',
//            url:'account/receivable/exportSalesBillCheckList.do'
//        });

        //全局导出
        $("#account-export-autobillcheck").click(function(){
            //封装查询条件
            var objecr  = $("#account-datagrid-billcheck").datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "account/receivable/autoExportSalesBillCheckList.do";
            exportByAnalyse(queryParam,"销售单据核对录入",'account-datagrid-billcheck',url);
        });

        $(document).keydown(function(event){
            switch(event.keyCode){
                case 27://Esc
                    $("#account-dialog-billcheck").dialog('close');
                    break;
            }
        });
    });

    function keydownNumberBox(){
        getNumberBox('account-salesamount-billcheck').focus();
        getNumberBox('account-salesamount-billcheck').select();

        getNumberBox("account-salesamount-billcheck").die("keydown").bind("keydown",function(event){
            if(event.keyCode==13){
                getNumberBox("account-salesamount-billcheck").blur();
                getNumberBox("account-billnums-billcheck").focus();
                getNumberBox("account-billnums-billcheck").select();
                event.stopPropagation();
            }
        });
        getNumberBox("account-billnums-billcheck").die("keydown").bind("keydown",function(event){
            if(event.keyCode==13){
                $("#account-savegoon-billcheck").focus();
                $("#account-savegoon-billcheck").click();
                event.stopPropagation();
            }
        });
    }

    function countTotalAmount(){
        var rows =  $("#account-datagrid-billcheck").datagrid('getChecked');
        var salesamount = 0;
        var billnums = 0;
        var inputsalesamount = 0;
        var inputbillnums = 0;

        for(var i=0;i<rows.length;i++){
            salesamount = Number(salesamount)+Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
            billnums = Number(billnums)+Number(rows[i].billnums == undefined ? 0 : rows[i].billnums);
            inputsalesamount = Number(inputsalesamount)+Number(rows[i].inputsalesamount == undefined ? 0 : rows[i].inputsalesamount);
            inputbillnums = Number(inputbillnums)+Number(rows[i].inputbillnums == undefined ? 0 : rows[i].inputbillnums);
        }
        var foot=[{customername:'选中合计',salesamount:salesamount,billnums:billnums,inputsalesamount:inputsalesamount,inputbillnums:inputbillnums}];
        if(null!=SR_footerobject){
            foot.push(SR_footerobject);
        }
        $("#account-datagrid-billcheck").datagrid("reloadFooter",foot);
    }
</script>
</body>
</html>
