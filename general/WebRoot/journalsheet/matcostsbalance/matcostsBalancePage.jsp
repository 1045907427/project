<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫收支情况页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  	<style type="text/css">
  	
  		.len70 {
  			width: 70px;
  		}
  		.len227 {
  			width: 227px;
  		}
  		.len240 {
  			width: 240px;
  		}
  		.len250 {
  			width: 250px;
  		}
  	
  	</style>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="journalsheet-panel-matcostsBalancePage">
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
	/*
	 * type:
	 *  list 列表，初期表示
	 *
	 *
	 *
	 */

	var type = '${param.type}';
	var url = 'journalsheet/matcostsbalance/matcostsBalanceListPage.do';
	
	//if(type == '') {
	
	//}

	$(function(){
	
		$panel.panel({
			href: url,
			cache: false,
			maximized: true,
			border: true,
			onLoad: function() {
			
			} // panel onLoad close
		});
	
	});
	
	var $panel = $('#journalsheet-panel-matcostsBalancePage');
	
	</script>
  </body>
</html>