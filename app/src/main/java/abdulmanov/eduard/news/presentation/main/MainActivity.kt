package abdulmanov.eduard.news.presentation.main

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation.navigation.CiceroneConstants
import abdulmanov.eduard.news.presentation.navigation.Screens
import abdulmanov.eduard.news.presentation.newshostcontainer.NewsHostContainerFragment
import abdulmanov.eduard.news.presentation.setting.SettingFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.transition.MaterialSharedAxis
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {

    @Inject
    @Named(CiceroneConstants.MAIN_NAVIGATOR_HOLDER)
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = object : SupportAppNavigator(this, R.id.fragmentContainer) {
        override fun setupFragmentTransaction(command: Command, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction) {
            if (command is Forward && currentFragment is NewsHostContainerFragment && nextFragment is SettingFragment) {
                animateTransitionFromNewsHostContainerFragmentToSettingFragment(currentFragment, nextFragment)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawable(null)

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf(Replace(Screens.NewsHostContainer)))
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

    private fun animateTransitionFromNewsHostContainerFragmentToSettingFragment(
        newsFragment: NewsHostContainerFragment,
        settingFragment: SettingFragment
    ) {
        newsFragment.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = this@MainActivity.resources.getInteger(R.integer.duration).toLong()
        }
        newsFragment.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = this@MainActivity.resources.getInteger(R.integer.duration).toLong()
        }

        settingFragment.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = this@MainActivity.resources.getInteger(R.integer.duration).toLong()
        }
        settingFragment.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = this@MainActivity.resources.getInteger(R.integer.duration).toLong()
        }
    }
}