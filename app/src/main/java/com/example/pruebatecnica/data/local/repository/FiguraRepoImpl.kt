package com.example.pruebatecnica.data.local.repository

import com.example.pruebatecnica.api.network.WebServiceHelper
import com.example.pruebatecnica.data.local.database.dao.FiguraDao
import com.example.pruebatecnica.data.local.database.entity.FiguraEntity
import com.example.pruebatecnica.data.local.database.repository.FiguraRepo
import com.example.pruebatecnica.mapper.toEntity

class FiguraRepoImpl(
    private val figuraDao: FiguraDao,
) : FiguraRepo {

    override suspend fun insertarFigurasDesdeJson() {
        val figuras = WebServiceHelper.obtenerFigurasDesdeJson()
        val figurasEntity = figuras.map { it.toEntity() }
        figuraDao.insert(figurasEntity)
    }

    override suspend fun getAll(): List<FiguraEntity> = figuraDao.getAll()

    override suspend fun insetarFigura(figura: FiguraEntity) = figuraDao.insert(figura)

    override suspend fun updateFigura(figura: FiguraEntity) = figuraDao.update(figura)

}
