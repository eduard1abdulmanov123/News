package abdulmanov.eduard.news.presentation.detailsnew

import androidx.fragment.app.Fragment
import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.getScreenSize
import abdulmanov.eduard.news.presentation._common.loadImg
import abdulmanov.eduard.news.presentation._common.ViewModelFactory
import abdulmanov.eduard.news.presentation.navigation.BackButtonListener
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
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

        viewModel.new.observe(viewLifecycleOwner, Observer(this::setData))
        viewModel.shareNewLiveEvent.observe(viewLifecycleOwner, Observer(this::startIntentSendNew))

        if (savedInstanceState == null) {
            viewModel.setNew(requireArguments().getParcelable(ARGUMENT_NEW)!!)
        }
    }

    private fun initUI() {
        detailsNewToolbar.setNavigationIcon(R.drawable.ic_close)
        detailsNewToolbar.setNavigationOnClickListener { viewModel.onBackCommandClick() }
        detailsNewToolbar.inflateMenu(R.menu.menu_details_new)
        detailsNewToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected)

        val size = requireContext().getScreenSize()
        iconImageView.layoutParams.height = (size.x / WIDTH_TO_HEIGHT_RATIO).toInt()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareItem -> viewModel.shareNew()
        }
        return true
    }

    override fun onBackPressed(): Boolean {
        viewModel.onBackCommandClick()
        return true
    }

    private fun setData(new: NewPresentationModel) {
        categoryTextView.text = new.category
        dateTextView.text = new.date
        titleTextView.text = new.title
        iconImageView.loadImg(new.image, R.color.color_placeholder)
        fullDescriptionTextView.text = new.fullDescription
    }

    private fun startIntentSendNew(link: String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, link)
        }
        startActivity(sendIntent)
    }

    companion object {
        private const val WIDTH_TO_HEIGHT_RATIO = 1.76

        private const val ARGUMENT_NEW = "ARGUMENT_NEW"

        fun newInstance(new: NewPresentationModel): DetailsNewFragment {
            return DetailsNewFragment().apply {
                arguments = bundleOf(ARGUMENT_NEW to new)
            }
        }
    }
}