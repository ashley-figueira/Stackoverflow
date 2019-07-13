package com.ashleyfigueira.domain.usecases

import com.ashleyfigueira.domain.common.SchedulerProvider
import com.ashleyfigueira.domain.repositories.UsersRepository
import javax.inject.Inject

class FollowUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val schedulerProvider: SchedulerProvider
) {
    operator fun invoke(userId: Long, isFollowing: Boolean) {

    }
}