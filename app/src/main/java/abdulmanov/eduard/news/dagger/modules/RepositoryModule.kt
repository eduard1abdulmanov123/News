package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.local.database.dao.IdentifiersDao
import abdulmanov.eduard.news.data.local.sharedpreferences.NewsSharedPreferences
import abdulmanov.eduard.news.data.local.sharedpreferences.SettingSharedPreferences
import abdulmanov.eduard.news.data.remote.news.NewsProvider
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
    fun provideNewRepository(newsProvider: NewsProvider, identifiersDao: IdentifiersDao, sharedPreferences: NewsSharedPreferences): NewsRepository {
        return NewsRepositoryImpl(newsProvider, identifiersDao, sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideSettingRepository(sharedPreferences: SettingSharedPreferences): SettingRepository {
        return SettingRepositoryImpl(sharedPreferences)
    }
}