package com.agoines.goods.api

import com.agoines.goods.api.bean.LoginUserBean
import com.agoines.goods.api.bean.result.LoginResult
import com.agoines.goods.api.converter.MoshiConverter
import com.agoines.goods.api.converter.moshiJson
import com.drake.net.Post
import kotlinx.coroutines.CoroutineScope

const val LOGIN_URL = "user/login"

context(CoroutineScope)
suspend fun String.login(
    username: String,
    password: String,
    autoLogin: Boolean
): LoginResult {
    return Post<LoginResult>(this@login + LOGIN_URL) {
        converter = MoshiConverter()
        moshiJson(
            LoginUserBean::class.java,
            LoginUserBean(autoLogin, username, password)
        )
    }.await()
}