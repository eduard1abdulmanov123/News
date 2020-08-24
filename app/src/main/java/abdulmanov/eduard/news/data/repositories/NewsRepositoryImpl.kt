package abdulmanov.eduard.news.data.repositories

import abdulmanov.eduard.news.data.local.database.dao.IdentifiersDao
import abdulmanov.eduard.news.data.local.database.models.IdentifierDbModel
import abdulmanov.eduard.news.data.local.sharedpreferences.NewsSharedPreferences
import abdulmanov.eduard.news.data.remote.news.NewsProvider
import abdulmanov.eduard.news.domain.models.news.Category
import abdulmanov.eduard.news.domain.models.news.New
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import io.reactivex.Completable
import io.reactivex.Single

class NewsRepositoryImpl(
    private val newsProvider: NewsProvider,
    private val identifiersDao: IdentifiersDao,
    private val sharedPreferences: NewsSharedPreferences
) : NewsRepository {

    private var cashedNews: List<New> = emptyList()

    override fun getNews(): Single<List<New>> {
        return Single.create {
            cashedNews = getNewsFromNetwork()
            deleteIdentifiersThatAreMissing(cashedNews)
            it.onSuccess(cashedNews)
        }
    }

    private fun getNewsFromNetwork(): List<New> {
        val news = newsProvider.getNews()
        news.forEach {
            it.alreadyRead = identifiersDao.isIdentifierExist(it.id)
        }
        return news
    }

    private fun deleteIdentifiersThatAreMissing(news: List<New>) {
        val existingIdentifiers = news.map { it.id }
        identifiersDao.deleteIdentifiersThatAreMissing(existingIdentifiers)
    }

    override fun getCachedNews(): List<New> {
        return cashedNews
    }

    override fun getCategories(): List<Category> {
        val categories = cashedNews.map { Category(it.category) }.distinctBy { it.name }
        val selectedCategories = sharedPreferences.getSelectedCategories()

        categories.forEach {
            it.selected = (it.name in selectedCategories)
        }

        return categories
    }

    override fun saveSelectedCategories(categories: List<Category>) {
        sharedPreferences.setSelectedCategories(categories.map { it.name })
    }

    override fun markNewAsAlreadyRead(id: Long): Completable {
        return Completable.create {
            val identifier = IdentifierDbModel(id)
            identifiersDao.insertIdentifier(identifier)
        }
    }
}