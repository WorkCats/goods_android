package com.agoines.goods.api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.agoines.goods.api.bean.DelGoodBean
import com.agoines.goods.api.bean.result.GetGoodResult
import com.agoines.goods.api.bean.result.TextResult
import com.agoines.goods.api.converter.MoshiConverter
import com.agoines.goods.api.converter.moshiJson
import com.agoines.goods.data.getUrlAndToken
import com.drake.net.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


context(CoroutineScope)
        suspend fun DataStore<Preferences>.getGoodList(): Flow<GetGoodResult> {
    return flow {
        getUrlAndToken().collect { list ->
            this.emit(
                Post<GetGoodResult>(list[0] + "good/getGoodList") {
                    setHeader("authorization", list[1])
                    converter = MoshiConverter()
                }.await()
            )
        }
    }

}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.delGood(goodId: String): Flow<TextResult> {
    return flow {
        getUrlAndToken().collect { list ->
            this.emit(
                Post<TextResult>(list[0] + "good/delGood") {
                    setHeader("authorization", list[1])
                    converter = MoshiConverter()
                    moshiJson(
                        DelGoodBean::class.java,
                        DelGoodBean(goodId)
                    )
                }.await()
            )
        }
    }
}