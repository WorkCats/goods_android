package com.agoines.goods.ui.vm

import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.agoines.goods.api.getGoodById
import com.agoines.goods.data.Good
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val toast: Toast,
    private val customTabsIntent: CustomTabsIntent
) : ViewModel() {
    fun decodeResult(
        navController: NavHostController,
        goodId: String,
        updateGoodEvent: suspend (Good) -> Unit,
        editGoodEvent: suspend () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.getGoodById(goodId = goodId).collect { goodResult ->
                when (goodResult.errCode) {
                    0 -> {
                        withContext(Dispatchers.Main) {
                            updateGoodEvent(goodResult.good!!)
                        }
                    }

                    else -> {
                        withContext(Dispatchers.Main) {
                            editGoodEvent()
                        }
                    }
                }
            }
        }
    }

    fun gotoURL(navController: NavHostController, url: String) {
        customTabsIntent.launchUrl(navController.context, Uri.parse(url))
    }
}