package com.agoines.goods.api.result

import com.agoines.goods.data.Good

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GetGoodResult(
    @SerialName(value = "good_list") val goodList: List<Good>,
    @SerialName(value = "errmsg") val errMsg: String,
    @SerialName(value = "errcode") val errCode: Int
)