package com.agoines.goods.api.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginUserBean(
    @Json(name = "auto_login") val autoLogin: Boolean,
    val username: String,
    val password: String
    )