<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户拜访记录列表</title>
    <%@include file="/include.jsp" %>    
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center'">
    		<table id="crm-table-crmVisitRecordOrderDetailListPage"></table>
    		<div id="crm-table-query-crmVisitRecordOrderDetailListPage" style="padding:2px;height:auto">
				<div>
                    <div>
                        <security:authorize url="/crm/visitrecord/setOKVisitRecordDetailBtn.do">
                            <a href="javaScript:void(0);" id="crm-btn-setOkCrmVisitRecordOrderDetailListPage" class="easyui-linkbutton" iconCls="button-open" plain="true" title="设置为合格">设置为合格</a>
                        </security:authorize>
                        <security:authorize url="/crm/visitrecord/setNotOKVisitRecordDetailBtn.do">
                            <a href="javaScript:void(0);" id="crm-btn-setNotOkCrmVisitRecordOrderDetailListPage" class="easyui-linkbutton" iconCls="button-close" plain="true" title="设置为不合格">设置为不合格</a>
                        </security:authorize>
                    </div>
					<form action="" id="crm-form-crmVisitRecordOrderDetailListPage" method="post">
                        <table class="querytable">
			    			<tr>
			    				<td>业务日期:</td>
			    				<td><input type="text" id="crm-crmVisitRecordOrderDetailListPage-businessdatestart" name="businessdatestart" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" id="crm-crmVisitRecordOrderDetailListPage-businessdateend" name="businessdateend" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
			    				<td>客户名称:</td>
			    				<td><input type="text" id="crm-crmVisitRecordOrderDetailListPage-customerid" name="customerid" style="width: 180px;"/></td>
			    				<td style="width:60px;">品牌:</td>
			    				<td style="width:300px;"><input type="text" id="crm-crmVisitRecordOrderDetailListPage-brand" name="brandid" style="width:150px;" /></td>
			    			</tr>
			    			<tr>
			    				<td>业务员名称:</td>
			    				<td><input type="text" id="crm-crmVisitRecordOrderDetailListPage-personid" name="personid" style="width: 225px;"/></td>
			    				<td>主管名称:</td>
			    				<td><input type="text" id="crm-crmVisitRecordOrderDetailListPage-leadid" name="leadid" style="width: 180px;"/></td>
			    				<td>陈列标准:</td>
			    				<td>
			    					<select id="crm-crmVisitRecordOrderDetailListPage-standard" name="standard" style="width:150px;">
			    						<option value=""></option>
		    							<c:forEach items="${displayStandard }" var="list">
		    								<option value="${list.code }">${list.codename }</option>
		    							</c:forEach>
			    					</select>
			    				</td>
			    			</tr>
			    			<tr>
			    				<td>客户分类:</td>
			    				<td><input type="text" id="crm-crmVisitRecordOrderDetailListPage-customersort" name="customersort" style="width: 225px;"/></td>
			    				<td>销售区域:</td>
			    				<td><input type="text" id="crm-crmVisitRecordOrderDetailListPage-salesarea" name="salesarea" style="width: 180px;"/></td>
			    				
			    				<td>检查状态:</td>
			    				<td>
			    					<select id="crm-crmVisitRecordOrderDetailListPage-isqa" name="isqa" style="width:150px;">
			    						<option value=""></option>
			    						<option value="0" selected="selected">未检查</option>
			    						<option value="1">合格</option>
			    						<option value="2">不合格</option>
			    					</select>
			    				</td>
			    			</tr>
			    			<tr>
			    				<td>销售部门:</td>
			    				<td><input type="text" id="crm-crmVisitRecordOrderDetailListPage-salesdept" name="salesdept" style="width: 225px;"/>
			    				</td>
			    				<td>备注:</td>
			    				<td><input type="text" name="detailremark" style="width: 180px;"/></td>
			    				<td colspan="2">
			    					<a href="javaScript:void(0);" id="crm-btn-queryCrmVisitRecordOrderDetailListPage" class="button-qr">查询</a>
									<a href="javaScript:void(0);" id="crm-btn-reloadCrmVisitRecordOrderDetailListPage" class="button-qr">重置</a>
			    				</td>
			    			</tr>
			    		</table>
					</form>
				</div>
			</div>
			<div style="display:none">
		    	<div id="crmVisitRecordOrderDetailList-dialog-operate"></div>
		    	<div id="crmVisitRecordOrderDetailList-dialog-operate-Detail"></div>
		    	<div id="crmVisitRecordOrderDetailList-dialog-operate-Map"></div>
	    	</div>
    	</div>
    </div>
    
<style>
		table.dataTable{
			border-collapse: collapse;
			width:305px !important;
			border:none;
			border:0px;
			margin-left:5px;
		}
		.dataTable td.dataTd{			
			border:none;
			overflow:hidden;
			border:0px;
			text-align:left;
		}
		.dataTable td.dataTitle{
			border:none;
			font-weight:bold;
			padding-right: 5px;
			padding-left: 5px;
			width:65px;
		}
		.recordDetailDataDiv{
			width:330px;
			float:left;
			border:0px;
			display:block;
			margin-left:5px;
			margin-right:15px;
		}
		
		.recordDetailDataInnerDiv{
			border:1px solid #ededed;
			width:320px;
			margin:5px;
			overflow:hidden;
		}
		.dataDetailHover{
			border:1px solid #FF4400;
		}
		.dataDetailOver{
			border:1px solid #ededed;
		}
		.dataCont{			
			width:225px !important;
			white-space: pre-wrap;
			word-wrap: break-word;
			word-break:break-all;
		}
	</style>
    
    <script type="text/javascript">
       	var showColumnInLine=3;
    	$(document).ready(function(){
    		var initDetailQueryJSON = null;
						
    		createDetailListDataGrid();
    		
 			$("#crm-table-query-crmVisitRecordOrderDetailListPage-advanced").advancedQuery({
		 		name:'crm_visitrecord',
		 		plain:true,
		 		datagrid:'crm-table-crmVisitRecordOrderDetailListPage'
			});
			
			//回车事件
			controlQueryAndResetByKey("crm-btn-queryCrmVisitRecordListPage","crm-btn-reloadCrmVisitRecordListPage");
			
			$("#crm-btn-queryCrmVisitRecordOrderDetailListPage").click(function(){
				//查询参数直接添加在url中         
       			var queryJSON = $("#crm-form-crmVisitRecordOrderDetailListPage").serializeJSON();					
 				$('#crm-table-crmVisitRecordOrderDetailListPage').datagrid('load',queryJSON);				
			});


			$("#crm-btn-reloadCrmVisitRecordOrderDetailListPage").click(function(){
				$("#crm-form-crmVisitRecordOrderDetailListPage")[0].reset();

				$("#crm-crmVisitRecordOrderDetailListPage-customerid").customerWidget("clear");
				$("#crm-crmVisitRecordOrderDetailListPage-brand").widget("clear");
				$("#crm-crmVisitRecordOrderDetailListPage-personid").widget("clear");
				$("#crm-crmVisitRecordOrderDetailListPage-leadid").widget("clear");
				$("#crm-crmVisitRecordOrderDetailListPage-customersort").widget("clear");
				$("#crm-crmVisitRecordOrderDetailListPage-salesdept").widget("clear");
				$("#crm-crmVisitRecordOrderDetailListPage-salesarea").widget("clear");
				
       			var queryJSON = $("#crm-form-crmVisitRecordOrderDetailListPage").serializeJSON();					
 				$('#crm-table-crmVisitRecordOrderDetailListPage').datagrid('load',queryJSON);		
			});
			
			$("#crm-btn-setOkCrmVisitRecordOrderDetailListPage").click(function(){
				var checkData=getRecordDetailCheckData() || {};
				var idOkArr=checkData.idOkArr ||[];
				var canNotArr=checkData.canNotArr ||[];
				var total=checkData.total || 0;
				if(canNotArr.length==0){
					var msg='抱歉，';
					if(idOkArr.length==total && total!=0){
						msg=msg+"您选择的都是已经设置为合格的记录<br/>。";
						msg=msg+'请选择要设置为合格的拜访记录！';
					}else{
						msg=msg+'请选择要设置为合格的拜访记录！';
					}
					$.messager.alert('提醒',msg);
					return false;
				}
				$.messager.confirm('确认','是否设置为合格?',function(r){
					if(r){
						$.ajax({   
				            url :'crm/visitrecord/setOKVisitRecordDetail.do',
				            data:{idarrs:canNotArr.join(",")},
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	loaded();
					            if(json.flag){
				            		$.messager.alert("提醒","设置成功");
				            		var queryJSON = $("#crm-form-crmVisitRecordOrderDetailListPage").serializeJSON();					
				     				$('#crm-table-crmVisitRecordOrderDetailListPage').datagrid('load',queryJSON);	           		
					            }else{
					            	$.messager.alert("提醒","设置失败");
					            }
				            }
				        });	
					}
				});
				
			});
			$("#crm-btn-setNotOkCrmVisitRecordOrderDetailListPage").click(function(){
				var checkData=getRecordDetailCheckData() || {};
				var idNotOkArr=checkData.idNotOkArr ||[];
				var canOkArr=checkData.canOkArr ||[];
				var total=checkData.total || 0;

				if(canOkArr.length==0){
					var msg='抱歉，';
					if(idNotOkArr.length==total && total!=0){
						msg=msg+"您选择的都是已经设置为不合格的数据<br/>。";
						msg=msg+'请选择想要设置为不合格的数据！';
					}else{
						msg=msg+'请选择想要设置为不合格的拜访数据！';
					}
					$.messager.alert('提醒',msg);
					return false;
				}
				$.messager.confirm('确认','是否设置为不合格?',function(r){
					if(r){
						$.ajax({   
				            url :'crm/visitrecord/setNotOKVisitRecordDetail.do',
				            data:{idarrs:canOkArr.join(",")},
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	loaded();
					            if(json.flag){
				            		$.messager.alert("提醒","设置成功");
				            		var queryJSON = $("#crm-form-crmVisitRecordOrderDetailListPage").serializeJSON();					
				     				$('#crm-table-crmVisitRecordOrderDetailListPage').datagrid('load',queryJSON);	           		
					            }else{
					            	$.messager.alert("提醒","设置失败");
					            }
				            }
				        });	
					}
				});
			});
    	});
    	
    	function createDetailListDataGrid(){
    		initDetailQueryJSON=$("#crm-form-crmVisitRecordOrderDetailListPage").serializeJSON()
    		$("#crm-table-crmVisitRecordOrderDetailListPage").datagrid({
				columns:[[
							{field:'id',title:'隐藏ID',width:50,hidden:true},
							{field:'idok',checkbox:true},
							{field:'data',title:' ',
								formatter: function(value,row,index){
									return renderItem(row);
								}
							}
				]],
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:true,
		 		checkOnSelect:false,
		 		selectOnCheck:false,
		 		showFooter: true,
		 		pageSize:18,
                sortName: 'addtime',
                sortOrder: 'desc',
		 		pageList:[18,30,60,120,300],
				toolbar:'#crm-table-query-crmVisitRecordOrderDetailListPage',
		 		url:"crm/visitrecord/showCrmVisitRecordOrderDetailPageList.do",
				queryParams:initDetailQueryJSON,
	  	 		onLoadSuccess:function(){
					$(this).datagrid("fixRownumber");
	  	 			regeditDataGridEvent();
		 		},
		 		onClickRow: function(rowIndex, rowData){
		        	$(this).datagrid('unselectRow',rowIndex);
		 		},
		 		onCheck:function(index,row){
		 			for(var i=1;i<=showColumnInLine;i++){
		 				if(row["data"+i] && row["data"+i].detailid){
		 					$(":checkbox[checkfor='detailid'][value='"+row["data"+i].detailid+"']").prop("checked",true);
		 				}
		 			}
		 		},
		 		onUncheck:function(index,row){
		 			for(var i=1;i<=showColumnInLine;i++){
		 				if(row["data"+i] && row["data"+i].detailid){
		 					$(":checkbox[checkfor='detailid'][value='"+row["data"+i].detailid+"']").prop("checked",false);
		 				}
		 			}
		 		},
		 		onCheckAll:function(rows){
		 			$(":checkbox[checkfor='detailid']").prop("checked",true);
		 		},
		 		onUncheckAll:function(rows){
		 			$(":checkbox[checkfor='detailid']").prop("checked",false);	
		 		}
			});

 			
 			$("#crm-crmVisitRecordOrderDetailListPage-brand").widget({
				referwid:'RL_T_BASE_GOODS_BRAND',
	    		width:150,
				onlyLeafCheck:false,
				singleSelect:true
			});
    		
    		$("#crm-crmVisitRecordOrderDetailListPage-customerid").customerWidget({ //客户参照窗口
    			name:'crm_visit_record',
				col:'customerid',
    			singleSelect:true,
    			isdatasql:false,
    			width:180,
    			onlyLeafCheck:false
    		});
			$("#crm-crmVisitRecordOrderDetailListPage-personid").widget({
				name:'crm_visit_record',
				col:'personid',
	    		width:225,
				onlyLeafCheck:false,
				singleSelect:true
			});
			$("#crm-crmVisitRecordOrderDetailListPage-leadid").widget({
				name:'crm_visit_record',
				col:'leadid',
	    		width:180,
				onlyLeafCheck:false,
				singleSelect:true
			});

			$("#crm-crmVisitRecordOrderDetailListPage-customersort").widget({
				name:'crm_visit_record',
				col:'customersort',
	    		width:225,
				onlyLeafCheck:false,
				singleSelect:true
			});
			$("#crm-crmVisitRecordOrderDetailListPage-salesdept").widget({
				name:'crm_visit_record',
				col:'salesdept',
	    		width:225,
				onlyLeafCheck:false,
				singleSelect:true
			});
			$("#crm-crmVisitRecordOrderDetailListPage-salesarea").widget({
				name:'crm_visit_record',
				col:'salesarea',
	    		width:180,
				onlyLeafCheck:false,
				singleSelect:true
			});
    	}
    	
    	function renderItem(data){
    		if(null==data || data.length==0){
    			return "";
    		}
    		var htmlsb=new Array();
    		for(var i=1;i<=showColumnInLine;i++){
    			if(data["data"+i] ==null || data["data"+i].detailid == null ){
    				continue;
    			}
	    		htmlsb.push("<div class=\"recordDetailDataDiv\">");
	    		htmlsb.push("<div class=\"recordDetailDataInnerDiv\">");
	    		htmlsb.push("<table class=\"dataTable\" cellspacing=\"0\" cellpadding=\"0\" >");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td colspan=\"2\" style=\"border:none;\">");
	    		htmlsb.push("<input type=\"checkbox\" checkfor=\"detailid\" value=\""+data["data"+i].detailid+"\" isqa=\""+data["data"+i].isqa+"\" />");
	    		htmlsb.push("<span style=\"line-height:18px;\">编号：</span>"+data["data"+i].detailid);
	    		htmlsb.push("</td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td colspan=\"2\" style=\"border:none;\">");
	    		htmlsb.push("<a href=\"javascript:void(0);\" onclick=\"showDetailImg("+data["data"+i].detailid+")\">");
	    		htmlsb.push("<img src=\""+data["data"+i].detailimg+"\" alt=\"展示图片\" style=\"width:300px;height:300px;\"></img>");
	    		htmlsb.push("</a>");
	    		htmlsb.push("</td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td class=\"dataTitle\">业务日期：</td>");
	    		htmlsb.push("<td class=\"dataTd\"><div class=\"dataCont\">"+data["data"+i].businessdate+"</div></td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td class=\"dataTitle\">业务员名称：</td>");
	    		htmlsb.push("<td class=\"dataTd\"><div class=\"dataCont\">");
	    		if(data["data"+i].personname){
	    			htmlsb.push(data["data"+i].personname);
	    		}
	    		htmlsb.push("</div></td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td class=\"dataTitle\">客户名称：</td>");
	    		htmlsb.push("<td class=\"dataTd\"><div class=\"dataCont\">");
	    		if(data["data"+i].customerid){
	    			htmlsb.push(data["data"+i].customerid);
	    		}
	    		htmlsb.push(" ");
	    		if(data["data"+i].customername){
	    			htmlsb.push(data["data"+i].customername);
	    		}
	    		htmlsb.push("</div></td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td class=\"dataTitle\">品牌名称：</td>");
	    		htmlsb.push("<td class=\"dataTd\"><div class=\"dataCont\">"+data["data"+i].brandname+"</div></td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td class=\"dataTitle\">陈列标准：</td>");
	    		htmlsb.push("<td class=\"dataTd\">"+data["data"+i].standardname+"</td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td class=\"dataTitle\">是否检查：</td>");
	    		htmlsb.push("<td class=\"dataTd\">");
	    		if(data["data"+i].isqa=='0'){
	    			htmlsb.push("未检查");
	    		}else if(data["data"+i].isqa=='1'){
	    			htmlsb.push("合格");
	    		}else if(data["data"+i].isqa=='2'){
	    			htmlsb.push("不合格");
	    		}else{
	    			htmlsb.push("未检查");
	    		}
	    		htmlsb.push("</td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td class=\"dataTitle\">GPS坐标：</td>");
	    		htmlsb.push("<td class=\"dataTd\">");
	    		if(isMapGps(data["data"+i].detailgps)){
	    			htmlsb.push("<a href=\"javascript:void(0);\" onclick=\"showMapInfo("+data["data"+i].detailid+");\">点击查看</a>");
	    		}
	    		htmlsb.push("</td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td class=\"dataTitle\">拍照时间：</td>");
	    		htmlsb.push("<td class=\"dataTd\">"+data["data"+i].ptime+"</td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("<tr>");
	    		htmlsb.push("<td class=\"dataTitle\">备注：</td>");
	    		htmlsb.push("<td class=\"dataTd\"><div class=\"dataCont\">"+data["data"+i].detailremark+"</div></td>");
	    		htmlsb.push("</tr>");
	    		htmlsb.push("</table>");
	    		htmlsb.push("<div style=\"clear:both;\"></div>")
	    		htmlsb.push("</div>");
	    		htmlsb.push("</div>");
    		}
    		return htmlsb.join("");
    	}
    	
    </script>
    
    <script type="text/javascript">
    	function regeditDataGridEvent(){

			$(".recordDetailDataDiv").hover(
				function(){
					$(this).children(".recordDetailDataInnerDiv").removeClass("dataDetailOver").addClass("dataDetailHover");
				},
				function(){
					$(this).children(".recordDetailDataInnerDiv").removeClass("dataDetailHover").addClass("dataDetailOver");
				}
			);
			$(".recordDetailDataDiv").bind("click",function(event){
				var checkbox=$(this).find(":checkbox[checkfor='detailid']");
				if(checkbox.is(":checked")){
					checkbox.prop("checked",false);
				}else{
					checkbox.prop("checked",true);
				}
			});
			$(".recordDetailDataDiv a").bind("click",function(event){
				event.stopPropagation();
			});
			$(".recordDetailDataDiv input[type='checkbox']").bind("click",function(event){
				event.stopPropagation();
			});
    	}
    	
    	function getRecordDetailCheckData(){
    		var idOkArr=new Array();
    		var idNotOkArr=new Array();
    		var canOkArr=new Array();
    		var canNotArr=new Array();
    		var icount=0;
    		$(":checkbox[checkfor='detailid']:checked").each(function(){
    			var id=$(this).val() || "";
    			id=$.trim(id);
    			if(id==""){
    				return true;
    			}
    			
    			var isqa=$(this).attr("isqa") || "";
    			if(isqa=='1'){
    				idOkArr.push(id);
    			}if(isqa=='2'){
    				idNotOkArr.push(id);    				
    			}
    			if(isqa!='1'){
    				canNotArr.push(id);
    			}
    			if(isqa!='2'){
    				canOkArr.push(id);
    			}
    			icount=icount+1;
    		});
    		var resultObj={
    				idOkArr:idOkArr,
    				idNotOkArr:idNotOkArr,
    				canOkArr:canOkArr,
    				canNotArr:canNotArr,
    				total:icount
    		};
    		return resultObj;
    	}
    	
    	function showDetailImg(detailid){


    		$('<div id="crmVisitRecordOrderDetailList-dialog-operate-Detail-content"></div>').appendTo("#crmVisitRecordOrderDetailList-dialog-operate-Detail");
			var $DetailOper=$("#crmVisitRecordOrderDetailList-dialog-operate-Detail-content");
			$DetailOper.dialog({   
			    title: '拜访记录明细图片查看',  
			    //width: 680,  
			    //height: 300,
			     fit:true,  
			    closed: true,  
			    cache: false,  
			    href: 'crm/visitrecord/crmVisitRecordDetailInfoPage.do?detailid='+ detailid,
			    maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){					    	
	   			},
			    onClose:function(){
			    	$DetailOper.dialog("destroy");
			    }
			});
			$DetailOper.dialog('open');
    	}


    	function showMapInfo(detailid){
    		//top.addOrUpdateTab('crm/visitrecord/crmVisitRecordDetailMapPage.do?detailid='+ detailid, "拜访记录地图商品陈列查看");
    		
    		$('<div id="crmVisitRecordOrderDetailList-dialog-operate-Map-content"></div>').appendTo("#crmVisitRecordOrderDetailList-dialog-operate-Map");
			var $DetailOper=$("#crmVisitRecordOrderDetailList-dialog-operate-Map-content");
			 
			$DetailOper.html('<iframe width="100%" height="100%" src="crm/visitrecord/crmVisitRecordDetailMapPage.do?detailid='+ detailid + '&zoom=1&out=1" frameborder="0"></iframe>');
    		
    		
			$DetailOper.dialog({  
			    title: '拜访记录地图商品陈列查看',  
			    //width: 680,  
			    //height: 300,
			    fit:true,  
			    closed: true,  
			    cache: false,  
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){		
			    	visitRecordMapWindowShow();
	   			},
			    onClose:function(){
			    	$DetailOper.dialog("destroy");
			    }
			});
			$DetailOper.dialog('open');
		    
    	}
    	function isMapGps(gps){
    		if(gps==null || gps==""){
    			return false;
    		}
    		var gpsArr=[];

			gpsArr=gps.split(",");
    		if(gpsArr.length==2){
    			if(gpsArr[0]==null || gpsArr[0]=="" || gpsArr[1]==null || gpsArr[1]==""){
    				return false;
    			}
    		}else{
    			return false;
    		}
    		return true;
    	}
    	

    	function refreshCrmVisitRecordListDataGrid(){
    		try{			
    			tabsWindowURL("/crm/visitrecord/crmVisitRecordListPage.do").$("#crm-table-crmVisitRecordListPage").datagrid('reload');
    		}catch(e){
    		}
    	}
    	
	</script>
  </body>
</html>
