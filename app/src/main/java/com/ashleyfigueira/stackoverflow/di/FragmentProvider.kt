package com.ashleyfigueira.stackoverflow.di

import com.ashleyfigueira.stackoverflow.userdetail.UserDetailsFragment
import com.ashleyfigueira.stackoverflow.users.UsersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    abstract fun provideUserDetailsFragment(): UserDetailsFragment
}