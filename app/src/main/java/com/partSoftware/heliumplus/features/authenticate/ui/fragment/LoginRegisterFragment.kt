package com.partSoftware.heliumplus.features.authenticate.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.partSoftware.heliumplus.core.common.safeNavigateTo
import com.partSoftware.heliumplus.databinding.FragmentLoginRegisterBinding

class LoginRegisterFragment : Fragment() {

    private lateinit var binding: FragmentLoginRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginRegisterBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
    }

    private fun setOnClick() {
        binding.apply {
            btnLoginFragmentLoginRegister.setOnClickListener {
                val directions =
                    LoginRegisterFragmentDirections.actionLoginRegisterFragmentToLoginFragment()
                findNavController().safeNavigateTo(directions)
            }

            btnRegisterFragmentLoginRegister.setOnClickListener {
                val directions =
                    LoginRegisterFragmentDirections.actionLoginRegisterFragmentToRegisterFragment()
                findNavController().safeNavigateTo(directions)
            }
        }
    }
}