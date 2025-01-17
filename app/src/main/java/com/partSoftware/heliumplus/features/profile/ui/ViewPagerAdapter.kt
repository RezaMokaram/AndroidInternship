package com.partSoftware.heliumplus.features.profile.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.partSoftware.heliumplus.features.profile.ui.fragment.BookmarksFragment
import com.partSoftware.heliumplus.features.profile.ui.fragment.MyArticlesFragment

class ViewPagerAdapter(fragmentManger: FragmentManager, lifecycle: Lifecycle, private val authorId: Int) :
    FragmentStateAdapter(fragmentManger, lifecycle) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> MyArticlesFragment(authorId)
            1 -> BookmarksFragment()
            else -> MyArticlesFragment(authorId)
        }
    }
}