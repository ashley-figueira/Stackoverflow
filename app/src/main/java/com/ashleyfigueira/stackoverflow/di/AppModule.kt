package com.ashleyfigueira.stackoverflow.di

import android.content.Context
import com.ashleyfigueira.domain.common.SchedulerProvider
import com.ashleyfigueira.domain.di.ApplicationContext
import com.ashleyfigueira.domain.di.PerApplication
import com.ashleyfigueira.stackoverflow.StackApplication
import com.ashleyfigueira.stackoverflow.common.SchedulerProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun provideContext(application: StackApplication): Context

    @Binds
    @PerApplication
    abstract fun schedulerProvider(schedulerProviderImpl: SchedulerProviderImpl) : SchedulerProvider
}