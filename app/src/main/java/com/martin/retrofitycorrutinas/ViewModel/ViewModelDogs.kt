package com.martin.retrofitycorrutinas.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.martin.retrofitycorrutinas.Model.DogsResponse
import com.martin.retrofitycorrutinas.Model.NetworkConnections
import retrofit2.Response

class ViewModelDogs : ViewModel() {

    val dogResponse = MutableLiveData<Response<DogsResponse>>()

    suspend fun dogImages(query:String){
        val response = NetworkConnections().getRetrofit(query)
        dogResponse.postValue(response)
    }
}