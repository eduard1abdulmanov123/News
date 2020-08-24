package abdulmanov.eduard.news.presentation.news.filternewsdialog

import abdulmanov.eduard.news.domain.interactors.NewsInteractor
import abdulmanov.eduard.news.domain.models.news.Category
import abdulmanov.eduard.news.presentation._common.extensions.notifyObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class FilterNewsViewModel @Inject constructor(
    private val newsInteractor: NewsInteractor
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    init {
        _categories.value = newsInteractor.getCategories()
    }

    fun selectCategory(category: Category, selected: Boolean) {
        _categories.value?.find { it == category }?.selected = selected
        _categories.notifyObserver()
    }

    fun throwOffFilterNews() {
        _categories.value?.forEach { it.selected = false }
        _categories.notifyObserver()
    }

    fun applyFilterNews() {
        _categories.value ?: return

        val selectedCategories = _categories.value!!.filter { it.selected }
        newsInteractor.saveSelectedCategories(selectedCategories)
    }
}