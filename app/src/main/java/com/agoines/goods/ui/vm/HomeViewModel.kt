package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agoines.goods.api.DEL_GOOD_URL
import com.agoines.goods.api.GET_GOOD_LIST_URL
import com.agoines.goods.api.delGood
import com.agoines.goods.api.getGoodList
import com.agoines.goods.data.Good
import com.agoines.goods.data.getUrl
import com.agoines.goods.di.showShortText
import com.agoines.goods.utils.setRequestInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val toast: Toast
) : ViewModel() {

    fun getGoodList(): Flow<List<Good>> {
        return callbackFlow {
            dataStore.getUrl().collect { url ->
                dataStore.setRequestInterceptor(url + GET_GOOD_LIST_URL)
                val getGoodResult = url.getGoodList()
                this@callbackFlow.trySend(getGoodResult.goodList)
            }
        }
    }

    fun delGood(goodId: String, event: () -> Unit) {
        viewModelScope.launch(IO) {
            dataStore.getUrl().collect { url ->
                dataStore.setRequestInterceptor(url + DEL_GOOD_URL)
                val delGoodResult = url.delGood(goodId)
                when (delGoodResult.errCode) {
                    0 -> event.invoke()
                    else -> toast.showShortText(delGoodResult.errMsg)
                }
            }
        }
    }
}