package com.nikitagorbatko.cart

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.nikitagorbatko.cart.databinding.ItemProductBinding
import com.nikitagorbatko.database_entities.CartDishDbo
import com.nikitagorbatko.entity.Dish

fun cartDishAdapterDelegate(
    onPlusClick: (CartDishDbo) -> Unit,
    onMinusClick: (CartDishDbo) -> Unit
) =
    adapterDelegateViewBinding<CartDishDbo, Dish, ItemProductBinding>({ layoutInflater, parent ->
        ItemProductBinding.inflate(layoutInflater, parent, false)
    }) {
        bind {
            val price = "${item.price}₽"
            val weight = " · ${item.weight}г"
            binding.textDishName.text = item.name
            binding.textPrice.text = price
            binding.textGrams.text = weight
            binding.counter.counter = item.amount
            binding.counter.setOnMinusClickListener {
                onMinusClick.invoke(item)
            }
            binding.counter.setOnPlusClickListener {
                onPlusClick.invoke(item)
            }
            Glide
                .with(binding.root)
                .load(item.imageUrl ?: item.description)
                .into(binding.imageProduct)
            binding.textDishName.text = item.name
        }
    }