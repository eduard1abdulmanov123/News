package abdulmanov.eduard.news.presentation.setting

import abdulmanov.eduard.news.domain.interactors.SettingInteractor
import abdulmanov.eduard.news.presentation.navigation.CiceroneConstants
import abdulmanov.eduard.news.presentation.navigation.Screens
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class SettingViewModel @Inject constructor(
    @Named(CiceroneConstants.MAIN_ROUTER) private val router: Router,
    private val settingInteractor: SettingInteractor
) : ViewModel() {

    private val _changeThemeTypeEvent = LiveEvent<Int>()
    val changeThemeTypeEvent: LiveData<Int>
        get() = _changeThemeTypeEvent

    fun setCurrentThemeType(type: Int) {
        settingInteractor.setThemeType(type)
        _changeThemeTypeEvent.value = type
    }

    fun getCurrentThemeType(): Int {
        return settingInteractor.getThemeType()
    }

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