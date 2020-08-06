package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.BuildConfig
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class SharedPreferencesModule {

    @Singleton
    @Provides
    @Named(SETTING_SHARED_PREFERENCES_NAME)
    fun provideSettingSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("${BuildConfig.APPLICATION_ID}$SETTING_SHARED_PREFERENCES_NAME", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    @Named(NEWS_SHARED_PREFERENCES_NAME)
    fun provideNewsSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("${BuildConfig.APPLICATION_ID}$NEWS_SHARED_PREFERENCES_NAME", Context.MODE_PRIVATE)
    }

    companion object {
        const val SETTING_SHARED_PREFERENCES_NAME = "_setting_pref"
        const val NEWS_SHARED_PREFERENCES_NAME = "_news_pref"
    }
}