package edu.cnm.deepdive.softspace.ui.feed

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.cnm.deepdive.softspace.databinding.ItemPostBinding
import edu.cnm.deepdive.softspace.model.entity.Post
import java.text.DateFormat

class PostAdapter(
    private val onCommentsClick: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var posts: List<Post> = emptyList()

    fun submitList(posts: List<Post>) {
        this.posts = posts.toList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position], onCommentsClick)
    }

    override fun getItemCount(): Int = posts.size

    class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post, onCommentsClick: (Post) -> Unit) {
            binding.textAuthorName.text = post.authorName
            binding.textPostDate.text = DateFormat.getDateTimeInstance().format(post.createdDate)
            binding.textCommentCount.text = post.commentCount.toString()

            val imageUrl = post.imageUrl
            binding.imgPostPhoto.visibility =
                if (imageUrl.isNullOrBlank()) View.GONE else View.VISIBLE
            if (!imageUrl.isNullOrBlank()) {
                binding.imgPostPhoto.setImageURI(Uri.parse(imageUrl))
            } else {
                binding.imgPostPhoto.setImageDrawable(null)
            }

            binding.layoutCommentsAction.setOnClickListener {
                onCommentsClick(post)
            }
        }
    }
}