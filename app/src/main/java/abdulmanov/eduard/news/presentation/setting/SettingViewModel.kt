package abdulmanov.eduard.news.presentation.setting

import abdulmanov.eduard.news.domain.interactors.SettingInteractor
import abdulmanov.eduard.news.presentation.navigation.Screens
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    private val router:Router,
    private val settingInteractor: SettingInteractor
):ViewModel() {

    private val _themeType = MutableLiveData<Int>()
    val themeType:LiveData<Int>
        get() = _themeType

    init {
        _themeType.value = settingInteractor.getThemeType()
    }

    fun setCurrentThemeType(type: Int) = settingInteractor.setThemeType(type)

    fun onOpenFeedbackCommandClick(){
        val feedbackData = settingInteractor.getFeedbackData()
        router.navigateTo(Screens.Feedback(feedbackData))
    }

    fun onOpenSupplierWebsiteCommandClick(){
        val url = settingInteractor.getLinkSupplierWebsite()
        router.navigateTo(Screens.Website(url))
    }

    fun onBackCommandClick() = router.exit()
}