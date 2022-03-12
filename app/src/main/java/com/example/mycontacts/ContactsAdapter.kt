package com.example.mycontacts

import Model.Contact
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycontacts.databinding.ContactListItemBinding

class ContactsAdapter(private val contacts: List<Contact>, private val mainActivity: MainActivity) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(val contactListItemBinding: ContactListItemBinding) :
        RecyclerView.ViewHolder(contactListItemBinding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val contactListItemBinding =
            ContactListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(contactListItemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val contact = contacts[position]

        viewHolder.contactListItemBinding.contact = contact
        viewHolder.contactListItemBinding.contactItemHandler = ContactItemHandler(position)
    }

    inner class ContactItemHandler(private val position: Int) {
        fun onItemClicked() {
            mainActivity.showUpdateAlertDialog(position)
        }
    }

    override fun getItemCount() = contacts.size
}