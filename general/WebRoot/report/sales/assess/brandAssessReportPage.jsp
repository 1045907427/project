<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按品牌销售情况考核报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-salesBrandAssess"></table>
    	<div id="report-toolbar-salesBrandAssess" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/sales/salesBrandAssessReportExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-salesBrandAssessPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-salesBrandAssess" method="post">
	    		<table class="querytable">

	    			<tr>
		    			<td>年度：</td>
		   				<td>
		   					<input id="report-query-year" class="easyui-numberspinner" style="width:100px;" name="year" required="required" data-options="min:2000" value="${year }">
		   				</td>
                        <td>商品品牌:</td>
                        <td><input type="text" id="report-query-brandid" name="brandid"/></td>
	    			</tr>
	    			<tr>
                        <td>月份：</td>
                        <td>
                            <input id="report-query-month" class="easyui-numberspinner" style="width:100px;" name="month" required="required" data-options="min:1,max:12" value="${month }">
                        </td>
	    				<td>品牌部门:</td>
	    				<td><input type="text" id="report-query-deptid" name="branddept"/></td>
	    				<td colspan="2">
	    					<a href="javaScript:void(0);" id="report-query-salesBrandAssess" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-salesBrandAssess" class="button-qr">重置</a>
	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-salesBrandAssess").serializeJSON();
    		$(function(){
    			var tableColumnListBrandJson = $("#report-datagrid-salesBrandAssess").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
						  {field:'branddept',title:'品牌部门',width:100,sortable:true,
							  formatter:function(value,rowData,rowIndex){
					        		return rowData.branddeptname;
					          }
						  },
						  {field:'brandid',title:'商品品牌',width:100,sortable:true,
							  formatter:function(value,rowData,rowIndex){
					        		return rowData.brandname;
					          }  
						  },
						  {field:'salestarget',title:'销售目标',width:100,align:'right',sortable:true,isShow:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	},
				        	editor:{
					        	type:'numberbox',
					        	options:{
					        		precision:2
					        	}
					        }
						  },
						  {field:'salesamount',title:'销售金额',width:100,align:'right',sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'salesnotaxamount',title:'销售未税金额',width:100,align:'right',sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'salestax',title:'销售税额',width:100,align:'right',sortable:true,isShow:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'salesrate',title:'销售完成比率',width:100,align:'right',sortable:true,isShow:true,
						  	formatter:function(value,rowData,rowIndex){
						  		if(value != undefined && value != ""){
						  			return formatterMoney(value)+"%";
						  		}
				        	}
						  },
						  {field:'grossamount',title:'销售毛利',width:100,align:'right',sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
    			$("#report-datagrid-salesBrandAssess").datagrid({ 
			 		authority:tableColumnListBrandJson,
			 		frozenColumns: tableColumnListBrandJson.frozen,
					columns:tableColumnListBrandJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
			 		sortName:'branddept',
					sortOrder:'asc',
					toolbar:'#report-toolbar-salesBrandAssess',
		  	 		onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							countTotalAmount();
						}
			 		},
					onCheckAll:function(){
						countTotalAmount();
					},
					onUncheckAll:function(){
						countTotalAmount();
					},
					onCheck:function(){
						countTotalAmount();
					},
					<security:authorize url="/report/target/addReportTargetBySalesBrand.do">
					onDblClickRow: function(rowIndex, rowData){
						onClickCell(rowIndex, "salestarget");
	    			},
	    			onClickCell: function(index, field, value){
    					onClickCell(index, field);
	    			},
	    			</security:authorize>
					onUncheck:function(){
						countTotalAmount();
					}
				}).datagrid("columnMoving");
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:130,
					singleSelect:false
				});
				$("#report-query-deptid").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:130,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-query-salesBrandAssess","report-reload-salesBrandAssess");
				
				//查询
				$("#report-query-salesBrandAssess").click(function(){
                    editIndex=undefined;
		      		var queryJSON = $("#report-query-form-salesBrandAssess").serializeJSON();
		      		$("#report-datagrid-salesBrandAssess").datagrid({
		      			url: 'report/sales/showBrandAssessReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-salesBrandAssess").click(function(){
                    $("#report-query-brandid").widget("clear");
					$("#report-query-deptid").widget("clear");
					$("#report-query-form-salesBrandAssess")[0].reset();
					var queryJSON = $("#report-query-form-salesBrandAssess").serializeJSON();
		       		$("#report-datagrid-salesBrandAssess").datagrid('loadData',{total:0,rows:[]});
                    $("#report-query-year").numberspinner('setValue', '${year}');
                    $("#report-query-month").numberspinner('setValue', '${month}');
				});
				
				$("#report-buttons-salesBrandAssessPage").Excel('export',{
					queryForm: "#report-query-form-salesBrandAssess", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'分品牌销售情况考核报表',
			 		url:'report/sales/exportBrandAssessReportData.do'
				});
    		});
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-salesBrandAssess").datagrid('getChecked');
    			var salesamount = 0;
    			var salestarget=0;
    			var grossamount = 0;
    			for(var i=0;i<rows.length;i++){
    				salesamount = Number(salesamount)+Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
    				salestarget = Number(salestarget)+Number(rows[i].salestarget == undefined ? 0 : rows[i].salestarget);
                    grossamount = Number(grossamount)+Number(rows[i].grossamount == undefined ? 0 : rows[i].grossamount);
    			}
				var salesrate = 0;
				if(salestarget > 0 && salesamount >= 0){
					salesrate = (Number(salesamount)/Number(salestarget))*100;
				}else if(salestarget != 0){
					if(salesamount >= salestarget){
						salesrate = 100 ;
					}else{
						salesrate = ((Number(salesamount) - Number(salestarget))/Math.abs(salestarget))*100;
					}
				}
    			var obj = {brandname:'选中合计',salesamount:salesamount,salestarget:salestarget,salesrate:salesrate,grossamount:grossamount}
	    		var foot=[];
	    		foot.push(obj);
	    		if(null!=SR_footerobject){
	        		foot.push(SR_footerobject);
	    		}
	    		$("#report-datagrid-salesBrandAssess").datagrid("reloadFooter",foot);
    		}
    		var $wareList = $("#report-datagrid-salesBrandAssess"); //商品datagrid的div对象
    		<security:authorize url="/report/target/addReportTargetBySalesBrand.do">
        	var editIndex = undefined;  
        	var thisIndex = undefined;
        	var editfiled = null;
            function endEditing(index,field){
                if (editIndex == undefined){return true};
           		if(field == "salestarget"){
                	if ($wareList.datagrid('validateRow', editIndex)){
                        var rows = $wareList.datagrid('getRows');
                        if(editIndex >= rows.length){
                            editIndex = index ;
                        }
    	                $wareList.datagrid('endEdit', editIndex);
    	                var row = $wareList.datagrid('getRows')[editIndex];
    					var year = $("#report-query-year").val();
    					var month = $("#report-query-month").val();
    					var brandid = row.brandid;
                        var amount = row.salestarget ;

                        $.ajax({
                            url:'report/target/addReportTargetBySalesBrand.do',
                            dataType:'json',
                            type:'post',
                            data:{brandid:brandid,year:year,month:month,amount:amount},
                            async:false,
                            success:function(json){
                            }
                        });

                        if(amount > 0 && row.salesamount >= 0){
                            row.salesrate = (Number(row.salesamount)/Number(amount))*100;
                        }else if(amount != 0){
                            if(row.salesamount >= amount){
                                row.salesrate = 100 ;
                            }else{
                                row.salesrate = ((Number(row.salesamount) - Number(amount))/Math.abs(amount))*100;
                            }
                        }

                        $wareList.datagrid('updateRow',{index:editIndex, row:row});
                        countTotal();

    	                editIndex = undefined;  
    	                return true;  
    	            } else {  
    	                return false;  
    	            }  
                }
            }
            function onClickCell(index, field){
                if (endEditing(index,editfiled)){
        			var row = $wareList.datagrid('getRows')[index];
        			if(row.brandid == undefined){
        				return false;
        			}
        			editfiled = field;
                	if(field == "salestarget"){  
                		$wareList.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});  
    	                editIndex = index;  
    	                thisIndex = index;
    	                var ed = $wareList.datagrid('getEditor', {index:editIndex,field:'salestarget'});
    	                $(ed.target).textbox('textbox').focus();
    	                $(ed.target).textbox('textbox').select();
                    }
                }  
            }
            //回车键操作
            $(document).die("keydown").live("keydown",function(event){
                var row = $wareList.datagrid('getRows')[editIndex];
                if(event.keyCode==13 && row != undefined) {

                    var year = $("#report-query-year").val();
                    var month = $("#report-query-month").val();

                    var brandid = row.brandid;
                    $wareList.datagrid('endEdit', editIndex);
                    var amount = row.salestarget;
                    $.ajax({
                        url: 'report/target/addReportTargetBySalesBrand.do',
                        dataType: 'json',
                        type: 'post',
                        data: {brandid: brandid, year: year, month: month, amount: amount},
                        async: false,
                        success: function (json) {
                        }
                    });
                    if(amount > 0 && row.salesamount >= 0){
                        row.salesrate = (Number(row.salesamount)/Number(amount))*100;
                    }else if(amount != 0){
                        if(row.salesamount >= amount){
                            row.salesrate = 100 ;
                        }else{
                            row.salesrate = ((row.salesamount - amount)/Math.abs(amount))*100;
                        }
                    }
                    $wareList.datagrid('updateRow', {index: editIndex, row: row});
                    countTotal();

                    editIndex += 1;
                    $wareList.datagrid('beginEdit', editIndex );

                    var ed = $wareList.datagrid('getEditor', {index: editIndex, field: 'salestarget'});
                    if(ed != null){
                        $(ed.target).textbox('textbox').focus();
                        $(ed.target).textbox('textbox').select();
                    }
                }
//                else{
//                    editIndex = undefined ;
//                }
            });

            </security:authorize>
            function countTotal(){ //计算合计
        		var rows = $wareList.datagrid('getRows');
        		var leng = rows.length;
        		var salesamount = 0;
        		var salestarget = 0;
        		var grossamount = 0;
        		for(var i=0; i<leng; i++){
        			salesamount += Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
        			salestarget += Number(rows[i].salestarget == undefined ? 0 : rows[i].salestarget);
                    grossamount = Number(grossamount)+Number(rows[i].grossamount == undefined ? 0 : rows[i].grossamount);
        		}
				var salesrate = 0;
				if(salestarget > 0 && salesamount >= 0){
					salesrate = (Number(salesamount)/Number(salestarget))*100;
				}else if(salestarget != 0){
					if(salesamount >= salestarget){
						salesrate = 100 ;
					}else{
						salesrate = ((Number(salesamount) - Number(salestarget))/Math.abs(salestarget))*100;
					}
				}
        		SR_footerobject = {brandname:'合计',salesamount:salesamount,salestarget:salestarget,salesrate:salesrate,grossamount:grossamount };
        		$wareList.datagrid('reloadFooter',[SR_footerobject]);
        	}
    	</script>
  </body>
</html>
