package com.example.pruebatecnica.api.services

import com.example.pruebatecnica.api.apiModel.FiguraApiModel
import com.example.pruebatecnica.api.util.DataArgs
import retrofit2.http.GET


interface SyncRetrofitService {
    @GET(DataArgs.Network.API)
    suspend fun getSync(): List<FiguraApiModel>

}