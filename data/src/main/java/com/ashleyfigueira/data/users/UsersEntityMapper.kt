package com.ashleyfigueira.data.users

import com.ashleyfigueira.data.users.local.UserRoomEntity
import com.ashleyfigueira.data.users.remote.UsersResponse
import com.ashleyfigueira.domain.common.Mapper
import com.ashleyfigueira.domain.entities.UserEntity
import org.joda.time.DateTime
import javax.inject.Inject

class UsersEntityMapper @Inject constructor() : Mapper<UsersResponse, List<UserEntity>>() {
    override fun mapFrom(from: UsersResponse): List<UserEntity> {
        return from.items?.map {
            UserEntity(
                it?.user_id?.toLong() ?: throw IllegalArgumentException("User does not have an id"),
                it.display_name ?: "",
                it.reputation?.toLong() ?: 0,
                it.profile_image ?: "",
                it.location ?: "",
                it.creation_date?.let { DateTime(it.toLong()) } ?: DateTime.now(),
                false,
                false
            )
        } ?: emptyList()
    }

    fun mapFromRoom(from: List<UserRoomEntity>): List<UserEntity> {
        return from.map { mapFromRoom(it) }
    }

    fun mapFromRoom(from: UserRoomEntity): UserEntity {
        return UserEntity(
            from.id,
            from.name,
            from.reputation,
            from.profileImageUrl,
            from.location,
            DateTime(from.creationDate),
            from.isFollowing,
            from.isBlocked
        )
    }

    fun mapToRoom(from: List<UserEntity>): List<UserRoomEntity> {
        return from.map { mapToRoom(it) }
    }

    fun mapToRoom(from: UserEntity): UserRoomEntity {
        return UserRoomEntity(
            from.id,
            from.name,
            from.reputation,
            from.profileImageUrl,
            from.location,
            from.creationDate.millis,
            from.isFollowing,
            from.isBlocked
        )
    }
}