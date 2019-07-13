package com.ashleyfigueira.stackoverflow.users

import androidx.lifecycle.LifecycleOwner
import com.ashleyfigueira.domain.common.StackError
import com.ashleyfigueira.domain.entities.UserEntity
import com.ashleyfigueira.domain.extensions.subscribeStackResult
import com.ashleyfigueira.domain.usecases.GetUsersUseCase
import com.ashleyfigueira.stackoverflow.R
import com.ashleyfigueira.stackoverflow.base.BaseViewModel
import com.ashleyfigueira.stackoverflow.base.ScreenState
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel<ScreenState<List<UserEntity>>>() {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getUsersUseCase()
            .addToLoadingState()
            .subscribeStackResult({ result ->
                _screenState.value = if (result.isEmpty()) ScreenState.empty() else ScreenState.success(result.sortedBy { it.reputation }.reversed())
            }, { error ->
                _screenState.value = when (error) {
                    is StackError.Offline -> ScreenState.noInternet()
                    else ->  ScreenState.error(R.string.error_loading_failed_message)
                }
            })
            .addToComposite()
    }
}
