package com.ashleyfigueira.data.di

import com.ashleyfigueira.data.users.UsersRepositoryImpl
import com.ashleyfigueira.domain.di.PerApplication
import com.ashleyfigueira.domain.repositories.UsersRepository
import dagger.Binds
import dagger.Module

@Module
abstract class UsersDataModule {

    @Binds
    @PerApplication
    abstract fun provideUsersRepository(usersRepository: UsersRepositoryImpl): UsersRepository
}