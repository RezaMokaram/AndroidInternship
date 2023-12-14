package com.partSoftware.heliumplus.features.search.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.partSoftware.heliumplus.R
import com.partSoftware.heliumplus.core.common.showSnackBar
import com.partSoftware.heliumplus.databinding.FragmentSearchBinding
import com.partSoftware.heliumplus.features.search.ui.adapter.SearchViewPagerAdapter
import com.partSoftware.heliumplus.features.search.ui.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpViewPager()
        setOnClick()
        setUpSoftInputMode()
        initObservation()
    }

    private fun initialize() {

        binding.apply {
            viewModel = searchViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun setUpViewPager() {

        binding.apply {
            viewPagerSearchFragment.apply {
                adapter = SearchViewPagerAdapter(childFragmentManager, lifecycle)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {

                        chipPostsFragmentSearch.isChecked = (position == 0)
                        chipTagsFragmentSearch.isChecked = (position == 1)
                        chipUsersFragmentSearch.isChecked = (position == 2)
                    }
                })
            }
        }
    }

    private fun setOnClick() {

        binding.apply {
            cgTagsFragmentSearch.setOnCheckedStateChangeListener { _, checkedIds ->

                when {
                    R.id.chip_posts_fragmentSearch in checkedIds -> {
                        viewPagerSearchFragment.currentItem = 0
                    }
                    R.id.chip_tags_fragmentSearch in checkedIds -> {
                        viewPagerSearchFragment.currentItem = 1
                    }
                    R.id.chip_users_fragmentSearch in checkedIds -> {
                        viewPagerSearchFragment.currentItem = 2
                    }
                }
            }
        }
    }

    @Suppress("Deprecation")
    private fun setUpSoftInputMode() {
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
        )
    }

    private fun initObservation() {
        searchViewModel.searchResult.observe(viewLifecycleOwner){
            requireContext().showSnackBar(binding.tilSearchFragmentSearch, it)
        }
    }
}