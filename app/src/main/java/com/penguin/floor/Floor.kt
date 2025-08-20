package com.penguin.floor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "floors",
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
    var id: Int? = null,

    @ColumnInfo(name = "Name")
    var name: String,

    @ColumnInfo(name = "Level")
    var level: Int,

    @ColumnInfo(name = "IsDeleted")
    var isDeleted: Boolean,

    @ColumnInfo(name = "VenueId")
    var venueId: Int
)
