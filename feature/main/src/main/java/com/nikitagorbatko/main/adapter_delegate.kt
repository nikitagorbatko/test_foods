package com.nikitagorbatko.main

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.nikitagorbatko.main.databinding.ItemCategoryBinding
import com.nikitagorbatko.network.Category
import com.nikitagorbatko.network.CategoryDto

fun categoryAdapterDelegate(onItemClick: (Category) -> Unit) = adapterDelegateViewBinding<CategoryDto, Category, ItemCategoryBinding>({
    layoutInflater, parent ->  ItemCategoryBinding.inflate(layoutInflater, parent, false)
}) {
    binding.root.setOnClickListener {
        onItemClick(item)
    }
    bind {
        Glide
            .with(binding.root)
            .load(item.imageUrl)
            .into(binding.imageCategory)
        binding.textCategory.text = item.name
    }
}