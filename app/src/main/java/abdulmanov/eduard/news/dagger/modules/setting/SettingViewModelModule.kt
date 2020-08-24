package abdulmanov.eduard.news.dagger.modules.setting

import abdulmanov.eduard.news.dagger.annotations.ViewModelKey
import abdulmanov.eduard.news.presentation.setting.SettingViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SettingViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindSettingViewModel(viewModel: SettingViewModel): ViewModel
}