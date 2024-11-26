package com.example.brofin.di

import android.content.Context
import androidx.room.Room
import com.example.brofin.data.local.room.BrofinDatabase
import com.example.brofin.data.local.room.dao.BudgetingDao
import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.FinancialGoalsDao
import com.example.brofin.data.local.room.dao.UserBalanceDao
import com.example.brofin.data.local.room.dao.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideStoryDatabase(@ApplicationContext context: Context): BrofinDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BrofinDatabase::class.java,
            "brofin_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(storyDatabase: BrofinDatabase): UserBalanceDao = storyDatabase.userDao()

    @Provides
    fun provideBudgetingDiaryDao(storyDatabase: BrofinDatabase): BudgetingDiaryDao = storyDatabase.budgetingDiaryDao()

    @Provides
    fun provideFinancialGoalsDao(storyDatabase: BrofinDatabase): FinancialGoalsDao = storyDatabase.financialGoalsDao()

    @Provides
    fun provideBudgetingDao(storyDatabase: BrofinDatabase): BudgetingDao = storyDatabase.budgetingDao()

    @Provides
    fun provideUserProfileDao(storyDatabase: BrofinDatabase): UserProfileDao = storyDatabase.userProfileDao()


}
