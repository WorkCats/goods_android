package com.agoines.goods.api.result

import com.agoines.goods.data.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserListResult(
    @SerialName(value = "user_list") val userList: List<User>,
    @SerialName(value = "errmsg") val errMsg: String,
    @SerialName(value = "errcode") val errCode: Int
)
