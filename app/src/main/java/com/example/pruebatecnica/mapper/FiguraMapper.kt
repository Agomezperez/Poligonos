package com.example.pruebatecnica.mapper

import com.example.pruebatecnica.api.apiModel.FiguraApiModel
import com.example.pruebatecnica.data.local.database.entity.FiguraEntity
import com.example.pruebatecnica.domain.model.PointModel

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

fun FiguraEntity.toModel(): FiguraApiModel {
    return FiguraApiModel(
        nombre = nombre,
        puntos = puntos.map { punto ->
            PointModel(
                x = punto.x,
                y = punto.y
            )
        }
    )
}


