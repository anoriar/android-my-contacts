package com.example.mycontacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter(private val contacts: Array<String>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val firstNameTextView: TextView = view.findViewById(R.id.firstNameTextView)
        private val lastNameTextView: TextView = view.findViewById(R.id.lastNameTextView)
        private val emailTextView: TextView = view.findViewById(R.id.emailTextView)
        private val phoneTextView: TextView = view.findViewById(R.id.phoneTextView)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.contact_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

//        TODO:
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = contacts[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = contacts.size
}