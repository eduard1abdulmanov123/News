package abdulmanov.eduard.news.presentation

import abdulmanov.eduard.news.dagger.components.AppComponent
import abdulmanov.eduard.news.dagger.components.DaggerAppComponent
import android.app.Application

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}