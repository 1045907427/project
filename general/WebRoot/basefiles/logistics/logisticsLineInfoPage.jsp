<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>线路档案页面</title>
		<%@include file="/include.jsp"%>
	</head>
	<body>
		<input type="hidden" id="lineInfo-opera" />
		<input type="hidden" id="lineInfo-id-hidden" value="${id}" />
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',split:false,border:false" style="height: 30px; overflow: hidden">
				<div class="buttonBG" id="lineInfo-div-button"></div>
			</div>
			<div data-options="region:'center'" style="border: 0px;">
				<div class="easyui-panel" data-options="fit:true" id="wares-div-lineInfo"></div>
				<div id="lineInfo-window-showOldImg"></div>
			</div>
		</div>
		<div id="line-dialog-customer"></div>
		<div id="line-dialog-car"></div>
		<script type="text/javascript">
  		var lineInfo_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return MyAjax.responseText;
		}
		
		$("#line-customer-sortarea").widget({ //区域
   			referwid:'RT_T_BASE_SALES_AREA',
			//col:'salesarea',
   			singleSelect:true,
   			width:110,
   			onlyLeafCheck:false,
   			view: true
   		});
		
		//加载下拉框 
		function loadDropdown(){
			//状态
			$('#lineInfo-widget-state').widget({
				width:120,
				name:'t_base_logistics_line',
				col:'state',
				singleSelect:true
			});
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
	   	
	   	//页面刷新
	   	function panelRefresh(url,title,type){
	   		$("#wares-div-lineInfo").panel({
				href:url,
				title:title,
			    cache:false,
			    maximized:true,
			    border:false,
			    loadingMessage:'数据加载中...'
			});
			$("#lineInfo-opera").attr("value",type);
	   	}
	   
		$(function(){
			//加载按钮
			$("#lineInfo-div-button").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/logisticsLineInfoAddBtn.do">
					{
						type:'button-add',//新增
						handler:function(){
							panelRefresh('basefiles/showLineInfoAddPage.do',' 线路档案【新增】','add');
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoEditBtn.do">
		 			{
			 			type:'button-edit',//修改
			 			handler:function(){
			 				var id = $("#lineInfo-id-baseInfo").val();
			 				var flag = isDoLockData(id,"t_base_logistics_line");
			 				if(!flag){
			 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
			 					return false;
			 				}
			 				panelRefresh('basefiles/showLineInfoEidtPage.do?id='+id,' 线路档案【修改】','edit');
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoSaveBtn.do">
		 			{
			 			type:'button-save',//保存
			 			handler:function(){
			 				var type = $("#lineInfo-div-button").buttonWidget("getOperType");
			 				if(type=="add"){
			 					if(!lineInfoFormValidate()){
				    				return false;
				    			}
			 					$.messager.confirm("提醒","是否保存新增线路档案?",function(r){
			 						if(r){
			 							loading("提交中..");
					 					addLineInfo("save");//保存新增线路档案
			 						}
			 					});
			 				}
			 				else{
                                if($("#lineInfo-select-car").val()==null ||$("#lineInfo-select-car").val()==""){
                                    $.messager.alert("提醒","默认车辆不能为空!");
                                    return false;
                                }
			 					if(!lineInfoFormValidate()){
				    				return false;
				    			}
			 					$.messager.confirm("提醒","是否保存修改线路档案?",function(r){
			 						if(r){
			 							loading("提交中..");
										editLineInfo("save");//保存修改线路档案
									}
			 					});
			 				}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoDeleteBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
			 				var flag = isLockData($("#lineInfo-id-baseInfo").val(),"t_base_logistics_line");
			 				if(flag){
			 					$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
			 					return false;
			 				}
			 				$.messager.confirm("提醒","是否确定删除线路档案?",function(r){
			 					if(r){
			 						var id = $("#lineInfo-id-baseInfo").val();
			 						var ret = lineInfo_AjaxConn({idStr:id},'basefiles/deleteLineInfo.do');
									var retJSON = $.parseJSON(ret);
									if(retJSON.flag){
										$.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>删除成功"+retJSON.num+"条记录;");
										if (top.$('#tt').tabs('exists','线路档案列表')){
						    				tabsWindow('线路档案列表').$("#logistics-table-logisticsLineInfoList").datagrid('reload');
						    			}
						    			var object = $("#lineInfo-div-button").buttonWidget("removeData",id);
						    			if(null != object){
						    				panelRefresh('basefiles/showLineInfoViewPage.do?id='+object.id,' 线路档案【查看】','view');
						    			}
						    			else{
						    				top.closeTab('线路档案');
						    			}
									}
									else{
										$.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>删除失败"+retJSON.num+"条记录;");
									}
			 					}
			 				});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoCopyBtn.do">
		 			{
			 			type:'button-copy',//复制
			 			handler:function(){
			 				var id = $("#lineInfo-id-baseInfo").val();
			 				panelRefresh('basefiles/showLineInfoCopyPage.do?id='+id,' 线路档案【复制】','copy');
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoEnableBtn.do">
		 			{
			 			type:'button-open',//启用
			 			handler:function(){
			 				$.messager.confirm("提醒","是否确定启用线路档案?",function(r){
			 					if(r){
			 						var ret = lineInfo_AjaxConn({idStr:$("#lineInfo-id-baseInfo").val()},'basefiles/enableLineInfos.do');
									var retJSON = $.parseJSON(ret);
									if(retJSON.flag){
										$.messager.alert("提醒","启用无效"+retJSON.invalidNum+"条记录;<br/>启用成功"+retJSON.num+"条记录;");
										if (top.$('#tt').tabs('exists','线路档案列表')){
						    				tabsWindow('线路档案列表').$("#logistics-table-logisticsLineInfoList").datagrid('reload');
						    			}
						    			var id=$("#lineInfo-id-baseInfo").val();
						    			panelRefresh('basefiles/showLineInfoViewPage.do?id='+id,'线路档案【查看】','view');
										//$("#wares-div-lineInfo").panel('refresh','basefiles/showLineInfoViewPage.do?id='+$("#lineInfo-id-baseInfo").val());
									}
									else{
										$.messager.alert("提醒","启用无效"+retJSON.invalidNum+"条记录;<br/>启用失败"+retJSON.num+"条记录;");
									}
			 					}
			 				});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoDisableBtn.do">
		 			{
			 			type:'button-close',//禁用
			 			handler:function(){
			 				$.messager.confirm("提醒","是否确定禁用线路档案?",function(r){
			 					if(r){
			 						var ret = lineInfo_AjaxConn({idStr:$("#lineInfo-id-baseInfo").val()},'basefiles/disableLineInfos.do');
									var retJSON = $.parseJSON(ret);
									if(retJSON.flag){
										$.messager.alert("提醒",""+retJSON.invalidNum+"条记录状态不允许禁用;<br/>禁用成功"+retJSON.num+"条记录;");
										if (top.$('#tt').tabs('exists','线路档案列表')){
						    				tabsWindow('线路档案列表').$("#logistics-table-logisticsLineInfoList").datagrid('reload');
						    			}
										var id=$("#lineInfo-id-baseInfo").val();
						    			panelRefresh('basefiles/showLineInfoViewPage.do?id='+id,'线路档案【查看】','view');
						    		}
									else{
										$.messager.alert("提醒",""+retJSON.invalidNum+"条记录状态不允许禁用;<br/>禁用失败"+retJSON.num+"条记录;");
									}
			 					}
			 				});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoBackBtn.do">
		 			{
			 			type:'button-back',//上一条
			 			handler:function(data){
			 					panelRefresh('basefiles/showLineInfoViewPage.do?id='+data.id,' 线路档案【查看】','view');
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoNextBtn.do">
		 			{
			 			type:'button-next',//下一条
			 			handler:function(data){
			 					panelRefresh('basefiles/showLineInfoViewPage.do?id='+data.id,' 线路档案【查看】','view');
			 			}
		 			},
		 			</security:authorize>
		 			<security:authorize url="/basefiles/logisticsLineInfoDRBtn.do">
					{
						type: 'button-import',
						attr: {
							type:'importUserdefined',
							url:'basefiles/logisticsLineInfoImport.do',
					 		importparam:'必填项：线路编码',
							onClose: function(){ //导入成功后窗口关闭时操作，
								var id = $("#lineInfo-id-baseInfo").val();
						        panelRefresh('basefiles/showLineInfoViewPage.do?id='+id,' 线路档案【查看】','view');                                                                                      
							}
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoDCBtn.do">
					{
						type: 'button-export',
						attr: {
					 		type:'exportUserdefined',
					 		url:'basefiles/logisticsLineInfoExport.do',
					 		name:'销售订单列表'
						}
					},
					</security:authorize>
					{}
				],
				model:'base',
				type:'bill',
				tab:'线路档案列表',
				datagrid:'logistics-table-logisticsLineInfoList',
				id:'${id}',
				tname:'t_base_logistics_line'
			});
			
			//加载新增线路页面
			var lineInfo_url = "";
			var lineInfo_opera = "add";
			var lineInfo_type = "add";
			var lineInfo_title = "线路档案【新增】";
			if("${type}" == "view"){
				lineInfo_url = "basefiles/showLineInfoViewPage.do?id=${id}"
				lineInfo_opera = "view";
				lineInfo_type = "view";
				lineInfo_title = "线路档案【查看】";
			}
			else if("${type}" == "edit"){
				lineInfo_url = "basefiles/showLineInfoEidtPage.do?id=${id}";
				lineInfo_opera = "edit";
				lineInfo_type = "edit";
				lineInfo_title = "线路档案【修改】";
			}
			else if("${type}" == "copy"){
				lineInfo_url = "basefiles/showLineInfoCopyPage.do?id=${id}";
				lineInfo_type = "copy";
				lineInfo_title = "线路档案【复制】";
			}
			else{
				lineInfo_url = "basefiles/showLineInfoAddPage.do?WCid=${WCid}";
			}
			$("#wares-div-lineInfo").panel({
			    href:lineInfo_url,
				title:lineInfo_title,
			    cache:false,
			    maximized:true,
			    border:false,
			    close:true
			});
			
			$("#lineInfo-opera").attr("value",lineInfo_opera);
			$("#lineInfo-div-button").buttonWidget("initButtonType",lineInfo_opera);
			$("#lineInfo-div-button").buttonWidget("setButtonType","${state}");
			
		});
  	</script>
	</body>
</html>
