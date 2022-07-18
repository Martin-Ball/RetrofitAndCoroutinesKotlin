package com.martin.retrofitycorrutinas.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.martin.retrofitycorrutinas.Model.API.DogsResponse
import com.martin.retrofitycorrutinas.Model.Dogs

import retrofit2.Response

class ViewModelDogs : ViewModel() {

    val dogResponse = MutableLiveData<Response<DogsResponse>>()

    suspend fun dogImages(query:String){
        val response:Response<DogsResponse> = Dogs().getDogsbyRace(query)

        dogResponse.postValue(response)
    }
}