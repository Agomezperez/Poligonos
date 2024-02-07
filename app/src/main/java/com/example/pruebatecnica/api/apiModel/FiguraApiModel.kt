package com.example.pruebatecnica.api.apiModel

import com.example.pruebatecnica.domain.model.PointModel
import com.google.gson.annotations.SerializedName

data class FiguraApiModel(
    @SerializedName("name") var nombre: String,
    @SerializedName("points")var puntos: List<PointModel>
)
