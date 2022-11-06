package com.agoines.goods.api.bean.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TextResult(
    @SerialName(value = "errmsg") val errMsg: String,
    @SerialName(value = "errcode") val errCode: Int
)