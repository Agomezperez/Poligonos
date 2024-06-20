package com.example.poligonos.mapper

import com.example.poligonos.api.apiModel.FiguraApiModel
import com.example.poligonos.data.local.database.entity.FiguraEntity
import com.example.poligonos.domain.model.PointModel

fun FiguraApiModel.toEntity(): FiguraEntity {
    return FiguraEntity(
        uid = null,
        nombre = nombre,
        puntos = puntos.map { punto ->
            PointModel(
                x = punto.x,
                y = punto.y
            )
        }
    )
}



