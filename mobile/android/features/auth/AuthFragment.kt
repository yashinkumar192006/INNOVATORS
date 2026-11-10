package com.trinetra.mobile.android.features.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// Auth Fragment for login/registration
class AuthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate auth layout - placeholder for now
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Implement auth UI (login form, register button, etc.)
        // Navigate on successful auth
        // findNavController().navigate(R.id.action_authFragment_to_homeFragment)
    }
}