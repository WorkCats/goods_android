package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agoines.goods.api.delGood
import com.agoines.goods.api.getGoodList
import com.agoines.goods.data.Good
import com.agoines.goods.di.showShortText
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
            dataStore.getGoodList().collect {
                this@callbackFlow.trySend(it.goodList)
            }
        }
    }

    fun delGood(goodId: String, event: () -> Unit) {
        viewModelScope.launch(IO) {
            dataStore.delGood(goodId).collect { delGoodResult ->
                when (delGoodResult.errCode) {
                    0 -> event.invoke()
                    else -> toast.showShortText(delGoodResult.errMsg)
                }
            }
        }
    }

}