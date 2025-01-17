package com.partSoftware.heliumplus.features.search.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.partSoftware.heliumplus.R
import com.partSoftware.heliumplus.core.common.addDynamicChipsSearch
import com.partSoftware.heliumplus.databinding.FragmentTagsBinding
import com.partSoftware.heliumplus.features.search.ui.model.toTagVew
import com.partSoftware.heliumplus.features.search.ui.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagsFragment : Fragment() {

    private lateinit var binding: FragmentTagsBinding

    private val searchViewModel: SearchViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTagsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservation()
    }

    private fun initObservation() {

        searchViewModel.searchText.observe(viewLifecycleOwner) { searchedText ->

            if (searchedText.isNotBlank())
                searchViewModel.getTags(searchedText)
            else
                binding.cgTagsFragmentTags.removeAllViews()
        }

        searchViewModel.tags.observe(viewLifecycleOwner) { tags ->

            binding.cgTagsFragmentTags.addDynamicChipsSearch(
                tags?.map { it.toTagVew() },
                R.style.littleChipStyle
            )
        }
    }
}