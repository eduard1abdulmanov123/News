package abdulmanov.eduard.news.presentation.newshostcontainer

import abdulmanov.eduard.news.presentation.navigation.NavigationConstants
import androidx.lifecycle.ViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class NewsHostContainerViewModel @Inject constructor(
    @Named(NavigationConstants.MAIN_ROUTER) private val router: Router
):ViewModel() {

    fun onBackCommandClick() = router.exit()
}