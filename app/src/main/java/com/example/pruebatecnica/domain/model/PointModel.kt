package com.example.pruebatecnica.domain.model

import com.google.gson.annotations.SerializedName

data class PointModel(
    @SerializedName("x") var x: Double,
    @SerializedName("y") var y: Double
)
