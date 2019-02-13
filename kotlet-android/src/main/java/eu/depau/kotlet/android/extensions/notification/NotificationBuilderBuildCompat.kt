package eu.depau.kotlet.android.extensions.notification

import android.app.Notification
import android.os.Build

@Suppress("DEPRECATION")
fun Notification.Builder.buildCompat(): Notification {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        this.build()
    } else {
        this.notification
    }
}