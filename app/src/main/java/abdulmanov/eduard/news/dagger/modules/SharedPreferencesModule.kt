package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.local.SharedPreferencesStore
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
    @Named(SharedPreferencesStore.SETTING_SHARED_PREFERENCES_NAME)
    fun provideSettingSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(SharedPreferencesStore.SETTING_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    @Named(SharedPreferencesStore.NEWS_SHARED_PREFERENCES_NAME)
    fun provideNewsSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(SharedPreferencesStore.NEWS_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}