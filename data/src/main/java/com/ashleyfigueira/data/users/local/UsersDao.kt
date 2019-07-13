package com.ashleyfigueira.data.users.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<UserRoomEntity>): Completable

    @Query("SELECT * from Users")
    fun getUsers(): Flowable<List<UserRoomEntity>>

    @Query("SELECT * from Users WHERE id = :id")
    fun getUser(id: Long): Flowable<UserRoomEntity>

    @Update
    fun update(user: UserRoomEntity): Completable

    @Delete
    fun delete(user: UserRoomEntity): Completable
}