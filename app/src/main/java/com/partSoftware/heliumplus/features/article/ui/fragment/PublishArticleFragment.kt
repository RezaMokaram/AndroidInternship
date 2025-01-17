package com.partSoftware.heliumplus.features.article.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.partSoftware.heliumplus.core.common.showSnackBar
import com.partSoftware.heliumplus.core.inputValidator.ValidationMessages
import com.partSoftware.heliumplus.databinding.FragmentPublishArticleBinding
import com.partSoftware.heliumplus.features.article.ui.model.TagView
import com.partSoftware.heliumplus.features.article.ui.viewModel.PublishArticleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PublishArticleFragment : Fragment() {

    private val publishArticleViewModel: PublishArticleViewModel by viewModels()

    private lateinit var binding: FragmentPublishArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPublishArticleBinding
            .inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpSoftInputMode()
        setOnClick()
        initObservation()
    }

    private fun initialize() {
        binding.apply {
            viewModel = publishArticleViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    @Suppress("Deprecation")
    private fun setUpSoftInputMode() {
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }


    private fun setOnClick() {

        binding.apply {

            tvAddTagFragmentPublishArticle.setOnClickListener {
                if (!publishArticleViewModel.isShowingBottomSheet) {
                    publishArticleViewModel.isShowingBottomSheet = true
                    TagBottomSheetFragment(true,
                        tvAddTagFragmentPublishArticle.text.toString(),
                        object : TagBottomSheetFragment.TagCallBack {
                            override fun callBack(selectedTag: TagView?) {
                                publishArticleViewModel.isShowingBottomSheet = false
                                if (selectedTag != null && selectedTag.name != "") {
                                    tvAddTagFragmentPublishArticle.text = selectedTag.name
                                    publishArticleViewModel.tagId = selectedTag.id.toString()
                                }
                            }
                        })
                        .show(childFragmentManager, null)
                }
            }

            ivCloseButtonFragmentPublishArticle.setOnClickListener {

                findNavController().navigateUp()
            }

            btnReleaseArticleFragmentPublishArticle.setOnClickListener {


                publishArticleViewModel.content =
                    etArticleBodyFragmentPublishArticle.text.toString()
                publishArticleViewModel.title =
                    etArticleTitleFragmentPublishArticle.text.toString()

                when (val validationMessage =
                    publishArticleViewModel.validatePublishArticleInputs()) {
                    ValidationMessages.SUCCESSFULLY -> publishArticleViewModel.addArticle()

                    else -> requireContext().showSnackBar(
                        binding.btnReleaseArticleFragmentPublishArticle,
                        validationMessage
                    )
                }
            }
        }
    }

    private fun initObservation() {
        publishArticleViewModel.addArticleResult.observe(viewLifecycleOwner) {
            requireContext().showSnackBar(binding.btnReleaseArticleFragmentPublishArticle, it)

            if (it == ValidationMessages.ARTICLE_SUCCESSFULLY_SENT) {
                findNavController().navigateUp()
            } else
                publishArticleViewModel.setButtonVisibility(false)
        }
    }
}