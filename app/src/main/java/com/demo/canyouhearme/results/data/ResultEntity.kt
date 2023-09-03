package com.demo.canyouhearme.results.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "result_table")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val score: Int,
    val rounds: List<Round>,
    val date: String
)
