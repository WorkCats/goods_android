package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.agoines.goods.data.Screen
import com.agoines.goods.data.setUrl
import com.agoines.goods.di.showShortText
import com.agoines.goods.utils.isHttp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val toast: Toast
) : ViewModel() {

    fun setUserURL(navHostController: NavHostController, url: String) {
        if (url.isHttp()) {
            this.viewModelScope.launch(IO) {
                dataStore.setUrl(url)
                this.launch(Main) {
                    navHostController.navigate(Screen.Login.route)
                }
            }
        } else {
            toast.showShortText("你填写的链接有问题")
        }
    }
}