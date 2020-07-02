package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.db.dao.NewDao
import abdulmanov.eduard.news.data.network.NewsProvider
import abdulmanov.eduard.news.data.repositories.NewsRepositoryImpl
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideNewRepository(newsProvider: NewsProvider, newDao: NewDao): NewsRepository {
        return NewsRepositoryImpl(newsProvider, newDao)
    }
}