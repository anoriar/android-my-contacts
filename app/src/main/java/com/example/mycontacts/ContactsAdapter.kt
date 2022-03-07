package com.example.mycontacts

import Model.Contact
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstNameTextView: TextView = view.findViewById(R.id.firstNameTextView)
        val lastNameTextView: TextView = view.findViewById(R.id.lastNameTextView)
        val emailTextView: TextView = view.findViewById(R.id.emailTextView)
        val phoneTextView: TextView = view.findViewById(R.id.phoneTextView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.contact_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val contact = contacts[position]

//        viewHolder.itemView.setOnClickListener(
//            object : View.OnClickListener {
//                override fun onClick(p0: View?) {
//                    TODO("Not yet implemented")
//                }
//            }
//        )

        viewHolder.firstNameTextView.text = contact.firstName
        viewHolder.lastNameTextView.text = contact.lastName
        viewHolder.emailTextView.text = contact.email
        viewHolder.phoneTextView.text = contact.phone
    }

    override fun getItemCount() = contacts.size
}