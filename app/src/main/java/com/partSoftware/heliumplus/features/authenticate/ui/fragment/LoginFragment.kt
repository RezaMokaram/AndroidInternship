package com.partSoftware.heliumplus.features.authenticate.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.partSoftware.heliumplus.core.common.NetworkConnectivity
import com.partSoftware.heliumplus.core.common.safeNavigateTo
import com.partSoftware.heliumplus.core.common.showSnackBar
import com.partSoftware.heliumplus.core.inputValidator.ValidationMessages
import com.partSoftware.heliumplus.databinding.FragmentLoginBinding
import com.partSoftware.heliumplus.databinding.LaytouSnackbarInternetBinding
import com.partSoftware.heliumplus.features.authenticate.ui.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpSoftInputMode()
        checkNetworkConnection()
        setOnClick()
        initObservation()
    }


    private fun initialize() {
        binding.apply {
            viewModel = loginViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    @Suppress("Deprecation")
    private fun setUpSoftInputMode() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun checkNetworkConnection() {
        if (!networkConnectivity.hasNetworkConnection())
            showInternetSnackBar()
    }

    private fun showInternetSnackBar() {
        val snackBar = Snackbar.make(binding.btnLoginFragmentLogin, "", Snackbar.LENGTH_LONG)
        val bin = LaytouSnackbarInternetBinding.inflate(layoutInflater, null, false)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setPadding(0, 0, 0, 0)
        bin.tvTryAgainLayoutSnackBarInternet.setOnClickListener {
            snackBar.dismiss()
        }
        bin.tvIgnoreLayoutSnackBarInternet.setOnClickListener {
            snackBar.dismiss()
        }
        snackBarLayout.addView(bin.root, 0)
        val params = snackBar.view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackBar.view.layoutParams = params
        snackBar.duration = 5000
        snackBar.show()
    }

    private fun setOnClick() {
        binding.apply {
            btnLoginFragmentLogin.setOnClickListener {

                loginViewModel.apply {
                    tilPhoneNumberFragmentLogin.isErrorEnabled = false
                    tilPasswordFragmentLogin.isErrorEnabled = false

                    when (val validationMessage = validateLoginInputs()) {
                        ValidationMessages.SUCCESSFULLY -> loginUser()
                        ValidationMessages.WRONG_PHONE_NUMBER,
                        ValidationMessages.EMPTY_PHONE_NUMBER ->
                            tilPhoneNumberFragmentLogin.error = validationMessage

                        ValidationMessages.EMPTY_PASSWORD,
                        ValidationMessages.WRONG_PASSWORD,
                        ValidationMessages.BAD_LENGTH_PASSWORD ->
                            tilPasswordFragmentLogin.error = validationMessage

                    }
                }
            }

            tvLinkToRegisterFragmentLogin.setOnClickListener {
                val directions =
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().safeNavigateTo(directions)
            }
        }
    }

    private fun initObservation() {
        loginViewModel.loginResult.observe(viewLifecycleOwner) {
            requireContext().showSnackBar(binding.btnLoginFragmentLogin, it)

            if (it == ValidationMessages.SUCCESSFULLY_LOGIN) {
                val directions =
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().safeNavigateTo(directions)
            } else
                loginViewModel.setButtonVisibility(false)
        }
    }
}