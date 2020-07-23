package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.domain.interactors.SettingInteractor
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import abdulmanov.eduard.news.domain.repositories.SettingRepository
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

    @Singleton
    @Provides
    fun provideSettingInteractor(repository: SettingRepository): SettingInteractor {
        return SettingInteractor(repository)
    }
}