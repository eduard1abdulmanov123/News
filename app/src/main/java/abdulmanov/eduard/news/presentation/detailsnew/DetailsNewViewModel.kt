package abdulmanov.eduard.news.presentation.detailsnew

import androidx.lifecycle.ViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class DetailsNewViewModel @Inject constructor(
    private val router: Router
) : ViewModel() {

    fun onBackCommandClick() = router.exit()
}