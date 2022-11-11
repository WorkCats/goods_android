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
import com.agoines.goods.data.Screen
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
    fun decodeResult(navController: NavHostController, goodId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.getGoodById(goodId = goodId).collect { goodResult ->
                when (goodResult.errCode) {
                    0 -> {
                        val good = goodResult.good!!
                        withContext(Dispatchers.Main) {
                            navController.navigate(
                                route = Screen.UpdateDialog.route
                                        + "/goodId=${good.id}/goodName=${good.name}/userName=${good.userName}/goodSize=${good.size}"
                            )

                        }
                    }

                    else -> {
                        withContext(Dispatchers.Main) {
                            navController.navigate(
                                route = Screen.AddDialog.route + "/goodId=${goodId}"
                            )
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