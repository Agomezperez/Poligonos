package com.example.pruebatecnica.data.local.database.repository

interface FiguraRepo {

   // suspend fun getAll(): List<FiguraModel>

    suspend fun insertarFigurasDesdeJson()

  //  suspend fun insert(figuras: List<FiguraModel>)
}