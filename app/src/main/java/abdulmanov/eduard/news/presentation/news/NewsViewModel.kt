package abdulmanov.eduard.news.presentation.news

import abdulmanov.eduard.news.presentation.navigation.Screens
import androidx.lifecycle.ViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

    fun onOpenLiveScreenCommandClick() = router.navigateTo(Screens.Live)

    fun onOpenDetailsNewScreenCommandClick() = router.navigateTo(Screens.DetailsNew)

    fun onBackCommandClick() = router.exit()
}