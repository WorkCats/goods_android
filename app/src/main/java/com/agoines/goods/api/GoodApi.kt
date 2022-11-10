package com.agoines.goods.api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.agoines.goods.api.bean.GoodIdBean
import com.agoines.goods.api.bean.GoodBean
import com.agoines.goods.api.bean.GoodNameBean
import com.agoines.goods.api.result.GoodListResult
import com.agoines.goods.api.result.TextResult
import com.agoines.goods.api.converter.SerializationConverter
import com.agoines.goods.api.converter.serializationJson
import com.agoines.goods.data.Good
import com.agoines.goods.data.getUrlAndToken
import com.drake.net.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


context(CoroutineScope)
        suspend fun DataStore<Preferences>.getGoodList(): Flow<GoodListResult> {
    return flow {
        getUrlAndToken().collect { list ->
            emit(
                Post<GoodListResult>(list[0] + "good/getGoodList") {
                    setHeader("authorization", list[1])
                    converter = SerializationConverter()
                }.await()
            )
        }
    }

}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.delGood(goodId: String): Flow<TextResult> {
    return flow {
        getUrlAndToken().collect { list ->
            emit(
                Post<TextResult>(list[0] + "good/delGood") {
                    setHeader("authorization", list[1])
                    converter = SerializationConverter()
                    serializationJson(
                        GoodIdBean(goodId)
                    )
                }.await()
            )
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.searchGood(goodName: String): Flow<GoodListResult> {
    return flow {
        getUrlAndToken().collect { list ->
            emit(
                Post<GoodListResult>(list[0] + "good/searchGood") {
                    setHeader("authorization", list[1])
                    converter = SerializationConverter()
                    serializationJson(
                        GoodNameBean(goodName)
                    )
                }.await()
            )
        }
    }
}


context(CoroutineScope)
        suspend fun DataStore<Preferences>.addGood(good: Good): Flow<TextResult> {
    return flow {
        getUrlAndToken().collect { list ->
            emit(
                Post<TextResult>(list[0] + "good/addGood") {
                    setHeader("authorization", list[1])
                    converter = SerializationConverter()
                    serializationJson(
                        GoodBean(
                            id = good.id,
                            name = good.name,
                            size = good.size,
                            userName = good.userName
                        )
                    )
                }.await()
            )
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.updateGood(good: Good): Flow<TextResult> {
    return flow {
        getUrlAndToken().collect { list ->
            emit(
                Post<TextResult>(list[0] + "good/updateGood") {
                    setHeader("authorization", list[1])
                    converter = SerializationConverter()
                    serializationJson(
                        GoodBean(
                            id = good.id,
                            name = good.name,
                            size = good.size,
                            userName = good.userName
                        )
                    )
                }.await()
            )
        }
    }
}

context(CoroutineScope)
        suspend fun DataStore<Preferences>.hasGood(goodId: String): Flow<TextResult> {
    return flow {
        getUrlAndToken().collect { list ->
            emit(
                Post<TextResult>(list[0] + "good/hasGood") {
                    setHeader("authorization", list[1])
                    converter = SerializationConverter()
                    serializationJson(
                        GoodIdBean(goodId)
                    )
                }.await()
            )
        }
    }
}