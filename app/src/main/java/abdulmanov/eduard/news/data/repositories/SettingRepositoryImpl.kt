package abdulmanov.eduard.news.data.repositories

import abdulmanov.eduard.news.BuildConfig
import abdulmanov.eduard.news.data.local.sharedpreferences.SettingSharedPreferences
import abdulmanov.eduard.news.domain.models.feedback.FeedbackData
import abdulmanov.eduard.news.domain.repositories.SettingRepository
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

class SettingRepositoryImpl(private val sharedPreferences: SettingSharedPreferences) : SettingRepository {

    override fun getLinkSupplierWebsite(): String {
        return LINK_SUPPLIER_WEBSITE
    }

    override fun getFeedbackData(): FeedbackData {
        return FeedbackData(Build.VERSION.SDK_INT, Build.MODEL, BuildConfig.VERSION_NAME)
    }

    override fun setThemeType(type: Int) {
        sharedPreferences.setThemeType(type)
    }

    override fun getThemeType(): Int {
        return sharedPreferences.getThemeType()
    }

    companion object {
        private const val LINK_SUPPLIER_WEBSITE = "https://www.vesti.ru/"
    }
}