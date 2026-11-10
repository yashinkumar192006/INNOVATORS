package com.trinetra.mobile.android.features.userprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.HiltFragment
import kotlinx.coroutines.flow.launch
import javax.inject.Inject

@HiltFragment
class UserProfileFragment : Fragment() {

    private var viewModel: UserProfileViewModel: by lazy {
        hiltViewModel()
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load user profile data
        viewModel.loadProfile().collect { profile ->
            // Update UI with profile data
            binding.username.text = profile.username
            binding.email.text = profile.email
            // ... other fields
        }

        // Setup edit profile click listener
        binding.editProfile.setOnClickListener {
            findNavController().navigate(
                com.trinetra.mobile.android.R.id.action_userProfileToEditProfile
            )
        }

        // Setup logout click listener
        binding.logout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(
                com.trinetra.mobile.android.R.id.action_userProfileToLogin
            )
        }
    }
}