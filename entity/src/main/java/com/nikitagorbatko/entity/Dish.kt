package com.nikitagorbatko.entity

interface Dish {
    val id: Int
    val name: String?
    val price: Int?
    val weight: Int?
    val description: String?
    val imageUrl: String?
    val tegs: List<String>
}