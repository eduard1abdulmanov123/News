package abdulmanov.eduard.news.presentation.detailsnew

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.news.presentation._common.extensions.loadImg
import abdulmanov.eduard.news.presentation.news.models.NewPresentationModel
import abdulmanov.eduard.news.presentation.newshostcontainer.NewsHostContainerFragment
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.fragment_details_new.*
import javax.inject.Inject

class DetailsNewFragment : Fragment(R.layout.fragment_details_new) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<DetailsNewViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireParentFragment() as NewsHostContainerFragment).newsComponent.inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.new.observe(viewLifecycleOwner, Observer(this::setData))

        if (savedInstanceState == null) {
            viewModel.setNew(requireArguments().getParcelable(ARGUMENT_NEW)!!)
        }
    }

    private fun initUI() {
        detailsNewToolbar.run {
            setNavigationIcon(R.drawable.ic_close)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
            inflateMenu(R.menu.menu_details_new)
            setOnMenuItemClickListener(this@DetailsNewFragment::onOptionsItemSelected)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareItem -> viewModel.onShareNewCommandClick()
        }
        return true
    }

    private fun setData(new: NewPresentationModel) {
        detailsNewRootNestedScrollView.transitionName = requireContext().getString(R.string.root_transition, new.id)

        categoryTextView.text = new.category
        dateTextView.text = new.date
        nameTextView.text = new.title
        fullDescriptionTextView.text = new.fullDescription
        iconImageView.loadImg(new.image, callback = object : Callback {
            override fun onSuccess() {
                startPostponedEnterTransition()
            }

            override fun onError(e: Exception?) {
                startPostponedEnterTransition()
            }
        })
    }

    companion object {
        private const val ARGUMENT_NEW = "ARGUMENT_NEW"

        fun newInstance(new: NewPresentationModel): DetailsNewFragment {
            return DetailsNewFragment().apply {
                arguments = bundleOf(ARGUMENT_NEW to new)
            }
        }
    }
}