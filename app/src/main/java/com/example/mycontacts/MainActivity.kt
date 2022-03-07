package com.example.mycontacts

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.security.InvalidParameterException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val contacts = mutableListOf<Contact>()
        contacts.add(Contact("test", "testov", "test@mail.ru", "79807774414"))
        contacts.add(Contact("test1", "testov1", "test1@mail.ru", "79807774416"))

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ContactsAdapter(contacts)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener({
            showAddAlertDialog()
        })
    }

    private fun showAddAlertDialog() {
        val view = getAlertDialogView()
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
                    addContact(Contact(firstName, lastName, email, phone))
                } catch (exception: InvalidParameterException) {
                    Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_SHORT).show()
                    return
                }
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

//    private fun showUpdateAlertDialog(contact: Contact) {
//        val view = getAlertDialogView()
//        val builder = AlertDialog.Builder(this)
//        builder.setView(view)
//        val title = view.findViewById<TextView>(R.id.newContactTitle)
//        title.text = "Edit"
//
//        builder.setPositiveButton("Update", object : DialogInterface.OnClickListener {
//            override fun onClick(p0: DialogInterface?, p1: Int) {
//
//            }
//
//        })
//
//        builder.show()
//    }

    private fun getAlertDialogView(): View {
        val layoutInflaterAndroid: LayoutInflater = LayoutInflater.from(getApplicationContext());
        val view: View = layoutInflaterAndroid.inflate(R.layout.layout_add_contact, null);


        return view
    }

    private fun addContact(contact: Contact) {

    }

    private fun updateContact() {

    }

    private fun deleteContact() {

    }
}