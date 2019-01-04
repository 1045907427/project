<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印内容排序策略生成列表</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
     	<div data-options="region:'center',border:false">
  			<table id="tplorderseq-tablecolumn-orderCreateSeqList"></table>
  			<div id="tplorderseq-query-orderCreateSeqList" style="padding:0px;height:auto">
				<table>
					<tr>
						<td>表名:</td>
						<td>
							<input type="text" id="tplorderseq-form-orderCreateSeqList-table" name="tablename" style="width:300px;"/>
						</td>
				   		<td>
			   				<a href="javaScript:void(0);" id="tplorderseq-query-queryorderCreateSeqList" class="button-qr">查询</a>
	    					<a href="javaScript:void(0);" id="tplorderseq-query-reloadorderCreateSeqList" class="button-qr">重置</a>&nbsp;
	    					<a href="javaScript:void(0);" id="tplorderseq-orderCreate-savebtn" class="button-qr">生成策略</a>
			   			</td>
					</tr>
				</table>
		    	<input type="hidden" id="tplorderseq-orderCreate-tablesavetoid" />
		    	<input type="hidden" id="tplorderseq-orderCreate-seqsavetoid" />
		    	<input type="hidden" id="tplorderseq-orderCreate-seqnamesavetoid" />
	   		</div>
   		</div>   
   	</div>
   	<script type="text/javascript">
   		$(document).ready(function(){
   			
   			$('#tplorderseq-tablecolumn-orderCreateSeqList').datagrid({ 
				fit:true,
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				rownumbers:true, 
				pagination:false,
				idField:'id',
				sortName:'usepk desc,colorder',
				sortOrder:'asc',
	  			toolbar:'#tplorderseq-query-orderCreateSeqList',
				columns:[[  
		   			{field:'idok',checkbox:true},
	  				{field:'columnname',title:'字段',width:100,sortable:true},	 
				    {field:'colchnname',title:'描述名',width:100},		    	
	  				{field:'orderseq',title:'字段排序',width:130,
	  					formatter:function(val,rowData,rowIndex){
	  						var htmlsb=new Array();
	  						htmlsb.push('<select id="seqordercreate_orderby_');
	  						htmlsb.push(rowData.id);
	  						htmlsb.push('" style="width:110px;">');
							htmlsb.push('<option value="ASC">ASC从小到大</option>');
							htmlsb.push('<option value="DESC">DESC从大到小</option>');
	  						htmlsb.push('</select>');
	  						return htmlsb.join('');
	  					}
	  				},
	  				{field:'seq',title:'顺序',width:100,
	  					formatter:function(val,rowData,rowIndex){
	  						var htmlsb=new Array();
	  						htmlsb.push('<input id="seqordercreate_seq_');
	  						htmlsb.push(rowData.id);
	  						htmlsb.push('"');
	  						htmlsb.push(' type="text" ');
	  						htmlsb.push(' onchange="if(/\\D/.test(this.value)){this.value=\'999\';}" ');
	  						htmlsb.push(' onpropertychange="if(/\\D/.test(this.value)){this.value=\'999\';}" ');
	  						htmlsb.push(' style="width:80px;" />');
	  						return htmlsb.join('');
	  					}
	  				}
	  			]]
	   		});
   			
   			$("#tplorderseq-orderCreate-savebtn").click(function(){
   				$.messager.confirm("提醒","是否生成排序策略?",function(r){
    				if(r){
						var dataRow=$("#tplorderseq-tablecolumn-orderCreateSeqList").datagrid('getChecked');
						if(dataRow==null || dataRow.length==0){
							$.messager.alert("提醒","请选择相应的字段!");
							return;
						}
						if(dataRow.length>0){
							
							var orderByObjArr=new Array();
							for(var i=0;i<dataRow.length;i++){
								if(dataRow[i].id==null){
									continue;
								}
								var orderby=$("#seqordercreate_orderby_"+dataRow[i].id).val() || "";
								orderby=$.trim(orderby);
								var seq=$("#seqordercreate_seq_"+dataRow[i].id).val() || 999;
								if(orderby== ""){
									continue;
								}
								var orderByObj={};
								orderByObj.colchnname=dataRow[i].colchnname;
								orderByObj.columnname=dataRow[i].columnname;
								orderByObj.orderby=orderby;
								orderByObj.seq=seq;
								orderByObjArr.push(orderByObj);
							}
							var resultArr=new Array();
							var resultChArr=new Array();
							if(orderByObjArr.length>0){
								orderByObjArr.sort(seqOrderCreatePageCompareSort("seq"));
								for(var j=0;j<orderByObjArr.length;j++){
									var item=orderByObjArr[j];
									if(null!=item){
										if(null==item.orderby){
											item.orderby='ASC';
										}
										item.orderby=item.orderby.toUpperCase();
										if(item.orderby!='ASC'){
											item.orderby='DESC';
										}
										if( null!=item.columnname){
											resultArr.push(item.columnname + " "+item.orderby);
											
											if(null!=item.colchnname){
												var tmpstr=item.colchnname;
												if(item.orderby=='ASC'){
													tmpstr=tmpstr+"从小到大";
												}else{
													tmpstr=tmpstr+'从大到小';
												}
												resultChArr.push(tmpstr);
											}											
										}
									}
								}
							}
							var seqsavetoid=$("#tplorderseq-orderCreate-seqsavetoid").val()||"tplresource-PrintTempletOrderseq-form-orderseq";
							if(seqsavetoid!="" && $("#"+seqsavetoid).size()>0){
								$("#"+seqsavetoid).val(resultArr.join(','));
							}
							var seqnamesavetoid=$("#tplorderseq-orderCreate-seqnamesavetoid").val()||"tplresource-PrintTempletOrderseq-form-seqnamesavetoid";
							if(seqnamesavetoid!="" && $("#"+seqnamesavetoid).size()>0){
								$("#"+seqnamesavetoid).val(resultChArr.join(','));
							}
							var tablename=$("#tplorderseq-form-orderCreateSeqList-table").widget('getValue') || "";
							var tablesavetoid=$("#tplorderseq-orderCreate-tablesavetoid").val() || "tplorderseq-form-add-linkdatatable";
							if(tablesavetoid!="" && $("#"+tablesavetoid).size()>0){
								$("#"+tablesavetoid).val(tablename);
							}
							$('#tplorderseq-dialog-printOrderSeqOper-orderCreate-content').dialog('close'); 
						}
    				}
    			});
   			});
   			
   			$("#tplorderseq-form-orderCreateSeqList-table").widget({
	   			referwid:'RL_T_SYS_TABLEINFO',
	   			singleSelect:true,
	   			width:'300',
	   			view:true,
	   			required:true,
	   			initValue:($("#tplorderseq-form-add-linkdatatable").val() ||""),
	   			param:[{field:'moduletype', op:'like', value:'printorderseq'}],
	   			onClear:function(){
	   				$("#tplorderseq-tablecolumn-orderCreateSeqList").datagrid('loadData',{total:0,rows:[]});
	   			}
	   		});
   			
   			
   			//查询
  			$("#tplorderseq-query-queryorderCreateSeqList").click(function(){
  				//把form表单的name序列化成JSON对象
  				var tablename=$("#tplorderseq-form-orderCreateSeqList-table").widget('getValue') || "";
  				if(tablename==""){
					$.messager.alert("提醒","请选择表名!");
					return;
  				}
  			 	$("#tplorderseq-tablecolumn-orderCreateSeqList").datagrid({
  	    			url: 'sysDataDictionary/showTableColumnPageAllList.do',
  	    			pageNumber:1,
  					queryParams:{fixtablename:tablename}
  	    		}).datagrid("columnMoving");
  			});
  			//重置
  			$('#tplorderseq-query-reloadorderCreateSeqList').click(function(){
  				$("#tplorderseq-form-orderCreateSeqList-table").widget('clear');
  				$("#tplorderseq-form-orderCreateSeqList")[0].reset();
	       		$("#tplorderseq-tablecolumn-orderCreateSeqList").datagrid('loadData',{total:0,rows:[]});
  			});
	    	
   		});

   		function seqOrderCreatePageCompareSort(propertyName) { 
   			return function (object1, object2) { 
	   			var value1 = object1[propertyName] || 999; 
	   			var value2 = object2[propertyName] || 999; 
	   			if (value2 > value1) { 
	   				return -1; 
	   			} 
	   			else if (value2 < value1) { 
	   				return 1; 
	   			} 
	   			else { 
	   				return 0; 
	   			} 
   			} 
   		} 
   		
   		function seqOrderCreatePageOnOpenInit(options){
	   		var tablesavetoid=options.tablesavetoid || "";
	   		var seqsavetoid=options.seqsavetoid || "";
	   		var initdata=options.initdata || "";
	   		var seqnamesavetoid=options.seqnamesavetoid || "";
   			$("#tplorderseq-orderCreate-seqsavetoid").val(seqsavetoid);
   			$("#tplorderseq-orderCreate-tablesavetoid").val(tablesavetoid);
   			$("#tplorderseq-orderCreate-seqnamesavetoid").val(seqnamesavetoid);
   		}
   	</script>
  </body>
</html>
