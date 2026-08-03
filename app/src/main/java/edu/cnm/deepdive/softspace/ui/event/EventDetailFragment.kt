package edu.cnm.deepdive.softspace.ui.event

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import edu.cnm.deepdive.softspace.R

/** Entry point for one event; only the stable entity ID crosses navigation. */
class EventDetailFragment : Fragment(R.layout.fragment_event_detail) {

    private val args: EventDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.detail_identifier).text =
            getString(R.string.event_id_format, args.eventId)
        // An EventDetailViewModel should use args.eventId to load the current Event record.
    }
}
