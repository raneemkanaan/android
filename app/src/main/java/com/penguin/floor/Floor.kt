package com.penguin.floor
import androidx.room.ColumnInfo
import androidx.room.Entity
//import com.penguin.floor.Venue

import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Floors",
    foreignKeys = [
        ForeignKey(
            entity = Venue::class,
            parentColumns = ["Id"],
            childColumns = ["VenueId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["VenueId"], name = "IX_Floors_VenueId")]
)
data class Floor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    val id: Int = 0,

    @ColumnInfo(name = "Name")
    val name: String,

    @ColumnInfo(name = "Level")
    val level: Int,

    @ColumnInfo(name = "IsDeleted")
    val isDeleted: Boolean,

    @ColumnInfo(name = "VenueId")
    val venueId: Int
)

