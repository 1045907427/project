
/**
 * from表单序列化成JSON对象
 */
$.fn.serializeJSON = function(){
    var serializeObj = {};
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
};

// jquery validate 扩展验证
/**
 * 扩展验证金额是否合法
 */
$.validator.addMethod('money', function(value, element) {

    if(value == undefined || value == null || value == '') {

        return true;
    }

    var regex = /^-?[0-9]+(.[0-9]+)?$/;

    return this.optional(element) || regex.test(value);
}, '请输入正确的金额');
//验证文字长度
$.validator.addMethod('maxByteLength', function(value, element, param) {

    value = value || '';
    var length = value.length;
    for (var i = 0; i < value.length; i++) {
        if (value.charCodeAt(i) > 127) {
            length++;
        }
    }
    return length <= param;

}, $.validator.format('请输入最大长度为{0}个字节(一个中文字算2个字节)'));

//数字格式金额 默认两位小数
function formatterMoney(val, fixed){

    if(typeof(fixed) == 'undefined' || fixed==null || fixed == '' || isNaN(fixed) || fixed < 0){
        fixed = 2;
    }

    if(val != null && (val != '' || val == 0)){
        if(Number(val) < 0){

            var newdata = Number(val).toFixed(fixed);
            if(newdata == 0){

                return Number(newdata).toFixed(fixed);
            }

            return newdata;

        } else if(Number(val) == 0){

            return Number(val).toFixed(fixed);

        } else {

            return Number(val).toFixed(fixed);
        }

    } else {

        return '';
    }
}

//只保存数字
function formatterNum(val){

    if(val != null && val != ''){

        return Number(val).toFixed(0);

    } else {

        return '';
    }
}

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

/**
 *
 * @param n
 * @returns {string}
 * @constructor
 */
function moneyToUpper(n) {

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
    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
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


//只保存不为0的数字且不取整 如果数字不存在或者为null 返回空
function formatterBigNumNoLen(val){
    if(val!=null &&val!=""){
        return Number(val);
    }else{
        return "";
    }
}


