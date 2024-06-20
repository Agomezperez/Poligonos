package com.example.poligonos.ui.main.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.poligonos.data.local.database.entity.FiguraEntity

interface MainViewModel {

    val showFiguras: MutableLiveData<List<FiguraEntity>>

    fun insertarPoligono(figuraEntity: FiguraEntity, callBack: () -> Unit)

    fun init()
}