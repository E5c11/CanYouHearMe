package com.demo.canyouhearme.results.helper

import androidx.room.TypeConverter
import com.demo.canyouhearme.results.data.Round
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun roundToString(value: List<Round>) = gson.toJson(value)

    @TypeConverter
    fun stringToRounds(value: String): List<Round> = gson.fromJson(value, object : TypeToken<List<Round>>() {}.type)
}