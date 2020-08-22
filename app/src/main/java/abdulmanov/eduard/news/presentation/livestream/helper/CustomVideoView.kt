package abdulmanov.eduard.news.presentation.livestream.helper

import abdulmanov.eduard.news.R
import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_custom_video_view.view.*

class CustomVideoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private var isPause: Boolean = true

    private var onCallbackError: (() -> Unit)? = null
    private var onCallbackPrepared: (() -> Unit)? = null
    private var onCallbackVideoState: ((isPause:Boolean) -> Unit)? = null

    init {
        inflate(context, R.layout.layout_custom_video_view, this)
        videoView.setOnPreparedListener(this)
        videoView.setOnErrorListener(this)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.setOnInfoListener { _, what, _ ->
            if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                isPause = false
                onCallbackVideoState?.invoke(isPause)
                onCallbackPrepared?.invoke()
                return@setOnInfoListener true
            }

            return@setOnInfoListener false
        }

        videoView.start()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        onCallbackError?.invoke()
        return false
    }

    fun setVideoPath(url: String){
        videoView.setVideoPath(url)
    }

    fun toggleVideoState(){
        if(isPause){
            videoView.start()
        }else{
            videoView.pause()
        }
        isPause = !isPause

        onCallbackVideoState?.invoke(isPause)
    }

    fun setOnCallbackError(onCallbackError: ()->Unit){
        this.onCallbackError = onCallbackError
    }

    fun setOnPreparedListener(onCallbackPrepared: ()->Unit) {
        this.onCallbackPrepared = onCallbackPrepared
    }

    fun setOnCallbackVideoState(onCallbackVideoState:(isPause:Boolean) -> Unit){
        this.onCallbackVideoState = onCallbackVideoState
    }
}