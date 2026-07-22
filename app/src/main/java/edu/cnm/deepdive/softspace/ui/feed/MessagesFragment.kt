package edu.cnm.deepdive.softspace.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import edu.cnm.deepdive.softspace.R
import edu.cnm.deepdive.softspace.model.entity.Message

class MessagesFragment : Fragment() {

    private lateinit var adapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_messages)
        adapter = MessageAdapter()
        recyclerView.adapter = adapter

        // Sample Data for testing UI
        val dummyMessages = listOf(
            Message(senderId = 1, text = "Hey, is the sensory workshop still open today?"),
            Message(senderId = 2, text = "Yes! It starts at 3:00 PM in the main hall.")
        )
        adapter.setMessages(dummyMessages)
    }
}