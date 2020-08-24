package abdulmanov.eduard.news.presentation.setting

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation._common.extensions.addOnBackPressedCallback
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_setting.*
import javax.inject.Inject

class SettingFragment : Fragment(R.layout.fragment_setting) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<SettingViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).scopeManager.attachSettingComponent().inject(this)
        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        viewModel.changeThemeTypeEvent.observe(viewLifecycleOwner, Observer(AppCompatDelegate::setDefaultNightMode))
    }

    override fun onDetach() {
        super.onDetach()
        if(!requireActivity().isChangingConfigurations) {
            (requireActivity().application as App).scopeManager.detachSettingComponent()
        }
    }

    private fun initUI() {
        settingToolbar.run {
            setTitle(R.string.setting_title)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { viewModel.onBackCommandClick() }
        }

        containerFeedbackConstraintLayout.setOnClickListener {
            viewModel.onOpenFeedbackCommandClick()
        }

        containerLicenseConstraintLayout.setOnClickListener {
            viewModel.onOpenSupplierWebsiteCommandClick()
        }

        when (viewModel.getCurrentThemeType()) {
            AppCompatDelegate.MODE_NIGHT_YES -> darkThemeRadioButton.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> lightThemeRadioButton.isChecked = true
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> automaticallyThemeRadioButton.isChecked = true
        }
        changeThemeRadioGroup.jumpDrawablesToCurrentState()

        changeThemeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.darkThemeRadioButton -> viewModel.setCurrentThemeType(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.lightThemeRadioButton -> viewModel.setCurrentThemeType(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.automaticallyThemeRadioButton -> viewModel.setCurrentThemeType(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }
}