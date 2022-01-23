package com.github.amrmsaraya.expirytracker.utils

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.WindowInsetsController

fun Activity.fullScreenMode(state: Boolean) {
    when (state) {
        true -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController?.hide(
                    android.view.WindowInsets.Type.statusBars()
                            or android.view.WindowInsets.Type.navigationBars()
                )
                window.decorView.windowInsetsController?.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            }
        }
        false -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController?.show(
                    android.view.WindowInsets.Type.statusBars()
                            or android.view.WindowInsets.Type.navigationBars()
                )
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN and
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION and
                        View.SYSTEM_UI_FLAG_IMMERSIVE and
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE and
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN and
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                if (resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK != Configuration.UI_MODE_NIGHT_YES
                ) {
                    window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
    }
}