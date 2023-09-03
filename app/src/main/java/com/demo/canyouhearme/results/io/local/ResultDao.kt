package com.demo.canyouhearme.results.io.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.canyouhearme.results.data.ResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: ResultEntity): Long

    @Delete
    suspend fun delete(result: ResultEntity): Int

    @Query("SELECT * FROM result_table")
    fun fetchAll(): Flow<List<ResultEntity>>

    @Query("SELECT * FROM result_table WHERE id = :id")
    suspend fun fetchById(id: Long): ResultEntity?
}