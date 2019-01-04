<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>结算方式</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="accounting-opera"/>
  	<input type="hidden" id="system-index-accounting"/>
  	<input type="hidden" id="system-begindate-accounting"/>
  	<input type="hidden" id="system-oldEndDate-accounting"/>
  	<input type="hidden" id="system-newEndDate-accounting"/>
  	<input type="hidden" id="system-year-accouting"/>
 	<div class="easyui-layout" data-options="fit:true,border:false" id="finance-layout-accounting">
		<div data-options="region:'north',split:false" style="overflow: hidden;">
			<table cellpadding="2px;" cellspacing="2px;" border="0px;" style="padding: 3px; width: 100%">
				<tr>
					<td align="left" width="30%">
						<table cellpadding="2" cellspacing="2" border="0" width="300px">
							<tr>
								<td width="60px">启用年度:</td>
								<td><input id="system-openyear-accounting" class="no_input" style="width:80px;" value="${openyear}" readonly="readonly"></td>
								<td width="30px">月度:</td>
								<td><input id="system-openmonth-accounting" type="text" class="no_input" style="width:80px;" readonly="readonly"></td>
							</tr>
						</table>
					</td>
					<td align="right" width="70%">
						<table cellpadding="0" cellspacing="0" border="0" width="260px">
							<tr>
								<td>
                                    <security:authorize url="/system/accounting/accountingCloseBtn.do">
                                        <a href="javaScript:void(0);" id="system-close-accounting" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'" title="关账">关账</a>
                                    </security:authorize>
                                    <security:authorize url="/system/accounting/accountingDeleteBtn.do">
                                        <a href="javaScript:void(0);" id="system-delete-accounting" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除">删除</a>
                                    </security:authorize>
                                    <security:authorize url="/system/accounting/accountingAddBtn.do">
                                        <a href="javaScript:void(0);" id="system-add-accounting" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
                                    </security:authorize>
									<a href="javaScript:void(0);" id="system-cancel-accounting" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-quit'" >退出</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
    	</div>
    	<div title="会计年度" data-options="region:'west',split:true" style="width:300px;">
            <table id="accounting-tree-list" class="ztree"></table>
    	</div>
    	<div data-options="region:'center',split:true">
    		<div class="easyui-layout" fit="true" >
    			<div data-options="region:'center',split:true,border:true" style="border: 0px;">
    				<table id="accounting-table-list"></table>
    			</div>
				<div data-options="region:'south',split:false" style="height: 70px;border: 0px;">
					<div align="right">
						<table cellpadding="2" cellspacing="2" border="0px" style="padding: 10px;">
							<tr>
								<td>当前账套最新会计年度为：<label id="system-newYear-accounting" style="color: red;">${newYear}</label>&nbsp年</td>
							</tr>
							<tr>
								<td>可以新建&nbsp<label id="system-addYear-accounting" style="color: red;">${addYear}</label>&nbsp年的会计月历</td>
							</tr>
						</table>
					</div>
				</div>  
    		</div>
   		</div>
	</div>
	<script type="text/javascript">
		var accounting_today = new Date();
		var accounting_year = accounting_today.getFullYear();
		var accounting_month = parseInt(accounting_today.getMonth()+1);
//		$('#system-openyear-accounting').numberspinner({
//		    min: parseInt(accounting_year),
//		    editable: false
//		});
	
    	//ajax调用
		var accounting_ajax = function (Data, Action) {
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return ajax.responseText;
		}
		
		//计算开始如期与结束日期的时间差
		function dateDifference(begindate,enddate){
            var ret = accounting_ajax({begindate:begindate,enddate:enddate},'system/accounting/getDaysBetweenDate.do');
            var retJson = $.parseJSON(ret);
            if(parseInt(retJson.days) > 18 && parseInt(retJson.days) <= 38){
                return true;
            }
			return false;
		}
		
		var accountingCol=$("#accounting-table-list").createGridColumnLoad({
	     	name:'base_finance_accounting',
	     	frozenCol:[[]],
	     	commonCol:[[
	     				{field:'id',title:'编码',width:50,sortable:true,hidden:true},
	     				{field:'begindate',title:'开始日期',width:120,sortable:true,align:'center'},
	     				{field:'enddate',title:'结束日期',width:120,sortable:true,align:'center',
	     					editor:{
	     						type:'dateText',
	     						options:{
	     							dchanged:'cDayFunc'
	     						}
	     					}
	     				},
                        {field:'belongmonth',title:'月度',width:60,align:'center',isShow:true},
	     				{field:'state',title:'状态',width:50,sortable:true,align:'center',
	     					formatter:function(val,rowData,rowIndex){
                                if("0" == val){
                                    return "未启用";
                                }else if("1" == val){
                                    return "启用";
                                }else if("5" == val){
                                    return "关账";
                                }
							}
	     				},
	     				{field:'remark',title:'备注',width:100,hidden:true,sortable:true},
						{field:'addusername',title:'建档人',width:60,hidden:true,sortable:true},
						{field:'adddeptname',title:'建档部门',width:80,hidden:true,sortable:true},
						{field:'addtime',title:'建档时间',width:115,hidden:true,sortable:true},
						{field:'modifyusername',title:'最后修改人',width:60,hidden:true,sortable:true},
						{field:'modifytime',title:'最后修改时间',width:115,hidden:true,sortable:true},
						{field:'openusername',title:'启用人',width:60,hidden:true,sortable:true},
						{field:'opentime',title:'启用时间',width:115,hidden:true,sortable:true},
						{field:'closeusername',title:'禁用人',width:60,hidden:true,sortable:true},
						{field:'closetime',title:'禁用时间',width:115,hidden:true,sortable:true}
		     	]]
	     });

	    function cDayFunc(){
            var year = $("#system-year-accouting").val();
			var begindate = $("#system-begindate-accounting").val();
			var index = $("#system-index-accounting").val();
			var date = $dp.cal.getNewDateStr().split("-");
			var y = date[0];
			var m = date[1];
			if(m.toString.length == 1){
				m = "0"+m;
			}
			var d = date[2];
			var endDate = y+'-'+m+'-'+d;
			if(!dateDifference(begindate,endDate)){
				endDate = $("#system-oldEndDate-accounting").val();
				$('#accounting-table-list').datagrid('reload');
				$.messager.alert("警告","每个会计期间必须在20~40天之间!");
			}
			$("#system-newEndDate-accounting").val(endDate);
    		if(index != ""){
    			var newEndDate = $("#system-newEndDate-accounting").val();
    			var oldEndDate = $("#system-oldEndDate-accounting").val();
    			if(newEndDate == oldEndDate){
    				$('#accounting-table-list').datagrid('endEdit',parseInt(index));
    				return false;
    			}
                var nochangeids = "";
                var rows = $('#accounting-table-list').datagrid('getRows');
                for(var i=parseInt(index)-1; i>=0; i--){
                    if("" == nochangeids){
                        nochangeids = rows[i].id;
                    }else{
                        nochangeids += "," + rows[i].id;
                    }
                }
    			var ret = accounting_ajax({year:year,nochangeids:nochangeids,newEndDate:newEndDate,changeid:rows[parseInt(index)].id},'system/accounting/editDateList.do');
    			var retJson = $.parseJSON(ret);
                var retEdit = accounting_ajax({year:year,dateArrStr:JSON.stringify(retJson)},'system/accounting/editAccounYear.do');
                var retEditJson = $.parseJSON(retEdit);
                if(!retEditJson.flag){
                    $.messager.alert("提醒","修改失败!");
                }else{
                    var treeObj = $.fn.zTree.getZTreeObj("accounting-tree-list");
                    var nodes = treeObj.getSelectedNodes();
                    zTreeBeforeClick("accounting-tree-list", nodes[0]);
                }
    			$('#accounting-table-list').datagrid('loadData',retJson);
    			$('#accounting-table-list').datagrid('endEdit',parseInt(index));
    		}
		}
	     
		function jsonList(year){
			var month = "";
			var arr = new Array();
			jsonstr="[";
			for(var i=0;i<12;i++){
				month = (i+1).toString();
				if(month.length == 1){
					month = "0"+month;
				}
				arr[i] = year + "-" + month + "-01";
				jsonstr += "{\"begindate\""+ ":" + "\"" + arr[i] + "\"},";
			}
			jsonstr = jsonstr.substring(0,jsonstr.lastIndexOf(','));
			jsonstr += "]";
			var json = $.parseJSON(jsonstr);
			return json;
		}
		
		//树型执行单击前操作
		function zTreeBeforeClick(treeId, treeNode) {
            if(null != treeNode){
                if(treeNode.id == ""){
                    return false;
                }
                else{
                    $("system-state-accouting").val(treeNode.state);;
                    $("#system-year-accouting").val(treeNode.id);
                    $('#accounting-table-list').datagrid({
                        authority:accountingCol,
                        frozenColumns:accountingCol.frozen,
                        columns:accountingCol.common,
                        fit:true,
                        method:'post',
                        rownumbers:true,
                        idField:'id',
                        singleSelect:true,
                        url:'system/accounting/getAccountingListByYear.do?year='+ treeNode.id,
                        onDblClickRow:function(rowIndex, rowData){
                            if("1" != rowData.state && "5" != rowData.state){
                                $("#system-begindate-accounting").val(rowData.begindate);
                                $("#system-oldEndDate-accounting").val(rowData.enddate);
                                $("#system-index-accounting").val(rowIndex);
                                $('#accounting-table-list').datagrid('beginEdit',rowIndex);
                                $.parser.parse('.Wdate');
                            }
                        },
                        onClickRow:function(rowIndex,rowData){
                            var index = $("#system-index-accounting").val();
                            $('#accounting-table-list').datagrid('endEdit',parseInt(index));
                        },
                        onLoadSuccess:function(data){
                            var rows = data.rows;
                            for(var i=0;i<rows.length;i++){
                                if(rows[i].state == "1"){
                                    var json = {};
                                    json['openyear'] = rows[i].year;
                                    json['openmonth'] = rows[i].belongmonth;
                                    setOpenYearAndOpenMonth(json);
                                }
                            }
                        }
                    }).datagrid('columnMoving');
                }
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                treeObj.selectNode(treeNode,false);
            }
		}

        function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
            var openYear = $('#system-openyear-accounting').val();
            if(openYear == ""){
                $('#accounting-table-list').datagrid({
                    authority:accountingCol,
                    frozenColumns:accountingCol.frozen,
                    columns:accountingCol.common,
                    fit:true,
                    method:'post',
                    rownumbers:true,
                    idField:'id',
                    singleSelect:true
                }).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{}]}).datagrid('columnMoving');
            }
            else{
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                var node = treeObj.getNodeByParam("id", openYear, null);
                treeObj.selectNode(node, false); //选中节点
                $("#system-year-accouting").val(openYear);
                zTreeBeforeClick("accounting-tree-list", node);
            }
        }

        function setOpenYearAndOpenMonth(json){
            $('#system-openyear-accounting').val(json.openyear);
            $("#system-openmonth-accounting").val(json.openmonth);
//            if("" != json.openyear){
//                $('#system-openyear-accounting').hide();
//            }else{
//                $('#system-openyear-accounting').show();
//            }
//            if("" != json.openmonth){
//                $("#system-openmonth-accounting").hide();
//            }else{
//                $("#system-openmonth-accounting").show();
//            }
        }

		$(function(){
            if("${isAutoCloseAccounting}" == "1"){
                $("#system-close-accounting").linkbutton('disable');
            }else{
                $("#system-close-accounting").linkbutton('enable');
            }
			//树型
			var accountingTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "system/accounting/getAccountingTree.do",
					autoParam: ["id","parentid", "text","state"]
				},
				data: {
					key:{
						title:"text",
						name:"text"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "parentid",
						rootPId: null
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: zTreeBeforeClick,
                    onAsyncSuccess: zTreeOnAsyncSuccess
				}
			};
		
			$.fn.zTree.init($("#accounting-tree-list"), accountingTreeSetting,null);
			var treeObj = $.fn.zTree.getZTreeObj("accounting-tree-list");

			if("${newYear}" == ""){
				$("#system-newYear-accounting").html("****");
			}
			if("${addYear}" == ""){
				$("#system-addYear-accounting").html(accounting_year);
			}
            //关账
            $("#system-close-accounting").click(function(){
                if($(this).linkbutton('options').disabled){
                    return false;
                }
                var row = $("#accounting-table-list").datagrid('getSelected');
                if(null == row){
                    $.messager.alert("提醒","请选中要关账的会计区间!");
                    return false;
                }
                if(row.state != '1'){
                    $.messager.alert("提醒","只有启用状态的会计区间允许关账!");
                    return false;
                }
                var index = $("#accounting-table-list").datagrid('getRowIndex',row);
                $("#accounting-table-list").datagrid('selectRow',index+Number(1));
                var nexrow = $("#accounting-table-list").datagrid('getSelected');

                var ret = accounting_ajax({id:row.id,nextid:nexrow.id},'system/accounting/closeAccounting.do');
                var retJson = $.parseJSON(ret);
                if(retJson.flag){
                    $.messager.alert("提醒","关账成功!");
                    zTreeBeforeClick("accounting-tree-list", retJson.node);
                }else{
                    $.messager.alert("提醒","关账失败!<br>"+retJson.msg);
                    $("#accounting-table-list").datagrid('selectRow',index);
                }
            });

			//新增按钮
			$("#system-add-accounting").click(function(){
				var year = $("#system-newYear-accounting").html();
				var newyear = 0;
		    	if(year != "****"){
		    		newyear = parseInt(year)+parseInt(1);
		    	}
		    	else{
		    		newyear = parseInt(accounting_year);
		    	}
				$.messager.confirm('确认信息','本账套的最新会计年度为：'+year+'年,是否新建'+newyear+'年的会计月历?',function(r){
				    if (r){
				    	var ret = accounting_ajax({year:parseInt(newyear)},'system/accounting/getDateList.do');
				    	var retJson = $.parseJSON(ret);
				    	if(retJson.flag){
				    		$('#accounting-table-list').datagrid('loadData',retJson.list);

                            var treeObj = $.fn.zTree.getZTreeObj("accounting-tree-list");
                            var pnode = treeObj.getNodeByParam("id", "", null);
                            treeObj.addNodes(pnode, retJson.node); //增加子节点
                            var snode = treeObj.getNodeByParam("id", retJson.node.id, null);
                            treeObj.selectNode(snode, false); //选中节点
                            $("#system-year-accouting").val(snode.id);
                            zTreeBeforeClick("accounting-tree-list", snode);

					    	$("#system-newYear-accounting").html(parseInt(newyear));
					    	$("#system-addYear-accounting").html(parseInt(newyear)+parseInt(1));
				    	}
				    	else{
				    		$.messager.alert("提醒","新增失败!");
				    	}
				    }   
				});
			});
			
			//取消
			$("#system-cancel-accounting").click(function(){
				top.closeTab('会计期间');
			});
			
			//删除
			$("#system-delete-accounting").click(function(){
				var year = $("#system-year-accouting").val();
				if(year == ""){
					$.messager.alert("提醒","请选择要删除的年度!");
					return false;
				}
				if($("system-state-accouting").val() == "1"){
					$.messager.alert("提醒","启用状态不允许删除!");
					return false;
				}
				$.messager.confirm('确认信息','确定删除该年度会计期间?',function(r){
					if(r){
						var ret = accounting_ajax({year:year},'system/accounting/deleteAccounting.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							var accoutingTree = $.fn.zTree.getZTreeObj("accounting-tree-list");
							var node = accoutingTree.getNodeByParam("id", year, null);
							accoutingTree.removeNode(node); //删除子节点
							var nodes = accoutingTree.getNodes();
							if(nodes[0].children.length != 0){
								$("#system-newYear-accounting").html(retJson.newYear);
					    		$("#system-addYear-accounting").html(retJson.addYear);
					    		$('#accounting-table-list').datagrid('reload',{});
				    		}
				    		else{
				    			location.reload();
                                $('#system-openyear-accounting').val('');
				    			$("#system-openmonth-accounting").val('');
				    		}
						}
						else{
							$.messager.alert("提醒","删除失败!");
						}
					}
				});
			});
		});
	</script>
  </body>
</html>
