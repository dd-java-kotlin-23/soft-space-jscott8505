package edu.cnm.deepdive.softspace.ui.post

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import edu.cnm.deepdive.softspace.R

/** Entry point for a post and its comments; only the stable entity ID crosses navigation. */
class PostDetailFragment : Fragment(R.layout.fragment_post_detail) {

    private val args: PostDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.detail_identifier).text =
            getString(R.string.post_id_format, args.postId)
        // A PostDetailViewModel should use args.postId to load the Post and its Comment records.
    }
}
