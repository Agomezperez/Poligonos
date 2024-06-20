package com.example.poligonos.data.local.database.repository

import com.example.poligonos.data.local.database.entity.FiguraEntity

interface FiguraRepo {

    suspend fun getAll(): List<FiguraEntity>


    suspend fun insetarFigura(figura: FiguraEntity): Long

    suspend fun updateFigura(figura: FiguraEntity)
}