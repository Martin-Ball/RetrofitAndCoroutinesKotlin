package com.martin.retrofitycorrutinas

import com.google.gson.annotations.SerializedName

data class DogsResponse (
    @SerializedName("status")var status:String,
    @SerializedName("message") var images:List<String>) {           //SerializedName se usa para cambiarle el nombre a las variables, llegan desde el API
                                                                    //con el nombre message pero el serializedName deja usar el nombre que quiera
}