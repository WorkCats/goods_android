package com.agoines.goods.api.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoodNameBean(
    @SerialName(value = "good_name") val goodName: String
)