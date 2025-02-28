package com.engineer.ai.work

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.engineer.ai.R
import com.engineer.ai.util.ImageLabelHelper
import com.engineer.ai.util.UriManager
import com.engineer.ai.util.createNotification
import java.util.concurrent.TimeUnit


const val WORK_TAG = "scan_url"

class ScanUrlWork(private val appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result {
        readAndParse(appContext)
        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            1, createNotification(
                applicationContext, id, applicationContext.getString(R.string.app_name)
            )
        )
    }
}

fun readAndParse(context: Context) {
    val uris = UriManager.getLocalUri(context)
    if (uris.isNotEmpty()) {
        ImageLabelHelper.getLabel(context, uris)
    }
}

fun createWorkRequest(): PeriodicWorkRequest {
    val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .setRequiresBatteryNotLow(true).build()


    return PeriodicWorkRequestBuilder<ScanUrlWork>(6, TimeUnit.HOURS).setConstraints(constraints)
        .setBackoffCriteria(BackoffPolicy.LINEAR, 30, TimeUnit.SECONDS).addTag(WORK_TAG).build()
}

fun triggerWork(context: Context) {
    val request = createWorkRequest()
    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        WORK_TAG, ExistingPeriodicWorkPolicy.KEEP, request
    )
}
