package com.demo.canyouhearme.results.io.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.canyouhearme.results.data.ResultEntity
import com.demo.canyouhearme.results.helper.TypeConverter


@Database(entities = [ResultEntity::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class ResultDatabase: RoomDatabase() {
    abstract fun resultDao(): ResultDao

    companion object {
        const val DB_NAME = "result_db"
    }

}