package com.partSoftware.heliumplus.features.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.partSoftware.heliumplus.databinding.ItemUserListBinding
import com.partSoftware.heliumplus.features.search.ui.model.UsersViewSearch

class UserAdapter : ListAdapter<UsersViewSearch, UserAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserListBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    private var onItemClickListener: ((userId: Int, userFullName: String) -> Unit)? = null

    fun setOnItemClickListener(actionListener: ((userId: Int, userFullName: String) -> Unit)) {
        onItemClickListener = actionListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UsersViewSearch) {
            binding.userModel = user
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    getItem(adapterPosition).let { userViewSearch ->
                        userViewSearch.id?.let { id ->
                            it(
                                id,
                                "${userViewSearch.firstName} ${userViewSearch.lastName}"
                            )
                        }
                    }
                }
            }
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<UsersViewSearch>() {
        override fun areItemsTheSame(oldItem: UsersViewSearch, newItem: UsersViewSearch): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UsersViewSearch,
            newItem: UsersViewSearch
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}