package Data

import Model.Contact
import androidx.room.*

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getAll(): List<Contact>

    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)
}