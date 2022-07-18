package com.martin.retrofitycorrutinas.Model

import com.martin.retrofitycorrutinas.APIService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkConnections {

    suspend fun getRetrofit(query:String): Response<DogsResponse> {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())     //gson es el conversor de json a la dataclass
            .build()
            .create(APIService::class.java)
            .getDogsByBreeds("$query/images")
    }
}