package com.ashleyfigueira.domain.entities

import org.joda.time.DateTime

data class UserEntity(
    val id: Long,
    val name: String,
    val reputation: Long,
    val profileImageUrl: String,
    val location: String,
    val creationDate: DateTime,
    val isFollowing: Boolean,
    val isBlocked: Boolean
)