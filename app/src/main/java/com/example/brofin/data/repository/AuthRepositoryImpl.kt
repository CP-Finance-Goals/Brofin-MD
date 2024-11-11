package com.example.brofin.data.repository

import com.example.brofin.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl (
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override fun signInWithEmail(
        email: String,
        password: String
    ): Flow<Boolean> {
        return flow {
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(result.user != null)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(false)
            }
        }
    }

    override fun registerWithEmail(
        email: String,
        password: String,
    ): Flow<Boolean> {
        return flow {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                emit(true)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(false)
            }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun userExists(): Flow<Boolean> {
        return flow {
            emit(firebaseAuth.currentUser != null)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}
