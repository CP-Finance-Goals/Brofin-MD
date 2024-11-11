package com.example.brofin.data.repository

import com.example.brofin.domain.models.doc.BudgetingDiaryDoc
import com.example.brofin.domain.models.doc.FinancialGoalsDoc
import com.example.brofin.domain.models.doc.UserBalanceDoc
import com.example.brofin.domain.repository.FirestoreRepository
import com.example.brofin.utils.CollectionFirestore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirestoreRepositoryImpl(
   firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : FirestoreRepository {

    private val goalsCollection = firestore.collection(CollectionFirestore.FINANCIAL_GOALS)
    private val usersCollection = firestore.collection(CollectionFirestore.USERS)
    private val budgetingCollection = firestore.collection(CollectionFirestore.BUDGETING_DIARY)

    // Financial Goals
    override suspend fun saveFinancialGoal(goal: FinancialGoalsDoc) {
        goalsCollection.document(goal.goalId).set(goal).await()
    }

    override fun getFinancialGoalsList(userId: String): Flow<List<FinancialGoalsDoc>> {
       return flow {
           val snapshot = goalsCollection.whereEqualTo("userId", userId).get().await()
           val goals = snapshot.documents.mapNotNull {
               it.toObject(FinancialGoalsDoc::class.java)
           }
           emit(goals)
       }
    }

    override suspend fun updateFinancialGoal(goal: FinancialGoalsDoc) {
        goalsCollection.document(goal.goalId).set(goal).await()
    }

    override suspend fun deleteFinancialGoal(goalId: String) {
        goalsCollection.document(goalId).delete().await()
    }

    override suspend fun deleteAllFinancialGoals(userId: String) {
        val snapshot = goalsCollection.whereEqualTo("userId", userId).get().await()
        snapshot.documents.forEach {
            goalsCollection.document(it.id).delete().await()
        }
    }



    // Budgeting Diary
    override suspend fun saveBudgetingDiary(diary: BudgetingDiaryDoc) {
        budgetingCollection.document(diary.entryId).set(diary).await()
    }

    override fun getBudgetingDiaryList(userId: String): Flow<List<BudgetingDiaryDoc>> {
        return flow {
            val snapshot = budgetingCollection.whereEqualTo("userId", userId).get().await()
            val entries = snapshot.documents.mapNotNull {
                it.toObject(BudgetingDiaryDoc::class.java)
            }
            emit(entries)
        }
    }

    override suspend fun updateBudgetingDiary(entry: BudgetingDiaryDoc) {
        budgetingCollection.document(entry.entryId).set(entry).await()
    }

    override suspend fun deleteBudgetingDiary(entryId: String) {
        budgetingCollection.document(entryId).delete().await()
    }

    override suspend fun deleteAllBudgetingDiary(userId: String) {
        val snapshot = budgetingCollection.whereEqualTo("userId", userId).get().await()
        snapshot.documents.forEach {
            budgetingCollection.document(it.id).delete().await()
        }
    }



    // User
    override suspend fun saveUser(user: UserBalanceDoc): Boolean {
        return try {
            usersCollection.document(user.userId).set(user).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun userExists(userId: String): Boolean {
        val snapshot = usersCollection.document(userId).get().await()
        return snapshot.exists()
    }

    override fun getUser(userId: String): Flow<UserBalanceDoc?> {
        return flow {
            val snapshot = usersCollection.document(userId).get().await()
            val user = snapshot.toObject(UserBalanceDoc::class.java)
            emit(user)
        }
    }

    override suspend fun updateUser(user: UserBalanceDoc) {
        usersCollection.document(user.userId).set(user).await()
    }

    override suspend fun deleteUser(userId: String) {
        usersCollection.document(userId).delete().await()
    }

}