package abdulmanov.eduard.news.presentation._common.extensions

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment

fun Fragment.setAppTheme(type: Int) {
    val activity = requireActivity() as AppCompatActivity
    activity.setAppTheme(type)
}

fun AppCompatActivity.setAppTheme(type: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        delegate.localNightMode = type
    } else {
        AppCompatDelegate.setDefaultNightMode(type)
    }
}