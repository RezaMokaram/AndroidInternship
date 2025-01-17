package com.partSoftware.heliumplus

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.partSoftware.heliumplus.core.common.safeNavigateTo
import com.partSoftware.heliumplus.core.common.showSnackBar
import com.partSoftware.heliumplus.core.data.Constants
import com.partSoftware.heliumplus.databinding.ActivityMainBinding
import com.partSoftware.heliumplus.features.authenticate.ui.fragment.SuccessfullyRegisterFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        initialize()
        handleBottomNavVisibility()
        setUpBottomNav()
    }

    private fun initialize() {

        navController = findNavController(R.id.navHost_activityMain)
    }

    private fun handleBottomNavVisibility() {

        binding.apply {
            supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {

                    override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) {
                        super.onFragmentResumed(fm, fragment)

                        when (navController.currentDestination?.id) {
                            R.id.homeFragment,
                            R.id.searchFragment,
                            R.id.profileFragment -> {
                                bottomNavActivityMain.visibility = View.VISIBLE
                                viewDividerActivityMain.visibility = View.VISIBLE
                            }

                            else -> {
                                bottomNavActivityMain.visibility = View.GONE
                                viewDividerActivityMain.visibility = View.GONE
                            }
                        }
                    }
                }, true
            )
        }
    }

    private fun setUpBottomNav() {

        binding.bottomNavActivityMain.setupWithNavController(navController)
    }

    //Handle back button
    private var lastClickedTime = 0L

    @Deprecated("Deprecated in Java")
    @Suppress("deprecation")
    override fun onBackPressed() {

        binding.apply {
            when (navController.currentDestination?.id) {
                R.id.homeFragment -> {
                    if (lastClickedTime + Constants.DOUBLE_TAP_DURATION > System.currentTimeMillis()) {
                        finish()
                    } else {
                        showSnackBar(
                            bottomNavActivityMain,
                            getString(R.string.please_enter_back_button)
                        )
                    }
                    lastClickedTime = System.currentTimeMillis()
                }

                //   R.id.profileFragment,
                R.id.searchFragment ->
                    bottomNavActivityMain.selectedItemId = R.id.homeFragment

                R.id.successfullyRegisterFragment -> {
                    val directions =
                        SuccessfullyRegisterFragmentDirections
                            .actionSuccessfullyRegisterFragmentToLoginFragment()
                    navController.safeNavigateTo(directions)
                }

                else -> super.onBackPressed()
            }
        }
    }
}