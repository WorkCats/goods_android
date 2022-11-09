package com.agoines.goods.api.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoodBean(
    @SerialName(value = "id") val id: String,
    val name: String,
    val size: UInt,
    @SerialName(value = "user_name") val userName: String,
)