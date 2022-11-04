package com.agoines.goods.api

import com.agoines.goods.api.bean.DelGoodBean
import com.agoines.goods.api.bean.result.GetGoodResult
import com.agoines.goods.api.bean.result.TextResult
import com.agoines.goods.api.converter.MoshiConverter
import com.agoines.goods.api.converter.moshiJson
import com.drake.net.Post
import kotlinx.coroutines.CoroutineScope

const val GET_GOOD_LIST_URL = "good/getGoodList"

const val DEL_GOOD_URL = "good/delGood"

context(CoroutineScope)
suspend fun String.getGoodList(): GetGoodResult {
    return Post<GetGoodResult>(this@getGoodList + GET_GOOD_LIST_URL) {
        converter = MoshiConverter()
    }.await()
}

context(CoroutineScope)
suspend fun String.delGood(goodId: String): TextResult {
    return Post<TextResult>(this@delGood + DEL_GOOD_URL) {
        converter = MoshiConverter()
        moshiJson(
            DelGoodBean::class.java,
            DelGoodBean(goodId)
        )
    }.await()
}