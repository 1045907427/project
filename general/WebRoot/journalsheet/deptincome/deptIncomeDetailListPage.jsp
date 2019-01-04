<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>收入录入列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="deptIncome-buttons-detaillist"></div>
    </div>
    <div data-options="region:'center'">
        <table id="deptIncomeListPage-table-detail"></table>
        <div id="deptIncomeListPage-form-div" style="padding:2px;height:auto">
            <form action="" id="deptIncomeListPage-form-ListQuery" method="post">
                <table class="querytable">
                    <tr>
                        <td>业务日期：</td>
                        <td>
                        	<input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today1 }" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today2 }"/>
                        </td>
                        <td>开单日期：</td>
                        <td>
                        	<input type="text" id="deptIncomeListPage-form-billtime" name="billtime" style="width:150px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="" />
                        </td>
                        <td>收入科目：</td>
                        <td>
                            <input id="deptIncomeListPage-form-costsort" name="costsortlike" type="text" style="width: 150px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>供应商:</td>
                        <td>
                        	<input id="deptIncomeListPage-form-supplier" type="text" name="supplierid" style="width: 190px;"/>
                        	<label style="display: inline-block; vertical-align: middle;  ">
                        		<input type="checkbox" id="deptIncomeListPage-form-supplier-empty" name="emptysupplier" value="1" style="margin:0 3px;vertical-align: middle; "/><span style="cursor:pointer;">选择空</span>
                        	</label>
                        </td>
                        <td>所属部门：</td>
                        <td>
                        	<input id="deptIncomeListPage-form-deptid" name="deptid" type="text" style="width: 150px;"/>
                        </td>
                        <td>品牌：</td>
                        <td>
                        	<input id="deptIncomeListPage-form-brandid" type="text" name="brandid" style="width: 150px;"/>
                        </td> 
                    </tr>
                    <tr>                       
                        <td>状态：</td>
                        <td>
                            <select name="status" style="width: 225px;">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">审核通过</option>
                                <option value="4">关闭</option>
                            </select>
                        </td>
                        <td>打印状态:</td>
	    				<td>
	    					<select name="printsign" style="width:150px;">
								<option></option>
								<option value="1">未打印</option>
								<%-- 特别
								<option value="2">小于</option>
								<option value="3">小于等于</option>
								 --%>
								<option value="4">已打印</option>
								<%-- 特别
								<option value="5">大于等于</option>
								 --%>												
							</select>
							<input type="hidden" name="queryprinttimes" value="0"/>
	    				</td>                  	
                        <td colspan="2" align="right">
                            <div>
                                <a href="javaScript:void(0);" id="departmentCosts-query-List" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="departmentCosts-query-reloadList" class="button-qr">重置</a>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div id="deptIncome-dialog-detail1"></div>
<div id="deptIncome-dialog-settlement"></div>
<script type="text/javascript">
//根据初始的列与用户保存的列生成以及字段权限生成新的列
var tableColJson=$("#deptIncomeListPage-table-detail").createGridColumnLoad({
    name:'t_js_dept_income',
    frozenCol:[[
        {field:'ck',checkbox:true,isShow:true}
    ]],
    commonCol:[[
        {field:'id',title:'编码',width:130,sortable:true},
        {field:'biltime',title:'开单日期',width:80,isShow:true,
            formatter:function(val,rowData,rowIndex){
            	if(rowData.addtime!=null){
            		var tmpArr=rowData.addtime.split(' ');
            		if(tmpArr.length>0){
            			return tmpArr[0];
            		}
            		return "";
            	}
            }
        },
        {field:'businessdate',title:'业务日期',width:80,sortable:true},
        // {field:'oaid',title:'OA编号',width:80,sortable:true},
        {field:'supplierid',title:'供应商编码',width:70},
    	{field:'suppliername',title:'供应商名称',width:100,isShow:true},
        {field:'deptid',title:'所属部门',width:70,sortable:true,
            formatter:function(val,rowData,rowIndex){
                return rowData.deptname;
            }
        },
        {field:'costsort',title:'收入科目',width:100,sortable:true,
            formatter:function(val,rowData,rowIndex){
                return rowData.costsortname;
            }
        },
        {field:'brandid',title:'品牌名称',width:60,sortable:true,
            formatter:function(val,rowData,rowIndex){
                return rowData.brandname;
            }
        },
        {field:'unitnum',title:'数量',width:80,sortable:true,align:'right',
            formatter:function(val,rowData,rowIndex){
                if(val != "" && val != null){
                    return formatterBigNumNoLen(val);
                }
                else{
                    return "0.00";
                }
            }
        },
        {field:'taxprice',title:'单价',width:80,sortable:true,align:'right',
            formatter:function(val,rowData,rowIndex){
                if(val != "" && val != null){
                    return formatterMoney(val,4);
                }
                else{
                    return "0.0000";
                }
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
        {field:'status',title:'状态',width:70,sortable:true,
            formatter:function(val,rowData,rowIndex){
                if(val=='2'){
                    return "保存";
                }else if(val=='3'){
                    return "审核通过";
                }else if(val=='4'){
                    return "关闭";
                }
            }
        },
        {field:'addusername',title:'制单人',width:80},
        {field:'addtime',title:'制单时间',width:130,sortable:true},
        {field:'modifyusername',title:'修改人',width:80,hidden:true},
        {field:'modifytime',title:'修改时间',width:130,hidden:true},
        {field:'auditusername',title:'审核人',width:80,sortable:true},
        {field:'audittime',title:'审核时间',width:130,sortable:true},
        {field:'remark',title:'备注',width:100},
		{field:'printstate',title:'打印状态',width:80,isShow:true,
	  		formatter:function(value,rowData,index){
    			if(rowData.printtimes>0){
    				return "已打印";
    			}else if(rowData.printtimes==null){
    				return "";
    			}else if(rowData.printtimes==-99){
    				return "";
    			}else{
    				return "未打印";
    			}
        	}
	    },
		{field:'printtimes',title:'打印次数',width:80,hidden:true}
    ]]
});
$("#deptIncome-buttons-detaillist").buttonWidget({
    initButton:[
        {},
        <security:authorize url="/journalsheet/deptincome/deptIncomeAddBtn.do">
        {
            type: 'button-add',
            handler: function(){
                $('<div id="deptIncome-dialog-detail"></div>').appendTo('#deptIncome-dialog-detail1');
                $('#deptIncome-dialog-detail').dialog({
                    title: '收入录入【新增】',
                    width: 750,
                    height: 300,
                    collapsible:false,
                    minimizable:false,
                    maximizable:true,
                    resizable:true,
                    closed: true,
                    cache: false,
                    href: 'journalsheet/deptincome/showDeptIncomeAddPage.do',
                    modal: true,
                    onLoad:function(){
                        $("#deptIncome-detail-supplier").focus();
                    },
                    onClose:function(){
                        $('#deptIncome-dialog-detail').dialog("destroy");
                    }
                });
                $('#deptIncome-dialog-detail').dialog("open");
            }
        },
        </security:authorize>
        <security:authorize url="/journalsheet/deptincome/deptIncomeViewBtn.do">
        {
            type: 'button-view',
            handler: function(){
                var con = $("#deptIncomeListPage-table-detail").datagrid('getSelected');
                if(con == null){
                    $.messager.alert("提醒","请选择一条记录");
                    return false;
                }
                $('<div id="deptIncome-dialog-detail"></div>').appendTo('#deptIncome-dialog-detail1');
                $('#deptIncome-dialog-detail').dialog({
                    title: '收入录入【详情】',
                    width: 750,
                    height: 300,
                    collapsible:false,
                    minimizable:false,
                    maximizable:true,
                    resizable:true,
                    closed: true,
                    cache: false,
                    href: 'journalsheet/deptincome/showDeptIncomeViewPage.do?id='+con.id,
                    modal: true,
                    onClose:function(){
                        $('#deptIncome-dialog-detail').dialog("destroy");
                    }
                });
                $('#deptIncome-dialog-detail').dialog("open");
            }
        },
        </security:authorize>
        <security:authorize url="/journalsheet/deptincome/deptIncomeEditBtn.do">
        {
            type: 'button-edit',
            handler: function(){
                var con = $("#deptIncomeListPage-table-detail").datagrid('getSelected');
                if(con == null){
                    $.messager.alert("提醒","请选择一条记录");
                    return false;
                }
                $('<div id="deptIncome-dialog-detail"></div>').appendTo('#deptIncome-dialog-detail1');
                $('#deptIncome-dialog-detail').dialog({
                    title: '收入录入【详情】',
                    width: 750,
                    height: 300,
                    collapsible:false,
                    minimizable:false,
                    maximizable:true,
                    resizable:true,
                    closed: true,
                    cache: false,
                    href: 'journalsheet/deptincome/showDeptIncomeInfoPage.do?id='+con.id,
                    modal: true,
                    onClose:function(){
                        $('#deptIncome-dialog-detail').dialog("destroy");
                    }
                });
                $('#deptIncome-dialog-detail').dialog("open");
            }
        },
        </security:authorize>
        <security:authorize url="/journalsheet/deptincome/deptIncomeDeleteBtn.do">
        {
            type: 'button-delete',
            handler: function(){
                $.messager.confirm("提醒","是否删除当前收入录入？",function(r){
                    if(r){
                        var rows = $("#deptIncomeListPage-table-detail").datagrid("getChecked");
                        var ids = "";
                        for(var i=0;i<rows.length;i++){
                            if(ids==""){
                                ids = rows[i].id;
                            }else{
                                ids += ","+ rows[i].id;
                            }
                        }
                        if(ids!=""){
                            loading("提交中..");
                            $.ajax({
                                url :'journalsheet/deptincome/deleteDeptIncome.do?ids='+ids,
                                type:'post',
                                dataType:'json',
                                success:function(json){
                                    loaded();
                                    if(json.flag){
                                        $.messager.alert("提醒", "删除成功</br>"+json.succssids+"</br>"+json.errorids);
                                        $("#deptIncomeListPage-table-detail").datagrid("reload");
                                    }else{
                                        $.messager.alert("提醒", "删除失败");
                                    }
                                },
                                error:function(){
                                    $.messager.alert("错误", "删除出错");
                                    loaded();
                                }
                            });
                        }
                    }
                });
            }
        },
        </security:authorize>
        <security:authorize url="/journalsheet/deptincome/deptIncomeAuditBtn.do">
        {
            type: 'button-audit',
            handler: function(){
                $.messager.confirm("提醒","是否审核选中的收入录入？",function(r){
                    if(r){
                        var rows = $("#deptIncomeListPage-table-detail").datagrid("getChecked");
                        var ids = "";
                        for(var i=0;i<rows.length;i++){
                            if(ids==""){
                                ids = rows[i].id;
                            }else{
                                ids += ","+ rows[i].id;
                            }
                        }
                        loading("审核中..");
                        $.ajax({
                            url :'journalsheet/deptincome/auditDeptIncome.do?ids='+ids,
                            type:'post',
                            dataType:'json',
                            success:function(json){
                                loaded();
                                if(json.flag){
                                    $.messager.alert("提醒", "审核成功</br>"+json.succssids+"</br>"+json.errorids);
                                    $("#deptIncomeListPage-table-detail").datagrid("reload");
                                }else{
                                    $.messager.alert("提醒", "审核失败");
                                }
                            },
                            error:function(){
                                $.messager.alert("错误", "审核出错");
                                loaded();
                            }
                        });
                    }
                });
            }
        },
        </security:authorize>
        <security:authorize url="/journalsheet/deptincome/deptIncomeOppauditBtn.do">
        {
            type:'button-oppaudit',
            handler:function(){
            	var rows = $("#deptIncomeListPage-table-detail").datagrid("getChecked");
                 if(rows.length == 0){
                 	$.messager.alert("提醒","请选择要反审的记录!");
                 	return false;
                 }
                $.messager.confirm("提醒","是否反审收入录入？",function(r){
                    if(r){
                        var ids = "";
                        for(var i=0;i<rows.length;i++){
                            if(ids==""){
                                ids = rows[i].id;
                            }else{
                                ids += ","+ rows[i].id;
                            }
                        }
                        if(ids!=""){
                        	loading("反审中..");
                            $.ajax({
                                url :'journalsheet/deptincome/oppauditDeptIncome.do?ids='+ids,
                                type:'post',
                                dataType:'json',
                                success:function(json){
                                    loaded();
                                    if(json.flag){
                                        $.messager.alert("提醒", "反审成功</br>"+json.succssids+"</br>"+json.errorids);
                                        $("#deptIncomeListPage-table-detail").datagrid("reload");
                                    }else{
                                        $.messager.alert("提醒", "反审失败");
                                    }
                                },
                                error:function(){
                                    $.messager.alert("错误", "反审出错");
                                    loaded();
                                }
                            });
                        }
                    }
                });
            }
        },
        </security:authorize>
        {}
    ],
    buttons:[
        <security:authorize url="/journalsheet/deptincome/deptIncomeUpdateSettleBtn.do">
        {
            id:'button-settlement',
            name:'结算',
            iconCls:'button-writeoff',
            handler:function(){
                $("#deptIncome-dialog-settlement").dialog({
                    title: '收入录入【结算】',
                    width: 400,
                    height: 250,
                    collapsible:false,
                    minimizable:false,
                    maximizable:false,
                    resizable:true,
                    closed: true,
                    cache: false,
                    href: 'journalsheet/deptincome/showDeptIncomeSettlePage.do',
                    modal: true
                });
                $("#deptIncome-dialog-settlement").dialog("open");
            }
        },
        </security:authorize>
		<security:authorize url="/journalsheet/deptincome/deptIncomePrintViewBtn.do">
		{
			id: 'button-printview-order',
			name:'打印预览',
			iconCls:'button-preview',
			handler: function(){
			}
		},
		</security:authorize>
		<security:authorize url="/journalsheet/deptincome/deptIncomePrintBtn.do">
		{
			id: 'button-print-order',
			name:'打印',
			iconCls:'button-print',
			handler: function(){
			}
		},
		</security:authorize>
		{}
    ],
    model: 'bill',
    type: 'list',
    tname: 't_js_dept_income'
});

var initQueryJSON =  $("#deptIncomeListPage-form-ListQuery").serializeJSON();
$(function(){
    $("#deptIncomeListPage-table-detail").datagrid({
        authority:tableColJson,
        frozenColumns:tableColJson.frozen,
        columns:tableColJson.common,
        fit:true,
        method:'post',
        showFooter: true,
        rownumbers:true,
        sortName:'id',
        sortOrder:'desc',
        pagination:true,
        idField:'id',
        singleSelect:false,
        checkOnSelect:true,
        selectOnCheck:true,
        pageSize:100,
        queryParams:initQueryJSON,
        toolbar:'#deptIncomeListPage-form-div',
        url: 'journalsheet/deptincome/showDeptIncomeList.do',
        onDblClickRow:function(rowIndex, rowData){
            $('<div id="deptIncome-dialog-detail"></div>').appendTo('#deptIncome-dialog-detail1');
            $('#deptIncome-dialog-detail').dialog({
                title: '收入录入【详情】',
                width: 750,
                height: 300,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'journalsheet/deptincome/showDeptIncomeInfoPage.do?id='+rowData.id,
                modal: true,
                onClose:function(){
                    $('#deptIncome-dialog-detail').dialog("destroy");
                }
            });
            $('#deptIncome-dialog-detail').dialog("open");
        },
        onLoadSuccess:function(){
            var footerrows = $(this).datagrid('getFooterRows');
            if(null!=footerrows && footerrows.length>0){
            	listFooterobject = footerrows[0];
            }
        },
        onCheckAll:function(){
            deptCostsCountTotalAmount();
        },
        onUncheckAll:function(){
            deptCostsCountTotalAmount();
        },
        onCheck:function(){
            deptCostsCountTotalAmount();
        },
        onUncheck:function(){
            deptCostsCountTotalAmount();
        }
    }).datagrid("columnMoving");
    
	$("#deptIncomeListPage-form-supplier").widget({ 
		referwid:'RL_T_BASE_BUY_SUPPLIER',
		width:190,
		singleSelect:true,
		onlyLeafCheck:true,
		onSelect:function(){
			$("#deptIncomeListPage-form-supplier-empty").prop({checked:false});
		}
	});
    $("#deptIncomeListPage-form-deptid").widget({
        referwid:'RT_T_SYS_DEPT',
        width:150,
        singleSelect:true,
        onlyLeafCheck:false
    });

    $("#deptIncomeListPage-form-costsort").widget({
        referwid:'RL_T_BASE_SUBJECT',
        width:150,
        singleSelect:true,
        onlyLeafCheck:false,
		treePName:false,
		treeNodeDataUseNocheck:true,
		param:[
  			{field:'typecode',op:'equal',value:'INCOME_SUBJECT'}
  		],
        onSelect:function(){
        }
    });
  //品牌
	$("#deptIncomeListPage-form-brandid").widget({
		referwid:'RL_T_BASE_GOODS_BRAND',
		singleSelect:true,
		width:150,
		onlyLeafCheck:true,
		onSelect:function(){
		}
	});

    $("#departmentCosts-query-List").click(function(){
        //把form表单的name序列化成JSON对象
        var queryJSON = $("#deptIncomeListPage-form-ListQuery").serializeJSON();
        $("#deptIncomeListPage-table-detail").datagrid("load",queryJSON);
    });

    $("#departmentCosts-query-reloadList").click(function(){
        $("#deptIncomeListPage-form-deptid").widget("clear");
        $("#deptIncomeListPage-form-costsort").widget("clear");
        $("#deptIncomeListPage-form-ListQuery")[0].reset();
        var queryJSON =  $("#deptIncomeListPage-form-ListQuery").serializeJSON();
        $("#deptIncomeListPage-table-detail").datagrid("load",queryJSON);
    });
    $("#deptIncomeListPage-form-supplier-empty").click(function(){
    	if($(this).prop("checked")){
    		$("#deptIncomeListPage-form-supplier").widget("clear");
    	}else{
    		$("#deptIncomeListPage-form-supplier").widget("clear"); 		
    	}
    });
});
var listFooterobject=null;
function deptCostsCountTotalAmount(){
	var rows =  $("#deptIncomeListPage-table-detail").datagrid('getChecked');
	if(null==rows || rows.length==0){
   		var foot=[];
		if(null!=listFooterobject){
    		foot.push({id:'选中金额',amount:0.00});
    		foot.push(listFooterobject);
		}
		$("#deptIncomeListPage-table-detail").datagrid("reloadFooter",foot);
   		return false;
	}
	var amount = 0;
	
	for(var i=0;i<rows.length;i++){
		amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);    		
	}
	var foot=[{id:'选中金额',amount:amount	}];
	if(null!=listFooterobject){
		foot.push(listFooterobject);
	}
	$("#deptIncomeListPage-table-detail").datagrid("reloadFooter",foot);
}

function computeAmountNumChange(type){
	if(type==null){
		return;
	}
	var unitnum = $("#deptIncome-detail-unitnum").val() || 0;
	var taxprice = $("#deptIncome-detail-taxprice").val() || 0;
	var amount = $("#deptIncome-detail-amount").val() || 0;
	
	
	$("#deptIncome-detail-unitnum").addClass("inputload");
	$("#deptIncome-detail-taxprice").addClass("inputload");
		$("#deptIncome-detail-amount").addClass("inputload");
		
		if(type=="1"){
			$("#deptIncome-detail-amount").removeClass("inputload");
		}else if(type=="2"){
			$("#deptIncome-detail-unitnum").removeClass("inputload");
		}else if(type=="3"){
		$("#deptIncome-detail-taxprice").removeClass("inputload");
		}
		
	$.ajax({   
        url :'journalsheet/deptincome/deptIncomeAmountNumChange.do',
        type:'post',
        data:{type:type,unitnum:unitnum,taxprice:taxprice,amount:amount},
        dataType:'json',
        async:false,
        success:function(json){
        	$("#deptIncome-detail-unitnum").val(json.unitnum);
			$("#deptIncome-detail-taxprice").val(json.taxprice);
  	  		$("#deptIncome-detail-amount").val(json.amount);
  	  		
      	  	$("#deptIncome-detail-unitnum").removeClass("inputload");
			$("#deptIncome-detail-taxprice").removeClass("inputload");
  	  		$("#deptIncome-detail-amount").removeClass("inputload");
        },
        error:function(){
        	$("#deptIncome-detail-unitnum").removeClass("inputload");
			$("#deptIncome-detail-taxprice").removeClass("inputload");
  	  		$("#deptIncome-detail-amount").removeClass("inputload");
        }
    });			
}
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-deptincome-dialog-print",
            code: "journalsheet_deptincome",
            tableId: "deptIncomeListPage-table-detail",
            url_preview: "print/journalsheet/deptIncomePrintView.do",
            url_print: "print/journalsheet/deptIncomePrint.do",
            btnPreview: "button-printview-order",
            btnPrint: "button-print-order",
            getData: function (tableId, printParam) {
                var data = $("#" + tableId).datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var idarray = [];
                var printArr = [];
                var errBDArr = [];
                var errSupplierArr = [];
                var errorArr = [];
                var ydprintArr = [];
                var printtip = false;
                var billtime = null;
                var supplier = null;
                for (var i = 0; i < data.length; i++) {
                    if (data[i].status != '3' && data[i].status != '4') {
                        errorArr.push(data[i].id);
                        continue;
                    }
                    var curbtime = "";
                    var cursupplier = "";
                    if (data[i].addtime != null) {
                        var tmpArr = data[i].addtime.split(' ');
                        if (tmpArr.length > 0) {
                            curbtime = tmpArr[0];
                        }
                    }
                    if (billtime == null) {
                        billtime = curbtime;
                    } else if (billtime != curbtime) {
                        errBDArr.push(data[i].id);
                        continue;
                    }
                    cursupplier = data[i].supplierid || "";
                    if (supplier == null) {
                        supplier = cursupplier;
                    } else if (supplier != cursupplier) {
                        errSupplierArr.push(data[i].id);
                        continue;
                    }
                    idarray.push(data[i].id);
                    if (data[i].printtimes > 0) {
                        ydprintArr.push(data[i].id);
                        printtip = true;
                    }
                    printArr.push(data[i]);
                }
                var msgArr = [];

                if (errorArr.length > 0 || errBDArr.length > 0 || errSupplierArr.length > 0) {
                    msgArr.push("抱歉，系统不能打印。问题如下：<br/>");
                    if (errorArr.length > 0) {
                        msgArr.push("保存状态下不能打印，单据号：");
                        msgArr.push(errorArr.join(","));
                        msgArr.push("<br/>");
                    }
                    if (errBDArr.length > 0) {
                        msgArr.push("开单日期不一致，单据号：");
                        msgArr.push(errBDArr.join(","));
                        msgArr.push("<br/>");
                    }
                    if (errSupplierArr.length > 0) {
                        msgArr.push("供应商不一致，单据号：");
                        msgArr.push(errSupplierArr.join(","));
                        msgArr.push("<br/>");
                    }
                    $.messager.alert("提醒", msgArr.join(''));
                    return false;
                }
                return data;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
