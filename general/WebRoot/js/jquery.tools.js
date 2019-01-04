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
                    importExcelForm(options.isShowImportMsg)
                );
            },
            onClose: function(){
                options.onClose();
            }
        });
        importExcelForm = function(isShowImportMsg){
            if(isShowImportMsg==null){
                isShowImportMsg=true;
            }
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

(function(){
	$.fn.extend({
		upload: function(options, suboptions){
			if(options == "delete"){
				var $d = $("#z-upload-virtualDelete-input");
				if($d.length < 1){

				}
				else{
					var dels = $d.val();
					$.get('common/deleteAttachFile.do', {"id": dels}, function(json){});
				}
			}
			else if(typeof options === 'object' || !options){
				var noImgShow = $.extend({}, $.upload.noImgShow, {}, options.noImgShow);
				options = $.extend({}, $.upload.defaults, options, {
					noImgShow: noImgShow
				});
				return this.each(function(){
					new $.upload(this, options);
				});
			}
		}
	});
})(jQuery);
$.upload = function(btn, options){
	var $btn = $(btn);
	$btn.unbind("click");
	var z_upload_reload = true;
	$btn.bind("click",function(){
		if($btn.hasClass("easyui-linkbutton") && $btn.linkbutton('options').disabled){
			return false;
		}
		if($("#z-upload-dialog").length < 1){
			$("body").append("<div id='z-upload-dialog'></div>");
		}
		if($("#z-upload-virtualDelete-input").length < 1){
			$("body").append("<input type='hidden' id='z-upload-virtualDelete-input' />");
		}
		if(z_upload_reload){
			$("#z-upload-virtualDelete-input").val("");
			$("#z-upload-dialog").dialog({
				href: "common/uploadPage.do?type="+ options.type,
				width: options.width,
				height: options.height,
				title: options.title,
			    colsed: false,
				cache: false,
				modal: true,
				onLoad: function(){
					var $fileList = $("#z-upload-dialog").find(".fileList");
					$fileList.html("");
					if($fileList.length > 0){
						$fileList.append("<ul style='margin:0;padding:5px;list-style:none;'></ul>");
						var ids = options.ids.split(',');
						for(var i = 0; i<ids.length; i++){
							var id = ids[i];
							if(id != null && id != ""){
								$.get(
									"common/getAttachFile.do",
									{id: id},
									function(data){
										if(data != null && data != "null"){
											var json = $.parseJSON(data);
											var li = new Array();
											li.push("<li style='line-height:25px;clear:both;padding:0 5px;cursor: pointer;' onmouseover='$.upload.handleMouseOver(this);' onmouseout='$.upload.handleMouseOut(this);' ondblclick='$.upload.handleDBClick("+json.file.id+");'>");
											li.push("<a onclick='$.upload.handleDBClick("+json.file.id+");' href='javaScript:void(0); class='span1' style='width:300px;float:left;'>"+ json.file.oldfilename +"</a");
											li.push("<span class='span2' style='display:none'>");
											li.push("<a onclick='$.upload.handleDBClick("+json.file.id+");' href='javaScript:void(0);'>查看</a> <a href='common/download.do?id="+json.file.id+"'>下载</a> ");
											if(options.virtualDel){
												li.push("<a href='javascript:;' onclick='$.upload.virtualDelete(this, "+json.file.id+");'>删除</a> ");
											}
											li.push("</li>");
											$fileList.find("ul").append(li.join(""));
										}
									}
								);
							}
						}
					}
					if(options.type == 'upload'){
						$(".fileList").hide();
						$(".fileUpload").show();
					}
					else if(options.type == 'list'){
						$(".fileList").show();
						$(".fileUpload").hide();
					}
					else{
						$(".fileList").show();
						$(".fileUpload").show();
					}
				}
			});
			z_upload_reload = false;
		}
		else{
			$("#z-upload-dialog").dialog('open');
		}
		$.upload.onComplete = function(data){
			if(options.attaInput == ''){
				options.onComplete(data);
			}
			else{
				var $input = $(options.attaInput);
				if($input.val() == ''){
					$input.val(data.id);
				}
				else{
					$input.val($input.val() + ',' + data.id);
				}
			}
		};
		$.upload.onDelete = function(json){
			if(options.attaInput == ''){
				options.onDelete(json);
			}
			else{
				if(json.flag == true){
					var deleteId = json.file.id;
					var $input = $(options.attaInput);
					var ids = $input.val();
					var idArr = ids.split(',');
					var idTemp = new Array();
					for(var i = 0; i<idArr.length; i++){
						if(deleteId == idArr[i]){

						}
						else{
							idTemp.push(idArr[i]);
						}
					}
					$input.val(idTemp.join(","));
				}
				else{
					$.messager.alert("提醒","删除失败");
				}
			}
		};
		$.upload.options = function(){
			return options;
		};
		$.upload.virtualDelete = function(obj, id){
			$.messager.confirm("提醒", "确定删除该文件？该删除操作在保存后生效。", function(r){
				if(r){
					if(options.attaInput == ''){
						options.onVirtualDelete(id);
					}
					else{
						var $dinput = $("#z-upload-virtualDelete-input"); //保存删除的文件编号
						var $input = $(options.attaInput); //保存文件编号
						var ids = $input.val();
						var idArr = ids.split(',');
						var idTemp = new Array();
						for(var i = 0; i<idArr.length; i++){
							if(id == idArr[i]){

							}
							else{
								idTemp.push(idArr[i]);
							}
						}
						$input.val(idTemp.join(","));
						if($dinput.val() == ''){
							$dinput.val(id);
						}
						else{
							$dinput.val($dinput.val() + ',' + id);
						}
					}
					$(obj).parent().parent().fadeOut(1000);
				}
			});
		};
	});
};
$.upload.defaults = {
	width: 522,
	height: 435,
	title: "附件",
	auto: false,
	del: true,
	virtualDel: true,
	type: '',
	allowType: '',
	ids: '',
	noImgShow: {},
	attaInput: '',
	onComplete: function(data){},
	onDelete: function(data){},
	onVirtualDelete: function(id){}
};
$.upload.noImgShow = {
	"default":"/common/upload/default.png",
	".doc":"/common/upload/doc.png"
};
$.upload.onComplete = function(data){};
$.upload.onDelete = function(file){};
$.upload.options = function(){};
$.upload.handleMouseOver = function(obj){
	var $obj = $(obj);
	$obj.css({"background": "#efefef"});
	$obj.children(".span2").show();
};
$.upload.handleMouseOut = function(obj){
	var $obj = $(obj);
	$obj.css({"background":""});
	$obj.children(".span2").hide();
};
$.upload.handleDBClick = function(id){
//	$.ajax({
//        url :'common/viewFile.do?id='+id,
//        type:'post',
//        dataType:'json',
//        success:function(json){
//        	if(null!=json.file){
//        		window.open(json.file.htmlpath)
//        	}else{
//        		$.messager.alert("提醒", "该文件不能在线查看！");
//        	}
//		}
//    });
	var path = $("#basePath").attr("href");
    window.open(path+"common/viewFileFlash.do?id="+id);
};

(function(){
	$.fn.extend({
		webuploader:function(options, suboptions){
			options = $.extend({}, $.webuploader.defaults,options);
			return this.each(function(){
				new $.webuploader(this, options);
			});
		}
	});
})(jQuery);

function refreshWebuploderDialog(){
    $("#z-webuploader-dialog").dialog('refresh');
}

var _webuploder_option;
$.webuploader = function(btn, options){
	var $btn = $(btn);
	$btn.unbind("click");
	var z_webuploader_reload = true;
	$btn.bind("click",function(){
		if($btn.hasClass("easyui-linkbutton") && $btn.linkbutton('options').disabled){
			return false;
		}
		if($("#z-webuploader-dialog").length < 1){
			$("body").append("<div id='z-webuploader-dialog' style='overflow-x: hidden;'></div>");
		}
		if(z_webuploader_reload){

			_webuploder_option = options;

			var _option_description = "";
			if(undefined != options.description && '' != options.description){
				_option_description = options.description;
			}

			$("#z-webuploader-dialog").dialog({
				href: "common/showCommonWebuploaderPage.do",
			    //fit:true,
				queryParams:{description:_option_description},
				width: options.width,
				height: options.height,
				title: options.title,
			    close: false,
				cache: false,
				modal: true,
                onBeforeLoad:function(){
                    options.onBeforeLoad();
                },
                onLoad:function(){
                    options.onLoad();
					setTimeout(function(){
						initLoadWebuploader();
					},50)
                }
			});
			z_webuploader_reload = false;
		}
		else{
			$("#z-webuploader-dialog").dialog('open');
		}
		$.webuploader.getOptions = function(){
			return options;
		};
	});
}

$.webuploader.defaults = {
		width: 600,
		height: 400,
		//fit:true,
		title: "文件上传",
		filetype: '',
		allowType: '',
		mimeTypes:'',
		attaInput:'',
		url:'',
		disableGlobalDnd:true,
		close:false,
		description:'',
        converthtml:false,
        convertpdf:false,
		formData:{},
		fileObjName:'',
		onComplete: function(data){},
        onBeforeLoad: function(){},
        onLoad:function(){}
	};
$.webuploader.options={};
$.webuploader.onComplete = function(data){};
$.webuploader.onBeforeLoad = function(){};
$.webuploader.onLoad = function(){};
$.webuploader.getOptions = function(){};
$.webuploader.setFormData=function(value){
	if(typeof _webuploder_option === 'object' && _webuploder_option && typeof value==='object'){
		var formData=$.extend({}, _webuploder_option.formData,value);
		_webuploder_option.formData=formData;
	}
};
$.webuploader.openShowFileViewer = function(id){
//	$.ajax({
//        url :'common/viewFile.do?id='+id,
//        type:'post',
//        dataType:'json',
//        success:function(json){
//        	if(null!=json.file){
//        		window.open(json.file.htmlpath)
//        	}else{
//        		$.messager.alert("提醒", "该文件不能在线查看！");
//        	}
//		}
//    });
	var path = $("#basePath").attr("href");
    window.open(path+"common/viewFileFlash.do?id="+id);
};
$.webuploader.showAttachListDialog=function(idarrs,dialogTitle){

	if(idarrs==null || $.trim(idarrs)==""){
		return false;
	}
	if(dialogTitle==null || $.trim(dialogTitle)==""){
		dialogTitle="查看附件列表";
	}
	if($("#z-webuploader-dialog-attachViewList").length > 0){
		$("#z-webuploader-dialog-attachViewList").remove();
	}
	$("body").append("<div id='z-webuploader-dialog-attachViewList' style='overflow-x: hidden;'></div>");
	var $attachViewListDailog=$("#z-webuploader-dialog-attachViewList");
	$attachViewListDailog.append("<ul style='margin:0;padding:5px;list-style:none;'></ul>");
	idarrs=$.trim(idarrs);
	$.ajax({
		url:"common/getAttachFileList.do",
		data:{idarrs: idarrs},
		async:true,
		type:'POST',
		dataType:"text",
		success:function(data){
			var lisb=new Array();
			if(data != null && data != "null"){
				var json = $.parseJSON(data);
				for(var i=0;i<json.length;i++){
					lisb.push("<li style='line-height:25px;clear:both;padding:0 5px;cursor: pointer;' onmouseover='style.backgroundColor=\"#efefef\"' onmouseout='style.backgroundColor=\"\"' ondblclick='$.webuploader.openShowFileViewer("+json[i].id+");'>");
					lisb.push("<a onclick='$.webuploader.openShowFileViewer("+json[i].id+");' href='javaScript:void(0); class='span1' style='width:300px;float:left;'>"+ json[i].oldfilename +"</a");
					lisb.push("<span class='span2' style='display:none'>");
					lisb.push("<a onclick='$.webuploader.openShowFileViewer("+json[i].id+");' href='javaScript:void(0);'>查看</a> <a href='common/download.do?id="+json[i].id+"'>下载</a> ");
					lisb.push("</li>");
				};
				$attachViewListDailog.find("ul").append(lisb.join(""));
			}
			if(lisb.length==0){
				$attachViewListDailog.append("<div style=\"padding:10px;\">抱歉，未能找到相关资源（相关资源可能被删除）。</div>")
			}
			$("#z-webuploader-dialog-attachViewList").dialog({
			    //fit:true,
				width: 522,
				height: 435,
				title: dialogTitle,
			    close: true,
				cache: false,
				modal: true,
			    onClose:function(){
			    	$("#z-webuploader-dialog-attachViewList").window("destroy");
			    }
			});
			$("#z-webuploader-dialog-attachViewList").dialog('open');
		}
	});


} //销售情况统计表选中合计
 function baseSalesReportCountTotalAmount(col,datagrid){
 	var rows =  $("#"+datagrid.id+"").datagrid('getChecked');
	var ordernum = 0;
	var ordertotalbox = 0;
	var orderamount = 0;
	var ordernotaxamount=0;
	var initsendnum = 0;
	var initsendtotalbox = 0;
	var initsendamount = 0;
	var initsendnotaxamount=0;
	var sendnum = 0;
	var sendtotalbox = 0;
	var sendamount = 0;
	var pushbalanceamount = 0;
	var sendnotaxamount = 0;
	var sendcostamount = 0;
	var directreturnnum=0;
	var directreturntotalbox = 0;
	var directreturnamount =0;
	var checkreturnnum=0;
	var checkreturntotalbox = 0;
	var checkreturnamount=0;
	var returnnum =0;
	var returntotalbox = 0;
	var returnamount = 0;
	var salenum = 0;
	var saletotalbox = 0;
	var saleamount = 0;
	var salenotaxamount = 0;
	var saletax = 0;
	var costamount = 0;
	var salemarginamount = 0;
	for(var i=0;i<rows.length;i++){
		ordernum = Number(ordernum)+Number(rows[i].ordernum == undefined ? 0 : rows[i].ordernum);
		ordertotalbox = Number(ordertotalbox)+Number(rows[i].ordertotalbox == undefined ? 0 : rows[i].ordertotalbox);
		orderamount = Number(orderamount)+Number(rows[i].orderamount == undefined ? 0 : rows[i].orderamount);
		ordernotaxamount = Number(ordernotaxamount)+Number(rows[i].ordernotaxamount == undefined ? 0 : rows[i].ordernotaxamount);
		initsendnum = Number(initsendnum)+Number(rows[i].initsendnum == undefined ? 0 : rows[i].initsendnum);
		initsendtotalbox = Number(initsendtotalbox)+Number(rows[i].initsendtotalbox == undefined ? 0 : rows[i].initsendtotalbox);
		initsendamount = Number(initsendamount)+Number(rows[i].initsendamount == undefined ? 0 : rows[i].initsendamount);
		initsendnotaxamount = Number(initsendnotaxamount)+Number(rows[i].initsendnotaxamount == undefined ? 0 : rows[i].initsendnotaxamount);
		sendnum = Number(sendnum)+Number(rows[i].sendnum == undefined ? 0 : rows[i].sendnum);
		sendtotalbox = Number(sendtotalbox)+Number(rows[i].sendtotalbox == undefined ? 0 : rows[i].sendtotalbox);
		sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
		pushbalanceamount = Number(pushbalanceamount)+Number(rows[i].pushbalanceamount == undefined ? 0 : rows[i].pushbalanceamount);
		sendnotaxamount = Number(sendnotaxamount)+Number(rows[i].sendnotaxamount == undefined ? 0 : rows[i].sendnotaxamount);
		sendcostamount = Number(sendcostamount)+Number(rows[i].sendcostamount == undefined ? 0 : rows[i].sendcostamount);
		directreturnnum = Number(directreturnnum)+Number(rows[i].directreturnnum == undefined ? 0 : rows[i].directreturnnum);
		directreturntotalbox = Number(directreturntotalbox)+Number(rows[i].directreturntotalbox == undefined ? 0 : rows[i].directreturntotalbox);
		directreturnamount = Number(directreturnamount)+Number(rows[i].directreturnamount == undefined ? 0 : rows[i].directreturnamount);
		checkreturnnum = Number(checkreturnnum)+Number(rows[i].checkreturnnum == undefined ? 0 : rows[i].checkreturnnum);
		checkreturntotalbox = Number(checkreturntotalbox)+Number(rows[i].checkreturntotalbox == undefined ? 0 : rows[i].checkreturntotalbox);
		checkreturnamount = Number(checkreturnamount)+Number(rows[i].checkreturnamount == undefined ? 0 : rows[i].checkreturnamount);
		returnnum = Number(returnnum)+Number(rows[i].returnnum == undefined ? 0 : rows[i].returnnum);
		returntotalbox = Number(returntotalbox)+Number(rows[i].returntotalbox == undefined ? 0 : rows[i].returntotalbox);
		returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
		salenum = Number(salenum)+Number(rows[i].salenum == undefined ? 0 : rows[i].salenum);
		saletotalbox = Number(saletotalbox)+Number(rows[i].saletotalbox == undefined ? 0 : rows[i].saletotalbox);
		saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
		salenotaxamount = Number(salenotaxamount)+Number(rows[i].salenotaxamount == undefined ? 0 : rows[i].salenotaxamount);
		saletax = Number(saletax)+Number(rows[i].saletax == undefined ? 0 : rows[i].saletax);
		costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
		salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
	}
	var obj = {ordernum:ordernum,ordertotalbox:ordertotalbox,orderamount:orderamount,ordernotaxamount:ordernotaxamount,
				initsendnum:initsendnum,initsendtotalbox:initsendtotalbox,initsendamount:initsendamount,initsendnotaxamount:initsendnotaxamount,
				sendnum:sendnum,sendtotalbox:sendtotalbox,sendamount:sendamount,pushbalanceamount:pushbalanceamount,sendnotaxamount:sendnotaxamount,sendcostamount:sendcostamount,
				directreturnnum:directreturnnum,directreturntotalbox:directreturntotalbox,directreturnamount:directreturnamount,
				checkreturnnum:checkreturnnum,checkreturntotalbox:checkreturntotalbox,checkreturnamount:checkreturnamount,
				returnnum:returnnum,returntotalbox:returntotalbox,returnamount:returnamount,
				salenum:salenum,saletotalbox:saletotalbox,saleamount:saleamount,salenotaxamount:salenotaxamount,saletax:saletax,costamount:costamount,salemarginamount:salemarginamount
   			};
   	if(col != ""){
		obj[col] = '选中合计';
	}else{
		obj['goodsname'] = '选中合计';
	}
	var foot=[];
	foot.push(obj);
	if(null!=SR_footerobject){
   		foot.push(SR_footerobject);
	}
	$("#"+datagrid.id+"").datagrid("reloadFooter",foot);
 }

 //资金回笼情况表选中合计
 function baseFinanceWithDrawnCountTotalAmount(col,datagrid){
	var rows =  $("#"+datagrid.id+"").datagrid('getChecked');
	var withdrawnamount = 0;
	var costwriteoffamount = 0;
	var writeoffmarginamount=0;
	for(var i=0;i<rows.length;i++){
		withdrawnamount = Number(withdrawnamount)+Number(rows[i].withdrawnamount == undefined ? 0 : rows[i].withdrawnamount);
		costwriteoffamount = Number(costwriteoffamount)+Number(rows[i].costwriteoffamount == undefined ? 0 : rows[i].costwriteoffamount);
		writeoffmarginamount = Number(writeoffmarginamount)+Number(rows[i].writeoffmarginamount == undefined ? 0 : rows[i].writeoffmarginamount);
	}
	var obj = {withdrawnamount:withdrawnamount,costwriteoffamount:costwriteoffamount,writeoffmarginamount:writeoffmarginamount};
 	if(col != ""){
		obj[col] = '选中合计';
	}else{
		obj['goodsname'] = '选中合计';
	}
	var foot=[];
	foot.push(obj);
	if(null!=SR_footerobject){
	  	foot.push(SR_footerobject);
	}
  	$("#"+datagrid.id+"").datagrid("reloadFooter",foot);
}

 //销售回笼考核报表选中合计
 function salesWithdrawnAssessCountTotalAmount(col,datagrid){
	 var rows =  $("#"+datagrid.id+"").datagrid('getChecked');
	 var saletargetamount = 0;
	 var saleamount = 0;
	 var saledonerate = 0;
	 var salemarginamount = 0;
	 //选中合计的目标毛利
	 var salemargintargetamountsum = 0;
	 var salemargindonesurpassrate = 0;
	 var withdrawntargetamount = 0;
	 var withdrawnamount = 0;
	 var writeoffmarginamount = 0;
	 var withdrawndonerate = 0;
	 //选中合计的回笼目标毛利
	 var withdrawnmargintargetamountsum = 0;
	 var withdrawnmargindonesurpassrate = 0;
	 var writeoffrate = 0;
	 var realrate = 0;
	 for(var i=0;i<rows.length;i++){
		 saletargetamount = Number(saletargetamount)+Number(rows[i].saletargetamount == undefined ? 0 : rows[i].saletargetamount);
		 saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
		 salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
		 //本期目标毛利=本期销售目标*本期毛利率目标
		 var marginratetarget = Number(rows[i].marginratetarget == undefined ? 0 : rows[i].marginratetarget);
		 var salemargintargetamount = Number(rows[i].saletargetamount == undefined ? 0 : rows[i].saletargetamount)*(marginratetarget/Number(100));
		 salemargintargetamountsum = Number(salemargintargetamountsum)+Number(salemargintargetamount);

		 withdrawntargetamount = Number(withdrawntargetamount)+Number(rows[i].withdrawntargetamount == undefined ? 0 : rows[i].withdrawntargetamount);
		 withdrawnamount = Number(withdrawnamount)+Number(rows[i].withdrawnamount == undefined ? 0 : rows[i].withdrawnamount);
		 writeoffmarginamount = Number(writeoffmarginamount)+Number(rows[i].writeoffmarginamount == undefined ? 0 : rows[i].writeoffmarginamount);
		 //回笼目标毛利=回笼目标*回笼毛利率目标
		 var writeoffratetarget = Number(rows[i].writeoffratetarget == undefined ? 0 : rows[i].writeoffratetarget);
		 var withdrawnmargintargetamount = Number(rows[i].withdrawntargetamount == undefined ? 0 : rows[i].withdrawntargetamount)*(writeoffratetarget/Number(100))
		 withdrawnmargintargetamountsum = Number(withdrawnmargintargetamountsum)+Number(withdrawnmargintargetamount);
	 }
	 //实际毛利率=销售毛利额/销售金额*100
	 if(Number(saleamount) != Number(0)){
		 realrate = Number(salemarginamount)/Number(saleamount)*Number(100);
	 }
	 //回笼毛利率= 回笼毛利额/回笼金额*100
	 if(Number(withdrawnamount) != Number(0)){
		 writeoffrate = Number(writeoffmarginamount)/Number(withdrawnamount)*Number(100);
	 }
	 //销售完成率=销售金额/本期销售目标
	 if(Number(saletargetamount) != Number(0)){
		 if(Number(saleamount)>= Number(0) && Number(saletargetamount)>Number(0)){
			 saledonerate = saleamount/saletargetamount*Number(100);
		 }else if(saleamount >= saletargetamount){
			 saledonerate = 100;
		 }else{
			 saledonerate = (saleamount-saletargetamount)/Math.abs(saletargetamount)*Number(100);
		 }
	 }
	 //销售业绩超率
	 if(Number(salemargintargetamountsum) >Number(0) &&  Number(salemarginamount) >= Number(0)){
		 salemargindonesurpassrate = salemarginamount / salemargintargetamountsum * Number(100);
	 }else if(Number(salemargintargetamountsum) != Number(0)) {
		 if (Number(salemargintargetamountsum) <= Number(salemarginamount)) {
			 salemargindonesurpassrate = 100;
		 } else {
			 var mindata = Number(salemarginamount) - Number(salemargintargetamountsum);
			 salemargindonesurpassrate = mindata / Math.abs(salemargintargetamountsum) * Number(100);
		 }
	 }
	 //回笼完成率=回笼金额/回笼目标
	 if(Number(withdrawntargetamount) > Number(0) && Number(withdrawnamount) >= Number(0)){
		 withdrawndonerate = withdrawnamount/withdrawntargetamount*Number(100);
	 }else if(Number(withdrawntargetamount) != Number(0)){
		 if(Number(withdrawnamount)<Number(withdrawntargetamount)){
			 withdrawndonerate = (withdrawnamount - withdrawntargetamount)/Math.abs(withdrawntargetamount)*Number(100);
		 }else {
			 withdrawndonerate = 100;
		 }
	 }
	 //回笼业绩超率=回笼毛利额/回笼目标毛利
	 if(Number(withdrawnmargintargetamountsum) > Number(0) && Number(writeoffmarginamount)>= Number(0)){
		 withdrawnmargindonesurpassrate = writeoffmarginamount/withdrawnmargintargetamountsum*Number(100);
	 }else if(Number(withdrawnmargintargetamountsum) != Number(0)){
		 if(Number(withdrawnmargintargetamountsum) > Number(writeoffmarginamount)){
			 withdrawnmargindonesurpassrate = (writeoffmarginamount-withdrawnmargintargetamountsum)/Math.abs(withdrawnmargintargetamountsum)*Number(100);
		 }else{
			 withdrawnmargindonesurpassrate = 100;
		 }
	 }
	 var obj={saletargetamount:saletargetamount,saleamount:saleamount,saledonerate:saledonerate,salemarginamount:salemarginamount,salemargintargetamount:salemargintargetamount,salemargindonesurpassrate:salemargindonesurpassrate,
		 withdrawntargetamount:withdrawntargetamount,withdrawnamount:withdrawnamount,writeoffmarginamount:writeoffmarginamount,withdrawnmargintargetamount:withdrawnmargintargetamount,withdrawndonerate:withdrawndonerate,
		 withdrawnmargindonesurpassrate:withdrawnmargindonesurpassrate,writeoffrate:writeoffrate,realrate:realrate
	 };
	 obj[col] = '选中合计';

	 var foot=[];
	 foot.push(obj);
	 if(null!=footerobject){
		 foot.push(footerobject);
	 }
	 $("#"+datagrid.id+"").datagrid("reloadFooter",foot);
 }


 //实际销售报表选中合计
 function baseRealSalesReportCountTotalAmount(col,datagrid){
	 var rows =  $("#"+datagrid.id+"").datagrid('getChecked');
	 var sendnum = 0;
	 var realsendnum = 0;
	 var sendtotalbox = 0;
	 var realsendtotalbox = 0;
	 var sendamount = 0;
	 var realsendamount = 0;
	 var pushbalanceamount = 0;
	 var realpushbalanceamount = 0;
	 var sendnotaxamount = 0;
	 var realsendnotaxamount = 0;
	 var returnnum =0;
	 var realreturnnum =0;
	 var returntotalbox = 0;
	 var realreturntotalbox = 0;
	 var returnamount = 0;
	 var realreturnamount = 0;
	 var salenum = 0;
	 var realsalenum = 0;
	 var saletotalbox = 0;
	 var realsaletotalbox = 0;
	 var saleamount = 0;
	 var realsaleamount = 0;
	 var salenotaxamount = 0;
	 var realsalenotaxamount = 0;
	 var saletax = 0;
	 var realsaletax = 0;
	 var costamount = 0;
	 var realcostamount = 0;
	 var nocostamount = 0;
	 var realnocostamount = 0;
	 var salemarginamount = 0;
	 var realsalemarginamount = 0;
	 var salenomarginamount = 0;
	 var realsalenomarginamount = 0;
	 for(var i=0;i<rows.length;i++){
		 sendnum = Number(sendnum)+Number(rows[i].sendnum == undefined ? 0 : rows[i].sendnum);
		 realsendnum = Number(realsendnum)+Number(rows[i].realsendnum == undefined ? 0 : rows[i].realsendnum);
		 sendtotalbox = Number(sendtotalbox)+Number(rows[i].sendtotalbox == undefined ? 0 : rows[i].sendtotalbox);
		 realsendtotalbox = Number(realsendtotalbox)+Number(rows[i].realsendtotalbox == undefined ? 0 : rows[i].realsendtotalbox);
		 sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
		 realsendamount = Number(realsendamount)+Number(rows[i].realsendamount == undefined ? 0 : rows[i].realsendamount);
		 pushbalanceamount = Number(pushbalanceamount)+Number(rows[i].pushbalanceamount == undefined ? 0 : rows[i].pushbalanceamount);
		 realpushbalanceamount = Number(realpushbalanceamount)+Number(rows[i].realpushbalanceamount == undefined ? 0 : rows[i].realpushbalanceamount);
		 sendnotaxamount = Number(sendnotaxamount)+Number(rows[i].sendnotaxamount == undefined ? 0 : rows[i].sendnotaxamount);
		 realsendnotaxamount = Number(realsendnotaxamount)+Number(rows[i].realsendnotaxamount == undefined ? 0 : rows[i].realsendnotaxamount);
		 returnnum = Number(returnnum)+Number(rows[i].returnnum == undefined ? 0 : rows[i].returnnum);
		 realreturnnum = Number(realreturnnum)+Number(rows[i].realreturnnum == undefined ? 0 : rows[i].realreturnnum);
		 returntotalbox = Number(returntotalbox)+Number(rows[i].returntotalbox == undefined ? 0 : rows[i].returntotalbox);
		 realreturntotalbox = Number(realreturntotalbox)+Number(rows[i].realreturntotalbox == undefined ? 0 : rows[i].realreturntotalbox);
		 returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
		 realreturnamount = Number(realreturnamount)+Number(rows[i].realreturnamount == undefined ? 0 : rows[i].realreturnamount);
		 salenum = Number(salenum)+Number(rows[i].salenum == undefined ? 0 : rows[i].salenum);
		 realsalenum = Number(realsalenum)+Number(rows[i].realsalenum == undefined ? 0 : rows[i].realsalenum);
		 saletotalbox = Number(saletotalbox)+Number(rows[i].saletotalbox == undefined ? 0 : rows[i].saletotalbox);
		 realsaletotalbox = Number(realsaletotalbox)+Number(rows[i].realsaletotalbox == undefined ? 0 : rows[i].realsaletotalbox);
		 saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
		 realsaleamount = Number( realsaleamount)+Number(rows[i]. realsaleamount == undefined ? 0 : rows[i]. realsaleamount);
		 salenotaxamount = Number(salenotaxamount)+Number(rows[i].salenotaxamount == undefined ? 0 : rows[i].salenotaxamount);
		 realsalenotaxamount = Number(realsalenotaxamount)+Number(rows[i].realsalenotaxamount == undefined ? 0 : rows[i].realsalenotaxamount);
		 saletax = Number(saletax)+Number(rows[i].saletax == undefined ? 0 : rows[i].saletax);
		 realsaletax = Number(realsaletax)+Number(rows[i].realsaletax == undefined ? 0 : rows[i].realsaletax);
		 costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
		 realcostamount = Number(realcostamount)+Number(rows[i].realcostamount == undefined ? 0 : rows[i].realcostamount);
		 nocostamount = Number(nocostamount)+Number(rows[i].nocostamount == undefined ? 0 : rows[i].nocostamount);
		 realnocostamount = Number(realnocostamount)+Number(rows[i].realnocostamount == undefined ? 0 : rows[i].realnocostamount);
		 salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
		 realsalemarginamount = Number(realsalemarginamount)+Number(rows[i].realsalemarginamount == undefined ? 0 : rows[i].realsalemarginamount);
		 salenomarginamount = Number(salenomarginamount)+Number(rows[i].salenomarginamount == undefined ? 0 : rows[i].salenomarginamount);
		 realsalenomarginamount = Number(realsalenomarginamount)+Number(rows[i].realsalenomarginamount == undefined ? 0 : rows[i].realsalenomarginamount);
	 }
	 var obj = {sendnum:sendnum,realsendnum:realsendnum,sendtotalbox:sendtotalbox,realsendtotalbox:realsendtotalbox,sendamount:sendamount,realsendamount:realsendamount,
		 pushbalanceamount:pushbalanceamount,realpushbalanceamount:realpushbalanceamount,realsendnotaxamount:realsendnotaxamount,
		 returnnum:returnnum,realreturnnum:realreturnnum,returntotalbox:returntotalbox,realreturntotalbox:realreturntotalbox,returnamount:returnamount,realreturnamount:realreturnamount,
		 salenum:salenum,realsalenum:realsalenum,saletotalbox:Number(saletotalbox).toFixed(3),realsaletotalbox:Number(realsaletotalbox).toFixed(3),
		 saleamount:saleamount,realsaleamount:realsaleamount,salenotaxamount:salenotaxamount,realsalenotaxamount:realsalenotaxamount,saletax:saletax,realsaletax:realsaletax,
		 costamount:costamount,realcostamount:realcostamount,nocostamount:nocostamount,realnocostamount:realnocostamount,salemarginamount:salemarginamount,
		 realsalemarginamount:realsalemarginamount,salenomarginamount:salenomarginamount,realsalenomarginamount:realsalenomarginamount
	 };
	 if(col != ""){
		 obj[col] = '选中合计';
	 }else{
		 obj['goodsname'] = '选中合计';
	 }
	 var foot=[];
	 foot.push(obj);
	 if(null!=SR_footerobject){
		 foot.push(SR_footerobject);
	 }
	 $("#"+datagrid.id+"").datagrid("reloadFooter",foot);
 }
