package abdulmanov.eduard.news.presentation.main

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation._common.extensions.setAppTheme
import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewFragment
import abdulmanov.eduard.news.presentation.navigation.Screens
import abdulmanov.eduard.news.presentation.news.NewsFragment
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setAppTheme(viewModel.getCurrentThemeType())
        }

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
        detailsNewFragment.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
        detailsNewFragment.enterTransition = Fade()
        newsFragment.exitTransition = Fade()
        detailsNewFragment.sharedElementReturnTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)

        fragmentTransaction.setReorderingAllowed(true)

        val view = newsFragment.currentSelectViewHolder?.itemView ?: return
        val iconImageView = view.iconImageView
        val titleTextView = view.titleTextView
        val categoryTextView = view.categoryTextView
        val dateTextView = view.dateTextView
        val descriptionTextView = view.descriptionTextView
        fragmentTransaction.addSharedElement(iconImageView, iconImageView.transitionName)
        fragmentTransaction.addSharedElement(titleTextView, titleTextView.transitionName)
        fragmentTransaction.addSharedElement(categoryTextView, categoryTextView.transitionName)
        fragmentTransaction.addSharedElement(dateTextView, dateTextView.transitionName)
        fragmentTransaction.addSharedElement(descriptionTextView, descriptionTextView.transitionName)
    }
}