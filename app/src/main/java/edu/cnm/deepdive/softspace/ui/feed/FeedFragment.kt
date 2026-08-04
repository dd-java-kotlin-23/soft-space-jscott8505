package edu.cnm.deepdive.softspace.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.cnm.deepdive.softspace.R
import edu.cnm.deepdive.softspace.databinding.FragmentFeedBinding
import edu.cnm.deepdive.softspace.model.entity.Post
import edu.cnm.deepdive.softspace.viewmodel.FeedViewModel

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding
        get() = checkNotNull(_binding)

    private val postAdapter = PostAdapter { post ->
        // TODO Navigate to the comments screen with post.id.
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
            val post = Post(
                content = binding.postContentInput.text.toString()
            )
            viewModel.create(post)
        }

        binding.btnSettings.setOnClickListener {
            // TODO Navigate to the settings screen when that destination is available.
        }
        binding.navChat.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_messagesFragment)
        }
        binding.navCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_calendarFragment)
        }
        binding.navProfile.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_profileFragment)
        }
    }

    override fun onDestroyView() {
        binding.postsRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

}
