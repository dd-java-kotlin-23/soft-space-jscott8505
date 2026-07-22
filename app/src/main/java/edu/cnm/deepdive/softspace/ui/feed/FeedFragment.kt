package edu.cnm.deepdive.softspace.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import edu.cnm.deepdive.softspace.R
import edu.cnm.deepdive.softspace.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

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

        binding.btnSettings.setOnClickListener {
            // TODO Navigate to the settings screen when that destination is available.
        }
        binding.navChat.setOnClickListener {
            binding.postsRecyclerView.scrollToPosition(0)
        }
        binding.navCalendar.setOnClickListener {
            // TODO Navigate to the calendar screen when that destination is available.
        }
        binding.navProfile.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
    }

    override fun onDestroyView() {
        binding.postsRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

}
