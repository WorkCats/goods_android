package com.agoines.goods.converter

import com.drake.net.convert.JSONConvert
import com.squareup.moshi.Moshi
import org.json.JSONObject
import java.lang.reflect.Type

class MoshiConverter : JSONConvert(code = "errorCode", message = "errorMsg", success = "0") {

    companion object {
        val moshi: Moshi = Moshi.Builder().build()
    }

    override fun <R> String.parseBody(succeed: Type): R? {
        return try {
            moshi.adapter<R>(succeed).fromJson(JSONObject(this).getString("data"))
        } catch (e: Exception) {
            moshi.adapter<R>(succeed).fromJson(this)
        }
    }
}