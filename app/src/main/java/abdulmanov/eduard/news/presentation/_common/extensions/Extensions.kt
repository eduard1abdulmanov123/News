package abdulmanov.eduard.news.presentation._common.extensions

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
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

fun Context.getScreenSize(): Point {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return Point().apply {
        wm.defaultDisplay.getSize(this)
    }
}

fun Fragment.addOnBackPressedCallback(handlerOnBackPressed: () -> Unit){
    requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handlerOnBackPressed.invoke()
        }
    })
}
