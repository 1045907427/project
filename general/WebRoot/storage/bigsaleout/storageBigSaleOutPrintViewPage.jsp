<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>大单发货打印预览</title>
    <%@include file="/include.jsp"%>
    <style type="text/css">
    	.head{
    		font-size:30px;
    		align:center;
    		font-weight:bold;
    	}
    	.body{
    		font-size:20px;
    	}
    	.style{
    		font-size:20px;
    	}
    	.style1{
    		border-bottom-style: none;
    		border-top-style:none;
    		border-right-style:none;
    	}
    	.font18{
    		font-size:20px;
    	}
    	.font15{
    		font-size:15px;
    	}
		.fixed_div{
			position:fixed;
		}
	</style>
  </head>
  <body>
  	<div class="fixed_div">
		<input type="button" id="bigsaleout-button-print" value="打印" title="打印"/>
	</div>
  	<object classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" id="web" name="web">



  	<!-- 按商品分客户区块 -->
  	<div style="page-break-after: always;"></div>

  	</object>
  	<script type="text/javascript">
         //打印
         $("#bigsaleout-button-print").click(function(){
         	$.messager.confirm('提醒','确定打印?',function(r){
			    if (r){
			        document.getElementById("bigsaleout-button-print").style.display = "none";
         			window.print();
         			$.ajax({
			            url :'storage/updateBigSaleoutPrintNum.do',
			            data:{id:"${id }"},
			            type:'post',
			            dataType:'json',
			            error:function(){
			            	$.messager.alert("错误","打印出错");
			            }
			        });
			    }else{
			    	window.close();
			    }
			});
         });
  	</script>
  </body>
</html>
