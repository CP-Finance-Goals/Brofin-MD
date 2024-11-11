package com.example.brofin.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {


//    @Provides
//    @Singleton
//    fun provideFirestoreRepository(): FirestoreRepository = FirestoreRepositoryImpl()

}