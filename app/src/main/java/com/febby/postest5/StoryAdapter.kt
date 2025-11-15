package com.febby.postest5

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.febby.postest5.databinding.ItemStoryBinding

class StoryAdapter(private val stories: List<Story>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = stories[position]
        holder.binding.storyName.text = story.name
        holder.binding.storyImage.setImageResource(story.profileImageRes)
    }

    override fun getItemCount(): Int = stories.size
}
