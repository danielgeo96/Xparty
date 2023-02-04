package com.example.xparty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xparty.R
import com.example.xparty.data.models.Party

class EventsAdapter(private val callBack: EventListener) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private val events = ArrayList<Party>()

    fun setEvents(events: Collection<Party>) {
        this.events.clear()
        this.events.addAll(events)
        notifyDataSetChanged()
    }

    interface EventListener {
        fun onEventClicked(event: Party)
        fun onEventLongClicked(event: Party)
        fun onImgClick(event: Party)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener,View.OnLongClickListener {
        private val eventName: TextView
        private val favBtn: ImageButton

        init {
            // Define click listener for the ViewHolder's View
            eventName = view.findViewById(R.id.row_view_name)
            favBtn = view.findViewById(R.id.imageButton)
        }

        public fun bindData(party: Party) {
            eventName.text = party.partyName
            favBtn.setOnClickListener {
                callBack.onImgClick(events[adapterPosition])
            }
        }

        override fun onClick(v: View?) {
            callBack.onEventClicked(events[adapterPosition])
        }

        override fun onLongClick(p0: View?): Boolean {
            callBack.onEventLongClicked(events[adapterPosition])
            return false
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
        viewHolder.bindData(events[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = events.size

}