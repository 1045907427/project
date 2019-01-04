var import_disabled = false;

(function(){
	$.fn.extend({
		Excel:function(type, options){
			if(undefined != options){
				if(type == "import"){
					if(options.type == "importmore"){
						options = $.extend({}, $.ExcelImportMore.defaults, {}, options);
						return new $.ExcelImportMore(this, options);
					}
					else if(options.type == "importbill"){//导入单据
						options = $.extend({}, $.ExcelImportBill.defaults, {}, options);
						return new $.ExcelImportBill(this, options);
					}else if(options.type == "importUserdefined"){//自定义导入
						options = $.extend({}, $.ExcelImportUserdefined.defaults, {}, options);
						return new $.ExcelImportUserdefined(this, options);
					}else{
						options = $.extend({}, $.ExcelImport.defaults, {}, options);
						return new $.ExcelImport(this, options);
					}
				}
				else if(type == "export"){
					if(options.type == "exportmore"){
						options = $.extend({}, $.ExcelExportMore.defaults, {}, options);
						return new $.ExcelExportMore(this, options);
					}
					else if(options.type == "exportbill"){//导出单据
						options = $.extend({}, $.ExcelExportBill.defaults, {}, options);
						return new $.ExcelExportBill(this, options);
					}else if(options.type == "exportUserdefined"){//自定义导出单据 需要定义url
						options = $.extend({}, $.ExcelExportUserdefined.defaults, {}, options);
						return new $.ExcelExportUserdefined(this, options);
					}
					else{
						options = $.extend({}, $.ExcelExport.defaults, {}, options);
						return new $.ExcelExport(this, options);
					}
				}
			}
		}
	});
})(jQuery);
$.ExcelImport = function(btn, options){
	$btn = $(btn);
	$btn.bind("click",function(){
		if($("#excel-import-dialog").length < 1){
			$("body").append("<div id='excel-import-dialog'></div>");
		}
		var importPageRequestParam=options.importPageRequestParam || {};
		$("#excel-import-dialog").dialog({
			href: 'common/importPage.do?version=1',
			width: options.width,
			height: options.height,
			title: options.title,
			colsed: false,
			cache: false,
			modal: true,
			method:'post',
			queryParams:importPageRequestParam,
			onLoad: function(){
				if(undefined != options.importparam){
		 			$("#common-div-importparam").html(options.importparam);
				}
				$("<input type='hidden' name='clazz' value='"+options.clazz+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='method' value='"+options.method+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='module' value='"+options.module+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='pojo' value='"+options.pojo+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='tn' value='"+options.tn+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='majorkey' value='"+options.majorkey+"' />").appendTo("#common_form_importExcel");

                $("#common_form_importExcel").form(
                    importExcelForm()
                );
			},
			onClose: function(){
				options.onClose();
			}
		});
		importExcelForm = function(){
			return {
				onSubmit: function(){ 
					var file = $("input[name=excelFile]").val();
					var ext = file.substring(file.lastIndexOf('.'));
					if(ext != ".xls" && ext != ".xlsx"){
						$.messager.alert("提醒", "请上传Excel文件");
						return false;
					}
			    	loading("导入中..");
			    },  
			    success:function(data){
			    	loaded();
			    	returnExcelMsg(data);
			    	$("#excel-import-dialog").dialog('close', true);
			    }  
			};
		};
	});
};
$.ExcelExport = function(btn, options){
	$btn = $(btn);
	$btn.bind("click",function(){
		if($("#excel-export-dialog").length < 1){
			$("body").append("<div id='excel-export-dialog'></div>");
		}
		$("#excel-export-dialog").dialog({
			href: 'common/exportPage.do?version=1',
			width: options.width,
			height: options.height,
			title: options.title,
			colsed: false,
			cache: false,
			modal: true,
			onLoad: function(){
				$("#common-name-exportPage").val(options.name);
				var queryJson = $(options.queryForm).serializeJSON();
				for(var key in queryJson){
		 			$("<input type='hidden' name='"+key+"' value='"+queryJson[key]+"' />").appendTo("#common-form-exportPage");
		 		}
		 		if(undefined != options.exportparam){
		 			$("#common-div-exportparam").html(options.exportparam);
				}
				$("<input type='hidden' name='tn' value='"+options.tn+"' />").appendTo("#common-form-exportPage");
				if(options.state != undefined){
					$("<input type='hidden' name='state' value='"+options.state+"' />").appendTo("#common-form-exportPage");
				}
				if(options.ordersql != undefined){
					$("<input type='hidden' name='ordersql' value='"+options.ordersql+"' />").appendTo("#common-form-exportPage");
				}
				if(undefined != options.datagrid){
					var rows = $(options.datagrid).datagrid('getChecked');
					var exportids = "";
					if(rows.length > 0){
						for(var i=0;i<rows.length;i++){
							if(exportids == ""){
								exportids = rows[i].id;
							}else{
								exportids += "," + rows[i].id;
							}
						}
					}
					if(exportids != ""){
						$("<input type='hidden' name='exportids' value='"+exportids+"' />").appendTo("#common-form-exportPage");
					}
				}
			}
		});
		
	});
};

$.ExcelImportMore = function(btn, options){
	$btn = $(btn);
	$btn.bind("click",function(){
		if(import_disabled){
			return false;
		}
		if($("#excel-import-dialog").length < 1){
			$("body").append("<div id='excel-import-dialog'></div>");
		}
		var version = "";
		if(undefined != options.version){
			version = options.version;
		}
		$("#excel-import-dialog").dialog({
			href: 'common/importPage.do?version='+version+'',
			width: options.width,
			height: options.height,
			title: options.title,
			colsed: false,
			cache: false,
			modal: true,
			onLoad: function(){
				$("#common_form_importExcel").removeAttr("action").attr("action","common/importMoreExcel.do");
				var tnstr = JSON.stringify(options.tnjson);
				var methodstr = JSON.stringify(options.methodjson);
				if(undefined != options.childsamemethod){
					var childsamemethod = JSON.stringify(options.childsamemethod);
					$("<input type='hidden' name='childsamemethod' value="+childsamemethod+" />").appendTo("#common_form_importExcel");
				}
				if(undefined != options.shortcutname){
					$("<input type='hidden' name='shortcutname' value='"+options.shortcutname+"' />").appendTo("#common_form_importExcel");
				}
				if(undefined != options.importparam){
		 			$("#common-div-importparam").html(options.importparam);
				}
				var pojostr = JSON.stringify(options.pojojson);
				$("<input type='hidden' name='clazz' value='"+options.clazz+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='methodstr' value='"+methodstr+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='module' value='"+options.module+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='pojostr' value='"+pojostr+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='tnstr' value='"+tnstr+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='maintn' value='"+options.maintn+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='majorkey' value='"+options.majorkey+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='childkey' value='"+options.childkey+"' />").appendTo("#common_form_importExcel");

                $("#common_form_importExcel").form(
                    importExcelForm()
                );
			},
			onClose: function(){
				options.onClose();
			}
		});
		importExcelForm = function(){
			return {
				onSubmit: function(){ 
					var file = $("input[name=excelFile]").val();
					var ext = file.substring(file.lastIndexOf('.'));
					if(ext != ".xls" && ext != ".xlsx"){
						$.messager.alert("提醒", "请上传Excel文件");
						return false;
					}
			    	loading("导入中..");
			    },  
			    success:function(data){
			    	loaded();
			    	returnExcelMsg(data);
			    	$("#excel-import-dialog").dialog('close', true);
			    }  
			};
		};
	});
};
$.ExcelExportMore = function(btn, options){
	$btn = $(btn);
	$btn.bind("click",function(){
		if($("#excel-export-dialog").length < 1){
			$("body").append("<div id='excel-export-dialog'></div>");
		}
		var version = "";
		if(undefined != options.version){
			version = options.version;
		}
		$("#excel-export-dialog").dialog({
			href: 'common/exportPage.do?version='+version+'',
			width: options.width,
			height: options.height,
			title: options.title,
			colsed: false,
			cache: false,
			modal: true,
			onLoad: function(){
				$("#common-form-exportPage").removeAttr("action").attr("action","common/exportMoreExcel.do");
				$("#common-name-exportPage").val(options.name);
				var tnjson = JSON.stringify(options.tnjson);
				if(undefined != options.shortcutname){
					$("<input type='hidden' name='shortcutname' value='"+options.shortcutname+"' />").appendTo("#common-form-exportPage");
				}
				if(undefined != options.queryparam){
					$("<input type='hidden' name='queryparam' value='"+options.queryparam+"' />").appendTo("#common-form-exportPage");
				}
				if(undefined != options.sort){
					$("<input type='hidden' name='sort' value='"+options.sort+"' />").appendTo("#common-form-exportPage");
				}
				if(undefined != options.exportparam){
		 			$("#common-div-exportparam").html(options.exportparam);
				}
				if(options.ordersql != undefined){
					$("<input type='hidden' name='ordersql' value='"+options.ordersql+"' />").appendTo("#common-form-exportPage");
				}
				var queryJson = $(options.queryForm).serializeJSON();
				for(var key in queryJson){
		 			$("<input type='hidden' name='"+key+"' value='"+queryJson[key]+"' />").appendTo("#common-form-exportPage");
		 		}
		 		if(undefined != options.uniontnjson){
					var uniontnjson = JSON.stringify(options.uniontnjson);
					$("<input type='hidden' name='uniontnjson' value='"+uniontnjson+"' />").appendTo("#common-form-exportPage");
				}
				$("<input type='hidden' name='tnstr' value='"+options.tnstr+"' />").appendTo("#common-form-exportPage");
				$("<input type='hidden' name='tnjson' value='"+tnjson+"' />").appendTo("#common-form-exportPage");
				$("<input type='hidden' name='maintn' value='"+options.maintn+"' />").appendTo("#common-form-exportPage");
				$("<input type='hidden' name='childkey' value='"+options.childkey+"' />").appendTo("#common-form-exportPage");
				if(undefined != options.datagrid){
					var rows = $(options.datagrid).datagrid('getChecked');
					var exportids = "";
					if(rows.length > 0){
						for(var i=0;i<rows.length;i++){
							if(exportids == ""){
								exportids = rows[i].id;
							}else{
								exportids += "," + rows[i].id;
							}
						}
					}
					if(exportids != ""){
						$("<input type='hidden' name='exportids' value='"+exportids+"' />").appendTo("#common-form-exportPage");
					}
					if(undefined != options.childkey){
						$("<input type='hidden' name='"+options.childkey+"' value='"+exportids+"' />").appendTo("#common-form-exportPage");
					}
				}
			}
		});
		
	});
};

$.ExcelImportBill = function(btn, options){
	$btn = $(btn);
	$btn.bind("click",function(){
		if(import_disabled){
			return false;
		}
		if($("#excel-import-dialog").length < 1){
			$("body").append("<div id='excel-import-dialog'></div>");
		}
		var importPageRequestParam=options.importPageRequestParam || {};
		$("#excel-import-dialog").dialog({
			href: 'common/importPage.do?version=1',
			width: options.width,
			height: options.height,
			title: options.title,
			colsed: false,
			cache: false,
			modal: true,
			method:'post',
			queryParams:importPageRequestParam,
			onLoad: function(){
				if(undefined != options.importparam){
		 			$("#common-div-importparam").html(options.importparam);
				}
				$("#common_form_importExcel").removeAttr("action").attr("action","sales/importSalesOrderExcel.do");
				$("<input type='hidden' name='module' value='"+options.module+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='pojo' value='"+options.pojo+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='clazz' value='"+options.clazz+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='method' value='"+options.method+"' />").appendTo("#common_form_importExcel");

                $("#common_form_importExcel").form(
                    importExcelForm()
                );
			},
			onClose: function(){
				options.onClose();
			}
		});
		importExcelForm = function(){
			return {
				onSubmit: function(){ 
					//filestype 1:Excel文件 2:txt文件
					var filestype = $("#common-filestype").val()
					var file = $("input[name=excelFile]").val();
					var ext = file.substring(file.lastIndexOf('.'));
					if("1" == filestype){
						if(ext != ".xls" && ext != ".xlsx"){
							$.messager.alert("提醒", "请上传Excel文件");
							$("#excel-import-dialog").dialog('close', true);
							return false;
						}
					}
					else if("2" == filestype){
						if(ext != ".txt"){
							$.messager.alert("提醒", "请上传txt文件");
							$("#excel-import-dialog").dialog('close', true);
							return false;
						}
					}
			    	loading("导入中..");
			    },  
			    success:function(data){
			    	loaded();
			    	returnExcelMsg(data);
			    	$("#excel-import-dialog").dialog('close', true);
			    }  
			};
		};
	});
};

 $.ExcelImportUserdefined = function(btn, options){
	$btn = $(btn);
	$btn.bind("click",function(){
		if(import_disabled){
			return false;
		}
		if($("#excel-import-dialog").length < 1){
			$("body").append("<div id='excel-import-dialog'></div>");
		}
		var version = "";
		if(undefined != options.version){
			version = options.version;
		}else{
			version = '1';
		}
		var importPageRequestParam=options.importPageRequestParam || {};
		$("#excel-import-dialog").dialog({
			href: 'common/importPage.do?version='+version+'',
			width: options.width,
			height: options.height,
			title: options.title,
			colsed: false,
			cache: false,
			modal: true,
			method:'post',
			queryParams:importPageRequestParam,
			onLoad: function(){
				$("#common_form_importExcel").removeAttr("action").attr("action",options.url);
				$("<input type='hidden' name='module' value='"+options.module+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='pojo' value='"+options.pojo+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='clazz' value='"+options.clazz+"' />").appendTo("#common_form_importExcel");
				$("<input type='hidden' name='method' value='"+options.method+"' />").appendTo("#common_form_importExcel");
				if(undefined != options.importparam){
		 			$("#common-div-importparam").html(options.importparam);
				}
				var queryJson = "";
				var fieldsb=new Array();
				if(options.queryForm && options.queryForm!="" ){
					queryJson = $(options.queryForm).serializeJSON();
					for(var key in queryJson){
						fieldsb.push("<input type='hidden' name='"+key+"' value='"+queryJson[key]+"' />");
			 		}
					$("#common_form_importExcel").append(fieldsb.join(""));
				}
				if(options.fieldParam && $.isPlainObject(options.fieldParam)){
					queryJson = options.fieldParam;
					fieldsb=new Array();
					for(var key in queryJson){
						fieldsb.push("<input type='hidden' name='"+key+"' value='"+queryJson[key]+"' />");
			 		}
					$("#common_form_importExcel").append(fieldsb.join(""));
				}
				if(options.isShowImportMsg==null ||
					options.isShowImportMsg !=false ||
					options.isShowImportMsg !=0 ||
					options.isShowImportMsg == ''){
					options.isShowImportMsg=true;
				}

                $("#common_form_importExcel").form(
                    importExcelForm(options)
                );
			},
			onClose: function(){
				options.onClose();
			}
		});
		importExcelForm = function(options){
			if(typeof options == "undefined" || options==null){
				options={};
			}
			var isShowImportMsg = options.isShowImportMsg || true;
			return {
				onSubmit: function(){
					var filestype = $("#common-filestype").val()
					var file = $("input[name=excelFile]").val();
					var ext = file.substring(file.lastIndexOf('.'));
					if("1" == filestype){
						if(ext != ".xls" && ext != ".xlsx"){
							$.messager.alert("提醒", "请上传Excel文件");
							$("#excel-import-dialog").dialog('close', true);
							return false;
						}
					}
					else if("2" == filestype){
						if(ext != ".txt"){
							$.messager.alert("提醒", "请上传txt文件");
							$("#excel-import-dialog").dialog('close', true);
							return false;
						}
					}
			    	loading("导入中..");
			    },
			    success:function(data){
			    	loaded();
			    	if(isShowImportMsg) {
                        returnExcelMsg(data);
                    }
                    try{
			    		options.callBackHandle(data);
					}catch (e){

					}
			    	$("#excel-import-dialog").dialog('close', true);
			    }
			};
		};
	});
};
$.ExcelExportBill = function(btn, options){
	$btn = $(btn);
	$btn.bind("click",function(){
		if($("#excel-export-dialog").length < 1){
			$("body").append("<div id='excel-export-dialog'></div>");
		}
		$("#excel-export-dialog").dialog({
			href: 'common/exportPage.do?version=1',
			width: options.width,
			height: options.height,
			title: options.title,
			colsed: false,
			cache: false,
			modal: true,
			onLoad: function(){
				$("#common-form-exportPage").removeAttr("action").attr("action","sales/exportBillExcel.do");
				$("#common-name-exportPage").val(options.name);
				var queryJson = $(options.queryForm).serializeJSON();
				for(var key in queryJson){
		 			$("<input type='hidden' name='"+key+"' value='"+queryJson[key]+"' />").appendTo("#common-form-exportPage");
		 		}
				if(options.state != undefined){
					$("<input type='hidden' name='state' value='"+options.state+"' />").appendTo("#common-form-exportPage");
				}
				if(undefined != options.exportparam){
		 			$("#common-div-exportparam").html(options.exportparam);
				}
				if(options.ordersql != undefined){
					$("<input type='hidden' name='ordersql' value='"+options.ordersql+"' />").appendTo("#common-form-exportPage");
				}
				if(undefined != options.datagrid){
					var rows = $(options.datagrid).datagrid('getChecked');
					var exportids = "";
					if(rows.length > 0){
						for(var i=0;i<rows.length;i++){
							if(exportids == ""){
								exportids = rows[i].id;
							}else{
								exportids += "," + rows[i].id;
							}
						}
					}
					if(exportids != ""){
						$("<input type='hidden' name='exportids' value='"+exportids+"' />").appendTo("#common-form-exportPage");
					}
				}
			}
		});
		
	});
};
$.ExcelExportUserdefined = function(btn, options){
	$btn = $(btn);
	$btn.bind("click",function(){
		if(options.onBeforeExport!=null){
			var flag = options.onBeforeExport();
			if(flag!=null && flag==false){
				return false;
			}
		}
		if($("#excel-export-dialog").length < 1){
			$("body").append("<div id='excel-export-dialog'></div>");
		}
		var version = "";
		if(undefined != options.version){
			version = options.version;
		}else{
			version = '1';
		}
		$("#excel-export-dialog").dialog({
			href: 'common/exportPage.do?version='+version+'',
			width: options.width,
			height: options.height,
			title: options.title,
			colsed: false,
			cache: false,
			modal: true,
			onLoad: function(){
				$("#common-form-exportPage").removeAttr("action").attr("action",options.url);
				$("#common-name-exportPage").val(options.name);
				var fieldsb=new Array();
				var queryJson={};
				if(options.queryForm && options.queryForm!="" ){
					queryJson = $(options.queryForm).serializeJSON();
					for(var key in queryJson){
						fieldsb.push("<input type='hidden' name='"+key+"' value='"+queryJson[key]+"' />");
			 		}
					$("#common-form-exportPage").append(fieldsb.join(""));
				}
				if(options.fieldParam && $.isPlainObject(options.fieldParam)){
					queryJson = options.fieldParam;
					fieldsb=new Array();
					for(var key in queryJson){
						fieldsb.push("<input type='hidden' name='"+key+"' value='"+queryJson[key]+"' />");
			 		}
					$("#common-form-exportPage").append(fieldsb.join(""));
				}
				if(options.state != undefined){
					$("<input type='hidden' name='state' value='"+options.state+"' />").appendTo("#common-form-exportPage");
				}
				if(undefined != options.exportparam){
		 			$("#common-div-exportparam").html(options.exportparam);
				}
				if(options.ordersql != undefined){
					$("<input type='hidden' name='ordersql' value='"+options.ordersql+"' />").appendTo("#common-form-exportPage");
				}
				if(undefined != options.datagrid){
					var rows = $(options.datagrid).datagrid('getChecked');
					var exportids = "";
					if(rows.length > 0){
						for(var i=0;i<rows.length;i++){
							if(exportids == ""){
								exportids = rows[i].id;
							}else{
								exportids += "," + rows[i].id;
							}
						}
					}
					if(exportids != ""){
						if(undefined == options.exportidname || options.exportidname==""){
							options.exportidname='exportids';
						}
						$("<input type='hidden' name='"+options.exportidname+"' value='"+exportids+"' />").appendTo("#common-form-exportPage");
					}
				}
			}
		});
		
	});
};

function appendDataExcelInfo(retstr){
	var data = $.parseJSON(retstr);
	var tablestar = "<table cellpadding='0' cellspacing='0' border='0' style='width: 300px;'>";
	var str = "";
	if(undefined != data.flag && data.flag){
		str += "<tr><td style='width:300px;'>导入成功!</td></tr>";
	}
	if(undefined != data.error && data.error){
		str += "<tr><td style='width:300px;'>导入出错!</td></tr>";
	}
    if(undefined != data.errorFile && data.errorFile){
        str += "<tr><td style='width:300px;'>文件未知错误，请尝试将数据复制到一个新文件再导入!</td></tr>";
    }
	if(undefined != data.excelempty && data.excelempty){
		str += "<tr><td style='width:300px;'>导入模版数据为空，请填写表格数据后再行导入!</td></tr>";
	}
    if(undefined != data.nolevel && data.nolevel){
        str += "<tr><td style='width:300px;'>该档案对应的编码级次未定义!</td></tr>";
    }
    if(undefined != data.levelNum){
        if(undefined != data.levelVal && data.levelVal != null && data.levelVal != ''){
            str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>编码"+data.levelVal+"长度不符合该档案的编码级次定义,"+data.levelNum+"条不允许导入;</td></tr>";
        }
    }
	if(undefined != data.dataerror && data.dataerror){
		str += "<tr><td style='width:300px;'>导入数据格式出错!</td></tr>";
	}
   	if(undefined != data.versionerror && data.versionerror){
		str += "<tr><td style='width:300px;'>导入模版错误!</td></tr>";
	}
	if(undefined != data.ordernum && data.ordernum != null && data.ordernum != ''){
		str += "<tr><td>导入成功"+data.ordernum+"张单据;</td></tr>";
	}
	if(undefined != data.success && data.success != null && data.success != ''){
		str += "<tr><td>导入成功"+data.success+"条;</td></tr>";
	}
	if(undefined != data.disableInfoidsmsg && data.disableInfoidsmsg != ''){
		str += "<tr><td>商品："+data.disableInfoidsmsg+"是禁用商品，不允许导入;</td></tr>";
	}
    if(undefined != data.mainCustomer && data.mainCustomer){
        str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>总店客户"+data.mainCustomer+"不允许导入;</td></tr>";
    }
	if(undefined != data.failure){
		if(undefined != data.failStr && data.failStr != null && data.failStr != ''){
			str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>编码"+data.failStr+"导入失败,导入失败"+data.failure+"条;</td></tr>";
		}
	}
    if(undefined != data.failureCustomerNum && data.failureCustomerNum != null && data.failureCustomerNum != ''){
        str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>客户编码不存在,导入失败！导入失败"+data.failureCustomerNum+"条;</td></tr>";
    }
    if(undefined != data.emptVal){
        if(undefined != data.emptSize && data.emptSize != null && data.emptSize != ''){
            str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>客户编码:"+data.emptCustomerID+"不存在,导入失败"+data.emptSize+"条;</td></tr>";
        }
    }
	if(undefined != data.repeatNum){
		if(undefined != data.repeatVal && data.repeatVal != null && data.repeatVal != ''){
			str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>编码"+data.repeatVal+"重复,"+data.repeatNum+"条不允许导入;</td></tr>";
		}
	}
	if(undefined != data.reptthisNum){
		if(undefined != data.reptthisnameVal && data.reptthisnameVal != null && data.reptthisnameVal != ''){
			str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>编码"+data.reptthisnameVal+"本级名称重复,"+data.reptthisNum+"条不允许导入;</td></tr>";
		}
	}
    if(undefined != data.emptPriceNum){
        if(undefined != data.emptPrice && data.emptPrice != null && data.emptPrice != ''){
            str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>编码"+data.emptPrice+"价格为空,"+data.emptPriceNum+"条不允许导入;</td></tr>";
        }
    }
    if(undefined != data.goodsidNum){
        if(undefined != data.goodsidVal && data.goodsidVal != null && data.goodsidVal != ''){
            str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>商品编码"+data.goodsidVal+"不存在,"+data.goodsidNum+"条不允许导入;</td></tr>";
        }
    }
    if(undefined != data.spellNum){
        if(undefined != data.goodsspellVal && data.goodsspellVal != null && data.goodsspellVal != ''){
            str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>商品助记码"+data.goodsspellVal+"不存在,"+data.spellNum+"条不允许导入;</td></tr>";
        }
    }
	if(undefined != data.barcodeNum){
		if(undefined != data.barcodeVal && data.barcodeVal != null && data.barcodeVal != ''){
			str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>商品条形码"+data.barcodeVal+"不存在,"+data.barcodeNum+"条不允许导入;</td></tr>";
		}
	}
    if(undefined != data.samenameStr && data.samenameStr != null && data.samenameStr != ''){
        str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>编码"+data.samenameStr+"存在相同名称数据;</td></tr>";
    }
    if(undefined != data.closeval && data.closeval != null && data.closeval != ''){
        str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>编码"+data.closeval+"商品未启用;</td></tr>";
    }

	if(undefined != data.coverNum){
		if(undefined != data.coverVal && data.coverVal != null && data.coverVal != ''){
			var coverval = data.coverVal;
			var coverret = "";
			if(coverval.indexOf(",") != -1){
				var coverarr = coverval.split(",");
				for(var i=0;i<coverarr.length;i++){
					if(coverret == ""){
						coverret = coverarr[i];
					}else{
						coverret += "<br />" + coverarr[i];
					}
				}
			}else{
				coverret = coverval;
			}
			str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>"+coverret+"覆盖;<br />"+data.coverNum+"条覆盖成功;</td></tr>";
		}
	}
	if(undefined != data.noidnum){
		if(undefined != data.noidval && data.noidval != null && data.noidval != ''){
			str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>"+data.noidval+","+data.noidnum+"条不允许导入;</td></tr>";
		}
	}
	if(undefined != data.closeNum){
		if(undefined != data.closeVal && data.closeVal != null && data.closeVal != ''){
			str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>编码"+data.closeVal+"禁用,"+data.closeNum+"条不允许导入;</td></tr>";
		}
	}
	if(undefined != data.errorNum){
		if(undefined != data.errorVal && data.errorVal != null && data.errorVal != ''){
			str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>编码"+data.errorVal+"出错,"+data.errorNum+"条导入失败;</td></tr>";
		}
	}
	if(undefined != data.errorChildNum){
		if(undefined != data.errorChildVal && data.errorChildVal != null && data.errorChildVal != ''){
			str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>编码"+data.errorChildVal+"的子表出错,请检查;</td></tr>";
		}
	}
	if(undefined != data.msg) {
        if (data.msg != null && data.msg != '') {
            str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>" + data.msg.replace(/\r\n/g, '<br/>').replace(/\n/g, '<br/>').replace(/\r/g, '<br/>') + "</td></tr>";

        }
    }
    if(undefined != data.errorid){
        if(data.errorid != null && data.errorid != ''){
            //str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>"+data.errorid+"</td></tr>";
            str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'><a href=\"common/download.do?id="+data.errorid+"\" target=\"_blank\">"+"点击下载出错记录"+"</a></td></tr>";
        }
    }
    if(data.exception){
        str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all; color: #F00;'>" + data.exception + "</td></tr>";
    }
    if(undefined != data.datafileid){
        if(data.datafileid != null && data.datafileid != ''){
            //str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;'>"+data.errorid+"</td></tr>";
            str += "<tr><td style='TABLE-LAYOUT:fixed;word-break:break-all;line-height: 30px;text-align: center;'><a href=\"common/download.do?id="+data.datafileid+"\" target=\"_blank\" style='font-weight: bold'>》点击下载文件《</a></td></tr>";
        }
    }
	var tableend = "</table>";
   	$(tablestar+str+tableend).appendTo("#importExcel-div-returndata");
}

function returnExcelMsg(data){
	if($("#excel-returnmsg-dialog").length < 1){
		$("body").append("<div id='excel-returnmsg-dialog'></div>");
	}
	$("#excel-returnmsg-dialog").dialog({
		href: 'common/returnExcelMsgPage.do',
		width: '350',
		height: '200',
		title: '提醒',
		colsed: true,
		cache: false,
		modal: true,
		onLoad: function(){
			appendDataExcelInfo(data);
		},
        onClose:function(){
            //销售区域
            var salesarea_title = tabsWindowTitle('/basefiles/salesArea.do');
            //客户分类
            var customersort_title = tabsWindowTitle('/basefiles/customerSort.do');
            //采购区域
            var buyarea_title = tabsWindowTitle('/basefiles/buyArea.do');
            //供应商分类
            var suppliersort_title = tabsWindowTitle('/basefiles/buySupplierSort.do');
            //费用分类
            var expensesSort_title = tabsWindowTitle('/basefiles/finance/expensesSortPage.do');
            //库位档案
            var storageLocation_title = tabsWindowTitle('/basefiles/showStorageLocationPage.do');
            var import_title = top.getNowTabTitle();
            if (top.$('#tt').tabs('exists',import_title) &&
                (import_title == salesarea_title || import_title == customersort_title ||
                    import_title == buyarea_title || import_title == suppliersort_title ||
                    import_title == expensesSort_title || import_title  == storageLocation_title)){
                top.$('#tt').tabs('select', import_title);
                var import_tab = top.$('#tt').tabs('getTab',import_title);
                top.refresh(import_tab);
            }
        }
	});
}

$.ExcelImport.defaults = {
	width: 400,
	height: 300,
	title: 'Excel导入',
	onComplete: function(data){},
	onClose: function(){}
};
$.ExcelExport.defaults = {
	width: 400,
	height: 300,
	title: 'Excel导出'
};
$.ExcelImportMore.defaults = {
	width: 400,
	height: 300,
	title: 'Excel多表导入',
	onComplete: function(data){},
	onClose: function(){}
};
$.ExcelExportMore.defaults = {
	width: 400,
	height: 300,
	title: 'Excel多表导出'
};
$.ExcelImportBill.defaults = {
	width: 400,
	height: 300,
	title: 'Excel单据导入',
	onComplete: function(data){},
	onClose: function(){}
};
$.ExcelImportUserdefined.defaults = {
	width: 400,
	height: 300,
	title: 'Excel单据导入',
	onComplete: function(data){},
	onClose: function(){}
};
$.ExcelExportBill.defaults = {
	width: 400,
	height: 300,
	title: 'Excel单据导出'
};
$.ExcelExportUserdefined.defaults = {
	width: 400,
	height: 300,
	title: 'Excel单据导出'
}
