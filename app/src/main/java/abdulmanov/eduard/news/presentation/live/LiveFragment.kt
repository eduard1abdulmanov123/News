package abdulmanov.eduard.news.presentation.live

import androidx.fragment.app.Fragment
import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation.base.ViewModelFactory
import abdulmanov.eduard.news.presentation.navigation.BackButtonListener
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class LiveFragment : Fragment(R.layout.fragment_live), BackButtonListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LiveViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(LiveViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        viewModel.onBackCommandClick()
        return true
    }

    companion object {
        fun newInstance(): LiveFragment {
            return LiveFragment()
        }
    }
}