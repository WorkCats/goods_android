package com.agoines.goods.api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.agoines.goods.api.bean.LoginUserBean
import com.agoines.goods.api.bean.UserBean
import com.agoines.goods.api.bean.UsernameBean
import com.agoines.goods.api.result.TokenResult
import com.agoines.goods.api.converter.SerializationConverter
import com.agoines.goods.api.converter.serializationJson
import com.agoines.goods.api.result.GoodListResult
import com.agoines.goods.api.result.TextResult
import com.agoines.goods.api.result.UserListResult
import com.agoines.goods.api.result.UsernameListResult
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
): Flow<TokenResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<TokenResult>(url + "user/login") {
                converter = SerializationConverter()
                serializationJson(LoginUserBean(autoLogin, username, password))
            }.await())
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.signup(
    username: String,
    password: String,
    isAdministrator: Boolean
): Flow<TextResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<TextResult>(url + "user/signup") {
                converter = SerializationConverter()
                serializationJson(UserBean(isAdministrator, username, password))
            }.await())
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.delUser(
    username: String,
): Flow<TextResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<TextResult>(url + "user/delUser") {
                converter = SerializationConverter()
                serializationJson(UsernameBean(username))
            }.await())
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.searchUser(
    username: String,
): Flow<UserListResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<UserListResult>(url + "user/searchUser") {
                converter = SerializationConverter()
                serializationJson(UsernameBean(username))
            }.await())
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.autologin(): Flow<TokenResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<TokenResult>(url + "user/autologin") {
                converter = SerializationConverter()
            }.await())
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.getUserList(): Flow<UserListResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<UserListResult>(url + "user/getUserList") {
                converter = SerializationConverter()
            }.await())
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.updateUser(
    username: String,
    password: String,
    isAdministrator: Boolean
): Flow<TextResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<TextResult>(url + "user/updateUser") {
                converter = SerializationConverter()
                serializationJson(UserBean(isAdministrator, username, password))
            }.await())
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.getUsernameList(): Flow<UsernameListResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<UsernameListResult>(url + "user/getUsernameList") {
                converter = SerializationConverter()
            }.await())
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.getGoods(
    username: String,
): Flow<GoodListResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<GoodListResult>(url + "user/getGoods") {
                converter = SerializationConverter()
                serializationJson(UsernameBean(username))
            }.await())
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.isAdministrator(): Flow<TextResult> {
    return flow {
        getUrl().collect { url ->
            emit(Post<TextResult>(url + "user/isAdministrator") {
                converter = SerializationConverter()
            }.await())
        }
    }
}