package com.agoines.goods.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Good(
    val id: String,
    var name: String,
    var size: UInt,
    @SerialName(value = "user_name") var userName: String
)