package com.ashleyfigueira.stackoverflow.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashleyfigueira.stackoverflow.common.ViewModelFactory
import com.ashleyfigueira.stackoverflow.userdetail.UserDetailsViewModel
import com.ashleyfigueira.stackoverflow.users.UsersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelProvider {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    abstract fun bindUsersViewModel(usersViewModel: UsersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailsViewModel::class)
    abstract fun bindUserDetailsViewModel(userDetailsViewModel: UserDetailsViewModel): ViewModel
}