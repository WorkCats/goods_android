package com.agoines.goods.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName(value = "is_administrator") val isAdministrator: Boolean,
    val username: String,
    val password: String
)