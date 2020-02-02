package com.valter.maytheforcebewith_valterfrancisco.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.valter.maytheforcebewith_valterfrancisco.data.db.converter.StringListTypeConverter
import com.valter.maytheforcebewith_valterfrancisco.data.db.entity.Person

@Database(
        entities = [Person::class],
        version = 1
)
@TypeConverters(
        StringListTypeConverter::class
)
abstract class SwapiDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}