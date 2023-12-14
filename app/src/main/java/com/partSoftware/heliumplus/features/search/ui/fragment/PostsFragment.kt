package com.partSoftware.heliumplus.features.search.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.partSoftware.heliumplus.R
import com.partSoftware.heliumplus.core.common.safeNavigateTo
import com.partSoftware.heliumplus.databinding.FragmentPostsBinding
import com.partSoftware.heliumplus.features.article.ui.adapter.ArticleAdapter
import com.partSoftware.heliumplus.features.search.ui.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private lateinit var binding: FragmentPostsBinding

    private val searchViewModel: SearchViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(layoutInflater, container, false)
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
                SearchFragmentDirections.actionSearchFragmentToArticleDetailFragment(
                    articleId
                )
            requireActivity().findNavController(R.id.navHost_activityMain)
                .safeNavigateTo(directions)
        }
        binding.rvArticleListFragmentPosts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }
    }

    private fun initObservation() {

        searchViewModel.searchText.observe(viewLifecycleOwner) {
            articleAdapter.submitList(emptyList())
            if (it.isNotBlank())
                searchViewModel.getArticles(it)
        }

        searchViewModel.articles.observe(viewLifecycleOwner) {
            articleAdapter.submitList(it)
        }
    }
}