package abdulmanov.eduard.news.domain.repositories

import abdulmanov.eduard.news.domain.models.New
import io.reactivex.Single

interface NewsRepository {

    fun getNews(page: Int): Single<List<New>>
}