package eu.depau.kotlet.android.extensions.ui.view

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Quickly show a Snackbar from any View.
 * @param message The message to show
 * @param duration (optional) The toast duration (defaults to `Snackbar.LENGTH_LONG`)
 */
fun View.snackbar(message: CharSequence, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}