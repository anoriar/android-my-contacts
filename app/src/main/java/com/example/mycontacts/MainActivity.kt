package com.example.mycontacts

import Data.ContactsDatabase
import Model.Contact
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.InvalidParameterException


class MainActivity : AppCompatActivity() {

    private lateinit var contactsDatabase: ContactsDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsAdapter: ContactsAdapter
    private var contacts: MutableList<Contact> = mutableListOf<Contact>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDb()

//        TODO: сделать асинхронно через корутины
        contacts = contactsDatabase.contactDao().getAll()
        initRecyclerView()
        initAddBtn()
        initSwipeHelper()
    }


    private fun initRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        contactsAdapter = ContactsAdapter(contacts, this)
        recyclerView.adapter = contactsAdapter
    }

    private fun initDb() {
        contactsDatabase = Room.databaseBuilder(
            applicationContext,
            ContactsDatabase::class.java, "contacts-database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    private fun initAddBtn() {
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener({
            showAddAlertDialog()
        })
    }

    private fun initSwipeHelper() {

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val contact: Contact = contacts.get(viewHolder.adapterPosition)
                deleteContact(contact)
            }
        }).attachToRecyclerView(recyclerView)
    }

    fun showAddAlertDialog() {
        val view = getAddUpdateAlertDialogView()
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val title = view.findViewById<TextView>(R.id.newContactTitle)
        title.text = "Add"

        val firstNameTextView: TextView = view.findViewById(R.id.firstNameEditText)
        val lastNameTextView: TextView = view.findViewById(R.id.lastNameEditText)
        val emailTextView: TextView = view.findViewById(R.id.emailEditText)
        val phoneTextView: TextView = view.findViewById(R.id.phoneEditText)



        builder.setPositiveButton("Save", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                try {
                    val firstName: String = firstNameTextView.text.toString()
                    val lastName: String = lastNameTextView.text.toString()
                    val email: String = emailTextView.text.toString()
                    val phone: String = phoneTextView.text.toString()
                    validateAlertDialogData(firstName, lastName, email)
                    addContact(firstName, lastName, email, phone)
                } catch (exception: InvalidParameterException) {
                    Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_SHORT).show()
                    return
                }
            }

        })

        builder.show()
    }

    fun showUpdateAlertDialog(position: Int) {
        val view = getAddUpdateAlertDialogView()
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val title = view.findViewById<TextView>(R.id.newContactTitle)
        title.text = "Add"

        val firstNameTextView: TextView = view.findViewById(R.id.firstNameEditText)
        val lastNameTextView: TextView = view.findViewById(R.id.lastNameEditText)
        val emailTextView: TextView = view.findViewById(R.id.emailEditText)
        val phoneTextView: TextView = view.findViewById(R.id.phoneEditText)

        val contact: Contact = contacts.get(position)

        firstNameTextView.text = contact.firstName
        lastNameTextView.text = contact.lastName
        emailTextView.text = contact.email
        phoneTextView.text = contact.phone

        builder.setPositiveButton("Save", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                try {
                    val firstName: String = firstNameTextView.text.toString()
                    val lastName: String = lastNameTextView.text.toString()
                    val email: String = emailTextView.text.toString()
                    val phone: String = phoneTextView.text.toString()
                    validateAlertDialogData(firstName, lastName, email)
                    updateContact(position, firstName, lastName, email, phone)
                } catch (exception: InvalidParameterException) {
                    Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_SHORT).show()
                    return
                }
            }

        })

        builder.show()
    }

    fun showDeleteAlertDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure to delete item?")

        builder.setPositiveButton("Confirm", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                try {
//                    deleteContact(position)
                } catch (exception: InvalidParameterException) {
                    Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_SHORT).show()
                    return
                }
            }
        }).setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0?.dismiss()
            }
        })

        builder.show()
    }

    private fun validateAlertDialogData(
        firstName: String,
        lastName: String,
        email: String
    ) {
        when {
            TextUtils.isEmpty(firstName) -> throw InvalidParameterException("Enter first name!")
            TextUtils.isEmpty(lastName) -> throw InvalidParameterException("Enter last name!")
            TextUtils.isEmpty(email) -> throw InvalidParameterException("Enter email!")
        }
    }

    private fun getAddUpdateAlertDialogView(): View {
        val layoutInflaterAndroid: LayoutInflater = LayoutInflater.from(getApplicationContext());
        val view: View = layoutInflaterAndroid.inflate(R.layout.layout_add_contact, null);

        return view
    }

    private fun addContact(
        firstName: String,
        lastName: String,
        email: String,
        phone: String? = null
    ) {
        val contact = Contact(firstName, lastName, email, phone)
        contactsDatabase.contactDao().insert(contact)

        contacts.add(0, contact)
        contactsAdapter.notifyDataSetChanged()
    }

    private fun updateContact(
        position: Int,
        firstName: String,
        lastName: String,
        email: String,
        phone: String? = null
    ) {
        val contact: Contact = contacts.get(position)

        contact.firstName = firstName
        contact.lastName = lastName
        contact.email = email
        contact.phone = phone


        contactsDatabase.contactDao().update(contact)
        contacts[position] = contact
        contactsAdapter.notifyDataSetChanged()

    }

    private fun deleteContact(contact: Contact) {
        contactsDatabase.contactDao().delete(contact)
        contacts.remove(contact)
        contactsAdapter.notifyDataSetChanged()
    }
}