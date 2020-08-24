package abdulmanov.eduard.news.presentation.news.filternewsdialog

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain.models.news.Category
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation._common.extensions.addViews
import abdulmanov.eduard.news.presentation._common.extensions.setOnClickListener
import abdulmanov.eduard.news.presentation.news.NewsFragment
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_dialog_filter_news.*
import javax.inject.Inject

class FilterNewsBottomSheetDialog : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<FilterNewsViewModel> { viewModelFactory }

    private var callback: FilterNewsCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (parentFragment as NewsFragment).newsComponent.inject(this)

        callback = parentFragment as FilterNewsCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_dialog_filter_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        throwOffTextView.setOnClickListener(viewModel::throwOffFilterNews)

        applyTextView.setOnClickListener {
            viewModel.applyFilterNews()
            callback?.onChangeFilterNews()
            callback = null
            dismiss()
        }

        viewModel.categories.observe(viewLifecycleOwner, Observer(this::setData))
    }

    @SuppressLint("InflateParams")
    private fun setData(categories: List<Category>) {
        val viewsFromCategories = categories.map { category ->
            (layoutInflater.inflate(R.layout.item_category, null) as TextView).apply {
                text = category.name
                isSelected = category.selected
                setOnClickListener {
                    viewModel.selectCategory(category, !isSelected)
                }
            }
        }
        categoriesFlowLayout.removeAllViews()
        categoriesFlowLayout.addViews(viewsFromCategories)
    }

    companion object {
        const val TAG = "FilterNewsBottomSheetDialog"

        fun newInstance(): FilterNewsBottomSheetDialog {
            return FilterNewsBottomSheetDialog()
        }
    }

    interface FilterNewsCallback {
        fun onChangeFilterNews()
    }
}