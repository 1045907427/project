//表单设计方法
//zhengziyong
function chooseUsers(time){
	$("#dialog_"+time).show();
	$("#dialog_"+time).dialog({
		title:"人员选择",
		width:600,
		height:400,
		close:false,
		modal:true,
		cache:false,
		href:'act/personnelChoosePage.do',
		buttons:[
			{
				text:'确定',
				iconCls:'button-save',
				handler:function(){
					var row = $("#personnelDataGird").datagrid("getSelected");
					if(row != null){
						$("#text_"+time).val(row.name);
						$("#hidden_"+time).val(row.id);
						$("#dialog_"+time).dialog('close');
					}
				}
			}
		]
	});
}
function chooseDepartment(time){
	$("#dialog_"+time).show();
	$("#dialog_"+time).dialog({
		title:"部门选择",
		width:600,
		height:400,
		close:false,
		modal:true,
		cache:false,
		href:'act/departChoosePage.do',
		buttons:[
			{
				text:'确定',
				iconCls:'button-save',
				handler:function(){
					var row = $("#departmentDataGrid").datagrid("getSelected");
					if(row != null){
						$("#text_"+time).val(row.name);
						$("#hidden_"+time).val(row.id);
						$("#dialog_"+time).dialog('close');
					}
				}
			}
		]
	});
}	