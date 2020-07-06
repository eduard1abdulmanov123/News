package abdulmanov.eduard.news.data.repositories

import abdulmanov.eduard.news.data.db.dao.NewDao
import abdulmanov.eduard.news.data.network.NewsProvider
import abdulmanov.eduard.news.domain.models.New
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import io.reactivex.Single

class NewsRepositoryImpl(
    private val newsProvider: NewsProvider,
    private val newsDao: NewDao
) : NewsRepository {

    override fun getNews(page: Int): Single<List<New>> {
        return Single.create {
            if (page == 1) {
                val freshNews = newsProvider.getNews()
                newsDao.insertNews(freshNews)
                newsDao.deleteOldNews()
                it.onSuccess(getNewsFromDatabase(page))
            } else {
                it.onSuccess(getNewsFromDatabase(page))
            }
        }
    }

    private fun getNewsFromDatabase(page: Int): List<New> {
        return newsDao.getNewsByPage(page).map { newDbModel ->
            New(
                id = newDbModel.id,
                title = newDbModel.title,
                link = newDbModel.link,
                description = newDbModel.description,
                date = newDbModel.date,
                category = newDbModel.category,
                image = newDbModel.image,
                fullDescription = newDbModel.fullDescription
            )
        }
    }
}