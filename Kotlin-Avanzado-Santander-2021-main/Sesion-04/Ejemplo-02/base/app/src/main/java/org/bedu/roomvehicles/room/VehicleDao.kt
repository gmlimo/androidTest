package org.bedu.roomvehicles.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface VehicleDao {

    @Insert
    fun insertVehicle(vehicle: Vehicle)

    @Update
    fun updateVehicle(vehicle: Vehicle)

    @Delete
    fun removeVehicel(vehicle: Vehicle)

    @Query("DELETE FROM Vehicle WHERE id=:id")
    fun removeVehicleById(id: Int)

    @Query("SELECT * FROM Vehicle")
    fun getVehicles(): List<Vehicle>
}