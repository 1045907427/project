{商品编码}[分隔符]"~~"
// 每行格式~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// 编码~~名称~~简码~~商品税目~~税率~~规格型号~~计量单位~~单价~~含税价标志~~隐藏标志~~中外合作油气田~~税收分类编码~~是否享受优惠政策~~税收分类编码名称~~优惠政策类型~~零税率标识~~编码版本号
<#list goodsList as goodsInfo>
${goodsInfo.jsgoodsid!}~~${goodsInfo.name!}~~${goodsInfo.id!}~~${goodsInfo.defaulttaxtype!}~~<#if goodsInfo.taxrate?exists>${goodsInfo.taxrate?string('0.##')}</#if>~~${goodsInfo.model!}~~${goodsInfo.mainunitName!}~~0~~False~~~~~~106010504~~~~~~~~~~
</#list>