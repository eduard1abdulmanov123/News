package abdulmanov.eduard.news.domain.repositories

import abdulmanov.eduard.news.domain.models.New
import abdulmanov.eduard.news.domain.models.Stream
import io.reactivex.Completable
import io.reactivex.Single

interface NewsRepository {

    fun getNews(page: Int): Single<List<New>>

    fun markNewAsAlreadyRead(id: Long): Completable

    fun getStream(): Single<Stream>
}