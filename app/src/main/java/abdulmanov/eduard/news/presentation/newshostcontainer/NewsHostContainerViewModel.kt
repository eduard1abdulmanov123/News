package abdulmanov.eduard.news.presentation.newshostcontainer

import abdulmanov.eduard.news.presentation.navigation.CiceroneConstants
import androidx.lifecycle.ViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class NewsHostContainerViewModel @Inject constructor(
    @Named(CiceroneConstants.MAIN_ROUTER) private val router: Router
) : ViewModel() {

    fun onBackCommandClick() = router.exit()
}