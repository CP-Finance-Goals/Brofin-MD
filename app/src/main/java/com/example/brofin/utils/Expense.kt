package com.example.brofin.utils

data class CategoryExpenses(
    val id: Int, // ID unik untuk kategori
    val namaKategori: String, // Nama kategori pengeluaran, misalnya "Makanan", "Transportasi"
    val deskripsi: String, // Deskripsi singkat tentang kategori
    val ikon: String, // Nama ikon atau URL ikon untuk mewakili kategori (opsional)
    val warna: String // Kode warna untuk tampilan kategori (misalnya, "#FF5733" untuk warna merah)
)

object Expense {
    val categoryExpensesLists = listOf(
        CategoryExpenses(
            id = 1,
            namaKategori = "Makanan",
            deskripsi = "Pengeluaran untuk makanan dan minuman sehari-hari",
            ikon = "icon_makanan",
            warna = "#FF5733"
        ),
        CategoryExpenses(
            id = 2,
            namaKategori = "Transportasi",
            deskripsi = "Pengeluaran untuk biaya transportasi seperti bensin, tiket, dan parkir",
            ikon = "icon_transportasi",
            warna = "#33AFFF"
        ),
        CategoryExpenses(
            id = 3,
            namaKategori = "Hiburan",
            deskripsi = "Pengeluaran untuk aktivitas hiburan, seperti nonton, rekreasi, dan hobi",
            ikon = "icon_hiburan",
            warna = "#FFD700"
        ),
        CategoryExpenses(
            id = 4,
            namaKategori = "Kesehatan",
            deskripsi = "Pengeluaran untuk biaya kesehatan seperti obat, cek kesehatan, dan asuransi",
            ikon = "icon_kesehatan",
            warna = "#4CAF50"
        ),
        CategoryExpenses(
            id = 5,
            namaKategori = "Pendidikan",
            deskripsi = "Pengeluaran untuk biaya pendidikan, buku, dan kursus",
            ikon = "icon_pendidikan",
            warna = "#FFA500"
        ),
        CategoryExpenses(
            id = 6,
            namaKategori = "Pakaian",
            deskripsi = "Pengeluaran untuk kebutuhan pakaian dan aksesoris",
            ikon = "icon_pakaian",
            warna = "#8A2BE2"
        ),
        CategoryExpenses(
            id = 7,
            namaKategori = "Tagihan dan Utilitas",
            deskripsi = "Pengeluaran untuk tagihan bulanan seperti listrik, air, dan internet",
            ikon = "icon_tagihan",
            warna = "#FF4500"
        ),
        CategoryExpenses(
            id = 8,
            namaKategori = "Donasi dan Zakat",
            deskripsi = "Pengeluaran untuk donasi, zakat, atau sumbangan lainnya",
            ikon = "icon_donasi",
            warna = "#B22222"
        ),
        CategoryExpenses(
            id = 9,
            namaKategori = "Investasi",
            deskripsi = "Pengeluaran untuk investasi seperti reksa dana, saham, atau deposito",
            ikon = "icon_investasi",
            warna = "#228B22"
        ),
        CategoryExpenses(
            id = 10,
            namaKategori = "Lain-lain",
            deskripsi = "Pengeluaran untuk hal-hal di luar kategori utama",
            ikon = "icon_lain_lain",
            warna = "#A9A9A9"
        )
    )

}