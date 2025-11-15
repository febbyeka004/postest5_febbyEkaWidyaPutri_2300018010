package com.febby.postest5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.febby.postest5.Post

class FeedAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfile: ImageView = itemView.findViewById(R.id.imgProfilePost)
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsernamePost)
        val ivPost: ImageView = itemView.findViewById(R.id.imgPost)
        val tvCaption: TextView = itemView.findViewById(R.id.tvCaption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val post = posts[position]

        holder.ivProfile.setImageResource(post.profileImage)
        holder.tvUsername.text = post.username
        holder.tvCaption.text = post.caption

        if (post.postImageUri != null) {
            holder.ivPost.setImageURI(post.postImageUri)
        } else if (post.postImageRes != null) {
            holder.ivPost.setImageResource(post.postImageRes)
        }
    }

    override fun getItemCount(): Int = posts.size
}
