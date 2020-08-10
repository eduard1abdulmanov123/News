package abdulmanov.eduard.news.domain.interactors

import abdulmanov.eduard.news.domain._common.DateFormatter
import abdulmanov.eduard.news.domain.models.news.Category
import abdulmanov.eduard.news.domain.models.news.New
import abdulmanov.eduard.news.domain.repositories.NewsRepository
import io.reactivex.Completable
import io.reactivex.Single

class NewsInteractor(private val newsRepository: NewsRepository) {

    fun getNewsFilteredByCategory(): Single<List<New>> {
        return newsRepository.getNews().map{ it.filterNewsByCategory() }
    }

    fun getCachedNewsFilteredByCategory(): List<New>{
        return newsRepository.getCachedNews().filterNewsByCategory()
    }

    private fun List<New>.filterNewsByCategory(): List<New>{
        val selectedCategory = getSelectedCategories()

        return sortedByDescending { DateFormatter.parseDate(it.date).time }
            .filter { it.category in selectedCategory }
    }

    private fun getSelectedCategories(): List<String>{
        val categories = newsRepository.getCategories()
        val selectedCategories = categories.filter { it.selected }

        val filterCategories = if(selectedCategories.isNotEmpty()){
            selectedCategories
        }else{
            categories
        }

        return filterCategories.map { it.name }
    }

    fun getCategories(): List<Category>{
        return newsRepository.getCategories()
    }

    fun saveCategories(categories: List<Category>){
        newsRepository.saveCategories(categories)
    }

    fun getQuantitySelectedCategories(): Int {
        return newsRepository.getCategories().filter { it.selected }.size
    }

    fun markNewAsAlreadyRead(id: Long): Completable {
        return newsRepository.markNewAsAlreadyRead(id)
    }
}