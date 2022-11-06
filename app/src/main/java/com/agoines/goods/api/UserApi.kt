package com.agoines.goods.api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.agoines.goods.api.bean.LoginUserBean
import com.agoines.goods.api.bean.result.LoginResult
import com.agoines.goods.api.converter.MoshiConverter
import com.agoines.goods.api.converter.moshiJson
import com.agoines.goods.data.getUrl
import com.drake.net.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


context(CoroutineScope)
        suspend fun DataStore<Preferences>.login(
    username: String,
    password: String,
    autoLogin: Boolean
): Flow<LoginResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<LoginResult>(url + "user/login") {
                converter = MoshiConverter()
                moshiJson(
                    LoginUserBean::class.java,
                    LoginUserBean(autoLogin, username, password)
                )
            }.await())
        }
    }
}