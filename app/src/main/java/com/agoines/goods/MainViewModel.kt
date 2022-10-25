package com.agoines.goods

import android.content.Context
import androidx.lifecycle.ViewModel
import com.agoines.goods.data.Screen
import com.agoines.goods.data.TOKEN
import com.agoines.goods.data.USER_URL
import com.agoines.goods.data.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel : ViewModel() {

    fun getStartDestination(context: Context): Flow<String> {
        return context.dataStore.data.map { preferences ->
            if (preferences[USER_URL] == null)
                Screen.Index.route
             else
                if (preferences[TOKEN] == null) Screen.Login.route else Screen.Home.route
        }
    }
}