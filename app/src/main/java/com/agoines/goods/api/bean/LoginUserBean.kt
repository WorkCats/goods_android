package com.agoines.goods.api.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginUserBean(
    @SerialName(value = "auto_login") val autoLogin: Boolean,
    val username: String,
    val password: String
    )