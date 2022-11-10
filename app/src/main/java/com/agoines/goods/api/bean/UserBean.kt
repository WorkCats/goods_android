package com.agoines.goods.api.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBean(
    @SerialName(value = "is_administrator") val isAdministrator: Boolean,
    val username: String,
    val password: String
)