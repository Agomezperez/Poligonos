package com.example.pruebatecnica.data.local.repository

import com.example.pruebatecnica.api.network.WebServiceHelper
import com.example.pruebatecnica.data.local.database.dao.FiguraDao
import com.example.pruebatecnica.data.local.database.repository.FiguraRepo
import com.example.pruebatecnica.mapper.toEntity
import timber.log.Timber

class FiguraRepoImpl(
    private val figuraDao: FiguraDao,
) : FiguraRepo {

    override suspend fun insertarFigurasDesdeJson() {
        val figuras = WebServiceHelper.obtenerFigurasDesdeJson()
        val figurasEntity = figuras.map { it.toEntity() }
        figuraDao.insert(figurasEntity)
        Timber.d("Figuras insertadas en la base de datos")
    }
}
