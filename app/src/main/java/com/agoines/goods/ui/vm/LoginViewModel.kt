package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.agoines.goods.api.login
import com.agoines.goods.data.Screen
import com.agoines.goods.data.setToken
import com.agoines.goods.di.showShortText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val toast: Toast
) : ViewModel() {
    fun login(
        navController: NavHostController,
        username: String,
        password: String,
        autoLogin: Boolean
    ) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch(IO) {
                dataStore.login(username, password, autoLogin).collect { loginResult ->
                    when (loginResult.errCode) {
                        0 -> {
                            dataStore.setToken(loginResult.token)
                            this.launch(Main) {
                                navController.navigate(Screen.Home.route)
                            }
                        }

                        else -> toast.showShortText(loginResult.errMsg)
                    }
                }
            }
        } else {
            toast.showShortText("你信息没填全")
        }
    }
}