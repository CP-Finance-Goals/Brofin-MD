package com.example.brofin.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.brofin.data.local.room.dao.BudgetingDiaryDao
import com.example.brofin.data.local.room.dao.FinancialGoalsDao
import com.example.brofin.data.local.room.dao.UserBalanceDao
import com.example.brofin.data.local.room.entity.BudgetingDiaryEntity
import com.example.brofin.data.local.room.entity.FinancialGoalsEntity
import com.example.brofin.data.local.room.entity.UserBalanceEntity


@Database(
    entities = [
        UserBalanceEntity::class,
        FinancialGoalsEntity::class,
        BudgetingDiaryEntity::class],
    version = 1,
    exportSchema = false
)

abstract class BrofinDatabase: RoomDatabase() {
    abstract fun userDao(): UserBalanceDao
    abstract fun financialGoalsDao(): FinancialGoalsDao
    abstract fun budgetingDiaryDao(): BudgetingDiaryDao
}