package com.ashleyfigueira.stackoverflow.userdetail

import com.ashleyfigueira.domain.entities.UserEntity
import com.ashleyfigueira.domain.extensions.subscribeStackResult
import com.ashleyfigueira.domain.usecases.BlockUserUseCase
import com.ashleyfigueira.domain.usecases.FollowUserUseCase
import com.ashleyfigueira.domain.usecases.GetUserUseCase
import com.ashleyfigueira.stackoverflow.R
import com.ashleyfigueira.stackoverflow.base.BaseViewModel
import com.ashleyfigueira.stackoverflow.base.ScreenState
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val followUserUseCase: FollowUserUseCase,
    private val blockUserUseCase: BlockUserUseCase
) : BaseViewModel<ScreenState<UserEntity>>() {

    private lateinit var user: UserEntity

    fun load(id: Long) {
        getUserUseCase(id)
            .subscribeStackResult({ user = it
                _screenState.value = ScreenState.success(it) },
                { _screenState.value = ScreenState.error(R.string.error_loading_failed_message) })
            .addToComposite()
    }

    fun follow() {
        followUserUseCase(user.id)
            .subscribe()
            .addToComposite()
    }

    fun block() {
        blockUserUseCase(user.id)
            .subscribe()
            .addToComposite()
    }
}
