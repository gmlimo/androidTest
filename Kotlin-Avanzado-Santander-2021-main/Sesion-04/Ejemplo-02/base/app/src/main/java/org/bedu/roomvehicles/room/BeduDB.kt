package org.bedu.roomvehicles.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vehicle::class], version = 1)
abstract class BeduDB: RoomDatabase() {

    abstract fun vehicleDao(): VehicleDao

    companion object {
        @Volatile
        private var beduInstance: BeduDB? = null

        const val DB_NAME = "Bedu_DB"

        fun getInstance(context: Context) : BeduDB {

            return beduInstance?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BeduDB::class.java,
                    DB_NAME
                ).build()
                beduInstance = instance
                // return instance
                instance
            }
        }
    }
}