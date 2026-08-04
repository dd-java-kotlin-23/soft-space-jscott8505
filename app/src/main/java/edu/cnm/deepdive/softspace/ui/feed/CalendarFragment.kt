package edu.cnm.deepdive.softspace.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import edu.cnm.deepdive.softspace.R
import edu.cnm.deepdive.softspace.model.entity.Event

class CalendarFragment : Fragment() {

    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView = view.findViewById<CalendarView>(R.id.calendar_view)
        // val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_events)

        // adapter = EventAdapter()
        // recyclerView.adapter = adapter

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            loadEventsForDate(year, month, dayOfMonth)
        }

        loadEventsForDate(2026, 6, 22)
    }

    private fun loadEventsForDate(year: Int, month: Int, day: Int) {
        // Sample events (replace with ViewModel / Room DB fetch later)
        val dummyEvents = listOf(
            Event(
                title = "Sensory Storytime",
                location = "Public Library - Room B",
                description = "Low lighting and soft seating available for children and families."
            ),
            Event(
                title = "Quiet Museum Hours",
                location = "Downtown Art Museum",
                description = "Audio exhibits turned down and crowd limits enforced."
            )
        )
        adapter.setEvents(dummyEvents)
    }
}