package com.agoines.goods.ui.vm

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agoines.goods.bean.result.GetGoodResult
import com.agoines.goods.converter.MoshiConverter
import com.agoines.goods.data.TOKEN
import com.agoines.goods.data.USER_URL
import com.drake.net.NetConfig
import com.drake.net.Post
import com.drake.net.interceptor.RequestInterceptor
import com.drake.net.okhttp.setRequestInterceptor
import com.drake.net.request.BaseRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val toast: Toast
) : ViewModel() {
    init {
        viewModelScope.launch(IO) {
            dataStore.data.map { preferences ->
                preferences[USER_URL]!!
            }.collect{ url ->
                NetConfig.initialize(url + "good/getGoodList") {
                    setRequestInterceptor(object : RequestInterceptor {
                        override fun interceptor(request: BaseRequest) {
                            viewModelScope.launch(IO) {
                                dataStore.data.map { preferences ->
                                    preferences[TOKEN]!!
                                }.collect { token ->
                                    request.setHeader("authorization", token)
                                }
                            }
                        }
                    })

                }
            }
        }
    }


    fun getGoodList() {

        viewModelScope.launch(IO) {
            dataStore.data.map { preferences ->
                preferences[USER_URL]!!

            }.collect{

                val getGoodResult = Post<GetGoodResult>(it + "good/getGoodList") {
                    converter = MoshiConverter()
                }.await()

                println(getGoodResult.goodList)
            }
        }
    }
}