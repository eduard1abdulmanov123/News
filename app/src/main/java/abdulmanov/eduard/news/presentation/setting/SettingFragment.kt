package abdulmanov.eduard.news.presentation.setting

import androidx.fragment.app.Fragment
import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_setting.*
import javax.inject.Inject

class SettingFragment : Fragment(R.layout.fragment_setting) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: SettingViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SettingViewModel::class.java)
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

        initUI()

        viewModel.themeType.observe(viewLifecycleOwner, Observer(this::setCheckedTypeTheme))
    }

    private fun initUI() {
        settingToolbar.setTitle(R.string.setting_title)
        settingToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        settingToolbar.setNavigationOnClickListener { viewModel.onBackCommandClick() }

        changeThemeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.darkThemeRadioButton -> viewModel.setCurrentThemeType(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.lightThemeRadioButton -> viewModel.setCurrentThemeType(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.automaticallyThemeRadioButton -> viewModel.setCurrentThemeType(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        containerFeedback.setOnClickListener {
            viewModel.onOpenFeedbackCommandClick()
        }

        containerLicense.setOnClickListener{
            viewModel.onOpenSupplierWebsiteCommandClick()
        }

    }

    private fun setCheckedTypeTheme(type:Int){
        changeThemeRadioGroup.clearCheck()
        when (type) {
            AppCompatDelegate.MODE_NIGHT_YES -> darkThemeRadioButton.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> lightThemeRadioButton.isChecked = true
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> automaticallyThemeRadioButton.isChecked = true
        }
    }

    companion object {
        fun newInstance():SettingFragment{
            return SettingFragment()
        }
    }
}