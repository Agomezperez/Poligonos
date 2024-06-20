package com.example.poligonos.api.services

import com.example.poligonos.api.apiModel.FiguraApiModel
import com.example.poligonos.api.util.DataArgs
import retrofit2.http.GET


interface SyncRetrofitService {
    @GET(DataArgs.Network.API)
    suspend fun getSync(): List<FiguraApiModel>

}