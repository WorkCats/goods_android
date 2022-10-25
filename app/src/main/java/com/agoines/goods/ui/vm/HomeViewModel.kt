package com.agoines.goods.ui.vm

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agoines.goods.bean.result.GetGoodResult
import com.agoines.goods.data.TOKEN
import com.agoines.goods.data.USER_URL
import com.drake.net.NetConfig
import com.drake.net.Post
import com.drake.net.interceptor.RequestInterceptor
import com.drake.net.okhttp.setRequestInterceptor
import com.drake.net.request.BaseRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val toast: Toast
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                NetConfig.initialize(preferences[USER_URL] + "") {
                    setRequestInterceptor(object : RequestInterceptor {
                        override fun interceptor(request: BaseRequest) {
                            request.setHeader("authorization", preferences[TOKEN]!!)
                        }
                    })
                }
            }
        }
    }


    fun getGoodList(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                val getGoodResult = Post<GetGoodResult>(preferences[USER_URL] + "user/login") {

                }.await()
            }
        }
    }
}