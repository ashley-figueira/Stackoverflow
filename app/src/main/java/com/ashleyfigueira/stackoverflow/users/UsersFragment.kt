package com.ashleyfigueira.stackoverflow.users

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashleyfigueira.stackoverflow.BR
import com.ashleyfigueira.stackoverflow.R
import com.ashleyfigueira.stackoverflow.base.BaseFragment
import com.ashleyfigueira.stackoverflow.base.ScreenState
import com.ashleyfigueira.stackoverflow.common.gone
import com.ashleyfigueira.stackoverflow.common.visible
import com.ashleyfigueira.stackoverflow.databinding.FragmentUsersBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class UsersFragment : BaseFragment<FragmentUsersBinding, UsersViewModel>() {

    private val groupAdapter = GroupAdapter<ViewHolder>()

    override fun getLayoutResId(): Int = R.layout.fragment_users

    override fun getBindingVariableId(): Int? = BR.vm

    override fun initUi() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, _ ->
            if (item is UserItem && item.userEntity.isBlocked.not()) {
                navigateTo(UsersFragmentDirections.userDetails(item.userEntity.id))
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.refreshLayout.isEnabled = false
        viewModel.screenState.observe(viewLifecycleOwner, Observer { screenState ->
            when (screenState) {
                is ScreenState.Error -> showErrorAsSnackbar(screenState.errorMessage)
                is ScreenState.Loading -> binding.refreshLayout.isRefreshing = screenState.isLoading
                is ScreenState.Empty -> {
                    binding.emptyView.visible()
                    binding.noInternetView.gone()
                    binding.recyclerView.gone()
                }
                is ScreenState.NoInternet -> {
                    binding.emptyView.gone()
                    binding.noInternetView.visible()
                    binding.recyclerView.gone()
                }
                is ScreenState.Success -> {
                    binding.emptyView.gone()
                    binding.noInternetView.gone()
                    binding.recyclerView.visible()

                    groupAdapter.update(screenState.data.map { UserItem(it) })
                }
            }
        })
    }
}
