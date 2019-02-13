package eu.depau.kotlet.android.extensions.uri

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

/**
 * Obtain the display name for a file.
 *
 * @param context Any valid context.
 */
fun Uri.getFileName(context: Context): String? {
    var result: String? = null

    if (this.scheme == "content") {
        val cursor = context.contentResolver.query(this, null, null, null, null)

        cursor.use {
            if (it != null && it.moveToFirst()) {
                result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }

    if (result == null) {
        result = this.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result!!.substring(cut + 1)
        }
    }

    return result
}