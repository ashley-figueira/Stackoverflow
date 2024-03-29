package com.ashleyfigueira.stackoverflow.di

import android.app.Activity
import com.ashleyfigueira.stackoverflow.LaunchActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityProvider {

    @ContributesAndroidInjector(modules = [
        FragmentProvider::class,
        ViewModelProvider::class,
        LaunchActivityModule::class
    ])
    abstract fun provideLaunchActivity(): LaunchActivity
}

@Module
abstract class LaunchActivityModule {
    @Binds
    abstract fun bindActivity(activity: LaunchActivity): Activity
}