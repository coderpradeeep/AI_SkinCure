package Database.DataType

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "UserDataType")
@Parcelize
data class UserDataType(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val name: String? = null,
    val age: Int? = null,
    val gender: String ?= null,
    val email: String ?= null,
    val password: String ?= null,
    val profilePic: String? = null,

    // Set the App for
    val isPatient: Boolean = true,
    val isGuest: Boolean = false,

    // Auth state
    val isAuthenticated: Boolean = false,

    // Extra fields for dermatologists
    val specialization: String? = null,
    val licenseNumber: String? = null,
    val experienceYears: Int? = null,
) : android.os.Parcelable {

    companion object {
        fun guest(): UserDataType {
            return UserDataType(
                id = -1,
                isGuest = true,
                isPatient = true,
                isAuthenticated = false
            )
        }
    }
}