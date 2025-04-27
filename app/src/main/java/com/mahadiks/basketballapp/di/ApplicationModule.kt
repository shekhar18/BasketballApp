package com.mahadiks.basketballapp.di

import android.content.Context
import com.mahadiks.basketballapp.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun repositoryProvider(@ApplicationContext context: Context) = ScheduleRepository(context)

}