package com.valter.maytheforcebewith_valterfrancisco.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(people: List<Person>)

    @Query("SELECT * FROM person")
    suspend fun getAllPersons() : List<Person>
}