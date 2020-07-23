package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.db.dao.NewDao
import abdulmanov.eduard.news.data.network.NewsProvider
import abdulmanov.eduard.news.data.repositories.NewsRepositoryImpl
import abdulmanov.eduard.news.data.repositories.SettingRepositoryImpl
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import abdulmanov.eduard.news.domain.repositories.SettingRepository
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewRepository(newsProvider: NewsProvider, newDao: NewDao): NewsRepository {
        return NewsRepositoryImpl(newsProvider, newDao)
    }

    @Singleton
    @Provides
    fun provideSettingRepository(@Named(SharedPreferencesModule.SETTING_SHARED_PREFERENCES_NAME) sharedPreferences: SharedPreferences): SettingRepository {
        return SettingRepositoryImpl(sharedPreferences)
    }
}