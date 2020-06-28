package abdulmanov.eduard.news.dagger.components

import abdulmanov.eduard.news.dagger.modules.NavigationModule
import abdulmanov.eduard.news.dagger.modules.ViewModelModule
import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewFragment
import abdulmanov.eduard.news.presentation.live.LiveFragment
import abdulmanov.eduard.news.presentation.main.MainActivity
import abdulmanov.eduard.news.presentation.news.NewsFragment
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NavigationModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(newsFragment: NewsFragment)

    fun inject(liveFragment: LiveFragment)

    fun inject(detailsNewFragment: DetailsNewFragment)
}