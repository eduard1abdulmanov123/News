package abdulmanov.eduard.news.presentation

import abdulmanov.eduard.news.dagger.components.AppComponent
import abdulmanov.eduard.news.dagger.components.DaggerAppComponent
import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
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