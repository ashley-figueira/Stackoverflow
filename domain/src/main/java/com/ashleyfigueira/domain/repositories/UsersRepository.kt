package com.ashleyfigueira.domain.repositories

import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.entities.OrderEntity
import com.ashleyfigueira.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface UsersRepository {
    fun getUsers(pageSize: Int, order: OrderEntity): Flowable<StackResult<List<UserEntity>>>
    fun getUser(id: Long): Flowable<StackResult<UserEntity>>
    fun updateUser(userEntity: UserEntity): Completable
}