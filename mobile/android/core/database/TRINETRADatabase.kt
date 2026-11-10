package com.trinetra.mobile.android.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.trinetra.mobile.android.core.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class TRINETRADatabase : RoomDatabase() {

    abstract fun userDao(): com.trinetra.mobile.android.core.database.dao.UserDao

    companion object {
        @Volatile
        private var INSTANCE: TRINETRADatabase? = null

        fun getInstance(context: Context): TRINETRADatabase {
            // Use synchronized to prevent multiple instances
            synchronized(this) {
                INSTANCE?.let { return it }

                val.Builder builder = Room.databaseBuilder(
                    applicationContext = context.applicationContext,
                    class = TRINETRADatabase::class.java,
                    databaseName = "trinetra_database"
                )

                // Enable SQLite WAL mode for better concurrency
                builder.setJournalMode(JournalMode.WAL.name)

                INSTANCE = builder.build()
                return INSTANCE!!
            }
        }
    }
}