package abdulmanov.eduard.news.dagger.components

import abdulmanov.eduard.news.dagger.annotations.FragmentScope
import abdulmanov.eduard.news.dagger.modules.setting.SettingDomainModule
import abdulmanov.eduard.news.dagger.modules.setting.SettingViewModelModule
import abdulmanov.eduard.news.presentation.setting.SettingFragment

import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [SettingViewModelModule::class, SettingDomainModule:: class])
interface SettingComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():SettingComponent
    }

    fun inject(settingFragment: SettingFragment)
}