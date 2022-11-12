package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agoines.goods.api.delGood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DelBottomSheetViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val toast: Toast
) : ViewModel() {

    fun delGood(goodId: String, resultEvent: () -> Unit, throwEvent: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.delGood(goodId).collect { delGoodResult ->
                when (delGoodResult.errCode) {
                    0 -> resultEvent()
                    else -> throwEvent()
                }
            }
        }
    }
}