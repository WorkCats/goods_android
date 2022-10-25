package com.agoines.goods.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Good(
    val id: String,
    val name: String,
    val size: Int,
    @Json(name = "user_name") val userName: String
)