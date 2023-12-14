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
import com.partSoftware.heliumplus.core.common.showSnackBar
import com.partSoftware.heliumplus.databinding.FragmentMyArticlesBinding
import com.partSoftware.heliumplus.features.article.ui.adapter.ArticleAdapter
import com.partSoftware.heliumplus.features.profile.ui.viewModel.MyArticleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyArticlesFragment(private val authorId: Int) : Fragment() {

    private val myArticleViewModel: MyArticleViewModel by viewModels()

    private lateinit var binding: FragmentMyArticlesBinding
    private lateinit var myArticleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_articles, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpRecyclerView()
        initObservation()
        getData()
    }

    private fun initialize() {
        myArticleAdapter = ArticleAdapter(false)
        binding.apply {
            viewModel = myArticleViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }


    private fun setUpRecyclerView() {

        myArticleAdapter.setOnItemClickListener { articleId ->
            val directions =
                ProfileFragmentDirections.actionProfileFragmentToReadArticleFragment(articleId)
            findNavController().safeNavigateTo(directions)
        }

        binding.rvMyArticleListFragmentMyArticle.apply {

            layoutManager = LinearLayoutManager(requireContext())
            adapter = myArticleAdapter
        }
    }

    private fun initObservation() {
        myArticleViewModel.authorArticles.observe(viewLifecycleOwner) { authorArticles ->
            myArticleAdapter.submitList(authorArticles)
        }

        myArticleViewModel.resultMessage.observe(viewLifecycleOwner){
            requireContext().showSnackBar(binding.rvMyArticleListFragmentMyArticle, it)
        }
    }

    private fun getData() {
        myArticleViewModel.getAuthorArticles(authorId)
    }
}