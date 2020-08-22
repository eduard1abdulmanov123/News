package abdulmanov.eduard.news.data.local.sharedpreferences

import abdulmanov.eduard.news.BuildConfig
import android.app.Application
import android.content.Context
import androidx.core.content.edit

class TvChannelsSharedPreferences(app:Application) {

    private val sharedPreferences = app.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun setIdSelectedTvChannel(id: Long) {
        sharedPreferences.edit { putLong(PREF_ID_SELECTED_TV_CHANNEL, id) }
    }

    fun getIdSelectedTvChannel(): Long {
        return sharedPreferences.getLong(PREF_ID_SELECTED_TV_CHANNEL, -1L)
    }

    companion object{
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_tv_channels"
        private const val PREF_ID_SELECTED_TV_CHANNEL = "id_selected_tv_channel"
    }
}