package com.example.pruebatecnica.ui.main.viewModel

import com.example.pruebatecnica.domain.util.UiEvent
import com.example.pruebatecnica.data.local.database.entity.FiguraEntity
import kotlinx.coroutines.flow.SharedFlow

interface MainPresenter {
    val uiEvent: SharedFlow<UiEvent>

    fun mostrarFiguras(figuras: List<FiguraEntity>)
}