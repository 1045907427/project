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
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="crm-buttons-crmVisitRecordListPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="crm-table-crmVisitRecordListPage"></table>
    		<div id="crm-table-query-crmVisitRecordListPage" style="padding:2px;height:auto">
				<div>
					<form action="" id="crm-form-crmVisitRecordListPage" method="post">
                        <input type="hidden" name="deptid" id="crm-deptid-crmVisitRecordListPage" value="${param.deptid }"/>
						<table class="querytable">
			    			<tr>
			    				<td>业务日期:</td>
			    				<td><input type="text" id="crm-crmVisitRecordListPage-businessdatestart" name="businessdatestart" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${param.startdate }"/> 到 <input type="text" id="crm-crmVisitRecordListPage-businessdateend" name="businessdateend" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${param.enddate }"/></td>
			    				<td>客户名称:</td>
			    				<td><input type="text" id="crm-crmVisitRecordListPage-customerid" name="customerid" style="width: 180px;"/></td>
			    				<td>检查状态:</td>
			    				<td>
			    					<select id="crm-crmVisitRecordListPage-Close" name="isClose" style="width:150px;">
			    						<option value="" <c:if test="${param.from eq 'report'}">selected="selected"</c:if>></option>
			    						<option value="2" <c:if test="${param.from ne 'report'}">selected="selected"</c:if>>未完成</option>
			    						<option value="4">完成</option>
			    					</select>
			    				</td>
			    			</tr>
			    			<tr>
			    				<td>业务员名称:</td>
			    				<td><input type="text" id="crm-crmVisitRecordListPage-personid" name="personid" style="width: 225px;" value="${param.personid }"/></td>
			    				<td>主管名称:</td>
			    				<td><input type="text" id="crm-crmVisitRecordListPage-leadid" name="leadid" style="width: 180px;"/></td>
                                <td>线路内:</td>
                                <td>
                                    <select id="crm-crmVisitRecordListPage-isplan" name="isplan" style="width:150px;">
                                        <option value="" <c:if test="${empty param.isplan}">selected="selected"</c:if>></option>
                                        <option value="1"<c:if test="${param.isplan eq '1'}">selected="selected"</c:if>>是</option>
                                        <option value="0"<c:if test="${param.isplan eq '0'}">selected="selected"</c:if>>否</option>
                                    </select>
                                </td>
			    			</tr>
			    			<tr>
                                <td>编号:</td>
                                <td><input type="text" name="id" style="width: 225px;"/>
                                </td>
			    				<td>客户分类:</td>
			    				<td><input type="text" id="crm-crmVisitRecordListPage-customersort" name="customersort" style="width: 180px;" value="${param.customersort }"/></td>
			    				<td>销售区域:</td>
			    				<td><input type="text" id="crm-crmVisitRecordListPage-salesarea" name="salesarea" style="width: 150px;" value="${param.salesarea }"/></td>
                            </tr>
                            <tr>
			    				<td>销售部门:</td>
			    				<td><input type="text" id="crm-crmVisitRecordListPage-salesdept" name="salesdept" style="width: 225px;"/>
			    				</td>
			    				<td>备　　注:</td>
			    				<td><input type="text" name="remark" style="width:180px;"/></td>
			    				<td colspan="2">
			    					<a href="javaScript:void(0);" id="crm-btn-queryCrmVisitRecordListPage" class="button-qr">查询</a>
									<a href="javaScript:void(0);" id="crm-btn-reloadCrmVisitRecordListPage" class="button-qr">重置</a>
			    				</td>
			    			</tr>
			    		</table>
                        <div id="dialog-autoexport"></div>
					</form>
				</div>
			</div>

		  <div id="crm-crmVisitRecordListPage-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;padding:10px 0;">
		        <div id="crm-crmVisitRecordListPage-itemView" iconCls="button-view">查看</div> 
		  </div>
			<div style="display:none">
		    	<div id="crmVisitRecordList-dialog-operate"></div>
		    	<div id="crmVisitRecordList-dialog-operate-Detail"></div>
		    	<div id="crmVisitRecordList-dialog-operate-Map"></div>
	    	</div>
    	</div>
    </div>
    <style type="text/css">
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
       	
		var SR_footerobject  = null;
    	$(document).ready(function(){
    		var initQueryJSON = $("#crm-form-crmVisitRecordListPage").serializeJSON();
    		
    		var crmVisitRecordListJson = $("#crm-table-crmVisitRecordListPage").createGridColumnLoad({
				name:'crm_visitrecord',
				frozenCol : [[
								{field:'idok',checkbox:true,isShow:true}
    			]],
    			commonCol :[[
 								{field:'id',title:'编号',width:135,sortable:true,
 									formatter: function(value,row,index){
 										return "<a href=\"javascript:void(0);\" onclick=\"showRecordInfo('"+value+"');\">"+value+"</a>";
 									}
 								},
 								{field:'gps',title:'门店地图',width:60,sortable:true,
 									formatter: function(value,row,index){
 										if(isMapGps(value) && row.id!=""){
 											return "<a href=\"javascript:void(0);\" onclick=\"showMapInfo('"+row.id+"');\">查看地图</a>";
 										}
 										return "";
 									}
 								},
    							{field:'businessdate',title:'业务日期',width:70,sortable:true},
    							{field:'weekday',title:'星期',width:50,
    								formatter: function(value,row,index){
										if(value == "1"){
											return "星期一";
										}else if(value == "2"){
											return "星期二";
										}else if(value == "3"){
											return "星期三";
										}else if(value == "4"){
											return "星期四";
										}else if(value == "5"){
											return "星期五";
										}else if(value == "6"){
											return "星期六";
										}else if(value == "7"){
											return "星期日";
										}else{
											return "";
										}
									}
    							},
    							{field:'personid',title:'业务员名称',width:80,
    								formatter: function(value,row,index){
										return row.personname;
									}
    							},
    							{field:'leadid',title:'主管名称',width:80,
    								formatter: function(value,row,index){
										return row.leadname;
									}
    							},
    							{field:'customerid',title:'客户编号',width:70},
    							{field:'customername',title:'客户名称',width:100},		
    							{field:'pcustomerid',title:'上级客户编号',width:80,hidden:true},
    							{field:'pcustomername',title:'上级客户',width:80},		
    							{field:'customersort',title:'客户分类',width:80,
    								formatter: function(value,row,index){
										return row.customersortname;
									}
    							},		
    							{field:'salesarea',title:'销售区域',width:80,
    								formatter: function(value,row,index){
										return row.salesareaname;
									}
    							},		
    							{field:'salesdept',title:'销售部门',width:80,
    								formatter: function(value,row,index){
										return row.salesdeptname;
									}
    							},
                    			{field:'phototime',title:'拍照时间',width:125,sortable:true},
    							{field:'addusername',title:'制单人',width:80,hidden:true},
    							{field:'adddeptname',title:'制单人部门',width:100,hidden:true},
    							{field:'addtime',title:'制单时间',width:125,sortable:true},
    							{field:'modifyusername',title:'修改人',width:120,hidden:true},
    							{field:'modifytime',title:'修改时间',width:120,sortable:true,hidden:true},
    							{field:'auditusername',title:'审核人',width:100,hidden:true},
    							{field:'audittime',title:'审核时间',width:100,hidden:true},
    							{field:'status',title:'检查状态',width:60,
    								formatter: function(value,row,index){
										if(value=='2' || value=='3'){
											return '未完成';
										}else if(value=='4'){
											return '完成';
										}else{
											return '未完成'
										}
									}
								},
                                {field:'isplan',title:'线路内/外',width:70,
                                    formatter: function(value,row,index){
                                        if (value == '1') {
                                            return '线路内';
                                        } else if (value == '0') {
                                            return '线路外';
                                        }
                                        return '';
                                    }
                                },
                                {field:'remark',title:'备注',width:100}
    			]]
			});
			$("#crm-table-crmVisitRecordListPage").datagrid({
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
                pageSize:100,
				toolbar:'#crm-table-query-crmVisitRecordListPage',
		 		url:"crm/visitrecord/showCrmVisitRecordPageList.do",
				queryParams:initQueryJSON,
				authority : crmVisitRecordListJson,
		 		frozenColumns: crmVisitRecordListJson.frozen,
				columns:crmVisitRecordListJson.common,
	  	 		onLoadSuccess:function(){
		 		},
				onDblClickRow:function(index, data){
					var requrl="";
					if(data.id==null || data.id==""){
						return false;
					}
					requrl="?billid="+data.id;
					$('<div id="crmVisitRecordList-dialog-operate-content"></div>').appendTo("#crmVisitRecordList-dialog-operate");
					var $DetailOper=$("#crmVisitRecordList-dialog-operate-content");
					$DetailOper.dialog({
					    title: '单据编号：'+data.id+' 客户拜访记录明细查看',  
					    //width: 680,  
					    //height: 300,
					    fit:true,  
					    closed: true,  
					    cache: false,  
					    href: 'crm/visitrecord/crmVisitRecordDetailListPage.do'+requrl,
						maximizable:true,
						resizable:true,  
					    modal: true, 
					    onLoad:function(){
					    	createDetailListDataGrid();
			   			},
					    onClose:function(){
					    	$DetailOper.dialog("destroy");
					    }
					});
					$DetailOper.dialog('open');
		    	},
	  	 		onRowContextMenu:function(e, rowIndex, rowData){
	  	 			e.preventDefault();
	  	 			var $contextMenu=$('#crm-crmVisitRecordListPage-Button-tableMenu');
	  	 			$contextMenu.menu('show', {
						left : e.pageX,
						top : e.pageY
					});
	  	 			$(this).datagrid('selectRow', rowIndex);
	                $contextMenu.menu('enableItem', '#crm-crmVisitRecordListPage-tableMenu-itemView');
	  	 		}
			}).datagrid("columnMoving");

			//按钮
			$("#crm-buttons-crmVisitRecordListPage").buttonWidget({
				initButton:[
					{},
					<%--<security:authorize url="/crm/visitrecord/crmVisitRecordViewBtn.do">--%>
					<%--{--%>
						<%--type:'button-view',--%>
						<%--handler: function(){--%>
							<%--var datarow = $("#crm-table-crmVisitRecordListPage").datagrid('getSelected');--%>
							<%--if(datarow==null ||  datarow.id ==null){--%>
			  		        	<%--$.messager.alert("提醒","请选择要查看的客户拜访记录");--%>
								<%--return false;--%>
							<%--}--%>
							<%--top.addOrUpdateTab('crm/visitrecord/crmVisitRecordPage.do?type=edit&id='+datarow.id,'客户拜访记录查看');--%>
						<%--}--%>
					<%--},--%>
					<%--</security:authorize>--%>
                    <security:authorize url="/crm/visitrecord/crmVisitRecordExportBtn.do">
                    {
                        type: 'button-export',
                        attr: {
                            queryForm: "#crm-form-crmVisitRecordListPage",
                            datagrid:'#crm-table-crmVisitRecordListPage',
                            type:'exportUserdefined',
                            name:'客户拜访记录表',
                            url:'crm/visitrecord/exportCrmVisitRecordPageList.do'
                        }

                    },
                    </security:authorize>
					{}
				],
				model:'bill',
				type:'list',
				datagrid:'crm-table-crmVisitRecordListPage',
				tname:'t_crm_visitrecord'
			});

    		
 			$("#crm-table-query-crmVisitRecordListPage-advanced").advancedQuery({
		 		name:'crm_visitrecord',
		 		plain:true,
		 		datagrid:'crm-table-crmVisitRecordListPage'
			});
			
			//回车事件
			controlQueryAndResetByKey("crm-btn-queryCrmVisitRecordListPage","crm-btn-reloadCrmVisitRecordListPage");
			
			$("#crm-btn-queryCrmVisitRecordListPage").click(function(){
				//查询参数直接添加在url中         
       			var queryJSON = $("#crm-form-crmVisitRecordListPage").serializeJSON();					
 				$('#crm-table-crmVisitRecordListPage').datagrid('load',queryJSON);				
			});
			$("#crm-btn-reloadCrmVisitRecordListPage").click(function(){
				$("#crm-form-crmVisitRecordListPage")[0].reset();
				
				$("#crm-crmVisitRecordListPage-customerid").customerWidget("clear");
				$("#crm-crmVisitRecordListPage-personid").widget("clear");
				$("#crm-crmVisitRecordListPage-leadid").widget("clear");
				$("#crm-crmVisitRecordListPage-customersort").widget("clear");
				$("#crm-crmVisitRecordListPage-salesdept").widget("clear");
				$("#crm-crmVisitRecordListPage-salesarea").widget("clear");
                $('#crm-deptid-crmVisitRecordListPage').val('');
				
       			var queryJSON = $("#crm-form-crmVisitRecordListPage").serializeJSON();					
 				$('#crm-table-crmVisitRecordListPage').datagrid('load',queryJSON);		
			});
			
			$("#crm-crmVisitRecordListPage-customerid").customerWidget({ //客户参照窗口
    			name:'crm_visit_record',
				col:'customerid',
    			singleSelect:true,
    			isdatasql:false,
    			width:185,
    			onlyLeafCheck:false
    		});
			$("#crm-crmVisitRecordListPage-personid").widget({
				name:'crm_visit_record',
				col:'personid',
	    		width:225,
				onlyLeafCheck:false,
				singleSelect:true
			});
			$("#crm-crmVisitRecordListPage-leadid").widget({
				name:'crm_visit_record',
				col:'leadid',
	    		width:180,
				onlyLeafCheck:false,
				singleSelect:true
			});

			$("#crm-crmVisitRecordListPage-customersort").widget({
				name:'crm_visit_record',
				col:'customersort',
	    		width:180,
				onlyLeafCheck:false,
				singleSelect:true
			});
			$("#crm-crmVisitRecordListPage-salesdept").widget({
				name:'crm_visit_record',
				col:'salesdept',
	    		width:225,
				onlyLeafCheck:false,
				singleSelect:true
			});
			$("#crm-crmVisitRecordListPage-salesarea").widget({
				name:'crm_visit_record',
				col:'salesarea',
	    		width:150,
				onlyLeafCheck:false,
				singleSelect:true
			});
			$("#crm-crmVisitRecordListPage-itemView").unbind("click").bind("click",function(){
				showRecordInfoByGrid();
			});
    	});
    	function showRecordInfo(id,evt){
    		$('<div id="crmVisitRecordList-dialog-operate-Detail-content"></div>').appendTo("#crmVisitRecordList-dialog-operate-Detail");
			var $DetailOper=$("#crmVisitRecordList-dialog-operate-Detail-content");
			$DetailOper.dialog({
			    title: '拜访记录信息查看',  
			    //width: 680,  
			    //height: 300,
			    fit:true,  
			    closed: true,  
			    cache: false,  
			    href: 'crm/visitrecord/crmVisitRecordInfoPage.do?id='+ id,
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
    	function showRecordInfoByGrid(){
			var data=$("#crm-table-crmVisitRecordListPage").datagrid('getSelected');
			if(null!=data && data.id!=""){
				showRecordInfo(data.id);
			}
    	}
    	function showMapInfo(id){
    		$('<div id="crmVisitRecordList-dialog-operate-Map-content"></div>').appendTo('#crmVisitRecordList-dialog-operate-Map');
    		var $DetailOper=$("#crmVisitRecordList-dialog-operate-Map-content");
    		var content='<iframe width="100%" height="100%" src="crm/visitrecord/crmVisitRecordMapPage.do?id='+ id + '&zoom=1&out=1" frameborder="0"></iframe>';
    		$DetailOper.dialog({  
			    title: '拜访记录门店地图查看',  
			    //width: 680,  
			    //height: 300,
			    fit:true,  
			    closed: true,  
			    cache: false,  
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    content:content,
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
    </script>
  </body>
</html>
