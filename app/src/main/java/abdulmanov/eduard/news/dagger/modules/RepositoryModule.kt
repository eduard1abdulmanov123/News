package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.local.database.dao.IdentifiersDao
import abdulmanov.eduard.news.data.local.sharedpreferences.NewsSharedPreferences
import abdulmanov.eduard.news.data.remote.NewsProvider
import abdulmanov.eduard.news.data.repositories.NewsRepositoryImpl
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewRepository(newsProvider: NewsProvider, identifiersDao: IdentifiersDao, sharedPreferences: NewsSharedPreferences): NewsRepository {
        return NewsRepositoryImpl(newsProvider, identifiersDao, sharedPreferences)
    }
}