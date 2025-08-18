import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "floors")
data class FloorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val level: Int,
    val isDeleted: Boolean,
    val venueId: Int
)
