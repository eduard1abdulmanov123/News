package abdulmanov.eduard.news.data.local.sharedpreferences

import abdulmanov.eduard.news.BuildConfig
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

class SettingSharedPreferences(app: Application) {

    private val sharedPreferences = app.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun setThemeType(type: Int) {
        sharedPreferences.edit { putInt(PREF_THEME_TYPE, type) }
    }

    fun getThemeType(): Int {
        return sharedPreferences.getInt(PREF_THEME_TYPE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    companion object {
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_setting_pref"
        private const val PREF_THEME_TYPE = "theme_type"
    }
}