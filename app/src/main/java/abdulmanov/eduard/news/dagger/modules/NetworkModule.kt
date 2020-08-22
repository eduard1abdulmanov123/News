package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.remote.NetworkHelper
import abdulmanov.eduard.news.data.remote.news.NewsProvider
import abdulmanov.eduard.news.data.remote.news.VestiProvider
import abdulmanov.eduard.news.data.remote.streams.TvChannelsProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideNewsProvider(client: OkHttpClient): NewsProvider {
        return VestiProvider(client)
    }

    @Singleton
    @Provides
    fun provideStreamsProvider(networkHelper: NetworkHelper): TvChannelsProvider{
        return TvChannelsProvider(networkHelper)
    }

    @Singleton
    @Provides
    fun provideNetworkHelper(client: OkHttpClient):NetworkHelper {
        return NetworkHelper(client)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }
}