package abdulmanov.eduard.news.data.repositories

import abdulmanov.eduard.news.data.network.NewsProvider
import abdulmanov.eduard.news.domain.models.New
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import io.reactivex.Single

class NewsRepositoryImpl(private val newsProvider: NewsProvider) : NewsRepository {

    override fun getNews(): Single<List<New>> {
        return Single.create {
            val news = newsProvider.getNews()
            it.onSuccess(news)
        }
    }
}