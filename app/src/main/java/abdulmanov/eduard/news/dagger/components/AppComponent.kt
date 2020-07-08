package abdulmanov.eduard.news.dagger.components

import abdulmanov.eduard.news.dagger.modules.*
import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewFragment
import abdulmanov.eduard.news.presentation.live.LiveActivity
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
        ViewModelModule::class,
        InteractorModule::class,
        RepositoryModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(newsFragment: NewsFragment)

    fun inject(liveActivity: LiveActivity)

    fun inject(detailsNewFragment: DetailsNewFragment)
}