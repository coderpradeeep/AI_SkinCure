package Database.DataType

data class PatientQueryDatatype(
    val id: String,
    val patientName: String,
    val timestamp: String,
    val summary: String,
    val imageUrl: String? = null // You can later load this via Coil if needed
)
