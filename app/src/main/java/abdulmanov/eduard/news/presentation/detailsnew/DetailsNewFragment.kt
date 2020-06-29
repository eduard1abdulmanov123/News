package abdulmanov.eduard.news.presentation.detailsnew

import androidx.fragment.app.Fragment
import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation.base.ViewModelFactory
import abdulmanov.eduard.news.presentation.navigation.BackButtonListener
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_details_new.*
import javax.inject.Inject

class DetailsNewFragment : Fragment(R.layout.fragment_details_new), BackButtonListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: DetailsNewViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(DetailsNewViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI(){
        detailsNewToolbar.setNavigationIcon(R.drawable.ic_close)
        detailsNewToolbar.setNavigationOnClickListener { viewModel.onBackCommandClick() }
        detailsNewToolbar.inflateMenu(R.menu.menu_details_new)
        detailsNewToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.shareItem -> {}
        }
        return true
    }

    override fun onBackPressed(): Boolean {
        viewModel.onBackCommandClick()
        return true
    }

    companion object {
        fun newInstance(): DetailsNewFragment {
            return DetailsNewFragment()
        }
    }
}