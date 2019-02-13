package eu.depau.kotlet.android.extensions.ui.activity

import android.app.Activity

val Activity.statusBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0)
            return resources.getDimensionPixelSize(resourceId)

        return 0
    }

val Activity.navigationBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

        if (resourceId > 0)
            return resources.getDimensionPixelSize(resourceId)

        return 0
    }