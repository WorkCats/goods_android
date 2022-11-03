package com.agoines.goods.ui.vm

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.agoines.goods.bean.LoginUserBean
import com.agoines.goods.bean.result.LoginResult
import com.agoines.goods.converter.MoshiConverter
import com.agoines.goods.converter.moshiJson
import com.agoines.goods.data.Screen
import com.agoines.goods.data.TOKEN
import com.agoines.goods.data.USER_URL
import com.agoines.goods.di.showShortText
import com.drake.net.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.map
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
                dataStore.data.map { preferences ->
                    preferences[USER_URL]!!
                }.collect{
                    val loginResult = Post<LoginResult>( it + "user/login") {
                        converter = MoshiConverter()
                        moshiJson(
                            LoginUserBean::class.java,
                            LoginUserBean(autoLogin, username, password)
                        )
                    }.await()
                    when (loginResult.errCode) {
                        0 -> {
                            dataStore.edit { settings ->
                                settings[TOKEN] = loginResult.token
                            }
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