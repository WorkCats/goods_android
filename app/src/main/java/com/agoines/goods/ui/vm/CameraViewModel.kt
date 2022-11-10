package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.agoines.goods.api.hasGood
import com.agoines.goods.data.Screen
import com.agoines.goods.di.showShortText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val toast: Toast
) : ViewModel() {
    fun decodeResult(navController: NavHostController, goodId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.hasGood(goodId = goodId).collect { hasGoodResult ->
                when (hasGoodResult.errCode) {
                    0 -> {
                        withContext(Dispatchers.Main) {
                            if (hasGoodResult.errMsg.toBoolean()) {
                                navController.navigate(Screen.UpdateDialog.route + "/$goodId")
                            } else {
                                navController.navigate(Screen.AddDialog.route + "/$goodId")
                            }
                        }
                    }

                    else -> toast.showShortText(hasGoodResult.errMsg)
                }
            }
        }
    }
}