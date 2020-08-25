package abdulmanov.eduard.news.presentation.livestream.helper

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

abstract class VideoActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var hideRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler = Handler(Looper.getMainLooper())
        hideRunnable = Runnable { hideSystemUi() }

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT

            decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                val isLandscape = (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                if (isLandscape && (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0)) {
                    handler.postDelayed(hideRunnable, DELAY_HIDE_SYSTEM_UI)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        editSystemUiAccordingOrientation(resources.configuration.orientation)

        val rootView = findViewById<ViewGroup>(android.R.id.content).rootView
        rootView.setOnApplyWindowInsetsListener { _, insets ->
            val isPortrait = (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            val insertTop = if (isPortrait) insets.systemWindowInsetTop else 0
            handlerWindowInsertTop(insertTop)
            insets
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(hideRunnable)
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        editSystemUiAccordingOrientation(newConfig.orientation)
    }

    private fun editSystemUiAccordingOrientation(orientation: Int) {
        handler.removeCallbacks(hideRunnable)

        handlerVisibilityMenuItems(orientation == Configuration.ORIENTATION_PORTRAIT)

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUi()
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            showSystemUi()
        }
    }

    private fun hideSystemUi() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN

            or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    private fun showSystemUi() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    abstract fun handlerWindowInsertTop(insertTop: Int)

    abstract fun handlerVisibilityMenuItems(visibility: Boolean)

    companion object {
        private const val DELAY_HIDE_SYSTEM_UI = 3000L
    }
}