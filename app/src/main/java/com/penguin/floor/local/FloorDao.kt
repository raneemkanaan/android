package com.penguin.floor.local

import androidx.room.*
import com.penguin.floor.Floor

@Dao
interface FloorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(floors: List<Floor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(floor: Floor)

    @Update
    suspend fun update(floor: Floor)

    @Delete
    suspend fun delete(floor: Floor)

    @Query("SELECT * FROM floors")
    suspend fun getAllFloors(): List<Floor>

    @Query("SELECT * FROM floors WHERE VenueId = :venueId")
    suspend fun getFloorsByVenue(venueId: Int): List<Floor>
}
