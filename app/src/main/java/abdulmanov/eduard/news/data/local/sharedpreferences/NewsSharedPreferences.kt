package abdulmanov.eduard.news.data.local.sharedpreferences

import abdulmanov.eduard.news.BuildConfig
import abdulmanov.eduard.news.data.repositories.NewsRepositoryImpl
import abdulmanov.eduard.news.domain.models.news.Category
import android.app.Application
import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewsSharedPreferences(app: Application) {

    private val sharedPreferences = app.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun setCategories(categories:List<Category>){
        val jsonCategories = Gson().toJson(categories)
        sharedPreferences.edit { putString(PREF_CATEGORIES, jsonCategories) }
    }

    fun getCategories():List<Category>{
        val jsonCategories = sharedPreferences.getString(PREF_CATEGORIES, "[]")
        val type = object : TypeToken<List<Category>>() {}.type
        return Gson().fromJson(jsonCategories, type)
    }

    companion object{
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_news_pref"
        private const val PREF_CATEGORIES = "selected_categories"
    }
}