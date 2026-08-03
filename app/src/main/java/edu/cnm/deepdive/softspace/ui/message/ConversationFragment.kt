package edu.cnm.deepdive.softspace.ui.message

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import edu.cnm.deepdive.softspace.R

/** Entry point for a message conversation; only the stable entity ID crosses navigation. */
class ConversationFragment : Fragment(R.layout.fragment_conversation) {

    private val args: ConversationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.detail_identifier).text =
            getString(R.string.message_id_format, args.messageId)
        // A ConversationViewModel should use args.messageId to resolve and observe the thread.
    }
}
