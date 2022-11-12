package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import com.agoines.goods.api.getGoodList
import com.agoines.goods.data.Good
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
}