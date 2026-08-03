package edu.cnm.deepdive.softspace.ui.profile

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.viewModels
    import dagger.hilt.android.AndroidEntryPoint
    import edu.cnm.deepdive.softspace.R
    import edu.cnm.deepdive.softspace.viewmodel.UserProfileViewModel

    @AndroidEntryPoint
    class ProfilePage : Fragment() {

        private val userProfileViewModel: UserProfileViewModel by viewModels()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.profile_page, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            observeViewModel()
        }

        private fun observeViewModel() {
        }
    }
