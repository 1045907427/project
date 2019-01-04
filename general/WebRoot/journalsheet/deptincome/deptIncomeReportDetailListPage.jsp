<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>收入弹出详细列表</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<table id="deptIncome-table-detail"></table>
<div id="deptIncome-form-div">
	<form action="" id="deptIncome-form-ListQuery" method="post">
		<input type="hidden" name="deptid" value="${param.deptid }"/>
		<input type="hidden" name="businessdate1" value="${param.businessdate1 }"/>
		<input type="hidden" name="businessdate2" value="${param.businessdate2 }"/>
		<input type="hidden" name="costsortlike" value="${param.subjectid }"/>
		<input type="hidden" name="statusarr" value="3,4"/>
	</form>
</div>
<script type="text/javascript">
//根据初始的列与用户保存的列生成以及字段权限生成新的列
var costDetailTableColJson=$("#deptIncome-table-detail").createGridColumnLoad({
    name:'t_js_dept_income',
    frozenCol:[[
        {field:'ck',checkbox:true,isShow:true}
    ]],
    commonCol:[[
        {field:'id',title:'编码',width:130,sortable:true},
        {field:'businessdate',title:'业务日期',width:80,sortable:true},
        // {field:'oaid',title:'OA编号',width:80,sortable:true},
        {field:'deptid',title:'所属部门',width:70,sortable:true,
            formatter:function(val,rowData,rowIndex){
                return rowData.deptname;
            }
        },
        {field:'costsort',title:'收入科目',width:160,sortable:true,
            formatter:function(val,rowData,rowIndex){
                return rowData.costsortname;
            }
        },
        {field:'amount',title:'金额',width:80,sortable:true,align:'right',
            formatter:function(val,rowData,rowIndex){
                if(val != "" && val != null){
                    return formatterMoney(val);
                }
                else{
                    return "0.00";
                }
            }
        },
        {field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
        {field:'addtime',title:'制单时间',width:130,sortable:true,hidden:true,
            formatter:function(val,rowData,rowIndex){
                if(val){
                    return val.replace(/[tT]/," ");
                }
            }
        },
        {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
        {field:'audittime',title:'审核时间',width:130,sortable:true,hidden:true},
        {field:'remark',title:'备注',width:100}
    ]]
});
var reportDetailFooterobject=null;
function showDeptIncomeDetailDataGrid(){
	var initQueryJSON =  $("#deptIncome-form-ListQuery").serializeJSON();
	$("#deptIncome-table-detail").datagrid({
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
        //toolbar:'#deptIncome-form-div',
        url: 'journalsheet/deptincome/showDeptIncomeList.do',
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
	var rows =  $("#deptIncome-table-detail").datagrid('getChecked');
	if(null==rows || rows.length==0){
   		var foot=[];
		if(null!=reportDetailFooterobject){
    		foot.push({id:'选中金额',amount:0.00});
    		foot.push(reportDetailFooterobject);
		}
		$("#deptIncome-table-detail").datagrid("reloadFooter",foot);
   		return false;
	}
	var amount = 0;
	
	for(var i=0;i<rows.length;i++){
		amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);    		
	}
	var foot=[{id:'选中金额',amount:amount	}];
	if(null!=reportDetailFooterobject){
		foot.push(reportDetailFooterobject);
	}
	$("#deptIncome-table-detail").datagrid("reloadFooter",foot);
}
</script>
</body>
</html>
