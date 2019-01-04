<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>菜单管理</title>
	<%@include file="/include.jsp" %>   
  </head>
  
  <body>
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north'" style="height: 30px;">
                <table>
                    <tr>
                        <td>权限范围：</td>
                        <td>
                            <select id="accesscontrol-datarule-scope" style="width: 120px;">
                                <option value="1" selected="selected">全局</option>
                                <option value="2">指定用户</option>
                            </select>
                        </td>
                        <td class="accesscontrol-user" style="display: none;">选择用户：</td>
                        <td class="accesscontrol-user" style="display: none;">
                            <input id="accesscontrol-selectUserid" style="width: 200px;"/>
                        </td>
                    </tr>

                </table>
            </div>
            <div data-options="region:'west'" style="width:300px;">
                <div data-options="region:'north'" style="height: 30px;">
                    <div style="background: #bed2df;height: 30px;padding: 2px 0px 2px 0px;">
                        <a href="javaScript:void(0);" id="accesscontrol-tree-expandTree" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">展开</a>
                        <a href="javaScript:void(0);" id="accesscontrol-tree-collapseTree" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">折叠</a>
                    </div>
                </div>
            	<div class="easyui-layout" data-options="fit:true">
            		<div data-options="region:'center'" style="background: #dfecf5;height: 30px;">
                        <div>
                            <div id="accesscontrol-tree-menu" class="ztree"></div>
                        </div>
            		</div>
            	</div>
            </div>
            <div data-options="region:'center'">
                <div class="easyui-layout" fit="true">
                    <div data-options="region:'north'" style="height: 30px;">
                        <div id="accesscontrol-toolbar-menuList" style="background: #bed2df;height: 30px;padding: 2px 0px 2px 0px;">
                            <a href="javaScript:void(0);" id="accesscontrol-edit-editDatarule" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-edit'">修改</a>
                            <a href="javaScript:void(0);" id="accesscontrol-save-saveDatarule" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-save'">保存</a>
                            <a href="javaScript:void(0);" id="accesscontrol-delete-deleteDatarule" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'">删除</a>
                        </div>
                    </div>
                    <div data-options="region:'center'">
                        <form action="accesscontrol/addOrUpdateDatarule.do" method="post" id="dataAccess-form-editDatarule">
                        <div id="datarule-showdiv" style="display: none;">
                            <div>
                                <table>
                                    <tr>
                                        <td>数据权限名称：</td>
                                        <td>
                                            <input id="datarule-input-name" name="datarule.dataname" style="width:400px;" readonly="readonly"/>
                                            <input type="hidden" id="dataAccess-hidden-tablename" style="width:400px" name="datarule.tablename" />
                                            <input type="hidden" id="dataAccess-hidden-ruleedit" name="datarule.rule" value=""/>
                                            <input type="hidden" id="dataAccess-hidden-dataid" name="datarule.dataid" />
                                            <input type="hidden" id="dataAccess-hidden-edittype" value="view"/>
                                            <input type="hidden" id="dataAccess-hidden-scope" name="datarule.scope" value="1"/>
                                            <input type="hidden" id="dataAccess-hidden-userid" name="datarule.userid"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>备注：</td>
                                        <td>
                                            <textarea id="datarule-input-remark"  name="datarule.remark" style="width:400px;height: 60px;" readonly="readonly"></textarea>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div id="datarule-div-ruleInfo"></div>
                        </div>
                        </form>
                        <div id="datarule-no-showdiv" style="display: none;">
                            	未添加数据权限！
                        </div>
                        <div id="datarule-no-showdiv-userid" style="display: none;">
                            未添加数据权限！以全局配置的数据权限来控制。
                        </div>
                    </div>
                </div>
			</div>
        </div>  
        <div id="accesscontrol-dialog-operate"></div>
    <script type="text/javascript">
    	var zTree;
        var selectNote;

		$(function(){
            reloadTreeAll();
            $("#accesscontrol-selectUserid").widget({
                referwid:'RL_T_SYS_USER',
                width:'200',
                singleSelect:true,
                onSelect:function(data){
                    console.info(data.userid);
                    reloadTree(data.userid);
                }
            });
            //展开全部节点
            $("#accesscontrol-tree-expandTree").bind("click", {type:"expandAll"}, expandNode);
            //折叠全部节点
            $("#accesscontrol-tree-collapseTree").bind("click", {type:"collapseAll"}, expandNode);
            //修改数据权限
            $("#accesscontrol-edit-editDatarule").click(function(){
                eidtDatarule();
            });
            //保存
            $("#accesscontrol-save-saveDatarule").click(function(){
                var edittype = $("#dataAccess-hidden-edittype").val();
                if(edittype!="edit"){
                    $.messager.alert("提醒","请先修改后再保存！");
                    return false;
                }
                var rules = $("#datarule-div-ruleInfo").queryRule('getRules');
                $("#dataAccess-hidden-ruleedit").val(rules);
                $.messager.confirm("提醒", "是否修改保存数据权限?", function(r){
                    if (r){
                        $("#dataAccess-form-editDatarule").submit();
                    }
                });
            });
            $("#accesscontrol-delete-deleteDatarule").click(function(){
                var dataid = $("#dataAccess-hidden-dataid").val();
                if(dataid==null && dataid!=""){
                    $.messager.alert("提醒","请选择数据权限资源！");
                    return false;
                }else{
                    $.messager.confirm("提醒", "是否删除当前数据权限?", function(r){
                        if (r){
                            var url = "accesscontrol/deleteDatarule.do?dataid="+dataid;
                            $.ajax({
                                url :url,
                                type:'post',
                                dataType:'json',
                                success:function(json){
                                    if(json.flag==true){
                                        $.messager.alert("提醒","删除成功！");
                                        $("#datarule-div-ruleInfo").hide();
                                        $("#datarule-no-showdiv").hide();
                                    }else{
                                        $.messager.alert("提醒","删除失败！");
                                    }
                                }
                            });
                        }
                    });
                }
            });
            $("#dataAccess-form-editDatarule").form({
                onSubmit: function(){
                    var flag = $(this).form('validate');
                    if(flag==false){
                        return false;
                    }
                },
                success:function(json){
                    //转为json对象
                    var data = $.parseJSON(json);
                    if(data.flag==true){
                        eidtDatarule();
                        $.messager.alert("提醒","保存成功");
                    }else{
                        $.messager.alert("提醒","保存失败");
                    }
                }
            });
            $("#accesscontrol-datarule-scope").change(function(){
                var val = $(this).val();
                if(val=="2"){
                    $(".accesscontrol-user").show();
                }else{
                    $("#accesscontrol-selectUserid").widget("clear");
                    reloadTreeAll();
                    $(".accesscontrol-user").hide();
                }
            });
		});
        /**
         * 隐藏无数据权限的菜单
         */
        function hidenMenuNoDatarule(event, treeId, treeNode, msg){
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            var nodes = treeObj.transformToArray(treeObj.getNodes());
            var flag = false;
            for(var i=0;i<nodes.length;i++){
                if(nodes[i].isParent==false && nodes[i].tablename==""){
                    flag = true;
                    treeObj.removeNode(nodes[i]);
                }
            }
            if(flag){
                hidenMenuNoDatarule(event, treeId, treeNode, msg);
            }
        }
        /**
         * zTree树节点展开或者折叠
         * @param e
         * @return
         */
        function expandNode(e) {
            var zTree = $.fn.zTree.getZTreeObj("accesscontrol-tree-menu");
            var type = e.data.type;
            var nodes = zTree.getSelectedNodes();
            if (type.indexOf("All")<0 && nodes.length == 0) {
                alert("请先选择一个父节点");
            }
            if (type == "expandAll") {
                zTree.expandAll(true);
            } else if (type == "collapseAll") {
                zTree.expandAll(false);
            } else {
                var callbackFlag = $("#callbackTrigger").attr("checked");
                for (var i=0, l=nodes.length; i<l; i++) {
                    zTree.setting.view.fontCss = {};
                    if (type == "expand") {
                        zTree.expandNode(nodes[i], true, null, null, callbackFlag);
                    } else if (type == "collapse") {
                        zTree.expandNode(nodes[i], false, null, null, callbackFlag);
                    } else if (type == "toggle") {
                        zTree.expandNode(nodes[i], null, null, null, callbackFlag);
                    } else if (type == "expandSon") {
                        zTree.expandNode(nodes[i], true, true, null, callbackFlag);
                    } else if (type == "collapseSon") {
                        zTree.expandNode(nodes[i], false, true, null, callbackFlag);
                    }
                }
            }
        }

        function eidtDatarule(){
            if(selectNote!=null){
                var userid = $("#dataAccess-hidden-userid").val();
                loading("加载中...");
                $("#datarule-input-remark").attr("readonly",false);
                $.ajax({
                    url:'accesscontrol/getDataRuleInfoByTablenameAndUserid.do',
                    dataType:'json',
                    type:'post',
                    data:{tablename:selectNote.tablename,userid:userid},
                    success:function(json){
                        loaded();
                        if(json.datarule.dataname!=null){
                            $("#dataAccess-hidden-tablename").val(json.datarule.tablename);
                            $("#datarule-input-name").val(json.datarule.dataname);
                            $("#datarule-input-remark").val(json.datarule.remark);
                            $("#dataAccess-hidden-dataid").val(json.datarule.dataid);
                            $("#dataAccess-hidden-scope").val(json.datarule.scope);
                            $("#dataAccess-hidden-userid").val(json.datarule.userid);
                        }else{
                            $("#datarule-input-name").val("");
                            $("#datarule-input-remark").val("");
                            $("#dataAccess-hidden-userid").val(userid);
                        }
                        $("#dataAccess-hidden-edittype").val("edit");
                        $("#datarule-div-ruleInfo").show();
                        $("#datarule-no-showdiv").hide();
                        $("#datarule-no-showdiv-userid").hide();
                        if(userid!=null && userid!=""){
                            $("#datarule-div-ruleInfo").queryRule({
                                rules:json.datarule.rule,
                                name:json.datarule.tablename,
                                scopetype:'2',
                                restype:'1'
                            });
                            $("#dataAccess-hidden-scope").val("2");
                        }else{
                            $("#dataAccess-hidden-scope").val("1");
                            $("#datarule-div-ruleInfo").queryRule({
                                rules:json.datarule.rule,
                                name:json.datarule.tablename,
                                scopetype:'1',
                                restype:'1'
                            });
                        }
                    },
                    error:function(){
                        loaded();
                    }
                });
            }
        }
        //刷新全局树
        function reloadTreeAll(){
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
                    url: "accesscontrol/showMenuTree.do?datarule=1",
                    otherParam:  { "datarule":"1"},
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
                    onAsyncSuccess: hidenMenuNoDatarule,
                    //点击树状菜单更新页面按钮列表
                    beforeClick: function(treeId, treeNode) {
                        if(treeNode.tablename!=""){
                            selectNote = treeNode;
                            $("#datarule-showdiv").show();
                            $("#datarule-no-showdiv").hide();
                            loading("加载中...");
                            $("#dataAccess-hidden-edittype").val("view");
                            $.ajax({
                                url:'accesscontrol/getDataRuleInfoByTablenameAndUserid.do',
                                dataType:'json',
                                type:'post',
                                data:{tablename:treeNode.tablename},
                                success:function(json){
                                    loaded();
                                    $("#dataAccess-hidden-dataid").val(json.datarule.dataid);
                                    $("#dataAccess-hidden-scope").val("1");
                                    $("#dataAccess-hidden-userid").val("");
                                    if(json.datarule.dataname!=null){
                                        $("#datarule-input-name").val(json.datarule.dataname);
                                        $("#datarule-input-remark").val(json.datarule.remark);
                                    }else{
                                        $("#datarule-input-name").val("");
                                        $("#datarule-input-remark").val("");
                                    }
                                    if(json.datarule.rule!=null && json.datarule.rule!=""){
                                        $("#datarule-div-ruleInfo").show();
                                        $("#datarule-div-ruleInfo").queryRule({
                                            rules:json.datarule.rule,
                                            name:json.datarule.tablename,
                                            restype:'1',
                                            type:'view'
                                        });
                                    }else{
                                        $("#datarule-div-ruleInfo").hide();
                                        $("#datarule-no-showdiv").show();
                                        $("#datarule-no-showdiv-userid").hide();
                                    }

                                },
                                error:function(){
                                    loaded();
                                }
                            });
                        }else{
                            var treeObj = $.fn.zTree.getZTreeObj(treeId);
                            if(treeNode.open){
                                if(treeNode.level>=1){
                                    treeObj.expandNode(treeNode, false, true, true);
                                }else{
                                    treeObj.expandNode(treeNode, false, false, true);
                                }
                            }else{
                                if(treeNode.level>=1){
                                    treeObj.expandNode(treeNode, true, true, true);
                                }else{
                                    treeObj.expandNode(treeNode, true, false, true);
                                }
                            }

                            $("#dataAccess-hidden-dataid").val("");
                            $("#datarule-showdiv").hide();
                            $("#datarule-no-showdiv").hide();
                            $("#datarule-no-showdiv-userid").hide();
                        }
                        $("#datarule-input-remark").attr("readonly",true);
                        return true;
                    }
                }
            };
            $.fn.zTree.init($("#accesscontrol-tree-menu"), treeSetting,null);
        }
        //刷新指定用户的菜单树
        function reloadTree(userid){
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
                    otherParam:  { "datarule":"1","userid":userid},
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
                    onAsyncSuccess: hidenMenuNoDatarule,
                    //点击树状菜单更新页面按钮列表
                    beforeClick: function(treeId, treeNode) {
                        if(treeNode.tablename!=""){
                            selectNote = treeNode;
                            $("#datarule-showdiv").show();
                            $("#datarule-no-showdiv").hide();
                            loading("加载中...");
                            $("#dataAccess-hidden-edittype").val("view");
                            $.ajax({
                                url:'accesscontrol/getDataRuleInfoByTablenameAndUserid.do',
                                dataType:'json',
                                type:'post',
                                data:{tablename:treeNode.tablename,userid:userid},
                                success:function(json){
                                    loaded();
                                    $("#dataAccess-hidden-dataid").val(json.datarule.dataid);
                                    if(json.datarule.dataname!=null){
                                        $("#datarule-input-name").val(json.datarule.dataname);
                                        $("#datarule-input-remark").val(json.datarule.remark);
                                        $("#dataAccess-hidden-scope").val(json.datarule.scope);
                                        $("#dataAccess-hidden-userid").val(json.datarule.userid);
                                    }else{
                                        $("#datarule-input-name").val("");
                                        $("#datarule-input-remark").val("");
                                        $("#dataAccess-hidden-scope").val("2");
                                        $("#dataAccess-hidden-userid").val(userid);
                                    }
                                    if(json.datarule.rule!=null && json.datarule.rule!=""){
                                        $("#datarule-div-ruleInfo").show();
                                        $("#datarule-div-ruleInfo").queryRule({
                                            rules:json.datarule.rule,
                                            name:json.datarule.tablename,
                                            restype:'1',
                                            scopetype:'2',
                                            type:'view'
                                        });
                                    }else{
                                        $("#datarule-div-ruleInfo").hide();
                                        $("#datarule-no-showdiv").hide();
                                        $("#datarule-no-showdiv-userid").show();
                                    }

                                },
                                error:function(){
                                    loaded();
                                }
                            });
                        }else{
                            var treeObj = $.fn.zTree.getZTreeObj(treeId);
                            if(treeNode.open){
                                if(treeNode.level>=1){
                                    treeObj.expandNode(treeNode, false, true, true);
                                }else{
                                    treeObj.expandNode(treeNode, false, false, true);
                                }
                            }else{
                                if(treeNode.level>=1){
                                    treeObj.expandNode(treeNode, true, true, true);
                                }else{
                                    treeObj.expandNode(treeNode, true, false, true);
                                }
                            }

                            $("#dataAccess-hidden-dataid").val("");
                            $("#datarule-showdiv").hide();
                            $("#datarule-no-showdiv").hide();
                            $("#datarule-no-showdiv-userid").hide();
                        }
                        $("#datarule-input-remark").attr("readonly",true);
                        return true;
                    }
                }
            };
            var treeObj = $.fn.zTree.getZTreeObj("accesscontrol-tree-menu");
            treeObj.destroy();
            $.fn.zTree.init($("#accesscontrol-tree-menu"), treeSetting,null);
        }
    </script>
  </body>
</html>
