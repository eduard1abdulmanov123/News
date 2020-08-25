package abdulmanov.eduard.news.presentation.newshostcontainer

import abdulmanov.eduard.news.R
import abdulmanov.eduard.news.dagger.components.NewsComponent
import abdulmanov.eduard.news.presentation.App
import abdulmanov.eduard.news.presentation._common.base.ViewModelFactory
import abdulmanov.eduard.news.presentation._common.extensions.addOnBackPressedCallback
import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewFragment
import abdulmanov.eduard.news.presentation.navigation.CiceroneConstants
import abdulmanov.eduard.news.presentation.navigation.Screens
import abdulmanov.eduard.news.presentation.news.NewsFragment
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject
import javax.inject.Named

class NewsHostContainerFragment : Fragment(R.layout.fragment_news_host_container) {

    lateinit var newsComponent: NewsComponent

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<NewsHostContainerViewModel> { viewModelFactory }

    @Inject
    @Named(CiceroneConstants.NEWS_NAVIGATOR_HOLDER)
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator: SupportAppNavigator by lazy {
        object : SupportAppNavigator(requireActivity(), childFragmentManager, R.id.newsFragmentContainer) {
            override fun setupFragmentTransaction(command: Command, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction) {
                if (command is Forward && currentFragment is NewsFragment && nextFragment is DetailsNewFragment) {
                    animateTransitionFromNewsFragmentToDetailsNewFragment(currentFragment, nextFragment, fragmentTransaction)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        newsComponent = (requireActivity().application as App).scopeManager.attachNewsComponent()
        newsComponent.inject(this)

        addOnBackPressedCallback(viewModel::onBackCommandClick)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.findFragmentById(R.id.newsFragmentContainer) == null) {
            navigator.applyCommands(arrayOf(Replace(Screens.News)))
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
        if (!requireActivity().isChangingConfigurations) {
            (requireActivity().application as App).scopeManager.detachNewsComponent()
        }
    }

    private fun animateTransitionFromNewsFragmentToDetailsNewFragment(
        newsFragment: NewsFragment,
        detailsNewFragment: DetailsNewFragment,
        fragmentTransaction: FragmentTransaction
    ) {
        newsFragment.reenterTransition = null
        newsFragment.exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.duration).toLong()
        }

        detailsNewFragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = resources.getInteger(R.integer.duration).toLong()
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            scrimColor = ContextCompat.getColor(requireContext(), R.color.colorScrim)
            fadeProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.2f, 0.9f)
            shapeMaskProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f, 1.0f)
            scaleMaskProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f, 1.0f)
            scaleProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f, 1.0f)
            startContainerColor = ContextCompat.getColor(requireContext(), R.color.colorBackground)
            containerColor = ContextCompat.getColor(requireContext(), R.color.colorBackground)
            startElevation = 4f
        }

        detailsNewFragment.sharedElementReturnTransition = MaterialContainerTransform().apply {
            duration = resources.getInteger(R.integer.duration).toLong()
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            scrimColor = ContextCompat.getColor(requireContext(), R.color.colorScrim)
            fadeProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.2f, 0.9f)
            shapeMaskProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f, 1.0f)
            scaleMaskProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f, 1.0f)
            scaleProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.0f, 1.0f)
            endContainerColor = ContextCompat.getColor(requireContext(), R.color.colorBackground)
            containerColor = ContextCompat.getColor(requireContext(), R.color.colorBackground)
            endElevation = 4f
        }

        val root = newsFragment.currentSelectViewHolder?.itemView ?: return
        fragmentTransaction.addSharedElement(root, root.transitionName)
        fragmentTransaction.setReorderingAllowed(true)
    }

    companion object {
        fun newInstance(): NewsHostContainerFragment {
            return NewsHostContainerFragment()
        }
    }
}