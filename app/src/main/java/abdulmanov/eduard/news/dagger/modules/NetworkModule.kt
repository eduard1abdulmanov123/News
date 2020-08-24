package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.remote.NetworkHelper
import abdulmanov.eduard.news.data.remote.NewsProvider
import abdulmanov.eduard.news.data.remote.TvChannelsProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideNewsProvider(networkHelper: NetworkHelper): NewsProvider {
        return NewsProvider(networkHelper)
    }

    @Singleton
    @Provides
    fun provideStreamsProvider(networkHelper: NetworkHelper): TvChannelsProvider {
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