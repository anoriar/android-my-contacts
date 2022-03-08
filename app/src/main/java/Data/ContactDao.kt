package Data

import Model.Contact
import androidx.room.*

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getAll(): MutableList<Contact>


    @Query("SELECT * FROM contacts where contact_id == :contactId")
    fun getById(contactId: Int): Contact

    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)
}