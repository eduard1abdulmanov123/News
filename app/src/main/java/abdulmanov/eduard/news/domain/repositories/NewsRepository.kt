package abdulmanov.eduard.news.domain.repositories

import abdulmanov.eduard.news.domain.models.news.Category
import abdulmanov.eduard.news.domain.models.news.New
import io.reactivex.Completable
import io.reactivex.Single

interface NewsRepository {

    fun getNews(): Single<List<New>>

    fun getCachedNews(): List<New>

    fun getCategories(): List<Category>

    fun saveCategories(categories: List<Category>)

    fun markNewAsAlreadyRead(id: Long): Completable

}