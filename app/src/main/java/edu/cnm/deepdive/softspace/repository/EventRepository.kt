package edu.cnm.deepdive.softspace.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import edu.cnm.deepdive.softspace.model.entity.Event
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class EventRepository @Inject constructor() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val eventCollection: CollectionReference = firestore.collection("events")
    private val _events = MutableLiveData<List<Event>>()

    val events: LiveData<List<Event>> = _events

    fun create(event: Event): Task<DocumentReference> {
        Log.i(TAG, "Creating event: $event")
        return eventCollection.add(event)
    }

    companion object {
        private const val TAG = "EventRepository"
    }

}