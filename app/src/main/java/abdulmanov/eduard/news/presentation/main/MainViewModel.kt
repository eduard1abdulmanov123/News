package abdulmanov.eduard.news.presentation.main

import abdulmanov.eduard.news.domain.interactors.SettingInteractor
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val settingInteractor: SettingInteractor
):ViewModel() {

    fun getCurrentThemeType() = settingInteractor.getThemeType()

}