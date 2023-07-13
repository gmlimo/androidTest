package org.bedu.roomvehicles.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val model: String?,
    @ColumnInfo val brand: String?,
    @ColumnInfo("plates_number") val platesNumber: String?,
    @ColumnInfo("is_working")val isWorking: Boolean
)