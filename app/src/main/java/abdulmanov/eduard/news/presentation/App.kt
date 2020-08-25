package abdulmanov.eduard.news.presentation

import abdulmanov.eduard.news.dagger.components.AppComponent
import abdulmanov.eduard.news.dagger.components.DaggerAppComponent
import abdulmanov.eduard.news.dagger.scope.ScopeManager
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext, this)
    }

    val scopeManager: ScopeManager by lazy {
        ScopeManager(appComponent)
    }

    override fun onCreate() {
        super.onCreate()
        initThemeApp()
        initPicasso()
        Stetho.initializeWithDefaults(this)
        RxJavaPlugins.setErrorHandler {}
    }

    private fun initThemeApp(){
        val theme = appComponent.settingSharedPreferences().getThemeType()
        AppCompatDelegate.setDefaultNightMode(theme)
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