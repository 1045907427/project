<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>员工工龄汇总表</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
    <table id="personnel-datagrid-workyearreport"></table>
    <div id="personnel-toolbar-workyearreport">
    	<form action="" id="personnel-query-form-workyearreport" method="post">
    		<table cellpadding="0" cellspacing="0" border="0">
    			<tr>
    				<td>编号:</td>
    				<td><input type="text" name="id" style="width:100px" /></td>
    				<td>姓名:</td>
    				<td><input type="text" name="name" style="width:120px" /></td>
    				<td>所属部门:</td>
    				<td><input id="personnel-belongdeptid-workyearreport" type="text" name="belongdeptid" style="width:120px" /></td>
    			</tr>
    			<tr>
    				<td>入职年月:</td>
    				<td><input type="text" class="Wdate" name="datesemployed" onclick="WdatePicker({'dateFmt':'yyyy-MM',maxDate:'%y%M'})" style="width:120px" />
    					<input id="personnel-hdbelongdeptid" name="belongdeptid" type="hidden"/>
    				</td>
    				<td>状态:</td>
    				<td><input /></td>
    				<td colspan="2">
	    				<a href="javaScript:void(0);" id="personnel-query-workyearreport" class="easyui-linkbutton" iconCls="icon-search" title="[Alt+Q]查询">查询</a>
			    		<a href="javaScript:void(0);" id="personnel-query-workyearreport" class="easyui-linkbutton" iconCls="icon-reload" title="[Alt+R]重置">重置</a>
		    			<span id="personnel-query-advanced-workyearreport"></span>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
		var workyearreportListColJson=$("#personnel-datagrid-workyearreport").createGridColumnLoad({
	     	name:'base_personnel',
	     	frozenCol:[[]],
	     	commonCol:[[{field:'id',title:'编码',width:50,sortable:true},
   				{field:'name',title:'姓名',width:80,sortable:true},
   				{field:'datesemployed',title:'入职时间',width:90,sortable:true},
   				{field:'workyear',title:'入职时间',width:90,sortable:true,isShow:true},
   				{field:'state',title:'状态',width:50,sortable:true,
   					formatter:function(val,rowData,rowIndex){
					return rowData.stateName;
				}
   				},
   				{field:'remark',title:'备注',width:100,sortable:true},
   				{field:'employetype',title:'业务属性',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.employetypeName;
					}
				},
   				{field:'personnelstyle',title:'员工类型',width:60,hidden:true,sortable:true,
   					formatter:function(val,rowData,rowIndex){
						return rowData.perStyleName;
					}
    			}, 
				{field:'belongdeptid',title:'所属部门',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.belongdept;
					}
				},
				{field:'belongpost',title:'所属岗位',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.belongpostName;
					}
				},
				{field:'sex',title:'性别',width:50,hidden:true,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.sexName;
					}
				},
				{field:'maritalstatus',title:'婚姻状况',width:60,hidden:true,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.maritalstatusName;
					}
				},
				{field:'birthday',title:'出生日期',width:80,hidden:true,sortable:true},
				{field:'age',title:'年龄',width:30,hidden:true,sortable:true},
				{field:'idcard',title:'身份证号码',width:120,hidden:true,sortable:true},
				{field:'nation',title:'民族',width:50,hidden:true,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.nationName;
					}
				},
				{field:'nativeplace',title:'籍贯',width:50,hidden:true,sortable:true}, 
				{field:'polstatus',title:'政治面貌',width:60,hidden:true,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.polstatusName;
					}
				}, 
				{field:'tel',title:'电话',width:80,sortable:true},
				{field:'fax',title:'传真',width:80,sortable:true},
				{field:'email',title:'邮箱',width:80,sortable:true},
				{field:'telphone',title:'手机号码',width:80,sortable:true},
				{field:'compcornet',title:'公司短号',width:70,sortable:true}, 
				{field:'salaryscheme',title:'薪酬方案',width:80,hidden:true,sortable:true},
				{field:'address',title:'居住地址',width:100,hidden:true,sortable:true},
				{field:'addrpostcode',title:'居住地邮编',width:50,hidden:true,sortable:true},
				{field:'householdaddr',title:'户籍地址',width:100,hidden:true,sortable:true},
				{field:'householdcode',title:'户籍地邮编',width:50,hidden:true,sortable:true}
			]]
		});
    </script>
  </body>
</html>
