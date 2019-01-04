<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
    <table id="sales-table-orderversion"></table>
    <script type="text/javascript">
    	$(function(){
			$("#sales-table-orderversion").datagrid({
				columns:[[
                    {field:'version',title:'版本号',width:60, align: 'right'},
                    {field:'id',title:'编号',width:120, align: 'left'},
                    {field:'businessdate',title:'业务日期',width:80,align:'left'},
                    {field:'customerid',title:'客户编码',width:60,align:'left'},
                    {field:'customername',title:'客户名称',width:150,align:'left',isShow:true},
                    {field:'salesdept',title:'销售部门',width:100,align:'left'},
                    {field:'salesuser',title:'客户业务员',width:80,align:'left'},
                    {field:'field01',title:'金额',width:80,align:'right',
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'field02',title:'未税金额',width:80,align:'right',hidden:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'field03',title:'税额',width:80,align:'right',hidden:true,
                        formatter:function(value,row,index){
                            return formatterMoney(value);
                        }
                    },
                    {field:'status',title:'状态',width:60,align:'left',
                        formatter:function(value,row,index){
                            return row.statusname;
                        }
                    },
                    {field:'indooruserid',title:'销售内勤',width:60,
                        formatter:function(value,rowData,index){
                            return rowData.indoorusername;
                        }
                    },
                    {field:'addusername',title:'制单人',width:80},
                    {field:'addtime',title:'制单时间',width:120},
                    {field:'modifyusername',title:'修改人',width:80},
                    {field:'modifytime',title:'修改时间',width:120},
                    {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
                    {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
                    {field:'remark',title:'备注',width:100}
                ]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
		 		data:JSON.parse('${listJson}'),
				onDblClickRow:function(rowIndex, rowData){
                    top.addTab('sales/orderVersionViewPage.do?id='+rowData.id+'&version='+rowData.version, '销售订单修改记录查看_'+rowData.version);
				}
			});
    	});
    </script>
  </body>
</html>
