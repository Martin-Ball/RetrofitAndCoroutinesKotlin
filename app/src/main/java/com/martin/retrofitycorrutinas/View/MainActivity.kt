package com.martin.retrofitycorrutinas.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.martin.retrofitycorrutinas.ViewModel.ViewModelDogs
import com.martin.retrofitycorrutinas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {  //OnQueryTextListener es el listener del searchview, cuando se escribe devuelve el texto

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : DogAdapter

    private val viewModelDogs:ViewModelDogs by viewModels()

    private val dogImages = mutableListOf<String>()     //cada vez que cambie la raza van a cambiarse todos los valores de la lista, por eso mutable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.svDogs.setOnQueryTextListener(this)
        initRecyclerView()

        viewModelDogs.dogResponse.observe(this, Observer {

            CoroutineScope(Dispatchers.IO).launch {
                //lo que se ejecute en esta seccion sera procesado en otro hilo
                val puppies = it.body()    //el signo de pregunta verifica si es null

                runOnUiThread {
                    //lo que se ejecute aqui sera ejecutado en el hilo principal
                    if (it.isSuccessful) {
                        //mostrar recyclerView
                        val images = puppies?.images
                            ?: emptyList() //el simbolo ?: verifica si es nulo o no, en caso de que lo sea devuelve emptyList
                        dogImages.clear()
                        dogImages.addAll(images)
                        adapter.notifyDataSetChanged()
                    } else {
                        showError()
                    }
                    hideKeyboard()
                }
            }
        })

    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = adapter
    }

    //creacion de la corrutina, toda la consulta se debe ejecutar en otro hilo que no sea el principal
    private suspend fun searchByName(query:String) {
        viewModelDogs.dogImages(query)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
        //puede ocurrir porque no hay internet, el servidor este caido, etc
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //cuando se toca en buscar
        if(!query.isNullOrEmpty()){
            CoroutineScope(Dispatchers.IO).launch { searchByName(query.lowercase()) }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}