package com.example.brofin.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    /**
     * Melakukan signin dengan email dan password.
     *
     * @param email Email pengguna.
     * @param password Password pengguna.
     * @return Flow<Boolean> yang mengindikasikan status signin berhasil atau gagal.
     */

    fun signInWithEmail(email: String, password: String): Flow<Boolean>

    /**
     * Melakukan registrasi dengan email, password, dan nama.
     *
     * @param email Email pengguna.
     * @param password Password pengguna.
     * @param name Nama pengguna.
     * @return Flow<Boolean> yang mengindikasikan status registrasi berhasil atau gagal.
     */

    fun registerWithEmail(name: String, email: String, password: String): Flow<Boolean>


    fun getCurrentUser(): FirebaseUser?

    fun userExists(): Flow<Boolean>

    suspend fun logout()

}
