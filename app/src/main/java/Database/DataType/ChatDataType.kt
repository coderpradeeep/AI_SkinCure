package Database.DataType

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatDataType(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var isUser: Boolean,
    var text: String
)