package com.example.brofin.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "financial_goals")
data class FinancialGoalsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,                     // ID unik untuk membedakan setiap goal
    val userId: String,                 // ID pengguna yang terkait dengan goal
    val name: String,                // Nama tujuan keuangan (misalnya, "Beli Laptop")
    val description: String?,        // Deskripsi tambahan tentang tujuan (opsional)
    val targetAmount: Double,        // Jumlah dana yang dibut  uhkan (target)
    val savedAmount: Double,         // Jumlah dana yang sudah terkumpul
    val installment: Double,         // Besaran cicilan per bulan (atau periode lainnya)
    val photoUrl: String?,           // URL gambar atau foto terkait dengan goal (opsional)
    val deadline: Long?,           // Deadline atau tenggat waktu untuk mencapai tujuan
    val createdAt: Long,           // Tanggal ketika goal dibuat
    val updatedAt: Long?,          // Tanggal terakhir kali goal diperbarui
    val prediction: String?,         // Prediksi kapan tujuan akan tercapai
    val isCompleted: Boolean,         // Status apakah goal sudah tercapai
    val invalid: Boolean? = false             // Status apakah goal sudah tidak valid
)
