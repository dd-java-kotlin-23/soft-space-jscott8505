package edu.cnm.deepdive.softspace.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cnm.deepdive.softspace.R
import edu.cnm.deepdive.softspace.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding
        get() = checkNotNull(_binding)

    private val postAdapter = PostAdapter { post ->
        val bundle = Bundle().apply {
            putLong("postId", post.id)
        }
        findNavController().navigate(R.id.action_feedFragment_to_postDetailFragment, bundle)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postsRecyclerView.adapter = postAdapter
    }


    override fun onDestroyView() {
        binding.postsRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

}
