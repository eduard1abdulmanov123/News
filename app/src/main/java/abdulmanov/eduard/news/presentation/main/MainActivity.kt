package abdulmanov.eduard.news.presentation.main

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation._common.extensions.setAppTheme
import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewFragment
import abdulmanov.eduard.news.presentation.navigation.Screens
import abdulmanov.eduard.news.presentation.news.NewsFragment
import abdulmanov.eduard.news.presentation.setting.SettingFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.item_new.view.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val navigator = object : SupportAppNavigator(this, R.id.fragmentContainer) {
        override fun setupFragmentTransaction(command: Command, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction) {
            if (command is Forward && currentFragment is NewsFragment && nextFragment is DetailsNewFragment) {
                animateTransitionFromNewsFragmentToDetailsNewFragment(currentFragment, nextFragment, fragmentTransaction)
            }

            if(command is Forward && currentFragment is NewsFragment && nextFragment is SettingFragment){
                animateTransitionFromNewsFragmentToSettingFragment(currentFragment, nextFragment)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        if (savedInstanceState == null) {
            setAppTheme(viewModel.getCurrentThemeType())
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawable(null)

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf(Replace(Screens.News)))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun animateTransitionFromNewsFragmentToDetailsNewFragment(
        newsFragment: NewsFragment,
        detailsNewFragment: DetailsNewFragment,
        fragmentTransaction: FragmentTransaction
    ) {
        newsFragment.reenterTransition = null
        newsFragment.exitTransition = Hold().apply { duration = 650 }

        detailsNewFragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 650
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            scrimColor = ContextCompat.getColor(this@MainActivity, R.color.colorScrim)
            fadeProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.2f, 0.9f)
            shapeMaskProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f,1.0f)
            scaleMaskProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f,1.0f)
            scaleProgressThresholds =  MaterialContainerTransform.ProgressThresholds(0.0f,1.0f)
            startContainerColor = ContextCompat.getColor(this@MainActivity, R.color.background)
            containerColor = ContextCompat.getColor(this@MainActivity, R.color.background)
            startElevation = 4f
        }

        detailsNewFragment.sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = 650
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            scrimColor = ContextCompat.getColor(this@MainActivity, R.color.colorScrim)
            fadeProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.2f, 0.9f)
            shapeMaskProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f,1.0f)
            scaleMaskProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f,1.0f)
            scaleProgressThresholds =  MaterialContainerTransform.ProgressThresholds(0.0f,1.0f)
            endContainerColor = ContextCompat.getColor(this@MainActivity, R.color.background)
            containerColor = ContextCompat.getColor(this@MainActivity, R.color.background)
            endElevation = 4f
        }

        val view = newsFragment.currentSelectViewHolder?.itemView ?: return
        val root = view.itemNewRoot
        fragmentTransaction.addSharedElement(root, root.transitionName)
        fragmentTransaction.setReorderingAllowed(true)
    }

    private fun animateTransitionFromNewsFragmentToSettingFragment(newsFragment: NewsFragment, settingFragment: SettingFragment){
        newsFragment.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply { duration = 650 }
        newsFragment.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply { duration = 650 }

        settingFragment.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply { duration = 650 }
        settingFragment.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply { duration = 650 }
    }
}