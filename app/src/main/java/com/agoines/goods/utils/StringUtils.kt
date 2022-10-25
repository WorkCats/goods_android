package com.agoines.goods.utils

import java.util.regex.Pattern

fun String.isHttp(): Boolean {
    val pattern = Pattern
        .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~/])+$")
    return pattern.matcher(this).matches()
}