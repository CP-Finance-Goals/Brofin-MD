package com.example.brofin.di

import com.example.brofin.data.local.room.dao.BudgetingDao
import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.FinancialGoalsDao
import com.example.brofin.data.local.room.dao.UserBalanceDao
import com.example.brofin.data.repository.BrofinRepositoryImpl
import com.example.brofin.domain.repository.BrofinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBrofinRepository(
        userBalanceDao: UserBalanceDao,
        budgetingDiaryDao: BudgetingDiaryDao,
        financialGoalsDao: FinancialGoalsDao,
        budgetingDao: BudgetingDao
    ): BrofinRepository {
        return BrofinRepositoryImpl(userBalanceDao, financialGoalsDao, budgetingDiaryDao, budgetingDao)
    }

}
