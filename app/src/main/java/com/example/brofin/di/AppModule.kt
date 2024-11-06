package com.example.brofin.di

import com.example.brofin.contract.repository.BrofinRepository
import com.example.brofin.contract.usecase.user.DeleteUserUseCase
import com.example.brofin.contract.usecase.user.GetCurrentBalanceUseCase
import com.example.brofin.contract.usecase.user.GetUserUseCase
import com.example.brofin.contract.usecase.user.InsertUserUseCase
import com.example.brofin.contract.usecase.user.UpdateUserUseCase
import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.FinancialGoalsDao
import com.example.brofin.data.local.room.dao.UserDao
import com.example.brofin.data.repository.BrofinRepositoryImpl
import com.example.brofin.domain.usecase.user.DeleteUserUseCaseImpl
import com.example.brofin.domain.usecase.user.GetCurrentBalanceUseCaseImpl
import com.example.brofin.domain.usecase.user.GetUserUseCaseImpl
import com.example.brofin.domain.usecase.user.InsertUserUseCaseImpl
import com.example.brofin.domain.usecase.user.UpdateUserUseCaseImpl
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
        userDao: UserDao,
        budgetingDiaryDao: BudgetingDiaryDao,
        financialGoalsDao: FinancialGoalsDao
    ): BrofinRepository {
        return BrofinRepositoryImpl(userDao, financialGoalsDao, budgetingDiaryDao)
    }

    // UseCase User
    @Provides
    @Singleton
    fun provideInsertUserUseCase(
        brofinRepository: BrofinRepository
    ): InsertUserUseCase {
        return InsertUserUseCaseImpl(brofinRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(
        brofinRepository: BrofinRepository
    ): UpdateUserUseCase {
        return UpdateUserUseCaseImpl(brofinRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteUserUseCase(
        brofinRepository: BrofinRepository
    ): DeleteUserUseCase {
        return DeleteUserUseCaseImpl(brofinRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(
        brofinRepository: BrofinRepository
    ): GetUserUseCase {
        return GetUserUseCaseImpl(brofinRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentBalanceUseCase(
        brofinRepository: BrofinRepository
    ): GetCurrentBalanceUseCase {
        return GetCurrentBalanceUseCaseImpl(brofinRepository)
    }
}