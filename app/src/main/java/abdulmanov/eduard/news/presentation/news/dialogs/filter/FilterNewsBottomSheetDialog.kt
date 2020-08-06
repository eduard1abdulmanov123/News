package abdulmanov.eduard.news.presentation.news.dialogs.filter

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.domain.models.news.Category
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_dialog_filter_news.*
import kotlinx.android.synthetic.main.item_new.view.*
import javax.inject.Inject

class FilterNewsBottomSheetDialog:BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: FilterNewsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(FilterNewsViewModel::class.java)
    }

    private lateinit var callback: FilterNewsCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)

        val parentFragment = parentFragment
        if (parentFragment is FilterNewsCallback) {
            callback = parentFragment
        } else if (context is FilterNewsCallback) {
            callback = context
        }
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

        throwOffTextView.setOnClickListener {
            viewModel.throwOffFilterNews()
        }

        applyTextView.setOnClickListener {
            viewModel.applyFilterNews()
            callback.onChangeFilterNews()
            dismiss()
        }

        viewModel.categories.observe(viewLifecycleOwner, Observer(this::setData))
    }

    @SuppressLint("InflateParams")
    private fun setData(categories: List<Category>){
        categoriesFlowLayout.removeAllViews()

        categories.forEach {category ->
            val viewForCategory = layoutInflater.inflate(R.layout.item_category, null)
            viewForCategory.titleTextView.text = category.name
            viewForCategory.titleTextView.isSelected = category.selected

            viewForCategory.titleTextView.setOnClickListener {
                viewModel.selectCategories(category, !viewForCategory.titleTextView.isSelected)
            }

            categoriesFlowLayout.addView(viewForCategory)
        }
    }

    companion object{
        const val TAG = "FilterNewsBottomSheetDialog"

        fun newInstance(): FilterNewsBottomSheetDialog {
            return FilterNewsBottomSheetDialog()
        }
    }

    interface FilterNewsCallback{
        fun onChangeFilterNews()
    }
}