package abdulmanov.eduard.news.presentation._common.base

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation._common.extensions.loadImg
import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_custom_video_view.view.*

class CustomVideoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :FrameLayout(context, attrs, defStyleAttr), MediaPlayer.OnPreparedListener {

    private var pause: Boolean = false
    private var isPrepared: Boolean = false

    init {
        inflate(context, R.layout.layout_custom_video_view, this)
        videoView.setOnPreparedListener(this as MediaPlayer.OnPreparedListener)

        controlImageView.setOnClickListener {
            pause = if(pause){
                controlImageView.setImageResource(R.drawable.ic_pause)
                videoView.start()
                false
            }else{
                controlImageView.setImageResource(R.drawable.ic_play)
                videoView.pause()
                true
            }
        }

        rootView.setOnClickListener {
            if(containerWithControlButtons.visibility == View.VISIBLE && isPrepared){
                containerWithControlButtons.visibility = View.GONE
            }else if(isPrepared){
                containerWithControlButtons.visibility = View.VISIBLE
            }
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        isPrepared = true
        containerWithControlButtons.visibility = View.VISIBLE
        thumbnailImageView.visibility = View.INVISIBLE
        videoView.start()
    }

    fun setVideoPath(title:String,thumbnail: String, videoUrl: String){
        containerWithControlButtons.visibility = View.GONE
        thumbnailImageView.visibility = View.VISIBLE
        thumbnailImageView.loadImg(thumbnail)
        titleTextView.text = title
        videoView.setVideoPath(videoUrl)
    }

    fun setCollapseClickListener(click:(View)->Unit){
        collapseImageView.setOnClickListener(click)
    }
}