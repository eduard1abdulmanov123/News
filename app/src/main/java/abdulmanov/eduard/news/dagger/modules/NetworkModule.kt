package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.remote.NewsProvider
import abdulmanov.eduard.news.data.remote.VestiProvider
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

}