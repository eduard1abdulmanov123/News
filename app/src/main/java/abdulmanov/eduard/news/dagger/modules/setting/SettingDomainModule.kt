package abdulmanov.eduard.news.dagger.modules.setting

import abdulmanov.eduard.news.dagger.annotations.FragmentScope
import abdulmanov.eduard.news.data.local.sharedpreferences.SettingSharedPreferences
import abdulmanov.eduard.news.data.repositories.SettingRepositoryImpl
import abdulmanov.eduard.news.domain.interactors.SettingInteractor
import abdulmanov.eduard.news.domain.repositories.SettingRepository
import dagger.Module
import dagger.Provides

@Module
class SettingDomainModule {

    @FragmentScope
    @Provides
    fun provideSettingRepository(sharedPreferences: SettingSharedPreferences): SettingRepository {
        return SettingRepositoryImpl(sharedPreferences)
    }

    @FragmentScope
    @Provides
    fun provideSettingInteractor(settingRepository: SettingRepository): SettingInteractor {
        return SettingInteractor(settingRepository)
    }
}