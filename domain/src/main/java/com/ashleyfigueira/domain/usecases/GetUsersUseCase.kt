package com.ashleyfigueira.domain.usecases

import com.ashleyfigueira.domain.common.SchedulerProvider
import com.ashleyfigueira.domain.entities.OrderEntity
import com.ashleyfigueira.domain.extensions.compose
import com.ashleyfigueira.domain.repositories.UsersRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository,
    private val schedulerProvider: SchedulerProvider
) {
    operator fun invoke(pageSize: Int = 20, order: OrderEntity = OrderEntity.Descending)
        = usersRepository.getUsers(pageSize, order).compose(schedulerProvider)
}