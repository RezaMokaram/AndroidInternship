package com.partSoftware.heliumplus.features.search.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.partSoftware.heliumplus.features.search.ui.fragment.PostsFragment
import com.partSoftware.heliumplus.features.search.ui.fragment.TagsFragment
import com.partSoftware.heliumplus.features.search.ui.fragment.UsersFragment

class SearchViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> PostsFragment()
            1 -> TagsFragment()
            2 -> UsersFragment()
            else -> PostsFragment()
        }
    }
}