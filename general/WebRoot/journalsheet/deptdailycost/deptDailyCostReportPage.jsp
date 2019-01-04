<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门日常费用报表</title>
    <%@include file="/include.jsp" %>
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
  </head>
  <body>
   	<div id="deptDailyCost-query-reportList" style="padding:2px;height:auto">
    	<form action="" id="deptDailyCost-query-form-reportList" method="post">
    		<table>
                <tr>
                    <security:authorize url="/journalsheet/deptdailycost/exportDeptDailyCostReportDataBtn.do">
                        <a href="javaScript:void(0);" id="deptDailyCost-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                        <a href="javaScript:void(0);" id="deptDailyCost-export-excel" style="display:none">导出</a>
                    </security:authorize>
                </tr>
    			<tr>
    				<td>业务日期:</td>
    				<td><input id="deptDailyCost-query-businessdate1" type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday }"/> 到 <input id="deptDailyCost-query-businessdate2" type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/></td>
    				<td>部门名称:</td>
    				<td><input id="deptDailyCost-query-deptid" type="text" name="deptid" style="width: 100px;"/></td>
    				<td></td>
    			</tr>
    			<tr>
    				<td>费用科目:</td>
    				<td><input id="deptDailyCost-query-subjectid" type="text" name="subjectid" style="width: 100px;"/></td>    				
    				<td colspan="2"></td>
                    <td class="tdbutton">
    					<a href="javaScript:void(0);" id="deptDailyCost-query-buton" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="deptDailyCost-reload-buton" class="button-qr">重置</a>
    				</td>	    						
    			</tr>
				<tr>
					<td colspan="6">
						&nbsp;
						<a href="javaScript:void(0);" id="deptDailyCost-tree-buton-expand" class="button-qr" style="width:60px">展开</a>
						<a href="javaScript:void(0);" id="deptDailyCost-tree-buton-collapse" class="button-qr" style="width:60px;">折叠</a>
						<a href="javaScript:void(0);" id="deptDailyCost-tree-buton-expandAll" expand="true" class="button-qr" style="width:80px;padding-left:2px;letter-spacing: 2px;">全部展开</a>
						<a href="javaScript:void(0);" id="deptDailyCost-tree-buton-collapseAll" class="button-qr" style="width:80px;padding-left:2px;letter-spacing: 2px">全部折叠</a>
					</td>
				</tr>
    		</table>
    	</form>
    </div>
    <table id="deptDailyCost-table-reportList"></table>
    <div class="easyui-menu" id="deptDailyCost-contextMenu" style="display: none;">
    	<div id="deptDailyCost-contextMenu-export">导出</div>
    	<div id="deptDailyCost-contextMenu-expand">展开</div>
		<div id="deptDailyCost-contextMenu-collapse">折叠</div>
		<div id="deptDailyCost-contextMenu-detail">查看明细</div>
    </div>
    <div style="display:none">
    	<div id="deptDailyCost-reportDetailList-div"></div>
    </div>
    <script type="text/javascript">
		var footerobject=null;
    	$(function(){
    		var queryInitJSON = $("#deptDailyCost-query-form-reportList").serializeJSON();
			$("#deptDailyCost-table-reportList").treegrid({ 
				columns:[[
						  {field:'deptidno',title:'部门编号',width:80,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
									return rowData.deptid;
									}
							  },
							  {field:'name',title:'部门/科目名称',width:200,sortable:true},
							  {field:'subamount',title:'科目金额',resizable:true,sortable:true,align:'right',isShow:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'totalamount',title:'金额',resizable:true,sortable:true,align:'right',isShow:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
				             ]],
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		idField:'rid',
		 		treeField:'name',
		 		singleSelect:true,
		 		showFooter: true,
		 		url: 'journalsheet/deptdailycost/showDeptDailyCostReportData.do',
		 		queryParams:queryInitJSON,
				toolbar:'#deptDailyCost-query-reportList',
				onDblClickRow:function(row){
					//$("#deptDailyCost-table-reportList").treegrid("expand",row.rid);
					showDeptDailyCostReportDetailList(row);
				},
				onContextMenu: function(e, rowData){
    				e.preventDefault();
    				$(this).treegrid('select', rowData.rid);
                    $("#deptDailyCost-contextMenu").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
			});
			$("#deptDailyCost-query-deptid").widget({
				width:200,
				name:'t_js_dept_dailycost',
				col:'deptid',
				singleSelect:true,
				onlyLeafCheck:false
			});
			$("#deptDailyCost-query-subjectid").widget({
    			referwid:'RL_T_JS_DEPARTMENTCOSTS_SUBJECT',
    			width:225,
				singleSelect:true,
				onlyLeafCheck:false
    		});
			$("#deptDailyCost-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#deptDailyCost-query-form-reportList").serializeJSON();
	      		$("#deptDailyCost-table-reportList").treegrid('load', queryJSON);
			});
			$("#deptDailyCost-reload-buton").click(function(){
				$("#deptDailyCost-query-form-reportList")[0].reset();
				$("#deptDailyCost-table-reportList").treegrid('loadData',[]);
				$("#deptDailyCost-query-deptid").widget('clear');
				$("#deptDailyCost-query-subjectid").widget('clear');
			});
			$("#deptDailyCost-contextMenu-export").click(function(){
				var row = $("#deptDailyCost-table-reportList").treegrid('getSelected');
				if(row.type=='1'){
					var sdeptid = row.id;
					var date1=$("#deptDailyCost-query-businessdate1").val()||"";
					var date2=$("#deptDailyCost-query-businessdate2").val()||"";
					var deptname = row.name ;
					var title="";
					title=date1+(date1!=""&&date2!=""?"至":"")+date2;
					title=title+$.trim(deptname)+"部门日常费用报表";
					$("#deptDailyCost-export-excel").Excel('export',{
						queryForm: "#deptDailyCost-query-form-reportList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
				 		type:'exportUserdefined',
				 		name:title,
				 		url:'journalsheet/deptdailycost/exportDeptDailyCostReportData.do?sdeptid='+sdeptid
					});
					$("#deptDailyCost-export-excel").trigger("click");
				}else if(row.type=='2'){
					$.messager.alert("提醒","请选择部门导出。");
				}
			});
			$("#deptDailyCost-contextMenu-expand").click(function(){
				var row = $("#deptDailyCost-table-reportList").treegrid('getSelected');
				$("#deptDailyCost-table-reportList").treegrid("expandAll",row.rid);
			});
			$("#deptDailyCost-contextMenu-collapse").click(function(){
				var row = $("#deptDailyCost-table-reportList").treegrid('getSelected');
				$("#deptDailyCost-table-reportList").treegrid("collapseAll",row.rid);
			});
			$("#deptDailyCost-contextMenu-detail").click(function(){
                var row = $("#deptDailyCost-table-reportList").treegrid('getSelected');
                showDeptDailyCostReportDetailList(row);
			});
			$("#deptDailyCost-export-buton").click(function(){
				var date1=$("#deptDailyCost-query-businessdate1").val()||"";
				var date2=$("#deptDailyCost-query-businessdate2").val()||"";
				var deptname=$("#deptDailyCost-query-deptid").widget("getText")|| "" ;
				var title="";
				title=date1+(date1!=""&&date2!=""?"至":"")+date2;
				title=title+$.trim(deptname)+"部门日常费用报表";
				$("#deptDailyCost-export-excel").Excel('export',{
					queryForm: "#deptDailyCost-query-form-reportList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'journalsheet/deptdailycost/exportDeptDailyCostReportData.do'
				});
				$("#deptDailyCost-export-excel").trigger("click");
			});
			$("#deptDailyCost-tree-buton-expandAll").click(function(){
                $("#deptDailyCost-table-reportList").treegrid("expandAll");
			});
			$("#deptDailyCost-tree-buton-collapseAll").click(function(){
                $("#deptDailyCost-table-reportList").treegrid("collapseAll");
			});

            $("#deptDailyCost-tree-buton-expand").click(function(){
                var row = $("#deptDailyCost-table-reportList").treegrid('getSelected');
                if(row!=null && row.rid!=null) {
                    $("#deptDailyCost-table-reportList").treegrid("expandAll", row.rid);
                }
            });
            $("#deptDailyCost-tree-buton-collapse").click(function(){
                var row = $("#deptDailyCost-table-reportList").treegrid('getSelected');
                if(row!=null && row.rid!=null) {
                    $("#deptDailyCost-table-reportList").treegrid("collapseAll", row.rid);
                }
            });
    	});
    	function showDeptDailyCostReportDetailList(dataRow) {
    	    if(dataRow==null){
    	        return false;
			}
			var title="部门日常费用明细";
            var queryJSON = $("#deptDailyCost-query-form-reportList").serializeJSON();
            var deptid=dataRow.id || "";
            if(deptid==""){
                deptid = dataRow.deptid || "";
                title = title +"-"+(dataRow.name || "");
			}else{
                title = (dataRow.name || "")+" " + title;
			}
            var subjectid = dataRow.sid || "";
            var queryparam={
                businessdate1:(queryJSON.businessdate1 || ""),
                businessdate2:(queryJSON.businessdate2 || ""),
				deptid:deptid,
				subjectid:subjectid
			};
            $('<div id="deptDailyCost-reportDetailList-div-content"></div>').appendTo("#deptDailyCost-reportDetailList-div");
            $('#deptDailyCost-reportDetailList-div-content').dialog({
                title: title,
                fit:true,
                closed: true,
                cache: false,
                queryParams:queryparam,
                href: "journalsheet/deptdailycost/deptDailyCostReportDetailListPage.do",
                maximizable:true,
                resizable:true,
                modal: true,
                onLoad:function(){
                    showDeptDailyCostDetailDataGrid();
                },
                onClose:function(){
                    $('#deptDailyCost-reportDetailList-div-content').window("destroy");
                }
            });
            $('#deptDailyCost-reportDetailList-div-content').dialog('open');
        }
    </script>
  </body>
</html>
