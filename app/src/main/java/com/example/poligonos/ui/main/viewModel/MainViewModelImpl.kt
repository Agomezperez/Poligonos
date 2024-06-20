package com.example.poligonos.ui.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.poligonos.data.local.database.entity.FiguraEntity
import com.example.poligonos.data.local.database.repository.FiguraRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val figuraRepo: FiguraRepo
): ViewModel(), MainViewModel {

    override val showFiguras: MutableLiveData<List<FiguraEntity>> = MutableLiveData()

    override fun init() {
        viewModelScope.launch {
            figuraRepo.getAll().also {
                showFiguras.postValue(it)
            }
        }
    }

    override fun insertarPoligono(figuraEntity: FiguraEntity, callBack: () -> Unit) {
        viewModelScope.launch {
            figuraRepo.insetarFigura(figuraEntity)
            callBack()
        }
    }

}