package eu.depau.kotlet.android.extensions.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

/**
 * Wrapper around `NotificationManager.createNotificationChannel`
 *
 * - On Android versions that use notification channels:
 *   creates a NotificationChannel using the given parameters and adds registers
 *   it to the NotificationManager.
 * - On Android version that don't:
 *   simply does nothing and returns `channelId`.
 *
 * Especially useful when used in combination with {@link Notification.Builder.buildCompat}
 * and {@link Context.getNotificationBuilder}.
 *
 * @param channelId The id of the channel. Must be unique per package. The value may be truncated if it is too long.
 * @param name The user visible name of the channel. You can rename this channel when the system locale changes by listening for the Intent.ACTION_LOCALE_CHANGED broadcast. The recommended maximum length is 40 characters; the value may be truncated if it is too long.
 * @param description (optional) The user visible description of this channel.
 * @param importance (optional) The importance of the channel. This controls how interruptive notifications posted to this channel are. Defaults to `NotificationManager.IMPORTANCE_DEFAULT` if null or not provided.
 */

fun NotificationManager.registerNotificationChannel(
    channelId: String,
    name: String,
    description: String?,
    importance: NotificationImportanceCompat = NotificationImportanceCompat.IMPORTANCE_DEFAULT
): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel(
                channelId,
                name,
                importance.androidImportance
            ).also {
                if (description != null)
                    it.description = description
            }

        this.createNotificationChannel(channel)
    }
    return channelId
}

/**
 * Delete a NotificationChannel by ID on platforms that support notification channels.
 * On old Android versions this does nothing.
 */
fun NotificationManager.deleteNotificationChannelCompat(channelId: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        deleteNotificationChannel(channelId)
}