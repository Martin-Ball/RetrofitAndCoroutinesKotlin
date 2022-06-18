package com.martin.retrofitycorrutinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.martin.retrofitycorrutinas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {  //OnQueryTextListener es el listener del searchview, cuando se escribe devuelve el texto

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : DogAdapter
    private val dogImages = mutableListOf<String>()     //cada vez que cambie la raza van a cambiarse todos los valores de la lista, por eso mutable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.svDogs.setOnQueryTextListener(this)
        initRecyclerView()

    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
    }

    //esta funcion va a instanciar el objeto retrofit va a tener toda la url, el conversor json a modelo de datos y todas las configs
    private fun getRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breeds/")
            .addConverterFactory(GsonConverterFactory.create())     //gson es el conversor de json a la dataclass
            .build()
    }

    //creacion de la corrutina, toda la consulta se debe ejecutar en otro hilo que no sea el principal
    private fun searchByName(query:String){
        CoroutineScope(Dispatchers.IO).launch{
        //lo que se ejecute en esta seccion sera procesado en otro hilo
            val call:Response<DogsResponse> = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            val puppies:DogsResponse? = call.body()     //el signo de pregunta verifica si es null

            runOnUiThread{
                //lo que se ejecute aqui sera ejecutado en el hilo principal
                if(call.isSuccessful){
                    //mostrar recyclerView
                    val images:List<String> = puppies?.images ?: emptyList() //el simbolo ?: verifica si es nulo o no, en caso de que lo sea devuelve emptyList
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
        //puede ocurrir porque no hay internet, el servidor este caido, etc
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //cuando se toca en buscar
        if(!query.isNullOrEmpty()){
            searchByName(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}