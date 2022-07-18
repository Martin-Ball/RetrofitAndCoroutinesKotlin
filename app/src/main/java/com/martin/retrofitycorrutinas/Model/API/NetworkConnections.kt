package com.martin.retrofitycorrutinas.Model.API

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.martin.retrofitycorrutinas.APIService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext


class NetworkConnections {

    suspend fun getRetrofit(query:String): Response<DogsResponse>? {
        postFirebase(query)

        val response =  Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
            .getDogsByBreeds("$query/images")

        if(response.isSuccessful) {
            return response
        }else{
            return null
        }
    }

    suspend fun postFirebase(query: String) {
        if(!query.isNullOrEmpty()) {
           Retrofit.Builder()
                .baseUrl("https://apitest-d70b6-default-rtdb.firebaseio.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
                .postRaceSelectedOnFirebase(query)
        }else{
            Log.d("ERROR:", "No especifica raza")
        }
    }
}