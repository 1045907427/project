{商品编码}[分隔符]"~~"
// 每行格式~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// 编码~~名称~~简码~~商品税目~~税率~~规格型号~~计量单位~~单价~~含税价标志~~隐藏标志~~中外合作油气田~~税收分类编码~~是否享受优惠政策~~税收分类编码名称~~优惠政策类型~~零税率标识~~编码版本号
<#list goodsList as goodsInfo>
<#if (goodsInfo.issortline?exists) && (goodsInfo.issortline?trim =='是' || goodsInfo.issortline?trim =='true' || goodsInfo.issortline =='1' )>
${goodsInfo.jsgoodsid!}~~${fiterStringFunc(goodsInfo.name)}~~
<#else >
${goodsInfo.jsgoodsid!}~~${fiterStringFunc(goodsInfo.name)}~~${fiterStringFunc(goodsInfo.jm)!}~~${goodsInfo.spsm!}~~${goodsInfo.sl!}~~${fiterStringFunc(goodsInfo.ggxh)!}~~${goodsInfo.jldw!}~~${goodsInfo.dj!}~~${goodsInfo.hsjbz!}~~${goodsInfo.ycbz!}~~${goodsInfo.zwhzyqt!}~~${goodsInfo.ssflbm!}~~${goodsInfo.sfxsyhzc!}~~${fiterStringFunc(goodsInfo.ssflbmmc)!}~~${goodsInfo.yhzclx!}~~${goodsInfo.lslbs!}~~${goodsInfo.bmbbh!}
</#if>
</#list>