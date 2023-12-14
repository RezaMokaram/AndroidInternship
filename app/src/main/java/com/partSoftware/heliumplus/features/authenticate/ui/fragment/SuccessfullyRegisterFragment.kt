package com.partSoftware.heliumplus.features.authenticate.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.partSoftware.heliumplus.core.common.safeNavigateTo
import com.partSoftware.heliumplus.databinding.FragmentSuccessFullyRegisterBinding

class SuccessfullyRegisterFragment : Fragment() {

    private lateinit var binding: FragmentSuccessFullyRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessFullyRegisterBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
    }

    private fun setOnClick() {

        binding.btnLoginFragmentSuccessfullyRegister.setOnClickListener {
            val directions =
                SuccessfullyRegisterFragmentDirections
                    .actionSuccessfullyRegisterFragmentToLoginFragment()
            findNavController().safeNavigateTo(directions)
        }
    }
}