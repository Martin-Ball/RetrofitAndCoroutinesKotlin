package com.martin.retrofitycorrutinas

import com.martin.retrofitycorrutinas.Model.DogsResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response<DogsResponse>  //se agrega suspend para realizar la consulta, sino da error

    //EJEMPLO DE POST
    @POST
    fun getEverything(@Body examplePost: ExamplePost): Call<*>

    //EJEMPLO MULTIPART PARA SUBIR IMAGENES O SUBIDAS DE ACHIVOS
    @Multipart
    @POST
    fun getEverithing2(@Part image:MultipartBody.Part): Call<*>
}

//CLASE DE EJEMPLO PARA POST, DEBERIA CREARSE EN OTRA CLASE
data class ExamplePost(val name:String, val age:Int)