package com.ashleyfigueira.stackoverflow.di

import com.ashleyfigueira.data.di.DataModule
import com.ashleyfigueira.data.di.UsersDataModule
import com.ashleyfigueira.domain.di.PerApplication
import com.ashleyfigueira.stackoverflow.StackApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    UsersDataModule::class,
    DataModule::class,
    ActivityProvider::class
])
@PerApplication
interface AppComponent : AndroidInjector<StackApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<StackApplication>()
}