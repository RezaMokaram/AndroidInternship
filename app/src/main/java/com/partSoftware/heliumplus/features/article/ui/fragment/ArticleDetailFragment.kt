package com.partSoftware.heliumplus.features.article.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.partSoftware.heliumplus.R
import com.partSoftware.heliumplus.core.common.showSnackBar
import com.partSoftware.heliumplus.databinding.FragmentArticleDetailBinding
import com.partSoftware.heliumplus.features.article.ui.viewModel.ArticleDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {


    private val articleDetailViewModel: ArticleDetailViewModel by viewModels()

    private lateinit var binding: FragmentArticleDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleDetailBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setOnClick()
    }

    private fun initialize() {
        binding.apply {
            viewModel = articleDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun setOnClick() {

        binding.apply {
            ivBackFragmentArticleDetail.setOnClickListener {
                findNavController().navigateUp()
            }

            ivBookmarkFragmentArticleDetail.setOnClickListener {

                articleDetailViewModel.bookMarkArticle()
                if (articleDetailViewModel.isBookmarked.value == false) {

                    requireContext().showSnackBar(
                        it,
                        getString(R.string.message_articleSuccessfullyAddedToBookmarks)
                    )
                }
            }
        }
    }
}