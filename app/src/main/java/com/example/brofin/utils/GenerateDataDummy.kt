package com.example.brofin.utils

import com.example.brofin.domain.models.doc.BudgetingDiaryDoc
import com.example.brofin.domain.models.doc.FinancialGoalsDoc
import com.example.brofin.domain.models.doc.UserBalanceDoc
import java.util.UUID

object GenerateDataDummy {


    // Fungsi untuk membuat daftar dummy BudgetingDiary untuk satu user
    fun generateBudgetingDiaryDummy(userId: String): BudgetingDiaryDoc{
        return  BudgetingDiaryDoc(
            entryId = 1.toString(),
            userId = userId,
            date = System.currentTimeMillis() - 86400000,
            description = "Lunch",
            amount = 15.0,
            isExpense = true
        )
    }


    fun generateFinancialGoalsDummy(userId: String): FinancialGoalsDoc {
        return FinancialGoalsDoc(
            goalId = 1.toString(),
            userId = userId,
            goalName = "Emergency Fund",
            targetAmount = 5000.0,
            currentAmount = 1000.0,
            deadline = System.currentTimeMillis() + 2592000000,
            createdAt = System.currentTimeMillis()
        )
    }
}
