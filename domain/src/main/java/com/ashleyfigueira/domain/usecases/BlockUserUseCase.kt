package com.ashleyfigueira.domain.usecases

import com.ashleyfigueira.domain.common.SchedulerProvider
import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.extensions.compose
import com.ashleyfigueira.domain.repositories.UsersRepository
import io.reactivex.Completable
import javax.inject.Inject

class BlockUserUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val schedulerProvider: SchedulerProvider
) {
    operator fun invoke(userId: Long): Completable {
        //Get user and change its value and finally update
        return usersRepository.getUser(userId)
            .firstOrError()
            .map {
                if (it is StackResult.Success) {
                    StackResult.Success(it.data.copy(isBlocked = !it.data.isBlocked))
                } else {
                    it
                }
            }.flatMapCompletable {
                if (it is StackResult.Success) {
                    usersRepository.updateUser(it.data)
                } else {
                    Completable.error(Throwable("Not able to update user"))
                }
            }.compose(schedulerProvider)
    }
}