package com.example.brofin.data.repository

import com.example.brofin.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override fun signInWithEmail(
        email: String,
        password: String
    ): Flow<Boolean> = flow {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(result.user != null)
    }.catch { e ->
        e.printStackTrace()
        emit(false)
    }

    override fun registerWithEmail(
        name: String,
        email: String,
        password: String,
    ): Flow<Boolean> = flow {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        result.user?.updateProfile(profileUpdates)?.await()
        emit(result.user != null)
    }.catch { e ->
        e.printStackTrace()
        emit(false)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun userExists(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }

        firebaseAuth.addAuthStateListener(authStateListener)

        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override fun signInWithGoogle(idToken: String): Flow<Boolean> {
          return flow {
              try {
                  val credential = GoogleAuthProvider.getCredential(idToken, null)
                  firebaseAuth.signInWithCredential(credential).await()
                  emit(true)
              } catch (e: Exception) {
                  emit(false)
              }
          }
    }
}

