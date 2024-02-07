package com.example.pruebatecnica.data.local.database.entity

import androidx.room.Entity

@Entity(tableName = "points")
data class PointEntity(
    val x: Double,
    val y: Double
)
