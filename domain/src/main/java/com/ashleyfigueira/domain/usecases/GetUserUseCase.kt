package com.ashleyfigueira.domain.usecases

import com.ashleyfigueira.domain.common.SchedulerProvider
import com.ashleyfigueira.domain.extensions.compose
import com.ashleyfigueira.domain.repositories.UsersRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val schedulerProvider: SchedulerProvider
) {
    operator fun invoke(id: Long) = usersRepository.getUser(id)
        .distinctUntilChanged()
        .compose(schedulerProvider)
}