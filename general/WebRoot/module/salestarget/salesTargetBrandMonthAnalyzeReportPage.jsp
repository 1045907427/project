<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按品牌月度目标分析</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
	  <style>
		  table.map-detail-table {
			  border-collapse: collapse;
			  border: 1px solid #000;
			  width: 100%;
		  }
		  table.map-detail-table th, table.map-detail-table td {
			  border: 1px solid #000;
			  line-height: 28px;
			  text-indent: 5px;
		  }
	  </style>
  </head>  
  <body>
  	<table id="report-datagrid-brandMonthAnalyzeReport"></table>
  	<div id="report-toolbar-brandMonthAnalyzeReport" style="padding: 0px">
		<div class="buttonBG" id="report-toolbar-brandMonthAnalyzeReport-buttons">
        </div>
	  	<form action="" method="post" id="report-form-brandMonthAnalyzeReport">
			<table class="querytable">
				<tr>
    				<td style="width: 60px;text-align: right;">月份:</td>
    				<td style="width:160px;">
						<select id="salestarget-brandMonthAnalyzeReport-query-month" name="month" style="width:150px;">
							<option value="">不选</option>
							<c:forEach var="month" begin="1" end="${iMonth}">
								<c:choose>
									<c:when test="${month == iMonth}">
										<option value="${month}" selected="selected">${month }月</option>
									</c:when>
									<c:otherwise>
										<option value="${month }">${month }月</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td style="text-align: right;">对比年份:</td>
					<td style="width:160px;">
						<input id="salestarget-brandMonthAnalyzeReport-query-year" type="text" name="year" style="width:150px;cursor: pointer" class="Wdate" value="${lastyear }" readonly="readonly"/>
					</td>
    				<td style="text-align: right;"><span title="业务日期">更新日期</span>:</td>
    				<td><input type="text" id="salestarget-brandMonthAnalyzeReport-query-businessdate" name="businessdate" value="${today }" style="width:150px;cursor: pointer" class="Wdate" readonly="readonly" /></td>
    			</tr>
    			<tr><td style="text-align: right;">渠道:</td>
					<td><input id="salestarget-brandMonthAnalyzeReport-widget-customersort" name="customersort" type="text" style="width: 150px;"/></td>
					<td style="text-align: right;">品牌:</td>
					<td>
						<input id="salestarget-brandMonthAnalyzeReport-widget-brandid" type="text" style="width: 150px;"/>
					</td>
	  				<td style="text-align: right;">客户业务员:</td>
	  				<td><input id="salestarget-brandMonthAnalyzeReport-widget-salesuserid" name="salesuserid" type="text" style="width: 150px;"/></td>
				</tr>
				<tr>
	  				<td style="text-align: right;">时间进度:</td>
	  				<td>
						<input id="salestarget-brandMonthAnalyzeReport-timeschedule" name="timeschedule" value="${timeSchedule}" type="text" style="width: 150px;" class="readonly" readonly="readonly"/>
					</td>
					<td style="text-align: right;">品牌列:</td>
					<td>
						<select id="salestarget-brandMonthAnalyzeReport-brandcol" style="width:120px">
							<option value="1">基于品牌档案</option>
							<option value="2">基于销售报表</option>
							<option value="3">基于销售目标</option>
							<option value="4">自选品牌列</option>
						</select>
						<a href="javaScript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" title="帮助" onclick="javascript:showBrandColHelpDialog();"> </a>
					</td>
                    <td colspan="2" style="text-align: center;">
						<a href="javaScript:void(0);" id="report-query-brandMonthAnalyzeReport" class="button-qr">查询</a>
	 					<a href="javaScript:void(0);" id="report-reload-brandMonthAnalyzeReport" class="button-qr">重置</a>

					</td>
				</tr>
			</table>
			<input id="salestarget-brandMonthAnalyzeReport-form-hidden-brandid" name="brandid" type="hidden"/>
		</form>
	</div>
	<div style="display:none">
		<security:authorize url="/module/salestargetreport/exportSalesTargetBrandMonthAnalyzeReportDataBtn.do">
			<a href="javaScript:void(0);" id="report-export-brandMonthAnalyzeReport" style="display: none">导出</a>
		</security:authorize>

		<div id="salestarget-brandMonthAnalyzeReport-dialog-clearcache">
			<table class="map-detail-table">
				<tr style="background-color: #dedede">
					<th style="width:60px;">编号</th>
					<th>名称</th>
					<th style="width:100px;">操作</th>
				</tr>
				<tr>
					<td>1：</td>
					<td>
					</td>
					<td>
					</td>
				</tr>
			</table>
		</div>
		<div id="salestarget-brandMonthAnalyzeReport-dialog-brandcol-help"></div>
	</div>
	<script type="text/javascript">
		var exportTitle="按品牌月度目标分解报表";
		var initQueryJSON = $("#report-datagrid-brandMonthAnalyzeReport").serializeJSON();
		var tableColumnListJson ;
		var detailDlgOpen=false;
		var report_lastyear="${lastyear}";
		function getGridRowStyle(subject){
			if(subject==null || $.trim(subject)==""){
				return "";
			}
			subject= $.trim(subject);
			var subject_Color1=["JNDYXSMB","JNDEXSMB","JNDYMLMB","JNDEMLMB"];
			var subject_Color2=["JNXS","DCL_DYXSMB","DCL_DEXSMB","JNML","DCL_DYMLMB","DCL_DEMLMB"];
			var subject_Color3=["JDCE_DYXSMB","JDCE_DYMLMB"];
			var subject_Color4=["CYE_DYXSMB","CYE_DEXSMB","CYE_DYMLMB","CYE_DEMLMB"];
			if($.inArray(subject, subject_Color1)>=0){
				return "background-color:#CEFFCE;";
			}else if($.inArray(subject, subject_Color2)>=0){
				return "background-color:#FFF4C1;";
			}else if($.inArray(subject, subject_Color3)>=0){
				return "background-color:#FFD9EC";
			}else if($.inArray(subject, subject_Color4)>=0){
				return "background-color:#FFB5B5";
			}
			return "";
		}

		$(function(){
			
			$("#report-datagrid-brandMonthAnalyzeReport").datagrid({
				frozenColumns:[[
					{field:'subjectsort',title:'分类',sortable:true,align:'center',isShow:true,
						styler: function(value,row,index){
							return "border-width: 0 1px 1px 0;border-style: dotted;";
						}
					},
					{field:'subject',title:'科目编码',isShow:true,hidden:true,isShow:true},
					{field:'subjectname',title:'科目',isShow:true}
				]],
				columns:getDefaultColumn(true),
		 		method:'post',
		 		idField:'subject',
	  	 		title:'',
	  	 		//pageList:[10,20,30,50,100,200,300],
	  	 		fit:true,
	  	 		rownumbers:true,
	  	 		pagination:false,
	  	 		singleSelect:true,
	  	 		pageSize:100,
				toolbar:'#report-toolbar-brandMonthAnalyzeReport',
				//onBeforeLoad:function(){},
				loadMsg:'正在加载数据，请耐心等待！',
				onLoadSuccess:function(data){
					EUIUtils.mergeCellsByFieldByTimeout("report-datagrid-brandMonthAnalyzeReport",'subjectsort',200);
				},
				rowStyler: function(index,row){
					if (row.subject!=null && row.subject!=""){
						return getGridRowStyle(row.subject); // return inline style
					}
				}
			}).datagrid("columnMoving");

			$("#salestarget-brandMonthAnalyzeReport-widget-customersort").widget({ //分类
				referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
				width:150,
				singleSelect:true,
				onlyLeafCheck:false,
				view: true
			});
			$("#salestarget-brandMonthAnalyzeReport-widget-salesuserid").widget({
				referwid:'RL_T_BASE_PERSONNEL_SELLER',
				width:150,
				singleSelect:true,
				onlyLeafCheck:false
			});
			$("#salestarget-brandMonthAnalyzeReport-widget-brandid").widget({
				//referwid:'RL_T_BASE_GOODS_BRAND',
				name:'t_salestarget_input',
				col:'brandid',
				width:150,
				singleSelect:false,
				onlyLeafCheck:false
			});

			$("#report-query-brandMonthAnalyzeReport").click(function(){
				var month=$("#salestarget-brandMonthAnalyzeReport-query-month").val();
				var year=$("#salestarget-brandMonthAnalyzeReport-query-year").val();
				var businessdate=$("#salestarget-brandMonthAnalyzeReport-query-businessdate").val();
				if(null==businessdate || "" == businessdate){
					$.messager.alert("提醒","抱歉，请填写更新日期");
					return false;
				}
				if(null==year || "" == year){
					$.messager.alert("提醒","抱歉，请填写对比年份");
					return false;
				}
				var businessdateArr=businessdate.split('-');
				if(businessdateArr.length!=3){
					$.messager.alert("提醒","抱歉，请填写更新日期");
					return false;
				}
				var updateMonth=businessdateArr[1]*1;
				var customersort=$("#salestarget-brandMonthAnalyzeReport-widget-customersort").widget("getValue")||"";
				customersort= $.trim(customersort);
				var selectDataObject=$("#salestarget-brandMonthAnalyzeReport-widget-brandid").widget('getObjects');
				if(selectDataObject!=null&&selectDataObject.length>0){
					if(selectDataObject.length>150) {
						$.messager.alert("提醒", "抱歉，选择查询的品牌数量不能超150个");
						return false;
					}
				}


				var selectDataIds=$("#salestarget-brandMonthAnalyzeReport-widget-brandid").widget('getValue');
				calcMonthAnalyzeTimeSchedule(month,businessdate);
				var brandcol=$("#salestarget-brandMonthAnalyzeReport-brandcol").val()||"";
				var paramdata={};
				paramdata.customersort=customersort;
				paramdata.brandidarrs=selectDataIds;
				if(brandcol=="1"){
					if(selectDataObject!=null&&selectDataObject.length>0) {
						setDataGridColumn(selectDataObject);
					}else {
						showRemoteDataGridColList('basefiles/getBrandEnableList.do', paramdata,true);
					}
				}else if(brandcol=="2"){
					if($.trim(month)!=""){
						var imonth= $.trim(month)*1;
						if(imonth<=0){
							imonth=1;
						}
						if(imonth>=updateMonth){
							paramdata.businessdatestart = year+"-"+(updateMonth<10?'0'+updateMonth:updateMonth)+"-01";
							paramdata.businessdateend = year+"-"+(updateMonth<10?'0'+updateMonth:updateMonth)+"-31";

							paramdata.businessdatestart2 = businessdateArr[0]+"-"+(updateMonth<10?'0'+updateMonth:updateMonth)+"-01";
							paramdata.businessdateend2 = businessdate;
						}else{
							paramdata.businessdatestart = year+"-"+(imonth<10?'0'+imonth:imonth)+"-01";
							paramdata.businessdateend = year+"-"+(imonth<10?'0'+imonth:imonth)+"-31";

							paramdata.businessdatestart2 = businessdateArr[0]+"-"+(imonth<10?'0'+imonth:imonth)+"-01";
							paramdata.businessdateend2 = businessdateArr[0]+"-"+(imonth<10?'0'+imonth:imonth)+"-31";
						}
					}else{
						paramdata.businessdatestart = year+"-01-01";
						paramdata.businessdateend = year+"-12-31";
						paramdata.businessdatestart2 = businessdateArr[0]+"-01-01";
						paramdata.businessdateend2 = businessdate;
					}
					showRemoteDataGridColList('module/salestargetinput/getBrandListInSalesReportCache.do',paramdata,true);
				} else if(brandcol=="3"){
					if($.trim(month)!=""){
						var imonth= $.trim(month)*1;
						if(imonth<=0){
							imonth=1;
						}
						if(imonth>=updateMonth){
							paramdata.yearmonthstart = businessdateArr[0]+"-"+businessdateArr[1];
							paramdata.yearmonthend = businessdateArr[0]+"-"+businessdateArr[1];
						}else{
							paramdata.yearmonthstart = businessdateArr[0]+"-"+(imonth<10?'0'+imonth:imonth);
							paramdata.yearmonthend = businessdateArr[0]+"-"+(imonth<10?'0'+imonth:imonth);
						}
					}else{
						paramdata.yearmonthstart=businessdateArr[0]+"-01";
						paramdata.yearmonthend= businessdateArr[0]+"-"+businessdateArr[1];
					}
					showRemoteDataGridColList('module/salestargetinput/getSalesTargetInputBrandGroupList.do',paramdata,true);
				}else{
					setDataGridColumn(selectDataObject);
				}

			});
			//重置
			$("#report-reload-brandMonthAnalyzeReport").click(function(){
				
				$("#report-form-brandMonthAnalyzeReport")[0].reset();
				
				$("#salestarget-brandMonthAnalyzeReport-widget-customersort").widget('clear');
				$("#salestarget-brandMonthAnalyzeReport-widget-brandid").widget('clear');
				$("#salestarget-brandMonthAnalyzeReport-widget-salesuserid").widget('clear');
				$("#salestarget-brandMonthAnalyzeReport-form-hidden-brandid").val("");
				var $table=$('#report-datagrid-brandMonthAnalyzeReport');
				var $obj=$("td[field='subjectsort']").parent();
				$obj.find("td[field^='target_']").remove().end();
				$table.datagrid('loadData', { total: 0, rows: [] });

			});

			$("#salestarget-brandMonthAnalyzeReport-query-year").off("click").on("click",function(event){
				WdatePicker({'dateFmt':'yyyy',maxDate:report_lastyear});
			});
			$("#salestarget-brandMonthAnalyzeReport-query-businessdate").off("click").on("click",function(event){
				WdatePicker({'dateFmt':'yyyy-MM-dd',
					maxDate:'${today }',
					onpicking:function(dp){
						pickedBusinessFunc(dp,"businessdate");
					}});
			});

			$("#report-toolbar-brandMonthAnalyzeReport-buttons").buttonWidget({
				initButton:[
					{}
				],
				buttons:[
					<security:authorize url="/module/salestargetreport/exportSalesTargetBrandMonthAnalyzeReportDataBtn.do">
					{
						id:'button-export-excel',
						name:'导出',
						iconCls:'button-export',
						handler: function(){
							var year=$("#salestarget-brandMonthAnalyzeReport-query-year").val();
							var businessdate=$("#salestarget-brandMonthAnalyzeReport-query-businessdate").val();
							if(null==businessdate || "" == businessdate){
								$.messager.alert("提醒","抱歉，请填写更新日期");
								return false;
							}
							if(null==year || "" == year){
								$.messager.alert("提醒","抱歉，请填写对比年份");
								return false;
							}

							exportTitle="按品牌月度目标分解报表 "+businessdate;
							$("#report-export-brandMonthAnalyzeReport").Excel('export',{
								queryForm: "#report-form-brandMonthAnalyzeReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
								type:'exportUserdefined',
								name:exportTitle,
								url:'module/salestargetreport/exportSalesTargetBrandMonthAnalyzeReportData.do'
							});
							$("#report-export-brandMonthAnalyzeReport").trigger("click");
						}
					},
					</security:authorize>
					<security:authorize url="/module/salestargetreport/clearSalesTargetBrandMonthAnalyzeReportCacheBtn.do">
					{
						id:'button-clearcache',
						name:'清除缓存',
						iconCls:'button-delete',
						handler: function(){
							$.messager.confirm('确认','是否清除基于销售报表的品牌列查询缓存?',function(r){
								try {
									$.ajax({
										url: "module/salestargetinput/clearBrandCustomerSortListInSalesReportCache.do",
										data: {opertype:'1'},
										type: 'POST',
										dataType: 'json',
										beforeSend:function(XHR){
											loading("清除品牌列查询缓存中...");
										},
										success: function (json) {
											loaded();
											if(json.flag==true){
												$.messager.alert("提醒","缓存清理成功");
												return false;
											}
										},
										error:function(){
											loaded();
										}
									});
								}catch(ex){
									loaded();
								}
							});
						}
					},
					</security:authorize>
					{}
				],
				model: 'bill',
				type: 'list',
				tname: 't_salestarget_report_brandanalyze'
			});
		});
		function setDataGridColumn(dataList){
			var newColumn=getDefaultColumn();
			tableColumnListJson=newColumn;
			var $brandid=$("#salestarget-brandMonthAnalyzeReport-form-hidden-brandid");
			$brandid.val("");
			var brandIdArr=new Array();
			if(dataList!=null && (dataList instanceof Array) && dataList.length>0){
				for(var i=0 ;i<dataList.length;i++){
					if(dataList[i].name=="" || dataList[i].id==""){
						continue;
					}
					brandIdArr.push(dataList[i].id);
					newColumn[0].push({field:'target_'+dataList[i].id,title:dataList[i].name,isShow:true,align:'right',width:130});
				}
				if(brandIdArr.length>0) {
					$brandid.val(brandIdArr.join(","));
				}
			}
			newColumn[0].push({field:'summarycolumn',title:'合计',isShow:true,align:'center'});
			newColumn[0].push({field:'realdiffcolumn',title:'真实差异',isShow:true,align:'center'});
			var queryJSON = $("#report-form-brandMonthAnalyzeReport").serializeJSON();
			$("#report-datagrid-brandMonthAnalyzeReport").datagrid({
				columns:newColumn,
				url: 'module/salestargetreport/showSalesTargetBrandMonthAnalyzeReportData.do',
      			pageNumber:1,
				queryParams:queryJSON});
		}
		
  		function getDefaultColumn(init){
			if(init!=null && init!=true){
				init=false;
			}
  	  		var column=[[
				]];
			if(init){
				column[0].push({field:'summarycolumn',title:'合计',isShow:true,align:'center'});
				column[0].push({field:'realdiffcolumn',title:'真实差异',isShow:true,align:'center'});
			}
	         return column;
  		}
		function pickedBusinessFunc(dp){
			if(dp==null || dp.el==null || dp.el.id!="salestarget-brandMonthAnalyzeReport-query-businessdate"){
				return false;
			}
			var year=dp.cal.getP('y');
			var month=dp.cal.getP('M');
			report_lastyear=year-1;
			initTimeSelectFunc(year-1,month);
			return true;
		}

		function initTimeSelectFunc(reflastyear,refmonth){
			var month="${iMonth}" || 0;
			if(refmonth!=null && !isNaN(refmonth)){
				month=refmonth;
			}
			var lastyear="${lastyear}" || 0;
			if(reflastyear!=null && !isNaN(reflastyear)){
				lastyear=reflastyear;
			}
			$("#salestarget-brandMonthAnalyzeReport-query-year").val(lastyear);

			$("#salestarget-brandMonthAnalyzeReport-query-month").html("");
			var htmlsb=new Array();
			htmlsb.push("<option value=\"\">不选</option>");
			for(var i=1;i<=month;i++){
				htmlsb.push("<option value=\"");
				htmlsb.push(i);
				htmlsb.push("\"");
				if(i==month) {
					htmlsb.push("  selected=\"selected\"");
				}
				htmlsb.push(">");
				htmlsb.push(i);
				htmlsb.push("月</option>");
			}
			$("#salestarget-brandMonthAnalyzeReport-query-month").html(htmlsb.join(""));
		}
		function calcMonthAnalyzeTimeSchedule(month,businessdate){
			if(null==businessdate || "" == businessdate){
				$.messager.alert("提醒","抱歉，请填写更新日期");
				return false;
			}
			$("#salestarget-brandMonthAnalyzeReport-timeschedule").addClass("inputload");
			$("#salestarget-brandMonthAnalyzeReport-timeschedule").val("计算中...");
			$.ajax({
				url :'module/salestargetreport/getCalcMonthAnalyzeTimeSchedule.do',
				type:'post',
				dataType:'json',
				//async: false,
				cache:false,
				data:{month:month,businessdate:businessdate},
				success:function(json){
					$("#salestarget-brandMonthAnalyzeReport-timeschedule").removeClass("inputload");
					$("#salestarget-brandMonthAnalyzeReport-timeschedule").val("");
					if(json.flag==true){
						var result=json.result||0;
						$("#salestarget-brandMonthAnalyzeReport-timeschedule").val(result+"%");
					}
				},
				error:function(){
					$("#salestarget-brandMonthAnalyzeReport-timeschedule").removeClass("inputload");
				}
			});
			var val=$("#salestarget-brandMonthAnalyzeReport-timeschedule").val() ||"";
			if(val.indexOf("计算中")>=0){
				$("#salestarget-brandMonthAnalyzeReport-timeschedule").val("");
			}
		}
		function showRemoteDataGridColList(url,data,showloading){
			if(data==null){
				return false;
			}
			if(url==null || $.trim(url)==''){
				return false;
			}
			url=$.trim(url);
			if(showloading==null || showloading==""){
				showloading=false;
			}
			try {
				$.ajax({
					url: url,
					data: data,
					type: 'POST',
					dataType: 'json',
					beforeSend:function(XHR){
						if(showloading){
							loading("加载品牌列中...");
						}
					},
					success: function (json) {
						if(showloading){
							loaded();
							setTimeout(function(){
								setDataGridColumn(json);
							},300);
						}else{
							setDataGridColumn(json);
						}
					},
					error:function(){
						if(showloading){
							loaded();
						}
						setDataGridColumn({});
					}
				});
			}catch(ex){
				setDataGridColumn({});
			}
		}
		function showClearCacheDialog(){
			$('#salestarget-brandMonthAnalyzeReport-dialog-clearcache').dialog({
				title: "清理缓存",
				//fit:true,
				width:460,
				height:300,
				closed: true,
				cache: false,
				maximizable:true,
				resizable:true,
				modal: true,
				onLoad:function(){
				}
			});
			$('#salestarget-brandMonthAnalyzeReport-dialog-clearcache').dialog('open');
		}
		function showBrandColHelpDialog(){
			var iframecontent= $('<iframe></iframe>').attr('height', '100%').attr('width', '100%').attr('marginheight', '0').attr('marginwidth', '0').attr('frameborder','0');
			setTimeout(function(){
				iframecontent.attr('src', "module/salestargetreport/showBrandMonthAnalyzeReportColSelectHelp.do");
			}, 1);
			$('#salestarget-brandMonthAnalyzeReport-dialog-brandcol-help').dialog({
				title: "品牌列帮助说明",
				fit:true,
				//width:460,
				//height:300,
				closed: true,
				cache: false,
				maximizable:true,
				resizable:true,
				content : iframecontent,
				modal: true
			});
			$('#salestarget-brandMonthAnalyzeReport-dialog-brandcol-help').dialog('open');

		}
	</script>
  </body>
</html>