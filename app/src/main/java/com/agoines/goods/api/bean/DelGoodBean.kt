package com.agoines.goods.api.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data  class DelGoodBean(
    @Json(name = "good_id") val goodId: String
)