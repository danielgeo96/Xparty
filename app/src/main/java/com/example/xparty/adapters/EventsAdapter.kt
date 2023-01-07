package com.example.xparty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xparty.R
import com.example.xparty.data.Party

class EventsAdapter(private val dataSet: List<Party>) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    var onItemClick:((Party) -> Unit)? = null
    var eventsData: List<Party> = emptyList()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val eventName: TextView
        private val eventLocation: TextView
        private val eventDescription: TextView

        init {
            // Define click listener for the ViewHolder's View
            eventName = view.findViewById(R.id.row_view_name)
            eventLocation = view.findViewById(R.id.row_view_location)
            eventDescription = view.findViewById(R.id.row_view_description)

            view.setOnClickListener {
                onItemClick?.invoke(eventsData[adapterPosition])
            }
        }

        public fun bindData(party: Party, position: Int) {
            eventName.text = party.partyName
            eventLocation.text = party.partyLocation
            eventDescription.text = party.partyDescription
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_view, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.bindData(eventsData[position],position)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}