package abdulmanov.eduard.news.dagger.components

import abdulmanov.eduard.news.dagger.modules.*
import abdulmanov.eduard.news.data.local.sharedpreferences.SettingSharedPreferences
import abdulmanov.eduard.news.presentation.detailsnew.DetailsNewFragment
import abdulmanov.eduard.news.presentation.main.MainActivity
import abdulmanov.eduard.news.presentation.news.NewsFragment
import abdulmanov.eduard.news.presentation.news.filternewsdialog.FilterNewsBottomSheetDialog
import abdulmanov.eduard.news.presentation.setting.SettingFragment
import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NavigationModule::class,
        ViewModelModule::class,
        InteractorModule::class,
        RepositoryModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        SharedPreferencesModule::class,
        AppSubcomponentsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context, @BindsInstance app: Application): AppComponent
    }

    fun liveStreamComponent():LiveStreamComponent.Factory

    fun inject(app: Application)

    fun inject(mainActivity: MainActivity)

    fun inject(newsFragment: NewsFragment)

    fun inject(filterNewsBottomSheetDialog: FilterNewsBottomSheetDialog)

    fun inject(detailsNewFragment: DetailsNewFragment)

    fun inject(settingFragment: SettingFragment)

    fun getSettingSharedPreferences(): SettingSharedPreferences
}

@Module(subcomponents = [LiveStreamComponent::class])
class AppSubcomponentsModule