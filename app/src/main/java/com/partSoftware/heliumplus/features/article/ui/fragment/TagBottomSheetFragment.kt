package com.partSoftware.heliumplus.features.article.ui.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.partSoftware.heliumplus.core.common.addDynamicChips
import com.partSoftware.heliumplus.core.common.setVisibility
import com.partSoftware.heliumplus.core.common.showSnackBar
import com.partSoftware.heliumplus.databinding.BottomSheetTagsBinding
import com.partSoftware.heliumplus.features.article.ui.model.TagView
import com.partSoftware.heliumplus.features.article.ui.viewModel.TagBottomSheetViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TagBottomSheetFragment(
    private val isSingleSelection: Boolean = false,
    private val selectedTagText: String = "",
    private val tagCallBack: TagCallBack? = null
) : BottomSheetDialogFragment(
) {

    private val tagBottomSheetViewModel: TagBottomSheetViewModel by viewModels()

    private lateinit var binding: BottomSheetTagsBinding

    private var tags: List<TagView>? = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetTagsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setOnClick()
        initObservation()
    }

    private fun initialize() {
        binding.apply {
            viewModel = tagBottomSheetViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun setOnClick() {
        binding.apply {

            cgTagGroupBottomSheetTags.setOnCheckedStateChangeListener { _, checkedTagsIds ->

                tags?.forEach { tagView ->
                    if (tagView.id in checkedTagsIds) {
                        tagView.isSelected = true
                    } else {
                        tagView.isSelected = false
                        tagView.isChecked = false
                    }
                }
                if (isSingleSelection)
                    btnSubmitBottomSheetTags.setVisibility(checkedTagsIds.isNotEmpty())
            }

            btnSubmitBottomSheetTags.setOnClickListener {
                tagBottomSheetViewModel.isSubmitButtonClicked = true
                dismiss()
            }

            ivCloseBottomSheetTags.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun getSelectedChipText(): TagView? {
        binding.apply {

            for (i in 0 until cgTagGroupBottomSheetTags.childCount) {
                val chip = (cgTagGroupBottomSheetTags.getChildAt(i) as Chip)
                if (chip.isChecked) {
                    return (TagView(
                        name = chip.text.toString(),
                        id = chip.id,
                        isChecked = true,
                        isSelected = false
                    ))
                }
            }
            return null
        }
    }

    private fun initObservation() {

        tagBottomSheetViewModel.tagsLive?.observe(viewLifecycleOwner) { tags ->
            this.tags = tags
            binding.cgTagGroupBottomSheetTags
                .addDynamicChips(tags, isSingleSelection = isSingleSelection, isBottomSheet = true)
            if (isSingleSelection)
                setSelectedChip()
        }

        tagBottomSheetViewModel.resultMessage.observe(viewLifecycleOwner) {
            requireContext().showSnackBar(binding.btnSubmitBottomSheetTags, it)
        }
    }

    private fun setSelectedChip() {
        binding.apply {
            for (i in 0 until cgTagGroupBottomSheetTags.childCount) {
                val chip = (cgTagGroupBottomSheetTags.getChildAt(i) as Chip)
                if (chip.text == selectedTagText) {
                    chip.isChecked = true
                }
            }
        }
    }

    //set bottomSheet height with screen height
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { layout ->
                val behaviour = BottomSheetBehavior.from(layout)
                setupHeight(layout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = Resources.getSystem().displayMetrics.heightPixels * 70 / 100
        bottomSheet.layoutParams = layoutParams
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (!isSingleSelection) {
            tags?.let { tags ->
                if (tagBottomSheetViewModel.isSubmitButtonClicked) {
                    tagBottomSheetViewModel.updateTags(tags)
                }
                tagCallBack?.callBack()
            }
        } else {
            if (tagBottomSheetViewModel.isSubmitButtonClicked) {
                tagCallBack?.callBack(getSelectedChipText())
            } else
                tagCallBack?.callBack()
        }
        super.onDismiss(dialog)
    }

    interface TagCallBack {
        fun callBack(
            selectedTag: TagView? = null
        )
    }
}
