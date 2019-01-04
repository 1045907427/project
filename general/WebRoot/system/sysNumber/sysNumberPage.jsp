<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>单据编号设置</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  	<input type="hidden" id="system-operate-sysNumber"/>
  	<input type="hidden" id="system-numberid-sysNumberList"/>
  	<input type="hidden" id="system-numberruleid-sysNumberRuleList"/>
  	<input type="hidden" id="system-isEdit-sysNumberRuleList" value="0"/>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="sysNumber-button-billtype"></div>
    	</div>
    	<div data-options="region:'center'">
    		<div class="easyui-layout" fit="true" id="sysNumber-layout-bill">
				<div title="单据类型" data-options="region:'west',split:true" style="width:330px;">
                    <div id="sysNumber-toolbar-billquery" style="padding:2px;height:auto">
                        <form action="" method="post" id="sysNumber-form-billquery" onsubmit="return false">
                            单据名称:<input name="billname" type="text" style="width: 120px;"/>
                            <a href="javaScript:void(0);" id="sysNumber-query-List" class="button-qr">查询</a>
                            <%--<br/><br/>--%>
                            <%--<div style="padding-left:180px">--%>
                            <%--<a href="javaScript:void(0);" id="sysNumber-query-reloadList" class="button-qr">重置</a>--%>
                            <%--</div>--%>

                        </form>
                    </div>
					<table id="sysNumber-table-billName"></table>
				</div>
				<div data-options="region:'center'"></div>
			</div>
    	</div>
	</div>
	<div id="system-dialog-sysNumberRule">
	<script type="text/javascript">
		var sysNumberRuleJsonStr = "";
		
		var sysNumber_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return MyAjax.responseText;
		}
		
		//判断是否加锁
		function isLockData(id,tablename){
			var flag = false;
			$.ajax({
	            url :'system/lock/isLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
		}
		//加锁
	    function isDoLockData(id,tablename){
	    	var flag = false;
	    	$.ajax({   
	            url :'system/lock/isDoLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
	    }

        function refreshLayout(title, url, type){
            $("#sysNumber-layout-bill").layout('remove','center').layout('add',{
                region: 'center',
                title: title,
                href:url
            });
            $("#system-operate-sysNumber").val(type);
        }

        //移除焦点获取固定值的长度
        function fixedvalBlur(){
            if($("#system-fixedval-sysNumberRule").size() != 0){
                $("#system-fixedval-sysNumberRule").blur(function(){
                    if($("#system-fixedval-sysNumberRule").val() != ""){
                        $("#sysNumber-sysNumberRule-length").val(parseInt(this.value.length));
                    }
                });
            }else if($("#system-dateval-sysNumberRule").size() != 0){
                $("#system-dateval-sysNumberRule").blur(function(){
                    if($("#system-dateval-sysNumberRule").val() != ""){
                        $("#sysNumber-sysNumberRule-length").val(parseInt(this.value.length));
                    }
                });
            }
        }

        //获取编码规则流水依据值、值名称
        function getTestValueAndName(rows){
            var obj = {};
            if(rows.length != 0){
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    if(row.state == "1"){
                        obj = row;
                    }
                }
            }
            if(!isObjectEmpty(obj)){
                $("#system-testvalue-sysNumber").val(obj.val);
                $("#system-valName-sysNumber").val(obj.valName);
            }else{
                $("#system-testvalue-sysNumber").val("");
                $("#system-valName-sysNumber").val("");
            }
        }

        //打开单据编号规则信息修改页面
        function openSysNumberRuleEditDialog(row){
            var type = $("#system-operate-sysNumber").val();
            if("" != type && "view" != type){
                var tablename = $("#system-tablename-sysNumber").widget('getValue');
                $('<div id="system-dialog-sysNumberRule-edit"></div>').appendTo('#system-dialog-sysNumberRule');
                $("#system-dialog-sysNumberRule-edit").dialog({
                    title:'单据编号规则信息',
                    width:500,
                    height:240,
                    closed:true,
                    cache:false,
                    href:'sysNumber/sysNumberRuleEdit.do?tablename='+tablename,
                    modal:true,
                    onClose:function(){
                        $('#system-dialog-sysNumberRule-edit').dialog("destroy");
                    },
                    onLoad: function(){
                        var obj = {};
                        obj['sysNumberRule.coltype'] = row.coltype;
                        obj['sysNumberRule.cover'] = row.cover;
                        obj['sysNumberRule.length'] = row.length;
                        obj['sysNumberRule.prefix'] = row.prefix;
                        obj['sysNumberRule.sequencing'] = row.sequencing;
                        obj['sysNumberRule.state'] = row.state;
                        obj['sysNumberRule.substart'] = row.substart;
                        obj['sysNumberRule.subtype'] = row.subtype;
                        obj['sysNumberRule.suffix'] = row.suffix;
                        obj['sysNumberRule.val'] = row.val;
                        obj['sysNumberRule.valName'] = row.valName;
                        $("#sysNumberRule-form-addNumberRule").form('load', obj);
                    }
                });
                $("#system-dialog-sysNumberRule-edit").dialog("open");
            }
        }

        //判断流水依据是否允许变动
        function checkRuleStateCanChange(type){
            var coltype = $("#system-coltype-sysNumberRule").val();
            var textvalue = $("#system-testvalue-sysNumber").val();//流水依据字段
            if("1" == coltype){
                $("#sysNumber-sysNumberRule-state").val("0");
            }else if("2" == coltype){
                var coldatatype = $("#system-coldatatype-sysNumberRule").val();
                if("add" == type){
                    if(textvalue != ""){//存在流水依据
                        $("#sysNumber-sysNumberRule-state").val("0");
                    }else{
                        if("datetime" != coldatatype){
                            $("#sysNumber-sysNumberRule-state").val("0");
                        }
                    }
                }else if("edit" == type){
                    if("datetime" != coldatatype){
                        $("#sysNumber-sysNumberRule-state").val("0");
                    }
                }
            }else if("3" == coltype){
                if("add" == type && textvalue != ""){//存在流水依据
                    $("#sysNumber-sysNumberRule-state").val("0");
                }
            }
        }

        //单据编号规则列表
        function sysNumberRuleDataGrid(type){
            var url = '';
            var numberid = $("#system-numberid-sysNumber").val();
            if(type != "add"){
                url = 'sysNumber/getSysNumberRuleList.do?numberid='+numberid;
            }
            $dgSysNumberRuleList = $("#system-table-sysNumberRule");
            $('#system-table-sysNumberRule').datagrid({
                fit:true,
                title:'编码规则',
                toolbar:"#system-button-sysNumberRule",
                method:'post',
                rownumbers:false,
                pagination:false,
                idField:'numberruleid',
                singleSelect:true,
                url:url,
                columns:[[
                    {field:'numberruleid',title:'单据规则编码',hidden:true},
                    {field:'numberid',title:'单据编码',hidden:true},
                    {field:'sequencing',title:'排序',width:50},
                    {field:'coltype',title:'类型',width:80,
                        formatter:function(val){
                            switch (val) {
                                case "1":
                                    return "固定值";
                                case "2":
                                    return "字段";
                                case "3":
                                    return "系统日期";
                                default:
                                    break;
                            }
                        }
                    },
                    {field:'val',title:'值',width:130,
                        formatter:function(val,row,index){
                            if(row.valName != "" && row.valName != null){
                                return row.valName;
                            }
                            else{
                                return row.val;
                            }
                        }
                    },
                    {field:'prefix',title:'前缀',width:80},
                    {field:'suffix',title:'后缀',width:80},
                    {field:'length',title:'长度',width:80},
                    {field:'subtype',title:'截取方向',width:100,
                        formatter:function(val){
                            if(val=='1'){
                                return "从前向后";
                            }else if(val=='2'){
                                return "从后向前";
                            }
                        }
                    },
                    {field:'substart',title:'截取开始位置',width:100,resizable:true},
                    {field:'cover',title:'补位符',width:80},
                    {field:'state',title:'是否以该数据作为流水依据',width:200,
                        formatter:function(val){
                            if(val=='1'){
                                return "是";
                            }else{
                                return "否";
                            }
                        }
                    }
                ]],
                onClickRow:function(rowIndex, rowData){
                    var numberruleid = $("#system-numberruleid-sysNumberRuleList").val();
                    var type = $("#system-operate-sysNumber").val();
                    if(type == "add"){
                        sysNumberRuleJsonStr = JSON.stringify(rowData);
                    }
                    else{
                        if(numberruleid != rowData.numberruleid){
                            sysNumberRuleJsonStr = "";
                        }
                    }
                },
                onDblClickRow: function(rowIndex, rowData){
                    if(rowData.coltype != undefined && rowData.val != undefined){
                        openSysNumberRuleEditDialog(rowData);
                    }
                }
            });

            //新增按钮
            $("#system-add-sysNumberRule").click(function(){
                if(!$("#sysNumber-form-billInfo").form("validate")){
                    $.messager.alert("提醒","请正确输入提示项!");
                    return false;
                }
                var tablename = $("#system-tablename-sysNumber").widget('getValue');
                $('<div id="system-dialog-sysNumberRule-add"></div>').appendTo('#system-dialog-sysNumberRule');
                $("#system-dialog-sysNumberRule-add").dialog({
                    title:'单据编号规则信息',
                    width:500,
                    height:240,
                    closed:true,
                    cache:false,
                    href:'sysNumber/sysNumberRuleAdd.do?tablename='+tablename,
                    modal:true,
                    onClose:function(){
                        $('#system-dialog-sysNumberRule-add').dialog("destroy");
                    }
                });
                $("#system-dialog-sysNumberRule-add").dialog("open");
            });

            //修改按钮
            $("#system-edit-sysNumberRule").click(function(){
                var row = $dgSysNumberRuleList.datagrid('getSelected');
                if (row == null) {
                    $.messager.alert("提醒","请选择要修改的单据编号规则!");
                    return false;
                }
                openSysNumberRuleEditDialog(row);
            });

            //删除按钮
            var delStr = "";
            $("#system-delete-sysNumberRule").click(function(){
                var row = $dgSysNumberRuleList.datagrid('getSelected');
                if (row == null) {
                    $.messager.alert("提醒","请选择行!");
                    return false;
                }
                if(row.state == "1"){//判断是否为流水依据
                    $("#system-valName-sysNumber").val("");
                    $("#system-testvalue-sysNumber").val("");
                }
                delStr += row.numberruleid + ",";
                $("#system-delStr-sysNumberRule").val(delStr);
                var rowIndex = $dgSysNumberRuleList.datagrid('getRowIndex', row);
                $dgSysNumberRuleList.datagrid('deleteRow', rowIndex);
                $dgSysNumberRuleList.datagrid('clearSelections');
                var seqRows = $("#system-table-sysNumberRule").datagrid('getRows');
                var temp = new Object();
                for(var i=0;i<seqRows.length;i++){
                    for(var j=0;j<seqRows.length;j++){
                        if(seqRows[i].sequencing < seqRows[j].sequencing){
                            temp = seqRows[i];
                            seqRows[i]=seqRows[j];
                            seqRows[j]=temp;
                        }
                    }
                }
                $("#system-table-sysNumberRule").datagrid('loadData',seqRows);
                //获取预览效果
                var preViewObj = new Object();
                if(seqRows.length != 0){
                    preViewObj["preView"] = JSON.stringify(seqRows);
                }
                var sysNumberJson = $("#sysNumber-form-billInfo").serializeJSON();
                for(key in sysNumberJson){
                    preViewObj[key] = sysNumberJson[key];
                };
                var preViewRet = sysNumber_AjaxConn(preViewObj,'sysNumber/getPreViewSysNumber.do');
                var preViewJSON = $.parseJSON(preViewRet);
                $("#system-preView-sysNumber").val(preViewJSON.preViewNo);
            });
        }

        $(function(){
            $("#sysNumber-table-billName").datagrid({
                fit:true,
                title:'',
                method:'post',
                rownumbers:true,
                pagination:true,
                idField:'numberid',
                singleSelect:true,
                pageSize:100,
                url:'sysNumber/getSysNumberList.do',
                toolbar:'#sysNumber-toolbar-billquery',
                columns:[[
                    {field:'billname',title:'单据名称',width:130},
                    {field:'state',title:'状态',width:80,
                        formatter:function(val,rowData,rowIndex){
                            return rowData.stateName;
                        }
                    }
                ]],
                onClickRow:function(rowIndex, rowData){
                    $("#system-numberid-sysNumberList").val(rowData.numberid);
                    refreshLayout("单据编号【查看】", 'sysNumber/sysNumberViewPage.do?numberid='+rowData.numberid);
                }
            });

            //查询
            $("#sysNumber-query-List").click(function(){
                //把form表单的name序列化成JSON对象
                var queryJSON = $("#sysNumber-form-billquery").serializeJSON();
                $("#sysNumber-table-billName").datagrid("load",queryJSON);
            });
            //重置
            $('#sysNumber-query-reloadList').click(function(){
                $("#sysNumber-form-billquery")[0].reset();
                $("#sysNumber-table-billName").datagrid("load",{});
            });

            $("#sysNumber-button-billtype").buttonWidget({
                //初始默认按钮 根据type对应按钮事件
                initButton:[
                    {},
                    <security:authorize url="/sysNumber/sysNumberAddBtn.do">
                    {
                        type:'button-add',//新增
                        handler:function(){
                            $("#sysNumber-table-billName").datagrid('clearSelections');
                            refreshLayout("单据编号【新增】", 'sysNumber/sysNumberAddPage.do','add');
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sysNumber/sysNumberEditBtn.do">
                    {
                        type:'button-edit',//修改
                        handler:function(){
                            var row = $("#sysNumber-table-billName").datagrid('getSelected');
                            if(row == null){
                                $.messager.alert("提醒","请选择要修改的单据名称!");
                                return false;
                            }
                            var flag = isDoLockData(row.numberid,"t_sys_number");
                            if(!flag){
                                $.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
                                return false;
                            }
                            refreshLayout("单据编号【修改】", 'sysNumber/sysNumberEditPage.do?numberid='+row.numberid,'edit');
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sysNumber/sysNumberSaveBtn.do">
                    {
                        type:'button-save',//保存
                        handler:function(){
                            if(!$("#sysNumber-form-billInfo").form("validate")){
                                return false;
                            }
                            var sysNumberJson = $("#sysNumber-form-billInfo").serializeJSON();
                            var sysNumberRuleRows = $("#system-table-sysNumberRule").datagrid('getRows');
                            var sysNumberRuleObj = new Object();
                            if(sysNumberRuleRows.length != 0){
                                sysNumberRuleObj["sysNumberRuleRows"] = JSON.stringify(sysNumberRuleRows);
                            }
                            else{
                                $.messager.alert("提醒","请填写单据编号规则!");
                                return false;
                            }
                            for(key in sysNumberJson){
                                sysNumberRuleObj[key] = sysNumberJson[key];
                            };
                            var type = $("#system-operate-sysNumber").val();
                            var url = "sysNumber/addSysNumberBill.do";
                            if(type == "edit"){
                                url = 'sysNumber/editSysNumberBill.do';
                            }
                            var ret = sysNumber_AjaxConn(sysNumberRuleObj,url);
                            var retJson = $.parseJSON(ret);
                            if(retJson.flag){
                                $.messager.alert("提醒","保存成功!");
                                $("#sysNumber-table-billName").datagrid('reload');
                                refreshLayout("单据编号【详情】", 'sysNumber/sysNumberViewPage.do?numberid='+retJson.numberid,'view');
                                $("#sysNumber-table-billName").datagrid('selectRecord',$("#system-numberid-sysNumber").val());
                            }
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sysNumber/sysNumberDelBtn.do">
                    {
                        type:'button-delete',//删除
                        handler:function(){
                            var row = $("#sysNumber-table-billName").datagrid('getSelected');
                            if(row == null){
                                $.messager.alert("提醒","请选择要删除的单据名称!");
                                return false;
                            }
                            var flag = isLockData(row.numberid,"t_sys_number");
                            if(flag){
                                $.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
                                return false;
                            }
                            $.messager.confirm("提醒","是否确定删除单据编号规则?",function(r){
                                if(r){
                                    var ret = sysNumber_AjaxConn({numberid:row.numberid},'sysNumber/deleteSysNumber.do');
                                    var retJson = $.parseJSON(ret);
                                    if(retJson.flag){
                                        $.messager.alert("提醒","删除成功!");
                                        $("#sysNumber-layout-bill").layout('remove','center');
                                        $("#sysNumber-table-billName").datagrid('reload');
                                    }
                                    else{
                                        $.messager.alert("提醒","删除失败!");
                                    }
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sysNumber/sysNumberCopyBtn.do">
                    {
                        type:'button-copy',//复制
                        handler:function(){
                            var row = $("#sysNumber-table-billName").datagrid('getSelected');
                            if(row == null){
                                $.messager.alert("提醒","请选择要修改的单据名称!");
                                return false;
                            }
                            refreshLayout("单据编号【复制】", 'sysNumber/sysNumberCopyPage.do?numberid='+row.numberid,'copy');
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sysNumber/sysNumberOpenBtn.do">
                    {
                        type:'button-open',//启用
                        handler:function(){
                            var row = $("#sysNumber-table-billName").datagrid('getSelected');
                            if(row == null){
                                $.messager.alert("提醒","请选择要启用的单据名称!");
                                return false;
                            }
                            $.messager.confirm("提醒","是否确定启用单据编号规则?",function(r){
                                if(r){
                                    var ret = sysNumber_AjaxConn({numberid:row.numberid},'sysNumber/enableSysNumber.do');
                                    var retJson = $.parseJSON(ret);
                                    if(retJson.flag){
                                        $.messager.alert("提醒","启用成功!");
                                        $("#sysNumber-table-billName").datagrid('reload');
                                        refreshLayout("单据编号【详情】", 'sysNumber/sysNumberViewPage.do?numberid='+row.numberid,'view');
                                    }
                                    else{
                                        $.messager.alert("提醒","启用失败!");
                                    }
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sysNumber/sysNumberCloseBtn.do">
                    {
                        type:'button-close',//禁用
                        handler:function(){
                            var row = $("#sysNumber-table-billName").datagrid('getSelected');
                            if(row == null){
                                $.messager.alert("提醒","请选择要禁用的单据名称!");
                                return false;
                            }
                            $.messager.confirm("提醒","是否确定禁用单据编号规则?",function(r){
                                if(r){
                                    var ret = sysNumber_AjaxConn({numberid:row.numberid},'sysNumber/disableSysNumber.do');
                                    var retJson = $.parseJSON(ret);
                                    if(retJson.flag){
                                        $.messager.alert("提醒","禁用成功!");
                                        $("#sysNumber-table-billName").datagrid('reload');
                                        refreshLayout("单据编号【详情】", 'sysNumber/sysNumberViewPage.do?numberid='+row.numberid,'view');
                                    }
                                    else{
                                        $.messager.alert("提醒","禁用失败!");
                                    }
                                }
                            });
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/sysNumber/sysNumberGiveUpBtn.do">
                    {
                        type:'button-giveup',//放弃
                        handler:function(){
                            var type = $("#system-operate-sysNumber").val();
                            if(type=="add"){
                                $("#sysNumber-layout-bill").layout('remove','center');
                                $("#sysNumber-button-billtype").buttonWidget("initButtonType","list");
                            }else if(type=="edit"){
                                var numberid = $("#system-numberid-sysNumber").val();
                                $.ajax({
                                    url :'system/lock/unLockData.do',
                                    type:'post',
                                    data:{id:numberid,tname:'t_sys_number'},
                                    dataType:'json',
                                    async: false,
                                    success:function(json){
                                        flag = json.flag
                                    }
                                });
                                if(!flag){
                                    $.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
                                    return false;
                                }
                                refreshLayout("单据编号【详情】", 'sysNumber/sysNumberViewPage.do?numberid='+numberid,'view');
                            }
                        }
                    },
                    </security:authorize>
                    {}
                ],
                model:'base',
                type:'list',
                tab:'单据类型',
                datagrid:'sysNumber-table-billName'
            });
        });

	</script>
  </body>
</html>
