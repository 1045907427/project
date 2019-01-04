<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>库存收发存明细</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
  <div id="report-toolbar-storageRecSendDetailReport" style="padding: 0px">
   		<div class="buttonBG">
	        <security:authorize url="/report/storage/storageRecSendReportExport.do">
	            <a href="javaScript:void(0);" id="report-buttons-storageRecSendReportDetailPage" class="easyui-linkbutton button-list" iconCls="button-export" plain="true" title="导出">导出</a>
	        </security:authorize>
	    </div>
   		<form action="" id="report-query-form-storageRecSendReportDetail" method="post">
  	    <input type="hidden" name="storageid" value="${storageid}"/>
		<input type="hidden" name="goodsid" value="${goodsid}"/>
		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
	    </form>
   	</div>
	<table id="report-datagrid-storageRecSendReportDetail"></table>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-storageRecSendReportDetail").serializeJSON();
    		$(function(){
    			$("#report-datagrid-storageRecSendReportDetail").datagrid({ 
					columns:[[
                          {field:'storageid',title:'仓库名称',width:80,
 	                          formatter:function(value,rowData,rowIndex){
		                         return rowData.storagename;
	                          }
                          },
                          {field:'goodsid',title:'商品编码',width:70},
                          {field:'goodsname',title:'商品名称',width:130,aliascol:'goodsid',sortable:true,}, 
                          {field:'billmodel',title:'业务单据类型',width:100,
                        	  formatter:function(value,rowData,rowIndex){
 		                         return rowData.billmodelname;
 	                          }  
                          },
                          {field:'barcode',title:'条形码',sortable:true,width:90,isShow:true},
                          {field:'brandid',title:'品牌名称',width:80,aliascol:'goodsid',sortable:true,
	                          formatter:function(value,rowData,rowIndex){
    		                      return rowData.brandname;
    	                      }
                          },
                          {field:'goodssort',title:'商品分类',width:80,aliascol:'goodsid',sortable:true,
	                          formatter:function(value,rowData,rowIndex){
    		                      return rowData.goodssortname;
                              }
                          },
                      {field:'boxnum',title:'箱装量',width:40,aliascol:'goodsid',align:'right',
	                     formatter:function(value,rowData,rowIndex){
		                    return formatterBigNumNoLen(value);
                         }
                      },
                      {field:'unitname',title:'单位',width:40},
                      {field:'beginnum',title:'期初数量',width:80,sortable:true,align:'right',
  	                     formatter:function(value,rowData,rowIndex){
		                    return formatterBigNumNoLen(value);
	                     }
                      },
                     {field:'auxbeginnum',title:'期初箱数',width:80,sortable:true,align:'right',hidden:true,
  	                     formatter:function(value,rowData,rowIndex){
		                     return formatterBigNumNoLen(value);
	                     }
                     },
                     {field:'beginnumdetail',title:'期初辅数量',width:90,sortable:true,align:'right',
	                    formatter:function(value,rowData,rowIndex){
		                   if(value!=""){
			                  return value;
		                   }else{
			                  return formatterBigNumNoLen(rowData.auxbeginnum);
		                   }
                        }
                     },
                     {field:'beginamount',title:'期初金额',width:90,sortable:true,align:'right',
   					  formatter:function(value,rowData,rowIndex){
   						  if(value!=""){
   							  return formatterMoney(value);
   						  }else{
   							  return formatterMoney(rowData.beginamount);
   						  }
   			          }
   				     },
                     {field:'receivenum',title:'入库数量',width:80,align:'right',sortable:true,
  	                    formatter:function(value,rowData,rowIndex){
		                   return formatterBigNumNoLen(value);
	                    }
                     },
                     {field:'auxreceivenum',title:'入库箱数',width:80,sortable:true,align:'right',hidden:true,
  	                    formatter:function(value,rowData,rowIndex){
		                   return formatterBigNumNoLen(value);
	                    }
                     },
                     {field:'receivenumdetail',title:'入库辅数量',width:90,sortable:true,align:'right',
	                    formatter:function(value,rowData,rowIndex){
		                   if(value!=""){
			                  return value;
		                   }else{
			                  return formatterBigNumNoLen(rowData.auxreceivenum);
		                   }
                        }  
                     },
                     {field:'receiveamount',title:'入库金额',width:90,sortable:true,align:'right',
   					  formatter:function(value,rowData,rowIndex){
   						  if(value!=""){
   							  return formatterMoney(value);
   						  }else{
   							  return formatterMoney(rowData.receiveamount);
   						  }
   			          }
   					  },
                     {field:'sendnum',title:'出库数量',width:80,align:'right',sortable:true,
  	                    formatter:function(value,rowData,rowIndex){
		                    return formatterBigNumNoLen(value);
	                    }
                     },
                     {field:'auxsendnum',title:'出库箱数',width:80,sortable:true,align:'right',hidden:true,
  	                    formatter:function(value,rowData,rowIndex){
		                   return formatterBigNumNoLen(value);
	                    }
                     },
                     {field:'sendnumdetail',title:'出库辅数量',width:90,sortable:true,align:'right',
	                    formatter:function(value,rowData,rowIndex){
		                   if(value!=""){
			                   return value;
		                   }else{
			                   return formatterBigNumNoLen(rowData.auxsendnum);
		                   }
                        }  
                     },
                     {field:'sendamount',title:'出库金额',width:90,sortable:true,align:'right',
   					  formatter:function(value,rowData,rowIndex){
   						  if(value!=""){
   							  return formatterMoney(value);
   						  }else{
   							  return formatterMoney(rowData.sendamount);
   						  }
   			          }
   				  },
                     {field:'endnum',title:'期末数量',width:90,align:'right',sortable:true,
  	                    formatter:function(value,rowData,rowIndex){
		                   return formatterBigNumNoLen(value);
	                    }
                     },
                     {field:'auxendnum',title:'期末箱数',width:80,sortable:true,align:'right',hidden:true,
  	                    formatter:function(value,rowData,rowIndex){
		                   return formatterBigNumNoLen(value);
	                    }
                     },
                     {field:'endnumdetail',title:'期末辅数量',width:95,sortable:true,align:'right',
	                       formatter:function(value,rowData,rowIndex){
		                   if(value!=""){
			                   return value;
		                   }else{
			                   return formatterBigNumNoLen(rowData.auxendnum);
		                   }
                        }   
                     },
                     {field:'endamount',title:'期末金额',width:90,sortable:true,align:'right',
   					  formatter:function(value,rowData,rowIndex){
   						  if(value!=""){
   							  return formatterMoney(value);
   						  }else{
   							  return formatterMoney(rowData.endamount);
   						  }
   			          }
   				     },
                     {field:'addtime',title:'添加时间',width:130,sortable:true,align:'right',
                    	 formatter: function(dateValue,row,index){
						  	 if(dateValue!=undefined){
						  		 var newstr=dateValue.replace("T"," ");
						  		 return newstr;
						     }
						     return " ";
						  }   
                     }
			         ]],
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
		  	 		toolbar:'#report-toolbar-storageRecSendDetailReport',
	      			url:'report/storage/showStorageRecSendReportDetailList.do',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
    			
    			
    			$("#report-buttons-storageRecSendReportDetailPage").Excel('export',{
    				queryForm: "#report-query-form-storageRecSendReportDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
    		 		type:'exportUserdefined',
    		 		name:'仓库收发存报表',
    		 		url:'report/storage/exportStorageRecSendReportDetailData.do'
    			});
    		});
    	</script>
  </body>
</html>
