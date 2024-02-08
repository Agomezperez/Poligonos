package com.example.pruebatecnica.ui.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnica.domain.util.UiEvent
import com.example.pruebatecnica.data.local.database.entity.FiguraEntity
import com.example.pruebatecnica.domain.use_case.GetAllFigureUseC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPresenterImpl @Inject constructor(
    private val getAllFigureUseC: GetAllFigureUseC
): ViewModel(), MainPresenter {

    private val _uiEventFlow: MutableSharedFlow<UiEvent> by lazy { MutableSharedFlow() }
    override val uiEvent: SharedFlow<UiEvent> = _uiEventFlow.asSharedFlow()

    sealed class MainEvent: UiEvent {
        data class ShowElements(val list: List<FiguraEntity>) : MainEvent()
    }

    override fun mostrarFiguras(figuras: List<FiguraEntity>) {
        viewModelScope.launch {
            val figuras = getAllFigureUseC()
            _uiEventFlow.emit(MainEvent.ShowElements(figuras))
        }
    }
}