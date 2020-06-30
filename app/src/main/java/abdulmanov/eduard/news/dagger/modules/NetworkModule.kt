package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.network.NewsProvider
import abdulmanov.eduard.news.data.network.NewsXmlParser
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideNewsProvider(client: OkHttpClient, xmlParser: NewsXmlParser): NewsProvider {
        return NewsProvider(client, xmlParser)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Singleton
    @Provides
    fun provideNewsXmlParser(): NewsXmlParser {
        return NewsXmlParser()
    }
}