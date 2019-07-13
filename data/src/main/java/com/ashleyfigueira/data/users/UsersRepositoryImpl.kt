package com.ashleyfigueira.data.users

import com.ashleyfigueira.data.common.ErrorMapper
import com.ashleyfigueira.data.common.OrderEntityMapper
import com.ashleyfigueira.data.users.local.UsersDao
import com.ashleyfigueira.data.users.remote.UsersService
import com.ashleyfigueira.domain.common.StackError
import com.ashleyfigueira.domain.common.StackResult
import com.ashleyfigueira.domain.entities.OrderEntity
import com.ashleyfigueira.domain.entities.UserEntity
import com.ashleyfigueira.domain.extensions.doOnStackSuccess
import com.ashleyfigueira.domain.extensions.toResult
import com.ashleyfigueira.domain.repositories.UsersRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersDao: UsersDao,
    private val usersService: UsersService,
    private val errorMapper: ErrorMapper,
    private val usersEntityMapper: UsersEntityMapper,
    private val orderEntityMapper: OrderEntityMapper
) : UsersRepository {

    override fun getUser(id: Long): Flowable<StackResult<UserEntity>> {
        return usersDao.getUser(id)
            .map { usersEntityMapper.mapFromRoom(it).toResult() }
            .onErrorReturn { errorMapper.mapFrom(it).toResult() }
    }

    override fun getUsers(pageSize: Int, order: OrderEntity): Single<StackResult<List<UserEntity>>> {
        val cache = usersDao.getUsers()
            .map {
                if (it.isEmpty()) {
                    StackResult.Failure(StackError.NothingInDatabase(Exception("No data in database")))
                } else {
                    usersEntityMapper.mapFromRoom(it).toResult()
                }
            }
            .onErrorReturn { errorMapper.mapFrom(it).toResult() }

        val networkWithSave = usersService.getUsers(pageSize, orderEntityMapper.mapFrom(order))
            .map { usersEntityMapper.mapFrom(it).toResult() }
            .onErrorReturn { errorMapper.mapFrom(it).toResult() }
            .doOnStackSuccess { usersDao.insertUsers(usersEntityMapper.mapToRoom(it)).subscribe() }

        return Single.concat(cache, networkWithSave)
            .filter { it is StackResult.Success }
            .firstOrError()
            .onErrorResumeNext(networkWithSave)
    }

    override fun updateUser(userEntity: UserEntity): Completable {
        return usersDao.update(usersEntityMapper.mapToRoom(userEntity))
    }
}