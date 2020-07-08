package abdulmanov.eduard.news.domain.interactors

import abdulmanov.eduard.news.domain.models.New
import abdulmanov.eduard.news.domain.models.Stream
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import io.reactivex.Completable
import io.reactivex.Single

class NewsInteractor(private val newsRepository: NewsRepository) {

    fun getNews(page: Int): Single<List<New>> {
        return newsRepository.getNews(page)
    }

    fun markNewAsAlreadyRead(id: Long): Completable {
        return newsRepository.markNewAsAlreadyRead(id)
    }

    fun getStream(): Single<Stream> {
        return newsRepository.getStream()
    }
}