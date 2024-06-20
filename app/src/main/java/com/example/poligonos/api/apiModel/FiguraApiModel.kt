package com.example.poligonos.api.apiModel

import com.example.poligonos.domain.model.PointModel
import com.google.gson.annotations.SerializedName

data class FiguraApiModel(
    @SerializedName("name") var nombre: String,
    @SerializedName("points")var puntos: List<PointModel>
)
