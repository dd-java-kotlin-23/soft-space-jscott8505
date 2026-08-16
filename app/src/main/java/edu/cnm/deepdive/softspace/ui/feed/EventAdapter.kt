package edu.cnm.deepdive.softspace.ui.feed

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.cnm.deepdive.softspace.R
import edu.cnm.deepdive.softspace.model.entity.Event

class EventAdapter(
    private var events: List<Event> = emptyList(),
    private val onEventClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.Holder>() {

    fun setEvents(events: List<Event>) {
        this.events = events
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(events[position], onEventClick)
    }

    override fun getItemCount(): Int = events.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.text_event_title)
        private val locationText: TextView = itemView.findViewById(R.id.text_event_location)
        private val descriptionText: TextView = itemView.findViewById(R.id.text_event_description)


        @SuppressLint("SetTextI18n")
        fun bind(event: Event, onEventClick: (Event) -> Unit) {
            titleText.text = event.title
            locationText.text = "📍 ${event.location}"
            descriptionText.text = event.description
            itemView.setOnClickListener { onEventClick(event) }
        }
    }

}
