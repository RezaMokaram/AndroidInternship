package com.partSoftware.heliumplus.features.authenticate.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.partSoftware.heliumplus.core.common.safeNavigateTo
import com.partSoftware.heliumplus.core.common.showSnackBar
import com.partSoftware.heliumplus.core.inputValidator.ValidationMessages
import com.partSoftware.heliumplus.databinding.FragmentRegisterBinding
import com.partSoftware.heliumplus.features.authenticate.ui.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DUPLICATE_LABEL_IN_WHEN")
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpSoftInputMode()
        setOnClick()
        initObservation()
    }

    private fun initialize() {
        binding.apply {
            viewModel = registerViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }


    @Suppress("Deprecation")
    private fun setUpSoftInputMode() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun setOnClick() {
        binding.apply {
            btnRegisterFragmentRegister.setOnClickListener {
                tilFirstNameFragmentRegister.isErrorEnabled = false
                tilLastNameFragmentRegister.isErrorEnabled = false
                tilUserNameFragmentRegister.isErrorEnabled = false
                tilEmailFragmentRegister.isErrorEnabled = false
                tilPhoneNumberFragmentRegister.isErrorEnabled = false
                tilPasswordFragmentRegister.isErrorEnabled = false

                when (val validationMessage = registerViewModel.validateRegisterInput()) {

                    ValidationMessages.WRONG_PHONE_NUMBER,
                    ValidationMessages.EMPTY_PHONE_NUMBER ->
                        tilPhoneNumberFragmentRegister.error = validationMessage

                    ValidationMessages.EMPTY_PASSWORD,
                    ValidationMessages.WRONG_PASSWORD,
                    ValidationMessages.BAD_LENGTH_PASSWORD ->
                        tilPasswordFragmentRegister.error = validationMessage

                    ValidationMessages.EMPTY_FIRSTNAME,
                    ValidationMessages.BAD_LENGTH_FIRSTNAME,
                    ValidationMessages.WRONG_FIRSTNAME ->
                        tilFirstNameFragmentRegister.error = validationMessage

                    ValidationMessages.EMPTY_LASTNAME,
                    ValidationMessages.BAD_LENGTH_LASTNAME,
                    ValidationMessages.WRONG_LASTNAME ->
                        tilLastNameFragmentRegister.error = validationMessage

                    ValidationMessages.EMPTY_USERNAME,
                    ValidationMessages.WRONG_USERNAME,
                    ValidationMessages.BAD_LENGTH_USERNAME ->
                        tilUserNameFragmentRegister.error = validationMessage

                    ValidationMessages.EMPTY_EMAIL,
                    ValidationMessages.WRONG_EMAIL,
                    ValidationMessages.BAD_LENGTH_EMAIL ->
                        tilEmailFragmentRegister.error = validationMessage

                    ValidationMessages.SUCCESSFULLY -> registerViewModel.registerUser()
                }
            }

            tvLoginFragmentRegister.setOnClickListener {
                val directions =
                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().safeNavigateTo(directions)
            }
        }
    }

    private fun initObservation() {
        registerViewModel.registerResult.observe(viewLifecycleOwner) {
            requireContext().showSnackBar(binding.btnRegisterFragmentRegister, it)

            if (it == ValidationMessages.SUCCESSFULLY_REGISTER) {
                val directions =
                    RegisterFragmentDirections.actionRegisterFragmentToSuccessfullyRegisterFragment()
                findNavController().safeNavigateTo(directions)
            } else
                registerViewModel.setButtonVisibility(false)
        }
    }
}