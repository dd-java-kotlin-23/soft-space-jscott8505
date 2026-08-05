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
        viewModel.getUserProfile().observe(viewLifecycleOwner, ::showProfile)
        viewModel.getError().observe(viewLifecycleOwner) { error ->
            if (error != null) {
                val message = error.localizedMessage ?: getString(R.string.profile_load_failed)
                Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
            }
        }
//      viewModel.load(args.userId)
        // FIXME: fix this so we can load the profile.
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
            ?.let(Uri::parse)
            ?.let(binding.imgProfile::setImageURI)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
