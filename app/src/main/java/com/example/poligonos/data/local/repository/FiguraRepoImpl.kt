package com.example.poligonos.data.local.repository

import com.example.poligonos.data.local.database.dao.FiguraDao
import com.example.poligonos.data.local.database.entity.FiguraEntity
import com.example.poligonos.data.local.database.repository.FiguraRepo

class FiguraRepoImpl(
    private val figuraDao: FiguraDao,
) : FiguraRepo {


    override suspend fun getAll(): List<FiguraEntity> = figuraDao.getAll()

    override suspend fun insetarFigura(figura: FiguraEntity): Long = figuraDao.insert(figura)

    override suspend fun updateFigura(figura: FiguraEntity) = figuraDao.update(figura)

}
