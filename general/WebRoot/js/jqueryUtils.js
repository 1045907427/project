/**
 * 通用查询规则定义
 */
(function($){
	var methods = {
		init : function(options){
			defaults = {
				rules :"",
				orders:"",
				divID :"",
				tableid:"ruletable",
				num : 0,
				column: [],
				name:"",
				restype:"1",//资源类型1数据字典2参照窗口
				type:"",
				previd:"",
				scopetype:'1',		//针对全局还是针对用户 1全局2用户
				query:false,
				orderIndex:0,
				opArray:'[{"equal": "相等"},{"notequal": "不相等"},{"in": "包括"},{"notin": "不包括"},{"startwith": "以..开始"},'+
				'{"equalCurr": "相等(当前条件)"},{"notequalCurr": "不相等(当前条件)"},{"startwithCurr": "以..开始(当前条件)"}]',
				baseHtml: '<table id="ruletable" class="ruletable"><tr class="trlast"><td colspan="4"><select id="ruletable-group-op" style="width:150px;">'+
				'<option value="and">并且(满足全部条件)</option><option value="or" selected="selected">或者(满足其中一条)</option></select>&nbsp;'+
				'<input type="button" class="addGroup" style="background-color: white;border-radius:5px;"  value="增加分组" tableid="ruletable"/>&nbsp;'+
				'<input type="button" class="addCondition" style="background-color: white;border-radius:5px;"  value="增加条件" tableid="ruletable"/>'

			};
			options = $.extend(defaults,options);

			getColunmList = function(){
				var column = "";
				var url = "";
				if(options.restype=='1'){
					url = 'common/getTableColList.do?name='+options.name;
					if(options.query){
						url+='&usecolquery=1&userAccess=1';
					}else{
						url+='&usedataprivilege=1';
					}
				}else if(options.restype=='2'){
					url = 'system/referWindow/getReferWindowColumnList.do?id='+options.name;
				}
				if(options.name!=null&&options.name!=""){
					$.ajax({
						url :url,
						type:'post',
						dataType:'json',
						async: false,
						success:function(json){
							column = json;
						}
					});
				}
				return column;
			};
			options.column = getColunmList();

			setRules = function(json,id,divID){
				var rules = json.rules;
				var groups = json.groups;
				var op = json.op;
				if(rules!=null){
					$.each(rules, function(i, obj) {
						var field = obj.field;
						var op = obj.op;
						var value = obj.value;
						addConditionInfo("ruletable",obj);
					});
				}
				$("#"+divID+" #ruletable-group-op").val(op);
				setGroupsRule(groups,"ruletable",divID);
			};
			setGroupsRule = function(groupJson,id,divID){
				for(var i in groupJson){
					var newid = addGroup(id);
					for(var j in groupJson[i].rules){
						addConditionInfo(newid,groupJson[i].rules[j]);
					}
					$("#"+divID+" #"+newid+"-group-op").val(groupJson[i].op);
					if(groupJson[i].groups!=null){
						setGroupsRule(groupJson[i].groups,newid,divID)
					}

				}
			};
			//根据规则把json转化页面显示
			addGroup = function(id){
				options.num = Number(options.num)+Number(1);
				var newid = "ruletable"+options.num;
				var cssstr = "";
				if($("#"+options.divID+" #"+id).hasClass("ruletable1")){
					cssstr = "ruletable0"
				}else{
					cssstr = "ruletable1"
				}
				var str = "<tr id=\""+newid+"tr\"><td colspan=\"4\"><table id=\""+newid+"\" class=\""+cssstr+"\"><tr class=\"trlast\"><td colspan=\"4\">"+
					"<select id=\""+newid+"-group-op\" style=\"width:150px;\"><option value=\"and\">并且(满足全部条件)</option><option value=\"or\">或者(满足其中一条)</option></select>"+
					"<input type=\"button\" class=\"addGroup\" style=\"background-color: white;border-radius:5px;\"  value=\"增加分组\" tableid=\""+newid+"\"/>"+
					"<input type=\"button\" class=\"addCondition\" style=\"background-color: white;border-radius:5px;\"  value=\"增加条件\" tableid=\""+newid+"\"/>"+
					"<input type=\"button\" class=\"deleteGroup\" style=\"background-color: white;border-radius:5px;\"  value=\"删除分组\" tableid=\""+newid+"\"/>"+
					"<input type=\"hidden\" class=\""+id+"table\" value=\""+newid+"\"/></td></tr></table></td></tr>";
				$(str).insertBefore("#"+options.divID+" #"+id+" >tbody:first > tr:last");
				return newid;
			};
			//增加条件
			addCondition = function(id){
				options.num = Number(options.num)+Number(1);
				var newid = id+options.num;
				var str = "<tr id=\""+newid+"ctr\" class=\""+id+"ctr\">"+
					"<td><select class=\"ruleselect fieldselect "+id+"field\" id=\""+newid+"field\" parentid=\""+id+"\" tableid=\""+newid+"\" style=\"width:100px;\"></select></td>"+
					"<td><select class=\"ruleselect opselect "+id+"op\" id=\""+newid+"op\" parentid=\""+id+"\" tableid=\""+newid+"\" style=\"width:100px;\"></select></td>"+
					"<td id=\""+newid+"td\"><input type=\"text\" class=\"ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" style=\"width:280px;\" autocomplete=\"off\"/></td>"+
					"<td><input type=\"button\" class=\"deleteCondition\"  style=\"background-color: white;border-radius:5px;\" value=\"删除\" tableid=\""+newid+"\"/>"+
					"<input type=\"hidden\" class=\""+id+"cd\" value=\""+newid+"\"/></td></tr>";
				$(str).insertBefore("#"+options.divID+" #"+id+" >tbody:first > tr:last");
				addRuleSelect(newid,null);
			};
			addConditionInfo = function(id,json){
				options.num = Number(options.num)+Number(1);
				var newid = id+options.num;
				var str = "<tr id=\""+newid+"ctr\" class=\""+id+"ctr\">"+
					"<td><select class=\"ruleselect fieldselect "+id+"field\" id=\""+newid+"field\" parentid=\""+id+"\" tableid=\""+newid+"\" style=\"width:100px;\"></select></td>"+
					"<td><select class=\"ruleselect opselect "+id+"op\" id=\""+newid+"op\" parentid=\""+id+"\" tableid=\""+newid+"\" style=\"width:100px;\"></select></td>"+
					"<td id=\""+newid+"td\"><input type=\"text\" class=\"ruleselect "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" style=\"width:280px;\" autocomplete=\"off\"/></td>"+
					"<td><input type=\"button\" class=\"deleteCondition\" style=\"background-color: white;border-radius:5px;\" value=\"删除\" tableid=\""+newid+"\"/>"+
					"<input type=\"hidden\" class=\""+id+"cd\" value=\""+newid+"\"/></td></tr>";
				$(str).insertBefore("#"+options.divID+" #"+id+" >tbody:first > tr:last");
				addRuleSelect(newid,json);
			};
			//初始化条件的select控件
			addRuleSelect = function(id,json){
				initField(id,json);
				initOpSelect(id,json);
				intiValue(id,json);
			};
			//初始化opselect
			initOpSelect = function(id,opJson){
				var json = $.parseJSON(options.opArray);
				var opvalue = "";
				if(opJson!=null&&opJson.op!=null){
					opvalue = opJson.op
				}
				var str = "";
				$.each(json, function(i, obj) {
					var keystr = "";
					var value = "";
					$.each(obj, function(k, v) {
						keystr = k;
						value = v;
					});
					if(options.query && (keystr=="equalCurr" || keystr=="notequalCurr" || keystr=="startwithCurr" || keystr=="endwithCurr"|| keystr=="likeCurr")){
					}else{
						str+="<option value='"+keystr+"'>"+value+"</option>";
					}
				});
				$("#"+options.divID+" #"+id+"op").append(str);
				$("#"+options.divID+" #"+id+"op").val(opvalue);
			};
			initOp = function(id){
				var json = $.parseJSON(options.opArray);
				var str = "";
				$.each(json, function(i, obj) {
					var keystr = "";
					var value = "";
					$.each(obj, function(k, v) {
						keystr = k;
						value = v;
					});
					if(options.query && (keystr=="equalCurr" || keystr=="notequalCurr" || keystr=="startwithCurr" || keystr=="endwithCurr"|| keystr=="likeCurr")){
					}else{
						str+="<option value='"+keystr+"'>"+value+"</option>";
					}
				});
				$("#"+options.divID+" #"+id+"op").html(str);
			};
			intiValue = function(id,json){
				var value = "";
				var field = "";
				var op = "";
				if(json!=null){
					value = json.value;
					field =json.field;
					op = json.op;
					options.num = Number(options.num)+Number(1);
					var newid = id+options.num;
					switch(field){
						case "CurrentUserID":
							showUserSelectHtml(id,id,value);
							break;
						case "CurrentRoleID":
							showRoleSelectHmtl(id,id,value);
							break;
						case "CurrentDeptID":
							showDeptSelectHmtl(id,id,value);
							break;
						default:
							setColumnSelectHtml(id,id,value,json);
							break;
					}
				}else{
					var field = "";
					if(options.column.length>0){
						field = options.column[0].id;
					}else{
						field = $("#"+options.divID+" #"+id+"field").val();
					}
					switch(field){
						case "CurrentUserID":
							showUserSelectHtml(id,id,"");
							break;
						case "CurrentRoleID":
							showRoleSelectHmtl(id,id,"");
							break;
						case "CurrentDeptID":
							showDeptSelectHmtl(id,newid,"");
							break;
						default:
							setColumnSelectHtml(id,id,"",json);
							break;
					}
				}
			};
			//字段改变后
			fieldChange = function(id,newid){
				initOp(newid);
				var field = $("#"+options.divID+" #"+newid+"field").val();
				switch(field){
					case "CurrentUserID":
						showUserSelectHtml(id,newid,"");
						break;
					case "CurrentRoleID":
						showRoleSelectHmtl(id,newid,"");
						break;
					case "CurrentDeptID":
						showDeptSelectHmtl(id,newid,"");
						break;
					default:
						setColumnSelectHtml(id,newid,"",null);
						break;
				}
			};
			//条件改变后
			opChange = function(id,newid){
				var field = $("#"+options.divID+" #"+newid+"field").val();
				var datatype = "";
				for(var i=0;i<options.column.length;i++){
					if(options.column[i].id==field){
						datatype = options.column[i].datatype;
						break;
					}
				}
				var op = $("#"+options.divID+" #"+newid+"op").val();
				//当字段为日期类型时
				if(datatype=="datetime"||datatype=="timestamp"){
					//选择最近多少天或者最近多少月
					if(op=="nearday"||op=="nearmonth"){
						var html = "<input class=\"easyui-validatebox ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
							"data-options=\"required:true,validType:'number'\" style=\"width:300px;\"/>";
						$("#"+options.divID+" #"+newid+"td").html(html);
						$.parser.parse("#"+options.divID+" #"+newid+"td");
						if(op=="nearday"){
							$("#"+options.divID+" #"+newid+"value").attr("value","7");
						}else{
							$("#"+options.divID+" #"+newid+"value").attr("value","1");
						}
					}else if(op=="thismonth"){
						//选择当前月
						var html = "<input class=\"ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
							"required=\"true\" style=\"width:300px;\" value=\"当月\" readonly/>";
						$("#"+options.divID+" #"+newid+"td").html(html);
					}else if(op=="thisquarter"){
						//选择本季度
						var html = "<input class=\"ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
							"required=\"true\" style=\"width:300px;\" value=\"本季度\" readonly/>";
						$("#"+options.divID+" #"+newid+"td").html(html);
					}else{
						//其他情况
						var combobox = "<input class=\"easyui-validatebox ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
							"data-options=\"required:true,validType:'illegalChar'\" style=\"width:300px;\" onfocus=\"WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd'})\"/>";
						$("#"+options.divID+" #"+newid+"td").html(combobox);
						$.parser.parse("#"+options.divID+" #"+newid+"td");
					}
				}else{
					if(field!="CurrentUserID" && field!="CurrentRoleID" && field!="CurrentDeptID"){
						if(op=="equalCurr" || op=="notequalCurr" || op=="startwithCurr"){
							var inputValue = $("#"+newid+"value").val();
							var html = "<select class=\"ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
								"required=\"true\" style=\"width:300px;\"><option value=\"CurrentUserID\">当前用户</option><option value=\"CurrentDeptID\">当前部门</options></select>";
							$("#"+options.divID+" #"+newid+"td").html(html);
						}else{
							setColumnSelectHtml(id,newid,"",{op:op});
						}
					}
				}
			};
			//当字段来源资源本身时，根据情况设置条件
			setColumnSelectHtml = function(id,newid,value,json){
				var field = $("#"+options.divID+" #"+newid+"field").val();
				var datatype = "";
				var usecoded = "";
				var codedcoltype = "";
				var opvalue = "";
				if(json!=null){
					opvalue = json.op;
				}else{
					opvalue = "equal";
				}
				for(var i=0;i<options.column.length;i++){
					if(options.column[i].id==field){
						datatype = options.column[i].datatype;
						usecoded = options.column[i].usecoded;
						codedcoltype = options.column[i].codedcoltype;
						break;
					}
				}
				if(datatype!="datetime"&&datatype!="timestamp"){
					var combobox = "<input class=\" ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" style=\"width:280px;\" autocomplete=\"off\"/>";
					$("#"+options.divID+" #"+newid+"td").html(combobox);
					var widgetFlag = false;
					var treeFlag = false;
					if(opvalue=="equal" || opvalue=="notequal" || opvalue=="startwith" || opvalue=="endwith" || opvalue=="like"){
						widgetFlag = true;
					}
					if(opvalue=="equal" || opvalue=="notequal"){
						treeFlag = true;
					}
					if(usecoded=="1"){
						widgetFlag = true;
						treeFlag = true;
					}
					var disabled = false;
					var allSelect = true;
					if(options.type=="view"){
						disabled = true;
						allSelect = false;
					}
					if(opvalue!="equalCurr" && opvalue!="notequalCurr" && opvalue!="startwithCurr"){
						$("#"+newid+"value").widget({
							name:options.name,
							col:field,
							width:280,
							singleSelect:widgetFlag,
							onlyLeafCheck:treeFlag,
							required:true,
							initValue:value,
							listnum:300,
							view:true,
							disabled:disabled,
							allSelect:allSelect
						});
					}else{
						$("#"+newid+"value").attr("value",value);
						if(value=="CurrentUserID"){
							var html = "<select class=\"ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
								"required=\"true\" style=\"width:280px;\"><option value=\"CurrentUserID\" selected=\"selected\">当前用户</option><option value=\"CurrentDeptID\">当前部门</options></select>";
							$("#"+options.divID+" #"+newid+"td").html(html);
						}else if(value=="CurrentDeptID"){
							var html = "<select class=\"ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
								"required=\"true\" style=\"width:280px;\"><option value=\"CurrentUserID\">当前用户</option><option value=\"CurrentDeptID\" selected=\"selected\">当前部门</options></select>";
							$("#"+options.divID+" #"+newid+"td").html(html);
						}
					}

				}else{
					if(opvalue=="greater"||opvalue=="greaterorequal"||opvalue=="less"||opvalue=="lessorequal"){
						var combobox = "<input class=\"easyui-validatebox ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
							"data-options=\"required:true,validType:'illegalChar'\" style=\"width:300px;\" onfocus=\"WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd'})\"/>";
						$("#"+options.divID+" #"+newid+"td").html(combobox);
						$.parser.parse("#"+options.divID+" #"+newid+"td");
						$("#"+options.divID+" #"+id+"value").attr("value",value);
					}else if(opvalue=="nearday"||opvalue=="nearmonth"){
						var html = "<input class=\"easyui-validatebox ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
							"data-options=\"required:true,validType:'number'\" style=\"width:300px;\"/>";
						$("#"+options.divID+" #"+newid+"td").html(html);
						$.parser.parse("#"+options.divID+" #"+newid+"td");
						$("#"+options.divID+" #"+id+"value").attr("value",value);
					}else if(opvalue=="thismonth"||opvalue=="thisquarter"){
						var html = "<input class=\"ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
							"required=\"true\" style=\"width:300px;\" readonly/>";
						$("#"+options.divID+" #"+newid+"td").html(html);
						$("#"+options.divID+" #"+id+"value").attr("value",value);
					}else{
						var combobox = "<input class=\"easyui-validatebox ruleselect ruleinput "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" "+
							"data-options=\"required:true,validType:'illegalChar'\" style=\"width:300px;\" onfocus=\"WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd'})\"/>";
						$("#"+options.divID+" #"+newid+"td").html(combobox);
						$.parser.parse("#"+options.divID+" #"+newid+"td");
						$("#"+options.divID+" #"+id+"value").attr("value",value);
					}
					var str = '<option value="greaterorequal">大于等于(>=)</option>'+
						'<option value="lessorequal">小于等于(<=)</option>'+
						'<option value="nearday">最近多少天</option><option value="nearmonth">最近多少月</option>'+
						'<option value="thismonth">当月</option><option value="thisquarter">本季度</option>';
					$("#"+options.divID+" #"+newid+"op").html(str);
					if(opvalue!=""){
						$("#"+options.divID+" #"+newid+"op").attr("value",opvalue);
					}
				}
			};
			setSelectWidget = function(id,newid,value,referid){
				var combobox = "<input class=\"easyui-combobox ruleselect "+id+"value\" id=\""+newid+"value\" name=\""+newid+"value\" style=\"width:280px;\" />";
				$("#"+options.divID+" #"+newid+"td").html(combobox);
				var disFlag = false;
				var allSelectFlag = true;
				if(options.type=="view"){
					disFlag = true;
					allSelectFlag = false;
				}
				$("#"+options.divID+" #"+newid+"value").widget({
					referwid:referid,
					width:280,
					singleSelect:false,
					onlyLeafCheck:false,
					initValue:value,
					disabled:disFlag,
					allSelect:allSelectFlag
				});
				$("#"+options.divID+" #"+newid+"op option[value='equalCurr']").remove();
				$("#"+options.divID+" #"+newid+"op option[value='notequalCurr']").remove();
				$("#"+options.divID+" #"+newid+"op option[value='startwithCurr']").remove();
			};
			//显示用户combobox控件
			showUserSelectHtml  = function(id,newid,value){
				setSelectWidget(id,newid,value,"RL_T_SYS_USER");
			};
			//显示角色combobox控件
			showRoleSelectHmtl = function (id,newid,value){
				setSelectWidget(id,newid,value,"RL_T_AC_AUTHORITY");
			};
			//显示部门combobox控件
			showDeptSelectHmtl = function (id,newid,value){
				setSelectWidget(id,newid,value,"RT_T_SYS_DEPT");
			};
			//初始化字段
			initField = function(id,json){
				var str = "";
				var field = "";
				if(json!=null&&json.field!=null){
					field = json.field
				}
				$.each(options.column, function(i, obj) {
					str+="<option value='"+obj.id+"'>"+obj.name+"</option>";
				});
				if(!options.query && options.scopetype=='1'){
					str += "<option value='CurrentUserID'>{当前用户}</option><option value='CurrentRoleID'>{当前角色}</option><option value='CurrentDeptID'>{当前部门}</option>";
				}
				$("#"+options.divID+" #"+id+"field").append(str);
				$("#"+options.divID+" #"+id+"field").val(field);
			};
			//初始化排序条件
			initOrders = function(){
				var orderRules = $.parseJSON(options.orders);
				for(var i=0;i<orderRules.length;i++){
					var orderHtml = '<div id="order-div'+options.orderIndex+'"><select id="query-select'+options.orderIndex+'" style="width:100px;" radioid="radio'+options.orderIndex+'" class="querySelect">';
					var orderArr = new Array();
					$("#"+options.divID+" .querySelect").each(function(){
						var field = $(this).val();
						orderArr.push(field);
					});
					var selectoptions = "";
					$.each(options.column, function(i, obj) {
						var flag = true;
						for(var i=0;i<orderArr.length;i++){
							if(obj.id==orderArr[i]){
								flag = false;
								break;
							}
						}
						if(flag){
							if(obj.id==orderRules[i].field){
								selectoptions += "<option value='"+obj.id+"' selected='selected'>"+obj.name+"</option>";
							}else{
								selectoptions += "<option value='"+obj.id+"'>"+obj.name+"</option>";
							}

						}
					});
					if(selectoptions==""){
						$("#"+options.divID+" #addQueryOrder").attr("disabled","disabled");
						return false;
					}
					if(orderRules[i].order=="asc"){
						orderHtml += selectoptions +"</select><input type='radio' id='radio-input"+options.orderIndex+"' name='radio"+options.orderIndex+"' value='asc' checked='checked'/>升序<input type='radio' name='radio"+options.orderIndex+"' value='desc'/>降序";
					}else if(orderRules[i].order=="desc"){
						orderHtml += selectoptions +"</select><input type='radio' id='radio-input"+options.orderIndex+"' name='radio"+options.orderIndex+"' value='asc'/>升序<input type='radio' name='radio"+options.orderIndex+"' value='desc' checked='checked'/>降序";
					}
					orderHtml += "<input type='button' divid='order-div"+options.orderIndex+"' class='orderDelete' style='background-color: white;border-radius:5px;'  value='删除'/></div>";
					$("#"+options.divID+" #queryOrder").append(orderHtml);
					options.orderIndex = options.orderIndex+1;
				}
			};
			this.each(function() {
				var divObject = $(this);
				options.divID = $(this).attr("id");
				if(options.rules!=null&&options.rules!=""){
					var html = options.baseHtml;
					if(options.query){
						html += '</td></tr></table>';
						html +='<div style="margin: 3px 5px 3px 5px;"><span style="font-weight: bold;font-size:14px;">排序:</span><div id="queryOrder"></div><div><input id="addQueryOrder" type="button" style="background-color: white;border-radius:5px;" value="添加排序 "/></div></div>';
					}else{
						if(options.type!='view'){
							'</td></tr></table>';
						}
					}
					divObject.html(html);
					var json = $.parseJSON(options.rules);
					setRules(json,"ruletable",options.divID);
				}else{
					var html = options.baseHtml;
					if(options.query){
						html += '</td></tr></table>';
						html +='<div style="margin: 3px 5px 3px 5px;"><span style="font-weight: bold;font-size:14px;">排序:</span><div id="queryOrder"></div><div><input id="addQueryOrder" type="button" style="background-color: white;border-radius:5px;"  value="添加排序 "/></div></div>';
					}else{
						'</td></tr></table>';
					}
					divObject.html(html);
				}
				if(options.orders!=null&&options.orders!=""){
					initOrders();
				}
				//先移除绑定的事件
				divObject.find(".addGroup").die();
				divObject.find(".addCondition").die();
				divObject.find(".deleteGroup").die();
				divObject.find(".deleteCondition").die();
				divObject.find(".fieldselect").die();

				if(options.query){
					divObject.find("#addQueryOrder").live("click",function(){
						var orderHtml = '<div id="order-div'+options.orderIndex+'"><select id="query-select'+options.orderIndex+'" style="width:100px;" radioid="radio'+options.orderIndex+'" class="querySelect">';
						var orderArr = new Array();
						$("#"+options.divID+" .querySelect").each(function(){
							var field = $(this).val();
							orderArr.push(field);
						});
						var selectoptions = "";
						$.each(options.column, function(i, obj) {
							var flag = true;
							for(var i=0;i<orderArr.length;i++){
								if(obj.id==orderArr[i]){
									flag = false;
									break;
								}
							}
							if(flag){
								selectoptions += "<option value='"+obj.id+"'>"+obj.name+"</option>";
							}
						});
						if(selectoptions==""){
							$("#"+options.divID+" #addQueryOrder").attr("disabled","disabled");
							return false;
						}
						orderHtml += selectoptions +"</select><input type='radio' id='radio-input"+options.orderIndex+"' name='radio"+options.orderIndex+"' value='asc' checked='checked'/>升序<input type='radio' name='radio"+options.orderIndex+"' value='desc'/>降序";
						orderHtml += "<input type='button' divid='order-div"+options.orderIndex+"' class='orderDelete' style='background-color: white;border-radius:5px;' value='删除'/></div>";
						$("#"+options.divID+" #queryOrder").append(orderHtml);
						options.orderIndex = options.orderIndex+1;
					});
					$(".orderDelete").live("click",function(){
						var divid = $(this).attr("divid");
						$("#"+options.divID+" #addQueryOrder").attr("disabled",false);
						$("#"+divid).remove();
					});
				}

				//绑定增加分组事件
				divObject.find(".addGroup").live("click",function(){
					var id = $(this).attr("tableid");
					addGroup(id);
				});
				//绑定增加条件事件
				divObject.find(".addCondition").live("click",function(){
					var id = $(this).attr("tableid");
					addCondition(id);
				});
				//绑定删除分组事件
				divObject.find(".deleteGroup").live("click",function(){
					var id = $(this).attr("tableid");
					$("#"+options.divID+" #"+id+"tr").remove();
				});
				//绑定删除条件事件
				divObject.find(".deleteCondition").live("click",function(){
					var id = $(this).attr("tableid");
					$("#"+options.divID+" #"+id+"ctr").remove();
				});
				divObject.find(".fieldselect").live("change",function(){
					var id = $(this).attr("parentid");
					var newid = $(this).attr("tableid");
					fieldChange(id,newid);
				});
				divObject.find(".opselect").live("change",function(){
					var id = $(this).attr("parentid");
					var newid = $(this).attr("tableid");
					opChange(id,newid);
				});
				divObject.find(".ruleinput").live("focus",function(){
					var id = $(this).attr("id");
					options.previd=id;
				});
				//判断是否显示模式
				if(options.type=='view'){
					$("#"+options.divID+" input").attr("disabled","disabled");
					$("#"+options.divID+" select").attr("disabled","disabled");
					$("#"+options.divID+" :button").hide();
				}
			});
		},
		getRules : function(){
			var divID =  $(this).attr("id");
			return getRuleJson(divID,"ruletable");
		},
		getQueryOrder: function(){
			var divID =  $(this).attr("id");
			var orderSelect = $(".querySelect");
			var orderArr = new Array();
			if(orderSelect.length>0){
				$("#"+divID+" .querySelect").each(function(){
					var radioid = $(this).attr("radioid");
					var field = $(this).val();
					var rval = $("#"+divID+" input:radio[name='"+radioid+"']:checked").val();
					orderArr.push({field:field,order:rval});
				});
			}
			return orderArr;
		}
	};
	$.fn.queryRule = function( method ) {
		if ( methods[method] ) {
			return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
		} else if ( typeof method === 'object' || ! method ) {
			return methods.init.apply( this, arguments );
		} else {
			$.error( 'Method ' +  method + ' does not exist on jQuery.tooltip' );
		}
	};
	var getRuleJson = function(divID,tableid){
		if(tableid==null||tableid==""){
			tableid = "ruletable";
		}
		var opstr = $("#"+divID+" #"+tableid+"-group-op").val();
		var rules = new Array();
		$("#"+divID+" ."+tableid+"cd").each(function(){
			var id = $(this).val();
			var field = $("#"+divID+" #"+id+"field").val();
			var op = $("#"+divID+" #"+id+"op").val();
			var value = null;
			//获取input里的value值
			$("#"+divID+" input[name='"+id+"value']").each(function(){
				if(value!=null){
					value=value+","+ $(this).val();
				}else{
					value=$(this).val();
				}
			});
			//当input中的value值为Null时，取select的值
			if(null==value){
				value = $("#"+divID+" select[name='"+id+"value']").val();
			}
			var rule ='{"field":"'+field+'","op":"'+op+'","value":"'+value+'"}';
			rules.push(rule);
		});
		var groupRules = new Array();
		$("#"+divID+" ."+tableid+"table").each(function(){
			var groupid = $(this).val();
			//递归调用
			var groups = getRuleJson(divID,groupid);
			groupRules.push(groups);
		});
		var ruleJson = "";
		if(rules.length>0){
			ruleJson = '{"rules":['+rules+'],';
		}else{
			ruleJson = '{';
		}
		if(groupRules.length>0){
			ruleJson +='"groups":['+groupRules+'],"op":"'+opstr+'"}';
		}else{
			ruleJson += '"op":"'+opstr+'"}';
		}
		return ruleJson;
	}
})(jQuery);
/**
 * 通用查询控件
 */
(function($){
	var methods = {
		init : function(options){
			defaults = {
				inputname:'queryRules',
				name:'',
				datagrid:'',
				divID:'',
				plain:true,				//按钮显示风格
				gridModel:'datagrid'		//datagrid/treegrid两种表格
			};
			options = $.extend(defaults,options);

			this.each(function() {
				var divObject = $(this);
				options.divID = $(this).attr("id");
				$("#"+options.divID).data("gridModel",options.gridModel);
				var html = '<a href="javaScript:void(0);" id="button-commonquery-ad" class="easyui-linkbutton button-list advancedQuery" data-options="plain:true,iconCls:\'button-commonquery\'" >通用查询</a>'+
					'<input type="hidden" id="advanced-datagrid" value="'+options.datagrid+'"/>'+
					'<input type="hidden" id="advanced-queryRule" /><input type="hidden" id="advanced-orderRule"/><div id="advanced-queryPage-'+options.divID+'"></div>';
				divObject.html(html);
				divObject.find(".advancedQuery").live("click",function(){
					var url = "common/showAdvancedQueryPage.do?name="+options.name+"&divID="+options.divID+"&gridid="+options.datagrid;
					$("#advanced-queryPage-"+options.divID).window({
						title: '通用查询',
						width: 650,
						height: 400,
						closed: false,
						cache: false,
						href: url,
						modal: true,
						collapsible:false,
						minimizable:false,
						onClose :function(){
							$("#advanced-query-savepage").dialog("destroy");
							$(this).window("destroy");
							divObject.append('<div id="advanced-queryPage-'+options.divID+'"></div>');
						}
					});
				});
				$.parser.parse('#'+options.divID);
			});
		},
		query : function(rules,orders){
			var datagrid = $(this).find("#advanced-datagrid").val();
			$(this).find("#advanced-queryRule").attr("value",rules);
			$(this).find("#advanced-orderRule").attr("value",orders);
			var gridModel =$(this).data("gridModel");
			if(gridModel=="datagrid"){
				$("#"+datagrid).datagrid("load",{queryRules:rules,orderRules:orders});
			}else if(gridModel=="treegrid"){
				$("#"+datagrid).treegrid({queryParams:{queryRules:rules,orderRules:orders}});
			}

		}
	};
	$.fn.advancedQuery = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(
				arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.tooltip');
		}
	}
})(jQuery);

/**
 * 商品选择控件
 */
(function($){
	var methods = {
		init : function(options){
			defaults = {
				required:false,
				param:null,
				storageid:'',
				isHiddenUsenum:false,
				isPromotion:false,          //是否显示促销买赠捆绑商品
				customerid:'',              //客户编号 显示促销时才生效
				isShowAll:false,			//是否显示全部启用禁用数据	true显示启用禁用数据 false只显示启用数据
				onSelect:function(data){},
				onClear:function(){},
                distribution: false         // 是否根据分销规则过滤商品
			};
			options = $.extend(defaults,options);
			this.each(function() {
				var id = $(this).attr("id");
				var name = $(this).attr("name");
				var text = $(this).attr("text");
				var initvalue = $(this).attr("value");
				var readonly = $(this).attr("readonly");
				var storageid = options.storageid;
				var queryAllBySupplier = options.queryAllBySupplier;
				var canBuySale = options.canBuySale;
				var isShowAll = "2";
				var isPromotion = '';
				var customerid = '';
				var showWidth = 680;
				var showShopFlag = true;
                var distribution = options.distribution;
				if(options.isShowAll){
					isShowAll = "1";
				}else{
					isShowAll = "2";
				}
				if(options.isPromotion){
					isPromotion = "1";
				}
				if(options.customerid!=null && options.customerid!=""){
					customerid = options.customerid;
					showShopFlag = false;
					showWidth = 750;
				}
				if(!name){
					name="";
				}
				if(!initvalue){
					initvalue = "";
				}
				if(!text){
					text = "";
				}
				var width = $(this).width();
				if(readonly=="readonly"){
					width = width+20;
				}
				$("#"+id).replaceWith('<span class="'+id+'class widgetinput"><input type="text" id="'+id+'" class="searchbox-text" style="width:'+(width-20)+'px; height: 20px; line-height: 18px;" value="'+text+'" getValueType="combogrid"><span><span class="searchbox-button-widget" id="'+id+'-search" style="height: 20px;float: right;"></span><span class="searchbox-clear" id="'+id+'-clear" style="width:20px;float: right;display: none;cursor: pointer;"></span></span></span>');
				$("#"+id).attr("value",text);
				$("#"+id).attr("autocomplete","off");
				$("#"+id+"-hidden").remove();
				$("#"+id).after('<input type="hidden" id="'+id+'-hidden" name="'+name+'" object="" value="'+initvalue+'"/>');
				//$("#"+id).after('<a id="'+id+'-search" title="查询" style="cursor: pointer;"><img src="image/search.png" style="border: none;vertical-align:middle;"/></a>');
				if(options.required==true){
					$("#"+id).validatebox({tipPosition:'top',required:true,validType:'widgetRequired["'+id+'-hidden"]'});
				}else{
					$("#"+id).validatebox({tipPosition:'top',validType:'widgetRequired["'+id+'-hidden"]'});
				}
				var paramRule = "";
				if(null!=options.param){
					paramRule = JSON.stringify(options.param);
				}
				var rand1 = Math.ceil(Math.random()*100000);
				var divid1 = id+"-search-div"+rand1;
				$("body").append('<div id="'+divid1+'-table" class="pulldown-div" style="width:'+showWidth+'px;"><table id="'+divid1+'"></table></div>');
				$("#"+divid1).datagrid({
					columns:[[
						{field:'id',title:'编码',width:65,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'name',title:'名称',width:180,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									if(keyvalue.indexOf(" ")>=0){
										var keyArr = keyvalue.split(" ");
										for(var i=0;i<keyArr.length;i++){
											var keyVal = keyArr[i];
											keyVal = keyVal.replace(" ","");
											var str1 = '<span style="color:red;">'+keyVal+'</span>';
											val = val.replace(keyVal,str1);
										}
									}else{
										var str = '<span style="color:red;">'+keyvalue+'</span>';
										val = val.replace(keyvalue,str);
									}
									if(rowData.ptype=='0' || rowData.ptype==null){
										return val;
									}else if(rowData.ptype=='1'){
										return "<font color='blue'>买赠&nbsp;</font>"+val;
									}else if(rowData.ptype=='2'){
										return "<font color='blue'>捆绑&nbsp;</font>"+val;
									}
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'brand',title:'商品品牌',width:55,
							formatter:function(val,rowData,rowIndex){
								return rowData.brandName;
							}
						},
						{field:'spell',title:'助记符',width:60,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.toLowerCase();
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'barcode',title:'条形码',width:90,resizable:true,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'shopid',title:'店内码',width:50,hidden:showShopFlag,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}},
						{field:'model',title:'规格型号',width:60,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}},
						{field:'mainunit',title:'单位',width:35,
							formatter:function(val,rowData,rowIndex){
								return rowData.mainunitName;
							}
						},
						{field:'boxnum',title:'箱装量',width:45,align:'right',
							formatter:function(val,rowData,rowIndex){
								return formatterBigNumNoLen(val);
							}
						},
						{field:'newinventory',title:'可用量',width:45,align:'right',hidden:options.isHiddenUsenum,
							formatter:function(value,rowData,rowIndex){
								return formatterBigNumNoLen(value);
							}
						}
					]],
					width:showWidth,
					height:320,
					method:'post',
					rownumbers:true,
					pagination:true,
					idField:'id',
					singleSelect:true,
					data:null,
					pageSize:50,
					pageList:[10,20,30,50,100],
					onLoadSuccess:function(data){
						var keytype = $("#"+id).data("keytype");
						if(keytype==null){
							keytype = "down";
						}
						if(data.rows.length>0){
							var nowvalue = $("#"+id+"-hidden").val();
							if(data.rows.length==1 && (nowvalue==null || nowvalue=="")){
								var objectStr = JSON.stringify(data.rows[0]);
								$("#"+id).val(data.rows[0].name);
								$("#"+id).attr("title",data.rows[0].name);
								$("#"+id+"-hidden").val(data.rows[0].id);
								$("#"+id+"-hidden").attr("object",objectStr);
								options.onSelect(data.rows[0]);

								$("#"+id).validatebox("validate");
								$("#"+divid1+"-table").hide();
								return false;
							}
							if(keytype=="down"){
								$("#"+divid1).datagrid("selectRow",0);
							}else{
								$("#"+divid1).datagrid("selectRow",data.rows.length-1);
							}
						}
						$("#"+id).focus();
					},
					onClickRow:function(rowIndex, rowData){
						var objectStr = JSON.stringify(rowData);
						$("#"+id).val(rowData.name);
						$("#"+id).attr("title",rowData.name);
						$("#"+id+"-hidden").val(rowData.id);
						$("#"+id+"-hidden").attr("object",objectStr);
						options.onSelect(rowData);
						$("#"+id).validatebox("validate");
						$("#"+divid1+"-table").hide();
					}
				});
				$("#"+id+"-clear").click(function(){
					$("#"+id).val("");
					$("#"+id+"-hidden").val("");
					$("#"+id+"-hidden").attr("object","");
					options.onClear();
				});
				if(readonly!="readonly"){
					var initwidthlen = $("#"+id).width();
					$("."+id+"class").mouseover(function(){
						var readonlyStr = $("#"+id).attr("readonly");
						if(readonlyStr =='readonly' || readonlyStr==true){
							return false;
						}
						$("#"+id).width(initwidthlen-20);
						$("#"+id+"-clear").show();
					}).mouseout(function(){
						var vobject = $(this).find(".validatebox-invalid");
						if(vobject.length==0){
							$("#"+id).width(initwidthlen);
							$("#"+id+"-clear").hide();
						}
					});
				}else{
					$("#"+id).attr("readonly","readonly");
				}
				$("#"+id).focus(function(){
					$("#"+id).die("keydown").live("keydown",function(event){
						switch(event.keyCode){
							case 13: //Enter
								var oldvalue = $("#"+id).data("oldvalue");
								var value = $("#"+id).val();
								$("#"+id).data("oldvalue",value);
								if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
									var readonly = $(event.target).attr("readonly");
									if($(event.target).attr("id")==id && readonly!="readonly"){
										if($("#"+id+"-hidden").val()!=""){
											value = $("#"+id+"-hidden").val();
										}
										$("#"+divid1).datagrid({
											url:'basefiles/getGoodsSelectListData.do',
											pageNumber:1,
											queryParams:{id:value,paramRule:paramRule,storageid:storageid,queryAllBySupplier:queryAllBySupplier,canBuySale:canBuySale,isPromotion:isPromotion,customerid:customerid,isShowAll:isShowAll,distribution:distribution}
										});
										$("#"+id).data("keytype","down");
										var max= $("#"+id).offset();
										var left = Number(max.left);
										var height = $("#"+id).height();
										var top = max.top+height+2;
										$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
										$("body").bind("mousedown", function(event){
											if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
												$("#"+divid1+"-table").hide();
											}
										});
									}
								}else{
									var object = $("#"+divid1).datagrid("getSelected");
									var objectStr = JSON.stringify(object);
									if(null!=object){
										$("#"+id).val(object.name);
										$("#"+id).attr("title",object.name);
										$("#"+id+"-hidden").val(object.id);
									}
									$("#"+id+"-hidden").attr("object",objectStr);
									options.onSelect(object);
									$("#"+id).validatebox("validate");
									$("#"+divid1+"-table").hide();
									return false;
								}
								break;
							case 8:	//backspace
								//$(event.target).val("")
								$("#"+id+"-hidden").val("");
								$("#"+id).attr("title","");
								$("#"+id+"-hidden").attr("object","");
								options.onClear();
								break;
							case 38:// 上
								var datagridObject =  $("#"+divid1);
								var rowSelected =datagridObject.datagrid("getSelected");
								var rowIndex = 0;
								if(null!=rowSelected){
									rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
									var rows =  datagridObject.datagrid("getRows").length;
									if(rowIndex>0){
										rowIndex = rowIndex-1;
										datagridObject.datagrid("selectRow",rowIndex);
									}else{
										var p = datagridObject.datagrid('getPager');
										var pageObject = $(p).pagination("options");
										if(pageObject.pageNumber>1){
											$(p).pagination("select",pageObject.pageNumber-1);
											$("#"+id).data("keytype","up");
										}
									}
								}else{
									datagridObject.datagrid("selectRow",0);
								}
								break;
							case 40:// 下
								var datagridObject =  $("#"+divid1);
								var rowSelected =datagridObject.datagrid("getSelected");
								var rowIndex = 0;
								if(null!=rowSelected){
									rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
									var rows =  datagridObject.datagrid("getRows").length;
									if(rowIndex<rows-1){
										rowIndex = rowIndex+1;
										datagridObject.datagrid("selectRow",rowIndex);
									}else{
										var p = datagridObject.datagrid('getPager');
										var pageObject = $(p).pagination("options");
										var nums = pageObject.total%pageObject.pageSize;
										var pages = 0;
										if(nums>0){
											pages = (pageObject.total-nums)/pageObject.pageSize+1;
										}else{
											pages = (pageObject.total-nums)/pageObject.pageSize;
										}
										if(pageObject.pageNumber<pages){
											$(p).pagination("select",pageObject.pageNumber+1);
											$("#"+id).data("keytype","down");
										}
									}
								}else{
									datagridObject.datagrid("selectRow",0);
								}
								break;
							case 37:  //左
								var p = $("#"+divid1).datagrid('getPager');
								var pageObject = $(p).pagination("options");
								if(pageObject.pageNumber>1){
									$(p).pagination("select",pageObject.pageNumber-1);
									$("#"+id).data("keytype","up");
								}
								break;
							case 39://右
								var p = $("#"+divid1).datagrid('getPager');
								var pageObject = $(p).pagination("options");
								var nums = pageObject.total%pageObject.pageSize;
								var pages = 0;
								if(nums>0){
									pages = (pageObject.total-nums)/pageObject.pageSize+1;
								}else{
									pages = (pageObject.total-nums)/pageObject.pageSize;
								}
								if(pageObject.pageNumber<pages){
									$(p).pagination("select",pageObject.pageNumber+1);
									$("#"+id).data("keytype","down");
								}
								break;
							default:
								if(event.ctrlKey==1 && (event.keyCode==67 || event.keyCode==17)){
								}else{
									$("#"+id+"-hidden").val("");
									$("#"+id).attr("title","");
									$("#"+id+"-hidden").attr("object","");
									//options.onClear();
								}
						}
					});
				}).blur(function(){
					$("#"+id).die("keydown");
				});
				$("#"+id+"-search").click(function(event){
					var oldvalue = $("#"+id).data("oldvalue");
					var value = $("#"+id).val();
					$("#"+id).data("oldvalue",value);
					if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
						if($("#"+id+"-hidden").val()!=""){
							value = $("#"+id+"-hidden").val();
						}
						$("#"+divid1).datagrid({
							url:'basefiles/getGoodsSelectListData.do',
							pageNumber:1,
							queryParams:{id:value,paramRule:paramRule,storageid:storageid,queryAllBySupplier:queryAllBySupplier,canBuySale:canBuySale,isPromotion:isPromotion,customerid:customerid,isShowAll:isShowAll,distribution:distribution}
						});
						$("#"+id).data("keytype","down");
						var max= $("#"+id).offset();
						var left = Number(max.left);
						var height = $("#"+id).height();
						var top = max.top+height+2;
						$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
						$("body").bind("mousedown", function(event){
							if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
								$("#"+divid1+"-table").hide();
							}
						});
					}else{
						var object = $("#"+divid1).datagrid("getSelected");
						var objectStr = JSON.stringify(object);
						$("#"+id).val(object.name);
						$("#"+id).attr("title",object.name);
						$("#"+id+"-hidden").val(object.id);
						$("#"+id+"-hidden").attr("object",objectStr);
						options.onSelect(object);
						$("#"+id).validatebox("validate");
						$("#"+divid1+"-table").hide();
					}
				});
				$("#"+id).data("onSelect",options.onSelect);
				if(readonly=="readonly"){
					$("#"+id+"-search").hide();
				}
			});
		},
		getText : function(){
			var id =  $(this).attr("id");
			return $("#"+id).val();
		},
		getValue : function(){
			var id =  $(this).attr("id");
			return $("#"+id+"-hidden").val();
		},
		setValue:function(value){
			var id =  $(this).attr("id");
			$("#"+id+"-hidden").val(value)
		},
		setText:function(value){
			var id =  $(this).attr("id");
			return $("#"+id).val(value);
		},
		getObject:function(){
			var id =  $(this).attr("id");
			var objectStr = $("#"+id+"-hidden").attr("object");
			var object = $.parseJSON(objectStr);
			return object;
		},
		clear:function(){
			var id =  $(this).attr("id");
			$("#"+id).val("");
			$("#"+id+"-hidden").val("");
			$("#"+id+"-hidden").attr("object","");
		},
		disable:function(){
			var id =  $(this).attr("id");
			$("#"+id).attr("disabled",true);
			$("#"+id+"-hidden").attr("disabled",true);
			$("#"+id+"-search").hide();
		},
		enable:function(){
			var id =  $(this).attr("id");
			$("#"+id).attr("disabled",false);
			$("#"+id+"-hidden").attr("disabled",false);
			$("#"+id+"-search").show();
		},
		readonly:function(flag){
			var id =  $(this).attr("id");
			$("#"+id).attr("readonly",flag);
			if(flag){
				$("#"+id+"-search").hide();
			}else{
				$("#"+id+"-search").show();
			}
		}
	};
	$.fn.goodsWidget = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(
				arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.tooltip');
		}
	}
})(jQuery);
/**
 * 仓库下商品选择控件
 */
(function($){
	var methods = {
		init : function(options){
			defaults = {
				required:false,
				param:null,
				onSelect:function(data){},
				onClear:function(){}
			};
			options = $.extend(defaults,options);
			this.each(function() {
				var id = $(this).attr("id");
				var name = $(this).attr("name");
				var text = $(this).attr("text");
				var initvalue = $(this).attr("value");
				var readonly = $(this).attr("readonly");
				var queryAllBySupplier = options.queryAllBySupplier;
				if(!name){
					name="";
				}
				if(!initvalue){
					initvalue = "";
				}
				if(!text){
					text = "";
				}
				var width = $(this).width();
				if(readonly=="readonly"){
					width = width+20;
				}
				$("#"+id).replaceWith('<span class="'+id+'class widgetinput"><input type="text" id="'+id+'" class="searchbox-text" style="width:'+(width-20)+'px; height: 20px; line-height: 18px;" value="'+text+'" getValueType="combogrid"><span><span class="searchbox-button-widget" id="'+id+'-search" style="height: 20px;float: right;"></span><span class="searchbox-clear" id="'+id+'-clear" style="width:20px;float: right;display: none;cursor: pointer;"></span></span></span>');
				$("#"+id).attr("value",text);
				$("#"+id).attr("autocomplete","off");
				$("#"+id+"-hidden").remove();
				$("#"+id).after('<input type="hidden" id="'+id+'-hidden" name="'+name+'" object="" value="'+initvalue+'"/>');

				if(options.required==true){
					$("#"+id).validatebox({tipPosition:'top',required:true,validType:'widgetRequired["'+id+'-hidden"]'});
				}else{
					$("#"+id).validatebox({tipPosition:'top',validType:'widgetRequired["'+id+'-hidden"]'});
				}
				var paramRule = "";
				if(null!=options.param){
					paramRule = JSON.stringify(options.param);
				}
				var rand1 = Math.ceil(Math.random()*100000);
				var divid1 = id+"-search-div"+rand1;
				$("body").append('<div id="'+divid1+'-table" class="pulldown-div" style="width:600px;"><table id="'+divid1+'"></table></div>');
				$("#"+divid1).datagrid({
					columns:[[
						{field:'goodsid',title:'商品编码',width:60,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'goodsname',title:'名称',width:100,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'brand',title:'商品品牌',width:70,
							formatter:function(val,rowData,rowIndex){
								return rowData.brandname;
							}
						},
						{field:'barcode',title:'条形码',width:90,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'spell',title:'助记码',width:70,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'model',title:'规格型号',width:70},
						{field:'unitid',title:'单位',width:30,
							formatter:function(val,rowData,rowIndex){
								return rowData.unitname;
							}
						},
						{field:'boxnum',title:'箱装量',width:50,align:'right',
							formatter:function(val,rowData,rowIndex){
								return formatterBigNumNoLen(val);
							}
						},
						{field:'existingnum',title:'现存量',width:60,align:'right',
							formatter:function(val,rowData,rowIndex){
								return formatterBigNumNoLen(val);
							}
						},
						{field:'usablenum',title:'可用量',width:60,align:'right',
							formatter:function(val,rowData,rowIndex){
								return formatterBigNumNoLen(val);
							}
						},

						{field:'defaultstoragename',title:'默认仓库',width:80},
						{field:'storageid',title:'所属仓库',width:80,
							formatter:function(val,rowData,rowIndex){
								return rowData.storagename;
							}
						},
						{field:'storagelocationid',title:'所属库位',width:80,
							formatter:function(val,rowData,rowIndex){
								return rowData.storagelocationname;
							}
						},
						{field:'batchno',title:'批次号',width:80}
					]],
					width:600,
					height:325,
					method:'post',
					rownumbers:true,
					pagination:true,
					idField:'summarybatchid',
					singleSelect:true,
					pageSize:10,
					pageList:[10,20,30,50],
					onLoadSuccess:function(data){
						var keytype = $("#"+id).data("keytype");
						if(keytype==null){
							keytype = "down";
						}
						if(data.rows.length>0){
							var nowvalue = $("#"+id+"-hidden").val();
							if(data.rows.length==1 && (nowvalue==null || nowvalue=="")){
								var objectStr = JSON.stringify(data.rows[0]);
								$("#"+id).val(data.rows[0].goodsname);
								$("#"+id).attr("title",data.rows[0].goodsname);
								$("#"+id+"-hidden").val(data.rows[0].goodsid);
								$("#"+id+"-hidden").attr("object",objectStr);
								options.onSelect(data.rows[0]);
								$("#"+id).validatebox("validate");
								$("#"+divid1+"-table").hide();
								return false;
							}
							if(keytype=="down"){
								$("#"+divid1).datagrid("selectRow",0);
							}else{
								$("#"+divid1).datagrid("selectRow",data.rows.length-1);
							}
						}
						$("#"+id).focus();
					},
					onClickRow:function(rowIndex, rowData){
						var objectStr = JSON.stringify(rowData);
						$("#"+id).val(rowData.goodsname);
						$("#"+id).attr("title",rowData.goodsname);
						$("#"+id+"-hidden").val(rowData.goodsid);
						$("#"+id+"-hidden").attr("object",objectStr);
						options.onSelect(rowData);
						$("#"+id).validatebox("validate");
						$("#"+divid1+"-table").hide();
					}
				});
				$("#"+id+"-clear").click(function(){
					$("#"+id).val("");
					$("#"+id+"-hidden").val("");
					$("#"+id+"-hidden").attr("object","");
					options.onClear();
				});
				if(readonly!="readonly"){
					var initwidthlen = $("#"+id).width();
					$("."+id+"class").mouseover(function(){
						var readonlyStr = $("#"+id).attr("readonly");
						if(readonlyStr =='readonly' || readonlyStr==true){
							return false;
						}
						$("#"+id).width(initwidthlen-20);
						$("#"+id+"-clear").show();
					}).mouseout(function(){
						var vobject = $(this).find(".validatebox-invalid");
						if(vobject.length==0){
							$("#"+id).width(initwidthlen);
							$("#"+id+"-clear").hide();
						}
					});
				}else{
					$("#"+id).attr("readonly","readonly");
				}
				$("#"+id).focus(function(){
					$("#"+id).die("keydown").live("keydown",function(event){
						switch(event.keyCode){
							case 13: //Enter
								var oldvalue = $("#"+id).data("oldvalue");
								var value = $("#"+id).val();
								$("#"+id).data("oldvalue",value);
								if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
									var readonly = $(event.target).attr("readonly");
									if($(event.target).attr("id")==id && readonly!="readonly"){
										if($("#"+id+"-hidden").val()!=""){
											value = $("#"+id+"-hidden").val();
										}
										$("#"+divid1).datagrid({
											url:'storage/getStorageGoodsSelectListData.do',
											pageNumber:1,
											queryParams:{id:value,paramRule:paramRule,queryAllBySupplier:queryAllBySupplier}
										});
										$("#"+id).data("keytype","down");
										var max= $("#"+id).offset();
										var left = Number(max.left);
										var height = $("#"+id).height();
										var top = max.top+height+2;
										$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
										$("body").bind("mousedown", function(event){
											if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
												$("#"+divid1+"-table").hide();
											}
										});
									}
								}else{
									var object = $("#"+divid1).datagrid("getSelected");
									var objectStr = JSON.stringify(object);
									$("#"+id).val(object.goodsname);
									$("#"+id).attr("title",object.goodsname);
									$("#"+id+"-hidden").val(object.goodsid);
									$("#"+id+"-hidden").attr("object",objectStr);
									options.onSelect(object);
									$("#"+id).validatebox("validate");
									$("#"+divid1+"-table").hide();
									return false;
								}
								break;
							case 8:	//backspace
								//$(event.target).val("")
								$("#"+id+"-hidden").val("");
								$("#"+id+"-hidden").attr("object","");
								options.onClear();
								break;
							case 38:// 上
								var datagridObject =  $("#"+divid1);
								var rowSelected =datagridObject.datagrid("getSelected");
								var rowIndex = 0;
								if(null!=rowSelected){
									rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
									var rows =  datagridObject.datagrid("getRows").length;
									if(rowIndex>0){
										rowIndex = rowIndex-1;
										datagridObject.datagrid("selectRow",rowIndex);
									}else{
										var p = datagridObject.datagrid('getPager');
										var pageObject = $(p).pagination("options");
										if(pageObject.pageNumber>1){
											$(p).pagination("select",pageObject.pageNumber-1);
											$("#"+id).data("keytype","up");
										}
									}
								}else{
									datagridObject.datagrid("selectRow",0);
								}
								break;
							case 40:// 下
								var datagridObject =  $("#"+divid1);
								var rowSelected =datagridObject.datagrid("getSelected");
								var rowIndex = 0;
								if(null!=rowSelected){
									rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
									var rows =  datagridObject.datagrid("getRows").length;
									if(rowIndex<rows-1){
										rowIndex = rowIndex+1;
										datagridObject.datagrid("selectRow",rowIndex);
									}else{
										var p = datagridObject.datagrid('getPager');
										var pageObject = $(p).pagination("options");
										var nums = pageObject.total%pageObject.pageSize;
										var pages = 0;
										if(nums>0){
											pages = (pageObject.total-nums)/pageObject.pageSize+1;
										}else{
											pages = (pageObject.total-nums)/pageObject.pageSize;
										}
										if(pageObject.pageNumber<pages){
											$(p).pagination("select",pageObject.pageNumber+1);
											$("#"+id).data("keytype","down");
										}
									}
								}else{
									datagridObject.datagrid("selectRow",0);
								}
								break;
							case 37:  //左
								var p = $("#"+divid1).datagrid('getPager');
								var pageObject = $(p).pagination("options");
								if(pageObject.pageNumber>1){
									$(p).pagination("select",pageObject.pageNumber-1);
									$("#"+id).data("keytype","up");
								}
								break;
							case 39://右
								var p = $("#"+divid1).datagrid('getPager');
								var pageObject = $(p).pagination("options");
								var nums = pageObject.total%pageObject.pageSize;
								var pages = 0;
								if(nums>0){
									pages = (pageObject.total-nums)/pageObject.pageSize+1;
								}else{
									pages = (pageObject.total-nums)/pageObject.pageSize;
								}
								if(pageObject.pageNumber<pages){
									$(p).pagination("select",pageObject.pageNumber+1);
									$("#"+id).data("keytype","down");
								}
								break;
							case 27:
								$("#"+divid1+"-table").hide();
								break;
							default:
								if(event.ctrlKey==1 && (event.keyCode==67 || event.keyCode==17)){
								}else{
									$("#"+id+"-hidden").val("");
									$("#"+id+"-hidden").attr("object","");
									//options.onClear();
								}
						}
					});
				}).blur(function(){
					$("#"+id).die("keydown");
				});
				$("#"+id+"-search").click(function(){
					var oldvalue = $("#"+id).data("oldvalue");
					var value = $("#"+id).val();
					$("#"+id).data("oldvalue",value);
					if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
						if($("#"+id+"-hidden").val()!=""){
							value = $("#"+id+"-hidden").val();
						}
						$("#"+divid1).datagrid({
							url:'storage/getStorageGoodsSelectListData.do',
							pageNumber:1,
							queryParams:{id:value,paramRule:paramRule,queryAllBySupplier:queryAllBySupplier}
						});
						$("#"+id).data("keytype","down");
						var max= $("#"+id).offset();
						var left = Number(max.left);
						var height = $("#"+id).height();
						var top = max.top+height+2;
						$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
						$("body").bind("mousedown", function(event){
							if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
								$("#"+divid1+"-table").hide();
							}
						});
					}else{
						var object = $("#"+divid1).datagrid("getSelected");
						var objectStr = JSON.stringify(object);
						$("#"+id).val(object.goodsname);
						$("#"+id).attr("title",object.goodsname);
						$("#"+id+"-hidden").val(object.goodsid);
						$("#"+id+"-hidden").attr("object",objectStr);
						options.onSelect(object);
						$("#"+id).validatebox("validate");
						$("#"+divid1+"-table").hide();
						return false;
					}
				});
				$("#"+id).data("onSelect",options.onSelect);
				if(readonly=="readonly"){
					$("#"+id+"-search").hide();
				}
			});
		},
		getText : function(){
			var id =  $(this).attr("id");
			return $("#"+id).val();
		},
		getValue : function(){
			var id =  $(this).attr("id");
			return $("#"+id+"-hidden").val();
		},
		setValue:function(value){
			var id =  $(this).attr("id");
			$("#"+id+"-hidden").val(value)
		},
		setText:function(value){
			var id =  $(this).attr("id");
			return $("#"+id).val(value);
		},
		getObject:function(){
			var id =  $(this).attr("id");
			var objectStr = $("#"+id+"-hidden").attr("object");
			var object = $.parseJSON(objectStr);
			return object;
		},
		clear:function(){
			var id =  $(this).attr("id");
			$("#"+id).val("");
			$("#"+id+"-hidden").val("");
			$("#"+id+"-hidden").attr("object","");
		},
		disable:function(){
			var id =  $(this).attr("id");
			$("#"+id).attr("disabled",true);
			$("#"+id+"-hidden").attr("disabled",true);
			$("#"+id+"-search").hide();
		},
		enable:function(){
			var id =  $(this).attr("id");
			$("#"+id).attr("disabled",false);
			$("#"+id+"-hidden").attr("disabled",false);
			$("#"+id+"-search").show();
		},
		readonly:function(flag){
			var id =  $(this).attr("id");
			$("#"+id).attr("readonly",flag);
			if(flag){
				$("#"+id+"-search").hide();
			}else{
				$("#"+id+"-search").show();
			}
		}
	};
	$.fn.storageGoodsWidget = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(
				arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.tooltip');
		}
	}
})(jQuery);
/**
 * 部门选择控件
 * @param $
 */
(function($){
	var methods = {
		init : function(options){
			defaults = {
				required:false,
				readonly:false,
				param:null,
				onSelect:function(data){},
				onClear:function(){}
			};
			options = $.extend(defaults,options);
			this.each(function() {
				var id = $(this).attr("id");
				var name = $(this).attr("name");
				var text = $(this).attr("text");
				var initvalue = $(this).attr("value");
				var readonly = $(this).attr("readonly");
				if(readonly==null && readonly){
					readonly = "readonly";
				}
				if(!name){
					name="";
				}
				if(!initvalue){
					initvalue = "";
				}
				if(!text){
					text = "";
				}
				var width = $(this).width();
				if(readonly=="readonly"){
					width = width+20;
				}
				$("#"+id).replaceWith('<span class="'+id+'class widgetinput"><input type="text" id="'+id+'" class="searchbox-text" style="width:'+(width-20)+'px; height: 20px; line-height: 18px;" value="'+text+'" getValueType="combogrid"><span><span class="searchbox-button-widget" id="'+id+'-search" style="height: 20px;float: right;"></span><span class="searchbox-clear" id="'+id+'-clear" style="width:20px;float: right;display: none;cursor: pointer;"></span></span></span>');
				$("#"+id).attr("value",text);
				$("#"+id).attr("autocomplete","off");
				$("#"+id+"-hidden").remove();
				$("#"+id).after('<input type="hidden" id="'+id+'-hidden" name="'+name+'" object="" value="'+initvalue+'"/>');
				if(options.required==true){
					$("#"+id).validatebox({tipPosition:'top',required:true,validType:'widgetRequired["'+id+'-hidden"]'});
				}else{
					$("#"+id).validatebox({tipPosition:'top',validType:'widgetRequired["'+id+'-hidden"]'});
				}
				var name = "";
				if(null!=options.param){
					name = JSON.stringify(options.param);
				}
				var rand1 = Math.ceil(Math.random()*100000);
				var divid1 = id+"-search-div"+rand1;
				$("body").append('<div id="'+divid1+'-table" class="pulldown-div" style="width:400px;"><table id="'+divid1+'"></table></div>');
				$("#"+divid1).datagrid({
					columns:[[
						{field:'id',title:'编号',width:50,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'name',title:'所在部门名称',width:245,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:#ff0000;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						}
					]],
					width:400,
					height:320,
					method:'post',
					rownumbers:true,
					pagination:true,
					idField:'id',
					singleSelect:true,
					data:null,
					pageSize:10,
					pageList:[10,20,30,50],
					onLoadSuccess:function(data){
						var keytype = $("#"+id).data("keytype");
						if(keytype==null){
							keytype = "down";
						}
						if(data.rows.length>0){
							var nowvalue = $("#"+id+"-hidden").val();
							if(data.rows.length==1 && (nowvalue==null || nowvalue=="")){
								var objectStr = JSON.stringify(data.rows[0]);
								$("#"+id).val(data.rows[0].name);
								$("#"+id).attr("title",data.rows[0].name);
								$("#"+id+"-hidden").val(data.rows[0].id);
								$("#"+id+"-hidden").attr("object",objectStr);
								options.onSelect(data.rows[0]);
								$("#"+id).validatebox("validate");
								$("#"+divid1+"-table").hide();
								return false;
							}
							if(keytype=="down"){
								$("#"+divid1).datagrid("selectRow",0);
							}else{
								$("#"+divid1).datagrid("selectRow",data.rows.length-1);
							}
						}
						$("#"+id).focus();
					},
					onClickRow:function(rowIndex, rowData){
						var objectStr = JSON.stringify(rowData);
						$("#"+id).val(rowData.name);
						$("#"+id).attr("title",rowData.name);
						$("#"+id+"-hidden").val(rowData.id);
						$("#"+id+"-hidden").attr("object",objectStr);
						options.onSelect(rowData);
						$("#"+id).validatebox("validate");
						$("#"+divid1+"-table").hide();
					}
				});
				$("#"+id+"-clear").click(function(){
					$("#"+id).val("");
					$("#"+id+"-hidden").val("");
					$("#"+id+"-hidden").attr("object","");
					options.onClear();
				});
				if(readonly!="readonly"){
					var initwidthlen = $("#"+id).width();
					$("."+id+"class").mouseover(function(){
						var readonlyStr = $("#"+id).attr("readonly");
						if(readonlyStr =='readonly' || readonlyStr==true){
							return false;
						}
						$("#"+id).width(initwidthlen-20);
						$("#"+id+"-clear").show();
					}).mouseout(function(){
						var vobject = $(this).find(".validatebox-invalid");
						if(vobject.length==0){
							$("#"+id).width(initwidthlen);
							$("#"+id+"-clear").hide();
						}
					});
				}else{
					$("#"+id).attr("readonly","readonly");
				}
				$("#"+id).focus(function(){
					$("#"+id).die("keydown").live("keydown",function(event){
						switch(event.keyCode){
							case 13: //Enter
								var oldvalue = $("#"+id).data("oldvalue");
								var value = $("#"+id).val();
								$("#"+id).data("oldvalue",value);
								if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
									var readonly = $(event.target).attr("readonly");
									if($(event.target).attr("id")==id && readonly!="readonly"){
										if($("#"+id+"-hidden").val()!=""){
											value = $("#"+id+"-hidden").val();
										}
										$("#"+divid1).datagrid({
											url:'basefiles/getDeptList.do',
											pageNumber:1,
											queryParams:{id:value,name:name}
										});
										$("#"+id).data("keytype","down");
										var max= $("#"+id).offset();
										var left = Number(max.left);
										var height = $("#"+id).height();
										var top = max.top+height+2;
										$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
										$("body").bind("mousedown", function(event){
											if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
												$("#"+divid1+"-table").hide();
											}
										});
									}
								}else{
									var object = $("#"+divid1).datagrid("getSelected");
									var objectStr = JSON.stringify(object);
									$("#"+id).val(object.name);
									$("#"+id).attr("title",object.name);
									$("#"+id+"-hidden").val(object.id);
									$("#"+id+"-hidden").attr("object",objectStr);
									options.onSelect(object);
									$("#"+id).validatebox("validate");
									$("#"+divid1+"-table").hide();
									return false;
								}
								break;
							case 8:	//backspace
								//$(event.target).val("")
								$("#"+id+"-hidden").val("");
								$("#"+id+"-hidden").attr("object","");
								options.onClear();
								break;
							case 38:// 上
								var datagridObject =  $("#"+divid1);
								var rowSelected =datagridObject.datagrid("getSelected");
								var rowIndex = 0;
								if(null!=rowSelected){
									rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
									var rows =  datagridObject.datagrid("getRows").length;
									if(rowIndex>0){
										rowIndex = rowIndex-1;
										datagridObject.datagrid("selectRow",rowIndex);
									}else{
										var p = datagridObject.datagrid('getPager');
										var pageObject = $(p).pagination("options");
										if(pageObject.pageNumber>1){
											$(p).pagination("select",pageObject.pageNumber-1);
											$("#"+id).data("keytype","up");
										}
									}
								}else{
									datagridObject.datagrid("selectRow",0);
								}
								break;
							case 40:// 下
								var datagridObject =  $("#"+divid1);
								var rowSelected =datagridObject.datagrid("getSelected");
								var rowIndex = 0;
								if(null!=rowSelected){
									rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
									var rows =  datagridObject.datagrid("getRows").length;
									if(rowIndex<rows-1){
										rowIndex = rowIndex+1;
										datagridObject.datagrid("selectRow",rowIndex);
									}else{
										var p = datagridObject.datagrid('getPager');
										var pageObject = $(p).pagination("options");
										var nums = pageObject.total%pageObject.pageSize;
										var pages = 0;
										if(nums>0){
											pages = (pageObject.total-nums)/pageObject.pageSize+1;
										}else{
											pages = (pageObject.total-nums)/pageObject.pageSize;
										}
										if(pageObject.pageNumber<pages){
											$(p).pagination("select",pageObject.pageNumber+1);
											$("#"+id).data("keytype","down");
										}
									}
								}else{
									datagridObject.datagrid("selectRow",0);
								}
								break;
							case 37:  //左
								var p = $("#"+divid1).datagrid('getPager');
								var pageObject = $(p).pagination("options");
								if(pageObject.pageNumber>1){
									$(p).pagination("select",pageObject.pageNumber-1);
									$("#"+id).data("keytype","up");
								}
								break;
							case 39://右
								var p = $("#"+divid1).datagrid('getPager');
								var pageObject = $(p).pagination("options");
								var nums = pageObject.total%pageObject.pageSize;
								var pages = 0;
								if(nums>0){
									pages = (pageObject.total-nums)/pageObject.pageSize+1;
								}else{
									pages = (pageObject.total-nums)/pageObject.pageSize;
								}
								if(pageObject.pageNumber<pages){
									$(p).pagination("select",pageObject.pageNumber+1);
									$("#"+id).data("keytype","down");
								}
								break;
							case 27:
								$("#"+divid1+"-table").hide();
								break;
							default:
								if(event.ctrlKey==1 && (event.keyCode==67 || event.keyCode==17)){
								}else{
									$("#"+id+"-hidden").val("");
									$("#"+id+"-hidden").attr("object","");
									//options.onClear();
								}
						}
					});
				}).blur(function(){
					$("#"+id).die('keydown');
				});
				$("#"+id+"-search").click(function(){
					var oldvalue = $("#"+id).data("oldvalue");
					var value = $("#"+id).val();
					$("#"+id).data("oldvalue",value);
					if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
						if($("#"+id+"-hidden").val()!=""){
							value = $("#"+id+"-hidden").val();
						}
						$("#"+divid1).datagrid({
							url:'basefiles/getDeptList.do',
							pageNumber:1,
							queryParams:{id:value,name:name}
						});
						$("#"+id).data("keytype","down");
						var max= $("#"+id).offset();
						var left = Number(max.left);
						var height = $("#"+id).height();
						var top = max.top+height+2;
						$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
						$("body").bind("mousedown", function(event){
							if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
								$("#"+divid1+"-table").hide();
							}
						});
					}else{
						var object = $("#"+divid1).datagrid("getSelected");
						var objectStr = JSON.stringify(object);
						$("#"+id).val(object.name);
						$("#"+id).attr("title",object.name);
						$("#"+id+"-hidden").val(object.id);
						$("#"+id+"-hidden").attr("object",objectStr);
						options.onSelect(object);
						$("#"+id).validatebox("validate");
						$("#"+divid1+"-table").hide();
						return false;
					}
				});
				$("#"+id).data("onSelect",options.onSelect);
				if(readonly=="readonly"){
					$("#"+id+"-search").hide();
				}
			});
		},
		getText : function(){
			var id =  $(this).attr("id");
			return $("#"+id).val();
		},
		getValue : function(){
			var id =  $(this).attr("id");
			return $("#"+id+"-hidden").val();
		},
		setValue:function(value){
			var id =  $(this).attr("id");
			$("#"+id+"-hidden").val(value)
		},
		setText:function(value){
			var id =  $(this).attr("id");
			return $("#"+id).val(value);
		},
		getObject:function(){
			var id =  $(this).attr("id");
			var objectStr = $("#"+id+"-hidden").attr("object");
			var object = $.parseJSON(objectStr);
			return object;
		},
		clear:function(){
			var id =  $(this).attr("id");
			$("#"+id).val("");
			$("#"+id+"-hidden").val("");
			$("#"+id+"-hidden").attr("object","");
		},
		disable:function(){
			var id =  $(this).attr("id");
			$("#"+id).attr("disabled",true);
			$("#"+id+"-hidden").attr("disabled",true);
			$("#"+id+"-search").hide();
		},
		enable:function(){
			var id =  $(this).attr("id");
			$("#"+id).attr("disabled",false);
			$("#"+id+"-hidden").attr("disabled",false);
			$("#"+id+"-search").show();
		},
		readonly:function(flag){
			var id =  $(this).attr("id");
			$("#"+id).attr("readonly",flag);
			if(flag){
				$("#"+id+"-search").hide();
			}else{
				$("#"+id+"-search").show();
			}
		}
	};
	$.fn.salesWidget = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(
				arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.tooltip');
		}
	}
})(jQuery);
/**
 * 客户选择控件
 */
(function($){
	var methods = {
		init : function(options){
			defaults = {
				required:false,
				ishead:false,			//客户显示总店或者门店 false：门店 true：总店
				isall:false,			//是否显示全部客户 true 全部 false以ishead为准
				isopen:false,			//是否只显示启用客户 true是 false否
				isdatasql:true,			//是否对客户进行权限控制 1是0否
				param:null,
				onSelect:function(data){},
				onClear:function(){}
			};
			options = $.extend(defaults,options);
			this.each(function() {
				var id = $(this).attr("id");
				var name = $(this).attr("name");
				var text = $(this).attr("text");
				var initvalue = $(this).attr("value");
				var readonly = $(this).attr("readonly");
				if(!name){
					name="";
				}
				if(!initvalue){
					initvalue = "";
				}
				if(!text){
					text = "";
				}
				var width = $(this).width();
				if(readonly=="readonly"){
					width = width+20;
				}
				$("#"+id).replaceWith('<span class="'+id+'class widgetinput" ><input type="text" id="'+id+'" class="searchbox-text" style="width:'+(width-20)+'px; height: 20px; line-height: 18px;" value="'+text+'" getValueType="combogrid"><span><span class="searchbox-button-widget" id="'+id+'-search" style="height: 20px;float: right;"></span><span class="searchbox-clear" id="'+id+'-clear" style="width:20px;float: right;display: none;cursor: pointer;"></span></span></span>');
				$("#"+id).attr("value",text);
				$("#"+id).attr("autocomplete","off");
				$("#"+id+"-hidden").remove();
				$("#"+id).after('<input type="hidden" id="'+id+'-hidden" name="'+name+'" object="" value="'+initvalue+'"/>');
				if(options.required==true){
					$("#"+id).validatebox({tipPosition:'top',required:true,validType:'widgetRequired["'+id+'-hidden"]'});
				}else{
					$("#"+id).validatebox({tipPosition:'top',validType:'widgetRequired["'+id+'-hidden"]'});
				}
				var paramRule = "";
				if(null!=options.param){
					paramRule = JSON.stringify(options.param);
				}
				//1表示门店 2表示总店 3表示全部
				var ishead = "1";
				if(options.ishead){
					ishead ="2";
				}
				if(options.isall){
					ishead = "3";
				}
				//0表示显示全部状态的 1表示只显示启用的
				var isopen = "0";
				if(options.isopen){
					isopen = "1";
				}
				//1表示进行权限控制 2表示不进行权限控制
				var isdatasql = "1";
				if(!options.isdatasql){
					isdatasql = "2";
				}
				var rand1 = Math.ceil(Math.random()*100000);
				var divid1 = id+"-search-div"+rand1;
				$("body").append('<div id="'+divid1+'-table" class="pulldown-div" style="width:700px;"><table id="'+divid1+'"></table></div>');
				$("#"+divid1).datagrid({
					columns:[[
						{field:'id',title:'编号',width:60,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span class="selectkey">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'name',title:'客户名称',width:180,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									if(keyvalue.indexOf(" ")>=0){
										var keyArr = keyvalue.split(" ");
										for(var i=0;i<keyArr.length;i++){
											var keyVal = keyArr[i];
											keyVal = keyVal.replace(" ","");
											var str1 = '<span class="selectkey">'+keyVal+'</span>';
											val = val.replace(keyVal,str1);
										}
									}else{
										var str = '<span class="selectkey">'+keyvalue+'</span>';
										val = val.replace(keyvalue,str);
									}
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'shortcode',title:'助记码',width:50,resizable:true,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span class="selectkey">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'address',title:'地址',width:175,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span class="selectkey">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'salesdeptname',title:'销售部门',width:60,hidden:true},
						{field:'salesusername',title:'客户业务员',width:60},
						{field:'salesareaname',title:'所属区域',width:60,resizable:true},
						{field:'customersortname',title:'所属分类',width:80}
					]],
					width:700,
					height:320,
					method:'post',
					rownumbers:true,
					pagination:true,
					idField:'id',
					singleSelect:true,
					data:null,
					pageSize:50,
					pageList:[10,20,30,50,100],
					onLoadSuccess:function(data){
						var keytype = $("#"+id).data("keytype");
						if(keytype==null){
							keytype = "down";
						}
						if(data.rows.length>0){
							var nowvalue = $("#"+id+"-hidden").val();
							if(data.rows.length==1 && (nowvalue==null || nowvalue=="")){
								var objectStr = JSON.stringify(data.rows[0]);
								$("#"+id).val(data.rows[0].name);
								$("#"+id).attr("title",data.rows[0].name);
								$("#"+id+"-hidden").val(data.rows[0].id);
								$("#"+id+"-hidden").attr("object",objectStr);
								options.onSelect(data.rows[0]);
								$("#"+id).validatebox("validate");
								$("#"+divid1+"-table").hide();
								return false;
							}
							if(keytype=="down"){
								$("#"+divid1).datagrid("selectRow",0);
							}else{
								$("#"+divid1).datagrid("selectRow",data.rows.length-1);
							}
						}
						$("#"+id).focus();
					},
					onClickRow:function(rowIndex, rowData){
						var objectStr = JSON.stringify(rowData);
						$("#"+id).val(rowData.name);
						$("#"+id).attr("title",rowData.name);
						$("#"+id+"-hidden").val(rowData.id);
						$("#"+id+"-hidden").attr("object",objectStr);
						options.onSelect(rowData);
						$("#"+id).validatebox("validate");
						$("#"+divid1+"-table").hide();
					}
				});
				$("#"+id+"-clear").click(function(){
					$("#"+id).val("");
					$("#"+id+"-hidden").val("");
					$("#"+id+"-hidden").attr("object","");
					options.onClear();
				});
				if(readonly!="readonly"){
					var initwidthlen = $("#"+id).width();
					$("."+id+"class").mouseover(function(){
						var readonlyStr = $("#"+id).attr("readonly");
						if(readonlyStr =='readonly' || readonlyStr==true){
							return false;
						}
						$("#"+id).width(initwidthlen-20);
						$("#"+id+"-clear").show();
					}).mouseout(function(){
						var vobject = $(this).find(".validatebox-invalid");
						if(vobject.length==0){
							$("#"+id).width(initwidthlen);
							$("#"+id+"-clear").hide();
						}
					});
				}else{
					$("#"+id).attr("readonly","readonly");
				}
				$("#"+id).focus(function(){
					$("#"+id).die("keydown").live("keydown",function(event){
						switch(event.keyCode){
							case 13: //Enter
								var oldvalue = $("#"+id).data("oldvalue");
								var value = $("#"+id).val();
								$("#"+id).data("oldvalue",value);
								if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
									var readonly = $(event.target).attr("readonly");
									if($(event.target).attr("id")==id && readonly!="readonly"){
										if($("#"+id+"-hidden").val()!=""){
											value = $("#"+id+"-hidden").val();
										}
										$("#"+divid1).datagrid({
											url:'basefiles/getCustomerSelectListData.do',
											pageNumber:1,
											queryParams:{id:value,paramRule:paramRule,ishead:ishead,isopen:isopen,isdatasql:isdatasql}
										});
										$("#"+id).data("keytype","down");
										var max= $("#"+id).offset();
										var left = Number(max.left);
										var height = $("#"+id).height();
										var top = max.top+height+2;
										$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
										$("body").bind("mousedown", function(event){
											if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
												$("#"+divid1+"-table").hide();
											}
										});
									}
								}else{
									var object = $("#"+divid1).datagrid("getSelected");
									var objectStr = JSON.stringify(object);
									$("#"+id).val(object.name);
									$("#"+id).attr("title",object.name);
									$("#"+id+"-hidden").val(object.id);
									$("#"+id+"-hidden").attr("object",objectStr);
									options.onSelect(object);
									$("#"+id).validatebox("validate");
									$("#"+divid1+"-table").hide();
									return false;
								}
								break;
							case 8:	//backspace
								//$(event.target).val("")
								$("#"+id+"-hidden").val("");
								$("#"+id+"-hidden").attr("object","");
								options.onClear();
								break;
							case 38:// 上
								var datagridObject =  $("#"+divid1);
								var rowSelected =datagridObject.datagrid("getSelected");
								var rowIndex = 0;
								if(null!=rowSelected){
									rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
									var rows =  datagridObject.datagrid("getRows").length;
									if(rowIndex>0){
										rowIndex = rowIndex-1;
										datagridObject.datagrid("selectRow",rowIndex);
									}else{
										var p = datagridObject.datagrid('getPager');
										var pageObject = $(p).pagination("options");
										if(pageObject.pageNumber>1){
											$(p).pagination("select",pageObject.pageNumber-1);
											$("#"+id).data("keytype","up");
										}
									}
								}else{
									datagridObject.datagrid("selectRow",0);
								}
								break;
							case 40:// 下
								var datagridObject =  $("#"+divid1);
								var rowSelected =datagridObject.datagrid("getSelected");
								var rowIndex = 0;
								if(null!=rowSelected){
									rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
									var rows =  datagridObject.datagrid("getRows").length;
									if(rowIndex<rows-1){
										rowIndex = rowIndex+1;
										datagridObject.datagrid("selectRow",rowIndex);
									}else{
										var p = datagridObject.datagrid('getPager');
										var pageObject = $(p).pagination("options");
										var nums = pageObject.total%pageObject.pageSize;
										var pages = 0;
										if(nums>0){
											pages = (pageObject.total-nums)/pageObject.pageSize+1;
										}else{
											pages = (pageObject.total-nums)/pageObject.pageSize;
										}
										if(pageObject.pageNumber<pages){
											$(p).pagination("select",pageObject.pageNumber+1);
											$("#"+id).data("keytype","down");
										}
									}
								}else{
									datagridObject.datagrid("selectRow",0);
								}
								break;
							case 37:  //左
								var p = $("#"+divid1).datagrid('getPager');
								var pageObject = $(p).pagination("options");
								if(pageObject.pageNumber>1){
									$(p).pagination("select",pageObject.pageNumber-1);
									$("#"+id).data("keytype","up");
								}
								break;
							case 39://右
								var p = $("#"+divid1).datagrid('getPager');
								var pageObject = $(p).pagination("options");
								var nums = pageObject.total%pageObject.pageSize;
								var pages = 0;
								if(nums>0){
									pages = (pageObject.total-nums)/pageObject.pageSize+1;
								}else{
									pages = (pageObject.total-nums)/pageObject.pageSize;
								}
								if(pageObject.pageNumber<pages){
									$(p).pagination("select",pageObject.pageNumber+1);
									$("#"+id).data("keytype","down");
								}
								break;
							case 27:
								$("#"+divid1+"-table").hide();
								break;
							default:
								if(event.ctrlKey==1 && (event.keyCode==67 || event.keyCode==17)){
								}else{
									$("#"+id+"-hidden").val("");
									$("#"+id+"-hidden").attr("object","");
									//options.onClear();
								}
						}
					});
				}).blur(function(){
					$("#"+id).die('keydown');
				});
				$("#"+id+"-search").click(function(){
					var oldvalue = $("#"+id).data("oldvalue");
					var value = $("#"+id).val();
					$("#"+id).data("oldvalue",value);
					if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
						if($("#"+id+"-hidden").val()!=""){
							value = $("#"+id+"-hidden").val();
						}
						$("#"+divid1).datagrid({
							url:'basefiles/getCustomerSelectListData.do',
							pageNumber:1,
							queryParams:{id:value,paramRule:paramRule,ishead:ishead,isopen:isopen,isdatasql:isdatasql}
						});
						$("#"+id).data("keytype","down");
						var max= $("#"+id).offset();
						var left = Number(max.left);
						var height = $("#"+id).height();
						var top = max.top+height+2;
						$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
						$("body").bind("mousedown", function(event){
							if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
								$("#"+divid1+"-table").hide();
							}
						});
					}else{
						var object = $("#"+divid1).datagrid("getSelected");
						var objectStr = JSON.stringify(object);
						$("#"+id).val(object.name);
						$("#"+id).attr("title",object.name);
						$("#"+id+"-hidden").val(object.id);
						$("#"+id+"-hidden").attr("object",objectStr);
						options.onSelect(object);
						$("#"+id).validatebox("validate");
						$("#"+divid1+"-table").hide();
						return false;
					}
				});
				$("#"+id).data("onSelect",options.onSelect);
				if(readonly=="readonly"){
					$("#"+id+"-search").hide();
				}
			});
		},
		getText : function(){
			var id =  $(this).attr("id");
			return $("#"+id).val();
		},
		getValue : function(){
			var id =  $(this).attr("id");
			return $("#"+id+"-hidden").val();
		},
		setValue:function(value){
			var id =  $(this).attr("id");
			$("#"+id+"-hidden").val(value)
		},
		setText:function(value){
			var id =  $(this).attr("id");
			return $("#"+id).val(value);
		},
		getObject:function(){
			var id =  $(this).attr("id");
			var objectStr = $("#"+id+"-hidden").attr("object");
			var object = $.parseJSON(objectStr);
			return object;
		},
		clear:function(){
			var id =  $(this).attr("id");
			$("#"+id).val("");
			$("#"+id+"-hidden").val("");
			$("#"+id+"-hidden").attr("object","");
		},
		disable:function(){
			var id =  $(this).attr("id");
			$("#"+id).attr("disabled",true);
			$("#"+id+"-hidden").attr("disabled",true);
			$("#"+id+"-search").hide();
		},
		enable:function(){
			var id =  $(this).attr("id");
			$("#"+id).attr("disabled",false);
			$("#"+id+"-hidden").attr("disabled",false);
			$("#"+id+"-search").show();
		},
		readonly:function(flag){
			var id =  $(this).attr("id");
			$("#"+id).attr("readonly",flag);
			if(flag){
				$("#"+id+"-search").hide();
			}else{
				$("#"+id+"-search").show();
			}
		}
	};
	$.fn.customerWidget = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(
				arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.tooltip');
		}
	}
})(jQuery);
/**
 * 供应商选择控件
 */

(function($){
	var methods = {
		init : function(options){
			defaults = {
				required:false,
				readonly:false,
				param:null,
				onSelect:function(data){},
				onClear:function(){}
			};
			options = $.extend(defaults,options);
			this.each(function() {
				var id = $(this).attr("id");
				var name = $(this).attr("name");
				var text = $(this).attr("text");
				var initvalue = $(this).attr("value");
				var readonly = $(this).attr("readonly");
				if(readonly==null && readonly){
					readonly = "readonly";
				}
				if(!name){
					name="";
				}
				if(!initvalue){
					initvalue = "";
				}
				if(!text){
					text = "";
				}
				var width = $(this).width();
				if(readonly=="readonly"){
					width = width+20;
				}
				$("#"+id).replaceWith('<span class="'+id+'class widgetinput"><input type="text" id="'+id+'" class="searchbox-text" style="width:'+(width-20)+'px; height: 20px; line-height: 18px;" value="'+text+'" getValueType="combogrid"><span><span class="searchbox-button-widget" id="'+id+'-search" style="height: 20px;float: right;"></span><span class="searchbox-clear" id="'+id+'-clear" style="width:20px;float: right;display: none;cursor: pointer;"></span></span></span>');
				$("#"+id).attr("value",text);
				$("#"+id).attr("autocomplete","off");
				$("#"+id+"-hidden").remove();
				$("#"+id).after('<input type="hidden" id="'+id+'-hidden" name="'+name+'" object="" value="'+initvalue+'"/>');
				if(options.required==true){
					$("#"+id).validatebox({tipPosition:'top',required:true,validType:'widgetRequired["'+id+'-hidden"]'});
				}else{
					$("#"+id).validatebox({tipPosition:'top',validType:'widgetRequired["'+id+'-hidden"]'});
				}
				var paramRule = "";
				if(null!=options.param){
					paramRule = JSON.stringify(options.param);
				}
				var rand1 = Math.ceil(Math.random()*100000);
				var divid1 = id+"-search-div"+rand1;
				$("body").append('<div id="'+divid1+'-table" class="pulldown-div" style="width:630px;"><table id="'+divid1+'"></table></div>');
				$("#"+divid1).datagrid({
					columns:[[
						{field:'id',title:'编号',width:50,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'name',title:'供应商名称',width:250,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'spell',title:'助记码',width:70,resizable:true,
							formatter:function(val,rowData,rowIndex){
								if(val!=null){
									var keyvalue = $("#"+id).val();
									var str = '<span style="color:red;">'+keyvalue+'</span>';
									val = val.replace(keyvalue,str);
									return val;
								}else{
									return "";
								}
							}
						},
						{field:'contactname',title:'对方联系人',width:80},
						{field:'buydeptname',title:'采购部门',width:80},
						{field:'buyusername',title:'采购员',width:80}
					]],
					width:630,
					height:320,
					method:'post',
					rownumbers:true,
					pagination:true,
					idField:'id',
					singleSelect:true,
					data:null,
					pageSize:10,
					pageList:[10,20,30,50],
					onLoadSuccess:function(data){
						var keytype = $("#"+id).data("keytype");
						if(keytype==null){
							keytype = "down";
						}
						if(data.rows.length>0){
							var nowvalue = $("#"+id+"-hidden").val();
							if(data.rows.length==1 && (nowvalue==null || nowvalue=="")){
								var objectStr = JSON.stringify(data.rows[0]);
								$("#"+id).val(data.rows[0].name);
								$("#"+id).attr("title",data.rows[0].name);
								$("#"+id+"-hidden").val(data.rows[0].id);
								$("#"+id+"-hidden").attr("object",objectStr);
								options.onSelect(data.rows[0]);
								$("#"+id).validatebox("validate");
								$("#"+divid1+"-table").hide();
								return false;
							}
							if(keytype=="down"){
								$("#"+divid1).datagrid("selectRow",0);
							}else{
								$("#"+divid1).datagrid("selectRow",data.rows.length-1);
							}
						}
						$("#"+id).focus();
					},
					onClickRow:function(rowIndex, rowData){
						var objectStr = JSON.stringify(rowData);
						$("#"+id).val(rowData.name);
						$("#"+id).attr("title",rowData.name);
						$("#"+id+"-hidden").val(rowData.id);
						$("#"+id+"-hidden").attr("object",objectStr);
						options.onSelect(rowData);
						$("#"+id).validatebox("validate");
						$("#"+divid1+"-table").hide();
					}
				});
				$("#"+id+"-clear").click(function(){
					$("#"+id).val("");
					$("#"+id+"-hidden").val("");
					$("#"+id+"-hidden").attr("object","");
					options.onClear();
				});
				if(readonly!="readonly"){
					var initwidthlen = $("#"+id).width();
					$("."+id+"class").mouseover(function(){
						var readonlyStr = $("#"+id).attr("readonly");
						if(readonlyStr =='readonly' || readonlyStr==true){
							return false;
						}
						$("#"+id).width(initwidthlen-20);
						$("#"+id+"-clear").show();
					}).mouseout(function(){
						var vobject = $(this).find(".validatebox-invalid");
						if(vobject.length==0){
							$("#"+id).width(initwidthlen);
							$("#"+id+"-clear").hide();
						}
					});
				}else{
					$("#"+id).attr("readonly","readonly");
				}
				$("#"+id).focus(function(){
					$("#"+id).die("keydown").live("keydown",function(event){
						switch(event.keyCode){
							case 13: //Enter
								var oldvalue = $("#"+id).data("oldvalue");
								var value = $("#"+id).val();
								$("#"+id).data("oldvalue",value);
								if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
									var readonly = $(event.target).attr("readonly");
									if($(event.target).attr("id")==id && readonly!="readonly"){
										if($("#"+id+"-hidden").val()!=""){
											value = $("#"+id+"-hidden").val();
										}
										$("#"+divid1).datagrid({
											url:'basefiles/getSupplierSelectListData.do',
											pageNumber:1,
											queryParams:{id:value,paramRule:paramRule}
										});
										$("#"+id).data("keytype","down");
										var max= $("#"+id).offset();
										var left = Number(max.left);
										var height = $("#"+id).height();
										var top = max.top+height+2;
										$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
										$("body").bind("mousedown", function(event){
											if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
												$("#"+divid1+"-table").hide();
											}
										});
									}
								}else{
									var object = $("#"+divid1).datagrid("getSelected");
									var objectStr = JSON.stringify(object);
									$("#"+id).val(object.name);
									$("#"+id).attr("title",object.name);
									$("#"+id+"-hidden").val(object.id);
									$("#"+id+"-hidden").attr("object",objectStr);
									options.onSelect(object);
									$("#"+id).validatebox("validate");
									$("#"+divid1+"-table").hide();
									return false;
								}
								break;
							case 8:	//backspace
								//$(event.target).val("")
								$("#"+id+"-hidden").val("");
								$("#"+id+"-hidden").attr("object","");
								options.onClear();
								break;
							case 38:// 上
								var datagridObject =  $("#"+divid1);
								var rowSelected =datagridObject.datagrid("getSelected");
								var rowIndex = 0;
								if(null!=rowSelected){
									rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
									var rows =  datagridObject.datagrid("getRows").length;
									if(rowIndex>0){
										rowIndex = rowIndex-1;
										datagridObject.datagrid("selectRow",rowIndex);
									}else{
										var p = datagridObject.datagrid('getPager');
										var pageObject = $(p).pagination("options");
										if(pageObject.pageNumber>1){
											$(p).pagination("select",pageObject.pageNumber-1);
											$("#"+id).data("keytype","up");
										}
									}
								}else{
									datagridObject.datagrid("selectRow",0);
								}
								break;
							case 40:// 下
								var datagridObject =  $("#"+divid1);
								var rowSelected =datagridObject.datagrid("getSelected");
								var rowIndex = 0;
								if(null!=rowSelected){
									rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
									var rows =  datagridObject.datagrid("getRows").length;
									if(rowIndex<rows-1){
										rowIndex = rowIndex+1;
										datagridObject.datagrid("selectRow",rowIndex);
									}else{
										var p = datagridObject.datagrid('getPager');
										var pageObject = $(p).pagination("options");
										var nums = pageObject.total%pageObject.pageSize;
										var pages = 0;
										if(nums>0){
											pages = (pageObject.total-nums)/pageObject.pageSize+1;
										}else{
											pages = (pageObject.total-nums)/pageObject.pageSize;
										}
										if(pageObject.pageNumber<pages){
											$(p).pagination("select",pageObject.pageNumber+1);
											$("#"+id).data("keytype","down");
										}
									}
								}else{
									datagridObject.datagrid("selectRow",0);
								}
								break;
							case 37:  //左
								var p = $("#"+divid1).datagrid('getPager');
								var pageObject = $(p).pagination("options");
								if(pageObject.pageNumber>1){
									$(p).pagination("select",pageObject.pageNumber-1);
									$("#"+id).data("keytype","up");
								}
								break;
							case 39://右
								var p = $("#"+divid1).datagrid('getPager');
								var pageObject = $(p).pagination("options");
								var nums = pageObject.total%pageObject.pageSize;
								var pages = 0;
								if(nums>0){
									pages = (pageObject.total-nums)/pageObject.pageSize+1;
								}else{
									pages = (pageObject.total-nums)/pageObject.pageSize;
								}
								if(pageObject.pageNumber<pages){
									$(p).pagination("select",pageObject.pageNumber+1);
									$("#"+id).data("keytype","down");
								}
								break;
							case 27:
								$("#"+divid1+"-table").hide();
								break;
							default:
								if(event.ctrlKey==1 && (event.keyCode==67 || event.keyCode==17)){
								}else{
									$("#"+id+"-hidden").val("");
									$("#"+id+"-hidden").attr("object","");
									//options.onClear();
								}
						}
					});
				}).blur(function(){
					$("#"+id).die('keydown');
				});
				$("#"+id+"-search").click(function(){
					var oldvalue = $("#"+id).data("oldvalue");
					var value = $("#"+id).val();
					$("#"+id).data("oldvalue",value);
					if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
						if($("#"+id+"-hidden").val()!=""){
							value = $("#"+id+"-hidden").val();
						}
						$("#"+divid1).datagrid({
							url:'basefiles/getSupplierSelectListData.do',
							pageNumber:1,
							queryParams:{id:value,paramRule:paramRule}
						});
						$("#"+id).data("keytype","down");
						var max= $("#"+id).offset();
						var left = Number(max.left);
						var height = $("#"+id).height();
						var top = max.top+height+2;
						$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
						$("body").bind("mousedown", function(event){
							if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
								$("#"+divid1+"-table").hide();
							}
						});
					}else{
						var object = $("#"+divid1).datagrid("getSelected");
						var objectStr = JSON.stringify(object);
						$("#"+id).val(object.name);
						$("#"+id).attr("title",object.name);
						$("#"+id+"-hidden").val(object.id);
						$("#"+id+"-hidden").attr("object",objectStr);
						options.onSelect(object);
						$("#"+id).validatebox("validate");
						$("#"+divid1+"-table").hide();
						return false;
					}
				});
				$("#"+id).data("onSelect",options.onSelect);
				if(readonly=="readonly"){
					$("#"+id+"-search").hide();
				}
			});
		},
		getText : function(){
			var id =  $(this).attr("id");
			return $("#"+id).val();
		},
		getValue : function(){
			var id =  $(this).attr("id");
			return $("#"+id+"-hidden").val();
		},
		setValue:function(value){
			var id =  $(this).attr("id");
			$("#"+id+"-hidden").val(value)
		},
		setText:function(value){
			var id =  $(this).attr("id");
			return $("#"+id).val(value);
		},
		getObject:function(){
			var id =  $(this).attr("id");
			var objectStr = $("#"+id+"-hidden").attr("object");
			var object = $.parseJSON(objectStr);
			return object;
		},
		clear:function(){
			var id =  $(this).attr("id");
			$("#"+id).val("");
			$("#"+id+"-hidden").val("");
			$("#"+id+"-hidden").attr("object","");
		},
		disable:function(){
			var id =  $(this).attr("id");
			$("#"+id).attr("disabled",true);
			$("#"+id+"-hidden").attr("disabled",true);
			$("#"+id+"-search").hide();
		},
		enable:function(){
			var id =  $(this).attr("id");
			$("#"+id).attr("disabled",false);
			$("#"+id+"-hidden").attr("disabled",false);
			$("#"+id+"-search").show();
		},
		readonly:function(flag){
			var id =  $(this).attr("id");
			$("#"+id).attr("readonly",flag);
			if(flag){
				$("#"+id+"-search").hide();
			}else{
				$("#"+id+"-search").show();
			}
		}
	};
	$.fn.supplierWidget = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(
				arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.tooltip');
		}
	}
})(jQuery);
/**
 * 通用下拉控件
 */
(function($){
	var methods = {
		init : function(options){
			defaults = {
				wname:'数据窗口',	//弹出窗口名称
				width:200,		//宽度
				name:'',		//表名
				col:'',			//字段名
				id:'',			//控件id
				aid:'',
				onlyLeafCheck:true,	//树状数据选择 true只能选择子节点 false能选择父节点
				onlyParentCheck:false,	//树状数据选择 true只能选择父节点
				referwid:null,		//参照窗口编号。如果指定了参照窗口编号，将直接取该参照窗口
				param:null,			//[{field:'name',op:'equal',value:'123'}]
				singleSelect:true,	//是否支持true单选false多选
				getValueType:'',
				initValue:'',		//初始值 多个值用,分割
				initSelectNull:false,//是否初始空值显示
				disabled:false,		//是否禁用组合框 true是false否
				view:false,			//控件是否用来显示或者查询等作用 view为true时，取参照窗口中查看sql
				allSelect:true,		//是否显示树状的全选按钮 true显示 false不显示
				treeDistint:false,	//是否去树状重复数据 true是 false 否
				treePName:true,		//是否把参照窗口名称当作树最顶级父节点 true是 false否
				treeNodeDataUseNocheck:false, //是否根据参数窗口字段treenodenocheck数据值(1或”true” 表示不可选)，值（0 或”false” 表示可选）开启结点是否可选
				required:false,
				readonly:false,
				async:true,			//是否异步加载数据 true是 false否
				setValueSelect:true,		//setValue时 是否调用onSelect等事件		 true是 false否
				listnum:200,					//参照窗口 总数据不超过50条时 全部显示下拉
				isPageReLoad:true,				//翻页后是否清空选择项
				onSelect:function(data){},	//选中事件
				onUnselect:function(data){},
				onClear:function(){},	//清除事件
				onChecked:function(data,checked){},	//树状下拉控件选中事件
				onLoadSuccess:function(){},			//加载成功后事件
				onSelectAll:function(){}			//树状控件全选事件
			};
			options = $.extend(defaults,options);

			hideMenu = function() {
				$(".widgettree").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			};
			onBodyDown = function(event) {
				if (event.target.id==""||!(event.target.id == options.id || event.target.id.indexOf("TreeContent")>0 || $(event.target).parents(".widgettree").length>0)) {
					hideMenu();
				}
			};

			setDatagrid = function(json,options){
				var value = $("#"+options.id).val();
				var paramRule = "";
				if(null!=options.param){
					paramRule = JSON.stringify(options.param);
				}
				//获取控件定义的宽度
				var inputwidth =  $("#"+options.id).width();
				if(options.width!=null && options.width!=0){
					inputwidth = options.width;
				}
				var inittext = "";
				var objectstr = "";
				var initValueStr = "";
				if(options.initValue!=null && options.initValue!="" && json.data!=null && json.data.length==1){
					inittext = json.data[0][json.column.namevalue];
					objectstr = JSON.stringify(json.data[0]);
					initValueStr = options.initValue;
					//options.onSelect(json.data[0]);
				}
				var initdata = json.data;
				var name = $("#"+options.id).attr("name");
				var rand1 = Math.ceil(Math.random()*100000);
				var divid1 = options.id+"-search-div"+rand1;
				$("#"+options.id).replaceWith('<div class="'+options.id+'class widgetinput" style="width:'+(inputwidth-2)+'px;"><input type="text" id="'+options.id+'" divid="'+divid1+'" class="searchbox-text" style="width:'+(inputwidth-25)+'px; height: 20px; line-height: 18px;" value="'+inittext+'" getValueType="combogrid"><span><span class="searchbox-button-widget" id="'+options.id+'-search" style="height: 20px;width:18px;float: right;"></span><span class="searchbox-clear" id="'+options.id+'-clear" style="width:20px;float: right;display: none;cursor: pointer;"></span></span></div>');
				$("#"+options.id).attr("autocomplete","off");
				$("#"+options.id+"-hidden").remove();
				$("#"+options.id).after('<input type="hidden" id="'+options.id+'-hidden" widget="combogrid" widgetname="'+name+'" name="'+name+'" object=\''+objectstr+'\' value="'+initValueStr+'"/>');
				$("#"+options.id).data("divid",divid1);
				$("#"+options.id).data("url",'system/referWindow/getReferWindowData.do');
				$("#"+options.id).data("queryParams",{id:json.referid,paramRule:paramRule,col:json.column.idvalue,colname:json.column.namevalue,content:value});

				if(options.required==true){
					$("#"+options.id).validatebox({required:true,validType:'widgetRequired["'+options.id+'-hidden"]'});
				}else{
					$("#"+options.id).validatebox({validType:'widgetRequired["'+options.id+'-hidden"]'});
				}
				if(options.readonly){
					$("#"+options.id).width(inputwidth-2);
					$("#"+options.id).attr("readonly",true);
					$("#"+options.id+"-search").hide();
					$("#"+options.id+"-clear").hide();
				}
				if(options.disabled){
					$("#"+options.id).width(inputwidth-2);
					$("#"+options.id).attr("disabled",true);
					$("#"+options.id+"-hidden").attr("disabled",true);
					$("#"+options.id+"-search").hide();
					$("#"+options.id+"-clear").hide();
				}
				$("#"+options.id+"-search").click(function(){
					var e = jQuery.Event("keydown");//模拟一个键盘事件
					e.keyCode = 13;//keyCode=13是回车
					$("#"+options.id).focus();
					$("#"+options.id).trigger(e);//模拟页码框按下回车
				});
				//选中事件
				$("#"+options.id).data("comgridonSelect",options.onSelect);

				$("body").append('<div id="'+divid1+'-table" class="pulldown-div" style="width:500px;"><table id="'+divid1+'"></table></div>');
				var inputoff = $("#"+options.id).offset();
				var inputheight = $("#"+options.id).height();
				var inputtop = inputoff.top+inputheight;
				var totalHigh = $(document.body).height();
				var dheitgh = 320;
//				if(totalHigh-inputtop<320 && totalHigh-inputtop>200){
//					dheitgh = totalHigh-inputtop;
//				}else if(totalHigh-inputtop<200){
//					if(inputtop>320){
//						dheitgh = 320;
//					}else{
//						dheitgh = 300;
//					}
//				}
//				$("#"+divid1+"-table").show();
				$("#"+divid1+"-table").height(dheitgh);
				var ids = options.initValue;
				if(ids!=""){
					var idsArr = ids.split(",");
					var initname = "";
					var objectArr = [];
					for(var i=0;i<idsArr.length;i++){
						for(var j=0;j<json.data.length;j++){
							if(idsArr[i]==json.data[j][json.column.idvalue]){
								if(initname==""){
									initname = json.data[j][json.column.namevalue];
									objectArr.push(json.data[j]);
								}else{
									initname += ","+json.data[j][json.column.namevalue];
									objectArr.push(json.data[j]);
								}
								break;
							}
						}
					}
					var objectStr = JSON.stringify(objectArr);
					$("#"+options.id).val(initname);
					$("#"+options.id).attr("title",initname);
					$("#"+options.id+"-hidden").val(ids);
					$("#"+options.id+"-hidden").attr("object",objectStr);
				}
				if(json.data!=null && json.data.length>0 && json.refertype=="all"){
					$("#"+options.id+"-clear").click(function(){
						$("#"+options.id).val("");
						$("#"+options.id+"-hidden").val("");
						$("#"+divid1).datagrid("clearSelections");
						$("#"+divid1).datagrid("clearChecked");
						$("#"+options.id+"-hidden").attr("object","");
						options.onClear();
					});
					var inputid = options.id;
					if(!options.readonly && !options.disabled){
						var initwidthlen = $("#"+options.id).width();
						$("."+options.id+"class").mouseover(function(){
							var readonlyStr = $("#"+inputid).attr("readonly");
							if(readonlyStr =='readonly' || readonlyStr==true){
								return false;
							}
							var disabledstr = $("#"+inputid).attr("disabled");
							if(disabledstr=="disabled" || disabledstr==true){
								return false;
							}
							$("#"+options.id).width(initwidthlen-25);
							$("#"+options.id+"-clear").show();
						}).mouseout(function(){
							var readonlyStr = $("#"+inputid).attr("readonly");
							if(readonlyStr =='readonly' || readonlyStr==true){
								return false;
							}
							var disabledstr = $("#"+inputid).attr("disabled");
							if(disabledstr=="disabled" || disabledstr==true){
								return false;
							}
							var vobject = $(this).find(".validatebox-invalid");
							if(vobject.length==0){
								$("#"+options.id).width(initwidthlen-5);
								$("#"+options.id+"-clear").hide();
							}
						});
					}
					var isPageReLoad = true;
					if(options.isPageReLoad!=null){
						isPageReLoad = options.isPageReLoad
					}
					$("#"+divid1).datagrid({
						frozenColumns:[[{field:'ck',checkbox:true,hidden:options.singleSelect}]],
						columns:[json.columnFieldList],
						width:500,
						height:dheitgh,
						rownumbers:true,
						singleSelect:options.singleSelect,
						checkOnSelect:!options.singleSelect,
						selectOnCheck:!options.singleSelect,
						idField:json.column.idvalue,
						data:json.data,
						onBeforeLoad:function(){
							if(!isPageReLoad){
								$(this).datagrid('clearChecked');
								$(this).datagrid('clearSelections');
							}
						},
						onLoadSuccess:function(data){
							var keytype = $("#"+options.id).data("keytype");
							if(keytype==null){
								keytype = "down";
							}
							if(options.singleSelect && data.rows.length>0){
								var nowvalue = $("#"+divid1+"-hidden").val();
								var textvalue = $("#"+options.id).val();
								//if(data.rows.length==1 && (nowvalue==null || nowvalue=="") &&(textvalue!="")){
								//	var rowData = data.rows[0];
								//	var objectStr = JSON.stringify(rowData)
								//	$("#"+options.id).val(rowData[json.column.namevalue]);
								//	$("#"+options.id).attr("title",rowData[json.column.namevalue]);
								//	$("#"+options.id+"-hidden").val(rowData[json.column.idvalue]);
								//	$("#"+options.id+"-hidden").attr("object",objectStr);
								//	options.onSelect(rowData);
								//	$("#"+options.id).validatebox("validate");
								//	$("#"+divid1+"-table").hide();
								//	return false;
								//}
								if(keytype=="down"){
									$("#"+divid1).datagrid("selectRow",0);
								}else{
									$("#"+divid1).datagrid("selectRow",data.rows.length-1);
								}
							}
						},
						onClickRow:function(rowIndex, rowData){
							if(options.singleSelect){
								var objectStr = JSON.stringify(rowData);
								$("#"+options.id).val(rowData[json.column.namevalue]);
								$("#"+options.id).attr("title",rowData[json.column.namevalue]);
								$("#"+options.id+"-hidden").val(rowData[json.column.idvalue]);
								$("#"+options.id+"-hidden").attr("object",objectStr);
								options.onSelect(rowData);
								$("#"+options.id).validatebox("validate");
								$("#"+divid1+"-table").hide();
							}else{
								var checkedData = $("#"+divid1).datagrid("getChecked");
								var objectStr = JSON.stringify(checkedData);
								$("#"+options.id+"-hidden").attr("object",objectStr);
								var namearr = "";
								var valuearr = "";
								for(var i=0;i<checkedData.length;i++){
									if(namearr==""){
										namearr = checkedData[i][json.column.namevalue];
										valuearr = checkedData[i][json.column.idvalue];
									}else{
										namearr += ","+checkedData[i][json.column.namevalue];
										valuearr += ","+checkedData[i][json.column.idvalue];
									}
								}
								$("#"+options.id).val(namearr);
								$("#"+options.id).attr("title",namearr);
								$("#"+options.id+"-hidden").val(valuearr);
								options.onSelect(rowData);
								$("#"+options.id).validatebox("validate");
								$("#"+options.id).focus();
							}
						},
						onCheck:function(rowIndex, rowData){
							var checkedData = $("#"+divid1).datagrid("getChecked");
							var objectStr = JSON.stringify(checkedData);
							$("#"+options.id+"-hidden").attr("object",objectStr);
							var namearr = "";
							var valuearr = "";
							for(var i=0;i<checkedData.length;i++){
								if(namearr==""){
									namearr = checkedData[i][json.column.namevalue];
									valuearr = checkedData[i][json.column.idvalue];
								}else{
									namearr += ","+checkedData[i][json.column.namevalue];
									valuearr += ","+checkedData[i][json.column.idvalue];
								}
							}
							$("#"+options.id).val(namearr);
							$("#"+options.id).attr("title",namearr);
							$("#"+options.id+"-hidden").val(valuearr);
							options.onSelect(rowData);
							$("#"+options.id).validatebox("validate");
							$("#"+options.id).focus();
						},
						onUncheck:function(rowIndex, rowData){
							if(!options.singleSelect){
								var checkedData = $("#"+divid1).datagrid("getChecked");
								var objectStr = JSON.stringify(checkedData);
								$("#"+options.id+"-hidden").attr("object",objectStr);
								var namearr = "";
								var valuearr = "";
								for(var i=0;i<checkedData.length;i++){
									if(namearr==""){
										namearr = checkedData[i][json.column.namevalue];
										valuearr = checkedData[i][json.column.idvalue];
									}else{
										namearr += ","+checkedData[i][json.column.namevalue];
										valuearr += ","+checkedData[i][json.column.idvalue];
									}
								}
								$("#"+options.id).val(namearr);
								$("#"+options.id).attr("title",namearr);
								$("#"+options.id+"-hidden").val(valuearr);
								options.onUnselect(rowData);
								$("#"+options.id).validatebox("validate");
								$("#"+options.id).focus();
							}else{
								$("#"+options.id).focus();
							}
						},
						onCheckAll:function(rows){
							var checkedData = rows;
							var objectStr = JSON.stringify(checkedData);
							$("#"+options.id+"-hidden").attr("object",objectStr);
							var namearr = "";
							var valuearr = "";
							for(var i=0;i<checkedData.length;i++){
								if(namearr==""){
									namearr = checkedData[i][json.column.namevalue];
									valuearr = checkedData[i][json.column.idvalue];
								}else{
									namearr += ","+checkedData[i][json.column.namevalue];
									valuearr += ","+checkedData[i][json.column.idvalue];
								}
								options.onUnselect(checkedData[i]);
							}
							$("#"+options.id).val(namearr);
							$("#"+options.id).attr("title",namearr);
							$("#"+options.id+"-hidden").val(valuearr);
							$("#"+options.id).focus();
						},
						onUncheckAll:function(){
							var ids = $("#"+options.id+"-hidden").val();
							if(ids!=options.initValue){
								$("#"+options.id).val("");
								$("#"+options.id).attr("title","");
								$("#"+options.id+"-hidden").val("");
								$("#"+options.id+"-hidden").attr("object","");
							}
						}
					});
					$("#"+options.id).focus(function(){
						$("#"+options.id).die("keydown").live("keydown",function(event){
							switch(event.keyCode){
								case 13: //Enter
									var oldvalue = $("#"+options.id).data("oldvalue");
									var value = $("#"+options.id).val();
									$("#"+options.id).data("oldvalue",value);
									if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
										var readonly = $(event.target).attr("readonly");
										if($(event.target).attr("id")==options.id && readonly!="readonly"){
											var max= $("#"+options.id).offset();
											var left = Number(max.left);
											var height = $("#"+options.id).height();
											var top = max.top+height+2;
											var totalHigh = $(document.body).height();
											var newheight = $("#"+divid1+"-table").height();
											if(totalHigh -top<newheight){
												top = max.top-newheight;
												if(top<0){
													top = 0;
												}
											}
											$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
											var idvalueStr = $("#"+options.id+"-hidden").val();
											if(idvalueStr!=""){
												value = $("#"+options.id+"-hidden").val();
											}
											if(idvalueStr==""){
												var newData = [];
												var conFlag = false;
												var conArr = null;
												if(value.indexOf(" ")>=0){
													conFlag = true;
													conArr = value.split(" ");
												}
												for(var z=0;z<json.data.length;z++){
													var tidv = json.data[z][json.column.idvalue]+"";
													var tnamev = json.data[z][json.column.namevalue]+"";
													if(tidv.indexOf(value)>=0 || tnamev.indexOf(value)>=0){
														newData.push(json.data[z]);
													}else if(conFlag && conArr!=null && conArr.length>0){
														var addFlag = true;
														for(var c=0;c<conArr.length;c++){
															var con = conArr[c];
															con = con.replace(" ","");
															if(tnamev.indexOf(con)==-1){
																addFlag = false;
															}
														}
														if(addFlag){
															newData.push(json.data[z]);
														}
													}
												}
												$("#"+divid1).datagrid("loadData",newData);
											}else{
												$("#"+divid1).datagrid("loadData",json.data);
												var idsArr = idvalueStr.split(",");
												for(var i=0;i<idsArr.length;i++){
													var rowIndex = $("#"+divid1).datagrid("getRowIndex",idsArr[i]);
													if(rowIndex>=0){
														$("#"+divid1).datagrid("selectRow",rowIndex);
													}
												}
											}
											$("body").bind("mousedown", function(event){
												if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
													$("#"+divid1+"-table").hide();
												}
											});
										}
									}else{
										if(options.singleSelect){
											var object = $("#"+divid1).datagrid("getSelected");
											var objectStr = JSON.stringify(object);
											$("#"+options.id).val(object[json.column.namevalue]);
											$("#"+options.id).attr("title",object[json.column.namevalue]);
											$("#"+options.id+"-hidden").val(object[json.column.idvalue]);
											$("#"+options.id+"-hidden").attr("object",objectStr);
											options.onSelect(object);
											$("#"+options.id).validatebox("validate");
											$("#"+divid1+"-table").hide();
										}
									}
									return false;
									break;
								case 38:// 上
									var datagridObject =  $("#"+divid1);
									var rowSelected =datagridObject.datagrid("getSelected");
									var rowIndex = 0;
									if(null!=rowSelected){
										rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
										var rows =  datagridObject.datagrid("getRows").length;
										if(rowIndex>0){
											rowIndex = rowIndex-1;
											datagridObject.datagrid("selectRow",rowIndex);
										}
									}else{
										datagridObject.datagrid("selectRow",0);
									}
									break;
								case 40:// 下
									var datagridObject =  $("#"+divid1);
									var rowSelected =datagridObject.datagrid("getSelected");
									var rowIndex = 0;
									if(null!=rowSelected){
										rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
										var rows =  datagridObject.datagrid("getRows").length;
										if(rowIndex<rows-1){
											rowIndex = rowIndex+1;
											datagridObject.datagrid("selectRow",rowIndex);
										}
									}else{
										datagridObject.datagrid("selectRow",0);
									}
									break;
								case 8:	//backspace
									//$(event.target).val("")
									$("#"+options.id+"-hidden").val("");
									$("#"+options.id+"-hidden").attr("object","");
									options.onClear();
									break;
								case 27:
									$("#"+divid1+"-table").hide();
									break;
								default:
									if(event.ctrlKey==1 && (event.keyCode==67 || event.keyCode==17)){
									}else{
										$("#"+options.id+"-hidden").val("");
										$("#"+options.id+"-hidden").attr("object","");
										//options.onClear();
									}
							}
						});
					}).blur(function(){
						$("#"+options.id).die('keydown');
					});

				}else{
					if(!options.singleSelect){
						var selectStr = "";
						for(var i=0;i<json.columnFieldList.length;i++){
							if(selectStr==""){
								selectStr = "<option value='"+json.columnFieldList[i].field+"'>"+json.columnFieldList[i].title+"</option>";
							}else{
								selectStr += "<option value='"+json.columnFieldList[i].field+"'>"+json.columnFieldList[i].title+"</option>";
							}
						}
						$("body").append('<div id="'+divid1+'-toolbar">查询列:<select id="'+divid1+'-querycol" name="querycol">'+selectStr+'</select>查询内容:<input id="'+divid1+'-conetent" type="text" name="content"/><a href="javaScript:void(0);" id="'+divid1+'-query" class="easyui-linkbutton" data-options="plain:true,iconCls:\'icon-search\'">查询</a></div>');
						$("#"+divid1+"-query").linkbutton({
							iconCls: 'icon-search',
							plain:true
						});
						$("#"+divid1+"-query").click(function(){
							var contentVal = $("#"+divid1+"-conetent").val();
							var querycol = $("#"+divid1+"-querycol").val();
							$("#"+divid1).datagrid({
								url:'system/referWindow/getReferWindowData.do',
								pageNumber:1,
								queryParams:{id:json.referid,paramRule:paramRule,col:json.column.idvalue,colname:json.column.namevalue,content:contentVal,querycol:querycol}
							});
						});
					}
					$("#"+divid1).datagrid({
						frozenColumns:[[{field:'ck',checkbox:true,hidden:options.singleSelect}]],
						columns:[json.columnFieldList],
						width:500,
						height:dheitgh,
						method:'post',
						rownumbers:true,
						pagination:true,
						singleSelect:options.singleSelect,
						checkOnSelect:!options.singleSelect,
						selectOnCheck:!options.singleSelect,
						idField:json.column.idvalue,
						data:null,
						pageSize:10,
						toolbar:"#"+divid1+"-toolbar",
						onBeforeLoad:function(){},
						onLoadSuccess:function(data){
							var keytype = $("#"+options.id).data("keytype");
							if(keytype==null){
								keytype = "down";
							}
							if(options.singleSelect && data.rows.length>0){
								var nowvalue = $("#"+divid1+"-hidden").val();
								var textvalue = $("#"+options.id).val();
								//if(data.rows.length==1 && (nowvalue==null || nowvalue=="") &&(textvalue!="")){
								//	var objectStr = JSON.stringify(data.rows[0])
								//	$("#"+options.id).val(data.rows[0][json.column.namevalue]);
								//	$("#"+options.id).attr("title",data.rows[0][json.column.namevalue]);
								//	$("#"+options.id+"-hidden").val(data.rows[0][json.column.idvalue]);
								//	$("#"+options.id+"-hidden").attr("object",objectStr);
								//	options.onSelect(data.rows[0]);
								//	$("#"+options.id).validatebox("validate");
								//	setTimeout(function(){
								//		$("#"+divid1+"-table").hide();
								//	},100);
								//}
								if(keytype=="down"){
									$("#"+divid1).datagrid("selectRow",0);
								}else{
									$("#"+divid1).datagrid("selectRow",data.rows.length-1);
								}
							}
						},
						onClickRow:function(rowIndex, rowData){
							if(options.singleSelect){
								var objectStr = JSON.stringify(rowData);
								$("#"+options.id).val(rowData[json.column.namevalue]);
								$("#"+options.id).attr("title",rowData[json.column.namevalue]);
								$("#"+options.id+"-hidden").val(rowData[json.column.idvalue]);
								$("#"+options.id+"-hidden").attr("object",objectStr);
								options.onSelect(rowData);
								$("#"+options.id).validatebox("validate");
								$("#"+divid1+"-table").hide();
							}else{
								var checkedData = $("#"+divid1).datagrid("getChecked");
								var objectStr = JSON.stringify(checkedData);
								$("#"+options.id+"-hidden").attr("object",objectStr);
								var namearr = "";
								var valuearr = "";
								for(var i=0;i<checkedData.length;i++){
									if(namearr==""){
										namearr = checkedData[i][json.column.namevalue];
										valuearr = checkedData[i][json.column.idvalue];
									}else{
										namearr += ","+checkedData[i][json.column.namevalue];
										valuearr += ","+checkedData[i][json.column.idvalue];
									}
								}
								$("#"+options.id).val(namearr);
								$("#"+options.id).attr("title",namearr);
								$("#"+options.id+"-hidden").val(valuearr);
								options.onSelect(rowData);
								$("#"+options.id).validatebox("validate");
								$("#"+options.id).focus();
							}
						},
						onCheck:function(rowIndex, rowData){
							var checkedData = $("#"+divid1).datagrid("getChecked");
							var objectStr = JSON.stringify(checkedData);
							$("#"+options.id+"-hidden").attr("object",objectStr);
							var namearr = "";
							var valuearr = "";
							for(var i=0;i<checkedData.length;i++){
								if(namearr==""){
									namearr = checkedData[i][json.column.namevalue];
									valuearr = checkedData[i][json.column.idvalue];
								}else{
									namearr += ","+checkedData[i][json.column.namevalue];
									valuearr += ","+checkedData[i][json.column.idvalue];
								}
							}
							$("#"+options.id).val(namearr);
							$("#"+options.id).attr("title",namearr);
							$("#"+options.id+"-hidden").val(valuearr);
							options.onSelect(rowData);
							$("#"+options.id).validatebox("validate");
							$("#"+options.id).focus();
						},
						onUncheck:function(rowIndex, rowData){
							if(!options.singleSelect){
								var checkedData = $("#"+divid1).datagrid("getChecked");
								var objectStr = JSON.stringify(checkedData);
								$("#"+options.id+"-hidden").attr("object",objectStr);
								var namearr = "";
								var valuearr = "";
								for(var i=0;i<checkedData.length;i++){
									if(namearr==""){
										namearr = checkedData[i][json.column.namevalue];
										valuearr = checkedData[i][json.column.idvalue];
									}else{
										namearr += ","+checkedData[i][json.column.namevalue];
										valuearr += ","+checkedData[i][json.column.idvalue];
									}
								}
								$("#"+options.id).val(namearr);
								$("#"+options.id).attr("title",namearr);
								$("#"+options.id+"-hidden").val(valuearr);
								options.onUnselect(rowData);
								$("#"+options.id).validatebox("validate");
								$("#"+options.id).focus();
							}else{
								$("#"+options.id).focus();
							}
						},
						onCheckAll:function(rows){
							var checkedData = rows;
							var objectStr = JSON.stringify(checkedData);
							$("#"+options.id+"-hidden").attr("object",objectStr);
							var namearr = "";
							var valuearr = "";
							for(var i=0;i<checkedData.length;i++){
								if(namearr==""){
									namearr = checkedData[i][json.column.namevalue];
									valuearr = checkedData[i][json.column.idvalue];
								}else{
									namearr += ","+checkedData[i][json.column.namevalue];
									valuearr += ","+checkedData[i][json.column.idvalue];
								}
								options.onUnselect(checkedData[i]);
							}
							$("#"+options.id).val(namearr);
							$("#"+options.id).attr("title",namearr);
							$("#"+options.id+"-hidden").val(valuearr);
							$("#"+options.id).focus();
						},
						onUncheckAll:function(){
							var ids = $("#"+options.id+"-hidden").val();
							if(ids!=options.initValue){
								$("#"+options.id).val("");
								$("#"+options.id).attr("title","");
								$("#"+options.id+"-hidden").val("");
								$("#"+options.id+"-hidden").attr("object","");
							}
						}
					});
					$("#"+options.id+"-clear").click(function(){
						$("#"+options.id).val("");
						$("#"+options.id+"-hidden").val("");
						$("#"+divid1).datagrid("clearSelections");
						$("#"+divid1).datagrid("clearChecked");
						$("#"+options.id+"-hidden").attr("object","");
						options.onClear();
						$("#"+divid1).datagrid("uncheckAll");
					});
					if(!options.readonly && !options.disabled){
						var inputid = options.id;
						var initwidthlen = $("#"+options.id).width();
						$("."+options.id+"class").mouseover(function(){
							var readonlyStr = $("#"+inputid).attr("readonly");
							if(readonlyStr =='readonly' || readonlyStr==true){
								return false;
							}
							var disabledstr = $("#"+inputid).attr("disabled");
							if(disabledstr=="disabled" || disabledstr==true){
								return false;
							}
							$("#"+options.id).width(initwidthlen-25);
							$("#"+options.id+"-clear").show();
						}).mouseout(function(){
							var readonlyStr = $("#"+inputid).attr("readonly");
							if(readonlyStr =='readonly' || readonlyStr==true){
								return false;
							}
							var disabledstr = $("#"+inputid).attr("disabled");
							if(disabledstr=="disabled" || disabledstr==true){
								return false;
							}
							var vobject = $(this).find(".validatebox-invalid");
							if(vobject.length==0){
								$("#"+options.id).width(initwidthlen-5);
								$("#"+options.id+"-clear").hide();
							}
						});
					}
					$("#"+options.id).focus(function(){
						$("#"+options.id).die("keydown").live("keydown",function(event){
							switch(event.keyCode){
								case 13: //Enter
									var oldvalue = $("#"+options.id).data("oldvalue");
									var value = $("#"+options.id).val();
									$("#"+options.id).data("oldvalue",value);
									if($("#"+divid1+"-table").is(":hidden") || oldvalue!=value){
										var readonly = $(event.target).attr("readonly");
										if($(event.target).attr("id")==options.id && readonly!="readonly"){
											var idvalueStr = $("#"+options.id+"-hidden").val();
											if(idvalueStr!=""){
												value = $("#"+options.id+"-hidden").val();
											}
											var loaddata = $("#"+divid1).datagrid("getData");
											if((idvalueStr==null || idvalueStr=="") || (loaddata.total==0)){
												$("#"+divid1).datagrid("uncheckAll");
												$("#"+divid1).datagrid({
													url:'system/referWindow/getReferWindowData.do',
													pageNumber:1,
													queryParams:{id:json.referid,paramRule:paramRule,col:json.column.idvalue,colname:json.column.namevalue,content:value}
												});
											}
											$("#"+options.id).data("keytype","down");
											var max= $("#"+options.id).offset();
											var left = Number(max.left);
											var height = $("#"+options.id).height();
											var top = max.top+height+2;
											var totalHigh = $(document.body).height();
											var newheight = $("#"+divid1+"-table").height();
											if(totalHigh -top<newheight){
												top = max.top-newheight;
												if(top<0){
													top = 0;
												}
											}
											$("#"+divid1+"-table").css({left:left + "px", top:top + "px"}).show();
											$("body").bind("mousedown", function(event){
												if (!(event.target.id == divid1 || event.target.id == divid1+"-table" || $(event.target).parents("#"+divid1+"-table").length>0)) {
													$("#"+divid1+"-table").hide();
												}
											});
										}
									}else{
										if(options.singleSelect){
											var object = $("#"+divid1).datagrid("getSelected");
											var objectStr = JSON.stringify(object);
											$("#"+options.id).val(object[json.column.namevalue]);
											$("#"+options.id).attr("title",object[json.column.namevalue]);
											$("#"+options.id+"-hidden").val(object[json.column.idvalue]);
											$("#"+options.id+"-hidden").attr("object",objectStr);
											options.onSelect(object);
											$("#"+options.id).validatebox("validate");
											$("#"+divid1+"-table").hide();
										}
									}
									return false;
									break;
								case 8:	//backspace
									//$(event.target).val("")
									$("#"+options.id+"-hidden").val("");
									$("#"+options.id+"-hidden").attr("object","");
									options.onClear();
									break;
								case 38:// 上
									var datagridObject =  $("#"+divid1);
									var rowSelected =datagridObject.datagrid("getSelected");
									var rowIndex = 0;
									if(null!=rowSelected){
										rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
										var rows =  datagridObject.datagrid("getRows").length;
										if(rowIndex>0){
											rowIndex = rowIndex-1;
											datagridObject.datagrid("selectRow",rowIndex);
										}else{
											var p = datagridObject.datagrid('getPager');
											var pageObject = $(p).pagination("options");
											if(pageObject.pageNumber>1){
												$(p).pagination("select",pageObject.pageNumber-1);
												$("#"+options.id).data("keytype","up");
											}
										}
									}else{
										datagridObject.datagrid("selectRow",0);
									}
									break;
								case 40:// 下
									var datagridObject =  $("#"+divid1);
									var rowSelected =datagridObject.datagrid("getSelected");
									var rowIndex = 0;
									if(null!=rowSelected){
										rowIndex =  datagridObject.datagrid("getRowIndex",rowSelected);
										var rows =  datagridObject.datagrid("getRows").length;
										if(rowIndex<rows-1){
											rowIndex = rowIndex+1;
											datagridObject.datagrid("selectRow",rowIndex);
										}else{
											var p = datagridObject.datagrid('getPager');
											var pageObject = $(p).pagination("options");
											var nums = pageObject.total%pageObject.pageSize;
											var pages = 0;
											if(nums>0){
												pages = (pageObject.total-nums)/pageObject.pageSize+1;
											}else{
												pages = (pageObject.total-nums)/pageObject.pageSize;
											}
											if(pageObject.pageNumber<pages){
												$(p).pagination("select",pageObject.pageNumber+1);
												$("#"+options.id).data("keytype","down");
											}
										}
									}else{
										datagridObject.datagrid("selectRow",0);
									}
									break;
								case 37:  //左
									var p = $("#"+divid1).datagrid('getPager');
									var pageObject = $(p).pagination("options");
									if(pageObject.pageNumber>1){
										$(p).pagination("select",pageObject.pageNumber-1);
										$("#"+options.id).data("keytype","up");
									}
									break;
								case 39://右
									var p = $("#"+divid1).datagrid('getPager');
									var pageObject = $(p).pagination("options");
									var nums = pageObject.total%pageObject.pageSize;
									var pages = 0;
									if(nums>0){
										pages = (pageObject.total-nums)/pageObject.pageSize+1;
									}else{
										pages = (pageObject.total-nums)/pageObject.pageSize;
									}
									if(pageObject.pageNumber<pages){
										$(p).pagination("select",pageObject.pageNumber+1);
										$("#"+options.id).data("keytype","down");
									}
									break;
								case 27:
									$("#"+divid1+"-table").hide();
									break;
								default:
									if(event.ctrlKey==1 && (event.keyCode==67 || event.keyCode==17)){
									}else{
										$("#"+options.id+"-hidden").val("");
										$("#"+options.id+"-hidden").attr("object","");
										//options.onClear();
									}
							}
						});
					}).blur(function(){
						$("#"+options.id).die('keydown');
					});
				}
				if(options.required){
					$("#"+options.id).validatebox("isValid");
				}
			};
			setTreeData = function(json,options){
				var state = "combotree";
				var paramRule = "";
				if(null!=options.param){
					paramRule = JSON.stringify(options.param);
				}
				var name = $("#"+options.id).attr("name");
				$("#"+options.id).replaceWith('<div id="'+options.id+'-div" class="treewidget" style="width:'+(options.width-2)+'px;"><input type="text" id="'+options.id+'" class="widgetTreeInput"><div>');
				$("#"+options.id).attr("getValueType","combotree");
				if(name==""){
					name =  $("#"+options.id).attr("input-name");
				}else{
					$("#"+options.id).attr("name","");
					$("#"+options.id).attr("input-name",name);
				}
				//生成随机id
				var aid =options.id+parseInt(100*Math.random());

				var url = 'system/referWindow/showReferWindow.do?id='+json.referid+'&vid='+options.id+'&state='+state+'&singleSelect='+options.singleSelect+'&paramRule='+paramRule+'&onlyLeafCheck='+options.onlyLeafCheck;
				$("#"+options.id).after('<a id="'+aid+'cancel" class="'+options.id+'-widget-a widgetclear" href="javaScript:widgetCancel(\''+options.id+'\',\''+aid+'\',\''+json.model+'\');">&nbsp;</a>');
				$("#"+options.id).data("cancel", aid+"cancel");
				$("#"+options.id).data("model", json.model);
				$("#"+aid+"cancel").before('<span id="'+options.id+'-treewidgetarr" class="treewidget-arr" href="javaScript:void(0)">&nbsp;&nbsp;</span>');
				var data = json.data;
				$("#"+options.id).css("width",options.width-20);
				$("#"+options.id).data("treeid",aid);
				$("#"+options.id).data("cancel", aid);
				if(options.required){
					$("#"+options.id).validatebox({
						required:true
					});
				}
				options.aid = aid;
				$("#"+options.id).attr("readonly","readonly");
				$("#"+options.id).after('<input type="hidden" class="'+options.id+'-widget-hidden" id="'+options.id+'-hidden" name="'+name+'" widget="tree" widgetname="'+name+'"/>');
				var treeWidth = 200;
				if(treeWidth<options.width){
					treeWidth = options.width;
				}
				$('<div id="'+aid+'TreeContent" class="widgettree pulldown-div '+options.id+'-widget-hidden" style="display:none;border: 1 solid #959595; position: absolute;width:'+treeWidth+'px;height:300px;overflow-y: auto;overflow-x: auto;"><ul id="'+aid+'Tree" class="ztree" style="margin-top:0; width:'+(options.width-10)+'px;"></ul></div>').appendTo("body");
				//是否禁用控件
				if(options.disabled){
					$("#"+options.id).attr("disabled","disabled");
					$("#"+aid+"cancel").hide();
				}else{
					if(options.readonly){
						$("#"+options.id).attr("readonly",true);
						$("#"+options.id).data("readonly",true);
						$("#"+aid+"cancel").hide();
					}else{
						$("#"+options.id).attr("readonly",false);
					}
				}
				var ztree = data;
				var initnames = "";
				//初始化默认值
				if(options.initValue!=""){
					var selectFlag = $("#"+options.id).attr("setValueSelect");
					if(selectFlag==false){
						$("#"+options.id+"-hidden").data("setValueSelectDo",true);
					}
					if(options.singleSelect){
						var node = null;
						//树状初始化 单选框选中
						for(var i=0;i<ztree.length;i++){
							if(options.initValue==ztree[i].id){
								ztree[i].checked=true;
								initnames = ztree[i].name;
								node = ztree[i];
								break;
							}
						}
						if(node !=null){
							//树状选中展开
							for(var i=0;i<ztree.length;i++){
								if(node.pId==ztree[i].id){
									ztree[i].open=true;
									break;
								}
							}
						}
					}else{
						var initValueArr = options.initValue.split(",");
						var nodeArr = new Array();
						//树状初始化 复选框选中
						for(var i=0;i<initValueArr.length;i++){
							for(var j=0;j<ztree.length;j++){
								if(initValueArr[i]==ztree[j].id){
									ztree[j].checked=true;
									if(i==0){
										initnames = ztree[j].name;
									}else{
										initnames += ","+ztree[j].name;
									}
									nodeArr.push(ztree[j]);
									break;
								}
							}
						}
						//树状选中展开
						for(var i=0;i<nodeArr.length;i++){
							for(var j=0;j<ztree.length;j++){
								if(nodeArr[i].pid==ztree[j].id){
									ztree[j].open=true;
									break;
								}
							}
						}
					}
					$("#"+options.id).attr("value",initnames);
					$("#"+options.id+"-hidden").attr("value",options.initValue);
				}

				if(options.onlyParentCheck){
					//是否只能选择父节点
					for(var i=0;i<ztree.length;i++){
						if("false"==ztree[i].isParent){
							ztree[i].nocheck=true;
						}
					}
				}else{
					//是否只能选择子节点
					if(options.onlyLeafCheck){
						for(var i=0;i<ztree.length;i++){
							if("true"==ztree[i].isParent){
								ztree[i].nocheck=true;
							}
						}
					}
				}
				if(options.treeNodeDataUseNocheck){
					for(var i=0;i<ztree.length;i++){
						if(ztree[i].treenodenocheck!=null){
							if("1"==ztree[i].treenodenocheck || "true"==ztree[i].treenodenocheck){
								ztree[i].nocheck=true;
							}
						}
					}
				}
				var chkStyle = "checkbox";
				if(options.singleSelect){
					chkStyle = "radio";
				}
				var ztreeSet = {
					view: {
						dblClickExpand: true
					},
					check: {
						enable: true,
						chkStyle: chkStyle,
						chkboxType: { "Y": "s", "N": "s" },
						radioType:'all'
					},
					data: {
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "pId",
							rootPId : ""
						}
					},
					callback: {
						beforeClick: function(treeId, treeNode){
							var check = (treeNode && !treeNode.isParent);
							if(options.onlyLeafCheck && !check){
								return false;
							}else if(treeNode.id==""){
								return false;
							}else{
								return true;
							}
						},
						onClick: function(e, treeId, treeNode){
							//点击节点 选中
							var treeObj = $.fn.zTree.getZTreeObj(aid+"Tree");
							treeObj.checkNode(treeNode, !treeNode.checked, null, options.onSelect(treeNode));
							//获取选中的节点 并且更新控件的值
							var nodes = treeObj.getCheckedNodes(true);
							var names = "";
							var values = "";
							for(var i=0;i<nodes.length;i++){
								names += nodes[i].name+",";
								values += nodes[i].id+",";
							}
							if (values.length > 0 ){
								values = values.substring(0, values.length-1);
							}
							if (names.length > 0 ){
								names = names.substring(0, names.length-1);
							}
							$("#"+options.id).attr("value",names);
							$("#"+options.id+"-hidden").attr("value",values);
							$("#"+options.id+"-hidden").data("object",nodes);

							options.onChecked(treeNode,treeNode.checked);
						},
						onCheck:function(e, treeId, treeNode){
							//选中节点后 并且选择节点
							var treeObj = $.fn.zTree.getZTreeObj(aid+"Tree");
							var nodes = treeObj.getCheckedNodes(true);
							var names = "";
							var values = "";
							for(var i=0;i<nodes.length;i++){
								names += nodes[i].name+",";
								values += nodes[i].id+",";
							}
							if (values.length > 0 ){
								values = values.substring(0, values.length-1);
							}
							if (names.length > 0 ){
								names = names.substring(0, names.length-1);
							}
							$("#"+options.id).attr("value",names);
							$("#"+options.id+"-hidden").attr("value",values);
							$("#"+options.id+"-hidden").data("object",nodes);

							if(treeNode.checked){
								treeObj.selectNode(treeNode);
								var selectFlag = $("#"+options.id+"-hidden").data("setValueSelectDo");
								if(selectFlag){
									$("#"+options.id+"-hidden").data("setValueSelectDo",false);
								}else{
									options.onSelect(treeNode);
									options.onChecked(treeNode,true);
								}
							}else{
								var selectFlag = $("#"+options.id+"-hidden").data("setValueSelectDo");
								if(selectFlag){
									$("#"+options.id+"-hidden").data("setValueSelectDo",false);
								}else{
									options.onSelect(treeNode);
									options.onChecked(treeNode,false);
								}
							}
						}
					}
				};
				//初始化树
				$.fn.zTree.init($("#"+aid+"Tree"), ztreeSet, ztree);
				//树为单选时
				if(options.singleSelect){
					var treeObj = $.fn.zTree.getZTreeObj(aid+"Tree");
					var selectNodes = treeObj.getCheckedNodes(true);
					for(var i=0;i<selectNodes.length;i++){
						treeObj.selectNode(selectNodes[i]);
					}
				}
				$("#"+options.id+"-treewidgetarr").click(function(){
					var obj = $("#"+options.id);
					var readonly = $("#"+options.id).data("readonly");
					if(readonly){
						return false;
					}
					var disableFlag = $("#"+options.id).attr("disabled");
					if(disableFlag=="disabled"){
						return false;
					}
					var offset = $("#"+options.id).offset();
					var top = offset.top;
					var totalHigh = $(document.body).height();
					if(totalHigh -top<200){
						top = top-220;
					}
					var zIndex = $.fn.window.defaults.zIndex++;
					$("#"+aid+"TreeContent").css("z-index",zIndex);
					$("#"+aid+"TreeContent").css({left:offset.left + "px", top:top + obj.outerHeight() + "px"}).slideDown("fast");
					$("body").bind("mousedown", onBodyDown);
				});
				$("#"+options.id).focus(function(){
					var obj = $("#"+options.id);
					var readonly = $("#"+options.id).data("readonly");
					if(readonly){
						return false;
					}
					var disableFlag = $("#"+options.id).attr("disabled");
					if(disableFlag=="disabled"){
						return false;
					}
					var offset = $("#"+options.id).offset();
					var top = offset.top;
					var totalHigh = $(document.body).height();
					if(totalHigh -top<200){
						top = top-220;
					}
					var zIndex = $.fn.window.defaults.zIndex++;
					$("#"+aid+"TreeContent").css("z-index",zIndex);
					$("#"+aid+"TreeContent").css({left:offset.left + "px", top:top + obj.outerHeight() + "px"}).slideDown("fast");
					$("body").bind("mousedown", onBodyDown);
				}).mouseover(function(){
					var readonly = $("#"+options.id).data("readonly");
					if(readonly){
						return false;
					}
					$("#"+aid+"cancel").show();
					$("#"+aid+"cancel").css("display","inline-block");
					$("#"+options.id).css("width",options.width-40);
				}).mouseout(function(){
					var readonly = $("#"+options.id).data("readonly");
					if(readonly){
						return false;
					}
					$("#"+aid+"cancel").hide();
					$("#"+options.id).css("width",options.width-20);
				});
				$("#"+aid+"cancel").mouseover(function(){
					var readonly = $("#"+options.id).data("readonly");
					if(readonly){
						return false;
					}
					$("#"+aid+"cancel").show();
					$("#"+aid+"cancel").css("display","inline-block");
					$("#"+options.id).css("width",options.width-40);
				}).mouseout(function(){
					var readonly = $("#"+options.id).data("readonly");
					if(readonly){
						return false;
					}
					$("#"+aid+"cancel").hide();
					$("#"+options.id).css("width",options.width-20);
				});
				$("#"+options.id).die("keydown").live("keydown",function(event){
					switch(event.keyCode){
						case 13: //Enter
							var val = $(this).val();
							var valHidden = $("#"+options.id+"-hidden").val();
							if(valHidden==""){
								var treeObj = $.fn.zTree.getZTreeObj(aid+"Tree");
								var hidenodes = treeObj.getNodesByParam("isHidden", true);
								if(hidenodes.length>0){
									treeObj.showNodes(hidenodes);
								}
								var nodes = treeObj.transformToArray(treeObj.getNodes());
								for(var i=0;i<nodes.length;i++){
									if(!nodes[i].isParent){
										if(nodes[i].name.indexOf(val)>=0){
											treeObj.showNode(nodes[i]);
										}else{
											treeObj.hideNode(nodes[i]);
										}
									}
								}
								treeObj.expandAll(true);

								var obj = $("#"+options.id);
								var readonly = $("#"+options.id).data("readonly");
								if(readonly){
									return false;
								}
								var offset = $("#"+options.id).offset();
								var top = offset.top;
								var totalHigh = $(document.body).height();
								if(totalHigh -top<200){
									top = top-220;
								}
								var zIndex = $.fn.window.defaults.zIndex++;
								$("#"+aid+"TreeContent").css("z-index",zIndex);
								$("#"+aid+"TreeContent").css({left:offset.left + "px", top:top + obj.outerHeight() + "px"}).slideDown("fast");
								$("body").bind("mousedown", onBodyDown);
							}
							break;
					}
				});
				$("#"+options.id).attr("aid",aid);
				$("#"+options.id).data("treeOnSelect",options.onSelect);
				$("#"+options.id).data("treeOnChecked",options.onChecked);
				if(options.required){
					$("#"+options.id).validatebox("isValid");
				}
			};
			this.each(function() {
				options.id = $(this).attr("id");
				var value = $(this).val();
				var text = $(this).attr("text");
				if(value!=null&&value!=""){
					options.initValue = value;
					$("#"+options.id).attr("value","");
				}
				var paramRule = "";
				if(null!=options.param){
					paramRule = JSON.stringify(options.param);
				}
				//获取input中的disabled值 判断是否禁用控件
				var disabled = $(this).attr("disabled");
				if(disabled!=null&&disabled=="disabled"){
					options.view = true;
					options.disabled = true;
				}else if(options.disabled){
					options.view = true;
					$(this).attr("disabled","disabled");
				}
				var readonly = $(this).attr("readonly");
				if(readonly!=null&&readonly=="readonly"){
					options.readonly = true;
				}

				var widgetnamehidden = "";
				if($("#"+options.id+"-hidden").attr("widgetname")!=undefined){
					var widgetname = $("#"+options.id+"-hidden").attr("widgetname");
					var widget = $("#"+options.id+"-hidden").attr("widget");
					if(widget=="combogrid"){
						$("."+options.id+"class").after("<input type='text' id='"+options.id+"' name='"+widgetname+"' />");
					}else if(widget=="tree"){
						$("#"+options.id+"-hidden").val("");
						$("#"+options.id).val("");
					}
					widgetnamehidden = widgetname;
				}
				$("."+options.id+"-widget-a").remove();
				$("."+options.id+"-widget-hidden").remove();
				$("."+options.id+"-widget-span").remove();
				$("."+options.id+"class").remove();

				//绑定选中事件
				$("#"+options.id).after('<span id="widgetData-'+options.id+'" class="'+options.id+'-widget-span"></span>');
				$("#widgetData-"+options.id).data("options", options);
				var isAllSelect = "0";
				if(!options.singleSelect&&options.allSelect){
					isAllSelect = "1";
				}
				//当直接指定参照窗口后
				if(null!=options.referwid){
					$.ajax({
						url :'system/referWindow/getReferWindowWidget.do',
						type:'post',
						dataType:'json',
						data:{id:options.referwid,paramRule:paramRule,view:options.view,treeDistint:options.treeDistint,treePName:options.treePName,treeNodeDataUseNocheck:options.treeNodeDataUseNocheck,initValue:options.initValue,listnum:options.listnum,isAllSelect:isAllSelect},
						async:false,
						success:function(json){
							if(json.model=='none'){
								return false;
							}
							//获取值方式
							$("#"+options.id).attr("getValueType","combogrid");
							var state = "combo";
							if(json.model=='tree'){
								state = "combotree";
								$("#"+options.id).attr("getValueType","combotree");
							}
							if(json.model=='normal'){
								setDatagrid(json,options);
							}else if(json.model=='tree'){
								setTreeData(json,options);
							}
							options.onLoadSuccess();
						}
					});
				}else{
					$.ajax({
						url :'common/getWidget.do',
						type:'post',
						dataType:'json',
						data:{name:options.name,col:options.col,paramRule:paramRule,view:options.view,treeDistint:options.treeDistint,treePName:options.treePName,treeNodeDataUseNocheck:options.treeNodeDataUseNocheck,initValue:options.initValue,listnum:options.listnum,isAllSelect:isAllSelect},
						async:false,
						success:function(json){
							if(json.type=='code'){
								var widgetName = $("#"+options.id).attr("name");
								var selectHtml = '<select  name="'+widgetName+'" id="'+options.id+'" style="width:'+options.width+'px">';
								var initValue = "";
								if(options.initSelectNull){
									selectHtml += '<option value="" selected="selected"></option>';
									initValue = "";
								}else{
									if(json.data.length>0 && initValue==""){
										initValue = json.data[0].id;
									}
								}
								for(var i=0;i<json.data.length;i++){
									if(options.initValue==json.data[i].id){
										initValue = json.data[i].id;
										selectHtml += '<option value="'+json.data[i].id+'" selected="selected">'+json.data[i].name+'</option>';
									}else{
										selectHtml += '<option value="'+json.data[i].id+'">'+json.data[i].name+'</option>';
									}
								}

								selectHtml +="</select>";
								//selectHtml +='<input id="'+options.id+'-Hidden" type="hidden" name="'+widgetName+'" value="'+initValue+'"/>';
								$("#"+options.id).replaceWith(selectHtml);
								//获取值方式
								$("#"+options.id).attr("getValueType","select");
								$("#"+options.id).live("change",function(){
									var value = $(this).val();
									var name = $(this).find("option:selected").text();
									var data = {id:value,name:name};
									options.onSelect(data);

									$("#"+options.id+"-Hidden").val(value);
								});
								if(options.required){
									$("#"+options.id).validatebox({
										required:true
									});
								}
								//判断是否禁用控件
								if(options.disabled){
//			            			$("#"+options.id).width(options.width);
									$("#"+options.id).attr("disabled","disabled");
								}
								if(options.readonly){
									$("#"+options.id).attr("disabled","disabled");
									$('<input type="hidden" id="'+options.id+'-hidden" name="'+widgetName+'" value="'+initValue+'"/>').insertAfter($("#"+options.id));
								}
							}else if(json.type=='refer'){
								if(json.model=='none'){
									return false;
								}
								//获取值方式
								$("#"+options.id).attr("getValueType","combogrid");
								var state = "combo";
								if(json.model=='tree'){
									state = "combotree";
									$("#"+options.id).attr("getValueType","combotree");
								}
								if(json.model=='normal'){
									setDatagrid(json,options);
								}else if(json.model=='tree'){
									setTreeData(json,options);
								}
							}else if(json.type=='normal'){
								$("#"+options.id).keydown(function(event){
									if(event.keyCode==13){
										return false;
									}
								});
								$("#"+options.id).css("width",options.width);
								//获取值方式
								$("#"+options.id).attr("getValueType","normal");
								//初始化默认值
								if(options.initValue!=""){
									$("#"+options.id).attr("value",options.initValue);
								}
							}else{
								$("#"+options.id).keydown(function(event){
									if(event.keyCode==13){
										return false;
									}
								});
								//获取值方式
								$("#"+options.id).attr("getValueType","normal");
								//初始化默认值
								if(options.initValue!=""){
									$("#"+options.id).attr("value",options.initValue);
								}
							}
							options.onLoadSuccess();
						}
					});
				}
				//setValue是否激发选中事件
				$("#"+options.id).attr("setValueSelect",options.setValueSelect);

			});
		},
		getObject :function(){
			var id =  $(this).attr("id");
			var getValueType = $("#"+id).attr("getValueType");
			if(getValueType==""||getValueType=="normal"){
				return $("#"+id).val();
			}else if(getValueType=="hidden"){
				var object = $("#hidden-"+id).data("object");
				return object[0];
			}else if(getValueType=="combogrid"){
				var objectStr = $("#"+id+"-hidden").attr("object");
				var object = $.parseJSON(objectStr);
				if(object!=null && object.length>0){
					return object[0];
				}else{
					return object;
				}
			}else if(getValueType=="combotree"){	//不支持树状结构获取数据对象
				var nodes = $("#"+id+"-hidden").data("object");
				if(null!=nodes && nodes.length>0){
					return nodes[0];
				}else{
					return null;
				}
			}else if(getValueType=="select"){
				var dataid = $("#"+id).val();
				var name = $("#"+id).find("option:selected").text();
				var data = {id:dataid,name:name};
				return data;
			}
		},
		getObjects :function(){
			var id =  $(this).attr("id");
			var getValueType = $("#"+id).attr("getValueType");
			if(getValueType==""||getValueType=="normal"){
				return $("#"+id).val();
			}else if(getValueType=="hidden"){
				var object = $("#hidden-"+id).data("object");
				return object;
			}else if(getValueType=="combogrid"){
				var objectStr = $("#"+id+"-hidden").attr("object");
				var object = $.parseJSON(objectStr);
				return object;
			}else if(getValueType=="combotree"){
				return $("#"+id+"-hidden").data("object");
			}else if(getValueType=="select"){
				var dataid = $("#"+id).val();
				var name = $("#"+id).find("option:selected").text();
				var data = {id:dataid,name:name};
				return [data];
			}
		},
		getValue : function(){
			var id =  $(this).attr("id");
			var getValueType = $("#"+id).attr("getValueType");
			if(getValueType==""||getValueType=="normal"){
				return $("#"+id).val();
			}else if(getValueType=="hidden"){
				return $("#hidden-"+id).val();
			}else if(getValueType=="combogrid"){
				return $("#"+id+"-hidden").val();
			}else if(getValueType=="combotree"){
				return $("#"+id+"-hidden").val();
			}else if(getValueType=="select"){
				var dataid = $("#"+id).val();
				return dataid;
			}
		},
		getText:function(){
			var id =  $(this).attr("id");
			var getValueType = $("#"+id).attr("getValueType");
			if(getValueType==""||getValueType=="normal"){
				return $("#"+id).val();
			}else if(getValueType=="hidden"){
				return $("#"+id).val();
			}else if(getValueType=="combogrid"){
				return $("#"+id).val();
			}else if(getValueType=="combotree"){
				return $("#"+id).val();
			}else if(getValueType=="select"){
				var name = $("#"+id).find("option:selected").text();
				return name;
			}
		},
		clear:function(){
			var id =  $(this).attr("id");
			var cancelid = $("#"+id).data("cancel");
			var model = $("#"+id).data("model");
			widgetCancel(id,cancelid,model);
		},
		setValue:function(value){
			var id =  $(this).attr("id");
			var aid = $(this).attr("aid");
			var selectFlag = $("#"+id).attr("setValueSelect");
			if(selectFlag==false){
				$("#"+id+"-hidden").data("setValueSelectDo",true);
			}
			var getValueType = $("#"+id).attr("getValueType");
			if(getValueType==""||getValueType=="normal"){
				$("#"+id).val(value);
			}else if(getValueType=="hidden"){
				$("#"+id).val(value);
			}else if(getValueType=="combogrid"){
				$("#"+id).attr("settype","setValue");
				var url = $("#"+id).data("url");
				var queryparam = $("#"+id).data("queryParams");
				if(null!=queryparam){
					queryparam.idcol = queryparam.col;
					queryparam.initvalue = value;
					$.ajax({
						url :url,
						type:'post',
						dataType:'json',
						data:queryparam,
						success:function(json){
							if(json.rows.length==1){
								var object =json.rows[0];
								var objectStr = JSON.stringify(object);
								$("#"+id).val(json.rows[0][queryparam.colname]);
								$("#"+id).attr("title",json.rows[0][queryparam.col]);
								$("#"+id+"-hidden").val(json.rows[0][queryparam.col]);
								$("#"+id+"-hidden").attr("object",objectStr);
								var onSelect = $("#"+id).data("comgridonSelect");
								if(null!=onSelect){
									if(selectFlag && selectFlag != 'false'){
										onSelect(object);
									}
								}

							}
						}
					});
				}
			}else if(getValueType=="combotree"){
				if(value !=null && value!=""){
					var aid = $("#"+id).data("treeid");
					var treeObj = $.fn.zTree.getZTreeObj(aid+"Tree");
					var valArr = value.split(",");
					var nodes = [];
					var names = "";
					for(var i=0;i<=valArr.length;i++){
						var val = valArr[i];
						var node = treeObj.getNodeByParam("id", val, null);
						var onSelect = $("#"+id).data("treeOnSelect");
						var onChecked = $("#"+id).data("treeOnChecked");
						if(node!=null){
							treeObj.selectNode(node);
							treeObj.checkNode(node, true, false);
							if(names==""){
								names = node.name;
							}else{
								names += ","+node.name;
							}
							onSelect(node);
							onChecked(node,true);
							nodes.push(node);
						}
					}
					$("#"+id+"-hidden").attr("value",value);
					if(valArr.length>1){
						$("#"+id).val(names);
						$("#"+id+"-hidden").data("object",nodes);
					}else{
						$("#"+id).val(nodes[0].name);
						$("#"+id+"-hidden").data("object",nodes[0]);
					}
				}
			}else if(getValueType=="select"){
				$("#"+id).val(value);
			}
		},
		setWidth : function(len){
			var id =  $(this).attr("id");
			var getValueType = $("#"+id).attr("getValueType");
			if(getValueType=="combogrid"){
				len = len -20;
				$("#"+id).width(len);
			}else if(getValueType=="combotree"){
				len = len- 25;
				$("#"+id).css("width",len);
			}else{
				$("#"+id).css("width",len);
			}

		},
		disable:function(){
			var id =  $(this).attr("id");
			var getValueType = $("#"+id).attr("getValueType");
			var aid = $("#"+id).attr("aid");
			if(!this[0].disabled && getValueType=="combogrid"){
				var setwidth = $("#"+id).width()+25;
				$("#"+id).css("width",setwidth);
				$("#"+id).attr("disabled",true);
				$("#"+id+"-hidden").attr("disabled");
				$("#"+id+"-search").hide();
                $("#"+id+"-clear").hide();
			}else if(getValueType=="combotree"){
				$("#"+id).attr("disabled",true);
				$("#"+aid).hide();
			}else{
				$("#"+id).attr("disabled",true);
			}
		},
		enable:function(){
			var id =  $(this).attr("id");
			var getValueType = $("#"+id).attr("getValueType");
			var aid = $("#"+id).attr("aid");
			if(getValueType=="combogrid"){
				var setwidth = $("#"+id).width()-25;
				$("#"+id).css("width",setwidth);
				$("#"+id).attr("disabled",false);
				$("#"+id+"-hidden").attr("disabled",false);
				$("#"+id+"-search").show();
                $("#"+id+"-clear").hide();
			}else if(getValueType=="combotree"){
				$("#"+id).attr("disabled",false);
			}else{
				$("#"+id).attr("disabled",false);
			}
		},
         required:function(flag){
             var id =  $(this).attr("id");
             $("#"+id).validatebox({required:flag});
            setTimeout(function(){
             $("#"+id).validatebox("validate");
            },50);
         },
		readonly1:function(flag){
			var id =  $(this).attr("id");
			$("#"+id).attr("readonly",flag);
			if(flag){
				$("#"+id+"-search").hide();
			}else{
				$("#"+id+"-search").show();
			}
		},
		readonly:function(flag){
			var id =  $(this).attr("id");
			var getValueType = $("#"+id).attr("getValueType");
			var aid = $("#"+id).attr("aid");
			if(getValueType==""||getValueType=="normal"){
				$("#"+id).attr("readonly",flag);
			}else if(getValueType=="hidden"){
				$("#"+id).attr("readonly",flag);
			}else if(getValueType=="combogrid"){
				var oldflag = $("#"+id).attr("readonly");
				$("#"+id).attr("readonly",flag);
				if(flag){
					if(oldflag=="readonly"){
						var setwidth = $("#"+id).width()+23;
						$("#"+id).css("width",setwidth);
					}
					$("#"+id+"-clear").hide();
					$("#"+id+"-search").hide();
				}else{
					var setwidth = $("#"+id).width()-23;
					$("#"+id).css("width",setwidth);
					$("#"+id+"-search").show();
				}
			}else if(getValueType=="combotree"){
				$("#"+id).attr("readonly",flag);
				if(flag){
					$("#"+id).data("readonly",true);
					$("#"+aid+"cancel").hide();
				}else{
					$("#"+id).data("readonly",false);
					$("#"+aid+"cancel").show();
				}
			}else if(getValueType=="select"){
				if(flag){
					$("#"+id).attr("disabled","disabled");
					var name = $("#"+id).attr("name");
					var value = $("#"+id).val();
					$('<input type="hidden" id="'+id+'-hidden" name="'+name+'" value="'+value+'"/>').insertAfter($("#"+id));
				}else{
					$("#"+id).attr("disabled",false);
					$("#"+id+"-hidden").remove();
				}
			}
		}
	};
	$.fn.widget = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(
				arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.tooltip');
		}
	}
})(jQuery);
//通用控件 弹出窗口
var widgetWindow = function(title,aid){
	var url = $("#"+aid).attr("url");
	var wid = getRandomid();
	$("#"+aid).after('<div id="div'+wid+'">');
	$('#div'+wid).window({
		title: title,
		width: 650,
		height: 400,
		closed: true,
		cache: false,
		href: url+'&divid=div'+wid,
		modal: true,
		minimizable:false,
		collapsible:false,
		onClose:function(){
			$('#div'+wid).window("destroy");
		}
	});
	$('#div'+wid).window("open");
};
//清楚控件数据
var widgetCancel = function(id,aid,model){
	var options = $("#widgetData-"+id).data("options");
	if(model==null || model=="normal" ){
		$("#"+id).val("");
		var divid = $("#"+id).data("divid");
		if(null!=divid){
			$("#"+divid).datagrid("clearChecked");
		}
		$("#"+id+"-hidden").val("");
		$("#"+id+"-hidden").attr("object","");
	}else if(model=="tree"){
		var treeObj = $.fn.zTree.getZTreeObj(aid+"Tree");
		var hidenodes = treeObj.getNodesByParam("isHidden", true);
		if(hidenodes.length>0){
			treeObj.showNodes(hidenodes);
			treeObj.expandAll(false);
		}
		if(options.singleSelect){
			var nodes = treeObj.getSelectedNodes();
			for(var i=0;i<nodes.length;i++){
				if(nodes[i].checked){
					treeObj.checkNode(nodes[i], false, null, options.onUnselect());
					treeObj.cancelSelectedNode(nodes[i]);
				}
			}
		}else{
			treeObj.checkAllNodes(false);
		}
		$("#"+options.id).attr("value","");
		$("#"+options.id+"-hidden").attr("value","");
	}
	if($("#"+options.id+"-hidden").size()>0) {
		var objval=$("#" + options.id + "-hidden").data("object");
		if(objval!=null){
			if(objval instanceof Array){
				$("#" + options.id + "-hidden").data("object",[]);
			}else if(objval instanceof Object){
				$("#" + options.id + "-hidden").data("object",{});
			}
		}
	}
	options.onClear();
};

/**
 * from表单序列化成JSON对象
 */
(function($){
	$.fn.serializeJSON=function(){
		var serializeObj={};
		var array=this.serializeArray();
		var str=this.serialize();
		$(array).each(function(){
			if(serializeObj[this.name]){
				if($.isArray(serializeObj[this.name])){
					serializeObj[this.name].push(this.value);
				}else{
					serializeObj[this.name]=[serializeObj[this.name],this.value];
				}
			}else{
				serializeObj[this.name]=this.value;
			}
		});
		return serializeObj;
	}
})(jQuery);

(function () {
	//显示遮罩
	loading = function (msg) {
		if (msg == undefined) {
			msg = "数据提交中，请稍候...";
		}
		var panel = $(document.body);
		$("<div class=\"datagrid-mask\" style='z-index:999998'></div>").css({ display: "block", width: panel.width(), height: panel.height()}).appendTo(panel);
		$("<div class=\"datagrid-mask-msg\" style=\"z-index: 999999;\"></div>").html(msg).appendTo(panel).css({ display: "block", left: (panel.width() - $("div.datagrid-mask-msg", panel).outerWidth()) / 2, top: (panel.height() - $("div.datagrid-mask-msg", panel).outerHeight()) / 2 });
	};
	//隐藏遮罩
	loaded = function () {
		setTimeout(function(){
			var panel = $(document.body);
			panel.find("div.datagrid-mask-msg").remove();
			panel.find("div.datagrid-mask").remove();
		},200);
	}
})(jQuery);

/**
 * from表提交验证
 */
(function($){
	var methods = {
		init : function(options){
			defaults = {
				json:null,
				form:null,
				successMsg:"成功!",
				failMsg:"失败!",

				isSuccess:function(){$.messager.alert("提醒",options.successMsg);},
				isFail:function(){$.messager.alert("提醒",options.failMsg);}
			};
			options = $.extend(defaults,options);

			this.each(function() {
				var id = $(this).attr("id");
				options.form = $("#"+id).form();
				var remind = "";
				if(options.json.msgList){
					var msgList = options.json.msgList;
					for(var i=0;i<msgList.length;i++){
						remind += msgList[i]+"<br/>";
					}
					if(options.json.state=='E'){
						$.messager.alert("错误",remind+"请修改后再进行操作!");
					}else if(options.json.state=='W'){
						$.messager.confirm("警告", remind+"是否继续...?", function(r){
							if (r){
								var url = options.form.attr("action");
								options.form.attr("action",url+"?type=1");
								options.form.submit();
							}
						});
					}
				}else{
					if(options.json.flag==true){
						options.isSuccess();
					}else{
						options.isFail();
					}
				}

			});
		}
	};
	$.fn.validateFormSubmit = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(
				arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.tooltip');
		}
	}
})(jQuery);

/**
 *
 * @requires jQuery,EasyUI
 *
 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列
 */
var createGridHeaderContextMenu = function(e, field) {
	e.preventDefault();
	var grid = $(this);/* grid本身 */
	//判断是否开启表头菜单
	var authority = grid.datagrid("options").authority;
	if(!authority||authority==null){
		return false;
	}
	var showHeaderButton = grid.datagrid('options').showHeaderButton;
	if(showHeaderButton == undefined){
		showHeaderButton = true ;
	}
	var frozenColumnArray = this.frozenColumn;
	var columnArray = this.columnArray;
	var change = true;
	var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
	if (!headerContextMenu||change) {
		var fields = grid.datagrid('getColumnFields');
		var frozenfields = grid.datagrid('getColumnFields',true);

		var tmenu;
		if(Number(fields.length)+Number(frozenfields.length)>10){
			if($.browser.msie){
				tmenu = $('<div style="overflow-y: scroll;width:150px;height:300px;"></div>').appendTo('body');
			}else{
				tmenu = $('<div class="menuContext"></div>').appendTo('body');
			}
		}else{
			tmenu = $('<div style="width:120px;"></div>').appendTo('body');
		}
		var fcolumn = [];
		if(frozenfields.length>0){
			var frozenHtml = '<div><span>冻结列</span><div style="width:100px;">';
			for ( var i = 0; i < frozenfields.length; i++) {
				var frozenfildOption = grid.datagrid('getColumnOption', frozenfields[i]);
				fcolumn.push(frozenfildOption);
				var title = frozenfildOption.title;
				if(!frozenfildOption.title){
					title = "复选框";
				}
				if (!frozenfildOption.hidden) {
					frozenHtml += '<div iconCls="icon-ok" class="frozen-Column" field="' + frozenfields[i] + '">'+title+'</div>';
				} else {
					frozenHtml += '<div iconCls="icon-empty" class="frozen-Column" field="' + frozenfields[i] + '">'+title+'</div>';
				}
			}
			frozenHtml +='</div></div>';
			$(frozenHtml).appendTo(tmenu);
			$('<div class="menu-sep"/>').html("").appendTo(tmenu);
		}
		var frozenArray = [];
		frozenArray.push(fcolumn);
		frozenColumnArray = this.frozenColumnArray = frozenArray;
		var column = [];
		for ( var i = 0; i < fields.length; i++) {
			var fildOption = grid.datagrid('getColumnOption', fields[i]);
			var colHtml = "";
			var title = fildOption.title;
			if(!fildOption.title){
				title = "复选框";
			}
			if (!fildOption.hidden) {
				colHtml+='<div iconCls="icon-ok" field="' + fields[i] + '"><span>'+title+'</span><div style="width:100px;">'+
					'<div iconCls="icon-remove" class="setcolumn-frozen" field="' + fields[i] + '">冻结</div>';
			} else {
				colHtml+='<div iconCls="icon-empty" field="' + fields[i] + '"><span>'+title+'</span><div style="width:100px;">'+
					'<div iconCls="icon-remove" class="setcolumn-frozen" field="' + fields[i] + '">冻结</div>';
			}
			colHtml +='</div></div>';
			$(colHtml).appendTo(tmenu);
			column.push(fildOption);
		}
		var cArray=[];
		cArray.push(column);
		columnArray = this.columnArray = cArray;

		var gridname = grid.context.id ;
		//买赠 捆绑的编辑页共用一个datagrid但列名不同，这里不给它添加 保存 重置 项
		if(showHeaderButton){
			$('<div class="menu-sep"/>').html("").appendTo(tmenu);
			$('<div iconCls="icon-save" >保存</div>').appendTo(tmenu);
			$('<div iconCls="icon-reload" >重置</div>').appendTo(tmenu);
		}

		headerContextMenu = this.headerContextMenu = tmenu.menu({
			onClick : function(item) {
				var field = $(item.target).attr('field');
				if (item.iconCls == 'icon-ok') {
					if($(item.target).hasClass("frozen-Column")){
						for(var i=0;i<frozenColumnArray[0].length;i++){
							if(frozenColumnArray[0][i].field==field){
								columnArray[0].push(frozenColumnArray[0][i]);
								frozenColumnArray[0].splice(i,1);
							}
						}
						grid.datagrid({
							frozenColumns:frozenColumnArray,
							columns:columnArray
						}).datagrid("columnMoving");
					}else{
						grid.datagrid('hideColumn', field);
						$(this).menu('setIcon', {
							target : item.target,
							iconCls : 'icon-empty'
						});
					}
				}else if(item.iconCls == 'icon-remove'){
					for(var i=0;i<columnArray[0].length;i++){
						if(columnArray[0][i].field==field){
							frozenColumnArray[0].push(columnArray[0][i]);
							columnArray[0].splice(i,1);
						}
					}
					grid.datagrid({
						frozenColumns:frozenColumnArray,
						columns:columnArray
					}).datagrid("columnMoving");
				}else if(item.iconCls == 'icon-reload'){
					var id = grid.attr("id");
					var columnOld = authority.columnOld;
					var name = authority.name;
					$.ajax({
						url :'common/deletePageCulumn.do',
						type:'post',
						data:'&grid='+id+'&name='+name,
						dataType:'json',
						success:function(json){
							if(json.flag==true){
								$.messager.alert("提醒","表单样式重置成功，请刷新页面");
							}else{
								$.messager.alert("提醒","表单样式重置失败！");
							}
						}
					});

				}else if(item.iconCls == 'icon-save'){
					var id = grid.attr("id");
					var columnOld = authority.columnOld;
					var name = authority.name;
					$.ajax({
						url :'common/savePageCulumn.do',
						type:'post',
						data:'frozenColumns='+JSON.stringify(frozenColumnArray)+'&columns='+JSON.stringify(columnArray)+'&grid='+id+'&name='+name+'&columnOld='+JSON.stringify(columnOld),
						dataType:'json',
						success:function(json){
							if(json.flag==true){
								$.messager.alert("提醒","表单样式保存成功！");
							}else{
								$.messager.alert("提醒","表单样式保存失败！");
							}
						}
					});

				} else {
					grid.datagrid('showColumn', field);
					$(this).menu('setIcon', {
						target : item.target,
						iconCls : 'icon-ok'
					});
				}
			}
		});
	}
	headerContextMenu.menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
//datagrid复选框与选择不级联
$.fn.datagrid.defaults.checkOnSelect = false;
$.fn.datagrid.defaults.selectOnCheck = false;
$.fn.datagrid.defaults.striped = true;
$.fn.datagrid.defaults.onBeforeLoad = function(){
	$(this).datagrid('clearChecked');
	$(this).datagrid('clearSelections');
};
$.fn.datagrid.defaults.pageList=[10,20,30,50,100,200,500,1000];
$.fn.datagrid.defaults.pageSize=20;
$.fn.treegrid.defaults.pageList=[10,20,30,50,100,200,500,1000];
$.fn.treegrid.defaults.pageSize=20;
//IE下弹出消息框解决select穿透问题
$.fn.window.defaults.onOpen = function(){
	if($.browser.msie && $.browser.version=='6.0') {
		$(this).data().window.shadow.append('<iframe width="100%" height="100%" frameborder="0" scrolling="no"></iframe>');
	}
};
$.fn.dialog.defaults.onOpen = function(){
	if($.browser.msie && $.browser.version=='6.0') {
		$(this).data().window.shadow.append('<iframe width="100%" height="100%" frameborder="0" scrolling="no"></iframe>');
	}
};
/**
 * datagrid列拖拽
 */
$.extend($.fn.datagrid.methods,{
	columnMoving: function(jq){
		return jq.each(function(){
			var target = this;
			var cells = $(this).datagrid('getPanel').find('div.datagrid-header td[field]');
			cells.draggable({
				revert:true,
				cursor:'pointer',
				edge:5,
				proxy:function(source){
					var p = $('<div class="tree-node-proxy tree-dnd-no" style="position:absolute;border:1px solid #ff0000;"/>').appendTo('body');
					p.html($(source).text());
					p.hide();
					return p;
				},
				onBeforeDrag:function(e){
					//判断是否为鼠标左击事件
					if(e.which!=1){
						return false;
					}
					e.data.startLeft = $(this).offset().left;
					e.data.startTop = $(this).offset().top;
				},
				onStartDrag: function(){
					$(this).draggable('proxy').css({
						left:-10000,
						top:-10000
					});
				},
				onDrag:function(e){
					$(this).draggable('proxy').show().css({
						left:e.pageX+15,
						top:e.pageY+15
					});
					return false;
				}
			}).droppable({
				accept:'td[field]',
				onDragOver:function(e,source){
					$(source).draggable('proxy').removeClass('tree-dnd-no').addClass('tree-dnd-yes');
					$(this).css('border-left','1px solid #ff0000');
					$(this).css('cursor','move');
				},
				onDragLeave:function(e,source){
					$(source).draggable('proxy').removeClass('tree-dnd-yes').addClass('tree-dnd-no');
					$(this).css('border-left',0);
				},
				onDrop:function(e,source){
					$(this).css('border-left',0);
					var fromField = $(source).attr('field');
					var toField = $(this).attr('field');
					setTimeout(function(){
						moveField(fromField,toField);
						$(target).datagrid();
						$(target).datagrid('columnMoving');
					},1);
				}
			});

			// move field to another location
			function moveField(from,to){
				var frozenfields = $(target).datagrid('getColumnFields',true);
				var frozenColumns = [];
				var fcols = [];
				for ( var i = 0; i < frozenfields.length; i++) {
					var frozenfildOption = $(target).datagrid('getColumnOption', frozenfields[i]);
					fcols.push(frozenfildOption);
				}
				frozenColumns.push(fcols);
				var columns = $(target).datagrid('options').columns;
				var cc = columns[0];
				var fc = frozenColumns[0];
				var c = _remove(from);
				if (c){
					_insert(to,c);
				}
				//判断表格是否树状表格
				if($(target).datagrid("options").treeField!=null){
					//重新加载列
					$(target).treegrid({
						frozenColumns:frozenColumns,
						columns:columns
					});
				}else{
					//重新加载列
					$(target).datagrid({
						frozenColumns:frozenColumns,
						columns:columns
					});
				}
				function _remove(field){
					for(var i=0; i<cc.length; i++){
						if (cc[i].field == field){
							var c = cc[i];
							cc.splice(i,1);
							return c;
						}
					}
					for(var i=0; i<fc.length; i++){
						if (fc[i].field == field){
							var c = fc[i];
							fc.splice(i,1);
							return c;
						}
					}
					return null;
				}
				function _insert(field,c){
					var newcc = [];
					var newfc = [];
					for(var i=0; i<cc.length; i++){
						if (cc[i].field == field){
							newcc.push(c);
						}
						newcc.push(cc[i]);
					}
					for(var i=0; i<fc.length; i++){
						if (fc[i].field == field){
							newfc.push(c);
						}
						newfc.push(fc[i]);
					}
					columns[0] = newcc;
					frozenColumns[0] = newfc;

				}
			}
		});
	}
});
/**
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 简单实现，如需高级功能，可以自由修改
 * 使用说明:
 *   在easyui.min.js之后导入本js
 *   代码案例:
 *		$("#dg").datagrid({....}).datagrid('tooltip'); 所有列
 *		$("#dg").datagrid({....}).datagrid('tooltip',['productid','listprice']); 指定列
 * @author ____′↘夏悸
 */
$.extend($.fn.datagrid.methods, {
	tooltip : function (jq, fields) {
		return jq.each(function () {
			var panel = $(this).datagrid('getPanel');
			if (fields && typeof fields == 'object' && fields.sort) {
				$.each(fields, function () {
					var field = this;
					bindEvent($('.datagrid-header td[field=' + field + '] .datagrid-cell', panel));
				});
			} else {
				bindEvent($(".datagrid-header .datagrid-cell", panel));
			}
		});

		function bindEvent(jqs) {
			jqs.mouseover(function () {
				var content = $(this).text();
				$(this).tooltip({
					content : content,
					trackMouse : true,
					onHide : function () {
						$(this).tooltip('destroy');
					}
				}).tooltip('show');
			});
		}
	}
});

//行号四位、五位显示不完全的解决办法
$.extend($.fn.datagrid.methods, {
	fixRownumber : function (jq) {
		return jq.each(function () {
			var panel = $(this).datagrid("getPanel");
			//获取最后一行的number容器,并拷贝一份
			var clone = $(".datagrid-cell-rownumber", panel).last().clone();
			//由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
			clone.css({
				"position" : "absolute",
				left : -1000
			}).appendTo("body");
			var width = clone.width("auto").width();
			//默认宽度是25,所以只有大于25的时候才进行fix
			if (width > 25) {
				//多加5个像素,保持一点边距
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
				//修改了宽度之后,需要对容器进行重新计算,所以调用resize
				$(this).datagrid("resize");
				//一些清理工作
				clone.remove();
				clone = null;
			} else {
				//还原成默认状态
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
			}
		});
	}
});

/**
 * 根据字段权限等加载表格的字段列
 */
(function($){
	$.fn.createGridColumnLoad = function(options) {
		defaults = {
			resize:true,
			name : '',
			url :'',
			frozenCol :[[]],
			formmater:'',
			commonCol :[[]]
		};
		options = $.extend(defaults,options);
		var id = (this.selector).replace("#","");
		var colJson = null;
		var pageWidth = $("body").width();
		var formmater = "";
        for(var j=0;j<options.frozenCol.length;j++){
            for(var i=0;i<options.frozenCol[j].length;i++) {
                if (options.frozenCol[j][i].formatter) {
                    var formmater1 = options.frozenCol[j][i].formatter ;
                    var field = options.frozenCol[j][i].field ;
                    if(formmater == ""){
                        formmater = "{"+field+":"+formmater1;
                    }else{
                        formmater = formmater + ","+field+":"+ formmater1;
                    }
                }
            }
        }
        for(var j=0;j<options.commonCol.length;j++){
            for(var i=0;i<options.commonCol[j].length;i++) {
                if (options.commonCol[j][i].formatter) {
                    var formmater1 = options.commonCol[j][i].formatter ;
                    var field = options.commonCol[j][i].field ;
                    if(formmater == ""){
                        formmater = "{"+field+":"+formmater1;
                    }else{
                        formmater = formmater + ","+field+":"+ formmater1;
                    }
                }
            }
        }
		$.ajax({
			url :'common/getPageCulumn.do',
			type:'post',
			data:'grid='+id+'&frozenCol='+JSON.stringify(options.frozenCol)+'&commonCol='+JSON.stringify(options.commonCol)+'&name='+options.name,
			dataType:'json',
			async:false,
			success:function(json){
				if(json!=null&&(json.frozen!=null||json.common!=null)){
					var realWidth = 0;
					var common1 = $.parseJSON(json.common);
					var frozen1 = $.parseJSON(json.frozen);

					//填回格式化列
                    for(var k=0;k<options.commonCol.length;k++) {
                        for (var i = 0; i < options.commonCol[k].length; i++) {
                            if (options.commonCol[k][i].formatter) {
                                for (var j = 0; j < common1[k].length; j++) {
                                    if (options.commonCol[k][i].field == common1[k][j].field) {
                                        common1[k][j].formatter = options.commonCol[k][i].formatter;
                                    }
                                }
                            }
                            if (options.commonCol[k][i].formatter) {
                                for (var j = 0; j < frozen1[0].length; j++) {
                                    if (options.commonCol[k][i].field == frozen1[0][j].field) {
                                        frozen1[0][j].formatter = options.commonCol[k][i].formatter;
                                    }
                                }
                            }
                            //填回排序规则
                            if (options.commonCol[k][i].sortable) {
                                for (var j = 0; j < common1[k].length; j++) {
                                    if (options.commonCol[k][i].field == common1[k][j].field) {
                                        common1[k][j].sortable = options.commonCol[k][i].sortable;
                                    }
                                }
                            }
                            if (options.commonCol[k][i].sortable) {
                                for (var j = 0; j < frozen1[0].length; j++) {
                                    if (options.commonCol[k][i].field == frozen1[0][j].field) {
                                        frozen1[0][j].sortable = options.commonCol[k][i].sortable;
                                    }
                                }
                            }

                            //填回编辑规则
                            if (options.commonCol[k][i].editor) {
                                for (var j = 0; j < common1[k].length; j++) {
                                    if (options.commonCol[k][i].field == common1[k][j].field) {
                                        common1[k][j].editor = options.commonCol[k][i].editor;
                                    }
                                }
                            }
                            if (options.commonCol[k][i].editor) {
                                for (var j = 0; j < frozen1[0].length; j++) {
                                    if (options.commonCol[k][i].field == frozen1[0][j].field) {
                                        frozen1[0][j].editor = options.commonCol[k][i].editor;
                                    }
                                }
                            }
                            //填回styler规则
                            if (options.commonCol[k][i].styler) {
                                for (var j = 0; j < common1[k].length; j++) {
                                    if (options.commonCol[k][i].field == common1[k][j].field) {
                                        common1[k][j].styler = options.commonCol[k][i].styler;
                                    }
                                }
                            }
                            if (options.commonCol[k][i].styler) {
                                for (var j = 0; j < frozen1[0].length; j++) {
                                    if (options.commonCol[k][i].field == frozen1[0][j].field) {
                                        frozen1[0][j].styler = options.commonCol[k][i].styler;
                                    }
                                }
                            }
                        }
                    }
					//填回格式化列
                    for (var i = 0; i < options.frozenCol[0].length; i++) {
                        if ((options.frozenCol[0][i].hidden == null || !options.frozenCol[0][i].hidden )) {
                            if (options.frozenCol[0][i].width != null) {
                                realWidth = Number(realWidth) + Number(options.frozenCol[0][i].width);
                            } else {
                                realWidth = Number(realWidth) + Number(25);
                            }
                        }
                        if (options.frozenCol[0][i].formatter) {
                            for (var j = 0; j < common1[0].length; j++) {
                                if (options.frozenCol[0][i].field == common1[0][j].field) {
                                    common1[0][j].formatter = options.frozenCol[0][i].formatter;
                                }
                            }
                        }
                        if (options.frozenCol[0][i].formatter) {
                            for (var j = 0; j < frozen1[0].length; j++) {
                                if (options.frozenCol[0][i].field == frozen1[0][j].field) {
                                    frozen1[0][j].formatter = options.frozenCol[0][i].formatter;
                                }
                            }
                        }
                        //填回排序规则
                        if (options.frozenCol[0][i].sortable) {
                            for (var j = 0; j < common1[0].length; j++) {
                                if (options.frozenCol[0][i].field == common1[0][j].field) {
                                    common1[0][j].sortable = options.frozenCol[0][i].sortable;
                                }
                            }
                        }
                        if (options.frozenCol[0][i].sortable) {
                            for (var j = 0; j < frozen1[0].length; j++) {
                                if (options.frozenCol[0][i].field == frozen1[0][j].field) {
                                    frozen1[0][j].sortable = options.frozenCol[0][i].sortable;
                                }
                            }
                        }

						//填回编辑规则
                        if (options.frozenCol[0][i].editor) {
                            for (var j = 0; j < common1[0].length; j++) {
                                if (options.frozenCol[0][i].field == common1[0][j].field) {
                                    common1[0][j].editor = options.frozenCol[0][i].editor;
                                }
                            }
                        }
                        if (options.frozenCol[0][i].editor) {
                            for (var j = 0; j < frozen1[0].length; j++) {
                                if (options.frozenCol[0][i].field == frozen1[0][j].field) {
                                    frozen1[0][j].editor = options.frozenCol[0][i].editor;
                                }
                            }
                        }

						//填回styler规则
                        if (options.frozenCol[0][i].styler) {
                            for (var j = 0; j < common1[0].length; j++) {
                                if (options.frozenCol[0][i].field == common1[0][j].field) {
                                    common1[0][j].styler = options.frozenCol[0][i].styler;
                                }
                            }
                        }
                        if (options.frozenCol[0][i].styler) {
                            for (var j = 0; j < frozen1[0].length; j++) {
                                if (options.frozenCol[0][i].field == frozen1[0][j].field) {
                                    frozen1[0][j].styler = options.frozenCol[0][i].styler;
                                }
                            }
                        }
                    }

                    for(var i=0;i<common1[0].length;i++){
                        if(common1[0][i].hidden==null || !common1[0][i].hidden){
                            if(common1[0][i].width!=null){
                                realWidth = Number(realWidth)+Number(common1[0][i].width);
                            }else{
                                if(common1[0][i].checkbox){
                                    realWidth = Number(realWidth)+Number(25);
                                }else{
                                    var len = common1[0][i].title.length;
                                    realWidth = Number(realWidth)+Number(17*len);
                                }
                            }
                        }
                    }
					//填回格式化列
					for(var i=0;i<frozen1[0].length;i++){
						if((frozen1[0][i].hidden==null || !frozen1[0][i].hidden )){
							if(frozen1[0][i].width!=null){
								realWidth = Number(realWidth)+Number(frozen1[0][i].width);
							}else{
								if(frozen1[0][i].checkbox){
									realWidth = Number(realWidth)+Number(15);
								}else{
									var len = common1[0][i].title.length;
									realWidth = Number(realWidth)+Number(17*len);
								}
							}
						}
					}
					//调整最后一列宽度 让他适应页面
					if(options.resize && realWidth<pageWidth && (pageWidth-realWidth)<150){
						var addwidth = pageWidth - realWidth;
						for(var i=common1[0].length-1;i>=0;i--){
							if(!common1[0][i].hidden &&  common1[0][i].align!="right"){
								common1[0][i].width=Number(common1[0][i].width)+Number(addwidth);
								break;
							}
						}
					}
					formmater = formmater.replace(/[\t|\n]/g,"");
					colJson={frozen:frozen1,common:common1,columnOld:json.columnOld,name:options.name,url:options.url,formmater:formmater};
				}
			}
		});
		return colJson;
	};
})(jQuery);

/**
 * 按钮统一操作控件
 */
(function($){
	var methods = {
		init : function(options){
			defaults = {
				initButton:[
					{type:'button-add',handler:function(){}},
					{type:'button-edit',handler:function(){}},
					{type:'button-hold',handler:function(){}},
					{type:'button-save',handler:function(){}},
					{type:'button-delete',handler:function(){}},
					{type:'button-copy',handler:function(){}},
					{type:'button-view',handler:function(){}},
					{type:'button-back',handler:function(data){}},
					{type:'button-next',handler:function(data){}},
					{type:'button-open',handler:function(){}},
					{type:'button-close',handler:function(){}},
					{type:'button-import',handler:function(){}},
					{type:'button-exprot',handler:function(){}},
					{type:'button-commonquery',handler:function(){}}
				],
				divid:'',				//控件编号
				buttons:[],				//[{id:'12345',name:'按钮名称',iconCls:'',handler:function(){}}]
				authButtons:[],			//当前用户可以访问的按钮URL地址列表
				model:'base',			//按钮类型 base基础档案 bill业务单据
				type:'list',			//按钮初始状态 初始状态为查看	add新增 edit修改 view查看 copy复制 list列表
				tab:'',					//tab标题 上一条下一条 数据来源tab页面
				taburl:'',				//tab标签中url地址
				datagrid:'',			//datagrid id
				tname:'',				//表名
				idField:'',				//主键字段
				id:'',					//当前数据主键编号
				data:[],				//数据列表 用来记录上一条下一条
				index:0,				//当前数据索引
				layoutid:''				//指定了layoutid 则在页面底部添加 保存 审核按钮
			};
			options = $.extend(defaults,options);

			getMenuHtml = function(id,button){
				var htmlsb = new Array();
				for(var i=0;i<button.length;i++){
					var display = '';
					if(button[i].id!=null){
						if(button[i].type=='menu'){
							htmlsb.push( '<div id="'+button[i].id+'" menuid="'+id+'" '+display+' data-options="iconCls:\''+button[i].iconCls+'\'"><span>'+button[i].name+'</span><div style="width:150px;">');
							htmlsb.push( getMenuHtml(id,button[i].button));
							htmlsb.push( '</div></div>');
						}else if(button[i].type=='menu-sep'){
							htmlsb.push( '<div class="menu-sep"></div>');
						}else{
							htmlsb.push( '<div id="'+button[i].id+'" menuid="'+id+'" '+display+' data-options="iconCls:\''+button[i].iconCls+'\'">'+button[i].name+'</div>');
						}
					}else if(button[i].type=='menu-sep'){
						htmlsb.push( '<div class="menu-sep"></div>');
					}
				}
				return htmlsb.join("");
			};
			setMenuButton = function(id,buttons){
				var html = '<div id="'+id+'" style="width:160px;">';
				html += getMenuHtml(id,buttons);
				html+="</div>";
				return html;
			};
			menuButtonClick = function(buttons){
				for(var i=0;i<buttons.length;i++){
					if(buttons[i].type=='menu-sep'){
						continue;
					}
					if(!buttons[i].type){
						//通过data给click事件传值
						$("#"+buttons[i].id).data("handler",buttons[i].handler);
						$("#"+buttons[i].id).click(function(){
							var menuid = $(this).attr("menuid");
							var id = $(this).attr("id");
							var flag = $("#"+menuid).menu('getItem','#'+id).disabled;
							if(flag){
								return false;
							}
							var handler = $(this).data("handler");
							handler();
						});
					}else{
						menuButtonClick(buttons[i].button);
					}
				}
			};

			initButton = function(){
				var initButtons = new Array();
				initButtons.push('<a href="javaScript:void(0);" id="button-add" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-add\'" style="display: none;">新增</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-edit" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-edit\'" style="display: none;">修改</a>');
				//initButtons.push( '<a href="javaScript:void(0);" id="button-hold" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-hold\',disabled:true" title="暂存" style="display: none;">暂存</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-save" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-save\',disabled:true" style="display: none;">保存</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-saveaudit" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-saveaudit\',disabled:true" style="display: none;">保存并审核</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-saveopen" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-save\',disabled:true" style="display: none;">保存并启用</a>');
				//initButtons.push( '<a href="javaScript:void(0);" id="button-giveup" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-giveup\'" title="放弃" style="display: none;">放弃</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-delete" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-delete\'" style="display: none;">删除</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-copy" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-copy\'" style="display: none;">复制</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-view" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-view\'" style="display: none;">查看</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-open" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-open\'" style="display: none;">启用</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-close" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-close\'" style="display: none;">禁用</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-audit" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-audit\'" style="display: none;">审核</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-supperaudit" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-audit\'" style="display: none;">超级审核</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-oppaudit" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-oppaudit\'" style="display: none;">反审</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-relation" class="easyui-menubutton button-list" data-options="menu:\'#relationsMenu\',plain:true,iconCls:\'button-relation\'" style="display: none;">关联业务</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-workflow" class="easyui-menubutton button-list" data-options="menu:\'#workflowMenu\',plain:true,iconCls:\'button-workflow\'" style="display: none;">工作流</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-back" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-back\'" style="display: none;">上一条</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-next" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-next\'" style="display: none;">下一条</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-import" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-import\'" style="display: none;">导入</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-export" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-export\'" style="display: none;">导出</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-preview" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-preview\'" style="display: none;">打印预览</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-print" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-print\'" style="display: none;">打印</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-file" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\'button-file\'" style="display: none;">附件</a>');
				initButtons.push( '<a href="javaScript:void(0);" id="button-more" class="easyui-menubutton" data-options="menu:\'#moreMenu\',plain:true,iconCls:\'button-more\'" style="display: none;">更多</a>');


				initButtons.push( '<div id="relationsMenu" style="width:120px;"><div id="relation-upper" menuid="relationsMenu" style="display: none;" iconCls="relation-upper">参照上游单据</div><div id="relation-upper-view" menuid="relationsMenu" style="display: none;" iconCls="relation-upper-view">上游单据查看</div><div class="menu-sep"></div><div id="relation-savedown" menuid="relationsMenu" style="display: none;"  iconCls="relation-savedown">生成下游单据</div><div id="relation-down-view" menuid="relationsMenu" style="display: none;" iconCls="relation-down-view">下游单据查看</div></div>');
				initButtons.push( '<div id="workflowMenu" style="width:120px;"><div id="workflow-submit" menuid="workflowMenu" style="display: none;" iconCls="workflow-submit">提交</div><div id="workflow-addidea" menuid="workflowMenu" style="display: none;" iconCls="workflow-addidea">填写审批意见</div><div id="workflow-viewflow" menuid="workflowMenu" style="display: none;" iconCls="workflow-viewflow">查看流程</div><div id="workflow-viewflow-pic" menuid="workflowMenu" style="display: none;" iconCls="workflow-viewflow-pic">查看流程图</div><div id="workflow-recover" menuid="workflowMenu" style="display: none;" iconCls="workflow-recover">撤销流程</div> </div> ');
				initButtons.push( '<div id="moreMenu" style="width:120px;"><div id="more-change" menuid="moreMenu" style="display: none;" iconCls="more-change">变更</div><div id="more-viewchange" menuid="moreMenu" style="display: none;" iconCls="more-viewchange">查看变更记录</div><div id="more-stop" menuid="moreMenu" style="display: none;" iconCls="more-stop">中止</div><div class="menu-sep"></div><div id="more-import" menuid="moreMenu" style="display: none;" iconCls="button-import">导入</div><div id="more-export" menuid="moreMenu" style="display: none;" iconCls="button-import">导出</div><div class="menu-sep"></div><div id="more-current" menuid="moreMenu" style="display: none;" iconCls="more-current">转当前</div><div id="more-history" menuid="moreMenu" style="display: none;" iconCls="more-history">转历史</div><div class="menu-sep"></div></div>');
				initButtons.push( '<input type="hidden" id="buttonWidget-addData"/>');

				var menuFlag = false;
				for(var i=0;i<options.buttons.length;i++){
					if(options.buttons[i].type!='more' && options.buttons[i].type!='menu'){
						var display = '';
						if(options.buttons[i].name!=null){
							initButtons.push( '<a href="javaScript:void(0);" id="'+options.buttons[i].id+'" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:\''+options.buttons[i].iconCls+'\'" '+display+'>'+options.buttons[i].name+'</a>');
						}
					}
				}
				for(var i=0;i<options.buttons.length;i++){
					if(options.buttons[i].type=='menu'){
						initButtons.push( '<a href="javaScript:void(0);" id="'+options.buttons[i].id+'" class="easyui-menubutton" data-options="menu:\'#'+options.buttons[i].id+'menu\',plain:true,iconCls:\''+options.buttons[i].iconCls+'\'">'+options.buttons[i].name+'</a>');
						initButtons.push( setMenuButton(options.buttons[i].id+"menu",options.buttons[i].button));
						menuFlag = true;
					}
				}
				if(menuFlag){
					initButtons.push( "</div>");
				}
				initButtons.push( '<a href="javaScript:void(0);" id="button-commonquery"  style="display: none;">通用查询</a>');
				$("#"+options.divid).html(initButtons.join(''));
				$.parser.parse('#'+options.divid);

			};
			//关联业务按钮事件绑定
			initRelationButton = function(initButtons){
				for(var j=0;j<initButtons.button.length;j++){
					$("#"+initButtons.button[j].type).show();
					//通过data给click事件传值
					$("#"+initButtons.button[j].type).data("handler",initButtons.button[j].handler);
					if(initButtons.button[j].type == "relation-upper" && initButtons.button[j].data.length>0){
						for(var z=0;z<initButtons.button[j].data.length;z++){
							$("#relation-upper").data("relation-upper"+initButtons.button[j].data[z].id,initButtons.button[j].data[z]);
							$("#relation-upper").data("relation-upper"+initButtons.button[j].data[z].id+"-handler",initButtons.button[j].handler);
							var item = $('#relationsMenu').menu('findItem', '参照上游单据');
							$("#relationsMenu").menu('appendItem',
								{
									parent:item.target,
									id:'relation-upper'+initButtons.button[j].data[z].id,
									text:initButtons.button[j].data[z].name,
									onclick:function(){
										var id = $(this).attr("id");
										var data = $("#relation-upper").data(id);
										var handler = $("#relation-upper").data(id+"-handler");
										handler(data);
									}
								}
							);
						}
					}else if(initButtons.button[j].type == "relation-savedown" && initButtons.button[j].data.length>0){
						for(var z=0;z<initButtons.button[j].data.length;z++){
							$("#relation-savedown").data("relation-savedown"+initButtons.button[j].data[z].id,initButtons.button[j].data[z]);
							$("#relation-savedown").data("relation-savedown"+initButtons.button[j].data[z].id+"-handler",initButtons.button[j].handler);
							var item = $('#relationsMenu').menu('findItem', '生成下游单据');
							var handler = initButtons.button[j].handler;
							$("#relationsMenu").menu('appendItem',
								{
									parent:item.target,
									id:'relation-savedown'+initButtons.button[j].data[z].id,
									text:initButtons.button[j].data[z].name,
									onclick:function(){
										var id = $(this).attr("id");
										var data = $("#relation-savedown").data(id);
										var handler = $("#relation-savedown").data(id+"-handler");
										handler(data);
									}
								}
							);
						}
					}else{
						$("#"+initButtons.button[j].type).click(function(){
							var id = $(this).attr("id");
							var flag = $("#relationsMenu").menu('getItem','#'+id).disabled;
							if(flag){
								return false;
							}
							var handler = $(this).data("handler");
							handler();
						});
					}
				}
			};

			initButtonClick = function(){
				//初始化默认按钮 绑定事件
				var initButtons = options.initButton;
				for(var i=0;i<initButtons.length;i++){
					$("#"+initButtons[i].type).show();
					if(initButtons[i].type=="button-audit"){
						$("#button-bottom-audit").show();
					}else if(initButtons[i].type=="button-save"){
						$("#button-bottom-save").show();
					}else if(initButtons[i].type=="button-saveaudit"){
						$("#button-bottom-saveaudit").show();
					}else if(initButtons[i].type=="button-saveopen"){
						$("#button-bottom-saveopen").show();
					}
					//通过data给click事件传值
					$("#"+initButtons[i].type).data("name",initButtons[i].type);
					$("#"+initButtons[i].type).data("handler",initButtons[i].handler);
					if(initButtons[i].type=="button-import"){
						$("#"+options.divid).data("importAttr",initButtons[i].attr);
					}else if(initButtons[i].type=="button-export"){
						$("#"+options.divid).data("exportAttr",initButtons[i].attr);
					}else if(initButtons[i].type=="button-file"){
						$("#"+options.divid).data("fileAttr",initButtons[i].attr);
					}
					//关联业务
					if(initButtons[i].type=="button-relation"){
						//绑定关联业务按钮事件
						for(var j=0;j<initButtons[i].button.length;j++){
							$("#"+initButtons[i].button[j].type).show();
							$("#"+initButtons[i].button[j].type).data("handler",initButtons[i].button[j].handler);
							$("#"+initButtons[i].button[j].type).click(function(){
								var id = $(this).attr("id");
								var flag = $("#relationsMenu").menu('getItem','#'+id).disabled;
								if(flag){
									return false;
								}
								var handler = $(this).data("handler");
								handler();
							});
						}
					}else if(initButtons[i].type=="button-workflow"){	//工作流按钮
						for(var j=0;j<initButtons[i].button.length;j++){
							$("#"+initButtons[i].button[j].type).show();
							$("#"+initButtons[i].button[j].type).data("handler",initButtons[i].button[j].handler);
							$("#"+initButtons[i].button[j].type).click(function(){
								var id = $(this).attr("id");
								var flag = $("#workflowMenu").menu('getItem','#'+id).disabled;
								if(flag){
									return false;
								}
								var handler = $(this).data("handler");
								handler();
							});
						}
					}else if(initButtons[i].type=="button-more"){
						for(var j=0;j<initButtons[i].button.length;j++){
							$("#"+initButtons[i].button[j].type).show();
							$("#"+initButtons[i].button[j].type).data("handler",initButtons[i].button[j].handler);
							$("#"+initButtons[i].button[j].type).click(function(){
								var id = $(this).attr("id");
								var flag = $("#moreMenu").menu('getItem','#'+id).disabled;
								if(flag){
									return false;
								}
								var type = id;
								var handler = $(this).data("handler");
								var flag = true;
								if(type=="more-change"){
									handler();
									if(options.type!="list"){
										options.type="edit";
									}
								}else{
									flag = handler();
								}
							});
						}
					}else{
						if(initButtons[i].type!="button-import" && initButtons[i].type!="button-export"  && initButtons[i].type!="button-file"){
							if(initButtons[i].type=="button-save"){
								$("#button-bottom-save").click(function(){
									var handler = $("#button-save").data("handler");
									//判断按钮是否可以点击
									if($(this).linkbutton('options').disabled){
										return false;
									}
									if(options.type=="edit"){
										//判断方法是否继续执行
										handler();
									}else if(options.type=="add"){
										//判断方法是否继续执行
										handler();

									}else{
										handler();
									}
								});
							}else if(initButtons[i].type=="button-audit"){
								$("#button-bottom-audit").click(function(){
									//判断按钮是否可以点击
									if($(this).linkbutton('options').disabled){
										return false;
									}
									var handler = $("#button-audit").data("handler");
									handler();
								});
							}else if(initButtons[i].type=="button-saveaudit"){
								$("#button-bottom-saveaudit").click(function(){
									//判断按钮是否可以点击
									if($(this).linkbutton('options').disabled){
										return false;
									}
									var handler = $("#button-saveaudit").data("handler");
									handler();
								});
							}else if(initButtons[i].type=="button-saveopen"){
								$("#button-bottom-saveopen").click(function(){
									//判断按钮是否可以点击
									if($(this).linkbutton('options').disabled){
										return false;
									}
									var handler = $("#button-saveopen").data("handler");
									handler();
								});
							}else if(initButtons[i].type=="button-commonquery"){
								$("#button-commonquery").advancedQuery(initButtons[i].attr);
							}
							$("#"+initButtons[i].type).click(function(){
								var type = $(this).attr("id");
								if(type=="button-commonquery"){
									return false;
								}
								//判断按钮是否可以点击
								if($(this).linkbutton('options').disabled){
									return false;
								}
								var handler = $(this).data("handler");
								if(handler==null){
									return false;
								}
								if(type=="button-back"){
									var objectArray = $("#"+options.divid).data("dataArray");
									var dataIndex = $("#"+options.divid).data("dataIndex");
									if(dataIndex>0){
										dataIndex = dataIndex -1;
										var object = objectArray[dataIndex];

										if(dataIndex == 0){
											$(this).linkbutton("disable");
										}
										$("#button-next").linkbutton("enable");
										$("#"+options.divid).data("dataIndex",dataIndex);
										handler(object);
									}
								}else if(type=="button-next"){
									var objectArray = $("#"+options.divid).data("dataArray");
									var dataIndex = $("#"+options.divid).data("dataIndex");
									if(dataIndex<objectArray.length-1){
										dataIndex = dataIndex +1;
										var object = objectArray[dataIndex];

										$("#button-back").linkbutton("enable");
										if(dataIndex == objectArray-1){
											$(this).linkbutton("disable");
										}
										$("#"+options.divid).data("dataIndex",dataIndex);
										handler(object);
									}
								}else if(type=="button-edit"){
									handler();
									options.type="edit";
								}else if(type=="button-giveup"){
									handler();
								}else if(type=="button-hold" || type=="button-save" || type=="button-buttom-save" || type=="button-saveaudit" || type=="button-bottom-saveaudit" || type=="button-saveopen" || type=="button-bottom-saveopen"){
									if(options.type=="edit"){
										//判断方法是否继续执行
										handler();
									}else if(options.type=="add"){
										//判断方法是否继续执行
										handler();

									}else{
										handler();
									}
								}else if(type=="button-delete"){
									handler();
								}else if(type=="button-add"){
									options.type="add";
									if(options.data.length==1){
										$("#button-back").linkbutton("enable");
									}
									handler();
								}else if(type=="button-copy"){
									handler();
									options.type="add";
								}else{
									handler();
								}
							});
						}
					}
				}
				//自定义按钮
				for(var i=0;i<options.buttons.length;i++){
					//通过data给click事件传值
					$("#"+options.buttons[i].id).data("id",options.buttons[i].id);
					$("#"+options.buttons[i].id).data("handler",options.buttons[i].handler);
					if(options.buttons[i].type=='menu'){
						if(options.buttons[i].disable){
							$("#"+options.buttons[i].id).menubutton("disable");
						}
						menuButtonClick(options.buttons[i].button);
					}else if(options.buttons[i].type=='more'){
						//根据URL 判断是否能访问该按钮
						var flag = true;
						if(flag){
							$("#moreMenu").data("moreMenu"+options.buttons[i].id,options.buttons[i].handler);
							$("#moreMenu").menu('appendItem',
								{
									id:options.buttons[i].id,
									text:options.buttons[i].name,
									iconCls : options.buttons[i].iconCls,
									onclick:function(){
										var id = $(this).attr("id");
										var handler = $("#moreMenu").data("moreMenu"+id);
										handler();
									}
								}
							);
						}
					}else{
						if(options.buttons[i].disable){
							$("#"+options.buttons[i].id).linkbutton("disable");
						}
						$("#"+options.buttons[i].id).click(function(){
							//判断按钮是否可以点击
							if($(this).linkbutton('options').disabled){
								return false;
							}
							var type = $(this).data("type");
							var handler = $(this).data("handler");
							handler();
						});
					}
				}
			};
			showButtonByModel = function(){
				var model = options.model;
				//基础档案
				if(model=="base" && options.type=="list"){
					//$("#button-hold").hide();
					//$("#button-save").hide();
					$("#button-audit").hide();
					$("#button-supperaudit").hide();
					$("#button-oppaudit").hide();
					$("#button-back").hide();
					$("#button-next").hide();
					$("#button-relation").hide();
					$("#button-workflow").hide();
					$("#button-more").hide();

					$("#button-add").linkbutton("enable");
					$("#button-edit").linkbutton("disable");
					$("#button-hold").linkbutton("disable");
					$("#button-save").linkbutton("disable");
					$("#button-saveopen").linkbutton("disable");
					$("#button-saveaudit").linkbutton("disable");
					$("#button-bottom-saveaudit").linkbutton("disable");
					$("#button-delete").linkbutton("disable");
					$("#button-copy").linkbutton("disable");
					$("#button-view").linkbutton("disable");
					$("#button-open").linkbutton("disable");
					$("#button-close").linkbutton("disable");
					$("#button-import").linkbutton("enable");
					$("#button-export").linkbutton("enable");
					$("#button-back").linkbutton("disable");
					$("#button-next").linkbutton("disable");
					$("#workflowMenu").menu('disableItem','#workflow-submit');
					$("#workflowMenu").menu('disableItem','#workflow-addidea');
					$("#workflowMenu").menu('disableItem','#workflow-viewflow');
					$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
					$("#workflowMenu").menu('disableItem','#workflow-recover');
					$("#button-file").linkbutton("disable");
					$("#button-giveup").linkbutton("disable");
				}else if(model=="base" && options.type=="multipleList"){
					$("#button-audit").hide();
					$("#button-supperaudit").hide();
					$("#button-oppaudit").hide();
					$("#button-back").hide();
					$("#button-next").hide();
					$("#button-relation").hide();
					$("#button-workflow").hide();
					$("#button-more").hide();

				}else if(model=="base" && (options.type=="add" || options.type=="edit")){
					$("#button-audit").hide();
					$("#button-supperaudit").hide();
					$("#button-oppaudit").hide();
					$("#button-relation").hide();
					$("#button-more").hide();
					$("#button-import").hide();
					$("#button-export").hide();

					$("#button-add").linkbutton("disable");
					$("#button-edit").linkbutton("disable");
					$("#button-hold").linkbutton("enable");
					$("#button-save").linkbutton("enable");
					$("#button-saveopen").linkbutton("enable");
					$("#button-saveaudit").linkbutton("enable");
					$("#button-bottom-saveaudit").linkbutton("enable");
					$("#button-delete").linkbutton("disable");
					$("#button-copy").linkbutton("disable");
					$("#button-view").linkbutton("disable");
					$("#button-open").linkbutton("disable");
					$("#button-close").linkbutton("disable");
					$("#button-import").linkbutton("disable");
					$("#button-export").linkbutton("disable");
					$("#button-back").linkbutton("disable");
					$("#button-next").linkbutton("disable");
					$("#workflowMenu").menu('disableItem','#workflow-submit');
					$("#workflowMenu").menu('disableItem','#workflow-addidea');
					$("#workflowMenu").menu('disableItem','#workflow-viewflow');
					$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
					$("#workflowMenu").menu('disableItem','#workflow-recover');
					$("#button-file").linkbutton("enable");
					$("#button-giveup").linkbutton("enable");
				}else if(model=="base" && options.type=="view"){
					$("#button-audit").hide();
					$("#button-supperaudit").hide();
					$("#button-oppaudit").hide();
					$("#button-relation").hide();
					$("#button-more").hide();
					$("#button-import").hide();
					$("#button-export").hide();

					$("#button-giveup").linkbutton("disable");
					$("#workflowMenu").menu('disableItem','#workflow-submit');
				}else if(model=="bill" && options.type=="list"){
					$("#button-open").hide();
					$("#button-close").hide();
					$("#button-back").hide();
					$("#button-next").hide();

					$("#button-giveup").linkbutton("disable");
					$("#workflowMenu").menu('disableItem','#workflow-submit');
				}else if(model=="bill" && options.type=="multipleList"){
					$("#button-hold").hide();
					$("#button-save").hide();
					$("#button-saveopen").hide();
					$("#button-saveaudit").hide();
					$("#button-bottom-saveaudit").hide();
					$("#button-open").hide();
					$("#button-close").hide();
					$("#button-back").hide();
					$("#button-next").hide();
					$("#button-relation").hide();
					$("#button-workflow").hide();

				}else if(model=="bill" && (options.type=="add" || options.type=="edit")){
					$("#button-open").hide();
					$("#button-close").hide();
					$("#button-view").hide();
					$("#button-import").hide();
					$("#button-export").hide();

					$("#button-add").linkbutton("disable");
					$("#button-edit").linkbutton("disable");
					$("#button-hold").linkbutton("enable");
					$("#button-save").linkbutton("enable");
					$("#button-saveopen").linkbutton("enable");
					$("#button-saveaudit").linkbutton("enable");
					$("#button-bottom-saveaudit").linkbutton("enable");
					$("#button-bottom-save").linkbutton("enable");
					$("#button-delete").linkbutton("disable");
					$("#button-copy").linkbutton("enable");
					$("#button-view").linkbutton("disable");
					$("#button-open").linkbutton("disable");
					$("#button-close").linkbutton("disable");
					$("#button-audit").linkbutton("enable");
					$("#button-supperaudit").linkbutton("enable");
					$("#button-bottom-audit").linkbutton("enable");
					$("#button-oppaudit").linkbutton("enable");
					$("#button-import").linkbutton("disable");
					$("#button-export").linkbutton("disable");
					$("#button-back").linkbutton("disable");
					$("#button-next").linkbutton("disable");
					$("#workflowMenu").menu('disableItem','#workflow-submit');
					$("#workflowMenu").menu('disableItem','#workflow-addidea');
					$("#workflowMenu").menu('disableItem','#workflow-viewflow');
					$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
					$("#workflowMenu").menu('disableItem','#workflow-recover');
					$("#button-file").linkbutton("enable");
					$("#moreMenu").menu('disableItem','#more-change');
					$("#moreMenu").menu('disableItem','#more-viewchange');
					$("#moreMenu").menu('disableItem','#more-stop');
					$("#moreMenu").menu('disableItem','#more-current');
					$("#moreMenu").menu('disableItem','#more-history');

					$("#button-giveup").linkbutton("enable");
					if(options.type=="add" || options.type=="copy"){
						$("#moreMenu").menu('enableItem','#relation-upper');
						$("#moreMenu").menu('disableItem','#relation-upper-view');
						$("#moreMenu").menu('disableItem','#relation-savedown');
						$("#moreMenu").menu('disableItem','#relation-down-view');
					}else if(options.type=="edit"){
						$("#moreMenu").menu('enableItem','#relation-upper');
						$("#moreMenu").menu('enableItem','#relation-upper-view');
						$("#moreMenu").menu('enableItem','#relation-savedown');
						$("#moreMenu").menu('enableItem','#relation-down-view');
					}
				}else if(model=="bill" && options.type=="view"){
					$("#button-view").hide();
					$("#button-open").hide();
					$("#button-close").hide();
//		 			$("#button-import").hide();
//					$("#button-export").hide();

					$("#workflowMenu").menu('disableItem','#workflow-submit');
					$("#button-giveup").linkbutton("disable");
					if(options.data.length>1){
						if(options.index==0){
							$("#button-back").linkbutton("disable");
							$("#button-next").linkbutton("enable");
						}else if(options.index==options.data.length-1){
							$("#button-back").linkbutton("enable");
							$("#button-next").linkbutton("disable");
						}else{
							$("#button-back").linkbutton("enable");
							$("#button-next").linkbutton("enable");
						}
					}else{
						$("#button-back").linkbutton("disable");
						$("#button-next").linkbutton("disable");
					}
				}
			};
			this.each(function() {
				var id = $(this).attr("id");
				options.divid = id;
				//获取datagrid中的基本信息
				if((options.taburl!="" || options.tab!="") && options.datagrid!=""){
					var flag = true;
					var conwindow = tabsWindowURL(options.taburl);
					if(null==conwindow){
						conwindow = tabsWindow(options.tab);
					}
					try{
						conwindow.$("#"+options.datagrid).datagrid('getRows');
					}catch(e){
						flag = false;
					}
					if(flag){
						//获取datagrid中的数据数组
						var data = conwindow.$("#"+options.datagrid).datagrid('getRows');
						//获取datagrid的主键字段
						var idField = conwindow.$("#"+options.datagrid).datagrid('options').idField;
						//根据ID获取当前数据在datagrid中的索引
						if(options.id!=''){
							for(var i=0;i<data.length;i++){
								if(options.id==data[i][idField]){
									options.index = i;
									break;
								}
							}
						}
						options.data = data;
						options.idField = idField;
						$("#"+options.divid).data("dataArray",options.data);
						$("#"+options.divid).data("dataIndex",options.index);
						$("#"+options.divid).data("dataIdField",options.idField);
					}
				}
				//初始化按钮
				initButton();
				initButtonClick();
				//根据业务模块功能显示不同的按钮
				//base基础档案 bill业务单据
				showButtonByModel();
				if(options.data.length){
					$("#"+options.divid).data("dataLength",options.data.length);
					$("#"+options.divid).data("dataIndex",options.index);
				}
				$("#"+options.divid).data("button-model",options.model);
				var importAttr = $("#"+options.divid).data("importAttr");
				var exportAttr = $("#"+options.divid).data("exportAttr");
				var fileAttr = $("#"+options.divid).data("fileAttr");
				$("#button-import").Excel('import',importAttr);
				$("#button-export").Excel('export',exportAttr);
				if(fileAttr!=null){
					$("#button-file").upload(fileAttr);
				}

			});
		},
		//移除数据 使上一条下一条不获取该数据
		//并且返回下一条数据，如果下一条数据不存在 则返回第一条。如果数据只有一条则返回null
		removeData : function(id){
			var divid = $(this).attr("id");
			var data =  $("#"+divid).data("dataArray");
			if(data==null || data.length==0){
				return null;
			}
			var index = $("#"+divid).data("dataIndex");
			data.splice(index,1);
			$("#"+divid).data("dataArray",data);
			$("#"+divid).data("dataLength",data.length);
			if(data.length>0){
				if(index==data.length){
					$("#"+divid).data("dataIndex",0);
					return data[0];
				}else{
					$("#"+divid).data("dataIndex",index);
					return data[index];
				}
			}else{
				$("#"+divid).data("dataIndex",0);
				return null;
			}
		},
		//设置附件上传下载
		setFileAttr : function(attr){
			$("#button-file").upload(attr);
		},
		//设置当前数据的编号与状态 控制按钮
		setDataID : function(param){
			if(param==null){
				return false;
			}
			var id = param.id;
			var state = param.state;
			var type = param.type;
			//控件id
			var widgetid = $(this).attr("id");
			$("#"+widgetid).data("currDataID",id);
			$("#"+widgetid).data("currDataState",state);
			var model = $("#"+widgetid).data("button-model");
			if(type==null){
				type = $("#"+widgetid).data("opertype");
			}
			$("#"+widgetid).buttonWidget("initButtonType",type);
			setTimeout(function(){
				if(model=="base"){
					//基础档案状态
					if(state=="1"){
						if(type=='edit' || type=='add'){
							$("#button-hold").linkbutton("disable");
							$("#button-close").linkbutton("disable");
							$("#button-open").linkbutton("disable");
						}else{
							$("#button-close").linkbutton("enable");
							$("#button-open").linkbutton("disable");
						}
						//$("#button-delete").linkbutton("disable");
					}else if(state=="0" || state=="2"){
						if(type=='edit' || type=='add'){
							$("#button-hold").linkbutton("disable");
							$("#button-close").linkbutton("disable");
							$("#button-open").linkbutton("disable");
						}else{
							$("#button-open").linkbutton("enable");
							$("#button-close").linkbutton("disable");
						}
						if(state=="0"){
							$("#button-edit").linkbutton("disable");
							$("#button-close").linkbutton("disable");
						}
					}else if(state=="3"){
						$("#button-open").linkbutton("disable");
						$("#button-close").linkbutton("disable");
					}
				}else if(model=="bill"){
					if(state=="1"){
						if(type=='edit' || type=='add'){
							$("#button-add").linkbutton("enable");
							$("#button-save").linkbutton("enable");
							$("#button-saveopen").linkbutton("enable");
							$("#button-saveaudit").linkbutton("enable");
							$("#button-bottom-saveaudit").linkbutton("enable");
							$("#button-bottom-save").linkbutton("enable");
							$("#button-hold").linkbutton("enable");
							$("#button-edit").linkbutton("disable");
							$("#button-delete").linkbutton("enable");
							$("#button-audit").linkbutton("disable");
							$("#button-supperaudit").linkbutton("disable");
							$("#button-bottom-audit").linkbutton("disable");
							$("#button-oppaudit").linkbutton("disable");
							$("#workflowMenu").menu('disableItem','#workflow-submit');
							$("#workflowMenu").menu('disableItem','#workflow-addidea');
							$("#workflowMenu").menu('disableItem','#workflow-viewflow');
							$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
							$("#workflowMenu").menu('disableItem','#workflow-recover');
						}else{
							$("#button-add").linkbutton("enable");
							$("#button-save").linkbutton("disable");
							$("#button-saveopen").linkbutton("disable");
							$("#button-saveaudit").linkbutton("disable");
							$("#button-bottom-saveaudit").linkbutton("disable");
							$("#button-bottom-save").linkbutton("disable");
							$("#button-hold").linkbutton("disable");
							$("#button-edit").linkbutton("enable");
							$("#button-delete").linkbutton("enable");
							$("#button-audit").linkbutton("disable");
							$("#button-supperaudit").linkbutton("disable");
							$("#button-bottom-audit").linkbutton("disable");
							$("#button-oppaudit").linkbutton("disable");
							$("#workflowMenu").menu('disableItem','#workflow-submit');
							$("#workflowMenu").menu('disableItem','#workflow-addidea');
							$("#workflowMenu").menu('disableItem','#workflow-viewflow');
							$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
							$("#workflowMenu").menu('disableItem','#workflow-recover');
						}
					}else if(state=="2"){
						if(type=='edit' || type=='add'){
							$("#button-add").linkbutton("enable");
							$("#button-save").linkbutton("enable");
							$("#button-saveopen").linkbutton("enable");
							$("#button-saveaudit").linkbutton("enable");
							$("#button-bottom-saveaudit").linkbutton("enable");
							$("#button-bottom-save").linkbutton("enable");
							$("#button-hold").linkbutton("disable");
							$("#button-edit").linkbutton("disable");
							$("#button-delete").linkbutton("enable");
							$("#button-audit").linkbutton("enable");
							$("#button-supperaudit").linkbutton("enable");
							$("#button-bottom-audit").linkbutton("enable");
							$("#button-oppaudit").linkbutton("disable");
							$("#workflowMenu").menu('enableItem','#workflow-submit');
							$("#workflowMenu").menu('disableItem','#workflow-addidea');
							$("#workflowMenu").menu('disableItem','#workflow-viewflow');
							$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
							$("#workflowMenu").menu('disableItem','#workflow-recover');
							$("#relationsMenu").menu('enableItem','#relation-upper-view');
						}else{
							$("#button-add").linkbutton("enable");
							$("#button-save").linkbutton("disable");
							$("#button-saveopen").linkbutton("disable");
							$("#button-saveaudit").linkbutton("disable");
							$("#button-bottom-saveaudit").linkbutton("disable");
							$("#button-bottom-save").linkbutton("disable");
							$("#button-hold").linkbutton("disable");
							$("#button-edit").linkbutton("enable");
							$("#button-delete").linkbutton("enable");
							$("#button-audit").linkbutton("enable");
							$("#button-supperaudit").linkbutton("enable");
							$("#button-bottom-audit").linkbutton("enable");
							$("#button-oppaudit").linkbutton("disable");
							$("#workflowMenu").menu('enableItem','#workflow-submit');
							$("#workflowMenu").menu('disableItem','#workflow-addidea');
							$("#workflowMenu").menu('disableItem','#workflow-viewflow');
							$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
							$("#workflowMenu").menu('disableItem','#workflow-recover');
							$("#relationsMenu").menu('enableItem','#relation-upper-view');
						}
					}else if(state=="3"){
						$("#button-add").linkbutton("enable");
						$("#button-save").linkbutton("disable");
						$("#button-saveopen").linkbutton("disable");
						$("#button-saveaudit").linkbutton("disable");
						$("#button-bottom-saveaudit").linkbutton("disable");
						$("#button-bottom-save").linkbutton("disable");
						$("#button-hold").linkbutton("disable");
						$("#button-edit").linkbutton("disable");
						$("#button-delete").linkbutton("disable");
						$("#button-audit").linkbutton("disable");
						$("#button-supperaudit").linkbutton("disable");
						$("#button-bottom-audit").linkbutton("disable");
						$("#button-oppaudit").linkbutton("enable");
						$("#workflowMenu").menu('disableItem','#workflow-submit');
						$("#workflowMenu").menu('disableItem','#workflow-addidea');
						$("#workflowMenu").menu('enableItem','#workflow-viewflow');
						$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
						$("#workflowMenu").menu('disableItem','#workflow-recover');
						$("#relationsMenu").menu('enableItem','#relation-upper-view');
					}else if(state=="4"){
						$("#button-add").linkbutton("enable");
						$("#button-save").linkbutton("disable");
						$("#button-saveopen").linkbutton("disable");
						$("#button-saveaudit").linkbutton("disable");
						$("#button-bottom-saveaudit").linkbutton("disable");
						$("#button-bottom-save").linkbutton("disable");
						$("#button-hold").linkbutton("disable");
						$("#button-edit").linkbutton("disable");
						$("#button-delete").linkbutton("disable");
						$("#button-audit").linkbutton("disable");
						$("#button-supperaudit").linkbutton("disable");
						$("#button-bottom-audit").linkbutton("disable");
						$("#button-oppaudit").linkbutton("disable");
						$("#workflowMenu").menu('disableItem','#workflow-submit');
						$("#workflowMenu").menu('disableItem','#workflow-addidea');
						$("#workflowMenu").menu('enableItem','#workflow-viewflow');
						$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
						$("#workflowMenu").menu('disableItem','#workflow-recover');
						$("#relationsMenu").menu('enableItem','#relation-upper-view');
					}else if(state=="5"){
						$("#button-add").linkbutton("enable");
						$("#button-save").linkbutton("disable");
						$("#button-saveopen").linkbutton("disable");
						$("#button-saveaudit").linkbutton("disable");
						$("#button-bottom-saveaudit").linkbutton("disable");
						$("#button-bottom-save").linkbutton("disable");
						$("#button-hold").linkbutton("disable");
						$("#button-edit").linkbutton("disable");
						$("#button-delete").linkbutton("disable");
						$("#button-audit").linkbutton("disable");
						$("#button-supperaudit").linkbutton("disable");
						$("#button-bottom-audit").linkbutton("disable");
						$("#button-oppaudit").linkbutton("disable");
						$("#workflowMenu").menu('disableItem','#workflow-submit');
						$("#workflowMenu").menu('disableItem','#workflow-submit');
						$("#workflowMenu").menu('disableItem','#workflow-addidea');
						$("#workflowMenu").menu('enableItem','#workflow-viewflow');
						$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
						$("#workflowMenu").menu('disableItem','#workflow-recover');
						$("#relationsMenu").menu('enableItem','#relation-upper-view');
					}else if(state=="6"){
						if(type=='edit'){
							$("#button-add").linkbutton("enable");
							$("#button-save").linkbutton("enable");
							$("#button-saveopen").linkbutton("enable");
							$("#button-saveaudit").linkbutton("enable");
							$("#button-bottom-saveaudit").linkbutton("enable");
							$("#button-bottom-save").linkbutton("disable");
							$("#button-hold").linkbutton("disable");
							$("#button-edit").linkbutton("disable");
							$("#button-delete").linkbutton("disable");
							$("#button-audit").linkbutton("disable");
							$("#button-supperaudit").linkbutton("disable");
							$("#button-bottom-audit").linkbutton("disable");
							$("#button-oppaudit").linkbutton("disable");
							$("#workflowMenu").menu('enableItem','#workflow-submit');
							$("#workflowMenu").menu('disableItem','#workflow-addidea');
							$("#workflowMenu").menu('disableItem','#workflow-viewflow');
							$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
							$("#workflowMenu").menu('disableItem','#workflow-recover');
							$("#relationsMenu").menu('enableItem','#relation-upper-view');
						}else{
							$("#button-add").linkbutton("enable");
							if(!window.handleWork_taskId){
								$("#button-edit").linkbutton("disable");
								$("#workflowMenu").menu('disableItem','#workflow-addidea');
							}else{
								$("#button-edit").linkbutton("enable");
								$("#workflowMenu").menu('enableItem','#workflow-addidea');
							}
							$("#button-delete").linkbutton("disable");
							$("#button-save").linkbutton("disable");
							$("#button-saveopen").linkbutton("disable");
							$("#button-saveaudit").linkbutton("disable");
							$("#button-bottom-saveaudit").linkbutton("disable");
							$("#button-bottom-save").linkbutton("disable");
							$("#button-hold").linkbutton("disable");
							$("#button-audit").linkbutton("disable");
							$("#button-supperaudit").linkbutton("disable");
							$("#button-bottom-audit").linkbutton("disable");
							$("#button-oppaudit").linkbutton("disable");
							$("#button-preview").linkbutton("disable");
							$("#button-print").linkbutton("disable");
							$("#relationsMenu").menu("disableItem","#relation-upper");
							$("#workflowMenu").menu('disableItem','#workflow-submit');
							$("#workflowMenu").menu('enableItem','#workflow-viewflow');
							$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
							$("#workflowMenu").menu('enableItem','#workflow-recover');
							$("#relationsMenu").menu('enableItem','#relation-upper-view');
						}
					}
				}

				var dataLength = $("#"+widgetid).data("dataLength");
				var dataIndex = $("#"+widgetid).data("dataIndex");

				if(dataLength>0){
					if(dataIndex!=0){
						$("#button-back").linkbutton("enable");
					}else{
						$("#button-back").linkbutton("disable");
					}
					if(dataIndex==dataLength-1){
						$("#button-next").linkbutton("disable");
					}else{
						$("#button-next").linkbutton("enable");
					}
				}
			},50);

		},
		//初始化按钮状态
		initButtonType : function(type){
			var id = $(this).attr("id");
			setTimeout(function(){
				if(type=="list"){
					$("#"+id).data("opertype","list");

					$("#button-add").linkbutton("enable");
					$("#button-edit").linkbutton("disable");
					$("#button-hold").linkbutton("disable");
					$("#button-save").linkbutton("disable");
					$("#button-saveopen").linkbutton("disable");
					$("#button-saveaudit").linkbutton("disable");
					$("#button-bottom-saveaudit").linkbutton("disable");
					$("#button-delete").linkbutton("disable");
					$("#button-copy").linkbutton("enable");
					$("#button-view").linkbutton("disable");
					$("#button-open").linkbutton("disable");
					$("#button-close").linkbutton("disable");
					$("#button-import").linkbutton("enable");
					$("#button-export").linkbutton("enable");
					$("#button-back").linkbutton("disable");
					$("#button-next").linkbutton("disable");
					$("#workflowMenu").menu('disableItem','#workflow-submit');
					$("#workflowMenu").menu('disableItem','#workflow-addidea');
					$("#workflowMenu").menu('disableItem','#workflow-viewflow');
					$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
					$("#workflowMenu").menu('disableItem','#workflow-recover');
					$("#button-file").linkbutton("disable");
					$("#button-giveup").linkbutton("disable");
				}else if(type=="multipleList"){
					$("#"+id).data("opertype","multipleList");

					$("#button-add").linkbutton("enable");
					$("#button-edit").linkbutton("enable");
					$("#button-hold").linkbutton("enable");
					$("#button-save").linkbutton("enable");
					$("#button-saveopen").linkbutton("enable");
					$("#button-saveaudit").linkbutton("enable");
					$("#button-bottom-saveaudit").linkbutton("enable");
					$("#button-delete").linkbutton("enable");
					$("#button-copy").linkbutton("enable");
					$("#button-view").linkbutton("enable");
					$("#button-open").linkbutton("enable");
					$("#button-close").linkbutton("enable");
					$("#button-import").linkbutton("enable");
					$("#button-export").linkbutton("enable");
					$("#button-back").linkbutton("enable");
					$("#button-next").linkbutton("enable");
					$("#workflowMenu").menu('enableItem','#workflow-submit');
					$("#workflowMenu").menu('enableItem','#workflow-addidea');
					$("#workflowMenu").menu('enableItem','#workflow-viewflow');
					$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
					$("#workflowMenu").menu('enableItem','#workflow-recover');
					$("#button-file").linkbutton("enable");
					$("#button-giveup").linkbutton("enable");
				}else if(type=="view"){
					$("#"+id).data("opertype","view");

					$("#button-add").linkbutton("enable");
					$("#button-edit").linkbutton("enable");
					$("#button-hold").linkbutton("disable");
					$("#button-save").linkbutton("disable");
					$("#button-saveopen").linkbutton("disable");
					$("#button-saveaudit").linkbutton("disable");
					$("#button-bottom-saveaudit").linkbutton("disable");
					$("#button-delete").linkbutton("enable");
					$("#button-copy").linkbutton("enable");
					$("#button-view").linkbutton("enable");
					$("#button-open").linkbutton("enable");
					$("#button-close").linkbutton("enable");
					$("#button-audit").linkbutton("enable");
					$("#button-supperaudit").linkbutton("enable");
					$("#button-oppaudit").linkbutton("enable");
					$("#button-import").linkbutton("enable");
					$("#button-export").linkbutton("enable");

					$("#workflowMenu").menu('enableItem','#workflow-submit');
					$("#workflowMenu").menu('enableItem','#workflow-addidea');
					$("#workflowMenu").menu('enableItem','#workflow-viewflow');
					$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
					$("#workflowMenu").menu('enableItem','#workflow-recover');
					$("#button-file").linkbutton("enable");
					$("#button-giveup").linkbutton("disable");

					var dataLength = $("#"+id).data("dataLength");
					var dataIndex = $("#"+id).data("dataIndex");
					if(dataLength>0){
						if(dataIndex!=0){
							$("#button-back").linkbutton("enable");
						}else{
							$("#button-back").linkbutton("disable");
						}
						if(dataIndex==dataLength-1){
							$("#button-next").linkbutton("disable");
						}else{
							$("#button-next").linkbutton("enable");
						}
					}

					$("#moreMenu").menu('enableItem','#more-change');
					$("#moreMenu").menu('enableItem','#more-viewchange');
					$("#moreMenu").menu('enableItem','#more-stop');
					$("#moreMenu").menu('enableItem','#more-current');
					$("#moreMenu").menu('enableItem','#more-history');
				}else if(type=="add" || type=="edit"){
					if(type=="add"){
						$("#"+id).data("opertype","add");
						$("#moreMenu").menu('disableItem','#more-viewchange');
						$("#button-audit").linkbutton("disable");
						$("#button-supperaudit").linkbutton("disable");
						$("#button-bottom-audit").linkbutton("disable");
					}else if(type=="edit"){
						$("#"+id).data("opertype","edit");
						$("#moreMenu").menu('enableItem','#more-viewchange');
						$("#button-audit").linkbutton("enable");
						$("#button-supperaudit").linkbutton("enable");
						$("#button-bottom-audit").linkbutton("enable");
					}

					$("#button-add").linkbutton("disable");
					$("#button-edit").linkbutton("disable");
					$("#button-hold").linkbutton("enable");
					$("#button-save").linkbutton("enable");
					$("#button-saveopen").linkbutton("enable");
					$("#button-saveaudit").linkbutton("enable");
					$("#button-bottom-saveaudit").linkbutton("enable");
					$("#button-bottom-save").linkbutton("enable");
					$("#button-delete").linkbutton("disable");
					$("#button-copy").linkbutton("enable");
					$("#button-view").linkbutton("disable");
					$("#button-open").linkbutton("disable");
					$("#button-close").linkbutton("disable");

					$("#button-oppaudit").linkbutton("disable");
					$("#button-import").linkbutton("enable");
					$("#button-export").linkbutton("enable");

					$("#relationsMenu").menu('disableItem','#relation-upper-view');

					$("#workflowMenu").menu('disableItem','#workflow-submit');
					$("#workflowMenu").menu('disableItem','#workflow-addidea');
					$("#workflowMenu").menu('disableItem','#workflow-viewflow');
					$("#workflowMenu").menu('enableItem','#workflow-viewflow-pic');
					$("#workflowMenu").menu('disableItem','#workflow-recover');
					$("#button-file").linkbutton("enable");
					$("#button-giveup").linkbutton("enable");

					$("#moreMenu").menu('disableItem','#more-change');
					$("#moreMenu").menu('disableItem','#more-stop');
					$("#moreMenu").menu('disableItem','#more-current');
					$("#moreMenu").menu('disableItem','#more-history');

					$("#moreMenu").menu('enableItem','#relation-upper');
					$("#moreMenu").menu('disableItem','#relation-savedown');

					var dataLength = $("#"+id).data("dataLength");
					var dataIndex = $("#"+id).data("dataIndex");
					if(dataLength>0){
						if(dataIndex!=0){
							$("#button-back").linkbutton("enable");
						}else{
							$("#button-back").linkbutton("disable");
						}
						if(dataIndex==dataLength-1){
							$("#button-next").linkbutton("disable");
						}else{
							$("#button-next").linkbutton("enable");
						}
					}
				}
			},50);

		},
		setButtonType : function(state){
			var id = $(this).attr("id");
			var model = $("#"+id).data("button-model");
			if(model=="base"){
				//基础档案状态
				if(state=="1"){
					$("#button-close").linkbutton("enable");
					$("#button-open").linkbutton("disable");
					$("#button-edit").linkbutton("enable");
					//$("#button-delete").linkbutton("disable");
				}else if(state=="0" || state=="2"){
					$("#button-open").linkbutton("enable");
					$("#button-close").linkbutton("disable");
					if(state=="0"){
						$("#button-edit").linkbutton("disable");
					}
				}else if(state=="3"){
					$("#button-open").linkbutton("disable");
					$("#button-close").linkbutton("disable");
				}
			}else if(model=="bill"){
				if(state=="1"){
					$("#button-audit").linkbutton("disable");
					$("#button-supperaudit").linkbutton("disable");
					$("#button-oppaudit").linkbutton("enable");
				}else if(state=="2"){
					$("#button-hold").linkbutton("disable");
					$("#button-audit").linkbutton("enable");
					$("#button-supperaudit").linkbutton("enable");
					$("#button-oppaudit").linkbutton("disable");
				}else if(state=="3"){
					$("#button-save").linkbutton("disable");
					$("#button-saveopen").linkbutton("disable");
					$("#button-saveaudit").linkbutton("disable");
					$("#button-bottom-saveaudit").linkbutton("disable");
					$("#button-hold").linkbutton("disable");
					$("#button-edit").linkbutton("disable");
					$("#button-audit").linkbutton("disable");
					$("#button-supperaudit").linkbutton("disable");
					$("#button-oppaudit").linkbutton("enable");
				}else if(state=="4"){
					$("#button-save").linkbutton("disable");
					$("#button-saveopen").linkbutton("disable");
					$("#button-saveaudit").linkbutton("disable");
					$("#button-hold").linkbutton("disable");
					$("#button-edit").linkbutton("disable");
				}
			}
		},
		getOperType : function(){
			var id = $(this).attr("id");
			var type = $("#"+id).data("opertype");
			return type;
		},
		disableButton : function(id){
			setTimeout(function(){
				$("#"+id).linkbutton("disable");
			},200);
		},
		enableButton : function(id){
			setTimeout(function(){
				$("#"+id).linkbutton("enable");
			},200);
		},
		//禁用整个菜单按钮
		disableMenu : function(id){
			setTimeout(function(){
				$("#"+id).menubutton("disable");
			},200);
		},
		//启用整个菜单按钮
		enableMenu : function(id){
			setTimeout(function(){
				$("#"+id).menubutton("enable");
			},200);
		},
		//禁用菜单按钮项
		disableMenuItem : function(id){
			setTimeout(function(){
				var menuid = $("#"+id).attr("menuid");
				$("#"+menuid).menu('disableItem','#'+id);
			},200);
		},
		//启用菜单按钮项
		enableMenuItem : function(id){
			setTimeout(function(){
				var menuid = $("#"+id).attr("menuid");
				$("#"+menuid).menu('enableItem','#'+id);
			},200);
		},
		//新增保存后 添加编号 可以进行上一条下一条操作
		addNewDataId : function(id){
			var divid = $(this).attr("id");
			var dataArr = $("#"+divid).data("dataArray");
			if(dataArr==null){
				dataArr = [];
			}
			dataArr.push({id:id});
			$("#button-next").linkbutton("disable");
			$("#"+divid).data("dataArray",dataArr);
			$("#"+divid).data("dataLength",dataArr.length);
			$("#"+divid).data("dataIndex",dataArr.length-1);
		}
	};
	$.fn.buttonWidget = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.buttonWidget');
		}
	}
})(jQuery);

(function($){
	// 扩展comments方法，获取工作流各节点审批信息
	$.fn.comments = function(options) {
		defaults = {
			businesskey: '',
			processid: '',
			instanceid: '',
			taskkey: '',
			agree: '1',
			type: 'horizontal',	/* vertical */
			interval: 10,	/* 间隔 */
			width: 140,
			definitionkey: ''
		};

        var isArray = function (obj) {
            if(Array.isArray){
                return Array.isArray(obj);
            }else{
                return Object.prototype.toString.call(obj)==="[object Array]";
            }
        };

        options = $.extend(defaults, options);

		var $o = $(this);
        $o.html('　加载中...');

		$.ajax({
			type: 'post',
			dataType: 'json',
			data: options,
			url: 'act/listComments.do',
			success: function(result) {

				var jsons = result.comments;
				var process = result.process;
				var assignee = result.assignee;

				if(options.type == 'horizontal') {

					for(var i = 0; i < jsons.length; i++){

						var json = jsons[i];
						var html = new Array();

						//if(i == jsons.length - 1) {
						html.push('<div style="width: ' + options.width + 'px; float: left; border: 1px solid #CCC; margin: 5px 5px 5px 5px;" title="' + json.name + '">');
						//} else {
						//	html.push('<div style="width: ' + options.width + 'px; float: left; border: 1px solid #CCC; margin: 5px ' + (options.interval) + 'px 5px 5px;" title="' + json.name + '">');
						//}
						if(json.name != undefined && json.name != null && json.name != '') {
							html.push('<div style="margin: 3px 3px 3px 3px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; text-align: center;"><b>' + json.name + '</b></div>');
						} else {
							html.push('<div style="margin: 3px 3px 3px 3px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"><b>&nbsp;</b></div>');
						}
						if(json.time == null || json.time == '') {
							html.push('<div style="margin: 3px 3px 3px 3px;">&nbsp;</div>');
							html.push('<div style="margin: 3px 3px 3px 3px;">&nbsp;</div>');
						} else {
							html.push('<div style="margin: 3px 3px 3px 3px; text-align: center;">' + json.user + '</div>');
							html.push('<div style="margin: 3px 3px 3px 3px; text-align: center;">' + json.time + '</div>');
						}
						html.push('</div>');

						$o.append(html.join(''));
					}

				} else if(options.type == 'vertical') {

					var html = new Array();

					if(jsons.length == 0) {
						$o.append('<div style="margin: 10px 10px 10px 10px;">该工作暂无审批信息。</div>');
						return ;
					}

					html.push('<div style="border: 1px solid #DDD; margin-left: 20px; margin-top: 5px; margin-bottom: 5px;">');
					html.push('<table style="border-collapse: collapse; border: 1px solid #B8D1E2; background: #fefefe; width: 100%;" cellspacing="3" cellpadding="3">');
					html.push('<tr style="height: 22px; background: rgb(231, 240, 255);">');
					html.push('<th style="width: 50px;">#</th>')
					html.push('<th style="width: 280px;">步骤</th>')
					html.push('<th style="width: 410px;">审批信息</th>')
					html.push('</tr>');

					var i = 0;
					jsons.shift();
					for(; i < jsons.length; i++){

						var json = jsons[i];

                        var sign = isArray(json);
                        if(sign) {

                        	var signComments = json;

                            var title = true;
                        	signComments.map(function (item) {

                                html.push('<tr class="commentline" style="background: #fff9be;" title="会签节点">');
                                if(title) {
                                    html.push('<td style="border-top: 1px solid #DDD;" rowspan="' + signComments.length + '">' + (i + 1) + '</td>');
									title = false;
                                }
                                html.push('<td style="border-top: 1px solid #DDD;">' + item.name + '</td>');
                                html.push('<td style="border-top: 1px solid #DDD;">');
                                html.push('<div style="float: left;margin-top: 5px;"><b><font color="red">办理人：&nbsp' + item.user + '</font></b></div>');
                                html.push('<div style="margin-top: 5px;">[' + (item.timeto != null && item.timeto != '' ? '<font color="green">已办理&nbsp;</font>用时：' : '<font color="green">办理中&nbsp;</font>已用时：' ) + item.timespan + ']</div>');
                                html.push('<div style="margin-top: 5px;">开始于：' + item.timefrom + '</div>');
                                if(item.timeto != null && item.timeto != '') {
                                    html.push('<div style="margin-top: 5px;">结束于：' + item.timeto + '</div>');
                                    html.push('<div style="margin-top: 5px;">');
                                    if(item.agree == '1') {
                                        html.push('<font color="">同意。</font>');
                                    } else {
                                        html.push('<font color="red">不同意。</font>');
                                    }
                                    if(item.comment != null && item.comment != '') {
                                        html.push('意见：' + item.comment + '');
                                    }
                                    html.push('</div>');
                                }
                                html.push('</td>');
                                html.push('</tr>');
                            });

                            continue ;
                        }

						html.push('<tr class="commentline" style="background: rgb(247, 250, 255)">');
						html.push('<td style="border-top: 1px solid #DDD;">' + (i + 1) + '</td>');
						html.push('<td style="border-top: 1px solid #DDD;">' + json.name + '</td>');
						if(json.start == '1') {

							html.push('<td style="border-top: 1px solid #DDD;"><div style="margin-top: 5px;"><b><font color="red">创建人：&nbsp;' + json.user + '</font></b></div>');
							html.push('<div style="margin-top: 5px;">创建于：' + json.timefrom + '</div>');
							html.push('</td>');
							continue ;
						}
						if(json.end == '1') {

							html.push('<td style="border-top: 1px solid #DDD;"></td>');
							continue ;
						}

						html.push('<td style="border-top: 1px solid #DDD;">');
						html.push('<div style="float: left;margin-top: 5px;"><b><font color="red">办理人：&nbsp' + json.user + '</font></b></div>');
						html.push('<div style="margin-top: 5px;">[' + (json.timeto != null && json.timeto != '' ? '<font color="green">已办理&nbsp;</font>用时：' : '<font color="green">办理中&nbsp;</font>已用时：' ) + json.timespan + ']</div>');
						html.push('<div style="margin-top: 5px;">开始于：' + json.timefrom + '</div>');
						if(json.timeto != null && json.timeto != '') {
							html.push('<div style="margin-top: 5px;">结束于：' + json.timeto + '</div>');

							html.push('<div style="margin-top: 5px;">');
							if(json.agree == '1') {
								html.push('<font color="">同意。</font>');
							} else {
								html.push('<font color="red">不同意。</font>');
							}
							if(json.comment != null && json.comment != '') {
								html.push('意见：' + json.comment + '');
							}
							html.push('</div>');
						}
						html.push('</td>');
						html.push('</tr>');
					}

					if(process.status != '9' && process.isend != '1' && jsons.length > 0 && process.taskid && process.taskid != jsons[jsons.length - 1].taskid) {

						html.push('<tr class="commentline" style="background: rgb(247, 250, 255)">');
						html.push('<td style="border-top: 1px solid #DDD;">' + (i + 1) + '</td>');
						html.push('<td style="border-top: 1px solid #DDD;">' + process.taskname + '</td>');
						html.push('<td style="border-top: 1px solid #DDD;">');
						html.push('<div style="float: left;margin-top: 5px; margin-bottom: 5px;"><b><font color="red">办理人：&nbsp' + assignee.name + '</font></b></div>');
						html.push('<div style="margin-top: 5px;">[<font color="red">未接收</font>]</div>');
						html.push('</td>');
						html.push('</tr>');
					}

					html.push('</table>');
					html.push('</div>');
					$o.empty();
					$o.append(html.join(''));

					// $('tr.commentline').mouseover(function() {
                    //
					// 	$(this).css('background', 'rgb(239, 242, 255)');
					// });
                    //
					// $('tr.commentline').mouseleave(function() {
                    //
					// 	$(this).css('background', 'rgb(247, 250, 255)');
					// });
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrown){
				var html = new Array();
				html.push('<div style="margin: 10px 10px 10px 10px;"><font color="red">获取审批信息失败！</font></div>');
				$o.append(html.join(''));
			}
		});
	},
		// 扩展attach方法，获取工作流附件
		$.fn.attach = function(options) {

			var defaults = {
				businessid: '',
				processid: '',
				attach: true,
				close: true
			};
			options = $.extend(defaults, options);

			var $o = $(this);

			lock = true;

			$o.html('');

			//if($('#common-attach-div') || $('#common-attach-div').length == 0) {

			if(options.attach) {

				var buttonid = 'addattach' + getRandomid();
				var divid = 'addattachdiv' + getRandomid();

				$o.append('<div class="common-attach-div" id="' + divid + '" style="margin: 10px 10px 10px 10px;"><a href="javascript:;" id="' + buttonid + '" title="点击添加附件。支持以下类型：*.png;*.jpg;*.gif;*.txt;*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;*.zip;*.rar;*.7z;">添加附件</a></div>');
				$('#' + buttonid).linkbutton({
					iconCls: 'button-add'
				});

				$('#' + buttonid).webuploader({
					title: '上传附件',
					filetype: 'Files',
					allowType: 'png,jpg,jpeg,gif,txt,doc,docx,xls,xlsx,ppt,pptx,zip,rar,7z,pdf',
					close: options.close,
					converthtml:true,
					convertpdf:true,
					onComplete: function(json) {

						$.messager.alert('提醒', '上传成功！');

						for(var i in json) {

							var procesid = options.processid;
							var attachid = json[i].id;

							$.ajax({
								type: 'post',
								data: {processid: procesid, attachid: attachid},
								url: 'act/addAttach.do',
								async: false,
								success: function (data) {

									var j = $.parseJSON(data);

									if (j.flag) {

										// $.messager.alert("提醒", "添加成功。");

										// $o.attach(options);
										var h2 = new Array();

										h2.push('<tr class="processfile" style="height: 27px; background: rgb(247, 250, 255)">');
										h2.push('<td style="border-top: 1px solid #DDD;">');
										h2.push('<div class="processfile">');
										h2.push('<a class="processfile newfile" href="void(0);" onclick="return false;">');
										// h2.push('<input type="hidden" value="' + file.id + '">');
										h2.push(json[i].oldFileName);
										h2.push('</a>');
										h2.push('<input type="hidden" value="' + json[i].id + '"/>');
										h2.push('<input type="hidden" value="' + (json[i].oldFileName.lastIndexOf('.') < 0 ? '' : json[i].oldFileName.substring(json[i].oldFileName.lastIndexOf('.')).toLocaleLowerCase()) + '"/>');
										h2.push('</div>');
										h2.push('</td>');
										h2.push('<td style="border-top: 1px solid #DDD;">');
										h2.push(j.adduser.name);
										h2.push('</td>');
										h2.push('<td style="border-top: 1px solid #DDD;">');
										h2.push(j.addtime);
										h2.push('</td>');
										h2.push('</tr>');

										$('table.common-attach-' + j.process.taskid).append(h2.join(''));
										$('div.common-attach-' + j.process.taskid).show();
										$('#common-attach-0file-tip').hide();

										$('tr.processfile.newfile').mouseover(function () {

											$(this).css('background', 'rgb(239, 242, 255)');
										});

										$('tr.processfile.newfile').mouseleave(function () {

											$(this).css('background', 'rgb(247, 250, 255)');
										});

										$('a.processfile.newfile').each(function () {

											var $obj = $(this);
											var fileid = $obj.next().val();

											var ext = $obj.next().next().val();
											var content = '<div style="margin: 5px 15px 5px 15px;"><a href="common/download.do?id=' + fileid + '" onclick="" target="_blank">下载</a></div>';
											// if(ext == '.jpg' || ext == '.jpeg' || ext == '.gif' || ext == '.bmp' || ext == '.png') {

												content = content + '<div style="margin: 5px 15px 5px 15px;"><a href="common/viewFileFlash.do?id=' + fileid + '" onclick="" target="_blank">查看</a></div>';
											// }
											content = content + '<div style="margin: 5px 15px 5px 15px;"><a id="deleteattach' + fileid + '" href="javascript:void(0);" >删除</a><input type="hidden" value="' + fileid + '" /></div>';
											$obj.tooltip({
												position: 'right',
												content: content,
												onShow: function (e) {

													$('#deleteattach' + fileid).die('click').live('click', function () {

														var fid = $(this).next().val();

														$.ajax({
															dataType: 'json',
															type: 'post',
															url: 'act/deleteAttach.do',
															data: {attachid: fid},
															success: function (json) {

																$obj.tooltip('hide');

																if (json.flag) {

																	$.messager.alert("提醒", "删除成功。");

																	$o.attach(options);

																	return true;
																}

																$.messager.alert("警告", "删除失败！");
																return true;
															}
														})
													});

													$obj.tooltip('tip').mouseover(function () {

														$obj.tooltip('show');
													});
													$obj.tooltip('tip').mouseleave(function () {

														$obj.tooltip('hide');
													});

													$obj.tooltip('tip').css({
														backgroundColor: '#FFF',
														borderColor: '#000'
													});
												}
											});

										});

										return true;
									}

									$.messager.alert("警告", "添加失败！");
									return true;

								},
								error: function () {

								}
							});

						}

					}
				});

			}
			//}

			$.ajax({
				type: 'post',
				data: options,
				url: 'act/listAttach.do',
				success: function(data) {

					var json = $.parseJSON(data);

					if(json == null || json.filelist == undefined) {
						json = {filelist: []};
					}
					//json = $.extend({filelist: []}, options);

					var jsons = json.filelist;
					var h = new Array();

					if(json.count == 0) {
						h.push('<div id="common-attach-0file-tip" style="margin: 10px 10px 10px 10px;">该工作不包含附件。</div>');
					}
					for(var i = 0; i < jsons.length; i++){

						var json = jsons[i];
						var files = json.files;

						if(files.length == 0) {

							h.push('<div class="common-attach-' + json.comment.taskid + '" id="common-attach-list" style="border: 1px solid #DDD; margin: 3px 5px 2px 5px; display: none;">');

						} else {

							h.push('<div class="common-attach-' + json.comment.taskid + '" id="common-attach-list" style="border: 1px solid #DDD; margin: 3px 5px 2px 5px; display: block;">');
						}

						h.push('<div class="common-attach-' + json.comment.taskid + '" style="margin-left: 5px; margin-top: 10px;font-size:;">步骤名称：<b>' + json.comment.taskname + '</b></div>');
						h.push('<div style="margin-left: 15px; margin-top: 5px; margin-bottom: 5px;">');
						h.push('<table class="common-attach-' + json.comment.taskid + '" style="width:100%;border-collapse:collapse;border:1px solid #B8D1E2;background:#fefefe;" cellspacing="3" cellpadding="3">');
						h.push('<tr style="height: 22px; background: rgb(231, 240, 255);">');
						h.push('<th style="width: 54%;">附件名称</th>');
						h.push('<th style="width: 20%;">创建人</th>');
						h.push('<th style="width: 26%;">创建时间</th>');
						h.push('</tr>');

						for(var j = 0; j < files.length; j++) {

							var file = files[j];

							h.push('<tr class="processfile" style="height: 27px; background: rgb(247, 250, 255)">');
							h.push('<td style="border-top: 1px solid #DDD;">');
							h.push('<div class="processfile">');
							h.push('<a class="processfile" href="void(0);" onclick="return false;" ' + (file.candelete == 1 ? ' candelete="1" ' : 'candelete="0" ') + '>');
							// h.push('<input type="hidden" value="' + file.id + '">');
							h.push(file.oldfilename);
							h.push('</a>');
							h.push('<input type="hidden" value="' + file.attachid + '"/>');
							h.push('<input type="hidden" value="' + (file.oldfilename.lastIndexOf('.') < 0 ? '' : file.oldfilename.substring(file.oldfilename.lastIndexOf('.')).toLocaleLowerCase()) + '"/>');
							h.push('</div>');
							h.push('</td>');
							h.push('<td style="border-top: 1px solid #DDD;">');
							h.push(json.addusername);
							h.push('</td>');
							h.push('<td style="border-top: 1px solid #DDD;">');
							h.push(file.addtime);
							h.push('</td>');
							h.push('</tr>');

						}

						h.push('</table>');
						h.push('</div>');
						h.push('</div>');

						//$o.html('');
						//if($('#common-attach-list').length > 0){
						//	$('#common-attach-list').remove();
						//}
						//$o.append(h.join(''));

					}

					$o.append(h.join(''));

					$('tr.processfile').mouseover(function() {

						$(this).css('background', 'rgb(239, 242, 255)');
					});

					$('tr.processfile').mouseleave(function() {

						$(this).css('background', 'rgb(247, 250, 255)');
					});

					$('a.processfile').each(function() {

						var $obj = $(this);
						var fileid = $obj.next().val();

						var ext = $obj.next().next().val();
						var candelete = $obj.attr('candelete');
						var content = '<div style="margin: 5px 15px 5px 15px;"><a href="common/download.do?id=' + fileid + '" onclick="" target="_blank">下载</a></div>';
						// if(ext == '.jpg' || ext == '.jpeg' || ext == '.gif' || ext == '.bmp' || ext == '.png') {

							content = content + '<div style="margin: 5px 15px 5px 15px;"><a href="common/viewFileFlash.do?id=' + fileid + '" onclick="" target="_blank">查看</a></div>';
						// }
						if(candelete == '1' && options.attach) {

							content = content + '<div style="margin: 5px 15px 5px 15px;"><a id="deleteattach' + fileid + '" href="javascript:void(0);" >删除</a><input type="hidden" value="' + fileid + '" /></div>';
						}
						$obj.tooltip({
							position: 'right',
							content: content,
							onShow: function(e){

								// $('#deleteattach' + fileid).unbind().click(function() {
								$('#deleteattach' + fileid).die('click').live('click', function () {

									var fid = $(this).next().val();

									$.ajax({
										dataType: 'json',
										type: 'post',
										url: 'act/deleteAttach.do',
										data: {attachid: fid},
										success: function(json) {

											$obj.tooltip('hide');

											if(json.flag) {

												$.messager.alert("提醒","删除成功。");

												$o.attach(options);

												return true;
											}

											$.messager.alert("警告","删除失败！");
											return true;
										}
									})
								});

								$obj.tooltip('tip').mouseover(function() {

									$obj.tooltip('show');
								});
								$obj.tooltip('tip').mouseleave(function() {

									$obj.tooltip('hide');
								});

								$obj.tooltip('tip').css({
									backgroundColor: '#FFF',
									borderColor: '#000'
								});
							}
						});

					});

				},
				error: function(){
					var html = new Array();
					html.push('<div style="margin: 10px 10px 10px 10px;"><font color="red">获取附件失败！</font></div>');
					$o.append(html.join(''));
				}
			});

		}
})(jQuery);

//延迟验证
$.fn.validatebox.defaults.delay=350;
/**
 * 验证扩展
 */
$.extend($.fn.validatebox.defaults.rules, {
	idcard: {
		validator: function (value, param) {
			return idCard(value);
		},
		message:'请输入正确的身份证号码'
	} ,
	maxByteLength:{
		validator:function(value, param){
			value = value || "";
			var length = value.length;
			for (var i = 0; i < value.length; i++) {
				if (value.charCodeAt(i) > 127) {
					length++;
				}
			}
			return length <= param;
		},
		message:'请输入最大长度为{0}个字节(一个中文字算2个字节)'
	},
	byteRangeLength:{
		validator:function(value,param){
			value = value || "";
			var length = value.length;
			for (var i = 0; i < value.length; i++) {
				if (value.charCodeAt(i) > 127) {
					length++;
				}
			}
			return (length >= param[0] && length <= param[1]);
		},
		message:'请输入{0}-{1}个字节(一个中文字算2个字节)'
	},
	minLength: {
		validator: function(value, param){
			return value.length >= param[0];
		},
		message: '请输入至少（2）个字符.'
	},
	max: {
		validator: function(value, param){
			return value<= param[0];
		},
		message: '输入的值超过最大值{0}'
	},
	length:{validator:function(value,param){
		var len=$.trim(value).length;
		return len>=param[0]&&len<=param[1];
	},
		message:"输入内容长度必须介于{0}和{1}之间."
	},
	phone : {// 验证电话号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '格式不正确,请使用下面格式:020-88888888'
	},
	mobile : {// 验证手机号码
		validator : function(value) {
			return /^(13|15|18)\d{9}$/i.test(value);
		},
		message : '手机号码格式不正确'
	},
	phoneOrMobile: {// 验证电话号码 或者手机号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value)||/^(13|15|18)\d{9}$/i.test(value);
		},
		message : '格式不正确,请使用下面格式:020-88888888或者手机号码'
	},
	intOrFloat : {// 验证整数或小数
		validator : function(value) {
			return /^-?\d+(\.\d+)?$/i.test(value);
		},
		message : '请输入数字，并确保格式正确'
	},
	intOrFloatNum : {// 验证整数或小数
		validator : function(value,param) {
			var reg = eval("/^[0-9]+\\d*$/i");
			var msg = "请输入正整数";
			if(Number(param[0]) != Number(0)){
				reg = eval("/^-?\\d+(\.\\d{1,"+param[0]+"})?$/");
				msg = "请输入数字(最大{0}位小数)，并确保格式正确";
			}
			if(!reg.test(value)){
				$.fn.validatebox.defaults.rules.intOrFloatNum.message = msg;
				return false;
			}
			return true;
		},
		message : ''
	},
	currency : {// 验证货币
		validator : function(value) {
			return /^\d+(\.\d+)?$/i.test(value);
		},
		message : '货币格式不正确'
	},
	qq : {// 验证QQ,从10000开始
		validator : function(value) {
			return /^[1-9]\d{4,9}$/i.test(value);
		},
		message : 'QQ号码格式不正确'
	},
	integer : {// 验证正整数
		validator : function(value) {
			return /^[0-9]+\d*$/i.test(value);
		},
		message : '请输入正整数'
	},
	signinter:{// 验证带符号整数
		validator : function(value) {
			return /^[-+]?[1-9]+\d*$/i.test(value);
		},
		message : '请输入整数'
	},
	number: {//验证数字
		validator: function (value, param) {
			return /^\d+$/.test(value);
		},
		message: '请输入数字'
	},
	age : {// 验证年龄
		validator : function(value) {
			return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
		},
		message : '年龄必须是0到120之间的整数'
	},
	chinese : {// 验证中文
		validator : function(value) {
			return /^[\u4e00-\u9fa5]+$/i.test(value);
		},
		message : '请输入中文'
	},
	english : {// 验证英语
		validator : function(value) {
			return /^[A-Za-z]+$/i.test(value);
		},
		message : '请输入英文'
	},
	unnormal : {// 验证是否包含空格和非法字符
		validator : function(value) {
			return /.+/i.test(value);
		},
		message : '输入值不能为空和包含其他非法字符'
	},
	illegalChar : {//验证是否包含特色字符
		validator : function(value) {
			var pattern=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
			if(pattern.test(value)){
				return false;
			}
			return true;
		},
		message : '不能输入特殊字符串'
	},
	sqlCheck : {//验证是否包含特色字符
		validator : function(value) {
			var pattern=/["\\\/;'[\]]/im;
			if(pattern.test(value)){
				return false;
			}
			return true;
		},
		message : "不能输入('\"\\/\{}[])等特殊字符串"
	},
	username : {// 验证用户名
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		},
		message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
	},
	dbtable : {// 验证数据库表名，列名，索引等
		validator : function(value) {
			return /^([a-zA-Z]+)|([a-zA-Z][a-zA-Z0-9_$]+)$/i.test(value);
		},
		message : '请以字母开头，任意字母、数字、“_”和“ $”'
	},
	faxno : {// 验证传真
		validator : function(value) {
//	            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '传真号码不正确'
	},
	zip : {// 验证邮政编码
		validator : function(value) {
			return /^[1-9]\d{5}$/i.test(value);
		},
		message : '邮政编码格式不正确'
	},
	ip : {// 验证IP地址
		validator : function(value) {
			var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
			if (reSpaceCheck.test(value))
			{
				value.match(reSpaceCheck);
				if (RegExp.$1<=255&&RegExp.$1>=0
					&&RegExp.$2<=255&&RegExp.$2>=0
					&&RegExp.$3<=255&&RegExp.$3>=0
					&&RegExp.$4<=255&&RegExp.$4>=0)
				{
					return true;
				}else
				{
					return false;
				}
			}else
			{
				return false;
			}
		},
		message : 'IP地址格式不正确'
	},
	name : {// 验证姓名，可以是中文或英文
		validator : function(value) {
			return /^[a-zA-Z\u4E00-\u9FA5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
		},
		message : '请输入姓名'
	},
	date : {// 验证姓名，可以是中文或英文
		validator : function(value) {
			//格式yyyy-MM-dd或yyyy-M-d
			return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
		},
		message : '请输入合适的日期格式'
	},
	msn:{
		validator : function(value){
			return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
		},
		message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
	},
	safepass: {
		validator: function (value, param) {
			return safePassword(value);
		},
		message: '密码由字母和数字组成，至少6位'
	},
	same:{
		validator : function(value, param){
			if($("#"+param[0]).val() != "" && value != ""){
				return $("#"+param[0]).val() == value;
			}else{
				return true;
			}
		},
		message : '两次输入的密码不一致！'
	},
	widgetRequired:{
		validator : function(value, param){
			if($("#"+param[0]).val() == ""){
				return false;
			}else{
				return true;
			}
		},
		message : '请选择确定的值！'
	},
	numProportion:{//数字比例
		validator : function(value, param){
			var reg = eval("/^[0|1]+(\\.\\d{1,2})?:[0|1]+(\\.\\d{1,2})?$/");
			if(!reg.test(value)){
				$.fn.validatebox.defaults.rules.numProportion.message = '请确保正确格式(a:b),a、b数值在[0-1]之间(最多2位小数)！';
				return false;
			}else{
				var numArr = value.split(":");
				if(Number(numArr[0])+Number(numArr[1]) != Number(1) && Number(numArr[0])+Number(numArr[1]) != Number(0)){
					$.fn.validatebox.defaults.rules.numProportion.message = '格式中的a、b相加需为0或1！';
					return false;
				}
			}
			return true;
		},
		message : '请确保正确格式(a:b)，a+b=0或a+b=1！'
	}
});
/* 密码由字母和数字组成，至少6位 */
var safePassword = function (value) {
	return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
};
/**
 * 变更皮肤
 * @param themeName
 * @return
 */
var changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	$.cookie('easyuiThemeName', themeName, {
		expires : 7
	});
	top.location.href="index.do";
};

/**
 * @author
 *
 * @requires jQuery,EasySSH
 *
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function(left, top) {
	//if (left < 0) {
	//	$(this).window('move', {
	//		left : 1
	//	});
	//}
	if (top < 0) {
		$(this).window('move', {
			top : 1
		});
	}
	var width = $(this).panel('options').width;
	var height = $(this).panel('options').height;
	var right = left + width;
	var buttom = top + height;
	var browserWidth = $(document).width();
	var browserHeight = $(document).height();
	//if (right > browserWidth) {
	//	$(this).window('move', {
	//		left : browserWidth - width
	//	});
	//}
	//if (buttom > browserHeight) {
	//	$(this).window('move', {
	//		top : browserHeight - height
	//	});
	//}
};
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;

/**
 * @author
 *
 * @requires jQuery,EasySSH
 *
 * panel关闭时回收内存
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			frame[0].contentWindow.document.write('');
			frame[0].contentWindow.close();
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		}
	} catch (e) {
	}
};
/**
 *panel页面加载前，记录页面中form表单的数据
 */
$.fn.panel.defaults.onBeforeOpen = function() {
	$("form").each(function(){
		//改造Form控件
		//添加form表单修改前的内容 提交表单后 获取改动过的内容
		if($(this).find(".oldFormData").length <= 0){
			var oldJSON = $(this).serializeJSON();
			var oldJSONStr = JSON.stringify(oldJSON);
			$("<input type='hidden' class='oldFormData' name='oldFromData' />").appendTo(this);
			$(this).find(".oldFormData").val(oldJSONStr);
		}
	});
};
/**
 * @author
 *
 * @requires jQuery
 *
 * 判断浏览器是否是IE并且版本小于8
 *
 * @returns true/false
 */
isLessThanIe8 = function() {
	return ($.browser.msie && $.browser.version < 8);
};

/**
 *
 */
;if (typeof JCommonUtils == "undefined" || !JCommonUtils) {
	var JCommonUtils = {};
}
JCommonUtils.getObjectValue=function(obj,key){
	if(typeof obj !="object" || obj==null){
		return "";
	}
	if(typeof key =="undefined" || obj==null){
		return "";
	}
	return (obj[key] ||"");
};

/**
 * 来自于Ext
 * Creates namespaces to be used for scoping variables and classes so that they are not global.  Usage:
 * <pre><code>
 Ext.namespace('Company', 'Company.data');
 Company.Widget = function() { ... }
 Company.data.CustomStore = function(config) { ... }
 </code></pre>
 * @param {String} namespace1
 * @param {String} namespace2
 * @param {String} etc
 * @method namespace
 */
JCommonUtils.namespace = function(){
	var a=arguments, o=null, i, j, d, rt;
	for (i=0; i<a.length; ++i) {
		d=a[i].split(".");
		rt = d[0];
		eval('if (typeof ' + rt + ' == "undefined"){' + rt + ' = {};} o = ' + rt + ';');
		for (j=1; j<d.length; ++j) {
			o[d[j]]=o[d[j]] || {};
			o=o[d[j]];
		}
	}
};


/**
 * easyUI 相关工具
 */
if (typeof EUIUtils == "undefined" || !EUIUtils) {
	var EUIUtils = {};
}
/**
 * EasyUI DataGrid根据字段动态合并单元格
 *
 * @param tableID
 *            要合并table的id
 * @param colList
 *            要合并的列,用逗号分隔(例如："name,department,office");
 */
EUIUtils.mergeCellsByField = function (tableID,colList){
	var ColArray = colList.split(",");
	var tTable = $('#'+tableID);
	var TableRowCnts=tTable.datagrid("getRows").length;
	if(TableRowCnts==0){
		return;
	}
	var tmpA;
	var tmpB;
	var PerTxt = "";
	var CurTxt = "";
	var alertStr = "";
	for (j=ColArray.length-1;j>=0 ;j-- )
	{
		PerTxt="";
		tmpA=1;
		tmpB=0;

		for (i=0;i<=TableRowCnts ;i++ )
		{
			if (i==TableRowCnts)
			{
				CurTxt="";
			}
			else
			{
				CurTxt=tTable.datagrid("getRows")[i][ColArray[j]];
			}
			if (PerTxt==CurTxt)
			{
				tmpA+=1;
			}
			else
			{
				tmpB+=tmpA;
				tTable.datagrid('mergeCells',{
					index:i-tmpA,
					field:ColArray[j],
					rowspan:tmpA,
					colspan:null
				});
				tmpA=1;
			}
			PerTxt=CurTxt;
		}
	}
};
/**
 * EasyUI DataGrid根据字段动态延时合并单元格
 *
 * @param tableID
 *            要合并table的id
 * @param colList
 *            要合并的列,用逗号分隔(例如："name,department,office");
 * @param times
 * 			  延迟时间(毫秒),缺省200毫秒
 */
EUIUtils.mergeCellsByFieldByTimeout= function (tableID,colList,times){
	if(typeof times == "undefined" || !times || !/\d+/.test(times)){
		times=200;
	}
	window.setTimeout(function(){EUIUtils.mergeCellsByField(tableID,colList);}, times);
};

/**
 * 工具包
 */
var Utils = {
	/**
	 * 数字转中文
	 *
	 * @number {Integer} 形如123的数字
	 * @return {String} 返回转换成的形如 一百二十三 的字符串
	 */
	numberToChinese : function(number) {
		/*
		 * 单位
		 */
		var units = '个十百千万@#%亿^&~';
		/*
		 * 字符
		 */
		var chars = '零一二三四五六七八九';
		var a = (number + '').split(''), s = [];
		if (a.length > 12) {
			throw new Error('too big');
		} else {
			for ( var i = 0, j = a.length - 1; i <= j; i++) {
				if (j == 1 || j == 5 || j == 9) {// 两位数 处理特殊的 1*
					if (i == 0) {
						if (a[i] != '1')
							s.push(chars.charAt(a[i]));
					} else {
						s.push(chars.charAt(a[i]));
					}
				} else {
					s.push(chars.charAt(a[i]));
				}
				if (i != j) {
					s.push(units.charAt(j - i));
				}
			}
		}
		// return s;
		return s.join('').replace(/零([十百千万亿@#%^&~])/g, function(m, d, b) {// 优先处理 零百 零千 等
			b = units.indexOf(d);
			if (b != -1) {
				if (d == '亿')
					return d;
				if (d == '万')
					return d;
				if (a[j - b] == '0')
					return '零'
			}
			return '';
		}).replace(/零+/g, '零').replace(/零([万亿])/g, function(m, b) {// 零百 零千处理后 可能出现 零零相连的 再处理结尾为零的
			return b;
		}).replace(/亿[万千百]/g, '亿').replace(/[零]$/, '').replace(/[@#%^&~]/g, function(m) {
			return {
				'@' : '十',
				'#' : '百',
				'%' : '千',
				'^' : '十',
				'&' : '百',
				'~' : '千'
			}[m];
		}).replace(/([亿万])([一-九])/g, function(m, d, b, c) {
			c = units.indexOf(d);
			if (c != -1) {
				if (a[j - c] == '0')
					return d + '零' + b
			}
			return m;
		});
	}
};
/**
 * 根据tab标签名称获取该tab下iframe
 * @param name
 * @return
 */
tabsWindow = function(name){
	if(top.$('#tt').length>0) {
		var tab = top.$('#tt').tabs('getTab', name);
		if (tab && tab.find('iframe').length > 0) {
			var iframe = tab.find('iframe')[0];
			return iframe.contentWindow;
		} else {
			return null;
		}
	}else{
		return null;
	}
};
/**
 * 根据URL地址获取tab下iframe
 * @param name
 * @return
 */
tabsWindowURL = function(url){
	var urlmd5 = hex_md5(url);
	var iframe = top.document.getElementById(urlmd5);
	if (iframe!=null) {
		return iframe.contentWindow;
	}else{
		return null;
	}

};

/**
 * 根据URL地址获取tab下的title
 */
tabsWindowTitle = function(url){
	var urlmd5 = hex_md5(url);
	return top.$("#"+urlmd5).attr("tabname");
};

/**
 * 根据URL弟子获取tab下iframe
 * @param name
 * @return
 */
tabsWindowURLMD5 = function(urlmd5){
	var iframe = top.document.getElementById(urlmd5);
	if (iframe!=null) {
		return iframe.contentWindow;
	}else{
		return null;
	}
};
/**
 * 生成随机id
 * @return
 */
getRandomid = function(){
	return Date.parse(new Date().toString())+parseInt(100*Math.random());
};

/**
 * 弹出信息提醒框
 * @return
 */
alertMsg = function(msg){
	top.$.messager.show({
		title:'提醒',
		msg:msg,
		showType:'slide',
		timeout:3000,
		style:{
			right:'',
			top:document.body.scrollTop+document.documentElement.scrollTop,
			bottom:'' ,
			zIndex:$.fn.window.defaults.zIndex++
		}
	});
};
/**
 * 重写消息弹出框
 * @return
 */
var easyuiMessagerAlert= $.messager.alert;
$.messager.alert = function(title,msg){
	var type = "info";
	var timeout = 3000;
	if(title=="提醒"){
		type = "info";
	}else if(title=="警告"){
		type = "warning";
		timeout = 6000;
	}else if(title=="错误"){
		type = "error";
		timeout = 6000;
	}else if(title=="导入提醒"){
		timeout = 6000;
	}
	// msg=msg.replace(/\&lt;\/br&gt;/ig,"</br>");
    var height = 90;
    var width = 300;
    if(msg.length>150&&msg.length<500){
        width = 500;
        height = 150;
    }else if(msg.length>500){
        width=500;
        height=380;
    }
	if(title=="导入提醒" ){
		var test = msg.substr(0,msg.indexOf("<br/>"));
		if(test.length > 50){
			width=500;
			height=380;
		}
	}
	top.$.messager.show({
		title:title,
		msg:'<div class="messager-icon messager-'+type+'" style="height:120px"></div><div style="height:100%;overflow: auto;">'+msg+'</div>',
		showType:'slide',
		timeout:timeout,
		width:width,
		height:height,
		style:{
			right:'',
			top:document.body.scrollTop+document.documentElement.scrollTop,
			bottom:'' ,
			zIndex:$.fn.window.defaults.zIndex++
		}
	});
};

var startdateid = "";
var enddateid = "";

function dateout(container,options){
	var str = "",dateFmt="",maxDate="",startDate="",minDate="";
	if(options != undefined){
		startDate = options.startDate;
		minDate = options.minDate;
		if(options.dateFmt == undefined){
			dateFmt = "yyyy-MM-dd";
		}
		else{
			dateFmt = options.dateFmt;
		}
		if(options.maxDate != undefined){
			if(options.maxDate == "enddate"){
				maxDate = "#F{$dp.$D("+enddateid+")}";

			}
			else{
				maxDate = options.maxDate;
			}
		}
		if(options.minDate != undefined){
			minDate = options.minDate;
		}
		else{
			minDate="1900-01-01";
		}
		str = 'dateFmt:\'' + dateFmt + '\',' + 'maxDate:\'' + maxDate + '\',' + 'startDate:\'' + startDate + '\',' + 'minDate:\'' +  minDate + '\'';
	}
	var input = $('<input id="'+startdateid+'" type="text" onfocus="WdatePicker({'+str+'})"/>').appendTo(container);
	return input;
}

var defaultSelectChangeFun;
$.extend($.fn.datagrid.defaults.editors, {
	comborefer: {  //datagrid 行编辑框
		init: function(container, options){
			if(options != undefined){
				var rewrite = options.rewrite;
			}
			var aid = getRandomid();
			var input = $('<input id="editor'+aid+'" type="text">').appendTo(container);
			$("#editor"+aid).widget(options);
			$("#editor"+aid).focus();
			return input;
		},
		getValue: function(target){
			var id = $(target).attr("id");
			var value = $("#"+id).widget("getValue");
			return value;
		},
		setValue: function(target, value){
			var id = $(target).attr("id");
			setTimeout(function(){
				$("#"+id).widget("setValue",value);
			},50);
		},
		resize: function(target, width){
			var id = $(target).attr("id");
			$("#"+id).widget("setWidth",width);
		}
	},
	goodswidget: {  //datagrid 行编辑框
		init: function(container, options){
			var aid = getRandomid();
			var width = options.width-5;
			var input = $('<input id="editorgoodswidget'+aid+'" type="text" text="" value="" style="width: '+width+'px;">').appendTo(container);
			$("#editorgoodswidget"+aid).goodsWidget(options);
			return input;
		},
		getValue: function(target){
			var id = $(target).attr("id");
			var value = $("#"+id).goodsWidget("getValue");
			return value;
		},
		setValue: function(target, value){
			var id = $(target).attr("id");
			setTimeout(function(){
				$("#"+id).goodsWidget("setValue",value);
			},50);
		},
		resize: function(target, width){
			var id = $(target).attr("id");
			var width = width - 5;
			$("#"+id).widget("setWidth",width);
		}
	},
	customerwidget: {  //datagrid 行编辑框
		init: function(container, options){
			var aid = getRandomid();
			var stylestr="";
			if(options.width!=null && options.width>0){
				stylestr = 'style="width:'+options.width+'px;"';
			}
			var input = $('<input id="editorcustomerwidget'+aid+'" '+stylestr+' type="text" text="" value="">').appendTo(container);
			$("#editorcustomerwidget"+aid).customerWidget(options);
			return input;
		},
		getValue: function(target){
			var id = $(target).attr("id");
			var value = $("#"+id).customerWidget("getValue");
			var text = $("#"+id).customerWidget("getText");
			if($("#editorcustomerwidget-text-"+value).length>0){
				$("#editorcustomerwidget-text-"+value).val(text);
			}else{
				$('<input id="editorcustomerwidget-text-'+value+'" type="hidden" value="'+text+'">').appendTo('body');
			}
			return value;
		},
		setValue: function(target, value){
			var id = $(target).attr("id");
			var text = $("#editorcustomerwidget-text-"+value).val();
			setTimeout(function(){
				$("#"+id).customerWidget("setValue",value);
				$("#"+id).customerWidget("setText",text);
			},50);
		}
	},
	widget: {  //datagrid 行编辑框
		init: function(container, options){
			// 默认async为true，true时，如果行编辑时有多个参照窗口时，可能会偶然 导致编辑项错乱。
			$.extend(options, {async: false});
			var aid = getRandomid();
			var stylestr="";
			if(options.width!=null && options.width>0){
				stylestr = 'style="width:'+options.width+'px;"';
			}
			var input = $('<input id="editorwidget'+aid+'" '+stylestr+' type="text" text="" value="">').appendTo(container);
			$("#editorwidget"+aid).widget(options);
			return input;
		},
		getValue: function(target){
			var id = $(target).attr("id");
			var value = $("#"+id).widget("getValue");
			var text = $("#"+id).widget("getText");
			if($("#editorwidget-text-"+value).length>0){
				$("#editorwidget-text-"+value).val(text);
			}else{
				$('<input id="editorwidget-text-'+value+'" type="hidden" value="'+text+'">').appendTo('body');
			}
			return value;
		},
		setValue: function(target, value){
			var id = $(target).attr("id");
			var text = $("#editorwidget-text-"+value).val();
			setTimeout(function(){
				$("#"+id).widget("setValue",value);
			},50);
		}
	},
	readtext: {  //开始日期编辑框
		init: function(container, options){
			var input = $('<input type="text" readonly="readonly"/>').appendTo(container);
			return input;
		},
		getValue: function(target){
			return $(target).val();
		},
		setValue: function(target, value){
			$(target).val(value);
		},
		resize: function(target, width){
			var input = $(target);
			if ($.boxModel == true){
				input.width(width - (input.outerWidth() - input.width())-10);
			} else {
				input.width(width-10);
			}
		}
	},
	startDate: {  //开始日期编辑框
		init: function(container, options){
			startdateid = "date"+getRandomid();
			return  setTimeout(function(){dateout(container,options);},1000);
		},
		getValue: function(target){
			return $(target).val();
		},
		setValue: function(target, value){
			$(target).val(value);
		},
		resize: function(target, width){
			var input = $(target);
			if ($.boxModel == true){
				input.width(width - (input.outerWidth() - input.width())-10);
			} else {
				input.width(width-10);
			}
		}
	},
	endDate: {  //结束日期编辑框
		init: function(container, options){
			enddateid = "date"+getRandomid();
			var str = "",dateFmt="",maxDate="",startDate="",minDate="";
			if(options != undefined){
				startDate = options.startDate;
				minDate = options.minDate;
				if(options.dateFmt == undefined){
					dateFmt = "yyyy-MM-dd";
				}
				else{
					dateFmt = options.dateFmt;
				}
				if(options.maxDate != undefined){
					maxDate = options.maxDate;
				}
				if(options.minDate != undefined){
					if(options.minDate == "startdate"){
						minDate = "#F{$dp.$D("+startdateid+")}";
					}
					else{
						minDate = options.minDate;
					}
				}
				else{
					minDate="1900-01-01";
				}
				str = 'dateFmt:\'' + dateFmt + '\',' + 'maxDate:\'' + maxDate + '\',' + 'startDate:\'' + startDate + '\',' + 'minDate:\'' +  minDate + '\'';
			}
			var input = $('<input id="'+enddateid+'" type="text" onfocus="WdatePicker({'+str+'})"/>').appendTo(container);
			return input;
		},
		getValue: function(target){
			return $(target).val();
		},
		setValue: function(target, value){
			$(target).val(value);
		},
		resize: function(target, width){
			var input = $(target);
			if ($.boxModel == true){
				input.width(width - (input.outerWidth() - input.width())-10);
			} else {
				input.width(width-10);
			}
		}
	},
	dateText: {  //日期编辑框
		init: function(container, options){
			var rid  = getRandomid();
			var str = "",dateFmt="yyyy-MM-dd",minDate="1900-01-01";
			var maxDateStr = "",minDateStr = "",startDateStr = "",method = "";
			var onSelect = null;
			if(options != undefined){
				if(options.dateFmt != undefined){
					dateFmt = options.dateFmt;
				}
				var dateFmtStr = 'dateFmt:\'' + dateFmt + '\',';
				if(options.maxDate != undefined){
					maxDateStr = 'maxDate:\'' + options.maxDate + '\',';
				}
				if(options.minDate != undefined){
					minDate = options.minDate;
				}
				var minDateStr = 'minDate:\'' + minDate + '\'';
				if(options.startDate != undefined){
					startDateStr = 'startDate:\'' + options.startDate + '\',';
				}
				if(options.dchanged != undefined){
					method =  'dchanged:'+ options.dchanged+ ',';
				}
				str = dateFmtStr + maxDateStr + startDateStr + method + minDateStr ;

				if(options.onSelect !=null){
					str += ",onpicked:"+options.onSelect;
				}
			}
			var input = $('<input id="'+rid+'" type="text" class="Wdate" onfocus="WdatePicker({'+str+'})"/>').appendTo(container);
			return input;
		},
		getValue: function(target){
			return $(target).val();
		},
		setValue: function(target, value){
			$(target).val(value);
		},
		resize: function(target, width){
			var input = $(target);
			if ($.boxModel == true){
				input.width(width - (input.outerWidth() - input.width())-10);
			} else {
				input.width(width-10);
			}
		}
	},
	datetimebox: {  //时间编辑框
		init: function(container, options){
			var input = $('<input type="text" onfocus="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm\'})"/>').appendTo(container);
			return input;
		},
		getValue: function(target){
			return $(target).val();
		},
		setValue: function(target, value){
			$(target).val(value);
		},
		resize: function(target, width){
			var input = $(target);
			if ($.boxModel == true){
				input.width(width - (input.outerWidth() - input.width())-5);
			} else {
				input.width(width-10);
			}
		}
	},
	selectboxText: {   //下拉选择框编辑
		init: function(container, options){
			var str = "";
			var select = '';
			var re = '';
			var vals = options.vals;
			var texts = options.texts;
			var defaultCheck = options.defaultChecked;
			var required = options.required;
			if(vals != undefined){
				var valArr = vals.split(",");
			}
			if(texts != undefined){
				var textArr = texts.split(",");
			}
			if(required != undefined){
				re = 'class="easyui-validatebox" required = "true"';
			}
			for(var i=0;i<valArr.length;i++){
				if(defaultCheck != undefined){
					if(valArr[i] == defaultCheck){
						select = 'selected="selected"';
					}
					else{
						select = '';
					}
				}
				str += '<option value="'+valArr[i]+'" '+select+'>'+textArr[i]+'</option>';
			}
			var html = '<select '+re+'>'+str+'</select>';
			var input = $(html).appendTo(container);
			return input;
		},
		getValue: function(target){
			return $(target).val();
		},
		setValue: function(target, value){
			$(target).val(value);
		},
		resize: function(target, width){
			var input = $(target);
			if ($.boxModel == true){
				input.width(width - (input.outerWidth() - input.width())-10);
			} else {
				input.width(width-10);
			}
		}
	},
	defaultSelect:{ //默认下拉选项框
		init: function(container, options){
			if(options != undefined){
				var str = "";
				var htmlbegin = "<select ";
				var optionstr = "",html = "";
				for(var i=0;i<options.data.length;i++){
					var select = '';
					if(options.defaultval != undefined && options.defaultval == options.data[i][options.valueField]){
						select = 'selected="selected"';
					}
					str += '<option value="'+options.data[i][options.valueField]+'" '+select+'>'+options.data[i][options.textField]+'</option>';
				}
				if(options.classid != undefined){
					optionstr = 'class="'+options.classid+'" ';
				}
				if(options.onChange != undefined){
					defaultSelectChangeFun = options.onChange;
					if("" == optionstr){
						optionstr = 'onfocus="oldval=this.value" onchange="defaultSelectChangeFun(oldval,this.value)"';
					}else{
						optionstr += " " + 'onfocus="oldval=this.value" onchange="defaultSelectChangeFun(oldval,this.value)"';
					}
				}
				var htmlend = '>'+str+'</select>';
				html = htmlbegin + optionstr + htmlend;
			}
			var input = $(html).appendTo(container);
			return input;
		},
		getValue: function(target){
			return $(target).val();
		},
		setValue: function(target, value){
			if(value == "disabled"){
				$(target).val(0);
				$(target).attr("disabled","disabled");
			}
			else{
				$(target).val(value);
			}
		},
		resize: function(target, width){
			var input = $(target);
			if ($.boxModel == true){
				input.width(width - (input.outerWidth() - input.width())-10);
			} else {
				input.width(width-10);
			}
		}
	},
	span:{ //span显示框
		init: function(container, options){
			var html = "";
			html = '<span></span>';
			var input = $(html).appendTo(container);
			return input;
		},
		getValue: function(target){
			return $(target).text();
		},
		setValue: function(target, value){
			$(target).text(value);
		},
		resize: function(target, width){

		}
	},
	select:{
		init: function(container, options){
			var input = $('<select></select>').appendTo(container);
			return input;
		},
		getValue: function(target){
			var value = $(target).val();
			return value;
		},
		setValue: function(target, value){
			$(target).val(value);
		},
		resize: function(target, width){
			var input = $(target);
			if ($.boxModel == true){
				input.width(width - (input.outerWidth() - input.width())-10);
			} else {
				input.width(width-10);
			}
		}
	}
});
//数字格式化金额
//s:值 保留几位小数
function fNumber(s, n){
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(),
		r = s.split(".")[1];
	t = "";
	for(i = 0; i < l.length; i ++ )
	{
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return t.split("").reverse().join("") + "." + r;
}
//数字格式金额 默认两位小数
function formatterMoney(val,fixed){
	if(typeof(fixed)=="undefined" || fixed==null || fixed == "" || isNaN(fixed) || fixed <0){
		fixed=2;
	}
	if(val!=null && (val!="" || val==0)){
		if(Number(val)<0){
			//return "-"+fNumber((-Number(val))+"",2);
			var newdata= Number(val).toFixed(fixed);
			if(newdata==0){
				//return newdata.toFixed(fixed);
				return Number(newdata).toFixed(fixed);
			}
			return newdata;
		}else if(Number(val) == 0){
			return Number(val).toFixed(fixed);
		}else{
			//return fNumber(val,2);
			return Number(val).toFixed(fixed);
		}
	}else{
		return "";
	}
}
//只保存数字
function formatterNum(val){
	if(val!=null &&val!=""){
		return Number(val).toFixed(0);
	}else{
		return '';
	}
}
//只保存数字
function formatterNum2(val){
	if(val!=null &&val!=""){
		return Number(val).toFixed(0);
	}else{
		return '0';
	}
}
//只保存不为0的数字 如果数字不存在或者为null 返回空
function formatterBigNum(val){
	if(val!=null &&val!=""){
		return Number(val).toFixed(0);
	}else{
		return "";
	}
}
//只保存不为0的数字 如果数字不存在或者为null 返回空
function formatterBigNum1(val){
	if(val!=null &&val!=""){
		return Number(val).toFixed(0);
	}else{
		return "0";
	}
}

//只保存不为0的数字且不取整 如果数字不存在或者为null 返回空
function formatterBigNumNoLen(val){
	if(val!=null &&val!=""){
		return Number(val);
	}else{
		return "";
	}
}
//只保存不为0的数字且不取整 如果数字不存在或者为0 返回空
function formatterBigNumNoLen1(val){
    if(val!=null &&val!=""){
        return Number(val);
    }else{
        return "0";
    }
}
//使用java正则表达式去掉多余的.与0
function formaterNumSubZeroAndDot(){
	var formaterNumEach = $(".formaterNum");
	$.each(formaterNumEach,function (index,domEle){
		domEle.value = Number(domEle.value);
	});
}

//数字格式金额，自定义n位小数
function formatterDefineMoney(val,n){
	if(typeof(n)=="undefined" || n == null || n == "" || isNaN(n) || n<0){
		n = 2;
	}
	if(val!=null && (val!="" || val==0)){
		if(Number(val)<0){
			//return "-"+fNumber((-Number(val))+"",2);
			var newdata= Number(val).toFixed(n);
			if(newdata==0){
				//return newdata.toFixed(n);
				return Number(newdata).toFixed(n);
			}
			return newdata;
		}else if(Number(val) == 0){
			return Number(val).toFixed(n);
		}else{
			//return fNumber(val,2);
			return Number(val).toFixed(n);
		}
	}else{
		return "";
	}
}
//扩展行编辑
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

$.fn.serializeString = function(){
	var obj = {};
	var count = 0;
	$.each( this.serializeArray(), function(i,o){
		var n = o.name, v = o.value;
		count++;
		obj[n] = obj[n] === undefined ? v
			: $.isArray( obj[n] ) ? obj[n].concat( v )
			: [ obj[n], v ];
	});
	obj.nameCounts = count + "";//表单name个数
	return JSON.stringify(obj);
};

/*
 * 检测对象是否是空对象(不包含任何可读属性)。 //如你上面的那个对象就是不含任何可读属性
 * 方法只既检测对象本身的属性，不检测从原型继承的属性。
 */
function isObjectEmpty(obj){
	for(var name in obj)
	{
		if(obj.hasOwnProperty(name))
		{
			return false;
		}
	}
	return true;
};

function AmountUnitCnChange(n) {
	var fraction = ['角', '分'];
	var digit = [
		'零', '壹', '贰', '叁', '肆',
		'伍', '陆', '柒', '捌', '玖'
	];
	var unit = [
		['元', '万', '亿'],
		['', '拾', '佰', '仟']
	];
	var head = n < 0? '负': '';
	n = Math.abs(n);

	var s = '';

	for (var i = 0; i < fraction.length; i++) {
		var tmpd=Number(n * 10 * Math.pow(10, i));
		tmpd=tmpd.toFixed(2);
		s += (digit[Math.floor(tmpd) % 10] + fraction[i]).replace(/零./, '');
	}
	s = s || '整';
	n = Math.floor(n);

	for (var i = 0; i < unit[0].length && n > 0; i++) {
		var p = '';
		for (var j = 0; j < unit[1].length && n > 0; j++) {
			p = digit[n % 10] + unit[1][j] + p;
			n = Math.floor(n / 10);
		}
		s = p.replace(/(零.)*零$/, '')
				.replace(/^$/, '零')
			+ unit[0][i] + s;
	}
	return head + s.replace(/(零.)*零元/, '元')
			.replace(/(零.)+/g, '零')
			.replace(/^整$/, '零元整');
};

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt) {

	var o = {
		"M+" : this.getMonth()+1,                 //月份
		"d+" : this.getDate(),                    //日
		"h+" : this.getHours(),                   //小时
		"m+" : this.getMinutes(),                 //分
		"s+" : this.getSeconds(),                 //秒
		"q+" : Math.floor((this.getMonth()+3)/3), //季度
		"S"  : this.getMilliseconds()             //毫秒
	};

	if(/(y+)/.test(fmt)) {

		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}

	for(var k in o) {
		if(new RegExp("("+ k +")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
		}
	}

	return fmt;
};

//根据当前时间判断反审单据时是否在会计区间内，true内，可反审，false外，不可反审
function isDoneOppauditBillCaseAccounting(businessdate){
	var flag = false;
	$.ajax({
		url :'system/accounting/isBetweenAccounting.do',
		type:'post',
		data:{businessdate:businessdate},
		dataType:'json',
		async: false,
		success:function(json){
			flag = json.flag
		}
	});
	return flag;
};
/**
 * 获取numberbox的input控件
 * @param id
 * @returns {*|jQuery}
 */
function getNumberBox(id){
	return $("#"+id).textbox('textbox');
};
/**
 *获取numberbox的input控件
 * @param target   ${"#id"}方式传值
 * @returns {*}
 */
function getNumberBoxObject(target){
	return target.textbox('textbox');
};
function setInputFormatterMoney(id,val,fixed){
	if(null == val || "" == val){
		val = 0;
	}
	$("#"+id+"").val(formatterMoney(val,fixed));
};

function exportByAnalyse(queryParam,name,id,url){
	var commonCol = "{\"common\":[[";

	if($("#dialog-autoexport").length < 1){
		$("body").append("<div id='dialog-autoexport'></div>");
	}

	//这是获取到所有的冻结列
	var frozen = $('#'+id).datagrid('getColumnFields',true);
	for(var i = 0 ; i < frozen.length ; ++ i){
		var col = $('#'+id).datagrid( "getColumnOption" , frozen[i] );
		var getCol = {};
		if(col.field != "idok" && col.hidden != true && col.title != undefined){
			getCol["field"] = col.field;
			getCol["title"] = col.title;
		}else{
			continue ;
		}
		commonCol = commonCol +  JSON.stringify(getCol) + "," ;
	}
	//这是获取到所有的解冻列
	var opts = $('#'+id).datagrid('getColumnFields');
	for(var i=0;i<opts.length;i++){
		var col = $('#'+id).datagrid( "getColumnOption" , opts[i] );
		var getCol = {};
		if( (col.hidden == undefined || col.hidden == false ) && col.title != undefined ){
			getCol["field"] = col.field;
			getCol["title"] = col.title;
		}else if(i == opts.length - 1 ){
			commonCol = commonCol +"null]],\"exportname\":\""+name+"\"}";
			break;
		}else{
			continue ;
		}
		if(i != opts.length - 1){
			commonCol = commonCol +  JSON.stringify(getCol) + "," ;
		}else{
			commonCol = commonCol + "," + JSON.stringify(getCol) +"]],\"exportname\":\""+name+"\"}";
		}
	}
	$("#dialog-autoexport").dialog({
		href: 'common/exportAnalysPage.do' ,
		method:'post',
		queryParams:{commonCol:commonCol},
		width: 400,
		height: 300,
		title: '全局导出',
		closed: false,
		cache: false,
		modal: true,
		onLoad: function(){
			$("#common-form-exportAnalysPage").attr("action",url);
			var obj = $('#'+id).datagrid('options');
			if(obj.authority != undefined && obj.authority.formmater != undefined){
				$("#common-formmater-exportAnalysPage").val(obj.authority.formmater);
			}
			$("#exportParam").val(queryParam);
		}
	});
};

//form表单初始化时，生成初始化数据
$(function(){
	$("form").each(function(){
		//改造Form控件
		//添加form表单修改前的内容 提交表单后 获取改动过的内容
		if($(this).find(".oldFormData").length <= 0){
			var oldJSON = $(this).serializeJSON();
			var oldJSONStr = JSON.stringify(oldJSON);
			$("<input type='hidden' class='oldFormData' name='oldFromData' />").appendTo(this);
			$(this).find(".oldFormData").val(oldJSONStr);
		}
	});
});

/**
 * 根据编码类型和编码获取编码名称
 */
function getSysCodeFirstCodeByType(type){
	var codeJson = top.codeJsonCache;
	if(codeJson==null){
		codeJson=Window["codeJsonCache"];
	}
	if(codeJson!=null){
		var codeList = codeJson[type];
		if(codeList!=null && codeList.length>0){
			codeList.sort(function(a,b){
				if(a.seq!=null && b.seq!=null){
					return a.seq-b.seq;
				}
				return 0;
			});
			return codeList[0].code||"";
		}
	}
	return "";
};

Number.prototype.round = function(digits) {
    digits = Math.floor(digits);
    if (isNaN(digits) || digits === 0) {
        return Math.round(this);
    }
    if (digits < 0 || digits > 16) {
        throw 'RangeError: Number.round() digits argument must be between 0 and 16';
    }
    var multiplicator = Math.pow(10, digits);
    return Math.round(this * multiplicator) / multiplicator;
};
Number.prototype.toFixed = function(digits) {
    digits = Math.floor(digits);
    if (isNaN(digits) || digits === 0) {
        return Math.round(this).toString();
    }
    var parts = this.round(digits).toString().split('.');
    var fraction = parts.length === 1 ? '' : parts[1];
    if (digits > fraction.length) {
        fraction += new Array(digits - fraction.length + 1).join('0');
    }
    return parts[0] + '.' + fraction;
};
//除法函数，用来得到精确的除法结果
//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//返回值：arg1除以arg2的精确结果
function accDiv(arg1,arg2){
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""));
        r2=Number(arg2.toString().replace(".",""));
        return (r1/r2)*pow(10,t2-t1);
    }
}
//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function (arg){
    return accDiv(this, arg);
};
//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}
//给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function (arg){
    return accMul(arg, this);
};
//加法函数，用来得到精确的加法结果
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
function accAdd(arg1,arg2){
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    return (arg1*m+arg2*m)/m;
}
//给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function (arg){
    return accAdd(arg,this);
}
//减法函数
function accSub(arg1,arg2){
     var r1,r2,m,n;
     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
     m=Math.pow(10,Math.max(r1,r2));
     //last modify by deeka
     //动态控制精度长度
     n=(r1>=r2)?r1:r2;
     return ((arg2*m-arg1*m)/m).toFixed(n);
}
///给number类增加一个sub方法，调用起来更加方便
Number.prototype.sub = function (arg){
    return accSub(arg,this);
}
 //销售情况统计表选中合计
 function baseSalesReportCountTotalAmount(col,datagrid){
 	var rows =  $("#"+datagrid.id+"").datagrid('getChecked');
	var ordernum = 0;
	var ordertotalbox = 0;
	var orderamount = 0;
	var ordernotaxamount=0;
	var initsendnum = 0;
	var initsendtotalbox = 0;
	var initsendamount = 0;
	var initsendnotaxamount=0;
	var sendnum = 0;
	var sendtotalbox = 0;
	var sendamount = 0;
	var pushbalanceamount = 0;
	var sendnotaxamount = 0;
	var sendcostamount = 0;
	var directreturnnum=0;
	var directreturntotalbox = 0;
	var directreturnamount =0;
	var checkreturnnum=0;
	var checkreturntotalbox = 0;
	var checkreturnamount=0;
	var returnnum =0;
	var returntotalbox = 0;
	var returnamount = 0;
	var salenum = 0;
	var saletotalbox = 0;
	var saleamount = 0;
	var salenotaxamount = 0;
	var saletax = 0;
	var costamount = 0;
	var salemarginamount = 0;
	for(var i=0;i<rows.length;i++){
		ordernum = Number(ordernum)+Number(rows[i].ordernum == undefined ? 0 : rows[i].ordernum);
		ordertotalbox = Number(ordertotalbox)+Number(rows[i].ordertotalbox == undefined ? 0 : rows[i].ordertotalbox);
		orderamount = Number(orderamount)+Number(rows[i].orderamount == undefined ? 0 : rows[i].orderamount);
		ordernotaxamount = Number(ordernotaxamount)+Number(rows[i].ordernotaxamount == undefined ? 0 : rows[i].ordernotaxamount);
		initsendnum = Number(initsendnum)+Number(rows[i].initsendnum == undefined ? 0 : rows[i].initsendnum);
		initsendtotalbox = Number(initsendtotalbox)+Number(rows[i].initsendtotalbox == undefined ? 0 : rows[i].initsendtotalbox);
		initsendamount = Number(initsendamount)+Number(rows[i].initsendamount == undefined ? 0 : rows[i].initsendamount);
		initsendnotaxamount = Number(initsendnotaxamount)+Number(rows[i].initsendnotaxamount == undefined ? 0 : rows[i].initsendnotaxamount);
		sendnum = Number(sendnum)+Number(rows[i].sendnum == undefined ? 0 : rows[i].sendnum);
		sendtotalbox = Number(sendtotalbox)+Number(rows[i].sendtotalbox == undefined ? 0 : rows[i].sendtotalbox);
		sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
		pushbalanceamount = Number(pushbalanceamount)+Number(rows[i].pushbalanceamount == undefined ? 0 : rows[i].pushbalanceamount);
		sendnotaxamount = Number(sendnotaxamount)+Number(rows[i].sendnotaxamount == undefined ? 0 : rows[i].sendnotaxamount);
		sendcostamount = Number(sendcostamount)+Number(rows[i].sendcostamount == undefined ? 0 : rows[i].sendcostamount);
		directreturnnum = Number(directreturnnum)+Number(rows[i].directreturnnum == undefined ? 0 : rows[i].directreturnnum);
		directreturntotalbox = Number(directreturntotalbox)+Number(rows[i].directreturntotalbox == undefined ? 0 : rows[i].directreturntotalbox);
		directreturnamount = Number(directreturnamount)+Number(rows[i].directreturnamount == undefined ? 0 : rows[i].directreturnamount);
		checkreturnnum = Number(checkreturnnum)+Number(rows[i].checkreturnnum == undefined ? 0 : rows[i].checkreturnnum);
		checkreturntotalbox = Number(checkreturntotalbox)+Number(rows[i].checkreturntotalbox == undefined ? 0 : rows[i].checkreturntotalbox);
		checkreturnamount = Number(checkreturnamount)+Number(rows[i].checkreturnamount == undefined ? 0 : rows[i].checkreturnamount);
		returnnum = Number(returnnum)+Number(rows[i].returnnum == undefined ? 0 : rows[i].returnnum);
		returntotalbox = Number(returntotalbox)+Number(rows[i].returntotalbox == undefined ? 0 : rows[i].returntotalbox);
		returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
		salenum = Number(salenum)+Number(rows[i].salenum == undefined ? 0 : rows[i].salenum);
		saletotalbox = Number(saletotalbox)+Number(rows[i].saletotalbox == undefined ? 0 : rows[i].saletotalbox);
		saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
		salenotaxamount = Number(salenotaxamount)+Number(rows[i].salenotaxamount == undefined ? 0 : rows[i].salenotaxamount);
		saletax = Number(saletax)+Number(rows[i].saletax == undefined ? 0 : rows[i].saletax);
		costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
		salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
	}
	var obj = {ordernum:ordernum,ordertotalbox:ordertotalbox,orderamount:orderamount,ordernotaxamount:ordernotaxamount,
				initsendnum:initsendnum,initsendtotalbox:initsendtotalbox,initsendamount:initsendamount,initsendnotaxamount:initsendnotaxamount,
				sendnum:sendnum,sendtotalbox:sendtotalbox,sendamount:sendamount,pushbalanceamount:pushbalanceamount,sendnotaxamount:sendnotaxamount,sendcostamount:sendcostamount,
				directreturnnum:directreturnnum,directreturntotalbox:directreturntotalbox,directreturnamount:directreturnamount,
				checkreturnnum:checkreturnnum,checkreturntotalbox:checkreturntotalbox,checkreturnamount:checkreturnamount,
				returnnum:returnnum,returntotalbox:returntotalbox,returnamount:returnamount,
				salenum:salenum,saletotalbox:saletotalbox,saleamount:saleamount,salenotaxamount:salenotaxamount,saletax:saletax,costamount:costamount,salemarginamount:salemarginamount
   			};
   	if(col != ""){
		obj[col] = '选中合计';
	}else{
		obj['goodsname'] = '选中合计';
	}
	var foot=[];
	foot.push(obj);
	if(null!=SR_footerobject){
   		foot.push(SR_footerobject);
	}
	$("#"+datagrid.id+"").datagrid("reloadFooter",foot);
 }

 //资金回笼情况表选中合计
 function baseFinanceWithDrawnCountTotalAmount(col,datagrid){
	var rows =  $("#"+datagrid.id+"").datagrid('getChecked');
	var withdrawnamount = 0;
	var costwriteoffamount = 0;
	var writeoffmarginamount=0;
	for(var i=0;i<rows.length;i++){
		withdrawnamount = Number(withdrawnamount)+Number(rows[i].withdrawnamount == undefined ? 0 : rows[i].withdrawnamount);
		costwriteoffamount = Number(costwriteoffamount)+Number(rows[i].costwriteoffamount == undefined ? 0 : rows[i].costwriteoffamount);
		writeoffmarginamount = Number(writeoffmarginamount)+Number(rows[i].writeoffmarginamount == undefined ? 0 : rows[i].writeoffmarginamount);
	}
	var obj = {withdrawnamount:withdrawnamount,costwriteoffamount:costwriteoffamount,writeoffmarginamount:writeoffmarginamount};
 	if(col != ""){
		obj[col] = '选中合计';
	}else{
		obj['goodsname'] = '选中合计';
	}
	var foot=[];
	foot.push(obj);
	if(null!=SR_footerobject){
	  	foot.push(SR_footerobject);
	}
  	$("#"+datagrid.id+"").datagrid("reloadFooter",foot);
}

 //销售回笼考核报表选中合计
 function salesWithdrawnAssessCountTotalAmount(col,datagrid){
	var rows =  $("#"+datagrid.id+"").datagrid('getChecked');
	var saletargetamount = 0;
	var saleamount = 0;
	var saledonerate = 0;
	var salemarginamount = 0;
	//选中合计的目标毛利
	var salemargintargetamountsum = 0;
	var salemargindonesurpassrate = 0;
	var withdrawntargetamount = 0;
	var withdrawnamount = 0;
	var writeoffmarginamount = 0;
	var withdrawndonerate = 0;
	//选中合计的回笼目标毛利
	var withdrawnmargintargetamountsum = 0;
	var withdrawnmargindonesurpassrate = 0;
	var writeoffrate = 0;
	var realrate = 0;
	for(var i=0;i<rows.length;i++){
		saletargetamount = Number(saletargetamount)+Number(rows[i].saletargetamount == undefined ? 0 : rows[i].saletargetamount);
		saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
		salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
		//本期目标毛利=本期销售目标*本期毛利率目标
		var marginratetarget = Number(rows[i].marginratetarget == undefined ? 0 : rows[i].marginratetarget);
		var salemargintargetamount = Number(rows[i].saletargetamount == undefined ? 0 : rows[i].saletargetamount)*(marginratetarget/Number(100));
		salemargintargetamountsum = Number(salemargintargetamountsum)+Number(salemargintargetamount);

		withdrawntargetamount = Number(withdrawntargetamount)+Number(rows[i].withdrawntargetamount == undefined ? 0 : rows[i].withdrawntargetamount);
		withdrawnamount = Number(withdrawnamount)+Number(rows[i].withdrawnamount == undefined ? 0 : rows[i].withdrawnamount);
		writeoffmarginamount = Number(writeoffmarginamount)+Number(rows[i].writeoffmarginamount == undefined ? 0 : rows[i].writeoffmarginamount);
		//回笼目标毛利=回笼目标*回笼毛利率目标
		var writeoffratetarget = Number(rows[i].writeoffratetarget == undefined ? 0 : rows[i].writeoffratetarget);
		var withdrawnmargintargetamount = Number(rows[i].withdrawntargetamount == undefined ? 0 : rows[i].withdrawntargetamount)*(writeoffratetarget/Number(100))
		withdrawnmargintargetamountsum = Number(withdrawnmargintargetamountsum)+Number(withdrawnmargintargetamount);
	}
	//实际毛利率=销售毛利额/销售金额*100
	if(Number(saleamount) != Number(0)){
		realrate = Number(salemarginamount)/Number(saleamount)*Number(100);
	}
	//回笼毛利率= 回笼毛利额/回笼金额*100
	if(Number(withdrawnamount) != Number(0)){
		writeoffrate = Number(writeoffmarginamount)/Number(withdrawnamount)*Number(100);
	}
	//销售完成率=销售金额/本期销售目标
	if(Number(saletargetamount) != Number(0)){
		if(Number(saleamount)>= Number(0) && Number(saletargetamount)>Number(0)){
			saledonerate = saleamount/saletargetamount*Number(100);
		}else if(saleamount >= saletargetamount){
			saledonerate = 100;
		}else{
			saledonerate = (saleamount-saletargetamount)/Math.abs(saletargetamount)*Number(100);
		}
	}
	//销售业绩超率
	if(Number(salemargintargetamountsum) >Number(0) &&  Number(salemarginamount) >= Number(0)){
		salemargindonesurpassrate = salemarginamount / salemargintargetamountsum * Number(100);
	}else if(Number(salemargintargetamountsum) != Number(0)) {
		if (Number(salemargintargetamountsum) <= Number(salemarginamount)) {
			salemargindonesurpassrate = 100;
		} else {
			var mindata = Number(salemarginamount) - Number(salemargintargetamountsum);
			salemargindonesurpassrate = mindata / Math.abs(salemargintargetamountsum) * Number(100);
		}
	}
	//回笼完成率=回笼金额/回笼目标
	if(Number(withdrawntargetamount) > Number(0) && Number(withdrawnamount) >= Number(0)){
		withdrawndonerate = withdrawnamount/withdrawntargetamount*Number(100);
	}else if(Number(withdrawntargetamount) != Number(0)){
		if(Number(withdrawnamount)<Number(withdrawntargetamount)){
			withdrawndonerate = (withdrawnamount - withdrawntargetamount)/Math.abs(withdrawntargetamount)*Number(100);
		}else {
			withdrawndonerate = 100;
		}
	}
	//回笼业绩超率=回笼毛利额/回笼目标毛利
	if(Number(withdrawnmargintargetamountsum) > Number(0) && Number(writeoffmarginamount)>= Number(0)){
		withdrawnmargindonesurpassrate = writeoffmarginamount/withdrawnmargintargetamountsum*Number(100);
	}else if(Number(withdrawnmargintargetamountsum) != Number(0)){
		if(Number(withdrawnmargintargetamountsum) > Number(writeoffmarginamount)){
			withdrawnmargindonesurpassrate = (writeoffmarginamount-withdrawnmargintargetamountsum)/Math.abs(withdrawnmargintargetamountsum)*Number(100);
		}else{
			withdrawnmargindonesurpassrate = 100;
		}
	}
	var obj={saletargetamount:saletargetamount,saleamount:saleamount,saledonerate:saledonerate,salemarginamount:salemarginamount,salemargintargetamount:salemargintargetamount,salemargindonesurpassrate:salemargindonesurpassrate,
		withdrawntargetamount:withdrawntargetamount,withdrawnamount:withdrawnamount,writeoffmarginamount:writeoffmarginamount,withdrawnmargintargetamount:withdrawnmargintargetamount,withdrawndonerate:withdrawndonerate,
		withdrawnmargindonesurpassrate:withdrawnmargindonesurpassrate,writeoffrate:writeoffrate,realrate:realrate
	};
	obj[col] = '选中合计';

	var foot=[];
	foot.push(obj);
	if(null!=footerobject){
		foot.push(footerobject);
	}
	$("#"+datagrid.id+"").datagrid("reloadFooter",foot);
}

 //行编辑后计算合计数据
 function salesWithdrawnAssessCountTotalAmountCaseEndEditing(obj,datagrid){
	 var newfoot = [];
	 var footerrows = datagrid.datagrid('getFooterRows');
	 if(null!=footerrows && footerrows.length>0){
		 for(var i=0;i<footerrows.length;i++){
			 var foot = footerrows[i];
			 if(foot.issum == "1"){
				 var saletargetamount = obj.targetamount;
				 var saleamount = foot.saleamount;
				 var saledonerate = 0;
				 var salemarginamount = foot.salemarginamount;
				 //选中合计的目标毛利
				 var salemargintargetamountsum = obj.salemargintargetamount;
				 var salemargindonesurpassrate = 0;
				 var withdrawntargetamount = obj.field05;
				 var withdrawnamount = foot.withdrawnamount;
				 var writeoffmarginamount =foot.writeoffmarginamount;
				 var withdrawndonerate = 0;
				 //选中合计的回笼目标毛利
				 var withdrawnmargintargetamountsum = obj.withdrawnmargintargetamount;
				 var withdrawnmargindonesurpassrate = 0;
				 var writeoffrate = 0;
				 var realrate = 0;
				 //销售完成率=销售金额/本期销售目标
				 if(Number(saletargetamount) != Number(0)){
					 if(Number(saleamount)>= Number(0) && Number(saletargetamount)>Number(0)){
						 saledonerate = saleamount/saletargetamount*Number(100);
					 }else if(saleamount >= saletargetamount){
						 saledonerate = 100;
					 }else{
						 saledonerate = (saleamount-saletargetamount)/Math.abs(saletargetamount)*Number(100);
					 }
				 }
				 foot.saletargetamount = saletargetamount;
				 foot.salemargintargetamount = salemargintargetamountsum;
				 foot.withdrawntargetamount = withdrawntargetamount;
				 foot.withdrawnmargintargetamount = withdrawnmargintargetamountsum;
				 foot.saledonerate = saledonerate;
				 //销售业绩超率
				 if(Number(salemargintargetamountsum) >Number(0) &&  Number(salemarginamount) >= Number(0)){
					 salemargindonesurpassrate = salemarginamount / salemargintargetamountsum * Number(100);
				 }else if(Number(salemargintargetamountsum) != Number(0)) {
					 if (Number(salemargintargetamountsum) <= Number(salemarginamount)) {
						 salemargindonesurpassrate = 100;
					 } else {
						 var mindata = Number(salemarginamount) - Number(salemargintargetamountsum);
						 salemargindonesurpassrate = mindata / Math.abs(salemargintargetamountsum) * Number(100);
					 }
				 }
				 foot.salemargindonesurpassrate = salemargindonesurpassrate;
				 //回笼完成率=回笼金额/回笼目标
				 if(Number(withdrawntargetamount) > Number(0) && Number(withdrawnamount) >= Number(0)){
					 withdrawndonerate = withdrawnamount/withdrawntargetamount*Number(100);
				 }else if(Number(withdrawntargetamount) != Number(0)){
					 if(Number(withdrawnamount)<Number(withdrawntargetamount)){
						 withdrawndonerate = (withdrawnamount - withdrawntargetamount)/Math.abs(withdrawntargetamount)*Number(100);
					 }else {
						 withdrawndonerate = 100;
					 }
				 }
				 foot.withdrawndonerate = withdrawndonerate;
				 //回笼业绩超率=回笼毛利额/回笼目标毛利
				 if(Number(withdrawnmargintargetamountsum) > Number(0) && Number(writeoffmarginamount)>= Number(0)){
					 withdrawnmargindonesurpassrate = writeoffmarginamount/withdrawnmargintargetamountsum*Number(100);
				 }else if(Number(withdrawnmargintargetamountsum) != Number(0)){
					 if(Number(withdrawnmargintargetamountsum) > Number(writeoffmarginamount)){
						 withdrawnmargindonesurpassrate = (writeoffmarginamount-withdrawnmargintargetamountsum)/Math.abs(withdrawnmargintargetamountsum)*Number(100);
					 }else{
						 withdrawnmargindonesurpassrate = 100;
					 }
				 }
				 foot.withdrawnmargindonesurpassrate = withdrawnmargindonesurpassrate;
				 newfoot.push(foot);
			 }else{
				 newfoot.push(foot);
			 }
		 }
		 datagrid.datagrid("reloadFooter",newfoot);
	 }
 }

function printByAnalyse(name,id,url,summary){
	//获取打印的表格的宽度，判断默认横向还是纵向
	//50是序号列的宽度
    var tablewidth = 50;
    var arrays = $("#" + id).datagrid('options').columns;
    for (var i = 0; i < arrays[0].length; i++) {
        var filed = arrays[0][i];
        if (!filed.hidden) {
            tablewidth += filed.width+10;
        }
    }

    var iid = "dialog-baseprint";
    var dialog = $("<div id='"+iid+"'></div>");
    dialog.appendTo("body");
    dialog.dialog({
        href: 'common/basePrintDialog.do' ,
        method:'post',
        queryParams:{ id:id, url:url, title:name, summary:summary,tablewidth:tablewidth },
        width: 400,
        height: 185,
        title: '全局打印',
        closed: false,
        cache: false,
        modal: true,
        onClose: function () {
            $("#" + iid).dialog("destroy");
        }
    });
}
function getCommonlCol(queryParam,name,id,url) {
    var commonCol = "{\"common\":[[";

    var $data = $('#'+id);

    var commom=$data.datagrid('options').columns;
    for(var i=0;i<commom.length;i++){
        var array=commom[i];
        if(i>0){
            commonCol=commonCol+",["
        }
        for(var j=0;j<array.length;j++){
            var col = array[j];
            var getCol = {};
            if( (col.hidden == undefined || col.hidden == false ) && col.title != undefined ){

                getCol["field"] = col.field;
                getCol["title"] = col.title;
                getCol["colspan"] = col.colspan;
                getCol["rowspan"] = col.rowspan;
                //打印的时候宽度和实际宽度有差异，需要加一点
                getCol["width"]=col.width+10;
                getCol["align"]=col.align;
            }
            else{
                continue ;
            }
            if(j != array.length - 1){
                commonCol = commonCol +  JSON.stringify(getCol) + "," ;
            }else{
                commonCol=commonCol +  JSON.stringify(getCol) +"]";
            }
        }
        if(i==commom.length-1){
            commonCol = commonCol  +"],\"exportname\":\""+name+"\"}";
        }
    }
    return commonCol;
}
function post(URL, PARAMS) { var temp_form = document.createElement("form");
    temp_form .action = URL;
    temp_form .target = "_blank";
    temp_form .method = "post";
    temp_form .style.display = "none"; for (var x in PARAMS) { var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = PARAMS[x];
        temp_form .appendChild(opt);
    }
    document.body.appendChild(temp_form);
    temp_form.submit();
}
