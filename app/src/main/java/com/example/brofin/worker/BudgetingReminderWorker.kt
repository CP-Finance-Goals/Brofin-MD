package com.example.brofin.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.brofin.data.local.room.dao.BudgetingDao
import com.example.brofin.data.local.room.entity.BudgetingEntity
import com.example.brofin.utils.getCurrentMonthAndYearAsLong
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltWorker
class BudgetingReminderWorker  @AssistedInject  constructor(
    @ApplicationContext private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val budgetingDao: BudgetingDao
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        try {
            Log.d("BudgetingReminderWorker", "Worker started")
            val monthAndYear = getCurrentMonthAndYearAsLong()

            val budgetingData = budgetingDao.getBudgetingByMonth(monthAndYear)
            if (budgetingData == null) {
                Log.e("BudgetingReminderWorker", "No budgeting data found.")
                return Result.failure() // Failure jika data tidak ada
            }

            checkBudgetingConditions(budgetingData)
            Log.d("BudgetingReminderWorker", "Worker finished successfully")
            return Result.success()
        } catch (e: Exception) {
            Log.e("BudgetingReminderWorker", "Error in Worker: ${e.message}", e)
            return Result.failure() // Failure jika ada error
        }
    }


    private fun checkBudgetingConditions(budgetingData: BudgetingEntity) {
        val essentialUsed = budgetingData.total / budgetingData.essentialNeedsLimit
        val wantsUsed = budgetingData.total / budgetingData.wantsLimit
        val savingsUsed = budgetingData.total / budgetingData.savingsLimit

        if (essentialUsed >= 0.5 && essentialUsed < 0.8) {
            showNotification("Reminder", "Essential needs usage has reached 50%.")
        } else if (essentialUsed >= 0.8) {
            showNotification("Critical Alert", "Essential needs usage has reached 80%. Manage your finances wisely!")
        }

        if (wantsUsed >= 0.5 && wantsUsed < 0.8) {
            showNotification("Reminder", "Wants usage has reached 50%.")
        } else if (wantsUsed >= 0.8) {
            showNotification("Critical Alert", "Wants usage has reached 80%. Consider revising your spending.")
        }

        if (savingsUsed >= 0.5 && savingsUsed < 0.8) {
            showNotification("Reminder", "Savings usage has reached 50%.")
        } else if (savingsUsed >= 0.8) {
            showNotification("Critical Alert", "Savings usage has reached 80%. Adjust your financial strategy!")
        }
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "budgeting_reminder_channel"

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Budgeting Reminder Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(appContext, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify((0..10000).random(), notification)
    }
}

