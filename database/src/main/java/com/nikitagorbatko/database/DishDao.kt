package com.nikitagorbatko.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nikitagorbatko.database.DishDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {
    @Insert
    suspend fun insertDish(dishDbo: DishDbo)

//    @Query("UPDATE word_table SET amount = amount + 1 WHERE word = :word")
//    suspend fun updateWord(word: String)

    @Update
    suspend fun updateDish(dishDbo: DishDbo)

    @Query("DELETE FROM cart_dishes_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM cart_dishes_table")
    suspend fun getAllDishes(): List<DishDbo>
}