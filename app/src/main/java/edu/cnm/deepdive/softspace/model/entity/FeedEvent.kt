package edu.cnm.deepdive.softspace.model.entity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import edu.cnm.deepdive.softspace.R

@AndroidEntryPoint
class FeedEvent {

    private lateinit var postAdapter: PostAdapter.PostViewHolder
    private lateinit var postList: List<Post>
    private lateinit var onCommentCLick: (Post) -> Unit
    private lateinit var commentCount: TextView
    private lateinit var commentButton: Button
    private lateinit var postPhoto: View
    private lateinit var postContent: TextView
    private lateinit var postDate: TextView
    private lateinit var authorName: TextView
    private lateinit var commentsAction: View
    private lateinit var post: Post

    class PostAdapter(
        private var posts: List<Post>, private val onCommentCLick: (Post) -> Unit
    ) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

        class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            annotation class PostViewHolder

            val authorName: TextView = itemView.findViewById(R.id.author_name)
            val postContent: TextView = itemView.findViewById(R.id.post_content)
            val postDate: TextView = itemView.findViewById(R.id.post_date)
            val commentButton: Button = itemView.findViewById(R.id.comment_button)
            val commentCount: TextView = itemView.findViewById(R.id.comment_count)
            val commentsAction: View = itemView.findViewById(R.id.layout_comments_action)
            val postPhoto: View = itemView.findViewById(R.id.img_post_photo)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
            return PostViewHolder(view)
        }

        override fun onBindViewHolder(p0: PostViewHolder, position: Int) {
            val post = posts[position]
            p0.authorName.text = post.authorName
            p0.postContent.text = post.content
            p0.postDate.text = post.date
            p0.commentButton.setOnClickListener { onCommentCLick(post)
                p0.postPhoto.visibility = if (!post.imageUrl.isNull) View.VISIBLE else View.GONE
                p0.commentsAction.visibility = View.VISIBLE
                p0.commentCount.visibility = View.VISIBLE
            }
        }

        override fun getItemCount(): Int {
            return posts.size
        }
    }

}