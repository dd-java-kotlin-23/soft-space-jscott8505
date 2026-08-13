package edu.cnm.deepdive.softspace.ui.feed

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.cnm.deepdive.softspace.R
import edu.cnm.deepdive.softspace.databinding.FragmentFeedBinding
import edu.cnm.deepdive.softspace.model.entity.Post
import edu.cnm.deepdive.softspace.viewmodel.FeedViewModel
import kotlin.getValue

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding
        get() = checkNotNull(_binding)
    private val viewModel: FeedViewModel by viewModels()

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

        viewModel.allPosts().observe(viewLifecycleOwner) { posts ->
            postAdapter.submitList(posts)
        }

        binding.buttonPost.setOnClickListener {
            Log.i("FF", "onViewCreated: ")
            val post = Post(
                content = binding.postContentInput.text.toString()
            )
            viewModel.create(post).addOnSuccessListener {
                Log.i("Success", "onViewCreated: ${it.id}")
            }.addOnFailureListener {
                Log.i("FF", "onViewCreated: ${it.localizedMessage}")
            }
        }
    }


    override fun onDestroyView() {
        binding.postsRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
        Log.i("FF", "onDestroyView: ")
    }

}
