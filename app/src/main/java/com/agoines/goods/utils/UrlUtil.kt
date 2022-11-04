package com.agoines.goods.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.agoines.goods.data.TOKEN
import com.drake.net.NetConfig
import com.drake.net.interceptor.RequestInterceptor
import com.drake.net.okhttp.setRequestInterceptor
import com.drake.net.request.BaseRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

context(CoroutineScope)
fun DataStore<Preferences>.setRequestInterceptor(url: String) {
    NetConfig.initialize(url) {
        setRequestInterceptor(object : RequestInterceptor {
            override fun interceptor(request: BaseRequest) {
                launch(Dispatchers.IO) {
                    this@setRequestInterceptor.data.map { preferences ->
                        preferences[TOKEN]!!
                    }.collect { token ->
                        request.setHeader("authorization", token)
                    }
                }
            }
        })
    }
}