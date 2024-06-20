package com.example.poligonos.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.poligonos.data.local.database.entity.FiguraEntity

@Dao
interface FiguraDao {
    @Query("SELECT * FROM figuras")
    suspend fun getAll(): List<FiguraEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(figura: FiguraEntity): Long

    @Update
    suspend fun update(figura: FiguraEntity)

}