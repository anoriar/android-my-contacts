package Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @ColumnInfo(name = "contact_first_name")
    var firstName: String,
    @ColumnInfo(name = "contact_last_name")
    var lastName: String,
    @ColumnInfo(name = "contact_email")
    var email: String,
    @ColumnInfo(name = "contact_phone")
    var phone: String?
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    var id: Int = 0
        set(value) {
            field = value
        }
}