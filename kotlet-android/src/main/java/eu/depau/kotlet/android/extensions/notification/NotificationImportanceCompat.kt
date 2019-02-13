package eu.depau.kotlet.android.extensions.notification

import android.app.NotificationManager
import android.os.Build

enum class NotificationImportanceCompat(private val internalId: Int) {
    IMPORTANCE_UNSPECIFIED(0),
    IMPORTANCE_NONE(1),
    IMPORTANCE_MIN(2),
    IMPORTANCE_LOW(3),
    IMPORTANCE_DEFAULT(4),
    IMPORTANCE_HIGH(5);

    val androidImportance: Int
        get() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                when (this.internalId) {
                    0 -> NotificationManager.IMPORTANCE_UNSPECIFIED
                    1 -> NotificationManager.IMPORTANCE_NONE
                    2 -> NotificationManager.IMPORTANCE_MIN
                    3 -> NotificationManager.IMPORTANCE_LOW
                    4 -> NotificationManager.IMPORTANCE_DEFAULT
                    5 -> NotificationManager.IMPORTANCE_HIGH
                    else -> -1
                }
            else {
                -1
            }
}

