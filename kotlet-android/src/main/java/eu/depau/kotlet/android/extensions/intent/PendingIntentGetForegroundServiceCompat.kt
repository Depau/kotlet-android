package eu.depau.kotlet.android.extensions.intent

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

fun pendingIntentGetForegroundServiceCompat(context: Context, requestCode: Int, intent: Intent, flags: Int) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        PendingIntent.getForegroundService(context, requestCode, intent, flags)
    } else {
        PendingIntent.getService(context, requestCode, intent, flags)
    }