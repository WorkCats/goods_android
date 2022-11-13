package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agoines.goods.api.addGood
import com.agoines.goods.api.hasGood
import com.agoines.goods.data.Good
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBottomSheetViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val toast: Toast
) : ViewModel() {

    fun addGood(good: Good, resultEvent: () -> Unit, throwEvent: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.addGood(good).collect { result ->
                when (result.errCode) {
                    0 -> resultEvent()
                    else -> throwEvent()
                }
            }
        }
    }

    fun hasGood(goodId: String, resultEvent: (Boolean) -> Unit, throwEvent: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.hasGood(goodId).collect { result ->
                when (result.errCode) {
                    0 -> resultEvent(result.errMsg.toBoolean())
                    else -> throwEvent()
                }
            }
        }
    }
}