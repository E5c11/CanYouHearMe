package com.demo.canyouhearme.results.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "result_table")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val score: Int,
    val rounds: ArrayList<Round>,
    val date: String
)
