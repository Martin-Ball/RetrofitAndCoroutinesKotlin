package com.martin.retrofitycorrutinas.Model.API

import com.martin.retrofitycorrutinas.APIService
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class NetworkConnections {

    suspend fun getRetrofit(query:String): Response<DogsResponse> {
        postFirebase(query)

        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())     //gson es el conversor de json a la dataclass
            .build()
            .create(APIService::class.java)
            .getDogsByBreeds("$query/images")
    }

    suspend fun postFirebase(query: String) {
        return Retrofit.Builder()
            .baseUrl("https://apitest-d70b6-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())     //gson es el conversor de json a la dataclass
            .build()
            .create(APIService::class.java)
            .postRaceSelectedOnFirebase(query)
    }
}