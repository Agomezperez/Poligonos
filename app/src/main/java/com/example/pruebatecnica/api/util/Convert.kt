package com.example.pruebatecnica.api.util

import androidx.room.TypeConverter
import com.example.pruebatecnica.data.local.database.entity.PointEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Convert {
    @TypeConverter
    @JvmStatic
    fun fromJson(value: String): List<PointEntity> {
        val listType = object : TypeToken<List<PointEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun toJson(list: List<PointEntity>): String {
        return Gson().toJson(list)
    }
}
