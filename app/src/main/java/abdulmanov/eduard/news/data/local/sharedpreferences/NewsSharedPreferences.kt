package abdulmanov.eduard.news.data.local.sharedpreferences

import abdulmanov.eduard.news.BuildConfig
import android.app.Application
import android.content.Context
import androidx.core.content.edit

class NewsSharedPreferences(app: Application) {

    private val sharedPreferences = app.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun setSelectedCategories(categories: List<String>) {
        sharedPreferences.edit { putStringSet(PREF_SELECTED_CATEGORIES, categories.toSet()) }
    }

    fun getSelectedCategories(): List<String> {
        val selectedCategories = sharedPreferences.getStringSet(PREF_SELECTED_CATEGORIES, setOf())
        return selectedCategories?.toList() ?: listOf()
    }

    companion object {
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_news_pref"
        private const val PREF_SELECTED_CATEGORIES = "selected_categories"
    }
}