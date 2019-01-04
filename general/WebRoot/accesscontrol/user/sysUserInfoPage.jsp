<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <title>系统用户详情</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
  		<form id="sysUser-form-showSysUser" action="accesscontrol/showSysUserInfo.do" method="post">
	  		<div data-options="region:'north',border:false" style="height: 165px;">
		  		<table cellpadding="1px" cellspacing="0" border="0">
					<tr>
						<td style="text-align: right;">用户名:</td>
						<td><input name="user.username" value="<c:out value="${user.username }"></c:out>" readonly="readonly" class="easyui-validatebox" required="true" validType="usernameCheck" style="width: 130px" />
							<input id="sysUser-hidden-hdDeptName" type="hidden" name="user.departmentname" value="<c:out value="${user.departmentname}"></c:out>"/>
							<input id="sysUser-hidden-hdUserAuthorityid" type="hidden" name="userAuthority.authorityid" value="${userAuthorityStr }" />
							<input id="sysUser-hidden-hdLockFlag" type="hidden" value="${lockFlag }"/>
							<input id="sysUser-hiiden-hdUserid" type="hidden" name="user.userid" value="${user.userid }"/>
							<input id="sysUser-hidden-hdState" type="hidden" name="user.state"/>
						</td>
						<td style="text-align: right;">姓名:</td>
						<td><input name="user.name" value="<c:out value="${user.name }"></c:out>" readonly="readonly" class="easyui-validatebox" validType="chineseChar[20]" style="width: 130px" /></td>
						<td style="text-align: right;">部门名称:</td>
						<td><input id="sysUser-widget-Dept" disabled="disabled" name="user.departmentid" value="${user.departmentid }" style="width: 130px" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">工作岗位:</td>
						<td><input id="sysUser-widget-workjob" name="user.workjobid" style="width: 130px" disabled="disabled" value="${user.workjobid }"/></td>
						<td style="text-align: right;">关联人员:</td>
						<td><input id="sysUser-widget-personnel" name="user.personnelid" value="${user.personnelid}" disabled="disabled" style="width: 130px" />
							<a href="javaScript:void(0);" <c:if test="${user.personnelid=='' || user.personnelid==null}">disabled="true"</c:if> id="personnel-button-showPersonnelInfo" class="easyui-linkbutton"  title="查看关联人员信息">详情</a>
						</td>
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
							<select style="width: 130px;" name="user.ispwd" disabled="disabled">
								<option value="1" <c:if test="${user.ispwd=='1' }">selected="selected"</c:if>>是</option>
								<option value="0" <c:if test="${user.ispwd=='0' }">selected="selected"</c:if>>否</option>
							</select>
						</td>
						<td style="text-align: right;">是否手机用户:</td>
						<td>
							<select style="width: 130px;" name="user.isphone" disabled="disabled">
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
						<td><input id="sysUser-combobox-sex" name="user.sex" value="${user.sex }" readonly="readonly"  style="width: 130px" /></td>
						<td style="text-align: right;">出生日期:</td>
						<td><input name="user.birthday" value="${user.birthday }" readonly="readonly" style="width:130px"/></td>
						<td style="text-align: right;">序号:</td>
						<td><input name="user.seq" style="width: 130px" class="easyui-numberbox" readonly="readonly" value="${user.seq }"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">联系电话:</td>
						<td colspan="5"><input type="text" name="user.telphone" value="${user.telphone }" style="width: 130px" readonly="readonly" /></td>		
					</tr>
				</table>
				<ul class="tags" style="min-width: 400px">
					<li id="firstli" class="selectTag">
						<a href="javascript:void(0)">角色列表</a>
					</li>
                    <li>
                        <a href="javascript:void(0)">菜单列表</a>
                    </li>
					<li>
						<a href="javascript:void(0)">数据权限</a>
					</li>
				</ul>
	  		</div>
	  		<div id="sysUser-center-div" data-options="region:'center',split:false,border:false">
				<div class="tagsDiv" style="min-width: 1024px">
					<div class="tagsDiv_item">
						<table id="sysUser-table-authorityList"></table>
					</div>
                    <div class="tagsDiv_item">
                        <div id="sysUser-div-menutree" class="ztree"></div>
                    </div>
					<div class="tagsDiv_item" style="float: left;">
				        <div style="float: left;width: 300px;">
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
  		</form>
  	</div>
  	<script type="text/javascript">
  		var $authorityList = $("#sysUser-table-authorityList");
		var $dataruleList = $("#sysUser-table-dataruleList");
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
		    		if(value != "${user.username }"){
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
  		
  		//从代码表中获取性别类型 
//  		$('#sysUser-combobox-sex').combobox({
//		    url:'common/sysCodeList.do?type=sex',
//		    valueField:'id',
//		    textField:'name'
//		});
        $("#sysUser-combobox-sex").widget({
            referwid:'RL_T_SYS_CODESEX',
            singleSelect:true,
            width:130
        });
		//部门 
	  	$("#sysUser-widget-Dept").widget({
			name:'t_sys_user',
			col:'departmentid',
			singleSelect:true,
			width:'130',
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
			singleSelect:true,
			onlyLeafCheck:true,
			width:'130',
			onSelect:function(data){
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
			width:'130',
			onlyLeafCheck:false,
			onSelect:function(data){
				//$("#sysUser-hidden-hdPersonnelid").val(data.id);
			},
	  		onLoadSuccess:function(){
	  			return true;
	  		}
		});
  		//初次加载
  		$(function(){
			
			//详情按钮 
			$("#personnel-button-showPersonnelInfo").click(function(){
				if($(this).linkbutton('options').disabled){
					return false;
				}
				var baseurl = $("#basePath").attr("href");
				top.addOrUpdateTab('basefiles/showPersonnelPage.do?type=view&id=${user.personnelid}','人员档案');
			});
			
			$(".tags").find("li").click(function(){
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 0){
					var cheight = $("#sysUser-center-div").height()-5;
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
				  	 		url:'accesscontrol/showAuthorityListTrue.do?userid=${user.userid}',
						    onLoadSuccess:function(data){
					    		for(var i=0;i<=data.rows.length;i++){
									$("input[type='checkbox']")[i].disabled=true;
								}
						    	var userAuthorityArr = "${userAuthorityStr}".split(',');
						    	for(var i=0;i<userAuthorityArr.length-1;i++){
						    		$('#sysUser-table-authorityList').datagrid("selectRecord",userAuthorityArr[i]);
						    	}
						    	var rows = $('#sysUser-table-authorityList').datagrid("getSelections");
						    	for(var j=0;j<rows.length;j++){
						    		var index = $('#sysUser-table-authorityList').datagrid("getRowIndex",rows[j]);
						    		$('#sysUser-table-authorityList').datagrid("selectRow",index);
						    	}
						    },
						    onClickRow:function(rowIndex, rowData){
						    	var userAuthorityArr = "${userAuthorityStr}".split(',');
						    	$('#sysUser-table-authorityList').datagrid("unselectRow",rowIndex);
						    	for(var i=0;i<userAuthorityArr.length-1;i++){
						    		if(userAuthorityArr[i].localeCompare(rowData.authorityid) == 0){
						    			$('#sysUser-table-authorityList').datagrid("selectRow",rowIndex);
						    		}
						    	}
						    }
						});
						$authorityList.addClass("create-datagrid");
					}
				}
                if(index == 1){
                    reloadTree();
                }
				if(index == 2){
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
        //刷新指定用户的菜单树
        function reloadTree(){
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
                    url: "accesscontrol/showMenuTreeByUserid.do",
                    otherParam:  { "datarule":"1","userid":'${user.userid }',buttontype:'1'},
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
            $.fn.zTree.init($("#sysUser-div-menutree"), treeSetting,null);
        }
  	</script>
  </body>
</html>
