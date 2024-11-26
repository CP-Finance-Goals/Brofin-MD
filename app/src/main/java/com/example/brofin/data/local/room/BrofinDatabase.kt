package com.example.brofin.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.brofin.data.local.room.dao.BudgetingDao
import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.FinancialGoalsDao
import com.example.brofin.data.local.room.dao.UserBalanceDao
import com.example.brofin.data.local.room.dao.UserProfileDao
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.BudgetingEntity
import com.example.brofin.data.local.room.entity.FinancialGoalsEntity
import com.example.brofin.data.local.room.entity.UserBalanceEntity
import com.example.brofin.data.local.room.entity.UserProfileEntity

@Database(
    entities = [
        UserBalanceEntity::class,
        FinancialGoalsEntity::class,
        BudgetingDiaryEntity::class,
        BudgetingEntity::class,
        UserProfileEntity::class],
    version = 2,
    exportSchema = false
)

abstract class BrofinDatabase: RoomDatabase() {
    abstract fun userDao(): UserBalanceDao
    abstract fun financialGoalsDao(): FinancialGoalsDao
    abstract fun budgetingDiaryDao(): BudgetingDiaryDao
    abstract fun budgetingDao(): BudgetingDao
    abstract fun userProfileDao(): UserProfileDao
}