package com.ashleyfigueira.data.users.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserRoomEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "reputation") val reputation: Long,
    @ColumnInfo(name = "profileImageUrl") val profileImageUrl: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "creationDate") val creationDate: Long
)