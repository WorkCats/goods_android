package com.agoines.goods.api.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DelGoodBean(
    @SerialName(value = "good_id") val goodId: String
)