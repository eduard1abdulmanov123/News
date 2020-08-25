package abdulmanov.eduard.news.dagger.components

import abdulmanov.eduard.news.dagger.modules.app.*
import abdulmanov.eduard.news.data.local.sharedpreferences.SettingSharedPreferences
import abdulmanov.eduard.news.presentation.main.MainActivity
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

    fun liveStreamComponent(): LiveStreamComponent.Factory

    fun settingComponent(): SettingComponent.Factory

    fun newsComponent(): NewsComponent.Factory

    fun inject(mainActivity: MainActivity)

    fun settingSharedPreferences(): SettingSharedPreferences
}

@Module(subcomponents = [LiveStreamComponent::class, SettingComponent::class, NewsComponent::class])
class AppSubcomponentsModule