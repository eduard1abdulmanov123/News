package abdulmanov.eduard.news.presentation.live

import androidx.fragment.app.Fragment
import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class LiveFragment : Fragment(R.layout.fragment_live) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LiveViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(LiveViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onBackCommandClick()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): LiveFragment {
            return LiveFragment()
        }
    }
}