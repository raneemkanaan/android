package com.penguin.floor.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VenueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venue: VenueEntity)

    @Query("SELECT * FROM venues")
    suspend fun getAllVenues(): List<VenueEntity>
}
