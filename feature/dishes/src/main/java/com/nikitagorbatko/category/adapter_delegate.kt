package com.nikitagorbatko.category

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.nikitagorbatko.category.databinding.ItemDishBinding
import com.nikitagorbatko.network.Dish
import com.nikitagorbatko.network.DishDto

fun dishAdapterDelegate(onItemClick: (Dish) -> Unit) =
    adapterDelegateViewBinding<DishDto, Dish, ItemDishBinding>({ layoutInflater, parent ->
        ItemDishBinding.inflate(layoutInflater, parent, false)
    }) {
        binding.root.setOnClickListener {
            onItemClick(item)
        }
        bind {
            Glide
                .with(binding.root)
                .load(item.imageUrl)
                .into(binding.imageDish)
            binding.textDishName.text = item.name
        }
    }