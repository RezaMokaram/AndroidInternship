package com.partSoftware.heliumplus.features.profile.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.partSoftware.heliumplus.R
import com.partSoftware.heliumplus.core.common.safeNavigateTo
import com.partSoftware.heliumplus.databinding.FragmentBookmarksBinding
import com.partSoftware.heliumplus.features.article.ui.adapter.ArticleAdapter
import com.partSoftware.heliumplus.features.profile.ui.viewModel.BookmarksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private val bookmarksViewModel: BookmarksViewModel by viewModels()

    private lateinit var binding: FragmentBookmarksBinding
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarks, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpRecyclerView()
        initObservation()
    }

    private fun initialize() {
        articleAdapter = ArticleAdapter()
    }


    private fun setUpRecyclerView() {

        articleAdapter.setOnItemClickListener { articleId ->
            val directions =
                ProfileFragmentDirections.actionProfileFragmentToReadArticleFragment(articleId)
            findNavController().safeNavigateTo(directions)
        }

        binding.rvBookMarksListFragmentBookmark.apply {

            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }
    }

    private fun initObservation() {

        bookmarksViewModel.bookmarkedArticles.observe(viewLifecycleOwner) {
            articleAdapter.submitList(it)
        }
    }
}