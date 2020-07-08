package abdulmanov.eduard.news.presentation.live

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain.models.Stream
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_live.*
import javax.inject.Inject

class LiveActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LiveViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(LiveViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)
        layoutVideoView.setCollapseClickListener {
            finish()
        }

        viewModel.stateView.observe(this, Observer(this::setStateView))
        viewModel.stream.observe(this, Observer(this::setStream))

        viewModel.loadUrl()
    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    private fun setStateView(state:Int){
        when(state){
            LiveViewModel.STATE_VIEW_PROGRESS_BAR -> {
                layoutVideoView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            LiveViewModel.STATE_VIEW_SHOW_CONTENT -> {
                layoutVideoView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
            LiveViewModel.STATE_VIEW_SHOW_ERROR -> {
                layoutVideoView.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun setStream(stream: Stream){
        layoutVideoView.setVideoPath(stream.titleChannel, stream.thumbnail, stream.streamUrl)
    }

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, LiveActivity::class.java)
        }
    }
}