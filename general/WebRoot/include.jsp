<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+(request.getServerPort() == 80 ? "" : ":" + request.getServerPort())+path+"/";
	String easyuiThemeName = "default";
	Cookie cookies[] = request.getCookies();
	if (cookies != null && cookies.length > 0) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("easyuiThemeName")) {
				easyuiThemeName = cookie.getValue();
				break;
			}
		}
	}
	//java.text.SimpleDateFormat includeDateFormatter=new java.text.SimpleDateFormat("yyyyMMdd");
	//String includeDateString=includeDateFormatter.format(new Date());
	
%>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
	<link rel="shortcut icon" type="image/x-icon" href="image/<%=easyuiThemeName%>/logo/logo.png" />
	<script type="text/javascript" src="<%=path %>/js/My97DatePicker/WdatePicker.js"></script>
	<base id="basePath" href="<%=basePath%>"/>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>   
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="js/themes/<%=easyuiThemeName%>/easyui.css" id="easyuiTheme"/>
	<link rel="stylesheet" href="css/<%=easyuiThemeName%>/main.css" type="text/css" id="easyuiTheme-menu"/>
	<link rel="stylesheet" href="js/ztree/zTreeStyle/zTreeStyle.css" type="text/css"/>
  	<script type="text/javascript" src="js/jquery-1.11.2.min.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="js/jquery.easyui.min.js" charset="UTF-8"></script>
  	<link rel="stylesheet" type="text/css" href="js/themes/icon.css" id="easyuiTheme"/>
	<%----合并JS文件注释掉的文件 表示合并到该文件中----%>
	<script type="text/javascript" src="js/easyui.tools.js" charset="UTF-8"></script>
  	<%--<script type="text/javascript" src="js/datagrid-groupview.js" charset="UTF-8"></script>--%>
  	<%--<script type="text/javascript" src="js/easyui-lang-zh_CN.js" charset="UTF-8"></script>--%>
	<%--<script type="text/javascript" src="js/easyui-validator.js" charset="UTF-8"></script> 	--%>
  	<script type="text/javascript" src="js/ztree/jquery.ztree.all-3.5.min.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="js/ztree/jquery.ztree.exhide-3.5.min.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="js/jqueryUtils.js?v=3" charset="UTF-8"></script>
	<script type="text/javascript" src="js/jquery.excel.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="js/jquery.upload.js" charset="UTF-8"></script>
	<script type="text/javascript" src="js/jquery.webuploader.js?v=20160303" charset="UTF-8"></script>
	<%----合并JS文件注释掉的文件 表示合并到该文件中----%>
	<script type="text/javascript" src="js/merge.js" charset="UTF-8"></script>
	<%--<script type="text/javascript" src="js/jquery.form.js" charset="UTF-8"></script>--%>
	<%--<script type="text/javascript" src="js/json2.js" charset="UTF-8"></script>--%>
	<%--<script type="text/javascript" src="js/jquery.cookie.js" charset="UTF-8"></script>--%>
	<%--<script type="text/javascript" src="js/md5.js" charset="UTF-8"></script>--%>
	<%--<script type="text/javascript" src="js/jquery.hotkeys.js" charset="UTF-8"></script>--%>
	<%--<script type="text/javascript" src="js/jquery.pinyin.js" charset="UTF-8"></script>--%>
    <script type="text/javascript" src="js/syscode.js?v=1" charset="UTF-8"></script>
	<script type="text/javascript" defer="defer">
        var easyuiThemeName = "<%=easyuiThemeName %>";
		var project_webrequest_path="<%=path %>";
        var erp_base_project_urlpath="<%=path %>";

  		//设置ajax请求通用处理
  		jQuery.ajaxSetup({
  			statusCode : {
  				900: function(){
  					alert("用户登录数量超过系统限制！");
    				top.location.href="login.do";
  				},
  				901: function(){
  					alert("未登录！");
    				top.location.href="login.do";
  				},
  				902 : function(){
  					alert("无权限操作！");
  				},
  				903 : function(){
  					alert("账号在其它地方登录!请注意账号安全!");
  					top.location.href="login.do";
  				}
  			},
  			error: function(jqXHR, textStatus, errorMsg){ // 出错时默认的处理函数
  				//console.log("出错了。"); //ie下会报错
  				if(window.console && console.log){
  					console.log("出错了。");
  				}
  		    }
  		});
  		
  		$(function(){
  			//自定义选项选择事件
			$(".sorttabs li").live("click",function(){ 
				var index = $(this).index();
				$(".sorttabs li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".sorttagsDiv .tagsDiv_item").hide().eq(index).show();
			});
			<%if(!"default1".equals(easyuiThemeName)){%>
  			//按钮效果补充
  			$(".button-list").live("mouseover",function(){
	 			var id = $(this).attr("id");
	 			if(!$("#"+id).hasClass("l-btn-disabled")){
	 				var options = $(this).attr("data-options");
		 			if(null!=options){
		 				var data = eval("("+"{"+options+"}"+")");
		 				$("#"+id+" .l-btn-left .l-btn-icon").removeClass(data.iconCls);
			 			$("#"+id+" .l-btn-left .l-btn-icon").addClass(data.iconCls+"1");
		 			}else{
		 				var iconcls = $(this).attr("iconcls");
		 				if(null!=iconcls && null!=""){
		 					$("#"+id+" .l-btn-left .l-btn-icon").removeClass(iconcls);
				 			$("#"+id+" .l-btn-left .l-btn-icon").addClass(iconcls+"1");
		 				}
		 			}
	 			}
	 		}).live("mouseout",function(){
	 			var id = $(this).attr("id");
	 			var options = $(this).attr("data-options");
	 			var iconcls = "";
	 			if(null!=options){
	 				var data = eval("("+"{"+options+"}"+")");
	 				iconcls = data.iconCls;
	 			}else{
	 				iconcls = $(this).attr("iconcls");
	 			}
	 			if($("#"+id+" .l-btn-left .l-btn-icon").hasClass(iconcls+"1")){
		 			$("#"+id+" .l-btn-left .l-btn-icon").removeClass(iconcls+"1");
		 			$("#"+id+" .l-btn-left .l-btn-icon").addClass(iconcls);
	 			}
	 		});
  			$(this).mousedown(function(event){
  				if (null!=event && !(event.target.id == 'main-bottom-div' || $(event.target).parents("#main-bottom-div").length>0
						||event.target.id == 'main-bottom' || $(event.target).parents("#main-bottom").length>0
						||event.target.id == 'group-user' || $(event.target).parents("#group-user").length>0)) {
					if(top.$("#main-bottom-div").hasClass("main-bottom-show")){
						top.$("#main-bottom-div").stop().animate({width: 'hide'}, 500);
						top.$("#main-bottom-div").removeClass("main-bottom-show");
						top.$("#main-tree-bottom-li").removeClass("main-tree-bottom-over");
					}
					top.$("#group-user").hide();
				}
  			}).bind("mousemove", function(event){
				if (!(event.target.id == 'main-tree2' || $(event.target).parents("#main-tree2").length>0
						||event.target.id == 'main-left' || $(event.target).parents("#main-left").length>0)) {
					top.$("#main-tree2").animate({width: 'hide'}, 500);
				}else if(event.target.id == 'main-left' || $(event.target).parents("#main-left").length>0){
					if(top.$("#main-tree2").is(":hidden")){
						top.$("#main-tree2").animate({width: 'show'}, 500);
					}
				}
				if((event.target.id == 'main-bottom-div' || $(event.target).parents("#main-bottom-div").length>0
						||event.target.id == 'main-bottom' || $(event.target).parents("#main-bottom").length>0)){
					if(!top.$("#main-bottom-div").hasClass("main-bottom-show")){
						top.$("#main-tree-bottom-li").addClass("main-tree-bottom-over");
	    				var Y = top.$('#main-bottom').offset().top; 
	           			var X = top.$('#main-bottom').width(); 
	           			var height = top.$('#main-bottom').height();
	           			top.$("#main-bottom-div").css({"top":Y + "px","left":X+ "px"});
	           			top.$("#main-bottom-div").stop().animate({width: 'show'}, 500);
	           			top.$("#main-bottom-div").addClass("main-bottom-show");
	    			}
				}
			});
  			<%}%>
  		});
  		
    	var async_ajaxContent = function (param, url) { //同步ajax
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false
		    });
		    return ajax.responseText;
		}

		//系统单据明细允许小数位位数
//		var decimallen = async_ajaxContent({},'common/getDecimalBillGoodsNumDecimalLen.do');
//		var decimallenjson = $.parseJSON(decimallen);
		var general_bill_decimallen = '<%=BillGoodsNumDecimalLenUtils.decimalLen %>';
		if(jQuery.isNumeric(general_bill_decimallen)){
		    general_bill_decimallen=Number(general_bill_decimallen);
		}else{
		    general_bill_decimallen=2;
		}

    	function validLength(len){ //只验证长度
    		$.extend($.fn.validatebox.defaults.rules, {
				validLength:{
			    	validator:function(value){
			    		var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量
			    		return reg.test(value);
			    	},
			    	message:'请输入'+len+'位字符!'
			    }
			});
    	}
    	//树形结构验证编号长度及重复
    	//len 验证的长度
    	//url 验证的地址
    	//id 验证的编号，该编号为父级编号，在方法中会用该编号加上本级编号做为编号去验证重复
    	//initValue 修改的时候有初始值，判断是否为初始值，是不进行重复验证，否则修改的时候会提醒初始值重复，这里是不需要验证的。
    	//message 提示
    	function validLengthAndUsedTree(len, url, id, initValue, message){ 
    		$.extend($.fn.validatebox.defaults.rules, {
				validLength:{
			    	validator:function(value){
			    		var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量 
	  					if(reg.test(value) == true){
	  						if(value == initValue){
	  							return true;
	  						}
				    		var data=async_ajaxContent({id:(id+value)},url);
		  					var json = $.parseJSON(data);
	    					if(json.flag == true){
		    					$.fn.validatebox.defaults.rules.validLength.message = message;
		    					return false;
		    				}else{
	    						return true;
		    				}
	    				}else{
	    					$.fn.validatebox.defaults.rules.validLength.message ='请输入'+len+'位字符!';
	    					return false;
	    				}
			    	},
			    	message:''
			    }
			});
    	}
	
	/**
	 * 根据编码类型和编码获取编码名称
	 */
	function getSysCodeName(type,code){
		var codeJson = top.codeJsonCache;
		if(codeJson!=null){
			var codes = codeJson[type];
			var codename = "";
			if(codes!=null){
				for(var i=0;i<codes.length;i++){
					if(code == codes[i].code){
						codename = codes[i].codename;
						break;
					}
				}
			}
			return codename;
		}else{
			return "";
		}
	}
	
	//键盘键Enter、Alt+R键控制查询和重置
    function controlQueryAndResetByKey(q,r){
    	if(q != "" || r != ""){
    		document.onkeydown = function(e){
    			var ev = e || window.event;
				if(undefined != ev.altKey){
					if(ev.altKey==true && ev.keyCode==81){//Alt+Q-查询
						if(q != ""){
							document.getElementById(q).click(); 
						}
					}
					else if(ev.altKey==true && ev.keyCode==82){//Alt+R-重置
						if(r != ""){
							document.getElementById(r).click();
						}
					}
				}
			}
    	}
    }
        //处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
        function forbidBackSpace(e) {
            var ev = e || window.event; //获取event对象 
            var obj = ev.target || ev.srcElement; //获取事件源 
            var t = obj.type || obj.getAttribute('type'); //获取事件源类型 
            //获取作为判断条件的事件类型 
            var vReadOnly = obj.readOnly;
            var vDisabled = obj.disabled;
            //处理undefined值情况 
            vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
            vDisabled = (vDisabled == undefined) ? true : vDisabled;
            //当敲Backspace键时，事件源类型为密码或单行、多行文本的， 
            //并且readOnly属性为true或disabled属性为true的，则退格键失效 
            var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
            //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效 
            var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
            //屏蔽滚轮中键按钮
            var flag3 = ev.button == 1;
            //判断 
            if (flag2 || flag1 || flag3) return false;
        }
        setTimeout(function(){
        	 //禁止后退键 作用于Firefox、Opera
            document.onkeypress = forbidBackSpace;
            //禁止后退键  作用于IE、Chrome
            document.onkeydown = forbidBackSpace;
            document.onmousedown = forbidBackSpace;
        },100);
        function addTab(url,name){
            if($('#tt').length>0){
                if(easyuiThemeName=="default"){
                    addTabDefault(url,name);
                }else if(easyuiThemeName=="default1"){
                    addTabDefault1(url,name);
                }else if(easyuiThemeName=="default2"){
					addTabDefault2(url,name);
				}
            }else{
                window.open(url);
            }
        }
        //添加或者更新tab页面
        function addOrUpdateTab(url,title){
            if($('#tt').length>0){
                if(!$('#tt').tabs('exists',title)){
                    addTab(url,title);
                }else{
                    updateTab(url,title);
                }
            }else{
                window.open(url);
            }

        }

</script>
