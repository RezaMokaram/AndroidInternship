package com.partSoftware.heliumplus.features.profile.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.partSoftware.heliumplus.R
import com.partSoftware.heliumplus.core.common.setVisibleOrGone
import com.partSoftware.heliumplus.databinding.FragmentProfileBinding
import com.partSoftware.heliumplus.features.profile.ui.ViewPagerAdapter
import com.partSoftware.heliumplus.features.profile.ui.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpTabLayout()
    }

    private fun initialize() {
        binding.tvUserFullNameFragmentProfile.text = profileViewModel.userFullName
    }

    private fun setUpTabLayout() {
        binding.apply {

            val viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle,profileViewModel.userId)
            viewPager2FragmentProfile.adapter = viewPagerAdapter

            TabLayoutMediator(
                tabLayoutProfileFragmentProfile,
                viewPager2FragmentProfile
            ) { tab, position ->

                when (position) {
                    0 -> {
                        tab.text = getString(R.string.label_myArticles)
                        tab.setIcon(R.drawable.ic_list_task)

                    }
                    1 -> {
                        tab.text = getString(R.string.label_bookmarks)
                        tab.setIcon(R.drawable.ic_bookmarks_favorite)
                    }
                }
            }.attach()


            if (profileViewModel.getUserIdArgument()!=null) {
               tabLayoutProfileFragmentProfile.setVisibleOrGone(false)
                viewPager2FragmentProfile.isUserInputEnabled=false
            }
        }
    }
}