package abdulmanov.eduard.news.presentation.live

import androidx.lifecycle.ViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class LiveViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

    fun onBackCommandClick() = router.exit()
}