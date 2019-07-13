package com.ashleyfigueira.domain.repositories

import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.entities.OrderEntity
import com.ashleyfigueira.domain.entities.UserEntity
import io.reactivex.Single

interface UsersRepository {
    fun getUsers(pageSize: Int, order: OrderEntity): Single<StackResult<List<UserEntity>>>
    fun getUser(id: Long): Single<StackResult<UserEntity>>
}