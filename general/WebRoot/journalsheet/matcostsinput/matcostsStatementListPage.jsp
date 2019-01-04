<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫核销记录页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center'">
    		<div id="journalsheet-table-matcostsStatementBtn" style="padding:2px;height:auto">
    			<form action="" id="matcostsStatement-form-ListQuery" method="post">
	    			<table class="querytable">
	    				<tr>
	    					<td>核销业务日期:</td>
	    					<td><input  name="begintime" value="${firstday }"  class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
   								到&nbsp<input name="endtime" value="${today }"  class="Wdate" style="width:100px;height: 20px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"  />
	    					</td>
                            <td>代垫编码:</td>
                            <td><input id="journalsheet-widget-payid" name="payidarr" type="text" style="width: 160px"/></td>
                            <td>供应商:</td>
                            <td><input id="journalsheet-widget-supplierquery" name="supplierid" type="text"/></td>
                        </tr>
	    				<tr>
                            <td>代垫业务日期:</td>
                            <td><input  name="ddbegintime" value=""  class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                                到&nbsp<input  name="ddendtime" value=""  class="Wdate" style="width:100px;height: 20px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"  />
                            </td>
	    					<td>收回编码:</td>
	    					<td><input id="journalsheet-widget-takebackid" name="takebackidarr" type="text" style="width: 160px;"/></td>
                            <td rowspan="2" colspan="2" class="tdbutton">
                                <div>
                                    <a href="javaScript:void(0);" id="matcostsStatement-query-List" class="button-qr">查询</a>
                                    <a href="javaScript:void(0);" id="matcostsStatement-query-reloadList" class="button-qr">重置</a>
                                </div>
                            </td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    		<table id="journalsheet-table-matcostsStatement"></table>
    		<div style="display:none">
	    		<div id="matcostsInput-dialog-operate"></div>
	    		<div id="matcostsReimburse-dialog-operate"></div>
	    	</div>
    	</div>
    </div>
    <script type="text/javascript">
		var statement_FooterObject=null;
    	var matcostsStatement_AjaxConn = function (Data, Action, Str) {
    		if(null != Str && "" != Str){
    			loading(Str);
    		}
		   var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    });
		    return MyAjax.responseText;
		}
		
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var matcostsStatementListColJson=$("#journalsheet-table-matcostsStatement").createGridColumnLoad({
	     	name:'t_js_matcostsinput_statement',
	     	frozenCol:[[
						{field:'idok',checkbox:true,isShow:true}
	     	]],
	     	commonCol:[[
	    		{field:'id',title:'编码',width:120,sortable:true,hidden:true},
	    		//{field:'oaid',title:'OA编码',width:80,sortable:true,isShow:true},
	    		{field:'supplierid',title:'供应商编码',width:70,sortable:true,isShow:true},
				{field:'suppliername',title:'供应商名称',width:210,sortable:true,isShow:true},
				{field:'supplierdeptid',title:'所属部门',width:70,sortable:true,isShow:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptname;
					}
				},
	     		{field:'payid',title:'代垫录入编号',width:125,
					formatter:function(val,rowData,rowIndex){
						if(val!=null && val!=""){
							return "<a href='javascript:void(0);' onclick=\"javascript:showMatcostsInput('"+val+"')\">"+val+"</a>"
						}
					}
				},
				{field:'factoryamount',title:'工厂投入',resizable:true,sortable:true,align:'right',hidden:true,
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'remainderamount',title:'核销前代垫剩余金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'remainderamountafter',title:'核销后代垫剩余金额',resizable:true,sortable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
	     		{field:'takebackid',title:'代垫收回编号',width:125,
					formatter:function(val,rowData,rowIndex){
						if(val!=null && val!=""){
							return "<a href='javascript:void(0);' onclick=\"javascript:showMatcostsReimburse('"+val+"')\">"+val+"</a>"
						}
					}
				},
				{field:'takebackamount',title:'收回总金额',resizable:true,sortable:true,align:'right',hidden:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'tbremainderamount',title:'核销前收回剩余金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'tbremainderamountafter',title:'核销后收回剩余金额',resizable:true,sortable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				}
				<c:forEach items="${reimbursetypeList }" var="list">
				  ,{field:'reimburse_${list.code}',title:'${list.codename}-核销',align:'right',resizable:true,isShow:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterMoney(value);
		        	}
				  }
				  </c:forEach>,
				{field:'relateamount',title:'核销合计金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'iswriteoff',title:'核销状态',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val=="1"){
							return "核销";
						}
					}
				},
				{field:'writeoffdate',title:'核销日期',resizable:true,sortable:true,
					formatter:function(val,rowData,rowIndex){
						if(rowData.iswriteoff=='1' && val){
							if(val.length>=10){
								return val.substring(0,10);
							}else{
								return val;
							}
						}
					}
				},
				{field:'adduserid',title:'核销人员',resizable:true,sortable:true,
					formatter:function(val,rowData,rowIndex){
						if(rowData.iswriteoff=='1' && rowData.addusername){
							return rowData.addusername;
						}
					}
				},
				{field:'ddbusinessdate',title:'代垫业务日期',width:130,sortable:true,isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val){
							return val.replace(/[tT]/," ");
						}
					}
				},
				{field:'addtime',title:'添加时间',width:130,sortable:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						if(val){
							return val.replace(/[tT]/," ");
						}
					}
				}
			]]
	     });
	     
	    
	    function showMatcostsInput(id){
		    if(id==null || $.trim(id)==""){
			    return false;
		    }
		    id=$.trim(id);
	   		$('#matcostsInput-dialog-operate').dialog({  
			    title: "代垫"+id+"详细信息",  
			    width: 530,
			    height: 340,
			    closed: false,  
			    cache: false,  
			    href: 'journalsheet/matcostsInput/showMatcostsInputViewPage.do?id='+id,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			}
			});
			$('#matcostsInput-dialog-operate').dialog('open');
	   	}

	    function showMatcostsReimburse(id){
		    if(id==null || $.trim(id)==""){
			    return false;
		    }
		    id=$.trim(id);
	   		$('#matcostsReimburse-dialog-operate').dialog({  
			    title: "收回"+id+"详细信息",  
			    width: 510,
			    height: 310,
			    closed: false,  
			    cache: false,  
			    href: 'journalsheet/matcostsInput/showMatcostsReimburseViewPage.do?id='+id,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			}
			});
			$('#matcostsReimburse-dialog-operate').dialog('open');
	   	}
	   	
		
		$(function(){

			//供应商查询
		  	$("#journalsheet-widget-supplierquery").widget({
		  		width:150,
				name:'t_js_matcostsinput',
				col:'supplierid',
				singleSelect:true,
				view:true
			});

			//查询
			$("#matcostsStatement-query-List").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#matcostsStatement-form-ListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#journalsheet-table-matcostsStatement").datagrid({
	      			url: 'journalsheet/matcostsInput/getMatcostsStatementPageListData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			
			//重置按钮
			$("#matcostsStatement-query-reloadList").click(function(){
				$("#matcostsStatement-form-ListQuery")[0].reset();
				$("#journalsheet-table-matcostsStatement").datagrid('loadData',{total:0,rows:[]});
				$("#journalsheet-widget-supplierquery").widget('clear');
			});
     		
     		$("#journalsheet-table-matcostsStatement").datagrid({ 
     			authority:matcostsStatementListColJson,
	  	 		frozenColumns:matcostsStatementListColJson.frozen,
				columns:matcostsStatementListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'writeoffdate',
	  	 		sortOrder:'desc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				pageSize:20,
				toolbar:'#journalsheet-table-matcostsStatementBtn',
				pageList:[10,20,30,50,200],
				onLoadSuccess:function(){
	     			var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						statement_FooterObject = footerrows[0];
					}
		 		},
				onCheckAll:function(){
		 			statementCountTotalAmount();
				},
				onUncheckAll:function(){
					statementCountTotalAmount();
				},
				onCheck:function(){
					statementCountTotalAmount();
				},
				onUncheck:function(){
					statementCountTotalAmount();
				}
			}).datagrid("columnMoving");
		});
		function statementCountTotalAmount(){
    		var rows =  $("#journalsheet-table-matcostsStatement").datagrid('getChecked');
    		if(null==rows || rows.length==0){
           		var foot=[];
           		if(null!=statement_FooterObject){
	        		foot.push(emptyChooseObjectFoot());
	        		foot.push(statement_FooterObject);
	    		}
    			$("#journalsheet-table-matcostsStatement").datagrid("reloadFooter",foot);
           		return false;
       		}
    		var relateamount = 0;
    		
    		<c:forEach items="${reimbursetypeList }" var="list">
    			var reimburse_${list.code}=0;
    		</c:forEach>
    		
    		for(var i=0;i<rows.length;i++){
    			relateamount = Number(relateamount)+Number(rows[i].relateamount == undefined ? 0 : rows[i].relateamount);

        		<c:forEach items="${reimbursetypeList }" var="list">
        			reimburse_${list.code} = Number(reimburse_${list.code})+Number(rows[i].reimburse_${list.code} == undefined ? 0 : rows[i].reimburse_${list.code});
        		</c:forEach>

    		}
    		var foot=[{suppliername:'选中金额',
        				relateamount:relateamount

        				<c:forEach items="${reimbursetypeList }" var="list">
							,reimburse_${list.code} : reimburse_${list.code}
						</c:forEach>
        			}];
    		if(null!=statement_FooterObject){
        		foot.push(statement_FooterObject);
    		}
    		$("#journalsheet-table-matcostsStatement").datagrid("reloadFooter",foot);
    	}
    	function emptyChooseObjectFoot(){
    		var relateamount=0;

    		<c:forEach items="${reimbursetypeList }" var="list">
    			var reimburse_${list.code}=0;
    		</c:forEach>

    		var foot={suppliername:'选中金额',
				relateamount:relateamount
				<c:forEach items="${reimbursetypeList }" var="list">
					,reimburse_${list.code} : reimburse_${list.code}
				</c:forEach>
			};
			return foot;
    	}
    </script>
  </body>
</html>
