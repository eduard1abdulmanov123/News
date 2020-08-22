package abdulmanov.eduard.news.dagger.modules

import abdulmanov.eduard.news.data.local.sharedpreferences.NewsSharedPreferences
import abdulmanov.eduard.news.data.local.sharedpreferences.SettingSharedPreferences
import abdulmanov.eduard.news.data.local.sharedpreferences.TvChannelsSharedPreferences
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
    fun provideSettingSharedPreferences(app: Application): SettingSharedPreferences {
        return SettingSharedPreferences(app)
    }

    @Singleton
    @Provides
    fun provideNewsSharedPreferences(app: Application): NewsSharedPreferences {
        return NewsSharedPreferences(app)
    }

    @Singleton
    @Provides
    fun provideChannelsSharedPreferences(app: Application): TvChannelsSharedPreferences {
        return TvChannelsSharedPreferences(app)
    }

}