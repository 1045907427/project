<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>子菜单添加页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
      <form action="accesscontrol/addOperate.do" method="post" id="accesscontrol-form-addMenu">
	   	<div id="accesscontrol-menuAddPage" class="pageContent">
			<p>
				<label>菜单名称:</label>
				<input type="text" name="operate.operatename" class="easyui-validatebox" required="true" style="width:200px;"/>
			</p>
			<p>
				<label>菜单描述:</label>
				<input type="text" name="operate.description" class="easyui-validatebox" required="true" style="width:200px;"/>
			</p>
			<p>
				<label>链接地址:</label>
				<input type="text" name="operate.url" class="easyui-validatebox" required="true"  style="width:200px;"/>
			</p>
            <p>
                <label>数据权限:</label>
                <input type="text" id="accesscontrol-menu-tablename" name="operate.tablename"  style="width:200px;"/>
            </p>
			<p>
				<label>图片:</label>
				<input type="text" id="accesscontrol-menu-operateimage" name="operate.image" style="width:200px;" readonly="readonly"/>
				<a id="accesscontrol-menu-imageSelect" href="javaScript:void(0);">选择</a>
			</p>
            <p>
                <label>顺序:</label>
                <input type="text" name="operate.seq" class="easyui-numberbox" data-options="min:0,max:99999,required:true,missingMessage:'请输入0到99999之间的数字'" style="width:200px;"/>
            </p>
			<p>
				<label>父菜单:</label>
				<input type="text" id="accesscontrol-menuAdd-pid" class="easyui-validatebox" required="true"  style="width:200px;" name="operate.pid" value="${pid }" readonly="readonly"/>
			</p>
	    </div>
	    <input type="hidden" name="operate.type" value="0"/>
    </form>
    <div id="accesscontrol-menu-image" class="menuImage">
    	<ul id="accesscontrol-menu-image-ul"></ul>
	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#accesscontrol-menuAdd-pid").widget({
    			referwid:'RT_T_AC_OPERATE',
    			singleSelect:true,
    			onlyLeafCheck:false
    		});
            $("#accesscontrol-menu-tablename").widget({
                referwid:'RL_T_AC_DATARULE_TABLENAME',
                singleSelect:true,
                onlyLeafCheck:false
            });
     		$("#accesscontrol-form-addMenu").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    },  
			    success:function(data){
			    	//转为json对象
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	$.messager.alert("提醒","添加成功");
			        	$.fn.zTree.init($("#accesscontrol-tree-menu"), treeSetting,null);
			        	$("#accesscontrol-dialog-operate").dialog('close',true);
			        }else{
			        	$.messager.alert("提醒","添加失败");
			        }
			    }  
			}); 
			$("#accesscontrol-save-addMenu").click(function(){
				$.messager.confirm("提醒", "是否添加菜单?", function(r){
					if (r){
						$("#accesscontrol-form-addMenu").submit();
					}
				});
			});
			$("#accesscontrol-menu-imageSelect").click(function(){
				var  max= $("#accesscontrol-menu-operateimage").offset();
				var min = $("#accesscontrol-menuAddPage").offset();
				var left = max.left-min.left+5;
				var top = max.top-min.top+50;
				$("#accesscontrol-menu-image").css({left:left + "px", top:top + "px"}).slideDown("fast");
				$.ajax({   
		            url :'accesscontrol/showMenuImageList.do',
		            type:'post',
		            dataType:'json',
		            success:function(json){
		            	$("#accesscontrol-menu-image-ul").html("");
		            	for(var i=0;i<json.length;i++){
		            		var html = '<li><a href="javaScript:void(0);" image="'+json[i]+'"><img src="image/menu/'+json[i]+'"></a></li>';
		            		$("#accesscontrol-menu-image-ul").append(html);
		            	}
		            }
		        });
				
				$("#accesscontrol-menu-image").show();
				$("body").bind("mousedown", function(event){
					if (!(event.target.id == "accesscontrol-menu-operateimage" || event.target.id == "accesscontrol-menu-image" || $(event.target).parents("#accesscontrol-menu-image").length>0)) {
						$("#accesscontrol-menu-image").hide();
					}
				});
			});
			$("#accesscontrol-menu-image-ul li img").live("mouseover",function() {
				$(this).addClass("menuImageOn");
			});
			$("#accesscontrol-menu-image-ul li img").live("mouseout",function() {
				$("#accesscontrol-menu-image-ul li img").removeClass("menuImageOn");
			});
			$("#accesscontrol-menu-image-ul li a").live("click",function() {
				var image = $(this).attr("image");
				$("#accesscontrol-menu-operateimage").attr("value","image/menu/"+image);
				$("#accesscontrol-menu-image").hide();
			});
     	});
    </script>
  </body>
</html>
