package com.agoines.goods.api.bean.result

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResult(
    val token: String,
    @Json(name = "errmsg") val errMsg: String,
    @Json(name = "errcode") val errCode: Int
)