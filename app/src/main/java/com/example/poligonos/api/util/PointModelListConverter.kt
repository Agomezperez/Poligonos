package com.example.poligonos.api.util

import androidx.room.TypeConverter
import com.example.poligonos.domain.model.PointModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PointModelListConverter {
    @TypeConverter
    fun fromJson(json: String): List<PointModel> {
        val type = object : TypeToken<List<PointModel>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(list: List<PointModel>): String {
        return Gson().toJson(list)
    }
}
