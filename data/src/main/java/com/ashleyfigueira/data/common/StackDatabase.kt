package com.ashleyfigueira.data.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ashleyfigueira.data.users.local.UserRoomEntity
import com.ashleyfigueira.data.users.local.UsersDao

@Database(entities = [(
    UserRoomEntity::class
)], version = 1, exportSchema = false)
abstract class StackDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}