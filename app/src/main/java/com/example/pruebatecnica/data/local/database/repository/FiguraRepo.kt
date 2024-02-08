package com.example.pruebatecnica.data.local.database.repository

import com.example.pruebatecnica.data.local.database.entity.FiguraEntity

interface FiguraRepo {

    suspend fun getAll(): List<FiguraEntity>

    suspend fun insertarFigurasDesdeJson()

    suspend fun insetarFigura(figura: FiguraEntity)

    suspend fun updateFigura(figura: FiguraEntity)
}