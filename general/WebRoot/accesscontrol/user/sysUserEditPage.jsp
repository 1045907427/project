<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>系统用户修改</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sysUser-form-editSysUser" action="accesscontrol/editSysUser.do" method="post">
	    	<div data-options="region:'north',border:false" style="height: 165px;">
	    		<table cellpadding="1px;" cellspacing="0" border="0">
					<tr>
						<td style="text-align: right;">用户名:</td>
						<td><input type="text" name="user.username" value="<c:out value="${user.username }"></c:out>" <c:if test="${colMap.username==null}">disabled="disabled"</c:if> class="easyui-validatebox" required="true" validType="usernameCheck" style="width: 130px" />
							<input id="sysUser-hidden-hdDeptName" type="hidden" name="user.departmentname" value="<c:out value="${user.departmentname}"></c:out>"/>
							<input id="sysUser-hidden-hdUserAuthorityid" type="hidden" name="userAuthority.authorityid" value="${userAuthorityStr }" />
							<input id="sysUser-hidden-hdLockFlag" type="hidden" value="${lockFlag }"/>
							<input id="sysUser-hiiden-hdUserid" type="hidden" name="user.userid" value="${user.userid }"/>
							<input id="sysUser-hidden-hdState" type="hidden" name="user.state"/>
							<input type="hidden" name="user.password" value="${user.password}"/>
							<input type="hidden" id="user-username" value="<c:out value="${user.username }"></c:out>"/>
						</td>
						<td style="text-align: right;">姓名:</td>
						<td><input type="text" id="user-name" name="user.name" value="<c:out value="${user.name }"></c:out>" <c:if test="${colMap.name==null}">disabled="disabled"</c:if> class="easyui-validatebox" validType="chineseChar[20]" style="width: 130px" /></td>
						<td style="text-align: right;">所属部门:</td>
						<td><input type="text" id="sysUser-widget-Dept" name="user.departmentid" <c:if test="${colMap.departmentid==null}">disabled="disabled"</c:if> value="${user.departmentid }" style="width: 130px" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">工作岗位:</td>
						<td><input type="text" id="sysUser-widget-workjob" name="user.workjobid" style="width: 200px" value="${user.workjobid }"/></td>
						<td style="text-align: right;">关联人员:</td>
						<td><input type="text" id="sysUser-widget-personnel" <c:if test="${colMap.personnelid==null}">disabled="disabled"</c:if> name="user.personnelid" value="${user.personnelid}" style="width: 130px" /></td>
						<td style="text-align: right;">状态:</td>
						<td><select id="sysUser-select-state" disabled="disabled" style="width: 130px">
								<option value="1" <c:if test="${user.state=='1' }">selected="selected"</c:if>>启用</option>
								<option value="0" <c:if test="${user.state=='0' }">selected="selected"</c:if>>禁用</option>
								<option value="2" <c:if test="${user.state=='2' }">selected="selected"</c:if>>保存</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">允许修改密码:</td>
						<td>
							<select style="width: 130px;" name="user.ispwd">
								<option value="1" <c:if test="${user.ispwd=='1' }">selected="selected"</c:if>>是</option>
								<option value="0" <c:if test="${user.ispwd=='0' }">selected="selected"</c:if>>否</option>
							</select>
						</td>
						<td style="text-align: right;">是否手机用户:</td>
						<td>
							<select style="width: 130px;" name="user.isphone">
								<option value="0" <c:if test="${user.isphone=='0' }">selected="selected"</c:if>>否</option>
								<option value="1" <c:if test="${user.isphone=='1' }">selected="selected"</c:if>>是</option>
							</select>
						</td>
						<td style="text-align: right;">是否上传定位:</td>
						<td>
							<select style="width: 130px;" name="user.isuploadlocation">
								<option value="0" <c:if test="${user.isuploadlocation=='0' }">selected="selected"</c:if>>否</option>
								<option value="1" <c:if test="${user.isuploadlocation=='1' }">selected="selected"</c:if>>是</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">性别:</td>
						<td><input type="text" id="sysUser-combobox-sex" name="user.sex" value="${user.sex }" <c:if test="${colMap.sex==null}">disabled="disabled"</c:if>  style="width: 130px" /></td>
						<td style="text-align: right;">出生日期:</td>
						<td><input type="text" name="user.birthday" value="${user.birthday }" <c:if test="${colMap.birthday==null}">disabled="disabled"</c:if> style="width:130px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
						<td style="text-align: right;">序号:</td>
						<td><input type="text" name="user.seq" style="width: 130px" class="easyui-numberbox" value="${user.seq }"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">联系电话:</td>
						<td colspan="5"><input type="text" name="user.telphone" value="${user.telphone }" style="width: 130px" /></td>		
					</tr>
				</table>
				<ul class="tags">
					<li id="firstli" class="selectTag">
						<a href="javascript:void(0)">角色列表</a>
					</li>
					<li>
						<a href="javascript:void(0)">数据权限</a>
					</li>
				</ul>
	    	</div>
	  		<div id="sysUser-center-div" data-options="region:'center',split:false,border:false">
				<div class="tagsDiv">
					<div class="tagsDiv_item">
                        <div style="float: left;width: 300px;">
						    <table id="sysUser-table-authorityList"></table>
                        </div>
                        <div style="float: left;margin-left: 100px;">
                            <div id="sysUser-div-menutree-scope" style="margin-bottom: 30px;">
                                <div id="sysUser-div-menutree" class="ztree" ></div>
                            </div>
                        </div>
					</div>
					<div class="tagsDiv_item" style="float: left;">
				        <div style="float: left;width: 300px;">
				           <div id="accesscontrol-toolbar-dataruleList" class="buttonBG">
				           	<security:authorize url="/accesscontrol/showDataruleAddPageByUserid.do">
				           		<a href="javaScript:void(0);" id="accesscontrol-add-addDatarule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">添加</a>
				           	</security:authorize>
				           	<security:authorize url="/accesscontrol/showDataruleEditPageByUserid.do">
				           		<a href="javaScript:void(0);" id="accesscontrol-button-editDatarule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
				           	</security:authorize>
				           	<security:authorize url="/accesscontrol/deleteDataruleByUserid.do">
				           		<a href="javaScript:void(0);" id="accesscontrol-delete-deleteDatarule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
				           	</security:authorize>
				           	</div>
				           	<table id="sysUser-table-dataruleList"></table>
						</div>
						<div style="float: left;margin-left: 100px;">
							<div id="datarule-div-ruleInfo"></div>
							<div style="float: left;padding: 30px;">
								<div id="datarule-div-ruleInfo-remark-div" style="float: left;display: none;">备注：</div>
								<div style="float: left;" id="datarule-div-ruleInfo-remark"></div>
							</div>
						</div>
					</div>
				</div>
	  		</div>
	  		<div data-options="region:'south'">
                <div class="buttonDetailBG" style="height:30px;text-align:right;">
                    <input type="button" name="savegoon" id="sysUser-save-saveMenu" value="保存"/>
                </div>
	  		</div>
  		</form>
  	</div>
  	<div id="datarule-div-ruleInfo-add">
  		<iframe id="datarule-div-ruleInfo-add-content" frameborder="0" style="border:0;width:100%;height:100%;min-width: 600px;"></iframe>
  	</div>
  	<script type="text/javascript">
  		var $authorityList = $("#sysUser-table-authorityList");
  		var $dataruleList = $("#sysUser-table-dataruleList");
        var associate = '${associate}';
        associate =  $.parseJSON(associate);
  		//表单数据验证
  		$.extend($.fn.validatebox.defaults.rules, {
  			chineseChar:{
   				validator : function(value,param) { 
		            return value.length < param[0]; 
		        }, 
		        message : '最大允许输入20个字符' 
   			},
   			usernameCheck:{//用户名的唯一性检验
		    	validator: function(value){
		    		var flag = true;
		    		if(value != $("#user-username").val()){
		    			$.ajax({   
				            url :'accesscontrol/usernameCheck.do?username='+value,
				            type:'post',
				            dataType:'json',
				            async: false,
				            success:function(json){
				            	flag =  json.flag;
				            }
				        });
		    		}
			    	return flag;
		    	},
		    	message:'用户名重复，请重新输入!'
		    }
  		});
  		
		
  		//初次加载
  		$(function(){
  			//从代码表中获取性别类型 
//	  		$('#sysUser-combobox-sex').combobox({
//			    url:'common/sysCodeList.do?type=sex',
//			    valueField:'id',
//			    textField:'name'
//			});
            $("#sysUser-combobox-sex").widget({
                referwid:'RL_T_SYS_CODESEX',
                singleSelect:true,
                width:130
            });
			//部门 
		  	$("#sysUser-widget-Dept").widget({
				name:'t_sys_user',
				col:'departmentid',
				width:'130',
				singleSelect:true,
				onlyLeafCheck:false,
				onSelect:function(data){
					$("#sysUser-hidden-hdDeptName").val(data.name);
				},
				onClear:function(){
					$("#sysUser-hidden-hdDeptName").val("");
				},
		  		onLoadSuccess:function(){
		  			return true;
		  		}
			}); 
			
			//关联人员列表 
		  	$("#sysUser-widget-personnel").widget({
				name:'t_sys_user',
				col:'personnelid',
				width:'130',
				singleSelect:true,
				onlyLeafCheck:true,
				onSelect:function(data){
                    var key = data.id ;
                    if(key in associate ){
                        var name = $("#user-name").val();
                        if(name != data.name){
                            $.messager.alert("提醒","关联失败，该用户已关联系统用户:"+ associate[key]);
                        }
                    }else{
                        $.messager.alert("提醒","关联成功");
                    }
                    //$("#sysUser-hidden-hdPersonnelid").val(data.id);
				},
		  		onLoadSuccess:function(){
		  			return true;
		  		}
			}); 
  			
  			//工作岗位
		  	$("#sysUser-widget-workjob").widget({
				name:'t_sys_user',
				col:'workjobid',
				singleSelect:true,
				onlyLeafCheck:false,
				width:'130',
				onSelect:function(data){
					$('#sysUser-table-authorityList').datagrid("unselectAll");
					$.ajax({   
			            url :'basefiles/getRoleListByWorkjob.do?id='+data.id,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	for(var i=0;i<json.length;i++){
			            		var id = json[i];
			            		var index = $('#sysUser-table-authorityList').datagrid("getRowIndex",id);
			            		$('#sysUser-table-authorityList').datagrid("selectRow",index);
			            	}
			            }
			        });
				},
		  		onLoadSuccess:function(){
		  			return true;
		  		}
			});
  			if("${lockFlag}" == "0"){//加锁
  				$.messager.alert("提醒","数据已加锁!");
  				return false;
  			}
			$("#accesscontrol-add-addDatarule").click(function(){
				var url = 'accesscontrol/showDataruleAddPageByUserid.do?userid=${user.userid }';
				$("#datarule-div-ruleInfo-add").dialog({  
				    title: '用户数据权限新增',  
				    fit:true,
				    collapsible:false,
				    minimizable:false,
				    maximizable:true,
				    resizable:true,
				    closed: true,  
				    cache: false,  
				    modal: true
				});
				$('#datarule-div-ruleInfo-add-content').attr("src",url); 
				$("#datarule-div-ruleInfo-add").dialog("open");
			});
			$("#accesscontrol-button-editDatarule").click(function(){
				var datarule = $dataruleList.datagrid('getSelected');
				if(datarule==null){
					$.messager.alert("提醒","请选择数据权限！");
		    		return false;
				}else{
					var url = 'accesscontrol/showDataruleEditPageByUserid.do?userid=${user.userid }&dataid='+datarule.dataid;
					$("#datarule-div-ruleInfo-add").dialog({  
					    title: '用户数据权限修改',  
					    fit:true,
					    collapsible:false,
					    minimizable:false,
					    maximizable:true,
					    resizable:true,
					    closed: true,  
					    cache: false,  
					    modal: true
					});
					$('#datarule-div-ruleInfo-add-content').attr("src",url);  
					$("#datarule-div-ruleInfo-add").dialog("open");
				}
			});
			//删除数据权限规则
			$("#accesscontrol-delete-deleteDatarule").click(function(){
				var datarule = $dataruleList.datagrid('getSelected');
				if(datarule==null){
					$.messager.alert("提醒","请选择数据权限！");
		    		return false;
				}else{
					$.messager.confirm("提醒", "是否删除"+datarule.dataname+"数据权限?", function(r){
						if (r){
							var url = "accesscontrol/deleteDatarule.do?dataid="+datarule.dataid;
							$.ajax({   
					            url :url,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","删除成功！");
					            		$dataruleList.datagrid('reload');
					            		$("#datarule-div-ruleInfo").html("");
					            	}else{
					            		$.messager.alert("提醒","删除失败！");
					            	}
					            }
					        });
				        }
					});
				}
			});
  			//保存按钮 
			$("#sysUser-save-saveMenu").click(function(){
                var personnelid = $("#sysUser-widget-personnel").widget('getValue');
                if(personnelid in associate){
                    var name = $("#user-name").val();
                    if(name != associate[personnelid]){
                        $.messager.alert("提醒","关联失败，该用户已关联系统用户:"+ associate[personnelid]);
                        $("#sysUser-widget-personnel").focus();
                        return false ;
                    }
                }
				$.messager.confirm("提醒", "是否修改系统用户信息?", function(r){
					if (r){
						var s ="";
						var sysUserRows = $('#sysUser-table-authorityList').datagrid('getChecked');

                        for(var i=0;i<sysUserRows.length;i++){
							s = s + sysUserRows[i].authorityid + ",";
						}
				    	$("#sysUser-hidden-hdUserAuthorityid").val(s);
				    	$("#sysUser-hidden-hdState").val($("#sysUser-select-state option:selected").val());
						$("#sysUser-form-editSysUser").submit();
					}
				});
			});
			$("#sysUser-form-editSysUser").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	//表单提交前 弹出提交等待页面
			    	//默认方式
			    	//loading();
			    	//可传值
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	//转为json对象
			    	var json = $.parseJSON(data);
			    	$("#sysUser-form-editSysUser").validateFormSubmit({
			    		json:json,
			    		successMsg:'修改成功',
			    		failMsg:'修改失败',
			    		//添加成功后 当添加了isSuccess事件后，添加成功后successMsg将不会弹出提醒框
			    		isSuccess:function(){
			    			tabsWindowURL("/accesscontrol/showSysUserPage.do").$("#sysUser-table-showSysUserList").datagrid('reload');
			    			tabsWindowURL("/accesscontrol/showSysUserPage.do").$("#sysUser-table-showSysUserList").datagrid('clearSelections');
			    			$.messager.alert("提醒",'修改成功');
			    			top.closeTab('系统用户修改');
			    		},
			    		//添加失败 当添加了isFail事件后，添加成功后failMsg将不会弹出提醒框
			    		isFail:function(){
			    			$.messager.alert("提醒",'修改失败');
			    		}
			    	});
			    }  
			});
            var loadTree = false;
			$(".tags").find("li").click(function(){
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 0){
					var cheight = $(window).height()-155;
					if(!$authorityList.hasClass("create-datagrid")){
						//角色列表
						$authorityList.datagrid({ 
				  	 		method:'post',
							columns:[[ 
								{field:'ck',checkbox:true},
						    	{field:'authorityname',title:'角色名称',width:100},
						        {field:'description',title:'角色描述',width:240},
						        {field:'alias',title:'角色别名',width:100}  
						    ]],
						    height:cheight,
				  	 		rownumbers:false,
				  	 		checkOnSelect:true,
				  	 		selectOnCheck:true,
				  	 		singleSelect:false,
				  	 		idField:'authorityid',
						    url:'accesscontrol/showAuthorityList.do',
						    onLoadSuccess:function(data){
						    	if("${lockFlag }" == "0"){//加锁 
						    		for(var i=0;i<=data.rows.length;i++){
										$("input[type='checkbox']")[i].disabled=true;
									}
						    	}
						    	var userAuthorityArr = "${userAuthorityStr}".split(',');
						    	for(var i=0;i<userAuthorityArr.length-1;i++){
                                    if(i==userAuthorityArr.length-2){
                                        loadTree = true;
                                    }
						    		var index = $('#sysUser-table-authorityList').datagrid("getRowIndex",userAuthorityArr[i]);
                                    $('#sysUser-table-authorityList').datagrid("checkRow",index);
                                }
						    },
                            onCheck: function(rowIndex,rowData){
                                if(loadTree){
                                    reloadTree();
                                }
                            },
                            onUncheck: function(rowIndex,rowData){
                                reloadTree();
                            },
                            onCheckAll: function(rows){
                                reloadTree();
                            },
                            onUncheckAll: function(rows){
                                reloadTree();
                            }
						});
						$authorityList.addClass("create-datagrid");
                        $("#sysUser-div-menutree-scope").height(cheight);
					}
				}
				if(index == 1){
					if(!$dataruleList.hasClass("create-datagrid")){
						var cheight = $("#sysUser-center-div").height()-5;
						//角色列表
						$dataruleList.datagrid({ 
				  	 		method:'post',
							columns:[[ 
								{field:'ck',checkbox:true},  	
								{field:'dataid',title:'编号',width:120,hidden:true}, 
								{field:'dataname',title:'资源名称',width:200},  		        
								{field:'type',title:'类型',width:100,
						        	formatter:function(val){
						        		if(val=='1'){
						        			return "数据字典";
						        		}else{
						        			return "参照窗口";
						        		}
						        	}
						        }  
						    ]],
						    width:350,
						    height:cheight,
				  	 		rownumbers:false,
				  	 		checkOnSelect:true,
				  	 		selectOnCheck:true,
				  	 		singleSelect:true,
				  	 		idField:'dataid',
				  	 		toolbar:"#accesscontrol-toolbar-dataruleList",
						    url:'accesscontrol/getDataruleListByUserid.do?userid=${user.userid }',
						    onClickRow:function(rowIndex, rowData){
						    	$("#datarule-div-ruleInfo").queryRule({
									rules:rowData.rule,
									name:rowData.tablename,
									restype:rowData.type,
									type:'view'
								});
						    	if(null!=rowData.remark && rowData.remark!=""){
						    		$("#datarule-div-ruleInfo-remark-div").show();
						    		$("#datarule-div-ruleInfo-remark").html(rowData.remark.replace("\n","</br>"));
						    	}else{
						    		$("#datarule-div-ruleInfo-remark").html("");
						    		$("#datarule-div-ruleInfo-remark-div").hide();
						    	}
						    }
						});
						$dataruleList.addClass("create-datagrid");
					}
				}
			});
			$("#firstli").click();
  		});
        //根据选中的数据权限刷新菜单
        function reloadTree(){
            var rows = $authorityList.datagrid("getChecked");
            var roleids = "";
            if(rows.length>0){
                for(var i=0;i<rows.length;i++){
                	if(rows[i]!=null){
                		if(roleids ==""){
                            roleids = rows[i].authorityid;
                        }else{
                            roleids += ","+rows[i].authorityid;
                        }
                	}
                }
            }else{
                $("#sysUser-div-menutree").hide();
                return false;
            }
            $("#sysUser-div-menutree").show();
            var treeSetting = {
                view: {
                    dblClickExpand: true,
                    showLine: true,
                    selectedMulti: false,
                    showIcon:true,
                    expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
                },
                async: {
                    enable: true,
                    url: "accesscontrol/showMenuTreeByRoleids.do",
                    otherParam:  {"roleids":roleids},
                    autoParam: ["id","pId", "name"]
                },
                data: {
                    key:{
                        title:"urlStr"
                    },
                    simpleData: {
                        enable:true,
                        idKey: "id",
                        pIdKey: "pId",
                        rootPId: ""
                    }
                },
                callback: {
                    //点击树状菜单更新页面按钮列表
                    beforeClick: function(treeId, treeNode) {
                        var treeObj = $.fn.zTree.getZTreeObj(treeId);
                        if(treeNode.open){
                            if(treeNode.level>=2){
                                treeObj.expandNode(treeNode, false, true, true);
                            }else{
                                treeObj.expandNode(treeNode, false, false, true);
                            }
                        }else{
                            if(treeNode.level>=2){
                                treeObj.expandNode(treeNode, true, true, true);
                            }else{
                                treeObj.expandNode(treeNode, true, false, true);
                            }
                        }
                        return true;
                    }
                }
            };
            $("#sysUser-div-menutree").html("");
            $.fn.zTree.init($("#sysUser-div-menutree"), treeSetting,null);
        }
  	</script>
  </body>
</html>
