package Data

import Model.Contact
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Contact::class], version = 2
)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}