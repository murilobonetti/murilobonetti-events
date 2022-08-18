package com.murilobonetti.events.eventslist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murilobonetti.events.data.Event
import com.murilobonetti.events.databinding.EventViewItemBinding

class EventsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Event, EventsAdapter.EventViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventViewItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.listItem.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(event)
        }

        holder.bind(event)
    }

    class EventViewHolder(private var binding: EventViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (event: Event) -> Unit) {
        fun onClick(event: Event) = clickListener(event)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }
    }
}