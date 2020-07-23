package abdulmanov.eduard.news.presentation.setting

import abdulmanov.eduard.news.domain.interactors.SettingInteractor
import abdulmanov.eduard.news.presentation.navigation.Screens
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    private val router: Router,
    private val settingInteractor: SettingInteractor
) : ViewModel() {

    private val _changeThemeTypeEvent = LiveEvent<Int>()
    val changeThemeTypeEvent: LiveData<Int>
        get() = _changeThemeTypeEvent

    fun setCurrentThemeType(type: Int) {
        settingInteractor.setThemeType(type)
        _changeThemeTypeEvent.value = type
    }

    fun getCurrentThemeType() = settingInteractor.getThemeType()

    fun onOpenFeedbackCommandClick() {
        val feedbackData = settingInteractor.getFeedbackData()
        router.navigateTo(Screens.Feedback(feedbackData))
    }

    fun onOpenSupplierWebsiteCommandClick() {
        val url = settingInteractor.getLinkSupplierWebsite()
        router.navigateTo(Screens.Website(url))
    }

    fun onBackCommandClick() = router.exit()
}