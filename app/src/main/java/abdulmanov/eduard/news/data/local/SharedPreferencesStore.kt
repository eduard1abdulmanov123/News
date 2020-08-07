package abdulmanov.eduard.news.data.local

import abdulmanov.eduard.news.BuildConfig

class SharedPreferencesStore {

    companion object {
        const val SETTING_SHARED_PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_setting_pref"
        const val NEWS_SHARED_PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_news_pref"
    }
}