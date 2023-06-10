package com.nikitagorbatko.cart

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.nikitagorbatko.cart.databinding.ItemProductBinding
import com.nikitagorbatko.database.DishDbo
import com.nikitagorbatko.entity.Dish

fun cartDishAdapterDelegate(onItemClick: (Dish) -> Unit) =
    adapterDelegateViewBinding<DishDbo, Dish, ItemProductBinding>({ layoutInflater, parent ->
        ItemProductBinding.inflate(layoutInflater, parent, false)
    }) {
        binding.root.setOnClickListener {
            onItemClick(item)
        }
        bind {
            Glide
                .with(binding.root)
                .load(item.imageUrl ?: item.description)
                .into(binding.imageProduct)
            binding.textDishName.text = item.name
        }
    }