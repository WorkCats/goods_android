package com.agoines.goods.bean.result

import com.agoines.goods.data.Good
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetGoodResult(
    @Json(name = "good_list")val goodList: List<Good>,
    @Json(name = "errmsg") val errMsg: String,
    @Json(name = "errcode") val errCode: Int
)