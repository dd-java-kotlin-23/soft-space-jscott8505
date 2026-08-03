package edu.cnm.deepdive.softspace.ui.feed

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.cnm.deepdive.softspace.R
import edu.cnm.deepdive.softspace.model.entity.Message
import java.text.SimpleDateFormat
import java.util.Locale

class MessageAdapter(
    private var messages: List<Message> = emptyList(),
    private val onMessageClick: (Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.Holder>() {

    fun serMessages(messages: List<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(messages[position], onMessageClick)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun setMessages(messages: List<Message>) {
        this.messages = messages.toList()
        notifyDataSetChanged()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val senderText: TextView = itemView.findViewById(R.id.text_message_sender)
        private val bodyText: TextView = itemView.findViewById(R.id.text_message_body)
        private val timeText: TextView = itemView.findViewById(R.id.text_message_time)
        private val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

        @SuppressLint("SetTextI18n")
        fun bind(message: Message, onMessageClick: (Message) -> Unit) {
            senderText.text = "User #${message.senderId}"
            bodyText.text = message.text
            timeText.text = dateFormat.format(message.timestamp)
            itemView.setOnClickListener { onMessageClick(message) }
        }
    }
}
