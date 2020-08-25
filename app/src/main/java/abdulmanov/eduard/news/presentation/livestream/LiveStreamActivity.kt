package abdulmanov.eduard.news.presentation.livestream

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.dagger.components.LiveStreamComponent
import abdulmanov.eduard.news.domain.models.streams.TvChannel
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation._common.extensions.setOnClickListener
import abdulmanov.eduard.news.presentation.livestream.changechanneldialog.ChangeTvChannelBottomSheetDialog
import abdulmanov.eduard.news.presentation.livestream.helper.VideoActivity
import abdulmanov.eduard.news.presentation.navigation.CiceroneConstants
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.core.view.iterator
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_live_stream.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
import javax.inject.Named

class LiveStreamActivity : VideoActivity(), ChangeTvChannelBottomSheetDialog.ChangeTvChannelCallback {

    lateinit var liveStreamComponent: LiveStreamComponent

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<LiveStreamViewModel> { viewModelFactory }

    @Inject
    @Named(CiceroneConstants.MAIN_NAVIGATOR_HOLDER)
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = SupportAppNavigator(this, -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        liveStreamComponent = (application as App).scopeManager.attachLiveStreamComponent()
        liveStreamComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_stream)

        initUI()

        viewModel.state.observe(this, Observer(this::setState))
        viewModel.errorMessage.observe(this, Observer(errorTextView::setText))
        viewModel.selectedTvChannel.observe(this, Observer(this::setData))
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!isChangingConfigurations){
            (application as App).scopeManager.detachLiveStreamComponent()
        }
    }

    override fun onBackPressed() {
        viewModel.onBackCommandClick()
    }

    override fun handlerWindowInsertTop(insertTop: Int) {
        (liveStreamManagementContainerConstraintLayout.layoutParams as FrameLayout.LayoutParams).topMargin = insertTop
    }

    override fun handlerVisibilityMenuItems(visibility: Boolean) {
        for(menuItem in streamToolbar.menu.iterator()){
            menuItem.isVisible = visibility
        }
    }

    override fun onChangeTvChannel() {
        viewModel.changeTvChannel()
    }

    private fun initUI(){
        streamToolbar.run {
            setNavigationIcon(R.drawable.ic_arrow_back_white)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
            inflateMenu(R.menu.menu_stream)
            setOnMenuItemClickListener(this@LiveStreamActivity::onOptionsItemSelected)
        }

        customVideoView.run {
            setOnCallbackError(viewModel::errorWhilePreparingTvChannel)
            setOnPreparedListener(viewModel::tvChannelHaveBeenPrepared)
            setOnCallbackVideoState(this@LiveStreamActivity::changeImageAccordingStateVideoView)
        }

        liveStreamStateManagementImageView.setOnClickListener(customVideoView::toggleVideoState)

        tryAgainTextView.setOnClickListener(viewModel::refresh)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.openChangeTvChannelItem -> {
                val dialog = ChangeTvChannelBottomSheetDialog.newInstance()
                dialog.show(supportFragmentManager, ChangeTvChannelBottomSheetDialog.TAG)
            }
        }
        return true
    }

    private fun changeImageAccordingStateVideoView(isPause: Boolean){
        val imageForLiveStreamState = if(isPause) R.drawable.ic_play_white else R.drawable.ic_pause_white
        liveStreamStateManagementImageView.setImageResource(imageForLiveStreamState)
    }

    private fun setState(state: Int){
        when(state){
            LiveStreamViewModel.VIEW_STATE_PROGRESS -> {
                liveStreamManagementContainerConstraintLayout.visibility = View.GONE
                liveStreamStateManagementImageView.visibility = View.GONE
                customVideoView.visibility = View.GONE
                errorContainerConstraintLayout.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            LiveStreamViewModel.VIEW_STATE_ERROR -> {
                liveStreamManagementContainerConstraintLayout.visibility = View.GONE
                liveStreamStateManagementImageView.visibility = View.GONE
                customVideoView.visibility = View.GONE
                errorContainerConstraintLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
            LiveStreamViewModel.VIEW_STATE_DATA -> {
                liveStreamManagementContainerConstraintLayout.visibility = View.VISIBLE
                liveStreamStateManagementImageView.visibility = View.GONE
                customVideoView.visibility = View.GONE
                errorContainerConstraintLayout.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            LiveStreamViewModel.VIEW_STATE_PREPARE_TV_CHANNEL -> {
                liveStreamManagementContainerConstraintLayout.visibility = View.VISIBLE
                liveStreamStateManagementImageView.visibility = View.GONE
                customVideoView.visibility = View.INVISIBLE
                errorContainerConstraintLayout.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            LiveStreamViewModel.VIEW_STATE_ERROR_WHILE_PREPARING_TV_CHANNEL -> {
                liveStreamManagementContainerConstraintLayout.visibility = View.VISIBLE
                liveStreamStateManagementImageView.visibility = View.GONE
                customVideoView.visibility = View.GONE
                errorContainerConstraintLayout.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
            LiveStreamViewModel.VIEW_STATE_TV_CHANNEL_HAVE_BEEN_PREPARED -> {
                liveStreamManagementContainerConstraintLayout.visibility = View.VISIBLE
                liveStreamStateManagementImageView.visibility = View.VISIBLE
                customVideoView.visibility = View.VISIBLE
                errorContainerConstraintLayout.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun setData(channel: TvChannel){
        streamToolbar.title = channel.title
        customVideoView.setVideoPath(channel.urlLiveStream)
        viewModel.prepareTvChannel()
    }

    companion object{
        fun getIntent(context: Context): Intent{
            return Intent(context, LiveStreamActivity::class.java)
        }
    }
}