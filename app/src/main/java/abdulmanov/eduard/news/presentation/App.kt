package abdulmanov.eduard.news.presentation

import abdulmanov.eduard.news.dagger.ScopeManager
import abdulmanov.eduard.news.dagger.components.AppComponent
import abdulmanov.eduard.news.dagger.components.DaggerAppComponent
import abdulmanov.eduard.news.data.local.sharedpreferences.SettingSharedPreferences
import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import javax.inject.Inject

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext, this)
    }

    val scopeManager: ScopeManager by lazy {
        ScopeManager(appComponent)
    }

    override fun onCreate() {
        super.onCreate()
        initPicasso()
        Stetho.initializeWithDefaults(this)
    }
    
    private fun initPicasso() {
        val picasso = Picasso.Builder(this)
            .memoryCache(LruCache(MAX_CACHE))
            .build()
        Picasso.setSingletonInstance(picasso)
    }

    companion object {
        private const val MAX_CACHE = 250_000_000
    }
}