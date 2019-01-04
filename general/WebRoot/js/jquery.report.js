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