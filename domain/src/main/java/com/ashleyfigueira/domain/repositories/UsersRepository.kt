package com.ashleyfigueira.domain.repositories

import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.entities.OrderEntity
import com.ashleyfigueira.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface UsersRepository {
    fun getUsers(pageSize: Int, order: OrderEntity): Single<StackResult<List<UserEntity>>>
    fun getUser(id: Long): Flowable<StackResult<UserEntity>>
    fun updateUser(userEntity: UserEntity): Completable
}