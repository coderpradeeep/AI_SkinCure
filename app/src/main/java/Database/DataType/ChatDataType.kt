package Database.DataType

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ChatDataType")
data class ChatDataType(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isUser: Boolean,
    val text: String
)