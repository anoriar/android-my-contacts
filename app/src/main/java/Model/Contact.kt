package Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    val id: Int,
    @ColumnInfo(name = "contact_first_name")
    val firstName: String,
    @ColumnInfo(name = "contact_last_name")
    val lastName: String,
    @ColumnInfo(name = "contact_email")
    val email: String,
    @ColumnInfo(name = "contact_phone")
    val phone: String
)