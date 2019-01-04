<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>日常费用弹出详细列表</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<table id="deptdailycost-table-detail"></table>
<div id="deptdailycost-form-div">
	<form action="" id="deptdailycost-form-ListQuery" method="post">
		<input type="hidden" name="deptid" value="${param.deptid }"/>
		<input type="hidden" name="businessdate1" value="${param.businessdate1 }"/>
		<input type="hidden" name="businessdate2" value="${param.businessdate2 }"/>
		<input type="hidden" name="costsortlike" value="${param.subjectid }"/>
		<input type="hidden" name="personnelid" value="${param.personnelid }"/>
		<input type="hidden" name="reporttype" value="${param.reporttype }"/>
		<input type="hidden" name="statusarr" value="3,4"/>
	</form>
</div>
<script type="text/javascript">
//根据初始的列与用户保存的列生成以及字段权限生成新的列
var costDetailTableColJson=$("#deptdailycost-table-detail").createGridColumnLoad({
    name:'t_js_dept_dailycost',
    frozenCol:[[
        {field:'ck',checkbox:true,isShow:true}
    ]],
    commonCol:[[
        {field: 'id', title: '编码', width: 130, sortable: true},
        {
            field: 'biltime', title: '开单日期', width: 80, isShow: true,
            formatter: function (val, rowData, rowIndex) {
                if (rowData.addtime != null) {
                    var tmpArr = rowData.addtime.split(' ');
                    if (tmpArr.length > 0) {
                        return tmpArr[0];
                    }
                    return "";
                }
            }
        },
        {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
        // {field:'oaid',title:'OA编号',width:80,sortable:true},
        {field: 'supplierid', title: '供应商编码', width: 70},
        {field: 'suppliername', title: '供应商名称', width: 100, isShow: true},
        {
            field: 'bankid', title: '银行名称', width: 100,
            formatter: function (value, rowData, rowIndex) {
                return rowData.bankname;
            }
        },
        {
            field: 'salesuser', title: '人员', hidden: true, width: 70, isShow: true,
            formatter: function (value, rowData, rowIndex) {
                return rowData.salesusername;
            }
        },
        {
            field: 'deptid', title: '所属部门', width: 100, sortable: true,
            formatter: function (val, rowData, rowIndex) {
                return rowData.deptname;
            }
        },
        {
            field: 'costsort', title: '费用科目', width: 100, sortable: true,
            formatter: function (val, rowData, rowIndex) {
                return rowData.costsortname;
            }
        },
        {
            field: 'brandid', title: '品牌名称', width: 60, sortable: true,
            formatter: function (val, rowData, rowIndex) {
                return rowData.brandname;
            }
        },
        {
            field: 'unitnum', title: '数量', width: 80, sortable: true, align: 'right',
            formatter: function (val, rowData, rowIndex) {
                if (val != "" && val != null) {
                    return formatterBigNumNoLen(val);
                }
                else {
                    return "0.00";
                }
            }
        },
        {
            field: 'taxprice', title: '单价', width: 80, sortable: true, align: 'right',
            formatter: function (val, rowData, rowIndex) {
                if (val != "" && val != null) {
                    return formatterMoney(val, 4);
                } else if (rowData.id == '合 计' || rowData.id == '选中合计') {
                    return "";
                }
                else {
                    return "0.0000";
                }
            }
        },
        {
            field: 'amount', title: '金额', width: 80, sortable: true, align: 'right',
            formatter: function (val, rowData, rowIndex) {
                if (val != "" && val != null) {
                    return formatterMoney(val);
                } else {
                    return "0.00";
                }
            }
        },
        {
            field: 'status', title: '状态', width: 70, sortable: true,
            formatter: function (val, rowData, rowIndex) {
                if (val == '2') {
                    return "保存";
                } else if (val == '3') {
                    return "审核通过";
                } else if (val == '4') {
                    return "关闭";
                }
            }
        },
        {field: 'addusername', title: '制单人', width: 80},
        {field: 'addtime', title: '制单时间', width: 130, sortable: true},
        {field:'remark',title:'备注',width:100}
    ]]
});
var reportDetailFooterobject=null;
function showDeptDailyCostDetailDataGrid(){
	var initQueryJSON =  $("#deptdailycost-form-ListQuery").serializeJSON();
	$("#deptdailycost-table-detail").datagrid({
        authority:costDetailTableColJson,
        frozenColumns:costDetailTableColJson.frozen,
        columns:costDetailTableColJson.common,
        fit:true,
        method:'post',
        showFooter: true,
        rownumbers:true,
        sortName:'costsort',
        sortOrder:'asc',
        pagination:true,
        idField:'id',
        singleSelect:false,
        checkOnSelect:true,
        selectOnCheck:true,
 		showFooter: true,
        pageSize:100,
        queryParams:initQueryJSON,
        //toolbar:'#deptdailycost-form-div',
        url: 'journalsheet/deptdailycost/showDeptDailyCostList.do',
        onLoadSuccess:function(){
            var footerrows = $(this).datagrid('getFooterRows');
            if(null!=footerrows && footerrows.length>0){
            	reportDetailFooterobject = footerrows[0];
            }
        },
        onCheckAll:function(){
            detailCountTotalAmount();
        },
        onUncheckAll:function(){
        	detailCountTotalAmount();
        },
        onCheck:function(){
        	detailCountTotalAmount();
        },
        onUncheck:function(){
        	detailCountTotalAmount();
        }
    }).datagrid("columnMoving");
}

function detailCountTotalAmount(){
	var rows =  $("#deptdailycost-table-detail").datagrid('getChecked');
	if(null==rows || rows.length==0){
   		var foot=[];
		if(null!=reportDetailFooterobject){
    		foot.push({id:'选中金额',amount:0.00});
    		foot.push(reportDetailFooterobject);
		}
		$("#deptdailycost-table-detail").datagrid("reloadFooter",foot);
   		return false;
	}
	var amount = 0;
	var unitnum=0;

	for(var i=0;i<rows.length;i++){
		amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
        unitnum= Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
	}
	var foot=[{id:'选中合计',amount:amount,unitnum:unitnum}];
	if(null!=reportDetailFooterobject){
		foot.push(reportDetailFooterobject);
	}
	$("#deptdailycost-table-detail").datagrid("reloadFooter",foot);
}
</script>
</body>
</html>
