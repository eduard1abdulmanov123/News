package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Singleton
    @Provides
    fun provideNewsInteractor(repository: NewsRepository): NewsInteractor {
        return NewsInteractor(repository)
    }
}