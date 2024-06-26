package com.example.poligonos.domain.use_case

import com.example.poligonos.data.local.database.repository.FiguraRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllFigureUseC @Inject constructor(
    private val figuraRepo: FiguraRepo
) {

    suspend operator fun invoke() = figuraRepo.getAll()
}