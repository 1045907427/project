//新增页面加载后按钮的启用状态
var business_addPage_load_buttonStatus = function(index){
	$("#business-add-leavePage").linkbutton('disable');
	$("#business-edit-leavePage").linkbutton('disable');
	$("#business-tempsave-leavePage").linkbutton('enable');
	$("#business-save-leavePage").linkbutton('enable');
	$("#business-remove-leavePage").linkbutton('disable');
	$("#business-check-leavePage").linkbutton('disable');
	$("#business-uncheck-leavePage").linkbutton('disable');
	$("#business-pre-leavePage").linkbutton('disable');
	if(index > 0){
		$("#business-pre-leavePage").linkbutton('enable');
	}
	$("#business-next-leavePage").linkbutton('disable');
	$("#business-process-leavePage").splitbutton('disable');
	$("#business-mm-leavePage").menu('disableItem','#business-submitProcess-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-submitComment-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-showProcess-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-showProcessDiagram-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-takeBackProcess-leavePage');
	$("#business-cancel-leavePage").linkbutton('disable');
};
//修改页面加载后按钮的启用状态
var business_editPage_load_buttonStatus = function(status, processFlag, type){
	$("#business-add-leavePage").linkbutton('disable');
	$("#business-edit-leavePage").linkbutton('disable');
	$("#business-tempsave-leavePage").linkbutton('disable');
	$("#business-save-leavePage").linkbutton('disable');
	$("#business-remove-leavePage").linkbutton('disable');
	$("#business-check-leavePage").linkbutton('disable');
	$("#business-uncheck-leavePage").linkbutton('disable');
	$("#business-pre-leavePage").linkbutton('disable');
	$("#business-next-leavePage").linkbutton('disable');
	$("#business-process-leavePage").splitbutton('disable');
	$("#business-mm-leavePage").menu('disableItem','#business-submitProcess-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-submitComment-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-showProcess-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-showProcessDiagram-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-takeBackProcess-leavePage');
	$("#business-cancel-leavePage").linkbutton('disable');
	if(status == "1"){
		$("#business-save-leavePage").linkbutton('enable');
		$("#business-tempsave-leavePage").linkbutton('enable');
	}
	if(status == "2"){
		$("#business-tempsave-leavePage").linkbutton('disable');
		$("#business-save-leavePage").linkbutton('enable');
		if(processFlag == "1" || processFlag == "2"){
			$("#business-tempsave-leavePage").linkbutton('disable');
			$("#business-save-leavePage").linkbutton('disable');
		}
	}
};
//查看页面加载后按钮的启用状态
var business_viewPage_load_buttonStatus = function(status, processFlag, type, arrayLength, index){
	$("#business-add-leavePage").linkbutton('enable');
	$("#business-edit-leavePage").linkbutton('disable');
	$("#business-tempsave-leavePage").linkbutton('disable');
	$("#business-save-leavePage").linkbutton('disable');
	$("#business-remove-leavePage").linkbutton('disable');
	$("#business-check-leavePage").linkbutton('disable');
	$("#business-uncheck-leavePage").linkbutton('disable');
	$("#business-pre-leavePage").linkbutton('disable');
  	$("#business-next-leavePage").linkbutton('disable');
	$("#business-process-leavePage").splitbutton('disable');
	$("#business-mm-leavePage").menu('disableItem','#business-submitProcess-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-submitComment-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-showProcess-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-showProcessDiagram-leavePage');
	$("#business-mm-leavePage").menu('disableItem','#business-takeBackProcess-leavePage');
	$("#business-cancel-leavePage").linkbutton('disable');
	if(type == "edit" || type == "view"){
		$("#business-process-leavePage").splitbutton('enable');
		if(status == "1"){
			$("#business-edit-leavePage").linkbutton('enable');
			$("#business-remove-leavePage").linkbutton('enable');
			$("#business-process-leavePage").splitbutton('disable');
		}
		if(status == "2"){
			$("#business-edit-leavePage").linkbutton('enable');
			$("#business-remove-leavePage").linkbutton('enable');
			$("#business-check-leavePage").linkbutton('enable');
			$("#business-mm-leavePage").menu('enableItem','#business-submitProcess-leavePage');
		}
		if(status == "3"){
			$("#business-check-leavePage").linkbutton('disable');
			$("#business-uncheck-leavePage").linkbutton('enable');
			$("#business-mm-leavePage").menu('enableItem','#business-showProcess-leavePage');
			$("#business-mm-leavePage").menu('enableItem','#business-showProcessDiagram-leavePage');
			$("#business-cancel-leavePage").linkbutton('enable');
			if(processFlag != "1" && processFlag != "2"){
				$("#business-process-leavePage").splitbutton('disable');
			}
		}
		if(status == "4"){
			$("#business-cancel-leavePage").linkbutton('disable');
		}
		if(processFlag == "1" || processFlag == "2"){
			$("#business-edit-leavePage").linkbutton('disable');
			$("#business-remove-leavePage").linkbutton('disable');
			$("#business-check-leavePage").linkbutton('disable');
			$("#business-mm-leavePage").menu('disableItem','#business-submitProcess-leavePage');
			$("#business-mm-leavePage").menu('enableItem','#business-showProcess-leavePage');
			$("#business-mm-leavePage").menu('enableItem','#business-showProcessDiagram-leavePage');
			if(processFlag == "2"){
				$("#business-mm-leavePage").menu('enableItem','#business-takeBackProcess-leavePage');
			}
		}
	}
	if(type == "handle" || type == "show"){
		$("#business-add-leavePage").linkbutton('disable');
		$("#business-edit-leavePage").linkbutton('enable');
		$("#business-process-leavePage").splitbutton('enable');
		$("#business-mm-leavePage").menu('enableItem','#business-submitComment-leavePage');
		$("#business-mm-leavePage").menu('enableItem','#business-showProcess-leavePage');
		$("#business-mm-leavePage").menu('enableItem','#business-showProcessDiagram-leavePage');
		if(type == "show"){
			$("#business-edit-leavePage").linkbutton('disable');
			$("#business-mm-leavePage").menu('disableItem','#business-submitComment-leavePage');
		}
	}
	if(index > 0){
		$("#business-pre-leavePage").linkbutton('enable');
    }
    if(index < (arrayLength - 1)){
	  	$("#business-next-leavePage").linkbutton('enable');
    }
};
//返回点击上一页或下一页后的编号
var business_preNext_show_id = function(array, index){
    return array[index];
};
//删除一条数据后数组显示的记录对应下标
var business_delete_show_index = function(array, index){
    if(array.length > 1){
	    if(index < array.length){
		    return index;
	    }
	    else{
		    return index - 1;
	    }
    }
    else{
		return -1;
    }
};