package abdulmanov.eduard.news.presentation.livestream.helper

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class DisappearingConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var hideRunnable:Runnable
    private var isShow = true

    init {
        hideRunnable = Runnable {
            show(false)
            isShow = false
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setOnClickListener {
            isShow = !isShow
            show(isShow)

            handler.removeCallbacks(hideRunnable)
            if(isShow)
                handler.postDelayed(hideRunnable, DELAY_BEFORE_DISAPPEARING)
        }
        hide()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler.removeCallbacks(hideRunnable)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return !isShow
    }

    private fun hide(){
        handler.removeCallbacks(hideRunnable)
        handler.postDelayed(hideRunnable, DELAY_BEFORE_DISAPPEARING)
    }

    private fun show(show:Boolean){
        val alpha = if(show) ALPHA_VISIBLE else ALPHA_INVISIBLE

        animate()
            .alpha(alpha)
            .setDuration(ANIMATE_DURATION)
            .setInterpolator(FastOutSlowInInterpolator())
            .start()

    }

    companion object{
        private const val ALPHA_VISIBLE = 1f
        private const val ALPHA_INVISIBLE = 0f
        private const val ANIMATE_DURATION = 300L
        private const val DELAY_BEFORE_DISAPPEARING = 3000L
    }
}