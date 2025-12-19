package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.models.Message

class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    
    private var messages: List<Message> = emptyList()

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
        val textViewBody: TextView = itemView.findViewById(R.id.textViewBody)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        holder.textViewName.text = message.name
        holder.textViewEmail.text = message.email
        holder.textViewBody.text = message.body
    }
    
    override fun getItemCount(): Int = messages.size

    fun updateMessages(newMessages: List<Message>) {
        messages = newMessages
        notifyDataSetChanged()
    }
}