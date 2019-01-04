<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>高级通用查询页面</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center'">
  			<form action="" id="advanced-form-query">
  			<div id="${divID}-query"></div>
  			</form>
		    <div>
		    	历史记录查询:<input type="text" id="advanced-history-query" style="width: 250px;"/>
		    	<input type="button" id="advanced-save" style="background-color: white;border-radius:5px;"   value="保存 " />
		    	<input type="button" id="advanced-delete" style="background-color: white;border-radius:5px;"  value="删除 " />
		    </div>
  		</div>
  		<div data-options="region:'south'" style="height: 40px;">
	    	<div class="buttonDivR">
                <input type="button" id="${divID}-advanced-queryPage-query" style="background-color: white;border-radius:5px;"  value="确定 "/>
	    		<%--<a href="javaScript:void(0);" id="${divID}-advanced-queryPage-query" class="easyui-linkbutton" >确定</a>--%>
	    	</div>
	    </div>
  	</div>
  	<div id="advanced-query-savepage" class="pageContent easyui-dialog" title="通用查询保存" data-options="closed:true" style="width:430px;height:300px;padding:10px">
		<form action="common/addQuery.do" id="advanced-form" method="post">
		<p>
			<label style="width: 60px">查询名称:</label>
			<input type="text" name="query.queryname" class="easyui-validatebox" required="true" style="width:200px; "/>
		</p>
		<p style="height: 100px;">
			<label style="width: 60px">描&nbsp;&nbsp;述:</label>
			<textarea name="query.description" style="width:195px;height: 100px;"></textarea>
		</p>
		<p>
			<label style="width: 60px">类&nbsp;&nbsp;型:</label>
			<select name="query.type" style="width:200px;">
				<option value="0">私有</option>
				<option value="1">公共</option>
				<option value="2">默认</option>
			</select>
		</p>
		<div class="buttondiv">
			<input type="hidden" name="query.divid" value="${gridid}"/>
			<input type="hidden" id="advanced-hidden-queryrule" name="query.queryrule"/>
			<input type="hidden" id="advanced-hidden-orderrule" name="query.orderrule"/>
			<input type="hidden" id="advanced-hidden-queryrule" name="query.tablename" value="${name }"/>
		</div>
            <div class="buttonDetailBG" style="float: right">
                <input type="button" id="advanced-button-saveQuery" style="width: 35px;border-radius:5px;"  value="确定 "/>
            </div>
		</form>
	</div>
    <script type="text/javascript">
    	var rules = $("#${divID} #advanced-queryRule").val();
    	var orders = $("#${divID} #advanced-orderRule").val();
    	$(function(){
    		//查询数据
    		$("#${divID}-advanced-queryPage-query").click(function(){
    			var flag = $("#advanced-form-query").form("validate");
    			if(flag==false){
    				return false;
    			}
    			var orders = $("#${divID}-query").queryRule('getQueryOrder');
    			var rules = $("#${divID}-query").queryRule('getRules');
    			//$("#${divID} #advanced-unite").attr("value",rules);
    			$("#${divID}").advancedQuery("query",rules,JSON.stringify(orders));
    			$("#advanced-queryPage-${divID}").window('close');
    		});
    		//打开通用查询保存页面
    		$("#advanced-save").die("click").live("click",function(){
    			var rules = $("#${divID}-query").queryRule('getRules');
    			if(rules.length<15){
    				$.messager.alert("提醒","请先配置查询规则");
    				return false;
    			}
    			var order = $("#${divID}-query").queryRule('getQueryOrder');
    			$("#advanced-hidden-orderrule").attr("value",JSON.stringify(order));
    			$("#advanced-hidden-queryrule").attr("value",rules);
    			$("#advanced-query-savepage").dialog('open');
    		});
    		$("#advanced-form").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	//表单提交前 弹出提交等待页面
			    	loading();
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	//转为json对象
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	$.messager.alert("提醒","添加成功");
			        	$("#advanced-query-savepage").dialog('close',true);
			        	$("#advanced-history-query").combogrid();
			        }else{
			        	$.messager.alert("提醒","添加失败");
			        }
			    }  
			}); 
    		//保存通用查询条件
    		$("#advanced-button-saveQuery").click(function(){
    			$("#advanced-form").submit();
    		});
    		$("#advanced-delete").click(function(){
    			var id = $("#advanced-history-query").combogrid('getValue');
    			if(null!=id&&id!=""){
    				$.ajax({   
		            url :'common/deleteQuery.do?id='+id,
		            type:'post',
		            dataType:'json',
		            success:function(json){
		            	if(json.flag==true){
		            		$.messager.alert("提醒","删除成功！");
		            		$("#advanced-history-query").combogrid();
		            	}else{
		            		$.messager.alert("提醒","删除失败！");
		            	}
		            }
		        });
    			}
    		});
    	});
    	
    	setTimeout(function(){
    		if(rules!=""){
    			$("#${divID}-query").queryRule({
		    		rules:rules,
		    		orders:orders,
		    		name:'${name}',
                    opArray:'[{"equal": "相等"},{"notequal": "不相等"},{"in": "包括"},{"notin": "不包括"},{"like": "相似"},{"startwith": "以..开始"},'+
                    '{"equalCurr": "相等(当前条件)"},{"notequalCurr": "不相等(当前条件)"},{"startwithCurr": "以..开始(当前条件)"}]',
		    		query:true
		    	});
	   		}else{
	   			$("#${divID}-query").queryRule({
		    		rules:'${queryrule}',
		    		orders:'${orderrule}',
		    		name:'${name}',
                    opArray:'[{"equal": "相等"},{"notequal": "不相等"},{"in": "包括"},{"notin": "不包括"},{"like": "相似"},{"startwith": "以..开始"},'+
                    '{"equalCurr": "相等(当前条件)"},{"notequalCurr": "不相等(当前条件)"},{"startwithCurr": "以..开始(当前条件)"}]',
		    		query:true
		    	});
	   		}
	    	$("#advanced-history-query").combogrid({
	    		panelWidth:450,
	    		rownumbers:true,
	    		idField:'id',    
	    		textField:'queryname',
	    		title:'历史查询记录',
	    		columns:[[
							{field:'queryname',title:'查询名称',width:100},  
							{field:'description',title:'描述',width:150},
							{field:'type',title:'类别',width:100,
						        	formatter:function(val){
						        		if(val=='1'){
						        			return '公共';
						        		}else if(val=='0'){
						        			return '私有';
						        		}else if(val=='0'){
						        			return '默认';
						        		}
						        	}
						    }
						]],
				url:'common/showUserQuery.do?id=${gridid}',
				onSelect:function(rowIndex, rowData){
					$("#${divID}-query").queryRule({
			    		rules:rowData.queryrule,
			    		orders:rowData.orderrule,
			    		name:'${name }',
			    		query:true
			    	});
				},
				onLoadSuccess:function(data){
					if(null==rules || rules==""){
						var defaultflag = false;
						for(var i=0;i<data.rows.length;i++){
							if(data.rows[i].id=='${defaulQueryId}'){
								defaultflag = true;
								break;
							}
						}
						if(defaultflag){
							$("#advanced-history-query").combogrid("setValue",'${defaulQueryId}');
						}
					}
				}
	    	});
    	},50)
    </script>
  </body>
</html>
