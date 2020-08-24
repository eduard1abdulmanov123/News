package abdulmanov.eduard.news.dagger.modules.news

import abdulmanov.eduard.news.dagger.annotations.FragmentScope
import abdulmanov.eduard.news.data.local.database.dao.IdentifiersDao
import abdulmanov.eduard.news.data.local.sharedpreferences.NewsSharedPreferences
import abdulmanov.eduard.news.data.remote.NewsProvider
import abdulmanov.eduard.news.data.repositories.NewsRepositoryImpl
import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class NewsDomainModule {

    @FragmentScope
    @Provides
    fun provideNewsInteractor(repository: NewsRepository): NewsInteractor {
        return NewsInteractor(repository)
    }

    @FragmentScope
    @Provides
    fun provideNewRepository(newsProvider: NewsProvider, identifiersDao: IdentifiersDao, sharedPreferences: NewsSharedPreferences): NewsRepository {
        return NewsRepositoryImpl(newsProvider, identifiersDao, sharedPreferences)
    }
}