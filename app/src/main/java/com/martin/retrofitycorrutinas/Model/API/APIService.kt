package com.martin.retrofitycorrutinas

import com.martin.retrofitycorrutinas.Model.API.DogsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse>  //se agrega suspend para realizar la consulta, sino da error

    @POST("/query.json")
    suspend fun postRaceSelectedOnFirebase(@Body query:String)
}