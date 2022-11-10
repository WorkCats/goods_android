package com.agoines.goods.api.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResult(
    val token: String,
    @SerialName(value = "errmsg") val errMsg: String,
    @SerialName(value = "errcode") val errCode: Int
)