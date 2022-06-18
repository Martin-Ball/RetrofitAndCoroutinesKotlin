package com.martin.retrofitycorrutinas

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse>  //se agrega suspend para realizar la consulta, sino da error
}