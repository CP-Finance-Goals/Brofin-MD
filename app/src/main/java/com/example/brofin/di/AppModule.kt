package com.example.brofin.di

import android.content.Context
import com.example.brofin.data.local.room.dao.BudgetingDao
import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.PredictDao
import com.example.brofin.data.local.room.dao.UserBalanceDao
import com.example.brofin.data.local.room.dao.UserProfileDao
import com.example.brofin.data.repository.BrofinRepositoryImpl
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.utils.ConnectivityObserver
import com.example.brofin.utils.ConnectivityObserverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver {
        return ConnectivityObserverImpl(context)
    }

    @Provides
    @Singleton
    fun provideBrofinRepository(
        userBalanceDao: UserBalanceDao,
        budgetingDiaryDao: BudgetingDiaryDao,
        predictDao: PredictDao,
        budgetingDao: BudgetingDao,
        userProfileDao: UserProfileDao
    ): BrofinRepository {
        return BrofinRepositoryImpl(userBalanceDao, predictDao, budgetingDiaryDao, budgetingDao, userProfileDao)
    }

}
