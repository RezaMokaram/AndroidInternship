package com.partSoftware.heliumplus.features.authenticate.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.partSoftware.heliumplus.R
import com.partSoftware.heliumplus.core.common.safeNavigateTo
import com.partSoftware.heliumplus.core.data.Constants
import com.partSoftware.heliumplus.databinding.FragmentSplashBinding
import com.partSoftware.heliumplus.features.authenticate.ui.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {


    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goToNextFragmentAfterDelay()
    }

    override fun onResume() {
        super.onResume()
        if (splashViewModel.delayFlag) {
            navigateToNextFragment()
        }
    }

    private fun goToNextFragmentAfterDelay() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(Constants.SPLASH_DELAY)
            splashViewModel.delayFlag = true
            navigateToNextFragment()
        }
    }

    private fun navigateToNextFragment() {

        val directions = if (splashViewModel.checkTokenExist())
            SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        else
            SplashFragmentDirections.actionSplashFragmentToLoginRegisterFragment()

        findNavController().safeNavigateTo(directions)
    }
}