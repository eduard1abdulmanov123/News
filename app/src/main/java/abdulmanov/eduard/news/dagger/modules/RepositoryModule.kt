package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.local.SharedPreferencesStore
import abdulmanov.eduard.news.data.local.database.dao.IdentifiersDao
import abdulmanov.eduard.news.data.remote.NewsProvider
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
    fun provideNewRepository(
        newsProvider: NewsProvider,
        identifiersDao: IdentifiersDao,
        @Named(SharedPreferencesStore.NEWS_SHARED_PREFERENCES_NAME) sharedPreferences: SharedPreferences
    ): NewsRepository {
        return NewsRepositoryImpl(newsProvider, identifiersDao, sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideSettingRepository(
        @Named(SharedPreferencesStore.NEWS_SHARED_PREFERENCES_NAME) sharedPreferences: SharedPreferences
    ): SettingRepository {
        return SettingRepositoryImpl(sharedPreferences)
    }
}