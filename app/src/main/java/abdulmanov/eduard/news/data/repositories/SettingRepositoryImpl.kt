package abdulmanov.eduard.news.data.repositories

import abdulmanov.eduard.news.BuildConfig
import abdulmanov.eduard.news.domain.models.feedback.FeedbackData
import abdulmanov.eduard.news.domain.repositories.SettingRepository
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

class SettingRepositoryImpl(private val sharedPreferences: SharedPreferences) : SettingRepository {

    override fun getLinkSupplierWebsite(): String {
        return LINK_SUPPLIER_WEBSITE
    }

    override fun getFeedbackData(): FeedbackData {
        return FeedbackData(Build.VERSION.SDK_INT, Build.MODEL, BuildConfig.VERSION_NAME)
    }

    override fun setThemeType(type: Int) {
        sharedPreferences.edit { putInt(PREF_THEME_TYPE, type) }
    }

    override fun getThemeType(): Int {
        return sharedPreferences.getInt(PREF_THEME_TYPE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    companion object {
        private const val LINK_SUPPLIER_WEBSITE = "https://www.vesti.ru/"
        private const val PREF_THEME_TYPE = "theme_type"
    }
}