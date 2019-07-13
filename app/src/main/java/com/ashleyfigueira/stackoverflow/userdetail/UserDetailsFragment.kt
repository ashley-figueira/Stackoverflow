package com.ashleyfigueira.stackoverflow.userdetail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ashleyfigueira.stackoverflow.R
import com.ashleyfigueira.stackoverflow.base.BaseFragment
import com.ashleyfigueira.stackoverflow.base.ScreenState
import com.ashleyfigueira.stackoverflow.common.getFormattedDate
import com.ashleyfigueira.stackoverflow.common.load
import com.ashleyfigueira.stackoverflow.databinding.FragmentUserDetailsBinding
import com.bumptech.glide.request.RequestOptions

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding, UserDetailsViewModel>() {

    private val userId by lazy { navArgs<UserDetailsFragmentArgs>().value.userId }

    override fun getLayoutResId(): Int = R.layout.fragment_user_details

    override fun getBindingVariableId(): Int? = null

    override fun initUi() {
        binding.followButton.setOnClickListener { viewModel.follow() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.screenState.observe(viewLifecycleOwner, Observer { screenState ->
            when (screenState) {
                is ScreenState.Error -> showErrorAsSnackbar(screenState.errorMessage)
                is ScreenState.Success -> {
                    binding.userName.text = screenState.data.name
                    binding.reputation.text = screenState.data.reputation.toString()
                    binding.idNumber.text = screenState.data.id.toString()
                    binding.creationDate.text = screenState.data.creationDate.getFormattedDate()
                    binding.location.text = screenState.data.location
                    binding.userProfileImage.load(screenState.data.profileImageUrl, RequestOptions.circleCropTransform())
                    binding.followButton.text = if (screenState.data.isFollowing) getString(R.string.un_follow) else getString(R.string.follow)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.load(userId)
    }

}
