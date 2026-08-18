package edu.cnm.deepdive.softspace.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import edu.cnm.deepdive.softspace.model.entity.Event
import java.util.Calendar

class CalendarViewModel(application: Application) : AndroidViewModel(application) {

    private val firestore = FirebaseFirestore.getInstance()

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _error = MutableLiveData<Throwable?>()
    val error: LiveData<Throwable?> = _error

    /**
     * Loads events from Firestore matching the start/end bounds of a selected date.
     */
    fun fetchEventsForDate(year: Int, monthZeroBased: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, monthZeroBased, dayOfMonth, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis

        // Set to end of the selected day (23:59:59.999)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = calendar.timeInMillis - 1

        firestore.collection("events")
            .whereGreaterThanOrEqualTo("dateTimestamp", startOfDay)
            .whereLessThanOrEqualTo("dateTimestamp", endOfDay)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("CalendarViewModel", "Error fetching events", e)
                    _error.value = e
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val eventList = snapshot.toObjects(Event::class.java)
                    _events.value = eventList
                }
            }
    }

    /**
     * Optional: Loads ALL events from Firestore without date filtering.
     */
    fun fetchAllEvents() {
        firestore.collection("events")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    _error.value = e
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    _events.value = snapshot.toObjects(Event::class.java)
                }
            }
    }
}