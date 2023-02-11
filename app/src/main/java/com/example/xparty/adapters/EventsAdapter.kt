package com.example.xparty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.xparty.R
import com.example.xparty.data.models.Party
import com.example.xparty.databinding.RowViewBinding

class EventsAdapter(private val listener: EventListener) :
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

    class ViewHolder(
        private val itemBinding: RowViewBinding,
        private val listener: EventListener
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener, View.OnLongClickListener {

        private lateinit var event: Party

        init {
            // Define click listener for the ViewHolder's View
            itemBinding.root.setOnClickListener(this)
        }

        fun bindData(item: Party) {
            this.event = item
            itemBinding.rowViewName.text = item.partyName
            Glide.with(itemBinding.root).load(item.images).placeholder(R.drawable.event_party)
                .into(itemBinding.rowImgaeView)

            itemBinding.rowFavButton.setOnClickListener {
                listener.onImgClick(event)
            }
        }

        override fun onClick(v: View?) {
            listener.onEventClicked(event)
        }

        override fun onLongClick(p0: View?): Boolean {
            listener.onEventLongClicked(event)
            return false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
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