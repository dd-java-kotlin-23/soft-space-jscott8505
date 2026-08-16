package edu.cnm.deepdive.softspace.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import edu.cnm.deepdive.softspace.R
import edu.cnm.deepdive.softspace.databinding.ProfilePageBinding
import edu.cnm.deepdive.softspace.model.entity.UserProfile
import edu.cnm.deepdive.softspace.ui.feed.PostAdapter
import edu.cnm.deepdive.softspace.viewmodel.UserProfileViewModel

@AndroidEntryPoint
class ProfilePage : Fragment() {

    private val args: ProfilePageArgs by navArgs()
    private val viewModel: UserProfileViewModel by viewModels()
    private var _binding: ProfilePageBinding? = null
    private val binding: ProfilePageBinding
        get() = checkNotNull(_binding)
    private val postAdapter = PostAdapter { post ->
        findNavController().navigate(
            R.id.postDetailFragment,
            Bundle().apply { putLong("postId", post.id) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvUserPosts.adapter = postAdapter
        viewModel.userProfile.observe(viewLifecycleOwner, ::showProfile)
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            postAdapter.submitList(posts ?: emptyList())
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                val message = error.localizedMessage ?: getString(R.string.profile_load_failed)
                Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
            }
        }
        val targetUserId = arguments?.getString("userId")

        if (targetUserId.isNullOrBlank()) {
            viewModel.loadCurrentUser()
        } else {
            viewModel.load(targetUserId)
            binding.rvUserPosts.adapter = postAdapter
            viewModel.posts.observe(viewLifecycleOwner) { posts ->
                postAdapter.submitList(posts ?: emptyList())

                // 1. ALWAYS attach adapter first
                binding.rvUserPosts.adapter = postAdapter

                // 2. Observe profile details
                viewModel.userProfile.observe(viewLifecycleOwner, ::showProfile)

                // 3. Observe posts list ONCE
                viewModel.posts.observe(viewLifecycleOwner) { posts ->
                    postAdapter.submitList(posts ?: emptyList())
                }

                viewModel.error.observe(viewLifecycleOwner) { error ->
                    if (error != null) {
                        val message = error.localizedMessage ?: "Unable to load profile."
                        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
                    }
                }

                // 4. Trigger the user load
                val targetUserId = arguments?.getString("userId")
                if (targetUserId.isNullOrBlank()) {
                    viewModel.loadCurrentUser()
                } else {
                    viewModel.load(targetUserId)
                }
            }
        }

    }

    private fun showProfile(profile: UserProfile?) {
        if (profile == null) {
            return
        }
        binding.tvName.text = profile.displayName
        binding.tvEmail.text = profile.email
        binding.bio.text = profile.bio
        binding.tvPostsTitle.text =
            getString(R.string.posts_by_format, profile.displayName.orEmpty())
        profile.profilePicture
            ?.takeIf(String::isNotBlank)
            ?.let { uriString ->
                try {
                    binding.imgProfile.setImageURI(Uri.parse(uriString))
                } catch (e: SecurityException) {
                    // Fallback to default avatar if permission is denied
                    binding.imgProfile.setImageResource(R.drawable.cover)
                } catch (e: Exception) {
                    // Catch any other unexpected image loading errors safely
                    binding.imgProfile.setImageResource(R.drawable.autism)

                    binding.tvName.text = profile.displayName
                    binding.tvEmail.text = profile.email

                    // Bind bio (with fallback if empty)
                    binding.bio.text = profile.bio.ifEmpty { "No bio available." }

                    binding.tvPostsTitle.text = "Posts by ${profile.displayName.orEmpty()}"

                    // ... profile image logic ...
                }
            }
    }

    override fun onDestroyView() {
        binding.rvUserPosts.adapter = null
        _binding = null
        super.onDestroyView()
    }

}
