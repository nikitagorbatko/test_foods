package com.nikitagorbatko.database_entities

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDishDao {
    @Query("SELECT * FROM cart_dishes")
    fun getCartDishes(): Flow<List<CartDishDbo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDish(dish: CartDishDbo)

    @Update
    suspend fun updateDish(dish: CartDishDbo)

    @Query("SELECT * FROM cart_dishes WHERE id LIKE :id")
    suspend fun getDish(id: Int): CartDishDbo?

    @Query("DELETE FROM cart_dishes WHERE id LIKE :id")
    suspend fun deleteCartDish(id: Int): Int

    @Update
    suspend fun updateCartDish(dish: CartDishDbo)
}
