package com.agoines.goods.api.result

import com.agoines.goods.data.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UsernameListResult(
    @SerialName(value = "username_list") val usernameList: List<String>,
    @SerialName(value = "errmsg") val errMsg: String,
    @SerialName(value = "errcode") val errCode: Int
)