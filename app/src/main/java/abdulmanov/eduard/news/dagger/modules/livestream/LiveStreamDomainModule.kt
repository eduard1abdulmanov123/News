package abdulmanov.eduard.news.dagger.modules.livestream

import abdulmanov.eduard.news.dagger.annotations.ActivityScope
import abdulmanov.eduard.news.data.local.sharedpreferences.TvChannelsSharedPreferences
import abdulmanov.eduard.news.data.remote.streams.TvChannelsProvider
import abdulmanov.eduard.news.data.repositories.TvChannelsRepositoryImpl
import abdulmanov.eduard.news.domain.interactors.TvChannelsInteractor
import abdulmanov.eduard.news.domain.repositories.TvChannelsRepository
import dagger.Module
import dagger.Provides

@Module
class LiveStreamDomainModule {

    @ActivityScope
    @Provides
    fun provideTvChannelsInteractor(repository: TvChannelsRepository): TvChannelsInteractor {
        return TvChannelsInteractor(repository)
    }

    @ActivityScope
    @Provides
    fun provideTvChannelsRepository(tvChannelsProvider: TvChannelsProvider, sharedPreferences: TvChannelsSharedPreferences): TvChannelsRepository{
        return TvChannelsRepositoryImpl(tvChannelsProvider, sharedPreferences)
    }
}