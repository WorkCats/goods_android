package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agoines.goods.api.addGood
import com.agoines.goods.api.updateGood
import com.agoines.goods.data.Good
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateBottomSheetViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val toast: Toast
) : ViewModel() {
    fun updateGood(good: Good, resultEvent: () -> Unit, throwEvent: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.updateGood(good).collect { result ->
                when (result.errCode) {
                    0 -> resultEvent()
                    else -> throwEvent()
                }
            }
        }
    }
}