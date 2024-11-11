package com.example.brofin.utils

import com.example.brofin.domain.models.BudgetingDiary
import kotlin.random.Random

fun generateSampleBudgetingData(userId: String, itemCount: Int): List<BudgetingDiary> {
    val sampleData = mutableListOf<BudgetingDiary>()
    val descriptions = listOf("Salary", "Groceries", "Rent", "Transport", "Dining Out", "Utilities", "Entertainment", "Shopping", "Bonus", "Freelance")

    repeat(itemCount) { index ->
        // Mengacak tanggal dalam 90 hari terakhir dengan variasi unik
        val currentDate = System.currentTimeMillis()
        val randomDateOffset = (index + 1) * Random.nextLong(1L, 90L * 24 * 60 * 60 * 1000 / itemCount)
        val randomDate = currentDate - randomDateOffset // Tanggal acak

        // Mengacak deskripsi dari daftar yang ada
        val description = descriptions.random()

        // Mengacak jumlah antara 100 dan 5000 untuk variasi nilai transaksi
        val amount = Random.nextDouble(100.0, 5000.0)

        // Mengacak tipe transaksi (true untuk pengeluaran, false untuk pemasukan)
        val isExpense = Random.nextBoolean()

        // Menambahkan data ke daftar
        sampleData.add(
            BudgetingDiary(
                id = index,
                userId = userId,
                date = randomDate,
                description = description,
                amount = amount,
                isExpense = isExpense
            )
        )
    }

    return sampleData
}
