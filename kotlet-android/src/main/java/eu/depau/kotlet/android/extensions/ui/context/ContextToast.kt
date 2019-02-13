package eu.depau.kotlet.android.extensions.ui.context

import android.content.Context
import android.widget.Toast

/**
 * Quickly show a toast from any Context.
 * @param message The message to show
 * @param duration (optional) The toast duration (defaults to `Toast.LENGTH_LONG`)
 */
fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}