package edu.cnm.deepdive.softspace.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.cnm.deepdive.softspace.R
import edu.cnm.deepdive.softspace.model.entity.Event

class CalendarFragment : Fragment() {

    private lateinit var adapter: EventAdapter
    private lateinit var viewModel: CalendarViewModel

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
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_events)

        adapter = EventAdapter { Log.d(tag, "$it") }
        recyclerView.adapter = adapter

        // Setup ViewModel
        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]

        val localPlaceholders = listOf(
            Event(
                title = "Walmart Daily Sensory Hours",
                location = "All Albuquerque Locations",
                description = "Everyday 8:00 AM – 10:00 AM: Dimmed overhead lights, muted radio, and static TV screens."
            ),
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

        // Observe events from Firebase & merge with local placeholders
        viewModel.events.observe(viewLifecycleOwner) { firebaseEvents ->
            val combinedList = mutableListOf<Event>()

            // Add local placeholders first
            combinedList.addAll(localPlaceholders)

            // Add Firebase events if present
            if (!firebaseEvents.isNullOrEmpty()) {
                combinedList.addAll(firebaseEvents)
            }

            // Update adapter
            adapter.setEvents(combinedList)
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            loadEventsForDate(year, month, dayOfMonth)
        }

        // Default load for August 22, 2026
        loadEventsForDate(year = 2026, month = 7, day = 22)
    }

    private fun loadEventsForDate(year: Int, month: Int, day: Int) {
                    viewModel.fetchEventsForDate(year, month, day)
                    }
}

    private val tag = CalendarFragment::class.java.simpleName



