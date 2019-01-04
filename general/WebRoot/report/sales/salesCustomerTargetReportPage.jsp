<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户目标考核报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>  
  <body>
  	<table id="report-datagrid-salesCustomerTargetReport"></table>
  	<div id="report-toolbar-salesCustomerTargetReport" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/sales/exportSalesCustomerTargetReportData.do">
                <a href="javaScript:void(0);" id="report-export-salesCustomerTargetReportBtn" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-export'" title="导出">导出</a>
                <a href="javaScript:void(0);" id="report-export-salesCustomerTargetReport" style="display: none">导出</a>
            </security:authorize>
        </div>
	  	<form action="" method="post" id="report-form-salesCustomerTargetReport">
			<table class="querytable">

				<tr>
    				<td>本期日期:</td>
    				<td><input type="text" id="report-bqstartdate-salesCustomerTargetReport" name="bqstartdate" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${monthday }'})" /> 到 <input type="text" id="report-bqenddate-salesCustomerTargetReport" name="bqenddate" value="${monthday }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${monthday }'})" /></td>			
    				<td>前期日期:</td>
    				<td><input type="text" name="qqstartdate" value="${prevyearfirstday }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="qqenddate" value="${prevyearcurday }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>    				  				
    			</tr>
    			<tr>
	  				<td>品牌部门:</td>
	  				<td><input type="text" name="branddept" id="report-query-branddept"/></td>
	  				<td>品牌业务员:</td>
	  				<td><input type="text" name="salesuser" id="report-query-branduser"/></td>
				</tr>
				<tr>
	  				<td>客户:</td>
	  				<td><input type="text" name="customeridarr" id="report-query-customeridarr"/></td>    
					<td colspan="2"></td>
                    <td colspan="2">
						<a href="javaScript:void(0);" id="report-query-salesCustomerTargetReport" class="button-qr">查询</a>
	 					<a href="javaScript:void(0);" id="report-reload-salesCustomerTargetReport" class="button-qr">重置</a>

					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="display:none">
	    <div id="report-salesCustomerTargetReport-dialog" class="easyui-dialog" data-options="closed:true">
	    	<form action="" method="post" id="report-detailform-salesCustomerTargetReport">
	    		<div id="report-form-salesCustomerTargetReport-detail"></div>
	    	</form>
	    	<div id="report-salesCustomerTargetReport-dialog-toolbar">
	  			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="保存并修改下一条" name="savegoon" id="report-salesCustomerTargetReport-dialog-addSaveGoOn" />
	  			<input type="button" value="保存" name="savenogo" id="report-salesCustomerTargetReport-dialog-addSave" />
  			</div>
		</div>
	</div>
	<script type="text/javascript">
		var editIndex;
		var exportTitle="客户目标考核报表";
		var initQueryJSON = $("#report-datagrid-salesCustomerTargetReport").serializeJSON();
		var brandListJson;
		var tableColumnListJson ;
		var detailDlgOpen=false;
		$(function(){
			
			$("#report-datagrid-salesCustomerTargetReport").datagrid({ 
				columns:getDefaultColumn(),
		 		method:'post',
		 		idField:'branduser',
	  	 		title:'',
	  	 		//pageList:[10,20,30,50,100,200,300],
	  	 		fit:true,
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		singleSelect:true,
	  	 		pageSize:100,
				toolbar:'#report-toolbar-salesCustomerTargetReport',
				//onBeforeLoad:function(){},
				onDblClickRow: function(rowIndex, rowData){
					editIndex=rowIndex;

		    		var branddept=$("#report-query-branddept").widget('getValue');
					if(null == branddept || "" == branddept){
						$.messager.alert("提醒","抱歉，请选择品牌部门");
						return false;				
					}
					var bqstartdate=$("#report-bqstartdate-salesCustomerTargetReport").val();
					var bqenddate=$("#report-bqenddate-salesCustomerTargetReport").val();
					if(null==bqstartdate || "" == bqstartdate){
						$.messager.alert("提醒","抱歉，请填写本期时间");
						return false;		
					}
					if(null==bqenddate || "" == bqenddate){
						$.messager.alert("提醒","抱歉，请填写本期结束时间");
						return false;		
					}
					
					showTargetReportDetailDialog(rowData);
				}
			});
			$("#report-query-customeridarr").widget({
	  			//referwid:'RL_T_BASE_SALES_CUSTOMER',
				name:'t_report_sales_customertarget',
				col:'customerid',
	   			singleSelect:false,
	   			width:225,
	   			onlyLeafCheck:true
	  		});
	   		//品牌部门
	   		$("#report-query-branddept").widget({
	   			//referwid:'RL_T_BASE_DEPARTMENT_PBUYER',
				name:'t_report_sales_customertarget',
				col:'branddept',
	   			singleSelect:true,
	   			width:225,
	   			onlyLeafCheck:true
	   		});
	   		
			$("#report-query-branduser").widget({
	   			//referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
				name:'t_report_sales_customertarget',
				col:'branduser',
	   			singleSelect:true,
	   			width:225,
	   			onlyLeafCheck:true
	   		});
			$("#report-query-salesCustomerTargetReport").click(function(){
				var branddept=$("#report-query-branddept").widget('getValue');
				if(null == branddept || "" == branddept){
					$.messager.alert("提醒","抱歉，请选择品牌部门");
					return false;				
				}
				var bqstartdate=$("#report-bqstartdate-salesCustomerTargetReport").val();
				var bqenddate=$("#report-bqenddate-salesCustomerTargetReport").val();
				if(null==bqstartdate || "" == bqstartdate){
					$.messager.alert("提醒","抱歉，请填写本期时间");
					return false;		
				}
				if(null==bqenddate || "" == bqenddate){
					$.messager.alert("提醒","抱歉，请填写本期结束时间");
					return false;		
				}

				brandListJson={};
				$.ajax({   
		            url :'basefiles/getBrandListWithParentByDeptid.do',
		            data:{deptid : branddept},
		            type:'POST',
		            dataType:'json',
		            success:function(json){
		            	brandListJson=json;	    		
						setDataGridColumn(json);
		            }
		        });
				
			});
			//重置
			$("#report-reload-salesCustomerTargetReport").click(function(){
				
				$("#report-form-salesCustomerTargetReport")[0].reset();
				
				$("#report-query-branddept").widget('clear');
				$("#report-query-branduser").widget('clear');
				$("#report-query-customeridarr").widget('clear');
				var $table=$('#report-datagrid-salesCustomerTargetReport');
				$table.datagrid('loadData', { total: 0, rows: [] });
								
				$("td[field='branduser']").parent().find("td[colspan='5']").remove().end();
				var $obj=$("td[field='saleamount']").parent();
				$obj.find("td[field^='targets_'],td[field^='saleamount_'],td[field^='salerate_'],td[field^='qqsaleamount_'],td[field^='nweektargets_']").remove().end();
				//$obj.find("td[field^='saleamount_']").remove().end();
				//$obj.find("td[field^='salerate_']").remove().end();
				//$obj.find("td[field^='qqsaleamount_']").remove().end();
				//$obj.find("td[field^='nweektargets_']").remove().end();
			});
	  		//回车事件
			controlQueryAndResetByKey("report-query-salesCustomerTargetReport","report-reload-salesCustomerTargetReport");

			$(document).keydown(function(event){
				//console.log(event.keyCode);
				switch(event.keyCode){
					case 13:
						event.preventDefault();
						if(detailDlgOpen){
							if(event.target){
								var listen=$(event.target).attr("eventlisten");
								var index=$(event.target).attr("tabindex");
								if(isNaN(index)){
									index=0;
								}else{
									index=index*1;
								}
								index=index+1;
								if(listen=="enter"){
									$(event.target).blur();
									var next=$(":input:text[tabindex='"+index+"']");
									if(next.size()>0){
										next.focus().select();
									}
								}
							}
							if(event.ctrlKey){
								$("#report-salesCustomerTargetReport-dialog-addSaveGoOn").trigger("click");
							}
						}
						break; 
					case 107: //+
						if(detailDlgOpen){
							$("#report-salesCustomerTargetReport-dialog-addSaveGoOn").trigger("click");
						}
						break;
				}
			});

			$("#report-salesCustomerTargetReport-dialog-addSaveGoOn").bind("click",function(){
				if(detailDlgOpen){
					saveReportDetailHandle(true);
				}
			});	
			$("#report-salesCustomerTargetReport-dialog-addSave").bind("click",function(){
				if(detailDlgOpen){
					$.messager.confirm("提醒","确定保存该考核信息？",function(r){
						if(r){
							saveReportDetailHandle(false);
						}
					});
				}
			});	

			<security:authorize url="/report/sales/exportSalesCustomerTargetReportData.do">

					
			$("#report-export-salesCustomerTargetReportBtn").click(function(){
				var branddept=$("#report-query-branddept").widget('getValue');
				if(null == branddept || "" == branddept){
					$.messager.alert("提醒","抱歉，请选择品牌部门");
					return false;				
				}
				var branddeptname=$("#report-query-branddept").widget('getText') || "";
				var bqstartdate=$("#report-bqstartdate-salesCustomerTargetReport").val();
				var bqenddate=$("#report-bqenddate-salesCustomerTargetReport").val();
				if(null==bqstartdate || "" == bqstartdate){
					$.messager.alert("提醒","抱歉，请填写本期时间");
					return false;		
				}
				if(null==bqenddate || "" == bqenddate){
					$.messager.alert("提醒","抱歉，请填写本期结束时间");
					return false;		
				}
				exportTitle=bqstartdate+"至"+bqenddate+" "+branddeptname+" 客户目标考核报表";
				$("#report-export-salesCustomerTargetReport").Excel('export',{
					queryForm: "#report-form-salesCustomerTargetReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:exportTitle,
			 		url:'report/sales/exportSalesCustomerTargetReportData.do'
				});		
				$("#report-export-salesCustomerTargetReport").trigger("click");
			});
		</security:authorize>		
		});
		function setDataGridColumn(brandList){
			if(!(brandList && (brandList instanceof Array) && brandList.length>0)){
				$("#report-datagrid-salesCustomerTargetReport").datagrid({columns:getDefaultColumn()});
				return;
			}
			var newColumn=getDefaultColumn();
			tableColumnListJson=newColumn;
			for(var i=0 ;i<brandList.length;i++){
				if(brandList[i].name=="" || brandList[i].id==""){ 
					continue;
				}
				newColumn[0].push({title:brandList[i].name,width:380,isShow:true,colspan:5,align:'center'});
				
				newColumn[1].push({field:'targets_'+brandList[i].id,title:'销售目标',width:70,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
	        	});
				newColumn[1].push({field:'saleamount_'+brandList[i].id,title:'销售实绩',width:70,align:'right',
				  	formatter:function(value,rowData,rowIndex){
	        			return formatterMoney(value);
	        		}
    			});
				newColumn[1].push({field:'salerate_'+brandList[i].id,title:'完成占比',width:70,align:'right',
				  	formatter:function(value,rowData,rowIndex){
			        	if(value && !isNaN(value)){
				        	return Number(value*100).toFixed(2) +"%";
			        	}		
			        }
		    	});
				newColumn[1].push({field:'qqsaleamount_'+brandList[i].id,title:'前期销售实绩',width:80,align:'right',
				  	formatter:function(value,rowData,rowIndex){
	        			return formatterMoney(value);
	        		}
				});
				newColumn[1].push({field:'nweektargets_'+brandList[i].id,title:'下月销售目标',width:80,align:'right',
				  	formatter:function(value,rowData,rowIndex){
	        			return formatterMoney(value);
	        		}
				});
			}
			var queryJSON = $("#report-form-salesCustomerTargetReport").serializeJSON();
			$("#report-datagrid-salesCustomerTargetReport").datagrid({
				columns:newColumn,
				url: 'report/sales/showSalesCustomerTargetReportData.do',
      			pageNumber:1,
				queryParams:queryJSON});
		}
		
  		function getDefaultColumn(){
  	  		var column=[[
     					{field:'branduser',title:'业务员',width:120,sortable:true,align:'center',rowspan:2,
    					  	formatter:function(value,rowData,rowIndex){
    		        			return rowData.brandusername;
    		        		}
    				  	},
    				  	{field:'customerid',title:'客户',width:210,isShow:true,rowspan:2,
      					  	formatter:function(value,rowData,rowIndex){
	  		        			return rowData.customername;
	  		        		}
	  				  	},
    					{title:'销售合计',width:200,isShow:true,colspan:2,align:'center'}
    					],[
    						{field:'saleamount',title:'本期销售',width:70,align:'right'},
    						{field:'qqsaleamount',title:'前期销售',width:70,isShow:true,align:'right'}
    		         ]];
	         return column;
  		}

  		function changeSaleRate(targetsid,amountid,rateid){
  	  		try{
  	  			$("#"+rateid).val(0); 
  	  			$("#div_"+rateid).html("0");
  	  	  		var targets=$("#"+targetsid).numberbox("getValue") || 0;
  	  	  		var saleamount = $("#"+amountid).val() || 0;
	  	  	  	var salerate;
	        	if(targets!=0){
	            	salerate=new Number(saleamount/targets);
	            	$("#"+rateid).val(salerate.toFixed(4)); 
					salerate=new Number(salerate*100);
	            	$("#div_"+rateid).html(salerate.toFixed(2)+"%");
	        	}
  	  		}catch(e){
  	  		}
  		}
  		function showTargetReportDetailDialog(rowData){
  			$('#report-salesCustomerTargetReport-dialog').dialog({  
			    title: '业务员：'+rowData.brandusername+'，客户：'+rowData.customername +' 目标考核信息',  
			    fit:true,
			    closed: true,  
			    cache: false,  
			    modal: true,
			    maximizable:true,
			    resizable:true,
			    buttons:'#report-salesCustomerTargetReport-dialog-toolbar',
			    onBeforeOpen:function(){
			    	var htmlsb=new Array();
			    	if(brandListJson){
			    		$("#report-form-salesCustomerTargetReport-detail").empty();
				    	var tabindex=1;
			    		for(var i=0 ;i<brandListJson.length;i++){
				    		var targets;
				    		var saleamount;
				    		var tmpd;
				    		var salerate=0;
							if(brandListJson[i].name=="" || brandListJson[i].id==""){ 
								continue;
							}
					    	htmlsb.push("<fieldset style='margin:15px;'>");
					    	htmlsb.push("<legend>"+brandListJson[i].name+"</legend>");					    	
					    	htmlsb.push("<table style=\"border-collapse:collapse;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
					    	htmlsb.push("<tr>");
					    	htmlsb.push("<td style=\"width:90px;height:30px;line-height:30px;\">销售目标：</td>");
					    	htmlsb.push("<td style=\"width:135px;\">");
					    	htmlsb.push("<input type=\"text\" name=\"targets_"+brandListJson[i].id+"\" eventlisten=\"enter\" id=\"targets_"+brandListJson[i].id+"\" style=\"width:100px;\" class=\"easyui-numberbox\" ");
					    	targets=rowData["targets_"+brandListJson[i].id] || 0;
				    		htmlsb.push(" value=\""+targets+"\" ");
				    		htmlsb.push(" tabindex=\""+(tabindex++)+"\"");
					    	htmlsb.push(" data-options=\"precision:2,onChange:function(newv,oldv){changeSaleRate('targets_"+brandListJson[i].id+"','saleamount_"+brandListJson[i].id+"','salerate_"+brandListJson[i].id+"');}\" ");
					    	htmlsb.push(" onblur=\"javascript:changeSaleRate('targets_"+brandListJson[i].id+"','saleamount_"+brandListJson[i].id+"','salerate_"+brandListJson[i].id+"')\" ");
					    	htmlsb.push(" >");
					    	htmlsb.push("</td>");
					    	htmlsb.push("<td style=\"width:90px;\">销售实绩：</td>");
					    	htmlsb.push("<td style=\"width:135px;\">");
					    	
					    	saleamount=rowData["saleamount_"+brandListJson[i].id] || 0;
					    	
					    	htmlsb.push("<div style=\"height:24px;line-height:24px;text-indent:2px;width:100px; border:1px solid #B3ADAB; background-color: #EBEBE4;\">");
					    	htmlsb.push(formatterMoney(saleamount));
					    	htmlsb.push("</div>");
					    	htmlsb.push("<input type=\"hidden\" id=\"saleamount_"+brandListJson[i].id+"\" ");
				    		htmlsb.push(" value=\""+saleamount+"\" ");
					    	htmlsb.push(" >");
					    	htmlsb.push(" </td>");
					    	htmlsb.push("<td style=\"width:90px;\">完成占比：</td>");							    	
				        	if(targets!=0){
				            	salerate=new Number(saleamount/targets);
				        	}
					    	htmlsb.push("<td style=\"width:135px;\">");
					    	htmlsb.push("<div id=\"div_salerate_"+brandListJson[i].id+"\" style=\"height:24px;line-height:24px;text-indent:2px;width:100px; border:1px solid #B3ADAB; background-color: #EBEBE4;\">");
					    	if(salerate==0){
					    		htmlsb.push(0);
					    	}else{
					    		salerate=new Number(salerate*100);
						    	htmlsb.push(salerate.toFixed(2)+"%");
					    	}
					    	htmlsb.push("</div>");
					    	htmlsb.push("<input type=\"hidden\" name=\"salerate_"+brandListJson[i].id+"\" id=\"salerate_"+brandListJson[i].id+"\" ");
					    	htmlsb.push(" value=\""+salerate.toFixed(4)+"\" ");
					    	htmlsb.push(" >");
					    	htmlsb.push("</td>");
					    	htmlsb.push("</tr>");
					    	htmlsb.push("<tr>");
					    	htmlsb.push("<td style=\"width:90px;height:30px;line-height:30px;\">前期销售实绩：</td>");							    	
					    	htmlsb.push("<td style=\"width:135px;\">");
					   		htmlsb.push("<div style=\"height:24px;line-height:24px;text-indent:2px;width:100px; border:1px solid #B3ADAB; background-color: #EBEBE4;\">");
					   		tmpd=rowData["qqsaleamount_"+brandListJson[i].id] || 0;
					   		htmlsb.push(formatterMoney(tmpd));
					   		htmlsb.push("</div>");
					    	htmlsb.push("<td style=\"width:90px;\">下月销售目标：</td>");
					    	htmlsb.push("<td style=\"width:135px;\" colspan='3'>");
					    	htmlsb.push("<input type=\"text\" name=\"nweektargets_"+brandListJson[i].id+"\" eventlisten=\"enter\" style=\"width:100px;\" class=\"easyui-numberbox\" data-options=\"precision:2\" ");
					    	tmpd=rowData["nweektargets_"+brandListJson[i].id] || 0;
					    	
					    	htmlsb.push(" value=\""+tmpd+"\" ");
				    		htmlsb.push(" tabindex=\""+(tabindex++)+"\"");
					    	htmlsb.push(" >");
					    	htmlsb.push("</td>");
					    	htmlsb.push("</tr>");
					    	htmlsb.push("</table>");
					    	//htmlsb.push("<input type=\"hidden\" name=\"brand_"+brandListJson[i].id+"\" value=\"1\"/>");
					    	htmlsb.push("</fieldset>");
				    	}
			    	}
			    	$("#report-form-salesCustomerTargetReport-detail").html(htmlsb.join(""));
			    	$.parser.parse($("#report-form-salesCustomerTargetReport-detail"));			    			    	
				},
				onOpen:function(){
			    	$(":input[id^='targets_']").first().focus().select();	
			    	detailDlgOpen=true;
				},
				onClose:function(){
					detailDlgOpen=false;
				}
			});
			$('#report-salesCustomerTargetReport-dialog').dialog("open");
  		}

  		function saveReportDetailHandle(addon){
  	  		if(addon){
  	  	  		addon=true;
  	  		}else{
  	  	  		addon=false;
  	  		}
  			var branddept=$("#report-query-branddept").widget('getValue');
			if(null == branddept || "" == branddept){
				$.messager.alert("提醒","抱歉，请选择品牌部门");
				return false;				
			}
			var bqstartdate=$("#report-bqstartdate-salesCustomerTargetReport").val();
			var bqenddate=$("#report-bqenddate-salesCustomerTargetReport").val();
			if(null==bqstartdate || "" == bqstartdate){
				$.messager.alert("提醒","抱歉，请填写本期时间");
				return false;		
			}
			if(null==bqenddate || "" == bqenddate){
				$.messager.alert("提醒","抱歉，请填写本期结束时间");
				return false;		
			}
			var targetsList=new Array();
			var ctargets=$("#report-detailform-salesCustomerTargetReport").serializeJSON();
				var datarow=$("#report-datagrid-salesCustomerTargetReport").datagrid('getRows')[editIndex];
				var data={};
				$.extend(data,datarow,ctargets);
				targetsList.push(data);
			if(targetsList!=null){
				targetsList=JSON.stringify(targetsList);
			}else{
				$.messager.alert("提醒","抱歉，未能找到要保存的考核数据！");
            	return false;
			}
			var queryJson = $("#report-form-salesCustomerTargetReport").serializeJSON();
			data={};
			$.extend(data,queryJson,{targetsList : targetsList});

			loading("保存中..");
			try{
				$.ajax({   
		            url :'report/sales/saveSalesCustomerTargetReport.do',
		            data:data,
		            type:'POST',
		            dataType:'json',
		            success:function(json){		 
		            	loaded();     		
			      		if(json.flag){
			      			$.messager.alert("提醒","保存考核数据成功！");
			      			$("#report-datagrid-salesCustomerTargetReport").datagrid('updateRow', {
			                	index:editIndex,
			                	row:ctargets
			                });
			      			$('#report-salesCustomerTargetReport-dialog').dialog("close");
			      			if(addon){
				      			var index=editIndex+1;
				      			var dataRows=$("#report-datagrid-salesCustomerTargetReport").datagrid('getRows');
				      			if(index<dataRows.length){
					      			var rowData=dataRows[index];
					      			showTargetReportDetailDialog(rowData);
					      			editIndex=index;
					      			$("#report-datagrid-salesCustomerTargetReport").datagrid('selectRow',index);
				      			}
			      			}
			            	return false;
			      		}else{
				      		if(json.msg){
				      			$.messager.alert("提醒",json.msg);
				      		}else{
				      			$.messager.alert("提醒","保存考核数据失败！");
				      		}
			      			return false;
			      		}
		            }
		        });
			}catch(e){
            	loaded();
			}
  		}
	</script>
  </body>
</html>