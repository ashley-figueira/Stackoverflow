package com.ashleyfigueira.data.di

import android.content.Context
import androidx.room.Room
import com.ashleyfigueira.data.common.DataConfig
import com.ashleyfigueira.data.common.StackDatabase
import com.ashleyfigueira.data.users.local.UsersDao
import com.ashleyfigueira.data.users.remote.UsersService
import com.ashleyfigueira.domain.di.ApplicationContext
import com.ashleyfigueira.domain.di.PerApplication
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
class DataModule {

    @Provides
    @PerApplication
    fun provideUsersDao(stackDatabase: StackDatabase): UsersDao = stackDatabase.usersDao()

    @PerApplication
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): StackDatabase = Room.databaseBuilder(context, StackDatabase::class.java,
        DataConfig.STACK_DB
    ).build()

    @PerApplication
    @Provides
    fun provideUsersService(retrofit: Retrofit): UsersService = retrofit.create(UsersService::class.java)

    @PerApplication
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(DataConfig.STACK_ENDPOINT)
        .build()

    @PerApplication
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor { message -> Timber.i(message)}.apply { level = HttpLoggingInterceptor.Level.BASIC })
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()
}