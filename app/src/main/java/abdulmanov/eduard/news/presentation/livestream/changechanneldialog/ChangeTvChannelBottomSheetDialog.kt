package abdulmanov.eduard.news.presentation.livestream.changechanneldialog

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain.models.streams.TvChannel
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation.livestream.LiveStreamActivity
import abdulmanov.eduard.news.presentation.livestream.changechanneldialog.adapters.TvChannelsAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_dialog_change_tv_channel.*
import javax.inject.Inject

class ChangeTvChannelBottomSheetDialog: BottomSheetDialogFragment(), TvChannelsAdapter.TvChannelsClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<ChangeTvChannelViewModel> { viewModelFactory }

    private var callback: ChangeTvChannelCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as LiveStreamActivity).liveStreamComponent.inject(this)

        callback = context as ChangeTvChannelCallback

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_dialog_change_tv_channel, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        channelsRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = TvChannelsAdapter(this@ChangeTvChannelBottomSheetDialog)
        }

        viewModel.tvChannels.observe(viewLifecycleOwner, Observer((channelsRecyclerView.adapter as TvChannelsAdapter)::swapData))
    }

    override fun onClickTvChannel(tvChannel: TvChannel) {
        viewModel.selectTvChannel(tvChannel)
        callback?.onChangeTvChannel()
        dismiss()
    }

    companion object {
        const val TAG = "ChangeChannelBottomSheetDialog"

        fun newInstance(): ChangeTvChannelBottomSheetDialog {
            return ChangeTvChannelBottomSheetDialog()
        }
    }

    interface ChangeTvChannelCallback{
        fun onChangeTvChannel()
    }
}