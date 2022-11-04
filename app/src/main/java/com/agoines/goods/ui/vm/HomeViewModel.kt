package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agoines.goods.bean.result.GetGoodResult
import com.agoines.goods.converter.MoshiConverter
import com.agoines.goods.data.Good
import com.agoines.goods.data.USER_URL
import com.agoines.goods.utils.setRequestInterceptor
import com.drake.net.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
            }.collect { url ->
                dataStore.setRequestInterceptor(url + "good/getGoodList")
            }
        }
    }

    fun getGoodList(): Flow<List<Good>> {
        return callbackFlow {
            dataStore.data.map { preferences ->
                preferences[USER_URL]!!

            }.collect {
                viewModelScope.launch(IO) {
                    val getGoodResult = Post<GetGoodResult>(it + "good/getGoodList") {
                        converter = MoshiConverter()
                    }.await()
                    this@callbackFlow.send(getGoodResult.goodList)
                }
            }
        }

    }
}