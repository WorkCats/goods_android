package com.agoines.goods.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Good(
    val id: String,
    val name: String,
    val size: UInt,
    @SerialName(value = "user_name") val userName: String
)