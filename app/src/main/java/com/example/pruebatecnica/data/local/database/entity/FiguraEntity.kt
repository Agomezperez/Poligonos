package com.example.pruebatecnica.data.local.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pruebatecnica.api.util.PointModelListConverter
import com.example.pruebatecnica.domain.model.PointModel
import kotlinx.parcelize.Parcelize

@Parcelize
@TypeConverters(PointModelListConverter::class)
@Entity(tableName = "figuras")
data class FiguraEntity(
    @PrimaryKey(autoGenerate = true) var uid: Long?,
    @ColumnInfo(name = "nombre" )var nombre: String,
    var puntos: List<PointModel>
): Parcelable