package com.ashleyfigueira.stackoverflow.userdetail

import com.ashleyfigueira.domain.entities.UserEntity
import com.ashleyfigueira.domain.extensions.subscribeStackResult
import com.ashleyfigueira.domain.usecases.GetUserUseCase
import com.ashleyfigueira.stackoverflow.R
import com.ashleyfigueira.stackoverflow.base.BaseViewModel
import com.ashleyfigueira.stackoverflow.base.ScreenState
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel<ScreenState<UserEntity>>() {

    fun load(id: Long) {
        getUserUseCase(id)
            .subscribeStackResult(
                { _screenState.value = ScreenState.success(it) },
                { _screenState.value = ScreenState.error(R.string.error_loading_failed_message) })
            .addToComposite()
    }

    fun follow(isFollowing: Boolean) {

    }
}
