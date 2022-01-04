package com.b2i.faf.utils.helper

object StringHelper {

    fun clean(data: String) = data.replace("\'", "\\\'")
}