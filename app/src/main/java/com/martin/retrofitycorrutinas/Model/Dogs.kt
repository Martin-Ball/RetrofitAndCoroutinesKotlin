package com.martin.retrofitycorrutinas.Model

import com.martin.retrofitycorrutinas.Model.API.DogsResponse
import com.martin.retrofitycorrutinas.Model.API.NetworkConnections
import retrofit2.Response

class Dogs {

    public suspend fun getDogsbyRace(query:String): Response<DogsResponse> {
        return NetworkConnections().getRetrofit(query)
    }
}