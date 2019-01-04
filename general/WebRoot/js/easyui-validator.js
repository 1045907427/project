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
    length:{validator:function(value,param){ 
        var len=$.trim(value).length; 
            return len>=param[0]&&len<=param[1]; 
        }, 
            message:"输入内容长度必须介于{0}和{1}之间." 
        }, 
    validLength:{
	    validator:function(value, len){
		    var reg=eval("/^[A-Za-z0-9]{"+len+"}$/");//正则表达式使用变量 
		    return reg.test(value)
	    },
	    message:'请输入{0}位字符!'
    },
    phone : {// 验证电话号码 
        validator : function(value) { 
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
        }, 
        message : '格式不正确,请使用下面格式:020-88888888' 
    }, 
    mobile : {// 验证手机号码 
        validator : function(value) { 
            return /^(13|14|15|17|18)\d{9}$/i.test(value); 
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
            return /^\d+(\.\d+)?$/i.test(value); 
        }, 
        message : '请输入数字，并确保格式正确' 
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
        message : '请输入整数' 
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
    signfloat:{
    	validator: function(value){
    		return /^[-+]?([1-9]\d*|0)(\.\d*)?$/i.test(value);
    	},
    	message:'请输入数字'
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
//            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value); 
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
            return /d+.d+.d+.d+/i.test(value); 
        }, 
        message : 'IP地址格式不正确' 
    }, 
    name : {// 验证姓名，可以是中文或英文 
            validator : function(value) { 
                return /^[\Α-\￥]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value); 
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
    } ,
    email:{
    	validator : function(value){
	    	return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
		}, 
		message : '请输入有效的邮箱(例：abc@hotnail.com)' 
    },
  //验证角色别名是否唯一
	authorityAliasCheck:{  
    	validator: function (value, param) {  
    		var flag = false;
    		if(param&&value==param[0]){
    			flag = true;
    		}else{
	        	$.ajax({   
		            url :'accesscontrol/checkAuthorityAlias.do?alias='+value,
		            type:'post',
		            dataType:'json',
		            async: false,
		            success:function(json){
		            	flag =  json.flag;
		            }
		        });
    		}
	        return flag;
        },  
        message:'角色别名已重复，请重新输入!'  
    }
    
});
/* 密码由字母和数字组成，至少6位 */  
var safePassword = function (value) {  
    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));  
}  
var idCard = function (value) {  
    if (value.length == 18 && 18 != value.length) return false;  
    var number = value.toLowerCase();  
    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';  
    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);  
    if (re == null || a.indexOf(re[1]) < 0) return false;  
    if (re[2].length == 9) {  
        number = number.substr(0, 6) + '19' + number.substr(6);  
        d = ['19' + re[4], re[5], re[6]].join('-');  
    } else d = [re[9], re[10], re[11]].join('-');  
    //if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;  
    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];  
    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));  
}

