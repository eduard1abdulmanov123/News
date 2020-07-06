package abdulmanov.eduard.news.presentation.detailsnew

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation._common.extensions.getScreenSize
import abdulmanov.eduard.news.presentation._common.extensions.loadImg
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.fragment_details_new.*
import javax.inject.Inject

class DetailsNewFragment : Fragment(R.layout.fragment_details_new) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: DetailsNewViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(DetailsNewViewModel::class.java)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        viewModel.new.observe(viewLifecycleOwner, Observer(this::setData))
        viewModel.sendNewLiveEvent.observe(viewLifecycleOwner, Observer(this::startIntentSendNew))

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
            R.id.shareItem -> viewModel.sendNew()
        }
        return true
    }

    private fun setData(new: NewPresentationModel) {
        categoryTextView.transitionName = requireContext().getString(R.string.category_transition, new.id)
        categoryTextView.text = new.category

        dateTextView.transitionName = requireContext().getString(R.string.date_transition, new.id)
        dateTextView.text = new.date

        titleTextView.transitionName = requireContext().getString(R.string.title_transition, new.id)
        titleTextView.text = new.title

        fullDescriptionTextView.transitionName = requireContext().getString(R.string.description_transition, new.id)
        fullDescriptionTextView.text = new.fullDescription

        iconImageView.transitionName = requireContext().getString(R.string.image_transition, new.id)
        iconImageView.loadImg(new.image, callback = object : Callback {
            override fun onSuccess() {
                startPostponedEnterTransition()
            }

            override fun onError(e: Exception) {
                startPostponedEnterTransition()
            }
        })
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