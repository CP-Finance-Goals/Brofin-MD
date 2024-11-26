package com.example.brofin.utils


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ColorSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Color", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeLong(value.value.toLong())
    }

    override fun deserialize(decoder: Decoder): Color {
        return Color(decoder.decodeLong())
    }
}


@Serializable
data class CategoryExpenses(
    val id: Int, // ID unik untuk kategori
    val namaKategori: String, // Nama kategori pengeluaran, misalnya "Makanan", "Transportasi"
    val deskripsi: String, // Deskripsi singkat tentang kategori
    val ikon: String, // Nama ikon atau URL ikon untuk mewakili kategori (opsional)
    val warna: String, // Kode warna untuk tampilan kategori (misalnya, "#FF5733" untuk warna merah)
    val budgetUsed: Double = 0.0 // Budget yang sudah digunakan (dalam Rupiah)
)

object Expense {
    val categoryExpensesLists = listOf(
        CategoryExpenses(
            id = 1,
            namaKategori = "Makanan",
            deskripsi = "Pengeluaran untuk makanan dan minuman sehari-hari",
            ikon = "",
            budgetUsed = 0.0,
            warna = "#FF5733"
        ),
        CategoryExpenses(
            id = 2,
            namaKategori = "Transportasi",
            deskripsi = "Pengeluaran untuk biaya transportasi seperti bensin, tiket, dan parkir",
            ikon = "Commute",
            budgetUsed = 0.0,
            warna = "#33AFFF"
        ),
        CategoryExpenses(
            id = 3,
            namaKategori = "Hiburan",
            deskripsi = "Pengeluaran untuk aktivitas hiburan, seperti nonton, rekreasi, dan hobi",
            ikon = "Movie",
            budgetUsed = 0.0,
            warna = "#4CAF50"
        ),
        CategoryExpenses(
            id = 4,
            namaKategori = "Kesehatan",
            deskripsi = "Pengeluaran untuk biaya kesehatan seperti obat, cek kesehatan, dan asuransi",
            ikon = "HealthAndSafety",
            budgetUsed = 0.0,
            warna = "#4CAF50"
        ),
        CategoryExpenses(
            id = 5,
            namaKategori = "Pendidikan",
            deskripsi = "Pengeluaran untuk biaya pendidikan, buku, dan kursus",
            ikon = "School",
            budgetUsed = 0.0,
            warna = "#FFA500"
        ),
        CategoryExpenses(
            id = 6,
            namaKategori = "Pakaian",
            deskripsi = "Pengeluaran untuk kebutuhan pakaian dan aksesoris",
            ikon = "",
            budgetUsed = 0.0,
            warna = "#8A2BE2"
        ),
        CategoryExpenses(
            id = 7,
            namaKategori = "Tagihan dan Utilitas",
            deskripsi = "Pengeluaran untuk tagihan bulanan seperti listrik, air, dan internet",
            ikon = "",
            budgetUsed = 0.0,
            warna = "#FF4500"
        ),
        CategoryExpenses(
            id = 8,
            namaKategori = "Belanja",
            deskripsi = "Pengeluaran untuk belanja kebutuhan pokok",
            ikon = "ShoppingCart",
            budgetUsed = 0.0,
            warna = "#FF4081"
        ),
        CategoryExpenses(
            id = 9,
            namaKategori = "Lain-lain",
            deskripsi = "Pengeluaran untuk hal-hal di luar kategori utama",
            ikon = "",
            budgetUsed = 0.0,
            warna = "#A9A9A9"
        )

    )

    fun getNameById(id: Int): String? {
        return categoryExpensesLists.find{
            it.id == id
        }?.namaKategori
    }


    // Fungsi untuk memetakan string ikon menjadi ImageVector
    fun getIconByName(name: String): ImageVector {
        return when (name) {
            "ShoppingCart" -> Icons.Default.ShoppingCart
            "Commute" -> Icons.Default.Commute
            "HealthAndSafety" -> Icons.Default.HealthAndSafety
            "Restaurant" -> Icons.Default.Restaurant
            "LocalDrink" -> Icons.Default.LocalDrink
            "School" -> Icons.Default.School
            else -> Icons.Default.Help
        }
    }

        val budgetAllocations = listOf(
            BudgetAllocation(
                id = 1,
                namaAlokasi = "Kebutuhan Pokok",
                deskripsi = "Alokasi 50% untuk kebutuhan dasar",
                persentase = 50,
                kategori = listOf(
                    categoryExpensesLists[0], // Makanan
                    categoryExpensesLists[1], // Transportasi
                    categoryExpensesLists[3], // Kesehatan
                    categoryExpensesLists[4], // Pendidikan
                    categoryExpensesLists[6], // Tagihan dan Utilitas
                ),
                warna = Color(0xFF4CAF50), // Hijau untuk Kebutuhan Pokok
            ),
            BudgetAllocation(
                id = 2,
                namaAlokasi = "Keinginan",
                deskripsi = "Alokasi 30% untuk hiburan",
                persentase = 30,
                kategori = listOf(
                    categoryExpensesLists[2], // Hiburan
                    categoryExpensesLists[5], // Pakaian
                    categoryExpensesLists[8], // Lain-lain
                ),
                warna = Color(0xFFFFC107), // Kuning untuk Keinginan

            ),
            BudgetAllocation(
                id = 3,
                namaAlokasi = "Tabungan",
                deskripsi = "Alokasi 20% untuk tabungan atau dana darurat",
                persentase = 20,
                kategori = emptyList(),
                warna = Color(0xFF2196F3), // Biru untuk Tabungan
            )
        )

}


@Serializable
data class BudgetAllocation(
    val id: Int, // ID unik untuk kategori utama
    val namaAlokasi: String, // Nama alokasi, misalnya "Kebutuhan Pokok"
    val deskripsi: String, // Deskripsi singkat tentang alokasi
    val persentase: Int, // Persentase dari total anggaran
    @Serializable(with = ColorSerializer::class)
    val warna: Color, // Warna unik untuk alokasi
    val kategori: List<CategoryExpenses>, // Daftar kategori yang termasuk dalam alokasi ini
)