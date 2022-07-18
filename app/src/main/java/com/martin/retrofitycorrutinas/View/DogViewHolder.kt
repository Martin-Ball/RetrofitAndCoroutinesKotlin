package com.martin.retrofitycorrutinas.View

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.martin.retrofitycorrutinas.databinding.ItemDogBinding
import com.squareup.picasso.Picasso

class DogViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemDogBinding.bind(view)

    //picasso convierte una URL en una imagen
    fun bind(image:String){
        Picasso.get().load(image).into(binding.ivDog)
    }
}