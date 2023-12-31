package com.demo.canyouhearme.common.helper

import java.io.IOException
import java.io.InputStream

object ResourcesReader {

    @Throws(IOException::class)
    fun readJsonFile(filename: String): String {
        val `is`: InputStream = this.javaClass.classLoader?.getResourceAsStream(filename) as InputStream
        val stringBuilder = StringBuilder()
        var i: Int
        val b = ByteArray(4096)
        while (`is`.read(b).also { i = it } != -1) {
            stringBuilder.append(String(b, 0, i))
        }
        return stringBuilder.toString()
    }
}