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
}
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
}
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