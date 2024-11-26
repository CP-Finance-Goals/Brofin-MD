package com.example.brofin.utils

import com.example.brofin.data.local.room.entity.FinancialGoalsEntity

val dummyGoals = listOf(
    FinancialGoalsEntity(
        id = 1,
        userId = "user123",
        name = "Beli Laptop",
        description = "Laptop untuk kerja",
        targetAmount = 15000000.0,
        savedAmount = 5000000.0,
        installment = 1000000.0,
        deadline = System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000, // 30 hari dari sekarang
        createdAt = System.currentTimeMillis(),
        updatedAt = null,
        prediction = "Desember 2024",
        isCompleted = false,
        photoUrl = "https://imgs.search.brave.com/vBTu_D6YprBba9O94B1b3kzMAolIpd_-8oMOhyaIdJ4/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly93d3cu/dGhlaG91c2VkZXNp/Z25lcnMuY29tL2lt/YWdlcy9wbGFucy8w/MS9TQ0EvYnVsay85/MzMzLzAxLWxhcXVp/bnRhLWZyb250LWNv/cHlfbS53ZWJw"
    ),
    FinancialGoalsEntity(
        id = 2,
        userId = "user123",
        name = "Liburan ke Bali",
        description = "Liburan keluarga selama 5 hari",
        targetAmount = 20000000.0,
        savedAmount = 12000000.0,
        installment = 2000000.0,
        deadline = System.currentTimeMillis() + 60L * 24 * 60 * 60 * 1000, // 60 hari dari sekarang
        createdAt = System.currentTimeMillis(),
        updatedAt = null,
        prediction = "Februari 2025",
        isCompleted = false,
        photoUrl = "https://imgs.search.brave.com/GYsMVK_E5L5O1EOmr5tVIRloOrvUYvxWq2BU9U9BWho/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5nZXR0eWltYWdl/cy5jb20vaWQvMTQ0/ODE0MjI3NC9waG90/by9tb2NrLXVwLW9m/LWxhcHRvcC5qcGc_/cz02MTJ4NjEyJnc9/MCZrPTIwJmM9N2Vr/R05IX2gwQ0JVUWxv/WlZaNTI0Y3ZJQ1FC/RURPMEU1YWFzOUk2/aXU2ST0"
    )
)
