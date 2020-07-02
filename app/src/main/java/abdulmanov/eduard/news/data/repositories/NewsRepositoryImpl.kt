package abdulmanov.eduard.news.data.repositories

import abdulmanov.eduard.news.data.db.dao.NewDao
import abdulmanov.eduard.news.data.db.dao.NewDao.Companion.ITEMS_PER_PAGE
import abdulmanov.eduard.news.data.db.models.NewDbModel
import abdulmanov.eduard.news.data.network.NewsProvider
import abdulmanov.eduard.news.domain.models.New
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import io.reactivex.Single

class NewsRepositoryImpl(
    private val newsProvider: NewsProvider,
    private val newsDao: NewDao
) : NewsRepository {

    override fun getNews(): Single<List<New>> {
        return Single.create {
            val freshNews = newsProvider.getNews()
            newsDao.insertNews(freshNews)
            newsDao.deleteOldNews()


            val allNews = newsDao.getNewsByPage(1).map { newDbModel->
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

            it.onSuccess(allNews)
        }
    }
}