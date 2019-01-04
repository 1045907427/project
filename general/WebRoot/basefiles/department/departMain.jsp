<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门档案主页</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  <input type="hidden" id="base-thisId-deptment"/>
  <input type="hidden" id="department-hidden-hdpId"/>
  <input type="hidden" id="department-hidden-hdIsParent"/>
    <div class="easyui-panel" data-options="fit:true,border:false" style="position:relative">
    	<div id="departMent" class="easyui-tabs" data-options="fit:true,border:false" style="position:relative">
			<div title="部门档案树状"> 
				<div class="easyui-layout" data-options="fit:true">
					<div title="" data-options="region:'north',border:false" style="height:30px;overflow: hidden">
			    		<div id="department-div-button" class="buttonBG">
			    		<security:authorize url="/basefiles/showDepartMentAddPage.do">
			            	<a href="javaScript:void(0);" id="department-add-addMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增部门信息">新增</a>
						</security:authorize>
						<security:authorize url="/basefiles/showDepartMentEditPage.do">
			            	<a href="javaScript:void(0);" id="department-edit-editMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'" title="修改部门信息">修改</a>
			           	 </security:authorize>
			           	  <security:authorize url="/basefiles/showDepartMentSavePage.do">
			           	 	 <a href="javaScript:void(0);" id="department-save-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存部门信息">保存</a>
			           	 </security:authorize>
			             <security:authorize url="/basefiles/showDepartMentDeletePage.do">
			             	<a href="javaScript:void(0);" id="department-delete-deleteMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除部门信息">删除</a>
			            </security:authorize>
			             <security:authorize url="/basefiles/showDepartMentCopyPage.do">
			             	<a href="javaScript:void(0);" id="department-copy-copyMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-copy'" title="复制部门信息">复制</a>
			            </security:authorize>
			            <security:authorize url="/basefiles/showDepartMentEnablePage.do">
			            	<a href="javaScript:void(0);" id="department-enable-enableMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'" title="启用部门">启用</a>
			            </security:authorize>
			             <security:authorize url="/basefiles/showDepartMentDisablePage.do">
			             	<a href="javaScript:void(0);" id="department-disable-disableMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'" title="禁用部门">禁用</a>
			            </security:authorize>
			           	 </div>
			    	</div>
			    	<div title="部门档案" data-options="region:'west',split:true" style="width:200px;" >
			            <div id="department-tree-department" class="ztree"></div>
			    	</div>
			    	<div title="" data-options="region:'center',border:true" >
			    		<div id="department-div-departmentInfo"></div>
		    		</div>
				</div>
			</div>
			<div title="部门档案列表"> 
	    		<div id="department-toolbar-operateList">
	    			<security:authorize url="/basefiles/showDepartMentEnablePage.do">
	    			<a href="javaScript:void(0);" id="departmentList-enable-enableMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'" title="启用选中部门">启用</a>
	            	</security:authorize>
	            	<security:authorize url="/basefiles/showDepartMentDisablePage.do">
	            	<a href="javaScript:void(0);" id="departmentList-disable-disableMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'" title="禁用选中部门">禁用</a>
	            	</security:authorize>
	            	<security:authorize url="/basefiles/showDepartMentDeletePage.do">
	            	<a href="javaScript:void(0);" id="departmentList-delete-deleteMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除选中部门">删除</a>
	            	</security:authorize>
	            	<security:authorize url="/basefiles/showDepartMentImportPage.do">
	            	<a href="javaScript:void(0);" id="departmentList-in-inMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-import'" title="导入部门">导入</a>
	            	</security:authorize>
	            	<security:authorize url="/basefiles/showDepartMentExportPage.do">
	            	<a href="javaScript:void(0);" id="departmentList-out-outMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-export'" title="导出部门">导出</a>
	            	</security:authorize>
                    <span id="department-query-advanced"></span>
	            	<form action="" id="department-form-departmentListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
			    		编码:<input name="id" style="width:100px" />
			   			名称:<input name="name" style="width:120px" />
			    		<a href="javaScript:void(0);" id="department-query-queryDepartmentList" class="button-qr">查询</a>
			    		<a href="javaScript:void(0);" id="department-query-reloadDepartmentList" class="button-qr">重置</a>

		    		</form>
	           	 </div>
	           	<table id="department-table-departmentList"></table>
			</div>
		</div>
    </div>
    <div id="department-window-tableInfoOper" closed="true"></div>
    <script type="text/javascript">
    var department_AjaxConn = function (Data, Action) {
	    var MyAjax = $.ajax({
	        type: 'post',
	        cache: false,
	        url: Action,
	        data: Data,
	        async: false,
	        ajaxSend:function(){
	        	loading("提交中..");
	        },
	        success:function(data){
	        	loaded();
	        }
	    })
	    return MyAjax.responseText;
	}
	
	//检验部门输入值的最大长度
	$.extend($.fn.validatebox.defaults.rules, {
		maxLen:{
  				validator : function(value,param) { 
	            return value.length <= param[0]; 
	        }, 
	        message : '最多可输入{0}个字符!' 
  		}
	});
     //列表操作JS
    //通用查询组建调用
	$("#department-query-advanced").advancedQuery({
		//查询针对的表
 		name:'base_department',
 		//查询针对的表格id
 		datagrid:'department-table-departmentList'
	});
     
     //根据初始的列与用户保存的列生成以及字段权限生成新的列
     var departmentListColJson=$("#department-table-departmentList").createGridColumnLoad({
     	name:'base_department',
     	frozenCol:[[]],
     	commonCol:[[{field:'id',title:'编码',width:80,sortable:true},
     				{field:'pid',title:'上级部门',width:80,sortable:true,
     					formatter:function(val,rowData,rowIndex){
							return rowData.pDeptName;
						}
     				},
     				{field:'thisid',title:'本级编码',width:80,sortable:true},
     				{field:'name',title:'名称',width:100,sortable:true},
     				{field:'state',title:'状态',sortable:true,width:80,
     					formatter:function(val,rowData,rowIndex){
							return rowData.stateName;
						}
     				}, 
					{field:'remark',title:'备注',width:130,sortable:true},
					{field:'manageruserid',title:'部门主管',width:80,sortable:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.managerName;
						}
					},
					{field:'tel',title:'电话',width:80,sortable:true},
					{field:'fax',title:'传真',width:80,sortable:true},
					{field:'workcalendar',title:'工作日历',width:80,sortable:true},
					{field:'leaf',title:'末级标记',sortable:true,width:80,hidden:true,
						formatter:function(val){
							if(val=='1'){
					        	return "是";
					        }else{
					        	return "否";
					        }
						}
					},
					{field:'depttype',title:'业务属性',sortable:true,width:60,hidden:true,
						formatter:function(val,rowData,rowIndex){
							return rowData.depttypeName;
						}
					},
					{field:'storageid',title:'关联仓库',width:60,
						formatter:function(val,rowData,rowIndex){
							return rowData.storagename;
						}
					},
					{field:'adduserid',title:'建档人',width:80,sortable:true,hidden:true},
					{field:'adddeptid',title:'建档部门',width:80,sortable:true,hidden:true},
					{field:'addtime',title:'建档时间',width:80,sortable:true,hidden:true},
					{field:'modifyuserid',title:'最后修改人',width:80,sortable:true,hidden:true},
					{field:'modifydeptid',title:'最后修改部门',width:80,sortable:true,hidden:true},
					{field:'modifytime',title:'最后修改时间',width:80,sortable:true,hidden:true},
					{field:'openuserid',title:'启用人',width:80,sortable:true,hidden:true},
					{field:'opentime',title:'启用时间',width:80,sortable:true,hidden:true},
					{field:'closeuserid',title:'禁用人',width:80,sortable:true,hidden:true},
					{field:'closetime',title:'禁用时间',width:80,sortable:true,hidden:true}
	     		]]
     });
     //树形操作JS

	//将表单序列化成json 字符串
    $.fn.serializeJson = function(){
	     var obj = {};
	     var count = 0;
	     $.each( this.serializeArray(), function(i,o){
	         var n = o.name, v = o.value;
	         count++;
	         obj[n] = obj[n] === undefined ? v
	         : $.isArray( obj[n] ) ? obj[n].concat( v )
	         : [ obj[n], v ];
	     });
	     obj.nameCounts = count + "";//表单name个数
	     return JSON.stringify(obj);
	 };
	 
	 //新增页面按钮状态的变化
	function changeButton(){
		//按钮状态变化 
		$("#department-add-addMenu").linkbutton('enable');
		$("#department-edit-editMenu").linkbutton('enable');
		$("#department-save-saveMenu").linkbutton('disable');
		$("#department-delete-deleteMenu").linkbutton('enable');
		$("#department-copy-copyMenu").linkbutton('enable');
		$("#department-enable-enableMenu").linkbutton('enable');
		$("#department-disable-disableMenu").linkbutton('enable');
	}

    function zTreeBeforeClick(treeId, treeNode){
        $("#department-hidden-hdpId").val(treeNode.parentid);
        $("#base-thisId-deptment").val(treeNode.id);
        if (treeNode.isParent) {
            $("#department-hidden-hdIsParent").val("isParent")
        } else {
            $("#department-hidden-hdIsParent").val("noParent")
        }
    }

    function refreshLayout(title, url){
        $("#department-div-departmentInfo").panel({
            title:title,
            href:url,
            cache:false,
            maximized:true
        });
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
		$(function(){
			//回车事件
			controlQueryAndResetByKey("department-query-queryDepartmentList","department-query-reloadDepartmentList");
		
			//查询
			$("#department-query-queryDepartmentList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#department-form-departmentListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#department-table-departmentList").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#department-query-reloadDepartmentList").click(function(){
				$("#department-form-departmentListQuery")[0].reset();
				$("#department-table-departmentList").datagrid("load",{});
				
			});
			
			//部门列表
		  	$('#department-table-departmentList').datagrid({
	  			authority:departmentListColJson,
	  	 		frozenColumns:[[]],
				columns:departmentListColJson.common,
			    fit:true,
				title:'',
				method:'post',
				rownumbers:true,
				pagination:true,
				idField:'id',
				singleSelect:true,
				url:'basefiles/showDepartMentListPage.do',
				toolbar:"#department-toolbar-operateList"
			}).datagrid("columnMoving");
			//树型
			var departMentTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/showDepartMentTree.do",
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
					beforeClick: function(treeId, treeNode) {
						if(treeNode.id == ""){
                            refreshLayout('部门信息【新增】', 'basefiles/showDepartMentAddPage.do?state=4');
						}
						else{
                            refreshLayout('部门信息【详情】', 'basefiles/showDepartMentByMenu.do?id='+treeNode.id);
						}
                        zTreeBeforeClick(treeId, treeNode);
						var zTree = $.fn.zTree.getZTreeObj("department-tree-department");
						if (treeNode.isParent) {
							if (treeNode.level == 0) {
								zTree.expandAll(false);
								zTree.expandNode(treeNode);
							} else {
								zTree.expandNode(treeNode);
							}
						}
						return true;
					}
				}
			};
			$.fn.zTree.init($("#department-tree-department"), departMentTreeSetting,null);
			//部门新增 
			$("#department-add-addMenu").click(function(){
					if($(this).linkbutton('options').disabled){
						return false;
					}
					var url = "";
					var departmentTree=$.fn.zTree.getZTreeObj("department-tree-department");
					if(departmentTree.getSelectedNodes(true).length == 0){
						url = "basefiles/showDepartMentAddPage.do?state=4";
					}
					else{
						var pid = departmentTree.getSelectedNodes()[0].id;
						var ret=department_AjaxConn({pId:pid},"basefiles/getNextLenght.do");
						var json = $.parseJSON(ret);
						if(json.nextLen == 0){
							$.messager.alert("提醒","已为最大级次,不允许新增!");
							return false;
						}
						url="basefiles/showDepartMentAddPage.do?state=4&id="+pid;
					}
                    refreshLayout('部门信息【新增】', url);
					return true;
				}
			);
		//修改部门 
		$("#department-edit-editMenu").click(function(){
			var departmentTree=$.fn.zTree.getZTreeObj("department-tree-department");
			if(departmentTree.getSelectedNodes(true).length == 0){
				$.messager.alert("警告","请选择对应的部门进行修改!");
				return false;
			}
			if($(this).linkbutton('options').disabled){
				return false;
			}
            var state = $("#department-hidden-state").val();
			if(state == "0"){
				$.messager.alert("警告","禁用状态下不允许修改!");
				return false;
			}
            var flag = isDoLockData(departmentTree.getSelectedNodes()[0].id,"t_base_department");
            if(!flag){
                $.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
                return false;
            }
			var url="basefiles/showDepartMentEditPage.do?id="+departmentTree.getSelectedNodes()[0].id;
            refreshLayout('部门信息【修改】', url);
			return true;
		});

		//保存部门 
		$("#department-save-saveMenu").click(function(){
			if($(this).linkbutton('options').disabled){
				return false;
			}
			changeButton();
			var urlEdit="";
			var departmentTree=$.fn.zTree.getZTreeObj("department-tree-department");
			if($("#departMent-hidden-hdType").val() == "add" || $("#departMent-hidden-hdType").val() == "copy"){
				$("#department-form-departmentInfoAdd").form({
					url:'basefiles/addDepartMent.do?style=4',
	    			onSubmit: function(){
	    				var flag = $(this).form('validate');
	    				if(flag==false){
	    					return false;
	    				}
	    				loading("提交中..");
	    			},
	    			success:function(data){
	    				loaded();
	    				//$.parseJSON()解析JSON字符串 
	    				var json = $.parseJSON(data);
	    				if(json.flag==true){
	    					$.messager.alert("提醒","新增保存成功!");
                            var id = $("#base-thisId-deptment").val();
                            var treeObj = $.fn.zTree.getZTreeObj("department-tree-department");
                            var node = treeObj.getNodeByParam("id", id, null);
                            treeObj.addNodes(node, json.node); //增加子节点
                            var snode = treeObj.getNodeByParam("id", json.node.id, null);
                            treeObj.selectNode(snode, false); //选中节点
                            zTreeBeforeClick("department-tree-department", snode);
                            refreshLayout('部门信息【详情】', 'basefiles/showDepartMentByMenu.do?id='+snode.id);
                            $("#department-table-departmentList").datagrid('reload');
                        }
	    				else{
	    					$.messager.alert("提醒","新增保存失败!");
	    				}
	    			}
	    		});
	    		$.messager.confirm("提醒","是否新增保存部门?",function(r){
	    			if(r){
	    				$("#departMent-hidden-hdClickState").val(2);
	    				$("#department-form-departmentInfoAdd").submit();
	    			}
	    		});
    		}
    		else {
    			if("isParent".localeCompare($("#department-hidden-hdIsParent").val())== 0){
					urlEdit="basefiles/editDepartMentAll.do";
				}
				else{
					urlEdit="basefiles/editDepartMent.do";
				}
    			$("#department-form-departmentEdit").form({
    				url:urlEdit,
	    			onSubmit: function(){
	    				var flag = $(this).form('validate');
	    				if(flag==false){
	    					return false;
	    				}
	    				loading("修改中..");
	    			},
	    			success:function(data){
	    				loaded();
	    				//$.parseJSON()解析JSON字符串 
	    				var json = $.parseJSON(data);
	    				if(json.flag==true){
                            $.messager.alert("提醒","修改保存成功!");
                            //更新所有子节点
                            var map = json.nodes;
                            var treeObj = $.fn.zTree.getZTreeObj("department-tree-department");
                            for(var key in map){
                                var object = map[key];
                                var node = treeObj.getNodeByParam("id", key, null);
                                node.id = object.id;
                                node.text = object.text;
                                node.parentid = object.parentid;
                                node.state = object.state;
                                treeObj.updateNode(node);
                            }
                            refreshLayout('部门信息【详情】', 'basefiles/showDepartMentByMenu.do?id='+$("#departMent-input-id").val());
                            $("#department-table-departmentList").datagrid('reload');
	    				}
	    				else{
	    					$.messager.alert("提醒","修改保存失败!"+json.msg);
	    				}
	    			}
	    		});
	    		$.messager.confirm("提醒","是否修改保存部门?",function(r){
	    			if(r){
	    				if($("#common-combobox-state").val() == "1"){
	    					$("#departMent-hidden-hdClickState").val(1);
						}
						else{
							$("#departMent-hidden-hdClickState").val(2);
						}
	    				$("#department-form-departmentEdit").submit();
	    			}
	    		});
    		}
		});
		//删除部门
		$("#department-delete-deleteMenu").click(function(){
			if($(this).linkbutton('options').disabled){
				return false;
			}
			var url="",str="",types = "";
			var departmentTree=$.fn.zTree.getZTreeObj("department-tree-department");
			if(departmentTree.getSelectedNodes(true).length == 0){
				$.messager.alert("提醒","请选择对应的部门进行删除!");
				return false;
			}
			if("isParent".localeCompare($("#department-hidden-hdIsParent").val())== 0){
				$.messager.alert("提醒","请先删除下级部门!");
				return false;
			}
			else{
				url="basefiles/deleteDepartment.do";
				str="确定删除吗?";
				tyeps = "one";
			}
			$.messager.confirm('提醒',''+str+'',function(r){   
			    if (r){
                    var id = departmentTree.getSelectedNodes()[0].id;
			        var ret=department_AjaxConn({id:id},url);
					var json = $.parseJSON(ret);
					$.messager.alert("提醒",""+json.sucNum+"条记录删除成功;<br/>"+json.failNum+"条记录删除失败;<br/>"+json.lockNum+"条记录被被其他人操作,暂不能删除;<br/>"+json.unAllowDel+"条记录被引用,不允许删除;<br/>");
                    if(json.sucNum > 0){
                        var treeObj = $.fn.zTree.getZTreeObj("department-tree-department");
                        var node = treeObj.getNodeByParam("id", id, null);
                        treeObj.removeNode(node); //删除子节点
                        var pid = $("#department-hidden-hdpId").val();
                        var snode = treeObj.getNodeByParam("id", pid, null);
                        treeObj.selectNode(snode, false); //选中节点
                        zTreeBeforeClick("department-tree-department", snode); //执行点击事件
                        if(snode.id != "" && null != snode.id){
                            refreshLayout('部门信息【详情】', 'basefiles/showDepartMentByMenu.do?id='+snode.id);
                        }else{
                            refreshLayout('部门信息【新增】', 'basefiles/showDepartMentAddPage.do?state=4');
                        }
                        $("#department-table-departmentList").datagrid('reload');
                    }
			    }
			});  
		});
		//复制部门
		$("#department-copy-copyMenu").click(function(){
			if($(this).linkbutton('options').disabled){
				return false;
			}
			var departmentTree=$.fn.zTree.getZTreeObj("department-tree-department");
			if(departmentTree.getSelectedNodes(true).length == 0){
				$.messager.alert("提醒","请选择对应的部门进行复制!");
				return false;
			}
			if($("#departMent-hidden-hdType").val() == "add"){return false;}
			var url="basefiles/copyDepartMent.do?state=4&id="+departmentTree.getSelectedNodes()[0].id;
            refreshLayout('部门信息【复制】', url);
			return true;
		});
		//启用部门
		$("#department-enable-enableMenu").click(function(){
			if($(this).linkbutton('options').disabled){
				return false;
			}
			var departmentTree=$.fn.zTree.getZTreeObj("department-tree-department");
			if(departmentTree.getSelectedNodes(true).length == 0){
				$.messager.alert("提醒","请选择对应的部门,再启用!");
				return false;
			}
            var state = $("#department-hidden-state").val();
			if(state != 2 && state != 0){
				$.messager.alert("提醒","该部门状态不能进行该操作!");
				return false;
			}
			else{$("#departMent-hidden-hdClickState").val(1);}
			//判断是否是整个部门类 
			if("isParent".localeCompare($("#department-hidden-hdIsParent").val())== 0){
				url="basefiles/enableDepartMentAll.do";
				str = "下级都将启用,确定要启用吗?"
				types = "all";
			}
			else{
				url="basefiles/enableDepartMent.do";
				str = "确定要启用吗?";
				types = "one";
			}
			$.messager.confirm("提醒",""+str+"",function(r){
				if(r){
					var ret=department_AjaxConn({id:departmentTree.getSelectedNodes(true)[0].id,pId:departmentTree.getSelectedNodes(true)[0].pId},url);
					var json = $.parseJSON(ret);
		    		if(types == "all"){
                        $.messager.alert("提醒",""+json.invNum+"条记录不符合启用条件,无效启用;<br/>"+json.sucNum+"条记录启用成功;<br/>"+json.failNum+"条记录启用失败;");
                        var id = $("#base-thisId-deptment").val();
                        refreshLayout('部门信息【详情】', 'basefiles/showDepartMentByMenu.do?id='+id);
			    		$("#department-table-departmentList").datagrid('reload');
		    		}
		    		else{
		    			if(json.pState == "0"){
		    				$.messager.alert("提醒","上级部门状态为禁用,不能启用该子级部门!");
		    				return false;
		    			}
		    			else{
		    				if(json.flag){
                                $.messager.alert("提醒","启用成功!");
                                var id = $("#base-thisId-deptment").val();
                                refreshLayout('部门信息【详情】', 'basefiles/showDepartMentByMenu.do?id='+id);
					    		$("#department-table-departmentList").datagrid('reload');
		    				}
		    				else{
		    					$.messager.alert("提醒","启用失败!");
		    				}
		    			}
		    		}
				}
				else{
					if("isParent".localeCompare($("#department-hidden-hdIsParent").val()) == 0){
						var ret=department_AjaxConn({id:departmentTree.getSelectedNodes(true)[0].id,pId:departmentTree.getSelectedNodes(true)[0].pId},'basefiles/enableDepartMent.do');
						var json = $.parseJSON(ret);
						if(json.pState == "0"){
		    				$.messager.alert("提醒","上级部门状态为禁用,不能启用该子级部门!");
		    				return false;
		    			}
		    			else{
		    				if(json.flag){
                                var id = $("#base-thisId-deptment").val();
                                refreshLayout('部门信息【详情】', 'basefiles/showDepartMentByMenu.do?id='+id);
					    		$("#department-table-departmentList").datagrid('reload');
		    					$.messager.alert("提醒","启用成功!");
		    				}
		    				else{
		    					$.messager.alert("提醒","启用失败!");
		    				}
		    			}
					}
				}
			});
		});
		//禁用部门
		$("#department-disable-disableMenu").click(function(){
			if($(this).linkbutton('options').disabled){
				return false;
			}
			var departmentTree=$.fn.zTree.getZTreeObj("department-tree-department");
			if(departmentTree.getSelectedNodes(true).length == 0){
				$.messager.alert("提醒","请选择对应的部门,再禁用!");
				return false;
			}
            var state = $("#department-hidden-state").val();
			if(state != 1){
				$.messager.alert("提醒","该部门状态不能进行该操作!");
				return false;
			}
			else{$("#departMent-hidden-hdClickState").val(0);}
			var str = "";
			var types = "";
			if("isParent".localeCompare($("#department-hidden-hdIsParent").val())== 0){
				url="basefiles/disableDepartMentAll.do";
				str = "下级都将禁用,确定要禁用吗?"
				types = "all";
			}
			else{
				url="basefiles/disableDepartMent.do";
				str = "确定要禁用吗?";
				types = "one";
			}
			$.messager.confirm('提醒',''+str+'',function(r){   
			    if (r){   
			        var ret=department_AjaxConn({id:departmentTree.getSelectedNodes()[0].id},url);
					var json = $.parseJSON(ret);
                    $.messager.alert("提醒",""+json.lockNum+"条记录被他人操作,暂无法禁用;<br/>"+json.sucNum+"条记录禁用成功;<br/>"+json.failNum+"条记录禁用失败;");
                    if(json.sucNum > 0){
                        var id = $("#base-thisId-deptment").val();
                        refreshLayout('部门信息【详情】', 'basefiles/showDepartMentByMenu.do?id='+id);
                        $("#department-table-departmentList").datagrid('reload');
                    }
			    }
			});  
		});

		//部门列表按钮
		//删除
		$("#departmentList-delete-deleteMenu").click(function(){
			if($(this).linkbutton('options').disabled){
				return false;
			}
			var department=$("#department-table-departmentList").datagrid('getSelected');
			if(department==null){
				$.messager.alert("提醒","请选择相应的部门!");
				return false;
			}
			var url="",str="";
  			var types = "";
  			if(department.leaf == "0"){//不是末级，可能为父级,可能为一级（鸿都百货下级）
  				url="basefiles/deleteDepartMentAll.do?id="+department.id;
				str="如果删除,包含其中的部门也将删除！确定删除吗?";
				types = "all";
  			}
  			else if(department.leaf == "1"){
  				url="basefiles/deleteDepartment.do?id="+department.id;
				str="确定删除吗?";
				types = "one";
  			}
			$.messager.confirm('提醒',''+str+'',function(r){   
			    if (r){   
			        $.ajax({
	  					url:url,
	  					type:'post',
	  					dataType:'json',
	  					success:function(json){
	  						$.messager.alert("提醒",""+json.sucNum+"条记录删除成功;<br/>"+json.failNum+"条记录删除失败;<br/>"+json.lockNum+"条记录被被其他人操作,暂不能删除;<br/>"+json.unAllowDel+"条记录被引用,不允许删除;<br/>");
	  						$("#department-table-departmentList").datagrid('reload');
	  					}
	  				});
			    }   
			});  
		});
		
		//启用 
		$("#departmentList-enable-enableMenu").click(function(){
			if($(this).linkbutton('options').disabled){
				return false;
			}
			var department=$("#department-table-departmentList").datagrid('getSelected');
  			if(department==null){
  				$.messager.alert("提醒","请选择相应的部门!");
  				return false;
  			}
  			else if(department.state != 0 && department.state != 2){
  				$.messager.alert("提醒","该部门状态不能进行该操作!");
  				return false;
  			}
  			else if(department.state == 1){
  				$.messager.alert("提醒","该部门状态已启用!");
  				return false;
  			}
  			var url="",str="";
  			var types = "";
  			if(department.leaf == "0"){//不是末级，可能为父级,可能为一级（鸿都百货下级）
  				url="basefiles/enableDepartMentAll.do?id="+department.id+"&pId="+department.pid;
				str = "下级都将启用,确定要启用吗?"
				types = "all";
  			}
  			else{
  				url='basefiles/enableDepartMent.do?id='+department.id+'&pId='+department.pid;
				str = "确定要启用吗?"
				types = "one";
  			}
  			$.messager.confirm("提醒",""+str+"",function(r){
  				if(r){
  					$.ajax({
	  					url:url,
	  					type:'post',
	  					dataType:'json',
	  					success:function(json){
	  						$("#department-table-departmentList").datagrid('reload');
  							if(types == "all"){
  								$.messager.alert("提醒",""+json.invNum+"条记录不符合启用条件,无效启用;<br/>"+json.sucNum+"条记录启用成功;<br/>"+json.failNum+"条记录启用失败;");
  							}
  							else{
  								if(json.pState == "0"){
				    				$.messager.alert("提醒","上级部门状态为禁用,不能启用该子级部门!");
				    				return false;
				    			}
				    			else{
				    				if(json.flag){
				    					$.messager.alert("提醒","启用成功!");
				    				}
				    				else{
				    					$.messager.alert("提醒","启用失败!");
				    				}
				    			}
  							}
	  					}
	  				});
  				}
  				else{
  					if(department.leaf == "0"){
						var ret=department_AjaxConn({id:department.id,pId:department.pid},'basefiles/enableDepartMent.do');
						var json = $.parseJSON(ret);
						if(json.pState == "0"){
		    				$.messager.alert("提醒","上级部门状态为禁用,不能启用该子级部门!");
		    				return false;
		    			}
		    			else{
		    				if(json.flag){
					    		$("#department-table-departmentList").datagrid('reload');
		    					$.messager.alert("提醒","启用成功!");
		    				}
		    				else{
		    					$.messager.alert("提醒","启用失败!");
		    				}
		    			}
					}
  				}
  			});
		});
		
		//禁用 
		$("#departmentList-disable-disableMenu").click(function(){
			if($(this).linkbutton('options').disabled){
				return false;
			}
			var department=$("#department-table-departmentList").datagrid('getSelected');
  			if(department==null){
  				$.messager.alert("提醒","请选择相应的部门!");
  				return false;
  			}
  			else if(department.state != 1){
  				$.messager.alert("提醒","该部门状态不能进行该操作!");
  				return false;
  			}
  			else if(department.state == 0){
  				$.messager.alert("提醒","该部门状态已禁用!");
  				return false;
  			}
  			var url="",str="";
  			var types = "";
  			if(department.leaf == "0"){//不是末级，可能为父级,可能为一级（鸿都百货下级）
  				url="basefiles/disableDepartMentAll.do?id="+department.id;
				str = "下级都将禁用,确定要禁用吗?"
				types = "all";
  			}
  			else{
  				url="basefiles/disableDepartMent.do?id="+department.id;
				str = "确定要禁用吗?";
				types = "one";
  			}
			$.messager.confirm("提醒",""+str+"",function(r){
  				if(r){
  					loading("禁用中..");
  					$.ajax({
						url:url,
						type:'post',
						dataType:'json',
						success:function(json){
							loaded();
				    		$("#department-table-departmentList").datagrid('reload');
				    		$.messager.alert("提醒",""+json.lockNum+"条记录被他人操作,暂无法禁用;<br/>"+json.sucNum+"条记录禁用成功;<br/>"+json.failNum+"条记录禁用失败;");
						}
					});
  				}
  			});
		});
		
		//导入
		$("#departmentList-in-inMenu").Excel('import',{
			clazz: "departMentService", //spring中注入的类名
	 		method: "addDRdeptMent", //插入数据库的方法
	 		tn: "t_base_department", //表名
            module: 'basefiles', //模块名，
	 		pojo: "DepartMent", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.DepartMent
			onClose: function(){ //导入成功后窗口关闭时操作，
		         $("#department-table-departmentList").datagrid('clearSelections');	  
		         $("#department-table-departmentList").datagrid('reload');	//更新列表	                                                                                        
			}
		});
		
		//导出 
		$("#departmentList-out-outMenu").Excel('export',{
			queryForm: "#department-form-departmentListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
	 		tn: 't_base_department', //表名
	 		name:'部门档案列表'
		});
		
	});
     </script>
  </body>
</html>
