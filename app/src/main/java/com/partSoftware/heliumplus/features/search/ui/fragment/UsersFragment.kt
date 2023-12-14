package com.partSoftware.heliumplus.features.search.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.partSoftware.heliumplus.R
import com.partSoftware.heliumplus.core.common.safeNavigateTo
import com.partSoftware.heliumplus.databinding.FragmentUsersBinding
import com.partSoftware.heliumplus.features.search.ui.adapter.UserAdapter
import com.partSoftware.heliumplus.features.search.ui.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment(R.layout.fragment_users) {

    private lateinit var binding: FragmentUsersBinding

    private lateinit var userAdapter: UserAdapter

    private val searchViewModel: SearchViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setUpRecyclerView()
        initObservation()
    }

    private fun initialize() {
        userAdapter = UserAdapter()
    }

    private fun setUpRecyclerView() {
        binding.rvUserListFragmentUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

        userAdapter.setOnItemClickListener { userId, userFullName ->
            val directions =
                SearchFragmentDirections.actionSearchFragmentToProfileFragment(
                    userId, userFullName
                )
            findNavController().safeNavigateTo(directions)
        }
    }

    private fun initObservation() {

        searchViewModel.searchText.observe(viewLifecycleOwner) { searchedText ->
            userAdapter.submitList(emptyList())
            if (searchedText.isNotBlank())
                searchViewModel.getUsers(searchedText)
        }

        searchViewModel.users.observe(viewLifecycleOwner) { users ->
            userAdapter.submitList(users)
        }
    }
}