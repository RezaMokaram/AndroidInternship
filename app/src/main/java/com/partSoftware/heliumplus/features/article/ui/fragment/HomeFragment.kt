package com.partSoftware.heliumplus.features.article.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.partSoftware.heliumplus.core.common.addDynamicChips
import com.partSoftware.heliumplus.core.common.safeNavigateTo
import com.partSoftware.heliumplus.core.common.showSnackBar
import com.partSoftware.heliumplus.databinding.FragmentHomeBinding
import com.partSoftware.heliumplus.features.article.ui.adapter.ArticleAdapter
import com.partSoftware.heliumplus.features.article.ui.model.TagView
import com.partSoftware.heliumplus.features.article.ui.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var articleAdapter: ArticleAdapter

    private var tags: List<TagView>? = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setOnClick()
        setUpRecyclerView()
        initObservation()
    }

    private fun initialize() {

        articleAdapter = ArticleAdapter()
        binding.apply {
            viewModel = homeViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun setOnClick() {

        binding.apply {

            fabGoToAddArticleFragmentHome.setOnClickListener {
                val directions =
                    HomeFragmentDirections.actionHomeFragmentToPublishArticleFragment()
                findNavController().safeNavigateTo(directions)
            }

            ivAddTagFragmentHome.setOnClickListener {
                if (!homeViewModel.isShowingBottomSheet) {
                    homeViewModel.isShowingBottomSheet = true
                    tags?.let { tags -> homeViewModel.updateTags(tags) }
                    TagBottomSheetFragment(isSingleSelection = false,
                        tagCallBack = object : TagBottomSheetFragment.TagCallBack {
                            override fun callBack(selectedTag: TagView?) {
                                homeViewModel.isShowingBottomSheet = false
                            }
                        }).show(childFragmentManager, null)
                }
            }

            cgTagsFragmentHome.setOnCheckedStateChangeListener { _, checkedids ->

                tags?.forEach { tagView ->
                    tagView.isChecked = (tagView.id in checkedids)
                }
                if (checkedids.isNotEmpty())
                    homeViewModel.getArticleByTags(checkedids)
                else
                    homeViewModel.getArticles()
            }
        }
    }

    private fun setUpRecyclerView() {

        articleAdapter.setOnItemClickListener { articleId ->
            val directions =
                HomeFragmentDirections.actionHomeFragmentToArticleDetailFragment(
                    articleId
                )
            findNavController().safeNavigateTo(directions)
        }

        binding.rvArticleListFragmentHome.apply {

            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }
    }

    private fun initObservation() {

        homeViewModel.articles.observe(viewLifecycleOwner) { articles ->

            articleAdapter.submitList(articles)
        }

        homeViewModel.selectedTags?.observe(viewLifecycleOwner) { tags ->
            this.tags = tags
            binding.apply {
                cgTagsFragmentHome.addDynamicChips(
                    tags,
                    isSingleSelection = false,
                    isBottomSheet = false
                )

                hsvTagsFragmentHome.postDelayed({
                    hsvTagsFragmentHome.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
                }, 0)
            }
        }

        homeViewModel.resultMessage.observe(viewLifecycleOwner) {
            requireContext().showSnackBar(binding.fabGoToAddArticleFragmentHome, it)
        }

        homeViewModel.checkedTagsIdsLiveData?.observe(viewLifecycleOwner) {
            if (it != null && !homeViewModel.isShowingBottomSheet) {
                if (it.isEmpty())
                    homeViewModel.getArticles()
                else
                    homeViewModel.getArticleByTags(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        tags?.let { tags -> homeViewModel.updateTags(tags) }
    }
}