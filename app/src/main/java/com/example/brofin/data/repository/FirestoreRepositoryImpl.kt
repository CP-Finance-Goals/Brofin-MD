package com.example.brofin.data.repository


import com.example.brofin.domain.models.Budgeting
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.domain.models.UserBalance
import com.example.brofin.domain.repository.FirestoreRepository
import com.example.brofin.utils.CollectionFirestore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepositoryImpl(
   firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : FirestoreRepository {

    private val goalsCollection = firestore.collection(CollectionFirestore.FINANCIAL_GOALS)
    private val usersCollection = firestore.collection(CollectionFirestore.USERS_BALANCE)
    private val budgetingCollection = firestore.collection(CollectionFirestore.BUDGETING)
    private val budgetingDiaryCollection = firestore.collection(CollectionFirestore.BUDGETING_DIARY)

    override suspend fun addBudgetingDiary(diary: BudgetingDiary): Result<Void?> {
        return try {
            budgetingDiaryCollection.add(diary).await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBudgetingDiaries(
        userId: String,
        monthAndYear: Long
    ): Result<List<BudgetingDiary>> {
        return try {
            val snapshot = budgetingDiaryCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("monthAndYear", monthAndYear)
                .get()
                .await()

            val diaries = snapshot.toObjects(BudgetingDiary::class.java)
            Result.success(diaries)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateBudgetingDiary(
        id: Int,
        updates: Map<String, Any>
    ): Result<Void?> {
        return try {
            budgetingDiaryCollection.document(id.toString())
                .update(updates)
                .await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteBudgetingDiary(id: Int): Result<Void?> {
        return try {
            budgetingDiaryCollection.document(id.toString())
                .delete()
                .await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addUserBalance(userBalance: UserBalance): Result<Void?> {
        return try {
            usersCollection.add(userBalance).await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserBalance(
        userId: String,
        monthAndYear: Long
    ): Result<UserBalance?> {
        return try {
            val snapshot = usersCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("monthAndYear", monthAndYear)
                .get()
                .await()

            val balance = snapshot.toObjects(UserBalance::class.java).firstOrNull()
            Result.success(balance)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUserBalance(
        userId: String,
        monthAndYear: Long,
        updates: Map<String, Any>
    ): Result<Void?> {
        return try {
            usersCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("monthAndYear", monthAndYear)
                .get()
                .await()
                .documents
                .firstOrNull()
                ?.reference
                ?.update(updates)
                ?.await()

            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUserBalance(
        userId: String,
        monthAndYear: Long
    ): Result<Void?> {
        return try {
            usersCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("monthAndYear", monthAndYear)
                .get()
                .await()
                .documents
                .firstOrNull()
                ?.reference
                ?.delete()
                ?.await()

            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun addBudgeting(budget: Budgeting): Result<Void?> {
        return try {
            budgetingCollection.add(budget).await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBudgeting(
        userId: String,
        monthAndYear: Long
    ): Result<Budgeting?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateBudgeting(
        monthAndYear: Long,
        userId: String,
        updates: Map<String, Any>
    ): Result<Void?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBudgeting(
        monthAndYear: Long,
        userId: String
    ): Result<Void?> {
        TODO("Not yet implemented")
    }


}