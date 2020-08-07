package abdulmanov.eduard.news.data.repositories

import abdulmanov.eduard.news.data.local.database.dao.IdentifiersDao
import abdulmanov.eduard.news.data.local.database.models.IdentifierDbModel
import abdulmanov.eduard.news.data.remote.NewsProvider
import abdulmanov.eduard.news.domain.models.news.Category
import abdulmanov.eduard.news.domain.models.news.New
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Completable
import io.reactivex.Single

class NewsRepositoryImpl(
    private val newsProvider: NewsProvider,
    private val identifiersDao: IdentifiersDao,
    private val sharedPreferences: SharedPreferences
) : NewsRepository {

    private var cashedNews: List<New> = emptyList()

    override fun getNews(): Single<List<New>> {
        return Single.create {
            cashedNews = getNewsFromNetwork()
            deleteIdentifiersThatAreMissing(cashedNews)
            updateCategoriesToSharedPreferences(cashedNews)
            it.onSuccess(cashedNews)
        }
    }

    private fun getNewsFromNetwork():List<New>{
        val news = newsProvider.getNews()
        news.forEach {
            it.alreadyRead = identifiersDao.isIdentifierExist(it.id)
        }
        return news
    }

    private fun deleteIdentifiersThatAreMissing(news:List<New>){
        val existingIdentifiers = news.map { it.id }
        identifiersDao.deleteIdentifiersThatAreMissing(existingIdentifiers)
    }

    private fun updateCategoriesToSharedPreferences(news: List<New>){
        val categoriesFromNetwork = news.filter { it.category.isNotEmpty() }
            .map { Category(it.category) }
            .distinctBy { it.name }
        val categoriesFromSharedPreferences = getCategories()

        categoriesFromNetwork.forEach { category ->
            category.selected = categoriesFromSharedPreferences.find { it.name == category.name }?.selected ?: false
        }
        saveCategories(categoriesFromNetwork)
    }

    override fun getCachedNews(): List<New> {
        return cashedNews
    }

    override fun getCategories(): List<Category> {
        val jsonCategories = sharedPreferences.getString(PREF_CATEGORIES,"[]")
        val type = object : TypeToken<List<Category>>(){}.type
        return Gson().fromJson(jsonCategories, type)
    }

    override fun saveCategories(categories: List<Category>) {
        val jsonCategories = Gson().toJson(categories)
        sharedPreferences.edit {
            putString(PREF_CATEGORIES, jsonCategories)
        }
    }

    override fun markNewAsAlreadyRead(id: Long): Completable {
        return Completable.create {
            val identifier = IdentifierDbModel(id)
            identifiersDao.insertIdentifier(identifier)
        }
    }

    companion object{
        private const val PREF_CATEGORIES = "categories"
    }
}