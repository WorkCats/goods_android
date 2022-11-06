package com.agoines.goods.api.converter

import com.drake.net.request.BodyRequest
import com.drake.net.request.MediaConst
import kotlinx.serialization.json.Json.Default.encodeToString
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.serializer
import okhttp3.RequestBody.Companion.toRequestBody


inline fun <reified T> BodyRequest.serializationJson(value: T) {
    val json = encodeToString(serializersModule.serializer(), value)
    this.body = json.toRequestBody(
        MediaConst.JSON
    )
}