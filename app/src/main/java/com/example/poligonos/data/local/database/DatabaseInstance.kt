package com.example.poligonos.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.poligonos.api.util.Convert
import com.example.poligonos.data.local.database.dao.FiguraDao
import com.example.poligonos.data.local.database.entity.FiguraEntity

@Database(entities = [FiguraEntity::class], version = 1)

@TypeConverters(Convert::class)
abstract class DatabaseInstance : RoomDatabase() {

    abstract fun figuraDao(): FiguraDao

    companion object {
        const val DATABASE_NAME = "figuras_db"

        @Volatile
        private var INSTANCE: DatabaseInstance? = null

        fun getInstance(context: Context): DatabaseInstance =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }

        private fun build(context: Context): DatabaseInstance =
            Room.databaseBuilder(
                context.applicationContext,
                DatabaseInstance::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .setJournalMode(JournalMode.TRUNCATE)
                .build()
    }
}