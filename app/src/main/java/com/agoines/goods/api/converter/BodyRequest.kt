package com.agoines.goods.api.converter

import com.agoines.goods.api.converter.MoshiConverter.Companion.moshi
import com.drake.net.request.BodyRequest
import com.drake.net.request.MediaConst
import okhttp3.RequestBody.Companion.toRequestBody

fun <T> BodyRequest.moshiJson(type: Class<T>, value: T) {
    val json = moshi.adapter(type).toJson(value)
    this.body = json.toRequestBody(
        MediaConst.JSON
    )
}