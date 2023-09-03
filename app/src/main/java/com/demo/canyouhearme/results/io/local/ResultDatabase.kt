package com.demo.canyouhearme.results.io.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Result::class], version = 1)
abstract class ResultDatabase: RoomDatabase() {
    abstract fun resultDao(): ResultDao

    companion object {
        const val DB_NAME = "result_db"
    }

}